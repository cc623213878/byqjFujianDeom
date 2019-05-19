package com.byqj.module.system.menuManagement.controller;


import com.byqj.module.system.menuManagement.constant.MenueManagementFunctionNoConstants;
import com.byqj.module.system.menuManagement.service.MenueManagementService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MenueManagementFunctionNoDispatcher {

    @Autowired
    MenueManagementService menueManagementService;
    @Autowired
    DataCenterService dataCenterService;

    public void dispatchByFunctionNo() {
        String functionNo = dataCenterService.getFunctionNo();
        log.debug("functionNo is {}", functionNo);

        switch (functionNo) {
            // 获取权组列表
            case MenueManagementFunctionNoConstants.GET_MENUE_INFO:
                menueManagementService.getMenueInfoRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(), ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
