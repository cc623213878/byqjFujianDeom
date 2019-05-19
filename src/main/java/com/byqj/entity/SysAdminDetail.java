package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SysAdminDetail {

    @TableId
    private String userId;
    private String userName;
    private String realName;
    private String email;
    private String phone;
    private Integer sex;
    private String post;
    private Date createTime;
    private Date updateTime;
    private Integer loginType;
    private Long deptId;
    private String idCard;


}
