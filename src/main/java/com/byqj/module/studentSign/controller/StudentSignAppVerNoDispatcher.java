package com.byqj.module.studentSign.controller;

import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentSignAppVerNoDispatcher {
    @Autowired
    StudentSignFunctionNoDispatcher studentSignFunctionNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByAppVerNo() {
        String appVerNo = dataCenterService.getAppVersionNo();
        String functionNo = dataCenterService.getFunctionNo();


        switch (appVerNo) {
            case "1.0.0":
                studentSignFunctionNoDispatcher.dispatchByFunctionNo(functionNo);
                break;

            default:
                break;
        }
    }

}
