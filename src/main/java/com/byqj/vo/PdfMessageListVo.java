package com.byqj.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Author shark
 * @Date 2019/4/2  16:48
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PdfMessageListVo {
    private String name;
    private String content;
    private int type = 0;
}
