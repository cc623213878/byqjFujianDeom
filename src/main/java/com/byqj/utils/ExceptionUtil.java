/**
 * @Title: ExceptionUtil.java  
 * @Package cn.edu.fjnu.towide.utils
 * @author CaoZhengxi
 * @date 2018年7月24日
 */  
package com.byqj.utils;

import com.byqj.exception.RequestFailureException;
import com.byqj.security.core.support.DataCenterService;
import com.byqj.security.core.support.ResponseData;
import com.byqj.security.core.support.enums.IReasonOfFailure;
import com.byqj.security.core.support.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName: ExceptionUtil
 * @author CaoZhengxi
 * @date 2018年7月24日
 *    
 */
@Component
public class ExceptionUtil {

	@Autowired
	private DataCenterService dataCenter;
	private static DataCenterService dataCenterService;

	@PostConstruct
	public void init() {
        dataCenterService = dataCenter;
    }

    /**
	 * 抛出自定义异常原因
     */
    public static RequestFailureException setFailureMsgAndThrow(IReasonOfFailure reasonOfFailure) {
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        RequestFailureException requestFailureException = new RequestFailureException();
		requestFailureException.setResponseData(responseData);

        ResponseDataUtil.setResponseDataWithFailureInfo(responseData, reasonOfFailure);
		throw requestFailureException;
	}


}
