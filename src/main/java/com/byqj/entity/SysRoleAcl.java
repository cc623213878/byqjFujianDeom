package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysRoleAcl {

    @TableId
    private String roleCode;
    private String aclCode;

}
