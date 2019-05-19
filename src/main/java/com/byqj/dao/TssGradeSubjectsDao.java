package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.GradeConditionDto;
import com.byqj.entity.TssGradeSubjects;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TssGradeSubjectsDao extends BaseMapper<TssGradeSubjects> {

    @Select("<script>"
            + "SELECT DISTINCT kmmc "
            + "FROM tss_grade_subjects "
            + "</script>")
    @Results({
            @Result(column = "kmmc", property = "kmmc"),
            @Result(column = "kmmc", property = "kssjhcsList", javaType = List.class,
                    many = @Many(select = "com.byqj.dao.TssGradeSubjectsDao.selectGradeKssjhcs"))
    })
    List<GradeConditionDto> selectGradeCondition();

    @Select("<script>"
            + "SELECT kssjhcs  "
            + "FROM tss_grade_subjects "
            + "WHERE kmmc=#{kmmc}"
            + "</script>")
    List<String> selectGradeKssjhcs(@Param("kmmc") String kmmc);

    @Select("<script>"
            + "SELECT id,kmmc,kssjhcs  "
            + "FROM tss_grade_subjects "
            + "WHERE 1"
            + "<if test=\"kmmc!=null and kmmc!=\'\' \"> "
            + "AND kmmc = #{kmmc} "
            + "</if> "
            + "<if test=\"kssjhcs!=null and kssjhcs!=\'\' \"> "
            + "AND kssjhcs = #{kssjhcs} "
            + "</if> "
            + "</script>")
    List<TssGradeSubjects> selectGrade(@Param("kmmc") String kmmc, @Param("kssjhcs") String kssjhcs);

    @Delete("<script>" +
            "DELETE FROM tss_grade_subjects WHERE" +
            "<foreach collection='list' index='index' item='item'  separator='OR'> " +
            "( kmmc= #{item.kmmc} AND kssjhcs=#{item.kssjhcs})" +
            "</foreach> " +
            "</script>")
    int deleteByNameAndTimes(@Param("list") List<TssGradeSubjects> list);
}
