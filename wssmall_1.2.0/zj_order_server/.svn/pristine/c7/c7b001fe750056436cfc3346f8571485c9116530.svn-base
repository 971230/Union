package com.ztesoft.net.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import util.MoneyAuditCSVUtils;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderStatusQryReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderStatusQryResp;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AuditZBParams;
import com.ztesoft.net.model.AuditZBVO;
import com.ztesoft.net.service.IAuditZBManager;

public class AuditZBManager extends BaseSupport implements IAuditZBManager{

	private IDcPublicInfoManager dcPublicInfoManager;
	
	public Page showList(AuditZBParams auditZBParams,int pageNo, int pageSize){
		String sql = "select t.out_tid,t.audit_num,t.audit_type,t.audit_status,t.audit_time,t.crawler_status,t.crawler_time,t.crawler_result from es_order_audit_zb t where t.source_from ='"+ManagerUtils.getSourceFrom()+"'";
		if(auditZBParams!=null){
			if(!StringUtils.isEmpty(auditZBParams.getOut_tid())){
				sql += " and t.out_tid='"+auditZBParams.getOut_tid()+"'";
			}
			if(!StringUtils.isEmpty(auditZBParams.getAudit_status())){
				if(StringUtil.equals("0", auditZBParams.getAudit_status())){
					sql += " and (t.audit_status='"+auditZBParams.getAudit_status()+"' or t.audit_status is null)";
				}else if(StringUtil.equals("1", auditZBParams.getAudit_status())||StringUtil.equals("2", auditZBParams.getAudit_status())){
					sql += " and t.audit_status='"+auditZBParams.getAudit_status()+"'";
				}
				
			}
			if(!StringUtils.isEmpty(auditZBParams.getCrawler_status())){
				if(StringUtil.equals("0", auditZBParams.getCrawler_status())){
					sql += " and (t.crawler_status='"+auditZBParams.getCrawler_status()+"' or t.crawler_status is null)";
				}else if(StringUtil.equals("1", auditZBParams.getCrawler_status())||StringUtil.equals("2", auditZBParams.getCrawler_status())){
					sql += " and t.crawler_status='"+auditZBParams.getCrawler_status()+"'";
				}
				
			}
		}
		
		Page page = daoSupport.queryForPage(sql, pageNo, pageSize, AuditZBVO.class);
		return page;
		
	}
	
	public String importacion(File file,String file_name) {
    	
    	String msg="0";
    	String sql=null;
    	List<Map<String,String>>  batchList=null;
		Map<String,String> addMap=new HashMap<String, String>();
		addMap.put("SOURCE_FROM", ManagerUtils.getSourceFrom());
		addMap.put("AUDIT_NUM", "0");
    	sql=SQL_EXCEL_FROM_ZB;
    	try {
			batchList=this.getDataZB(file, addMap, file_name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    		msg=batchList.size()+"#out_tid";
    	}
    	 
    	logger.info(msg);
		return msg;
    }
	
	
	private List<Map<String,String>> getDataZB(File file,Map<String,String> addMap,String file_name) throws Exception{
		Map<String,String> fieldMap=new HashMap<String, String>();
		fieldMap.put( "订单号","OUT_TID");
		
		int titleInRow=1;
	return MoneyAuditCSVUtils.getBatchMap(file, titleInRow, fieldMap, addMap,true);
	}
	
	public void auditByHand(String out_tids){
		AdminUser user = ManagerUtils.getAdminUser();
		String sql = "update es_order_audit_zb set audit_num = nvl(audit_num,0)+1,audit_status = '1', audit_time = sysdate,oper_id = '"+user.getUserid()+"',audit_type='hand' where out_tid= ?";
		if(!StringUtils.isEmpty(out_tids)){
			String[] str = out_tids.split(",");
			
			for(int j=0;j<str.length;j++){
				String out_tid = str[j];
				this.baseDaoSupport.execute(sql, out_tid);
			}
		}
		
		
	}
	public String checkStatus(String out_tids){
		String flag = "0";
		String sql = "select count(t.out_tid) from es_order_audit_zb t where (nvl(t.audit_num,0)<3 or t.audit_status='1')";
		String new_out_tids = "";
		if(!StringUtils.isEmpty(out_tids)){
			String[] str = out_tids.split(",");
			
			for(int j=0;j<str.length;j++){
				String order_id = str[j];
				if(!StringUtils.isEmpty(new_out_tids)){
					new_out_tids = new_out_tids+",'"+order_id+"'";
				}else{
					new_out_tids = order_id;
				}
				
			}
		}	
		if(!StringUtils.isEmpty(new_out_tids)){
			sql +=" and t.out_tid in ("+new_out_tids+")";
		}
		Integer i = this.baseDaoSupport.queryForInt(sql, null);
		if(i>0){
			flag = "1";
		}
		return flag;
		
	}
	
	public String getOrderDesc(String order_id,String flow_trace_id){
		String sql = "select to_char(wm_concat(t.comments))  from es_order_handle_logs t "
				+ " where t.order_id = '"+order_id+"' and t.flow_trace_id = '"+flow_trace_id+"' ";
		String order_desc = this.baseDaoSupport.queryForString(sql, null);
		return order_desc;
		
	}
	
	public Page qryBSSOrderStatus(AuditZBParams auditZBParams,int pageNo, int pageSize){
		Page page = new Page();
		List status_list = new ArrayList();
		Map map = new HashMap();
		String order_id = "";
		String out_tid = "";
		String pname = "";
		OrderTreeBusiRequest ordertree = null;
		if(auditZBParams!=null){
			order_id = auditZBParams.getOrder_id();
			if(StringUtil.isEmpty(order_id)){
				out_tid = auditZBParams.getOut_tid();
				if(!StringUtil.isEmpty(out_tid)){
					ordertree = CommonDataFactory.getInstance().getOrderTreeByOutId(out_tid);
					if(ordertree!=null){
						order_id = ordertree.getOrder_id();
					}
				}
			}else{
				ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
				if(ordertree!=null){
					out_tid = ordertree.getOrderExtBusiRequest().getOut_tid();
				}
			}
			if(ordertree!=null){
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				OrderStatusQryReq req = new OrderStatusQryReq();
				req.setNotNeedReqStrOrderId(order_id);
				OrderStatusQryResp resp = client.execute(req, OrderStatusQryResp.class);
				if(!StringUtil.isEmpty(resp.getCode())&&StringUtil.equals("00000", resp.getCode())){
					String status = resp.getRespJson().getOrder_status();
					DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
					List<Map> list = dcPublicCache.getList("DIC_BSS_ORDER_STATUS");
			       
			        for(int i=0;i<list.size();i++){
			        	if(StringUtil.equals(status, (String)list.get(i).get("pkey"))){
			        		pname = (String)list.get(i).get("pname");
			        		break;
			        	}
			        }
			        if(!StringUtil.isEmpty(pname)){
			        	map.put("result", "0");
			        	map.put("msg", "订单状态："+pname);
			        }else{
			        	map.put("result", "1");
			        	map.put("msg", "没有定义该状态的静态值，状态为："+status);
			        	pname = status;
			        }
				}else{
					map.put("result", "1");
		        	map.put("msg", "接口调用失败："+resp.getMsg());
				}
				map.put("order_id", order_id);
				map.put("out_tid", out_tid);
				map.put("status", pname);
				status_list.add(map);
				page.setResult(status_list);
				page.setPageSize(pageSize);
				page.setTotalCount(status_list.size());
			}else{
				map.put("result", "1");
	        	map.put("msg", "找不到订单！");
	        	map.put("order_id", "");
				map.put("out_tid", "");
				map.put("status", "");
				page.setPageSize(pageSize);
				page.setTotalCount(0);
			}
		}
		return page;
		
	}
	
	private String SQL_EXCEL_FROM_ZB = "INSERT INTO ES_ORDER_AUDIT_ZB (OUT_TID,AUDIT_NUM,SOURCE_FROM) VALUES(:OUT_TID,:AUDIT_NUM,:SOURCE_FROM)";
}
