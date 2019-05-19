package com.byqj.module.system.userCenter.service;

import com.byqj.dao.SysUserDao;
import com.byqj.entity.SysUser;
import com.byqj.module.system.userCenter.enums.UserCenterReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserCenterCheckService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysUserDao userDao;

    public void updatePasswordCheck() {
        String oldPassword = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("oldPassword");
        String newPassword = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("newPassword");

        if (StringUtils.isBlank(oldPassword)) {
            ExceptionUtil.setFailureMsgAndThrow(UserCenterReasonOfFailure.OLD_PASSWORD_IS_BLANK);
        }
        if (StringUtils.isBlank(newPassword)) {
            ExceptionUtil.setFailureMsgAndThrow(UserCenterReasonOfFailure.NEW_PASSWORD_IS_BLANK);
        }

        String currentUserId = dataCenterService.getCurrentUserId();
        SysUser user = userDao.selectById(currentUserId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            ExceptionUtil.setFailureMsgAndThrow(UserCenterReasonOfFailure.OLD_PASSWORD_ERROR);
        }

        dataCenterService.setData("newPassword", newPassword.trim());
    }
}
