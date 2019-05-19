package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.TssExamPlaceDao;
import com.byqj.entity.TssClassInfo;
import com.byqj.entity.TssExamPlace;
import com.byqj.service.IClassInfoService;
import com.byqj.service.IExamPlaceService;
import com.byqj.utils.LevelUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by willim on 2019/3/21.
 */
@Service(value = "examPlaceService")
public class ExamPlaceServiceImpl extends ServiceImpl<TssExamPlaceDao, TssExamPlace>
        implements IExamPlaceService {
    @Autowired
    private IClassInfoService classInfoService;

    public boolean checkClassFree(TssExamPlace examPlaceOrClass) {
        String exPLevel = examPlaceOrClass.getLevel();
        String classId = examPlaceOrClass.getTciId();
        Integer studentCount = examPlaceOrClass.getStudentCount();

        if (StringUtils.isBlank(classId)) {
            return true;
        }

        // "0.mainExamId.examId"
        Matcher m = Pattern.compile("(^0\\..*\\..*?)\\..*").matcher(exPLevel + LevelUtil.SEPARATOR);
        if (!m.find()) {
            return false;
        }

        String exClassLevelPrefix = m.group(1);
        // 检查教室是否已被分配
        int result = this.countByLevelAndClassId(exClassLevelPrefix, classId);
        if (result > 0) {
            // 设置主考室时，不能将已设置的教室设为主考室，不管容量是否足够
            if (examPlaceOrClass.getParentId().equals(examPlaceOrClass.getTeId())) {
                return false;
            }
            // 检查教室容量
            TssClassInfo classInfo = classInfoService.getById(classId);
            // 获取安排了教室的考点、考场
            List<TssExamPlace> examPlaces = list(new LambdaQueryWrapper<TssExamPlace>()
                    .likeRight(TssExamPlace::getLevel, exClassLevelPrefix)
                    .eq(TssExamPlace::getTciId, classId));
            int sum = 0;
            for (TssExamPlace examPlace : examPlaces) {
                if (examPlace.getStudentCount() == -1) { // 不允许主考室被设置为考场
                    return false;
                } else {
                    sum += examPlace.getStudentCount();
                }
//                if (examPlace.getStudentCount() != -1) { // 允许主考室被设置为考场
//                    sum += examPlace.getStudentCount();
//                }
            }
            return sum + studentCount <= classInfo.getCapacity();
        }
        return true;
    }

    public List<TssExamPlace> selectByTeId(String teId) {
        return list(new LambdaQueryWrapper<TssExamPlace>()
                .eq(TssExamPlace::getTeId, teId).orderByAsc(TssExamPlace::getNameOrSeq));
    }

    public List<TssExamPlace> selectByTeIdList(List<String> ids) {
        return list(new LambdaQueryWrapper<TssExamPlace>()
                .in(TssExamPlace::getTeId, ids).orderByAsc(TssExamPlace::getNameOrSeq));
    }

    public List<TssExamPlace> getSubExamPlace(List<String> ids) {
        return list(new LambdaQueryWrapper<TssExamPlace>()
                .in(TssExamPlace::getParentId, ids));
    }

    public List<TssExamPlace> getSubExamPlaceOrderBy(String id) {
        return list(new LambdaQueryWrapper<TssExamPlace>()
                .eq(TssExamPlace::getParentId, id).orderByAsc(TssExamPlace::getNameOrSeq));
    }

    public List<TssExamPlace> selectMainPlaceByTeId(String exId) {
        return list(new LambdaQueryWrapper<TssExamPlace>()
                .eq(TssExamPlace::getTeId, exId)
                .eq(TssExamPlace::getStudentCount, -1));
    }

    public int getStuCountByTeIdAndTciId(String examId, String classId) {

        return getBaseMapper().getStuCountByTeIdAndTciId(examId, classId);
    }

    public int countByLevelAndClassId(String exClassLevelPrefix, String classId) {

        return count(new LambdaQueryWrapper<TssExamPlace>()
                .likeRight(TssExamPlace::getLevel, exClassLevelPrefix)
                .eq(TssExamPlace::getTciId, classId));
    }

    public synchronized boolean saveAndSetPlaceNum(TssExamPlace examClass) {

        // get exam class number under a exam
        int examClassNum = getBaseMapper().getMaxSeq(examClass.getTeId());
        if (examClassNum <= -1) {
            examClassNum = 0;
        }
        examClass.setNameOrSeq(examClassNum + 1);

        return save(examClass);

    }

    public List<String> getOrderedClassId(TssExamPlace examPlace) {
        Multimap<String, TssExamPlace> classIdExamPlaceMap = ArrayListMultimap.create();
        // 过滤后的已安排教室id
        List<String> filterClassIds = new ArrayList<String>();

        // 获取考场
        List<TssExamPlace> examClasses =
                list(new LambdaQueryWrapper<TssExamPlace>()
                        .eq(TssExamPlace::getParentId, examPlace.getId()));

        classIdExamPlaceMap.put(examPlace.getTciId(), examPlace);
        for (TssExamPlace examClass : examClasses) {
            classIdExamPlaceMap.put(examClass.getTciId(), examClass);
        }
        // 获取不重复的，已安排的教室id
        List<String> classIds = new ArrayList<String>(classIdExamPlaceMap.keySet());

        // 获取已安排的教室信息
        Collection<TssClassInfo> classInfoList = classInfoService.listByIds(classIds);
        // 判断容量
        int sum;
        for (TssClassInfo classInfo : classInfoList) {
            sum = 0;
            // 获取安排了该教室的考点或考场
            List<TssExamPlace> examClassListTemp = (List<TssExamPlace>) classIdExamPlaceMap.get(classInfo.getId());
            for (TssExamPlace exClassTemp : examClassListTemp) {
//                sum += exClassTemp.getStudentCount();
//                if (sum == -1) break;
                if (exClassTemp.getStudentCount() != -1) { // 允许主考室被安排为考场
                    sum += exClassTemp.getStudentCount();
                }
            }
            if (sum >= classInfo.getCapacity() || sum < 0) {
                filterClassIds.add(classInfo.getId());
            }
        }

        return filterClassIds;
    }

    public List<TssExamPlace> likeRightLevel(String level) {
        return list(new LambdaQueryWrapper<TssExamPlace>()
                .likeRight(TssExamPlace::getLevel, level));
    }

    public synchronized boolean delAndUpdateSeqById(String exCLassId) {
        TssExamPlace examClass = getById(exCLassId);
        boolean result = removeById(exCLassId);
        if (result) {
            return getBaseMapper()
                    .updateSeqById(examClass.getTeId(), examClass.getNameOrSeq());
        }
        return false;
    }

    @Override
    public int countByClassIdAndExamPlaceId(String classId, List<String> examIdList) {
        return count(new LambdaQueryWrapper<TssExamPlace>()
                .eq(TssExamPlace::getTciId, classId)
                .in(TssExamPlace::getTeId, examIdList));
    }


}
