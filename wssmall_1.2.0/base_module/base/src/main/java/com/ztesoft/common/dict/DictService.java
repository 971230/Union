package com.ztesoft.common.dict;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.buffalo.BaseService;

public class DictService extends BaseService{
	//	处理Action (配置java bean使用)
	public DynamicDict getServiceDTO(String actionName , String methodName ) {
		DynamicDict dto = new DynamicDict(1);// 1：JSP访问；0：BHO访问
		dto.flag = 0;// 1:Action;0:Service
		
		dto.m_ActionId = actionName ;
		try {
			dto.setValueByName("execMethod", methodName) ;
		} catch (FrameException e) {
			e.printStackTrace();
		}
		return dto ;
	}
	
	//处理Action (配置SQL使用)
	public  DynamicDict getActionDTO(String actionName ) {
		DynamicDict dto = new DynamicDict(1);// 1：JSP访问；0：BHO访问
		dto.flag = 1;// 1:Action;0:Service
		dto.m_ActionId = actionName ;
		return dto ;
	}
	/**
     * 获取全局变量(登陆信息)
     *
     * @param strGlobalName
     * @return
     * @throws Exception
     */
//    public String getGlobalVar(String strGlobalName) throws Exception {
//        //GlobalVariableHelper helper = new GlobalVariableHelper(getRequest());
////    	GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() );
////        String strGlobal = helper.getVariable(strGlobalName);
////        return strGlobal==null?"":strGlobal;
//    	return "";
//    }
}
