package com.byqj.module.examManagement.controller;

import com.byqj.exception.RequestFailureException;
import com.byqj.module.examManagement.service.ExamManagementService;
import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/exam" )
public class ExamManagementController {
	@Autowired
	ExamManagementAppVerNoDispatcher examManagementAppVerNoDispatcher;
	@Autowired
	DataCenterService dataCenterService;
    @Autowired
    ExamManagementService examManagementService;
	
	@RequestMapping("/examManagement" )
	public Object home() {

		examManagementAppVerNoDispatcher.dispatchByAppVerNo();
		return dataCenterService.getResponseDataFromDataLocal();
	}


	/**
	 * 获取考务签到考试名称列表
	 *
	 * @return
	 */
	@RequestMapping("/getCheckInExam")
	public Object getCheckInExam() {
		examManagementAppVerNoDispatcher.dispatchByAppVerNo();
		return dataCenterService.getResponseDataFromDataLocal();
	}

	/**
	 * 获取考点
	 *
	 * @return
	 */
	@RequestMapping("/getExamPlaceByExamId")
	public Object getExamPlaceByExamId() {
		examManagementAppVerNoDispatcher.dispatchByAppVerNo();
		return dataCenterService.getResponseDataFromDataLocal();
	}

	/**
	 * 考务签到
	 *
	 * @return
	 */
	@RequestMapping("/kaoWuSignIn")
	public Object kaoWuSignIn() {
		examManagementAppVerNoDispatcher.dispatchByAppVerNo();
		return dataCenterService.getResponseDataFromDataLocal();
	}

	/**
	 * 查询考务签到信息列表
	 *
	 * @return
	 */
	@RequestMapping("/kaoWuPersonInfo")
	public Object kaoWuPersonInfo() {
		examManagementAppVerNoDispatcher.dispatchByAppVerNo();
		return dataCenterService.getResponseDataFromDataLocal();
	}

    @RequestMapping("/downloadJobCostExcel")
	public Object download(HttpServletRequest request, HttpServletResponse response) {
        examManagementService.downloadPersonFreeRequestProcess(request, response);
		return dataCenterService.getResponseDataFromDataLocal();
    }

    @RequestMapping("/downloadJobSignIn")
	public Object downloadJobSign(HttpServletRequest request, HttpServletResponse response) {
        examManagementService.downloadJobSignInRequestProcess(request, response);
		return dataCenterService.getResponseDataFromDataLocal();
    }

//	@RequestMapping("/downloadPostNoticeList")
//	public void downloadPostNoticeList(HttpServletRequest request, HttpServletResponse response) {
//		examManagementService.downloadPostNoticeListRequestProcess(request, response);
//	}

	@ExceptionHandler(RequestFailureException.class)
	public Object handleException(RequestFailureException requestFailureException){

		return requestFailureException.getResponseData();
		
	}
}
