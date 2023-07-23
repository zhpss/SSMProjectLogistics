package com.zhp.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-16 15:13
 **/

/*
*   认证管理的控制器
*
* */
@Controller
public class LoginController {
    @RequestMapping("/login.do")
    public String login(Model model, HttpServletRequest servletRequest){
        Object attribute = servletRequest.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (attribute != null){
            System.out.println(attribute.toString()+"**********");
        }
        if (UnknownAccountException.class.getName().equals(attribute)){
            System.out.println("----账号错误----");
            model.addAttribute("msg","账号错误");
        } else if (IncorrectCredentialsException.class.getName().equals(attribute)){
            System.out.println("-----密码错误-----");
            model.addAttribute("msg","密码错误");
        } else {
            System.out.println("----其它错误-----");
            model.addAttribute("msg","其它错误");
        }
        return "login";
    }
    @RequestMapping("/logout.do")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "login";
    }
}
