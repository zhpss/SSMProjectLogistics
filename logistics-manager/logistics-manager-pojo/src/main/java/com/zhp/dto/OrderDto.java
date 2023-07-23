package com.zhp.dto;

import com.zhp.pojo.Order;
import com.zhp.pojo.OrderDetail;

import java.util.List;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-22 10:27
 **/
public class OrderDto extends Order {
    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
