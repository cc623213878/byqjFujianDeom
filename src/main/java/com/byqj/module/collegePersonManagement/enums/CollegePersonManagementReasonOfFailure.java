package com.byqj.module.collegePersonManagement.enums;


import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum CollegePersonManagementReasonOfFailure implements IReasonOfFailure {


    PAGE_PARAM_IS_ILLEGAL("Page param is illegal", "分页信息错误"),
    DELETE_ERROR("Delete error", "删除失败"),
    THE_PARAMETERS_SUBMITTED_ARE_INCORRECT("The parameters submitted are incorrect", "提交的参数有误"),
    USER_ALREADY_EXISTS_DESCRIPTION("The parameters submitted are incorrect", "用户已经存在"),
    USER_SFZ_ALREADY_EXISTS_DESCRIPTION("The parameters submitted are incorrect", "用户身份证已存在"),
    USER_PHONE_ALREADY_EXISTS_DESCRIPTION("The parameters submitted are incorrect", "用户电话已存在"),
    USER_GH_ALREADY_EXISTS_DESCRIPTION("The parameters submitted are incorrect", "用户工号已存在"),
    SUBMITTED_PHONE_IS_WRONG("phone is wrong!", "手机号格式错误"),
    SUBMITTED_IDCARD_IS_WRONG("IDCard is wrong!", "身份证格式错误"),
    INSERT_ERROR("insert error", "插入失败"),
    UPDATE_ERROP("update error", "修改失败"),
    LIST_NULL("List null", "数组不能为空"),
    COLLEGE_ID_ILLEGAL("College id illegal", "学院id错误");

    private String en_msg;
    private String zh_msg;

    CollegePersonManagementReasonOfFailure(String en_msg, String zh_msg) {
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

    public void setEn_msg(String en_msg) {
        this.en_msg = en_msg;
    }

    public void setZh_msg(String zh_msg) {
        this.zh_msg = zh_msg;
    }
}
