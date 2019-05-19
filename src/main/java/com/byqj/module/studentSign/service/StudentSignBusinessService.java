package com.byqj.module.studentSign.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.*;
import com.byqj.dto.TssSeatScheduleDto;
import com.byqj.entity.TssExam;
import com.byqj.entity.TssExamPlace;
import com.byqj.entity.TssSeatSchedule;
import com.byqj.entity.TssTempSeatSchedule;
import com.byqj.module.studentSign.constant.LogConstant;
import com.byqj.module.studentSign.enums.StudentSignReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.AESUtil;
import com.byqj.utils.CheckVariableUtil;
import com.byqj.utils.DateTimeUtil;
import com.byqj.utils.ExceptionUtil;
import com.byqj.vo.ExaminationSignReturnVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class StudentSignBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private LogCenterService logCenterService;
    @Autowired
    private TssExamPlaceDao tssExamPlaceDao;
    @Autowired
    private TssStudentInfoDao tssStudentInfoDao;
    @Autowired
    private TssSeatScheduleDao tssSeatScheduleDao;
    @Autowired
    private TssExamDao tssExamDao;
    @Autowired
    private TssTempSeatScheduleDao tssTempSeatScheduleDao;
    @Value("${encode-role}")
    private String encodeRole;

    /**
     * 监考登录
     */
    public void logOnRequestProcess() {
        String account = dataCenterService.getData("account");
        String password = dataCenterService.getData("password");
        Integer passwordInteger = CheckVariableUtil.parseInt(password, 0);
        ExaminationSignReturnVo examinationSignReturnVo = tssExamPlaceDao.getExamMessageByExamPlaceId(account, passwordInteger);
        if (examinationSignReturnVo == null) {
            ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.ACCONUT_OR_PASSWORD_IS_WRONG);
        }
        //装载考点信息  根据考场的parentId查出来
        String examPointName = tssExamPlaceDao.getExamPointNameByExamPlaceParentId(examinationSignReturnVo.getParentId());
        examinationSignReturnVo.setExamPointName(examPointName);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "examinationSignReturnVo", examinationSignReturnVo);

        // 记录日志
        String operationContent = String.format(LogConstant.LOG_ON, account);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    /**
     * 考生签到
     */
    @Transactional
    public void signInRequestProcess() {
        String zkzh = dataCenterService.getData("zkzh");
        String examPlaceId = dataCenterService.getData("examPlaceId");
        TssExamPlace tssExamPlace = tssExamPlaceDao.selectOne(new LambdaQueryWrapper<TssExamPlace>().eq(TssExamPlace::getId, examPlaceId));

        if (tssExamPlace != null) {
            TssExam tssExam = tssExamDao.selectOne(new LambdaQueryWrapper<TssExam>().eq(TssExam::getId, tssExamPlace.getTeId()));
            if (tssExam != null) {

                //校验是否现在可以签到
                Date timeStart = DateTimeUtil.getDateAddMinutes(tssExam.getStartTime(), -tssExam.getReportStart());
                Date timeEnd = DateTimeUtil.getDateAddMinutes(tssExam.getStartTime(), tssExam.getReportEnd());

                if (!(new Date().after(timeStart) && new Date().before(timeEnd))) {
                    ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.NOW_CAN_NOT_SIGN_ON);
                }

                // 校验是否是当前考场的人
                if (tssExam.getStatus() == 0) {
                    TssTempSeatSchedule tssTempSeatSchedule = tssTempSeatScheduleDao.getStudentMessageByExamPlaceIdAndZkzh(examPlaceId, zkzh);
                    if (tssTempSeatSchedule == null) {
                        ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.NOT_IS_CURRENT_EXAM_PLACE);
                    }
                }
                if (tssExam.getStatus() == 1) {
                    TssSeatSchedule tssSeatSchedule = tssSeatScheduleDao.getStudentMessageByExamPlaceIdAndZkzh(examPlaceId, zkzh);
                    if (tssSeatSchedule == null) {
                        ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.NOT_IS_CURRENT_EXAM_PLACE);
                    }
                }

                //签到
                TssExam mainExam = tssExamDao.selectOne(new LambdaQueryWrapper<TssExam>().eq(TssExam::getId, tssExam.getParentId()));
                boolean result = tssStudentInfoDao.updateTimeByZkzh(zkzh, mainExam.getId());
                if (!result) {
                    ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.SIGN_IN_ERROR);
                }
            }
            if (tssExam == null) {
                ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.NO_EXAM);
            }
        }
        if (tssExamPlace == null) {
            ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.NO_EXAM);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);


        // 记录日志
        String operationContent = String.format(LogConstant.SIGN_IN, zkzh);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    /**
     * 查询考生签到信息
     */
    public void searchStudentSignMessageRequestProcess() {
        String examPlaceId = dataCenterService.getData("examPlaceId");
        TssExamPlace tssExamPlace = tssExamPlaceDao.selectOne(new LambdaQueryWrapper<TssExamPlace>().eq(TssExamPlace::getId, examPlaceId));
        List<TssSeatScheduleDto> tssSeatScheduleDtos = Lists.newArrayList();
        if (tssExamPlace != null) {
            TssExam tssExam = tssExamDao.selectOne(new LambdaQueryWrapper<TssExam>().eq(TssExam::getId, tssExamPlace.getTeId()));

            if (tssExam != null) {
                if (tssExam.getType() == 1) {
                    ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.PEN_EXAM_NO_SIGN_ON);
                }
                if (tssExam.getStatus() == 0) {
                    tssSeatScheduleDtos = tssTempSeatScheduleDao.getStudentSignMessageByExamPlaceId(examPlaceId);
                }
                if (tssExam.getStatus() == 1) {
                    tssSeatScheduleDtos = tssSeatScheduleDao.getStudentSignMessageByExamPlaceId(examPlaceId);
                }
            } else {
                ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.NO_EXAM);
            }
        } else {
            ExceptionUtil.setFailureMsgAndThrow(StudentSignReasonOfFailure.NO_EXAM);
        }
        //身份证解密
        tssSeatScheduleDtos.forEach(item -> item.setZjhm(AESUtil.AESDecode(encodeRole, item.getZjhm())));
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "studentMessages", tssSeatScheduleDtos);

    }
}
