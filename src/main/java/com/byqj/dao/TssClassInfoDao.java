package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.entity.TssClassInfo;
import com.byqj.vo.TssClassInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TssClassInfoDao extends BaseMapper<TssClassInfo> {
    @Select("<script> "
            + "SELECT *"
            + " FROM tss_class_info "
            + "WHERE 1"
            + "<if test=\"name!=null and name!=\'\' \"> "
            + "AND name like concat('%',#{name},'%') "
            + "</if> "
            + "<if test=\"place!=null\"> "
            + "AND place=#{place} "
            + "</if> "
            + "<if test=\"type!=null\"> "
            + "AND type=#{type} "
            + "</if> "
            + "<if test=\"status!=null\"> "
            + "AND status=#{status} "
            + "</if> "
            + "order by name "
            + "</script> "
    )
    List<TssClassInfoVo> seachByCondition(TssClassInfo tssClassInfo);

    @Select("SELECT capacity FROM tss_class_info WHERE id=#{id}")
    int getCapacityById(@Param("id") String classId);
}
