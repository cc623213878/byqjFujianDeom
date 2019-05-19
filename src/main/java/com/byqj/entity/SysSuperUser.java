package com.byqj.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysSuperUser {

    @TableId
    private String userId;

}
