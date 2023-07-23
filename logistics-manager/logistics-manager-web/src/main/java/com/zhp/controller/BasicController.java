package com.zhp.controller;

import com.zhp.pojo.BasicData;
import com.zhp.service.IBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-16 22:15
 **/
/*
* 基础数据
* */
@Controller
@RequestMapping("/basic")
public class BasicController {
    @Autowired
    private IBasicService service;
    @RequestMapping("/query")
    public String query(BasicData data, Model model) throws Exception{
        List<BasicData> list = service.query(data);
        model.addAttribute("list",list);
        return "basic/basic";
    }
    @RequestMapping("/baseDispatch")
    public String baseDispatch(Integer id,Model model) throws Exception{
        if (id != null){
            //是更新操作 根据编号查询出对应的基础数据
            BasicData basicData = service.queryById(id);
            model.addAttribute("basicData",basicData);
        }
        //查询出所有的大类数据
        List<BasicData> list = service.queryAllParentData();
        model.addAttribute("parents",list);
        //根据获取当前用户
        return "/basic/updateBasic";
    }
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(BasicData data) throws Exception{

        if (data.getBaseId() != null && data.getBaseId() > 0){
            // 说明是更新操作
            service.updateBasicData(data);
        }else {
            service.addBasicData(data);
        }
        return "redirect:/basic/query";
    }
}
