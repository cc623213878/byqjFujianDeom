package com.byqj.module.common.importStudentInfo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byqj.dao.SysAdminDetailDao;
import com.byqj.dao.SysDepartmentDao;
import com.byqj.dao.TssSolicitationTimeDao;
import com.byqj.entity.SysAdminDetail;
import com.byqj.entity.SysDepartment;
import com.byqj.entity.TssSolicitationTime;
import com.byqj.module.common.importStudentInfo.enums.ImportStudentInfoReasonOfFailure;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImportStudentInfoCheckService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private TssSolicitationTimeDao tssSolicitationTimeDao;
    @Autowired
    private SysAdminDetailDao adminDetailDao;
    @Autowired
    private SysDepartmentDao departmentDao;

    public void postInfoRequestProcessCheck() {
        String teId = dataCenterService.getData("teId");
        TssSolicitationTime tssSolicitationTime = tssSolicitationTimeDao.selectOne(new LambdaQueryWrapper<TssSolicitationTime>().eq(TssSolicitationTime::getTeId, teId));

        String currentUserId = dataCenterService.getCurrentUserId();
        SysAdminDetail adminDetail = adminDetailDao.selectById(currentUserId);
        SysDepartment department = departmentDao.selectById(adminDetail.getDeptId());

        if (tssSolicitationTime.getDeptId() == adminDetail.getDeptId()) {
            Date date = new Date();
            Date startTime = tssSolicitationTime.getStudentTimeStart();
            Date endTime = tssSolicitationTime.getStudentTimeEnd();

            if (startTime.after(date) || endTime.before(date)) { //开始时间比当前时间晚，结束时间比当前时间早
                ExceptionUtil.setFailureMsgAndThrow(ImportStudentInfoReasonOfFailure.NOT_IN_TIME);
            }
        }

        //外来的部门即人员库的部门
        if (department.getType() == 1 && tssSolicitationTime.getDeptId() != adminDetail.getDeptId()) {
            ExceptionUtil.setFailureMsgAndThrow(ImportStudentInfoReasonOfFailure.UNSPECIFIED_DEPARTMENT_CANNOT_UPLOAD);
        }

    }
}
