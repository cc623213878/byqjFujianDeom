package com.byqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssSolicitationTimeDao;
import com.byqj.entity.TssSolicitationTime;
import com.byqj.service.ISolicitationTimeService;
import org.springframework.stereotype.Service;

/**
 * Created by willim on 2019/3/21.
 */
@Service(value = "solicitationTimeService")
public class SolicitationTimeServiceImpl extends ServiceImpl<TssSolicitationTimeDao, TssSolicitationTime>
        implements ISolicitationTimeService {
}
