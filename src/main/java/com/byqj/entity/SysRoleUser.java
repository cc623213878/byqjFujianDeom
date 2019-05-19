package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysRoleUser {

    @TableId
    private Long id;
    private String userId;
    private String roleCode;


}
