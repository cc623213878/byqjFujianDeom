package com.byqj.module.dataKeepPostManagement.service;

import com.alibaba.fastjson.JSONArray;
import com.byqj.dao.TssPostInfoDao;
import com.byqj.module.dataKeepPostManagement.enums.DataKeepPostManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.service.ITempPostPersonService;
import com.byqj.utils.CheckVariableUtil;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataKeepPostManagementCheckService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private TssPostInfoDao tssPostInfoDao;
    @Autowired
    private ITempPostPersonService tempPostPersonService;

    /**
     * 查询岗位
     */
    public void searchPostRequestCheck() {

        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.PAGE_PARAM_IS_ILLEGAL);
        }
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);

    }

    /**
     * 删除岗位
     */
    public void batchDeletePostRequestCheck() {

        JSONArray groupJA = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("postIdList");
        List<String> postIdList = groupJA.toJavaList(String.class);
        if (CollectionUtils.isEmpty(postIdList)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.LIST_NULL);
        }

        int postNum = tempPostPersonService.countByPostIds(postIdList);
        if (postNum > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.THE_POST_WAS_USED_FOR_THE_EXAM_AND_CANNOT_BE_DELETED);
        }

        dataCenterService.setData("postIdList", postIdList);

    }

    /**
     * 添加岗位
     */
    public void addPostRequestCheck() {
        checkPostContent();
    }

    /**
     * 更新岗位
     */
    public void updatePostRequestCheck() {

        String id = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        if (StringUtils.isBlank(id)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.ID_NULL);
        }
        checkPostContent();
        dataCenterService.setData("id", id.trim());

    }

    public void checkPostContent() {

        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String free = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("free");
        if (StringUtils.isBlank(name)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.NAME_NULL);
        }
        if (StringUtils.isBlank(free)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.FREE_NULL);
        }
        dataCenterService.setData("name", name.trim());
        dataCenterService.setData("free", free);

    }
}
