package com.byqj.module.formBuild.controller;

import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormBuildAppVerNoDispatcher {
    @Autowired
    FormBuildFunctionNoDispatcher formBuildFunctionNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    public Object dispatchByAppVerNo() {
        String appVerNo = dataCenterService.getAppVersionNo();

        switch (appVerNo) {
            case "1.0.0":
                formBuildFunctionNoDispatcher.dispatchByFunctionNo();
                break;

            default:
                break;
        }
        return null;
    }

}
