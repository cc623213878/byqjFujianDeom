package com.byqj.module.studentSign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentSignService {
    @Autowired
    private StudentSignBusinessService studentSignBusinessService;
    @Autowired
    private StudentSignCheckService studentSignCheckService;

    /**
     * 监考登录
     */
    public void logOnRequestProcess() {
        studentSignCheckService.logOnRequestCheck();
        studentSignBusinessService.logOnRequestProcess();
    }

    /**
     * 考生签到
     */
    public void signInRequestProcess() {
        studentSignCheckService.signInRequestCheck();
        studentSignBusinessService.signInRequestProcess();
    }

    /**
     * 查询考生签到信息
     */
    public void searchStudentSignMessageRequestProcess() {
        studentSignCheckService.searchStudentSignMessageRequestCheck();
        studentSignBusinessService.searchStudentSignMessageRequestProcess();
    }
}
