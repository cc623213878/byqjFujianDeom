package com.byqj.module.collegeExamManagement.controller;

import com.byqj.module.collegeExamManagement.constant.CollegeExamManagementFunctionNoConstants;
import com.byqj.module.collegeExamManagement.service.CollegeExamManagementService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollegeExamManagementFunctionNoDispatcher {

    @Autowired
    CollegeExamManagementService collegeExamManagementService;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {

            // 准考证打印
            case CollegeExamManagementFunctionNoConstants.GET_CARD_FOR_EXAM_LIST:
                collegeExamManagementService.getCardForExamListRequestProcess();
                break;


            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
