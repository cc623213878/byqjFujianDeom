package com.byqj.dto;


import com.byqj.entity.TssPersonLibrary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TssPersonLibraryDto extends TssPersonLibrary {
    private String postName; //部门名
    private String bankDescription;
    private String personKindDescription;
    private String personTypeDescription;
    private String moneyDescription;

}
