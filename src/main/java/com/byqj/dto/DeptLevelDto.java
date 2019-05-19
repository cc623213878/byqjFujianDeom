package com.byqj.dto;

import com.byqj.entity.SysDepartment;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class DeptLevelDto extends SysDepartment {
    private List<DeptLevelDto> deptList = Lists.newArrayList();

}
