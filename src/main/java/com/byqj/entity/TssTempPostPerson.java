package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TssTempPostPerson {

    @TableId
    private String id = "";
    private String tepId = "";
    private String postId = "";
    private String postName = "";
    private Double postFree = 0.0D;
    private Integer personNum = 0;
    private String tpiIdStr = "";

}
