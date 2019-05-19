package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SysUser {

    @TableId
    private String userId;
    private String password;
    @TableField("is_locked")
    private Integer locked;
    private String verificationCode;
    private Date verificationCodeGenerateTime;

}
