package com.byqj.module.examManagement.constant;

public interface ExamManagementConstant {

    //原因描述
    String FAIL_DELETE_INFO = "上传文件失败";
    //操作内容
    String DELETE_INFO_LOG = "上传名为 '%s' 的文件";

    String ID = "id";
    String EXNAME = "exName";
    String EXTYPE = "exType";
    String STIME = "sTime";
    String ETIME = "eTime";
    String REMARK = "remark";
    String EXAM_LIST = "examList";
    String EXAMS = "exams";
    String UPDATE_EXAMS = "updateExams";
    String ADD_EXAMS = "addExams";
    String EXAM_PLACES = "exPlaces";
    String EXAM_PLACE = "exPlace";
    String MAIN_EXAM_ID = "mainExamId";
    String EXAM_ID = "exId";
    String EXAM_PLACE_ID = "exPlaceId"; // 考点id
    String EXAM_PLACE_OR_CLASS_ID = "exPlaceOrClassId"; // 考点或考场id
    String EXAM_CLASS = "examCLass";
    String EXAM_CLASS_ID = "exClassId"; // 考场id
    String STU_NUM = "stuNum";
    String POST_NUM = "postNum";
    String POST_PERSON = "postPerson";
    String CLASSID = "classId";
    String OPER = "oper";
    String ADD = "add";
    String STATUS = "status";
    //    String POST_PERSON_ID = "ppId";
    String CLASSES = "classes";
    String PAGE_SIZE = "pageSize";
    String PAGE_NUM = "pageNum";
    String XLSX = ".xlsx";
    String POST_ID_AND_NUM = "postIdNumList";

    Integer UNSCHEDULED_EXAMINEE_SIGN = 0;
    String EXAMINEE_ID_STR = "examineeIdStr"; // 已选考生idStr
    String EXAMINEE_ID_LIST = "examineeIdList"; // 已选考生idStr
    String POST_ID = "postId"; //岗位id
    String PERSON_ID_STR = "personIdStr"; // 已选考务人员idStr

    String POST_FREE_LIST = "postFreeList";

    Integer UNFILED_STATUS = 0; // 未归档状态0
    Integer ARCHIVE_STATUS = 1; // 归档状态1


    String DEP_ID = "departmentId"; //部门id
    String START_TIME = "startTime"; //开始时间
    String END_TIME = "endTime"; // 结束时间

    String COLLEGE_NAME = "collegeName";
    String STU_NAME = "stuName";
    String LEVEL = "level";
    String EXAM_PLACE_NUM = "examPlaceNum"; // 考场号

    String SEPORATOR_CHARS = ",";

    String[] PDF_FIELD_ONE = {"mainExamNameOne", "collegeOne", "nameOne", "contentOne", "examTimeOne",
            "levelOne", "trainAddressOne", "trainTimeOne", "examAddressOne", "numberOne", "attentionOne", "timeOne"};//pdf模板中设置名称
    String[] PDF_FIELD_TWO = {"mainExamNameTwo", "collegeTwo", "nameTwo", "contentTwo", "examTimeTwo",
            "levelTwo", "trainAddressTwo", "trainTimeTwo", "examAddressTwo", "numberTwo", "attentionTwo", "timeTwo"};

    String EXAM_POINT = "EXAM_POINT";

    String POST_NOTICE_LIST_NAME = "PostNoticeListZip.zip";
    String CARD_FOR_EXAM_NAME = "CardForExamListZip.zip";
    String PDF_EXAM_ADDRESS_SUFFIX = "考场"; // 监考通知单中考场后缀
    String PDF_NAME_SUFFIX = "  老师"; // 监考通知单中考场后缀

    String[] PDF_FIELD_THREE = {"mainExamNameOne", "collegeOne", "nameOne", "contentOne", "examTimeOne",
            "levelOne", "addressOne", "trainTimeOne", "postOne", "attentionOne", "timeOne"};//pdf模板中设置名称
    String[] PDF_FIELD_FOUR = {"mainExamNameTwo", "collegeTwo", "nameTwo", "contentTwo", "examTimeTwo",
            "levelTwo", "addressTwo", "trainTimeTwo", "postTwo", "attentionTwo", "timeTwo"};

    String[] CARD_FRR_EXAM_PDF_FIELD_ONE = {"titleOne", "stuNumberOne", "nameOne", "examLanguageOne", "collegeOne",
            "majorOne", "examNumberOne", "photoOne", "penTimeOne", "penAddressOne", "computerTimeOne", "computerAddressOne"}; //pdf模板中设置名称
    String[] CARD_FRR_EXAM_PDF_FIELD_TWO = {"titleTwo", "stuNumberTwo", "nameTwo", "examLanguageTwo", "collegeTwo",
            "majorTwo", "examNumberTwo", "photoTwo", "penTimeTwo", "penAddressTwo", "computerTimeTwo", "computerAddressTwo"}; //pdf模板中设置名称
    String[] CARD_FRR_EXAM_PDF_FIELD_THREE = {"titleThree", "stuNumberThree", "nameThree", "examLanguageThree", "collegeThree",
            "majorThree", "examNumberThree", "photoThree", "penTimeThree", "penAddressThree", "computerTimeThree", "computerAddressThree"}; //pdf模板中设置名称

    int KCH_LENGTH = 3;//考场号显示长度
    String PAD_STR = "0";//考场号长度不够填充字符串

}
