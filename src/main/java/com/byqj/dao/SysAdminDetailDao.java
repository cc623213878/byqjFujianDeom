package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.SysAdminDetailDto;
import com.byqj.dto.SysAdminSelectDetailDto;
import com.byqj.entity.SysAdminDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName:SysAdminDetailDao
 * @Description:
 * @Author:lwn
 * @Date:2019/3/18 16:49
 **/
@Repository
public interface SysAdminDetailDao extends BaseMapper<SysAdminDetail> {

    @Update("UPDATE sys_admin_detail SET real_name=#{realName} WHERE user_Id=#{userId}")
    boolean updateRealNameByUserId(@Param("realName") String realName, @Param("userId") String userId);

    @Delete("<script>  " +
            "DELETE FROM sys_admin_detail WHERE user_id IN " +
            "<foreach collection='userIdList' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    void batchDeleteAdminFromUserDetails(@Param("userIdList") List<String> userIdList);

    /**
     * gys
     * userId传给子查询,返回roleList
     * @param partUserName
     * @param partRealName
     * @param partPhone
     * @param departmentIdList
     * @param state
     * @param currentUserId
     * @return
     */
    @Select("<script>"
            + "SELECT sad.*,su.is_locked,sd.name "
            + "FROM sys_admin_detail AS sad "
            + "LEFT JOIN sys_user AS su ON su.user_id=sad.user_id "
            + "LEFT JOIN sys_department AS sd ON sd.id=sad.dept_id "
            + "WHERE sad.user_id NOT LIKE #{currentUserId} "
            + "AND sad.dept_id NOT LIKE '0' "
            + "<if test=\"partUserName!=null and partUserName!=\'\' \"> "
            + "AND sad.user_name like concat('%', #{partUserName}, '%')  "
            + "</if> "
            + "<if test=\"partRealName!=null and partRealName!=\'\' \"> "
            + "AND sad.real_name like concat('%', #{partRealName}, '%') "
            + "</if> "
            + "<if test=\"partPhone!=null and partPhone!=\'\' \"> "
            + "AND sad.phone like concat('%', #{partPhone}, '%') "
            + "</if> "
            + "<if test=\"state!=null and state!=\'\' \"> "
            + "AND su.is_locked=#{state} "
            + "</if> "
            + "<if test=\"departmentIdList!=null and departmentIdList.size()>0 \"> "
            + "AND sd.id IN "
            + "<foreach collection='departmentIdList' index='index' item='item' open='(' close=')' separator=','> "
            + "#{item} "
            + "</foreach> "
            + "</if> "
            + "</script>")
    @Results({
            @Result(column = "is_locked", property = "locked"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "user_id", property = "sysRoleList", javaType = List.class,
                    many = @Many(select = "com.byqj.dao.SysRoleUserDao.searchUserRolesById"))
    })
    List<SysAdminDetailDto> fuzzyQueryByPartAdminInfoFromUsersAndAdminDetails(
            @Param("partUserName") String partUserName,
            @Param("partRealName") String partRealName,
            @Param("partPhone") String partPhone,
            @Param("departmentIdList") List<Long> departmentIdList,
            @Param("state") String state,
            @Param("currentUserId") String currentUserId);

    /**
     * @param userId
     * @return
     */

    @Select("<script>"
            + "SELECT sud.*,sd.name  "
            + "FROM sys_admin_detail AS sud "
            + "LEFT JOIN sys_department AS sd ON sd.id=sud.dept_id "
            + "WHERE 1 "
            + "<if test=\"userId!=null and userId!=\'\' \"> "
            + "AND sud.user_id=#{userId} "
            + "</if> "
            + "</script>")
    SysAdminSelectDetailDto selectAdminDetail(@Param("userId") String userId);

    /**
     * gys
     * userId传给子查询,返回roleList
     * @param partUserName
     * @param partRealName
     * @param partPhone
     * @param roleCode
     * @param departmentIdList
     * @param state
     * @param currentUserId
     * @return
     */
    @Select("<script>"
            + "SELECT sad.*,su.is_locked,sd.name "
            + "FROM sys_admin_detail AS sad "
            + "LEFT JOIN sys_user AS su ON su.user_id=sad.user_id "
            + "LEFT JOIN sys_department AS sd ON sd.id=sad.dept_id "
            + "LEFT JOIN sys_role_user AS sru ON sru.user_id=sad.user_id "
            + "WHERE sad.user_id NOT LIKE #{currentUserId} "
            + "AND sad.dept_id NOT LIKE '0' "
            + "<if test=\"partUserName!=null and partUserName!=\'\' \"> "
            + "AND sad.user_name like concat('%', #{partUserName}, '%')  "
            + "</if> "
            + "<if test=\"partRealName!=null and partRealName!=\'\' \"> "
            + "AND sad.real_name like concat('%', #{partRealName}, '%') "
            + "</if> "
            + "<if test=\"partPhone!=null and partPhone!=\'\' \"> "
            + "AND sad.phone like concat('%', #{partPhone}, '%') "
            + "</if> "
            + "<if test=\"state!=null and state!=\'\' \"> "
            + "AND su.is_locked=#{state} "
            + "</if> "
            + "<if test=\"departmentIdList!=null and departmentIdList.size()>0 \"> "
            + "AND sd.id IN "
            + "<foreach collection='departmentIdList' index='index' item='item' open='(' close=')' separator=','> "
            + "#{item} "
            + "</foreach> "
            + "</if> "
            + "<if test=\"roleCode!=null and roleCode!=\'\' \"> "
            + "AND sru.role_code like concat('%', #{roleCode}, '%')  "
            + "</if> "
            + "</script>")
    @Results({
            @Result(column = "is_locked", property = "locked"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "user_id", property = "sysRoleList", javaType = List.class,
                    many = @Many(select = "com.byqj.dao.SysRoleUserDao.searchUserRolesById"))
    })
    List<SysAdminDetailDto> fuzzyQueryByGroupIdAndPartAdminInfoFromUsersAndAdminDetails(
            @Param("partUserName") String partUserName,
            @Param("partRealName") String partRealName,
            @Param("partPhone") String partPhone,
            @Param("roleCode") String roleCode,
            @Param("departmentIdList") List<Long> departmentIdList,
            @Param("state") String state,
            @Param("currentUserId") String currentUserId);

    @Select("<script>" +
            "SELECT sad.user_name " +
            "FROM sys_admin_detail AS sad " +
            "WHERE 1 " +
            "<if test=\"list!=null and list.size()>0 \"> " +
            "AND sad.id IN " +
            "<foreach collection='list' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</if>" +
            "</script>")
    List<String> searchAdminDetailByIdList(@Param("list") List<String> list);

}
