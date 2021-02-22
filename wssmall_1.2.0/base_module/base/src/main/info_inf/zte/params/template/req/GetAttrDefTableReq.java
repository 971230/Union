package zte.params.template.req;

import params.ZteRequest;
import zte.params.template.resp.GetAttrDefTableResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 模板转出入参
 * @author xuefeng
 */
public class GetAttrDefTableReq extends ZteRequest<GetAttrDefTableResp>{
	
	@Override
	@NotDbField
	public String getApiMethodName() {
		return  "zte.net.iservice.template.getAttrDefTableReq";
	}

	/**
	 * Description: TODO(用一句话描述该文件做什么)  
	 * @throws ApiRuleException 
	 * @see params.ZteRequest#check()
	 * @author zhouqiangang 
	 * @date 2015年12月4日 上午11:25:44 
	 */
	@Override
	public void check() throws ApiRuleException {
		// TODO 自动生成的方法存根
		
	}


	
}
