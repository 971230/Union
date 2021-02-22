package com.ztesoft.net.ecsord.params.ecaop.req;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.util.ZjCommonUtils; 

public class BusiHandleBSSReq  extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="查询类型",type="String",isNecessary="Y",desc="1=服务号码2=上网账号")
	private String query_type;
	@ZteSoftCommentAnnotationParam(name="服务号码/上网账号",type="String",isNecessary="Y",desc="服务号码/上网账号")
	private String query_data;
	@ZteSoftCommentAnnotationParam(name="提交模式",type="String",isNecessary="Y",desc="0=预提交1=正式提交2=订单作废3=精准校验")
	private String submit_type = "0";//暂时写死预提交，后续有其他动作再处理
	@ZteSoftCommentAnnotationParam(name="活动编码",type="String",isNecessary="Y",desc="活动编码")
	private String scheme_id;
	@ZteSoftCommentAnnotationParam(name="预提交订单号",type="String",isNecessary="Y",desc="预提交订单号")
	private String pre_submit_orderID;
	@ZteSoftCommentAnnotationParam(name="渠道类型",type="String",isNecessary="Y",desc="渠道类型")
	private String channel_type;
	@ZteSoftCommentAnnotationParam(name="第一发展人",type="String",isNecessary="Y",desc="第一发展人")
	private String developer_first;
	@ZteSoftCommentAnnotationParam(name="第二发展人",type="String",isNecessary="Y",desc="第二发展人")
	private String developer_second;
	@ZteSoftCommentAnnotationParam(name="产品ID",type="String",isNecessary="Y",desc="产品ID")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name="备用1",type="String",isNecessary="Y",desc="packageflow表示订购流量半年包")
	private String standby1;
	@ZteSoftCommentAnnotationParam(name="备用2",type="String",isNecessary="Y",desc="备用1")
	private String standby2;
	@ZteSoftCommentAnnotationParam(name="备用3",type="String",isNecessary="Y",desc="备用3")
	private String standby3;
	@ZteSoftCommentAnnotationParam(name="营业点",type="String",isNecessary="Y",desc="营业点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name="营业员",type="String",isNecessary="Y",desc="营业员")
	private String operator_id;
	/**
	 * Add by Wcl 
	 * out_order_id 增加订单中心的流水号,[可空]
	 * @return
	 */
	@ZteSoftCommentAnnotationParam(name="订单中心的流水号",type="String",isNecessary="Y",desc="订单中心的流水号")
	private String out_order_id;
	
	/*@ZteSoftCommentAnnotationParam(name="是否收费",type="String",isNecessary="Y",desc="是否收费")
	private String is_pay;*/
	
	public String getQuery_type() {
//		//根据订单id查找业务类型
//		String biz_type = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, "biz_type");
//		//如果是宽带续费
//		if ("10004".equals(biz_type)) {
//			return "2";//上网账号
//		} else {
//			return "1";//服务号码
//		}
		return "1";//全部默认服务号码    --songqi 
	}
	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}
	public String getQuery_data() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}
	public void setQuery_data(String query_data) {
		this.query_data = query_data;
	}
	public String getSubmit_type() {
		return submit_type;
	}
	public void setSubmit_type(String submit_type) {
		this.submit_type = submit_type;
	}
	public String getScheme_id() {
		return CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, "bss_code");
	}
	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}
	public String getPre_submit_orderID() {
		return pre_submit_orderID;
	}
	public void setPre_submit_orderID(String pre_submit_orderID) {
		this.pre_submit_orderID = pre_submit_orderID;
	}
	public String getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	public String getDeveloper_first() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String FZNZD_DEVELOP = cacheUtil.getConfigInfo("FZNZD_DEVELOP");
		if(!StringUtil.isEmpty(FZNZD_DEVELOP)&&"yes".equals(FZNZD_DEVELOP)){
			String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
	        String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_CAT_ID);
			String save_terminal_cat_id = cacheUtil.getConfigInfo("save_terminal_cat_id");
	        if ("10093".equals(order_from) && StringUtils.equals(save_terminal_cat_id, goods_cat) ) {
	        	String develop_point_code = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "develop_point_code_new");
	    		if(!StringUtil.isEmpty(develop_point_code)){
	    			developer_first = develop_point_code;
	    		}
	        }
		}
		
		return developer_first;
	}
	public void setDeveloper_first(String developer_first) {
		this.developer_first = developer_first;
	}
	public String getDeveloper_second() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String FZNZD_DEVELOP = cacheUtil.getConfigInfo("FZNZD_DEVELOP");
		if(!StringUtil.isEmpty(FZNZD_DEVELOP)&&"yes".equals(FZNZD_DEVELOP)){
			String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
	        String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_CAT_ID);
			String save_terminal_cat_id = cacheUtil.getConfigInfo("save_terminal_cat_id");
	        if ("10093".equals(order_from) && StringUtils.equals(save_terminal_cat_id, goods_cat) ) {
	        	String develop_code = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "develop_code_new");
	    		if(!StringUtil.isEmpty(develop_code)){
	    			developer_second = develop_code;
	    		}
	        }
		}
		
		return developer_second;
	}
	public void setDeveloper_second(String developer_second) {
		this.developer_second = developer_second;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getStandby1() {
		String type_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, "cat_id");
		if(!StringUtils.isEmpty(type_id)&&type_id.equals("180161053012000830")){
			standby1 = "packageflow";
		}else{
			standby1 = "";
		}
		return standby1;
	}
	public void setStandby1(String standby1) {
		this.standby1 = standby1;
	}
	public String getStandby2() {
		String esn = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "object_esn");
		
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "object_esn");
	}
	public void setStandby2(String standby2) {
		this.standby2 = standby2;
	}
	public String getStandby3() {
		 return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "element_id");
	}
	public void setStandby3(String standby3) {
		this.standby3 = standby3;
	}
	public String getOffice_id() {
//		如果是自传播的泛智能终端来源
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
	    String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_CAT_ID);
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    String save_terminal_cat_id = cacheUtil.getConfigInfo("save_terminal_cat_id");
	    if ("10093".equals(order_from) && StringUtils.isNotEmpty(save_terminal_cat_id) && save_terminal_cat_id.contains(goods_cat)) {
//			取默认值
	    	return ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
	    	
		}
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
	}
	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}
	public String getOperator_id() {
		
//		如果是自传播的泛智能终端来源
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
	    String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_CAT_ID);
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    String save_terminal_cat_id = cacheUtil.getConfigInfo("save_terminal_cat_id");
	    if ("10093".equals(order_from) && StringUtils.isNotEmpty(save_terminal_cat_id) && save_terminal_cat_id.contains(goods_cat)) {
//			取默认值
	    	return ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
	    	
		}
		
		
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	/*public String getIs_pay() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "is_pay");
	}
	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}*/
	
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	public String getOut_order_id() {
		return getNotNeedReqStrOrderId();
	}
	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.busiHandleBSS";
	}

}
