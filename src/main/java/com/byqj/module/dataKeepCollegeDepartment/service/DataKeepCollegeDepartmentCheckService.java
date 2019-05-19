package com.byqj.module.dataKeepCollegeDepartment.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byqj.dao.TssPersonLibraryDao;
import com.byqj.entity.TssPersonLibrary;
import com.byqj.module.dataKeepCollegeDepartment.enums.DataKeepCollegeDepartmentReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.utils.CheckVariableUtil;
import com.byqj.utils.ExceptionUtil;
import com.google.common.primitives.Longs;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataKeepCollegeDepartmentCheckService {

    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    private TssPersonLibraryDao tssPersonLibraryDao;

    /**
     * 查询人员
     */
    public void searchPersonRequestCheck() {

        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.PAGE_PARAM_IS_ILLEGAL);
        }
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);

    }

    /**
     * 删除人员
     */
    public void batchDeletePersonRequestCheck() {

        JSONArray groupJA = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("personIdList");
        if (groupJA == null) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        List<String> personIdList = groupJA.toJavaList(String.class);
        if (CollectionUtils.isEmpty(personIdList)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.LIST_NULL);
        }
        dataCenterService.setData("personIdList", personIdList);

    }

    /**
     * 添加人员
     */
    public void addUserRequestCheck() {
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String collegeIdStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("collegeId");
        String workCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("workCode");
        String pid = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pid");
        String bankStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("bank");
        String bankOpenCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("bankOpenCode");
        String bankOpen = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("bankOpen");
        String bankCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("bankCode");
        String categoryStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("category");
        String typeStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("type");
        String moneyTypeStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("moneyType");
        String finance = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("finance");
        String phone = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("phone");
        String sex = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("sex");
        if (StringUtils.isBlank(collegeIdStr) || StringUtils.isBlank(bankStr) || StringUtils.isBlank(categoryStr) || StringUtils.isBlank(moneyTypeStr)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        Long collegeId = Longs.tryParse(collegeIdStr);
        Long bank = Longs.tryParse(bankStr);
        Long category = Longs.tryParse(categoryStr);
        Long type = Longs.tryParse(typeStr);
        Long moneyType = Longs.tryParse(moneyTypeStr);
        if (!CheckVariableUtil.isMobile(phone)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.SUBMITTED_PHONE_IS_WRONG);
        }
        if (CheckVariableUtil.iDCardIsIllegal(pid)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.SUBMITTED_IDCARD_IS_WRONG);
        }
        if (StringUtils.isBlank(name)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        if (StringUtils.isBlank(workCode)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }

        if (StringUtils.isBlank(bankOpenCode)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        if (StringUtils.isBlank(bankOpen)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        if (StringUtils.isBlank(bankCode)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }

        if (StringUtils.isBlank(finance)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }

        //查重
        int phoneResult = tssPersonLibraryDao.selectCount(new QueryWrapper<TssPersonLibrary>().lambda().eq(TssPersonLibrary::getPhone, phone));
        if (phoneResult > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.USER_PHONE_ALREADY_EXISTS_DESCRIPTION);
        }
        int idCardResult = tssPersonLibraryDao.selectCount(new QueryWrapper<TssPersonLibrary>().lambda().eq(TssPersonLibrary::getPid, pid));
        if (idCardResult > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.USER_SFZ_ALREADY_EXISTS_DESCRIPTION);
        }
        int workCodeResult = tssPersonLibraryDao.selectCount(new QueryWrapper<TssPersonLibrary>().lambda().eq(TssPersonLibrary::getWorkCode, workCode));
        if (workCodeResult > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.USER_GH_ALREADY_EXISTS_DESCRIPTION);
        }
        //封装数据
        TssPersonLibrary tssPersonLibrary = new TssPersonLibrary();
        tssPersonLibrary.setCollegeId(collegeId);
        tssPersonLibrary.setWorkCode(workCode.trim());
        tssPersonLibrary.setName(name.trim());
        tssPersonLibrary.setPhone(phone.trim());
        tssPersonLibrary.setSex(sex.trim());
        tssPersonLibrary.setPid(pid.trim());
        tssPersonLibrary.setBank(bank);
        tssPersonLibrary.setBankOpenCode(bankOpenCode.trim());
        tssPersonLibrary.setBankCode(bankCode.trim());
        tssPersonLibrary.setBankOpen(bankOpen.trim());
        tssPersonLibrary.setCategory(category);
        tssPersonLibrary.setType(type);
        tssPersonLibrary.setMoneyType(moneyType);
        tssPersonLibrary.setFinance(finance.trim());
        dataCenterService.setData("tssPersonLibrary", tssPersonLibrary);
    }

    /**
     * 修改人员
     */
    public void modifyPersonnelRequestCheck() {
        String id = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String collegeIdStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("collegeId");
        String workCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("workCode");
        String pid = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pid");
        String bankStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("bank");
        String bankOpenCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("bankOpenCode");
        String bankOpen = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("bankOpen");
        String bankCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("bankCode");
        String categoryStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("category");
        String typeStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("type");
        String moneyTypeStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("moneyType");
        String finance = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("finance");
        String phone = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("phone");
        String sex = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("sex");
        if (StringUtils.isBlank(collegeIdStr) || StringUtils.isBlank(bankStr) || StringUtils.isBlank(categoryStr) || StringUtils.isBlank(moneyTypeStr)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        Long collegeId = Longs.tryParse(collegeIdStr);
        Long bank = Longs.tryParse(bankStr);
        Long category = Longs.tryParse(categoryStr);
        Long type = Longs.tryParse(typeStr);
        Long moneyType = Longs.tryParse(moneyTypeStr);
        if (!CheckVariableUtil.isMobile(phone)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.SUBMITTED_PHONE_IS_WRONG);
        }
        if (CheckVariableUtil.iDCardIsIllegal(pid)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.SUBMITTED_IDCARD_IS_WRONG);
        }
        if (StringUtils.isBlank(name)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        if (StringUtils.isBlank(workCode)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }

        if (StringUtils.isBlank(bankOpenCode)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        if (StringUtils.isBlank(bankOpen)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        if (StringUtils.isBlank(bankCode)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }

        if (StringUtils.isBlank(finance)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        if (StringUtils.isBlank(id)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
        }
        //查重
        int phoneResult = tssPersonLibraryDao.selectCount(new QueryWrapper<TssPersonLibrary>().lambda().eq(TssPersonLibrary::getPhone, phone).ne(TssPersonLibrary::getId, id));
        if (phoneResult > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.USER_ALREADY_EXISTS_DESCRIPTION);
        }
        int idCardResult = tssPersonLibraryDao.selectCount(new QueryWrapper<TssPersonLibrary>().lambda().eq(TssPersonLibrary::getPid, pid).ne(TssPersonLibrary::getId, id));
        if (idCardResult > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.USER_ALREADY_EXISTS_DESCRIPTION);
        }
        int workCodeResult = tssPersonLibraryDao.selectCount(new QueryWrapper<TssPersonLibrary>().lambda().eq(TssPersonLibrary::getWorkCode, workCode).ne(TssPersonLibrary::getId, id));
        if (workCodeResult > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepCollegeDepartmentReasonOfFailure.USER_ALREADY_EXISTS_DESCRIPTION);
        }
        //封装数据
        TssPersonLibrary tssPersonLibrary = new TssPersonLibrary();
        tssPersonLibrary.setId(id.trim());
        tssPersonLibrary.setCollegeId(collegeId);
        tssPersonLibrary.setWorkCode(workCode.trim());
        tssPersonLibrary.setName(name.trim());
        tssPersonLibrary.setPhone(phone.trim());
        tssPersonLibrary.setSex(sex.trim());
        tssPersonLibrary.setPid(pid.trim());
        tssPersonLibrary.setBank(bank);
        tssPersonLibrary.setBankOpenCode(bankOpenCode.trim());
        tssPersonLibrary.setBankCode(bankCode.trim());
        tssPersonLibrary.setBankOpen(bankOpen.trim());
        tssPersonLibrary.setCategory(category);
        tssPersonLibrary.setType(type);
        tssPersonLibrary.setMoneyType(moneyType);
        tssPersonLibrary.setFinance(finance.trim());
        dataCenterService.setData("tssPersonLibrary", tssPersonLibrary);
    }


}
