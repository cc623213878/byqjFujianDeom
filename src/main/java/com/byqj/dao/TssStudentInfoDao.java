package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.entity.TssStudentInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TssStudentInfoDao extends BaseMapper<TssStudentInfo> {

    @Select("<script> " +
            "SELECT * " +
            "FROM tss_student_info " +
            "where 1 " +
            "AND user_id IN " +
            "<foreach collection='userIdList' index='index' item='item' open='(' close=')' separator=','> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    List<TssStudentInfo> searchStudentInfo(@Param("userIdList") List<String> userIdList);

    /**
     * gys
     */
    @Update("UPDATE tss_student_info SET time=NOW() WHERE bszkzh=#{zkzh} AND te_id=#{examId} ")
    boolean updateTimeByZkzh(@Param("zkzh") String zkzh,
                             @Param("examId") String examId);

}
