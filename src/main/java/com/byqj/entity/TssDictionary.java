package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TssDictionary {

    @TableId
    private Long id;
    private String type;
    private Integer code;
    private String description;
    private String remark;

}
