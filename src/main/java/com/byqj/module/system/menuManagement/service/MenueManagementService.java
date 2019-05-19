package com.byqj.module.system.menuManagement.service;

import com.byqj.security.core.support.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenueManagementService {

    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    MenueManagementCheckService menueManagementCheckService;
    @Autowired
    MenueManagementBusinessService menueManagementBusinessService;


    public void getMenueInfoRequestProcess() {
        menueManagementBusinessService.getMenueInfoRequestProcess();
    }
}
