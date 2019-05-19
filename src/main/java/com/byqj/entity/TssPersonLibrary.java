package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TssPersonLibrary {

    @TableId
    private String id;
    private Long collegeId;
    private String workCode;
    private String name;
    private String phone;
    private String sex;
    private String pid;
    private Long bank;
    private String bankOpenCode;
    private String bankOpen;
    private String bankCode;
    private Long category;
    private Long type;
    private Long moneyType;
    private String finance;
    private String yesNo;
    private String address;


}
