package com.byqj.vo;

/*
 * @Author shark
 * @Date 2019/4/12  1:52
 **/

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class StudentSignTableVo extends BaseRowModel {

    private String id;
    private String teId;
    @ExcelProperty(value = "姓名", index = 0)
    private String xm;
    @ExcelProperty(value = "证件号", index = 1)
    private String zjhm;
    @ExcelProperty(value = "学号", index = 2)
    private String xh;
    @ExcelProperty(value = "所属学校", index = 3)
    private String ssxx;
    @ExcelProperty(value = "学历", index = 4)
    private String xl;
    @ExcelProperty(value = "学制", index = 5)
    private Integer xz;
    @ExcelProperty(value = "年级", index = 6)
    private String nz;
    @ExcelProperty(value = "院系", index = 7)
    private String yx;
    @ExcelProperty(value = "专业", index = 8)
    private String zy;
    @ExcelProperty(value = "班级", index = 9)
    private String bj;
    @ExcelProperty(value = "考试时间或次数", index = 10)
    private String kssjhcs;
    @ExcelProperty(value = "笔试准考证号", index = 11)
    private String bszkzh;

    @ExcelProperty(value = "考试场次", index = 12)
    private String sceneName; //场次的id

    @ExcelProperty(value = "考点", index = 13)
    private String examPointName; // 考点名称
    @ExcelProperty(value = "考场号", index = 14)
    private String tepId; // 考点或考场id
    private String examPlaceName; // 考场名

    @JsonIgnore
    private String classId;
    @ExcelProperty(value = "教室", index = 15)
    private String className; //教室

    @ExcelProperty(value = "座位号", index = 16)
    private Integer seatNum;//座位号

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelProperty(value = "是否签到", index = 17)
    private Date time;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime; // 场次时间
    private Integer type; // 考试类型
    private String bskmmc; // 笔试科目名称

}
