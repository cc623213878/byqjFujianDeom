package com.byqj.module.departmentManagement.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.SysAdminDetailDao;
import com.byqj.dao.SysDepartmentDao;
import com.byqj.dao.TssPersonLibraryDao;
import com.byqj.dto.DeptLevelDto;
import com.byqj.entity.SysAdminDetail;
import com.byqj.entity.SysDepartment;
import com.byqj.entity.TssPersonLibrary;
import com.byqj.module.departmentManagement.constant.DepartmentConstants;
import com.byqj.module.departmentManagement.constant.LogConstant;
import com.byqj.module.departmentManagement.enums.DepartmentManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.IDepartmentService;
import com.byqj.service.IPersonLibraryService;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.ExceptionUtil;
import com.byqj.utils.LevelUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class DepartmentManagementBusinessService {

    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    private LogCenterService logCenterService;
    @Autowired
    SysDepartmentDao sysDepartmentDao;
    @Autowired
    SysAdminDetailDao sysAdminDetailDao;
    @Autowired
    TssPersonLibraryDao tssPersonLibraryDao;
    @Autowired
    IDepartmentService departmentService;
    @Autowired
    IPersonLibraryService personLibraryService;


    public void getDepartmentListFromOrganizationRequestProcess() {
        List<DeptLevelDto> allDeptList = sysDepartmentDao.getAllDept();
        List<DeptLevelDto> transformList = deptListToTree(allDeptList);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "DeptList", transformList);
    }


    /*
     * @Author lwn
     * @Description  数据字典模块获取部门，只显示type为外来的部门
     * @Date 20:33 2019/3/20
     * @Param []
     * @return void
     **/
    public void getDepartmentListFromDictRequestProcess() {
        List<DeptLevelDto> allDeptList = sysDepartmentDao.getDeptDict(DepartmentConstants.TYPE_OUT);
        List<DeptLevelDto> transformList = deptListToTree(allDeptList);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "DeptList", transformList);
    }

    public void getDepartmentListFromCollegeRequestProcess() {
        String currentUserId = dataCenterService.getCurrentUserId();
        //获取当前用户所在部门的部门id和level
        DeptLevelDto rootDepartment = sysDepartmentDao.getDepartmentByUserId(currentUserId);
        if (rootDepartment == null) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.CRURRENT_USER_NO_DEPARTMENT);
        }
        String newLevel = rootDepartment.getLevel() + LevelUtil.SEPARATOR + rootDepartment.getId();
        //查询该部门下的子部门
        List<DeptLevelDto> childrenDeptList = sysDepartmentDao.getChildrenDept(newLevel);
        childrenDeptList.add(rootDepartment);
        //构建rootList
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();
        rootList.add(rootDepartment);
        //插入map
        for (DeptLevelDto deptLevelDto : childrenDeptList) {
            levelDeptMap.put(deptLevelDto.getLevel(), deptLevelDto);
        }
        //递归生成树形结构
        transformDeptTree(rootList, rootDepartment.getLevel(), levelDeptMap);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "DeptList", rootList);


    }


    private List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList) {
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        // level -> [dept1, dept2, ...] Map<String, List<Object>>
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();

        for (DeptLevelDto dto : deptLevelList) {
            levelDeptMap.put(dto.getLevel(), dto);
            // 将层级为0，即根部门加入List
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        // 按seq从小到大排序，JDK1.8支持
        rootList.sort(Comparator.comparingInt(SysDepartment::getSeq));
        // 递归生成树
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);

        return rootList;
    }

    // level:0, 0, all 0->0.1,0.2
    // level:0.1

    // level:0.2
    private void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDeptMap) {
        // 遍历该层的每个元素
        for (DeptLevelDto deptLevelDto : deptLevelList) {
            // 下一层级
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 按seq从小到大排序
                tempDeptList.sort(Comparator.comparingInt(SysDepartment::getSeq));
                // 设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                // 进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }

    @Transactional
    public void addDepartmentRequestProcess() {
        //获取数据
        String name = dataCenterService.getData("name");
        Long parentId = dataCenterService.getData("parentId");
        int seq = dataCenterService.getData("seq");
        String remark = dataCenterService.getData("remark");
        int type = dataCenterService.getData("type");
        //封装数据
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setName(name);
        sysDepartment.setParentId(parentId);
        sysDepartment.setSeq(seq);
        sysDepartment.setRemark(remark);
        sysDepartment.setType(type);
        sysDepartment.setCreateTime(new Date());
        //获取parentId对应level构造level
        String newLevel = null;
        if (parentId != 0) {
            SysDepartment parentDepartment = departmentService.getOne(new LambdaQueryWrapper<SysDepartment>().eq(SysDepartment::getId, parentId));
            newLevel = LevelUtil.calculateLevel(parentDepartment.getLevel(), parentId);
        } else {
            newLevel = "0";
        }
        sysDepartment.setLevel(newLevel);
        //插入数据库
        boolean insertResult = departmentService.save(sysDepartment);
        if (!insertResult) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.INSERT_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.ADD_AUTHORITY_GROUP_TEMPLATE, name);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }

    @Transactional
    public void updateDepartmentRequestProcess() {
        Long id = dataCenterService.getData("id");
        String name = dataCenterService.getData("name");
        Long parentId = dataCenterService.getData("parentId");
        int seq = dataCenterService.getData("seq");
        String remark = dataCenterService.getData("remark");
        int type = dataCenterService.getData("type");
        //封装数据
        SysDepartment newDept = new SysDepartment();
        newDept.setId(id);
        newDept.setName(name);
        newDept.setParentId(parentId);
        newDept.setSeq(seq);
        newDept.setRemark(remark);
        newDept.setType(type);
        newDept.setUpdateTime(new Date());
        //查出库里的旧信息
        SysDepartment before = departmentService.getOne(new LambdaQueryWrapper<SysDepartment>().eq(SysDepartment::getId, id));
        if (before == null) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.OLD_DEPT_NOT_EXIT);
        }
        //如果有修改父部门
        if (before.getParentId() != parentId) {
            //获取parentId对应level构造level
            String newLevel = null;
            if (parentId != 0) {
                SysDepartment parentDepartment = departmentService.getOne(new LambdaQueryWrapper<SysDepartment>().eq(SysDepartment::getId, parentId));
                newLevel = LevelUtil.calculateLevel(parentDepartment.getLevel(), parentId);
            } else {
                newLevel = "0";
            }
            newDept.setLevel(newLevel);
            //修改部门子部门的level
            String oldLevel = LevelUtil.calculateLevel(before.getLevel(), before.getId());
            List<DeptLevelDto> oldDeptList = sysDepartmentDao.getChildrenDept(oldLevel);
            List<SysDepartment> toBeUpdateList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(oldDeptList)) {
                for (SysDepartment dept : oldDeptList) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevel) == 0) { //查找相同字符串的第一个字符串的位置
                        level = newLevel + LevelUtil.SEPARATOR + id + level.substring(oldLevel.length());
                        dept.setLevel(level);
                    }
                    toBeUpdateList.add(dept);
                }
                boolean updateResult = departmentService.updateBatchById(toBeUpdateList);
                if (!updateResult) {
                    ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.UPDATE_ERROR);
                }
            }
        }
        //更新数据库
        boolean updateResult = departmentService.updateById(newDept);
        if (!updateResult) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.UPDATE_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.MODIFY_AUTHORITY_GROUP_TEMPLATE, name);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }


    @Transactional
    public void deleteDepartmentRequestProcess() {
        Long id = dataCenterService.getData("id");
        int deptCount = departmentService.count(new LambdaQueryWrapper<SysDepartment>().eq(SysDepartment::getParentId, id));
        if (deptCount > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.EXIST_SUB_DEPT);

        }
        int adminDetailCount = sysAdminDetailDao.selectCount(new LambdaQueryWrapper<SysAdminDetail>().eq(SysAdminDetail::getDeptId, id));
        int personLibraryCount = personLibraryService.count(new LambdaQueryWrapper<TssPersonLibrary>().eq(TssPersonLibrary::getCollegeId, id));
        if (adminDetailCount > 0 || personLibraryCount > 0) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.DEPT_EXIST_USER);
            // 记录日志
            String operationContent = String.format(LogConstant.MEMBERS_IN_THE_GROUP_ARE_NOT_ALLOWED_TO_DELETE_DESCRIPTION);
            logCenterService.setContent(operationContent);
            logCenterService.setResultIsFalse();
        }
        String departmentName = sysDepartmentDao.getDepartmentNameById(id);
        int deleteResult = departmentService.getBaseMapper().deleteById(id);
        if (deleteResult < 1) {
            ExceptionUtil.setFailureMsgAndThrow(DepartmentManagementReasonOfFailure.DB_OPER_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.DELETE_AUTHORITY_GROUP_TEMPLATE, departmentName);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }
}
