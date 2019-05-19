package com.byqj.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AccessRouteDto {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String level;
    @JsonIgnore
    private Integer seq;
    private String name;
    private String path;
    private String component;
    private String componentPath;
    private MetaDto meta = new MetaDto();
    private List<AccessRouteDto> children = Lists.newArrayList();

}
