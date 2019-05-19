package com.byqj.module.scoreQuery.enums;


import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum ScoreQueryReasonOfFailure implements IReasonOfFailure {


    KMMC_IS_NULL("Kmmc is null", "科目名称不能为空"),
    KSCSHSJ_IS_NULL("Kscshsj is null", "考试次数或时间不能为空"),
    XM_IS_NULL("Xm is null", "姓名不能为空"),
    BSZKZH_IS_NULL("Bszkzh is null", "准考证号不能为空"),
    PERSON_NOT_EXSIT("Person not exsit", "人员不存在"),
    ON_EXAM("On exam", "没有这场考试成绩"),
    ON_SCORE("On score", "考试成绩还未录入");

    private String en_msg;
    private String zh_msg;

    ScoreQueryReasonOfFailure(String en_msg, String zh_msg) {
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
