package com.byqj.module.collegePersonManagement.controller;

import com.byqj.module.collegePersonManagement.constant.CollegePersonManagementFunctionNoConstants;
import com.byqj.module.collegePersonManagement.service.CollegePersonManagementService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollegePersonManagementFunctionNoDispatcher {

    @Autowired
    CollegePersonManagementService collegePersonManagementService;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {

            // 查询人员
            case CollegePersonManagementFunctionNoConstants.SEARCH_PERSON:
                collegePersonManagementService.searchPersonRequestProcess();
                break;
            // 删除人员
            case CollegePersonManagementFunctionNoConstants.DELETE_PERSON:
                collegePersonManagementService.batchDeletePersonRequestProcess();
                break;
            // 添加人员
            case CollegePersonManagementFunctionNoConstants.ADD_PERSON:
                collegePersonManagementService.addUserRequestProcess();
                break;
            // 修改人员
            case CollegePersonManagementFunctionNoConstants.MODIFY_PERSON:
                collegePersonManagementService.modifyPersonnelRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
