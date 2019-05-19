package com.byqj.module.classInfoManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassInfoManagementService {


    @Autowired
    ClassInfoManagementCheckService classInfoManagementCheckService;
    @Autowired
    ClassInfoManagementBusinessService classInfoManagementBusinessService;

    /*
     * @Author lwn
     * @Description 条件搜索
     * @Date 22:29 2019/3/18
     * @Param []
     * @return void
     **/
    public void seachByConditionRequestProcess() {
        classInfoManagementCheckService.seachByConditionRequestCheck();
        classInfoManagementBusinessService.seachByConditionRequestProcess();
    }
    /*
     * @Author lwn
     * @Description 修改考室信息
     * @Date 22:30 2019/3/18
     * @Param
     * @return
     **/

    public void updateClassroomRequestProcess() {
        classInfoManagementCheckService.updateClassroomRequestCheck();
        classInfoManagementBusinessService.updateClassroomRequestProcess();
    }


    /*
     * @Author lwn
     * @Description 添加考室
     * @Date 23:34 2019/3/18
     * @Param []
     * @return void
     **/
    public void addClassroomRequestProcess() {
        classInfoManagementCheckService.addClassroomRequestCheck();
        classInfoManagementBusinessService.addClassroomRequestProcess();

    }

    /*
     * @Author lwn
     * @Description  删除考室
     * @Date 23:34 2019/3/18
     * @Param []
     * @return void
     **/
    public void deleteClassroomRequestProcess() {
        classInfoManagementCheckService.deleteClassroomRequestCheck();
        classInfoManagementBusinessService.deleteClassroomRequestProcess();
    }

    public void updateStatusRequestProcess() {
        classInfoManagementCheckService.updateStatusRequestCheck();
        classInfoManagementBusinessService.updateStatusRequestProcess();
    }
}
