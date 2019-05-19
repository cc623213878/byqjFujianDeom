package com.byqj.module.studentSign.service;

import com.byqj.module.studentSign.enums.StudentSignReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentSignCheckService {

    @Autowired
    private DataCenterService dataCenterService;

    /**
     * 考生签到
     */
    public void signInRequestCheck() {

        String zkzh = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("zkzh");
        String examPlaceId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPlaceId");
        if (StringUtils.isBlank(zkzh)) {
            ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.ZKZH_IS_BLANK);
        }
        if (StringUtils.isBlank(examPlaceId)) {
            ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.EXAM_PLACE_ID_IS_BLANK);
        }
        dataCenterService.setData("zkzh", zkzh.trim());
        dataCenterService.setData("examPlaceId", examPlaceId.trim());
    }

    /**
     * 监考登录
     */
    public void logOnRequestCheck() {
        String account = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("account");
        String password = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("password");

        if (StringUtils.isBlank(account)) {
            ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.ACCONUT_IS_BLANK);
        }
        if (StringUtils.isBlank(password)) {
            ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.PASSWORD_IS_BLANK);
        }
        dataCenterService.setData("account", account.trim());
        dataCenterService.setData("password", password.trim());
    }

    /**
     * 查询考生签到信息
     */
    public void searchStudentSignMessageRequestCheck() {
        String examPlaceId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPlaceId");

        if (StringUtils.isBlank(examPlaceId)) {
            ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.EXAM_PLACE_ID_IS_BLANK);
        }
        dataCenterService.setData("examPlaceId", examPlaceId.trim());
    }
}
