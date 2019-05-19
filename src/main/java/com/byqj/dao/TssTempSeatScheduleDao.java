package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.TssSeatScheduleDto;
import com.byqj.entity.TssTempSeatSchedule;
import com.byqj.vo.StudentSignTableVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TssTempSeatScheduleDao extends BaseMapper<TssTempSeatSchedule> {

    @Update("<script> UPDATE tss_temp_seat_schedule SET tep_id='',seat_num=0  WHERE tep_id IN" +
            "  <foreach collection='tepIdList' item='item' open='(' separator=',' close=')'>" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    boolean resetTepIdAndSeatNumInTemp(@Param("tepIdList") List<String> readyDels);

    @Insert("REPLACE INTO tss_seat_schedule(id,xymc,xm,bkjb,te_id,tep_id,seat_num) " +
            " SELECT id,xymc,xm,bkjb,te_id,tep_id,seat_num" +
            " FROM tss_temp_seat_schedule WHERE te_id=#{teId}")
    int copyToMainTableByTeId(@Param("teId") String mainExId);

    @Select("<script>"
            + "SELECT * "
            + "FROM tss_temp_seat_schedule "
            + "WHERE 1 "
            + "<if test=\"name!=null and name!=\'\' \"> "
            + "AND xm like concat('%', #{name}, '%') "
            + "</if> "
            + "<if test=\"ids!=null and ids.size()>0 \"> "
            + "AND tep_id IN "
            + "<foreach collection='ids' index='index' item='item' open='(' close=')' separator=','> "
            + "#{item} "
            + "</foreach> "
            + "</if> "
            + "<if test=\"ids==null or ids.size()==0 \"> "
            + "AND id is null "
            + "</if> "
            + "ORDER BY tep_id "
            + "</script>")
    List<StudentSignTableVo> getSeatSchedule(
            @Param("ids") List<String> ids,
            @Param("name") String name);

    @Select("<script>"
            + "SELECT ttss.seat_num,tsi.* "
            + "FROM tss_temp_seat_schedule AS ttss "
            + "LEFT JOIN tss_student_info AS tsi ON tsi.user_id = ttss.id "
            + "WHERE ttss.tep_id = #{examPlaceId} "
            + "</script>")
    List<TssSeatScheduleDto> getStudentSignMessageByExamPlaceId(String examPlaceId);

    @Select("<script>"
            + "SELECT ttss.seat_num,tsi.* "
            + "FROM tss_temp_seat_schedule AS ttss "
            + "LEFT JOIN tss_student_info AS tsi ON tsi.user_id = ttss.id "
            + "WHERE ttss.tep_id = #{examPlaceId} "
            + "AND tsi.bszkzh = #{zkzh} "
            + "</script>")
    TssTempSeatSchedule getStudentMessageByExamPlaceIdAndZkzh(@Param("examPlaceId") String examPlaceId,
                                                              @Param("zkzh") String zkzh);
}
