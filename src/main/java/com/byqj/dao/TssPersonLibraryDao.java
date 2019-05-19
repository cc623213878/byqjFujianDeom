package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.TssPersonLibraryDto;
import com.byqj.entity.TssPersonLibrary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TssPersonLibraryDao extends BaseMapper<TssPersonLibrary> {

    @Select("<script> " +
            "SELECT tpl.*,sd.name \"post_name\" " +
            "FROM tss_person_library AS tpl " +
            "LEFT JOIN sys_department AS sd ON sd.id=tpl.college_id " +
            "WHERE 1" +
            "<if test=\"name!=null and name!=\'\' \"> " +
            "AND tpl.name like concat('%', #{name}, '%') " +
            "</if> " +
            "<if test=\"workCode!=null and workCode!=\'\' \"> " +
            "AND tpl.work_code like concat('%', #{workCode}, '%') " +
            "</if> " +
            "<if test=\"phone!=null and phone!=\'\' \"> " +
            "AND tpl.phone like concat('%', #{phone}, '%') " +
            "</if> " +
            "<if test=\"collegeIdList!=null and collegeIdList.size()>0 \"> " +
            "AND tpl.college_id IN " +
            "<foreach collection='collegeIdList' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</if> " +
            "</script>")
    List<TssPersonLibraryDto> searchPerson(@Param("name") String name,
                                           @Param("workCode") String workCode,
                                           @Param("phone") String phone,
                                           @Param("collegeIdList") List<Long> collegeIdList);


    @Select("<script> " +
            "SELECT tpl.*,sd.name \"post_name\" " +
            "FROM tss_person_library AS tpl " +
            "LEFT JOIN sys_department AS sd ON sd.id=tpl.college_id " +
            "WHERE 1" +
            "<if test=\"name!=null and name!=\'\' \"> " +
            "AND tpl.name like concat('%', #{name}, '%') " +
            "</if> " +
            "<if test=\"workCode!=null and workCode!=\'\' \"> " +
            "AND tpl.work_code like concat('%', #{workCode}, '%') " +
            "</if> " +
            "<if test=\"phone!=null and phone!=\'\' \"> " +
            "AND tpl.phone like concat('%', #{phone}, '%') " +
            "</if> " +
            "<if test=\"collegeIdList!=null and collegeIdList.size()>0 \"> " +
            "AND tpl.college_id IN " +
            "<foreach collection='collegeIdList' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</if> " +
            "AND tpl.id NOT IN " +
            "( SELECT ID FROM tss_temp_submit_person WHERE te_id=#{teId} )" +
            "</script>")
    List<TssPersonLibraryDto> seachNoSubmitPerson(@Param("name") String name,
                                                  @Param("workCode") String workCode,
                                                  @Param("phone") String phone,
                                                  @Param("collegeIdList") List<Long> collegeIdList,
                                                  @Param("teId") String teId);


    /**
     * author lwn
     * description post_name为学院名称 该函数根据用户id返回带有学院名称的人员信息
     * date 2019/4/12 11:56
     * param
     * return java.util.List<com.byqj.dto.TssPersonLibraryDto>
     */
    @Select("<script>" +
            "SELECT tpl.*,sd.name AS post_name " +
            "FROM tss_person_library AS tpl " +
            "LEFT JOIN sys_department AS sd ON sd.id=tpl.college_id " +
            "WHERE 1 " +
            "<if test=\" name!=null and name!=\'\' \"> " +
            "AND tpl.name like CONCAT('%', #{name}, '%')  " +
            "</if>" +
            "<if test=\"deptId !=null  \"> " +
            "AND tpl.college_id=#{deptId} OR  sd.level like concat(#{newLevel},'.','%')  " +
            "</if>" +
            "<if test=\"list!=null and list.size()>0 \"> " +
            "AND tpl.id IN " +
            "<foreach collection='list' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</if>" +
            "</script>")
    List<TssPersonLibraryDto> searchPersonByIds(@Param("list") List<String> list, @Param("deptId") Long deptId, @Param("name") String name, @Param("newLevel") String newLevel);

    @Select("<script>" +
            "SELECT tpl.name " +
            "FROM tss_person_library AS tpl " +
            "WHERE 1 " +
            "<if test=\"list!=null and list.size()>0 \"> " +
            "AND tpl.id IN " +
            "<foreach collection='list' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</if>" +
            "</script>")
    List<String> searchPersonByIdList(@Param("list") List<String> list);
}
