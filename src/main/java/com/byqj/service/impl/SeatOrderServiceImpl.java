package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssSeatOrderDao;
import com.byqj.entity.TssSeatOrder;
import com.byqj.service.ISeatOrderService;
import org.springframework.stereotype.Service;


@Service(value = "seatOrderService")
public class SeatOrderServiceImpl extends ServiceImpl<TssSeatOrderDao, TssSeatOrder>
        implements ISeatOrderService {

    public boolean updateTciIdById(String id, String tciId) {
        return update(new LambdaUpdateWrapper<TssSeatOrder>()
                .set(TssSeatOrder::getTciId, tciId)
                .eq(TssSeatOrder::getTepId, id));
    }
}
