package com.byqj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byqj.entity.SysAcl;

import java.util.List;

public interface ISysAclService extends IService<SysAcl> {
    List<SysAcl> selectModuleIdByAclCodes(List<String> aclCodes);
}
