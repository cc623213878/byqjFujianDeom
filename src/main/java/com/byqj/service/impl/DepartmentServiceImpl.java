package com.byqj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byqj.dao.SysDepartmentDao;
import com.byqj.entity.SysDepartment;
import com.byqj.service.IDepartmentService;
import org.springframework.stereotype.Service;

/**
 * @ClassName:DepartmentServicelmpl
 * @Description:
 * @Author:lwn
 * @Date:2019/3/22 16:05
 **/

@Service(value = "departmentService")
public class DepartmentServiceImpl extends ServiceImpl<SysDepartmentDao, SysDepartment>
        implements IDepartmentService {

}