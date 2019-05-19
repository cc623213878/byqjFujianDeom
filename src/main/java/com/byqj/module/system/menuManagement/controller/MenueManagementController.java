package com.byqj.module.system.menuManagement.controller;

import com.byqj.exception.RequestFailureException;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RestController
@RequestMapping("/system")
public class MenueManagementController {
    @Autowired
    MenueManagementAppVerNoDispatcher menueManagementAppVerNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    @RequestMapping("/menueManagement")
    public Object home() {
        menueManagementAppVerNoDispatcher.dispatchByAppVerNo();
        return dataCenterService.getResponseDataFromDataLocal();
    }

    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException) {

        ResponseData responseData = requestFailureException.getResponseData();

        return responseData;

    }
}
