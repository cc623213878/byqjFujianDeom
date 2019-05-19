package com.byqj.dto;

import com.byqj.entity.SysLog;
import lombok.Getter;
import lombok.Setter;

/*
 * @Author shark
 * @Description
 * @Date 2019/4/9  11:17
 **/
@Getter
@Setter
public class SysLogDto extends SysLog {

    private String userName;

}
