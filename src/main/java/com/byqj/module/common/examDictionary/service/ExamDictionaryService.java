package com.byqj.module.common.examDictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamDictionaryService {


    @Autowired
    ExamDictionaryCheckService examDictionaryCheckService;
    @Autowired
    ExamDictionaryBusinessService examDictionaryBusinessService;

    /*
     * @Author lwn
     * @Description 获取考点
     * @Date 22:29 2019/3/18
     * @Param []
     * @return void
     **/
    public void getExamPlaceRequestProcess() {

        examDictionaryBusinessService.getExamPlaceRequestProcess();
    }
    /*
     * @Author lwn
     * @Description 获取考试类型
     * @Date 22:30 2019/3/18
     * @Param
     * @return
     **/

    public void getExamTypeRequestProcess() {
        examDictionaryBusinessService.getExamTypeRequestProcess();
    }
}
