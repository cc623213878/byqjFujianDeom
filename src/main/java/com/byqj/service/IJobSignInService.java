package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssJobSignIn;

import java.util.List;
import java.util.Set;

public interface IJobSignInService extends IService<TssJobSignIn> {
    List<TssJobSignIn> selectByteIds(Set<String> teId);
}
