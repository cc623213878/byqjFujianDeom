package com.byqj.module.dataKeepPostManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataKeepPostManagementService {


    @Autowired
    private DataKeepPostManagementCheckService dataKeepPostManagementCheckService;
    @Autowired
    private DataKeepPostManagementBusinessService dataKeepPostManagementBusinessService;

    /**
     * 查询岗位
     */
    public void searchPostRequestProcess() {
        dataKeepPostManagementCheckService.searchPostRequestCheck();
        dataKeepPostManagementBusinessService.searchPostRequestProcess();
    }

    /**
     * 删除岗位
     */
    public void batchDeletePostRequestProcess() {
        dataKeepPostManagementCheckService.batchDeletePostRequestCheck();
        dataKeepPostManagementBusinessService.batchDeletePostRequestProcess();
    }

    /**
     * 更新岗位
     */
    public void updatePostRequestProcess() {
        dataKeepPostManagementCheckService.updatePostRequestCheck();
        dataKeepPostManagementBusinessService.updatePostRequestProcess();
    }

    /**
     * 添加岗位
     */
    public void addPostRequestProcess() {
        dataKeepPostManagementCheckService.addPostRequestCheck();
        dataKeepPostManagementBusinessService.addPostRequestProcess();
    }
}
