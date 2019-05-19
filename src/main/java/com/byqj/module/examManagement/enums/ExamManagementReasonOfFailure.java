package com.byqj.module.examManagement.enums;


import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum ExamManagementReasonOfFailure implements IReasonOfFailure {

    DEMO("demo", "demo"),
    PARAM_ERROR("param error", "参数有误"),
    EXNAME_BLANK("Exam's name can't blank", "考试名称不能为空"),
    EXNAME_ID("Exame ID  can't not be blank", "考试ID不能为空"),
    TIME_BLANK("Exam's time can't blank", "请设置考试时间"),
    PLACE_BLANK("Exam's palce can't blank", "请设置考点"),
    SAVE_FAILURE("Data save failure", "保存失败，请重试"),
    EXAM_ID_BLANK("Exam's palce can't blank", "请选择正确的考试"),
    MAIN_EXAM_ID_IS_BLANK("The main exam id is blank", "主考试id不能为空"),
    EXAM_PLACE_ID_IS_BLANK("The exam place id is blank", "考点或考场ID不能为空"),
    NO_CANDIDATES_IN_THE_EXAMINATION_ROOM("No candidates in the examination room", "请给考场添加考生"),
    NUMBER_OF_PEOPLE_EXCEEDS_CAPACITY("Number of people exceeds capacity", "人数超过教室容量"),
    CLASS_ORDERED("Class already id", "教室已被分配"),
    EXAM_PLACE_DOES_NOT_EXIST("Exam Place does not exist", "考点或考场不存在"),
    POST_NAME_IS_EMPTY("Post name is empty", "岗位名称不能为空"),
    NO_STAFF_UNDER_THE_POST("No staff under the post", "岗位下无人员"),
    THE_NUMBER_EXCEEDS_THE_LIMIT("The number exceeds the limit", "人数超过岗位数量"),
    CLASS_PLACE_ERROR("place choose correct class", "请选择考点下的教室"),
    NULL("null", "数据不能为空"),
    FILE_ERROR("File error", "归档失败"),
    TIME_EXCESS("subExam time over main exam time.", "场次时间在考试时间之外"),
    POST_EXIST("post exist", "存在重复岗位，请重试"),
    EXAM_CLASS_EXIST_STUDENT("Exam class exist student", "已安排考生，无法调整考生数"),
    CLASS_FULL("Class is full", "教室已满"),
    TIME_BEFORE_IMPORT_END("Time before import end time", "学生数据导入未截至，无法安排"),
    TIME_BEFORE_POST_TIME_END("Time before post end time", "岗位报名未截至，无法安排"),

    STATUS_ERROR("Status error", "状态错误"),
    EXAM_ID_IS_BLANK("Exam id is blank", "考试id为空"),
    NOT_EXAM_PLACE("It is not exam place", "请提交考点编号"),
    POST_PERSON_ID_NULL("Post person id is null.", "请选择要删除的岗位"),
    DEL_ERROR("Delete error", "删除失败，请重试"),
    POST_FREE_IS_EMPTY("The post cost is empty", "岗位费用不能为空"),
    EXAM_DOES_NOT_EXIST("Exam does not exist", "考试不存在"),
    THE_EXAM_HAS_BEEN_ARCHIVED_AND_CANNOT_BE_MODIFIED("The exam has been archived and cannot be modified", "考试已经归档 无法修改"),
    DEP_ID_IS_BLANK("Department id is blank", "部门ID为空"),
    END_TIME_IS_BLANK("End time is blank", "结束时间不能为空"),
    START_TIME_IS_BLANK("Start time is blank", "开始时间不能为空"),
    SAVE_ERROR("Save error", "保存失败"),
    NO_EXAM("No exam", "查无此考试"),
    OPERATOR_ERROR("Operator error", "操作失败"),
    PAGE_PARAM_IS_ILLEGAL("Page param is illegal", "分页参数非法"),

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
    NOW_CAN_NOT_PRINT("Now can not print", "不在可打印时间范围内"),
    ALREADY_SIGN_IN("Already sign in", "该用户已经签到"),
    NO_LIST_TO_BE_DOWNLOAD("no list to be download", "没有需要导出的数据"),
    TEMPLATE_IS_WRONG("Template is wrong", "模板码错误"),
    EXAM_TYPE_ERROR("Exam type error", "考试类型错误");


    private String en_msg;
    private String zh_msg;

    ExamManagementReasonOfFailure(String en_msg, String zh_msg) {
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
