package com.byqj.module.gradeManagement.service;

import com.alibaba.fastjson.JSONArray;
import com.byqj.dao.TssPersonLibraryDao;
import com.byqj.module.gradeManagement.enums.GradeManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.utils.CheckVariableUtil;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeManagementCheckService {

    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    private TssPersonLibraryDao tssPersonLibraryDao;

    public void postGradeRequestCheck() {

    }

    /**
     * 查询人员信息
     */
    public void searchPersonRequestCheck() {
        String kssjhcs = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("kssjhcs");
        String bskmmc = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("bskmmc");
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");

        if (StringUtils.isEmpty(kssjhcs)) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.THE_KSSJHCS_IS_EMPTY);
        }
        if (StringUtils.isEmpty(bskmmc)) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.THE_BSKMMC_IS_EMPTY);
        }
        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.PAGE_PARAM_IS_ILLEGAL);
        }

        dataCenterService.setData("kssjhcs", kssjhcs.trim());
        dataCenterService.setData("bskmmc", bskmmc.trim());
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);
    }

    /**
     * 查询成绩
     */
    public void searchGradeRequestCheck() {
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");

        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.PAGE_PARAM_IS_ILLEGAL);
        }

        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);
    }

    /**
     * 删除成绩信息
     */
    public void deleteGradeRequestCheck() {
        JSONArray groupJA = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("deleteList");
        List<Integer> deleteList = groupJA.toJavaList(Integer.class);

        if (CollectionUtils.isEmpty(deleteList)) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.LIST_NULL);
        }

        dataCenterService.setData("deleteList", deleteList);
    }
}
