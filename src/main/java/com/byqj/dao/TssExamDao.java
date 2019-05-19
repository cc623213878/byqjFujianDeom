package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.entity.TssExam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TssExamDao extends BaseMapper<TssExam> {

    @Insert("<script> " +
            " insert into " +
            " tss_exam (id, `name`, type, start_time, end_time, report_start, report_end, parent_id, `level`, status, remark) " +
            " <foreach collection='examList' item='item' separator=','> " +
            "   (#{item.id,jdbcType=VARCHAR}, #{item.name,jdbcType=VARCHAR}, #{item.type,jdbcType=INTEGER}, " +
            "    #{item.startTime,jdbcType=TIMESTAMP}, #{item.endTime,jdbcType=TIMESTAMP}, " +
            "    #{item.reportStart,jdbcType=TIMESTAMP}, #{item.reportEnd,jdbcType=TIMESTAMP}, " +
            "    #{item.parentId,jdbcType=VARCHAR}, #{item.level,jdbcType=VARCHAR}, " +
            "    #{item.status,jdbcType=INTEGER}, #{item.remark,jdbcType=VARCHAR}) " +
            " </foreach> " +
            "</script>")
    void insertBatch(@Param("examList") List<TssExam> exams);

    @Select("<script>"
            + "SELECT * "
            + "FROM tss_exam "
            + "WHERE 1 "
            + "<if test=\"mainExamId!=null and mainExamId!=\'\' \"> "
            + "AND parent_id=#{mainExamId} "
            + "</if> "
            + "<if test=\"scene!=null and scene!=\'\' \"> "
            + "AND name like concat('%', #{scene}, '%') "
            + "</if> "
            + "</script>")
    List<TssExam> getExamScene(
            @Param("mainExamId") String mainExamId,
            @Param("scene") String scene);

}
