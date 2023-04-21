package com.company.sample.controller;


import com.company.sample.bean.admin.AdminOrderListParam;
import com.company.sample.bean.admin.EnterpriseAdminOrderListParam;
import com.company.sample.bean.admin.TypeAdminOrderListParam;
import com.company.sample.bean.vo.adminVo;
import com.company.sample.service.OrderService;
import com.company.sample.bean.Order;
import com.company.sample.bean.command.AdminOrderListCommand;
import com.company.sample.bean.command.EnterpriseAdminOrderListCommand;
import com.company.sample.bean.command.TypeAdminOrderListCommand;
import com.company.sample.bean.vo.AdminOrderListVO;
import com.company.sample.bean.vo.EnterpriseAdminOrderListVO;
import com.company.sample.bean.vo.TypeAdminOrderListVO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderController {

    private OrderService orderService;

    public List<adminVo> getAdminOrderList(AdminOrderListCommand command) {
        List<Order> orders = orderService.getAdminOrderListByParam((AdminOrderListParam) command.to());
        return orders.stream().map(AdminOrderListVO::from).collect(Collectors.toList());
    }

    public List<adminVo> getTypeAdminOrderList(TypeAdminOrderListCommand command) {
        List<Order> orders = orderService.getTypeAdminOrderListByParam((TypeAdminOrderListParam) command.to());
        return orders.stream().map(TypeAdminOrderListVO::from).collect(Collectors.toList());
    }

    public List<adminVo> getEnterpriseAdminOrderList(EnterpriseAdminOrderListCommand command) {
        List<Order> orders = orderService.getEnterpriseOrderListByParam((EnterpriseAdminOrderListParam) command.to());
        return orders.stream().map(EnterpriseAdminOrderListVO::from).collect(Collectors.toList());
    }
}
