package com.byqj.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.entity.TssGradeSubjects;
import com.byqj.entity.TssImportGrade;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TssImportGradeDao extends BaseMapper<TssImportGrade> {
    @Delete("<script>" +
            "DELETE FROM tss_import_grade WHERE" +
            "<foreach collection='list' index='index' item='item'  separator='OR'> " +
            "( bskmmc= #{item.kmmc} AND kssjhcs=#{item.kssjhcs})" +
            "</foreach> " +
            "</script>")
    int deleteByNameAndTimes(@Param("list") List<TssGradeSubjects> list);
}
