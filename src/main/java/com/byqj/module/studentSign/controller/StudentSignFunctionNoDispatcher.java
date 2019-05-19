package com.byqj.module.studentSign.controller;

import com.byqj.module.studentSign.constant.StudentSignFunctionNoConstants;
import com.byqj.module.studentSign.service.StudentSignService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentSignFunctionNoDispatcher {

    @Autowired
    StudentSignService studentSignService;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {

            //考生签到监考登录
            case StudentSignFunctionNoConstants.LOG_ON:
                studentSignService.logOnRequestProcess();
                break;
            //考生签到
            case StudentSignFunctionNoConstants.SIGN_IN:
                studentSignService.signInRequestProcess();
                break;
            //查询考生签到信息
            case StudentSignFunctionNoConstants.SEARCH_STUDENT_SIGN_MESSAGE:
                studentSignService.searchStudentSignMessageRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
