package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class TssExam {

    @TableId
    private String id;
    private String name;
    private Integer type;
    private Date startTime;
    private Date endTime;
    private Integer reportStart = 0;
    private Integer reportEnd = 0;
    @JsonIgnore
    private String parentId = "0";
    @JsonIgnore
    private String level = "0";
    private String remark = "";
    private Integer status = 0;
    private String placeCodeStr = "";

}
