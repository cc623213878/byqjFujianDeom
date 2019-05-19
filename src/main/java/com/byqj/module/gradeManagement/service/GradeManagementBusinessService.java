package com.byqj.module.gradeManagement.service;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byqj.dao.TssGradeSubjectsDao;
import com.byqj.dao.TssImportGradeDao;
import com.byqj.dto.GradeConditionDto;
import com.byqj.dto.TssImportGradeDto;
import com.byqj.entity.TssGradeSubjects;
import com.byqj.entity.TssImportGrade;
import com.byqj.module.gradeManagement.constant.LogConstant;
import com.byqj.module.gradeManagement.enums.GradeManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.IGradeSubjectsService;
import com.byqj.service.IImportGradeService;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.AESUtil;
import com.byqj.utils.ExcelUtil;
import com.byqj.utils.ExceptionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@SuppressWarnings("ALL")
@Service
public class GradeManagementBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private LogCenterService logCenterService;
    @Autowired
    private TssImportGradeDao tssImportGradeDao;
    @Autowired
    private TssGradeSubjectsDao tssGradeSubjectsDao;
    @Autowired
    private IImportGradeService iImportGradeService;
    @Autowired
    private IGradeSubjectsService gradeSubjectsService;
    @Value("${file-path.download-grade-template}")
    private String downloadGradeTemplate;
    @Value("${encode-role}")
    private String encodeRole;

    @Transactional
    public void postGradeRequestProcess() {
        Part part = dataCenterService.getData("part");
        InputStream inputStream = null;
        Set<TssGradeSubjects> set = Sets.newHashSet(); //科目集合
        Multimap<TssGradeSubjects, TssImportGrade> map = ArrayListMultimap.create(); //科目-要导入的成绩 通过查询科目里所有的成绩取出操作
        try {
            inputStream = part.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.FILE_UPLOAD_ERROR);
        }
        //获取从excel中读取信息
        List<TssImportGradeDto> excelList = (List<TssImportGradeDto>) (List) ExcelUtil.readExcelWithModel(inputStream, TssImportGradeDto.class, ExcelTypeEnum.XLS);
        //转换数据库操作实体
        List<TssImportGrade> list = new ArrayList<>();
        for (TssImportGradeDto tssImportGradeDto : excelList) {
            TssImportGrade tssImportGrade = new TssImportGrade();
            BeanUtils.copyProperties(tssImportGradeDto, tssImportGrade);
            list.add(tssImportGrade);
        }
        for (TssImportGrade tssImportGrade : list) {
            TssGradeSubjects tssGradeSubjects = new TssGradeSubjects();
            tssGradeSubjects.setKmmc(tssImportGrade.getBskmmc());
            tssGradeSubjects.setKssjhcs(tssImportGrade.getKssjhcs());
            set.add(tssGradeSubjects);
            map.put(tssGradeSubjects, tssImportGrade);
        }
        //库里删除上传科目和有记录成绩的用户
        List<TssGradeSubjects> subjectsList = new ArrayList<>(set);
        tssGradeSubjectsDao.deleteByNameAndTimes(subjectsList);
        tssImportGradeDao.deleteByNameAndTimes(subjectsList);
        //把上传数据插入数据库
        boolean subjectsResult = gradeSubjectsService.saveBatch(subjectsList);
        if (!subjectsResult) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.INSERT_ERROR);
        }
        //获取插入到科目表中的id 修改待保存的上传成绩数据中的examId
        for (TssGradeSubjects tssGradeSubjects : subjectsList) {
            List<TssImportGrade> beModifiedList = (List<TssImportGrade>) map.get(tssGradeSubjects);
            if (CollectionUtils.isNotEmpty(beModifiedList)) {
                for (TssImportGrade tssImportGrade : beModifiedList) {
                    tssImportGrade.setExamId(tssGradeSubjects.getId());
                }
            }

        }

        boolean gradeResult = iImportGradeService.saveBatch(list);
        if (!gradeResult) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.INSERT_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.POST_GRADE);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }

    @Transactional
    public void postSingleGradeRequestProcess() {
        Part part = dataCenterService.getData("part");
        String examId = dataCenterService.getData("examId");
        InputStream inputStream = null;
        //获取该考试名称和时间
        TssGradeSubjects gradeSubject = gradeSubjectsService.getById(examId);
        if (gradeSubject == null) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.NO_EXAM);
        }
        try {
            inputStream = part.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.FILE_UPLOAD_ERROR);
        }
        //获取从excel中读取信息
        List<TssImportGradeDto> excelList = (List<TssImportGradeDto>) (List) ExcelUtil.readExcelWithModel(inputStream, TssImportGradeDto.class, ExcelTypeEnum.XLS);
        //转换数据库操作实体
        List<TssImportGrade> list = new ArrayList<>();
        for (TssImportGradeDto tssImportGradeDto : excelList) {
            TssImportGrade tssImportGrade = new TssImportGrade();
            if (gradeSubject.getKmmc().equals(tssImportGradeDto.getBskmmc()) && gradeSubject.getKssjhcs().equals(tssImportGrade.getKssjhcs())) {
                BeanUtils.copyProperties(tssImportGradeDto, tssImportGrade);
                list.add(tssImportGrade);
            }
        }
        //身份证加密
        list.forEach(item -> item.setZjhm(AESUtil.AESEncode(encodeRole, item.getZjhm())));
        //库里删除上传科目和有记录成绩的用户
        iImportGradeService.remove(new LambdaQueryWrapper<TssImportGrade>().eq(TssImportGrade::getExamId, examId));
        //把上传数据插入数据库
        boolean gradeResult = iImportGradeService.saveBatch(list);
        if (!gradeResult) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.INSERT_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

    }

    /**
     * 查询人员信息
     */
    public void searchPersonRequestProcess() {

        String kssjhcs = dataCenterService.getData("kssjhcs");
        String bskmmc = dataCenterService.getData("bskmmc");
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");

        PageHelper.startPage(pageNum, pageSize);
        List<TssImportGrade> personMessages = tssImportGradeDao.selectList(new QueryWrapper<TssImportGrade>()
                .lambda()
                .eq(TssImportGrade::getKssjhcs, kssjhcs)
                .eq(TssImportGrade::getBskmmc, bskmmc));
        //身份证解密
        personMessages.forEach(item -> item.setZjhm(AESUtil.AESEncode(encodeRole, item.getZjhm())));
        PageInfo<TssImportGrade> pageResult = new PageInfo<>(personMessages);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "personMessge", pageResult);

    }

    /**
     * 查询成绩条件信息
     */
    public void searchGradeConditionRequestProcess() {

        List<GradeConditionDto> gradeCondition = tssGradeSubjectsDao.selectGradeCondition();
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "gradeCondition", gradeCondition);

    }

    /**
     * 查询成绩
     */
    public void searchGradeRequestProcess() {

        String kssjhcs = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("kssjhcs");
        String kmmc = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("kmmc");
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");

        PageHelper.startPage(pageNum, pageSize);
        List<TssGradeSubjects> gradeList = tssGradeSubjectsDao.selectGrade(kmmc, kssjhcs);

        PageInfo<TssGradeSubjects> pageResult = new PageInfo<>(gradeList);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "gradeList", pageResult);

    }

    /**
     * 删除成绩信息
     */
    @Transactional
    public void deleteGradeRequestProcess() {

        List<Integer> deleteList = dataCenterService.getData("deleteList");
        int result = tssGradeSubjectsDao.deleteBatchIds(deleteList);
        boolean resultofDeleteByGradeExamIds = iImportGradeService.deleteByGradeExamIds(deleteList);
        if (result < 0 || resultofDeleteByGradeExamIds == false) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.DELETE_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        List<TssImportGrade> gradeList = (List<TssImportGrade>) iImportGradeService.listByIds(deleteList);
        List<String> gradeNameList = gradeList.stream().map(TssImportGrade::getBskmmc).collect(Collectors.toList());
        String operationContent = String.format(LogConstant.DELETE_GRADE, gradeNameList);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    /*
     * author lwn
     * date 2019/4/8 22:50
     * param
     * return void
     * 获取成绩上传模板地址
     */
    public void downloadTemplateRequestProcess() {
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.putValueToData(responseData, "downloadUrl", downloadGradeTemplate);
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.DOWNLOAD_TEMPLATE);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }
}
