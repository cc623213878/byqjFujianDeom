package com.byqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssGradeSubjectsDao;
import com.byqj.entity.TssGradeSubjects;
import com.byqj.service.IGradeSubjectsService;
import org.springframework.stereotype.Service;

/**
 * @ClassName:GradeSubjectsServiceImpl
 * @Description:
 * @Author:lwn
 * @Date:2019/3/26 15:04
 **/
@Service(value = "gradeSubjectsService")
public class GradeSubjectsServiceImpl extends ServiceImpl<TssGradeSubjectsDao, TssGradeSubjects> implements IGradeSubjectsService {
}
