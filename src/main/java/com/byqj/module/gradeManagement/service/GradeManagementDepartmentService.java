package com.byqj.module.gradeManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeManagementDepartmentService {


    @Autowired
    private GradeManagementCheckService gradeManagementCheckService;
    @Autowired
    private GradeManagementBusinessService gradeManagementBusinessService;

    /**
     * 查询人员信息
     */
    public void searchPersonRequestProcess() {
        gradeManagementCheckService.searchPersonRequestCheck();
        gradeManagementBusinessService.searchPersonRequestProcess();
    }

    /**
     * 查询成绩条件信息
     */
    public void searchGradeConditionRequestProcess() {
        gradeManagementBusinessService.searchGradeConditionRequestProcess();
    }

    /**
     * 查询成绩
     */
    public void searchGradeRequestProcess() {
        gradeManagementCheckService.searchGradeRequestCheck();
        gradeManagementBusinessService.searchGradeRequestProcess();
    }

    /**
     * 删除成绩信息
     */
    public void deleteGradeRequestProcess() {
        gradeManagementCheckService.deleteGradeRequestCheck();
        gradeManagementBusinessService.deleteGradeRequestProcess();
    }

    /**
     * 下载模版
     */
    public void downloadTemplateRequestProcess() {
        gradeManagementBusinessService.downloadTemplateRequestProcess();
    }
}
