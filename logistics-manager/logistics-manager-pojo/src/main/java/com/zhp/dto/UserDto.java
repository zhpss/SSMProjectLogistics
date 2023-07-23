package com.zhp.dto;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.zhp.pojo.User;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-09 22:11
 **/
/*
*
* DTO 数据传输对象
* */
public class UserDto extends BasePage {

    public User user;

    public List<Integer> roleIds;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
