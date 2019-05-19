package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.dto.SubmitPersonDto;
import com.byqj.entity.TssSubmitPerson;

import java.util.List;

/**
 * Created by willim on 2019/3/22.
 */
public interface ISubmitPersonService extends IService<TssSubmitPerson> {
    List<SubmitPersonDto> selectPersonBatchIds(List<String> personIdList);
//    boolean resetTepIdByTepIds(List<String> delIds);

//    boolean resetTepIdByTepIdsInTemp(List<String> readyDels);
}
