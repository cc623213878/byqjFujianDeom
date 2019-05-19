package com.byqj.module.dataKeepDictionaryKeep.enums;


import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum DataKeepDictionaryKeepReasonOfFailure implements IReasonOfFailure {


    PAGE_PARAM_IS_ILLEGAL("Page param is illegal", "分页信息错误"),
    TYPE_EXIST("Type exist", "类型已经存在"),
    TYPE_NULL("Type null", "类型不能为空"),
    TYPE_WRONG("Type wrong", "类型错误"),
    DESCRIPTION_NULL("Description null", "描述不能为空"),
    INSERT_ERROR("Insert error", "插入失败"),
    UPDATE_ERROR("Update error", "更新失败"),
    ID_NULL("Id null", "ID不能为空"),
    LIST_NULL("List null", "数组不能为空"),
    DELETE_ERROR("Delete error", "删除失败"),
    DATA_IS_USED("data is used", "数据已被使用无法删除");

    private String en_msg;
    private String zh_msg;

    DataKeepDictionaryKeepReasonOfFailure(String en_msg, String zh_msg) {
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
