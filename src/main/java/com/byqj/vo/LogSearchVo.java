package com.byqj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/*
 * @Author shark
 * @Date 2019/4/8  13:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogSearchVo {
    private String userName;
    private String operationResult;
    private String beginTime = "0000-00-00 00:00:00";
    private String endTime = "2099-12-30 00:00:00";

    public void setBeginTime(String beginTime) {
        this.beginTime = StringUtils.isBlank(beginTime) ? "0000-00-00 00:00:00" : beginTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = StringUtils.isBlank(endTime) ? "2099-12-30 00:00:00" : endTime;
    }
}
