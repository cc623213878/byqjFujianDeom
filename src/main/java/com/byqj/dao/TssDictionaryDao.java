package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.entity.TssDictionary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TssDictionaryDao extends BaseMapper<TssDictionary> {


    @Select("SELECT code,description From tss_dictionary where type=#{type} ")
    List<Map<String, Object>> getDescription(String type);

    @Select("<script>"
            + "SELECT code "
            + "FROM tss_dictionary "
            + "WHERE 1 "
            + "<if test=\"description!=null and description!=\'\' \"> "
            + "AND description like concat('%', #{description}, '%') "
            + "</if> "
            + "<if test=\"description==null or description==\'\' \"> "
            + "AND type = 'EXAM_POINT' "
            + "</if> "
            + "</script>")
    List<String> getCodeByDescription(@Param("description") String description);

    @Select("SELECT id,description From tss_dictionary where type=#{type} ")
    List<Map<String, Object>> getDescriptionAndId(String type);

    @Select("<script>" +
            "SELECT description " +
            "FROM tss_dictionary " +
            "WHERE 1 " +
            "<if test=\"list!=null and list.size()>0 \"> " +
            "AND id IN " +
            "<foreach collection='list' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</if>" +
            "</script>")
    List<String> getDescriptionById(@Param("list") List<String> list);
}
