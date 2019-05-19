package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssTempPostPerson;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */
public interface ITempPostPersonService extends IService<TssTempPostPerson> {
    List<TssTempPostPerson> selectByExamPlaceIdsInTemp(List<String> examPlaceIds);

    boolean delByTepIdsInTemp(List<String> readyDelIds);

    boolean updatePostNumById(String id, int number);

    boolean copyToMainTableByTepIds(List<String> tepIds);

    boolean removeByTepIds(List<String> tepIds);

    int countByPostIdsAndTepId(List<String> postIds, String tepId);

    int countByPostIds(List<String> postIds);

    boolean postIsExist(String id);
}
