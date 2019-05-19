package com.byqj.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SysRole {

    @TableId
    private String roleCode;
    private String roleName;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String operator;


}
