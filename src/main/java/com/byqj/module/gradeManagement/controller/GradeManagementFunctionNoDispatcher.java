package com.byqj.module.gradeManagement.controller;

import com.byqj.module.gradeManagement.constant.GradeManagementFunctionNoConstants;
import com.byqj.module.gradeManagement.service.GradeManagementDepartmentService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GradeManagementFunctionNoDispatcher {

    @Autowired
    GradeManagementDepartmentService gradeManagementDepartmentService;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {
            case GradeManagementFunctionNoConstants.SEARCH_PERSON:
                gradeManagementDepartmentService.searchPersonRequestProcess();
                break;
            case GradeManagementFunctionNoConstants.SEARCH_GRADE_CONDITION:
                gradeManagementDepartmentService.searchGradeConditionRequestProcess();
                break;
            case GradeManagementFunctionNoConstants.SEARCH_GRADE:
                gradeManagementDepartmentService.searchGradeRequestProcess();
                break;
            case GradeManagementFunctionNoConstants.DELETE_GRADE:
                gradeManagementDepartmentService.deleteGradeRequestProcess();
                break;
            //获取下载成绩导入模板地址
            case GradeManagementFunctionNoConstants.DOWNLOAD_TEMPLATE:
                gradeManagementDepartmentService.downloadTemplateRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
