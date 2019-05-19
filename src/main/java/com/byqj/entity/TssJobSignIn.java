package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class TssJobSignIn {
    @TableId
    Long id;//自增id
    private String teId;//场次id
    private String userId;//用户id
    private Date signTime;//签到时间

}
