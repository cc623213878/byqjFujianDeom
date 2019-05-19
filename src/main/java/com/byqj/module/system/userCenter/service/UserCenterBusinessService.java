package com.byqj.module.system.userCenter.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.SysAdminDetailDao;
import com.byqj.dao.SysUserDao;
import com.byqj.entity.SysAdminDetail;
import com.byqj.module.system.userCenter.constant.LogConstant;
import com.byqj.module.system.userCenter.enums.UserCenterReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCenterBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private LogCenterService logCenterService;
    @Autowired
    private SysUserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysAdminDetailDao sysAdminDetailDao;


    public void updatePasswordRequestProcess() {
        String newPassword = dataCenterService.getData("newPassword");
        boolean result = userDao.setPassword(dataCenterService.getCurrentUserId(), passwordEncoder.encode(newPassword));

        if (!result) {
            ExceptionUtil.setFailureMsgAndThrow(UserCenterReasonOfFailure.UPDATE_PASSWORD_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        SysAdminDetail sysAdminDetail = sysAdminDetailDao.selectOne(new LambdaQueryWrapper<SysAdminDetail>()
                .eq(SysAdminDetail::getUserId, dataCenterService.getCurrentUserId()));
        String operationContent = String.format(LogConstant.UPDATE_PASSWORD, sysAdminDetail.getUserName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

}
