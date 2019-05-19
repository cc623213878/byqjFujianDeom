package com.byqj.module.common.examDictionary.enums;


import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum ExamDictionaryReasonOfFailure implements IReasonOfFailure {


    ;


    private String en_msg;
    private String zh_msg;

    ExamDictionaryReasonOfFailure(String en_msg, String zh_msg) {
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
