package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.AclDto;
import com.byqj.entity.SysAcl;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAclDao extends BaseMapper<SysAcl> {

    @Delete("<script>  " +
            "DELETE FROM sys_user_acl WHERE user_id IN " +
            "<foreach collection='userIdList' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    void batchDeleteUserFromSysUserAcl(@Param("userIdList") List<String> userIdList);

    @Update("UPDATE sys_user_acl SET acl_code=#{aclCode}  WHERE user_id=#{userId}")
    boolean updatePersonnelToSysUserAcl(@Param("userId") String userId,
                                        @Param("aclCode") String aclCode);
    /*
     * @Author lwn
     * @Description 根据moduleId查询对应的权限
     * @Date 0:02 2019/3/4
     * @Param
     * @return
     **/

    @Select("SELECT * FROM sys_acl WHERE acl_module_id=#{moduleId} order by seq ")
    List<AclDto> getAclByModuleId(Long moduleId);


}
