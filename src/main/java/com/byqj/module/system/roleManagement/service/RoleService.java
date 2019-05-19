package com.byqj.module.system.roleManagement.service;

import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private RoleCheckService roleCheckService;
    @Autowired
    private RoleBusinessService roleBusinessService;

    /**
     * 查询role
     */
    public void searchRoleRequestProcess() {
        roleCheckService.searchRoleRequestCheck();
        roleBusinessService.searchRoleRequestProcess();

    }

    /**
     * 查询role人员
     */
    public void searchRolePersonRequestProcess() {
        roleCheckService.searchRolePersonRequestCheck();
        roleBusinessService.searchRolePersonRequestProcess();

    }

    /**
     * 添加role
     */
    public void addRoleRequestProcess() {
        roleCheckService.addRoleRequestCheck();
        roleBusinessService.addRoleRequestProcess();
    }


    /**
     * 删除role
     */
    public void deleteRoleRequestProcess() {
        roleCheckService.deleteRoleRequestCheck();
        roleBusinessService.deleteRoleRequestProcess();
    }

    /**
     * 获取role类别
     */
    public void searchRoleClassRequestProcess() {
        roleBusinessService.searchRoleClassRequestProcess();
    }


    /**
     * 修改role
     */

    public void modifyRoleRequestProcess() {
        roleCheckService.modifyRoleRequestCheck();
        roleBusinessService.modifyRoleRequestProcess();
    }


    /*
     * @Author lwn
     * @Description 获取权限组列表
     * @Date 2:44 2019/3/4
     * @Param []
     * @return void
     **/
    public void getRoleAclRequestProcess() {
        roleCheckService.getRoleAclRequestCheck();
        roleBusinessService.getRoleAclRequestProcess();
    }


    /*
     * author lwn
     * date 2019/4/3 17:22
     * param
     * return void
     * 获取当前登录用户的权限
     */
    public void getCurrentUserAclRequestProcess() {
        roleBusinessService.getCurrentUserAclRequestProcess();
    }
}
