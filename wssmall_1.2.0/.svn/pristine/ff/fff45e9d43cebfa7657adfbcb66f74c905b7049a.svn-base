package com.ztesoft.common.dict;

import java.util.Map;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;

/**
 * 
 * @author easonwu 2009-01-05 
 * 服务管理类，屏蔽Dict等繁琐getter/setter等，简化程序调用
 *
 */
public class ServiceManager {
	
	public static final int SERVICE_SQL = 1 ;
	public static final int SERVICE_JAVABEAN = 0 ;
	
//	处理Action (配置java bean使用)
	public static  DynamicDict getServiceDTO(String actionName , String methodName ) {
		DynamicDict dto = new DynamicDict(1);// 1：JSP访问；0：BHO访问
		dto.flag = SERVICE_JAVABEAN;// 1:Action;0:Service
		
		dto.m_ActionId = actionName ;
		try {
			dto.setValueByName("execMethod", methodName) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto ;
	}
	
	//处理Action (配置SQL使用)
	public static  DynamicDict getActionDTO(String actionName ) {
		DynamicDict dto = new DynamicDict(1);// 1：JSP访问；0：BHO访问
		dto.flag = SERVICE_SQL;// 1:Action;0:Service
		dto.m_ActionId = actionName ;
		return dto ;
	}
	
	private ServiceManager(){
		
	}
	/**
	 * 简化调用方式：JavaBean式
	 * @param service
	 * @param method
	 * @param param
	 * @return
	 * @throws Exception
	 * @author easonwu 2010-01-05
	 */
	public static Object callJavaBeanService(String service, String method , Map param ) throws Exception{
		DynamicDict dto = getServiceDTO(service ,method) ;
		dto.setValueByName(Const.ACTION_PARAMETER, param) ;
		ActionDispatch.dispatch(dto);
		return dto.getValueByName(Const.ACTION_RESULT) ;
	}
	
	/**
	 * 简化调用方式：SQL式
	 * @param service
	 * @param method
	 * @param param
	 * @return
	 * @throws Exception
	 * @author easonwu 2010-01-05
	 */
	public static Object callSQLService(String service , Map param ) throws Exception{
		DynamicDict dto = getActionDTO(service) ;
		dto.setValueByName(Const.ACTION_PARAMETER, param) ;
		ActionDispatch.dispatch(dto);
		return dto.m_Values.get(service);
	}

	/**
	 * 调用SQL方式，返回值为DTO，可获取DTO中其它信息，如错误提示信息等
	 * @param service
	 * @param param
	 * @return
	 * @throws Exception
	 * @author easonwu 2010-01-11
	 */
	public static DynamicDict callSQLServiceReturnWithDto(String service , Map param ) throws Exception{
		DynamicDict dto = getActionDTO(service) ;
		dto.setValueByName(Const.ACTION_PARAMETER, param) ;
		ActionDispatch.dispatch(dto);
		return dto;
	}
	
	/**
	 * javabean方式调用，返回值为DTO，可获取DTO中其它信息，如错误提示信息等
	 * @param service
	 * @param method
	 * @param param
	 * @return
	 * @throws Exception
	 * @author easonwu 2010-01-11
	 * 
	 */
	public static DynamicDict callJavaBeanServiceReturnWithDto(String service, String method , Map param ) throws Exception{
		DynamicDict dto = getServiceDTO(service ,method) ;
		dto.setValueByName(Const.ACTION_PARAMETER, param) ;
		ActionDispatch.dispatch(dto);
		return dto ;
	}

}
