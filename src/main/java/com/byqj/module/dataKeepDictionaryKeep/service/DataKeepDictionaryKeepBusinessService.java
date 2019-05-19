package com.byqj.module.dataKeepDictionaryKeep.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byqj.dao.TssDictionaryDao;
import com.byqj.dao.TssPostPersonDao;
import com.byqj.entity.TssDictionary;
import com.byqj.module.dataKeepDictionaryKeep.constant.DataKeepDictionaryKeepLogConstant;
import com.byqj.module.dataKeepDictionaryKeep.enums.DataKeepDictionaryKeepReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.ExceptionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.primitives.Longs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@SuppressWarnings("ALL")
@Service
public class DataKeepDictionaryKeepBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private LogCenterService logCenterService;
    @Autowired
    private TssDictionaryDao tssDictionaryDao;
    @Autowired
    private TssPostPersonDao tssPostPersonDao;

    /**
     * 查询字典数据
     */
    public void searchDictionaryRequestProcess() {

        String type = dataCenterService.getData("type");
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");

        PageHelper.startPage(pageNum, pageSize);
        List<TssDictionary> dictionaryList = tssDictionaryDao
                .selectList(new QueryWrapper<TssDictionary>().lambda().eq(TssDictionary::getType, type));

        PageInfo<TssDictionary> pageResult = new PageInfo<>(dictionaryList);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "dictionaryList", pageResult);

    }

    /**
     * 删除字典数据
     */
    @Transactional
    public void batchDeleteDictionaryRequestProcess() {

        List<String> dictionaryIdList = dataCenterService.getData("dictionaryIdList");

        int result = tssDictionaryDao.deleteBatchIds(dictionaryIdList);
        if (result < 1) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.DELETE_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        List<String> contentList = tssDictionaryDao.getDescriptionById(dictionaryIdList);
        String operationContent = String.format(DataKeepDictionaryKeepLogConstant.DELETE_DICTIONARY, contentList);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    /**
     * 添加字典数据
     */
    @Transactional
    public void addDictionaryRequestProcess() {

        String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");
        String type = dataCenterService.getData("type");
        String description = dataCenterService.getData("description");

        int typeResult = tssDictionaryDao.selectCount(new QueryWrapper<TssDictionary>().lambda()
                .eq(TssDictionary::getType, type).eq(TssDictionary::getDescription, description));
        if (typeResult > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.TYPE_EXIST);
        }

        TssDictionary dictionary = new TssDictionary();
        dictionary.setType(type);
        dictionary.setCode(0);
        dictionary.setDescription(description);
        dictionary.setRemark(remark);
        int result = tssDictionaryDao.insert(dictionary);
        if (result < 1) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.INSERT_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(DataKeepDictionaryKeepLogConstant.ADD_DICTIONARY, description);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    /**
     * 更新字典数据
     */
    @Transactional
    public void updateDictionaryRequestProcess() {

        String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");
        String description = dataCenterService.getData("description");
        String type = dataCenterService.getData("type");
        String idString = dataCenterService.getData("id");
        Long id = Longs.tryParse(idString);

        int typeResult = tssDictionaryDao.selectCount(new QueryWrapper<TssDictionary>().lambda()
                .eq(TssDictionary::getType, type)
                .eq(TssDictionary::getDescription, description)
                .ne(TssDictionary::getId, id));
        if (typeResult > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.TYPE_EXIST);
        }
        TssDictionary dictionary = new TssDictionary();
        dictionary.setId(id);
        dictionary.setDescription(description);
        dictionary.setRemark(remark);
        int resultOfTssPostInfo = tssDictionaryDao.update(dictionary, new QueryWrapper<TssDictionary>().lambda().eq(TssDictionary::getId, id));
        if (resultOfTssPostInfo < 1) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepDictionaryKeepReasonOfFailure.UPDATE_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(DataKeepDictionaryKeepLogConstant.UPDATE_DICTIONARY, description);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }
}
