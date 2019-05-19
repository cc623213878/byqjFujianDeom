package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TssStudentInfo {

    @TableId
    private String userId;
    private String teId;
    private String xm;
    private String zjhm;
    private String xh;
    private String ssxx;
    private String xl;
    private Integer xz = 0;
    private String nz;
    private String yx;
    private String zy;
    private String bj;
    private String kssjhcs;
    private String bszkzh;
    private String bskmmc;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

}
