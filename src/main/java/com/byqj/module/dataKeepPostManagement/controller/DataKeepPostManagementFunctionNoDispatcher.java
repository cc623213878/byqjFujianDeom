package com.byqj.module.dataKeepPostManagement.controller;

import com.byqj.module.dataKeepPostManagement.constant.DataKeepPostManagementFunctionNoConstants;
import com.byqj.module.dataKeepPostManagement.service.DataKeepPostManagementService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataKeepPostManagementFunctionNoDispatcher {

    @Autowired
    DataKeepPostManagementService dataKeepPostManagementService;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {

            // 查询岗位
            case DataKeepPostManagementFunctionNoConstants.SEARCH_POST:
                dataKeepPostManagementService.searchPostRequestProcess();
                break;
            // 删除岗位
            case DataKeepPostManagementFunctionNoConstants.DELETE_POST:
                dataKeepPostManagementService.batchDeletePostRequestProcess();
                break;
            // 修改岗位
            case DataKeepPostManagementFunctionNoConstants.UPDATE_POST:
                dataKeepPostManagementService.updatePostRequestProcess();
                break;
            // 添加岗位
            case DataKeepPostManagementFunctionNoConstants.ADD_POST:
                dataKeepPostManagementService.addPostRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
