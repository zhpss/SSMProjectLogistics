package com.zhp.service;

import com.zhp.dto.OrderDto;
import org.springframework.ui.Model;

public interface IOrderService {
    public void queryAddRequirdData(Model model) throws Exception;
    public void saveOrder(OrderDto orderDto);

}
