package com.byqj.module.common.operationLog.controller;

import com.byqj.module.common.operationLog.constant.OperationLogFunctionNoConstants;
import com.byqj.module.common.operationLog.service.OperationLogService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OperationLogFunctionNoDispatcher {

    @Autowired
    OperationLogService operationLogService;
    @Autowired
    DataCenterService dataCenterService;

    public void dispatchByFunctionNo() {
        String functionNo = dataCenterService.getFunctionNo();
        log.debug("functionNo is {}", functionNo);

        switch (functionNo) {
            case OperationLogFunctionNoConstants.SEARCH_OPERATION_LOG:
                operationLogService.searchOperationLogRequestProcess();
                break;

            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(), ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
