package com.byqj.dto;

import com.byqj.entity.TssClassInfo;
import com.byqj.entity.TssExamPlace;
import com.byqj.entity.TssPostPerson;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by willim on 2019/3/20.
 */

@Getter
@Setter
public class TssExamPlaceDto extends TssExamPlace {

    @JsonProperty("name")
    private String nameStr;
    private TssClassInfo classInfo;
    private List<TssPostPerson> postPersonList;
    // 已安排人数
    private Integer ordered = 0;
    private List<TssExamPlaceDto> examPlaceList;
}
