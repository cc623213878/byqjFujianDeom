package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssExamDao;
import com.byqj.entity.TssExam;
import com.byqj.service.IExamService;
import com.byqj.utils.LevelUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */

@Service(value = "examService")
public class ExamServiceImpl extends ServiceImpl<TssExamDao, TssExam>
        implements IExamService {

    public List<TssExam> getSubExam(String mainExamId) {
        return list(new LambdaQueryWrapper<TssExam>()
                .eq(TssExam::getParentId, mainExamId)
                .orderByAsc(TssExam::getStartTime));

    }

    public List<TssExam> listMainExamByCondition(int status, String sTime, String eTime) {
        return list(new LambdaQueryWrapper<TssExam>()
                .eq(TssExam::getStatus, status)
                .eq(TssExam::getLevel, LevelUtil.ROOT)
                .between(TssExam::getStartTime, sTime, eTime)
                .orderByDesc(TssExam::getStartTime));
    }

    public int countById(String mainExamId) {
        return count(new LambdaQueryWrapper<TssExam>()
                .eq(TssExam::getId, mainExamId));
    }

    public boolean delExam(String mainExamId) {
        return update(new LambdaUpdateWrapper<TssExam>()
                .set(TssExam::getStatus, 901)
                .eq(TssExam::getId, mainExamId));
    }

    public boolean fileExam(String mainExId) {
        return update(new LambdaUpdateWrapper<TssExam>()
                .set(TssExam::getStatus, 1)
                .eq(TssExam::getId, mainExId)
                .or()
                .eq(TssExam::getParentId, mainExId));
    }

    public boolean setReportTime(String mainExId, int sTime, int eTime) {
        return update(new LambdaUpdateWrapper<TssExam>()
                .set(TssExam::getReportStart, sTime)
                .set(TssExam::getReportEnd, eTime)
                .eq(TssExam::getId, mainExId)
                .or()
                .eq(TssExam::getParentId, mainExId));
    }

}
