package com.byqj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*
 * @Author shark
 * @Date 2019/4/13  1:11
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostNoticeListVo {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date examTime;//考试日期

    private String teId;//场次id
    private String personId;//人员id
    private String kckdId;//考场考点id
    private int order;//序号
    private String postName; //部门名称
    private String workCode; //工号
    private String name;//姓名
    private String phone;//电话
    private String sex;//性别
    private String pid;//身份证
    private String kscc;//考试场次
    private String kd; //考点名称
    private Integer kch;//考场号
    private String className;//教室名称
    private String jobName; //岗位名称
    private Date signTime;//签到时间


}
