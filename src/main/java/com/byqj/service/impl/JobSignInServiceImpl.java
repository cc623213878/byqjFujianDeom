package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssJobSignInDao;
import com.byqj.entity.TssJobSignIn;
import com.byqj.service.IJobSignInService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service(value = "jobSignInService")
public class JobSignInServiceImpl extends ServiceImpl<TssJobSignInDao, TssJobSignIn> implements IJobSignInService {
    @Override
    public List<TssJobSignIn> selectByteIds(Set<String> teIds) {
        return list(new LambdaQueryWrapper<TssJobSignIn>().in(TssJobSignIn::getTeId, teIds));
    }
}
