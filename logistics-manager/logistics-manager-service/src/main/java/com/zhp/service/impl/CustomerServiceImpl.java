package com.zhp.service.impl;

import com.zhp.Constant;
import com.zhp.dto.CustomerDto;
import com.zhp.mapper.CustomerMapper;
import com.zhp.mapper.OrderMapper;
import com.zhp.pojo.*;
import com.zhp.service.IBasicService;
import com.zhp.service.ICustomerService;
import com.zhp.service.IRoleService;
import com.zhp.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-18 19:59
 **/
@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private IBasicService basicService;
    @Autowired
    private OrderMapper orderMapper;


    @Override
    public void getUpdateInfo(Integer id, Model model) throws Exception {
        //查询角色是业务员的用户信息
        List<User> users = userService.queryUserByRoleName(Constant.ROLE_SALEMAN);
        //查询货运区间 基础数据
        List<BasicData> intervals = basicService.queryByParentName(Constant.BASIC_COMMN_INTERVAL);
        //
        if (id != null){
            //表示更新数据，根据当前的编号查询出对应的客户信息
            Customer customer = customerMapper.selectByPrimaryKey(id);
            model.addAttribute("customer",customer);
        }
        model.addAttribute("users",users);
        model.addAttribute("intervals",intervals);




    }

    @Override
    public Integer save(Customer customer) {
        return customerMapper.insertSelective(customer);
    }

    /**
     * 客户管理只能是 业务员 操作员 管理员
     *     操作员和管理员 可以查看所有的客户信息
     *     业务员只能查看属于自身的客户
     * @param customer
     * @return
     * @throws Exception
     */
    @Override
    public List<CustomerDto> query(Customer customer) throws Exception {
        // 1.根据当前登录用户获取角色信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Role> roles = userService.queryUserHaveRole(user);
        //2.判断当前用户是否具有操作员或者管理员的角色
        boolean flag = false;;
        for (Role role : roles) {
            if (Constant.ROLE_ADMIN.equals(role.getRoleName()) ||
                    Constant.ROLE_OPERATOR.equals(role.getRoleName())
            ){
                flag = true;
            }
        }


        CustomerExample example = new CustomerExample();
        if (!flag){
            // 说明只有 业务员这一个角色
            example.createCriteria().andUserIdEqualTo(user.getUserId());

        }
        List<Customer> list = customerMapper.selectByExample(example);
        //返回的结果是Dto
        List<CustomerDto> dtos = new ArrayList<>();
        if (list != null && list.size() > 0){
            for (Customer c : list) {
                CustomerDto dto = new CustomerDto();
                //实现对象属性的拷贝
                BeanUtils.copyProperties(c,dto);
                // 扩展Dto中的属性  业务名称，常用区间名称
                String userName = userService.queryById(dto.getUserId()).getUserName();
                dto.setUserName(userName);
                String baseName = basicService.queryById(dto.getBaseId()).getBaseName();
                dto.setIntervalName(baseName);
                dtos.add(dto);
            }
            return dtos;
        }


        List<Customer> customers = customerMapper.selectByExample(example);

        return null;
    }

    @Override
    public void update(Customer customer) {
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    @Override
    public String checkCustomer(Integer id) {
        OrderExample example = new OrderExample();
        example.createCriteria().andCustomerIdEqualTo(id);
        int i = orderMapper.countByExample(example);

        return i > 0?"1":"0";
    }

    @Override
    public void deleteById(Integer id) {
        customerMapper.deleteByPrimaryKey(id);
    }
}
