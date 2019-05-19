package com.byqj.module.common.operationLog.controller;

import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperationLogAppVerNoDispatcher {
    @Autowired
    OperationLogFunctionNoDispatcher operationLogFunctionNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    public Object dispatchByAppVerNo() {
        String appVerNo = dataCenterService.getAppVersionNo();

        switch (appVerNo) {
            case "1.0.0":
                operationLogFunctionNoDispatcher.dispatchByFunctionNo();
                break;

            default:
                break;
        }
        return null;
    }

}
