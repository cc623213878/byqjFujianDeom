package com.byqj.module.common.examDictionary.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.entity.TssDictionary;
import com.byqj.module.common.examDictionary.constant.ExamDictionaryConstants;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamDictionaryBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private IDictionaryService dictionaryService;

    /*
     * @Author lwn
     * @Description 获取考点信息
     * @Date 22:30 2019/3/18
     * @Param []
     * @return void
     **/
    public void getExamPlaceRequestProcess() {
        List<TssDictionary> lists = dictionaryService.list(new LambdaQueryWrapper<TssDictionary>().eq(TssDictionary::getType, ExamDictionaryConstants.EXAM_POINT));
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "examPoint", lists);

    }

    /*
     * @Author lwn
     * @Description 获取考试类型
     * @Date 22:30 2019/3/18
     * @Param
     * @return
     **/

    public void getExamTypeRequestProcess() {
        List<TssDictionary> lists = dictionaryService.list(new LambdaQueryWrapper<TssDictionary>().eq(TssDictionary::getType, ExamDictionaryConstants.EXAM_TYPE));
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "examType", lists);
    }
}
