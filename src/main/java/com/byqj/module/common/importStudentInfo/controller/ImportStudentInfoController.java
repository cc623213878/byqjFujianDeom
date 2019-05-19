package com.byqj.module.common.importStudentInfo.controller;

import com.byqj.exception.RequestFailureException;
import com.byqj.module.common.importStudentInfo.enums.ImportStudentInfoReasonOfFailure;
import com.byqj.module.common.importStudentInfo.service.ImportStudentInfoService;
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

@RequestMapping("/common")
public class ImportStudentInfoController {
    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private ImportStudentInfoService importStudentInfoService;


//    @PostMapping(value = "/addStudentInfo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Object addStudentInfo(@RequestParam("file") MultipartFile file,
//                                 @RequestParam(value = "teId") String teId) {
    @RequestMapping("/addStudentInfo")
    public Object addStudentInfo(HttpServletRequest request, HttpServletResponse response) {
        String teId = request.getParameter("teId");
        if (StringUtils.isBlank(teId)) {
            ExceptionUtil.setFailureMsgAndThrow(ImportStudentInfoReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        Part part = null;
        try {
            request.setCharacterEncoding("UTF-8");
            part = request.getPart("file");
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(ImportStudentInfoReasonOfFailure.FILE_UPLOAD_ERROR);

        } catch (ServletException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(ImportStudentInfoReasonOfFailure.FILE_UPLOAD_ERROR);
        }
        if (!CheckFileUtil.isExcelFile(part)) {
            ExceptionUtil.setFailureMsgAndThrow(ImportStudentInfoReasonOfFailure.FILE_IS_ILLEGAL);
        }
        dataCenterService.setData("part", part);
        dataCenterService.setData("teId", teId);
        importStudentInfoService.postGradeRequestProcess();
        return dataCenterService.getResponseDataFromDataLocal();
    }

    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException) {

        return requestFailureException.getResponseData();

    }
}
