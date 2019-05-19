package com.byqj.module.classInfoManagement.enums;


import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum ClassInfoManagementReasonOfFailure implements IReasonOfFailure {
    PAGEINFO_ERROR("PageInfoError", "分页信息错误"),
    PARAMETER_IS_EMPTY("Parameter is empty", "提交参数为空"),
    PARAMETER_ERROR("Parameter error", "提交参数错误"),
    CLASS_NAME_REPEAT("Class name repeat", "教室名已经存在"),
    INSERT_ERROR("Insert error", "插入失败"),
    UPDATE_ERROR("Update error", "更新失败"),
    DELETE_ERROR("Delete error", "删除失败"),
    UNABLE_TO_DELETE_CLASSROOM_HAS_BEEN_ARRANGED("Unable to delete, classroom has been arranged", "教室已经被安排，无法删除！"),
    UNABLE_TO_UPDATE_STATUS_CLASSROOM_HAS_BEEN_ARRANGED("Unable to update status, classroom has been arranged", "教室已经被安排，无法停用");


    private String en_msg;
    private String zh_msg;

    ClassInfoManagementReasonOfFailure(String en_msg, String zh_msg) {
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
