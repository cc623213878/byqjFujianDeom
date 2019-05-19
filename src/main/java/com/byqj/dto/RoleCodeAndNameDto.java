package com.byqj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName:RoleCodeAndNameDto
 * @Description: 人员管理模块显示角色
 * @Author:lwn
 * @Date:2019/3/6 16:06
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RoleCodeAndNameDto {
    private String roleCode;
    private String roleName;
}
