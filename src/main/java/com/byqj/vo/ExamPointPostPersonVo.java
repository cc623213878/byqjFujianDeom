package com.byqj.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamPointPostPersonVo {
    private String postId; // 岗位id
    private String postName; // 岗位名称
    private Integer postNum; // 岗位数
    private Integer schedulePostNum; // 已经安排的数量
}
