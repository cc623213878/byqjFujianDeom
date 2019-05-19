package com.byqj.module.system.userCenter.enums;

import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum UserCenterReasonOfFailure implements IReasonOfFailure {


    OLD_PASSWORD_IS_BLANK("Old password is blank", "旧密码不能为空"),
    NEW_PASSWORD_IS_BLANK("New password is blank", "新密码不能为空"),
    OLD_PASSWORD_ERROR("Old password error", "旧密码错误"),
    UPDATE_PASSWORD_ERROR("update password error", "修改密码失败");//此处添加枚举值

    private String en_msg;
    private String zh_msg;

    UserCenterReasonOfFailure(String en_msg, String zh_msg) {
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
