package zte.net.workCustomBean.common;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoUpdateReq;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import consts.ConstsCore;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPayBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class CommonUpdateWaitPayBean extends BasicBusiBean {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	protected IDcPublicInfoManager dcPublicInfoManager; 

	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	public String doBusi() throws Exception{
		return "false";
	}
	
	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断，
	 * 不是后台等待环节的业务处理代码无需实现该方法
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean doBackWaitCheck() throws Exception{
		String json = this.getFlowData().getJson_param();
		
		if(org.apache.commons.lang.StringUtils.isBlank(json))
			return false;
		
		String[] params = json.split("<separator>");
		if(params==null || params.length<2)
			return false;
		
		if(!Const.PAY_RESULT.equals(params[0]))
			//不是支付状态更新接口调用，则等待
			return false;
		
		json = params[1];
		
		JSONObject jsonObj = JSONObject.fromObject(json);
		OrderTreeBusiRequest orderTree = this.getFlowData().getOrderTree();
		String order_id = orderTree.getOrder_id();
		
		//将传入的参数转换为对象
		OrderInfoUpdateReq req = (OrderInfoUpdateReq) JSONObject.toBean(jsonObj, OrderInfoUpdateReq.class);
		
		String pay_result = req.getPay_result(); 
		
		if (StringUtils.equals(pay_result,ZjEcsOrderConsts.MA_PAY_SUCCESS)){
			
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String QUERY_PAY_RESULT = cacheUtil.getConfigInfo("QUERY_PAY_RESULT");
			
			//更新数据库支付状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String[]{AttrConsts.PAY_STATUS}, new String[]{"1"});
			
			//更新订单树支付状态
			OrderBusiRequest OrderBusi = orderTree.getOrderBusiRequest();
			OrderBusi.setPay_status(1);
			OrderBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			OrderBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			OrderBusi.store();
			
			/**
			 * 更新支付时间
			 */
			OrderBusiRequest orderReq = orderTree.getOrderBusiRequest();
			orderReq.setPay_time(Consts.SYSDATE);
			orderReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderReq.store();
			
			String pay_method = req.getPay_method();
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
			String pname = "";
			if (StringUtil.equals("1", orderTree.getOrderExtBusiRequest().getIs_aop())) {
				List<Map> list = dcPublicCache.getList("DIC_PAY_METHOD_CBSS");
		        for(int i=0;i<list.size();i++){
		        	if(StringUtil.equals(pay_method, (String)list.get(i).get("pkey"))){
		        		pname = (String)list.get(i).get("pname");
		        		break;
		        	}
		        }
			}else{
				List<Map> list = dcPublicCache.getList("DIC_PAY_METHOD_BSS");
		        for(int i=0;i<list.size();i++){
		        	if(StringUtil.equals(pay_method, (String)list.get(i).get("pkey"))){
		        		pname = (String)list.get(i).get("pname");
		        		break;
		        	}
		        }
			}
	        if(!StringUtil.isEmpty(pname)){
	        	pay_method = pname;
	        }
	        
	        //更新支付方式
	        OrderPayBusiRequest orderpay = orderTree.getOrderPayBusiRequests().get(0);
	        
			orderpay.setPay_method(pay_method);
			orderpay.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderpay.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderpay.store();
			
			//更新支付流水
			String pay_id = "";
			if(!StringUtils.isEmpty(req.getPay_sequ())){
				pay_id = req.getPay_sequ()+"|";
			}else{
				pay_id = "null"+"|";
			}
			if(!StringUtils.isEmpty(req.getPay_back_sequ())){
				pay_id += req.getPay_back_sequ();
			}else{
				pay_id +="null";
			}
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String[]{AttrConsts.PAY_PLATFORM_ORDER_ID}, new String[]{pay_id});
			String is_pay = req.getIs_pay();
			if(!StringUtils.isEmpty(is_pay)){
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String[]{AttrConsts.IS_PAY}, new String[]{is_pay});
			}
			
			//是否是BSS
			String is_bss = req.getIs_bss();
			if(!StringUtils.isEmpty(is_bss)){
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String[]{"is_bss"}, new String[]{is_bss});
			}
			
			//操作员，操作点
			String new_out_operator  = req.getDeal_operator();
			String new_out_office  = req.getDeal_office_id();
			if(!StringUtils.isEmpty(new_out_office)){
				String old_out_office  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OLD_OUT_OFFICE);
				if(StringUtil.isEmpty(old_out_office)){
					String out_office  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String[]{AttrConsts.OLD_OUT_OFFICE}, new String[]{out_office});
				}
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String[]{AttrConsts.OUT_OFFICE}, new String[]{new_out_office});
			}
			
			if(!StringUtils.isEmpty(new_out_operator)){
				String old_out_operator  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OLD_OUT_OPERATOR);
				if(StringUtil.isEmpty(old_out_operator)){
					String out_operator  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String[]{AttrConsts.OLD_OUT_OPERATOR}, new String[]{out_operator});
				}
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String[]{AttrConsts.OUT_OPERATOR}, new String[]{new_out_operator});
			}
			
			//BSS或CBSS的订单编号
			String cbss_order_id = req.getCbss_order_id();
			String bss_order_id = req.getBss_order_id();
			if(!StringUtil.isEmpty(cbss_order_id)||!StringUtil.isEmpty(bss_order_id)){
				OrderItemExtBusiRequest itemsExt = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
				if(!StringUtil.isEmpty(cbss_order_id)){
					itemsExt.setActive_no(cbss_order_id);
				}
				if(!StringUtil.isEmpty(bss_order_id)){
					itemsExt.setBssOrderId(bss_order_id);
					itemsExt.setActive_no(bss_order_id);
				}
				itemsExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				itemsExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				itemsExt.store();
			}
		}else if(StringUtils.equals(pay_result,ZjEcsOrderConsts.MA_PAY_FAIL)){
			logger.error(order_id+"订单支付更新失败");
			throw new Exception(order_id+"订单支付更新失败");
		}
		
		return true;
	}
}
