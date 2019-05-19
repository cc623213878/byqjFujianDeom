package com.byqj.module.classInfoManagement.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.TssClassInfoDao;
import com.byqj.entity.TssClassInfo;
import com.byqj.entity.TssExam;
import com.byqj.module.classInfoManagement.constant.ClassInfoConstants;
import com.byqj.module.classInfoManagement.enums.ClassInfoManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.service.IExamPlaceService;
import com.byqj.service.IExamService;
import com.byqj.utils.CheckVariableUtil;
import com.byqj.utils.ExceptionUtil;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassInfoManagementCheckService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private TssClassInfoDao tssClassInfoDao;
    @Autowired
    private IExamService examService;
    @Autowired
    private IExamPlaceService examPlaceService;


    public void seachByConditionRequestCheck() {

        String className = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("className");
        Integer status = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("status");
        Integer examPointCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPointCode");
        Integer examTypeCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examTypeCode");
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.PAGEINFO_ERROR);
        }

        dataCenterService.setData("className", className);
        dataCenterService.setData("status", status);
        dataCenterService.setData("examPointCode", examPointCode);
        dataCenterService.setData("examTypeCode", examTypeCode);
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);
    }

    public void addClassroomRequestCheck() {
        String className = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("className");
        Integer status = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("status");
        Integer examPointCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPointCode");
        Integer examTypeCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examTypeCode");
        Integer capacity = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("capacity");
        String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");

        if (status == null || examPointCode == null || examTypeCode == null || capacity == null) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.PARAMETER_IS_EMPTY);
        }
        if (StringUtils.isBlank(className)) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.PARAMETER_IS_EMPTY);
        }
        int count = tssClassInfoDao.selectCount(new LambdaQueryWrapper<TssClassInfo>().eq(TssClassInfo::getName, className));
        if (count > 0) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.CLASS_NAME_REPEAT);
        }
        dataCenterService.setData("className", className.trim());
        dataCenterService.setData("status", status);
        dataCenterService.setData("examPointCode", examPointCode);
        dataCenterService.setData("examTypeCode", examTypeCode);
        dataCenterService.setData("capacity", capacity);
        dataCenterService.setData("remark", remark);
    }

    public void updateClassroomRequestCheck() {
        String id = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        String className = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("className");
        Integer status = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("status");
        Integer examPointCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPointCode");
        Integer examTypeCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examTypeCode");
        Integer capacity = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("capacity");
        String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");
        if (status == null || examPointCode == null || examTypeCode == null || capacity == null) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.PARAMETER_IS_EMPTY);
        }
        if (StringUtils.isBlank(className)) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.PARAMETER_IS_EMPTY);
        }
        if (StringUtils.isBlank(id)) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.PARAMETER_IS_EMPTY);
        }
        int count = tssClassInfoDao.selectCount(new LambdaQueryWrapper<TssClassInfo>().eq(TssClassInfo::getName, className).ne(TssClassInfo::getId, id));
        if (count > 0) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.CLASS_NAME_REPEAT);
        }
        dataCenterService.setData("id", id.trim());
        dataCenterService.setData("className", className.trim());
        dataCenterService.setData("status", status);
        dataCenterService.setData("examPointCode", examPointCode);
        dataCenterService.setData("examTypeCode", examTypeCode);
        dataCenterService.setData("capacity", capacity);
        dataCenterService.setData("remark", remark);
    }

    public void deleteClassroomRequestCheck() {
        String id = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        if (StringUtils.isBlank(id)) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.PARAMETER_IS_EMPTY);
        }

        //查询未归档的场次
        int num = countClassByClassIdFromExamPlace(id);

        if (num > 0) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.UNABLE_TO_DELETE_CLASSROOM_HAS_BEEN_ARRANGED);
        }

        dataCenterService.setData("id", id.trim());
    }

    private int countClassByClassIdFromExamPlace(String classId) {
        List<TssExam> examList = examService.getBaseMapper().selectList(new LambdaQueryWrapper<TssExam>().eq(TssExam::getStatus, 0).ne(TssExam::getParentId, 0));

        List<String> examIdList = examList.stream().filter(Objects::nonNull).map(TssExam::getId).collect(Collectors.toList());

        return examPlaceService.countByClassIdAndExamPlaceId(classId, examIdList);
    }

    public void updateStatusRequestCheck() {
        String id = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        Integer status = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("status");
        if (StringUtils.isBlank(id)) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.PARAMETER_IS_EMPTY);
        }
        if (status == null) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.PARAMETER_IS_EMPTY);
        }
        Set<Integer> set = Sets.newHashSet();
        set.add(ClassInfoConstants.START);
        set.add(ClassInfoConstants.STOP);

        if (!set.contains(status)) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.PARAMETER_ERROR);
        }

        if (ClassInfoConstants.STOP == status) {
            int num = countClassByClassIdFromExamPlace(id);
            if (num > 0) {
                ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.UNABLE_TO_UPDATE_STATUS_CLASSROOM_HAS_BEEN_ARRANGED);
            }
        }

        dataCenterService.setData("id", id.trim());
        dataCenterService.setData("status", status);
    }

}
