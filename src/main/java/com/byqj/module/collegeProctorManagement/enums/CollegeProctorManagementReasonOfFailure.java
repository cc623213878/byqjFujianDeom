package com.byqj.module.collegeProctorManagement.enums;


import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum CollegeProctorManagementReasonOfFailure implements IReasonOfFailure {


    PAGE_PARAM_IS_ILLEGAL("Page param is illegal", "分页信息错误"),
    DELETE_ERROR("Delete error", "删除失败"),
    THE_PARAMETERS_SUBMITTED_ARE_INCORRECT("The parameters submitted are incorrect", "提交的参数有误"),
    SUBMITTED_PHONE_IS_WRONG("phone is wrong!", "手机号格式错误"),
    SUBMITTED_IDCARD_IS_WRONG("IDCard is wrong!", "身份证格式错误"),
    INSERT_ERROR("insert error", "插入失败"),
    UPDATE_ERROP("update error", "修改失败"),
    DATA_CHANGE_ERROR("Data change error", "数据转换错误"),
    FILE_SAVE_ERROR("File save error", "文件保存失败"),
    NO_USER("No user", "不存在该用户"),
    USER_ALREADY_SUBMIT("User alrady submit", "用户已经报名"),
    TIME_ERROR("Time Error", "不在规定时间内，无法报名"),
    LIST_NULL("List null", "数组不能为空");

    private String en_msg;
    private String zh_msg;

    CollegeProctorManagementReasonOfFailure(String en_msg, String zh_msg) {
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
