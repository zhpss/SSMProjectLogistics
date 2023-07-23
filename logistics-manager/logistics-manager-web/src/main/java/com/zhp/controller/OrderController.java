package com.zhp.controller;

import com.zhp.dto.OrderDto;
import com.zhp.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-22 09:53
 **/
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService service;
    @RequestMapping("/orderDispatch")
    public String orderDispatch(Model model) throws Exception {
        service.queryAddRequirdData(model);
        return "order/addOrder";
    }
    @RequestMapping("/save")
    @ResponseBody
    public String saveOrder(@RequestBody OrderDto dto){
        service.saveOrder(dto);
        return "success";
    }
}
