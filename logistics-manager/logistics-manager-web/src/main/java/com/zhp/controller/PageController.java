package com.zhp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-09 11:16
 **/
@Controller
public class PageController {
    /*
    *
    * HOME页面
    *
    * */
    @RequestMapping(value = {"/","/login"})
    public String showMain(){
        return "login";
    }

    /*
    * RestFul风格处理
    *
    * */
    @RequestMapping("/{path}")
    public String showPage(@PathVariable String path){
        return path;
    }
}
