package com.byqj.module.departmentManagement.service;

import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentManagementService {

    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    DepartmentManagementCheckService departmentManagementCheckService;
    @Autowired
    DepartmentManagementBusinessService departmentManagementBusinessService;


    public void getDepartmentListFromOrganizationRequestProcess() {
        departmentManagementBusinessService.getDepartmentListFromOrganizationRequestProcess();
    }

    public void addDepartmentRequestProcess() {
        departmentManagementCheckService.addDepartmentRequestCheck();
        departmentManagementBusinessService.addDepartmentRequestProcess();
    }

    public void updateDepartmentRequestProcess() {
        departmentManagementCheckService.updateDepartmentRequestCheck();
        departmentManagementBusinessService.updateDepartmentRequestProcess();
    }

    public void deleteDepartmentRequestProcess() {
        departmentManagementCheckService.deleteDepartmentRequestCheck();
        departmentManagementBusinessService.deleteDepartmentRequestProcess();
    }

    public void getDepartmentListFromDictRequestProcess() {
        departmentManagementBusinessService.getDepartmentListFromDictRequestProcess();
    }

    public void getDepartmentListFromCollegeRequestProcess() {
        departmentManagementBusinessService.getDepartmentListFromCollegeRequestProcess();
    }
}
