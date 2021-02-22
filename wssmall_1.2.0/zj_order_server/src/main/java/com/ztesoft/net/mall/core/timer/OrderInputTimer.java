package com.ztesoft.net.mall.core.timer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import params.ZteRequest;
import params.ZteResponse;
import rule.params.coqueue.req.CoQueueRuleReq;
import zte.params.orderctn.resp.OrderCtnResp;

import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.OrderInputVO;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.service.IOrderExtManager;

public class OrderInputTimer {

	private String sql=" select t.* from es_order_input_mid t where t.source_from is not null and t.status<>'CLZ' ";
	
	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String input_max = cacheUtil.getConfigInfo("INPUT_MAX");
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		Page coPage = support.queryForPageNoCount(sql, 1, Integer.parseInt(input_max), OrderInputVO.class, null);
		List<OrderInputVO>  inputList = coPage.getResult();
		if(inputList.size()>0){
				
				
			CoQueueRuleReq coQueueRuleReq = null;
			for (OrderInputVO inputOrder : inputList) {
				/*String lock_sql = " update es_order_input_mid set status= ?,deal_num=? where input_inst_id = '"+inputOrder.getInput_inst_id()+"' ";
				support.execute(lock_sql, "CLZ",inputOrder.getDeal_num());*/
				Map<String, Object> param = new HashMap<String, Object>();
				try {
					BeanUtils.bean2Map(param, inputOrder);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				coQueueRuleReq = new CoQueueRuleReq();
				coQueueRuleReq.setObject_id(inputOrder.getInput_inst_id());
				coQueueRuleReq.setParams(param);
				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(coQueueRuleReq) {
						@Override
						public ZteResponse execute(ZteRequest zteRequest) {
							Map<String, Object> params = new HashMap<String, Object>();
							
							CoQueueRuleReq coQueueRuleReq =(CoQueueRuleReq)(zteRequest);
							params = coQueueRuleReq.getParams();
							params.put("source_system", "10011");
						try{
							ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
							String rpc_type = cacheUtil.getConfigInfo("INPUT_RPC_TYPE");
							IOrderExtManager orderExtManager = SpringContextHolder.getBean("orderExtManager");
							OrderCtnResp resp = orderExtManager.saveManualOrder(params,rpc_type);
							if(!"0".equals(resp.getError_code())){
								lock(params, "CLSB", Integer.parseInt(Const.getStrValue(params, "deal_num"))+1);
							}else{
								//inputOrder.setStatus("CLCG");
								IDaoSupport	support = SpringContextHolder.getBean("baseDaoSupport");
								params.put("status", "CLCG");
								params.remove("source_system");
								Iterator ite = params.entrySet().iterator();
								String key = "";
								String value = "";
								while(ite.hasNext()){			
									Map.Entry<Object, Object> entry = (Entry<Object, Object>) ite.next();	
									key += entry.getKey() + ",";
									if(StringUtil.isEmpty(entry.getValue()+"")||entry.getValue()==null){
										value += "null,";
									}else{
										value += "'"+entry.getValue() + "',";
									}
								}
								String insert_sql = "insert into es_order_input_mid_his ("+key.substring(0, key.lastIndexOf(","))+") values ("+value.substring(0, value.lastIndexOf(","))+")";
								support.execute(insert_sql,null);
								//support.insert("es_order_input_mid_his", params);
								String del_sql = "delete from es_order_input_mid where input_inst_id = '"+Const.getStrValue(params, "input_inst_id")+"' ";
								support.execute(del_sql, null);
							}
						}catch(Exception ex){
							lock(params, "CLSB", Integer.parseInt(Const.getStrValue(params, "deal_num"))+1);
							ex.printStackTrace();
						}
						
						
						return new ZteResponse();
						}	
				});
				Map<String, Object> param1 = new HashMap<String, Object>();
				param1.put("deal_num", inputOrder.getDeal_num());
				param1.put("input_inst_id", inputOrder.getInput_inst_id());
				this.lock(param1, "CLZ", Integer.parseInt(Const.getStrValue(param1, "deal_num")));
				ThreadPoolFactory.orderExecute(taskThreadPool);
				
				
			}
		}
		
	}
	//
	public void lock(Map<String, Object> params,String status,int deal_num){
		IDaoSupport	support = SpringContextHolder.getBean("baseDaoSupport");
		String lock_sql = " update es_order_input_mid set status= ?,deal_num=? where input_inst_id = '"+Const.getStrValue(params, "input_inst_id")+"' ";
		support.execute(lock_sql, status ,deal_num);
	}
}
