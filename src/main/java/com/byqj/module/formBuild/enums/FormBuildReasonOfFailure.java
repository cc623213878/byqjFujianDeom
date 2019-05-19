package com.byqj.module.formBuild.enums;

import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum FormBuildReasonOfFailure implements IReasonOfFailure {
    USER_DOES_NOT_EXIST("User does not exist", "用户不存在"),
    FUNCTION_NO_ARE_INCORRECT("functionNoAreIncorrect", "功能号不正确！"),
    THE_PARAMETERS_SUBMITTED_ARE_INCORRECT("The parameters submitted are incorrect", "提交的参数有误"),
    USER_ALREADY_EXISTS_DESCRIPTION("The parameters submitted are incorrect", "用户已经存在"),
    USER_SFZ_ALREADY_EXISTS_DESCRIPTION("The parameters submitted are incorrect", "用户身份证已存在"),
    USER_PHONE_ALREADY_EXISTS_DESCRIPTION("The parameters submitted are incorrect", "用户电话已存在"),
    USER_GH_ALREADY_EXISTS_DESCRIPTION("The parameters submitted are incorrect", "用户工号已存在"),
    USER_ID_IS_EMPTY("The parameters submitted are incorrect", "用户ID不能为空"),
    THE_PERMISSIONS_CANNOT_BE_EMPTY("The parameters submitted are incorrect", "权限不能为空"),
    PAGEINFO_ERROR("PageInfoError", "分页信息错误"),
    OPERATE_ERROR("Operate error", "操作失败"),
    SUBMITTED_PHONE_IS_WRONG("phone is wrong!", "手机号格式错误"),
    SUBMITTED_IDCARD_IS_WRONG("IDCard is wrong!", "身份证格式错误"),
    SUBMITTED_EMAIL_IS_WRONG("Email is wrong!", "邮箱格式错误"),
    DEPT_SUBMITTED_ARE_NULL("Dept submitted are null!", "提交的身份不能为空！"),
    ORDER_SUBMITTED_ARE_NULL("Order submitted are null!", "提交的查询顺序码不能为空！"),
    ORDER_SUBMITTED_ARE_WRONG("Order submitted are wrong!", "提交的查询顺序码错误！"),
    DEPT_SUBMITTED_ARE_WRONG("Dept submitted are wrong!", "提交的身份错误！"),
    USER_ID_SUBMITTED_ARE_NULL("User id submitted are null!", "提交的用户Id为空！"),
    ENABLED_STATE_IS_ILLEGAL("Enabled state is illegal", "启停用状态非法"),
    PERMISSIONS_EXCEED_YOURS("Permissions exceed yours", "所选超过你自身权限"),
    USER_NAME_IS_EMPTY("User name is empty", "提交的用户名为空"),
    POST_IS_EMPTY("Post name is empty", "提交的职位为空"),
    DEPT_ID_IS_EMPTY("Dept id is empty", "提交的部门id为空"),
    SAVE_USER_ACL_ERROR("Save user acl error", "保存用户权限失败"),
    SAVE_USER_ROLE_ERROR("Save user role error", "保存用户角色失败"),
    REAL_NAME_IS_EMPTY("Real name is empty", "提交的真实姓名为空");//此处添加枚举值

    private String en_msg;
    private String zh_msg;

    FormBuildReasonOfFailure(String en_msg, String zh_msg) {
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
