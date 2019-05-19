package com.byqj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.byqj.utils.CommonUtil;
import lombok.Data;

import java.util.Date;

@Data
public class TssSolicitationTime {

    @TableId
    private String teId;
    private Date postTimeStart = CommonUtil.getMaxTime();
    private Date postTimeEnd = CommonUtil.getMaxTime();
    private Date studentTimeStart = CommonUtil.getMaxTime();
    private Date studentTimeEnd = CommonUtil.getMaxTime();
    private Date admissionTicketTimeStart = CommonUtil.getMaxTime();
    private Date admissionTicketTimeEnd = CommonUtil.getMaxTime();
    private Long deptId = 0L;
    private String remark = "";

}
