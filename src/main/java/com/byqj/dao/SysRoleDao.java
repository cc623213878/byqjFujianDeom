package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.RoleCodeAndNameDto;
import com.byqj.entity.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleDao extends BaseMapper<SysRole> {

    @Select("<script> " +
            "SELECT role_code, role_name, remark, create_time " +
            "FROM sys_role " +
            "WHERE 1" +
            "<if test=\"roleName!=null and roleName!=\'\' \"> " +
            "AND role_name like concat('%', #{roleName}, '%') " +
            "</if> " +
            "</script>")
    List<SysRole> searchRole(@Param("roleName") String roleName);

    @Select("SELECT role_name FROM sys_role WHERE role_code=#{roleCode}")
    String selectRoleNameByRoleCode(String roleCode);

    @Delete("<script>  " +
            "DELETE FROM sys_role WHERE role_code IN " +
            "<foreach collection='roleCodeList' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    void batchDeleteRoleByRoleCode(@Param("roleCodeList") List<String> roleCodeList);

    @Select("<script> " +
            "SELECT DISTINCT role_code,role_name " +
            "FROM sys_role " +
            "</script>")
    List<RoleCodeAndNameDto> searchRoleClass();

    @Select("SELECT role_code,role_name FROM sys_role")
    List<RoleCodeAndNameDto> showRoleCodeAndName();

    @Select("<script> " +
            "SELECT sr.role_code,sr.role_name " +
            "FROM sys_role_user AS sru " +
            "LEFT JOIN sys_role AS sr ON sru.role_code=sr.role_code " +
            "where 1" +
            "<if test=\"userId!=null and userId!=\'\' \"> " +
            "AND sru.user_id=#{userId} " +
            "</if> " +
            "</script>")
    List<RoleCodeAndNameDto> searchRoleName(@Param("userId") String userId);

    @Select("<script>" +
            "SELECT role_name " +
            "FROM sys_role " +
            "WHERE 1 " +
            "<if test=\"list!=null and list.size()>0 \"> " +
            "AND role_code IN " +
            "<foreach collection='list' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</if>" +
            "</script>")
    List<String> getRoleNameById(@Param("list") List<String> list);
}
