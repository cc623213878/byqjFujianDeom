package com.byqj.module.collegeExamManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollegeExamManagementService {

    @Autowired
    private CollegeExamManagementBusinessService collegeExamManagementBusinessService;
    @Autowired
    private CollegeExamManagementCheckService collegeExamManagementCheckService;

    /**
     * 准考证打印
     */
    public void getCardForExamListRequestProcess() {
        collegeExamManagementCheckService.getCardForExamListRequestCheck();
        collegeExamManagementBusinessService.getCardForExamListRequestProcess();
    }

}
