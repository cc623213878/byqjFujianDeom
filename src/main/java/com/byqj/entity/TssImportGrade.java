package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TssImportGrade {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String xm;
    private String zjhm;
    private String xh;
    private String ssxx;
    private String xl;
    private String xz;
    private String nz;
    private String yx;
    private String zy;
    private String bj;
    private String kssjhcs;
    private String bszkzh;
    private String bskmmc;
    private String cjdh;
    private String zf;
    private String cj1;
    private String cj2;
    private String cj3;
    private String cj4;
    private String bz;
    private Long examId;

}
