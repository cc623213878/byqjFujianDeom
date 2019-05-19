package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssPostPersonDao;
import com.byqj.entity.TssPostPerson;
import com.byqj.service.IPostPersonService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */
@Service(value = "postPersonService")
public class PostPersonServiceImpl extends ServiceImpl<TssPostPersonDao, TssPostPerson>
        implements IPostPersonService {

    public List<TssPostPerson> selectByExamPlaceIds(List<String> examPlaceIds) {
        return list(new LambdaQueryWrapper<TssPostPerson>()
                .in(TssPostPerson::getTepId, examPlaceIds));
    }

    public boolean delByTepIds(List<String> delIds) {
        return remove(new LambdaQueryWrapper<TssPostPerson>()
                .in(TssPostPerson::getTepId, delIds));
    }

    public boolean delByTepIdsInTemp(List<String> readyDels) {

        return getBaseMapper().delByTepIdsInTemp(readyDels);
    }
}
