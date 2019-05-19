package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssSeatOrder;


public interface ISeatOrderService extends IService<TssSeatOrder> {
    boolean updateTciIdById(String id, String tciId);
}
