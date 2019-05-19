package com.byqj.module.gradeManagement.controller;

import com.byqj.exception.RequestFailureException;
import com.byqj.module.collegeProctorManagement.enums.CollegeProctorManagementReasonOfFailure;
import com.byqj.module.gradeManagement.enums.GradeManagementReasonOfFailure;
import com.byqj.module.gradeManagement.service.GradeManagementBusinessService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.utils.CheckFileUtil;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@CrossOrigin
@RestController

public class GradeManagementController {
    @Autowired
    private GradeManagementAppVerNoDispatcher gradeManagementAppVerNoDispatcher;
    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private GradeManagementBusinessService gradeManagementBusinessService;


    @RequestMapping("/gradeManagement")
    public Object home() {

        gradeManagementAppVerNoDispatcher.dispatchByAppVerNo();
        return dataCenterService.getResponseDataFromDataLocal();

    }

    @RequestMapping("/gradeManagement/searchGradeCondition")
    public Object searchGradeCondition() {
        gradeManagementAppVerNoDispatcher.dispatchByAppVerNo();
        return dataCenterService.getResponseDataFromDataLocal();
    }

    @RequestMapping("/addGrade")
    public Object addGrade(HttpServletRequest request, HttpServletResponse response) {
        Part part = null;
        try {
            request.setCharacterEncoding("UTF-8");
            part = request.getPart("file");
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.FILE_UPLOAD_ERROR);

        } catch (ServletException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.FILE_UPLOAD_ERROR);
        }
        if (!CheckFileUtil.isExcelFile(part)) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.FILE_IS_ILLEGAL);
        }
        dataCenterService.setData("part", part);
        gradeManagementBusinessService.postGradeRequestProcess();
        return dataCenterService.getResponseDataFromDataLocal();
    }


    @RequestMapping("/addSingleGrade")
    public Object addSingleGrade(HttpServletRequest request, HttpServletResponse response) {
        Part part = null;
        String examId = request.getParameter("examId");
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        try {
            request.setCharacterEncoding("UTF-8");
            part = request.getPart("file");
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.FILE_UPLOAD_ERROR);

        } catch (ServletException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.FILE_UPLOAD_ERROR);
        }
        if (!CheckFileUtil.isExcelFile(part)) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.FILE_IS_ILLEGAL);
        }
        dataCenterService.setData("part", part);
        dataCenterService.setData("examId", examId);
        gradeManagementBusinessService.postSingleGradeRequestProcess();
        return dataCenterService.getResponseDataFromDataLocal();
    }

    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException) {

        return requestFailureException.getResponseData();

    }
}
