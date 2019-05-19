package com.byqj.module.examManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ExamManagementService {


    @Autowired
    private ExamManagementCheckService examManagementCheckService;
    @Autowired
    private ExamManagementBusinessService examManagementBusinessService;

    // add or update exam
    public void setExamInfoRequestProcess() {
        examManagementCheckService.setExamInfoRequestCheck();
        examManagementBusinessService.setExamInfoRequestProcess();
    }

    // get exam info
    public void getExamInfoRequestProcess() {
        examManagementCheckService.getExamInfoRequestCheck();
        examManagementBusinessService.getExamInfoRequestProcess();
    }

    // add or update exam place
    public void setExamPlaceInfoRequestProcess() {
        examManagementCheckService.setExamPlaceInfoRequestCheck();
        examManagementBusinessService.setExamPlaceInfoRequestProcess();
    }

    // get exam place
    public void getExamPlaceInfoRequestProcess() {
        examManagementCheckService.getExamPlaceInfoRequestCheck();
        examManagementBusinessService.getExamPlaceInfoRequestProcess();
    }

    // set post
    public void setPostRequestProcess() {
        examManagementCheckService.setPostRequestCheck();
        examManagementBusinessService.setPostRequestProcess();
    }

    // set main class
    public void setMainClassRequestProcess() {
        examManagementCheckService.setMainClassRequestCheck();
        examManagementBusinessService.setMainClassRequestProcess();
    }

    // get free class
    public void getFreeClassRequestProcess() {
        examManagementCheckService.getFreeClassRequestCheck();
        examManagementBusinessService.getFreeClassRequestProcess();
    }

    // get exam info list
    public void getExamRequestProcess() {
        examManagementCheckService.getExamRequestCheck();
        examManagementBusinessService.getExamRequestProcess();
    }

    // delete post
    public void delPostRequestProcess() {
        examManagementCheckService.delPostRequestCheck();
        examManagementBusinessService.delPostRequestProcess();
    }

    // delete exam
    public void delExamRequestProcess() {
        examManagementCheckService.delExamRequestCheck();
        examManagementBusinessService.delExamRequestProcess();
    }

    // delete exam class
    public void delExamClassRequestProcess() {
        examManagementCheckService.delExamClassRequestCheck();
        examManagementBusinessService.delExamClassRequestProcess();
    }

    // 设置岗位征集时间
    public void setSolicitationTimeRequestProcess() {
        examManagementCheckService.setSolicitationTimeRequestCheck();
        examManagementBusinessService.setSolicitationTimeRequestProcess();
    }

    // 设置签到时间
    public void setReportTimeRequestProcess() {
        examManagementCheckService.setReportTimeRequestCheck();
        examManagementBusinessService.setReportTimeRequestProcess();
    }

    // 导出考场签到账号
    public void exportAccountRequestProcess() {
        examManagementCheckService.exportAccountRequestCheck();
        examManagementBusinessService.exportAccountRequestProcess();
    }

    // 考试归档
    public void fileExamRequestProcess() {
        examManagementCheckService.fileExamRequestCheck();
        examManagementBusinessService.fileExamRequestProcess();
    }


    /**
     * 获取待安排的考生
     */
    public void getUnscheduledExamineeRequestProcess() {
        examManagementCheckService.getUnscheduledExamineeRequestCheck();
        examManagementBusinessService.getUnscheduledExamineeRequestProcess();
    }


    /**
     * 安排考生
     */
    public void arrangeExamineeRequestProcess() {
        examManagementCheckService.arrangeExamineeRequestCheck();
        examManagementBusinessService.arrangeExamineeRequestProcess();
    }

    /**
     * 获取待安排与已安排的考务人员
     */
    public void getUnscheduledPersonRequestProcess() {
        examManagementCheckService.getUnscheduledPersonRequestCheck();
        examManagementBusinessService.getUnscheduledPersonRequestProcess();
    }

    /**
     * 安排岗位
     */
    public void arrangePostRequestProcess() {
        examManagementCheckService.arrangePostRequestCheck();
        examManagementBusinessService.arrangePostRequestProcess();
    }

    /**
     * 获取考点岗位信息
     */
    public void getExamPlacePostInfoRequestProcess() {
        examManagementCheckService.getExamPlacePostInfoRequestCheck();
        examManagementBusinessService.getExamPlacePostInfoRequestProcess();
    }

    /**
     * 通过考试场次id获取考点信息
     */
    public void getExamPlaceByExamIdRequestProcess() {
        examManagementCheckService.getExamPlaceByExamIdRequestCheck();
        examManagementBusinessService.getExamPlaceByExamIdRequestProcess();
    }


    /**
     * 获取岗位费用设置
     */
    public void getPostFreeSetRequestProcess() {
        examManagementCheckService.getPostFreeSetRequestCheck();
        examManagementBusinessService.getPostFreeSetRequestProcess();
    }

    /**
     * 设置岗位费用
     */
    public void setPostFreeRequestProcess() {
        examManagementCheckService.setPostFreeRequestCheck();
        examManagementBusinessService.setPostFreeRequestProcess();
    }

    /**
     * 获取岗位人员费用
     */
    public void getPostFreeRequestProcess() {
        examManagementCheckService.getPostFreeRequestCheck();
        examManagementBusinessService.getPostFreeRequestProcess();
    }


    /**
     * 下载考试人员费用表
     */
    public void downloadPersonFreeRequestProcess(HttpServletRequest request, HttpServletResponse response) {
        examManagementCheckService.downloadPersonFreeRequestCheck(request);
        examManagementBusinessService.downloadPersonFreeRequestProcess(response);
    }

    /**
     * 获取岗位安排信息表
     */
    public void getPostSchedulingInfoRequestProcess() {
        examManagementCheckService.getPostSchedulingInfoRequestCheck();
        examManagementBusinessService.getPostSchedulingInfoRequestProcess();
    }

    /**
     * 准考证下载设置
     */
    public void admissionTicketDownloadSettingRequestProcess() {
        examManagementCheckService.admissionTicketDownloadSettingRequestCheck();
        examManagementBusinessService.admissionTicketDownloadSettingRequestProcess();
    }

    /**
     * 导入学生数据设置
     */
    public void importStudentInfoSettingRequestProcess() {
        examManagementCheckService.importStudentInfoSettingRequestCheck();
        examManagementBusinessService.importStudentInfoSettingRequestProcess();
    }


    /**
     * 查询岗位征集时间 学生导入时间 准考证下载时间
     */
    public void getSolicitationTimeRequestProcess() {
        examManagementCheckService.getSolicitationTimeRequestCheck();
        examManagementBusinessService.getSolicitationTimeRequestProcess();
    }

    /**
     * 获取考生签到信息
     */
    public void getStuCheckInInfoRequestProcess() {
        examManagementCheckService.getStuCheckInInfoRequestCheck();
        examManagementBusinessService.getStuCheckInInfoRequestProcess();
    }

    /**
     * 通过场次id获取岗位信息下拉框
     */
    public void getPostInfoByExamIdRequestProcess() {
        examManagementCheckService.getPostInfoByExamIdRequestCheck();
        examManagementBusinessService.getPostInfoByExamIdRequestProcess();
    }

    /**
     * 重置打印时间
     */
    public void resetPrintTimeRequestProcess() {
        examManagementCheckService.resetPrintTimeRequestCheck();
        examManagementBusinessService.resetPrintTimeRequestProcess();
    }

    /*
     * author lwn
     * description 岗位签到
     * date 2019/4/10 22:31
     * param
     * return void
     */
    public void jobSignInRequestProcess() {
        examManagementCheckService.jobSignInRequestCheck();
        examManagementBusinessService.jobSignInRequestProcess();
    }

    /**
     * 获取考务签到页面的考试
     */
    public void getCheckInExamRequestProcess() {
        examManagementCheckService.getCheckInExamRequestCheck();
        examManagementBusinessService.getCheckInExamRequestProcess();
    }

    /**
     * 获取考生签到表查询
     */
    public void getStudentSignTableRequestProcess() {
        examManagementCheckService.getStudentSignTableRequestCheck();
        examManagementBusinessService.getStudentSignTableRequestProcess();
    }


    /**
     * author lwn
     * description 下载岗位签到表
     * date 2019/4/13 16:07
     * param
     * return void
     */
    public void downloadJobSignInRequestProcess(HttpServletRequest request, HttpServletResponse response) {
        examManagementCheckService.downloadJobSignInRequestCheck(request);
        examManagementBusinessService.downloadJobSignInRequestProcess(request, response);
    }

    /**
     * 下载考生签到表
     */
    public void downloadStudentSignTableRequestProcess() {
        examManagementCheckService.downloadStudentSignTableRequestCheck();
        examManagementBusinessService.downloadStudentSignTableRequestProcess();
    }

    /**
     * 获取岗位通知单
     */
    public void getPostNoticeListRequestProcess() {
        examManagementCheckService.getPostNoticeListRequestCheck();
        examManagementBusinessService.getPostNoticeListRequestProcess();
    }

    /*
     * author lwn
     * description 考务签到
     * date 2019/4/15 15:31
     * param
     * return void
     */
    public void kaoWuSignInRequestProcess() {
        examManagementCheckService.kaoWuSignInRequestCheck();
        examManagementBusinessService.kaoWuSignInRequestProcess();

    }

    /**
     * 准考证打印
     */
    public void getCardForExamListRequestProcess() {
        examManagementCheckService.getCardForExamListRequestCheck();
        examManagementBusinessService.getCardForExamListRequestProcess();
    }

    /**
     * author lwn
     * description 考务签到页面人员信息
     * date 2019/4/20 21:26
     * param
     * return void
     */
    public void kaoWuSignInInfoRequestProcess() {
        examManagementCheckService.kaoWuSignInInfoRequestCheck();
        examManagementBusinessService.kaoWuSignInInfoRequestProcess();
    }
}
