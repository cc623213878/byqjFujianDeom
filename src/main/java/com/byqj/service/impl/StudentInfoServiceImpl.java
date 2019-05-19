package com.byqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssStudentInfoDao;
import com.byqj.entity.TssStudentInfo;
import com.byqj.service.IStudentInfoService;
import org.springframework.stereotype.Service;

@Service(value = "studentInfoService")
public class StudentInfoServiceImpl extends ServiceImpl<TssStudentInfoDao, TssStudentInfo>
        implements IStudentInfoService {

}
