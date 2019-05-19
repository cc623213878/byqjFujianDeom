package com.byqj.module.departmentManagement.enums;

import com.byqj.security.core.support.enums.IReasonOfFailure;

public enum DepartmentManagementReasonOfFailure implements IReasonOfFailure {


    USER_DOES_NOT_EXIST("User does not exist", "用户不存在"),
    FUNCTION_NO_ARE_INCORRECT("functionNoAreIncorrect", "功能号不正确！"),
    THE_PARAMETERS_SUBMITTED_ARE_INCORRECT("The parameters submitted are incorrect", "提交的参数有误"),
    THE_DEPARTMENT_NAME_REPEAT("The department's name repeat", "角色名称重复"),
    INSERT_ERROR("Insert error", "插入失败"),
    UPDATE_ERROR("Update error", "更新失败"),
    OLD_DEPT_NOT_EXIT("old department not exit", "待更新的部门不存在"),
    EXIST_SUB_DEPT("already subsidiary department", "当前部门下面有子部门，无法删除"),
    PARENT_DEPARTMENT_ERROR("Parent department error", "所选父节点不能是自己"),
    DB_OPER_ERROR("DB operate error", "数据库操作失败"),
    CRURRENT_USER_NO_DEPARTMENT("Current user no department", "当前登录用户查询不到部门"),
    DEPT_EXIST_USER("department exit user", "当前部门下面有用户，无法删除"),;

    private String en_msg;
    private String zh_msg;

    DepartmentManagementReasonOfFailure(String en_msg, String zh_msg) {
        this.en_msg = en_msg;
        this.zh_msg = zh_msg;
    }

    @Override
    public String getZhMsgOfFailure() {
        return zh_msg;
    }

    @Override
    public String getEnMsgOfFailure() {
        return en_msg;
    }


}
