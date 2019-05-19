package com.byqj.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.byqj.entity.SysAdminDetail;
import com.byqj.entity.SysRole;
import com.byqj.security.rbac.enums.UserStateEnum;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysAdminDetailDto extends SysAdminDetail {

    @TableField("is_locked")
    private UserStateEnum locked;
    private String name;
    private String roleCode;
    private List<SysRole> sysRoleList;

}
