package com.byqj.module.common.operationLog.service;

import com.byqj.module.common.operationLog.enums.ReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.utils.CheckVariableUtil;
import com.byqj.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationLogCheckService {

    @Autowired
    DataCenterService dataCenterService;

    public void searchOperationLogRequestCheck() {
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");

        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.PAGEINFO_ERROR);
        }

        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);
    }

}
