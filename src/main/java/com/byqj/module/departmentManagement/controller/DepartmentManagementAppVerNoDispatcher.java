package com.byqj.module.departmentManagement.controller;

import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentManagementAppVerNoDispatcher {
    @Autowired
    DepartmentManagementFunctionNoDispatcher departmentManagementFunctionNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    public Object dispatchByAppVerNo() {
        String appVerNo = dataCenterService.getAppVersionNo();

        switch (appVerNo) {
            case "1.0.0":
                departmentManagementFunctionNoDispatcher.dispatchByFunctionNo();
                break;

            default:
                break;
        }
        return null;
    }
}
