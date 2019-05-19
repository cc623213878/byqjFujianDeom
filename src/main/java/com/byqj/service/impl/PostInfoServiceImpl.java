package com.byqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssPostInfoDao;
import com.byqj.entity.TssPostInfo;
import com.byqj.service.IPostInfoService;
import org.springframework.stereotype.Service;

/**
 * Created by willim on 2019/3/26.
 */

@Service(value = "postInfoService")
public class PostInfoServiceImpl extends ServiceImpl<TssPostInfoDao, TssPostInfo>
        implements IPostInfoService {
}
