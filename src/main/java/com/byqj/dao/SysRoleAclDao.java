package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.entity.SysRoleAcl;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleAclDao extends BaseMapper<SysRoleAcl> {

    @Delete("<script>  " +
            "DELETE FROM sys_role_acl WHERE role_code IN " +
            "<foreach collection='roleCodeList' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    void batchDeleteRoleAclByRoleCode(@Param("roleCodeList") List<String> roleCodeList);

    /*
     * @Author lwn
     * @Description 根据userId获取所属role的全部权限
     * @Date 20:21 2019/3/3
     * @Param
     * @return
     **/
    @Select("SELECT acl_code FROM sys_role_user LEFT JOIN sys_role_acl ON sys_role_user.role_id=sys_role_acl.role_id WHERE user_id=#{userId}")
    List<String> getAclByRoleUserId(String userId);


}
