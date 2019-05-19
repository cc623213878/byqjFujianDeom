package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssDictionary;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */
public interface IDictionaryService extends IService<TssDictionary> {
    List<TssDictionary> getPointAndType(String examPoint, String examType);

    List<TssDictionary> getExamPoint();

    List<TssDictionary> getExamType();
}
