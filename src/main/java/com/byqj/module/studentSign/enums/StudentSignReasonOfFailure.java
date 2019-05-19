package com.byqj.module.studentSign.enums;


import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum StudentSignReasonOfFailure implements IReasonOfFailure {

    ACCONUT_IS_BLANK("Acconut is blank", "账户不能为空"),
    PASSWORD_IS_BLANK("Password is blank", "密码不能为空"),
    ACCONUT_OR_PASSWORD_IS_WRONG("Acconut or password is wrong", "账户或者密码错误"),
    SIGN_IN_ERROR("Sign in error", "签到失败，该用户不是当前考点场次的工作人员"),
    EXAM_PLACE_ID_IS_BLANK("Exam place id is blank", "考场id不能为空"),
    PEN_EXAM_NO_SIGN_ON("Pen exam no sign on", "笔试没有签到"),
    TE_ID_IS_BLANK("TeId is blank", "场次id不能为空"),
    NOW_CAN_NOT_SIGN_ON("Now can not sign on", "不在签到范围内"),
    NO_EXAM("No exam", "没有这场考试"),
    ZKZH_IS_BLANK("zkzh is blank", "准考证号不能为空"),
    NOT_IS_CURRENT_EXAM_PLACE("Not is current exam place", "不是当前考场人员");


    private String en_msg;
    private String zh_msg;

    StudentSignReasonOfFailure(String en_msg, String zh_msg) {
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
