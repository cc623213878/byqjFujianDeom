package com.byqj.module.collegeProctorManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CollegeProctorManagementService {


    @Autowired
    private CollegeProctorManagementCheckService collegeProctorManagementCheckService;
    @Autowired
    private CollegeProctorManagementBusinessService collegeProctorManagementBusinessService;


    public void searchSubmitPersonRequestProcess() {
        collegeProctorManagementCheckService.searchSubmitPersonByConditionRequestCheck();
        collegeProctorManagementBusinessService.searchSubmitPersonByConditionRequestProcess();
    }


    public void addUserRequestProcess() {
        collegeProctorManagementCheckService.addUserRequestCheck();
        collegeProctorManagementBusinessService.addUserRequestProcess();
    }


    public void searchNoSubmitPersonRequestProcess() {
        collegeProctorManagementCheckService.searchNoSubmitPersonByConditionRequestCheck();
        collegeProctorManagementBusinessService.searchNoSubmitPersonByConditionRequestProcess();
    }

    public void downloadSubmitPersonRequestProcess(HttpServletRequest request, HttpServletResponse response) {
        collegeProctorManagementCheckService.downloadSubmitPersonRequestCheck(request);
        collegeProctorManagementBusinessService.downloadSubmitPersonRequestProcess(request, response);
    }
}
