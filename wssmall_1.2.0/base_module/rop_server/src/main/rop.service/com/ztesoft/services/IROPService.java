package com.ztesoft.services;

//import com.ztesoft.common.util.BeanUtils;
//import com.ztesoft.common.util.ContextHelper;
//import com.ztesoft.ibss.common.service.CommonService;
import com.ztesoft.parameters.AbstractRopRequest;

import java.util.HashMap;

/**
 * @author Reason.Yea 
 * @version 创建时间：May 20, 2013
 */
public abstract class IROPService {
    protected final String APPKEY = "appKey";
    protected final String OWNER_ID = "ownerId";
    protected final String OWNER_NAME = "ownerName";
    protected final String ACCT_TYPE = "acctType";

	/**
	 * @param req ROP请求参数
	 * @param retClass ROP返回对象Class,如果为null，则返回Map/List对象
	 * @param mod  业务模块
	 * @param met  模块方法
	 * @return 返回对象
	 */
	public Object excute(AbstractRopRequest req, Class retClass, String mod,String met){
		/**
		HashMap reqMap = (HashMap) BeanUtils.beanToMap(req);
		HashMap resMap = null;
		try {
			HttpServletRequest servletRequest = (HttpServletRequest) req.getRopRequestContext().getRawRequestObject();
			ContextHelper.setRequest(servletRequest, null);
			reqMap.put("MOD", mod);
			reqMap.put("MET", met);
			reqMap.put("opn_app_code", req.getRopRequestContext().getAppKey());
			CommonService service = new CommonService();
			resMap =(HashMap) service.excute("SERV", false, reqMap).get("result");
		} catch (Exception e) {
			return resMap;
		}
		
		if(retClass==null)return resMap;
		
		Object res =  BeanUtils.mapToBean(resMap,retClass.getName());
		return res;
		*
		*/
		return new HashMap() ;
	}
}
