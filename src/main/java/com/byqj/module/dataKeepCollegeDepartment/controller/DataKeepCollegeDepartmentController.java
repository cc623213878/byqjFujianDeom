package com.byqj.module.dataKeepCollegeDepartment.controller;

import com.byqj.exception.RequestFailureException;
import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/dataKeep")
public class DataKeepCollegeDepartmentController {
    @Autowired
    DataKeepCollegeDepartmentAppVerNoDispatcher dataKeepCollegeDepartmentAppVerNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    @RequestMapping("/dataKeepCollegeDepartment")
    public Object home() {

        dataKeepCollegeDepartmentAppVerNoDispatcher.dispatchByAppVerNo();
        return dataCenterService.getResponseDataFromDataLocal();

    }

    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException) {

        return requestFailureException.getResponseData();

    }
}
