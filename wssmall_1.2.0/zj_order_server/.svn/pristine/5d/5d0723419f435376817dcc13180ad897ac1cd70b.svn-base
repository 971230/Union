package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.utils.SpecUtils;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.CheckAcctConfig;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.CheckAcctLog;
import com.ztesoft.net.model.OrderItemAgentMoney;
import com.ztesoft.net.model.PayAssureVo;
import com.ztesoft.net.service.IAgentMoneyManager;
import com.ztesoft.net.service.IPayAssureManager;
import com.ztesoft.net.sqls.SF;

/**
 * @Description
 * @author  zhangJun
 * @date    2015-1-23
 * @version 1.0
 */
public class PayAssureManager extends BaseSupport implements IPayAssureManager {


    
	/**
	 * 从历史表和现用表中查询:
	 * 来源是百度and 昨天退单and支付方式为担保免支付and 支付机构id是payProviderid的订单
	 * payProviderid-支付机构id
	 */
	@Override
	public List<PayAssureVo> YesterdayPayAssureList(String payProviderid) {
		String sql = getQueryListSql(payProviderid);//百度
		List<Map> list = this.baseDaoSupport.queryForList(sql);
		
		List<PayAssureVo> listPay=new ArrayList<PayAssureVo>();
		if(list!=null){
			for (Map map : list) {
				String order_id=Const.getStrValue(map, "order_id");
				String is_his=Const.getStrValue(map, "is_his");
				if(EcsOrderConsts.IS_ORDER_HIS_YES.equals(is_his)){//历史单
					String mobileTel=CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.PHONE_NUM);
					if(!StringUtil.isEmpty(mobileTel)){
						PayAssureVo pay=new PayAssureVo();
						pay.setMobileTel(mobileTel);
						pay.setCardNo(CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.CERT_CARD_NUM));
						pay.setMobileNet(CommonDataFactory.getInstance().getNumberSpec(mobileTel, SpecConsts.NUMERO_NO_GEN));//2G、4G、3G
						pay.setBaiduId(CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.BAIDU_ID));
						pay.setSerialNumber(CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.FREEZE_TRAN_NO));
						pay.setFrozenSection(CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.FREEZE_FEE));//
						pay.setOrderId(CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.OUT_TID));
						listPay.add(pay);
					}
				}else{
					String mobileTel=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
					if(!StringUtil.isEmpty(mobileTel)){
						PayAssureVo pay=new PayAssureVo();
						pay.setMobileTel(mobileTel);
						pay.setCardNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM));
						pay.setMobileNet(CommonDataFactory.getInstance().getNumberSpec(mobileTel, SpecConsts.NUMERO_NO_GEN));//2G、4G、3G
						pay.setBaiduId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BAIDU_ID));
						pay.setSerialNumber(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.FREEZE_TRAN_NO));
						pay.setFrozenSection(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.FREEZE_FEE));//
						pay.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
						listPay.add(pay);
					}
				}
				
				
			}
		}
		return listPay;
	}

	@Override
	public List<CheckAcctConfig> getCheckConfig(String systemName) {
		String sql = SF.ecsordSql("CheckAccConfigBySystemName");
		List<CheckAcctConfig> list = this.baseDaoSupport.queryForList(sql, CheckAcctConfig.class,systemName);
		if(list==null){
			list =new ArrayList<CheckAcctConfig>();
		}
		return list;
	}
    /**
     * 生成sql
    * @Description: 
    * @return String    
    * @throws
     */
	public String getQueryListSql(String payProviderid){
		
		//sql条件部分
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where a.refund_deal_type ='"+EcsOrderConsts.REFUND_DEAL_TYPE_02+"'");
		whereSql.append(" and a.pay_type='"+EcsOrderConsts.PAY_TYPE_DBZF+"'");
		whereSql.append(" and a.refund_status in ('"+EcsOrderConsts.REFUND_STATUS_04+"','"+EcsOrderConsts.REFUND_STATUS_05+"','"+EcsOrderConsts.REFUND_STATUS_06+"','"+EcsOrderConsts.REFUND_STATUS_07+"')");
		whereSql.append(" and (a.refund_time>=trunc(sysdate-1) and a.refund_time<trunc(sysdate))");
		whereSql.append(" and a.source_from='"+ManagerUtils.getSourceFrom()+"'");
	
		//汇总
		StringBuilder sql = new StringBuilder(" select a.order_id,'0' is_his  from es_order_ext a ");
		sql.append(whereSql);
		sql.append(" and (select l.payproviderid  from es_order_extvtl l where  l.order_id=a.order_id  ) ='"+payProviderid+"' ");
		
		sql.append(" union all");
		sql.append(" select a.order_id,'1' is_his  from es_order_ext_his a ");
		sql.append(whereSql);
		sql.append(" and (select l.payproviderid  from es_order_extvtl_his l where  l.order_id=a.order_id  ) ='"+payProviderid+"' ");
		
		
		return sql.toString();
		
	}
	
}
