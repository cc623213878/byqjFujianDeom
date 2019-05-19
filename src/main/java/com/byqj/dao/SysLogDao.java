package com.byqj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byqj.dto.SysLogDto;
import com.byqj.entity.SysLog;
import com.byqj.vo.LogSearchVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysLogDao extends BaseMapper<SysLog> {

	@Insert("INSERT INTO admin_log "
			+ "("
			+ "username,"
			+ "ip,operation_content,operation_date_time,operation_result,"
			+ "reason"
			+ ")VALUES("
			+ "#{username},"
			+ "#{ip},#{operationContent},#{operationDateTime},#{operationResult},"
			+ "#{reason}"
			+ ")")
	void addAdminOperationLog(SysLog sysLog);

    @Select("select * from operation_log id by operation_date_time desc")
	List<SysLog> getOperationLogList();

    @Select("<script>"
            + "SELECT sl.id, sl.user_id, sl.ip, sl.operation_content, sl.operation_date_time, sl.operation_result,"
            + " sl.reason, sad.user_name "
            + "FROM sys_log AS sl "
            + "LEFT JOIN sys_admin_detail AS sad ON sad.user_id=sl.user_id "
            + "WHERE 1=1 "
            + "AND (sl.operation_date_time BETWEEN #{beginTime} AND #{endTime} )"
            + "<if test=\"operationResult!=null and operationResult!=\'\' \"> "
            + "AND sl.operation_result like concat('%', #{operationResult}, '%') "
            + "</if> "
            + "<if test=\"userName!=null and userName!=\'\' \"> "
            + "AND sad.user_name like concat('%', #{userName}, '%') "
            + "</if> "
            + "ORDER BY sl.operation_date_time DESC "
            + "</script>")
    List<SysLogDto> searchSelective(LogSearchVo logSearchVo);

    //	@Select("<script>"
//			+ "SELECT user_id, ip, operation_content, operation_date_time, operation_result, reason FROM sys_log "
//			+ "WHERE 1=1 "
//			+ "AND (operation_date_time BETWEEN #{startTime} AND #{endTime} )"
//			+ "<if test=\"userId!=null and userId!=\'\' \"> "
//			+ "AND user_id like concat('%', #{userId}, '%') "
//			+ "</if> "
//			+ "<if test=\"ip!=null and ip!=\'\' \"> "
//			+ "AND ip like concat('%', #{ip}, '%') "
//			+ "</if> "
//			+ "<if test=\"result!=null and result!=\'\' \"> "
//			+ "AND operation_result like concat('%', #{result}, '%') "
//			+ "</if> "
//			+ "<if test=\"reason!=null and reason!=\'\' \"> "
//			+ "AND reason like concat('%', #{reason}, '%') "
//			+ "</if> "
//			+ "<if test=\"content!=null and content!=\'\' \"> "
//			+ "AND operation_content like concat('%', #{content}, '%') "
//			+ "</if> "
//			+ "ORDER BY operation_date_time desc"
//			+ "</script>")
//	List<SysLog> searchSelective(SysLogSearchVo logSearchVo);

	@Insert("<script> " +
			"  insert into sys_log " +
			"    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" > " +
			"      <if test=\"id != null\" > " +
			"        id, " +
			"      </if> " +
			"      <if test=\"userId != null\" > " +
			"        user_id, " +
			"      </if> " +
			"      <if test=\"ip != null\" > " +
			"        ip, " +
			"      </if> " +
			"      <if test=\"operationContent != null\" > " +
			"        operation_content, " +
			"      </if> " +
			"      <if test=\"operationDateTime != null\" > " +
			"        operation_date_time, " +
			"      </if> " +
			"      <if test=\"operationResult != null\" > " +
			"        operation_result, " +
			"      </if> " +
			"      <if test=\"reason != null\" > " +
			"        reason, " +
			"      </if> " +
			"    </trim> " +
			"    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" > " +
			"      <if test=\"id != null\" > " +
			"        #{id,jdbcType=BIGINT}, " +
			"      </if> " +
			"      <if test=\"userId != null\" > " +
			"        #{userId,jdbcType=VARCHAR}, " +
			"      </if> " +
			"      <if test=\"ip != null\" > " +
			"        #{ip,jdbcType=VARCHAR}, " +
			"      </if> " +
			"      <if test=\"operationContent != null\" > " +
			"        #{operationContent,jdbcType=VARCHAR}, " +
			"      </if> " +
			"      <if test=\"operationDateTime != null\" > " +
			"        #{operationDateTime,jdbcType=TIMESTAMP}, " +
			"      </if> " +
			"      <if test=\"operationResult != null\" > " +
			"        #{operationResult,jdbcType=VARCHAR}, " +
			"      </if> " +
			"      <if test=\"reason != null\" > " +
			"        #{reason,jdbcType=VARCHAR}, " +
			"      </if> " +
			"    </trim>" +
			"</script>")
	void insertSelective(SysLog sysLog);
}