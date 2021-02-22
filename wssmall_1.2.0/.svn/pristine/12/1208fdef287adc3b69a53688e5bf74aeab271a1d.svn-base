package com.ztesoft.paas.filter;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import params.DubboParam;
import params.ZteRequest;
import utils.CoreThreadLocalHolder;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * Created with IntelliJ IDEA.
 * User: wu.i
 * Date: 2014-01-24 09:42
 * To change this template use File | Settings | File Templates.
 */
@Activate(group = "consumer")
public class DubboAppCustomerFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    	
    	/**
    	 * 1、远程调用前需要将source_from,app_key设置到zterequest对象中去。避免深度dubbo调用时zterequest source_from,app_key丢失的情况。dubbo消费前会调用
    	 */
    	
//    	logger.info(Thread.currentThread().getId()+"DubboAppCustomerFilter");
    	Result result =null;
        logger.info("获取来源信息:{}", invocation.getArguments());
        try{
        	setDubboParam(invocation);
	        result = invoker.invoke(invocation);
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	for (Object obj : invocation.getArguments()) {
                if (obj instanceof ZteRequest) {
		        	CoreThreadLocalHolder.getInstance().getZteCommonData().removeDubboParam();
		        	
		        	//删除完成后还是基数，说明有异常，全部删除
		        	Stack<DubboParam> dubboParams = CoreThreadLocalHolder.getInstance().getZteCommonData().getDubboParams();
		        	if(dubboParams.size()%2!=0)
		        		CoreThreadLocalHolder.getInstance().getZteCommonData().clearDubboParam();
               }
        	}
        }
        
        return result;
    }
    
    public void setDubboParam(Invocation invocation){
    	for (Object obj : invocation.getArguments()) {
            if (obj instanceof ZteRequest) {
                ZteRequest zteRequest = (ZteRequest)obj;
                //首次调用两个值肯定不为空
                DubboParam dubboParam = new DubboParam();
                if(!StringUtils.isEmpty(zteRequest.getSourceFrom()) && !StringUtils.isEmpty(zteRequest.getAppKey())){ //第一次跳转
	               	 dubboParam.setApp_key(zteRequest.getAppKey());
	               	 dubboParam.setSource_from(zteRequest.getSourceFrom());
                }else{
                	dubboParam = CoreThreadLocalHolder.getInstance().getZteCommonData().getDubboParam();
                	if (dubboParam != null && !StringUtils.isEmpty(dubboParam.getSource_from()) 
                			&& !StringUtils.isEmpty(dubboParam.getApp_key())) {
                		
	                	zteRequest.setSourceFrom(dubboParam.getSource_from());
	                	zteRequest.setAppKey(dubboParam.getApp_key());
                	}
                }
                
                if (dubboParam != null && !StringUtils.isEmpty(dubboParam.getApp_key()) 
                		&& !StringUtils.isEmpty(dubboParam.getSource_from())) {
                	
	                	CoreThreadLocalHolder.getInstance().getZteCommonData().setDubboParam(dubboParam); //设置到消费的线程变量中去
                }
            }
    	}
    }
}

