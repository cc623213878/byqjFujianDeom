package com.byqj.module.scoreQuery.service;

import com.byqj.entity.TssImportGrade;
import com.byqj.module.scoreQuery.enums.ScoreQueryReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.IImportGradeService;
import com.byqj.utils.AESUtil;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

//@SuppressWarnings("ALL")
@Service
public class ScoreQueryBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private IImportGradeService iImportGradeService;
    @Value("${encode-role}")
    private String encodeRole;

    /**
     * 查询成绩
     */
    public void scoreQueryRequestProcess() {
        String kssjhcs = dataCenterService.getData("kssjhcs");
        String kmmc = dataCenterService.getData("kmmc");
        String xm = dataCenterService.getData("xm");
        String bszkzh = dataCenterService.getData("bszkzh");

        List<TssImportGrade> result = iImportGradeService.selectGradeByCondition(kssjhcs, kmmc, xm, bszkzh);
        if (CollectionUtils.isEmpty(result)) {
            ExceptionUtil.setFailureMsgAndThrow(ScoreQueryReasonOfFailure.ON_EXAM);
        }
        for (TssImportGrade resultOne : result) {
            if (StringUtils.equals(resultOne.getZf(), "0")
                    && StringUtils.equals(resultOne.getCj1(), "0")
                    && StringUtils.equals(resultOne.getCj2(), "0")
                    && StringUtils.equals(resultOne.getCj3(), "0")
                    && StringUtils.equals(resultOne.getCj4(), "0")) {
                ExceptionUtil.setFailureMsgAndThrow(ScoreQueryReasonOfFailure.ON_SCORE);
            }
        }
        //身份证解密
        result.forEach(item -> item.setZjhm(AESUtil.AESDecode(encodeRole, item.getZjhm())));
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "scoreList", result);

    }
}
