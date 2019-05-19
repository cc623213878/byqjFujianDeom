package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.SysAclDao;
import com.byqj.entity.SysAcl;
import com.byqj.service.ISysAclService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "sysAclService")
public class SysAclServiceImpl extends ServiceImpl<SysAclDao, SysAcl> implements ISysAclService {

    @Override
    public List<SysAcl> selectModuleIdByAclCodes(List<String> aclCodes) {
        return list(new LambdaQueryWrapper<SysAcl>().in(SysAcl::getCode, aclCodes));
    }
}
