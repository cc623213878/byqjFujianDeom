package com.byqj.module.common.operationLog.service;

import com.alibaba.fastjson.JSONObject;
import com.byqj.dao.SysLogDao;
import com.byqj.dto.SysLogDto;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.vo.LogSearchVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationLogBusinessService {

    @Autowired
    DataCenterService dataCenterService;

    @Autowired
    SysLogDao sysLogDao;


    public void searchOperationLogRequestProcess() {

        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");
        JSONObject json = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("selectMessage");
        LogSearchVo logSearchVo = json.toJavaObject(LogSearchVo.class);

        PageHelper.startPage(pageNum, pageSize);
        List<SysLogDto> sysLogList = sysLogDao.searchSelective(logSearchVo);

        PageInfo<SysLogDto> pageResult = new PageInfo<SysLogDto>(sysLogList);

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "logList", pageResult);
    }

}
