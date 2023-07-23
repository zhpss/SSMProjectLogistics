package com.zhp.service;

import com.zhp.dto.CustomerDto;
import com.zhp.pojo.Customer;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-18 19:58
 **/
public interface ICustomerService {
    /**
     * 获取添加或者更新客户需要准备的数据
     * @param id
     * @param model
     */
    void getUpdateInfo(Integer id, Model model) throws Exception;

    Integer save(Customer customer);

    List<CustomerDto> query(Customer customer) throws Exception;

    void update(Customer customer);

    String checkCustomer(Integer id);

    void deleteById(Integer id);
}
