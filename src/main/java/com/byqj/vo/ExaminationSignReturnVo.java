package com.byqj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/*
 * @Author shark
 * @Description
 * @Date 2019/4/17  21:49
 **/
@Getter
@Setter
@ToString
public class ExaminationSignReturnVo {

    private String id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private String className;
    private String parentId;
    private String examPointName;

}
