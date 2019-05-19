package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.ExamPointClassDto;
import com.byqj.entity.TssExamPlace;
import com.byqj.vo.ExaminationSignReturnVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TssExamPlaceDao extends BaseMapper<TssExamPlace> {

    @Select("SELECT IFNULL(SUM(student_count),0) " +
            "  FROM tss_exam_place " +
            "  WHERE te_id=#{teId} AND tci_id=#{tciId} AND student_count<>-1")
    int getStuCountByTeIdAndTciId(@Param("teId") String examId, @Param("tciId") String classId);

    @Select("<script>"
            + "SELECT tep.id,tci.name "
            + "FROM tss_exam_place AS tep "
            + "LEFT JOIN tss_class_info AS tci ON tep.tci_id=tci.id "
            + "WHERE tep.id IN "
            + "<foreach collection='examPointIds' index='index' item='item' open='(' close=')' separator=','> "
            + "#{item} "
            + "</foreach> "
            + "</script>")
    List<ExamPointClassDto> getMainClassByExamPointIds(@Param("examPointIds") List<String> examPointIds);

    @Select("SELECT IFNULL(MAX(name_or_seq),0) FROM tss_exam_place WHERE te_id=#{teId}")
    int getMaxSeq(@Param("teId") String teId);

    @Update("UPDATE tss_exam_place " +
            "  SET name_or_seq=name_or_seq-1 " +
            "  WHERE name_or_seq>#{nameOrSeq} AND te_id=#{teId} ")
    boolean updateSeqById(@Param("teId") String teId, @Param("nameOrSeq") Integer nameOrSeq);

//    @Select("SELECT tci_id FROM tss_exam_place WHERE id=#{id} OR parent_id=#{id} ")
//    List<String> getOrderedClassId(@Param("id") String exPlaceId);

    @Select("<script>"
            + "SELECT * "
            + "FROM tss_exam_place "
            + "WHERE 1 "
            + "AND parent_id IN "
            + "<foreach collection='ids' index='index' item='item' open='(' close=')' separator=','> "
            + "#{item} "
            + "</foreach> "
            + "<if test=\"examPlaceId!=null and examPlaceId!=\'\' \"> "
            + "AND name_or_seq like concat('%', #{examPlaceId}, '%') "
            + "</if> "
            + "</script>")
    List<TssExamPlace> getExamPlace(
            @Param("ids") List<String> ids,
            @Param("examPlaceId") Integer examPlaceId);

    /**
     * 查询考点
     *
     * @param ids
     * @param examPoint
     * @return
     */
    @Select("<script>"
            + "SELECT * "
            + "FROM tss_exam_place "
            + "WHERE 1 "
            + "AND parent_id IN "
            + "<foreach collection='ids' index='index' item='item' open='(' close=')' separator=','> "
            + "#{item} "
            + "</foreach> "
            + "AND name_or_seq IN "
            + "<foreach collection='examPoint' index='index' item='item' open='(' close=')' separator=','> "
            + "#{item} "
            + "</foreach> "
            + "</script>")
    List<TssExamPlace> getExamPoint(
            @Param("ids") List<String> ids,
            @Param("examPoint") List<String> examPoint);

    /**
     * gys
     * @param account
     * @param password
     * @return
     */
    @Select("<script>"
            + "SELECT tep.id,te.name,te.start_time,te.end_time,tci.name \'className\' ,tep.parent_id "
            + "FROM tss_exam_place AS tep "
            + "LEFT JOIN tss_exam AS te ON tep.te_id=te.id "
            + "LEFT JOIN tss_class_info AS tci ON tep.tci_id=tci.id "
            + "WHERE tep.id =#{account} "
            + "AND tep.name_or_seq =#{password} "
            + "</script>")
    ExaminationSignReturnVo getExamMessageByExamPlaceId(
            @Param("account") String account,
            @Param("password") Integer password);

    /**
     * gys
     * @param parentId
     * @return
     */
    @Select("<script>"
            + "SELECT td.description "
            + "FROM  tss_exam_place AS tep "
            + "LEFT JOIN tss_dictionary AS td ON tep.name_or_seq = td.code "
            + "WHERE tep.id =#{parentId} "
            + "</script>")
    String getExamPointNameByExamPlaceParentId(String parentId);


}
