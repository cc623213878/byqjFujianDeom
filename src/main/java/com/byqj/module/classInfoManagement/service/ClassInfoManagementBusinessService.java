package com.byqj.module.classInfoManagement.service;

import com.byqj.dao.TssClassInfoDao;
import com.byqj.dao.TssDictionaryDao;
import com.byqj.entity.TssClassInfo;
import com.byqj.module.classInfoManagement.constant.ClassInfoConstants;
import com.byqj.module.classInfoManagement.constant.LogConstant;
import com.byqj.module.classInfoManagement.enums.ClassInfoManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.ExceptionUtil;
import com.byqj.utils.IdUtils;
import com.byqj.vo.TssClassInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassInfoManagementBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private LogCenterService logCenterService;
    @Autowired
    private TssClassInfoDao tssClassInfoDao;
    @Autowired
    private TssDictionaryDao tssDictionaryDao;


    public void seachByConditionRequestProcess() {
        //获取对象
        String className = dataCenterService.getData("className");
        Integer status = dataCenterService.getData("status");
        Integer examPointCode = dataCenterService.getData("examPointCode");
        Integer examTypeCode = dataCenterService.getData("examTypeCode");
        int pageNum = dataCenterService.getData("pageNum");
        int pageSize = dataCenterService.getData("pageSize");
        //封装对象
        TssClassInfo tssClassInfo = new TssClassInfo();
        tssClassInfo.setName(className);
        tssClassInfo.setPlace(examPointCode);
        tssClassInfo.setType(examTypeCode);
        tssClassInfo.setStatus(status);
        //数据库查询code对应的description
        Map<Integer, String> examPointMap = new HashMap<>();
        Map<Integer, String> examTypeMap = new HashMap<>();

        List<Map<String, Object>> examPointMapList = tssDictionaryDao.getDescription(ClassInfoConstants.EXAM_POINT);
        List<Map<String, Object>> examTypeMapList = tssDictionaryDao.getDescription(ClassInfoConstants.EXAM_TYPE);
        listToMap(examPointMapList, examPointMap);
        listToMap(examTypeMapList, examTypeMap);
        //数据库查询
        PageHelper.startPage(pageNum, pageSize);
        List<TssClassInfoVo> classInfoList = tssClassInfoDao.seachByCondition(tssClassInfo);
        for (TssClassInfoVo tssClassInfoVo : classInfoList) {
            tssClassInfoVo.setPlaceDescription(examPointMap.get(tssClassInfoVo.getPlace()));
            tssClassInfoVo.setTypeDescription(examTypeMap.get(tssClassInfoVo.getType()));
        }
        PageInfo<TssClassInfoVo> pageResult = new PageInfo<>(classInfoList);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "classInfoList", pageResult);




    }

    private void listToMap(List<Map<String, Object>> list, Map target) {
        for (Map<String, Object> integerStringMap : list) {
            Integer code = null;
            String description = null;
            for (Map.Entry<String, Object> entry : integerStringMap.entrySet()) {
                if ("code".equals(entry.getKey())) {
                    code = (Integer) entry.getValue();
                }
                if ("description".equals(entry.getKey())) {
                    description = (String) entry.getValue();
                }
            }
            target.put(code, description);
        }
    }

    public void addClassroomRequestProcess() {
        //获取数据
        String className = dataCenterService.getData("className");
        Integer status = dataCenterService.getData("status");
        Integer examPointCode = dataCenterService.getData("examPointCode");
        Integer examTypeCode = dataCenterService.getData("examTypeCode");
        Integer capacity = dataCenterService.getData("capacity");
        String remark = dataCenterService.getData("remark");
        //封装数据
        TssClassInfo classInfo = new TssClassInfo();
        classInfo.setId(IdUtils.getUUID());
        classInfo.setName(className);
        classInfo.setStatus(status);
        classInfo.setPlace(examPointCode);
        classInfo.setType(examTypeCode);
        classInfo.setCapacity(capacity);
        classInfo.setRemark(remark);
        //数据库操作
        int insertResult = tssClassInfoDao.insert(classInfo);
        if (insertResult == 0) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.INSERT_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.ADD_CLASSROOM, classInfo.getName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    public void updateClassroomRequestProcess() {
        //获取数据
        String id = dataCenterService.getData("id");
        String className = dataCenterService.getData("className");
        Integer status = dataCenterService.getData("status");
        Integer examPointCode = dataCenterService.getData("examPointCode");
        Integer examTypeCode = dataCenterService.getData("examTypeCode");
        Integer capacity = dataCenterService.getData("capacity");
        String remark = dataCenterService.getData("remark");
        //封装数据
        TssClassInfo classInfo = new TssClassInfo();
        classInfo.setId(id);
        classInfo.setName(className);
        classInfo.setStatus(status);
        classInfo.setPlace(examPointCode);
        classInfo.setType(examTypeCode);
        classInfo.setCapacity(capacity);
        classInfo.setRemark(remark);
        //数据库操作
        int updateResult = tssClassInfoDao.updateById(classInfo);
        if (updateResult == 0) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.UPDATE_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.UPDATE_CLASSROOM, classInfo.getName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    public void deleteClassroomRequestProcess() {
        String id = dataCenterService.getData("id");
        TssClassInfo tssClassInfo = tssClassInfoDao.selectById(id);
        int deleteResult = tssClassInfoDao.deleteById(id);
        if (deleteResult == 0) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.DELETE_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.DELETE_CLASSROOM, tssClassInfo.getName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    public void updateStatusRequestProcess() {
        String id = dataCenterService.getData("id");
        Integer status = dataCenterService.getData("status");
        TssClassInfo tssClassInfo = tssClassInfoDao.selectById(id);
        TssClassInfo classInfo = new TssClassInfo();
        classInfo.setId(id);
        classInfo.setStatus(status);
        int updateResult = tssClassInfoDao.updateById(classInfo);
        if (updateResult == 0) {
            ExceptionUtil.setFailureMsgAndThrow(ClassInfoManagementReasonOfFailure.UPDATE_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.UPDATE_STATUS, tssClassInfo.getName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }
}
