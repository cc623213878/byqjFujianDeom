package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TssClassInfo {

    @TableId
    private String id;
    private String name;
    private Integer place;
    private Integer capacity;
    private Integer type;
    // @JsonIgnore
    private Integer status;
    private String remark;

}
