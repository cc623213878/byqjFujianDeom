package com.byqj.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonFreeVo extends BaseRowModel {

    private String id; // 人员id
    @ExcelProperty(value = "部门/单位", index = 0)
    private String department; // 部门/单位
    @ExcelProperty(value = "工号", index = 1)
    private String num; // 工号
    @ExcelProperty(value = "姓名", index = 2)
    private String name; // 姓名
    @Builder.Default
    @ExcelProperty(value = "总费用", index = 3)
    private Double free = 0.0D; // 总费用
}
