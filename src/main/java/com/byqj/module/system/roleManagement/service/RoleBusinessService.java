package com.byqj.module.system.roleManagement.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.byqj.dao.SysAclModuleDao;
import com.byqj.dao.SysRoleAclDao;
import com.byqj.dao.SysRoleDao;
import com.byqj.dao.SysRoleUserDao;
import com.byqj.dto.AclDto;
import com.byqj.dto.AclModuleLevelDto;
import com.byqj.dto.RoleCodeAndNameDto;
import com.byqj.dto.SysRoleUserDto;
import com.byqj.entity.SysAclModule;
import com.byqj.entity.SysRole;
import com.byqj.entity.SysRoleAcl;
import com.byqj.module.system.roleManagement.constant.AclSplitConstants;
import com.byqj.module.system.roleManagement.constant.LogConstant;
import com.byqj.module.system.roleManagement.constant.StatusConstant;
import com.byqj.module.system.roleManagement.constant.TypeConstants;
import com.byqj.module.system.roleManagement.enums.RoleReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.ExceptionUtil;
import com.byqj.utils.IdUtils;
import com.byqj.utils.LevelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleBusinessService {

    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    SysRoleDao sysRoleDao;
    @Autowired
    SysRoleAclDao sysRoleAclDao;
    @Autowired
    SysRoleUserDao sysRoleUserDao;
    @Autowired
    LogCenterService logCenterService;
    @Autowired
    SysAclModuleDao sysAclModuleDao;

    /**
     * 获取ROLE列表
     */
    public void searchRoleRequestProcess() {
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");

        String roleName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("roleName");

        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> roles = sysRoleDao.searchRole(roleName);
        PageInfo<SysRole> pageResult = new PageInfo<>(roles);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "roles", pageResult);
    }

    /**
     * 获取ROLE类型
     */
    public void searchRoleClassRequestProcess() {

        List<RoleCodeAndNameDto> rolesClass = sysRoleDao.searchRoleClass();
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "rolesClass", rolesClass);
    }

    /**
     * 添加权组
     */
    @Transactional
    public void addRoleRequestProcess() {
        String roleCode = IdUtils.getUUID();
        Date date = new Date();
        String roleName = dataCenterService.getData("roleName");
        String remark = dataCenterService.getData("remark");
        List<String> permissions = dataCenterService.getData("permissions");
        String userId = dataCenterService.getCurrentUserId();
        SysRole role = new SysRole();
        role.setOperator(userId);
        role.setRoleName(roleName);
        role.setRemark(remark);
        role.setRoleCode(roleCode);
        role.setCreateTime(date);
        role.setUpdateTime(date);
        int result = sysRoleDao.insert(role);
        if (result == 0) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.INSERT_ERROR);
        }

        // 获取主键插入到role_acl表中
        SysRoleAcl sysRoleAcl = new SysRoleAcl();
        sysRoleAcl.setRoleCode(roleCode);
        String permissionStr = StringUtils.join(permissions, AclSplitConstants.REGEX);
        sysRoleAcl.setAclCode(permissionStr);
        int insertRoleAclReslut = sysRoleAclDao.insert(sysRoleAcl);
        if (insertRoleAclReslut == 0) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.INSERT_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        String operationContent = String.format(LogConstant.ADD_AUTHORITY_GROUP_TEMPLATE, roleName);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }


    /**
     * 删除一个或多个权组
     */
    @Transactional
    public void deleteRoleRequestProcess() {
        List<String> roleCodeList = dataCenterService.getData("roleCodeList");
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();

        sysRoleAclDao.batchDeleteRoleAclByRoleCode(roleCodeList);
        sysRoleDao.batchDeleteRoleByRoleCode(roleCodeList);
        // 记录日志
        List<String> roleName = sysRoleDao.getRoleNameById(roleCodeList);
        String operationContent = String.format(LogConstant.DELETE_AUTHORITY_GROUP_TEMPLATE, roleName);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    /**
     * 修改权组
     */
    @Transactional
    public void modifyRoleRequestProcess() {
        Date date = new Date();
        String roleCode = dataCenterService.getData("roleCode");
        String roleName = dataCenterService.getData("roleName");
        String remark = dataCenterService.getData("remark");
        List<String> permissions = dataCenterService.getData("permissions");
        //封装对象
        SysRole role = new SysRole();
        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        role.setRemark(remark);
        role.setUpdateTime(date);
        int result = sysRoleDao.updateById(role);
        if (result == 0) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.UPDATE_ROLE_ERROR);
        }
//        //构造权限，权限包括原本权限中当前用户所有的权限以外的权限加上新上传的权限
//        //1、获取该角色原先的权限
//        SysRoleAcl beforeRoleAcl = sysRoleAclDao.selectOne(new QueryWrapper<SysRoleAcl>().lambda().eq(SysRoleAcl::getRoleCode, roleCode));
//        String beforePermission = beforeRoleAcl.getAclCode();
        List<String> newAclList = Lists.newArrayList();
//        if (beforePermission != null) {
//            String[] beforeAcl = beforePermission.split(AclSplitConstants.REGEX);
//            List<String> beforeAclLists = Arrays.asList(beforeAcl);
//            //获取当前登录用户的权限
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            List<String> currentUserAcl = (List) authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//            //获取不在当前登陆用户可选权限但是原先已经有的权限
//            List<String> reduceAclLists = beforeAclLists.stream().filter(item -> !currentUserAcl.contains(item)).collect(Collectors.toList());
//            //构造新的权限包括新上传的权限和当前登录用户不可选但是原先已经包含的权限
//            newAclList.addAll(reduceAclLists);
//        }
        newAclList.addAll(permissions);
        // 获取主键插入到role_acl表中
        SysRoleAcl sysRoleAcl = new SysRoleAcl();
        String acl = StringUtils.join(newAclList, AclSplitConstants.REGEX);
        sysRoleAcl.setAclCode(acl);
        int updateResult = sysRoleAclDao.update(sysRoleAcl, new UpdateWrapper<SysRoleAcl>().lambda().eq(SysRoleAcl::getRoleCode, roleCode));
        if (updateResult == 0) {
            ExceptionUtil.setFailureMsgAndThrow(RoleReasonOfFailure.UPDATE_ROLE_ACL_ERROR);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        // 记录日志
        String operationContent = String.format(LogConstant.MODIFY_AUTHORITY_GROUP_TEMPLATE, roleName);
        logCenterService.setContent(operationContent);
        logCenterService.setResultIsTrue();
    }

    /*
     * @Author lwn
     * @Description 获取role对应的权限树
     * @Date 2:50 2019/3/4
     * @Param []
     * @return void
     **/
    public void getRoleAclRequestProcess() {
        String roleCode = dataCenterService.getData("roleCode");
        //获取所有部门及其权限
        List<AclModuleLevelDto> getBackgroundModuleList = sysAclModuleDao.getModuleWithAcl(TypeConstants.BACKGROUND, StatusConstant.ENABLE);
        List<AclModuleLevelDto> getFrontModuleList = sysAclModuleDao.getModuleWithAcl(TypeConstants.FRONT, StatusConstant.ENABLE);
        //获取当前登录用户的所有权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> aclCodes = (List) authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        //获取前台选中role的权限.
        List<SysRoleAcl> getRoleAclObjects = sysRoleAclDao.selectList(new QueryWrapper<SysRoleAcl>().lambda().eq(SysRoleAcl::getRoleCode, roleCode)); //获取选中角色的所有权限
        //将获取到的权限拆分后放入集合中
        Set<String> roleAclCodeSet = new HashSet<>(); //存储当前role所拥有的权限，用于checked的判断
        Set<String> userAclCodeSet = new HashSet<>(); //存储当前操作的用户所拥有的权限，用于hasAcl的判断
        String[] acl = null;
        //处理当前用户的所有权限
        userAclCodeSet.addAll(aclCodes);
        //处理选中角色的所有权限
        for (SysRoleAcl getRoleAclObject : getRoleAclObjects) {
            String roleAcl = getRoleAclObject.getAclCode();
            acl = roleAcl.split(AclSplitConstants.REGEX);
            List<String> aclLists = Arrays.asList(acl);
            roleAclCodeSet.addAll(aclLists);
        }
        //判断 background checked 与 hasAcl 状态
        for (AclModuleLevelDto aclModuleLevelDto : getBackgroundModuleList) {
            if (CollectionUtils.isEmpty(aclModuleLevelDto.getAclList())) {
                continue;
            }
            List<AclDto> aclDtos = aclModuleLevelDto.getAclList();
            for (AclDto aclDto : aclDtos) {
                if (roleAclCodeSet.contains(aclDto.getCode())) {
                    aclDto.setChecked(true);
                }
                if (userAclCodeSet.contains(aclDto.getCode())) {
                    aclDto.setHasAcl(true);
                }
            }

        }

        //判断 front checked 与 hasAcl 状态
        for (AclModuleLevelDto aclModuleLevelDto : getFrontModuleList) {
            if (CollectionUtils.isEmpty(aclModuleLevelDto.getAclList())) {
                continue;
            }
            List<AclDto> aclDtos = aclModuleLevelDto.getAclList();
            for (AclDto aclDto : aclDtos) {
                if (roleAclCodeSet.contains(aclDto.getCode())) {
                    aclDto.setChecked(true);
                }
                if (userAclCodeSet.contains(aclDto.getCode())) {
                    aclDto.setHasAcl(true);
                }
            }

        }

        //权限模块dto转tree
        List<AclModuleLevelDto> backgroundAclModuleLevelTreeList = aclModuleListToTree(getBackgroundModuleList);
        List<AclModuleLevelDto> frontAclModuleLevelTreeList = aclModuleListToTree(getFrontModuleList);

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "backgroundAcl", backgroundAclModuleLevelTreeList);
        ResponseDataUtil.putValueToData(responseData, "frontAcl", frontAclModuleLevelTreeList);
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

    public void searchRolePersonRequestProcess() {

        String roleCode = dataCenterService.getData("roleCode");
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");

        PageHelper.startPage(pageNum, pageSize);
        List<SysRoleUserDto> RoleUser = sysRoleUserDao.searchUserDetailByRoleCode(roleCode);

        PageInfo<SysRoleUserDto> pageResult = new PageInfo<>(RoleUser);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "RoleUser", pageResult);

    }

    public void getCurrentUserAclRequestProcess() {
        //获取所有部门及其权限
        List<AclModuleLevelDto> getBackgroundModuleList = sysAclModuleDao.getModuleWithAcl(TypeConstants.BACKGROUND, StatusConstant.ENABLE);
        List<AclModuleLevelDto> getFrontModuleList = sysAclModuleDao.getModuleWithAcl(TypeConstants.FRONT, StatusConstant.ENABLE);
        //获取当前登录用户的所有权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> aclCodes = (List) authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        //将获取到的权限拆分后放入集合中
        Set<String> userAclCodeSet = new HashSet<>(); //存储当前操作的用户所拥有的权限，用于hasAcl的判断
        String[] acl = null;
        //处理当前用户的所有权限
        userAclCodeSet.addAll(aclCodes);
        //设置 background hasAcl 状态
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

        //设置 front hasAcl 状态
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
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "backgroundAcl", backgroundAclModuleLevelTreeList);
        ResponseDataUtil.putValueToData(responseData, "frontAcl", frontAclModuleLevelTreeList);
    }
}
