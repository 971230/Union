package zte.net.card.params.req;

import params.ZteRequest;
import zte.net.card.params.resp.QueryQueueAlowCountResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @Description 查询队列允许插入数量值
 * @author  zhangJun
 * @date    2017年3月17日
 * @version 1.0
 */
public class QueryQueueAlowCountReq extends ZteRequest<QueryQueueAlowCountResp> {

	private static final long serialVersionUID = 1L;
	


	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.card.queryQueueAlowCount";
	}

	
	
}
