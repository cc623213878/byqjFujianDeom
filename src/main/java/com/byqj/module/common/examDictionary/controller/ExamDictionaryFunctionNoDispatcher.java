package com.byqj.module.common.examDictionary.controller;

import com.byqj.module.common.examDictionary.constant.ExamDictionaryFunctionNoConstants;
import com.byqj.module.common.examDictionary.service.ExamDictionaryService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamDictionaryFunctionNoDispatcher {

    @Autowired
    ExamDictionaryService examDictionaryService;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {

            //获取考点
            case ExamDictionaryFunctionNoConstants.GET_EXAM_PLACE:
                examDictionaryService.getExamPlaceRequestProcess();
                break;
            //获取考试类型
            case ExamDictionaryFunctionNoConstants.GET_EXAM_TYPE:
                examDictionaryService.getExamTypeRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
