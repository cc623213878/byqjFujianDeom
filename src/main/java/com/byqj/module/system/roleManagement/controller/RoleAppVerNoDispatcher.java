package com.byqj.module.system.roleManagement.controller;

import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleAppVerNoDispatcher {
    @Autowired
    RoleFunctionNoDispatcher roleFunctionNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    public Object dispatchByAppVerNo() {
        String appVerNo = dataCenterService.getAppVersionNo();

        switch (appVerNo) {
            case "1.0.0":
                roleFunctionNoDispatcher.dispatchByFunctionNo();
                break;

            default:
                break;
        }
        return null;
    }
}
