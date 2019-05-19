package com.byqj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.byqj.constant.LogConstant;
import org.springframework.stereotype.Service;

@Service
public class LogCenterService {

    private ThreadLocal<JSONObject> logHolder = new ThreadLocal<>();

    private <T> void setLogData(String dataName, T dataValue) {
        JSONObject data = logHolder.get();
        if (data==null){
            this.initLogCenter();
            data = logHolder.get();
        }
        data.put(dataName, dataValue);
    }

    private <T> T getLogData(String dataName) {
        JSONObject data = logHolder.get();
        if (data==null){
            this.initLogCenter();
            data = logHolder.get();
        }
        T tempData = (T) data.get(dataName);
        return tempData;
    }


    private void setLogType(String logType) {
        this.setLogData(LogConstant.LOG_LOG_TYPE, logType);
    }

    private void initLogCenter(){
        JSONObject data=new JSONObject();
        this.logHolder.set(data);
    }

    /**
     * 设置操作结果为 成功
     */
    public void setResultIsTrue() {
        this.setLogData(LogConstant.LOG_RESULT, LogConstant.LOG_LOG_RESULT_IS_TRUE);
    }

    /**
     * 设置操作结果为 失败
     */
    public void setResultIsFalse() {
        this.setLogData(LogConstant.LOG_RESULT, LogConstant.LOG_LOG_RESULT_IS_FALSE);
    }

    /**
     * 设置失败理由
     * @param reason
     */
    public void setReason(String reason) {
        this.setLogData(LogConstant.LOG_REASON, reason);
    }

    /**
     * 设置操作内容
     * @param content
     */
    public void setContent(String content) {
        this.setLogData(LogConstant.LOG_CONTENT, content);
    }





    public String getOperationResult() {
        return this.getLogData(LogConstant.LOG_RESULT);
    }

    public String getContent() {
        return this.getLogData(LogConstant.LOG_CONTENT);
    }

    public String getReason() {
        return this.getLogData(LogConstant.LOG_REASON);
    }


    public void remove() {
        this.logHolder.remove();
    }
}
