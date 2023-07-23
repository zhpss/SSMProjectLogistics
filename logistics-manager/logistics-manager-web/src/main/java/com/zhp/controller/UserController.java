package com.zhp.controller;

import com.github.pagehelper.PageInfo;
import com.zhp.dto.UserDto;
import com.zhp.pojo.Role;
import com.zhp.pojo.User;
import com.zhp.service.IRoleService;
import com.zhp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:用户管理
 * @author: Mr.zhang
 * @create: 2023-07-09 14:30
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private IRoleService roleService;
    @GetMapping("/query")
    public String query(User user, Model model,UserDto userDto) throws Exception {
        List<User> list = userService.queryBypage(userDto).getList();
        PageInfo<User> pageInfo = userService.queryBypage(userDto);
        model.addAttribute("pageModel",pageInfo);
        return "user/user";
    }
    @RequestMapping("/userDispatch")
    public String userDispatch(Integer userId,Model model)throws Exception {
        System.out.println("---------------------------------");

        System.out.println("---------------------------------");
        if (userId != null && userId > 0){
            //当前的请求是要更新用户数据，需要用户编号查询出用户的详细信息
            User user = userService.queryById(userId);
            System.out.println("---------------------------------");
            System.out.println(user);
            System.out.println("---------------------------------");
            model.addAttribute("user",user);
            //查询出当前用户具有的角色数据
            List<Integer> integers = userService.queryUserRoleIds(userId);
            model.addAttribute("ownerRoleIds",integers);
        }
        //查询所有的角色信息
        List<Role> query = roleService.query(new Role());
        Model role = model.addAttribute("roles", query);
        return "user/updateUser";
    }
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(UserDto dto) throws Exception {
        // 1.保存用户信息
        // 2.保存角色和用户的关联关系
        Integer count = userService.saveOrUpdate(dto);
        return "redirect:/user/query";
    }
    @RequestMapping("/checkUserName")
    @ResponseBody
    public String checkUserName(User user) throws Exception {
        List<User> query = userService.query(user);
        if (query == null || query.size() == 0){
            return "1";
        }
        System.out.println("----------------------------------");
        System.out.println("----------------------------------");
        System.out.println("----------------------------------");
        System.out.println("----------------------------------");
        System.out.println("----------------------------------");
        System.out.println("----------------------------------");
        System.out.println("-------------------checkUserName---------------");
        return "0";
    }
    @RequestMapping("/deleteUser")
    public String deleteUser(Integer userId) throws Exception{
        Integer integer = userService.deleteUser(userId);
        return "redirect:/user/query";
    }

}
