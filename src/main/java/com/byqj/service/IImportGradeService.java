package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssImportGrade;

import java.util.List;

public interface IImportGradeService extends IService<TssImportGrade> {
    Boolean deleteByGradeExamIds(List<Integer> delsteIds);

    List<TssImportGrade> selectGradeByCondition(String kssjhcs, String kmmc, String xm, String bszkzh);
}
