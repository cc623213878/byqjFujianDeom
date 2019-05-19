package com.byqj.module.departmentManagement.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.SysDepartmentDao;
import com.byqj.entity.SysDepartment;
import com.byqj.module.departmentManagement.enums.DepartmentManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.service.IDepartmentService;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DepartmentManagementCheckService {

    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    SysDepartmentDao sysDepartmentDao;
    @Autowired
    IDepartmentService departmentService;


    public void addDepartmentRequestCheck() {
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String parentIdStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("parentId");
        int seq = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("seq");
        String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");
        int type = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("type");
        Long parentId = NumberUtils.toLong(parentIdStr.trim(), -1L);
        if (parentId == -1L) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        if (StringUtils.isBlank(name)) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        int count = departmentService.count(new LambdaQueryWrapper<SysDepartment>().eq(SysDepartment::getName, name));
        if (count > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.THE_DEPARTMENT_NAME_REPEAT);
        }
        dataCenterService.setData("name", name.trim());
        dataCenterService.setData("parentId", parentId);
        dataCenterService.setData("seq", seq);
        dataCenterService.setData("remark", remark);
        dataCenterService.setData("type", type);
    }


    public void updateDepartmentRequestCheck() {
        String idStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String parentIdStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("parentId");
        Integer seq = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("seq");
        String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");
        Integer type = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("type");
        Long parentId = NumberUtils.toLong(parentIdStr.trim(), -1L);
        if (parentId == -1L) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        Long id = NumberUtils.toLong(idStr.trim(), -1L);
        if (id == -1L) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        if (StringUtils.isBlank(name)) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        int count = departmentService.count(new LambdaQueryWrapper<SysDepartment>().eq(SysDepartment::getName, name).ne(SysDepartment::getId, id));
        if (count > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.THE_DEPARTMENT_NAME_REPEAT);
        }
        if (id.equals(parentId)) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.PARENT_DEPARTMENT_ERROR);
        }
        dataCenterService.setData("id", id);
        dataCenterService.setData("name", name.trim());
        dataCenterService.setData("parentId", parentId);
        dataCenterService.setData("seq", seq);
        dataCenterService.setData("remark", remark);
        dataCenterService.setData("type", type);
    }


    public void deleteDepartmentRequestCheck() {
        String idStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        Long id = NumberUtils.toLong(idStr.trim(), -1L);
        if (id == -1L) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        dataCenterService.setData("id", id);
    }
}
