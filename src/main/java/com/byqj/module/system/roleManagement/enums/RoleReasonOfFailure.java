package com.byqj.module.system.roleManagement.enums;

import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum RoleReasonOfFailure implements IReasonOfFailure {


    USER_DOES_NOT_EXIST("User does not exist", "用户不存在"),
    FUNCTION_NO_ARE_INCORRECT("functionNoAreIncorrect", "功能号不正确！"),
    THE_PARAMETERS_SUBMITTED_ARE_INCORRECT("The parameters submitted are incorrect", "提交的参数有误"),
    THE_PERMISSIONS_ARE_WRONG("The permissions are wrong", "权限参数有误"),
    THE_PAGE_PARAM_IS_ILLEGAL("pageParamIsIllegal", "分页参数有误"),
    USER_ALREADY_EXISTS_DESCRIPTION("The parameters submitted are incorrect", "用户已经存在"),
    THE_PERMISSIONS_CANNOT_BE_EMPTY("The parameters submitted are incorrect", "权限不能为空"),
    MEMBERS_IN_THE_GROUP_ARE_NOT_ALLOWED_TO_DELETE("The parameters submitted are incorrect", "组内有成员请先删除成员！"),
    THE_ROLE_CANNOT_BE_EMPTY("The parameters submitted are incorrect", "职务ID不能为空"),
    THE_ROLE_CODE_IS_EMPTY("The parameters submitted are incorrect", "角色码不能为空"),//此处添加枚举值
    THE_ROLE_CODE_REPEAT("The userManagement code repeat", "角色码重复"),
    THE_ROLE_NAME_REPEAT("The userManagement name repeat", "角色名称重复"),
    INSERT_ERROR("Insert error", "插入失败"),
    UPDATE_ROLE_ERROR("Update role error", "更新角色失败"),
    UPDATE_ROLE_ACL_ERROR("Update role acl error", "更新角色权限失败"),
    THE_ROLE_NAME_IS_EMPTY("The parameters submitted are incorrect", "角色名称不能为空");//此处添加枚举值

    private String en_msg;
    private String zh_msg;

    RoleReasonOfFailure(String en_msg, String zh_msg) {
        this.en_msg = en_msg;
        this.zh_msg = zh_msg;
    }

    @Override
    public String getZhMsgOfFailure() {
        return zh_msg;
    }

    @Override
    public String getEnMsgOfFailure() {
        return en_msg;
    }


}
