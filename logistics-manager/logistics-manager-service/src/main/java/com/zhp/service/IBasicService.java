package com.zhp.service;

import com.zhp.pojo.BasicData;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description: 角色信息
 * @author: Mr.zhang
 * @create: 2023-07-09 14:21
 **/
/*
*
* 用户信息
* */
public interface IBasicService {
    /**
     * 根据条件查询基础数据信息
     * @param data
     * @return
     */
    List<BasicData> query(BasicData data) throws Exception;

    /**
     * 添加基础数据信息
     * @param data
     * @return
     */
    Integer addBasicData(BasicData data) throws Exception;

    BasicData queryById(Integer id) throws Exception;

    /**
     * 更新用户信息
     * @param data
     * @return
     */
    Integer updateBasicData(BasicData data) throws Exception;
    /*
    * 查询出所有的大类数据
    * */
    List<BasicData> queryAllParentData() throws Exception;


    /**
     * 根据编号删除基础数据信息
     * @param id
     * @return
     */
    Integer deleteBasicData(Integer id) throws Exception;

    List<BasicData> queryByParentName(String parentName) throws Exception;


}
