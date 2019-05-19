package com.byqj.vo;

import com.byqj.entity.TssClassInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName:TssClassInfoVo
 * @Description: 前台展示考室信息
 * @Author:lwn
 * @Date:2019/3/19 10:25
 **/
@Getter
@Setter
@ToString
public class TssClassInfoVo extends TssClassInfo {
    private String placeDescription;
    private String TypeDescription;

}
