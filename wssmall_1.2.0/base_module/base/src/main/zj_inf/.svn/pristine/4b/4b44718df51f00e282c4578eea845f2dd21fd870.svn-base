package zte.net.ecsord.params.taobao.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.taobao.vo.LogisticsTAOBAOOnline;
import zte.net.ecsord.params.taobao.vo.LogisticsTAOBAOVirtual;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author sguo 
 * 订单信息同步:订单系统调用该接口将订单备注信息同步到淘宝
 * 
 */
public class SynchronousOrderTBRequset extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="淘宝交易ID",type="String",isNecessary="Y",desc="tid：淘宝交易ID")
	private String tid;

	@ZteSoftCommentAnnotationParam(name="在线订单发货",type="String",isNecessary="Y",desc="memo：虚拟发货")
	private String memo;

	@ZteSoftCommentAnnotationParam(name="卖家交易备注旗帜",type="String",isNecessary="Y",desc="flag:可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，默认值为0支持最大值为：5支持最小值为：0")
	private String flag;

	@ZteSoftCommentAnnotationParam(name="是否对memo的值置空",type="String",isNecessary="Y",desc="reset:若为true，则不管传入的memo字段的值是否为空，都将会对已有的memo值清空，慎用； 若用false，则会根据memo是否为空来修改memo的值：若memo为空则忽略对已有memo字段的修改，若memo非空，则使用新传入的memo覆盖已有的memo的值")
	private String reset;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	public String getTid() {
		tid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_TID);
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getMemo() {
		memo = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SELLER_MESSAGE);
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getReset() {
		return reset;
	}

	public void setReset(String reset) {
		this.reset = reset;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.taobao.SynchronousOrder";
	}

}
