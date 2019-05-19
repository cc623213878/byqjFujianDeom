package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TssTempSubmitPerson {

    @TableId
    private String id;
    private Long collegeId;
    private String workCode;
    private String name;
    private String phone;
    private String sex;
    private String pid;
    private String bank;
    private String bankOpenCode;
    private String bankOpen;
    private String bankCode;
    private String category;
    private String type;
    private String moneyType;
    private String finance;
    private String yesNo;
    private String address;
    private String teId;

}
