package com.byqj.module.common.importStudentInfo.service;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dto.TssStudentInfoDto;
import com.byqj.entity.TssStudentInfo;
import com.byqj.entity.TssTempSeatSchedule;
import com.byqj.module.common.importStudentInfo.constant.LogConstant;
import com.byqj.module.common.importStudentInfo.enums.ImportStudentInfoReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.IStudentInfoService;
import com.byqj.service.ITempSeatScheduleService;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.AESUtil;
import com.byqj.utils.ExcelUtil;
import com.byqj.utils.ExceptionUtil;
import com.byqj.utils.IdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//@SuppressWarnings("ALL")
@Service
public class ImportStudentInfoBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private LogCenterService logCenterService;
    @Autowired
    private IStudentInfoService studentInfoService;
    @Autowired
    private ITempSeatScheduleService tempSeatScheduleService;
    @Value("${encode-role}")
    private String encodeRole;


    @Transactional
    public void postInfoRequestProcess() {
        Part part = dataCenterService.getData("part");
        String teId = dataCenterService.getData("teId");
        InputStream inputStream = null;
        try {
            inputStream = part.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(ImportStudentInfoReasonOfFailure.FILE_UPLOAD_ERROR);
        }
        //从上传信息中获取excle里的所有数据
        List<TssStudentInfoDto> excelList = (List<TssStudentInfoDto>) (List) ExcelUtil.readExcelWithModel(inputStream, TssStudentInfoDto.class, ExcelTypeEnum.XLS);
        //转换数据库操作实体
        List<TssStudentInfo> studentInfoList = new ArrayList<>();
        List<TssTempSeatSchedule> seatSchedulesList = new ArrayList<>();
        for (TssStudentInfoDto tssStudentInfoDto : excelList) {
            TssStudentInfo studentInfo = new TssStudentInfo();
            TssTempSeatSchedule tssTempSeatSchedule = new TssTempSeatSchedule();
            //生成自增Id
            String id = IdUtils.getUUID();
            studentInfo.setUserId(id);
            studentInfo.setTeId(teId);
            tssTempSeatSchedule.setId(id);
            tssTempSeatSchedule.setTeId(teId);
            //转换
            BeanUtils.copyProperties(tssStudentInfoDto, studentInfo);
            tssTempSeatSchedule.setXymc(tssStudentInfoDto.getYx());
            tssTempSeatSchedule.setXm(tssStudentInfoDto.getXm());
            tssTempSeatSchedule.setBkjb(tssStudentInfoDto.getBskmmc());
            //身份证加密
            String zjhm = studentInfo.getZjhm();
            if (zjhm != null) {
                studentInfo.setZjhm(AESUtil.AESEncode(encodeRole, zjhm));
            }
            //加入list
            studentInfoList.add(studentInfo);
            seatSchedulesList.add(tssTempSeatSchedule);
        }
        //库里删除上传的该考试的已有数据
        studentInfoService.remove(new LambdaQueryWrapper<TssStudentInfo>().eq(TssStudentInfo::getTeId, teId));
        tempSeatScheduleService.remove(new LambdaQueryWrapper<TssTempSeatSchedule>().eq(TssTempSeatSchedule::getTeId, teId));
        //把上传数据插入数据库
        boolean studentInfoResult = studentInfoService.saveBatch(studentInfoList);
        if (!studentInfoResult) {
            ExceptionUtil.setFailureMsgAndThrow(ImportStudentInfoReasonOfFailure.INSERT_ERROR);
        }
        boolean tempSeatResult = tempSeatScheduleService.saveBatch(seatSchedulesList);
        if (!tempSeatResult) {
            ExceptionUtil.setFailureMsgAndThrow(ImportStudentInfoReasonOfFailure.INSERT_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.POST_GRADE);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }


}
