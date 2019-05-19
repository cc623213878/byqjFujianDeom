package com.byqj.module.common.importStudentInfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportStudentInfoService {


    @Autowired
    private ImportStudentInfoCheckService importStudentInfoCheckService;
    @Autowired
    private ImportStudentInfoBusinessService importStudentInfoBusinessService;


    public void postGradeRequestProcess() {
        importStudentInfoCheckService.postInfoRequestProcessCheck();
        importStudentInfoBusinessService.postInfoRequestProcess();
    }
}
