package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.entity.SysUserAcl;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserAclDao extends BaseMapper<SysUserAcl> {
    @Select("<script>"
            + "SELECT sua.acl_code "
            + "FROM sys_user_acl AS sua "
            + "WHERE sua.user_id = #{userId} "
            + "</script>")
    String selectAclById(String userId);

    @Select("<script>"
            + "SELECT count(*) "
            + "FROM sys_user_acl "
            + "WHERE user_id = #{userId} "
            + "AND acl_code IS NOT NULL "
            + "</script>")
    int selectAclCountById(String userId);

    @Delete("DELETE FROM sys_user_acl WHERE user_id=#{userId}")
    boolean deleteFromSysUserAcl(String userId);

}
