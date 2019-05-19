package com.byqj.module.collegeProctorManagement.controller;

import com.byqj.exception.RequestFailureException;
import com.byqj.module.collegeProctorManagement.service.CollegeProctorManagementService;
import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController

public class CollegeProctorManagementController {
    @Autowired
    private CollegeProctorManagementAppVerNoDispatcher collegeProctorManagementAppVerNoDispatcher;
    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private CollegeProctorManagementService proctorManagementService;

    @RequestMapping("/collegeProctorManagement")
    public Object home() {

        collegeProctorManagementAppVerNoDispatcher.dispatchByAppVerNo();
        return dataCenterService.getResponseDataFromDataLocal();

    }

    @RequestMapping("/downloadSubmitPerson")
    public Object downloadSubmitPerson(HttpServletRequest request, HttpServletResponse response) {
        proctorManagementService.downloadSubmitPersonRequestProcess(request, response);
        return dataCenterService.getResponseDataFromDataLocal();
    }

    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException) {

        return requestFailureException.getResponseData();

    }
}
