package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TssTempSeatSchedule {

    @TableId
    private String id;
    private String xymc;
    private String xm;
    private String bkjb;
    private String teId;
    private String tepId;
    private Integer seatNum;

}
