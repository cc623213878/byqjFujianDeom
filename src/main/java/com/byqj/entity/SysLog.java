package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SysLog {

    @TableId
    private Long id;
    private String userId;
    private String ip;
    private String operationContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operationDateTime;
    private String operationResult;
    private String reason;


}
