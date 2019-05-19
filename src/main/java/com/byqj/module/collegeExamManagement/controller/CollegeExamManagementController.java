package com.byqj.module.collegeExamManagement.controller;

import com.byqj.exception.RequestFailureException;
import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController

public class CollegeExamManagementController {
    @Autowired
    private CollegeExamManagementAppVerNoDispatcher collegeExamManagementAppVerNoDispatcher;
    @Autowired
    private DataCenterService dataCenterService;

    @RequestMapping("/collegeExamManagement")
    public Object home() {

        collegeExamManagementAppVerNoDispatcher.dispatchByAppVerNo();
        return dataCenterService.getResponseDataFromDataLocal();

    }

    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException) {

        return requestFailureException.getResponseData();

    }
}
