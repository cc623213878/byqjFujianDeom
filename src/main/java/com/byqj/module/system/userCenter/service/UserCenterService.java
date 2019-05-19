package com.byqj.module.system.userCenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCenterService {

    @Autowired
    private UserCenterCheckService userCenterCheckService;
    @Autowired
    private UserCenterBusinessService userCenterBusinessService;


    public void updatePasswordRequestProcess() {
        userCenterCheckService.updatePasswordCheck();
        userCenterBusinessService.updatePasswordRequestProcess();
    }

}
