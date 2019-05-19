package com.byqj.module.formBuild.controller;

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
@RequestMapping("/from")
public class FormBuildController {
    @Autowired
    FormBuildAppVerNoDispatcher formBuildAppVerNoDispatcher;

    @Autowired
    DataCenterService dataCenterService;

    @RequestMapping("/fromBuild")
    public Object home() {
        formBuildAppVerNoDispatcher.dispatchByAppVerNo();
        return dataCenterService.getResponseDataFromDataLocal();
    }

    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException) {

        ResponseData responseData = requestFailureException.getResponseData();

        return responseData;

    }
}
