package com.byqj.dto;

import com.byqj.entity.TssStudentInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * @Author shark
 * @Description
 * @Date 2019/4/18  1:43
 **/
@Getter
@Setter
@ToString
public class TssSeatScheduleDto extends TssStudentInfo {
    private Integer seatNum;
}
