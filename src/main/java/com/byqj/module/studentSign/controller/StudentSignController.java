package com.byqj.module.studentSign.controller;

import com.byqj.exception.RequestFailureException;
import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/studentSign")
public class StudentSignController {
    @Autowired
    StudentSignAppVerNoDispatcher studentSignAppVerNoDispatcher;
    @Autowired
    DataCenterService dataCenterService;

    @RequestMapping("/logOn")
    public Object home() {
        studentSignAppVerNoDispatcher.dispatchByAppVerNo();
        return dataCenterService.getResponseDataFromDataLocal();
    }

    @RequestMapping("/signInModule")
    public Object signIn() {
        studentSignAppVerNoDispatcher.dispatchByAppVerNo();
        return dataCenterService.getResponseDataFromDataLocal();
    }

    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException) {

        return requestFailureException.getResponseData();

    }
}
