package com.byqj.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssTempStudentInfoDao;
import com.byqj.entity.TssTempStudentInfo;
import com.byqj.service.ITempStudentInfoService;
import org.springframework.stereotype.Service;

@Service(value = "tempStudentInfoService")
public class TempStudentInfoServiceImpl extends ServiceImpl<TssTempStudentInfoDao, TssTempStudentInfo>
        implements ITempStudentInfoService {
}
