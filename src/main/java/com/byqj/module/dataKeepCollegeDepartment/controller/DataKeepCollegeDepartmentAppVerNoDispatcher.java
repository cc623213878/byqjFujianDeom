package com.byqj.module.dataKeepCollegeDepartment.controller;

import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataKeepCollegeDepartmentAppVerNoDispatcher {
    @Autowired
    DataKeepCollegeDepartmentFunctionNoDispatcher dataKeepCollegeDepartmentFunctionNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByAppVerNo() {
        String appVerNo = dataCenterService.getAppVersionNo();
        String functionNo = dataCenterService.getFunctionNo();


        switch (appVerNo) {
            case "1.0.0":
                dataKeepCollegeDepartmentFunctionNoDispatcher.dispatchByFunctionNo(functionNo);
                break;

            default:
                break;
        }
    }

}
