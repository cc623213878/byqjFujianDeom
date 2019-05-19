package com.byqj.module.dataKeepDictionaryKeep.controller;

import com.byqj.module.dataKeepDictionaryKeep.constant.DataKeepDictionaryKeepFunctionNoConstants;
import com.byqj.module.dataKeepDictionaryKeep.service.DataKeepDictionaryKeepService;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.enums.ReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataKeepDictionaryKeepFunctionNoDispatcher {

    @Autowired
    DataKeepDictionaryKeepService dataKeepDictionaryKeepService;
    @Autowired
    DataCenterService dataCenterService;

    void dispatchByFunctionNo(String functionNo) {
        switch (functionNo) {

            // 查询字典数据
            case DataKeepDictionaryKeepFunctionNoConstants.SEARCH_DICTIONARY:
                dataKeepDictionaryKeepService.searchDictionaryRequestProcess();
                break;
            // 删除字典数据
            case DataKeepDictionaryKeepFunctionNoConstants.DELETE_DICTIONARY:
                dataKeepDictionaryKeepService.batchDeleteDictionaryRequestProcess();
                break;
            // 修改字典数据
            case DataKeepDictionaryKeepFunctionNoConstants.UPDATE_DICTIONARY:
                dataKeepDictionaryKeepService.updateDictionaryRequestProcess();
                break;
            // 添加字典数据
            case DataKeepDictionaryKeepFunctionNoConstants.ADD_DICTIONARY:
                dataKeepDictionaryKeepService.addDictionaryRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),
                        ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
