package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.SubmitPersonDto;
import com.byqj.entity.TssTempSubmitPerson;
import com.byqj.vo.TempSubmitPersonVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by willim on 2019/3/22.
 */
@Repository
public interface TssTempSubmitPersonDao extends BaseMapper<TssTempSubmitPerson> {

//    @Update("<script> UPDATE tss_temp_submit_person SET tep_id='' WHERE tep_id IN" +
//            "  <foreach collection='tepIdList' item='item' open='(' separator=',' close=')'>" +
//            "    #{item}" +
//            "  </foreach>" +
//            "</script>")
//    boolean resetTepIdByTepIdsInTemp(@Param("tepIdList") List<String> readyDels);


    @Select("<script> " +
            " SELECT ttsp.id,ttsp.`name`,ttsp.pid,ttsp.sex,ttsp.work_code,ttsp.college_id,ttsp.address,sd.`name` AS depName " +
            " FROM tss_temp_submit_person AS ttsp  " +
            " LEFT JOIN sys_department AS sd ON ttsp.college_id = sd.id " +
            " WHERE ttsp.te_id=#{mainExamId} " +
            " <if test=\"name!=null and name!=\'\' \" >  " +
            " AND ttsp.name like concat('%',#{name},'%')  " +
            " </if> " +
            " <if test=\"sex!=null and sex!=\'\' \" >  " +
            " AND ttsp.sex=#{sex}  " +
            " </if> " +
            " <if test=\"depIds!=null and depIds.size()>0\"> " +
            " AND ttsp.college_id IN " +
            "  <foreach collection='depIds' item='item' open='(' separator=',' close=')'>" +
            "    #{item}" +
            "  </foreach>" +
            " </if> " +
            " <if test=\"idList!=null and idList.size()>0\"> " +
            " AND ttsp.id NOT IN " +
            "  <foreach collection='idList' item='item' open='(' separator=',' close=')'>" +
            "    #{item}" +
            "  </foreach>" +
            " </if> " +
            "</script>")
    List<SubmitPersonDto> selectPersonExcludeIds(@Param("idList") List<String> idList,
                                                 @Param("mainExamId") String mainExamId,
                                                 @Param("depIds") List<Long> depId,
                                                 @Param("sex") String sex,
                                                 @Param("name") String name);

    @Select("<script> " +
            " SELECT ttsp.id,ttsp.`name`,ttsp.pid,ttsp.sex,ttsp.work_code,ttsp.college_id,ttsp.address,sd.`name` AS depName " +
            " FROM tss_temp_submit_person AS ttsp  " +
            " LEFT JOIN sys_department AS sd ON ttsp.college_id = sd.id " +
            " WHERE te_id =#{mainExamId} " +
            " AND ttsp.id IN " +
            "  <foreach collection='idList' item='item' open='(' separator=',' close=')'>" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    List<SubmitPersonDto> selectPersonBatchIds(@Param("idList") List<String> IdList, @Param("mainExamId") String mainExamId);


    @Select("<script> " +
            "SELECT tpl.*,sd.name \"post_name\" " +
            "FROM tss_temp_submit_person AS tpl " +
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
            "AND te_id=#{teId}" +
            "</script>")
    List<TssTempSubmitPerson> seachSubmitPerson(@Param("name") String name,
                                                @Param("workCode") String workCode,
                                                @Param("phone") String phone,
                                                @Param("collegeIdList") List<Long> collegeIdList,
                                                @Param("teId") String teId);

    @Select("<script> " +
            "SELECT tpl.*, sd.name AS dept_name " +
            "FROM tss_temp_submit_person AS tpl " +
            "LEFT JOIN sys_department AS sd ON sd.id=tpl.college_id " +
            "WHERE 1" +
            "<if test=\"collegeIdList!=null and collegeIdList.size()>0 \"> " +
            "AND tpl.college_id IN " +
            "<foreach collection='collegeIdList' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</if> " +
            "AND te_id=#{teId}" +
            "</script>")
    List<TempSubmitPersonVo> seachSubmitPersonByTwoConditions(
            @Param("collegeIdList") List<Long> collegeIdList,
            @Param("teId") String teId);



    @Insert("REPLACE INTO" +
            " tss_submit_person(id,college_id,work_code,`name`,phone,sex,pid,bank,bank_open_code,bank_open,bank_code,category,type,money_type,finance,yes_no,address,te_id) " +
            " SELECT id,college_id,work_code,`name`,phone,sex,pid,bank,bank_open_code,bank_open,bank_code,category,type,money_type,finance,yes_no,address,te_id " +
            " FROM tss_temp_submit_person WHERE te_id=#{teId}")
    int copyToMainTableByTeId(@Param("teId") String mainExId);
}
