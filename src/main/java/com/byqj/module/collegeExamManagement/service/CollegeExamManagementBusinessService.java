package com.byqj.module.collegeExamManagement.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.*;
import com.byqj.entity.*;
import com.byqj.module.collegeExamManagement.constant.CollegeExamManagementConstant;
import com.byqj.module.collegeExamManagement.constant.LogConstant;
import com.byqj.module.collegeExamManagement.enums.CollegeExamManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.*;
import com.byqj.vo.PdfMessageListVo;
import com.byqj.vo.StudentSignTableVo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollegeExamManagementBusinessService {
    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private LogCenterService logCenterService;
    @Autowired
    private SysDepartmentDao sysDepartmentDao;
    @Autowired
    private SysAdminDetailDao sysAdminDetailDao;
    @Autowired
    private TssStudentInfoDao tssStudentInfoDao;
    @Autowired
    private TssTempSeatScheduleDao tssTempSeatScheduleDao;
    @Autowired
    private TssClassInfoDao tssClassInfoDao;
    @Autowired
    private TssDictionaryDao tssDictionaryDao;
    @Autowired
    private TssExamDao tssExamDao;
    @Autowired
    private TssExamPlaceDao tssExamPlaceDao;
    @Autowired
    private TssSeatScheduleDao tssSeatScheduleDao;
    @Value("${file-path.card-for-exam-file-path}")
    private String cardForExamFilePath;
    @Value("${file-path.download-file-web-path}")
    private String downloadFileWebPath;
    @Value("${file-path.export-file-local-path}")
    private String exportFileLocalPath;
    @Value("${encode-role}")
    private String encodeRole;

    /**
     * 准考证打印
     */
    public void getCardForExamListRequestProcess() {
        String examId = dataCenterService.getData("mainExamId");
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String scene = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("scene");
        String examPoint = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPoint");
        String examPlaceNumberString = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPlaceNumber");
        Integer examPlaceNumber = null;
        if (!StringUtils.isBlank(examPlaceNumberString)) {
            examPlaceNumber = Integer.parseInt(examPlaceNumberString);
        }
        //获取考试状态
        TssExam exam = tssExamDao.selectOne(new LambdaQueryWrapper<TssExam>().eq(TssExam::getId, examId));
        //根据归档状态条件查询
        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeExamManagementReasonOfFailure.NO_EXAM);
        }

        //获取考试下场次的list
        List<TssExam> examList = tssExamDao.getExamScene(examId, scene);
        List<String> examIdList = examList.stream().map(TssExam::getId).collect(Collectors.toList());

        //获取考点或考场
        //获取考点
        List<TssExamPlace> examPointList = Lists.newArrayList();
        List<String> examPointCode = tssDictionaryDao.getCodeByDescription(examPoint);
        if (!CollectionUtils.isEmpty(examIdList) && !CollectionUtils.isEmpty(examPointCode)) {
            examPointList = tssExamPlaceDao.getExamPoint(examIdList, examPointCode);
        }
        //获取考场
        List<String> examPointIdList = examPointList.stream().map(TssExamPlace::getId).collect(Collectors.toList());
        List<TssExamPlace> examPlaceList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(examPointList) && !CollectionUtils.isEmpty(examPointIdList)) {
            examPlaceList = tssExamPlaceDao.getExamPlace(examPointIdList, examPlaceNumber);
        }

        List<String> examPlaceIdList = examPlaceList.stream().map(TssExamPlace::getId).collect(Collectors.toList());

        //获取座位表中所有人员 ,已有姓名，座位号，考试考点id（考场号）

        //dao层方法，examPlaceIdList为空则查不出数据
        List<StudentSignTableVo> studentSignTableVos = Lists.newArrayList();
        if (exam.getStatus() == 1) {
            studentSignTableVos = tssSeatScheduleDao.getSeatSchedule(examPlaceIdList, name);
        }
        if (exam.getStatus() == 0) {
            studentSignTableVos = tssTempSeatScheduleDao.getSeatSchedule(examPlaceIdList, name);
        }
        List<String> userIdList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(studentSignTableVos)) {
            userIdList = studentSignTableVos.stream().map(StudentSignTableVo::getId).collect(Collectors.toList());
        }

        //获取所有学生信息
        List<TssStudentInfo> tssStudentInfos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(userIdList)) {
            tssStudentInfos = tssStudentInfoDao.searchStudentInfo(userIdList);
        }

        //获取字典表中所有的考点
        List<TssDictionary> tssDictionaries = tssDictionaryDao.selectList(new LambdaQueryWrapper<TssDictionary>()
                .eq(TssDictionary::getType, CollegeExamManagementConstant.EXAM_POINT));
        //装载教室id 考点 学生信息
        if (CollectionUtils.isNotEmpty(studentSignTableVos)) {

            List<TssExamPlace> finalExamPlaceList = examPlaceList;
            List<TssStudentInfo> finalTssStudentInfos = tssStudentInfos;
            List<TssExamPlace> finalExamPointList = examPointList;
            studentSignTableVos.forEach(item -> {

                TssExamPlace tempExamPlace = finalExamPlaceList.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getId(), item.getTepId())).findFirst().get();
                TssStudentInfo tssStudentInfo = finalTssStudentInfos.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getUserId(), item.getId())).findFirst().get();
                TssExamPlace tempExamPoint = finalExamPointList.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getId(), tempExamPlace.getParentId())).findFirst().get();
                TssDictionary tssDictionary = tssDictionaries.stream().filter(value -> tempExamPoint.getNameOrSeq().equals(value.getCode())).findFirst().get();
                //装载教室id
                item.setClassId(tempExamPlace.getTciId());
                //装载考点
                item.setExamPointName(tssDictionary.getDescription());
                //装载笔试时间
                item.setStartTime(examList.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getId(), tempExamPlace.getTeId())).findFirst().get().getStartTime());
                //装载考试类型
                item.setType(examList.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getId(), tempExamPlace.getTeId())).findFirst().get().getType());
                //装载考场
                item.setSceneName(examList.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getId(), tempExamPlace.getTeId())).findFirst().get().getName());
                //装载学生信息
                item.setZjhm(tssStudentInfo.getZjhm());
                item.setXh(tssStudentInfo.getXh());
                item.setSsxx(tssStudentInfo.getSsxx());
                item.setXl(tssStudentInfo.getXl());
                item.setXz(tssStudentInfo.getXz());
                item.setNz(tssStudentInfo.getNz());
                item.setYx(tssStudentInfo.getYx());
                item.setZy(tssStudentInfo.getZy());
                item.setBj(tssStudentInfo.getBj());
                item.setKssjhcs(tssStudentInfo.getKssjhcs());
                item.setBszkzh(tssStudentInfo.getBszkzh());
                item.setTime(tssStudentInfo.getTime());
                item.setBskmmc(tssStudentInfo.getBskmmc());
                //身份证解密
                String pid = item.getZjhm();
                if (pid != null) {
                    item.setZjhm(AESUtil.AESDecode(encodeRole, pid));
                }

            });
        }

        //查找所有教室内容
        List<String> classIds = studentSignTableVos.stream().map(StudentSignTableVo::getClassId).collect(Collectors.toList());
        List<TssClassInfo> tssClassInfos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(classIds)) {
            tssClassInfos = tssClassInfoDao.selectBatchIds(classIds);
        }

        //装载教室内容
        if (CollectionUtils.isNotEmpty(studentSignTableVos)) {

            List<TssClassInfo> finalTssClassInfos = tssClassInfos;
            studentSignTableVos.forEach(item -> {

                TssClassInfo tempExamPlace = finalTssClassInfos.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getId(), item.getClassId())).findFirst().get();
                item.setClassName(tempExamPlace.getName());
            });
        }

        //获取当前登录人员的学院的名称
        List<String> departments = Lists.newArrayList();
        String sysUserId = dataCenterService.getCurrentUserId();
        SysAdminDetail sysAdminDetail = sysAdminDetailDao.selectById(sysUserId);
        if (sysAdminDetail.getDeptId() != null) {
            SysDepartment currentSysDepartment = sysDepartmentDao.selectById(sysAdminDetail.getDeptId());
            departments.add(currentSysDepartment.getName());
        }

        //创建pdf
        //创建随机生成文件夹
        String fileName = IdUtils.getUUID();
        String filePath = exportFileLocalPath + cardForExamFilePath + fileName;
        File dir = new File(filePath.toString());
        String pdfFileName = null;
        try {
            FileUtils.forceMkdir(dir);
        } catch (Exception e) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeExamManagementReasonOfFailure.SAVE_FAILURE);
        }
            //遍历学院名 按照每个学院的信息创建pdf
            for (String department : departments) {
                List<StudentSignTableVo> branchNoticeList = studentSignTableVos.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getYx(), department)).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(branchNoticeList)) {
                    ExceptionUtil.setFailureMsgAndThrow(CollegeExamManagementReasonOfFailure.NO_LIST_TO_BE_DOWNLOAD);
                }
                List<List<PdfMessageListVo>> datas = Lists.newArrayList();
                int total = branchNoticeList.size();
                if (total % 3 == 1) {
                    total = total - 1;//减一的目的是让所有个数的准考证处理起来相同
                }
                if (total % 3 == 2) {
                    total = total - 2;//减二的目的是让所有个数的准考证处理起来相同
                }
                for (int count = 0; count < total; count = count + 3) {
                    List<PdfMessageListVo> data = Lists.newArrayList();
                    //装载这一页的信息（一页三个准考证）

                    PdfMessageListVo mainExamNameOne = new PdfMessageListVo();
                    mainExamNameOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[0]);
                    mainExamNameOne.setContent(exam.getName());
                    data.add(mainExamNameOne);
                    PdfMessageListVo stuNumberOne = new PdfMessageListVo();
                    stuNumberOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[1]);
                    stuNumberOne.setContent(branchNoticeList.get(count).getXh());
                    data.add(stuNumberOne);
                    PdfMessageListVo nameOne = new PdfMessageListVo();
                    nameOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[2]);
                    nameOne.setContent(branchNoticeList.get(count).getXm());
                    data.add(nameOne);
                    PdfMessageListVo examLanguageOne = new PdfMessageListVo();
                    examLanguageOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[3]);
                    examLanguageOne.setContent(branchNoticeList.get(count).getBskmmc());
                    data.add(examLanguageOne);
                    PdfMessageListVo collegeOne = new PdfMessageListVo();
                    collegeOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[4]);
                    collegeOne.setContent(branchNoticeList.get(count).getYx());
                    data.add(collegeOne);
                    PdfMessageListVo majorOne = new PdfMessageListVo();
                    majorOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[5]);
                    majorOne.setContent(branchNoticeList.get(count).getZy());
                    data.add(majorOne);
                    PdfMessageListVo examNumberOne = new PdfMessageListVo();
                    examNumberOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[6]);
                    examNumberOne.setContent(branchNoticeList.get(count).getBszkzh());
                    data.add(examNumberOne);
                    //中间不设置photo
                    if (branchNoticeList.get(count).getType() == 1) {
                        PdfMessageListVo penTimeOne = new PdfMessageListVo();
                        penTimeOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[8]);
                        penTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count).getStartTime()));
                        data.add(penTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[9]);
                        penAddressOne.setContent(branchNoticeList.get(count).getClassName());
                        data.add(penAddressOne);
                    }
                    if (branchNoticeList.get(count).getType() == 0) {
                        PdfMessageListVo computerTimeOne = new PdfMessageListVo();
                        computerTimeOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[10]);
                        computerTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count).getStartTime()));
                        data.add(computerTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[11]);
                        penAddressOne.setContent(branchNoticeList.get(count).getClassName());
                        data.add(penAddressOne);
                    }

                    PdfMessageListVo mainExamNameTwo = new PdfMessageListVo();
                    mainExamNameTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[0]);
                    mainExamNameTwo.setContent(exam.getName());
                    data.add(mainExamNameTwo);
                    PdfMessageListVo stuNumberTwo = new PdfMessageListVo();
                    stuNumberTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[1]);
                    stuNumberTwo.setContent(branchNoticeList.get(count + 1).getXh());
                    data.add(stuNumberTwo);
                    PdfMessageListVo nameTwo = new PdfMessageListVo();
                    nameTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[2]);
                    nameTwo.setContent(branchNoticeList.get(count + 1).getXm());
                    data.add(nameTwo);
                    PdfMessageListVo examLanguageTwo = new PdfMessageListVo();
                    examLanguageTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[3]);
                    examLanguageTwo.setContent(branchNoticeList.get(count + 1).getBskmmc());
                    data.add(examLanguageTwo);
                    PdfMessageListVo collegeTwo = new PdfMessageListVo();
                    collegeTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[4]);
                    collegeTwo.setContent(branchNoticeList.get(count + 1).getYx());
                    data.add(collegeTwo);
                    PdfMessageListVo majorTwo = new PdfMessageListVo();
                    majorTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[5]);
                    majorTwo.setContent(branchNoticeList.get(count + 1).getZy());
                    data.add(majorTwo);
                    PdfMessageListVo examNumberTwo = new PdfMessageListVo();
                    examNumberTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[6]);
                    examNumberTwo.setContent(branchNoticeList.get(count + 1).getBszkzh());
                    data.add(examNumberTwo);
                    //中间不设置photo
                    if (branchNoticeList.get(count + 1).getType() == 1) {
                        PdfMessageListVo penTimeTwo = new PdfMessageListVo();
                        penTimeTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[8]);
                        penTimeTwo.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count + 1).getStartTime()));
                        data.add(penTimeTwo);
                        PdfMessageListVo penAddressTwo = new PdfMessageListVo();
                        penAddressTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[9]);
                        penAddressTwo.setContent(branchNoticeList.get(count + 1).getClassName());
                        data.add(penAddressTwo);
                    }
                    if (branchNoticeList.get(count + 1).getType() == 0) {
                        PdfMessageListVo computerTimeTwo = new PdfMessageListVo();
                        computerTimeTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[10]);
                        computerTimeTwo.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count + 1).getStartTime()));
                        data.add(computerTimeTwo);
                        PdfMessageListVo penAddressTwo = new PdfMessageListVo();
                        penAddressTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[11]);
                        penAddressTwo.setContent(branchNoticeList.get(count + 1).getClassName());
                        data.add(penAddressTwo);
                    }

                    PdfMessageListVo mainExamNameThree = new PdfMessageListVo();
                    mainExamNameThree.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[0]);
                    mainExamNameThree.setContent(exam.getName());
                    data.add(mainExamNameThree);
                    PdfMessageListVo stuNumberThree = new PdfMessageListVo();
                    stuNumberThree.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[1]);
                    stuNumberThree.setContent(branchNoticeList.get(count + 2).getXh());
                    data.add(stuNumberThree);
                    PdfMessageListVo nameThree = new PdfMessageListVo();
                    nameThree.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[2]);
                    nameThree.setContent(branchNoticeList.get(count + 2).getXm());
                    data.add(nameThree);
                    PdfMessageListVo examLanguageThree = new PdfMessageListVo();
                    examLanguageThree.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[3]);
                    examLanguageThree.setContent(branchNoticeList.get(count + 2).getBskmmc());
                    data.add(examLanguageThree);
                    PdfMessageListVo collegeThree = new PdfMessageListVo();
                    collegeThree.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[4]);
                    collegeThree.setContent(branchNoticeList.get(count + 2).getYx());
                    data.add(collegeThree);
                    PdfMessageListVo majorThree = new PdfMessageListVo();
                    majorThree.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[5]);
                    majorThree.setContent(branchNoticeList.get(count + 2).getZy());
                    data.add(majorThree);
                    PdfMessageListVo examNumberThree = new PdfMessageListVo();
                    examNumberThree.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[6]);
                    examNumberThree.setContent(branchNoticeList.get(count + 2).getBszkzh());
                    data.add(examNumberThree);
                    //中间不设置photo
                    if (branchNoticeList.get(count + 2).getType() == 1) {
                        PdfMessageListVo penTimeThree = new PdfMessageListVo();
                        penTimeThree.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[8]);
                        penTimeThree.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count + 2).getStartTime()));
                        data.add(penTimeThree);
                        PdfMessageListVo penAddressThree = new PdfMessageListVo();
                        penAddressThree.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[9]);
                        penAddressThree.setContent(branchNoticeList.get(count + 2).getClassName());
                        data.add(penAddressThree);
                    }
                    if (branchNoticeList.get(count + 2).getType() == 0) {
                        PdfMessageListVo computerTimeThree = new PdfMessageListVo();
                        computerTimeThree.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[10]);
                        computerTimeThree.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count + 2).getStartTime()));
                        data.add(computerTimeThree);
                        PdfMessageListVo penAddressThree = new PdfMessageListVo();
                        penAddressThree.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[11]);
                        penAddressThree.setContent(branchNoticeList.get(count + 2).getClassName());
                        data.add(penAddressThree);
                    }
                    datas.add(data);
                }
                int lastMessage = branchNoticeList.size();
                if (lastMessage % 3 == 2) {
                    List<PdfMessageListVo> data = Lists.newArrayList();
                    PdfMessageListVo mainExamNameOne = new PdfMessageListVo();
                    mainExamNameOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[0]);
                    mainExamNameOne.setContent(exam.getName());
                    data.add(mainExamNameOne);
                    PdfMessageListVo stuNumberOne = new PdfMessageListVo();
                    stuNumberOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[1]);
                    stuNumberOne.setContent(branchNoticeList.get(lastMessage - 2).getXh());
                    data.add(stuNumberOne);
                    PdfMessageListVo nameOne = new PdfMessageListVo();
                    nameOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[2]);
                    nameOne.setContent(branchNoticeList.get(lastMessage - 2).getXm());
                    data.add(nameOne);
                    PdfMessageListVo examLanguageOne = new PdfMessageListVo();
                    examLanguageOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[3]);
                    examLanguageOne.setContent(branchNoticeList.get(lastMessage - 2).getBskmmc());
                    data.add(examLanguageOne);
                    PdfMessageListVo collegeOne = new PdfMessageListVo();
                    collegeOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[4]);
                    collegeOne.setContent(branchNoticeList.get(lastMessage - 2).getYx());
                    data.add(collegeOne);
                    PdfMessageListVo majorOne = new PdfMessageListVo();
                    majorOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[5]);
                    majorOne.setContent(branchNoticeList.get(lastMessage - 2).getZy());
                    data.add(majorOne);
                    PdfMessageListVo examNumberOne = new PdfMessageListVo();
                    examNumberOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[6]);
                    examNumberOne.setContent(branchNoticeList.get(lastMessage - 2).getBszkzh());
                    data.add(examNumberOne);
                    //中间不设置photo
                    if (branchNoticeList.get(lastMessage - 2).getType() == 1) {
                        PdfMessageListVo penTimeOne = new PdfMessageListVo();
                        penTimeOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[8]);
                        penTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 2).getStartTime()));
                        data.add(penTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[9]);
                        penAddressOne.setContent(branchNoticeList.get(lastMessage - 2).getClassName());
                        data.add(penAddressOne);
                    }
                    if (branchNoticeList.get(lastMessage - 2).getType() == 0) {
                        PdfMessageListVo computerTimeOne = new PdfMessageListVo();
                        computerTimeOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[10]);
                        computerTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 2).getStartTime()));
                        data.add(computerTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[11]);
                        penAddressOne.setContent(branchNoticeList.get(lastMessage - 2).getClassName());
                        data.add(penAddressOne);
                    }

                    PdfMessageListVo mainExamNameTwo = new PdfMessageListVo();
                    mainExamNameTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[0]);
                    mainExamNameTwo.setContent(exam.getName());
                    data.add(mainExamNameTwo);
                    PdfMessageListVo stuNumberTwo = new PdfMessageListVo();
                    stuNumberTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[1]);
                    stuNumberTwo.setContent(branchNoticeList.get(lastMessage - 1).getXh());
                    data.add(stuNumberTwo);
                    PdfMessageListVo nameTwo = new PdfMessageListVo();
                    nameTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[2]);
                    nameTwo.setContent(branchNoticeList.get(lastMessage - 1).getXm());
                    data.add(nameTwo);
                    PdfMessageListVo examLanguageTwo = new PdfMessageListVo();
                    examLanguageTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[3]);
                    examLanguageTwo.setContent(branchNoticeList.get(lastMessage - 1).getBskmmc());
                    data.add(examLanguageTwo);
                    PdfMessageListVo collegeTwo = new PdfMessageListVo();
                    collegeTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[4]);
                    collegeTwo.setContent(branchNoticeList.get(lastMessage - 1).getYx());
                    data.add(collegeTwo);
                    PdfMessageListVo majorTwo = new PdfMessageListVo();
                    majorTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[5]);
                    majorTwo.setContent(branchNoticeList.get(lastMessage - 1).getZy());
                    data.add(majorTwo);
                    PdfMessageListVo examNumberTwo = new PdfMessageListVo();
                    examNumberTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[6]);
                    examNumberTwo.setContent(branchNoticeList.get(lastMessage - 1).getBszkzh());
                    data.add(examNumberTwo);
                    //中间不设置photo
                    if (branchNoticeList.get(lastMessage - 1).getType() == 1) {
                        PdfMessageListVo penTimeTwo = new PdfMessageListVo();
                        penTimeTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[8]);
                        penTimeTwo.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 1).getStartTime()));
                        data.add(penTimeTwo);
                        PdfMessageListVo penAddressTwo = new PdfMessageListVo();
                        penAddressTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[9]);
                        penAddressTwo.setContent(branchNoticeList.get(lastMessage - 1).getClassName());
                        data.add(penAddressTwo);
                    }
                    if (branchNoticeList.get(lastMessage - 1).getType() == 0) {
                        PdfMessageListVo computerTimeTwo = new PdfMessageListVo();
                        computerTimeTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[10]);
                        computerTimeTwo.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 1).getStartTime()));
                        data.add(computerTimeTwo);
                        PdfMessageListVo penAddressTwo = new PdfMessageListVo();
                        penAddressTwo.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[11]);
                        penAddressTwo.setContent(branchNoticeList.get(lastMessage - 1).getClassName());
                        data.add(penAddressTwo);
                    }
                    datas.add(data);
                }
                if (lastMessage % 3 == 1) {

                    List<PdfMessageListVo> data = Lists.newArrayList();
                    PdfMessageListVo mainExamNameOne = new PdfMessageListVo();
                    mainExamNameOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[0]);
                    mainExamNameOne.setContent(exam.getName());
                    data.add(mainExamNameOne);
                    PdfMessageListVo stuNumberOne = new PdfMessageListVo();
                    stuNumberOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[1]);
                    stuNumberOne.setContent(branchNoticeList.get(lastMessage - 1).getXh());
                    data.add(stuNumberOne);
                    PdfMessageListVo nameOne = new PdfMessageListVo();
                    nameOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[2]);
                    nameOne.setContent(branchNoticeList.get(lastMessage - 1).getXm());
                    data.add(nameOne);
                    PdfMessageListVo examLanguageOne = new PdfMessageListVo();
                    examLanguageOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[3]);
                    examLanguageOne.setContent(branchNoticeList.get(lastMessage - 1).getBskmmc());
                    data.add(examLanguageOne);
                    PdfMessageListVo collegeOne = new PdfMessageListVo();
                    collegeOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[4]);
                    collegeOne.setContent(branchNoticeList.get(lastMessage - 1).getYx());
                    data.add(collegeOne);
                    PdfMessageListVo majorOne = new PdfMessageListVo();
                    majorOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[5]);
                    majorOne.setContent(branchNoticeList.get(lastMessage - 1).getZy());
                    data.add(majorOne);
                    PdfMessageListVo examNumberOne = new PdfMessageListVo();
                    examNumberOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[6]);
                    examNumberOne.setContent(branchNoticeList.get(lastMessage - 1).getBszkzh());
                    data.add(examNumberOne);
                    //中间不设置photo
                    if (branchNoticeList.get(lastMessage - 1).getType() == 1) {
                        PdfMessageListVo penTimeOne = new PdfMessageListVo();
                        penTimeOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[8]);
                        penTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 1).getStartTime()));
                        data.add(penTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[9]);
                        penAddressOne.setContent(branchNoticeList.get(lastMessage - 1).getClassName());
                        data.add(penAddressOne);
                    }
                    if (branchNoticeList.get(lastMessage - 1).getType() == 0) {
                        PdfMessageListVo computerTimeOne = new PdfMessageListVo();
                        computerTimeOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[10]);
                        computerTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 1).getStartTime()));
                        data.add(computerTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(CollegeExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[11]);
                        penAddressOne.setContent(branchNoticeList.get(lastMessage - 1).getClassName());
                        data.add(penAddressOne);
                    }
                    datas.add(data);
                }
                try {
                    String pdfFileCardForExamOnePath = String.valueOf(ResourceUtils.getURL("target/classes/pdfTemplates/cardforexamone.pdf"));
                    String pdfFileCardForExamTwoPath = String.valueOf(ResourceUtils.getURL("target/classes/pdfTemplates/cardforexamtwo.pdf"));
                    String pdfFileCardForExamThreePath = String.valueOf(ResourceUtils.getURL("target/classes/pdfTemplates/cardforexamthree.pdf"));
                    PDFForExamUtil.createPdf(datas, pdfFileCardForExamOnePath, pdfFileCardForExamTwoPath, pdfFileCardForExamThreePath, filePath, department + ".pdf");
                pdfFileName = downloadFileWebPath + cardForExamFilePath + fileName + '/' + department + ".pdf";
                } catch (Exception e) {
                    ExceptionUtil.setFailureMsgAndThrow(CollegeExamManagementReasonOfFailure.SAVE_FAILURE);
                }
            }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "pdfFileName", pdfFileName);

        // 记录日志
        String operationContent = String.format(LogConstant.GET_CARD_FOR_EXAM_LIST, exam.getName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }
}
