package com.byqj.module.collegePersonManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollegePersonManagementService {


    @Autowired
    private CollegePersonManagementCheckService collegePersonManagementCheckService;
    @Autowired
    private CollegePersonManagementBusinessService collegePersonManagementBusinessService;


    /**
     * 查询人员
     */
    public void searchPersonRequestProcess() {
        collegePersonManagementCheckService.searchPersonRequestCheck();
        collegePersonManagementBusinessService.searchPersonRequestProcess();
    }

    /**
     * 删除人员
     */
    public void batchDeletePersonRequestProcess() {
        collegePersonManagementCheckService.batchDeletePersonRequestCheck();
        collegePersonManagementBusinessService.batchDeletePersonRequestProcess();
    }

    /**
     * 添加人员
     */
    public void addUserRequestProcess() {
        collegePersonManagementCheckService.addUserRequestCheck();
        collegePersonManagementBusinessService.addUserRequestProcess();
    }

    /**
     * 修改人员
     */
    public void modifyPersonnelRequestProcess() {
        collegePersonManagementCheckService.modifyPersonnelRequestCheck();
        collegePersonManagementBusinessService.modifyPersonnelRequestProcess();
    }

}
