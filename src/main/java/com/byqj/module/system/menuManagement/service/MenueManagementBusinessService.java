package com.byqj.module.system.menuManagement.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.SysAclModuleDao;
import com.byqj.dao.SysAdminDetailDao;
import com.byqj.dto.AccessMenuDto;
import com.byqj.dto.AccessRouteDto;
import com.byqj.dto.MetaDto;
import com.byqj.entity.SysAcl;
import com.byqj.entity.SysAclModule;
import com.byqj.entity.SysAdminDetail;
import com.byqj.module.system.menuManagement.constant.MenueManagementConstant;
import com.byqj.module.system.menuManagement.enums.MenueManagementOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.util.ResponseDataUtil;
import com.byqj.service.IAclModuleService;
import com.byqj.service.ISysAclService;
import com.byqj.utils.ExceptionUtil;
import com.byqj.utils.LevelUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.primitives.Longs;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenueManagementBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private SysAdminDetailDao sysAdminDetailDao;
    @Autowired
    private SysAclModuleDao sysAclModuleDao;
    @Autowired
    private ISysAclService sysAclService;
    @Autowired
    private IAclModuleService aclModuleService;


    public void getMenueInfoRequestProcess() {
        //获取登录属于前台还是后台
        Integer type = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("type");
        if (type == null) {
            type = MenueManagementConstant.BACKGROUND;
        }
        if (type != MenueManagementConstant.BACKGROUND && type != MenueManagementConstant.FRONT) {
            ExceptionUtil.setFailureMsgAndThrow(MenueManagementOfFailure.Type_ERROR);
        }
        //获取当前登录用户的所有权限存入userPermissions
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> userPermissions = (List) authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        //获取当前登录用户的所有信息
        String userId = dataCenterService.getCurrentUserId();
        SysAdminDetail sysAdminDetail = sysAdminDetailDao.selectOne(new LambdaQueryWrapper<SysAdminDetail>().eq(SysAdminDetail::getUserId, userId));

        List<SysAclModule> finalAclModulesList = null;
        //获取权限对应的module信息
        //收集权限对应的模块
        Set<Long> moduleIdSet = Sets.newHashSet();
        List<SysAcl> sysAclList = sysAclService.selectModuleIdByAclCodes(userPermissions);
        for (SysAcl sysAcl : sysAclList) {
            moduleIdSet.add(sysAcl.getAclModuleId());
        }
        List<SysAclModule> moduleIdList = sysAclModuleDao.selectBatchIds(moduleIdSet);
        //遍历获取对应模块的上级模块
        Set<Long> allModuleIdSet = Sets.newHashSet();
        for (SysAclModule sysAclModule : moduleIdList) {
            String level = sysAclModule.getLevel();
            String[] ids = StringUtils.split(level, MenueManagementConstant.SPERATOR);
            for (String id : ids) {
                Long moduleId = Longs.tryParse(id);
                if (moduleId != null && moduleId != 0L) {
                    allModuleIdSet.add(moduleId);
                }
            }
            allModuleIdSet.add(sysAclModule.getId());
        }
        finalAclModulesList = aclModuleService.searchByIdsAndType(allModuleIdSet, type);
        //构建返回前台的对象
        Multimap<String, AccessMenuDto> accessMenueLeveltMap = ArrayListMultimap.create(); //用于构建树形结构
        Multimap<String, AccessRouteDto> accessRouteLeveltMap = ArrayListMultimap.create();//用于构建树形结构
        List<AccessMenuDto> accessMenueRootList = Lists.newArrayList();//用于构建树形结构
        List<AccessRouteDto> accessRouteRootList = Lists.newArrayList();//用于构建树形结构
        for (SysAclModule sysAclModule : finalAclModulesList) {
            AccessMenuDto accessMenuDto = new AccessMenuDto();
            AccessRouteDto accessRouteDto = new AccessRouteDto();
            //构建 accessMenuDto
            accessMenuDto.setId(sysAclModule.getId());
            accessMenuDto.setLevel(sysAclModule.getLevel());
            accessMenuDto.setSeq(sysAclModule.getSeq());
            accessMenuDto.setTitle(sysAclModule.getName());
            accessMenuDto.setPath(sysAclModule.getPath());
            accessMenuDto.setIcon(sysAclModule.getIcon());
            //构建 accessRouteDto
            accessRouteDto.setId(sysAclModule.getId());
            accessRouteDto.setLevel(sysAclModule.getLevel());
            accessRouteDto.setSeq(sysAclModule.getSeq());
            accessRouteDto.setName(sysAclModule.getRouteName());
            accessRouteDto.setComponent(sysAclModule.getComponent());
            accessRouteDto.setComponentPath(sysAclModule.getComponentPath());
            accessRouteDto.setPath(sysAclModule.getPath());
            MetaDto metaDto = new MetaDto();
            metaDto.setTitle(sysAclModule.getName());
            metaDto.setCache(sysAclModule.isCache());
            accessRouteDto.setMeta(metaDto);
            //加入list
            if (sysAclModule.getParentId() == 0) { //根节点
                accessMenueRootList.add(accessMenuDto);
                accessRouteRootList.add(accessRouteDto);
            } else {
                accessMenueLeveltMap.put(sysAclModule.getLevel(), accessMenuDto);
                accessRouteLeveltMap.put(sysAclModule.getLevel(), accessRouteDto);
            }
        }
        //转换成树状结构
        transformAccessMenuTree(accessMenueRootList, LevelUtil.ROOT, accessMenueLeveltMap);
        transformAccessRouteTree(accessRouteRootList, LevelUtil.ROOT, accessRouteLeveltMap);
        //返回给前台
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "userName", sysAdminDetail);
        ResponseDataUtil.putValueToData(responseData, "userPermissions", userPermissions);
        ResponseDataUtil.putValueToData(responseData, "accessMenus", accessMenueRootList);
        ResponseDataUtil.putValueToData(responseData, "accessRoutes", accessRouteRootList);


    }


    // level:0, 0, all 0->0.1,0.2
    // level:0.1

    // level:0.2
    private void transformAccessMenuTree(List<AccessMenuDto> deptLevelList, String level, Multimap<String, AccessMenuDto> levelDeptMap) {
        // 遍历该层的每个元素
        for (AccessMenuDto deptLevelDto : deptLevelList) {
            // 下一层级
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            List<AccessMenuDto> tempDeptList = (List<AccessMenuDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 按seq从小到大排序
                tempDeptList.sort(Comparator.comparingInt(AccessMenuDto::getSeq));
                // 设置下一层部门
                deptLevelDto.setChildren(tempDeptList);
                // 进入到下一层处理
                transformAccessMenuTree(tempDeptList, nextLevel, levelDeptMap);

            }
        }
    }

    // level:0, 0, all 0->0.1,0.2
    // level:0.1

    // level:0.2
    private void transformAccessRouteTree(List<AccessRouteDto> deptLevelList, String level, Multimap<String, AccessRouteDto> levelDeptMap) {
        // 遍历该层的每个元素
        for (AccessRouteDto deptLevelDto : deptLevelList) {
            // 下一层级
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            List<AccessRouteDto> tempDeptList = (List<AccessRouteDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 按seq从小到大排序
                tempDeptList.sort(Comparator.comparingInt(AccessRouteDto::getSeq));
                // 设置下一层部门
                deptLevelDto.setChildren(tempDeptList);
                // 进入到下一层处理
                transformAccessRouteTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }

}
