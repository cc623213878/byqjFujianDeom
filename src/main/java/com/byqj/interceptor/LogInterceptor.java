package com.byqj.interceptor;

import com.byqj.dao.SysLogDao;
import com.byqj.dao.SysUserDao;
import com.byqj.entity.SysLog;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.util.IpUtil;
import com.byqj.service.impl.LogCenterService;
import com.byqj.utils.SpringApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    private DataCenterService dataCenterService;
    private LogCenterService logCenterService;
    private SysLogDao sysLogDao;
    private SysUserDao sysUserDao;

    private static final String START_TIME = "requestStartTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        log.info("request start. url:{}", url);
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String url = request.getRequestURI();
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request finished. url:{}, cost:{}", url, end - start);

        if (dataCenterService == null) {
            dataCenterService = SpringApplicationContextUtil.getBean(DataCenterService.class);
        }
        if (logCenterService == null) {
            logCenterService = SpringApplicationContextUtil.getBean(LogCenterService.class);
        }

        if (logCenterService != null && StringUtils.isNotBlank(logCenterService.getOperationResult())) {
            // 从数据中心拿到userID
            String currentUserId = dataCenterService.getCurrentUserId();
            if (sysLogDao == null) {
                sysLogDao = SpringApplicationContextUtil.getBean(SysLogDao.class);
            }
            SysLog sysLog = new SysLog();
            sysLog.setUserId(currentUserId);
            sysLog.setIp(IpUtil.getIpAddr(request));
            sysLog.setOperationContent(logCenterService.getContent());
            sysLog.setOperationResult(logCenterService.getOperationResult());
            sysLog.setReason(logCenterService.getReason());
            try {
                sysLogDao.insertSelective(sysLog);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Log failure ,reason is：{}" + e.getMessage());
            }
            log.info("log success ,log content is:{}" + sysLog.toString());
            logCenterService.remove();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURI();
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request completed. url:{}, cost:{}", url, end - start);
    }
}
