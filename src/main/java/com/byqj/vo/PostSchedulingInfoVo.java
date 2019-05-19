package com.byqj.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSchedulingInfoVo {
    private String id; // 人员id
    private String department; // 部门/单位
    private String name; // 姓名
    private String gender;
    @JsonIgnore
    private String examId; //场次的id
    private String examName; // 场次
    @JsonIgnore
    private String examPlaceId; // 考点或考场id
    private String examPlaceName; // 考场名称
    private String examPointName; // 考点名称
    private Integer examPlaceNum; // 考场号
    @JsonIgnore
    private String classId;
    private String className; //教室
    private String postName;//

}
