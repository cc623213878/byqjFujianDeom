package com.byqj.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by willim on 2019/4/1.
 * 账号导出类
 */

@Getter
@Setter
public class AccountVo extends BaseRowModel {

    @ExcelProperty(value = "考试名称", index = 0)
    private String exName;
    @ExcelProperty(value = "考试地点", index = 1)
    private String place;
    @ExcelProperty(value = "考场号", index = 2)
    private String number;
    @ExcelProperty(value = "签到时间", index = 3)
    private String reportTime;
    @ExcelProperty(value = "账号", index = 4)
    private String username;
    @ExcelProperty(value = "密码", index = 5)
    private String password;
}
