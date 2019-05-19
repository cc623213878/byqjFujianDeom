package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class TssExamPlace {

    @TableId
    private String id = "";
    private Integer nameOrSeq = 0;
    private String teId = "";
    private String tciId = "";
    @JsonIgnore
    private String parentId = "0";
    @JsonIgnore
    private String level = "0";
    private Integer studentCount = 0;
    @JsonIgnore
    private Date createTime;

}
