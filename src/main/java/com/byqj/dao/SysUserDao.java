package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserDao extends BaseMapper<SysUser> {

    @Update("UPDATE sys_user SET password=#{password} WHERE user_id=#{userId}")
    boolean setPassword(@Param("userId") String userId, @Param("password") String password);

    @Update("UPDATE sys_user SET is_locked=#{stateConstant} WHERE user_id=#{userId}")
    boolean modifyUserStatus(@Param("userId") String userId, @Param("stateConstant") Integer stateConstant);

    @Delete("<script>  " +
            "DELETE FROM sys_user WHERE user_id IN " +
            "<foreach collection='userIdList' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    void batchDeleteUserFromUsers(@Param("userIdList") List<String> userIdList);

    @Select("<script> "
            + "SELECT * "
            + "FROM sys_user "
            + "WHERE user_id =#{userId} "
            + "</script> "
    )
    SysUser seachUserByUserId(String userId);

}
