package zte.net.ecsord.params.zb.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.zb.vo.Goods;

public class NotifyStringGDRequest extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="OrderId：订单编号")
	private String OrderId;
	
	@ZteSoftCommentAnnotationParam(name="操作人信息",type="String",isNecessary="N",desc="OperatorInfo：操作人信息")
	private String OperatorInfo;
	
	@ZteSoftCommentAnnotationParam(name="顺序号",type="String",isNecessary="N",desc="ACCOUNT_INDEX：顺序号")
	private String ACCOUNT_INDEX;
	
	@ZteSoftCommentAnnotationParam(name="节点",type="List",isNecessary="N",desc="GoodsInfo：节点")
	private List<Goods> GoodsInfo;

	public String getActiveNo() {
		return ActiveNo;
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getOperatorInfo() {
		return OperatorInfo;
	}

	public void setOperatorInfo(String operatorInfo) {
		OperatorInfo = operatorInfo;
	}

	public String getACCOUNT_INDEX() {
		return ACCOUNT_INDEX;
	}

	public void setACCOUNT_INDEX(String aCCOUNT_INDEX) {
		ACCOUNT_INDEX = aCCOUNT_INDEX;
	}

	public List<Goods> getGoodsInfo() {
		return GoodsInfo;
	}

	public void setGoodsInfo(List<Goods> goodsInfo) {
		GoodsInfo = goodsInfo;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.NotifyStringGD";
	}

}
