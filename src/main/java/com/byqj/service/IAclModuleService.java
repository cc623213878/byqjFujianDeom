package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.SysAclModule;

import java.util.Collection;
import java.util.List;

public interface IAclModuleService extends IService<SysAclModule> {
    List<SysAclModule> searchByIdsAndType(Collection<Long> ids, int type);
}
