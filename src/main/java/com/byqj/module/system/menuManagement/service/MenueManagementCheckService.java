package com.byqj.module.system.menuManagement.service;

import com.byqj.security.core.support.DataCenterService;
import com.byqj.service.impl.LogCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MenueManagementCheckService {

    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    LogCenterService logCenterService;


}
