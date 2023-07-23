package com.zhp.service.impl;

import com.zhp.mapper.RoleMapper;
import com.zhp.pojo.Role;
import com.zhp.pojo.RoleExample;
import com.zhp.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-09 15:56
 **/
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> query(Role role) throws Exception {
        RoleExample roleExample = new RoleExample();
        return roleMapper.selectByExample(roleExample);
    }

    @Override
    public Integer addrole(Role role) throws Exception {
        return roleMapper.insertSelective(role);
    }

    @Override
    public Role queryById(Integer id) throws Exception {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updaterole(Role role) throws Exception {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public Integer deleterole(Integer id) throws Exception {
        return roleMapper.deleteByPrimaryKey(id);
    }
}
