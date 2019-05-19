package com.byqj.module.collegeExamManagement.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.TssSolicitationTimeDao;
import com.byqj.entity.TssSolicitationTime;
import com.byqj.module.collegeExamManagement.constant.CollegeExamManagementConstant;
import com.byqj.module.collegeExamManagement.enums.CollegeExamManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CollegeExamManagementCheckService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private TssSolicitationTimeDao tssSolicitationTimeDao;

    /**
     * 准考证打印
     */
    public void getCardForExamListRequestCheck() {
        String mainExamId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(CollegeExamManagementConstant.MAIN_EXAM_ID);
        if (StringUtils.isBlank(mainExamId)) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeExamManagementReasonOfFailure.MAIN_EXAM_ID_IS_BLANK);
        }
        TssSolicitationTime tssSolicitationTime = tssSolicitationTimeDao.selectOne(new LambdaQueryWrapper<TssSolicitationTime>()
                .eq(TssSolicitationTime::getTeId, mainExamId));
        //假如有设定打印时间，则判断时间，没有设定则放过
        if (tssSolicitationTime != null) {
            if (!(new Date().after(tssSolicitationTime.getAdmissionTicketTimeStart())
                    && new Date().before(tssSolicitationTime.getAdmissionTicketTimeEnd()))) {
                ExceptionUtil.setFailureMsgAndThrow(CollegeExamManagementReasonOfFailure.NOW_CAN_NOT_PRINT);
            }
        }
        dataCenterService.setData(CollegeExamManagementConstant.MAIN_EXAM_ID, mainExamId.trim());
    }
}
