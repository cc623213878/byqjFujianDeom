package com.byqj.module.common.operationLog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationLogService {


    @Autowired
    OperationLogCheckService operationLogCheckService;
    @Autowired
    OperationLogBusinessService operationLogBusinessService;

    public void searchOperationLogRequestProcess() {
        operationLogCheckService.searchOperationLogRequestCheck();
        operationLogBusinessService.searchOperationLogRequestProcess();
    }

}
