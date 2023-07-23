package com.zhp.controller;

import com.zhp.pojo.Role;
import com.zhp.service.IRoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-09 16:01
 **/
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService service;

    @RequiresRoles(value = {"管理员","普通管理员"},logical = Logical.OR)
    @RequestMapping("/query")
    public String query(Role role, Model model) throws Exception {
        List<Role> query = service.query(role);
        model.addAttribute("list",query);
        return "role/role";
    }

    /**
     * 处理页面跳转
     * @return
     */
    @RequiresRoles(value = {"管理员"},logical = Logical.OR)
    @RequestMapping("/roleDispatch")
    public String handlePageDispatch(Integer id,Model model) throws Exception {
        if (id != null){
            //表示更新
            Role role = service.queryById(id);
            model.addAttribute("role",role);
        }
        //角色添加
        return "role/updateRole";

    }
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Role role) throws Exception {
        if (role.getRoleId() != null && role.getRoleId() > 0){
            //表示更新角色信息
            service.updaterole(role);
            return "redirect:/role/query";
        }
        service.addrole(role);
        return "redirect:/role/query";
    }
    @RequiresRoles(value = {"超级管理员","普通管理员","普通员工"},logical = Logical.OR)
    @RequestMapping("deleteById")
    public String deleteById(Integer id) throws Exception{
        service.deleterole(id);
        return "redirect:/role/query";
    }
}
