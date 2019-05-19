package com.byqj.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccessMenuDto {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String level;
    @JsonIgnore
    private Integer seq;
    private String title;
    private String path;
    private String icon;
    private List<AccessMenuDto> children = Lists.newArrayList();

}
