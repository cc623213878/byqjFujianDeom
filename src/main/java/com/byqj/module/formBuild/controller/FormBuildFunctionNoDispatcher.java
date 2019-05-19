package com.byqj.module.formBuild.controller;

import com.byqj.module.formBuild.constant.FormBuildFunctionNoConstants;
import com.byqj.module.formBuild.service.FormBuildService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FormBuildFunctionNoDispatcher {

    @Autowired
    FormBuildService formBuildService;

    @Autowired
    DataCenterService dataCenterService;

    public void dispatchByFunctionNo() {

        String functionNo = dataCenterService.getFunctionNo();
        log.debug("functionNo is {}", functionNo);
        switch (functionNo) {
            // 人员账号密码重置
            case FormBuildFunctionNoConstants.RESET_PASSWORD:
                formBuildService.resetPasswordRequestProcess();
                break;
            // 用户使用状态修改
            case FormBuildFunctionNoConstants.MODIFY_USER_STATUS:
                formBuildService.modifyUserStatusRequestProcess();
                break;
            // 删除admin and user
            case FormBuildFunctionNoConstants.DELETE_PERSONNEL:
                formBuildService.deletePersonnelRequestProcess();
                break;
            //查询人员
            case FormBuildFunctionNoConstants.SEARCH_PERSONNEL_BY_CONDITION:
                formBuildService.searchPersonnelByConditionRequestProcess();
                break;
            //获取管理员权限信息
            case FormBuildFunctionNoConstants.GET_MANAGER_ACL_INFO:
                formBuildService.getManagerAclInfoRequestProcess();
                break;
            // 修改admin
            case FormBuildFunctionNoConstants.MODIFY_PERSONNEL:
                formBuildService.modifyPersonnelRequestProcess();
                break;
            // 添加admin
            case FormBuildFunctionNoConstants.ADD_USER:
                formBuildService.addAdminRequestProcess();
                break;
            // 添加界面展示
            case FormBuildFunctionNoConstants.ADD_USER_DISPLAY:
                formBuildService.addUserDisplayRequestProcess();
                break;

            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
