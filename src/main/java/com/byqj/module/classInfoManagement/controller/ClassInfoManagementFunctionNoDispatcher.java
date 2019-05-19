package com.byqj.module.classInfoManagement.controller;

import com.byqj.module.classInfoManagement.constant.ClassInfoManagementFunctionNoConstants;
import com.byqj.module.classInfoManagement.service.ClassInfoManagementService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClassInfoManagementFunctionNoDispatcher {

    @Autowired
    ClassInfoManagementService classInfoManagementService;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {

            //条件搜索
            case ClassInfoManagementFunctionNoConstants.SEACH_BY_CONDITION:
                classInfoManagementService.seachByConditionRequestProcess();
                break;
            //修改考室信息
            case ClassInfoManagementFunctionNoConstants.UPDATE_CLASSROOM:
                classInfoManagementService.updateClassroomRequestProcess();
                break;
            //添加考室信息
            case ClassInfoManagementFunctionNoConstants.ADD_CLASSROOM:
                classInfoManagementService.addClassroomRequestProcess();
                break;
            //删除考室信息
            case ClassInfoManagementFunctionNoConstants.DELETE_CLASSROOM:
                classInfoManagementService.deleteClassroomRequestProcess();
                break;
            //修改启用停用状态
            case ClassInfoManagementFunctionNoConstants.UPDATE_STATUS:
                classInfoManagementService.updateStatusRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
