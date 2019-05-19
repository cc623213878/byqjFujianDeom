package com.byqj.dto;

import com.byqj.entity.TssExam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class TssExamDto extends TssExam {

    // 类型名称
    private String typeName;
    // 考生报名状态，0=未开始，1=进行，2=完成
    private Integer ksbmStatus = 2;
    // 准考证打印状态
    private Integer zkzdyStatus = 2;
    // 岗位征集状态
    private Integer gwzjStatus = 2;
    private List<TssExam> exams;
    private Date postTimeStart;
    private Date postTimeEnd;

}
