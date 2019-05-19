package com.byqj.module.gradeManagement.enums;


import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum GradeManagementReasonOfFailure implements IReasonOfFailure {


    PAGE_PARAM_IS_ILLEGAL("Page param is illegal", "分页信息错误"),
    THE_KSSJHCS_IS_EMPTY("The kssjhcs submitted are incorrect", "考试时间或次数不能为空"),
    THE_BSKMMC_IS_EMPTY("The bskmmc submitted are incorrect", "考试名称不能为空"),
    LIST_NULL("List null", "数组不能为空"),
    NO_EXAM("No exam", "无此考试"),
    DELETE_ERROR("Delete error", "删除失败"),
    USER_ALREADY_EXISTS_DESCRIPTION("The parameters submitted are incorrect", "用户已经存在"),
    THE_PARAMETERS_SUBMITTED_ARE_INCORRECT("The parameters submitted are incorrect", "提交的参数有误"),
    FILE_UPLOAD_ERROR("File upload error", "文件上传失败"),
    FILE_IS_ILLEGAL("File is illegal", "文件类型错误"),
    INSERT_ERROR("insert error", "插入失败"),
    UPDATE_ERROP("update error", "修改失败");

    private String en_msg;
    private String zh_msg;

    GradeManagementReasonOfFailure(String en_msg, String zh_msg) {
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
