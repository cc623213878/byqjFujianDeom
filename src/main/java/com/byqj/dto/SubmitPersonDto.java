package com.byqj.dto;


import com.byqj.entity.TssTempSubmitPerson;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SubmitPersonDto extends TssTempSubmitPerson {
    private String depName; // 部门名称
}
