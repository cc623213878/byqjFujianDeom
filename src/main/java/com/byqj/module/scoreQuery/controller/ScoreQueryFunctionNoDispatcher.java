package com.byqj.module.scoreQuery.controller;

import com.byqj.module.scoreQuery.constant.ScoreQueryFunctionNoConstants;
import com.byqj.module.scoreQuery.service.ScoreQueryService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScoreQueryFunctionNoDispatcher {

    @Autowired
    private ScoreQueryService scoreQueryService;
    @Autowired
    private DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {

            // 查询成绩
            case ScoreQueryFunctionNoConstants.SCORE_QUERY:
                scoreQueryService.scoreQueryRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
