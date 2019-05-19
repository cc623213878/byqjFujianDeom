package com.byqj.dto;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TssStudentInfoDto extends BaseRowModel {
    @ExcelProperty(value = "姓名", index = 1)
    private String xm;
    @ExcelProperty(value = "证件号码", index = 2)
    private String zjhm;
    @ExcelProperty(value = "学号", index = 3)
    private String xh;
    @ExcelProperty(value = "所属学校", index = 4)
    private String ssxx;
    @ExcelProperty(value = "学历", index = 5)
    private String xl;
    @ExcelProperty(value = "学制", index = 6)
    private String xz;
    @ExcelProperty(value = "年级", index = 7)
    private String nz;
    @ExcelProperty(value = "院系", index = 8)
    private String yx;
    @ExcelProperty(value = "专业", index = 9)
    private String zy;
    @ExcelProperty(value = "班级", index = 10)
    private String bj;
    @ExcelProperty(value = "考试时间或次数", index = 11)
    private String kssjhcs;
    @ExcelProperty(value = "笔试准考证号", index = 12)
    private String bszkzh;
    @ExcelProperty(value = "笔试科目名称", index = 13)
    private String bskmmc;
    @ExcelProperty(value = "成绩单号", index = 14)
    private String cjdh;
    @ExcelProperty(value = "备注", index = 15)
    private String bz;
}
