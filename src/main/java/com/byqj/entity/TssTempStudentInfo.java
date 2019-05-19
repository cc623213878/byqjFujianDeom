package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TssTempStudentInfo {

    @TableId
    private String userId;
    private String teId;
    private String xm;
    private String zjhm;
    private String xh;
    private String ssxx;
    private String xl;
    private Integer xz;
    private String nz;
    private String yx;
    private String zy;
    private String bj;
    private String kssjhcs;
    private String bszkzh;
    private String bskmmc;

}
