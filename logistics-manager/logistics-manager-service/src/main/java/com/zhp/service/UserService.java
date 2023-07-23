package com.zhp.service;

import com.github.pagehelper.PageInfo;
import com.zhp.dto.UserDto;
import com.zhp.pojo.Role;
import com.zhp.pojo.User;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-09 14:21
 **/
/*
*
* 用户信息
* */
public interface UserService {
    /**
     * 根据条件查询用户信息
     * @param user
     * @return
     */
    List<User> query(User user) throws Exception;

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    Integer addUser(User user) throws Exception;

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Integer updateUser(User user) throws Exception;

    /**
     * 根据编号删除用户信息
     * @param id
     * @return
     */
    Integer deleteUser(Integer id) throws Exception;

    Integer saveOrUpdate(UserDto dto) throws Exception;

    User queryById(Integer userid) throws Exception;

    List<Integer> queryUserRoleIds(Integer userId);

    PageInfo<User> queryBypage(UserDto userDto) throws Exception;

    User login(String userName);

    List<Role> queryUserHaveRole(User user) throws Exception;

    List<User> queryUserByRoleName(String rolename) throws Exception;
}
