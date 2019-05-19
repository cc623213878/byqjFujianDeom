package com.byqj.dto;


import com.byqj.entity.SysAdminDetail;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysAdminSelectDetailDto extends SysAdminDetail {
    private String name;
}
