package com.byqj.module.examManagement.constant;

public interface ExamManagementFunctionNoConstants {

    String SET_EXAM_INFO = "SetExamInfo";               // 设置或新增考试信息
    String GET_EXAM_INFO = "GetExamInfo";               // 获取考试详细信息
    String SET_EXAM_PLACE_INFO = "SetExamPlaceInfo";    // 设置或新增考场考点信息
    String GET_EXAM_PLACE_INFO = "GetExamPlaceInfo";    // 获取考场考点信息
    String DEL_EXAM = "DelExam";                        // 删除考试
    String FILE_EXAM = "FileExam";                      // 考试归档
    String DEL_EXAM_CLASS = "DelExamClass";             // 删除考场
    String SET_POST = "SetPost";                        // 设置岗位
    String DEL_POST = "DelPost";                        // 删除岗位
    String SET_MAIN_CLASS = "SetMainClass";             // 设置主考室
    String GET_FREE_CLASS = "GetFreeClass";             // 获取未安排教室
    String GET_EXAM = "GetExam";                        // 获取考试
    String SET_SOLICITATION_TIME
            = "SetSolicitationTime";                    // 设置岗位征集时间
    String SET_REPORT_TIME = "SetReportTime";           // 设置签到时间
//    String GET_ACCOUNT = "GetAccount";                  // 获取考试账号
    String EXPORT_ACCOUNT = "ExportAccount";            // 导出考试账号

    String GET_UNSCHEDULED_EXAMINEE = "GetUnscheduledExaminee"; // 获取待安排的考生
    String ARRANGE_EXAMINEE = "ArrangeExaminee"; // 安排考生
    String ARRANGE_POST = "ArrangePost"; // 安排岗位
    String GET_UNSCHEDULED_PERSON = "GetUnscheduledPerson"; // 获取待安排的考务人员
    String GET_EXAM_PLACE_POST_INFO = "GetExamPlacePostInfo"; // 获取考场考点信息
    String GET_EXAM_PLACE_BY_EXAM_ID = "GetExamPlaceByExamId"; // 通过场次id获取考点信息
    String GET_POST_FREE_SET = "GetPostFreeSet"; // 获取岗位费用设置
    String SET_POST_FREE = "SetPostFree"; // 设置岗位费用
    String GET_POST_FREE = "GetPostFree"; // 获取岗位费用
    String DOWNLOAD_PERSON_FREE = "DownloadPersonFree"; // 下载岗位人员费用表
    String GET_POST_SCHEDULING_INFO = "GetPostSchedulingInfo"; // 获取岗位安排信息表
    String ADMISSION_TICKET_DOWNLOAD_SETTING = "AdmissionTicketDownloadSetting"; // 准考证打印设置
    String IMPORT_STUDENT_INFO_SETTING = "ImportStudentInfoSetting"; // 导入学生数据设置
    String GET_SOLICITATION_TIME = "GetSolicitationTime"; // 查询岗位征集时间 学生导入时间 准考证下载时间
    String GET_STU_CHECK_IN_INFO = "GetStuCheckInInfo"; // 获取考生签到数据

    String SET_PRINT_TIME = "setPrintTime"; // 设置考试日期
    String GET_POST_INFO_BY_EXAM_ID = "GetPostInfoByExamId"; //通过场次id获取岗位信息
    String JOB_SIGN_IN = "JobSignIn"; //岗位签到表

    String GET_CHECK_IN_EXAM = "GetCheckInExam"; // 获取考务签到页面的考试
    String GET_STUDENT_SIGN_TABLE = "GetStudentSignTable"; //获取考生签到表
    String GET_POST_NOTICE_LIST = "GetPostNoticeList"; //获取岗位通知单
    String GET_CARD_FOR_EXAM_LIST = "GetCardForExamList"; //准考证打印
    String DOWNLOAD_STUDENT_SIGN_EXCEL = "DownloadStudentSignExcel"; //下载考生签到表

    String KAO_WU_SIGN_IN = "KaoWuSignIn";//考务签到
    String KAO_WU_PERSON_INFO = "KaoWuPersonInfo";//考务签到人员信息
}
