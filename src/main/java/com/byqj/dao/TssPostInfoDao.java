package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.entity.TssPostInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TssPostInfoDao extends BaseMapper<TssPostInfo> {

    @Select("<script>" +
            "SELECT name " +
            "FROM tss_post_info " +
            "WHERE 1 " +
            "<if test=\"list!=null and list.size()>0 \"> " +
            "AND id IN " +
            "<foreach collection='list' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</if>" +
            "</script>")
    List<String> getPostNameById(@Param("list") List<String> list);
}
