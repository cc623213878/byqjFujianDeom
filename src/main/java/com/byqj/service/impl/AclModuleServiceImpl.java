package com.byqj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.SysAclModuleDao;
import com.byqj.entity.SysAclModule;
import com.byqj.service.IAclModuleService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service(value = "aclModuleService")
public class AclModuleServiceImpl extends ServiceImpl<SysAclModuleDao, SysAclModule>
        implements IAclModuleService {
    @Override
    public List<SysAclModule> searchByIdsAndType(Collection<Long> ids, int type) {
        return list(new LambdaQueryWrapper<SysAclModule>().eq(SysAclModule::getType, type).in(SysAclModule::getId, ids));
    }
}
