package com.byqj.module.dataKeepDictionaryKeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataKeepDictionaryKeepService {


    @Autowired
    private DataKeepDictionaryKeepCheckService dataKeepDictionaryKeepCheckService;
    @Autowired
    private DataKeepDictionaryKeepBusinessService dataKeepDictionaryKeepBusinessService;

    /**
     * 查询字典数据
     */
    public void searchDictionaryRequestProcess() {
        dataKeepDictionaryKeepCheckService.searchDictionaryRequestCheck();
        dataKeepDictionaryKeepBusinessService.searchDictionaryRequestProcess();
    }

    /**
     * 删除字典数据
     */
    public void batchDeleteDictionaryRequestProcess() {
        dataKeepDictionaryKeepCheckService.batchDeleteDictionaryRequestCheck();
        dataKeepDictionaryKeepBusinessService.batchDeleteDictionaryRequestProcess();
    }

    /**
     * 更新字典数据
     */
    public void updateDictionaryRequestProcess() {
        dataKeepDictionaryKeepCheckService.updateDictionaryRequestCheck();
        dataKeepDictionaryKeepBusinessService.updateDictionaryRequestProcess();
    }

    /**
     * 添加字典数据
     */
    public void addDictionaryRequestProcess() {
        dataKeepDictionaryKeepCheckService.addDictionaryRequestCheck();
        dataKeepDictionaryKeepBusinessService.addDictionaryRequestProcess();
    }
}
