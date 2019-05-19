package com.byqj.module.system.menuManagement.controller;

import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenueManagementAppVerNoDispatcher {
    @Autowired
    MenueManagementFunctionNoDispatcher menueManagementFunctionNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    public Object dispatchByAppVerNo() {
        String appVerNo = dataCenterService.getAppVersionNo();

        switch (appVerNo) {
            case "1.0.0":
                menueManagementFunctionNoDispatcher.dispatchByFunctionNo();
                break;

            default:
                break;
        }
        return null;
    }
}
