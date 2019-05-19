package com.byqj.module.common.examDictionary.controller;

import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamDictionaryAppVerNoDispatcher {
    @Autowired
    ExamDictionaryFunctionNoDispatcher examDictionaryFunctionNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByAppVerNo() {
        String appVerNo = dataCenterService.getAppVersionNo();
        String functionNo = dataCenterService.getFunctionNo();


        switch (appVerNo) {
            case "1.0.0":
                examDictionaryFunctionNoDispatcher.dispatchByFunctionNo(functionNo);
                break;

            default:
                break;
        }
    }

}
