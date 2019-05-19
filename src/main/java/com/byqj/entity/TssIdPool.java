package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TssIdPool {

    @TableId
    private String teId="";
    private String teStartTime="";
    private Integer examNum=0;
    private Integer count=1;


}
