package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.DeptLevelDto;
import com.byqj.entity.SysDepartment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDepartmentDao extends BaseMapper<SysDepartment> {
    @Select("SELECT * FROM sys_department ")
    List<DeptLevelDto> getAllDept();

    /*
     * @Author lwn
     * @Description  获该部门的子部门
     * @Date 22:55 2019/3/19
     * @Param []
     * @return java.util.List<com.byqj.dto.DeptLevelDto>
     **/

    @Select("SELECT * FROM sys_department WHERE  level like #{level} or level like concat( #{level}, '.' , '%') ")
    List<DeptLevelDto> getChildrenDept(String level);

    @Select("SELECT * FROM sys_department WHERE type=#{type}")
    List<DeptLevelDto> getDeptDict(int type);

    @Select("SELECT id FROM sys_department WHERE level like concat( #{level}, '.' ,#{id}) or level like concat( #{level}, '.' ,#{id},'.' ,'%') ")
    List<Long> getChildrenCollegeId(@Param("level") String level, @Param("id") Long id);


    /*
        根据userId 获取当前用户所在部门和level
     */
    @Select("SELECT  sd.*  FROM  sys_admin_detail AS tp LEFT JOIN sys_department  sd ON tp.dept_id=sd.id WHERE tp.user_id=#{userId}")
    DeptLevelDto getDepartmentByUserId(String userId);

    /*
        添加部门时，插入部门的seq的后面的seq都要向后挪动一位
     */
    @Update("UPDATE sys_department SET seq=seq+1 WHERE id in" +
            "(" +
            "SELECT id  FROM sys_department WHERE parentId=#{parentId}" +
            ")" +
            "AND seq >=#{seq}")
    int updateInsertSeq(@Param("parentId") Long parentId, @Param("seq") int seq);

    /*
        当部门移开时，原先seq的后面的seq都要向前挪动一位
     */
    @Update("UPDATE sys_department SET seq=seq-1 WHERE id in" +
            "(" +
            "SELECT id  FROM sys_department WHERE parentId=#{parentId}" +
            ")" +
            "AND seq >=#{seq}")
    int updateDeleteSeq(@Param("parentId") Long parentId, @Param("seq") int seq);

    @Select("SELECT name FROM sys_department WHERE id=#{id} ")
    String getDepartmentNameById(@Param("id") Long id);
}
