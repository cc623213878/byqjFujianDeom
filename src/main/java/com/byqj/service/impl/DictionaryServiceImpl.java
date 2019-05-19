package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.constant.CommonConstant;
import com.byqj.dao.TssDictionaryDao;
import com.byqj.entity.TssDictionary;
import com.byqj.service.IDictionaryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */
@Service(value = "dictionaryService")
public class DictionaryServiceImpl extends ServiceImpl<TssDictionaryDao, TssDictionary>
        implements IDictionaryService {

    public List<TssDictionary> getPointAndType(String examPoint, String examType) {
        return list(new LambdaQueryWrapper<TssDictionary>()
                .eq(TssDictionary::getType, examPoint)
                .or()
                .eq(TssDictionary::getType, examType)
                .orderByDesc(TssDictionary::getCode));
    }

    public List<TssDictionary> getExamPoint() {
        return list(new LambdaQueryWrapper<TssDictionary>()
                .eq(TssDictionary::getType, CommonConstant.EXAM_POINT)
                .orderByDesc(TssDictionary::getCode));
    }

    public List<TssDictionary> getExamType() {
        return list(new LambdaQueryWrapper<TssDictionary>()
                .eq(TssDictionary::getType, CommonConstant.EXAM_TYPE)
                .orderByDesc(TssDictionary::getCode));
    }
}
