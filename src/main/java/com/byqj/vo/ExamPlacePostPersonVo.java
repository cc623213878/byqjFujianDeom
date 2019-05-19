package com.byqj.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamPlacePostPersonVo {
    private String examPlaceId; // 考场id
    private String examPlaceNum; // 考场号
    private String className; // 教室名称
    private List<PostVo> postInfoList; // 岗位数
    private Integer schedulePostNum; // 已经安排的数量
}
