package com.byqj.vo;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TempSubmitPersonVo extends BaseRowModel {
    @Excel(name = "序号", orderNum = "0")
    private Integer order;
    @Excel(name = "部门", orderNum = "1")
    private String deptName;
    @Excel(name = "工号", orderNum = "2")
    private String workCode;
    @Excel(name = "姓名", orderNum = "3")
    private String name;
    @Excel(name = "联系方式", orderNum = "4")
    private String phone;
    @Excel(name = "性别", orderNum = "5")
    private String sex;
    @Excel(name = "身份证", orderNum = "6")
    private String pid;
    @Excel(name = "银行", orderNum = "7")
    private String bank;
    @Excel(name = "开户行名称", orderNum = "8")
    private String bankOpen;
    @Excel(name = "银行卡卡号", orderNum = "9")
    private String bankCode;
    @Excel(name = "人员类别", orderNum = "10")
    private String category;
    @Excel(name = "考点", orderNum = "11")
    private String type;
    @ExcelIgnore
    private String address; //选择校区
    @ExcelIgnore
    private Long collegeId;

}
