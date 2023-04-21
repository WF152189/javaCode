package com.company.sample.dao;

import com.company.sample.bean.Order;
import com.company.sample.bean.admin.Admin;
import com.company.sample.bean.admin.AdminOrderListParam;
import com.company.sample.bean.admin.EnterpriseAdminOrderListParam;
import com.company.sample.bean.admin.TypeAdminOrderListParam;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public List<Order> getListOrder(Admin admin){
        return new ArrayList<>();
    }

    public List<Order> findAdminOrderListByParam(AdminOrderListParam admin){
        return getListOrder(admin);
    }
    public List<Order> findTypeAdminOrderListByParam(TypeAdminOrderListParam admin){
        return getListOrder(admin);
    }
    public List<Order> findEnterpriseAdminOrderListByParam(EnterpriseAdminOrderListParam admin){
        return getListOrder(admin);
    }
}
