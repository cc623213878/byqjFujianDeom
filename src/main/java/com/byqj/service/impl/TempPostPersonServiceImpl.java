package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssTempPostPersonDao;
import com.byqj.entity.TssTempPostPerson;
import com.byqj.service.ITempPostPersonService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */
@Service(value = "tempPostPersonService")
public class TempPostPersonServiceImpl extends ServiceImpl<TssTempPostPersonDao, TssTempPostPerson>
        implements ITempPostPersonService {

    public List<TssTempPostPerson> selectByExamPlaceIdsInTemp(List<String> examPlaceIds) {
        return list(new LambdaQueryWrapper<TssTempPostPerson>()
                .in(TssTempPostPerson::getTepId, examPlaceIds));
    }

    public boolean delByTepIdsInTemp(List<String> readyDelIds) {

        return remove(new LambdaQueryWrapper<TssTempPostPerson>()
                .in(TssTempPostPerson::getTepId, readyDelIds));
    }

    public boolean updatePostNumById(String id, int number) {
        return update(new LambdaUpdateWrapper<TssTempPostPerson>()
                .set(TssTempPostPerson::getPersonNum, number)
                .eq(TssTempPostPerson::getId, id));
    }

    public boolean copyToMainTableByTepIds(List<String> tepIds) {

        return getBaseMapper().copyToMainTableByTepIds(tepIds) >= 0;
    }

    public boolean removeByTepIds(List<String> tepIds) {
        return remove(new LambdaQueryWrapper<TssTempPostPerson>()
                .in(TssTempPostPerson::getTepId, tepIds));
    }

    public int countByPostIdsAndTepId(List<String> postIds, String tepId) {
        return count(new LambdaQueryWrapper<TssTempPostPerson>()
                .eq(TssTempPostPerson::getTepId, tepId)
                .in(TssTempPostPerson::getPostId, postIds));
    }

    public int countByPostIds(List<String> postIds) {
        return count(new LambdaQueryWrapper<TssTempPostPerson>()
                .in(TssTempPostPerson::getPostId, postIds));
    }

    public boolean postIsExist(String id) {
        int count = count(new LambdaQueryWrapper<TssTempPostPerson>()
                .eq(TssTempPostPerson::getId, id));

        return count > 0;
    }
}
