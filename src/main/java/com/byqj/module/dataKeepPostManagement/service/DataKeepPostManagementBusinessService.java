package com.byqj.module.dataKeepPostManagement.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byqj.dao.TssPostInfoDao;
import com.byqj.dao.TssTempPostPersonDao;
import com.byqj.entity.TssPostInfo;
import com.byqj.entity.TssTempPostPerson;
import com.byqj.module.dataKeepPostManagement.constant.DataKeepPostManagementLogConstant;
import com.byqj.module.dataKeepPostManagement.enums.DataKeepPostManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.ExceptionUtil;
import com.byqj.utils.IdUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@SuppressWarnings("ALL")
@Service
public class DataKeepPostManagementBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private LogCenterService logCenterService;
    @Autowired
    private TssPostInfoDao tssPostInfoDao;
    @Autowired
    private TssTempPostPersonDao tempPostPersonDao;

    /**
     * 查询岗位
     */
    public void searchPostRequestProcess() {

        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");

        PageHelper.startPage(pageNum, pageSize);
        List<TssPostInfo> PostList = tssPostInfoDao.selectList(new QueryWrapper<TssPostInfo>());

        PageInfo<TssPostInfo> pageResult = new PageInfo<>(PostList);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "PostList", pageResult);
    }

    /**
     * 删除岗位
     */
    @Transactional
    public void batchDeletePostRequestProcess() {

        List<String> postIdList = dataCenterService.getData("postIdList");
        //装载不可删除和可以删除的List
//        List<String> postIdReturnList = Lists.newArrayList();
//        List<String> postIdDeleteList = Lists.newArrayList();
//        for (String postId : postIdList) {
//            TssPostInfo tssPostInfo = tssPostInfoDao.selectById(postId);
//            if (tssPostInfo != null) {
//                int count = tssPostPersonDao
//                        .selectCount(new LambdaQueryWrapper<TssPostPerson>().eq(TssPostPerson::getPostName, tssPostInfo.getName()));
//                if (count > 0) {
//                    postIdReturnList.add(postId);
//                }
//                if (count == 0) {
//                    postIdDeleteList.add(postId);
//                }
//            }
//        }
        if (!CollectionUtils.isEmpty(postIdList)) {
            int result = tssPostInfoDao.deleteBatchIds(postIdList);
            if (result < 1) {
                ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.DELETE_ERROR);
            }
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
//        ResponseDataUtil.putValueToData(responseData, "postIdReturnList", postIdReturnList);

        // 记录日志
        List<String> contentList = tssPostInfoDao.getPostNameById(postIdList);
        String operationContent = String.format(DataKeepPostManagementLogConstant.DELETE_POST, contentList);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }

    /**
     * 添加岗位
     */
    @Transactional
    public void addPostRequestProcess() {

        String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");
        String name = dataCenterService.getData("name");
        String freeString = dataCenterService.getData("free");
        Double free = Double.parseDouble(freeString);
        String id = IdUtils.getUUID();
        Integer num = tssPostInfoDao.selectCount(new LambdaQueryWrapper<TssPostInfo>().eq(TssPostInfo::getName, name));
        if (num > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.POST_EXIST);
        }

        TssPostInfo post = new TssPostInfo();
        post.setId(id);
        post.setName(name);
        post.setFree(free);
        post.setRemark(remark);
        int result = tssPostInfoDao.insert(post);

        if (result < 1) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.INSERT_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(DataKeepPostManagementLogConstant.ADD_POST, name);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }

    /**
     * 更新岗位
     */
    @Transactional
    public void updatePostRequestProcess() {

        String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");
        String name = dataCenterService.getData("name");
        String freeString = dataCenterService.getData("free");
        String id = dataCenterService.getData("id");
        Double free = Double.parseDouble(freeString);

        Integer num = tssPostInfoDao.selectCount(new LambdaQueryWrapper<TssPostInfo>().eq(TssPostInfo::getName, name).ne(TssPostInfo::getId, id));
        if (num > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.POST_EXIST);
        }

//        //修改附表
////        int postResult = tssPostInfoDao.selectCount(new QueryWrapper<TssPostInfo>().lambda()
////                .eq(TssPostInfo::getName, name)
////                .ne(TssPostInfo::getId, id));
////        if (postResult > 0) {
////            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.POST_EXIST);
////        }

        TssPostInfo tssPostInfo = tssPostInfoDao.selectById(id);
        String oldPostName = tssPostInfo.getName();
        //修改岗位人员临时表

        TssTempPostPerson tempPostPerson = new TssTempPostPerson();
        tempPostPerson.setPostName(name);

        tempPostPersonDao.update(tempPostPerson, new LambdaQueryWrapper<TssTempPostPerson>().eq(TssTempPostPerson::getPostName, oldPostName));

        //更新岗位信息表
        TssPostInfo post = new TssPostInfo();
        post.setId(id);
        post.setName(name);
        post.setFree(free);
        post.setRemark(remark);
        int resultOfTssPostInfo = tssPostInfoDao.update(post, new QueryWrapper<TssPostInfo>().lambda().eq(TssPostInfo::getId, id));
        if (resultOfTssPostInfo < 1) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.UPDATE_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(DataKeepPostManagementLogConstant.UPDATE_POST, name);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }
}
