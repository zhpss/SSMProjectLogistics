package com.zhp.service;

import com.zhp.pojo.Role;
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
public interface IRoleService {
    /**
     * 根据条件查询用户信息
     * @param role
     * @return
     */
    List<Role> query(Role role) throws Exception;

    /**
     * 添加用户信息
     * @param role
     * @return
     */
    Integer addrole(Role role) throws Exception;

    Role queryById(Integer id) throws Exception;

    /**
     * 更新用户信息
     * @param role
     * @return
     */
    Integer updaterole(Role role) throws Exception;

    /**
     * 根据编号删除用户信息
     * @param id
     * @return
     */
    Integer deleterole(Integer id) throws Exception;
}
