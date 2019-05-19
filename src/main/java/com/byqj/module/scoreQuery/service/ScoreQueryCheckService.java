package com.byqj.module.scoreQuery.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.TssImportGradeDao;
import com.byqj.entity.TssImportGrade;
import com.byqj.module.scoreQuery.enums.ScoreQueryReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreQueryCheckService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private TssImportGradeDao tssImportGradeDao;

    /**
     * 查询成绩
     */
    public void scoreQueryRequestCheck() {
        String kssjhcs = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("kssjhcs");
        String kmmc = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("kmmc");
        String xm = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("xm");
        String bszkzh = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("bszkzh");
        if (StringUtils.isBlank(kssjhcs)) {
            ExceptionUtil.setFailureMsgAndThrow(ScoreQueryReasonOfFailure.KSCSHSJ_IS_NULL);
        }
        if (StringUtils.isBlank(kmmc)) {
            ExceptionUtil.setFailureMsgAndThrow(ScoreQueryReasonOfFailure.KMMC_IS_NULL);
        }
        if (StringUtils.isBlank(xm)) {
            ExceptionUtil.setFailureMsgAndThrow(ScoreQueryReasonOfFailure.XM_IS_NULL);
        }
        if (StringUtils.isBlank(bszkzh)) {
            ExceptionUtil.setFailureMsgAndThrow(ScoreQueryReasonOfFailure.BSZKZH_IS_NULL);
        }
        int result = tssImportGradeDao.selectCount(new LambdaQueryWrapper<TssImportGrade>()
                .eq(TssImportGrade::getXm, xm)
                .eq(TssImportGrade::getBszkzh, bszkzh));
        if (result < 1) {
            ExceptionUtil.setFailureMsgAndThrow(ScoreQueryReasonOfFailure.PERSON_NOT_EXSIT);
        }
        dataCenterService.setData("kssjhcs", kssjhcs.trim());
        dataCenterService.setData("kmmc", kmmc.trim());
        dataCenterService.setData("xm", xm.trim());
        dataCenterService.setData("bszkzh", bszkzh.trim());
    }
}
