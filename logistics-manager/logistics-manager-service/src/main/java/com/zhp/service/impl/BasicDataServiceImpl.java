package com.zhp.service.impl;

import com.zhp.mapper.BasicDataMapper;
import com.zhp.pojo.BasicData;
import com.zhp.pojo.BasicDataExample;
import com.zhp.service.IBasicService;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-16 22:18
 **/
@Service
public class BasicDataServiceImpl implements IBasicService {


    @Autowired
    private BasicDataMapper basicDataMapper;


    @Override
    public List<BasicData> query(BasicData data) throws Exception {
        BasicDataExample example = new BasicDataExample();
        BasicDataExample.Criteria criteria = example.createCriteria();

        if (data != null ){
            if (data.getBaseName() != null && !"".equals(data.getBaseName())){
                criteria.andBaseNameEqualTo(data.getBaseName());

            }
            if (data.getParentId() != null && data.getParentId() > 0){
                criteria.andParentIdEqualTo(data.getParentId());
            }
        }
        return basicDataMapper.selectByExample(example);
    }

    @Override
    public Integer addBasicData(BasicData data) throws Exception {
        if (data.getParentId() != null && data.getParentId() == -1){
            // parentID == null 为大类
            data.setParentId(null);
        }
        return basicDataMapper.insertSelective(data);
    }

    @Override
    public BasicData queryById(Integer id) throws Exception {
        return basicDataMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateBasicData(BasicData data) throws Exception {
        if (data.getParentId() != null && data.getParentId() == -1){
           // data = this.queryById(data.getBaseId());
            data.setParentId(null);
        }
        return basicDataMapper.updateByPrimaryKeySelective(data);
    }

    @Override
    public List<BasicData> queryAllParentData() throws Exception {
        BasicDataExample example = new BasicDataExample();
        example.createCriteria().andParentIdIsNull();
        return basicDataMapper.selectByExample(example);
    }

    @Override
    public Integer deleteBasicData(Integer id) throws Exception {
        return basicDataMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据父类名称会对应的子类信息
     * @param parentName
     * @return
     */
    @Override
    public List<BasicData> queryByParentName(String parentName) throws Exception {
        //根据名称找到信息
        BasicData basicData = new BasicData();
        basicData.setBaseName(parentName);
        //常用区间 对应的 基础数据
        List<BasicData> query = this.query(basicData);
        if (query != null && query.size()>0){
            BasicData bd = query.get(0);
            BasicData newBd = new BasicData();
            newBd.setParentId(bd.getBaseId());
            List<BasicData> list = this.query(newBd);
            return list;
        }
        return null;

    }
}
