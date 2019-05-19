package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SysDepartment {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long parentId;
    private String level;
    private Integer seq;
    private Integer type;
    private String remark;
    //注解解决date类型传给前端不为时间的问题
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}
