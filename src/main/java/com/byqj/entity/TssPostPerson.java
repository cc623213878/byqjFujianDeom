package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class TssPostPerson {

    @TableId
    private String id = "";
    private String tepId = "";
    @JsonIgnore
    private String postId = "";
    private String postName = "";
    private Double postFree = 0.0D;
    private Integer personNum = 0;
    @JsonIgnore
    private String tpiIdStr = "";

}
