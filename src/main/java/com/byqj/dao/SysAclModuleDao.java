package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.AclModuleLevelDto;
import com.byqj.entity.SysAclModule;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName:SysAclModule
 * @Description:
 * @Author:lwn
 * @Date:2019/3/3 1:10
 **/
@Repository
public interface SysAclModuleDao extends BaseMapper<SysAclModule> {

    @Select("SELECT *  FROM sys_acl_module WHERE type=#{type} AND `status`=#{status}")
    @Results({
            @Result(id = true, property = "id", column = "id", javaType = Long.class),
            @Result(column = "id", property = "aclList", javaType = List.class, many = @Many(select = "com.byqj.dao.SysAclDao.getAclByModuleId")
            )
    })
    List<AclModuleLevelDto> getModuleWithAcl(@Param("type") int type, @Param("status") int status);


}
