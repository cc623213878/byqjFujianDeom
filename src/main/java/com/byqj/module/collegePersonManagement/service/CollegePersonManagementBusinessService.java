package com.byqj.module.collegePersonManagement.service;

import com.byqj.dao.SysAdminDetailDao;
import com.byqj.dao.SysDepartmentDao;
import com.byqj.dao.TssDictionaryDao;
import com.byqj.dao.TssPersonLibraryDao;
import com.byqj.dto.TssPersonLibraryDto;
import com.byqj.entity.SysAdminDetail;
import com.byqj.entity.SysDepartment;
import com.byqj.entity.TssPersonLibrary;
import com.byqj.module.collegePersonManagement.constant.CollegePersonManagementConstant;
import com.byqj.module.collegePersonManagement.constant.LogConstant;
import com.byqj.module.collegePersonManagement.enums.CollegePersonManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.AESUtil;
import com.byqj.utils.ExceptionUtil;
import com.byqj.utils.IdUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@SuppressWarnings("ALL")
@Service
public class CollegePersonManagementBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private LogCenterService logCenterService;
    @Autowired
    private TssPersonLibraryDao tssPersonLibraryDao;
    @Autowired
    private SysDepartmentDao sysDepartmentDao;
    @Autowired
    private SysAdminDetailDao sysAdminDetailDao;
    @Autowired
    private TssDictionaryDao tssDictionaryDao;
    @Value("${encode-role}")
    private String encodeRole;

    /**
     * 查询人员
     * Map是为了解决一表不能对另外一个表进行多次查询
     **/
    public void searchPersonRequestProcess() {
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String workCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("workCode");
        String phone = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("phone");
        String collegeIdString = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("collegeId");
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");
        String sysUserId = dataCenterService.getCurrentUserId();

        //查询当前管理员的子部门的id
        SysAdminDetail sysAdminDetail = sysAdminDetailDao.selectById(sysUserId);
        List<Long> currentChildrenCollegeId = Lists.newArrayList();
        if (sysAdminDetail.getDeptId() != null) {
            SysDepartment currentSysDepartment = sysDepartmentDao.selectById(sysAdminDetail.getDeptId());
            currentChildrenCollegeId = sysDepartmentDao.getChildrenCollegeId(currentSysDepartment.getLevel(), currentSysDepartment.getId());
            currentChildrenCollegeId.add(sysAdminDetail.getDeptId());
        }
        //查询子部门的id
        List<Long> childrenCollegeId = Lists.newArrayList();
        if (!StringUtils.isBlank(collegeIdString)) {
            Long collegeId = Longs.tryParse(collegeIdString);
            if (!currentChildrenCollegeId.contains(collegeId)) {
                ExceptionUtil.setFailureMsgAndThrow(CollegePersonManagementReasonOfFailure.COLLEGE_ID_ILLEGAL);
            }
            SysDepartment sysDepartment = sysDepartmentDao.selectById(collegeId);
            childrenCollegeId = sysDepartmentDao.getChildrenCollegeId(sysDepartment.getLevel(), sysDepartment.getId());
            childrenCollegeId.add(collegeId);
        } else {
            childrenCollegeId = currentChildrenCollegeId;
        }

        Map<Long, String> bankMap = new HashMap<>();
        Map<Long, String> personKindMap = new HashMap<>();
        Map<Long, String> personTypeMap = new HashMap<>();
        Map<Long, String> moneyMap = new HashMap<>();

        //装载返回来的MapList
        List<Map<String, Object>> bankMapList = tssDictionaryDao.getDescriptionAndId(CollegePersonManagementConstant.bank);
        List<Map<String, Object>> personKindMapList = tssDictionaryDao.getDescriptionAndId(CollegePersonManagementConstant.personKind);
        List<Map<String, Object>> personTypeMapList = tssDictionaryDao.getDescriptionAndId(CollegePersonManagementConstant.personType);
        List<Map<String, Object>> moneyMapList = tssDictionaryDao.getDescriptionAndId(CollegePersonManagementConstant.money);

        //转换map类型<String, Object> to <Long, String>
        listToMap(bankMapList, bankMap);
        listToMap(personKindMapList, personKindMap);
        listToMap(personTypeMapList, personTypeMap);
        listToMap(moneyMapList, moneyMap);

        PageHelper.startPage(pageNum, pageSize);
        List<TssPersonLibraryDto> PersonList = tssPersonLibraryDao.searchPerson(name, workCode, phone, childrenCollegeId);

        //遍历装载上字典表中查询出来的数据
        for (TssPersonLibraryDto Person : PersonList) {
            Person.setBankDescription(bankMap.get(Person.getBank()));
            Person.setPersonKindDescription(personKindMap.get(Person.getCategory()));
            Person.setPersonTypeDescription(personTypeMap.get(Person.getType()));
            Person.setMoneyDescription(moneyMap.get(Person.getMoneyType()));
            //身份证解密
            String pid = Person.getPid();
            Person.setPid(AESUtil.AESDecode(encodeRole, pid));
        }

        PageInfo<TssPersonLibraryDto> pageResult = new PageInfo<>(PersonList);
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "PersonList", pageResult);

    }


    private void listToMap(List<Map<String, Object>> list, Map target) {
        for (Map<String, Object> integerStringMap : list) {
            Long id = null;
            String description = null;
            for (Map.Entry<String, Object> entry : integerStringMap.entrySet()) {
                if ("id".equals(entry.getKey())) {
                    //TODO 2019/3/23 是否可用valueof
                    id = Longs.tryParse(String.valueOf(entry.getValue()));
                }
                if ("description".equals(entry.getKey())) {
                    description = (String) entry.getValue();
                }
            }
            target.put(id, description);
        }
    }

    /**
     * 删除人员
     */
    @Transactional
    public void batchDeletePersonRequestProcess() {

        //TODO 2019/3/21 是否删除其他关联表
        List<String> personIdList = dataCenterService.getData("personIdList");
        //装载不可删除和可以删除的List
        int result = tssPersonLibraryDao.deleteBatchIds(personIdList);
        if (result < 1) {
            ExceptionUtil.setFailureMsgAndThrow(CollegePersonManagementReasonOfFailure.DELETE_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        List<String> tssPersonLibraries = tssPersonLibraryDao.searchPersonByIdList(personIdList);
        String operationContent = String.format(LogConstant.DELETE_PERSON, tssPersonLibraries);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    /**
     * 添加人员
     */
    @Transactional
    public void addUserRequestProcess() {
        TssPersonLibrary tssPersonLibrary = dataCenterService.getData("tssPersonLibrary");
        //身份证加密
        String pid = tssPersonLibrary.getPid();
        tssPersonLibrary.setPid(AESUtil.AESEncode(encodeRole, pid));
        //获取id
        tssPersonLibrary.setId(IdUtils.getUUID());
        int result = tssPersonLibraryDao.insert(tssPersonLibrary);
        if (result == 0) {
            ExceptionUtil.setFailureMsgAndThrow(CollegePersonManagementReasonOfFailure.INSERT_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.ADD_PERSON, tssPersonLibrary.getName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }

    /**
     * 修改人员
     */
    @Transactional
    public void modifyPersonnelRequestProcess() {
        TssPersonLibrary tssPersonLibrary = dataCenterService.getData("tssPersonLibrary");
        //身份证加密
        String pid = tssPersonLibrary.getPid();
        tssPersonLibrary.setPid(AESUtil.AESEncode(encodeRole, pid));
        int result = tssPersonLibraryDao.updateById(tssPersonLibrary);
        if (result == 0) {
            ExceptionUtil.setFailureMsgAndThrow(CollegePersonManagementReasonOfFailure.UPDATE_ERROP);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        // 记录日志
        String operationContent = String.format(LogConstant.MODIFY_PERSON, tssPersonLibrary.getName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

}
