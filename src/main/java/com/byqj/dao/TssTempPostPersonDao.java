package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.JobSignInInfoDto;
import com.byqj.dto.PostPersonDto;
import com.byqj.entity.TssTempPostPerson;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TssTempPostPersonDao extends BaseMapper<TssTempPostPerson> {

    @Delete("<script> DELETE FROM tss_temp_post_person WHERE tep_id IN" +
            "  <foreach collection='tepIdList' item='item' open='(' separator=',' close=')'>" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    boolean delByTepIdsInTemp(@Param("tepIdList") List<String> readyDels);



    @Update("<script> " +
            "UPDATE tss_post_person " +
            "SET post_name=#{name},post_free=#{free} " +
            "WHERE " +
            "id IN " +
            "<foreach collection='postPersonIdList' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    int updatePostNameAndFreeById(@Param("name") String name,
                                  @Param("free") Double free,
                                  @Param("postPersonIdList") List<String> postPersonIdList);

    @Select("<script> " +
            "SELECT id " +
            "FROM tss_post_person " +
            "WHERE post_name=#{name} " +
            "</script>")
    List<String> searchPostPersonId(String name);

    @Insert("<script> REPLACE INTO tss_post_person(id,tep_id,post_id,post_name,post_free,person_num,tpi_id_str) " +
            "  SELECT id,tep_id,post_id,post_name,post_free,person_num,tpi_id_str " +
            "  FROM tss_temp_post_person WHERE tep_id IN " +
            "  <foreach collection='tepIdList' item='item' open='(' separator=',' close=')'>" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    int copyToMainTableByTepIds(@Param("tepIdList") List<String> tepIds);

    @Select("SELECT post_name AS job_name,tpi_id_str FROM tss_temp_post_person WHERE tep_id=#{tepId}")
    List<PostPersonDto> getPostNameAndPerson(String tepId);

    /*
     * author lwn
     * description 岗位签到根据考试id查询 考试id->tss_exam中查到场次id 考点code,考试的状态->tss_exam_place中te_id(场次)查询到教室id->教室表中查询到教室名称
     *              kd :考点  kch:考场号 class_name:教室
     * date 2019/4/11 14:50
     * param 考试id
     * return
     */
    @Select("<script>" +
            "SELECT te.name as kscc,tep.te_id,td.description AS kd,tep.name_or_seq AS kch,tep.parent_id,tci.name AS class_name,tep.id AS kckd_id ,te.start_time AS examTime " +
            "FROM tss_exam AS te " +
            "LEFT JOIN  tss_exam_place AS tep ON te.id=tep.te_id  " +
            "LEFT JOIN  tss_class_info AS tci ON tep.tci_id=tci.id " +
            "LEFT JOIN tss_dictionary AS td ON tci.place=td.code " +
            "<where>" +
            "<if test=\"examId!=null and examId!=\'\' \">" +
            "AND te.parent_id=#{examId}" +
            "</if>" +
            "<if test=\"teId !=null and teId!=\'\' \">" +
            "AND tep.te_id=#{teId} " +
            "</if>" +
            "<if test=\"examPointId != null and examPointId!=\'\' \">" +
            "AND tep.id=#{examPointId} OR tep.parent_id=#{examPointId} " +
            "</if>" +
            "<if test=\"kcId != null and kcId!=\'\' \">" +
            "AND tep.name_or_seq=#{kcId} " +
            "</if>" +
            "</where>" +
            "</script>")
    @Results({
            @Result(column = "kckd_id", property = "kckdId"),
            @Result(column = "kckd_id", property = "list", many = @Many(select = "com.byqj.dao.TssTempPostPersonDao.getPostNameAndPerson"))
    })
    List<JobSignInInfoDto> seachSignExamInfo(@Param("examId") String examId, @Param("teId") String teId, @Param("examPointId") String examPointId, @Param("kcId") String kcId);
}
