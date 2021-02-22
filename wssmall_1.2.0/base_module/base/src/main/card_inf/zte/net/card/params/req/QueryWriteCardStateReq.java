package zte.net.card.params.req;

import params.ZteRequest;
import zte.net.card.params.resp.QueryWriteCardStateResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 订单模块请求写卡机dubbo服务读取ICCID值。
 * @Description
 * @author  zhangJun
 * @date    2017年3月2日
 * @version 1.0
 */
public class QueryWriteCardStateReq extends ZteRequest<QueryWriteCardStateResp> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.card.writeCardStateQuery";
	}

}
