package com.byqj.vo;

import lombok.Data;

import java.util.List;

@Data
public class ExamListVo {
    private String exId;
    private String exName;
    private List<String> sTime;
    //    private Date eTime;
    private List<String> code;
}
