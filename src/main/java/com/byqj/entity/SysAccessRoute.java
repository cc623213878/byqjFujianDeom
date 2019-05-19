package com.byqj.entity;

import lombok.Data;

@Data
public class SysAccessRoute {
    private Integer id;
    private Integer aclModuleId;
    private String component;
    private String componentPath;
    boolean cache;
}
