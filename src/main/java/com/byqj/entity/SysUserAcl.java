package com.byqj.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysUserAcl {

    @TableId
    private Long id;
    private String userId;
    private String aclCode;

}
