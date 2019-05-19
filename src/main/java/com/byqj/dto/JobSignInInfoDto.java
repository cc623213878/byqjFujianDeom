package com.byqj.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * author lwn
 * description  岗位签到
 * date 2019/4/11 20:38
 * param
 * return
 */
@Getter
@Setter
public class JobSignInInfoDto {
    private String kscc;//考试场次
    private String teId; //场次id
    private Date examTime;//考试日期
    private String kd; //考点名称
    private String kch;//考场号
    private String className;//教室名称
    private String kckdId;//考场考点id
    private List<PostPersonDto> list; //根据考场考点id查询到该场的岗位信息
    private String parentId;
}
