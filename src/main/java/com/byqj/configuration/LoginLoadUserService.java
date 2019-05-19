package com.byqj.configuration;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.SysAdminDetailDao;
import com.byqj.entity.SysAdminDetail;
import com.byqj.security.rbac.authentication.LoadUserService;
import com.byqj.utils.CheckVariableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: LoginLoadUserService
 * @Description: 通过登录账号获取userId
 * @Author: lwn
 * @Date: 2019/3/8 16:54
 **/
@Component
public class LoginLoadUserService implements LoadUserService {
    private static final Logger logger = LoggerFactory.getLogger(LoginLoadUserService.class);
    @Autowired
    private SysAdminDetailDao sysAdminDetailDao;

    @Override
    public String getUserIdByPrincipal(String principal) {
        SysAdminDetail sysAdminDetail = null;
        if (CheckVariableUtil.isMobile(principal)) {
            sysAdminDetail = sysAdminDetailDao.selectOne(new LambdaQueryWrapper<SysAdminDetail>().eq(SysAdminDetail::getPhone, principal));
            if (sysAdminDetail != null) {
                return sysAdminDetail.getUserId();
            }
        }

        if (CheckVariableUtil.isEmai(principal)) {
            sysAdminDetail = sysAdminDetailDao.selectOne(new LambdaQueryWrapper<SysAdminDetail>().eq(SysAdminDetail::getEmail, principal));
            if (sysAdminDetail != null) {
                return sysAdminDetail.getUserId();
            }
        }

        if (!CheckVariableUtil.iDCardIsIllegal(principal)) {
            sysAdminDetail = sysAdminDetailDao.selectOne(new LambdaQueryWrapper<SysAdminDetail>().eq(SysAdminDetail::getIdCard, principal));
            if (sysAdminDetail != null) {
                return sysAdminDetail.getUserId();
            }
        }

        sysAdminDetail = sysAdminDetailDao.selectOne(new LambdaQueryWrapper<SysAdminDetail>().eq(SysAdminDetail::getUserName, principal));
        if (sysAdminDetail != null) {
            return sysAdminDetail.getUserId();
        }

        return principal;
    }
}
