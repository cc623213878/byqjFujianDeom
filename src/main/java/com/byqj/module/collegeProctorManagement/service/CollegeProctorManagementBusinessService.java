package com.byqj.module.collegeProctorManagement.service;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.SysAdminDetailDao;
import com.byqj.dao.SysDepartmentDao;
import com.byqj.dao.TssDictionaryDao;
import com.byqj.dao.TssExamDao;
import com.byqj.dao.TssPersonLibraryDao;
import com.byqj.dao.TssTempSubmitPersonDao;
import com.byqj.dto.SysAdminSelectDetailDto;
import com.byqj.dto.TssPersonLibraryDto;
import com.byqj.dto.TssTempPostPersonAddDto;
import com.byqj.entity.SysAdminDetail;
import com.byqj.entity.SysDepartment;
import com.byqj.entity.TssExam;
import com.byqj.entity.TssPersonLibrary;
import com.byqj.entity.TssTempSubmitPerson;
import com.byqj.module.collegeProctorManagement.constant.CollegeProctorManagementConstant;
import com.byqj.module.collegeProctorManagement.constant.LogConstant;
import com.byqj.module.collegeProctorManagement.enums.CollegeProctorManagementReasonOfFailure;
import com.byqj.module.gradeManagement.enums.GradeManagementReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.ITempSubmitPersonService;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.AESUtil;
import com.byqj.utils.ExceptionUtil;
import com.byqj.vo.TempSubmitPersonVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Longs;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CollegeProctorManagementBusinessService {

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
    @Autowired
    private ITempSubmitPersonService iTempSubmitPersonService;
    @Autowired
    private TssTempSubmitPersonDao tssTempSubmitPersonDao;
    @Autowired
    private TssExamDao tssExamDao;
    @Value("${file-path.submit-person-excel-file-save}")
    private String submitPersonExcelFileSave;
    @Value("${file-path.export-file-local-path}")
    private String exportFileLocalPath;
    @Value("${file-path.download-file-web-path}")
    private String downloadFileWebPath;
    @Value("${encode-role}")
    private String encodeRole;


    /**
     * 查询已经提交报名岗位的人员
     **/
    public void searchSubmitPersonByConditionRequestProcess() {
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String workCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("workCode");
        String phone = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("phone");
        int pageNum = dataCenterService.getData("pageNum");
        int pageSize = dataCenterService.getData("pageSize");
        String teId = dataCenterService.getData("teId");
        String sysUserId = dataCenterService.getCurrentUserId();

        //查询管理员的子部门的id
        SysAdminDetail sysAdminDetail = sysAdminDetailDao.selectById(sysUserId);
        List<Long> childrenCollegeId = Lists.newArrayList();
        if (sysAdminDetail.getDeptId() != null) {
            SysDepartment sysDepartment = sysDepartmentDao.selectById(sysAdminDetail.getDeptId());
            childrenCollegeId = sysDepartmentDao.getChildrenCollegeId(sysDepartment.getLevel(), sysDepartment.getId());
            childrenCollegeId.add(sysAdminDetail.getDeptId());
        }
        //查询人员类型和人员类别的字典表
        //获取数据字典内容
        Map<Long, String> bankMap = new HashMap<>();
        Map<Long, String> personKindMap = new HashMap<>(); //CATEGORY
        Map<Long, String> personTypeMap = new HashMap<>(); //TYPE
        Map<Long, String> moneyMap = new HashMap<>();
        getDictMap(bankMap, personKindMap, personTypeMap, moneyMap);
        PageHelper.startPage(pageNum, pageSize);
        //查询已报名的用户信息
        List<TssTempSubmitPerson> PersonList = tssTempSubmitPersonDao.seachSubmitPerson(name, workCode, phone, childrenCollegeId, teId);
        //遍历装载上字典表中查询出来的数据和解密身份证信息
        for (TssTempSubmitPerson Person : PersonList) {
            String bankStr = bankMap.get(Longs.tryParse(Person.getBank()));
            String personCategoryStr = personKindMap.get(Longs.tryParse(Person.getCategory()));
            String personTypeStr = personTypeMap.get(Longs.tryParse(Person.getType()));
            String moneyStr = moneyMap.get(Longs.tryParse(Person.getMoneyType()));
            String pid = Person.getPid();
            if (bankStr != null) {
                Person.setBank(bankStr);
            }
            if (personCategoryStr != null) {
                Person.setCategory(personCategoryStr);
            }
            if (personTypeStr != null) {
                Person.setType(personTypeStr);
            }
            if (moneyStr != null) {
                Person.setMoneyType(moneyStr);
            }
            Person.setPid(AESUtil.AESDecode(encodeRole, pid));
        }
        PageInfo<TssTempSubmitPerson> pageResult = new PageInfo<>(PersonList);
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "SubmitPersonList", pageResult);

    }

    /*
     * author lwn
     * date 2019/4/6 13:01
     * param
     * return void
     * 查询未提交岗位的当前部门的人
     */
    public void searchNoSubmitPersonByConditionRequestProcess() {
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        String workCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("workCode");
        String phone = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("phone");
        int pageNum = dataCenterService.getData("pageNum");
        int pageSize = dataCenterService.getData("pageSize");
        String teId = dataCenterService.getData("teId");
        String sysUserId = dataCenterService.getCurrentUserId();

        //查询管理员的子部门的id
        SysAdminDetail sysAdminDetail = sysAdminDetailDao.selectById(sysUserId);
        List<Long> childrenCollegeId = Lists.newArrayList();
        if (sysAdminDetail.getDeptId() != null) {
            SysDepartment sysDepartment = sysDepartmentDao.selectById(sysAdminDetail.getDeptId());
            childrenCollegeId = sysDepartmentDao.getChildrenCollegeId(sysDepartment.getLevel(), sysDepartment.getId());
            childrenCollegeId.add(sysAdminDetail.getDeptId());
        }
        //获取数据字典内容
        Map<Long, String> bankMap = new HashMap<>();
        Map<Long, String> personKindMap = new HashMap<>(); //CATEGORY
        Map<Long, String> personTypeMap = new HashMap<>();
        Map<Long, String> moneyMap = new HashMap<>();
        getDictMap(bankMap, personKindMap, personTypeMap, moneyMap);
        PageHelper.startPage(pageNum, pageSize);
        //查询未报名的用户信息
        List<TssPersonLibraryDto> PersonList = tssPersonLibraryDao.seachNoSubmitPerson(name, workCode, phone, childrenCollegeId, teId);
        //遍历装载上字典表中查询出来的数据和身份证解密
        for (TssPersonLibraryDto Person : PersonList) {
            Person.setBankDescription(bankMap.get(Person.getBank()));
            Person.setPersonKindDescription(personKindMap.get(Person.getCategory()));
            Person.setPersonTypeDescription(personTypeMap.get(Person.getType()));
            Person.setMoneyDescription(moneyMap.get(Person.getMoneyType()));
            String pid = Person.getPid();
            Person.setPid(AESUtil.AESDecode(encodeRole, pid));
        }

        PageInfo<TssPersonLibraryDto> pageResult = new PageInfo<>(PersonList);
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "NoSubmitPersonList", pageResult);

    }

    private void getDictMap(Map<Long, String> bankMap, Map<Long, String> personKindMap, Map<Long, String> personTypeMap, Map<Long, String> moneyMap) {
        List<Map<String, Object>> bankMapList = tssDictionaryDao.getDescriptionAndId(CollegeProctorManagementConstant.bank);
        List<Map<String, Object>> personKindMapList = tssDictionaryDao.getDescriptionAndId(CollegeProctorManagementConstant.personKind);
        List<Map<String, Object>> personTypeMapList = tssDictionaryDao.getDescriptionAndId(CollegeProctorManagementConstant.personType);
        List<Map<String, Object>> moneyMapList = tssDictionaryDao.getDescriptionAndId(CollegeProctorManagementConstant.money);

        //转换map类型<String, Object> to <Long, String>
        listToMap(bankMapList, bankMap);
        listToMap(personKindMapList, personKindMap);
        listToMap(personTypeMapList, personTypeMap);
        listToMap(moneyMapList, moneyMap);
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


    @Transactional
    public void addUserRequestProcess() {
        List<TssTempPostPersonAddDto> userList = dataCenterService.getData("userList");
        String teId = dataCenterService.getData("teId");
        iTempSubmitPersonService.remove(new LambdaQueryWrapper<TssTempSubmitPerson>().eq(TssTempSubmitPerson::getTeId, teId));
        if (userList.size() == 0) {
            ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
            ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
            return;
        }
        //收集用户id获取报名用户的信息
        List<String> userIdList = Lists.newArrayList();
        Map<String, String> userAddressMap = Maps.newHashMap(); // id->所报名的考点 便于后续查找用户报名的考点
        if (userList != null) {
            for (TssTempPostPersonAddDto tssTempPostPersonAddDto : userList) {
                userIdList.add(tssTempPostPersonAddDto.getUserId());
                userAddressMap.put(tssTempPostPersonAddDto.getUserId(), tssTempPostPersonAddDto.getTepId());
            }
            int count = iTempSubmitPersonService.countByUserListAndExamId(teId, userIdList);
            if (count > 0) {
                ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.USER_ALREADY_SUBMIT);
            }
        }
        List<TssPersonLibrary> tssPersonLibraries = tssPersonLibraryDao.selectBatchIds(userIdList); //报名用户的所有信息
        List<TssTempSubmitPerson> addUserList = Lists.newArrayList();
        //获取数据字典内容
        Map<Long, String> bankMap = new HashMap<>();
        Map<Long, String> personKindMap = new HashMap<>();
        Map<Long, String> personTypeMap = new HashMap<>();
        Map<Long, String> moneyMap = new HashMap<>();
        //装载返回来的MapList
        getDictMap(bankMap, personKindMap, personTypeMap, moneyMap);
        //将tssPersonLibrary对象转换成tssSubmitPerson对象
        for (TssPersonLibrary tssPersonLibrary : tssPersonLibraries) {
            TssTempSubmitPerson temp = new TssTempSubmitPerson();
            temp.setTeId(teId);
            temp.setBank(bankMap.get(tssPersonLibrary.getBank()));
            temp.setCategory(personKindMap.get(tssPersonLibrary.getCategory()));
            temp.setType(personTypeMap.get(tssPersonLibrary.getType()));
            temp.setMoneyType(moneyMap.get(tssPersonLibrary.getMoneyType()));
            try {
                BeanUtils.copyProperties(temp, tssPersonLibrary);
                temp.setAddress(userAddressMap.get(tssPersonLibrary.getId()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.DATA_CHANGE_ERROR);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.DATA_CHANGE_ERROR);
            }
            addUserList.add(temp);
        }
        boolean result = iTempSubmitPersonService.saveBatch(addUserList);
        if (!result) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.INSERT_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

        List<String> addUserNameList = addUserList.stream().map(TssTempSubmitPerson::getName).collect(Collectors.toList());

        // 记录日志
        String operationContent = String.format(LogConstant.ADD_PERSON, addUserNameList);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }

    public void downloadSubmitPersonRequestProcess(HttpServletRequest request, HttpServletResponse response) {
        String teId = dataCenterService.getData("teId");
        String sysUserId = dataCenterService.getCurrentUserId();
        //查询管理员的子部门的id
        SysAdminSelectDetailDto sysAdminSelectDetailDto = sysAdminDetailDao.selectAdminDetail(sysUserId);
        List<Long> childrenCollegeId = Lists.newArrayList();
        SysDepartment sysDepartment = null;
        if (sysAdminSelectDetailDto == null) {
            ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.NO_USER);
        }
        if (sysAdminSelectDetailDto.getDeptId() != null) {
            sysDepartment = sysDepartmentDao.selectById(sysAdminSelectDetailDto.getDeptId());
            childrenCollegeId = sysDepartmentDao.getChildrenCollegeId(sysDepartment.getLevel(), sysDepartment.getId());
        }
        //获取考试报名人员信息
        List<TempSubmitPersonVo> PersonList = tssTempSubmitPersonDao.seachSubmitPersonByTwoConditions(childrenCollegeId, teId);
        //转换为excel写入对象
        List<TempSubmitPersonVo> toBeWrite = Lists.newArrayList();
        int count = 1;
        for (TempSubmitPersonVo tssTempSubmitPerson : PersonList) {
            tssTempSubmitPerson.setOrder(count);
            count++;
            //身份证解密
            String pid = tssTempSubmitPerson.getPid();
            tssTempSubmitPerson.setPid(AESUtil.AESDecode(encodeRole, pid));
            toBeWrite.add(tssTempSubmitPerson);
        }
        //获取导出考试的名称和时间
        TssExam tssExam = tssExamDao.selectById(teId);
        if (tssExam == null) {
            ExceptionUtil.setFailureMsgAndThrow(GradeManagementReasonOfFailure.NO_EXAM);
        }
        //生成excel表格
        String collegeName = sysAdminSelectDetailDto.getName();
        Date date1 = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String date = df.format(date1);
        String fileName = collegeName + "岗位报名表" + ".xls";  //文件名
        String title = collegeName + "（盖章）" + tssExam.getName() + " " + "监考名单" + "  " + "导出时间：" + date;//excel标题
        try (OutputStream outputStream = new FileOutputStream(exportFileLocalPath + submitPersonExcelFileSave + fileName)) {
            File file = new File(exportFileLocalPath + submitPersonExcelFileSave);
            if (!file.exists()) {//不存在则创建文件夹
                file.mkdirs();
            }
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(
                    title, "岗位报名表"), TempSubmitPersonVo.class, toBeWrite);
            workbook.write(outputStream);

        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.setFailureMsgAndThrow(CollegeProctorManagementReasonOfFailure.FILE_SAVE_ERROR);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "downloadUrl", downloadFileWebPath + submitPersonExcelFileSave + fileName);

        // 记录日志
        String operationContent = String.format(LogConstant.DOWNLOAD_SUBMIT_PERSON, tssExam.getName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

}
