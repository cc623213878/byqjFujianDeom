package com.byqj.module.dataKeepDictionaryKeep.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.entity.TssPersonLibrary;
import com.byqj.module.dataKeepDictionaryKeep.enums.DataKeepDictionaryKeepReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.service.IPersonLibraryService;
import com.byqj.utils.CheckVariableUtil;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataKeepDictionaryKeepCheckService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private IPersonLibraryService personLibraryService;

    /**
     * 查询字典数据
     */
    public void searchDictionaryRequestCheck() {

        String type = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("type");
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.PAGE_PARAM_IS_ILLEGAL);
        }
        if (StringUtils.isBlank(type)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.TYPE_NULL);
        }
        dataCenterService.setData("type", type.trim());
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);

    }

    /**
     * 删除字典数据
     */
    public void batchDeleteDictionaryRequestCheck() {

        JSONArray groupJA = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("dictionaryIdList");
        List<String> dictionaryIdList = groupJA.toJavaList(String.class);
        if (CollectionUtils.isEmpty(dictionaryIdList)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.LIST_NULL);
        }

        // 银行
        int bankNum = personLibraryService.count(new LambdaQueryWrapper<TssPersonLibrary>().in(TssPersonLibrary::getBank, dictionaryIdList));
        // 人员类型
        int categoryNum = personLibraryService.count(new LambdaQueryWrapper<TssPersonLibrary>().in(TssPersonLibrary::getCategory, dictionaryIdList));
        // 人员类别
        int typeNum = personLibraryService.count(new LambdaQueryWrapper<TssPersonLibrary>().in(TssPersonLibrary::getType, dictionaryIdList));
        // 汇款类型
        int moneyTypeNum = personLibraryService.count(new LambdaQueryWrapper<TssPersonLibrary>().in(TssPersonLibrary::getMoneyType, dictionaryIdList));

        if (bankNum > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.DATA_IS_USED);
        }

        if (categoryNum > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.DATA_IS_USED);
        }

        if (typeNum > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.DATA_IS_USED);
        }

        if (moneyTypeNum > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.DATA_IS_USED);
        }


        dataCenterService.setData("dictionaryIdList", dictionaryIdList);

    }

    /**
     * 添加字典数据
     */
    public void addDictionaryRequestCheck() {

        checkDictionaryMessage();

    }

    public void checkDictionaryMessage() {

        String type = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("type");
        String description = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("description");
        if (StringUtils.isBlank(type)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.TYPE_NULL);
        }
        if (StringUtils.isBlank(description)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.DESCRIPTION_NULL);
        }
        dataCenterService.setData("type", type.trim());
        dataCenterService.setData("description", description.trim());

    }

    /**
     * 更新字典数据
     */
    public void updateDictionaryRequestCheck() {

        String id = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        if (StringUtils.isBlank(id)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.ID_NULL);
        }
        checkDictionaryMessage();
        dataCenterService.setData("id", id.trim());

    }

}
