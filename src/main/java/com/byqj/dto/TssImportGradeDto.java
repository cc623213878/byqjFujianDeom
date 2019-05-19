package com.byqj.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TssImportGradeDto extends BaseRowModel {
    @ExcelProperty(index = 1)
    private String xm;
    @ExcelProperty(index = 2)
    private String zjhm;
    @ExcelProperty(index = 3)
    private String xh;
    @ExcelProperty(index = 4)
    private String ssxx;
    @ExcelProperty(index = 5)
    private String xl;
    @ExcelProperty(index = 6)
    private String xz;
    @ExcelProperty(index = 7)
    private String nz;
    @ExcelProperty(index = 8)
    private String yx;
    @ExcelProperty(index = 9)
    private String zy;
    @ExcelProperty(index = 10)
    private String bj;
    @ExcelProperty(index = 11)
    private String kssjhcs;
    @ExcelProperty(index = 12)
    private String bszkzh;
    @ExcelProperty(index = 13)
    private String bskmmc;
    @ExcelProperty(index = 14)
    private String cjdh;
    @ExcelProperty(index = 15)
    private String zf;
    @ExcelProperty(index = 16)
    private String cj1;
    @ExcelProperty(index = 17)
    private String cj2;
    @ExcelProperty(index = 18)
    private String cj3;
    @ExcelProperty(index = 19)
    private String cj4;
    @ExcelProperty(index = 20)
    private String bz;
}
