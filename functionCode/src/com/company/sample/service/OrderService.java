package com.company.sample.service;

import com.company.sample.bean.Order;
import com.company.sample.bean.admin.AdminOrderListParam;
import com.company.sample.bean.admin.EnterpriseAdminOrderListParam;
import com.company.sample.bean.admin.TypeAdminOrderListParam;
import com.company.sample.dao.OrderMapper;
import com.company.sample.utils.CurrentUserUtil;

import java.util.List;
import java.util.function.Consumer;

public class OrderService {

    private OrderMapper orderMapper;

    public List<Order> getAdminOrderListByParam(AdminOrderListParam adminParam) {
        OrderService.fillCommonCondition(adminParam::setEnterpriseType, adminParam::setEnterpriseId, adminParam::setTeamId);
        return orderMapper.findAdminOrderListByParam(adminParam);
    }

    public List<Order> getTypeAdminOrderListByParam(TypeAdminOrderListParam typeAdminParam) {
        OrderService.fillCommonCondition(typeAdminParam::setEnterpriseType,
                typeAdminParam::setEnterpriseId, typeAdminParam::setTeamId);
        return orderMapper.findTypeAdminOrderListByParam(typeAdminParam);
    }

    public List<Order> getEnterpriseOrderListByParam(EnterpriseAdminOrderListParam enterpriseAdminParam) {
        OrderService.fillCommonCondition(enterpriseAdminParam::setEnterpriseType,
                enterpriseAdminParam::setEnterpriseId, enterpriseAdminParam::setTeamId);
        return orderMapper.findEnterpriseAdminOrderListByParam(enterpriseAdminParam);
    }

    public static void fillCommonCondition(Consumer<String> setEnterpriseType,
                                           Consumer<String> setEnterpriseId, Consumer<String> setTeamId) {
        if (setEnterpriseType != null) {
            setEnterpriseType.accept(CurrentUserUtil.currentEnterpriseType());
        }
        if (setEnterpriseId != null) {
            setEnterpriseId.accept(CurrentUserUtil.currentEnterpriseId());
        }
        if (setTeamId != null) {
            setTeamId.accept(CurrentUserUtil.currentTeamId());
        }
    }
}
