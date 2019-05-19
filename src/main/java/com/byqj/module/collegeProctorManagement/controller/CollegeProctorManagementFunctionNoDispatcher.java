package com.byqj.module.collegeProctorManagement.controller;

import com.byqj.module.collegeProctorManagement.constant.CollegeProctorManagementFunctionNoConstants;
import com.byqj.module.collegeProctorManagement.service.CollegeProctorManagementService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollegeProctorManagementFunctionNoDispatcher {

    @Autowired
    CollegeProctorManagementService collegeProctorManagementService;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {

            // 查询选中人员
            case CollegeProctorManagementFunctionNoConstants.SEARCH_SUBMIT_PERSON:
                collegeProctorManagementService.searchSubmitPersonRequestProcess();
                break;
            // 查询未选中人员
            case CollegeProctorManagementFunctionNoConstants.SEACH_NO_SUBMIT_PERSON:
                collegeProctorManagementService.searchNoSubmitPersonRequestProcess();
                break;
            // 添加人员
            case CollegeProctorManagementFunctionNoConstants.ADD_PERSON:
                collegeProctorManagementService.addUserRequestProcess();
                break;

            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
