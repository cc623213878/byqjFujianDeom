package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.SubmitPersonDto;
import com.byqj.entity.TssSubmitPerson;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by willim on 2019/3/22.
 */
@Repository
public interface TssSubmitPersonDao extends BaseMapper<TssSubmitPerson> {

    @Update("<script> UPDATE tss_temp_submit_person SET tep_id='' WHERE tep_id IN" +
            "  <foreach collection='tepIdList' item='item' open='(' separator=',' close=')'>" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    boolean resetTepIdByTepIdsInTemp(@Param("tepIdList") List<String> readyDels);


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
            " FROM tss_submit_person AS ttsp  " +
            " LEFT JOIN sys_department AS sd ON ttsp.college_id = sd.id " +
            " WHERE ttsp.id IN " +
            "  <foreach collection='idList' item='item' open='(' separator=',' close=')'>" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    List<SubmitPersonDto> selectPersonBatchIds(@Param("idList") List<String> IdList);
}
