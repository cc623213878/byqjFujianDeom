package com.byqj.module.system.roleManagement.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byqj.dao.SysRoleDao;
import com.byqj.dao.SysRoleUserDao;
import com.byqj.entity.SysRole;
import com.byqj.module.system.roleManagement.constant.LogConstant;
import com.byqj.module.system.roleManagement.enums.RoleReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.CheckVariableUtil;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleCheckService {

    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    SysRoleUserDao sysRoleUserDao;
    @Autowired
    SysRoleDao sysRoleDao;
    @Autowired
    LogCenterService logCenterService;


    /**
     * 获取权组列表
     */
    public void searchRoleRequestCheck() {

        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_PAGE_PARAM_IS_ILLEGAL);
        }
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);
    }


    /**
     * 添加权组
     */
    public void addRoleRequestCheck() {


        String roleName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("roleName");
        String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");

        if (StringUtils.isEmpty(roleName)) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_ROLE_NAME_IS_EMPTY);
        }

        JSONArray permissionsJA = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("permissions");
        List<String> permissions = permissionsJA.toJavaList(String.class);
        if (permissions.isEmpty()) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_PERMISSIONS_CANNOT_BE_EMPTY);
        }
        if (!isHasAcl(permissions)) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_PERMISSIONS_ARE_WRONG);
        }

        int result = sysRoleDao.selectCount(new QueryWrapper<SysRole>().lambda().eq(SysRole::getRoleName, roleName));

        if (result > 0) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_ROLE_NAME_REPEAT);
        }


        dataCenterService.setData("roleName", roleName.trim());
        dataCenterService.setData("remark", remark.trim());
        dataCenterService.setData("permissions", permissions);

    }


    /**
     * 删除权组检测，如果权组内有成员，禁止删除
     */
    public void deleteRoleRequestCheck() {
        JSONArray groupJA = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("roleCodeList");
        List<String> roleCodeList = groupJA.toJavaList(String.class);

        if (CollectionUtils.isEmpty(roleCodeList)) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_ROLE_CANNOT_BE_EMPTY);
        }

        for (String roleCode : roleCodeList) {
            boolean result = sysRoleUserDao.checkUserInRole(roleCode);
            if (result) {
                String groupName = sysRoleDao.selectRoleNameByRoleCode(roleCode);
                // 记录日志
                String operationContent = String.format(LogConstant.DELETE_AUTHORITY_GROUP_TEMPLATE, groupName);
                logCenterService.setContent(operationContent);
                logCenterService.setResultIsFalse();
                logCenterService.setReason(LogConstant.MEMBERS_IN_THE_GROUP_ARE_NOT_ALLOWED_TO_DELETE_DESCRIPTION);
                ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.MEMBERS_IN_THE_GROUP_ARE_NOT_ALLOWED_TO_DELETE);
            }
        }
        dataCenterService.setData("roleCodeList", roleCodeList);
    }


    /**
     * 修改权组检测
     */
    public void modifyRoleRequestCheck() {
        String roleCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("roleCode");
        String roleName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("roleName");
        String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");

        if (StringUtils.isEmpty(roleCode)) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_ROLE_CODE_IS_EMPTY);
        }
        if (StringUtils.isEmpty(roleName)) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_ROLE_NAME_IS_EMPTY);
        }


        JSONArray permissionsJA = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("permissions");

        List<String> permissions = permissionsJA.toJavaList(String.class);

        if (permissions.isEmpty()) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_PERMISSIONS_CANNOT_BE_EMPTY);
        }
        if (!isHasAcl(permissions)) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_PERMISSIONS_ARE_WRONG);
        }

        int result = sysRoleDao.selectCount(new QueryWrapper<SysRole>().lambda().eq(SysRole::getRoleName, roleName));

        if (result > 1) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_ROLE_NAME_REPEAT);
        }

        dataCenterService.setData("roleCode", roleCode.trim());
        dataCenterService.setData("roleName", roleName.trim());
        dataCenterService.setData("remark", remark);
        dataCenterService.setData("permissions", permissions);
    }

    private void checkEmptyLongVariable(Long groupId) {
        if (groupId == null || groupId < 0) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
            return;
        }

    }


    public void getRoleAclRequestCheck() {
        String roleCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("roleCode");
        if (StringUtils.isEmpty(roleCode)) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_ROLE_CODE_IS_EMPTY);
        }
        dataCenterService.setData("roleCode", roleCode.trim());

    }

    /*
     * @Author lwn
     * @Description 检测上传的权限是否是当前用户所包含的权限
     * @Date 15:21 2019/3/4
     * @Param []
     * @return boolean
     **/
    private boolean isHasAcl(List<String> permissions) {
        //获取当前登录用户的所有权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> aclCodes = (List) authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return aclCodes.containsAll(permissions);
    }


    public void searchRolePersonRequestCheck() {

        String roleCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("roleCode");
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");

        if (StringUtils.isEmpty(roleCode)) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_ROLE_CODE_IS_EMPTY);
        }
        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.THE_PAGE_PARAM_IS_ILLEGAL);
        }

        dataCenterService.setData("roleCode", roleCode.trim());
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);
    }
}
