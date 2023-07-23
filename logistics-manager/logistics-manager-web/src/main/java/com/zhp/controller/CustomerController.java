package com.zhp.controller;

import com.zhp.dto.CustomerDto;
import com.zhp.pojo.Customer;
import com.zhp.service.ICustomerService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-18 20:00
 **/
@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @RequiresRoles(value = {"业务员","操作员","管理员"},logical = Logical.OR)
    @RequestMapping("/query")
    public String query(Customer customer,Model model) throws Exception{

        List<CustomerDto> query = customerService.query(customer);

        model.addAttribute("List",query);
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println(query.toString());
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        return "customer/customer";


    }





    @RequestMapping("/customerDispatch")
    public String customerDispatch(Integer id, Model model) throws Exception{
        customerService.getUpdateInfo(id,model);
        return "customer/updateCustomer";
    }
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Customer customer){
        if (customer != null && customer.getCustomerId() != null){
            //更新
            customerService.update(customer);
            return "redirect:/customer/query";
        }
        customerService.save(customer);
        return "redirect:/customer/query";

    }
    @RequestMapping("/checkCustomer")
    @ResponseBody
    public String checkCustomer(Integer id){
        return customerService.checkCustomer(id);

    }
    @RequestMapping("/deleteById")
    public String deleteById(Integer id){
        customerService.deleteById(id);
        System.out.println(id);
        System.out.println("------------------------------");
        System.out.println("------------------------------");
        System.out.println("------------------------------");
        System.out.println("++++++++++++++++++++++++++++++-----------------------------");
        return "redirect:/customer/query";

    }



}
