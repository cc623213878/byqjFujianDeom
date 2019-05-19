package com.byqj.module.system.menuManagement.enums;

import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum MenueManagementOfFailure implements IReasonOfFailure {


    Type_ERROR("Type error", "type类型错误");//此处添加枚举值

    private String en_msg;
    private String zh_msg;

    MenueManagementOfFailure(String en_msg, String zh_msg) {
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
