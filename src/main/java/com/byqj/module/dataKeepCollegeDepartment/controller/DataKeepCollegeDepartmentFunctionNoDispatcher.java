package com.byqj.module.dataKeepCollegeDepartment.controller;

import com.byqj.module.dataKeepCollegeDepartment.constant.DataKeepCollegeDepartmentFunctionNoConstants;
import com.byqj.module.dataKeepCollegeDepartment.service.DataKeepCollegeDepartmentService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataKeepCollegeDepartmentFunctionNoDispatcher {

    @Autowired
    DataKeepCollegeDepartmentService dataKeepCollegeDepartmentService;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {

            // 查询人员
            case DataKeepCollegeDepartmentFunctionNoConstants.SEARCH_PERSON:
                dataKeepCollegeDepartmentService.searchPersonRequestProcess();
                break;
            // 删除人员
            case DataKeepCollegeDepartmentFunctionNoConstants.DELETE_PERSON:
                dataKeepCollegeDepartmentService.batchDeletePersonRequestProcess();
                break;
            // 添加人员
            case DataKeepCollegeDepartmentFunctionNoConstants.ADD_PERSON:
                dataKeepCollegeDepartmentService.addUserRequestProcess();
                break;
            // 修改人员
            case DataKeepCollegeDepartmentFunctionNoConstants.MODIFY_PERSON:
                dataKeepCollegeDepartmentService.modifyPersonnelRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
