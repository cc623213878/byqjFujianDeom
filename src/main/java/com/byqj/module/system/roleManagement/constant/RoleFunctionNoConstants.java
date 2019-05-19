package com.byqj.module.system.roleManagement.constant;

public interface RoleFunctionNoConstants {

    String SEARCH_ROLE = "SearchRole";  //获取权组列表
    String SEARCH_ROLE_PERSON = "SearchRolePerson";  //获取该角色下所有人
    String SEARCH_ROLE_CLASS = "SearchRoleClass";  //获取角色类型
    String GET_ROLE_ACL = "GetRoleAcl";  //获取权限列表
    String ADD_AUTHORITY_GROUP = "AddRole";  //添加权组
    String DELETE_AUTHORITY_GROUP = "DeleteRole";  //删除权组
    String MODIFY_AUTHORITY_GROUP = "ModifyRole";  //修改权组
    String GET_CURRENT_USER_ALC = "GetCurrentUserAcl";//获取当前登录用户的所有权限


}
