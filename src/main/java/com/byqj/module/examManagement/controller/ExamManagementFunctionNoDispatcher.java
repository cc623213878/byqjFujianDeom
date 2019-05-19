package com.byqj.module.examManagement.controller;

import com.byqj.module.examManagement.constant.ExamManagementFunctionNoConstants;
import com.byqj.module.examManagement.service.ExamManagementService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamManagementFunctionNoDispatcher {

    @Autowired
    ExamManagementService examManagementService;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {

            case ExamManagementFunctionNoConstants.SET_EXAM_INFO:
                examManagementService.setExamInfoRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_EXAM_INFO:
                examManagementService.getExamInfoRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.SET_EXAM_PLACE_INFO:
                examManagementService.setExamPlaceInfoRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_EXAM_PLACE_INFO:
                examManagementService.getExamPlaceInfoRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.SET_POST:
                examManagementService.setPostRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.SET_MAIN_CLASS:
                examManagementService.setMainClassRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_FREE_CLASS:
                examManagementService.getFreeClassRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_EXAM:
                examManagementService.getExamRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.DEL_POST:
                examManagementService.delPostRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.DEL_EXAM:
                examManagementService.delExamRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.DEL_EXAM_CLASS:
                examManagementService.delExamClassRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.SET_SOLICITATION_TIME:
                examManagementService.setSolicitationTimeRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.SET_REPORT_TIME:
                examManagementService.setReportTimeRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.EXPORT_ACCOUNT:
                examManagementService.exportAccountRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.FILE_EXAM:
                examManagementService.fileExamRequestProcess();
                break;

            case ExamManagementFunctionNoConstants.GET_UNSCHEDULED_EXAMINEE:
                examManagementService.getUnscheduledExamineeRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.ARRANGE_EXAMINEE:
                examManagementService.arrangeExamineeRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_UNSCHEDULED_PERSON:
                examManagementService.getUnscheduledPersonRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.ARRANGE_POST:
                examManagementService.arrangePostRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_EXAM_PLACE_POST_INFO:
                examManagementService.getExamPlacePostInfoRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_EXAM_PLACE_BY_EXAM_ID:
                examManagementService.getExamPlaceByExamIdRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_POST_FREE_SET:
                examManagementService.getPostFreeSetRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.SET_POST_FREE:
                examManagementService.setPostFreeRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_POST_FREE:
                examManagementService.getPostFreeRequestProcess();
                break;
//            case ExamManagementFunctionNoConstants.DOWNLOAD_PERSON_FREE:
//                examManagementService.downloadPersonFreeRequestProcess();
//                break;
            case ExamManagementFunctionNoConstants.GET_POST_SCHEDULING_INFO:
                examManagementService.getPostSchedulingInfoRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.ADMISSION_TICKET_DOWNLOAD_SETTING:
                examManagementService.admissionTicketDownloadSettingRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.IMPORT_STUDENT_INFO_SETTING:
                examManagementService.importStudentInfoSettingRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_SOLICITATION_TIME:
                examManagementService.getSolicitationTimeRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_STU_CHECK_IN_INFO:
                examManagementService.getStuCheckInInfoRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_POST_INFO_BY_EXAM_ID:
                examManagementService.getPostInfoByExamIdRequestProcess();
                break;

            case ExamManagementFunctionNoConstants.SET_PRINT_TIME:
                examManagementService.resetPrintTimeRequestProcess();
                break;
            //岗位签到表
            case ExamManagementFunctionNoConstants.JOB_SIGN_IN:
                examManagementService.jobSignInRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_CHECK_IN_EXAM:
                examManagementService.getCheckInExamRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_STUDENT_SIGN_TABLE:
                examManagementService.getStudentSignTableRequestProcess();
                break;
            case ExamManagementFunctionNoConstants.GET_POST_NOTICE_LIST:
                examManagementService.getPostNoticeListRequestProcess();
                break;
            //考务签到
            case ExamManagementFunctionNoConstants.KAO_WU_SIGN_IN:
                examManagementService.kaoWuSignInRequestProcess();
                break;
            //准考证打印
            case ExamManagementFunctionNoConstants.GET_CARD_FOR_EXAM_LIST:
                examManagementService.getCardForExamListRequestProcess();
                break;
            //下载考生签到表
            case ExamManagementFunctionNoConstants.DOWNLOAD_STUDENT_SIGN_EXCEL:
                examManagementService.downloadStudentSignTableRequestProcess();
                break;
            //考务签到页面信息显示
            case ExamManagementFunctionNoConstants.KAO_WU_PERSON_INFO:
                examManagementService.kaoWuSignInInfoRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
