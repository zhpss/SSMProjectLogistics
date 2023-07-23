package com.zhp.service.impl;

import com.oracle.jrockit.jfr.ValueDefinition;
import com.zhp.Constant;
import com.zhp.dto.CustomerDto;
import com.zhp.dto.OrderDto;
import com.zhp.mapper.OrderDetailMapper;
import com.zhp.mapper.OrderMapper;
import com.zhp.pojo.BasicData;
import com.zhp.pojo.OrderDetail;
import com.zhp.pojo.User;
import com.zhp.service.IBasicService;
import com.zhp.service.ICustomerService;
import com.zhp.service.IOrderService;
import com.zhp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-22 09:55
 **/
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private IBasicService basicService;
    @Autowired
    private OrderDetailMapper detailMapper;
    /*
    *1.查询出所有的业务员
    *2.出巡出所有的客户
    *3.插叙基础数据
    *   付款方式
    *   货运方式
    *   取件方式
    *   常用区间
    *   单位
    * */
    @Override
    public void queryAddRequirdData(Model model) throws Exception {
        //1.查询所有的客户
        List<CustomerDto> customers = customerService.query(null);
        //2.查询出所有的业务员
        List<User> users = userService.queryUserByRoleName(Constant.ROLE_SALEMAN);
        //3.查询基础数据
        //3.1 付款方式
        List<BasicData> payments = basicService.queryByParentName(Constant.BASIC_PAYMENT_TYPE);
        //3.2 货运方式
        List<BasicData> freights = basicService.queryByParentName(Constant.BASIC_FREIGHT_TYPE);
        //3.3 取件方式
        List<BasicData> fetchs = basicService.queryByParentName(Constant.BASIC_FETCH_TYPE);
        //3.4 国家/城市
        List<BasicData> countrys = basicService.queryByParentName(Constant.BASIC_COMMN_INTERVAL);
        //3.5 单位
        List<BasicData> units = basicService.queryByParentName(Constant.BASIC_UNIT);

        model.addAttribute("customers",customers);
        model.addAttribute("users",users);
        model.addAttribute("payments",payments);
        model.addAttribute("freights",freights);
        model.addAttribute("fetchs",fetchs);
        model.addAttribute("countrys",countrys);
        model.addAttribute("units",units);


    }

    @Override
    public void saveOrder(OrderDto orderDto) {
        //保存主表数据
        orderMapper.insertSelective(orderDto);
        //保存详情数据
        List<OrderDetail> orderDetails = orderDto.getOrderDetails();
        if (orderDetails != null && orderDetails.size() > 0){
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.setOrderId(orderDto.getOrderId());
                detailMapper.insertSelective(orderDetail);
            }
        }

    }
}
