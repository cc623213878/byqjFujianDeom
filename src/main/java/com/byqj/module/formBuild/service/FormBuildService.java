package com.byqj.module.formBuild.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormBuildService {

    @Autowired
    private FormBuildCheckService formBuildCheckService;
    @Autowired
    private FormBuildBusinessService formBuildBusinessService;

    /**
     * 重置 管理员 账号密码
     */
    public void resetPasswordRequestProcess() {
        formBuildCheckService.resetPasswordRequestCheck();
        formBuildBusinessService.resetPasswordRequestProcess();
    }

    /**
     * 修改 管理员 使用状态为启用或停用
     */
    public void modifyUserStatusRequestProcess() {
        formBuildCheckService.modifyUserStatusRequestCheck();
        formBuildBusinessService.modifyUserStatusRequestProcess();
    }

    /**
     * 删除管理员
     */
    public void deletePersonnelRequestProcess() {
        formBuildCheckService.deletePersonnelRequestCheck();
        formBuildBusinessService.deletePersonnelRequestProcess();
    }

    /*
     * @Author gys
     * @Description 获取管理员编辑显示列表
     * @Date 0:59 2019/3/5
     * @Param []
     * @return void
     **/
    public void getManagerAclInfoRequestProcess() {
        formBuildCheckService.getManagerAclInfoRequestCheck();
        formBuildBusinessService.getManagerAclInfoRequestProcess();
    }

    /**
     * 按条件搜索
     */
    public void searchPersonnelByConditionRequestProcess() {
        formBuildCheckService.searchPersonnelByConditionRequestCheck();
        formBuildBusinessService.searchPersonnelByConditionRequestProcess();

    }

    /**
     * 修改管理员
     */
    public void modifyPersonnelRequestProcess() {
        formBuildCheckService.modifyPersonnelRequestCheck();
        formBuildBusinessService.modifyPersonnelRequestProcess();
    }

    /**
     * 添加管理员
     */
    public void addAdminRequestProcess() {
        formBuildCheckService.addAdminRequestCheck();
        formBuildBusinessService.addAdminRequestProcess();
    }

    /**
     * 添加管理员页面显示 角色 权限
     */
    public void addUserDisplayRequestProcess() {
        formBuildBusinessService.addUserDisplayRequestProcess();
    }
}
