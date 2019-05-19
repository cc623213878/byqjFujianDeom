package com.byqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssSubmitPersonDao;
import com.byqj.dto.SubmitPersonDto;
import com.byqj.entity.TssSubmitPerson;
import com.byqj.service.ISubmitPersonService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by willim on 2019/3/22.
 */

@Service(value = "submitPersonService")
public class SubmitPersonServiceImpl extends ServiceImpl<TssSubmitPersonDao, TssSubmitPerson>
        implements ISubmitPersonService {
    @Override
    public List<SubmitPersonDto> selectPersonBatchIds(List<String> personIdList) {
        return getBaseMapper().selectPersonBatchIds(personIdList);
    }


//    public boolean resetTepIdByTepIds(List<String> delIds) {
//        return update(new LambdaUpdateWrapper<TssSubmitPerson>()
//                .set(TssSubmitPerson::getTeId, "")
//                .in(TssSubmitPerson::getTeId, delIds));
//    }
//
//    public boolean resetTepIdByTepIdsInTemp(List<String> readyDels) {
//        return getBaseMapper().resetTepIdByTepIdsInTemp(readyDels);
//    }
}
