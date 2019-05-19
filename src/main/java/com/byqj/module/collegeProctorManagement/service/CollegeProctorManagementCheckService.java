package com.byqj.module.collegeProctorManagement.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.TssPersonLibraryDao;
import com.byqj.dao.TssSolicitationTimeDao;
import com.byqj.dto.TssTempPostPersonAddDto;
import com.byqj.entity.TssSolicitationTime;
import com.byqj.module.collegeProctorManagement.enums.CollegeProctorManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.utils.CheckVariableUtil;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class CollegeProctorManagementCheckService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private TssPersonLibraryDao tssPersonLibraryDao;
    @Autowired
    private TssSolicitationTimeDao tssSolicitationTimeDao;

    public void searchSubmitPersonByConditionRequestCheck() {

        CheckSearchCondition();

    }

    private void CheckSearchCondition() {
        int pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        int pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        String teId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("teId");
        if (StringUtils.isBlank(teId)) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.PAGE_PARAM_IS_ILLEGAL);
        }
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);
        dataCenterService.setData("teId", teId.trim());
    }


    public void addUserRequestCheck() {
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userList");
        String teId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("teId");
        if (StringUtils.isBlank(teId)) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        if (array == null) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        List<TssTempPostPersonAddDto> userList = array.toJavaList(TssTempPostPersonAddDto.class);

        TssSolicitationTime tssSolicitationTime = tssSolicitationTimeDao.selectOne(new LambdaQueryWrapper<TssSolicitationTime>().eq(TssSolicitationTime::getTeId, teId));
        Date date = new Date();
        if (date.after(tssSolicitationTime.getPostTimeEnd()) || date.before(tssSolicitationTime.getPostTimeStart())) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.TIME_ERROR);
        }
        dataCenterService.setData("teId", teId.trim());
        dataCenterService.setData("userList", userList);

    }


    public void searchNoSubmitPersonByConditionRequestCheck() {
        CheckSearchCondition();
    }

    public void downloadSubmitPersonRequestCheck(HttpServletRequest request) {
        String teId = request.getParameter("teId");
        if (StringUtils.isBlank(teId)) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        dataCenterService.setData("teId", teId.trim());
    }
}
