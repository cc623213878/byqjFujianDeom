package com.byqj.module.examManagement.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.constant.CommonConstant;
import com.byqj.dao.TssExamPlaceDao;
import com.byqj.dao.TssSolicitationTimeDao;
import com.byqj.entity.TssClassInfo;
import com.byqj.entity.TssExam;
import com.byqj.entity.TssExamPlace;
import com.byqj.entity.TssIdPool;
import com.byqj.entity.TssSolicitationTime;
import com.byqj.entity.TssTempPostPerson;
import com.byqj.module.dataKeepPostManagement.enums.DataKeepPostManagementReasonOfFailure;
import com.byqj.module.examManagement.constant.ExamManagementConstant;
import com.byqj.module.examManagement.enums.ExamManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.service.IClassInfoService;
import com.byqj.service.IExamPlaceService;
import com.byqj.service.IExamService;
import com.byqj.service.IIdPoolService;
import com.byqj.service.ISeatOrderService;
import com.byqj.service.ISolicitationTimeService;
import com.byqj.service.ITempPostPersonService;
import com.byqj.service.ITempSeatScheduleService;
import com.byqj.utils.CheckVariableUtil;
import com.byqj.utils.ExceptionUtil;
import com.byqj.utils.IdUtils;
import com.byqj.utils.LevelUtil;
import com.byqj.vo.AddPostVo;
import com.byqj.vo.ExamListVo;
import com.byqj.vo.PostFreeVo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExamManagementCheckService {


    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private IExamService examService;
    @Autowired
    private TssSolicitationTimeDao tssSolicitationTimeDao;
    @Autowired
    private IExamPlaceService examPlaceService;
    @Autowired
    private ITempPostPersonService tempPostPersonService;
    @Autowired
    private ITempSeatScheduleService tempSeatScheduleService;
    @Autowired
    private ISeatOrderService seatOrderService;
    @Autowired
    private IIdPoolService idPoolService;
    @Autowired
    private IClassInfoService classInfoService;
    @Autowired
    private ISolicitationTimeService solicitationTimeService;
    @Autowired
    private TssExamPlaceDao tssExamPlaceDao;


//    private void checkPageInfo() {
//        int pageNum;
//        int pageSize;
//        try {
//            pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.PAGE_NUM);
//            pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.PAGE_SIZE);
//        } catch (Exception e) {
//            pageNum = 1;
//            pageSize = 0;
//        }
//
//        dataCenterService.setData(ExamManagementConstant.PAGE_NUM, pageNum);
//        dataCenterService.setData(ExamManagementConstant.PAGE_SIZE, pageSize);
//    }

    private List<Date> myParseDate(List<String> time, String parsePattern) {
        List<Date> dates = new ArrayList<Date>();
        try {
            for (String e : time) {
                dates.add(DateUtils.parseDate(e, parsePattern));
            }
            return dates;
        } catch (Exception e) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_BLANK);
        }
        return dates;
    }

    private void checkMainExId() {
        String mainExamId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.MAIN_EXAM_ID);
        if (StringUtils.isBlank(mainExamId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_BLANK);
        }
        dataCenterService.setData(ExamManagementConstant.MAIN_EXAM_ID, mainExamId.trim());
    }

    private void addExamInfoCheck() {
        String exName = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXNAME);
        Integer exType = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXTYPE);
        List<String> time = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STIME);
        String remark = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.REMARK);

        JSONArray exListJson = dataCenterService.
                getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_LIST);
        List<ExamListVo> exList = exListJson.toJavaList(ExamListVo.class);

        if (CollectionUtils.isEmpty(time) || time.size() < 2
                || time.get(0).compareTo(time.get(1)) > 0) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_BLANK);
            return;
        }
        if (StringUtils.isBlank(exName)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXNAME_BLANK);
            return;
        }

        if (exType == null || exType < 0 || exType > 1) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_TYPE_ERROR);
        }

        List<Date> mainTime = myParseDate(time, CommonConstant.TIME_PATTERN);
        List<TssExam> exams = new ArrayList<TssExam>();
        List<TssExamPlace> examPlaces = new ArrayList<TssExamPlace>();

        TssExam mainExam = new TssExam();
        String id = IdUtils.getUUID();
        mainExam.setId(id);
        mainExam.setName(exName);
        mainExam.setType(exType);
        mainExam.setStartTime(mainTime.get(0));
        mainExam.setEndTime(mainTime.get(1));
        mainExam.setRemark(remark);
        exams.add(mainExam);

        for (ExamListVo eVo : exList) {
            List<String> nameOrSeq = eVo.getCode();
            List<String> timeList = eVo.getSTime();
            if (nameOrSeq.isEmpty()) {
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PLACE_BLANK);
                return;
            }
            if (CollectionUtils.isEmpty(timeList) || timeList.size() < 2) {
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_BLANK);
                return;
            }

            List<Date> exTime = myParseDate(timeList, CommonConstant.TIME_PATTERN);
            Date startTime = exTime.get(0);
            Date endTime = exTime.get(1);
            // get(0)=startTime, get(1)=endTime
            if (startTime.before(mainExam.getStartTime()) || startTime.after(mainExam.getEndTime())
                    || endTime.before(mainExam.getStartTime()) || endTime.after(mainExam.getEndTime())) {
                exams.clear();
                examPlaces.clear();
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_EXCESS);
                return;
            }

            TssExam te = new TssExam();
            te.setId(IdUtils.getUUID());
            te.setName(eVo.getExName());
            te.setType(exType);
            te.setStartTime(startTime);
            te.setEndTime(endTime);
            te.setParentId(id);
            te.setLevel(LevelUtil.calculateLevel(LevelUtil.ROOT, id));
            te.setPlaceCodeStr(String.join(CommonConstant.COMMA, nameOrSeq));
            exams.add(te);

            nameOrSeq.forEach(e -> {
                TssExamPlace tep = new TssExamPlace();
                tep.setId(IdUtils.getUUID());
                tep.setNameOrSeq(Integer.parseInt(e));
                tep.setTeId(te.getId());
                tep.setParentId(te.getId());
                tep.setLevel(LevelUtil.calculateLevel(te.getLevel(), te.getId()));
                tep.setStudentCount(-1);

                examPlaces.add(tep);
            });
        }

        dataCenterService.setData(ExamManagementConstant.OPER, ExamManagementConstant.ADD);
        dataCenterService.setData(ExamManagementConstant.EXAMS, exams);
        dataCenterService.setData(ExamManagementConstant.EXAM_PLACES, examPlaces);
        dataCenterService.setData(ExamManagementConstant.MAIN_EXAM_ID, mainExam.getId());

    }

    @Transactional
    protected void updateExamInfoCheck(String mainExamId) {
        String exName = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXNAME);
        Integer exType = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXTYPE);
        List<String> time = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STIME);
        String remark = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.REMARK);
        JSONArray exListJson = dataCenterService.
                getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_LIST);
        List<ExamListVo> exList = exListJson.toJavaList(ExamListVo.class);


        if (CollectionUtils.isEmpty(time) || time.size() < 2
                || time.get(0).compareTo(time.get(1)) > 0) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_BLANK);
            return;
        }
        if (StringUtils.isBlank(exName)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXNAME_BLANK);
        }

        List<Date> mainTime = myParseDate(time, CommonConstant.TIME_PATTERN);
        List<TssExam> updateExams = new ArrayList<TssExam>();
        List<TssExam> addExams = new ArrayList<TssExam>();
        List<TssExamPlace> examPlaces = new ArrayList<TssExamPlace>();

        TssExam mainExam = examService.getById(mainExamId);
        if (mainExam.getStatus() != 0) { // 已归档
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PARAM_ERROR);
            return;
        }

        mainExam.setName(exName);
        mainExam.setType(exType);
        mainExam.setStartTime(mainTime.get(0));
        mainExam.setEndTime(mainTime.get(1));
        mainExam.setRemark(remark);
        updateExams.add(mainExam);

        for (ExamListVo eVo : exList) {
            List<String> codeStrList = eVo.getCode();
            List<String> timeList = eVo.getSTime();
            if (codeStrList.isEmpty()) {
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PLACE_BLANK);
                return;
            }
            if (CollectionUtils.isEmpty(timeList) || timeList.size() < 2) {
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_BLANK);
                return;
            }

            List<Date> exTime = myParseDate(timeList, CommonConstant.TIME_PATTERN);

            // get(0)=startTime, get(1)=endTime
            Date startTime = exTime.get(0);
            Date endTime = exTime.get(1);
            if (startTime.before(mainExam.getStartTime()) || startTime.after(mainExam.getEndTime())
                    || endTime.before(mainExam.getStartTime()) || endTime.after(mainExam.getEndTime())) {
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_EXCESS);
                return;
            }

            List<Integer> codeIntList = codeStrList.stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            TssExam te = new TssExam();
            te.setId(eVo.getExId());
            te.setName(eVo.getExName());
            te.setType(exType);
            te.setStartTime(startTime);
            te.setEndTime(endTime);
            te.setParentId(mainExamId);
            te.setLevel(LevelUtil.calculateLevel(LevelUtil.ROOT, mainExamId));
            te.setPlaceCodeStr(String.join(CommonConstant.COMMA, codeStrList));

            if (StringUtils.isBlank(eVo.getExId())) {
                // 添加新的子考试
                te.setId(IdUtils.getUUID());
                addExams.add(te);
                codeIntList.forEach(place -> {
                    TssExamPlace addTep = new TssExamPlace();
                    addTep.setId(IdUtils.getUUID());
                    addTep.setNameOrSeq(place);
                    addTep.setTeId(te.getId());
                    addTep.setParentId(te.getId());
                    addTep.setLevel(LevelUtil.calculateLevel(te.getLevel(), te.getId()));
                    addTep.setStudentCount(-1);
                    examPlaces.add(addTep);
                });
            } else {
                // 更新已存在考试
                updateExams.add(te);
                // 获取该考试下，所有主考点信息
                List<TssExamPlace> mainExPlace = examPlaceService.selectMainPlaceByTeId(eVo.getExId());
                // 判断考点是新增、删除
                if (mainExPlace.size() > codeIntList.size()) { // del
                    List<Integer> nameNum = mainExPlace.stream()
                            .map(TssExamPlace::getNameOrSeq)
                            .collect(Collectors.toList());
                    List<String> readyDelIds = new ArrayList<String>();
                    // 获取删除考点的名称序号
                    codeIntList = (List<Integer>) CollectionUtils.subtract(nameNum, codeIntList);
                    for (Integer i : codeIntList) {
                        for (TssExamPlace e : mainExPlace) {
                            if (!e.getNameOrSeq().equals(i)) {
                                continue;
                            }
                            // 获取该考点及其子考场id
                            List<TssExamPlace> del = examPlaceService.getSubExamPlace(Lists.newArrayList(e.getId()));
                            List<String> delIds = del.stream().map(TssExamPlace::getId).collect(Collectors.toList());
                            readyDelIds.addAll(delIds);
                            readyDelIds.add(e.getId());
                        }
                    }
                    // 删除岗位安排信息 tss_temp_post_person
                    tempPostPersonService.delByTepIdsInTemp(readyDelIds);
                    // 删除座位安排信息 tss_seat_order
                    seatOrderService.removeByIds(readyDelIds);
                    // 重置报名岗位人员信息 tss_temp_submit_person
//                        tempSubmitPersonService.resetTeIdByTeIdsInTemp(Lists.newArrayList(mainExamId));
                    // 重置学生安排信息 tss_temp_seat_schedule
                    tempSeatScheduleService.resetTepIdAndSeatNumInTemp(readyDelIds);
                    // 删除考点及其子考场
                    examPlaceService.removeByIds(readyDelIds);

                } else if (mainExPlace.size() < codeIntList.size()) {
                    // add
                    List<Integer> nameNum = mainExPlace.stream()
                            .map(TssExamPlace::getNameOrSeq)
                            .collect(Collectors.toList());
                    codeIntList = (List<Integer>) CollectionUtils.subtract(codeIntList, nameNum);

                    for (Integer i : codeIntList) {
                        TssExamPlace tep = new TssExamPlace();
                        tep.setId(IdUtils.getUUID());
                        tep.setNameOrSeq(i);
                        tep.setTeId(te.getId());
                        tep.setParentId(te.getId());
                        tep.setLevel(LevelUtil.calculateLevel(te.getLevel(), te.getId()));
                        tep.setStudentCount(-1);
                        examPlaces.add(tep);
                    }
                }
            }
        }


        dataCenterService.setData(ExamManagementConstant.OPER, "update");
        dataCenterService.setData(ExamManagementConstant.UPDATE_EXAMS, updateExams);
        dataCenterService.setData(ExamManagementConstant.ADD_EXAMS, addExams);
        dataCenterService.setData(ExamManagementConstant.EXAM_PLACES, examPlaces);
        dataCenterService.setData(ExamManagementConstant.MAIN_EXAM_ID, mainExam.getId());

    }

    private void addExamPlaceInfoCheck() {
        // add
        String examId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID);
        String examPlaceId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_ID);
        int stuNum = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STU_NUM);
        String classId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.CLASSID);

        String mainExamId;
        if (stuNum <= 0) { // 学生数至少为1
            stuNum = 1;
        }

        // 检查归档
        TssExam exam = examService.getById(examId);
        if (exam.getStatus() != 0) { // 已归档
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PARAM_ERROR);
            return;
        }

        TssExamPlace examPlace = examPlaceService.getById(examPlaceId);
        if (StringUtils.isNotBlank(classId)) {
            // 教室是否符合考点位置
            TssClassInfo classInfo = classInfoService.getById(classId);
            if (!classInfo.getPlace().equals(examPlace.getNameOrSeq())) {
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.CLASS_PLACE_ERROR);
                return;
            }
            // 检查教室容量是否足够
            int stuCount = examPlaceService.getStuCountByTeIdAndTciId(examId, classId);
            if (stuCount + stuNum > classInfo.getCapacity()) {
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NUMBER_OF_PEOPLE_EXCEEDS_CAPACITY);
                return;
            }
        } else {
            classId = "";
        }

        // 获取主考试id
        // level = "0.mainExamId.examId
        String exPLevel = examPlace.getLevel();
        List<String> idS = Splitter.on(".").splitToList(exPLevel);
        mainExamId = idS.get(1);

        // 通过主考试id获得考场id
        TssIdPool id = idPoolService.getAndUpdateCountById(mainExamId);
        // 新增考场
        TssExamPlace examCLass = new TssExamPlace();
        String tepId = IdUtils.getId(id.getTeStartTime(), id.getExamNum(), id.getCount());
        examCLass.setId(tepId);
        // 考场号在insert时设置
//            examCLass.setNameOrSeq(exPlaceNum);
        examCLass.setTeId(examId);
        examCLass.setTciId(classId);
        examCLass.setParentId(examPlaceId);
        examCLass.setLevel(LevelUtil.calculateLevel(examPlace.getLevel(), examPlaceId));
        examCLass.setStudentCount(stuNum);


        dataCenterService.setData(ExamManagementConstant.OPER, ExamManagementConstant.ADD);
        dataCenterService.setData(ExamManagementConstant.EXAM_CLASS, examCLass);
    }

    private void updateExamPlaceInfoCheck(String examClassId) {
        // 考生数
        int stuNum = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STU_NUM);
        String classId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.CLASSID);
        String examPlaceId = null;

        // 检查考场id合法性是否存在
        TssExamPlace examClass = examPlaceService.getById(examClassId);
        if (examClass == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_PLACE_ID_IS_BLANK);
            return;
        }

        // "0.mainExamId.examId.examPlaceId"
        String exPLevel = examClass.getLevel();
        List<String> idS = Splitter.on(".").splitToList(exPLevel);
        examPlaceId = idS.get(3);

        // 检查归档
        TssExam mainExam = examService.getById(idS.get(1));
        if (mainExam.getStatus() != 0) { // 已归档
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PARAM_ERROR);
            return;
        }

        if (StringUtils.isNotBlank(classId)) {
            // 教室是否符合考点位置
            TssClassInfo classInfo = classInfoService.getById(classId);
            TssExamPlace examPlace = examPlaceService.getById(examPlaceId);
            if (!classInfo.getPlace().equals(examPlace.getNameOrSeq())) {
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.CLASS_PLACE_ERROR);
                return;
            }
        } else {
            classId = "";
        }

        // 更新考场信息
        examClass.setStudentCount(stuNum);
        examClass.setTciId(classId);

        dataCenterService.setData(ExamManagementConstant.OPER, "u");
        dataCenterService.setData(ExamManagementConstant.EXAM_CLASS, examClass);
    }

    private void addPostCheck() {

        JSONArray postIdNumListJson = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.POST_ID_AND_NUM);
        String examPlaceOrClassId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_OR_CLASS_ID);

        List<AddPostVo> addPostList = postIdNumListJson.toJavaList(AddPostVo.class);

        if (CollectionUtils.isNotEmpty(addPostList)) {
            // 去重
            addPostList = addPostList.stream().distinct().collect(Collectors.toList());
        }
        if (StringUtils.isBlank(examPlaceOrClassId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PLACE_BLANK);
            return;
        }

        dataCenterService.setData(ExamManagementConstant.POST_ID_AND_NUM, addPostList);
        dataCenterService.setData(ExamManagementConstant.EXAM_PLACE_OR_CLASS_ID, examPlaceOrClassId);
        dataCenterService.setData(ExamManagementConstant.OPER, ExamManagementConstant.ADD);
    }

    private void updatePostCheck(String id) {
        int number = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.POST_NUM);
        String examPlaceOrClassId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_OR_CLASS_ID);

        if (StringUtils.isBlank(examPlaceOrClassId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NULL);
            return;
        }

        boolean result = tempPostPersonService.postIsExist(id);
        if (!result) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NULL);
            return;
        }

        dataCenterService.setData(ExamManagementConstant.POST_NUM, number);
        dataCenterService.setData(ExamManagementConstant.ID, id);
        dataCenterService.setData(ExamManagementConstant.EXAM_PLACE_OR_CLASS_ID, examPlaceOrClassId);
    }

    // set or update exam info check
    public void setExamInfoRequestCheck() {

        String mainExamId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.MAIN_EXAM_ID);
        if (StringUtils.isBlank(mainExamId)) {
            addExamInfoCheck();
        } else {
            updateExamInfoCheck(mainExamId);
        }

    }

    // get exam info check
    public void getExamInfoRequestCheck() {
        checkMainExId();
    }

    // set or update exam place check
    public void setExamPlaceInfoRequestCheck() {
        String examClassId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_CLASS_ID);
        if (StringUtils.isBlank(examClassId)) {
            addExamPlaceInfoCheck();
        } else {
            updateExamPlaceInfoCheck(examClassId);
        }
    }

    // get exam place check
    public void getExamPlaceInfoRequestCheck() {
        String examId =
                dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID);
        int status = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STATUS);
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_BLANK);
        }
    }

    // add or update post check
    public void setPostRequestCheck() {
        String id =
                dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.ID);
        if (StringUtils.isBlank(id)) {
            // add
            addPostCheck();
        } else {
            // update
            updatePostCheck(id);
        }
    }

    // set main class check
    public void setMainClassRequestCheck() {
        String exPlaceId
                = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_ID);
        String classId
                = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.CLASSID);

        // 检查考场id合法性是否存在
        TssExamPlace examPlace = examPlaceService.getById(exPlaceId);
        if (examPlace == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_PLACE_ID_IS_BLANK);
            return;
        }
        if (StringUtils.isBlank(classId)) {
            classId = "";
        }
        examPlace.setTciId(classId);

        dataCenterService.setData(ExamManagementConstant.EXAM_PLACE, examPlace);
    }

    // get free class check
    public void getFreeClassRequestCheck() {
        String exPlaceId
                = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_ID);
        int exType
                = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXTYPE);
        if (StringUtils.isBlank(exPlaceId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_PLACE_ID_IS_BLANK);
        }
    }

    // get exam list check
    public void getExamRequestCheck() {
        int status
                = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STATUS);
        List<String> time = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STIME);

        String sTime = CommonConstant.MIN_TIME_STRING;
        String eTime = CommonConstant.MAX_TIME_STRING;
        if (CollectionUtils.isNotEmpty(time)) {

            if (StringUtils.isNotBlank(time.get(0))) {
                sTime = time.get(0);
            }
            if (StringUtils.isNotBlank(time.get(1))) {
                eTime = time.get(1);
            }
        }

        dataCenterService.setData(ExamManagementConstant.STATUS, status);
        dataCenterService.setData(ExamManagementConstant.STIME, sTime);
        dataCenterService.setData(ExamManagementConstant.ETIME, eTime);
    }

    // delete post check
    public void delPostRequestCheck() {
        String id = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.ID);
        if (StringUtils.isBlank(id)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.POST_PERSON_ID_NULL);
        }
    }

    // delete exam class check
    public void delExamClassRequestCheck() {
        String exCLassId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_CLASS_ID);
        if (StringUtils.isBlank(exCLassId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_PLACE_ID_IS_BLANK);
        }
    }

    // delete exam check
    public void delExamRequestCheck() {
        checkMainExId();
    }

    //
    public void setSolicitationTimeRequestCheck() {
        String mainExId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.MAIN_EXAM_ID);
        List<String> time = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STIME);

        if (CollectionUtils.isEmpty(time)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_BLANK);
            return;
        }
        String sTime = time.get(0);
        String eTime = time.get(1);
        if (StringUtils.isBlank(mainExId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_BLANK);
            return;
        }
        if (StringUtils.isBlank(sTime) || StringUtils.isBlank(eTime)
                || sTime.compareTo(eTime) > 0) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_BLANK);
            return;
        }

        dataCenterService.setData(ExamManagementConstant.MAIN_EXAM_ID, mainExId);
        dataCenterService.setData(ExamManagementConstant.STIME, sTime);
        dataCenterService.setData(ExamManagementConstant.ETIME, eTime);
    }


    public void setReportTimeRequestCheck() {
        try {

            String mainExId = dataCenterService
                    .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.MAIN_EXAM_ID);
            int sTime = dataCenterService
                    .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STIME);
            int eTime = dataCenterService
                    .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.ETIME);

            dataCenterService.setData(ExamManagementConstant.MAIN_EXAM_ID, mainExId);
            dataCenterService.setData(ExamManagementConstant.STIME, sTime);
            dataCenterService.setData(ExamManagementConstant.ETIME, eTime);
        } catch (Exception e) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_BLANK);
        }

    }

    // export account check
    public void exportAccountRequestCheck() {
        checkMainExId();
    }

    public void fileExamRequestCheck() {
        checkMainExId();
    }

    /************************************************************************************************************/


    public void getUnscheduledExamineeRequestCheck() {
        String mainExamId
                = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.MAIN_EXAM_ID);
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.PAGE_NUM);
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.PAGE_SIZE);

        if (StringUtils.isBlank(mainExamId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.MAIN_EXAM_ID_IS_BLANK);
        }

        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PAGE_PARAM_IS_ILLEGAL);
        }

        TssExam exam = examService.getById(mainExamId);
        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_DOES_NOT_EXIST);
        }

        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus())) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.THE_EXAM_HAS_BEEN_ARCHIVED_AND_CANNOT_BE_MODIFIED);
        }

        dataCenterService.setData(ExamManagementConstant.MAIN_EXAM_ID, mainExamId.trim());
        dataCenterService.setData(ExamManagementConstant.PAGE_NUM, pageNum);
        dataCenterService.setData(ExamManagementConstant.PAGE_SIZE, pageSize);
    }

    /**
     * 安排考生
     */
    public void arrangeExamineeRequestCheck() {

        String examPlaceId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_ID);
        String examineeIdStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAMINEE_ID_STR);

        if (StringUtils.isBlank(examPlaceId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_PLACE_ID_IS_BLANK);
        }
        List<String> examineeIdList = new ArrayList<>();

        if (examineeIdStr != null) {
            examineeIdList = Splitter.on(",").omitEmptyStrings().splitToList(examineeIdStr);
        }
        //检测是否在规定时间内

        TssExamPlace tssExamPlace = tssExamPlaceDao.selectOne(new LambdaQueryWrapper<TssExamPlace>().eq(TssExamPlace::getId, examPlaceId));//通过考点获取场次
        TssExam exam = examService.getById(tssExamPlace.getTeId()); // 通过场次获取考试
        TssSolicitationTime tssSolicitationTime = tssSolicitationTimeDao.selectOne(new LambdaQueryWrapper<TssSolicitationTime>().eq(TssSolicitationTime::getTeId, exam.getParentId()));

        if (tssSolicitationTime.getDeptId() > 0) {
            Date date = new Date();
            if (date.before(tssSolicitationTime.getStudentTimeEnd())) {
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_BEFORE_IMPORT_END);
            }
        }


        //检测考场内的考生是否超过限额
        TssExamPlace examPlace = examPlaceService.getById(examPlaceId);
        if (examineeIdList.size() > examPlace.getStudentCount()) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NUMBER_OF_PEOPLE_EXCEEDS_CAPACITY);
        }

        dataCenterService.setData(ExamManagementConstant.EXAM_PLACE_ID, examPlaceId.trim());
        dataCenterService.setData("examPlace", examPlace);
        dataCenterService.setData(ExamManagementConstant.EXAMINEE_ID_LIST, new ArrayList<>(examineeIdList));
    }

    /**
     * 获取待安排的考务人员与已安排的考务人员
     */
    public void getUnscheduledPersonRequestCheck() {
        String mainExamId
                = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.MAIN_EXAM_ID);
        String examPlaceId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_ID);//考场或考点
        String postId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.POST_ID);

        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.PAGE_NUM);
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.PAGE_SIZE);

        if (StringUtils.isBlank(mainExamId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.MAIN_EXAM_ID_IS_BLANK);
        }

        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PAGE_PARAM_IS_ILLEGAL);
        }


        if (StringUtils.isBlank(examPlaceId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_PLACE_ID_IS_BLANK);
        }

        if (StringUtils.isBlank(postId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.POST_NAME_IS_EMPTY);
        }

        TssExam exam = examService.getById(mainExamId);
        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_DOES_NOT_EXIST);
        }

        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus())) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.THE_EXAM_HAS_BEEN_ARCHIVED_AND_CANNOT_BE_MODIFIED);
        }

        TssExamPlace examPlace = examPlaceService.getOne(new LambdaQueryWrapper<TssExamPlace>().eq(TssExamPlace::getId, examPlaceId));

        if (examPlace == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_PLACE_DOES_NOT_EXIST);
        }

        dataCenterService.setData("examPlace", examPlace);
        dataCenterService.setData(ExamManagementConstant.POST_ID, postId.trim());
        dataCenterService.setData(ExamManagementConstant.PAGE_NUM, pageNum);
        dataCenterService.setData(ExamManagementConstant.PAGE_SIZE, pageSize);
    }

    /**
     * 安排岗位
     */
    public void arrangePostRequestCheck() {
        String examPlaceId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_ID);
        String postId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.POST_ID);
        String personIdStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.PERSON_ID_STR);
        personIdStr = personIdStr == null ? "" : personIdStr;

        TssExamPlace tssExamPlace = tssExamPlaceDao.selectOne(new LambdaQueryWrapper<TssExamPlace>().eq(TssExamPlace::getId, examPlaceId));//通过考点获取场次
        TssExam exam = examService.getById(tssExamPlace.getTeId()); // 通过场次获取考试
        TssSolicitationTime tssSolicitationTime = tssSolicitationTimeDao.selectOne(new LambdaQueryWrapper<TssSolicitationTime>().eq(TssSolicitationTime::getTeId, exam.getParentId()));

        Date date = new Date();

        if (date.before(tssSolicitationTime.getPostTimeEnd())) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_BEFORE_POST_TIME_END);
        }

        if (StringUtils.isBlank(examPlaceId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_PLACE_ID_IS_BLANK);
        }

        if (StringUtils.isBlank(postId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.POST_NAME_IS_EMPTY);
        }

//        if (StringUtils.isBlank(personIdStr)) {
//            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NO_STAFF_UNDER_THE_POST);
//        }
        List<String> personIdList = new ArrayList<>();

        if (StringUtils.isNotBlank(personIdStr)) {
            personIdList = Splitter.on(",").omitEmptyStrings().splitToList(personIdStr);

            TssTempPostPerson postPerson = tempPostPersonService.getOne(new LambdaQueryWrapper<TssTempPostPerson>().eq(TssTempPostPerson::getId, postId));

            if (personIdList.size() > postPerson.getPersonNum()) {
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.THE_NUMBER_EXCEEDS_THE_LIMIT);
            }
        }


        //todo 对传上来的人员做检测，查看当前场次下的人员有没有安排过，参看查看的接口

        dataCenterService.setData(ExamManagementConstant.PERSON_ID_STR, personIdStr.trim());
        dataCenterService.setData(ExamManagementConstant.EXAM_PLACE_ID, examPlaceId.trim());
        dataCenterService.setData(ExamManagementConstant.POST_ID, postId);
    }


    /**
     * 获取考点岗位信息
     */
    public void getExamPlacePostInfoRequestCheck() {
        Integer status = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STATUS);
        String examPlaceId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_ID);
        if (StringUtils.isBlank(examPlaceId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_PLACE_ID_IS_BLANK);
        }
        if (!(ExamManagementConstant.UNFILED_STATUS.equals(status) || ExamManagementConstant.ARCHIVE_STATUS.equals(status))) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.STATUS_ERROR);
        }
        dataCenterService.setData(ExamManagementConstant.STATUS, status);
        dataCenterService.setData(ExamManagementConstant.EXAM_PLACE_ID, examPlaceId.trim());
    }

    /**
     * 通过考试场次id获取考点信息getExamPlacePostInfoRequestCheck
     */
    public void getExamPlaceByExamIdRequestCheck() {
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID);
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }
        dataCenterService.setData(ExamManagementConstant.EXAM_ID, examId.trim());
    }


    /**
     * 获取岗位费用设置
     */
    public void getPostFreeSetRequestCheck() {
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID);// 考试id

        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }

        TssExam exam = examService.getById(examId);

        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_DOES_NOT_EXIST);
        }

        dataCenterService.setData("exam", exam);
        dataCenterService.setData(ExamManagementConstant.EXAM_ID, examId.trim());
    }

    /**
     * 设置岗位费用
     */
    public void setPostFreeRequestCheck() {
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID);
        JSONArray postFreeArray = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.POST_FREE_LIST);

        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }

        if (CollectionUtils.isEmpty(postFreeArray)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.POST_FREE_IS_EMPTY);
        }
        TssExam exam = examService.getById(examId);
        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_DOES_NOT_EXIST);
        }

        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus())) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.THE_EXAM_HAS_BEEN_ARCHIVED_AND_CANNOT_BE_MODIFIED);
        }

        List<PostFreeVo> postFreeVoList = postFreeArray.toJavaList(PostFreeVo.class);

        dataCenterService.setData(ExamManagementConstant.EXAM_ID, examId.trim());
        dataCenterService.setData(ExamManagementConstant.POST_FREE_LIST, postFreeVoList);
    }

    /**
     * 获取岗位费用
     */
    public void getPostFreeRequestCheck() {
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID);
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }

        TssExam exam = examService.getById(examId);
        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_DOES_NOT_EXIST);
        }
        dataCenterService.setData(ExamManagementConstant.EXAM_ID, examId.trim());
        dataCenterService.setData("exam", exam);
    }

    /**
     * 下载岗位人员费用表
     */
    public void downloadPersonFreeRequestCheck(HttpServletRequest request) {
        String examId = request.getParameter(ExamManagementConstant.EXAM_ID);
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }

        TssExam exam = examService.getById(examId);
        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_DOES_NOT_EXIST);
        }
        dataCenterService.setData(ExamManagementConstant.EXAM_ID, examId.trim());
        dataCenterService.setData("exam", exam);
    }

    /**
     * 获取岗位安排信息表
     */
    public void getPostSchedulingInfoRequestCheck() {
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID);
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }

        TssExam exam = examService.getById(examId);
        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_DOES_NOT_EXIST);
        }
        dataCenterService.setData(ExamManagementConstant.EXAM_ID, examId.trim());
        dataCenterService.setData("exam", exam);
    }

    /**
     * 准考证打印设置
     */
    public void admissionTicketDownloadSettingRequestCheck() {
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID);
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }

        String startTimeStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.START_TIME);
        String endTimeStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.END_TIME);

        if (StringUtils.isBlank(startTimeStr)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.START_TIME_IS_BLANK);
        }
        if (StringUtils.isBlank(endTimeStr)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.END_TIME_IS_BLANK);
        }

        TssSolicitationTime solicitationTime = solicitationTimeService.getById(examId);
        if (solicitationTime == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_DOES_NOT_EXIST);
        }
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = DateUtils.parseDate(startTimeStr, CommonConstant.TIME_PATTERN);
            endTime = DateUtils.parseDate(endTimeStr, CommonConstant.TIME_PATTERN);
        } catch (ParseException e) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.OPERATOR_ERROR);
            e.printStackTrace();
        }


        dataCenterService.setData("solicitationTime", solicitationTime);

        dataCenterService.setData(ExamManagementConstant.START_TIME, startTime);
        dataCenterService.setData(ExamManagementConstant.END_TIME, endTime);
    }

    /**
     * 导入学生数据设置
     */
    public void importStudentInfoSettingRequestCheck() {
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID);
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }

        String depIdStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.DEP_ID);

        if (StringUtils.isBlank(depIdStr)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.DEP_ID_IS_BLANK);
        }

        Long depId = Longs.tryParse(depIdStr);

        String startTimeStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.START_TIME);
        String endTimeStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.END_TIME);

        if (StringUtils.isBlank(startTimeStr)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.START_TIME_IS_BLANK);
        }
        if (StringUtils.isBlank(endTimeStr)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.END_TIME_IS_BLANK);
        }

        TssSolicitationTime solicitationTime = solicitationTimeService.getById(examId);
        if (solicitationTime == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_DOES_NOT_EXIST);
        }
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = DateUtils.parseDate(startTimeStr, CommonConstant.TIME_PATTERN);
            endTime = DateUtils.parseDate(endTimeStr, CommonConstant.TIME_PATTERN);
        } catch (ParseException e) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.OPERATOR_ERROR);
            e.printStackTrace();
        }


        dataCenterService.setData("solicitationTime", solicitationTime);
        dataCenterService.setData(ExamManagementConstant.DEP_ID, depId);
        dataCenterService.setData(ExamManagementConstant.START_TIME, startTime);
        dataCenterService.setData(ExamManagementConstant.END_TIME, endTime);
    }

    /**
     * 查询岗位征集时间 学生导入时间 准考证下载时间
     */
    public void getSolicitationTimeRequestCheck() {
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID);
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }
        dataCenterService.setData(ExamManagementConstant.EXAM_ID, examId.trim());
    }

    /**
     * 获取考生签到数据
     */
    public void getStuCheckInInfoRequestCheck() {
        String mainExamId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.MAIN_EXAM_ID);
        if (StringUtils.isBlank(mainExamId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.MAIN_EXAM_ID_IS_BLANK);
        }
        dataCenterService.setData(ExamManagementConstant.MAIN_EXAM_ID, mainExamId.trim());
    }

    /************************************************************************************************************/


    /**
     * 设置打印时间
     */
    public void resetPrintTimeRequestCheck() {
        String admissionTicketTimeStart = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("admissionTicketTimeStart");
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examId");
        String admissionTicketTimeEnd = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("admissionTicketTimeEnd");
        if (StringUtils.isBlank(admissionTicketTimeStart)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.START_TIME_IS_BLANK);
        }
        if (StringUtils.isBlank(admissionTicketTimeEnd)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.END_TIME_IS_BLANK);
        }
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }
        dataCenterService.setData("admissionTicketTimeStart", admissionTicketTimeStart);
        dataCenterService.setData("admissionTicketTimeEnd", admissionTicketTimeEnd);
        dataCenterService.setData("examId", examId.trim());
    }

    /**
     * 获取考生签到表查询
     */
    public void getStudentSignTableRequestCheck() {

        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examId");
        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PAGE_PARAM_IS_ILLEGAL);
        }
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }
        dataCenterService.setData("examId", examId);
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);

    }

    /**
     * 下载考生签到表
     */
    public void downloadStudentSignTableRequestCheck() {

        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examId");
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }
        dataCenterService.setData("examId", examId.trim());

    }

    /**
     * 通过场次id获取岗位信息下拉框
     */
    public void getPostInfoByExamIdRequestCheck() {
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID); //场次id
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }
        TssExam exam = examService.getById(examId);
        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_DOES_NOT_EXIST);
        }
        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus())) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.THE_EXAM_HAS_BEEN_ARCHIVED_AND_CANNOT_BE_MODIFIED);
        }
        dataCenterService.setData(ExamManagementConstant.EXAM_ID, examId.trim());
    }

    /*
     * author lwn
     * description 岗位签到
     * date 2019/4/10 22:33
     * param
     * return void
     */
    public void jobSignInRequestCheck() {
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examId");
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.PAGE_PARAM_IS_ILLEGAL);
        }

        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }
        dataCenterService.setData("examId", examId.trim());
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);
    }

    /**
     * 获取考务签到页面的考试
     */
    public void getCheckInExamRequestCheck() {

    }

    public void downloadJobSignInRequestCheck(HttpServletRequest request) {
        String examId = request.getParameter("examId");
        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }
        dataCenterService.setData("examId", examId.trim());
    }

    /**
     * 获取岗位通知单
     */
    public void getPostNoticeListRequestCheck() {
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examId");
        String content = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("content");
        String level = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("level");
        String trainTime = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("trainTime");
        String attention = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("attention");
        String time = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("time");
        String template = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("template");

        if (StringUtils.isBlank(examId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_ID_IS_BLANK);
        }
        if (StringUtils.isBlank(content)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.CONTENT_IS_BLANK);
        }
        if (StringUtils.isBlank(level)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.LEVEL_IS_BLANK);
        }
        if (StringUtils.isBlank(trainTime)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TRAIN_TIME_IS_BLANK);
        }
        if (StringUtils.isBlank(attention)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.ATTENTION_IS_BLANK);
        }
        if (StringUtils.isBlank(time)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TIME_IS_BLANK);
        }
        if (StringUtils.isBlank(template)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TEMPLATE_IS_BLANK);
        }
        if ((!StringUtils.equals(template, "0")) && (!StringUtils.equals(template, "1"))) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TEMPLATE_IS_WRONG);
        }
        dataCenterService.setData("examId", examId.trim());
        dataCenterService.setData("content", content.trim());
        dataCenterService.setData("level", level.trim());
        dataCenterService.setData("trainTime", trainTime.trim());
        dataCenterService.setData("attention", attention.trim());
        dataCenterService.setData("time", time.trim());
        dataCenterService.setData("template", template.trim());

    }

    /**
     * author lwn
     * description  考务签到
     * date 2019/4/15 15:32
     * param
     * return void
     */
    public void kaoWuSignInRequestCheck() {
        String teId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("teId");
        String pid = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pid");
        String examPointId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPointId");//考点
        if (CheckVariableUtil.iDCardIsIllegal(pid)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PID_IS_ILLEGAL);
        }
        if (StringUtils.isBlank(teId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TE_ID_IS_BLANK);
        }
        if (StringUtils.isBlank(examPointId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAMP_POINT_ID_IS_BLANK);
        }
        dataCenterService.setData("teId", teId.trim());
        dataCenterService.setData("pid", pid.trim());
        dataCenterService.setData("examPointId", examPointId.trim());
    }

    /**
     * 准考证打印
     */
    public void getCardForExamListRequestCheck() {
        String mainExamId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.MAIN_EXAM_ID);
        if (StringUtils.isBlank(mainExamId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.MAIN_EXAM_ID_IS_BLANK);
        }
//        TssSolicitationTime tssSolicitationTime = tssSolicitationTimeDao.selectOne(new LambdaQueryWrapper<TssSolicitationTime>()
//                .eq(TssSolicitationTime::getTeId, mainExamId));
//        //假如有设定打印时间，则判断时间，没有设定则放过
//        if (tssSolicitationTime != null) {
//            if (!(new Date().after(tssSolicitationTime.getAdmissionTicketTimeStart())
//                    && new Date().before(tssSolicitationTime.getAdmissionTicketTimeEnd()))) {
//                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NOW_CAN_NOT_PRINT);
//            }
//        }
        dataCenterService.setData(ExamManagementConstant.MAIN_EXAM_ID, mainExamId.trim());
    }

    public void kaoWuSignInInfoRequestCheck() {
        String teId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("teId");
        String examPointId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPointId");//考点
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(DataKeepPostManagementReasonOfFailure.PAGE_PARAM_IS_ILLEGAL);
        }
        if (StringUtils.isBlank(teId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.TE_ID_IS_BLANK);
        }
        if (StringUtils.isBlank(examPointId)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAMP_POINT_ID_IS_BLANK);
        }
        dataCenterService.setData("teId", teId.trim());
        dataCenterService.setData("examPointId", examPointId.trim());
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);
    }
}
