package com.byqj.module.departmentManagement.controller;


import com.byqj.module.departmentManagement.constant.DepartmentManagementFunctionNoConstants;
import com.byqj.module.departmentManagement.service.DepartmentManagementService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DepartmentManagementFunctionNoDispatcher {

    @Autowired
    DepartmentManagementService departmentManagementService;
    @Autowired
    DataCenterService dataCenterService;

    public void dispatchByFunctionNo() {
        String functionNo = dataCenterService.getFunctionNo();
        log.debug("functionNo is {}", functionNo);

        switch (functionNo) {
            // 获取组织架构部门
            case DepartmentManagementFunctionNoConstants.DEPARTMENT_LIST_FORM_ORGANIZATION:
                departmentManagementService.getDepartmentListFromOrganizationRequestProcess();
                break;
            // 获取数据字典部门
            case DepartmentManagementFunctionNoConstants.DEPARTMENT_LIST_FROM_DICT:
                departmentManagementService.getDepartmentListFromDictRequestProcess();
                break;
            // 获取学院端部门
            case DepartmentManagementFunctionNoConstants.DEPARTMENT_LIST_FROM_COLLEGE:
                departmentManagementService.getDepartmentListFromCollegeRequestProcess();
                break;
            // 添加部门
            case DepartmentManagementFunctionNoConstants.ADD_DEPARTMENT:
                departmentManagementService.addDepartmentRequestProcess();
                break;
            // 修改部门
            case DepartmentManagementFunctionNoConstants.UPDATE_DEPARTMENT:
                departmentManagementService.updateDepartmentRequestProcess();
                break;
            //删除部门
            case DepartmentManagementFunctionNoConstants.DELETE_DEPARTMENT:
                departmentManagementService.deleteDepartmentRequestProcess();
                break;

            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(), ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
