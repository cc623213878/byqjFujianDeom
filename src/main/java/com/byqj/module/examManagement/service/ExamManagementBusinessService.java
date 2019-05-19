package com.byqj.module.examManagement.service;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byqj.constant.CommonConstant;
import com.byqj.dao.*;
import com.byqj.dto.*;
import com.byqj.entity.*;
import com.byqj.module.examManagement.constant.ExamManagementConstant;
import com.byqj.module.examManagement.enums.ExamManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.*;
import com.byqj.utils.*;
import com.byqj.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.google.common.primitives.Longs;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ExamManagementBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private IExamService examService;
    @Autowired
    private IExamPlaceService examPlaceService;
    @Autowired
    private ISolicitationTimeService solicitationTimeService;
    @Autowired
    private ITempSeatScheduleService tempSeatScheduleService;
    @Autowired
    private ISeatOrderService seatOrderService;
    @Autowired
    private IDictionaryService dictionaryService;
    @Autowired
    private IPostInfoService postInfoService;
    @Autowired
    private IPostPersonService postPersonService;
    @Autowired
    private ITempPostPersonService tempPostPersonService;
    @Autowired
    private IClassInfoService classInfoService;
    @Autowired
    private IIdPoolService idPoolService;
    @Autowired
    private ITempSubmitPersonService tempSubmitPersonService;
    @Autowired
    private TssTempSubmitPersonDao tempSubmitPersonDao;
    @Autowired
    private ISubmitPersonService submitPersonService;
    @Autowired
    SysDepartmentDao sysDepartmentDao;
    @Autowired
    SysAdminDetailDao sysAdminDetailDao;
    @Autowired
    private TssStudentInfoDao tssStudentInfoDao;
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
    @Autowired
    private TssTempSeatScheduleDao tssTempSeatScheduleDao;
    @Autowired
    private TssTempPostPersonDao tssTempPostPersonDao;
    @Autowired
    private TssPostPersonDao tssPostPersonDao;
    @Autowired
    private TssPersonLibraryDao tssPersonLibraryDao;
    @Autowired
    private IJobSignInService jobSignInService;
    @Value("${file-path.job-cost-file-save}")
    private String jobCostFileSave;
    @Value("${file-path.download-file-web-path}")
    private String downloadFileWebPath;
    @Value("${file-path.export-file-local-path}")
    private String exportFileLocalPath;
    @Value("${file-path.job-sign-in-file-save}")
    private String jobSignInFileSave;
    @Value("${file-path.student-sign-table-file-path}")
    private String studentSignTableFilePath;
    @Value("${file-path.card-for-exam-file-path}")
    private String cardForExamFilePath;
    @Value("${file-path.post-notice-list-file-path}")
    private String postNoticeListFilePath;
    @Value("${encode-role}")
    private String encodeRole;
    @Value("${file-path.account-table}")
    private String accountTable;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamManagementBusinessService.class);

    private List<TssExamPlaceDto> examPlaceListToTree(List<TssExamPlaceDto> dtoList) {

        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        // level -> [examPalce1, examPalce2, ...] Map<String, List<Object>>
        Multimap<String, TssExamPlaceDto> levelExamPlaceMap = ArrayListMultimap.create();
        List<TssExamPlaceDto> rootList = Lists.newArrayList();

        //root根添加到list中
        for (TssExamPlaceDto dto : dtoList) {
            levelExamPlaceMap.put(dto.getLevel(), dto);
            if (dto.getStudentCount() == -1 && dto.getParentId().equals(dto.getTeId())) { // 父id = 考试id
                rootList.add(dto);
            }
        }
        String root = rootList.get(0).getLevel();
        //根据level转为tree，把子节点添加到rootList中
        transformExamPlaceTree(rootList, root, levelExamPlaceMap);
        return rootList;
    }

    private void transformExamPlaceTree(List<TssExamPlaceDto> dtoList, String level, Multimap<String, TssExamPlaceDto> levelExamPlaceMap) {
        for (TssExamPlaceDto dto : dtoList) {
            //0.1  |  0.2
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            //从 levelExamPlaceMap 中找出子节点
            List<TssExamPlaceDto> tempList = (List<TssExamPlaceDto>) levelExamPlaceMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                dto.setExamPlaceList(tempList);
                //递归转为tree
                transformExamPlaceTree(tempList, nextLevel, levelExamPlaceMap);
            }
        }
    }

    private String getSeatOrder(Integer studentCount) {
        StringBuilder sb = new StringBuilder();
        String temp = CommonConstant.COMMA + "0";
        sb.append('0');
        for (int i = 1; i < studentCount; i++) {
            sb.append(temp);
        }
        return sb.toString();
    }

    private int countSeatOrderZero(String seatOrder) {

        List<String> temp = Splitter.on(",").omitEmptyStrings().splitToList(seatOrder);
        int num = 0;
        if (CollectionUtils.isNotEmpty(temp)) {
            for (String s : temp) {
                if ("0".equals(s)) num++;
            }
        }
        return num;
    }

    private Integer checkTime(Date now, Date start, Date end) {
        if (now.before(start)) {
            return 0; // 未开始
        }
        if (now.after(end)) {
            return 2; // 已完成
        }

        return 1; // 进行中
    }

    @Transactional
    public void setExamInfoRequestProcess() {
        String operation = dataCenterService.getData(ExamManagementConstant.OPER);
        String mainExamId = dataCenterService.getData(ExamManagementConstant.MAIN_EXAM_ID);
        List<TssExamPlace> examPlaces = dataCenterService.getData(ExamManagementConstant.EXAM_PLACES);
        try {
            if (ExamManagementConstant.ADD.equals(operation)) {
                List<TssExam> exams = dataCenterService.getData(ExamManagementConstant.EXAMS);
                // 初始化征集时间
                TssSolicitationTime tst = new TssSolicitationTime();
                tst.setTeId(mainExamId);
                // init id pool
                Date eDate = exams.get(0).getStartTime();
                Calendar c = Calendar.getInstance();
                c.setTime(eDate);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                String exTime = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
                List<TssIdPool> idPools = idPoolService.selectByTeStartTime(exTime);
                TssIdPool idPool = new TssIdPool();
                idPool.setTeId(mainExamId);
                idPool.setCount(1);
                idPool.setTeStartTime(exTime);
                if (idPools.isEmpty()) {
                    // 当天未安排考试
                    idPool.setExamNum(1);
                } else {
                    // 当天已安排过考试
                    idPool.setExamNum(idPools.size() + 1);
                }
                if (CollectionUtils.isNotEmpty(exams)
                        && CollectionUtils.isNotEmpty(examPlaces)) {
                    examService.saveBatch(exams);
                    examPlaceService.saveBatch(examPlaces);
                    solicitationTimeService.save(tst);
                    idPoolService.save(idPool);
                }
            } else {
                List<TssExam> updateExams = dataCenterService.getData(ExamManagementConstant.UPDATE_EXAMS);
                List<TssExam> addExams = dataCenterService.getData(ExamManagementConstant.ADD_EXAMS);

                if (CollectionUtils.isNotEmpty(updateExams)) {
                    examService.updateBatchById(updateExams);
                }
                if (CollectionUtils.isNotEmpty(addExams)) {
                    examService.saveBatch(addExams);
                }
                examPlaceService.saveBatch(examPlaces);
            }

            ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
            ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
            ResponseDataUtil.putValueToData(responseData, ExamManagementConstant.MAIN_EXAM_ID, mainExamId);
        } catch (Exception e) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
        }
    }

    void getExamInfoRequestProcess() {

        String mainExamId = dataCenterService.getData(ExamManagementConstant.MAIN_EXAM_ID);
        // 获取岗位征集时间
        TssSolicitationTime tst = solicitationTimeService
                .getOne(new QueryWrapper<TssSolicitationTime>()
                        .select("te_id", "post_time_start", "post_time_end")
                        .eq("te_id", mainExamId));

        // 获取子考试信息
        List<TssExam> exams = examService.getSubExam(mainExamId);
        TssExam mainExam = examService.getById(mainExamId);

        TssExamDto examDto = new TssExamDto();
        BeanUtils.copyProperties(mainExam, examDto);
        examDto.setExams(exams);
        examDto.setPostTimeStart(tst.getPostTimeStart());
        examDto.setPostTimeEnd(tst.getPostTimeEnd());

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, ExamManagementConstant.EXAMS, examDto);
    }

    @Transactional
    public void setExamPlaceInfoRequestProcess() {
        String oper = dataCenterService.getData(ExamManagementConstant.OPER);
        TssExamPlace examClass = dataCenterService.getData(ExamManagementConstant.EXAM_CLASS);

        if (ExamManagementConstant.ADD.equals(oper)) {

            // 初始化座位安排
            String order = getSeatOrder(examClass.getStudentCount());
            TssSeatOrder seatOrder = new TssSeatOrder();
            seatOrder.setTepId(examClass.getId());
            seatOrder.setTciId(examClass.getTciId());
            seatOrder.setSeatOrder(order);

            seatOrderService.save(seatOrder);
            examPlaceService.saveAndSetPlaceNum(examClass);

        } else { // update
            TssExamPlace old = examPlaceService.getById(examClass.getId());
            if (old.getStudentCount() == -1) {
                // 考点不在该接口更新数据，防止考生数被更新
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PARAM_ERROR);
                return;
            }

            // 考生数或教室发生变动
            boolean stuCountEq = old.getStudentCount().equals(examClass.getStudentCount());
            boolean tciIdEq = old.getTciId().equals(examClass.getTciId());
            if (!stuCountEq || !tciIdEq) {

                // 检查是否已安排考生
                TssSeatOrder seatOrder = seatOrderService.getById(examClass.getId());
                int num = countSeatOrderZero(seatOrder.getSeatOrder());
                if (num != old.getStudentCount()) {
                    ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.EXAM_CLASS_EXIST_STUDENT);
                    return;
                }
                // 检查教室容量
                if (tciIdEq) {
                    int afterCount = examClass.getStudentCount();
                    int beforeCount = old.getStudentCount();
                    // 教室未改变时，人数需相减，才能正确判断教室容量
                    int offset = afterCount - beforeCount;
//                    examClass.setStudentCount(afterCount - beforeCount);
//                    boolean isFree = examPlaceService.checkClassFree(examClass);
//                    if (!isFree) {
//                        ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.CLASS_FULL);
//                        return;
//                    }
//                    examClass.setStudentCount(afterCount);

                    // 检查教室容量是否足够
                    int classCapacity = classInfoService.getCapacityById(examClass.getTciId());
                    int stuCount = examPlaceService.getStuCountByTeIdAndTciId(examClass.getTeId(), examClass.getTciId());
                    if (stuCount + offset > classCapacity) {
                        ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NUMBER_OF_PEOPLE_EXCEEDS_CAPACITY);
                        return;
                    }
                }
                examPlaceService.updateById(examClass);
                seatOrder.setTciId(examClass.getTciId());
                seatOrder.setSeatOrder(getSeatOrder(examClass.getStudentCount()));
                seatOrderService.updateById(seatOrder);
            }

        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, ExamManagementConstant.EXAM_CLASS_ID, examClass.getId());
    }

    void getExamPlaceInfoRequestProcess() {

        String examId =
                dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID);
        int status = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STATUS);

        // 获取考试下考点考场信息
        List<TssExamPlace> examPlaces = examPlaceService.selectByTeId(examId);
        List<TssExamPlaceDto> tree = new ArrayList<TssExamPlaceDto>();

        if (CollectionUtils.isNotEmpty(examPlaces)) {
            // 获取字典表中考点和考试类型
            List<TssDictionary> dictionaryList =
                    dictionaryService.getPointAndType(CommonConstant.EXAM_POINT, CommonConstant.EXAM_TYPE);
            // groupBy type
            Map<String, List<TssDictionary>> dGroup =
                    dictionaryList.stream().collect(Collectors.groupingBy(TssDictionary::getType));

            // 获取所有考场考点下的岗位征集信息
            List<TssPostPerson> postPersonList = new ArrayList<TssPostPerson>();
            if (status == 0) { // 未归档
                List<TssTempPostPerson> tempPostPersonList =
                        tempPostPersonService.selectByExamPlaceIdsInTemp(examPlaces.stream()
                                .map(TssExamPlace::getId).collect(Collectors.toList()));
                for (TssTempPostPerson e : tempPostPersonList) {
                    TssPostPerson temp = new TssPostPerson();
                    BeanUtils.copyProperties(e, temp);
                    postPersonList.add(temp);
                }
                tempPostPersonList.clear();
            } else {
                postPersonList = postPersonService.selectByExamPlaceIds(examPlaces.stream()
                        .map(TssExamPlace::getId).collect(Collectors.toList()));
            }
            // groupBy tepId(考场考点id)
            Map<String, List<TssPostPerson>> ppGroup =
                    postPersonList.stream().collect(Collectors.groupingBy(TssPostPerson::getTepId));

            // 获取多个考点下，所有的教室信息
            List<Integer> place = examPlaces.stream()
                    .filter(e -> e.getStudentCount() == -1)
                    .map(TssExamPlace::getNameOrSeq).collect(Collectors.toList());
            List<TssClassInfo> classInfos = classInfoService.selectByPlace(place);
            Map<String, TssClassInfo> idToClass = classInfos.stream()
                    .collect(Collectors.toMap(TssClassInfo::getId, Function.identity()));

            // 获取考场下考生安排情况
            List<String> tepIds = examPlaces.stream().map(TssExamPlace::getId).collect(Collectors.toList());
            List<TssSeatOrder> seatOrders = seatOrderService.getBaseMapper().selectBatchIds(tepIds);

            // 计算考场未安排的考生数
            Map<String, Integer> tepIdUnOrderNumMap = new HashMap<String, Integer>();
            if (CollectionUtils.isNotEmpty(seatOrders)) {
                for (TssSeatOrder e : seatOrders) {
                    int num = countSeatOrderZero(e.getSeatOrder());
                    tepIdUnOrderNumMap.put(e.getTepId(), num);
//                    String classId = e.getTciId();
//                    if (classIdOrderNumMap.containsKey(classId)) {
//                        // 当一个教室被选择两次时，未安排人数需要叠加
//                        Integer unOrderedSum = classIdOrderNumMap.get(classId) + num;
//                        classIdOrderNumMap.put(classId, unOrderedSum);
//                    } else {
//                        classIdOrderNumMap.put(e.getTciId(), num);
//                    }
                }
            }

            // 拼接考点考场信息
            List<TssExamPlaceDto> examPlaceDtos = new ArrayList<TssExamPlaceDto>();
            for (TssExamPlace examPlace : examPlaces) {
                TssExamPlaceDto examPlaceDto = new TssExamPlaceDto();
                BeanUtils.copyProperties(examPlace, examPlaceDto);
                if (examPlace.getStudentCount() == -1) { // 考点
                    // 在字典中查找考点对应名称
                    dGroup.get(CommonConstant.EXAM_POINT).forEach(dictionary -> {
                        if (examPlaceDto.getNameOrSeq().intValue() == dictionary.getCode().intValue()) {
                            examPlaceDto.setNameStr(dictionary.getDescription());
                        }
                    });
                } else { // 考场
                    examPlaceDto.setNameStr(
                            StringUtils.leftPad(examPlace.getNameOrSeq().toString(), 3, LevelUtil.ROOT));
                }

                // 设置教室信息
                examPlaceDto.setClassInfo(idToClass.get(examPlaceDto.getTciId()));

                // 设置岗位信息
                examPlaceDto.setPostPersonList(ppGroup.get(examPlace.getId()));

                // 设置考场已安排的人数
                if (examPlace.getStudentCount() != -1 && !examPlace.getParentId().equals(examPlace.getTeId())) {
                    Integer unOrdered = tepIdUnOrderNumMap.get(examPlace.getId());
                    examPlaceDto.setOrdered(examPlaceDto.getStudentCount() - unOrdered);
                }
                examPlaceDtos.add(examPlaceDto);
            }

            // examPlaceDtos转为树形
            tree = examPlaceListToTree(examPlaceDtos);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, ExamManagementConstant.EXAM_PLACES, tree);

    }

    @Transactional
    public void setPostRequestProcess() {

        String operation = dataCenterService.getData(ExamManagementConstant.OPER);
        List<TssTempPostPerson> postPersonList = null;
        boolean result = true;

        if (ExamManagementConstant.ADD.equals(operation)) {
            // 改批量添加
            List<AddPostVo> postIdNumList = dataCenterService.getData(ExamManagementConstant.POST_ID_AND_NUM);
            String examPlaceOrClassId = dataCenterService.getData(ExamManagementConstant.EXAM_PLACE_OR_CLASS_ID);

            if (CollectionUtils.isNotEmpty(postIdNumList)) {
                Map<String, Integer> postIdNumMap = Maps.newHashMap();
                postIdNumList.forEach(e -> postIdNumMap.put(e.getPostId(), e.getNum()));
                List<String> postIds = Lists.newArrayList(postIdNumMap.keySet());
                // 判断该岗位是否已经添加
                int count = tempPostPersonService.countByPostIdsAndTepId(postIds, examPlaceOrClassId);
                if (count > 0) {
                    ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.POST_EXIST);
                    return;
                }
                List<TssPostInfo> postInfoList = (List<TssPostInfo>) postInfoService.listByIds(postIds);
                postPersonList = Lists.newArrayList();
                if (CollectionUtils.isNotEmpty(postInfoList)) {
                    for (TssPostInfo postInfo : postInfoList) {
                        TssTempPostPerson tempPostPerson = new TssTempPostPerson();
                        tempPostPerson.setId(IdUtils.getUUID());
                        tempPostPerson.setTepId(examPlaceOrClassId);
                        tempPostPerson.setPostId(postInfo.getId());
                        tempPostPerson.setPostName(postInfo.getName());
                        tempPostPerson.setPostFree(postInfo.getFree());
                        tempPostPerson.setPersonNum(postIdNumMap.get(postInfo.getId()));

                        postPersonList.add(tempPostPerson);
                    }
                }
                result = tempPostPersonService.saveBatch(postPersonList);
            }
        } else {
            // update
            int number = dataCenterService.getData(ExamManagementConstant.POST_NUM);
            String id = dataCenterService.getData(ExamManagementConstant.ID);
            String examPlaceOrClassId = dataCenterService.getData(ExamManagementConstant.EXAM_PLACE_OR_CLASS_ID);
            result = tempPostPersonService.updatePostNumById(id, number);
            postPersonList = tempPostPersonService.selectByExamPlaceIdsInTemp(Lists.newArrayList(examPlaceOrClassId));
        }

        if (!result) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
            return;
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, ExamManagementConstant.POST_PERSON, postPersonList);
    }

    @Transactional
    public void setMainClassRequestProcess() {
        TssExamPlace examPlace = dataCenterService.getData(ExamManagementConstant.EXAM_PLACE);

//        boolean result = examPlaceService.checkClassFree(examPlace);
//        if (!result) {
//            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
//            return;
//        }
        examPlaceService.updateById(examPlace);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    void getFreeClassRequestProcess() {
        String exPlaceId
                = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_ID);
        int exType
                = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXTYPE);
        // 检查是否为考点
        TssExamPlace examPlace = examPlaceService.getById(exPlaceId);
        if (examPlace == null || examPlace.getStudentCount() != -1) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NOT_EXAM_PLACE);
            return;
        }

        List<String> orderedClassIds = examPlaceService.getOrderedClassId(examPlace);
        List<TssClassInfo> classInfos = classInfoService.getUnOrderedClass(orderedClassIds, examPlace.getNameOrSeq(), exType);

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, ExamManagementConstant.CLASSES, classInfos);
    }

    // get exam list
    void getExamRequestProcess() {
        int status = dataCenterService.getData(ExamManagementConstant.STATUS);
        String sTime = dataCenterService.getData(ExamManagementConstant.STIME);
        String eTime = dataCenterService.getData(ExamManagementConstant.ETIME);

        List<TssExam> mainExamList = examService.listMainExamByCondition(status, sTime, eTime);
        List<TssDictionary> dictionaryList = dictionaryService.getExamType();

        Map<Integer, String> typeToName = Maps.newHashMap();
        dictionaryList.forEach(e ->
                typeToName.put(e.getCode(), e.getDescription())
        );

        List<TssExamDto> examDtos = new ArrayList<TssExamDto>();
        if (CollectionUtils.isNotEmpty(mainExamList)) {

            if (status == 0) { // 未归档
                List<String> examIds = mainExamList.stream().map(TssExam::getId).collect(Collectors.toList());
                List<TssSolicitationTime> solicitationTimes =
                        (List<TssSolicitationTime>) solicitationTimeService.listByIds(examIds);
                Map<String, TssSolicitationTime> stGroup = Maps.newHashMap();
                solicitationTimes.forEach(e -> stGroup.put(e.getTeId(), e));

                Calendar cal = Calendar.getInstance();
                for (TssExam exam : mainExamList) {
                    TssExamDto examDto = new TssExamDto();
                    BeanUtils.copyProperties(exam, examDto);
                    examDto.setTypeName(typeToName.get(examDto.getType()));
                    TssSolicitationTime tstTemp = stGroup.get(exam.getId());
                    // 考生报名状态
                    examDto.setKsbmStatus(checkTime(cal.getTime(),
                            tstTemp.getStudentTimeStart(), tstTemp.getStudentTimeEnd()));
                    // 准考证打印状态
                    examDto.setZkzdyStatus(checkTime(cal.getTime(),
                            tstTemp.getAdmissionTicketTimeStart(), tstTemp.getAdmissionTicketTimeEnd()));
                    // 岗位征集状态
                    examDto.setGwzjStatus(checkTime(cal.getTime(),
                            tstTemp.getPostTimeStart(), tstTemp.getPostTimeEnd()));

                    examDtos.add(examDto);
                }
            } else {
                for (TssExam exam : mainExamList) {
                    TssExamDto examDto = new TssExamDto();
                    BeanUtils.copyProperties(exam, examDto);
                    examDto.setTypeName(typeToName.get(examDto.getType()));
                    examDtos.add(examDto);
                }
            }
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, ExamManagementConstant.EXAM_LIST, examDtos);
    }

    @Transactional
    public void delPostRequestProcess() {
        String id = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.ID);
        try {
            // delete post
            tempPostPersonService.removeById(id);
        } catch (Exception e) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.DEL_ERROR);
            return;
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    @Transactional
    public void delExamRequestProcess() {
        String mainExamId = dataCenterService.getData(ExamManagementConstant.MAIN_EXAM_ID);

        int result = examService.countById(mainExamId);
        if (result == 1) {
            examService.delExam(mainExamId);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    @Transactional
    public void delExamClassRequestProcess() {
        String exCLassId = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_CLASS_ID);

        try {
            // 删除岗位安排信息 tss_temp_post_person
            tempPostPersonService.delByTepIdsInTemp(Lists.newArrayList(exCLassId));
            // 删除座位安排信息 tss_seat_order
            seatOrderService.removeByIds(Lists.newArrayList(exCLassId));
            // 重置报名岗位人员信息 tss_temp_submit_person
//            tempSubmitPersonService.resetTeIdByTeIdsInTemp();
            // 重置学生安排信息 tss_temp_seat_schedule
            tempSeatScheduleService.resetTepIdAndSeatNumInTemp(Lists.newArrayList(exCLassId));
            // 删除考场，考场号减一
            examPlaceService.delAndUpdateSeqById(exCLassId);

        } catch (Exception e) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.DEL_ERROR);
            return;
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    @Transactional
    public void setSolicitationTimeRequestProcess() {
        String mainExId = dataCenterService.getData(ExamManagementConstant.MAIN_EXAM_ID);
        String sTime = dataCenterService.getData(ExamManagementConstant.STIME);
        String eTime = dataCenterService.getData(ExamManagementConstant.ETIME);

        // get solicitation
        TssSolicitationTime solicitationTime = solicitationTimeService.getById(mainExId);
        // update
        try {
            solicitationTime.setPostTimeStart(DateUtils.parseDate(sTime, CommonConstant.TIME_PATTERN));
            solicitationTime.setPostTimeEnd(DateUtils.parseDate(eTime, CommonConstant.TIME_PATTERN));
            solicitationTimeService.updateById(solicitationTime);
        } catch (Exception e) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
            return;
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    @Transactional
    public void setReportTimeRequestProcess() {
        String mainExId = dataCenterService.getData(ExamManagementConstant.MAIN_EXAM_ID);
        int sTime = dataCenterService.getData(ExamManagementConstant.STIME);
        int eTime = dataCenterService.getData(ExamManagementConstant.ETIME);
        try {
            examService.setReportTime(mainExId, sTime, eTime);
        } catch (Exception e) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
            return;
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    void exportAccountRequestProcess() {
        String mainExamId = dataCenterService.getData(ExamManagementConstant.MAIN_EXAM_ID);

        List<TssExam> examList = examService.getSubExam(mainExamId);
        String url = null;

        if (CollectionUtils.isNotEmpty(examList)) {
            // 获取字典表中考点
            List<TssDictionary> dictionaryList = dictionaryService.getExamPoint();
            Map<Integer, String> typeToName = Maps.newHashMap();
            dictionaryList.forEach(dictionary ->
                    typeToName.put(dictionary.getCode(), dictionary.getDescription())
            );

            TssExam mainExam = examService.getById(mainExamId);
            // 计算签到时间
            int advance = mainExam.getReportStart() > 0 ? mainExam.getReportStart() * -1 : mainExam.getReportStart();
            int later = mainExam.getReportEnd() > 0 ? mainExam.getReportEnd() : mainExam.getReportEnd() * -1;
            String sTime = DateTimeUtil.getDateTimeString(DateUtils.addMinutes(mainExam.getStartTime(), advance));
            String eTime = DateTimeUtil.getDateTimeString(DateUtils.addMinutes(mainExam.getStartTime(), later));
            OutputStream out = null;
            try {
                String saveDir = this.exportFileLocalPath + this.accountTable;
                FileUtils.forceMkdirParent(new File(saveDir));
                String filePath = saveDir + mainExamId + ExamManagementConstant.XLSX;
                out = new FileOutputStream(filePath);
                ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
                int sheetNum = 1;
                for (TssExam exam : examList) {
                    List<AccountVo> accounts = new ArrayList<AccountVo>();
                    // 获取一场子考试下，所有的考点、考场
                    List<TssExamPlace> examPlaceAndClassList = examPlaceService.selectByTeId(exam.getId());
                    List<TssExamPlaceDto> examPlaceDtos = new ArrayList<TssExamPlaceDto>();

                    // 转为树形，区分考点、考场
                    examPlaceAndClassList.forEach(e -> {
                        TssExamPlaceDto examPlaceDto = new TssExamPlaceDto();
                        BeanUtils.copyProperties(e, examPlaceDto);
                        examPlaceDtos.add(examPlaceDto);
                    });
                    List<TssExamPlaceDto> tree = examPlaceListToTree(examPlaceDtos);

                    for (TssExamPlaceDto examPlaceDto : tree) {
                        List<TssExamPlaceDto> examClassList = examPlaceDto.getExamPlaceList();
                        //避免考点下没有考场导出账号是报NPE
                        if (CollectionUtils.isNotEmpty(examClassList)) {
                            for (TssExamPlaceDto examClass : examClassList) {
                                AccountVo account = new AccountVo();
                                account.setExName(exam.getName());
                                account.setPlace(typeToName.get(examPlaceDto.getNameOrSeq()));
                                account.setNumber(StringUtils
                                        .leftPad(examClass.getNameOrSeq().toString(), 3, LevelUtil.ROOT));
                                account.setReportTime(sTime + "-" + eTime);
                                account.setUsername(examClass.getId());
                                account.setPassword(account.getNumber());

                                accounts.add(account);
                            }
                        }
                    }
                    // 导出accounts到excel
                    if (CollectionUtils.isNotEmpty(accounts)) {
                        Sheet sheet = new Sheet(sheetNum++, 3, AccountVo.class, exam.getName(), null);
                        sheet.setAutoWidth(Boolean.TRUE);
                        writer.write(accounts, sheet);
                    }
                }
                writer.finish();
                url = downloadFileWebPath + this.accountTable + mainExamId + ExamManagementConstant.XLSX;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "url", url);
    }

    @Transactional
    public void fileExamRequestProcess() {
        String mainExId = dataCenterService.getData(ExamManagementConstant.MAIN_EXAM_ID);
        int result = examService.countById(mainExId);
        if (result == 1) {
            String level = LevelUtil.ROOT + LevelUtil.SEPARATOR + mainExId + LevelUtil.SEPARATOR;
            // 通过level 获取考试下所有考点考场
            List<TssExamPlace> examPlaceAndClassList = examPlaceService.likeRightLevel(level);

            if (CollectionUtils.isNotEmpty(examPlaceAndClassList)) {
                List<String> tepIds = examPlaceAndClassList.stream()
                        .map(TssExamPlace::getId).collect(Collectors.toList());
                try {
                    boolean re;

                    // tss_temp_post_person -> tss_post_person
                    re = tempPostPersonService.copyToMainTableByTepIds(tepIds);
                    if (re) {
                        tempPostPersonService.removeByTepIds(tepIds);
                    } else {
                        throw new Exception();
                    }

                    // tss_temp_seat_schedule -> tss_seat_schedule
                    re = tempSeatScheduleService.copyToMainTableByTeId(mainExId);
                    if (re) {
                        tempSeatScheduleService.removeByTeId(mainExId);
                    } else {
                        throw new Exception();
                    }

                    // tss_temp_submit_person -> tss_submit_person
                    re = tempSubmitPersonService.copyToMainTableByTeId(mainExId);
                    if (re) {
                        tempSubmitPersonService.removeByTeId(mainExId);
                    } else {
                        throw new Exception();
                    }

                    // set exam status to 1
                    re = examService.fileExam(mainExId);
                    if (!re) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.FILE_ERROR);
                    return;
                }
            }
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }



    /* -------------------------------------------------------------------------------------------------- */


    /**
     * 获取待安排的考生
     */
    public void getUnscheduledExamineeRequestProcess() {
        String mainExamId = dataCenterService.getData(ExamManagementConstant.MAIN_EXAM_ID);
        String examPlaceId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_ID);
        Integer pageNum = dataCenterService.getData(ExamManagementConstant.PAGE_NUM);
        Integer pageSize = dataCenterService.getData(ExamManagementConstant.PAGE_SIZE);
        String collegeName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.COLLEGE_NAME);
        String stuName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STU_NAME);
        String level = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.LEVEL);

        LambdaQueryWrapper<TssTempSeatSchedule> queryWrapper = new LambdaQueryWrapper<TssTempSeatSchedule>();
        queryWrapper.eq(TssTempSeatSchedule::getTeId, mainExamId);
        queryWrapper.eq(TssTempSeatSchedule::getSeatNum, ExamManagementConstant.UNSCHEDULED_EXAMINEE_SIGN);
        if (StringUtils.isNotBlank(collegeName)) {
            queryWrapper.like(TssTempSeatSchedule::getXymc, collegeName);
        }
        if (StringUtils.isNotBlank(level)) {
            queryWrapper.like(TssTempSeatSchedule::getBkjb, level);
        }
        if (StringUtils.isNotBlank(stuName)) {
            queryWrapper.like(TssTempSeatSchedule::getXm, stuName);
        }

        //待安排的考生
        PageHelper.startPage(pageNum, pageSize);
        List<TssTempSeatSchedule> tssSeatScheduleList = tempSeatScheduleService.getBaseMapper()
                .selectList(queryWrapper);

        PageInfo<TssTempSeatSchedule> pageResult = new PageInfo<>(tssSeatScheduleList);

        //查询考点下已经安排的考生
        List<TssTempSeatSchedule> examPlaceSeatScheduleList = null;
        if (StringUtils.isNotBlank(examPlaceId)) {

            examPlaceSeatScheduleList = tempSeatScheduleService.getBaseMapper()
                    .selectList(new LambdaQueryWrapper<TssTempSeatSchedule>()
                            .eq(TssTempSeatSchedule::getTepId, examPlaceId)
                            .orderByAsc(TssTempSeatSchedule::getSeatNum));
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "examPlaceSeatScheduleList", examPlaceSeatScheduleList);
        ResponseDataUtil.putValueToData(responseData, "unscheduledExamineeList", pageResult);
    }


    /**
     * ->先把该考场中已有的人全标记为未安排
     * ->打乱排到考场中
     * -->需考虑字符串拼接
     */
    @Transactional
    public void arrangeExamineeRequestProcess() {
        TssExamPlace examPlace = dataCenterService.getData("examPlace");
        String examPlaceId = dataCenterService.getData(ExamManagementConstant.EXAM_PLACE_ID);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        // 上传上来的考场考生idList
        List<String> examineeIdList = dataCenterService.getData(ExamManagementConstant.EXAMINEE_ID_LIST);

        TssSeatOrder tssSeatOrder = seatOrderService.getById(examPlaceId);
        String seatOrderStr = tssSeatOrder.getSeatOrder();
        // 数据库考场中座位顺序的考生idList
        List<String> seatOrderList = new ArrayList<>(Splitter.on(",").omitEmptyStrings().splitToList(seatOrderStr));

        //被清除考生的list
        List<TssTempSeatSchedule> beClearExamineeSeatScheduleList = new ArrayList<>();

        boolean clearResult = true;
        boolean updateResult = true;
        boolean updateSeatOrderResult;

        //如果移除所有学生
        if (CollectionUtils.isEmpty(examineeIdList)) {
            String setOrderStr = this.getSeatOrder(examPlace.getStudentCount());
            tssSeatOrder.setSeatOrder(setOrderStr);
            updateSeatOrderResult = seatOrderService.updateById(tssSeatOrder);
            for (String id : seatOrderList) {
                TssTempSeatSchedule seatSchedule = TssTempSeatSchedule.builder().id(id).tepId("").seatNum(0).build();
                beClearExamineeSeatScheduleList.add(seatSchedule);
            }
            // 需要移除的清除掉
            clearResult = tempSeatScheduleService.updateBatchById(beClearExamineeSeatScheduleList);

            if (!(clearResult && updateSeatOrderResult)) {
                ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
            }
            ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
            return;
        }


        // 数据库中的座位安排list与上传上来考场的考试idlist做交集，找出没有变动的考生
        List<String> intersectionIdList = (List<String>) CollectionUtils.intersection(examineeIdList, seatOrderList);


        //被移除出考场的考生
        List<String> beClearedExamineeIdList = new ArrayList<>();
        beClearedExamineeIdList.addAll(seatOrderList);

        if (CollectionUtils.isNotEmpty(intersectionIdList)) {

            beClearedExamineeIdList.removeAll(intersectionIdList);

            //考场新增的考生 examineeIdList中只有新增的考生id
            examineeIdList.removeAll(intersectionIdList);
        }

        //打乱新增的考生id顺序
        Collections.shuffle(examineeIdList);

        List<TssTempSeatSchedule> seatScheduleList = new ArrayList<>();

        ListIterator eilli = examineeIdList.listIterator();

        ListIterator<String> solli = seatOrderList.listIterator();

        while (solli.hasNext()) {
            int num = solli.nextIndex() + 1;//座位号
            String temp = solli.next();

            //将移出考场的id改为0
            if (beClearedExamineeIdList.contains(temp)) {
                solli.set("0");
            }

            //如果当前位置的值为0表示空位，安排一名考生到该位置
            if (StringUtils.equals(temp, "0")) {
                boolean result = eilli.hasNext();

                if (result) {
                    String examineeId = (String) eilli.next();
                    solli.set(examineeId);
                    TssTempSeatSchedule seatSchedule = TssTempSeatSchedule.builder().id(examineeId).tepId(examPlaceId).seatNum(num).build();
                    seatScheduleList.add(seatSchedule);
                }

                //如果examineeIdList中的考生都安排完了无需再进行迭代
                if (!result) {
                    break;
                }
            }
        }

        String setOrderStr = StringUtils.join(seatOrderList, ",");
        tssSeatOrder.setSeatOrder(setOrderStr);
        updateSeatOrderResult = seatOrderService.updateById(tssSeatOrder);


        // 将移除的考生考场和座位号进行擦除
        for (String id : beClearedExamineeIdList.stream().filter(i -> !StringUtils.equals(i, "0")).collect(Collectors.toList())) {
            TssTempSeatSchedule seatSchedule = TssTempSeatSchedule.builder().id(id).tepId("").seatNum(0).build();
            beClearExamineeSeatScheduleList.add(seatSchedule);
        }

        // 先将移除的清除掉
        if (CollectionUtils.isNotEmpty(beClearExamineeSeatScheduleList)) {

            clearResult = tempSeatScheduleService.updateBatchById(beClearExamineeSeatScheduleList);
        }

        if (CollectionUtils.isNotEmpty(seatScheduleList)) {
            updateResult = tempSeatScheduleService.updateBatchById(seatScheduleList);
        }

        if (!(clearResult && updateResult && updateSeatOrderResult)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
        }
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    /**
     * 获取待安排的考务人员与已安排的考务人员
     * <p>
     * 场次中的人员包含了监考以及考务
     * ->查出A（该场次中已经参加的考务人员）
     * ->查出除A之外的报名人员
     * ->同时返回当前场次或者考点岗位中已经安排的人员
     */
    public void getUnscheduledPersonRequestProcess() {
        Integer pageNum = dataCenterService.getData(ExamManagementConstant.PAGE_NUM);
        Integer pageSize = dataCenterService.getData(ExamManagementConstant.PAGE_SIZE);

        String deptIdStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("deptId");
        String sex = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("sex");
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");

        //当前考点或者考场的实体对象
        TssExamPlace examPlace = dataCenterService.getData("examPlace");
        String postId = dataCenterService.getData(ExamManagementConstant.POST_ID);

        String mainExamId = examService.getById(examPlace.getTeId()).getParentId();

        //查询当前场次下所有的考点或考场
        List<TssExamPlace> examPlaceList = examPlaceService.getBaseMapper().selectList(new LambdaQueryWrapper<TssExamPlace>().eq(TssExamPlace::getTeId, examPlace.getTeId()));

        //当前场次下考点和考场的idList
        List<String> examPlaceIdList = examPlaceList.stream().map(TssExamPlace::getId).collect(Collectors.toList());

        //查询当前场次下所有的考点或考场已经安排的人员
        List<TssTempPostPerson> scheduledPostPersonList = tempPostPersonService.selectByExamPlaceIdsInTemp(examPlaceIdList);

        //场次下岗位已经安排的人逗号分割的idStrLIst
        List<String> scheduledPersonStrIdList = scheduledPostPersonList.stream().map(TssTempPostPerson::getTpiIdStr).collect(Collectors.toList());

        //岗位已经安排的人idList
        List<String> scheduledPersonIdList = new ArrayList<>();
        for (String s : scheduledPersonStrIdList) {
            scheduledPersonIdList.addAll(Splitter.on(",").omitEmptyStrings().splitToList(s));
        }

        List<Long> childrenDeptId = null;
        if (StringUtils.isNotBlank(deptIdStr)) {
            Long deptId = Longs.tryParse(deptIdStr);
            SysDepartment sysDepartment = sysDepartmentDao.selectById(deptId);
            childrenDeptId = sysDepartmentDao.getChildrenCollegeId(sysDepartment.getLevel(), deptId);
            childrenDeptId.add(deptId);

        }


        PageHelper.startPage(pageNum, pageSize);
        // 查询所有待安排的
        List<SubmitPersonDto> unScheduledPlacePersonList = tempSubmitPersonDao.selectPersonExcludeIds(scheduledPersonIdList, mainExamId, childrenDeptId, sex, name);
        //身份证解密
        unScheduledPlacePersonList.forEach(item -> item.setPid(AESUtil.AESDecode(encodeRole, item.getPid())));
        PageInfo<SubmitPersonDto> pageResult = new PageInfo<>(unScheduledPlacePersonList);

        //获取当前岗位下安排的人
        TssTempPostPerson tempPostPerson = scheduledPostPersonList.stream()
                .filter(tssTempPostPerson -> StringUtils.equals(tssTempPostPerson.getId(), postId)).findFirst().get();

        //当前考点或考场已经安排的考点或考场的人员idList
        List<String> scheduledPlacePersonIdList = Splitter.on(",").omitEmptyStrings().splitToList(tempPostPerson.getTpiIdStr());
        // 装载当前考点或考场已经安排的人员信息
        List<SubmitPersonDto> scheduledPlacePersonList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(scheduledPlacePersonIdList)) {

            scheduledPlacePersonList = tempSubmitPersonDao.selectPersonBatchIds(scheduledPlacePersonIdList, mainExamId);
            //身份证解密
            scheduledPlacePersonList.forEach(item -> item.setPid(AESUtil.AESDecode(encodeRole, item.getPid())));
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "scheduledPlacePersonList", scheduledPlacePersonList);
        ResponseDataUtil.putValueToData(responseData, "unScheduledPlacePersonList", pageResult);
    }

    /**
     * 安排岗位
     */
    @Transactional
    public void arrangePostRequestProcess() {
        String personIdStr = dataCenterService.getData(ExamManagementConstant.PERSON_ID_STR);
        String examPlaceId = dataCenterService.getData(ExamManagementConstant.EXAM_PLACE_ID);
        String postId = dataCenterService.getData(ExamManagementConstant.POST_ID);

        boolean result = tempPostPersonService.update(new TssTempPostPerson().builder().tpiIdStr(personIdStr).build(),
                new LambdaQueryWrapper<TssTempPostPerson>()
                        .eq(TssTempPostPerson::getTepId, examPlaceId)
                        .eq(TssTempPostPerson::getId, postId));
        if (!result) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    /**
     * 获取考点岗位信息
     */
    public void getExamPlacePostInfoRequestProcess() {
        String examPlaceId = dataCenterService.getData(ExamManagementConstant.EXAM_PLACE_ID); // 考点id
        Integer status = dataCenterService.getData(ExamManagementConstant.STATUS); // 归档状态

        // 当前考点下所有考场
        List<TssExamPlace> examPlaceList = examPlaceService.getSubExamPlaceOrderBy(examPlaceId);
        // 收集考场id并把考点id添加进去
        List<String> examPlaceIdList = examPlaceList.stream().filter(Objects::nonNull).map(TssExamPlace::getId).collect(Collectors.toList());
        examPlaceIdList.add(examPlaceId);

        //所有教室信息
        List<TssClassInfo> classInfoList = classInfoService.getBaseMapper()
                .selectList(new LambdaQueryWrapper<TssClassInfo>().select(TssClassInfo::getId, TssClassInfo::getName));


        List<ExamPlacePostPersonVo> examPlacePostPersonVoList = new ArrayList<>();
        List<ExamPointPostPersonVo> examPointPostPersonVoList = new ArrayList<>();
        List<TssTempPostPerson> tempPostPersonList = null;
        List<TssPostPerson> postPersonList = null;
        //未归档的考试
        if (ExamManagementConstant.UNFILED_STATUS.equals(status) && CollectionUtils.isNotEmpty(examPlaceIdList)) {
            //查询考点或考场下的岗位人员
            tempPostPersonList = tempPostPersonService.getBaseMapper()
                    .selectList(new LambdaQueryWrapper<TssTempPostPerson>().in(TssTempPostPerson::getTepId, examPlaceIdList));
        }

        if (ExamManagementConstant.ARCHIVE_STATUS.equals(status) && CollectionUtils.isNotEmpty(examPlaceIdList)) {
            //查询考点或考场下的岗位人员
            postPersonList = postPersonService.getBaseMapper()
                    .selectList(new LambdaQueryWrapper<TssPostPerson>().in(TssPostPerson::getTepId, examPlaceIdList));
        }

        if (CollectionUtils.isNotEmpty(postPersonList)) {
            for (TssPostPerson tssPostPerson : postPersonList) {
                TssTempPostPerson tempPostPerson = new TssTempPostPerson();
                BeanUtils.copyProperties(tssPostPerson, tempPostPerson);
                tempPostPersonList.add(tempPostPerson);

            }
        }

        //给考点和考场装载岗位信息
        if (CollectionUtils.isNotEmpty(tempPostPersonList)) {
            List<TssTempPostPerson> finalTempPostPersonList = tempPostPersonList;

            //装载考场信息
            examPlaceList.forEach(i -> {

                //收集当前考场下的岗位list
                List<TssTempPostPerson> tssTempPostPersonList = finalTempPostPersonList.stream().filter(postPerson -> StringUtils.equals(postPerson.getTepId(), i.getId())).collect(Collectors.toList());
                //list拷贝到postVo中
                List<PostVo> postVos = new ArrayList<>();
                tssTempPostPersonList.forEach(item -> {

                    PostVo postVo = PostVo.builder()
                            .postId(item.getId())
                            .personNum(item.getPersonNum())
                            .postName(item.getPostName())
                            .tepId(item.getTepId())
                            .examPlaceNum(StringUtils.leftPad(i.getNameOrSeq().toString(), 3, LevelUtil.ROOT))
                            .build();
                    postVos.add(postVo);
                });

                // 过滤一条出来填出列表数据
                Optional<TssTempPostPerson> tempPostPerson = finalTempPostPersonList.stream().filter(postPerson -> StringUtils.equals(postPerson.getTepId(), i.getId())).findAny();
                if (tempPostPerson != null && tempPostPerson.isPresent()) {
                    ExamPlacePostPersonVo examPlacePostPersonVo = ExamPlacePostPersonVo.builder()
                            .examPlaceId(i.getId())
                            .examPlaceNum(StringUtils.leftPad(i.getNameOrSeq().toString(), 3, LevelUtil.ROOT))
                            .className(i.getNameOrSeq().toString()).className(classInfoList.stream().filter(classInfo -> classInfo.getId().equals(i.getTciId())).findFirst().get().getName())
                            .postInfoList(postVos)
                            .schedulePostNum(Splitter.on(",").omitEmptyStrings().splitToList(tempPostPerson.get().getTpiIdStr()).size())
                            .build();
                    examPlacePostPersonVoList.add(examPlacePostPersonVo);
                }
            });

            //装载考点信息
            List<TssTempPostPerson> pointPostPersonList = tempPostPersonList.stream().filter(i -> i.getTepId().equals(examPlaceId)).collect(Collectors.toList());
            pointPostPersonList.forEach(i -> {
                ExamPointPostPersonVo examPointPostPersonVo = ExamPointPostPersonVo
                        .builder()
                        .postId(i.getId())
                        .postName(i.getPostName())
                        .postNum(i.getPersonNum())
                        .schedulePostNum(Splitter.on(",").omitEmptyStrings().splitToList(i.getTpiIdStr()).size())
                        .build();
                examPointPostPersonVoList.add(examPointPostPersonVo);
            });
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "examPlacePostPersonList", examPlacePostPersonVoList);
        ResponseDataUtil.putValueToData(responseData, "examPointPostPersonList", examPointPostPersonVoList);

    }

    /**
     * 通过考试场次id获取考点信息
     */
    public void getExamPlaceByExamIdRequestProcess() {
        String examId = dataCenterService.getData(ExamManagementConstant.EXAM_ID);
        // 获取场次下考点信息
        List<TssExamPlace> examPlaceList = examPlaceService.getSubExamPlaceOrderBy(examId);
        List<TssDictionary> dictionaryList = dictionaryService.getBaseMapper().selectList(new LambdaQueryWrapper<TssDictionary>().eq(TssDictionary::getType, CommonConstant.EXAM_POINT));

        List<ExamPointVo> examPointVoList = new ArrayList<>();

        examPlaceList.forEach(examPlace ->
                examPointVoList.add(
                        new ExamPointVo().builder()
                                .id(examPlace.getId())
                                .name(dictionaryList.stream().filter(dict -> examPlace.getNameOrSeq().equals(dict.getCode())).findFirst().get().getDescription())
                                .build()));

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "examPointList", examPointVoList);
    }


    /**
     * 获取岗位费用设置
     */
    public void getPostFreeSetRequestProcess() {
        String examId = dataCenterService.getData(ExamManagementConstant.EXAM_ID); // 考试id
        TssExam exam = dataCenterService.getData("exam");

        List<String> examPlaceIdList = null;
        //获取考试下场次list
        List<TssExam> examList = examService.getSubExam(examId);
        if (CollectionUtils.isNotEmpty(examList)) {
            // 获取考试下考点考场信息
            List<TssExamPlace> examPlaceList = examPlaceService.selectByTeIdList(examList.stream().map(TssExam::getId).collect(Collectors.toList()));
            examPlaceIdList = examPlaceList.stream().map(TssExamPlace::getId).collect(Collectors.toList());
        }


        List<TssTempPostPerson> tempPostPersonList = null;
        if (ExamManagementConstant.UNFILED_STATUS.equals(exam.getStatus())) {
            tempPostPersonList = tempPostPersonService.selectByExamPlaceIdsInTemp(examPlaceIdList);
        }

        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus())) {
            List<TssPostPerson> postPersonList = postPersonService.selectByExamPlaceIds(examPlaceIdList);
            if (CollectionUtils.isNotEmpty(postPersonList)) {
                for (TssPostPerson tssPostPerson : postPersonList) {
                    TssTempPostPerson tempPostPerson = new TssTempPostPerson();
                    BeanUtils.copyProperties(tssPostPerson, tempPostPerson);
                    tempPostPersonList.add(tempPostPerson);
                }

            }

        }

        Set<PostFreeVo> postFreeSet = new HashSet<>();

        if (CollectionUtils.isNotEmpty(tempPostPersonList)) {
            for (TssTempPostPerson tempPostPerson : tempPostPersonList) {
                PostFreeVo postFreeVo = new PostFreeVo();
                BeanUtils.copyProperties(tempPostPerson, postFreeVo);
                postFreeSet.add(postFreeVo);

            }
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "postFreeSet", postFreeSet);
    }

    /**
     * 设置岗位费用
     */
    @Transactional
    public void setPostFreeRequestProcess() {
        String examId = dataCenterService.getData(ExamManagementConstant.EXAM_ID); // 考试id
        List<PostFreeVo> postFreeVoList = dataCenterService.getData(ExamManagementConstant.POST_FREE_LIST);

        List<String> examPlaceIdList = null;
        //获取考试下场次list
        List<TssExam> examList = examService.getSubExam(examId);
        // 获取考试下考点考场信息
        List<TssExamPlace> examPlaceList = examPlaceService.selectByTeIdList(examList.stream().map(TssExam::getId).collect(Collectors.toList()));
        examPlaceIdList = examPlaceList.stream().map(TssExamPlace::getId).collect(Collectors.toList());

        List<TssTempPostPerson> postPersonList = tempPostPersonService.selectByExamPlaceIdsInTemp(examPlaceIdList);

        postPersonList.forEach(i -> i.setPostFree(postFreeVoList.stream().filter(postFree -> i.getPostId().equals(postFree.getPostId())).findFirst().get().getPostFree()));

        boolean result = tempPostPersonService.updateBatchById(postPersonList);

        if (!result) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

    }

    /**
     * 获取岗位人员费用
     */
    public void getPostFreeRequestProcess() {
        String examId = dataCenterService.getData(ExamManagementConstant.EXAM_ID);
        TssExam exam = dataCenterService.getData("exam");


        List<String> examPlaceIdList = null;
        //获取考试下场次list
        List<TssExam> examList = examService.getSubExam(examId);
        if (CollectionUtils.isNotEmpty(examList)) {
            // 获取考试下考点考场信息
            List<TssExamPlace> examPlaceList = examPlaceService.selectByTeIdList(examList.stream().map(TssExam::getId).collect(Collectors.toList()));
            examPlaceIdList = examPlaceList.stream().map(TssExamPlace::getId).collect(Collectors.toList());
        }

        List<TssTempPostPerson> tempPostPersonList = new ArrayList<>();

        // 未归档的

        if (ExamManagementConstant.UNFILED_STATUS.equals(exam.getStatus()) && CollectionUtils.isNotEmpty(examPlaceIdList)) {
            tempPostPersonList = tempPostPersonService.selectByExamPlaceIdsInTemp(examPlaceIdList);
        }

        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus()) && CollectionUtils.isNotEmpty(examPlaceIdList)) {
            List<TssPostPerson> postPersonList = postPersonService.selectByExamPlaceIds(examPlaceIdList);
            if (CollectionUtils.isNotEmpty(postPersonList)) {
                for (TssPostPerson tssPostPerson : postPersonList) {
                    TssTempPostPerson tempPostPerson = new TssTempPostPerson();
                    BeanUtils.copyProperties(tssPostPerson, tempPostPerson);
                    tempPostPersonList.add(tempPostPerson);
                }
            }
        }

        List<PersonFreeVo> personFreeList = new ArrayList<>();

        if (CollectionUtils.isEmpty(tempPostPersonList)) {
            ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
            ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
            ResponseDataUtil.putValueToData(responseData, "personFreeList", personFreeList);
            return;
        }

        //临时人员费用list 一个人可以出现多次
        List<PersonFreeVo> tempPersonFreeList = new ArrayList<>();

        tempPostPersonList.forEach(item ->
                Splitter.on(",").omitEmptyStrings().splitToList(item.getTpiIdStr()).forEach(personId ->
                        tempPersonFreeList.add(PersonFreeVo.builder().id(personId).free(item.getPostFree()).build())));

        //人员费用List 一个人只出现一次
        List<String> personIdList = tempPersonFreeList.stream().map(PersonFreeVo::getId).distinct().collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(personIdList)) {
            personIdList.forEach(id -> {
                double salary = tempPersonFreeList.stream().filter(value -> StringUtils.equalsIgnoreCase(id, value.getId())).mapToDouble(PersonFreeVo::getFree).sum();
                personFreeList.add(PersonFreeVo.builder().id(id).free(salary).build());
            });

        }

        List<SubmitPersonDto> submitPersonList = null;

        if (ExamManagementConstant.UNFILED_STATUS.equals(exam.getStatus()) && CollectionUtils.isNotEmpty(personIdList)) {
            submitPersonList = tempSubmitPersonDao.selectPersonBatchIds(personIdList, examId);
        }

        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus()) && CollectionUtils.isNotEmpty(personIdList)) {
            submitPersonList = submitPersonService.selectPersonBatchIds(personIdList);
        }

        List<SubmitPersonDto> finalSubmitPersonList = submitPersonList;
        if (CollectionUtils.isNotEmpty(personFreeList)) {
            personFreeList.forEach(item -> {
                SubmitPersonDto temp = finalSubmitPersonList.stream().filter(value -> StringUtils.equals(item.getId(), value.getId())).findFirst().get();
                item.setDepartment(temp.getDepName());
                item.setName(temp.getName());
                item.setNum(temp.getWorkCode());
            });
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "personFreeList", personFreeList);
    }

    /**
     * 下载岗位人员费用表
     */
    public void downloadPersonFreeRequestProcess(HttpServletResponse response) {
        String examId = dataCenterService.getData(ExamManagementConstant.EXAM_ID);
        TssExam exam = dataCenterService.getData("exam");

        List<String> examPlaceIdList = null;
        //获取考试下场次list
        List<TssExam> examList = examService.getSubExam(examId);
        if (CollectionUtils.isNotEmpty(examList)) {
            // 获取考试下考点考场信息
            List<TssExamPlace> examPlaceList = examPlaceService.selectByTeIdList(examList.stream().map(TssExam::getId).collect(Collectors.toList()));
            examPlaceIdList = examPlaceList.stream().map(TssExamPlace::getId).collect(Collectors.toList());
        }
        List<TssTempPostPerson> tempPostPersonList = null;

        // 未归档的
        if (ExamManagementConstant.UNFILED_STATUS.equals(exam.getStatus()) && CollectionUtils.isNotEmpty(examPlaceIdList)) {
            tempPostPersonList = tempPostPersonService.selectByExamPlaceIdsInTemp(examPlaceIdList);
        }

        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus()) && CollectionUtils.isNotEmpty(examPlaceIdList)) {
            List<TssPostPerson> postPersonList = postPersonService.selectByExamPlaceIds(examPlaceIdList);
            if (CollectionUtils.isNotEmpty(postPersonList)) {
                for (TssPostPerson tssPostPerson : postPersonList) {
                    TssTempPostPerson tempPostPerson = new TssTempPostPerson();
                    BeanUtils.copyProperties(tssPostPerson, tempPostPerson);
                    tempPostPersonList.add(tempPostPerson);
                }
            }
        }

        List<PersonFreeVo> personFreeList = new ArrayList<>();


        //临时人员费用list 一个人可以出现多次
        List<PersonFreeVo> tempPersonFreeList = new ArrayList<>();
        //人员费用List 一个人只出现一次
        List<String> personIdList = null;

        if (CollectionUtils.isNotEmpty(tempPostPersonList)) {
            tempPostPersonList.forEach(item ->
                    Splitter.on(",").omitEmptyStrings().splitToList(item.getTpiIdStr()).forEach(personId ->
                            tempPersonFreeList.add(PersonFreeVo.builder().id(personId).free(item.getPostFree()).build())));
            //人员费用List 一个人只出现一次
            personIdList = tempPersonFreeList.stream().map(PersonFreeVo::getId).distinct().collect(Collectors.toList());
        }


        if (CollectionUtils.isNotEmpty(personIdList)) {
            personIdList.forEach(id -> {
                double salary = tempPersonFreeList.stream().filter(value -> StringUtils.equalsIgnoreCase(id, value.getId())).mapToDouble(PersonFreeVo::getFree).sum();
                personFreeList.add(PersonFreeVo.builder().id(id).free(salary).build());
            });

        }

        List<SubmitPersonDto> submitPersonList = null;

        if (ExamManagementConstant.UNFILED_STATUS.equals(exam.getStatus()) && CollectionUtils.isNotEmpty(personIdList)) {
            submitPersonList = tempSubmitPersonDao.selectPersonBatchIds(personIdList, examId);
        }

        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus()) && CollectionUtils.isNotEmpty(personIdList)) {
            submitPersonList = submitPersonService.selectPersonBatchIds(personIdList);
        }

        List<SubmitPersonDto> finalSubmitPersonList = submitPersonList;
        if (CollectionUtils.isNotEmpty(personFreeList)) {
            personFreeList.forEach(item -> {
                SubmitPersonDto temp = finalSubmitPersonList.stream().filter(value -> StringUtils.equals(item.getId(), value.getId())).findFirst().get();
                item.setDepartment(temp.getDepName());
                item.setName(temp.getName());
                item.setNum(temp.getWorkCode());
            });
        }


        Date date1 = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String date = df.format(date1);
        String fileName = "PostFreeTable" + ".xlsx";  //文件名
        //写入excel
        if (personFreeList == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NO_LIST_TO_BE_DOWNLOAD);
        }
        String filePath = exportFileLocalPath + jobCostFileSave;
        String returnPath = downloadFileWebPath + jobCostFileSave + fileName;
        File file = new File(filePath);
        if (!file.exists()) {//不存在则创建文件夹
            file.mkdirs();
        }
        try (OutputStream outputStream = new FileOutputStream(filePath + fileName)) {
            Table table = new Table(1);
            table.setClazz(PersonFreeVo.class);
            ExcelUtil.writeExcelWithModel(outputStream, personFreeList, table, PersonFreeVo.class, ExcelTypeEnum.XLSX);

        } catch (IOException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);

        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "downloadUrl", returnPath);

    }

    /**
     * 获取岗位安排信息表
     */
    public void getPostSchedulingInfoRequestProcess() {
        String examId = dataCenterService.getData(ExamManagementConstant.EXAM_ID);
        TssExam exam = dataCenterService.getData("exam");
        // 获取考试下考点考场信息
        List<TssExamPlace> examPlaceList = examPlaceService.selectByTeId(examId);
        //获取考试下场次的list
        List<TssExam> examList = examService.getSubExam(examId);
        List<String> examPlaceIdList = examPlaceList.stream().map(TssExamPlace::getId).collect(Collectors.toList());

        //岗位人员list
        List<TssTempPostPerson> tempPostPersonList = null;

        // 未归档的
        if (ExamManagementConstant.UNFILED_STATUS.equals(exam.getStatus())) {
            tempPostPersonList = tempPostPersonService.selectByExamPlaceIdsInTemp(examPlaceIdList);
        }

        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus())) {
            List<TssPostPerson> postPersonList = postPersonService.selectByExamPlaceIds(examPlaceIdList);
            if (CollectionUtils.isNotEmpty(postPersonList)) {
                for (TssPostPerson tssPostPerson : postPersonList) {
                    TssTempPostPerson tempPostPerson = new TssTempPostPerson();
                    BeanUtils.copyProperties(tssPostPerson, tempPostPerson);
                    tempPostPersonList.add(tempPostPerson);
                }
            }
        }


        // 考试下所有人list
        List<PostSchedulingInfoVo> postSchedulingInfoVoList = new ArrayList<>();

        // 装载 人员id 岗位名称 考点或考场id 场次id 教室id
        if (CollectionUtils.isNotEmpty(tempPostPersonList)) {
            tempPostPersonList.forEach(item -> {

                TssExamPlace tempExamPlace = examPlaceList.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getId(), item.getTepId())).findFirst().get();

                Splitter.on(",").omitEmptyStrings().splitToList(item.getTpiIdStr()).forEach(personId ->
                        postSchedulingInfoVoList.add(PostSchedulingInfoVo
                                .builder()
                                .id(personId)
                                .examPointName(item.getPostName())
                                .examPlaceId(item.getTepId())
                                .examId(tempExamPlace.getTeId())
                                .classId(tempExamPlace.getTciId())
                                .build()));
            });

        }

        //查询人员个人信息
        List<SubmitPersonDto> submitPersonList = null;
        if (CollectionUtils.isNotEmpty(postSchedulingInfoVoList)) {
            //收集人员id
            List<String> personIdList = postSchedulingInfoVoList.stream().map(PostSchedulingInfoVo::getId).collect(Collectors.toList());
            //未归档
            if (ExamManagementConstant.UNFILED_STATUS.equals(exam.getStatus()) && CollectionUtils.isNotEmpty(personIdList)) {
                submitPersonList = tempSubmitPersonDao.selectPersonBatchIds(personIdList, examId);
            }

            //已归档
            if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus()) && CollectionUtils.isNotEmpty(personIdList)) {
                submitPersonList = submitPersonService.selectPersonBatchIds(personIdList);
            }
            //身份证解密
            submitPersonList.forEach(item -> item.setPid(AESUtil.AESDecode(encodeRole, item.getPid())));

        }

        // 装载考试场次 考点 考场号
        if (CollectionUtils.isNotEmpty(postSchedulingInfoVoList)) {

            List<SubmitPersonDto> finalSubmitPersonList = submitPersonList;
            postSchedulingInfoVoList.forEach(item -> {

                SubmitPersonDto tempPersonDto = finalSubmitPersonList.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getId(), item.getId())).findFirst().get();
                //设置场次名称
                item.setExamName(examList.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getId(), item.getExamId())).findFirst().get().getName());
                item.setGender(tempPersonDto.getSex());
                item.setName(tempPersonDto.getName());
                item.setDepartment(tempPersonDto.getDepName());
            });
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "postSchedulingInfoVoList", postSchedulingInfoVoList);
    }

    /**
     * 准考证下载设置
     */
    public void admissionTicketDownloadSettingRequestProcess() {
        TssSolicitationTime solicitationTime = dataCenterService.getData("solicitationTime");
        Date startTime = dataCenterService.getData(ExamManagementConstant.START_TIME);
        Date endTime = dataCenterService.getData(ExamManagementConstant.END_TIME);
        solicitationTime.setAdmissionTicketTimeStart(startTime);
        solicitationTime.setAdmissionTicketTimeEnd(endTime);
        int result = solicitationTimeService.getBaseMapper().updateById(solicitationTime);

        if (result != 1) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.OPERATOR_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }


    /**
     * 导入学生数据设置
     */
    public void importStudentInfoSettingRequestProcess() {
        TssSolicitationTime solicitationTime = dataCenterService.getData("solicitationTime");
        Long depId = dataCenterService.getData(ExamManagementConstant.DEP_ID);
        Date startTime = dataCenterService.getData(ExamManagementConstant.START_TIME);
        Date endTime = dataCenterService.getData(ExamManagementConstant.END_TIME);

        solicitationTime.setDeptId(depId);
        solicitationTime.setStudentTimeStart(startTime);
        solicitationTime.setStudentTimeEnd(endTime);
        int result = solicitationTimeService.getBaseMapper().updateById(solicitationTime);

        if (result != 1) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.OPERATOR_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    /**
     * 查询岗位征集时间 学生导入时间 准考证下载时间
     */
    public void getSolicitationTimeRequestProcess() {
        String examId = dataCenterService.getData(ExamManagementConstant.EXAM_ID);
        TssSolicitationTime solicitationTime = solicitationTimeService.getById(examId);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "solicitationTime", solicitationTime);
    }

    /**
     * 获取考生签到数据
     */
    public void getStuCheckInInfoRequestProcess() {
        String mainExamId = dataCenterService.getData(ExamManagementConstant.MAIN_EXAM_ID);
        String examId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_ID); // 场次id
        String collegeName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.COLLEGE_NAME);
        String stuName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.STU_NAME);
        String examPlaceNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName(ExamManagementConstant.EXAM_PLACE_NUM); // 考场号


    }

    /**
     * 通过场次id获取岗位信息下拉框
     */
    public void getPostInfoByExamIdRequestProcess() {
        String examId = dataCenterService.getData(ExamManagementConstant.EXAM_ID); // 场次id
        //获取考点或考场
        List<TssExamPlace> examPlaceList = examPlaceService.selectByTeIdList(Lists.newArrayList(examId));
        List<String> examPlaceIdList = examPlaceList.stream().map(TssExamPlace::getId).collect(Collectors.toList());
        List<TssTempPostPerson> tempPostPersonList = tempPostPersonService.selectByExamPlaceIdsInTemp(examPlaceIdList);
        List<PostVo> postVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tempPostPersonList)) {
            tempPostPersonList.stream().filter(Objects::nonNull).forEach(item -> {
                PostVo postVo = new PostVo();
                BeanUtils.copyProperties(item, postVo);
                postVoList.add(postVo);
            });
        }

        List<PostVo> postList = postVoList.stream().distinct().collect(Collectors.toList());
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "postList", postList);
    }


    /* -------------------------------------------------------------------------------------------------- */


    /**
     * 设置打印时间
     */
    @Transactional
    public void resetPrintTimeRequestProcess() {
        String admissionTicketTimeStartString = null;
        String admissionTicketTimeEndString = null;
        admissionTicketTimeStartString = dataCenterService.getData("admissionTicketTimeStart");
        admissionTicketTimeEndString = dataCenterService.getData("admissionTicketTimeEnd");
        String examId = dataCenterService.getData("examId");
        TssSolicitationTime tssSolicitationTime = new TssSolicitationTime();
        Date admissionTicketTimeStart = null;
        Date admissionTicketTimeEnd = null;
        try {
            admissionTicketTimeStart = DateTimeUtil.changeStringToDate(admissionTicketTimeStartString);
            admissionTicketTimeEnd = DateTimeUtil.changeStringToDate(admissionTicketTimeEndString);
        } catch (ParseException e) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.PARAM_ERROR);
        }
        tssSolicitationTime.setAdmissionTicketTimeStart(admissionTicketTimeStart);
        tssSolicitationTime.setAdmissionTicketTimeEnd(admissionTicketTimeEnd);

        Boolean result = solicitationTimeService.update(tssSolicitationTime, new QueryWrapper<TssSolicitationTime>().lambda().eq(TssSolicitationTime::getTeId, examId));
        if (!result) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.OPERATOR_ERROR);
        } else {
            ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
            ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        }
    }

    /*
     * author lwn
     * description 岗位签到表
     * date 2019/4/10 23:08
     * param
     * return void
     */
    public void jobSignInRequestProcess() {
        //获取数据
        String examId = dataCenterService.getData("examId");
        String deptIdStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("deptId");//部门id
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String exId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("exId");//场次Id
        String examPointId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPointId");//考点
        String kcId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("kcId");//考场号
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");
        //转换成Long类型
        Long deptId = Longs.tryParse(deptIdStr);
        //获取考试状态
        TssExam exam = tssExamDao.selectOne(new LambdaQueryWrapper<TssExam>().eq(TssExam::getId, examId));
        //根据归档状态条件查询
        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NO_EXAM);
        }

        List<JobSignInInfoVo> displayList = Lists.newArrayList();//最后返回前台的展示对象
        if (ExamManagementConstant.UNFILED_STATUS.equals(exam.getStatus())) { //未归档
            List<JobSignInInfoDto> noArchiveList = tssTempPostPersonDao.seachSignExamInfo(examId, exId, examPointId, kcId);
            PageHelper.startPage(pageNum, pageSize);
            seachJobSignInInfo(displayList, noArchiveList, deptId, name);

        }
        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus())) { //归档
            List<JobSignInInfoDto> ArchiveList = tssPostPersonDao.seachSignExamInfo(examId, exId, examPointId, kcId);
            PageHelper.startPage(pageNum, pageSize);
            seachJobSignInInfo(displayList, ArchiveList, deptId, name);

        }
        //身份证解密
        displayList.forEach(item -> item.setPid(AESUtil.AESDecode(encodeRole, item.getPid())));
        PageInfo<JobSignInInfoVo> pageResult = new PageInfo<>(displayList);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "pageResult", pageResult);


    }

    private void seachJobSignInInfo(List<JobSignInInfoVo> displayList, List<JobSignInInfoDto> noArchiveList, Long deptId, String name) {
        Set<String> personIdSets = Sets.newHashSet();//收集该考试下所有参与的人员id
        Set<String> teIdSets = Sets.newHashSet();//收集该考试下所有场次信息
        for (JobSignInInfoDto jobSignInInfoDto : noArchiveList) {
            teIdSets.add(jobSignInInfoDto.getTeId());
            List<PostPersonDto> jobInfoList = jobSignInInfoDto.getList();//获取该场次所有岗位
            if (jobInfoList == null) {
                continue;
            }
            //遍历该场次所有岗位信息
            for (PostPersonDto postPersonDto : jobInfoList) {
                //获取该岗位人员
                String personStr = postPersonDto.getTpiIdStr();
                String[] personArray = StringUtils.split(personStr, ExamManagementConstant.SEPORATOR_CHARS);
                List<String> personList = Arrays.asList(personArray);
                //构造前台展示对象,构造一个场次下的一个岗位的多个人的前台展示信息
                for (String s : personList) {
                    JobSignInInfoVo jobSignInInfoVo = new JobSignInInfoVo();
                    BeanUtils.copyProperties(jobSignInInfoDto, jobSignInInfoVo);
                    BeanUtils.copyProperties(postPersonDto, jobSignInInfoVo);
                    jobSignInInfoVo.setExamTime(jobSignInInfoDto.getExamTime());//beansUtil不能copy date
                    jobSignInInfoVo.setPersonId(s);//记录该人员id便于后续查询该人员的所有信息
                    personIdSets.add(s);//可能存在一场考试一个人有多个岗位，所以只记录一次一个人的信息
                    displayList.add(jobSignInInfoVo); //最后返回前台的展示对象
                }
            }
        }
        //查询到该考试该场次对应岗位的所有人的个人信息
        if (personIdSets.size() != 0) {
            List<String> personIdList = new ArrayList<>(personIdSets);
            //获取部门level查询部门及其子部门的人
            String newLevel = null;
            if (deptId != null) {
                SysDepartment sysDepartment = sysDepartmentDao.selectOne(new LambdaQueryWrapper<SysDepartment>().eq(SysDepartment::getId, deptId));
                newLevel = sysDepartment.getLevel() + LevelUtil.SEPARATOR + deptId;
            }
            List<TssPersonLibraryDto> personInfoList = tssPersonLibraryDao.searchPersonByIds(personIdList, deptId, name, newLevel);
            //查询该考试下所有场次的签到信息
            List<TssJobSignIn> signInList = jobSignInService.selectByteIds(teIdSets);
            //构造map 通过人员id获取到该id对应所有信息
            Map<String, TssPersonLibraryDto> personInfoMap = Maps.newHashMap();
            for (TssPersonLibraryDto tssPersonLibrary : personInfoList) {
                personInfoMap.put(tssPersonLibrary.getId(), tssPersonLibrary);
            }
            //构造mutimap 通过场次用户id获取到所有场次对应的用户签到信息
            Multimap<String, TssJobSignIn> signInfoMap = ArrayListMultimap.create();
            for (TssJobSignIn tssJobSignIn : signInList) {
                signInfoMap.put(tssJobSignIn.getUserId(), tssJobSignIn);
            }
            //vo里补充个人信息，签到信息
            int count = 1;
            Iterator<JobSignInInfoVo> iterator = displayList.iterator();
            List<Map<String, Object>> examPointMapList = tssDictionaryDao.getDescriptionAndId(ExamManagementConstant.EXAM_POINT);
            Map<Integer, String> examPointMap = Maps.newHashMap();
            listToMap(examPointMapList, examPointMap);
            while (iterator.hasNext()) {
                JobSignInInfoVo jobSignInInfoVo = iterator.next();
                //人员信息
                TssPersonLibraryDto libraryDto = personInfoMap.get(jobSignInInfoVo.getPersonId());
                //根据条件查询到的人员信息，如果没有这个人的信息说明不是条件查询的结果要删掉
                if (libraryDto == null) {
                    iterator.remove();
                    continue;
                }
                BeanUtils.copyProperties(libraryDto, jobSignInInfoVo);
                //签到信息通过用户id获取该用户所有场次的签到，过滤选取当前场次的签到信息
                List<TssJobSignIn> teSignInfoList = (List<TssJobSignIn>) signInfoMap.get(jobSignInInfoVo.getPersonId());
                Optional<TssJobSignIn> personJobSignInfoOption = teSignInfoList.stream().filter(t -> (jobSignInInfoVo.getTeId().equals(t.getTeId()))).findFirst();
                if (personJobSignInfoOption != null && personJobSignInfoOption.isPresent()) {
                    BeanUtils.copyProperties(personJobSignInfoOption.get(), jobSignInInfoVo);
                    jobSignInInfoVo.setSignTime(personJobSignInfoOption.get().getSignTime());
                }
                jobSignInInfoVo.setOrder(count);
                String kch = jobSignInInfoVo.getKch();
                jobSignInInfoVo.setKch(StringUtils.leftPad(kch, ExamManagementConstant.KCH_LENGTH, ExamManagementConstant.PAD_STR));
                if (StringUtils.isBlank(jobSignInInfoVo.getClassName())) { //只安排考场没安排教室页面只显示考点名称不显示考场号
                    jobSignInInfoVo.setKd(examPointMap.get(jobSignInInfoVo.getKch()));
                    jobSignInInfoVo.setKch("");
                }
                count++;
            }

        }

    }

    /**
     * 获取考生签到表查询
     */
    public void getStudentSignTableRequestProcess() {

        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");
        String examId = dataCenterService.getData("examId");
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String scene = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("scene");
        String examPoint = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPoint");
        String examPlaceNumberString = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPlaceNumber");
        Integer examPlaceNumber = null;
        if (!StringUtils.isBlank(examPlaceNumberString)) {
            examPlaceNumber = Integer.parseInt(examPlaceNumberString);
        }
        //获取考试信息
        TssExam exam = tssExamDao.selectOne(new LambdaQueryWrapper<TssExam>().eq(TssExam::getId, examId));
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

        PageHelper.startPage(pageNum, pageSize);
        //dao层方法，examPlaceIdList为空则查不出数据
        List<StudentSignTableVo> studentSignTableVos = Lists.newArrayList();
        if (exam != null) {
            if (exam.getStatus() == 1) {
                studentSignTableVos = tssSeatScheduleDao.getSeatSchedule(examPlaceIdList, name);
            }
            if (exam.getStatus() == 0) {
                studentSignTableVos = tssTempSeatScheduleDao.getSeatSchedule(examPlaceIdList, name);
            }
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
                .eq(TssDictionary::getType, ExamManagementConstant.EXAM_POINT));
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
                //装载考试场次
                item.setSceneName(examList.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getId(), tempExamPlace.getTeId())).findFirst().get().getName());
                //装载考场
                item.setExamPlaceName(StringUtils.leftPad(tempExamPlace.getNameOrSeq().toString(), 3, LevelUtil.ROOT));
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
        //身份证解密
        studentSignTableVos.forEach(item -> item.setZjhm(AESUtil.AESDecode(encodeRole, item.getZjhm())));
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        PageInfo<StudentSignTableVo> pageResult = new PageInfo<>(studentSignTableVos);
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "studentSignTableList", pageResult);
    }

    /**
     * 下载考生签到表
     */
    public void downloadStudentSignTableRequestProcess() {
        String examId = dataCenterService.getData("examId");
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String scene = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("scene");
        String examPoint = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPoint");
        String examPlaceNumberString = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPlaceNumber");
        Integer examPlaceNumber = null;
        if (!StringUtils.isBlank(examPlaceNumberString)) {
            examPlaceNumber = Integer.parseInt(examPlaceNumberString);
        }

        //获取考试信息
        TssExam exam = tssExamDao.selectOne(new LambdaQueryWrapper<TssExam>().eq(TssExam::getId, examId));

        //获取考试下场次的list
        List<TssExam> examList = tssExamDao.getExamScene(examId, scene);
        List<String> examIdList = examList.stream().map(TssExam::getId).collect(Collectors.toList());

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
        List<StudentSignTableVo> studentSignTableVos = Lists.newArrayList();
        if (exam != null) {
            if (exam.getStatus() == 1) {
                studentSignTableVos = tssSeatScheduleDao.getSeatSchedule(examPlaceIdList, name);
            }
            if (exam.getStatus() == 0) {
                studentSignTableVos = tssTempSeatScheduleDao.getSeatSchedule(examPlaceIdList, name);
            }
        }
        if (CollectionUtils.isEmpty(studentSignTableVos)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NO_LIST_TO_BE_DOWNLOAD);
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
                .eq(TssDictionary::getType, ExamManagementConstant.EXAM_POINT));
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
                //装载考试场次
                item.setSceneName(examList.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getId(), tempExamPlace.getTeId())).findFirst().get().getName());
                //装载考场
                item.setExamPlaceName(StringUtils.leftPad(tempExamPlace.getNameOrSeq().toString(), 3, LevelUtil.ROOT));
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
        //身份证解密
        studentSignTableVos.forEach(item -> item.setZjhm(AESUtil.AESDecode(encodeRole, item.getZjhm())));
        //生成excel表格
        Date date1 = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String date = df.format(date1);
        String fileName = "StudentSignTable" + "-" + date + ".xlsx";  //文件名
        String filePath = exportFileLocalPath + studentSignTableFilePath;
        String returnPath = downloadFileWebPath + studentSignTableFilePath + fileName;
        File file = new File(filePath);
        try {
            if (!file.exists()) {//不存在则创建文件夹
                file.mkdirs();
            }
            OutputStream outputStream = new FileOutputStream(filePath + fileName);
            Table table = new Table(1);
            table.setClazz(StudentSignTableVo.class);
            ExcelUtil.writeExcelWithModel(outputStream, studentSignTableVos, table, StudentSignTableVo.class, ExcelTypeEnum.XLSX);
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);

        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "downloadUrl", returnPath);

    }

    /**
     * 获取考务签到页面的考试
     */
    public void getCheckInExamRequestProcess() {
        Date todayStart = DateTimeUtil.getTodeyStartTime();
        Date todayEnd = DateTimeUtil.getTodetEndTime();
        List<TssExam> allExamList = examService.getBaseMapper()
                .selectList(new LambdaQueryWrapper<TssExam>().ne(TssExam::getStatus, 901).between(TssExam::getStartTime, DateUtils.addSeconds(todayStart, -10), DateUtils.addSeconds(todayEnd, 10)));

        List<TssExamDto> examDtoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(allExamList)) {

            // 考试list
            List<TssExam> mainExamList = allExamList.stream().filter(value -> StringUtils.equals(value.getParentId(), "0")).collect(Collectors.toList());

            mainExamList.forEach(item -> {
                //场次list
                TssExamDto tssExamDto = new TssExamDto();
                List<TssExam> examList = allExamList.stream().filter(value -> StringUtils.equals(value.getParentId(), item.getId())).collect(Collectors.toList());
                BeanUtils.copyProperties(item, tssExamDto);
                tssExamDto.setExams(examList);
                examDtoList.add(tssExamDto);
            });
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "examList", examDtoList);

    }

    /**
     * author lwn
     * description  下载岗位签到表
     * date 2019/4/13 13:41
     * param
     * return
     */
    void downloadJobSignInRequestProcess(HttpServletRequest request, HttpServletResponse response) {
        //获取数据
        String examId = dataCenterService.getData("examId");
        String deptIdStr = request.getParameter("deptId");//部门id
        String name = request.getParameter("name");
        String exId = request.getParameter("exId");//场次Id
        String examPointId = request.getParameter("examPointId");//考点
        String kcId = request.getParameter("kcId");//考场号
        //转换成Long类型
        Long deptId = Longs.tryParse(deptIdStr);
        //获取考试状态
        TssExam exam = tssExamDao.selectOne(new LambdaQueryWrapper<TssExam>().eq(TssExam::getId, examId));
        //根据归档状态条件查询
        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NO_EXAM);
        }
        List<JobSignInInfoVo> displayList = Lists.newArrayList();//最后返回前台的展示对象
        if (ExamManagementConstant.UNFILED_STATUS.equals(exam.getStatus())) { //未归档
            List<JobSignInInfoDto> noArchiveList = tssTempPostPersonDao.seachSignExamInfo(examId, exId, examPointId, kcId);
            seachJobSignInInfo(displayList, noArchiveList, deptId, name);

        }
        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus())) { //归档
            List<JobSignInInfoDto> ArchiveList = tssPostPersonDao.seachSignExamInfo(examId, exId, examPointId, kcId);
            seachJobSignInInfo(displayList, ArchiveList, deptId, name);

        }
        Date date1 = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String date = df.format(date1);
        String fileName = "jobSignIn" + "-" + date + ".xlsx";  //文件名
        //写入并下载excel
        //身份证解密
        displayList.forEach(item -> item.setPid(AESUtil.AESDecode(encodeRole, item.getPid())));
        String filePath = exportFileLocalPath + jobSignInFileSave;
        String returnPath = downloadFileWebPath + jobSignInFileSave + fileName;
        File file = new File(filePath);
        if (!file.exists()) {//不存在则创建文件夹
            file.mkdirs();
        }
        try (OutputStream outputStream = new FileOutputStream(filePath + fileName)) {
            Table table = new Table(1);
            table.setClazz(JobSignInInfoVo.class);
            ExcelUtil.writeExcelWithModel(outputStream, displayList, table, JobSignInInfoVo.class, ExcelTypeEnum.XLSX);


        } catch (IOException e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "downloadUrl", returnPath);
    }

    /**
     * 获取岗位通知单
     */
    public void getPostNoticeListRequestProcess() {

        //获取数据
        String examId = dataCenterService.getData("examId");
        //过滤的一些条件
        String deptIdStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("deptId");//部门id
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String exId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("exId");//场次Id
        String examPointId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examPointId");//考点
        String kcId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("kcId");//考场号

        //管理员自己添加的数据
        String content = dataCenterService.getData("content");
        String level = dataCenterService.getData("level");
        String trainTime = dataCenterService.getData("trainTime");
        String attention = dataCenterService.getData("attention");
        String time = dataCenterService.getData("time");
        String template = dataCenterService.getData("template"); //模板码
        JSONArray groupJA = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("postNameList");
        List<String> postNameList = groupJA.toJavaList(String.class);

        //转换成Long类型
        Long deptId = null;
        if (!StringUtils.isBlank(deptIdStr)) {
            deptId = Longs.tryParse(deptIdStr);
        }

        //获取考试状态
        TssExam exam = tssExamDao.selectOne(new LambdaQueryWrapper<TssExam>().eq(TssExam::getId, examId));
        //根据归档状态条件查询
        if (exam == null) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NO_EXAM);
        }

        List<JobSignInInfoVo> displayList = Lists.newArrayList();

        if (ExamManagementConstant.UNFILED_STATUS.equals(exam.getStatus())) { //未归档
            List<JobSignInInfoDto> noArchiveList = tssTempPostPersonDao.seachSignExamInfo(examId, exId, examPointId, kcId);
            seachJobSignInInfo(displayList, noArchiveList, deptId, name);

        }
        if (ExamManagementConstant.ARCHIVE_STATUS.equals(exam.getStatus())) { //归档
            List<JobSignInInfoDto> ArchiveList = tssPostPersonDao.seachSignExamInfo(examId, exId, examPointId, kcId);
            seachJobSignInInfo(displayList, ArchiveList, deptId, name);

        }
        //身份证解密
        displayList.forEach(item -> item.setPid(AESUtil.AESDecode(encodeRole, item.getPid())));
        List<JobSignInInfoVo> postNoticeList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(postNameList)) {
            postNoticeList = displayList.stream().filter(value -> postNameList.contains(value.getJobName())).collect(Collectors.toList());
        } else {
            postNoticeList = displayList;
        }
        if (CollectionUtils.isEmpty(postNoticeList)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NO_LIST_TO_BE_DOWNLOAD);
        }
        //获取所有学院 获取考点id
        List<String> departments = Lists.newArrayList();
        List<String> examPointIds = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(postNoticeList)) {
            departments = postNoticeList.stream().map(JobSignInInfoVo::getPostName).distinct().collect(Collectors.toList());
            examPointIds = postNoticeList.stream().map(JobSignInInfoVo::getParentId).distinct().collect(Collectors.toList());
        }
        //查询主考室
        List<ExamPointClassDto> examPointClassDtos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(examPointIds)) {
            examPointClassDtos = tssExamPlaceDao.getMainClassByExamPointIds(examPointIds);
        }
        //创建pdf
        //创建随机生成文件夹
        String fileName = IdUtils.getUUID();
        String filePath = exportFileLocalPath + postNoticeListFilePath + fileName;
        File dir = new File(filePath.toString());
        try {
            FileUtils.forceMkdir(dir);
            //遍历学院名 按照每个学院的信息创建pdf
            Map<String, String> fileNameMap = new HashMap<>();
            for (String department : departments) {
                fileNameMap.put(department, department + ".pdf");
                List<JobSignInInfoVo> branchNoticeList = postNoticeList.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getPostName(), department)).collect(Collectors.toList());
                List<List<PdfMessageListVo>> datas = Lists.newArrayList();
                int total = branchNoticeList.size();
                if (total % 2 == 1) {
                    total--;//减一的目的奇偶个数的监考单处理起来相同
                }
                if (StringUtils.equals(template, "0")) {
                    for (int count = 0; count < total; count = count + 2) {
                        List<PdfMessageListVo> data = Lists.newArrayList();
                        //装载这一页的信息（一页两个监考单）

                        PdfMessageListVo mainExamNameOne = new PdfMessageListVo();
                        mainExamNameOne.setName(ExamManagementConstant.PDF_FIELD_ONE[0]);
                        mainExamNameOne.setContent(exam.getName());
                        data.add(mainExamNameOne);
                        PdfMessageListVo collegeOne = new PdfMessageListVo();
                        collegeOne.setName(ExamManagementConstant.PDF_FIELD_ONE[1]);
                        collegeOne.setContent(branchNoticeList.get(count).getPostName());
                        data.add(collegeOne);
                        PdfMessageListVo nameOne = new PdfMessageListVo();
                        nameOne.setName(ExamManagementConstant.PDF_FIELD_ONE[2]);
                        nameOne.setContent(branchNoticeList.get(count).getName() + ExamManagementConstant.PDF_NAME_SUFFIX);
                        data.add(nameOne);
                        PdfMessageListVo contentOne = new PdfMessageListVo();
                        contentOne.setName(ExamManagementConstant.PDF_FIELD_ONE[3]);
                        contentOne.setContent(content);
                        data.add(contentOne);
                        PdfMessageListVo examTimeOne = new PdfMessageListVo();
                        examTimeOne.setName(ExamManagementConstant.PDF_FIELD_ONE[4]);
                        examTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count).getExamTime()));
                        data.add(examTimeOne);
                        PdfMessageListVo levelOne = new PdfMessageListVo();
                        levelOne.setName(ExamManagementConstant.PDF_FIELD_ONE[5]);
                        levelOne.setContent(level);
                        data.add(levelOne);
                        PdfMessageListVo trainAddressOne = new PdfMessageListVo();
                        trainAddressOne.setName(ExamManagementConstant.PDF_FIELD_ONE[6]);
                        String parentIdOne = branchNoticeList.get(count).getParentId();
                        trainAddressOne.setContent(examPointClassDtos.stream()
                                .filter(Value -> StringUtils.equalsIgnoreCase(Value.getId(), parentIdOne))
                                .findFirst().get().getName());
                        data.add(trainAddressOne);
                        PdfMessageListVo trainTimeOne = new PdfMessageListVo();
                        trainTimeOne.setName(ExamManagementConstant.PDF_FIELD_ONE[7]);
                        trainTimeOne.setContent(trainTime);
                        data.add(trainTimeOne);
                        PdfMessageListVo examAddressOne = new PdfMessageListVo();
                        examAddressOne.setName(ExamManagementConstant.PDF_FIELD_ONE[8]);
                        examAddressOne.setContent(branchNoticeList.get(count).getClassName());
                        data.add(examAddressOne);
                        PdfMessageListVo numberOne = new PdfMessageListVo();
                        numberOne.setName(ExamManagementConstant.PDF_FIELD_ONE[9]);
                        numberOne.setContent(branchNoticeList.get(count).getKch() + ExamManagementConstant.PDF_EXAM_ADDRESS_SUFFIX);
                        data.add(numberOne);
                        PdfMessageListVo attentionOne = new PdfMessageListVo();
                        attentionOne.setName(ExamManagementConstant.PDF_FIELD_ONE[10]);
                        attentionOne.setContent(attention);
                        data.add(attentionOne);
                        PdfMessageListVo timeOne = new PdfMessageListVo();
                        timeOne.setName(ExamManagementConstant.PDF_FIELD_ONE[11]);
                        timeOne.setContent(time);
                        data.add(timeOne);

                        PdfMessageListVo mainExamNameTwo = new PdfMessageListVo();
                        mainExamNameTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[0]);
                        mainExamNameTwo.setContent(exam.getName());
                        data.add(mainExamNameTwo);
                        PdfMessageListVo collegeTwo = new PdfMessageListVo();
                        collegeTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[1]);
                        collegeTwo.setContent(branchNoticeList.get(count + 1).getPostName());
                        data.add(collegeTwo);
                        PdfMessageListVo nameTwo = new PdfMessageListVo();
                        nameTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[2]);
                        nameTwo.setContent(branchNoticeList.get(count + 1).getName() + ExamManagementConstant.PDF_NAME_SUFFIX);
                        data.add(nameTwo);
                        PdfMessageListVo contentTwo = new PdfMessageListVo();
                        contentTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[3]);
                        contentTwo.setContent(content);
                        data.add(contentTwo);
                        PdfMessageListVo examTimeTwo = new PdfMessageListVo();
                        examTimeTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[4]);
                        examTimeTwo.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count + 1).getExamTime()));
                        data.add(examTimeTwo);
                        PdfMessageListVo levelTwo = new PdfMessageListVo();
                        levelTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[5]);
                        levelTwo.setContent(level);
                        data.add(levelTwo);
                        PdfMessageListVo trainAddressTwo = new PdfMessageListVo();
                        trainAddressTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[6]);
                        String parentIdTwo = branchNoticeList.get(count + 1).getParentId();
                        trainAddressOne.setContent(examPointClassDtos.stream()
                                .filter(Value -> StringUtils.equalsIgnoreCase(Value.getId(), parentIdTwo))
                                .findFirst().get().getName());
                        data.add(trainAddressTwo);
                        PdfMessageListVo trainTimeTwo = new PdfMessageListVo();
                        trainTimeTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[7]);
                        trainTimeTwo.setContent(trainTime);
                        data.add(trainTimeTwo);
                        PdfMessageListVo examAddressTwo = new PdfMessageListVo();
                        examAddressTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[8]);
                        examAddressTwo.setContent(branchNoticeList.get(count + 1).getClassName());
                        data.add(examAddressTwo);
                        PdfMessageListVo numberTwo = new PdfMessageListVo();
                        numberTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[9]);
                        numberTwo.setContent(branchNoticeList.get(count + 1).getKch() + ExamManagementConstant.PDF_EXAM_ADDRESS_SUFFIX);
                        data.add(numberTwo);
                        PdfMessageListVo attentionTwo = new PdfMessageListVo();
                        attentionTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[10]);
                        attentionTwo.setContent(attention);
                        data.add(attentionTwo);
                        PdfMessageListVo timeTwo = new PdfMessageListVo();
                        timeTwo.setName(ExamManagementConstant.PDF_FIELD_TWO[11]);
                        timeTwo.setContent(time);
                        data.add(timeTwo);

                        datas.add(data);
                    }
                    int lastMessage = branchNoticeList.size();
                    if (lastMessage % 2 == 1) {
                        List<PdfMessageListVo> data = Lists.newArrayList();
                        PdfMessageListVo mainExamNameOne = new PdfMessageListVo();
                        mainExamNameOne.setName(ExamManagementConstant.PDF_FIELD_ONE[0]);
                        mainExamNameOne.setContent(exam.getName());
                        data.add(mainExamNameOne);
                        PdfMessageListVo collegeOne = new PdfMessageListVo();
                        collegeOne.setName(ExamManagementConstant.PDF_FIELD_ONE[1]);
                        collegeOne.setContent(branchNoticeList.get(lastMessage - 1).getPostName());
                        data.add(collegeOne);
                        PdfMessageListVo nameOne = new PdfMessageListVo();
                        nameOne.setName(ExamManagementConstant.PDF_FIELD_ONE[2]);
                        nameOne.setContent(branchNoticeList.get(lastMessage - 1).getName() + ExamManagementConstant.PDF_NAME_SUFFIX);
                        data.add(nameOne);
                        PdfMessageListVo contentOne = new PdfMessageListVo();
                        contentOne.setName(ExamManagementConstant.PDF_FIELD_ONE[3]);
                        contentOne.setContent(content);
                        data.add(contentOne);
                        PdfMessageListVo examTimeOne = new PdfMessageListVo();
                        examTimeOne.setName(ExamManagementConstant.PDF_FIELD_ONE[4]);
                        examTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 1).getExamTime()));
                        data.add(examTimeOne);
                        PdfMessageListVo levelOne = new PdfMessageListVo();
                        levelOne.setName(ExamManagementConstant.PDF_FIELD_ONE[5]);
                        levelOne.setContent(level);
                        data.add(levelOne);
                        PdfMessageListVo trainAddressOne = new PdfMessageListVo();
                        trainAddressOne.setName(ExamManagementConstant.PDF_FIELD_ONE[6]);
                        String parentIdOne = branchNoticeList.get(lastMessage - 1).getParentId();
                        trainAddressOne.setContent(examPointClassDtos.stream()
                                .filter(Value -> StringUtils.equalsIgnoreCase(Value.getId(), parentIdOne))
                                .findFirst().get().getName());
                        data.add(trainAddressOne);
                        PdfMessageListVo trainTimeOne = new PdfMessageListVo();
                        trainTimeOne.setName(ExamManagementConstant.PDF_FIELD_ONE[7]);
                        trainTimeOne.setContent(trainTime);
                        data.add(trainTimeOne);
                        PdfMessageListVo examAddressOne = new PdfMessageListVo();
                        examAddressOne.setName(ExamManagementConstant.PDF_FIELD_ONE[8]);
                        examAddressOne.setContent(branchNoticeList.get(lastMessage - 1).getClassName());
                        data.add(examAddressOne);
                        PdfMessageListVo numberOne = new PdfMessageListVo();
                        numberOne.setName(ExamManagementConstant.PDF_FIELD_ONE[9]);
                        numberOne.setContent(branchNoticeList.get(lastMessage - 1).getKch() + ExamManagementConstant.PDF_EXAM_ADDRESS_SUFFIX);
                        data.add(numberOne);
                        PdfMessageListVo attentionOne = new PdfMessageListVo();
                        attentionOne.setName(ExamManagementConstant.PDF_FIELD_ONE[10]);
                        attentionOne.setContent(attention);
                        data.add(attentionOne);
                        PdfMessageListVo timeOne = new PdfMessageListVo();
                        timeOne.setName(ExamManagementConstant.PDF_FIELD_ONE[11]);
                        timeOne.setContent(time);
                        data.add(timeOne);

                        datas.add(data);
                    }
                    String pdfFilePostNoticeOnePath = String.valueOf(ResourceUtils.getURL("target/classes/pdfTemplates/postnoticeone.pdf"));
                    String pdfFilePostNoticeTwoPath = String.valueOf(ResourceUtils.getURL("target/classes/pdfTemplates/postnoticetwo.pdf"));
                    PDFForExamUtil.createPostNoticePdf(datas, pdfFilePostNoticeOnePath, pdfFilePostNoticeTwoPath, filePath, department + ".pdf");
                }

                if (StringUtils.equals(template, "1")) {
                    for (int count = 0; count < total; count = count + 2) {
                        List<PdfMessageListVo> data = Lists.newArrayList();
                        //装载这一页的信息（一页两个监考单）

                        PdfMessageListVo mainExamNameOne = new PdfMessageListVo();
                        mainExamNameOne.setName(ExamManagementConstant.PDF_FIELD_THREE[0]);
                        mainExamNameOne.setContent(exam.getName());
                        data.add(mainExamNameOne);
                        PdfMessageListVo collegeOne = new PdfMessageListVo();
                        collegeOne.setName(ExamManagementConstant.PDF_FIELD_THREE[1]);
                        collegeOne.setContent(branchNoticeList.get(count).getPostName());
                        data.add(collegeOne);
                        PdfMessageListVo nameOne = new PdfMessageListVo();
                        nameOne.setName(ExamManagementConstant.PDF_FIELD_THREE[2]);
                        nameOne.setContent(branchNoticeList.get(count).getName() + ExamManagementConstant.PDF_NAME_SUFFIX);
                        data.add(nameOne);
                        PdfMessageListVo contentOne = new PdfMessageListVo();
                        contentOne.setName(ExamManagementConstant.PDF_FIELD_THREE[3]);
                        contentOne.setContent(content);
                        data.add(contentOne);
                        PdfMessageListVo examTimeOne = new PdfMessageListVo();
                        examTimeOne.setName(ExamManagementConstant.PDF_FIELD_THREE[4]);
                        examTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count).getExamTime()));
                        data.add(examTimeOne);
                        PdfMessageListVo levelOne = new PdfMessageListVo();
                        levelOne.setName(ExamManagementConstant.PDF_FIELD_THREE[5]);
                        levelOne.setContent(level);
                        data.add(levelOne);
                        PdfMessageListVo addressOne = new PdfMessageListVo();
                        addressOne.setName(ExamManagementConstant.PDF_FIELD_THREE[6]);
                        String parentIdOne = branchNoticeList.get(count).getParentId();
                        addressOne.setContent(examPointClassDtos.stream()
                                .filter(Value -> StringUtils.equalsIgnoreCase(Value.getId(), parentIdOne))
                                .findFirst().get().getName());
                        data.add(addressOne);
                        PdfMessageListVo trainTimeOne = new PdfMessageListVo();
                        trainTimeOne.setName(ExamManagementConstant.PDF_FIELD_THREE[7]);
                        trainTimeOne.setContent(trainTime);
                        data.add(trainTimeOne);
                        PdfMessageListVo postOne = new PdfMessageListVo();
                        postOne.setName(ExamManagementConstant.PDF_FIELD_THREE[8]);
                        postOne.setContent(branchNoticeList.get(count).getJobName());
                        data.add(postOne);
                        PdfMessageListVo attentionOne = new PdfMessageListVo();
                        attentionOne.setName(ExamManagementConstant.PDF_FIELD_THREE[9]);
                        attentionOne.setContent(attention);
                        data.add(attentionOne);
                        PdfMessageListVo timeOne = new PdfMessageListVo();
                        timeOne.setName(ExamManagementConstant.PDF_FIELD_THREE[10]);
                        timeOne.setContent(time);
                        data.add(timeOne);

                        PdfMessageListVo mainExamNameTwo = new PdfMessageListVo();
                        mainExamNameTwo.setName(ExamManagementConstant.PDF_FIELD_FOUR[0]);
                        mainExamNameTwo.setContent(exam.getName());
                        data.add(mainExamNameTwo);
                        PdfMessageListVo collegeTwo = new PdfMessageListVo();
                        collegeTwo.setName(ExamManagementConstant.PDF_FIELD_FOUR[1]);
                        collegeTwo.setContent(branchNoticeList.get(count + 1).getPostName());
                        data.add(collegeTwo);
                        PdfMessageListVo nameTwo = new PdfMessageListVo();
                        nameTwo.setName(ExamManagementConstant.PDF_FIELD_FOUR[2]);
                        nameTwo.setContent(branchNoticeList.get(count + 1).getName() + ExamManagementConstant.PDF_NAME_SUFFIX);
                        data.add(nameTwo);
                        PdfMessageListVo contentTwo = new PdfMessageListVo();
                        contentTwo.setName(ExamManagementConstant.PDF_FIELD_FOUR[3]);
                        contentTwo.setContent(content);
                        data.add(contentTwo);
                        PdfMessageListVo examTimeTwo = new PdfMessageListVo();
                        examTimeTwo.setName(ExamManagementConstant.PDF_FIELD_FOUR[4]);
                        examTimeTwo.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count + 1).getExamTime()));
                        data.add(examTimeTwo);
                        PdfMessageListVo levelTwo = new PdfMessageListVo();
                        levelTwo.setName(ExamManagementConstant.PDF_FIELD_FOUR[5]);
                        levelTwo.setContent(level);
                        data.add(levelTwo);
                        PdfMessageListVo addressTwo = new PdfMessageListVo();
                        addressTwo.setName(ExamManagementConstant.PDF_FIELD_FOUR[6]);
                        String parentIdTwo = branchNoticeList.get(count + 1).getParentId();
                        addressOne.setContent(examPointClassDtos.stream()
                                .filter(Value -> StringUtils.equalsIgnoreCase(Value.getId(), parentIdTwo))
                                .findFirst().get().getName());
                        data.add(addressTwo);
                        PdfMessageListVo trainTimeTwo = new PdfMessageListVo();
                        trainTimeTwo.setName(ExamManagementConstant.PDF_FIELD_FOUR[7]);
                        trainTimeTwo.setContent(trainTime);
                        data.add(trainTimeTwo);
                        PdfMessageListVo postTwo = new PdfMessageListVo();
                        postTwo.setName(ExamManagementConstant.PDF_FIELD_FOUR[8]);
                        postTwo.setContent(branchNoticeList.get(count + 1).getJobName());
                        data.add(postTwo);
                        PdfMessageListVo attentionTwo = new PdfMessageListVo();
                        attentionTwo.setName(ExamManagementConstant.PDF_FIELD_FOUR[9]);
                        attentionTwo.setContent(attention);
                        data.add(attentionTwo);
                        PdfMessageListVo timeTwo = new PdfMessageListVo();
                        timeTwo.setName(ExamManagementConstant.PDF_FIELD_FOUR[10]);
                        timeTwo.setContent(time);
                        data.add(timeTwo);

                        datas.add(data);
                    }
                    int lastMessage = branchNoticeList.size();
                    if (lastMessage % 2 == 1) {
                        List<PdfMessageListVo> data = Lists.newArrayList();
                        PdfMessageListVo mainExamNameOne = new PdfMessageListVo();
                        mainExamNameOne.setName(ExamManagementConstant.PDF_FIELD_THREE[0]);
                        mainExamNameOne.setContent(exam.getName());
                        data.add(mainExamNameOne);
                        PdfMessageListVo collegeOne = new PdfMessageListVo();
                        collegeOne.setName(ExamManagementConstant.PDF_FIELD_THREE[1]);
                        collegeOne.setContent(branchNoticeList.get(lastMessage - 1).getPostName());
                        data.add(collegeOne);
                        PdfMessageListVo nameOne = new PdfMessageListVo();
                        nameOne.setName(ExamManagementConstant.PDF_FIELD_THREE[2]);
                        nameOne.setContent(branchNoticeList.get(lastMessage - 1).getName() + ExamManagementConstant.PDF_NAME_SUFFIX);
                        data.add(nameOne);
                        PdfMessageListVo contentOne = new PdfMessageListVo();
                        contentOne.setName(ExamManagementConstant.PDF_FIELD_THREE[3]);
                        contentOne.setContent(content);
                        data.add(contentOne);
                        PdfMessageListVo examTimeOne = new PdfMessageListVo();
                        examTimeOne.setName(ExamManagementConstant.PDF_FIELD_THREE[4]);
                        examTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 1).getExamTime()));
                        data.add(examTimeOne);
                        PdfMessageListVo levelOne = new PdfMessageListVo();
                        levelOne.setName(ExamManagementConstant.PDF_FIELD_THREE[5]);
                        levelOne.setContent(level);
                        data.add(levelOne);
                        PdfMessageListVo addressOne = new PdfMessageListVo();
                        addressOne.setName(ExamManagementConstant.PDF_FIELD_THREE[6]);
                        String parentIdOne = branchNoticeList.get(lastMessage - 1).getParentId();
                        addressOne.setContent(examPointClassDtos.stream()
                                .filter(Value -> StringUtils.equalsIgnoreCase(Value.getId(), parentIdOne))
                                .findFirst().get().getName());
                        data.add(addressOne);
                        PdfMessageListVo trainTimeOne = new PdfMessageListVo();
                        trainTimeOne.setName(ExamManagementConstant.PDF_FIELD_THREE[7]);
                        trainTimeOne.setContent(trainTime);
                        data.add(trainTimeOne);
                        PdfMessageListVo postOne = new PdfMessageListVo();
                        postOne.setName(ExamManagementConstant.PDF_FIELD_THREE[8]);
                        postOne.setContent(branchNoticeList.get(lastMessage - 1).getJobName());
                        data.add(postOne);
                        PdfMessageListVo attentionOne = new PdfMessageListVo();
                        attentionOne.setName(ExamManagementConstant.PDF_FIELD_THREE[9]);
                        attentionOne.setContent(attention);
                        data.add(attentionOne);
                        PdfMessageListVo timeOne = new PdfMessageListVo();
                        timeOne.setName(ExamManagementConstant.PDF_FIELD_THREE[10]);
                        timeOne.setContent(time);
                        data.add(timeOne);

                        datas.add(data);
                    }
                    String pdfFilePostNoticeThreePath = String.valueOf(ResourceUtils.getURL("target/classes/pdfTemplates/postnoticethree.pdf"));
                    String pdfFilePostNoticeFourPath = String.valueOf(ResourceUtils.getURL("target/classes/pdfTemplates/postnoticefour.pdf"));
                    PDFForExamUtil.createPostNoticePdf(datas, pdfFilePostNoticeThreePath, pdfFilePostNoticeFourPath, filePath, department + ".pdf");
                }
            }
            ToZip.packageZip(ExamManagementConstant.POST_NOTICE_LIST_NAME, fileNameMap, filePath);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
        }
        String zipPath = downloadFileWebPath + postNoticeListFilePath + fileName + '/' + ExamManagementConstant.POST_NOTICE_LIST_NAME;
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "zipPath", zipPath);
    }

    /**
     * author lwn
     * description  考务签到
     * date 2019/4/15 15:32
     * param
     * return void
     */
    public void kaoWuSignInRequestProcess() {
        String teId = dataCenterService.getData("teId");//场次id
        String pid = dataCenterService.getData("pid");
        String examPointId = dataCenterService.getData("examPointId");
        List<JobSignInInfoVo> displayList = Lists.newArrayList();//最后返回前台的展示对象
        List<JobSignInInfoDto> noArchiveList = tssTempPostPersonDao.seachSignExamInfo(null, teId, examPointId, null);
        Set<String> personIdSets = Sets.newHashSet();//收集该考试下所有参与的人员id
        Set<String> teIdSets = Sets.newHashSet();//收集该考试下所有场次信息
        for (JobSignInInfoDto jobSignInInfoDto : noArchiveList) {
            teIdSets.add(jobSignInInfoDto.getTeId());
            List<PostPersonDto> jobInfoList = jobSignInInfoDto.getList();//获取该场次所有岗位
            //遍历该场次所有岗位信息
            for (PostPersonDto postPersonDto : jobInfoList) {
                //获取该岗位人员
                String personStr = postPersonDto.getTpiIdStr();
                String[] personArray = StringUtils.split(personStr, ExamManagementConstant.SEPORATOR_CHARS);
                List<String> personList = Arrays.asList(personArray);
                //构造前台展示对象,构造一个场次下的一个岗位的多个人的前台展示信息
                for (String s : personList) {
                    JobSignInInfoVo jobSignInInfoVo = new JobSignInInfoVo();
                    BeanUtils.copyProperties(jobSignInInfoVo, jobSignInInfoDto);
                    BeanUtils.copyProperties(jobSignInInfoVo, postPersonDto);
                    jobSignInInfoVo.setPersonId(s);//记录该人员id便于后续查询该人员的所有信息
                    personIdSets.add(s);//可能存在一场考试一个人有多个岗位，所以只记录一次一个人的信息
                    displayList.add(jobSignInInfoVo); //最后返回前台的展示对象
                }
            }
        }
        //身份证加密
        String aesPid = AESUtil.AESEncode(encodeRole, pid);
        //获取身份证所对应的用户id
        TssPersonLibrary signInUser = tssPersonLibraryDao.selectOne(new LambdaQueryWrapper<TssPersonLibrary>().eq(TssPersonLibrary::getPid, aesPid));
        //判断该用户是不是当前考点场次的人员
        if (!personIdSets.contains(signInUser.getId())) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SIGN_IN_ERROR);
        }
        //判断是否已经签到
        int count = jobSignInService.count(new LambdaQueryWrapper<TssJobSignIn>().eq(TssJobSignIn::getUserId, signInUser.getId()).eq(TssJobSignIn::getTeId, teId));
        if (count > 0) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.ALREADY_SIGN_IN);
        }
        //插入签到信息
        TssJobSignIn jobSignIn = new TssJobSignIn();
        jobSignIn.setUserId(signInUser.getId());
        jobSignIn.setSignTime(new Date());
        jobSignIn.setTeId(teId);
        boolean insertResult = jobSignInService.save(jobSignIn);
        if (!insertResult) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

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
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NO_EXAM);
        }

        //获取考试下场次的list
        List<TssExam> examList = tssExamDao.getExamScene(examId, scene);
        List<String> examIdList = examList.stream().map(TssExam::getId).collect(Collectors.toList());

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
        if (CollectionUtils.isEmpty(studentSignTableVos)) {
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.NO_LIST_TO_BE_DOWNLOAD);
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
                .eq(TssDictionary::getType, ExamManagementConstant.EXAM_POINT));
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

        //获取所有学院
        List<String> departments = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(studentSignTableVos)) {
            departments = studentSignTableVos.stream().map(StudentSignTableVo::getYx).distinct().collect(Collectors.toList());
        }
        //创建pdf
        //创建随机生成文件夹
        String fileName = IdUtils.getUUID();
        String filePath = exportFileLocalPath + cardForExamFilePath + fileName;
        File dir = new File(filePath.toString());
        try {
            FileUtils.forceMkdir(dir);
            //遍历学院名 按照每个学院的信息创建pdf
            Map<String, String> fileNameMap = new HashMap<>();
            for (String department : departments) {
                fileNameMap.put(department, department + ".pdf");
                List<StudentSignTableVo> branchNoticeList = studentSignTableVos.stream().filter(value -> StringUtils.equalsIgnoreCase(value.getYx(), department)).collect(Collectors.toList());
                //身份证解密
                branchNoticeList.forEach(item -> item.setZjhm(AESUtil.AESDecode(encodeRole, item.getZjhm())));
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
                    mainExamNameOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[0]);
                    mainExamNameOne.setContent(exam.getName());
                    data.add(mainExamNameOne);
                    PdfMessageListVo stuNumberOne = new PdfMessageListVo();
                    stuNumberOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[1]);
                    stuNumberOne.setContent(branchNoticeList.get(count).getXh());
                    data.add(stuNumberOne);
                    PdfMessageListVo nameOne = new PdfMessageListVo();
                    nameOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[2]);
                    nameOne.setContent(branchNoticeList.get(count).getXm());
                    data.add(nameOne);
                    PdfMessageListVo examLanguageOne = new PdfMessageListVo();
                    examLanguageOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[3]);
                    examLanguageOne.setContent(branchNoticeList.get(count).getBskmmc());
                    data.add(examLanguageOne);
                    PdfMessageListVo collegeOne = new PdfMessageListVo();
                    collegeOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[4]);
                    collegeOne.setContent(branchNoticeList.get(count).getYx());
                    data.add(collegeOne);
                    PdfMessageListVo majorOne = new PdfMessageListVo();
                    majorOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[5]);
                    majorOne.setContent(branchNoticeList.get(count).getZy());
                    data.add(majorOne);
                    PdfMessageListVo examNumberOne = new PdfMessageListVo();
                    examNumberOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[6]);
                    examNumberOne.setContent(branchNoticeList.get(count).getBszkzh());
                    data.add(examNumberOne);
                    //中间不设置photo
                    if (branchNoticeList.get(count).getType() == 1) {
                        PdfMessageListVo penTimeOne = new PdfMessageListVo();
                        penTimeOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[8]);
                        penTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count).getStartTime()));
                        data.add(penTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[9]);
                        penAddressOne.setContent(branchNoticeList.get(count).getClassName());
                        data.add(penAddressOne);
                    }
                    if (branchNoticeList.get(count).getType() == 0) {
                        PdfMessageListVo computerTimeOne = new PdfMessageListVo();
                        computerTimeOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[10]);
                        computerTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count).getStartTime()));
                        data.add(computerTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[11]);
                        penAddressOne.setContent(branchNoticeList.get(count).getClassName());
                        data.add(penAddressOne);
                    }

                    PdfMessageListVo mainExamNameTwo = new PdfMessageListVo();
                    mainExamNameTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[0]);
                    mainExamNameTwo.setContent(exam.getName());
                    data.add(mainExamNameTwo);
                    PdfMessageListVo stuNumberTwo = new PdfMessageListVo();
                    stuNumberTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[1]);
                    stuNumberTwo.setContent(branchNoticeList.get(count + 1).getXh());
                    data.add(stuNumberTwo);
                    PdfMessageListVo nameTwo = new PdfMessageListVo();
                    nameTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[2]);
                    nameTwo.setContent(branchNoticeList.get(count + 1).getXm());
                    data.add(nameTwo);
                    PdfMessageListVo examLanguageTwo = new PdfMessageListVo();
                    examLanguageTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[3]);
                    examLanguageTwo.setContent(branchNoticeList.get(count + 1).getBskmmc());
                    data.add(examLanguageTwo);
                    PdfMessageListVo collegeTwo = new PdfMessageListVo();
                    collegeTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[4]);
                    collegeTwo.setContent(branchNoticeList.get(count + 1).getYx());
                    data.add(collegeTwo);
                    PdfMessageListVo majorTwo = new PdfMessageListVo();
                    majorTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[5]);
                    majorTwo.setContent(branchNoticeList.get(count + 1).getZy());
                    data.add(majorTwo);
                    PdfMessageListVo examNumberTwo = new PdfMessageListVo();
                    examNumberTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[6]);
                    examNumberTwo.setContent(branchNoticeList.get(count + 1).getBszkzh());
                    data.add(examNumberTwo);
                    //中间不设置photo
                    if (branchNoticeList.get(count + 1).getType() == 1) {
                        PdfMessageListVo penTimeTwo = new PdfMessageListVo();
                        penTimeTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[8]);
                        penTimeTwo.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count + 1).getStartTime()));
                        data.add(penTimeTwo);
                        PdfMessageListVo penAddressTwo = new PdfMessageListVo();
                        penAddressTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[9]);
                        penAddressTwo.setContent(branchNoticeList.get(count + 1).getClassName());
                        data.add(penAddressTwo);
                    }
                    if (branchNoticeList.get(count + 1).getType() == 0) {
                        PdfMessageListVo computerTimeTwo = new PdfMessageListVo();
                        computerTimeTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[10]);
                        computerTimeTwo.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count + 1).getStartTime()));
                        data.add(computerTimeTwo);
                        PdfMessageListVo penAddressTwo = new PdfMessageListVo();
                        penAddressTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[11]);
                        penAddressTwo.setContent(branchNoticeList.get(count + 1).getClassName());
                        data.add(penAddressTwo);
                    }

                    PdfMessageListVo mainExamNameThree = new PdfMessageListVo();
                    mainExamNameThree.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[0]);
                    mainExamNameThree.setContent(exam.getName());
                    data.add(mainExamNameThree);
                    PdfMessageListVo stuNumberThree = new PdfMessageListVo();
                    stuNumberThree.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[1]);
                    stuNumberThree.setContent(branchNoticeList.get(count + 2).getXh());
                    data.add(stuNumberThree);
                    PdfMessageListVo nameThree = new PdfMessageListVo();
                    nameThree.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[2]);
                    nameThree.setContent(branchNoticeList.get(count + 2).getXm());
                    data.add(nameThree);
                    PdfMessageListVo examLanguageThree = new PdfMessageListVo();
                    examLanguageThree.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[3]);
                    examLanguageThree.setContent(branchNoticeList.get(count + 2).getBskmmc());
                    data.add(examLanguageThree);
                    PdfMessageListVo collegeThree = new PdfMessageListVo();
                    collegeThree.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[4]);
                    collegeThree.setContent(branchNoticeList.get(count + 2).getYx());
                    data.add(collegeThree);
                    PdfMessageListVo majorThree = new PdfMessageListVo();
                    majorThree.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[5]);
                    majorThree.setContent(branchNoticeList.get(count + 2).getZy());
                    data.add(majorThree);
                    PdfMessageListVo examNumberThree = new PdfMessageListVo();
                    examNumberThree.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[6]);
                    examNumberThree.setContent(branchNoticeList.get(count + 2).getBszkzh());
                    data.add(examNumberThree);
                    //中间不设置photo
                    if (branchNoticeList.get(count + 2).getType() == 1) {
                        PdfMessageListVo penTimeThree = new PdfMessageListVo();
                        penTimeThree.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[8]);
                        penTimeThree.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count + 2).getStartTime()));
                        data.add(penTimeThree);
                        PdfMessageListVo penAddressThree = new PdfMessageListVo();
                        penAddressThree.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[9]);
                        penAddressThree.setContent(branchNoticeList.get(count + 2).getClassName());
                        data.add(penAddressThree);
                    }
                    if (branchNoticeList.get(count + 2).getType() == 0) {
                        PdfMessageListVo computerTimeThree = new PdfMessageListVo();
                        computerTimeThree.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[10]);
                        computerTimeThree.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(count + 2).getStartTime()));
                        data.add(computerTimeThree);
                        PdfMessageListVo penAddressThree = new PdfMessageListVo();
                        penAddressThree.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_THREE[11]);
                        penAddressThree.setContent(branchNoticeList.get(count + 2).getClassName());
                        data.add(penAddressThree);
                    }
                    datas.add(data);
                }
                int lastMessage = branchNoticeList.size();
                if (lastMessage % 3 == 2) {
                    List<PdfMessageListVo> data = new ArrayList<>();
                    PdfMessageListVo mainExamNameOne = new PdfMessageListVo();
                    mainExamNameOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[0]);
                    mainExamNameOne.setContent(exam.getName());
                    data.add(mainExamNameOne);
                    PdfMessageListVo stuNumberOne = new PdfMessageListVo();
                    stuNumberOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[1]);
                    stuNumberOne.setContent(branchNoticeList.get(lastMessage - 2).getXh());
                    data.add(stuNumberOne);
                    PdfMessageListVo nameOne = new PdfMessageListVo();
                    nameOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[2]);
                    nameOne.setContent(branchNoticeList.get(lastMessage - 2).getXm());
                    data.add(nameOne);
                    PdfMessageListVo examLanguageOne = new PdfMessageListVo();
                    examLanguageOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[3]);
                    examLanguageOne.setContent(branchNoticeList.get(lastMessage - 2).getBskmmc());
                    data.add(examLanguageOne);
                    PdfMessageListVo collegeOne = new PdfMessageListVo();
                    collegeOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[4]);
                    collegeOne.setContent(branchNoticeList.get(lastMessage - 2).getYx());
                    data.add(collegeOne);
                    PdfMessageListVo majorOne = new PdfMessageListVo();
                    majorOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[5]);
                    majorOne.setContent(branchNoticeList.get(lastMessage - 2).getZy());
                    data.add(majorOne);
                    PdfMessageListVo examNumberOne = new PdfMessageListVo();
                    examNumberOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[6]);
                    examNumberOne.setContent(branchNoticeList.get(lastMessage - 2).getBszkzh());
                    data.add(examNumberOne);
                    //中间不设置photo
                    if (branchNoticeList.get(lastMessage - 2).getType() == 1) {
                        PdfMessageListVo penTimeOne = new PdfMessageListVo();
                        penTimeOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[8]);
                        penTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 2).getStartTime()));
                        data.add(penTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[9]);
                        penAddressOne.setContent(branchNoticeList.get(lastMessage - 2).getClassName());
                        data.add(penAddressOne);
                    }
                    if (branchNoticeList.get(lastMessage - 2).getType() == 0) {
                        PdfMessageListVo computerTimeOne = new PdfMessageListVo();
                        computerTimeOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[10]);
                        computerTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 2).getStartTime()));
                        data.add(computerTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[11]);
                        penAddressOne.setContent(branchNoticeList.get(lastMessage - 2).getClassName());
                        data.add(penAddressOne);
                    }

                    PdfMessageListVo mainExamNameTwo = new PdfMessageListVo();
                    mainExamNameTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[0]);
                    mainExamNameTwo.setContent(exam.getName());
                    data.add(mainExamNameTwo);
                    PdfMessageListVo stuNumberTwo = new PdfMessageListVo();
                    stuNumberTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[1]);
                    stuNumberTwo.setContent(branchNoticeList.get(lastMessage - 1).getXh());
                    data.add(stuNumberTwo);
                    PdfMessageListVo nameTwo = new PdfMessageListVo();
                    nameTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[2]);
                    nameTwo.setContent(branchNoticeList.get(lastMessage - 1).getXm());
                    data.add(nameTwo);
                    PdfMessageListVo examLanguageTwo = new PdfMessageListVo();
                    examLanguageTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[3]);
                    examLanguageTwo.setContent(branchNoticeList.get(lastMessage - 1).getBskmmc());
                    data.add(examLanguageTwo);
                    PdfMessageListVo collegeTwo = new PdfMessageListVo();
                    collegeTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[4]);
                    collegeTwo.setContent(branchNoticeList.get(lastMessage - 1).getYx());
                    data.add(collegeTwo);
                    PdfMessageListVo majorTwo = new PdfMessageListVo();
                    majorTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[5]);
                    majorTwo.setContent(branchNoticeList.get(lastMessage - 1).getZy());
                    data.add(majorTwo);
                    PdfMessageListVo examNumberTwo = new PdfMessageListVo();
                    examNumberTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[6]);
                    examNumberTwo.setContent(branchNoticeList.get(lastMessage - 1).getBszkzh());
                    data.add(examNumberTwo);
                    //中间不设置photo
                    if (branchNoticeList.get(lastMessage - 1).getType() == 1) {
                        PdfMessageListVo penTimeTwo = new PdfMessageListVo();
                        penTimeTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[8]);
                        penTimeTwo.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 1).getStartTime()));
                        data.add(penTimeTwo);
                        PdfMessageListVo penAddressTwo = new PdfMessageListVo();
                        penAddressTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[9]);
                        penAddressTwo.setContent(branchNoticeList.get(lastMessage - 1).getClassName());
                        data.add(penAddressTwo);
                    }
                    if (branchNoticeList.get(lastMessage - 1).getType() == 0) {
                        PdfMessageListVo computerTimeTwo = new PdfMessageListVo();
                        computerTimeTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[10]);
                        computerTimeTwo.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 1).getStartTime()));
                        data.add(computerTimeTwo);
                        PdfMessageListVo penAddressTwo = new PdfMessageListVo();
                        penAddressTwo.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_TWO[11]);
                        penAddressTwo.setContent(branchNoticeList.get(lastMessage - 1).getClassName());
                        data.add(penAddressTwo);
                    }
                    datas.add(data);
                }
                if (lastMessage % 3 == 1) {

                    List<PdfMessageListVo> data = Lists.newArrayList();
                    PdfMessageListVo mainExamNameOne = new PdfMessageListVo();
                    mainExamNameOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[0]);
                    mainExamNameOne.setContent(exam.getName());
                    data.add(mainExamNameOne);
                    PdfMessageListVo stuNumberOne = new PdfMessageListVo();
                    stuNumberOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[1]);
                    stuNumberOne.setContent(branchNoticeList.get(lastMessage - 1).getXh());
                    data.add(stuNumberOne);
                    PdfMessageListVo nameOne = new PdfMessageListVo();
                    nameOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[2]);
                    nameOne.setContent(branchNoticeList.get(lastMessage - 1).getXm());
                    data.add(nameOne);
                    PdfMessageListVo examLanguageOne = new PdfMessageListVo();
                    examLanguageOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[3]);
                    examLanguageOne.setContent(branchNoticeList.get(lastMessage - 1).getBskmmc());
                    data.add(examLanguageOne);
                    PdfMessageListVo collegeOne = new PdfMessageListVo();
                    collegeOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[4]);
                    collegeOne.setContent(branchNoticeList.get(lastMessage - 1).getYx());
                    data.add(collegeOne);
                    PdfMessageListVo majorOne = new PdfMessageListVo();
                    majorOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[5]);
                    majorOne.setContent(branchNoticeList.get(lastMessage - 1).getZy());
                    data.add(majorOne);
                    PdfMessageListVo examNumberOne = new PdfMessageListVo();
                    examNumberOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[6]);
                    examNumberOne.setContent(branchNoticeList.get(lastMessage - 1).getBszkzh());
                    data.add(examNumberOne);
                    //中间不设置photo
                    if (branchNoticeList.get(lastMessage - 1).getType() == 1) {
                        PdfMessageListVo penTimeOne = new PdfMessageListVo();
                        penTimeOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[8]);
                        penTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 1).getStartTime()));
                        data.add(penTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[9]);
                        penAddressOne.setContent(branchNoticeList.get(lastMessage - 1).getClassName());
                        data.add(penAddressOne);
                    }
                    if (branchNoticeList.get(lastMessage - 1).getType() == 0) {
                        PdfMessageListVo computerTimeOne = new PdfMessageListVo();
                        computerTimeOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[10]);
                        computerTimeOne.setContent(DateTimeUtil.getDateTimeStringOrNull(branchNoticeList.get(lastMessage - 1).getStartTime()));
                        data.add(computerTimeOne);
                        PdfMessageListVo penAddressOne = new PdfMessageListVo();
                        penAddressOne.setName(ExamManagementConstant.CARD_FRR_EXAM_PDF_FIELD_ONE[11]);
                        penAddressOne.setContent(branchNoticeList.get(lastMessage - 1).getClassName());
                        data.add(penAddressOne);
                    }
                    datas.add(data);
                }
                String pdfFileCardForExamOnePath = String.valueOf(ResourceUtils.getURL("target/classes/pdfTemplates/cardforexamone.pdf"));
                String pdfFileCardForExamTwoPath = String.valueOf(ResourceUtils.getURL("target/classes/pdfTemplates/cardforexamtwo.pdf"));
                String pdfFileCardForExamThreePath = String.valueOf(ResourceUtils.getURL("target/classes/pdfTemplates/cardforexamthree.pdf"));

                PDFForExamUtil.createPdf(datas, pdfFileCardForExamOnePath, pdfFileCardForExamTwoPath, pdfFileCardForExamThreePath, filePath, department + ".pdf");
            }
            ToZip.packageZip(ExamManagementConstant.CARD_FOR_EXAM_NAME, fileNameMap, filePath);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(ExamManagementReasonOfFailure.SAVE_FAILURE);
        }
        String zipPath = downloadFileWebPath + cardForExamFilePath + fileName + '/' + ExamManagementConstant.CARD_FOR_EXAM_NAME;
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "zipPath", zipPath);

    }


    /**
     * author lwn
     * description  考务签到列表信息
     * date 2019/4/15 15:32
     * param
     * return void
     */
    public void kaoWuSignInInfoRequestProcess() {
        String teId = dataCenterService.getData("teId");//场次id
        String examPointId = dataCenterService.getData("examPointId");//考点
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");
        List<JobSignInInfoVo> displayList = Lists.newArrayList();//最后返回前台的展示对象
        //未归档签到查询
        List<JobSignInInfoDto> noArchiveList = tssTempPostPersonDao.seachSignExamInfo(null, teId, examPointId, null);
        //查询到该考试该场次对应岗位的所有人的个人信息
        //查询该考试下所有场次的签到信息
        PageHelper.startPage(pageNum, pageSize);
        seachJobSignInInfo(displayList, noArchiveList, null, null);
        //身份证解密
        displayList.forEach(item -> item.setPid(AESUtil.AESDecode(encodeRole, item.getPid())));
        PageInfo<JobSignInInfoVo> pageResult = new PageInfo<>(displayList);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "pageResult", pageResult);


    }

    private void listToMap(List<Map<String, Object>> list, Map target) {
        for (Map<String, Object> integerStringMap : list) {
            Integer code = null;
            String description = null;
            for (Map.Entry<String, Object> entry : integerStringMap.entrySet()) {
                if ("code".equals(entry.getKey())) {
                    //TODO 2019/3/23 是否可用valueof
                    code = NumberUtils.toInt(String.valueOf(entry.getValue()));
                }
                if ("description".equals(entry.getKey())) {
                    description = (String) entry.getValue();
                }
            }
            target.put(code, description);
        }
    }
}
