package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssExam;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */

public interface IExamService extends IService<TssExam> {

    List<TssExam> getSubExam(String mainExamId);

    List<TssExam> listMainExamByCondition(int status, String sTime, String eTime);

    int countById(String mainExamId);

    boolean delExam(String mainExamId);

    boolean fileExam(String mainExId);

    boolean setReportTime(String mainExId, int sTime, int eTime);

}
