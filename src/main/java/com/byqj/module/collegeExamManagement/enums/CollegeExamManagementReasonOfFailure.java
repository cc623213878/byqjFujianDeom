package com.byqj.module.collegeExamManagement.enums;


import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum CollegeExamManagementReasonOfFailure implements IReasonOfFailure {

    MAIN_EXAM_ID_IS_BLANK("The main exam id is blank", "主考试id不能为空"),
    NOW_CAN_NOT_PRINT("Now can not print", "不在可打印时间范围内"),
    NO_EXAM("No exam", "查无此考试"),
    SAVE_FAILURE("Data save failure", "保存失败，请重试"),
    NO_LIST_TO_BE_DOWNLOAD("no list to be download", "没有需要导出的数据"),

    CONTENT_IS_BLANK("Content is blank", "工作内容说明不能为空"),
    LEVEL_IS_BLANK("Level is blank", "级别不能为空"),
    TRAIN_TIME_IS_BLANK("Train time is blank", "培训时间不能为空"),
    ATTENTION_IS_BLANK("Attention is blank", "注意事项不能为空"),
    TE_ID_IS_BLANK("TeId is blank", "场次id不能为空"),
    PID_IS_ILLEGAL("PID is illegal", "身份证号码有误"),
    EXAMP_POINT_ID_IS_BLANK("ExamPointId is blank", "考点id不能为空"),
    SIGN_IN_ERROR("Sign in error", "签到失败，该用户不是当前考点场次的工作人员"),
    TIME_IS_BLANK("Time is blank", "时间不能为空"),
    TEMPLATE_IS_BLANK("Template is blank", "模板码不能为空"),
    TEMPLATE_IS_WRONG("Template is wrong", "模板码错误");

    private String en_msg;
    private String zh_msg;

    CollegeExamManagementReasonOfFailure(String en_msg, String zh_msg) {
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
