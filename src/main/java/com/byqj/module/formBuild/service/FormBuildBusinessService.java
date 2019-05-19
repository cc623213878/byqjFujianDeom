package com.byqj.module.formBuild.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byqj.dao.SysAclDao;
import com.byqj.dao.SysAclModuleDao;
import com.byqj.dao.SysAdminDetailDao;
import com.byqj.dao.SysDepartmentDao;
import com.byqj.dao.SysRoleAclDao;
import com.byqj.dao.SysRoleDao;
import com.byqj.dao.SysRoleUserDao;
import com.byqj.dao.SysUserAclDao;
import com.byqj.dao.SysUserDao;
import com.byqj.dto.AclDto;
import com.byqj.dto.AclModuleLevelDto;
import com.byqj.dto.RoleCodeAndNameDto;
import com.byqj.dto.SysAdminDetailDto;
import com.byqj.dto.SysAdminSelectDetailDto;
import com.byqj.entity.SysAclModule;
import com.byqj.entity.SysAdminDetail;
import com.byqj.entity.SysDepartment;
import com.byqj.entity.SysUser;
import com.byqj.entity.SysUserAcl;
import com.byqj.module.formBuild.constant.AclSplitConstants;
import com.byqj.module.formBuild.constant.LogConstant;
import com.byqj.module.formBuild.constant.StatusConstant;
import com.byqj.module.formBuild.constant.TypeConstants;
import com.byqj.module.formBuild.constant.UserStatusConstant;
import com.byqj.module.formBuild.enums.FormBuildReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.CommonUtil;
import com.byqj.utils.ExceptionUtil;
import com.byqj.utils.IdUtils;
import com.byqj.utils.LevelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.primitives.Longs;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FormBuildBusinessService {

    @Autowired
    FormBuildCheckService formBuildCheckService;
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    SysAclDao sysAclDao;
    @Autowired
    SysUserAclDao sysUserAclDao;
    @Autowired
    SysRoleAclDao sysRoleAclDao;
    @Autowired
    SysRoleUserDao sysRoleUserDao;
    @Autowired
    SysAclModuleDao sysAclModuleDao;
    @Autowired
    LogCenterService logCenterService;
    @Autowired
    SysRoleDao sysRoleDao;
    @Autowired
    SysAdminDetailDao sysAdminDetailDao;
    @Autowired
    SysDepartmentDao sysDepartmentDao;

    @Value("${user.init-password}")
    private String initPassword;

    /**
     * 重置管理员密码
     */
    @Transactional
    public void resetPasswordRequestProcess() {
        String userId = dataCenterService.getData("userId");
        boolean result = sysUserDao.setPassword(userId, CommonUtil.passwordEncodeByBCrypt(initPassword));

        if (!result) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_DOES_NOT_EXIST);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        // 记录日志
        SysAdminDetail sysAdminDetail = sysAdminDetailDao.selectOne(new LambdaQueryWrapper<SysAdminDetail>()
                .eq(SysAdminDetail::getUserId, userId));
        String operationContent = String.format(LogConstant.RESET_PERSONNEL_PASSWORD_TEMPLATE, sysAdminDetail.getUserName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }

    /**
     * 修改管理员 使用状态，启用或停用
     */
    @Transactional
    public void modifyUserStatusRequestProcess() {
        Integer stateConstant = null;// 设置state常量
        String userId = dataCenterService.getData("userId");
        Integer state = dataCenterService.getData("state");

        if (state.equals(1)) {
            stateConstant = UserStatusConstant.DISABLED;
        } else if (state.equals(0)) {
            stateConstant = UserStatusConstant.ENABLE;
        }

        boolean result = sysUserDao.modifyUserStatus(userId, stateConstant);
        if (!result) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_DOES_NOT_EXIST);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        // 记录日志
        SysAdminDetail sysAdminDetail = sysAdminDetailDao.selectOne(new LambdaQueryWrapper<SysAdminDetail>()
                .eq(SysAdminDetail::getUserId, userId));
        String operationContent = String.format(LogConstant.MODIFY_PERSONNEL_STATUS_TEMPLATE, sysAdminDetail.getUserName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    /**
     * 删除管理员
     */
    @Transactional
    public void deletePersonnelRequestProcess() {
        List<String> userIdList = dataCenterService.getData("userIdList");

        sysAdminDetailDao.batchDeleteAdminFromUserDetails(userIdList);
        sysUserDao.batchDeleteUserFromUsers(userIdList);
        sysAclDao.batchDeleteUserFromSysUserAcl(userIdList);
        sysRoleUserDao.batchDeleteUserFromSysRoleUser(userIdList);

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        // 记录日志
        List<String> userNameList = sysAdminDetailDao.searchAdminDetailByIdList(userIdList);
        String operationContent = String.format(LogConstant.DELETE_PERSONNEL_TEMPLATE, userNameList);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();

    }

    /**
     * 按条件模糊查询搜索
     */
    public void searchPersonnelByConditionRequestProcess() {

        String partUserName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userName");
        String partRealName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("realName");
        String partPhone = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("phone");
        String deptIdString = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("deptId");
        String roleCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("roleCode");
        String state = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("state");
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");

        List<SysAdminDetailDto> userList = Lists.newArrayList();
        String currentUserId = dataCenterService.getCurrentUserId();

        //查询Level，运用在查询里
        SysDepartment sysDepartment = new SysDepartment();
        List<Long> departmentIdList = Lists.newArrayList();
        if (!StringUtils.isBlank(deptIdString)) {
            Long deptId = Longs.tryParse(deptIdString);
            sysDepartment = sysDepartmentDao.selectById(deptId);
            departmentIdList = sysDepartmentDao.getChildrenCollegeId(sysDepartment.getLevel(), sysDepartment.getId());
            departmentIdList.add(sysDepartment.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isBlank(roleCode)) {
            userList = sysAdminDetailDao.fuzzyQueryByPartAdminInfoFromUsersAndAdminDetails(partUserName,
                    partRealName, partPhone, departmentIdList, state, currentUserId);
        } else {
            userList = sysAdminDetailDao.fuzzyQueryByGroupIdAndPartAdminInfoFromUsersAndAdminDetails(partUserName,
                    partRealName, partPhone, roleCode, departmentIdList, state, currentUserId);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        PageInfo<SysAdminDetailDto> pageResult = new PageInfo<>(userList);
        JSONObject data = new JSONObject();
        data.put("userList", pageResult);
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        responseData.setData(data);
    }

    /*
     * @Author gys
     * @Description 获取编辑用户对应的权限树
     * @Date 1:52 2019/3/4
     * @Param []
     * @return void
     **/
    public void getManagerAclInfoRequestProcess() {

        String userId = dataCenterService.getData("userId");
        //获取所有部门及其权限
        List<AclModuleLevelDto> getBackgroundModuleList = sysAclModuleDao.getModuleWithAcl(TypeConstants.BACKGROUND, StatusConstant.SYS_ACL_ENABLE);
        List<AclModuleLevelDto> getFrontModuleList = sysAclModuleDao.getModuleWithAcl(TypeConstants.FRONT, StatusConstant.SYS_ACL_ENABLE);
        //获取当前登录用户的所有权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> aclCodes = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        //获取编辑用户的权限.
        int result = sysUserAclDao.selectAclCountById(userId);
        String getManagerAcl = null;
        if (result != 0) {
            getManagerAcl = sysUserAclDao.selectAclById(userId); //获取选中管理员的所有权限
        }
        //将获取到的权限拆分后放入集合中
        Set<String> userAclCodeSet = new HashSet<>(); //存储编辑管理者所拥有的权限，用于checked的判断
        Set<String> currentUserAclCodeSet = new HashSet<>(); //存储当前操作的用户所拥有的权限，用于hasAcl的判断
        String[] acl = null;
        //处理当前用户的所有权限
        if (aclCodes != null) {
            currentUserAclCodeSet.addAll(aclCodes);
        }
        //处理编辑用户的所有权限
        if (getManagerAcl != null) {
            acl = getManagerAcl.split(AclSplitConstants.REGEX);
            List<String> userAclLists = Arrays.asList(acl);
            userAclCodeSet.addAll(userAclLists);
        }
        //判断 background 的checked 与 hasAcl 状态
        for (AclModuleLevelDto aclModuleLevelDto : getBackgroundModuleList) {
            if (CollectionUtils.isEmpty(aclModuleLevelDto.getAclList())) {
                continue;
            }
            List<AclDto> aclDtos = aclModuleLevelDto.getAclList();
            for (AclDto aclDto : aclDtos) {
                if (userAclCodeSet.contains(aclDto.getCode())) {
                    aclDto.setChecked(true);
                }
                if (currentUserAclCodeSet.contains(aclDto.getCode())) {
                    aclDto.setHasAcl(true);
                }
            }

        }

        //判断 front 的 checked 与 hasAcl 状态
        for (AclModuleLevelDto aclModuleLevelDto : getFrontModuleList) {
            if (CollectionUtils.isEmpty(aclModuleLevelDto.getAclList())) {
                continue;
            }
            List<AclDto> aclDtos = aclModuleLevelDto.getAclList();
            for (AclDto aclDto : aclDtos) {
                if (userAclCodeSet.contains(aclDto.getCode())) {
                    aclDto.setChecked(true);
                }
                if (currentUserAclCodeSet.contains(aclDto.getCode())) {
                    aclDto.setHasAcl(true);
                }
            }

        }

        //权限模块dto转tree
        List<AclModuleLevelDto> aclBackgroundModuleLevelTreeList = aclModuleListToTree(getBackgroundModuleList);
        List<AclModuleLevelDto> aclFrontModuleLevelTreeList = aclModuleListToTree(getFrontModuleList);

        //获取roleList
        List<RoleCodeAndNameDto> roleList = sysRoleDao.searchRoleName(userId);

        //获取选中用户的细节，确认选中人员细节有装载
        SysAdminSelectDetailDto sysAdminDetail = sysAdminDetailDao.selectAdminDetail(userId);
        if (StringUtils.isBlank(sysAdminDetail.getUserId())) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.OPERATE_ERROR);
        }
        //获取roleList
        List<RoleCodeAndNameDto> AllRoleList = sysRoleDao.showRoleCodeAndName();
        if (CollectionUtils.isEmpty(AllRoleList)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.OPERATE_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "sysAdminDetail", sysAdminDetail);
        ResponseDataUtil.putValueToData(responseData, "roleList", roleList);
        ResponseDataUtil.putValueToData(responseData, "AllRoleList", AllRoleList);
        ResponseDataUtil.putValueToData(responseData, "backgroundAcl", aclBackgroundModuleLevelTreeList);
        ResponseDataUtil.putValueToData(responseData, "frontAcl", aclFrontModuleLevelTreeList);
    }

    private List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        // level -> [aclmodule1, aclmodule2, ...] Map<String, List<Object>>
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();

        //root根添加到list中并将所有的aclmodule添加到
        for (AclModuleLevelDto dto : dtoList) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        //rootList排序
        rootList.sort(aclModuleSeqComparator);
        //根据level转为tree，把子节点添加到rootList中
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
        return rootList;
    }

    private Comparator<AclModuleLevelDto> aclModuleSeqComparator = Comparator.comparingInt(SysAclModule::getSeq);

    private void transformAclModuleTree(List<AclModuleLevelDto> dtoList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        for (AclModuleLevelDto dto : dtoList) {
            //0.1  |  0.2
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            //从levelAclModuleMap中找出子节点
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                //对子节点进行排序
                tempList.sort(aclModuleSeqComparator);
                dto.setAclModuleList(tempList);
                //递归转为tree
                transformAclModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        }
    }


    /**
     * 修改管理员
     */
    @Transactional
    public void modifyPersonnelRequestProcess() {
        //获取数据
        String userId = dataCenterService.getData("userId");
        String userName = dataCenterService.getData("userName");
        String realName = dataCenterService.getData("realName");
        List<String> aclList = dataCenterService.getData("aclList");
        List<String> roleCodeList = dataCenterService.getData("roleCodeList");
        String phone = dataCenterService.getData("phone");
        String post = dataCenterService.getData("post");
//        String email = dataCenterService.getData("email");
//        Integer sex = dataCenterService.getData("sex");
//        String deptIdString = dataCenterService.getData("deptId");
//        Long deptId = Longs.tryParse(deptIdString);
//        String idCard = dataCenterService.getData("idCard");
        //将数据封装到对象中
        SysAdminDetail sysAdminDetail = new SysAdminDetail();
        sysAdminDetail.setUserName(userName);
        sysAdminDetail.setUserId(userId);
        sysAdminDetail.setRealName(realName);
        sysAdminDetail.setPhone(phone);
        sysAdminDetail.setPost(post);
//        sysAdminDetail.setEmail(email);
//        sysAdminDetail.setSex(sex);
//        sysAdminDetail.setDeptId(deptId);
//        sysAdminDetail.setIdCard(idCard);
        //更新数据库
        int updatePersonnelToAdminDetailsResult = sysAdminDetailDao.updateById(sysAdminDetail);
        if (updatePersonnelToAdminDetailsResult == 0) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.OPERATE_ERROR);
        }
        if (!(CollectionUtils.isEmpty(aclList))) {
            String aclString = StringUtils.join(aclList, AclSplitConstants.REGEX);
            SysUserAcl sysUserAcl = new SysUserAcl();
            sysUserAcl.setUserId(userId);
            sysUserAcl.setAclCode(aclString);
            sysUserAclDao.delete(new QueryWrapper<SysUserAcl>().lambda().eq(SysUserAcl::getUserId, userId));
            int updateUserAclResult = sysUserAclDao.insert(sysUserAcl);
            if (updateUserAclResult == 0) {
                ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.SAVE_USER_ACL_ERROR);
            }
        }
        sysRoleUserDao.deleteUserFromSysRoleUser(userId);
        if (!(CollectionUtils.isEmpty(roleCodeList))) {
            boolean addPersonnelToGroupMembersResult = sysRoleUserDao.addPersonnelToSysRoleUser(userId, roleCodeList);
            if (!addPersonnelToGroupMembersResult) {
                ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.SAVE_USER_ROLE_ERROR);
            }
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        // 记录日志
        String operationContent = String.format(LogConstant.MODIFY_PERSONNEL_TEMPLATE, sysAdminDetail.getUserName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    /*
        添加管理员
     */
    @Transactional
    public void addAdminRequestProcess() {
        //获取数据
        String userId = IdUtils.getUUID();
        String userName = dataCenterService.getData("userName");
        String realName = dataCenterService.getData("realName");
        String phone = dataCenterService.getData("phone");
        String post = dataCenterService.getData("post");
//        Integer sex = dataCenterService.getData("sex");
//        String idCard = dataCenterService.getData("idCard");
//        String email = dataCenterService.getData("email");
        String deptIdString = dataCenterService.getData("deptId");
        Long deptId = Longs.tryParse(deptIdString);
        List<String> aclList = dataCenterService.getData("aclList");
        List<String> roleCodeList = dataCenterService.getData("roleCodeList");
        //将数据封装到对象中
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setPassword(CommonUtil.passwordEncodeByBCrypt(initPassword));
        sysUser.setLocked(StatusConstant.ENABLE);
        SysAdminDetail sysAdminDetail = new SysAdminDetail();
        sysAdminDetail.setUserId(userId);
        sysAdminDetail.setUserName(userName);
        sysAdminDetail.setRealName(realName);
        sysAdminDetail.setPhone(phone);
        sysAdminDetail.setPost(post);
//        sysAdminDetail.setSex(sex);
//        sysAdminDetail.setEmail(email);
        sysAdminDetail.setDeptId(deptId);
//        sysAdminDetail.setIdCard(idCard);
        sysAdminDetail.setCreateTime(new Date());
        //更新数据库
        int insertUserResult = sysUserDao.insert(sysUser);
        if (insertUserResult == 0) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.OPERATE_ERROR);
        }
        int insertAdminDetailResult = sysAdminDetailDao.insert(sysAdminDetail);
        if (insertAdminDetailResult == 0) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.OPERATE_ERROR);
        }
        if (!(CollectionUtils.isEmpty(aclList))) {
            SysUserAcl sysUserAcl = new SysUserAcl();
            String aclString = StringUtils.join(aclList, AclSplitConstants.REGEX);
            sysUserAcl.setUserId(userId);
            sysUserAcl.setAclCode(aclString);
            int insertSysUserAclResult = sysUserAclDao.insert(sysUserAcl);
            if (insertSysUserAclResult == 0) {
                ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.OPERATE_ERROR);
            }

        }
        if (!(CollectionUtils.isEmpty(roleCodeList))) {
            boolean addPersonnelToGroupMembersResult = sysRoleUserDao.addPersonnelToSysRoleUser(userId, roleCodeList);
            if (!addPersonnelToGroupMembersResult) {
                ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.OPERATE_ERROR);
            }
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        // 记录日志
        String operationContent = String.format(LogConstant.ADD_PERSONNEL_TEMPLATE, sysAdminDetail.getUserName());
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    public void addUserDisplayRequestProcess() {
        String userId = dataCenterService.getCurrentUserId();
        //获取所有部门及其权限
        List<AclModuleLevelDto> getBackgroundModuleList = sysAclModuleDao.getModuleWithAcl(TypeConstants.BACKGROUND, StatusConstant.SYS_ACL_ENABLE);
        List<AclModuleLevelDto> getFrontModuleList = sysAclModuleDao.getModuleWithAcl(TypeConstants.FRONT, StatusConstant.SYS_ACL_ENABLE);
        //获取当前登录用户的所有权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> aclCodes = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        //将获取到的权限拆分后放入集合中
        Set<String> userAclCodeSet = new HashSet<>(); //存储当前操作的用户所拥有的权限，用于hasAcl的判断
        if (!CollectionUtils.isEmpty(aclCodes)) {
            userAclCodeSet.addAll(aclCodes);
        }
        String[] acl = null;
        //判断 background 的checked 与 hasAcl 状态
        for (AclModuleLevelDto aclModuleLevelDto : getBackgroundModuleList) {
            if (CollectionUtils.isEmpty(aclModuleLevelDto.getAclList())) {
                continue;
            }
            List<AclDto> aclDtos = aclModuleLevelDto.getAclList();
            for (AclDto aclDto : aclDtos) {
                if (userAclCodeSet.contains(aclDto.getCode())) {
                    aclDto.setHasAcl(true);
                }
            }

        }

        //判断 front 的checked 与 hasAcl 状态
        for (AclModuleLevelDto aclModuleLevelDto : getFrontModuleList) {
            if (CollectionUtils.isEmpty(aclModuleLevelDto.getAclList())) {
                continue;
            }
            List<AclDto> aclDtos = aclModuleLevelDto.getAclList();
            for (AclDto aclDto : aclDtos) {
                if (userAclCodeSet.contains(aclDto.getCode())) {
                    aclDto.setHasAcl(true);
                }
            }

        }

        //权限模块dto转tree
        List<AclModuleLevelDto> backgroundAclModuleLevelTreeList = aclModuleListToTree(getBackgroundModuleList);
        List<AclModuleLevelDto> frontAclModuleLevelTreeList = aclModuleListToTree(getFrontModuleList);


        //获取roleList
        List<RoleCodeAndNameDto> roleList = sysRoleDao.showRoleCodeAndName();
        if (CollectionUtils.isEmpty(roleList)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.OPERATE_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "roleList", roleList);
        ResponseDataUtil.putValueToData(responseData, "backgroundModuleAcl", backgroundAclModuleLevelTreeList);
        ResponseDataUtil.putValueToData(responseData, "frontModuleAcl", frontAclModuleLevelTreeList);

    }
}
