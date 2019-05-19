package com.byqj.module.system.roleManagement.controller;


import com.byqj.module.system.roleManagement.constant.RoleFunctionNoConstants;
import com.byqj.module.system.roleManagement.service.RoleService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RoleFunctionNoDispatcher {

    @Autowired
    RoleService roleService;
    @Autowired
    DataCenterService dataCenterService;

    public void dispatchByFunctionNo() {
        String functionNo = dataCenterService.getFunctionNo();
        log.debug("functionNo is {}", functionNo);

        switch (functionNo) {
            // 获取权组列表
            case RoleFunctionNoConstants.SEARCH_ROLE:
                roleService.searchRoleRequestProcess();
                break;
            // 获取角色类型
            case RoleFunctionNoConstants.SEARCH_ROLE_CLASS:
                roleService.searchRoleClassRequestProcess();
                break;
            // 获取角色对应权限
            case RoleFunctionNoConstants.GET_ROLE_ACL:
                roleService.getRoleAclRequestProcess();
                break;
            //添加角色
            case RoleFunctionNoConstants.ADD_AUTHORITY_GROUP:
                roleService.addRoleRequestProcess();
                break;
            //删除权组
            case RoleFunctionNoConstants.DELETE_AUTHORITY_GROUP:
                roleService.deleteRoleRequestProcess();
                break;
            //修改角色
            case RoleFunctionNoConstants.MODIFY_AUTHORITY_GROUP:
                roleService.modifyRoleRequestProcess();
                break;
            //获取该角色下所有人
            case RoleFunctionNoConstants.SEARCH_ROLE_PERSON:
                roleService.searchRolePersonRequestProcess();
                break;
            //获取当前登录用户的所有权限
            case RoleFunctionNoConstants.GET_CURRENT_USER_ALC:
                roleService.getCurrentUserAclRequestProcess();
                break;

            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(), ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
