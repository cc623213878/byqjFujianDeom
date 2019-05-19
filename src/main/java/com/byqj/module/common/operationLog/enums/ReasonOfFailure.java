package com.byqj.module.common.operationLog.enums;

import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum ReasonOfFailure implements IReasonOfFailure {

    THE_PARAMETERS_SUBMITTED_ARE_INCORRECT("The parameters submitted are incorrect", "提交的参数有误"),
    PAGEINFO_ERROR("PageInfoError", "分页信息错误");


    private String en_msg;
    private String zh_msg;

    ReasonOfFailure(String en_msg, String zh_msg) {
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
