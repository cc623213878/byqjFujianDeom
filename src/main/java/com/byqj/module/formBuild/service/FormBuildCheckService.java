package com.byqj.module.formBuild.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byqj.dao.SysAdminDetailDao;
import com.byqj.dao.SysUserDao;
import com.byqj.entity.SysAdminDetail;
import com.byqj.entity.SysUser;
import com.byqj.module.formBuild.constant.StatusConstant;
import com.byqj.module.formBuild.enums.FormBuildReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.utils.CheckVariableUtil;
import com.byqj.utils.ExceptionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * CaoZhengxi
 * 2018年4月14日
 */
@Service
public class FormBuildCheckService {

    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    SysAdminDetailDao sysAdminDetailDao;


    /**
     * 重置管理员密码请求检测
     */
    public void resetPasswordRequestCheck() {
        String userId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userId");
        if (StringUtils.isBlank(userId)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_ID_IS_EMPTY);
        }
        dataCenterService.setData("userId", userId.trim());
    }

    /**
     * 修改管理员使用状态，启用或停用检测
     */
    public void modifyUserStatusRequestCheck() {
        String userId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userId");
        Integer state = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("state");
        if (StringUtils.isBlank(userId)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_ID_IS_EMPTY);
        }
        checkEnabled(state);
        dataCenterService.setData("userId", userId.trim());
        dataCenterService.setData("state", state);
    }

    /**
     * 删除管理员检测
     */
    public void deletePersonnelRequestCheck() {
        JSONArray userIdList = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userIdList");
        List<String> IdList = userIdList.toJavaList(String.class);
        if (CollectionUtils.isEmpty(IdList)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_ID_IS_EMPTY);
        }

        dataCenterService.setData("userIdList", IdList);
    }

    /*
     * @Author gys
     * @Description 获取管理员编辑显示列表
     * @Date 15:19 2019/3/5
     * @Param []
     * @return void
     **/
    public void getManagerAclInfoRequestCheck() {
        String userId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userId");
        if (StringUtils.isBlank(userId)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_ID_SUBMITTED_ARE_NULL);
        }
        int result = sysUserDao.selectCount(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserId, userId));
        if (result == 0) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_DOES_NOT_EXIST);
        }
        dataCenterService.setData("userId", userId.trim());
    }

    /**
     * 按条件搜索
     */
    public void searchPersonnelByConditionRequestCheck() {

        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");

        if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.PAGEINFO_ERROR);
        }
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);

    }


    /**
     * 检测state 启用停用标志是否合法
     */
    private void checkEnabled(Integer state) {
        if ((state != StatusConstant.ENABLE) && (state != StatusConstant.DISABLED)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.ENABLED_STATE_IS_ILLEGAL);
        }
    }


    /**
     * 修改管理员检测
     */
    public void modifyPersonnelRequestCheck() {

//        String email = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("email");
//        Integer sex = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("sex");
//        String idCard = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("idCard");
//        String deptId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("deptId");
        String userId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userId");
        String phone = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("phone");
        String realName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("realName");
        String userName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userName");
        String post = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("post");
        JSONArray aclJsonArray = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("aclList");
        List<String> aclList = aclJsonArray.toJavaList(String.class);
        JSONArray groupIdJsonArray = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("roleCodeList");
        List<String> roleIdList = groupIdJsonArray.toJavaList(String.class);

        if (StringUtils.isEmpty(userId)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_ID_IS_EMPTY);
        }
        int result = sysUserDao.selectCount(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserId, userId));
        if (result == 0) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_DOES_NOT_EXIST);
        }
        if (StringUtils.isBlank(realName)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.REAL_NAME_IS_EMPTY);
        }
        if (StringUtils.isBlank(userName)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_NAME_IS_EMPTY);
        }
        if (StringUtils.isBlank(post)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.POST_IS_EMPTY);
        }
        if (!CheckVariableUtil.isMobile(phone)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.SUBMITTED_PHONE_IS_WRONG);
        }
//        if (CheckVariableUtil.iDCardIsIllegal(idCard)) {
//            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.SUBMITTED_IDCARD_IS_WRONG);
//        }
//        if (StringUtils.isBlank(deptId)) {
//            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.DEPT_ID_IS_EMPTY);
//        }
//        if (!CheckVariableUtil.isEmai(email)) {
//            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.SUBMITTED_EMAIL_IS_WRONG);
//        }
//        if (!CheckVariableUtil.isSexConstants(sex)) {
//            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
//        }

        //查重处理，身份证，手机号,邮箱库里不能重复
        int phoneResult = sysAdminDetailDao.selectCount(new QueryWrapper<SysAdminDetail>().lambda().eq(SysAdminDetail::getPhone, phone).ne(SysAdminDetail::getUserId, userId));
        if (phoneResult > 0) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_ALREADY_EXISTS_DESCRIPTION);
        }
//        int idCardResult = sysAdminDetailDao.selectCount(new QueryWrapper<SysAdminDetail>().lambda().eq(SysAdminDetail::getIdCard, idCard).ne(SysAdminDetail::getUserId, userId));
//        if (idCardResult > 0) {
//            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_ALREADY_EXISTS_DESCRIPTION);
//        }
//        int emailResult = sysAdminDetailDao.selectCount(new QueryWrapper<SysAdminDetail>().lambda().eq(SysAdminDetail::getEmail, email).ne(SysAdminDetail::getUserId, userId));
//        if (emailResult > 0) {
//            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_ALREADY_EXISTS_DESCRIPTION);
//        }
        if (CollectionUtils.isEmpty(aclList) && CollectionUtils.isEmpty(roleIdList)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.THE_PERMISSIONS_CANNOT_BE_EMPTY);
        }

        // 角色和权限可以为空
        if (aclList != null) {
            if (!(isHasAcl(aclList))) {
                ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.PERMISSIONS_EXCEED_YOURS);
            }
        }

        dataCenterService.setData("realName", realName.trim());
        dataCenterService.setData("userName", userName.trim());
        dataCenterService.setData("aclList", aclList);
        dataCenterService.setData("roleCodeList", roleIdList);
        dataCenterService.setData("phone", phone.trim());
        dataCenterService.setData("post", post.trim());
        dataCenterService.setData("userId", userId.trim());
//        dataCenterService.setData("deptId", deptId);
//        dataCenterService.setData("sex", sex);
//        dataCenterService.setData("email", email);
//        dataCenterService.setData("idCard", idCard);

    }

    /*
     * @Author lwn
     * @Description 检测上传的权限是否是当前用户所包含的权限
     * @Date 15:21 2019/3/4
     * @Param []
     * @return boolean
     **/
    private boolean isHasAcl(List<String> permissions) {
        //获取当前登录用户的所有权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> aclCodes = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return aclCodes.containsAll(permissions);
    }

    /*
     * @Author lwn
     * @Description 添加admin
     * @Date 0:07 2019/3/6
     * @Param []
     * @return void
     **/
    public void addAdminRequestCheck() {
//        String email = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("email");
//        Integer sex = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("sex");
//        String idCard = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("idCard");
        String deptId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("deptId");
        String phone = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("phone");
        String realName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("realName");
        String userName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userName");
        String post = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("post");
        JSONArray aclJsonArray = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("aclList");
        List<String> aclList = aclJsonArray.toJavaList(String.class);
        JSONArray groupIdJsonArray = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("roleCodeList");
        List<String> roleIdList = groupIdJsonArray.toJavaList(String.class);

        if (StringUtils.isBlank(realName)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.REAL_NAME_IS_EMPTY);
        }
        if (StringUtils.isBlank(userName)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_NAME_IS_EMPTY);
        }
        if (StringUtils.isBlank(post)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.POST_IS_EMPTY);
        }
        if (!CheckVariableUtil.isMobile(phone)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.SUBMITTED_PHONE_IS_WRONG);
        }
        if (StringUtils.isBlank(deptId)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.DEPT_ID_IS_EMPTY);
        }
//        if (CheckVariableUtil.iDCardIsIllegal(idCard)) {
//            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.SUBMITTED_IDCARD_IS_WRONG);
//        }
//        if (!CheckVariableUtil.isEmai(email)) {
//            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.SUBMITTED_EMAIL_IS_WRONG);
//        }
//        if (!CheckVariableUtil.isSexConstants(sex)) {
//            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
//        }

        //查重处理，身份证，手机号,邮箱库里不能重复
        int phoneResult = sysAdminDetailDao.selectCount(new QueryWrapper<SysAdminDetail>().lambda().eq(SysAdminDetail::getPhone, phone));
        if (phoneResult > 0) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_PHONE_ALREADY_EXISTS_DESCRIPTION);
        }
//        int idCardResult = sysAdminDetailDao.selectCount(new QueryWrapper<SysAdminDetail>().lambda().eq(SysAdminDetail::getIdCard, idCard));
//        if (idCardResult > 0) {
//            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_ALREADY_EXISTS_DESCRIPTION);
//        }
//        int emailResult = sysAdminDetailDao.selectCount(new QueryWrapper<SysAdminDetail>().lambda().eq(SysAdminDetail::getEmail, email));
//        if (emailResult > 0) {
//            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.USER_ALREADY_EXISTS_DESCRIPTION);
//        }
        // 角色和权限可以为空
        if (aclList != null) {
            if (!(isHasAcl(aclList))) {
                ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.PERMISSIONS_EXCEED_YOURS);
            }
        }
        if (CollectionUtils.isEmpty(aclList) && CollectionUtils.isEmpty(roleIdList)) {
            ExceptionUtil.setFailureMsgAndThrow(FormBuildReasonOfFailure.THE_PERMISSIONS_CANNOT_BE_EMPTY);
        }
        dataCenterService.setData("realName", realName.trim());
        dataCenterService.setData("userName", userName.trim());
        dataCenterService.setData("aclList", aclList);
        dataCenterService.setData("roleCodeList", roleIdList);
        dataCenterService.setData("phone", phone.trim());
        dataCenterService.setData("post", post.trim());
//        dataCenterService.setData("email", email);
        dataCenterService.setData("deptId", deptId);
//        dataCenterService.setData("sex", sex);
//        dataCenterService.setData("idCard", idCard);

    }

}
