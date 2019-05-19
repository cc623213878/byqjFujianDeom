package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.TssExamPlace;

import java.util.List;

/**
 * Created by willim on 2019/3/21.
 */
public interface IExamPlaceService extends IService<TssExamPlace> {

    boolean checkClassFree(TssExamPlace examPlaceOrClass);

    List<TssExamPlace> selectByTeId(String teId);

    List<TssExamPlace> selectByTeIdList(List<String> ids);

    List<TssExamPlace> getSubExamPlace(List<String> ids);

    List<TssExamPlace> getSubExamPlaceOrderBy(String id);

    List<TssExamPlace> selectMainPlaceByTeId(String exId);

    int getStuCountByTeIdAndTciId(String examId, String classId);

    int countByLevelAndClassId(String exClassLevelPrefix, String classId);

    boolean saveAndSetPlaceNum(TssExamPlace examClass);

    List<String> getOrderedClassId(TssExamPlace examPlace);

    List<TssExamPlace> likeRightLevel(String level);

    boolean delAndUpdateSeqById(String exCLassId);

    int countByClassIdAndExamPlaceId(String classId, List<String> examIdList);

    //List<TssExamPlace> getExamPlace(List<String> ids, String examPlaceId, String examPoint);

}
