package com.ztesoft.net.service.impl;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts2.ServletActionContext;
import org.drools.core.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import util.MoneyAuditCSVUtils;
import util.MoneyAuditExcelUtil;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.IRegionService;
import zte.params.region.req.RegionsGetReq;
import zte.params.region.req.RegionsListReq;
import zte.params.region.resp.RegionsGetResp;
import zte.params.region.resp.RegionsListResp;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.DataUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.NumeroImportLog;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ExportExcelHelper;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AuditBdtbVO;
import com.ztesoft.net.model.AuditBusinessVO;
import com.ztesoft.net.model.AuditOfflinePayVO;
import com.ztesoft.net.model.AuditQueryParame;
import com.ztesoft.net.model.AuditWoVO;
import com.ztesoft.net.model.AuditZbtbVO;
import com.ztesoft.net.model.AutoOrderTree;
import com.ztesoft.net.model.LockOrder;
import com.ztesoft.net.model.MoneyAuditBaseDataVo;
import com.ztesoft.net.model.AuditZbDetailsVO;
import com.ztesoft.net.service.IMoneyAuditManager;
import com.ztesoft.net.sqls.SF;

import consts.ConstsCore;

/**
 * @Description 财务稽核
 * @author  zhangJun
 * @date    2016年10月1日
 */
public class MoneyAuditManagerImpl extends BaseSupport implements IMoneyAuditManager {
	
	private final static String EXCEL_FROM_ZBTB="excel_from_zbtb";//总部淘宝报表导入
	private final static String EXCEL_FROM_AGENCY="excel_from_agency";//代收货款报表导入
	private final static String EXCEL_FROM_BDTB="excel_from_bdtb";//本地淘宝报表
	private final static String EXCEL_TYPE_WO="excel_type_wo";//沃平台报表导入
	private final static String EXCEL_TYPE_ZB_BUSI_PAY="excel_type_zb_busi_pay";//ECS报表导入
	private final static String EXCEL_TYPE_ZB_DETAILS="excel_type_zb_details";//总部订单详情报表导入
	private final static String EXCEL_TYPE_BSS="excel_type_bss";//BSS报表导入 (营业款)
	private final static String EXCEL_TYPE_CBSS="excel_type_cbss";//CBSS报表导入(营业款)
	
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	private IRegionService regionService;
	/**
	 * 查询稽核列表
	 */
	@Override
	public Page queryList(AuditQueryParame parame, int pageNo, int pageSize) {
		String sql = SQL_QUERY_BASE_DATA;
		if(parame!=null){
			if(!StringUtil.isEmpty(parame.getAudit_busi_money_status())){
				sql+=" and oe.audit_busi_money_status = '"+parame.getAudit_busi_money_status()+"'";
			}
			
			if(!StringUtil.isEmpty(parame.getAudit_receive_money_status())){
				sql+=" and oe.audit_receive_money_status = '"+parame.getAudit_receive_money_status()+"'";
			}
			if(!StringUtil.isEmpty(parame.getStart_time())){
				sql+=" and trunc(item.bss_account_time) >= trunc(to_date('"+parame.getStart_time()+"','yyyy-MM-dd'))";
			}
			if(!StringUtil.isEmpty(parame.getEnd_time())){
				sql+=" and trunc(item.bss_account_time) <= trunc(to_date('"+parame.getEnd_time()+"','yyyy-MM-dd'))";
			}
			if(!StringUtil.isEmpty(parame.getOrder_id())){
				sql+=" and o.order_id = '"+parame.getOrder_id()+"'";
			}
			if(!StringUtil.isEmpty(parame.getPay_type())){
				sql+=" and oe.Pay_type = '"+parame.getPay_type()+"'";
			}
			if(!StringUtil.isEmpty(parame.getOrder_from())){
				sql+=" and oet.source_type = '"+parame.getOrder_from()+"'";
			}
			if(!StringUtil.isEmpty(parame.getOrder_type())){
				if(EcsOrderConsts.BSS_CANCEL_STATUS_1.equals(parame.getOrder_type())){
					sql+=" and (oe.Bss_cancel_status = '"+parame.getOrder_type()+"' or oe.Ess_cancel_status= '"+parame.getOrder_type()+"')";
				}else{
					sql+=" and (oe.Bss_cancel_status = '"+parame.getOrder_type()+"' and oe.Ess_cancel_status= '"+parame.getOrder_type()+"')";
				}
				
			}
			if(!StringUtil.isEmpty(parame.getPayproviderid())){
				sql+=" and oet.payproviderid = '"+parame.getPayproviderid()+"'";
			}
			
		}
		sql+="  order by o.create_time desc ";
		logger.info("列表sql"+sql);
		Page page=daoSupport.queryForPageByMap(sql.toString(), pageNo, pageSize, MoneyAuditBaseDataVo.class, null);
		return page;
	}
	
	
	
	
	public List queryListForExp(AuditQueryParame parame,String excel) {
		String sql = SQL_QUERY_BASE_DATA_FOR_EXP;
		if(parame!=null){
			if(!StringUtil.isEmpty(parame.getAudit_busi_money_status())){
				sql+=" and oe.audit_busi_money_status = '"+parame.getAudit_busi_money_status()+"'";
			}
			
			if(!StringUtil.isEmpty(parame.getAudit_receive_money_status())){
				sql+=" and oe.audit_receive_money_status = '"+parame.getAudit_receive_money_status()+"'";
			}
			if(!StringUtil.isEmpty(parame.getStart_time())){
				sql+=" and trunc(item.bss_account_time) >= trunc(to_date('"+parame.getStart_time()+"','yyyy-MM-dd'))";
			}
			if(!StringUtil.isEmpty(parame.getEnd_time())){
				sql+=" and trunc(item.bss_account_time) <= trunc(to_date('"+parame.getEnd_time()+"','yyyy-MM-dd'))";
			}
			if(!StringUtil.isEmpty(parame.getOrder_id())){
				sql+=" and o.order_id = '"+parame.getOrder_id()+"'";
			}
			if(!StringUtil.isEmpty(parame.getPay_type())){
				sql+=" and oe.Pay_type = '"+parame.getPay_type()+"'";
			}
			if(!StringUtil.isEmpty(parame.getOrder_from())){
				sql+=" and oet.source_type = '"+parame.getOrder_from()+"'";
			}
			if(!StringUtil.isEmpty(parame.getOrder_type())){
				sql+=" and o.Order_type = '"+parame.getOrder_type()+"'";
			}
			if(!StringUtil.isEmpty(parame.getPayproviderid())){
				sql+=" oet.payproviderid = '"+parame.getPayproviderid()+"'";
			}
			
		}
		sql+="  order by o.create_time desc ";
		//Page page=this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
		List list=daoSupport.queryForList(sql.toString(),  null);
		List<Map> list_new=new ArrayList<Map>();
		List order_id_list=new ArrayList();
		List logi_no_order_id_list=new ArrayList();
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			String audit_busi_money_status = getConsts(StypeConsts.AUDIT_BUSI_MONEY_STATUS,Const.getStrValue(map, "audit_busi_money_status"));
			map.put("audit_busi_money_status",audit_busi_money_status);
			String audit_receive_money_status =getConsts(StypeConsts.AUDIT_BUSI_MONEY_STATUS,Const.getStrValue(map, "audit_receive_money_status"));
			map.put("audit_receive_money_status",audit_receive_money_status);
			String pay_type = getConsts(StypeConsts.PAY_TYPE,Const.getStrValue(map, "pay_type_ext"));
			map.put("pay_type",pay_type);
			String order_city_name = listRegions(Const.getStrValue(map, "order_city_code"));
			map.put("order_city_name",order_city_name);
			String order_from = getConsts("ZbOrderSource",Const.getStrValue(map, "order_from"));
			map.put("order_from",order_from);
			String order_type_return = Const.getStrValue(map, "order_type_return");
			if(StringUtil.equals(EcsOrderConsts.BSS_CANCEL_STATUS_0, order_type_return)){
				order_type_return="新增";
			}
			if(StringUtil.equals(EcsOrderConsts.BSS_CANCEL_STATUS_1, order_type_return)){
				order_type_return="返销";
			}
			map.put("order_type_return",order_type_return);
			String is_old = getConsts(StypeConsts.USER_TYPE,Const.getStrValue(map, "is_old"));
			map.put("is_old",is_old);
			
			//金额计算
			String audit_related_field=Const.getStrValue(map, "audit_related_field");
			String order_id=Const.getStrValue(map, "order_id");
			if("order_id".equals(audit_related_field)){//订单id 关联
				order_id_list.add(order_id);
			}else if("logi_no".equals(audit_related_field)){//物流单号关联
				logi_no_order_id_list.add(order_id);
			}
			
			list_new.add(map);
			
		}
		list_new=this.getMoneyList(list_new, order_id_list, logi_no_order_id_list);
		//把无匹配流水的union 进去
		if("busi".equals(excel)){
			
			
			
			String sql_b="select a.area order_city_name,a.phone_number phone_num,a.busi_money busi_money_sum  ,"
					+ "'系统稽核通过' audit_busi_money_status,'系统稽核通过' audit_receive_money_status,'无匹配流水提交' audit_remark  from es_order_audit_business a where a.order_id='无匹配流水' ";
			if(!StringUtil.isEmpty(parame.getStart_time())){
				sql_b+= " and    trunc(to_date(a.batch_number,'yyyy-mm-dd'))>=trunc(to_date('"+parame.getStart_time()+"','yyyy-MM-dd'))";
			}
			if(!StringUtil.isEmpty(parame.getEnd_time())){
				sql_b+=" and trunc(to_date(a.batch_number,'yyyy-mm-dd'))<=trunc(to_date('"+parame.getEnd_time()+"','yyyy-MM-dd')) ";
			}		
							
			List list_b=daoSupport.queryForList(sql_b.toString(),  null);
			if(list_b!=null){
				list_new.addAll(list_b);
			}
		}
		return list_new;
	}
   private List getMoneyList(List base_list,List<String> order_id_list,List<String> logi_no_order_id_list){
	   String order_id_lists="";
	   String logi_no_order_id_lists="";
	   for (String order_id : order_id_list) {
		   order_id_lists=order_id_lists+",'"+order_id+"'";
	   }
	   for (String order_id : logi_no_order_id_list) {
		   logi_no_order_id_lists=logi_no_order_id_lists+",'"+order_id+"'";
	   }
	   List order_id_list_m=null;
	   List logi_no_order_id_list_m=null;
	   if(!order_id_lists.equals("")){
		   order_id_lists=order_id_lists.substring(1, order_id_lists.length());
		 //根据orderId 查询
		   
		   String sql = "select t.order_id,a.store_order_id,a.activity_code,a.pay_channel,a.receive_money,a.receive_fee_date from es_order_ext t,es_order_audit_wo a "
				   + "where t.out_tid=a.store_order_id and t.order_id  in ("+order_id_lists+") and t.source_from='"+ManagerUtils.getSourceFrom()+"'"
				   + "union all "
				   + "select t.order_id,a.store_order_id,a.activity_code,a.pay_channel,a.receive_money,a.receive_fee_date from es_order_ext t,es_order_audit_bdtb a "
				   + "where t.order_id=a.base_order_id and t.order_id in ("+order_id_lists+") and t.source_from='"+ManagerUtils.getSourceFrom()+"'"
				   + "union all "
				   + "select t.order_id,a.store_order_id,'' activity_code,'' pay_channel,a.MONEY as receive_money,a.RECEIPT_DATE receive_fee_date from es_order_ext t,es_order_audit_zbtb a "
				   + "where t.out_tid=a.store_order_id and t.order_id in ("+order_id_lists+") and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
		    order_id_list_m=daoSupport.queryForListByMap(sql.toString(), null);
	   }
	   if(!logi_no_order_id_lists.equals("")){
		   logi_no_order_id_lists=logi_no_order_id_lists.substring(1, logi_no_order_id_lists.length());
		   String logi_no_sql = "select t.order_id,t.logi_no,a.cod_amoun from es_delivery t,es_order_audit_offline_pay a "
				   + "where t.logi_no=a.hwb_no  and t.order_id in ("+logi_no_order_id_lists+") and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
		    logi_no_order_id_list_m=daoSupport.queryForListByMap(logi_no_sql.toString(), null);
	   }
	   
	   
	
	   List<Map> list_new=new ArrayList<Map>();
	   for(int i=0;i<base_list.size();i++){
			Map map = (Map)base_list.get(i);
			String order_id=Const.getStrValue(map, "order_id");
			//orderId
			if(order_id_list_m!=null){
				 for(int j=0;j<order_id_list_m.size();j++){
					 Map o_map = (Map)order_id_list_m.get(j);
					String order_id_m=Const.getStrValue(o_map, "order_id");
					if(order_id.equals(order_id_m)){
						map.put("zh_money", Const.getStrValue(o_map, "receive_money"));
					}
				}
			}
			//物流单号
			if(logi_no_order_id_list_m!=null){
				 for(int k=0;k<logi_no_order_id_list_m.size();k++){
					 Map o_map = (Map)logi_no_order_id_list_m.get(k);
					String order_id_m=Const.getStrValue(o_map, "order_id");
					if(order_id.equals(order_id_m)){
						map.put("zh_money", Const.getStrValue(o_map, "cod_amoun"));
					}
				}
			}
			list_new.add(map);
	 }
	return list_new;
   }
	public List queryOfflineListForExp(String batch_number){
		String sql = "select t.pickup_date,t.hwb_no,t.consignee_tell,t.cod_amoun,t.service_charge from es_order_audit_offline_pay t,es_delivery a"
				+ " where t.hwb_no=a.logi_no and t.batch_number='"+batch_number+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
		List list=daoSupport.queryForList(sql.toString(),  null);
		return list;
		
	}
	
	public MoneyAuditBaseDataVo queryOrderListById(String order_id){
		String sql = " select o.order_id,decode(oe.Bss_cancel_status ,'1',oe.Bss_cancel_status,oe.Ess_cancel_status)   order_type_return,"
				+" oe.out_tid,oe.audit_related_field,oet.source_type as order_from,oe.pay_type,oe.lock_user_id,oe.order_city_code,audit_busi_money_status,audit_receive_money_status ,"
				+" item.bss_account_time,item.phone_num,item.invoice_no,"
				+" oet.GoodsName,oet.phone_owner_name,oet.payproviderid,oet.payprovidername ,"
				+" o.busi_money,o.paymoney,oe.audit_udp,oe.audit_remark "
				+" ,(select d.logi_no  from es_delivery d where d.order_id=o.order_id and d.DELIVERY_TYPE='0') logi_no"
				+ " ,(select sum(eoab.busi_money)  from es_order_audit_business eoab where eoab.order_id=o.order_id ) busi_money_sum "
				+ ",(select prod.is_old from es_order_items_prod_ext prod where o.order_id=prod.order_id and rownum=1) is_old, "
      			+ "(select prod.terminal_num from es_order_items_prod_ext prod where o.order_id=prod.order_id and rownum=1) terminal_num "
				+" from es_order o,es_order_ext oe,es_order_extvtl oet,es_order_items_ext item"
				+" where o.order_id=oe.order_id(+)  and o.order_id=oet.order_id(+) and o.order_id=item.order_id(+)  "
				+ " and o.source_from='"+ManagerUtils.getSourceFrom()+"'";
		sql+=" and o.order_id = '"+order_id+"' ";
		sql+="  order by o.create_time desc ";
		Page page=daoSupport.queryForPageByMap(sql.toString(), 1, 1, MoneyAuditBaseDataVo.class, null);
		List<MoneyAuditBaseDataVo> list = page.getResult();
		for(int i=0;i<list.size();i++){
			MoneyAuditBaseDataVo map = list.get(i);
			String audit_busi_money_status = getConsts(StypeConsts.AUDIT_BUSI_MONEY_STATUS,map.getAudit_busi_money_status());
			map.setAudit_busi_money_status(audit_busi_money_status);
			String audit_receive_money_status =getConsts(StypeConsts.AUDIT_BUSI_MONEY_STATUS,map.getAudit_receive_money_status());
			map.setAudit_receive_money_status(audit_receive_money_status);
			String pay_type = getConsts(StypeConsts.PAY_TYPE,map.getPay_type());
			map.setPay_type(pay_type);
			String order_city_code = listRegions(map.getOrder_city_code());
			map.setAudit_receive_money_status(order_city_code);
			String order_from = getConsts("ZbOrderSource",map.getOrder_from());
			map.setOrder_from(order_from);
			String order_type_return = map.getOrder_type_return();
			//Float busi_money = map.getBusi_money();
			//Float paymoney = map.getPaymoney();
			if(StringUtil.equals(EcsOrderConsts.BSS_CANCEL_STATUS_0, order_type_return)){
				order_type_return="新增";
			}
			if(StringUtil.equals(EcsOrderConsts.BSS_CANCEL_STATUS_1, order_type_return)){
				order_type_return="返销";
			}
			map.setOrder_type_return(order_type_return);
			String is_old = getConsts(StypeConsts.USER_TYPE,map.getIs_old());
			map.setIs_old(is_old);
			
		}
		
		MoneyAuditBaseDataVo moneyAudit = (MoneyAuditBaseDataVo) list.get(0);
		return moneyAudit;
		
	}
	
	private String listRegions(String order_city_code){
		RegionsGetReq req = new RegionsGetReq();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String province_region = cacheUtil.getConfigInfo("PROVINCE_REGION_CODE");
		req.setRegion_id(order_city_code);;
		RegionsGetResp resp = regionService.getRegion(req);
		Regions  region = resp.getRegions();
		return region.getLocal_name();
	}
	
	private String getConsts(String key,String pkey){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> list = dcPublicCache.getList(key);
        String pname = "";
        for(int i=0;i<list.size();i++){
        	if(StringUtil.equals(pkey, (String)list.get(i).get("pkey"))){
        		pname = (String)list.get(i).get("pname");
        	}
        }
        return pname;
	}
	
	public List queryFlowById(String order_id){
		String sql = " select t.area,t.audit_bss_id,t.order_id,t.operation_time,t.serial_number,t.phone_number,t.busi_money,t.business_type,t.data_from,t.operator from es_order_audit_business t "
				+ " where t.order_id= '"+order_id+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
		List list=daoSupport.queryForListByMap(sql.toString(), null);
		return list;
	}
	public List queryMoneyForUDPById(String order_id){
		String sql = "select t.order_id,t.audit_udp,a.payprovidername,a.pay_bank,b.busi_type,b.money,a.pay_time,b.brand from es_order_ext t,es_order_audit_zb_details a,es_order_audit_zb_busi_pay b "
				   + "where t.audit_udp=a.udp_code and t.audit_udp=b.udp_code and t.order_id='"+order_id+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
		List list=daoSupport.queryForListByMap(sql.toString(), null);
		return list;
		
	}
	public List queryMoneyForOrderIdById(String order_id){
		String sql = "select a.store_order_id,a.activity_code,a.pay_channel,a.receive_money,a.receive_fee_date from es_order_ext t,es_order_audit_wo a "
				   + "where t.out_tid=a.store_order_id and t.order_id='"+order_id+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'"
				   + "union all "
				   + "select a.store_order_id,a.activity_code,a.pay_channel,a.receive_money,a.receive_fee_date from es_order_ext t,es_order_audit_bdtb a "
				   + "where t.order_id=a.base_order_id and t.order_id='"+order_id+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'"
				   + "union all "
				   + "select a.store_order_id,'' activity_code,'' pay_channel,a.MONEY as receive_money,a.RECEIPT_DATE receive_fee_date from es_order_ext t,es_order_audit_zbtb a "
				   + "where t.out_tid=a.store_order_id and t.order_id='"+order_id+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
		List list=daoSupport.queryForListByMap(sql.toString(), null);
		return list;
		
	}
	public List queryMoneyForLogiNoById(String order_id){
		String sql = "select t.order_id,t.logi_no,a.cod_amoun from es_delivery t,es_order_audit_offline_pay a "
				   + "where t.logi_no=a.hwb_no  and t.order_id='"+order_id+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
		List list=daoSupport.queryForListByMap(sql.toString(), null);
		return list;
		
	}
	public Map updateBusi(String order_id,String audit_bss_id){
		Map result = new HashMap();
		try{
			String update_sql= "update es_order_audit_business set order_id=null "
					+ "where order_id='"+order_id+"' and audit_bss_id='"+audit_bss_id+"'";
			daoSupport.execute(update_sql, null);
		}catch(Exception e){
			result.put("res", "1");
			result.put("msg", e.getMessage());
			return result;
		}
		result.put("res", "0");
		result.put("msg", "操作成功");
		return result;
		
	}
	
	public Map updateOrder(String order_id, Map change){
		boolean flag = false;
		Map result = new HashMap();
		OrderTreeBusiRequest orderTree =  CommonDataFactory.getInstance().getOrderTree(order_id);// CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();//paytype  udp_no audit_note
		OrderBusiRequest orderBusi = orderTree.getOrderBusiRequest();//busi_money  order_type
		OrderDeliveryBusiRequest orderDelivery = orderTree.getOrderDeliveryBusiRequests().get(0);//logi_no
		if(!(change.get("busi_money")+"").equals(orderBusi.getBusi_money()+"")&&!StringUtil.isEmpty(change.get("busi_money")+"")
				&&!StringUtil.isEmpty(orderBusi.getBusi_money()+"")){
			orderBusi.setBusi_money(Double.parseDouble(change.get("busi_money")+""));
			flag=true;
		}
		if(!(change.get("order_type")+"").equals(orderBusi.getOrder_type()+"")&&!StringUtil.isEmpty(change.get("order_type")+"")
				&&!StringUtil.isEmpty(orderBusi.getOrder_type()+"")){
			orderBusi.setOrder_type(change.get("order_type")+"");
			flag=true;
		}
		if(!(change.get("logi_no")+"").equals(orderDelivery.getLogi_no()+"")&&!StringUtil.isEmpty(change.get("logi_no")+"")
				&&!StringUtil.isEmpty(orderDelivery.getLogi_no()+"")&&"1".equals(orderDelivery.getSign_status())
				&&!StringUtil.isEmpty(orderDelivery.getSign_status())){
			orderDelivery.setLogi_no(change.get("logi_no")+"");
			flag=true;
		}
		if(!(change.get("paytype")+"").equals(orderExt.getPay_type()+"")&&!StringUtil.isEmpty(change.get("paytype")+"")
				&&!StringUtil.isEmpty(orderExt.getPay_type()+"")){
			orderExt.setPay_type(change.get("paytype")+"");
			flag=true;
		}
		if(!(change.get("udp_no")+"").equals(orderExt.getAudit_udp()+"")&&!StringUtil.isEmpty(change.get("udp_no")+"")
				&&!StringUtil.isEmpty(orderExt.getAudit_udp()+"")){
			orderExt.setAudit_udp(change.get("udp_no")+"");
			flag=true;
		}
		if(!(change.get("audit_note")+"").equals(orderExt.getAudit_remark()+"")&&!StringUtil.isEmpty(change.get("audit_note")+"")
				&&!StringUtil.isEmpty(orderExt.getAudit_remark()+"")){
			orderExt.setAudit_remark(change.get("audit_note")+"");
			flag=true;
		}
		if(flag==false){
			result.put("res", "1");
			result.put("msg", "没有需要修改的内容");
			return result;
		}
		try{
			orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderDelivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderDelivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExt.store();
			orderBusi.store();
			orderDelivery.store();
			
		}catch(Exception e){
			result.put("res", "1");
			result.put("msg", e.getMessage());
			return result;
		}
		result.put("res", "0");
		result.put("msg", "操作成功");
		return result;
		
	}
	
	public Map addFlow(String order_id, Map change){
		Map result = new HashMap();
		try{
			String update_sql = "";
			if(StringUtil.equals(change.get("is_minus")+"","1")){
				update_sql="update es_order_audit_business set order_id='"+order_id+"' where data_from='"+change.get("data_from")+""+"' and serial_number='"+change.get("serial_number")+""+"' and busi_money>=0" ;
				
			}else if(StringUtil.equals(change.get("is_minus")+"","0")){
				update_sql="update es_order_audit_business set order_id='"+order_id+"' where data_from='"+change.get("data_from")+""+"' and serial_number='"+change.get("serial_number")+""+"' and busi_money<0" ;
			}
			this.baseDaoSupport.execute(update_sql, null);
			result.put("res", "0");
			result.put("msg", "操作成功");
			return result;
		}catch(Exception e){
			result.put("res", "1");
			result.put("msg", e.getMessage());
			return result;
		}
	}
	/**
	 * 导入excel数据到数据库
	 * @throws Exception 
	 */
	@Transactional 
    public String importacion(File file,String excel_from,String batch_number,String file_name) throws Exception {
    	
    	String msg="0";
    	String sql=null;
    	List<Map<String,String>>  batchList=null;
		Map<String,String> addMap=new HashMap<String, String>();
		addMap.put("SOURCE_FROM", ManagerUtils.getSourceFrom());
		addMap.put("BATCH_NUMBER", batch_number);
		addMap.put("CREATE_USER", ManagerUtils.getAdminUser().getUsername());
		try {
			addMap.put("CREATE_DATE",DateUtil.getTime2() );
		} catch (FrameException e) {
			e.printStackTrace();
		}
	

		
    	if(EXCEL_FROM_ZBTB.equals(excel_from)){//总部淘宝
    		sql=SQL_EXCEL_FROM_ZBTB;
    		batchList=this.getDataZBTB(file, addMap, file_name);
    		
    	}else  if(EXCEL_FROM_AGENCY.equals(excel_from)){//顺丰
    		sql=SQL_EXCEL_FROM_AGENCY;
    		batchList=this.getDataAgency(file, addMap, file_name);
    		
    	}else  if(EXCEL_FROM_BDTB.equals(excel_from)){//本地淘宝
    		sql=SQL_EXCEL_FROM_BDTB;
    		batchList=this.getDataBDTB(file, addMap, file_name);
    		
    	}else  if(EXCEL_TYPE_WO.equals(excel_from)){//沃平台
    		sql=SQL_EXCEL_TYPE_WO;
    		batchList=this.getDataWo(file, addMap, file_name);
    		
    		
    	}else  if(EXCEL_TYPE_ZB_BUSI_PAY.equals(excel_from)){//完成
    		sql=SQL_EXCEL_TYPE_ZB_BUSI_PAY;
    		batchList=this.getDataBusiPay(file, addMap, file_name);
    		
    	}else  if(EXCEL_TYPE_ZB_DETAILS.equals(excel_from)){//完成
    		sql=SQL_EXCEL_TYPE_ZB_DETAILS;
    		batchList=this.getDataZbDetails(file, addMap, file_name);
    		
    	}else  if(EXCEL_TYPE_BSS.equals(excel_from)){//完成
    		sql=SQL_EXCEL_TYPE_BSS;
    		batchList=this.getDataBSS(file, addMap, file_name);
    		
    	}else  if(EXCEL_TYPE_CBSS.equals(excel_from)){//完成
    		sql=SQL_EXCEL_TYPE_BSS;
    		batchList=this.getDataCBSS(file, addMap, file_name);
    	}
    	//测试代码-start
    	/*for (Map<String, String> fieldMap : batchList) {
    		Set<String> set=fieldMap.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				String value = fieldMap.get(key);
				logger.info(key +" : " +value);
			}
			logger.info("###############################");
		}*/
    	//测试代码-end
    	if(!StringUtil.isEmpty(sql)&&batchList!=null&&!batchList.isEmpty()){
    		this.baseDaoSupport.batchExecuteByListMap(sql, batchList);
    		msg=batchList.size()+"#"+batch_number;
    	}
    	 
    	logger.info(msg);
		return msg;
    }
    
    private List<Map<String,String>> getDataZBTB(File file,Map<String,String> addMap,String file_name) throws Exception{
 		Map<String,String> fieldMap=new HashMap<String, String>();
 		
 		fieldMap.put( "省份","PROVINCE");
 		fieldMap.put("地市","CITY");
 		fieldMap.put("商品类别","GOODS_TYPE" );
 		fieldMap.put("商品名称","GOODS_NAME" );
 		fieldMap.put("资费计划","FEE_PLAN" );
 		
 		fieldMap.put("淘宝订单号","TB_ORDER_ID" );
 		fieldMap.put("商城订单号","STORE_ORDER_ID" );//重要字段
 		fieldMap.put("入网证件号码","ID_NUMBER" );
 		fieldMap.put("手机号码(新购)","PHONE_NUMBER" );
 		fieldMap.put("订单创建日期","ORDER_CREATE_DATE");
 		fieldMap.put("订单费用(应收)","ORDER_FEE");
 		fieldMap.put("实收日期","RECEIPT_DATE");
 		fieldMap.put("收款金额","MONEY" );//重要字段
 		
 		//先不入库,反正没啥用
 		

 		addMap.put("BROKERAGE_FEE","");
 		addMap.put("EXPAND_FEE","");
 		addMap.put("CARD_FEE","");
 		addMap.put("FINE_FEE","");
 		addMap.put("RECEIVE_MONEY","");
 		
 		int titleInRow=3;
    	return MoneyAuditCSVUtils.getBatchMap(file, titleInRow, fieldMap, addMap,true);
     }
    private List<Map<String,String>> getDataAgency(File file,Map<String,String> addMap,String file_name){
 		Map<String,String> fieldMap=new HashMap<String, String>();
 		
 		fieldMap.put("PICKUP_DATE", "寄件日期(PickupDate)");
 		fieldMap.put("SETTLEMENT_DATE", "日期(Settlementdate)");
 		fieldMap.put("HWB_NO", "运单号码(HWBNo.)");
 		fieldMap.put("DESTINATION_AREA", "对方地区(DestinationArea)");
 		fieldMap.put("COMPANY_NAME", "对方公司名称(Consignee'sCompanyName)");
 		fieldMap.put("CONSIGNEE_TELL", "对方公司电话(Consignee'sTell)");
 		fieldMap.put("COD_AMOUN", "代收货款金额(CODamount)");
 		fieldMap.put("SERVICE_CHARGE", "服务费(ServiceCharge)");
 		fieldMap.put("HANDLED_BY", "经手人(HandledBy)");
 		fieldMap.put("DELIVERY_COURIE", "收派员(DeliveryCourier)");
 		fieldMap.put("SERVICE_TYPE", "业务(Servicetype)");
 		fieldMap.put("PRODUCT_TYPE", "产品类型(Producttype)");
 		fieldMap.put("REMARKS", "备注(Remarks)");
 		
 		int titleInRow=1;
 		 List<Map<String,String>> list= MoneyAuditExcelUtil.getBatchMap(file, fieldMap, addMap,file_name,titleInRow);
  		List<Map<String,String>> list_new=new ArrayList<Map<String,String>>();
  		 for (Map<String, String> map : list) {//排除商户订单号是空的数据
 			String hwb_no=Const.getStrValue(map, "HWB_NO");
 			if(!StringUtil.isEmpty(hwb_no)){
 				list_new.add(map);
 			}

 		}
      	return list_new;
     }
    private List<Map<String,String>> getDataBDTB(File file,Map<String,String> addMap,String file_name){
 		Map<String,String> fieldMap=new HashMap<String, String>();
 		
 		fieldMap.put("RECEIVE_FEE_DATE", "入账时间");
 		fieldMap.put("ACTIVITY_CODE", "支付宝交易号");
 		fieldMap.put("ACTIVITY_NO", "支付宝流水号");
 		fieldMap.put("STORE_ORDER_ID", "商户订单号");//重要字段
 		fieldMap.put("ACCOUNTING_TYPE", "账务类型");
 		fieldMap.put("RECEIVE_MONEY", "收入（+元）");//重要字段
 		fieldMap.put("PAY_MONEY", "支出（-元）");
 		fieldMap.put("SERVICE_MONEY", "服务费（元）");
 		fieldMap.put("PAY_CHANNEL", "支付渠道");
 		fieldMap.put("SIGN_PRODUCT", "签约产品");
 		fieldMap.put("ACCOUNT", "对方账户");
 		fieldMap.put("ACCOUNT_NAME", "对方名称");
 		fieldMap.put("BANK_ORDER_ID", "银行订单号");
 		fieldMap.put("GOODS_NAME", "商品名称");
 		fieldMap.put("REMARKS", "备注");
 		
 		int titleInRow=3;
 		 List<Map<String,String>> list= MoneyAuditExcelUtil.getBatchMap(file, fieldMap, addMap,file_name,titleInRow);
 		List<Map<String,String>> list_new=new ArrayList<Map<String,String>>();
 		 for (Map<String, String> map : list) {//排除商户订单号是空的数据
			String store_order_id=Const.getStrValue(map, "STORE_ORDER_ID");
			if(!StringUtil.isEmpty(store_order_id)){
				store_order_id=store_order_id.substring(store_order_id.indexOf("P")+1, store_order_id.length());
				map.put("STORE_ORDER_ID", store_order_id);
				
				
				String pay_money=Const.getStrValue(map, "PAY_MONEY").trim();
				if(!StringUtil.isEmpty(pay_money)){
					map.put("RECEIVE_MONEY", "-"+pay_money);
				}
				list_new.add(map);
			}

		}
     	return list_new;
     }
    private List<Map<String,String>> getDataWo(File file,Map<String,String> addMap,String file_name){
		Map<String,String> fieldMap=new HashMap<String, String>();
		
		fieldMap.put("RECEIVE_FEE_DATE", "入账时间");
		fieldMap.put("ACTIVITY_CODE", "支付宝交易号");
		fieldMap.put("ACTIVITY_NO", "支付宝流水号");
		fieldMap.put("STORE_ORDER_ID", "商户订单号");
		fieldMap.put("ACCOUNTING_TYPE", "账务类型");
		fieldMap.put("RECEIVE_MONEY", "收入（+元）");
		fieldMap.put("PAY_MONEY", "支出（-元）");
		fieldMap.put("SERVICE_MONEY", "服务费（元）");
		fieldMap.put("PAY_CHANNEL", "支付渠道");
		fieldMap.put("SIGN_PRODUCT", "签约产品");
		fieldMap.put("ACCOUNT", "对方账户");
		fieldMap.put("ACCOUNT_NAME", "对方名称");
		fieldMap.put("BANK_ORDER_ID", "银行订单号");
		fieldMap.put("GOODS_NAME", "商品名称");
		fieldMap.put("REMARKS", "备注");
		
		
		int titleInRow=3;
		
		List<Map<String,String>> rList=MoneyAuditExcelUtil.getBatchMap(file, fieldMap, addMap,file_name,titleInRow);
		List<Map<String,String>> rNewList=new ArrayList<Map<String,String>>();
		//把支出（-元）列表放到收入列，并改为负数
		for (Map<String, String> map : rList) {
			String pay_money=Const.getStrValue(map, "PAY_MONEY").trim();
			if(!StringUtil.isEmpty(pay_money)){
				map.put("RECEIVE_MONEY", "-"+pay_money);
			}
			rNewList.add(map);
		}
		if(rNewList!=null&&rNewList.size()>=1){//excel 表格最后多出一行，剔除,如果判断不是多余的内容就不删
			String store_order_id=Const.getStrValue(rNewList.get(rNewList.size()-1), "STORE_ORDER_ID").trim();
			if(StringUtil.isEmpty(store_order_id)){
				rNewList.remove(rNewList.size()-1);
			}
		}
    	return rNewList;
    }
    
    private List<Map<String,String>> getDataBusiPay(File file,Map<String,String> addMap,String file_name) throws Exception{
		Map<String,String> fieldMap=new HashMap<String, String>();
		
		fieldMap.put("统一支付订单号","UDP_CODE");//重要字段
		fieldMap.put("业务订单号","BUSI_ORDER_ID" );
		fieldMap.put("业务类型","BUSI_TYPE");//重要字段
		fieldMap.put("支付机构","PAY_ORGANIZATION");
		fieldMap.put("银行编码","BANK_CODE");
		//fieldMap.put("描述1","REMARKS_ONE");  //不入库
		//fieldMap.put("描述2","REMARKS_TWO");//不入库
		fieldMap.put("金额(元)","MONEY");//重要字段
		fieldMap.put("支付时间","PAY_TIME");//重要字段
		fieldMap.put("省份","PROVINCE");
		fieldMap.put("地市","CITY");
		fieldMap.put("退款时间","REFUND_TIME");//重要字段
		fieldMap.put("品牌","BRAND");
		fieldMap.put("号码","TEL_NUM");
		
		//不入库
		addMap.put("REMARKS_ONE","");  
		addMap.put("REMARKS_TWO","");
		fieldMap.put("REFUND_TIME","");
		
		int titleInRow=3;
    	return MoneyAuditCSVUtils.getBatchMap(file, titleInRow, fieldMap, addMap,false);
    }
    private List<Map<String,String>> getDataZbDetails(File file,Map<String,String> addMap,String file_name) throws Exception{
		Map<String,String> fieldMap=new HashMap<String, String>();
		
		fieldMap.put("订单号","ORDER_NUM");//重要字段
		fieldMap.put("订单ID","ORDER_ID");//重要字段
		fieldMap.put("ESS订单号","ESS_ORDER_ID" );//重要字段
		
		//
		/*fieldMap.put("订单日期","ORDER_CREATE_DATE");
		fieldMap.put("订单状态","ORDER_STATUS");
		fieldMap.put("省分","PROVINCE");
		fieldMap.put("地市","CITY");
		fieldMap.put("商品类型","GOODS_TYPE");
		fieldMap.put("商品名称","GOODS_NAME" );
		fieldMap.put("套餐名称","COMBO_NAME");
		fieldMap.put("号码网别","NET_TYPE");
		fieldMap.put("终端品牌","PHONE_BRAND");
		fieldMap.put("终端型号","PHONE_MODEL");
		fieldMap.put("终端颜色","PHONE_COLOUR");
		fieldMap.put("活动类型","ACTIVITY_TYPE");
		fieldMap.put("商城实收","STORE_RECEIVE_MONEY");*/
		
		//先不入库
		addMap.put("ORDER_CREATE_DATE","");
		addMap.put("ORDER_STATUS","");
		addMap.put("PROVINCE","");
		addMap.put("CITY","");
		addMap.put("GOODS_TYPE","");
		addMap.put("GOODS_NAME" ,"");
		addMap.put("COMBO_NAME","");
		addMap.put("NET_TYPE","");
		addMap.put("PHONE_BRAND","");
		addMap.put("PHONE_MODEL","");
		addMap.put("PHONE_COLOUR","");
		addMap.put("ACTIVITY_TYPE","");
		addMap.put("STORE_RECEIVE_MONEY","");
		
		
		fieldMap.put("支付机构","PAYPROVIDERNAME");//重要字段
		fieldMap.put("统一支付订单号","UDP_CODE");//重要字段
		fieldMap.put("支付时间","PAY_TIME");//重要字段
		fieldMap.put("退款时间","REFUND_TIME");//重要字段
		fieldMap.put("支付方式","PAY_TYPE");//重要字段
		fieldMap.put("支付银行","PAY_BANK");//重要字段
		int titleInRow=3;
    	return MoneyAuditCSVUtils.getBatchMap(file, titleInRow, fieldMap, addMap,false);
    }
    private List<Map<String,String>> getDataCBSS(File file,Map<String,String> addMap,String file_name){
		Map<String,String> fieldMap=new HashMap<String, String>();
		
		addMap.put("DATA_FROM", ZjEcsOrderConsts.AUDIT_BUSI_DATA_FROM_CBSS);
		
		fieldMap.put("AREA", "");
		fieldMap.put("SERIAL_NUMBER", "业务流水号");
		fieldMap.put("PHONE_NUMBER", "业务号码");
		fieldMap.put("BUSINESS_TYPE", "业务类型");
		fieldMap.put("BUSI_MONEY", "实收金额");
		fieldMap.put("OPERATION_TIME", "收费时间");
		fieldMap.put("OPERATOR", "营业员名称");
		int titleInRow=1;
    	return MoneyAuditExcelUtil.getBatchMap(file, fieldMap, addMap,file_name,titleInRow);
    }
    private List<Map<String,String>> getDataBSS(File file,Map<String,String> addMap,String file_name){
		Map<String,String> fieldMap=new HashMap<String, String>();
		
		addMap.put("DATA_FROM", ZjEcsOrderConsts.AUDIT_BUSI_DATA_FROM_BSS);
		
		fieldMap.put("AREA", "地市");
		fieldMap.put("SERIAL_NUMBER", "流水号");
		fieldMap.put("PHONE_NUMBER", "服务号码");
		fieldMap.put("BUSINESS_TYPE", "业务种类");
		fieldMap.put("BUSI_MONEY", "金额");
		fieldMap.put("OPERATION_TIME", "操作时间");
		fieldMap.put("OPERATOR", "操作员");
		int titleInRow=1;
		List<Map<String,String>> rList=MoneyAuditExcelUtil.getBatchMap(file, fieldMap, addMap,file_name,titleInRow);
		List<Map<String,String>> rNewList=new ArrayList<Map<String,String>>();
		//去除操作员名称是：省分自助、网厅自助开头的数据
		for (Map<String, String> map : rList) {
			String operator=Const.getStrValue(map, "OPERATOR");
			if(!operator.startsWith("网厅自助")&&!operator.startsWith("省分自助")){
				rNewList.add(map);
			}
		}
		
    	return rNewList;
    }
	
    
  //完成
    private static final String SQL_EXCEL_FROM_ZBTB = "INSERT INTO ES_ORDER_AUDIT_ZBTB "
			+ " (ZBTB_ID,PROVINCE,CITY,GOODS_TYPE,GOODS_NAME,FEE_PLAN,TB_ORDER_ID,STORE_ORDER_ID,ID_NUMBER,PHONE_NUMBER,ORDER_CREATE_DATE,ORDER_FEE,RECEIPT_DATE,MONEY,BROKERAGE_FEE,EXPAND_FEE,CARD_FEE,FINE_FEE,RECEIVE_MONEY,SOURCE_FROM,BATCH_NUMBER,CREATE_DATE,CREATE_USER)"
			+ " VALUES (S_ES_ORDER_AUDIT_ZBTB.NEXTVAL, :PROVINCE, :CITY, :GOODS_TYPE, :GOODS_NAME, :FEE_PLAN, :TB_ORDER_ID, :STORE_ORDER_ID, :ID_NUMBER, :PHONE_NUMBER, :ORDER_CREATE_DATE, :ORDER_FEE, :RECEIPT_DATE, :MONEY, :BROKERAGE_FEE, :EXPAND_FEE, :CARD_FEE, :FINE_FEE, :RECEIVE_MONEY, :SOURCE_FROM, :BATCH_NUMBER, to_date(  :CREATE_DATE ,'yyyy-MM-dd hh24:mi:ss'), :CREATE_USER)";
   
   //完成
    private static final String SQL_EXCEL_FROM_AGENCY = "INSERT INTO ES_ORDER_AUDIT_OFFLINE_PAY "
			+ " (AOP_ID,PICKUP_DATE,SETTLEMENT_DATE,HWB_NO,DESTINATION_AREA,COMPANY_NAME,CONSIGNEE_TELL,COD_AMOUN,SERVICE_CHARGE,HANDLED_BY,DELIVERY_COURIE,SERVICE_TYPE,PRODUCT_TYPE,REMARKS,SOURCE_FROM,BATCH_NUMBER,CREATE_DATE,CREATE_USER)"
			+ " VALUES (S_ES_ORDER_AUDIT_OFFLINE_PAY.NEXTVAL, :PICKUP_DATE, :SETTLEMENT_DATE, :HWB_NO, :DESTINATION_AREA, :COMPANY_NAME, :CONSIGNEE_TELL, :COD_AMOUN, :SERVICE_CHARGE, :HANDLED_BY, :DELIVERY_COURIE, :SERVICE_TYPE, :PRODUCT_TYPE, :REMARKS, :SOURCE_FROM, :BATCH_NUMBER, to_date(  :CREATE_DATE ,'yyyy-MM-dd hh24:mi:ss'), :CREATE_USER)";
	
    private static final String SQL_EXCEL_FROM_BDTB = "INSERT INTO ES_ORDER_AUDIT_BDTB "
    		+ " (BDTB_ID,STORE_ORDER_ID,RECEIVE_MONEY,PAY_CHANNEL,RECEIVE_FEE_DATE,SOURCE_FROM,BATCH_NUMBER,CREATE_DATE,CREATE_USER,ACTIVITY_CODE)"
			+ " VALUES (S_ES_ORDER_AUDIT_BDTB.NEXTVAL,  :STORE_ORDER_ID, :RECEIVE_MONEY, :PAY_CHANNEL, :RECEIVE_FEE_DATE, :SOURCE_FROM, :BATCH_NUMBER, to_date(  :CREATE_DATE ,'yyyy-MM-dd hh24:mi:ss'), :CREATE_USER, :ACTIVITY_CODE)";
    //完成
    private static final String SQL_EXCEL_TYPE_WO = "INSERT INTO ES_ORDER_AUDIT_WO "
			+ " (WO_ID,RECEIVE_FEE_DATE,ACTIVITY_CODE,ACTIVITY_NO,STORE_ORDER_ID,ACCOUNTING_TYPE,RECEIVE_MONEY,PAY_MONEY,SERVICE_MONEY,PAY_CHANNEL,SIGN_PRODUCT,ACCOUNT,ACCOUNT_NAME,BANK_ORDER_ID,GOODS_NAME,REMARKS,SOURCE_FROM,BATCH_NUMBER,CREATE_DATE,CREATE_USER)"
			+ " VALUES (S_ES_ORDER_AUDIT_WO.NEXTVAL, to_date(  :RECEIVE_FEE_DATE ,'yyyy-MM-dd hh24:mi:ss'), :ACTIVITY_CODE, :ACTIVITY_NO, :STORE_ORDER_ID, :ACCOUNTING_TYPE, :RECEIVE_MONEY, :PAY_MONEY, :SERVICE_MONEY, :PAY_CHANNEL, :SIGN_PRODUCT, :ACCOUNT, :ACCOUNT_NAME, :BANK_ORDER_ID, :GOODS_NAME, :REMARKS, :SOURCE_FROM, :BATCH_NUMBER, to_date(  :CREATE_DATE ,'yyyy-MM-dd hh24:mi:ss'), :CREATE_USER)";
   
   //完成
    private static final String SQL_EXCEL_TYPE_ZB_DETAILS = "INSERT INTO ES_ORDER_AUDIT_ZB_DETAILS "
			+ " (ZB_ID,ORDER_NUM,ORDER_ID,ESS_ORDER_ID,ORDER_CREATE_DATE,ORDER_STATUS,PROVINCE,CITY,GOODS_TYPE,GOODS_NAME,COMBO_NAME,NET_TYPE,PHONE_BRAND,PHONE_MODEL,PHONE_COLOUR,ACTIVITY_TYPE,STORE_RECEIVE_MONEY,SOURCE_FROM,BATCH_NUMBER,CREATE_DATE,CREATE_USER ,PAYPROVIDERNAME,UDP_CODE,PAY_TIME,REFUND_TIME,PAY_BANK,PAY_TYPE)"
			+ " VALUES (S_ES_ORDER_AUDIT_ZB_DETAILS.NEXTVAL, :ORDER_NUM, :ORDER_ID, :ESS_ORDER_ID, :ORDER_CREATE_DATE, :ORDER_STATUS, :PROVINCE, :CITY, :GOODS_TYPE, :GOODS_NAME, :COMBO_NAME, :NET_TYPE, :PHONE_BRAND, :PHONE_MODEL, :PHONE_COLOUR, :ACTIVITY_TYPE, :STORE_RECEIVE_MONEY, :SOURCE_FROM, :BATCH_NUMBER, to_date(  :CREATE_DATE ,'yyyy-MM-dd hh24:mi:ss'), :CREATE_USER, :PAYPROVIDERNAME, :UDP_CODE, :PAY_TIME, :REFUND_TIME, :PAY_BANK,  :PAY_TYPE)";
    //完成
    private static final String SQL_EXCEL_TYPE_ZB_BUSI_PAY = "INSERT INTO ES_ORDER_AUDIT_ZB_BUSI_PAY "
			+ " (ZB_PAY_ID,UDP_CODE,BUSI_ORDER_ID,BUSI_TYPE,PAY_ORGANIZATION,BANK_CODE,REMARKS_ONE,REMARKS_TWO,MONEY,PAY_TIME,PROVINCE,CITY,REFUND_TIME,BRAND,TEL_NUM,SOURCE_FROM,BATCH_NUMBER,CREATE_DATE,CREATE_USER)"
			+ " VALUES (S_ES_ORDER_AUDIT_ZB_BUSI_PAY.NEXTVAL, :UDP_CODE, :BUSI_ORDER_ID, :BUSI_TYPE, :PAY_ORGANIZATION, :BANK_CODE, :REMARKS_ONE, :REMARKS_TWO, :MONEY, :PAY_TIME, :PROVINCE, :CITY, :REFUND_TIME , :BRAND, :TEL_NUM, :SOURCE_FROM, :BATCH_NUMBER,  to_date(  :CREATE_DATE ,'yyyy-MM-dd hh24:mi:ss'), :CREATE_USER )";
    private static final String SQL_EXCEL_TYPE_ZB = "INSERT INTO ES_ORDER_AUDIT_ZB "
			+ " ()"
			+ " VALUES (S_ES_ORDER_AUDIT_ZB.NEXTVAL,ZB_ID, )";
    //完成
    private static final String SQL_EXCEL_TYPE_BSS = "INSERT INTO ES_ORDER_AUDIT_BUSINESS "
			+ " (AUDIT_BSS_ID,DATA_FROM,AREA,SERIAL_NUMBER,PHONE_NUMBER,BUSINESS_TYPE,BUSI_MONEY,OPERATION_TIME,OPERATOR,SOURCE_FROM,BATCH_NUMBER,CREATE_DATE,CREATE_USER)"
			+ " VALUES (S_ES_ORDER_AUDIT_BUSINESS.NEXTVAL, :DATA_FROM, :AREA, :SERIAL_NUMBER, :PHONE_NUMBER, :BUSINESS_TYPE, :BUSI_MONEY, :OPERATION_TIME, :OPERATOR, :SOURCE_FROM, :BATCH_NUMBER,  to_date(  :CREATE_DATE ,'yyyy-MM-dd hh24:mi:ss'), :CREATE_USER)";

    
    /**
     * 导入完成后进行数据稽核操作
     */
	@Override
	public String auditData(String excel_from, String batch_number) {
		if(EXCEL_FROM_ZBTB.equals(excel_from)){
    		this.auditDataZBTB(batch_number);
    		
    	}else  if(EXCEL_FROM_AGENCY.equals(excel_from)){
    		this.auditDataAGENCY(batch_number);
    		
    	}else  if(EXCEL_FROM_BDTB.equals(excel_from)){
    		
    		this.auditDataBDTB(batch_number);
    	}else  if(EXCEL_TYPE_WO.equals(excel_from)){
    		this.auditDataWO(batch_number);
    		
    	}else  if(EXCEL_TYPE_ZB_BUSI_PAY.equals(excel_from)){
    		
    		
    	}else  if(EXCEL_TYPE_ZB_DETAILS.equals(excel_from)){//总部订单实收款稽核 ： 总部订单详情报表、 ECS报表
    		this.auditDataZB(batch_number);
    	}else  if(EXCEL_TYPE_BSS.equals(excel_from)){//营业款  BSS/CBSS
    		this.auditDataBusi(batch_number);
    	}
		return null;
	}

	

	@Override
	public String dataExport(AuditQueryParame parame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delExcle(String excel_from, String batch_number) {
        String table="";
        String sql="delete from  ";
        table=this.getTableByType(excel_from);
		sql=sql+table+" where batch_number='"+batch_number+"'";
		if(EXCEL_TYPE_BSS.equals(excel_from)){
			sql=sql+"and data_from='BSS'";
    	}else  if(EXCEL_TYPE_CBSS.equals(excel_from)){
    		sql=sql+"and data_from='CBSS'";
    	}
		this.daoSupport.execute(sql);
	}

	//根据类型获取表名
	private String getTableByType(String excel_from){
        String table="";
		if(EXCEL_FROM_ZBTB.equals(excel_from)){
			table="es_order_audit_zbtb";
    	}else  if(EXCEL_FROM_AGENCY.equals(excel_from)){
    		table="es_order_audit_offline_pay";
    	}else  if(EXCEL_FROM_BDTB.equals(excel_from)){
    		table="es_order_audit_bdtb";
    	}else  if(EXCEL_TYPE_WO.equals(excel_from)){
    		table="es_order_audit_wo";
    	}else  if(EXCEL_TYPE_ZB_BUSI_PAY.equals(excel_from)){
    		table="es_order_audit_zb_busi_pay";
    	}else  if(EXCEL_TYPE_ZB_DETAILS.equals(excel_from)){
    		table="es_order_audit_zb_details";
    	}else  if(EXCEL_TYPE_BSS.equals(excel_from)){
    		table="es_order_audit_business";
    	}else  if(EXCEL_TYPE_CBSS.equals(excel_from)){
    		table="es_order_audit_business";
    	}
		return table;
	}
	//完成
     private static final String SQL_QUERY_BUSI_BY_BATCH_NUMBER = "select a.*  from es_order_audit_business a  where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.batch_number=? and DATA_FROM=?";
  


     
     
     //完成
    private static  String SQL_QUERY_BASE_DATA =  " select  o.order_id,o.busi_money,o.paymoney,"
			+" oe.out_tid,oe.AUDIT_UDP,oet.source_type as order_from,oe.pay_type ,oe.order_city_code,oe.audit_busi_money_status,oe.audit_receive_money_status ,"
			+ "decode(oe.Bss_cancel_status ,'1',oe.Bss_cancel_status,oe.Ess_cancel_status)   order_type_return,"//是否是返销的  非2  就是新增
			+"  to_char( item.bss_account_time ,'yyyy-MM-dd') bss_account_time ,item.phone_num,item.invoice_no,"
			+" oet.GoodsName,oet.phone_owner_name,oet.payproviderid,oet.payprovidername ,"
			+" (select d.logi_id  from es_delivery d where d.order_id=o.order_id and d.DELIVERY_TYPE='0') logi_id,"
		    +"(select prod.is_old from es_order_items_prod_ext prod where o.order_id=prod.order_id and rownum=1) is_old,"//这个表可能会有多条记录
			+"(select prod.terminal_num from es_order_items_prod_ext prod where o.order_id=prod.order_id and rownum=1) terminal_num"//这个表可能会有多条记录
			+" ,(select t2.username from es_ORDER_STATS_TACHE eost,ES_ADMINUSER t2  where t2.userid = eost.d_op_id and eost.order_id=o.order_id  and rownum='1') lock_user_id"
			+ " from es_order o,es_order_ext oe,es_order_extvtl oet,es_order_items_ext item"
			+" where o.order_id=oe.order_id(+)  and o.order_id=oet.order_id(+) and o.order_id=item.order_id(+) "
			+ " and item.bss_account_time is not  null "
			+ "and o.source_from='"+ManagerUtils.getSourceFrom()+"'";
    
    private static final String SQL_QUERY_BASE_DATA_FOR_EXP =  " select  oe.out_tid,o.order_id,"
    		+ "decode(oe.Bss_cancel_status ,'1',oe.Bss_cancel_status,oe.Ess_cancel_status)   order_type_return,o.busi_money,o.paymoney,item.bss_account_time,oet.special_busi_type,oe.spread_channel, "
       +"oet.source_type as order_from,oe.pay_type pay_type_ext,oe.order_city_code,oe.audit_busi_money_status,oe.audit_receive_money_status ,decode(oe.src_out_tid,null,oe.out_tid,oe.src_out_tid) store_out_tid ,oe.AUDIT_RELATED_FIELD,"
       +"item.bss_account_time,item.phone_num,item.invoice_no,oet.plan_title,item.GOODS_TYPE,"
       +"oet.GoodsName,oet.phone_owner_name,oet.payproviderid,oet.payprovidername ,"
       +"o.busi_money,o.paymoney,oe.audit_udp,oe.audit_remark,o.ship_name,"
       + "(select  a.pname value_desc from es_dc_public_ext a where a.stype='goods_type' and a.pkey=item.GOODS_TYPE) GOODS_TYPE_names,"
       +"(select d.logi_no  from es_delivery d where d.order_id=o.order_id and d.DELIVERY_TYPE='0') logi_no,"
       +" (select prod.is_old from es_order_items_prod_ext prod where o.order_id=prod.order_id and rownum=1) is_old,"
       +"(select prod.terminal_num from es_order_items_prod_ext prod where o.order_id=prod.order_id and rownum=1) terminal_num"
       +",(select sum(eoab.busi_money)  from es_order_audit_business eoab where eoab.order_id=o.order_id ) busi_money_sum"
       +",(select decode(eoab.data_from,'BSS','3G','CBSS','4G')  from es_order_audit_business eoab where eoab.order_id=o.order_id and rownum=1) data_from "
       +",(select d.ship_mobile  from es_delivery d where d.order_id=o.order_id and d.DELIVERY_TYPE='0') logi_receiver_phone "
       +",(select d.ship_addr  from es_delivery d where d.order_id=o.order_id and d.DELIVERY_TYPE='0') ship_addr"
       +",(select eoazbp.money  from es_order_audit_zb_busi_pay eoazbp where eoazbp.udp_code=oe.audit_udp) zh_money "  
       +",(select substr(eoazd.pay_time,0,10) pay_time  from es_order_audit_zb_details eoazd where eoazd.udp_code=oe.audit_udp) pay_time "
       +",(select eoazd.refund_time  from es_order_audit_zb_details eoazd where eoazd.udp_code=oe.audit_udp) refund_time "
       + " ,(select eoazd.pay_type  from es_order_audit_zb_details eoazd where eoazd.udp_code=oe.audit_udp) paytype "
       +",(select eoazd.payprovidername  from es_order_audit_zb_details eoazd where eoazd.udp_code=oe.audit_udp) new_payprovidername "
       +",(select sum(eoab.busi_money)  from es_order_audit_business eoab where eoab.order_id=o.order_id and eoab.data_from='BSS') bss_money "
       +",(select sum(eoab.busi_money)  from es_order_audit_business eoab where eoab.order_id=o.order_id and eoab.data_from='CBSS') cbss_money "
       +",rank() over (order by o.create_time desc) id "
       + " ,( select t2.username from es_ORDER_STATS_TACHE eost,ES_ADMINUSER t2  where t2.userid = eost.d_op_id and eost.order_id=o.order_id  and rownum='1') lock_user_id"
       +" from es_order o,es_order_ext oe,es_order_extvtl oet,es_order_items_ext item "
       +"  where o.order_id=oe.order_id(+)  and o.order_id=oet.order_id(+) and o.order_id=item.order_id(+)  "
       +" and item.bss_account_time is not  null "
			+ "and o.source_from='"+ManagerUtils.getSourceFrom()+"'";

	@Override
	public List<MoneyAuditBaseDataVo> queryBaseDataList(String batch_number) {
		String sql=SQL_QUERY_BASE_DATA;
		sql=sql+" and trunc(item.bss_account_time)=to_date('"+batch_number+"','yyyy-mm-dd')";
		List<MoneyAuditBaseDataVo> list=this.daoSupport.queryForList(sql, MoneyAuditBaseDataVo.class);
		return list;
	}
	public List<MoneyAuditBaseDataVo> queryBaseDataListByOrderId(String order_id) {
		String sql=SQL_QUERY_BASE_DATA;
		sql=sql+" and o.order_id='"+order_id+"'";
		List<MoneyAuditBaseDataVo> list=this.daoSupport.queryForList(sql, MoneyAuditBaseDataVo.class);
		return list;
	}
	/**
	 * 稽核营业款
	 * @param excel_from
	 * @param batch_number
	 */
	private void auditDataBusi(String batch_number){
		
		
		//1、根据批次号查询基础数据和excel导入的数据
		String sql="select a.*  from es_order_audit_business a  where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.batch_number=? ";
		List<AuditBusinessVO> busi_batch_list=this.daoSupport.queryForList(sql,AuditBusinessVO.class,batch_number);
		List<MoneyAuditBaseDataVo> base_list=this.queryBaseDataList(batch_number);
		
		//2、根据条件做数据匹配关联
		List<Map<String,String>>  batchUpdate=new ArrayList<Map<String,String>>();
		for (AuditBusinessVO auditBusinessVO : busi_batch_list) {
			for (MoneyAuditBaseDataVo baseVO : base_list) {
				if(baseVO.getPhone_num()!=null&&baseVO.getPhone_num().equals(auditBusinessVO.getPhone_number())){//根据号码匹配
					Map<String,String> map=new HashMap<String, String>();
					if(auditBusinessVO.getOperator()!=null&&auditBusinessVO.getOperator().startsWith("沃平台")){//如果操作员名称是：   沃平台- 开头的则用号码匹配，不管正负
						map.put("ORDER_ID",baseVO.getOrder_id() );
						map.put("AUDIT_BSS_ID", auditBusinessVO.getAudit_bss_id());
					}else{
						Float money=auditBusinessVO.getBusi_money();
						if(money==null){money=0F;};//空则认为是0
						if(money>=0&&EcsOrderConsts.BSS_CANCEL_STATUS_0.equals(baseVO.getOrder_type_return())){//正和0    匹配新增类订单
							map.put("ORDER_ID",baseVO.getOrder_id() );
							map.put("AUDIT_BSS_ID", auditBusinessVO.getAudit_bss_id());
						}else if(money<0&&EcsOrderConsts.BSS_CANCEL_STATUS_1.equals(baseVO.getOrder_type_return())){//负的   匹配返销类订单
							map.put("ORDER_ID",baseVO.getOrder_id() );
							map.put("AUDIT_BSS_ID", auditBusinessVO.getAudit_bss_id());
						}
					}
					if(!map.isEmpty()){
						batchUpdate.add(map);
					}
				}
			}
		}
		String update_business=" update es_order_audit_business a set a.order_id = :ORDER_ID where a.audit_bss_id= :AUDIT_BSS_ID";
		this.baseDaoSupport.batchExecuteByListMap(update_business, batchUpdate);
		
		
		//3、根据order_id 分组查询统计金额
		String sql_query_busi_group="select a.order_id,sum(a.busi_money) busi_money from es_order_audit_business a  "
					+" where  a.phone_number is not null  "
					+" and a.batch_number='"+batch_number+"'"
					+" and a.source_from='"+ManagerUtils.getSourceFrom()+"'  "
					+" and a.order_id is not null  " 
					+" group by a.order_id";
		List<AuditBusinessVO> busi_group_list=this.daoSupport.queryForList(sql_query_busi_group,AuditBusinessVO.class);

		//4、订单系统基础数据与导入数据金额比较，并更新订单数据的稽核状态
		
		List<Map<String,Object>> param_list=new ArrayList< Map<String,Object>>();
		for (MoneyAuditBaseDataVo moneyAuditBaseDataVo : base_list) {
			Float base_money=moneyAuditBaseDataVo.getBusi_money();
			if(base_money==null){
				base_money=0.0f;
			}
			String base_order_id=moneyAuditBaseDataVo.getOrder_id();
			for (AuditBusinessVO auditBusinessVO : busi_group_list) {
				String order_id=auditBusinessVO.getOrder_id();
				if(base_order_id.equals(order_id)){//order_id
					Map<String,Object> param_map=new HashMap<String, Object>();
					
					Float  busi_moneys=0.0f;//初始化金额是0
					if(auditBusinessVO.getBusi_money()!=null){
						busi_moneys=auditBusinessVO.getBusi_money();
					}
					String audit_busi_money_status="";
					if(EcsOrderConsts.BSS_CANCEL_STATUS_0.equals(moneyAuditBaseDataVo.getOrder_type_return())){//新增
						if(Math.abs(base_money-busi_moneys)<0.00000001){//比较考虑精度问题
							audit_busi_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
						}else{//不相等
							audit_busi_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
						}
					}else{//返销
						if(Math.abs(base_money+busi_moneys)<0.00000001){//比较考虑精度问题
							audit_busi_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
						}else{//不相等
							audit_busi_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
						}
					}
					
					param_map.put("ORDER_ID", order_id);
					param_map.put("AUDIT_BUSI_MONEY_STATUS", audit_busi_money_status);
					param_list.add(param_map);
				}
			}
		}
		//5、批量更新稽核状态
		if(param_list!=null&&!param_list.isEmpty()){
			String update_status_sql=" update es_order_ext a set a.audit_busi_money_status= :AUDIT_BUSI_MONEY_STATUS "
					+" where a.order_id= :ORDER_ID and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
			this.daoSupport.batchExecuteByListMap(update_status_sql, param_list);
		}
		//6、设置营业款、实收款默认稽核通过的
		this.auditDataBusiDefault(batch_number, base_list);
		
	}
	/**
	 * 设置稽核默认通过的订单
	 */
	private void auditDataBusiDefault(String batch_number,List<MoneyAuditBaseDataVo> base_list){
		 CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String GOODS_CAT_ID = cacheUtil.getConfigInfo("GOODS_CAT_ID");
		
		String goods_cat_id=GOODS_CAT_ID;//"'69010213','69010214','69010216'";//后续多的话可以再考虑配置到数据库里面,滴滴王卡/腾讯王卡/百度卡
		String update_status_sql="update es_order_ext a set a.AUDIT_BUSI_MONEY_STATUS='"+ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3+"',a.AUDIT_RECEIVE_MONEY_STATUS='"+ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3+"' where a.order_id= :ORDER_ID"
				+" and exists (select 1 from es_order_items_ext i where i.order_id=a.order_id and i.goods_cat_id in ("+goods_cat_id+"))";
		List<Map> param_list =new ArrayList<Map>();
		if(base_list!=null&&!base_list.isEmpty()){
			for (MoneyAuditBaseDataVo vo : base_list) {
				Map<String, String> batch_map=new HashMap<String, String>();
				batch_map.put("ORDER_ID", vo.getOrder_id());
				param_list.add(batch_map);
			}
			if(!param_list.isEmpty()){
				this.daoSupport.batchExecuteByListMap(update_status_sql, param_list);
			}
		}
	}
	/**
	 * 总部订单实收款稽核
	 * @param batch_number
	 */
	public void auditDataZBByOrderId(String order_id){/*

		
		//1、查询导入的数据 es_order_audit_zb_details
		String sql_query_by_batch_num="select a.order_num,a.order_id,a.udp_code,a.pay_time,a.PAYPROVIDERNAME,a.pay_bank,a.pay_type ,"
				+"(select p.money  from es_order_audit_zb_busi_pay p where p.udp_code=a.udp_code ) money,"
				+ "(select p.BUSI_TYPE  from es_order_audit_zb_busi_pay p where p.udp_code=a.udp_code ) BUSI_TYPE"
				+" from es_order_audit_zb_details a where a.batch_number='"+batch_number+"' "
				+" and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		
		List<AuditZbDetailsVO> zb_batch_num_list=this.daoSupport.queryForList(sql_query_by_batch_num,AuditZbDetailsVO.class);

		
		//3、订单系统基础数据与导入数据金额比较，并更新订单数据的稽核状态
		
		
		List<MoneyAuditBaseDataVo> base_list=this.queryBaseDataListByOrderId(order_id);
		
		List<Map<String,Object>> param_list=new ArrayList< Map<String,Object>>();
		for (MoneyAuditBaseDataVo moneyAuditBaseDataVo : base_list) {
			Float base_money=moneyAuditBaseDataVo.getPaymoney();
			if(base_money==null){
				base_money=0.0f;
			}
			String base_order_id=moneyAuditBaseDataVo.getOrder_id();
			for (AuditZbDetailsVO auditZbDetailsVO : zb_batch_num_list) {
				String order_id=auditZbDetailsVO.getOrder_id();
				
				if(base_order_id.equals(order_id)&&auditZbDetailsVO.getUdp_code()!=null&&!auditZbDetailsVO.getUdp_code().equals("-")){//order_id
					Map<String,Object> param_map=new HashMap<String, Object>();
					Float  out_pay_moneys=0.0f;//初始化金额是0
					if(auditZbDetailsVO.getMoney()!=null){
						out_pay_moneys=auditZbDetailsVO.getMoney();
					}
					String audit_receive_money_status="";
					
					if(EcsOrderConsts.BSS_CANCEL_STATUS_0.equals(moneyAuditBaseDataVo.getOrder_type_return())){//新增
						if((Math.abs(base_money-out_pay_moneys)<0.00000001)){//比较考虑精度问题
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
						}else{//不相等
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
						}
					}else{//返销
						if((Math.abs(base_money+out_pay_moneys)<0.00000001)){//比较考虑精度问题
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
						}else{//不相等
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
						}
					}	
					param_map.put("AUDIT_RECEIVE_MONEY_STATUS", audit_receive_money_status);
					param_map.put("ORDER_ID", order_id);
					param_map.put("AUDIT_UDP", auditZbDetailsVO.getUdp_code());
					param_list.add(param_map);
				}
			}
		}
		//批量更新稽核状态
		if(param_list!=null&&!param_list.isEmpty()){
			String update_status_sql=" update es_order_ext a set a.AUDIT_RECEIVE_MONEY_STATUS= :AUDIT_RECEIVE_MONEY_STATUS,a.AUDIT_UDP= :AUDIT_UDP,AUDIT_RELATED_FIELD= 'udp' "
					+" where a.order_id= :ORDER_ID and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
			this.daoSupport.batchExecuteByListMap(update_status_sql, param_list);
		}
		
	
	*/}
	/**
	 * 总部订单实收款稽核
	 * @param batch_number
	 */
	private void auditDataZB(String batch_number){
		
		//1、查询导入的数据 es_order_audit_zb_details
		String sql_query_by_batch_num="select a.order_num,a.order_id,a.udp_code,a.pay_time,a.PAYPROVIDERNAME,a.pay_bank,a.pay_type ,"
				+ "p.money,p.BUSI_TYPE "
				//+"(select p.money  from es_order_audit_zb_busi_pay p where p.udp_code=a.udp_code ) money,"
				//+ "(select p.BUSI_TYPE  from es_order_audit_zb_busi_pay p where p.udp_code=a.udp_code ) BUSI_TYPE"
				+" from es_order_audit_zb_details a ,es_order_audit_zb_busi_pay p where a.batch_number='"+batch_number+"' "
				+" and p.udp_code=a.udp_code "
				+ "and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		
		List<AuditZbDetailsVO> zb_batch_num_list=this.daoSupport.queryForList(sql_query_by_batch_num,AuditZbDetailsVO.class);

		
		//3、订单系统基础数据与导入数据金额比较，并更新订单数据的稽核状态
		
		
		List<MoneyAuditBaseDataVo> base_list=this.queryBaseDataList(batch_number);
		
		List<Map<String,Object>> param_list=new ArrayList< Map<String,Object>>();
		for (MoneyAuditBaseDataVo moneyAuditBaseDataVo : base_list) {
			Float base_money=moneyAuditBaseDataVo.getPaymoney();
			if(base_money==null){
				base_money=0.0f;
			}
			String base_out_order_id=moneyAuditBaseDataVo.getOut_tid();
			String base_order_id=moneyAuditBaseDataVo.getOrder_id();
			for (AuditZbDetailsVO auditZbDetailsVO : zb_batch_num_list) {
				String order_id=auditZbDetailsVO.getOrder_num();//总部的单号是10位的
				
				if(base_out_order_id.equals(order_id)&&auditZbDetailsVO.getUdp_code()!=null&&!auditZbDetailsVO.getUdp_code().equals("-")){//order_id
					Map<String,Object> param_map=new HashMap<String, Object>();
					Float  out_pay_moneys=0.0f;//初始化金额是0
					if(auditZbDetailsVO.getMoney()!=null){
						out_pay_moneys=auditZbDetailsVO.getMoney();
					}
					String audit_receive_money_status="";
					
					if(EcsOrderConsts.BSS_CANCEL_STATUS_0.equals(moneyAuditBaseDataVo.getOrder_type_return())){//新增
						if((Math.abs(base_money-out_pay_moneys)<0.00000001)){//比较考虑精度问题
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
						}else{//不相等
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
						}
					}else{//返销
						if((Math.abs(base_money+out_pay_moneys)<0.00000001)){//比较考虑精度问题
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
						}else{//不相等
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
						}
					}	
					param_map.put("AUDIT_RECEIVE_MONEY_STATUS", audit_receive_money_status);
					param_map.put("ORDER_ID", base_order_id);
					param_map.put("AUDIT_UDP", auditZbDetailsVO.getUdp_code());
					param_list.add(param_map);
				}
			}
		}
		//批量更新稽核状态
		if(param_list!=null&&!param_list.isEmpty()){
			String update_status_sql=" update es_order_ext a set a.AUDIT_RECEIVE_MONEY_STATUS= :AUDIT_RECEIVE_MONEY_STATUS,a.AUDIT_UDP= :AUDIT_UDP,AUDIT_RELATED_FIELD= 'udp' "
					+" where a.order_id= :ORDER_ID and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
			this.daoSupport.batchExecuteByListMap(update_status_sql, param_list);
		}
		
	}
	
	/**
	 * 沃平台订单实收款稽核
	 * @param batch_number
	 */
	private void auditDataWO(String batch_number){
		
		//1、查询导入的数据
		String sql_query_by_batch_num="select a.wo_id,a.store_order_id,a.RECEIVE_MONEY,a.ACTIVITY_CODE,a.PAY_CHANNEL,a.RECEIVE_FEE_DATE,a.batch_number"
				+ " from es_order_audit_wo a "
				+ " where a.batch_number='"+batch_number+"' "
				+" and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		
		List<AuditWoVO> zb_batch_num_list=this.daoSupport.queryForList(sql_query_by_batch_num,AuditWoVO.class);

		//3、订单系统基础数据与导入数据金额比较，并更新订单数据的稽核状态
		
		
		List<MoneyAuditBaseDataVo> base_list=this.queryBaseDataList(batch_number);
		
		List<Map<String,Object>> param_list=new ArrayList< Map<String,Object>>();
		for (MoneyAuditBaseDataVo moneyAuditBaseDataVo : base_list) {
			Float base_money=moneyAuditBaseDataVo.getPaymoney();
			if(base_money==null){
				base_money=0.0f;
			}
			String base_order_id=moneyAuditBaseDataVo.getOrder_id();
			String base_out_order_id=moneyAuditBaseDataVo.getOut_tid();
			for (AuditWoVO auditWoVO : zb_batch_num_list) {
				String order_id=auditWoVO.getStore_order_id();//
				if(base_out_order_id.equals(order_id)){//order_id
					Map<String,Object> param_map=new HashMap<String, Object>();
					
					Float  out_pay_moneys=0.0f;//初始化金额是0
					if(auditWoVO.getReceive_money()!=null){
						out_pay_moneys=auditWoVO.getReceive_money();
					}
					String audit_receive_money_status="";
					if(EcsOrderConsts.BSS_CANCEL_STATUS_0.equals(moneyAuditBaseDataVo.getOrder_type_return())){//新增
						if((Math.abs(base_money-out_pay_moneys)<0.00000001)){//比较考虑精度问题
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
						}else{//不相等
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
						}
					}else{//返销
						if((Math.abs(base_money+out_pay_moneys)<0.00000001)){//比较考虑精度问题
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
						}else{//不相等
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
						}
					}	
					param_map.put("ORDER_ID", base_order_id);
					param_map.put("AUDIT_RECEIVE_MONEY_STATUS", audit_receive_money_status);
					param_list.add(param_map);
				}
			}
		}
		//批量更新稽核状态
		if(param_list!=null&&!param_list.isEmpty()){
			String update_status_sql=" update es_order_ext a set a.AUDIT_RECEIVE_MONEY_STATUS= :AUDIT_RECEIVE_MONEY_STATUS,AUDIT_RELATED_FIELD= 'order_id' "
					+" where a.order_id= :ORDER_ID and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
			this.daoSupport.batchExecuteByListMap(update_status_sql, param_list);
		}
		
	}
	/**
	 * 本地淘宝实收款稽核
	 * @param batch_number
	 */
	private void auditDataBDTB(String batch_number){
		 //把关联到的order_id  写入到 es_order_audit_bdtb  base_order_id
		 // 第一：本身就是订单系统的外部订单号 （包括补录的）
         String up_by_out_tid="update es_order_audit_bdtb a set a.base_order_id=(select e.order_id  from es_order_ext e where e.SRC_OUT_TID=a.store_order_id )"
								+" where exists (select 1  from es_order_ext e where e.out_tid=a.store_order_id )"
								+" and a.source_from='"+ManagerUtils.getSourceFrom()+"'"
								+" and a.batch_number='"+batch_number+"'";
         
         String up_by_src_out_tid="update es_order_audit_bdtb a set a.base_order_id=(select e.order_id  from es_order_ext e where e.SRC_OUT_TID=a.store_order_id )"
					+" where exists (select 1  from es_order_ext e where e.SRC_OUT_TID=a.store_order_id )"
					+" and a.source_from='"+ManagerUtils.getSourceFrom()+"'"
					+" and a.batch_number='"+batch_number+"'";
         
         this.daoSupport.execute(up_by_out_tid);
         this.daoSupport.execute(up_by_src_out_tid);
		
		
		//1、查询导入的数据
		String sql_query_by_batch_num="select a.BDTB_ID,a.store_order_id,a.RECEIVE_MONEY,a.ACTIVITY_CODE,a.PAY_CHANNEL,a.RECEIVE_FEE_DATE,a.batch_number,base_order_id,"
				+ "(select  o.paymoney from es_order o where o.order_id=a.base_order_id ) base_paymoney,"
				+ "decode(oe.Bss_cancel_status ,'1',oe.Bss_cancel_status,oe.Ess_cancel_status)   order_type_return"
				+ " from es_order_audit_bdtb a ,es_order_ext oe "
				+ " where oe.order_id(+)=a.base_order_id "
				+ " and a.batch_number='"+batch_number+"' "
				+" and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		
		List<AuditBdtbVO> zb_batch_num_list=this.daoSupport.queryForList(sql_query_by_batch_num,AuditBdtbVO.class);

		
		List<Map<String,Object>> param_list=new ArrayList< Map<String,Object>>();
	
			for (AuditBdtbVO auditZbtbVO : zb_batch_num_list) {
			
				String base_order_id=auditZbtbVO.getBase_order_id();
				if(!StringUtil.isEmpty(base_order_id)){//order_id
					Map<String,Object> param_map=new HashMap<String, Object>();
					
					
					Float  out_pay_moneys=0.0f;//初始化金额是0
					Float  base_money=0.0f;//初始化金额是0
					if(auditZbtbVO.getReceive_money()!=null){
						out_pay_moneys=auditZbtbVO.getReceive_money();
					}
					if(auditZbtbVO.getBase_paymoney()!=null){
						base_money=auditZbtbVO.getBase_paymoney();
					}
					String audit_receive_money_status="";
					if(EcsOrderConsts.BSS_CANCEL_STATUS_0.equals(auditZbtbVO.getOrder_type_return())){//新增
						if((Math.abs(base_money-out_pay_moneys)<0.00000001)){//比较考虑精度问题
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
						}else{//不相等
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
						}
					}else{//返销
						if((Math.abs(base_money+out_pay_moneys)<0.00000001)){//比较考虑精度问题
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
						}else{//不相等
							audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
						}
					}
					param_map.put("ORDER_ID", base_order_id);
					param_map.put("AUDIT_RECEIVE_MONEY_STATUS", audit_receive_money_status);
					param_list.add(param_map);
				}
			}
		
		//批量更新稽核状态
			if(param_list!=null&&!param_list.isEmpty()){
				String update_status_sql=" update es_order_ext a set a.AUDIT_RECEIVE_MONEY_STATUS= :AUDIT_RECEIVE_MONEY_STATUS,AUDIT_RELATED_FIELD= 'order_id' "
						+" where a.order_id= :ORDER_ID and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
				this.daoSupport.batchExecuteByListMap(update_status_sql, param_list);
			}
		
	}
	
	/**
	 * 顺丰代收款实收款稽核
	 * @param batch_number
	 */
	private void auditDataAGENCY(String batch_number){
		
		//1、查询导入的数据
		String sql_query_by_batch_num="select a.HWB_NO,a.COD_AMOUN,a.batch_number,d.order_id,"
				+ "(select o.paymoney  from es_order o where d.order_id=o.order_id and o.order_id is not null) paymoney"
				+ " from es_order_audit_offline_pay a,es_delivery d "
				+ " where a.hwb_no= d.logi_no(+) and a.batch_number='"+batch_number+"' "
				+" and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		
		List<AuditOfflinePayVO> batch_num_list=this.daoSupport.queryForList(sql_query_by_batch_num,AuditOfflinePayVO.class);

		//3、订单系统基础数据与导入数据金额比较，并更新订单数据的稽核状态
	
		List<Map<String,Object>> param_list=new ArrayList< Map<String,Object>>();
		
			for (AuditOfflinePayVO auditOfflinePayVO : batch_num_list) {
				Float cod_amoun=auditOfflinePayVO.getCod_amoun();//顺丰金额
				Float paymoney=auditOfflinePayVO.getPaymoney();//订单系统金额
				String order_id=auditOfflinePayVO.getOrder_id();//订单号
				if(order_id!=null){//order_id
					Map<String,Object> param_map=new HashMap<String, Object>();
					if(paymoney==null){
						paymoney=0.0f;
					}
					if(cod_amoun==null){
						cod_amoun=0.0f;
					}
					String audit_receive_money_status="";
					if((Math.abs(paymoney-cod_amoun)<0.00000001)){//比较考虑精度问题
						audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
					}else{//不相等
						audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
					}
					
					param_map.put("ORDER_ID", order_id);
					param_map.put("AUDIT_RECEIVE_MONEY_STATUS", audit_receive_money_status);
					param_list.add(param_map);
				}
			}
		
			//批量更新稽核状态
			if(param_list!=null&&!param_list.isEmpty()){
				String update_status_sql=" update es_order_ext a set a.AUDIT_RECEIVE_MONEY_STATUS= :AUDIT_RECEIVE_MONEY_STATUS,AUDIT_RELATED_FIELD= 'logi_no' "
						+" where a.order_id= :ORDER_ID and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
				this.daoSupport.batchExecuteByListMap(update_status_sql, param_list);
			}
		
	}
	
	/**
	 * 总部淘宝实收款稽核
	 * @param batch_number
	 */
	private void auditDataZBTB(String batch_number){
		
		//1、查询导入的数据
		String sql_query_by_batch_num="select a.money,a.store_order_id,"
				+ "(select o.paymoney  from es_order o,es_order_ext e where e.order_id=o.order_id and e.out_tid=a.store_order_id) paymoney"
				+ " from es_order_audit_zbtb a "
				+ " where  a.batch_number='"+batch_number+"' "
				+" and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		
		List<AuditZbtbVO> batch_num_list=this.daoSupport.queryForList(sql_query_by_batch_num,AuditZbtbVO.class);

		//3、订单系统基础数据与导入数据金额比较，并更新订单数据的稽核状态
	
		List<Map<String,Object>> param_list=new ArrayList< Map<String,Object>>();
		
			for (AuditZbtbVO auditOfflinePayVO : batch_num_list) {
				Float receive_money=auditOfflinePayVO.getMoney();//淘宝金额
				Float paymoney=auditOfflinePayVO.getPaymoney();//订单系统金额
				String out_order_id=auditOfflinePayVO.getStore_order_id();//外部订单号
				if(out_order_id!=null){//order_id
					Map<String,Object> param_map=new HashMap<String, Object>();
					if(paymoney==null){
						paymoney=0.0f;
					}
					if(receive_money==null){
						receive_money=0.0f;
					}
					String audit_receive_money_status="";
					if((Math.abs(paymoney-receive_money)<0.00000001)){//比较考虑精度问题
						audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
					}else{//不相等
						audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_1;
					}
					
					param_map.put("OUT_TID", out_order_id);
					param_map.put("AUDIT_RECEIVE_MONEY_STATUS", audit_receive_money_status);
					param_list.add(param_map);
				}
			}
		
			//批量更新稽核状态
			if(param_list!=null&&!param_list.isEmpty()){
				String update_status_sql=" update es_order_ext a set a.AUDIT_RECEIVE_MONEY_STATUS= :AUDIT_RECEIVE_MONEY_STATUS,AUDIT_RELATED_FIELD= 'order_id' "
						+" where a.OUT_TID= :OUT_TID and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
				this.daoSupport.batchExecuteByListMap(update_status_sql, param_list);
			}
		
	}
	
	
	public void export(List list,String fileTitile,String [] custCols,String [] str,HttpServletResponse response){
		  ExportExcelHelper exh = new ExportExcelHelper();
		  
		  
		  OutputStream os =null;
		  WritableWorkbook wbook = null;
		  
		  try {
		    String title = "";
		    
		    for(int i = 0; i < custCols.length; i++ ){
		        title += custCols[i] + ",";
		    }
		      
		    title = title.substring(0, title.length() -1);      
		    title = new String(title.getBytes("gb2312"), "gbk");
		    
		    String titles[] = title.split(",");
		    String fileName = new String(fileTitile.getBytes("gb2312"), "iso-8859-1"); // 乱码解决
		    os = response.getOutputStream();// 取得输出流
		    response.reset();// 清空输出流
		    response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");// 设定输出文件头
		    response.setContentType("application/msexcel;charset=utf-8");// 定义输出类型
		    response.setCharacterEncoding("utf-8");
		    //response.setContentType("application/vnd.ms-excel;charset=GB18030");// 定义输出类型
		    String path=Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1,Thread.currentThread().getContextClassLoader().getResource("").getPath().lastIndexOf("/WEB-INF"));
		    wbook =  Workbook.createWorkbook(new File(path+"/shop/admin/未匹配报表.xls")); 
		    WritableSheet wsheet = wbook.createSheet(fileTitile, 0); // sheet名称
		
		    // 设置excel标题
		    WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
		        UnderlineStyle.NO_UNDERLINE, Colour.RED);
		    WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		    wcfFC.setBackground(Colour.AQUA);
		    wcfFC = new WritableCellFormat(wfont);
		
		    // 开始标题
		    for (int i = 0; i < titles.length; i++) {
		      wsheet.addCell(new Label(i, 0, titles[i], wcfFC));
		      wsheet.setColumnView(i, 23);// 设置宽度为23
		    }
		    
		    //生成内容
		      if(list != null && !list.isEmpty()){
		        for(int j=0;j<list.size();j++){
		          Object obj = list.get(j);
		          //获取对象的属性数及属性值
		          for(int k=0;k<str.length;k++){
		            String name = str[k];
		            String attr = String.valueOf(exh.getFieldValueByName(name,obj));
		            if("null".equals(attr) || StringUtil.isEmpty(attr))
		            	attr ="";
		            Label label = new Label(k,j+1,attr);
		            wsheet.addCell(label);
		          }
		       	}
		       }
		    
		    // 主体内容生成结束
		    wbook.write(); // 写入文件
		  } catch (Exception ex) {
		    ex.printStackTrace();
		  }finally {
		    if(wbook != null) {
		      try {
		        wbook.close();
		      } catch (WriteException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      }
		    }
		    
		    if(os != null) {
		      try {
				os.close();  // 关闭流
		      }catch (Exception e) {
		        e.printStackTrace();
		      }
		    }
		  }
		}

	@Override
	public String auditBusiMoneyByOrderId(String order_id) {
		String msg="";
		//1、根据order_id  查询基础数据
		List<MoneyAuditBaseDataVo> base_list=this.queryBaseDataListByOrderId(order_id);
		//2、根据order_id  累加统计营业款金额总值
		String sql_query_busi_group="select a.order_id,sum(a.busi_money) busi_money from es_order_audit_business a  "
					+" where  a.phone_number is not null  "
					+" and a.order_id='"+order_id+"'"
					+" and a.source_from='"+ManagerUtils.getSourceFrom()+"'  "
					+" and a.order_id is not null  " 
					+" group by a.order_id";
		List<AuditBusinessVO> busi_group_list=this.daoSupport.queryForList(sql_query_busi_group,AuditBusinessVO.class);

		//3、金额比较、区分是新增单还是返销订单
		List<Map<String,Object>> param_list=new ArrayList< Map<String,Object>>();
		if(base_list==null||base_list.size()==0){
			msg="查询不到订单数据";
			return msg;
		}
        if(busi_group_list==null||busi_group_list.size()==0){
			
        	 Map<String,Object> param_map=new HashMap<String, Object>();
				param_map.put("ORDER_ID", order_id);
				param_map.put("AUDIT_BUSI_MONEY_STATUS", ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_2);
				param_list.add(param_map);
        	msg="无关联流水，订单状态已改为待复核";
			
		}else{
			MoneyAuditBaseDataVo moneyAuditBaseDataVo=base_list.get(0);
			AuditBusinessVO auditBusinessVO=busi_group_list.get(0);
			Float  base_money=0.0f;//初始化金额是0
			Float  busi_moneys=0.0f;//初始化金额是0
			if(moneyAuditBaseDataVo.getBusi_money()!=null){
				base_money=moneyAuditBaseDataVo.getBusi_money();
			}
			if(auditBusinessVO.getBusi_money()!=null){
				busi_moneys=auditBusinessVO.getBusi_money();
			}
			//比较
			String audit_busi_money_status="";
			if(EcsOrderConsts.BSS_CANCEL_STATUS_0.equals(moneyAuditBaseDataVo.getOrder_type_return())){//新增
				if(Math.abs(base_money-busi_moneys)<0.00000001){//比较考虑精度问题
					audit_busi_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
				}else{//不相等
					audit_busi_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_2;//待复核
				}
			}else{//返销
				if(Math.abs(base_money+busi_moneys)<0.00000001){//比较考虑精度问题
					audit_busi_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
				}else{//不相等
					audit_busi_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_2;//待复核
				}
			}
			 Map<String,Object> param_map=new HashMap<String, Object>();
				param_map.put("ORDER_ID", order_id);
				param_map.put("AUDIT_BUSI_MONEY_STATUS", audit_busi_money_status);
				param_list.add(param_map);
			
		}
		
		
		//4、更新稽核状态
  		if(param_list!=null&&!param_list.isEmpty()){
  			String update_status_sql=" update es_order_ext a set a.audit_busi_money_status= :AUDIT_BUSI_MONEY_STATUS "
  					+" where a.order_id= :ORDER_ID and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
  			this.daoSupport.batchExecuteByListMap(update_status_sql, param_list);
  		}
		return msg;
	}
	@Override
	public String auditReceiveByOrderId(String order_id) {
		String msg="";
		//1、根据order_id  查询基础数据
		List<MoneyAuditBaseDataVo> base_list=this.queryBaseDataListByOrderId(order_id);
		if(base_list==null||base_list.size()==0){
			msg="查询不到订单数据";
			return msg;
		}
		MoneyAuditBaseDataVo moneyAuditBaseDataVo=base_list.get(0);
		String udp_code=moneyAuditBaseDataVo.getAudit_udp();
		if(StringUtil.isEmpty(udp_code)){
			msg="udp 为空不能稽核";
			return msg;
		}
		//2、根据udp 号查询实收款数据
		String sql_query_by_batch_num="select a.order_num,a.order_id,a.udp_code,a.pay_time,a.PAYPROVIDERNAME,a.pay_bank,a.pay_type ,"
				+"(select p.money  from es_order_audit_zb_busi_pay p where p.udp_code=a.udp_code ) money,"
				+ "(select p.BUSI_TYPE  from es_order_audit_zb_busi_pay p where p.udp_code=a.udp_code ) BUSI_TYPE"
				+" from es_order_audit_zb_details a where a.udp_code='"+udp_code+"' "
				+" and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		
		List<AuditZbDetailsVO> zb_batch_num_list=this.daoSupport.queryForList(sql_query_by_batch_num,AuditZbDetailsVO.class);
		if(zb_batch_num_list==null||zb_batch_num_list.size()==0){
			msg="根据udp号查询不到收款记录流水";
			return msg;
		}
		
		//3、数据做对比
		List<Map<String,Object>> param_list=new ArrayList< Map<String,Object>>();
		AuditZbDetailsVO ad=zb_batch_num_list.get(0);
		
		Float  pay_money=0.0f;//初始化金额是0
		Float  receive_moneys=0.0f;//初始化金额是0
		if(moneyAuditBaseDataVo.getPaymoney()!=null){
			 pay_money=moneyAuditBaseDataVo.getPaymoney();
		}
		if(ad.getMoney()!=null){
			receive_moneys=ad.getMoney();
		}
		//比较
		String audit_receive_money_status="";
		if(EcsOrderConsts.BSS_CANCEL_STATUS_0.equals(moneyAuditBaseDataVo.getOrder_type_return())){//新增
			if(Math.abs(pay_money-receive_moneys)<0.00000001){//比较考虑精度问题
				audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
			}else{//不相等
				audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_2;//待复核
			}
		}else{//返销
			if(Math.abs(pay_money+receive_moneys)<0.00000001){//比较考虑精度问题
				audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_3;
			}else{//不相等
				audit_receive_money_status=ZjEcsOrderConsts.AUDIT_BUSI_MONEY_STATUS_2;//待复核
			}
		}
		 Map<String,Object> param_map=new HashMap<String, Object>();
			param_map.put("ORDER_ID", order_id);
			param_map.put("AUDIT_RECEIVE_MONEY_STATUS", audit_receive_money_status);
			param_list.add(param_map);
		//4、更新稽核状态
			
		//批量更新稽核状态
		if(param_list!=null&&!param_list.isEmpty()){
			String update_status_sql=" update es_order_ext a set a.AUDIT_RECEIVE_MONEY_STATUS= :AUDIT_RECEIVE_MONEY_STATUS,AUDIT_RELATED_FIELD= 'udp' "
					+" where a.order_id= :ORDER_ID and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
			this.daoSupport.batchExecuteByListMap(update_status_sql, param_list);
		}
		return msg;
	}
	
	
	@Transactional 
    public String importacion_orderInput(File file,String file_name) throws Exception {
		String msg="0";
    	String sql=null;
    	List<Map<String,String>>  batchList=null;
		Map<String,String> addMap=new HashMap<String, String>();
		addMap.put("IMPORT_USER_ID", ManagerUtils.getAdminUser().getUserid());
		String id = addMap.put("ID", this.baseDaoSupport.getSequences("seq_order_input"));
		try {
			addMap.put("CREATE_TIME",DateUtil.getTime2() );
		} catch (FrameException e) {
			e.printStackTrace();
		}
		sql=" insert into ES_ORDER_INPUT_MID (OUT_ORDER_ID,INPUT_INST_ID,ACC_NBR,IS_REALNAME,SPECIAL_BUSI_TYPE,CUST_NAME,CERTI_TYPE,CERTI_NUM,OLD_PLAN_TITLE,ORDER_CITY_CODE,SOURCE_FROM,IS_OLD,OFFER_EFF_TYPE,PAY_METHOD,PROD_OFFER_TYPE,PROD_OFFER_CODE,IS_ATTACHED,PLAN_TITLE,CONTRACT_MONTH,PAY_MONEY,ESS_MONEY,SHIP_NAME,SHIP_PHONE,SHIP_ADDR,LOGI_NO,INVOICE_NO,TERMINAL_NUM,BUYER_MESSAGE,ICCID,SERVICE_REMARKS,CREATE_TIME,IMPORT_USER_ID,STATUS,DEAL_NUM,SHIP_CITY,SHIP_COUNTY,ID) "+
			" values (:OUT_ORDER_ID,:INPUT_INST_ID,:ACC_NBR,:IS_REALNAME,:SPECIAL_BUSI_TYPE,:CUST_NAME,:CERTI_TYPE,:CERTI_NUM,:OLD_PLAN_TITLE,:ORDER_CITY_CODE,:SOURCE_FROM,:IS_OLD,:OFFER_EFF_TYPE,:PAY_METHOD,:PROD_OFFER_TYPE,:PROD_OFFER_CODE,:IS_ATTACHED,:PLAN_TITLE,:CONTRACT_MONTH,:PAY_MONEY,:ESS_MONEY,:SHIP_NAME,:SHIP_PHONE,:SHIP_ADDR,:LOGI_NO,:INVOICE_NO,:TERMINAL_NUM,:BUYER_MESSAGE,:ICCID,:SERVICE_REMARKS,:CREATE_TIME ,:IMPORT_USER_ID,:STATUS,:DEAL_NUM,:SHIP_CITY,:SHIP_COUNTY,:ID) ";
		batchList=this.getDataOrderInput(file, addMap, file_name);
		for(int i=0;i<batchList.size();i++){
			batchList.get(i).put("INPUT_INST_ID", this.baseDaoSupport.getSequences("seq_order_input"));
			batchList.get(i).put("OUT_ORDER_ID", "ZJ"+this.baseDaoSupport.getSequences("seq_out_order_id"));
		}
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String max_num = cacheUtil.getConfigInfo("INPUT_ORDER_NUM_MAX");
		if(batchList.size()<=Integer.parseInt(max_num)){
			if(!StringUtil.isEmpty(sql)&&batchList!=null&&!batchList.isEmpty()){
	    		this.baseDaoSupport.batchExecuteByListMap(sql, batchList);
	    		msg = batchList.size()+"#";
	    	}
		}else{
			msg = "导入数量超过"+max_num+",导入失败";
		}
		return msg;
	}
	
	private List<Map<String,String>> getDataOrderInput(File file,Map<String,String> addMap,String file_name) throws Exception{
 		Map<String,String> fieldMap=new HashMap<String, String>();
 		
 		fieldMap.put( "OUT_ORDER_ID","外系统单号");
 		fieldMap.put( "ACC_NBR","用户号码");
 		fieldMap.put("IS_REALNAME","是否实名");
 		fieldMap.put("SPECIAL_BUSI_TYPE","业务类型");
 		fieldMap.put("CUST_NAME","用户姓名");
 		fieldMap.put("CERTI_TYPE","证件类型" );
 		fieldMap.put("CERTI_NUM","证件号码" );
 		fieldMap.put("OLD_PLAN_TITLE","当前套餐" );
 		fieldMap.put("ORDER_CITY_CODE" ,"地市");
 		fieldMap.put("SOURCE_FROM","订单来源" );
 		fieldMap.put("IS_OLD","新老用户");
 		fieldMap.put("OFFER_EFF_TYPE","首月");
 		fieldMap.put("PAY_METHOD","支付方式");
 		fieldMap.put("PROD_OFFER_TYPE","商品分类" );
 		fieldMap.put("PROD_OFFER_CODE","商品名称" );
 		fieldMap.put("IS_ATTACHED","是否副卡" );
 		fieldMap.put("PLAN_TITLE","套餐名称" );
 		fieldMap.put("CONTRACT_MONTH","合约期" );
 		fieldMap.put("PAY_MONEY","商城实收" );
 		fieldMap.put("ESS_MONEY","营业款" );
 		fieldMap.put("SHIP_NAME","收货人姓名" );
 		fieldMap.put("SHIP_PHONE","收货人电话" );
 		fieldMap.put("SHIP_ADDR","收货地址" );
 		fieldMap.put("SHIP_CITY","收货地市" );
 		fieldMap.put("SHIP_COUNTY","收货县分" );
 		fieldMap.put("LOGI_NO","物流单号" );
 		fieldMap.put("INVOICE_NO","发票号码" );
 		fieldMap.put("TERMINAL_NUM","手机串号" );
 		fieldMap.put("BUYER_MESSAGE","买家留言" );
 		fieldMap.put("ICCID","ICCID" );
 		fieldMap.put("SERVICE_REMARKS","备注" );
 		
 		addMap.put("STATUS", "WCL");
 		addMap.put("DEAL_NUM", "0");
 		
 		int titleInRow=1;
    	return MoneyAuditExcelUtil.getBatchMap(file, fieldMap, addMap,file_name,titleInRow);
     }
	
	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}




	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}




	public IRegionService getRegionService() {
		return regionService;
	}




	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}




	
	
	
}
