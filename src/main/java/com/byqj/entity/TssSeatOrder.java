package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TssSeatOrder {

    @TableId
    private String tepId;
    private String tciId;
    private String seatOrder;

}
