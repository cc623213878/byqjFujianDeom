package com.byqj.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysAcl {

    @TableId
    private String code;
    private Long id;
    private String name;
    private Long aclModuleId;
    private String url;
    private String functionNo;
    private Integer type;
    private Integer status;
    private Integer seq;
    private String remark;

}
