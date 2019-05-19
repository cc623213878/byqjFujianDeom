package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysAclModule {

    @TableId
    private Long id;
    private String code;
    private String name;
    private Long parentId;
    private String level;
    private Integer type;
    private Integer seq;
    private Integer status;
    private String remark;
    private String routeName;
    private String icon;
    private String componentPath;
    private String component;
    private boolean cache;
    private String path;


}
