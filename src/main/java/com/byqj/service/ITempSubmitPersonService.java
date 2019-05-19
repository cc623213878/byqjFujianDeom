package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssTempSubmitPerson;

import java.util.Collection;
import java.util.List;

/**
 * Created by willim on 2019/3/22.
 */
public interface ITempSubmitPersonService extends IService<TssTempSubmitPerson> {

    boolean resetTeIdByTeIdsInTemp(List<String> readyDels);

    List<TssTempSubmitPerson> batchSelectPartById(List<String> ids);

    boolean copyToMainTableByTeId(String mainExId);

    boolean removeByTeId(String mainExId);

    int countByUserListAndExamId(String teId, Collection<String> userId);
}
