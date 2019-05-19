package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TssPostInfo {

    @TableId
    private String id;
    private String name;
    private Double free;
    private String remark;

}
