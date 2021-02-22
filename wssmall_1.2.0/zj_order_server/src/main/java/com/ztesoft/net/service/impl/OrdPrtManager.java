package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemsAptPrintsBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AcceptPrintModel;
import com.ztesoft.net.model.OrderQueryParams;
import com.ztesoft.net.model.PrintOrderTree;
import com.ztesoft.net.service.IOrdPrtManager;

import consts.ConstsCore;

public class OrdPrtManager extends BaseSupport implements IOrdPrtManager {
	@Resource
	protected IDcPublicInfoManager dcPublicInfoManager;
	
	/**
	 * 票据打印列表
	 */
	@Override
	public Page qryPrtOrdList(int pageNo, int pageSize, OrderQueryParams params) {
		boolean isHis=false;
		String his = "_his";
		String order_table = "es_order";
		String order_ext_table = "es_order_ext";
		String es_delivery = "es_delivery";
		String es_order_items_ext_table = "es_order_items_ext";
		String es_order_items_table = "es_order_items";
		String es_payment_logs_table="es_payment_logs";
		
		
		if(params!=null&&EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())){//历史单
			 isHis=true;
			 order_table = order_table+his;
			 order_ext_table = order_ext_table+his;
			 es_delivery = es_delivery+his;
			 es_order_items_ext_table = es_order_items_ext_table+his;
			 es_order_items_table = es_order_items_table+his;
			 es_payment_logs_table=es_payment_logs_table+his;
			
		}
		
		
		
		
		StringBuilder sql = new StringBuilder("select o.* from "+order_table+" o,"+order_ext_table+" oe,es_member em");
		sql.append(" where o.order_id=oe.order_id and o.source_from=oe.source_from and o.source_from=em.source_from");
		sql.append(" and o.member_id=em.member_id and o.source_from=?");
		List sqlParams = new ArrayList();
		sqlParams.add(ManagerUtils.getSourceFrom());
//		private String out_tid;//外单编号 ES_ORDER_EXT->ES_ORDER_EXT
		if(!StringUtil.isEmpty(params.getOut_tid())){
			sql.append(" and oe.out_tid=?");
			sqlParams.add(params.getOut_tid());
		}
//		private String order_id;//内部单号 es_order->order_id
		if(!StringUtil.isEmpty(params.getOrder_id())){
			sql.append(" and o.order_id=?");
			sqlParams.add(params.getOrder_id());
		}
//		private String phone_num; // 开户号码
		if(StringUtils.isNotBlank(params.getPhone_num())){
			sql.append(" and o.order_id in (select t.order_id from "+es_order_items_ext_table+" t where t.phone_num=?)");
			sqlParams.add(params.getPhone_num());
		}
		//private String shipping_id;//配送方式 es_order->shipping_id （修改：配送方式应该是shipping_type）
		if(!StringUtil.isEmpty(params.getShipping_id()) && !"-1".equals(params.getShipping_id())){
			sql.append(" and o.shipping_type =? ");
			sqlParams.add(params.getShipping_id());
		}
		if(!StringUtil.isEmpty(params.getCreate_start())){
			sql.append(" and oe.tid_time>="+DBTUtil.to_sql_date("?", 2));
			sqlParams.add(params.getCreate_start());
		}
//		private String create_end;//创建时间 ES_ORDER_EXT->tid_time
		if(!StringUtil.isEmpty(params.getCreate_end())){
			sql.append(" and oe.tid_time<="+DBTUtil.to_sql_date("?", 2));
			sqlParams.add(params.getCreate_end());
		}
		String countSql = "select count(*) from ("+sql.toString()+") tab";
		Page page  = this.baseDaoSupport.queryForCPage(sql.toString(), pageNo, pageSize, OrderBusiRequest.class, countSql, sqlParams.toArray());
		if(page==null)return page;
		List<OrderBusiRequest> list = page.getResult();
		if(list==null)return page;
		List<PrintOrderTree> tree = new ArrayList<PrintOrderTree>();
		for(OrderBusiRequest ob:list){	
			OrderTreeBusiRequest orderTree= null;
//			try{
			if(params!=null&&EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())){//历史单
				try {
					orderTree = CommonDataFactory.getInstance().getOrderTreeHis(ob.getOrder_id());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}else{
				orderTree = CommonDataFactory.getInstance().getOrderTree(ob.getOrder_id());
			}
//			}catch(Exception ex){
//				try{
//					OrderTreeBusiRequest orderTreeBusiRequest = new OrderTreeBusiRequest();
//					orderTreeBusiRequest.setOrder_id(ob.getOrder_id());
//					orderTreeBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
//					orderTreeBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
//					orderTreeBusiRequest.setCreate_time(Consts.SYSDATE);
//					orderTreeBusiRequest.setUpdate_time(Consts.SYSDATE);
//					orderTreeBusiRequest.store();
//					CommonDataFactory.getInstance().updateOrderTree(ob.getOrder_id());
//					orderTree = CommonDataFactory.getInstance().getOrderTree(ob.getOrder_id());
//				}catch(Exception e){
//					
//				}
//			}
			
			//获取流程页面访问地址
			if(orderTree!=null){
				String action_url = null;
				DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		        List<Map> n_list = dcPublicCache.getList(EcsOrderConsts.DIC_ORDER_NODE);
		        String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		        for(Map m:n_list){
		        	String t_id = (String) m.get("pkey");
		        	if(t_id!=null && t_id.equals(trace_id)){
		        		action_url = (String) m.get("codeb");
		        		break ;
		        	}
		        }
		        String Order_from_c = null;
		        List<Map> sourceFrom = dcPublicCache.getList(EcsOrderConsts.SOURCE_FROM);
		        String ofrom = orderTree.getOrderExtBusiRequest().getOrder_from();
				if(!StringUtil.isEmpty(ofrom)){
					for(Map s:sourceFrom){
						String key = (String) s.get("pkey");
						if(key!=null && key.equals(ofrom)){
							Order_from_c=(String)s.get("pname");
							
							break ;
						}
					}
				}
		        String phone_num= "";
				String terminal ="";
				String goods_package = "";
		        if(params!=null&&EcsOrderConsts.IS_ORDER_HIS_YES.equals(params.getOrder_is_his())){//历史单
		        	 phone_num= CommonDataFactory.getInstance().getAttrFieldValueHis(ob.getOrder_id(), AttrConsts.PHONE_NUM);
					 terminal = CommonDataFactory.getInstance().getProductSpecHis(ob.getOrder_id(), SpecConsts.TYPE_ID_10000, null, SpecConsts.GOODS_NAME);
					 goods_package = CommonDataFactory.getInstance().getAttrFieldValueHis(ob.getOrder_id(), "plan_title");
		        }else{
		        	 phone_num= CommonDataFactory.getInstance().getAttrFieldValue(ob.getOrder_id(), AttrConsts.PHONE_NUM);
					 terminal = CommonDataFactory.getInstance().getProductSpec(ob.getOrder_id(), SpecConsts.TYPE_ID_10000, null, SpecConsts.GOODS_NAME);
					 goods_package = CommonDataFactory.getInstance().getAttrFieldValue(ob.getOrder_id(), "plan_title");
		        }
				
				PrintOrderTree ot = new PrintOrderTree();
				ot.setOrder_from_c(Order_from_c);
				ot.setOrderTree(orderTree);
				ot.setAction_url(action_url);
				ot.setPhone_num(phone_num);
				ot.setGoods_package(goods_package);
				ot.setTerminal(terminal);
				
				OrderItemsAptPrintsBusiRequest ordItemsAptPrtBusiReq = getOrdItem(orderTree,isHis);
				ot.setAcceptance_html(ordItemsAptPrtBusiReq.getAcceptance_html());
				ot.setReceipt_code(ordItemsAptPrtBusiReq.getReceipt_code());
				ot.setReceipt_mode(ordItemsAptPrtBusiReq.getReceipt_mode());
				ot.setStatus(ordItemsAptPrtBusiReq.getStatus());
				if(orderTree.getOrderDeliveryBusiRequests()!=null&&orderTree.getOrderDeliveryBusiRequests().size()>0&&orderTree.getOrderDeliveryBusiRequests().get(0).getShip_status()!=null){
					ot.setShip_status(orderTree.getOrderDeliveryBusiRequests().get(0).getShip_status());//发货状态
				}
				tree.add(ot);
			}
		}
		page.setResult(tree);
		return page;
	}
	
	private OrderItemsAptPrintsBusiRequest getOrdItem(OrderTreeBusiRequest orderTree,boolean isHis){
	
		String es_order_items_apt_prints_table = "es_order_items_apt_prints";
		if(isHis){
			es_order_items_apt_prints_table=es_order_items_apt_prints_table+"_his";
		}
		OrderItemsAptPrintsBusiRequest ordItemsAptPrtBusiReq = new OrderItemsAptPrintsBusiRequest();
//		if(orderTree.getOrder_id().equals("MZ201410296061860862")){
//			int i=0;
//			i++;
//		}
		List<OrderItemBusiRequest> list = orderTree.getOrderItemBusiRequests();
		if(list.size()>0){
			OrderItemBusiRequest ordItem = list.get(0);
			List<OrderItemsAptPrintsBusiRequest> orderItemsAptPrintsRequests = ordItem.getOrderItemsAptPrintsRequests();
			if(orderItemsAptPrintsRequests.size()>0){
				ordItemsAptPrtBusiReq = orderItemsAptPrintsRequests.get(0);
//				String sql = "select p.acceptance_html from "+es_order_items_apt_prints_table+" p where p.order_id=? and p.item_id=? and p.source_from=?";
//				List sqlParams = new ArrayList();
//				sqlParams.add(ordItemsAptPrtBusiReq.getOrder_id());
//				sqlParams.add(ordItemsAptPrtBusiReq.getItem_id());
//				sqlParams.add(ManagerUtils.getSourceFrom());
				String acceptance_html = ordItemsAptPrtBusiReq.getAcceptance_html();//this.baseDaoSupport.queryForString(sql, sqlParams.toArray());
				
				ordItemsAptPrtBusiReq.setAcceptance_html(acceptance_html);
			}else{
				String sql = "select p.* from "+es_order_items_apt_prints_table+" p where p.order_id=? and p.source_from=?";
				List sqlParams = new ArrayList();
				sqlParams.add(orderTree.getOrder_id());
				sqlParams.add(ManagerUtils.getSourceFrom());
				try{
					ordItemsAptPrtBusiReq = (OrderItemsAptPrintsBusiRequest)this.baseDaoSupport.queryForObject(sql, OrderItemsAptPrintsBusiRequest.class, sqlParams.toArray());
				}catch (Exception e){
				}
			}
		}else{
			String sql = "select p.* from "+es_order_items_apt_prints_table+" p where p.order_id=? and p.source_from=?";
			List sqlParams = new ArrayList();
			sqlParams.add(orderTree.getOrder_id());
			sqlParams.add(ManagerUtils.getSourceFrom());
			try{
				ordItemsAptPrtBusiReq = (OrderItemsAptPrintsBusiRequest)this.baseDaoSupport.queryForObject(sql, OrderItemsAptPrintsBusiRequest.class, sqlParams.toArray());
			}catch (Exception e){
			}
		}
		if(null == ordItemsAptPrtBusiReq) ordItemsAptPrtBusiReq = new OrderItemsAptPrintsBusiRequest();
		return ordItemsAptPrtBusiReq;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	@Override
	public PrintOrderTree getOrdItemsAptPrtBusiReq(
			String order_id,boolean isHis) {
		PrintOrderTree pot = new PrintOrderTree();
		OrderTreeBusiRequest orderTree= null;
//		try{
		if(isHis){
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		}else{
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}
			
//		}catch(Exception ex){
//			try{
//				OrderTreeBusiRequest orderTreeBusiRequest = new OrderTreeBusiRequest();
//				orderTreeBusiRequest.setOrder_id(order_id);
//				orderTreeBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
//				orderTreeBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
//				orderTreeBusiRequest.setCreate_time(Consts.SYSDATE);
//				orderTreeBusiRequest.setUpdate_time(Consts.SYSDATE);
//				orderTreeBusiRequest.store();
//				CommonDataFactory.getInstance().updateOrderTree(order_id);
//				orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
//			}catch(Exception e){
//				
//			}
//		}
//		
		//获取流程页面访问地址
		if(orderTree!=null){
			String action_url = null;
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
	        List<Map> n_list = dcPublicCache.getList(EcsOrderConsts.DIC_ORDER_NODE);
	        String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
	        for(Map m:n_list){
	        	String t_id = (String) m.get("pkey");
	        	if(t_id!=null && t_id.equals(trace_id)){
	        		action_url = (String) m.get("codeb");
	        		break ;
	        	}
	        }
	        String phone_num= "";
			String terminal ="";
			String goods_package = "";
	        if(isHis){//历史单
	        	 phone_num= CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.PHONE_NUM);
				 terminal = CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.GOODS_NAME);
				 goods_package = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, "plan_title");
	        }else{
	        	 phone_num= CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
				 terminal = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.GOODS_NAME);
				 goods_package = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "plan_title");
	        }
			
			pot.setOrderTree(orderTree);
			pot.setAction_url(action_url);
			pot.setPhone_num(phone_num);
			pot.setGoods_package(goods_package);
			pot.setTerminal(terminal);
			
			OrderItemsAptPrintsBusiRequest ordItemsAptPrtBusiReq = getOrdItem(orderTree,isHis);
			String accept_html=ordItemsAptPrtBusiReq.getAcceptance_html();
			if(!StringUtils.isEmpty(ordItemsAptPrtBusiReq.getAcceptance_html_2()))
			{
				accept_html+=ordItemsAptPrtBusiReq.getAcceptance_html_2();
			}
			if(!StringUtils.isEmpty(ordItemsAptPrtBusiReq.getAcceptance_html_3()))
			{
				accept_html+=ordItemsAptPrtBusiReq.getAcceptance_html_3();
			}
			
			pot.setAcceptance_html(accept_html);
			pot.setReceipt_code(ordItemsAptPrtBusiReq.getReceipt_code());
			pot.setReceipt_mode(ordItemsAptPrtBusiReq.getReceipt_mode());
			pot.setStatus(ordItemsAptPrtBusiReq.getStatus());
		}
	
		return pot;
	}

	@Override
	public AcceptPrintModel getActPrtMod(String receipt_code,String apply_type) {
		AcceptPrintModel model = new AcceptPrintModel();
		String sql = "select a.* from es_accept_print_model a where a.apply_type=? and a.receipt_code=?";
		List sqlParams = new ArrayList();
		sqlParams.add(apply_type);
		sqlParams.add(receipt_code);
		model = (AcceptPrintModel)this.baseDaoSupport.queryForObject(sql, AcceptPrintModel.class, sqlParams.toArray());
		return model;
	}

	@Override
	public void updateOrdItemsAptPrtBusiReq(String order_id,boolean isHis) {
		OrderTreeBusiRequest orderTree =null;
		String es_order_items_apt_prints_table = "es_order_items_apt_prints";
		if(isHis){
			 orderTree =CommonDataFactory.getInstance().getOrderTreeHis(order_id);
			 es_order_items_apt_prints_table=es_order_items_apt_prints_table+"_his";
		}else{
			 orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}
		
		List<OrderItemBusiRequest> list = orderTree.getOrderItemBusiRequests();
		if(list.size()>0){
			OrderItemBusiRequest ordItem = list.get(0);
			List<OrderItemsAptPrintsBusiRequest> orderItemsAptPrintsRequests = ordItem.getOrderItemsAptPrintsRequests();
			if(orderItemsAptPrintsRequests.size()>0){
				OrderItemsAptPrintsBusiRequest ordItemsAptPrtBusiReq = orderItemsAptPrintsRequests.get(0);
				ordItemsAptPrtBusiReq.setStatus(EcsOrderConsts.ES_ORDER_ITEMS_APT_PRINTS_STATUS_1);
				ordItemsAptPrtBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				ordItemsAptPrtBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				ordItemsAptPrtBusiReq.store();
			}else{
				//订单树更新失败
				String sql = "update "+es_order_items_apt_prints_table+" p set p.status=? where p.order_id=? and p.source_from=?";
				List sqlParams = new ArrayList();
				sqlParams.add(EcsOrderConsts.ES_ORDER_ITEMS_APT_PRINTS_STATUS_1);
				sqlParams.add(order_id);
				sqlParams.add(ManagerUtils.getSourceFrom());
				this.baseDaoSupport.execute(sql, sqlParams.toArray());
			
			}
		}else{//订单树更新失败
			String sql = "update "+es_order_items_apt_prints_table+" p set p.status=? where p.order_id=? and p.source_from=?";
			List sqlParams = new ArrayList();
			sqlParams.add(EcsOrderConsts.ES_ORDER_ITEMS_APT_PRINTS_STATUS_1);
			sqlParams.add(order_id);
			sqlParams.add(ManagerUtils.getSourceFrom());
			this.baseDaoSupport.execute(sql, sqlParams.toArray());
		}
	}

	public String getOrderId(String out_order_id) {
		String order_id = "";
		StringBuilder sql = new StringBuilder("select t.order_id from es_order_ext t");
		sql.append(" where 1=1");
		sql.append(" and t.out_tid=?");
		List<Map> list = baseDaoSupport.queryForList(sql.toString(), out_order_id);
		if (list != null && list.size() > 0) {
			for (Map map : list) {
				if (map != null && map.size() > 0) {
					return map.get("ORDER_ID")!=null?map.get("ORDER_ID").toString():"";
				}
			}
		}
		return order_id;
	}
	/**
	 * 如果现用订单表没查询到数据则返回 0
	 * @return  总数
	 */
	private int getCountByOutId(String outId){
	    int i=0;
		StringBuilder sql = new StringBuilder("select count(*) counts from  es_order  o, es_order_ext oe,es_member em");
		sql.append(" where o.order_id=oe.order_id and o.source_from=oe.source_from and o.source_from=em.source_from");
		sql.append(" and o.member_id=em.member_id and o.source_from=?");
		sql.append(" and oe.out_tid=?");
		
		Map map=this.baseDaoSupport.queryForMap(sql.toString(),ManagerUtils.getSourceFrom(),outId);
		if(map!=null&&!map.isEmpty()){
			if(map.get("counts")!=null){
			 int count=	(Integer)map.get("counts");
			 if(count>0){
				 i=count;
			 }
			}
			
		}
		return i;
	}
	/**
	 *是新商城系统调用票据打印链接，并且根据outId查询现有表没有记录-则返回true
	 *否则--false
	 * @return
	 */
	public boolean checkIfNeedQueryHis(HttpServletRequest request,String outId){
		boolean flag=false;
		if(request.getSession().getAttribute(EcsOrderConsts.NEW_MALL_LOGIN)!=null){//新商城session不为空
			IUserService userService = UserServiceFactory.getUserService();
			if(!userService.isUserLoggedIn()){//没登陆
				String  sessionVal=(String) request.getSession().getAttribute(EcsOrderConsts.NEW_MALL_LOGIN);
				if(!sessionVal.equals("")){//是新商城调用
					if(this.getCountByOutId(outId)==0){//现用表没有数据
						flag=true;
					}
				}
			}
			
		};
		return flag;
	}
	
	
}
