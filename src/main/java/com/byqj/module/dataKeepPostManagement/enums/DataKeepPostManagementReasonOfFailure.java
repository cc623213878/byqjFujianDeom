package com.byqj.module.dataKeepPostManagement.enums;


import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum DataKeepPostManagementReasonOfFailure implements IReasonOfFailure {


    PAGE_PARAM_IS_ILLEGAL("Page param is illegal", "分页信息错误"),
    NAME_NULL("Name null", "姓名不能为空"),
    FREE_NULL("Free null", "费用不能为空"),
    ID_NULL("Id null", "ID不能为空"),
    INSERT_ERROR("Insert error", "插入失败"),
    DELETE_ERROR("Delete error", "删除失败"),
    UPDATE_ERROR("Update error", "更新失败"),
    LIST_NULL("List null", "数组不能为空"),
    POST_EXIST("Post exist", "岗位已经存在"),
    THE_POST_WAS_USED_FOR_THE_EXAM_AND_CANNOT_BE_DELETED("The post was used for the exam and cannot be deleted.", "岗位被进行中的考试引用，无法删除。");

    private String en_msg;
    private String zh_msg;

    DataKeepPostManagementReasonOfFailure(String en_msg, String zh_msg) {
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
