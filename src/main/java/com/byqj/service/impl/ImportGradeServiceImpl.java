package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssImportGradeDao;
import com.byqj.entity.TssImportGrade;
import com.byqj.service.IImportGradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "ImportGradeService")
public class ImportGradeServiceImpl extends ServiceImpl<TssImportGradeDao, TssImportGrade>
        implements IImportGradeService {

    public Boolean deleteByGradeExamIds(List<Integer> delsteIds) {
        return remove(new LambdaQueryWrapper<TssImportGrade>()
                .in(TssImportGrade::getExamId, delsteIds));
    }

    public List<TssImportGrade> selectGradeByCondition(String kssjhcs, String kmmc, String xm, String bszkzh) {
        return list(new LambdaQueryWrapper<TssImportGrade>()
                .select(TssImportGrade::getCjdh, TssImportGrade::getZf, TssImportGrade::getCj1,
                        TssImportGrade::getCj2, TssImportGrade::getCj3, TssImportGrade::getCj4,
                        TssImportGrade::getBz, TssImportGrade::getXm, TssImportGrade::getBszkzh,
                        TssImportGrade::getKssjhcs, TssImportGrade::getBj, TssImportGrade::getBskmmc)
                .eq(TssImportGrade::getKssjhcs, kssjhcs)
                .eq(TssImportGrade::getBskmmc, kmmc)
                .eq(TssImportGrade::getXm, xm)
                .eq(TssImportGrade::getBszkzh, bszkzh));
    }
}
