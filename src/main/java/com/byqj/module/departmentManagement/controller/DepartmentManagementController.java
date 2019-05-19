package com.byqj.module.departmentManagement.controller;

import com.byqj.exception.RequestFailureException;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController

public class DepartmentManagementController {
    @Autowired
    DepartmentManagementAppVerNoDispatcher departmentManagementAppVerNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    @RequestMapping("/departmentManagement")
    public Object home() {
        departmentManagementAppVerNoDispatcher.dispatchByAppVerNo();
        return dataCenterService.getResponseDataFromDataLocal();
    }

    @RequestMapping("/departmentManagement/departmentListFromDict")
    public Object DepartmentListFromDict() {
        departmentManagementAppVerNoDispatcher.dispatchByAppVerNo();
        return dataCenterService.getResponseDataFromDataLocal();
    }

    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException) {

        ResponseData responseData = requestFailureException.getResponseData();

        return responseData;

    }
}
