package com.byqj.dao;

import com.byqj.dto.SysRoleUserDto;
import com.byqj.entity.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleUserDao {

    @Select("SELECT COUNT(sru.user_id) FROM sys_role_user AS sru WHERE role_code=#{roleCode}")
    boolean checkUserInRole(String roleCode);

    @Delete("DELETE FROM sys_role_user WHERE user_id=#{userId}")
    boolean deleteUserFromSysRoleUser(String userId);

    @Delete("<script>  " +
            "DELETE FROM sys_role_user WHERE user_id IN " +
            "<foreach collection='userIdList' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    void batchDeleteUserFromSysRoleUser(@Param("userIdList") List<String> userIdList);

    @Select("<script>"
            + "SELECT sr.* "
            + "FROM sys_role_user AS sru "
            + "LEFT JOIN sys_role AS sr ON sr.role_code=sru.role_code "
            + "WHERE sru.user_id =#{userId} "
            + "</script>")
    List<SysRole> searchUserRolesById(String userId);

    @Insert("<script>"
            + "INSERT INTO sys_role_user (user_id,role_code)"
            + "VALUES "
            + "<foreach collection=\"roleCodeList\" item=\"item\"  index=\"index\" separator=\",\"> "
            + "(#{userId},#{item})"
            + "</foreach> "
            + "</script>")
    boolean addPersonnelToSysRoleUser(@Param("userId") String userId,
                                      @Param("roleCodeList") List<String> roleCodeList);

    @Select("<script>"
            + "SELECT sad.user_name,sad.real_name,sad.phone,sd.name,sad.post "
            + "FROM sys_role_user AS sru "
            + "LEFT JOIN sys_admin_detail AS sad ON sru.user_id=sad.user_id "
            + "LEFT JOIN sys_department AS sd ON sad.dept_id=sd.id "
            + "WHERE 1 "
            + "<if test=\"roleCode!=null and roleCode!=\'\' \"> "
            + "AND sru.role_code =#{roleCode} "
            + "</if> "
            + "</script>")
    List<SysRoleUserDto> searchUserDetailByRoleCode(@Param("roleCode") String roleCode);

}
