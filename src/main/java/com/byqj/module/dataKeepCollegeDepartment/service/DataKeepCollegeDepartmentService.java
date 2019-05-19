package com.byqj.module.dataKeepCollegeDepartment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataKeepCollegeDepartmentService {


    @Autowired
    private DataKeepCollegeDepartmentCheckService dataKeepCollegeDepartmentCheckService;
    @Autowired
    private DataKeepCollegeDepartmentBusinessService dataKeepCollegeDepartmentBusinessService;

    /**
     * 查询人员
     */
    public void searchPersonRequestProcess() {
        dataKeepCollegeDepartmentCheckService.searchPersonRequestCheck();
        dataKeepCollegeDepartmentBusinessService.searchPersonRequestProcess();
    }

    /**
     * 删除人员
     */
    public void batchDeletePersonRequestProcess() {
        dataKeepCollegeDepartmentCheckService.batchDeletePersonRequestCheck();
        dataKeepCollegeDepartmentBusinessService.batchDeletePersonRequestProcess();
    }

    /**
     * 添加人员
     */
    public void addUserRequestProcess() {
        dataKeepCollegeDepartmentCheckService.addUserRequestCheck();
        dataKeepCollegeDepartmentBusinessService.addUserRequestProcess();
    }

    /**
     * 修改人员
     */
    public void modifyPersonnelRequestProcess() {
        dataKeepCollegeDepartmentCheckService.modifyPersonnelRequestCheck();
        dataKeepCollegeDepartmentBusinessService.modifyPersonnelRequestProcess();
    }
}
