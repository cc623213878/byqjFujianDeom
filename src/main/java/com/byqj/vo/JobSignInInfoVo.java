package com.byqj.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JobSignInInfoVo extends BaseRowModel {
    // TODO: 2019/4/12 考试场次字段
    private String parentId;
    private String teId;//场次id
    private String personId;//人员id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date examTime;//考试日期
    private String kckdId;//考场考点id
    @ExcelProperty(value = "序号", index = 0)
    private int order;//序号
    @ExcelProperty(value = "部门", index = 1)
    private String postName; //部门名称
    @ExcelProperty(value = "工号学号", index = 2)
    private String workCode; //工号
    @ExcelProperty(value = "姓名", index = 3)
    private String name;//姓名
    @ExcelProperty(value = "电话", index = 4)
    private String phone;//电话
    @ExcelProperty(value = "性别", index = 5)
    private String sex;//性别
    @ExcelProperty(value = "身份证", index = 6)
    private String pid;//身份证
    @ExcelProperty(value = "考试场次", index = 7)
    private String kscc;//考试场次
    @ExcelProperty(value = "考点", index = 8)
    private String kd; //考点名称
    @ExcelProperty(value = "考场号", index = 9)
    private String kch;//考场号
    @ExcelProperty(value = "教室", index = 10)
    private String className;//教室名称
    @ExcelProperty(value = "岗位名称", index = 11)
    private String jobName; //岗位名称
    @ExcelProperty(value = "签到", index = 12)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signTime;//签到时间


}
