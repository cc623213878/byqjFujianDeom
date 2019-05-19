package com.byqj.dto;

import com.byqj.entity.SysAcl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by czx on 2018/10/21.
 */
@Getter
@Setter
@ToString
public class AclDto extends SysAcl {
    // 是否要默认选中
    private boolean checked = false;

    // 是否有权限操作
    private boolean hasAcl = false;

}
