package com.ztesoft.net.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.drools.core.util.StringUtils;

import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.sr.vo.MachineNoRG;
import zte.net.ecsord.params.sr.vo.MachineNoRGResp;
import zte.net.ecsord.utils.PCWriteCardTools;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ExportExcelHelper;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.CardMateOrdItemByStatusVO;
import com.ztesoft.net.model.CardMateStatusVO;
import com.ztesoft.net.model.QueueCardMateVo;
import com.ztesoft.net.model.QueueManageVO;
import com.ztesoft.net.model.QueueParams;
import com.ztesoft.net.model.QueueWriteCardVO;
import com.ztesoft.net.service.IQueueCardMateManager;
import com.ztesoft.net.sqls.SF;

public class QueueCardMateManager extends BaseSupport implements IQueueCardMateManager{

	@Override
	public Page queryQueueCardMateForPage(int pageNo, int pageSize, Map params) {
		String sql ="select * from es_queue_card_mate_manager where source_from='"+ManagerUtils.getSourceFrom()+"'";
		List sqlParams = new ArrayList();
		if(!StringUtil.isEmpty((String)params.get("queue_code"))){
			sql += " and queue_code=? ";
			sqlParams.add((String)params.get("queue_code"));
		}
		if(!StringUtil.isEmpty((String)params.get("card_mate_code"))){
			sql += " and card_mate_code=? ";
			sqlParams.add((String)params.get("card_mate_code"));
		}
		if(!StringUtil.isEmpty((String)params.get("card_mate_status"))){
			sql += " and card_mate_status=? ";
			sqlParams.add((String)params.get("card_mate_status"));
		}
		//排序 写卡机编码    --songqi
		sql += " order by card_mate_code ";
		Page page = baseDaoSupport.qryForPage(sql, pageNo, pageSize, QueueCardMateVo.class, sqlParams.toArray());
		return page;
	}

	@Override
	public boolean saveQueueCardMate(QueueCardMateVo queueCardMateVo) {
		String id = this.daoSupport.nextVal("ES_QUEUE_CARD_MATE_MANAGER_SEQ")+"";
		String insertSql = "insert into es_queue_card_mate_manager(queue_card_mate_id,queue_code,card_mate_code,describe,create_time,source_from) " +
				"values(?,?,?,?,sysdate,?)";
		List sqlParams = new ArrayList();
		sqlParams.add(id);
		sqlParams.add(queueCardMateVo.getQueue_code());
		sqlParams.add(queueCardMateVo.getCard_mate_code());
		sqlParams.add(queueCardMateVo.getDescribe());
		sqlParams.add(Consts.ECS_SOURCE_FROM);
		baseDaoSupport.execute(insertSql, sqlParams.toArray());
//		queueCardMateVo.setQueue_card_mate_id(id);
//		try {
//			queueCardMateVo.setCreate_time(DateUtil.getTime2());
//		} catch (FrameException e) {
//			e.printStackTrace();
//		}
//		baseDaoSupport.insert("es_queue_card_mate_manager", queueCardMateVo);
		return false;
	}

	@Override
	public boolean deleteQueueCardMate(String id) {
		String delSql = "delete from es_queue_card_mate_manager where source_from=? and queue_card_mate_id = ?";
		List sqlParams = new ArrayList();
		sqlParams.add(Consts.ECS_SOURCE_FROM);
		sqlParams.add(id);
		baseDaoSupport.execute(delSql, sqlParams.toArray());
		return true;
	}

	@Override
	public QueueCardMateVo queryQueueCardMate(String id) {
		QueueCardMateVo queueCardMateVo = new QueueCardMateVo();
		String qrySql = "select * from es_queue_card_mate_manager where source_from=? and queue_card_mate_id = ?";
		List sqlParams = new ArrayList();
		sqlParams.add(Consts.ECS_SOURCE_FROM);
		sqlParams.add(id);
		queueCardMateVo = (QueueCardMateVo) baseDaoSupport.queryForObject(qrySql, QueueCardMateVo.class, sqlParams.toArray());
		return queueCardMateVo;
	}

	@Override
	public boolean modifyQueueCardMate(String id,
			QueueCardMateVo queueCardMateVo) {
		String updateSql = "update es_queue_card_mate_manager set queue_code=?,card_mate_code=?,describe=? where queue_card_mate_id = ? and source_from=?";
		List sqlParams = new ArrayList();
		sqlParams.add(queueCardMateVo.getQueue_code());
		sqlParams.add(queueCardMateVo.getCard_mate_code());
		sqlParams.add(queueCardMateVo.getDescribe());
		sqlParams.add(id);
		sqlParams.add(Consts.ECS_SOURCE_FROM);
		baseDaoSupport.execute(updateSql, sqlParams.toArray());
//		baseDaoSupport.update("es_queue_card_mate_manager", queueCardMateVo, "queue_card_mate_id = '"+id+"'");
		return true;
	}

	@Override
	public boolean checkCardMateCode(String card_mate_code,String queue_card_mate_id) {
		String checkSql = "select * from es_queue_card_mate_manager where card_mate_code = ? ";
		List sqlParams = new ArrayList();
		sqlParams.add(card_mate_code);
		if(!StringUtils.isEmpty(queue_card_mate_id)){
			checkSql += " and queue_card_mate_id != ?";
			sqlParams.add(queue_card_mate_id);
		}
		List list = baseDaoSupport.queryForList(checkSql, sqlParams.toArray());
		if(list==null || list.size()==0){
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public Page getQueueManageList(int pageNo,int pageSize,QueueParams params) {
		List sqlParam = new ArrayList();
		String checkSql = "select * from ES_QUEUE_MANAGER t where source_from='"+ManagerUtils.getSourceFrom()+"'";
		if(params!=null){
			if(!StringUtils.isEmpty(params.getQueue_no())){
				checkSql += " and queue_no=?";
				sqlParam.add(params.getQueue_no());
			}
			if(!StringUtils.isEmpty(params.getQueue_switch())){
				checkSql += " and queue_switch=?";
				sqlParam.add(params.getQueue_switch());
			}
		}
		
		Page page  = this.baseDaoSupport.queryForPage(checkSql, pageNo, pageSize,sqlParam.toArray());
		return page;
	}
	
	@Override
	public QueueManageVO getQueueManageById(String queueId) {
		List sqlParams = new ArrayList();
		sqlParams.add(queueId);
		QueueManageVO vo = (QueueManageVO) baseDaoSupport.queryForObject("select * from ES_QUEUE_MANAGER where id=?", QueueManageVO.class, sqlParams.toArray());
		return vo;
	}
	
	@Override
	public boolean updateQueueManageVO(QueueManageVO queueManagevo) {
		try{
			baseDaoSupport.update("ES_QUEUE_MANAGER", queueManagevo, " id='"+queueManagevo.getId()+"'");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}		
	
	@Override
	public List<Map<String, String>> queueReturnList() {
		String sql ="select b.order_id,b.queue_status,b.open_account_status,b.write_card_status from es_queue_write_card b where b.source_from=? and b.queue_status=? and "+
				"exists (select 1 from es_order_ext a where a.order_id = b.order_id and a.flow_trace_id in ('X','D')) and rownum<=300";
		List sqlParams = new ArrayList();
		sqlParams.add(Consts.ECS_SOURCE_FROM);
		sqlParams.add(EcsOrderConsts.QUEUE_STATUS_WAIT_RETURN);
		List<Map<String,String>> orders = baseDaoSupport.queryForList(sql,sqlParams.toArray());
		return orders;
	}

	@Override
	public List<Map<String, String>> queueDeleteList() {
		String sql ="select b.order_id from es_queue_write_card b where b.source_from=? and "+
				"not exists (select 1 from es_order_ext a where a.order_id = b.order_id and a.source_from = b.source_from " +
				"and a.flow_trace_id in ('X','D')) and rownum<=300";
		List<Map<String,String>> orders = baseDaoSupport.queryForList(sql,Consts.ECS_SOURCE_FROM);
		return orders;
	}

	
	@Override
	public boolean updateQueueManageCon(List<String> updateParams,String ids) {
		try{
			Map updateMap = new HashMap();
			updateMap.put("queue_switch", updateParams.get(0));
			updateMap.put("deal_reason", updateParams.get(1));
			if(StringUtil.equals(updateParams.get(0), EcsOrderConsts.QUEUE_STATUS_OPENED)){//开启时把连续失败次数清零
				updateMap.put("conti_open_failed_num", "0");
				updateMap.put("conti_card_failed_num", "0");
			}

			baseDaoSupport.update("ES_QUEUE_MANAGER", updateMap, " id in ('"+ids.replace(",", "','")+"')");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}	

	@Override
	public boolean saveQueueManage(QueueManageVO queueManagevo) {
//		List sqlParams = new ArrayList();
//		sqlParams.add(queueManagevo.getQueue_no());
//		int cnt = baseDaoSupport.queryForInt("select count(1) cnt from ES_QUEUE_MANAGER where queue_no=?", sqlParams);
//		if(cnt>0){
//			return false;			
//		}else{
			String id = this.daoSupport.nextVal("SEQ_ES_QUEUE_MANAGER")+"";
			queueManagevo.setId(id);
			queueManagevo.setSource_from(ManagerUtils.getSourceFrom());
			try{
				baseDaoSupport.insert("es_queue_manager", queueManagevo);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
//		}
	}
	

	@Override
	public boolean rebackQueueManage(List<String> updateParams,String ids) {
		try{
			Map updateMap = new HashMap();
			updateMap.put("QUEUE_STATUS", updateParams.get(0));

			baseDaoSupport.update("ES_QUEUE_WRITE_CARD", updateMap, " QUEUE_CODE in ('"+ids.replace(",", "','")+"')");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public Page getQueueOrderList(int pageNo,int pageSize,QueueParams params) {
		List sqlParam = new ArrayList();
		String checkSql = "select ta.queue_code,ta.order_id,ta.create_time,ta.open_account_status,ta.open_account_time,ta.write_card_status,ta.write_card_time,ta.exception_type,ta.exception_msg,ta.queue_status,ta.source_from ,tb.flow_trace_id,tb.order_model from ES_QUEUE_WRITE_CARD ta left join es_order_ext tb on ta.order_id=tb.order_id where ta.source_from='"+ManagerUtils.getSourceFrom()+"'";
		if(params!=null){
			if(!StringUtils.isEmpty(params.getOrder_id())){
				checkSql += " and ta.order_id=?";
				sqlParam.add(params.getOrder_id());
			}
			if(!StringUtils.isEmpty(params.getQueue_no())){
				checkSql += " and ta.queue_code=?";
				sqlParam.add(params.getQueue_no());
			}
			if(!StringUtils.isEmpty(params.getQueue_status())){
				checkSql += " and ta.queue_status=?";
				sqlParam.add(params.getQueue_status());
			}
		}
		
		checkSql += "order by ta.create_time desc";
		Page page  = this.baseDaoSupport.queryForPage(checkSql, pageNo, pageSize,sqlParam.toArray());
		return page;
	}
	
	@Override
	public boolean rebackQueueOrder(List<String> updateParams,String ids) {
		try{
			Map updateMap = new HashMap();
			updateMap.put("QUEUE_STATUS", updateParams.get(0));
			updateMap.put("EXCEPTION_TYPE", updateParams.get(1));
			updateMap.put("EXCEPTION_MSG", updateParams.get(2));
			updateMap.put("USER_ID", updateParams.get(3));
			updateMap.put("USER_NAME", updateParams.get(4));

			baseDaoSupport.update("ES_QUEUE_WRITE_CARD", updateMap, " ORDER_ID in ('"+ids.replace(",", "','")+"')");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean rebackQueueOrder(String order_id) {
		String updateSql = SF.orderSql("UPDATE_QUEUE_STATUS");
		List sqlParam = new ArrayList();
		sqlParam.add(EcsOrderConsts.QUEUE_STATUS_WAIT_RETURN);
		sqlParam.add(order_id);
		baseDaoSupport.execute(updateSql, sqlParam.toArray());
		return true;
	}
	
	public List<QueueCardMateVo> queryQueueCardMate(QueueParams params) {
		String sql ="select * from es_queue_card_mate_manager where source_from='"+ManagerUtils.getSourceFrom()+"'";
		List sqlParams = new ArrayList();
		if(!StringUtil.isEmpty(params.getQueue_no())){
			sql += " and queue_code=? ";
			sqlParams.add(params.getQueue_no());
		}
		if(!StringUtil.isEmpty(params.getCard_mate_code())){
			sql += " and card_mate_code=? ";
			sqlParams.add(params.getCard_mate_code());
		}
		if(!StringUtil.isEmpty(params.getCard_mate_status())){
			sql += " and card_mate_status=? ";
			sqlParams.add(params.getCard_mate_status());
		}
		List<QueueCardMateVo> page = baseDaoSupport.queryForList(sql, QueueCardMateVo.class, sqlParams.toArray());
		return page;
	}
	
	public List<String> getQueueOrderWaitWriteCardList(QueueParams params) {
		List sqlParam = new ArrayList();
		String checkSql = "select ta.order_id from ES_QUEUE_WRITE_CARD ta where ta.source_from='"+ManagerUtils.getSourceFrom()+"' and QUEUE_STATUS<>'1' and ta.write_card_status='0' and ta.open_account_status='2'";
		if(params!=null){
			if(!StringUtils.isEmpty(params.getOrder_id())){
				checkSql += " and ta.order_id=?";
				sqlParam.add(params.getOrder_id());
			}
			if(!StringUtils.isEmpty(params.getQueue_no())){
				checkSql += " and ta.queue_code=?";
				sqlParam.add(params.getQueue_no());
			}
			if(!StringUtils.isEmpty(params.getQueue_status())){
				checkSql += " and ta.queue_status=?";
				sqlParam.add(params.getQueue_status());
			}
		}
		
		List<String> list = this.baseDaoSupport.queryForList(checkSql,sqlParam.toArray());
		return list;
	}
	
	public List<String> getQueueOrderWaitOpenAccList(QueueParams params) {
		List sqlParam = new ArrayList();
		String checkSql = "select ta.order_id from ES_QUEUE_WRITE_CARD ta where ta.source_from='"+ManagerUtils.getSourceFrom()+"' and QUEUE_STATUS<>'1' and ta.open_account_status='0'";
		if(params!=null){
			if(!StringUtils.isEmpty(params.getOrder_id())){
				checkSql += " and ta.order_id=?";
				sqlParam.add(params.getOrder_id());
			}
			if(!StringUtils.isEmpty(params.getQueue_no())){
				checkSql += " and ta.queue_code=?";
				sqlParam.add(params.getQueue_no());
			}
			if(!StringUtils.isEmpty(params.getQueue_status())){
				checkSql += " and ta.queue_status=?";
				sqlParam.add(params.getQueue_status());
			}
		}
		
		List<String> list = this.baseDaoSupport.queryForList(checkSql,sqlParam.toArray());
		return list;
	}
	
	public List<QueueManageVO> getQueueManage(QueueParams params) {
		List sqlParam = new ArrayList();
		String checkSql = "select * from ES_QUEUE_MANAGER t where source_from='"+ManagerUtils.getSourceFrom()+"'";
		if(params!=null){
			if(!StringUtils.isEmpty(params.getQueue_no())){
				checkSql += " and queue_no=?";
				sqlParam.add(params.getQueue_no());
			}
			if(!StringUtils.isEmpty(params.getQueue_switch())){
				checkSql += " and queue_switch=?";
				sqlParam.add(params.getQueue_switch());
			}
		}
		
		List<QueueManageVO> list = this.baseDaoSupport.queryForList(checkSql, QueueManageVO.class,sqlParam.toArray());
		return list;
	}
	
	public void updateCardQueueStatus(){
		String sql = "select d.queue_code,to_char(wmsys.wm_concat(d.card_mate_code)) write_machines"
				+ " from ES_QUEUE_MANAGER c, es_queue_card_mate_manager d where d.queue_code = c.queue_no"
				+ " and d.source_from = c.source_from and d.source_from = ? group by d.queue_code";
		//调接口获取写卡机组状态并记录数据
				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
				//获取配置的队列编码（写卡机组）及写卡机编码
				List writeList = support.queryForList(sql, ManagerUtils.getSourceFrom());
				if(null != writeList && writeList.size() > 0){
					Map m = null;
					for(int i = 0; i < writeList.size(); i++){
						String queue_code = "";
						String write_machines = "";
						try{
							List<MachineNoRGResp> machinesList = new ArrayList<MachineNoRGResp>();
							List<MachineNoRG> param = new ArrayList<MachineNoRG>();
							m = (Map)writeList.get(i);
							queue_code = m.get("queue_code").toString();
							write_machines = m.get("write_machines").toString();
							logger.info("queue_code["+queue_code+"],write_machines["+write_machines+"]");
							String []tmp = write_machines.split("\\,");
							for(int j = 0; j < tmp.length; j++){
								MachineNoRG machine = new MachineNoRG();
								machine.setMachineNo(tmp[j]);
								param.add(machine);
							}
							//调森锐接口获取写卡机状态并更新es_queue_card_mate_manager表状态值
							machinesList = PCWriteCardTools.getMachineNoByMachineList(param);
							if(null != machinesList && machinesList.size() > 0){
								for(MachineNoRGResp mn : machinesList){
									PCWriteCardTools.modifyMachineStatus(mn.getStatus(), queue_code, mn.getMachineNo());
								}
								//更新写卡机队列维护表ES_QUEUE_MANAGER数据
								PCWriteCardTools.modifyQueueMacNums(queue_code, machinesList.size());
							}
						}catch(Exception e){
							logger.info("log===============批量获取写卡机状态失败queue_code["+queue_code+"],write_machines["+write_machines+"]");
							e.printStackTrace();
						}
					}
				}
	}
	
	public Page queryQueueCardMateOrderNum(QueueParams queueParams,int pageNo,int pageSize){
		String sql =" select a.machine_no,decode(m.card_mate_status,'1','正常','2','写卡中','3','空闲','4','异常') as card_mate_status, "
				   + "count(case when a.status='成功' then 1end) as success,count(case when a.status='失败' then 1end) as fail,count(status) as num from ( "
				   +" select l.machine_no,t.order_id,(case when t.flow_trace_id in ('H','J','L') "
				   +" and t.order_model='06' then '成功' when t.flow_trace_id='B' and t.order_model='02'then '失败' else '其他' end) as status from es_order_ext t "
				   +" left join es_queue_card_action_log l on t.order_id=l.order_id "
				   +" left join (select min(create_time) as create_time,order_id from es_queue_write_card_his group by order_id) w on w.order_id=l.order_id "
				   +" where 1=1";
		if(!StringUtils.isEmpty(queueParams.getStart_time())){
			sql += " and trunc(w.create_time)>=to_date('"+queueParams.getStart_time()+"','yyyy-mm-dd') ";
		}
		if(!StringUtils.isEmpty(queueParams.getEnd_time())){
			sql += " and trunc(w.create_time)<=to_date('"+queueParams.getEnd_time()+"','yyyy-mm-dd') ";
		}
		sql += " and t.order_id=l.order_id and w.order_id=l.order_id  and l.source_from='"+ManagerUtils.getSourceFrom()+"' "
			+" group by t.order_id,t.flow_trace_id,t.order_model,l.machine_no) a "
			+" inner join es_queue_card_mate_manager m on m.card_mate_code=a.machine_no "
			+" group by a.machine_no,m.card_mate_status ";
		String countSql = "select count(1) from ("+sql+") cord";
		Page page  = this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, CardMateStatusVO.class, countSql,null);
		return page;
		
	}
	
	public Page queryOrderItemsByStatus(String cardmateid, String sucflag,String sta_time, String end_time,int pageNo,int pageSize){
		String sql = " select distinct t.out_tid, t.tid_time,g.name as goodsName,f.pname order_from_n,decode(t.refund_deal_type, '02','订单退单', "
				   + " decode(t.flow_trace_id, 'J', '处理完成', 'L', '处理完成', null, '流程启动失败','处理中--' || n.pname)) order_status, "
				   + " n.pname flow_trace_name,iet.phone_num ,v.phone_owner_name ,pt.cert_card_num ,d.ship_name,d.ship_mobile, "
				   + " (case when t.flow_trace_id in ('H', 'J', 'L') and t.order_model = '06' then '成功' when t.flow_trace_id = 'B' and t.order_model = '02' then "
				   + " '失败' else '其他' end) card_status, "
				   + " (case when t.flow_trace_id = 'B' and t.order_model = '02' then t.exception_desc end) exception_desc "
				   + " from es_order_ext t "
				   + " left join es_queue_card_action_log l "
				   + " on t.order_id = l.order_id "
				   + " left join (select min(create_time) as create_time, order_id from es_queue_write_card_his group by order_id) w "
				   + " on w.order_id = l.order_id "
				   + " left join es_order o on o.order_id=t.order_id "
				   + " left join es_goods g on g.goods_id=o.goods_id "
				   + " left join es_dc_public_ext f on f.stype='source_from' and f.pkey=t.order_from "
				   + " left join es_dc_public_ext n on n.stype='DIC_ORDER_NODE' and n.pkey=t.flow_trace_id "
				   + " left join es_order_items_ext iet on iet.order_id=t.order_id "
				   + " left join es_order_extvtl v on v.order_id=t.order_id "
				   + " left join es_order_items_prod_ext pt on pt.order_id=t.order_id "
				   + " left join es_delivery d on d.order_id=t.order_id "
				   + " where 1 = 1 "
				   + " and t.order_id = l.order_id "
				   + " and w.order_id = l.order_id "
				   + " and iet.order_id=t.order_id "
				   + " and v.order_id=t.order_id "
				   + " and pt.order_id=t.order_id "
				   + " and d.order_id=t.order_id "
				   + " and l.source_from='"+ManagerUtils.getSourceFrom()+"' ";
		if(!StringUtils.isEmpty(sta_time)){
			sql += " and trunc(w.create_time)>=to_date('"+sta_time+"','yyyy-mm-dd') ";
		}
		if(!StringUtils.isEmpty(end_time)){
			sql += " and trunc(w.create_time)<=to_date('"+end_time+"','yyyy-mm-dd') ";
		}
		if(!StringUtils.isEmpty(end_time)){
			sql += " and l.machine_no = '"+cardmateid+"' ";
		}
		if(!StringUtils.isEmpty(sucflag)&&!"all".equals(sucflag)){
			if("suc".equals(sucflag)){
				sql += " and t.flow_trace_id in ('H', 'J', 'L') and t.order_model = '06' ";
			}else if("fail".equals(sucflag)){
				sql += " and t.flow_trace_id = 'B' and t.order_model = '02' ";
			}
		}
		String countSql = "select count(1) from ("+sql+") cord";
		Page page  = this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, CardMateOrdItemByStatusVO.class, countSql,null);
		return page;
		
	}
	
	public List queryOrderItemsListByStatus(String cardmateid, String sucflag,String sta_time, String end_time){
		String sql = " select distinct t.out_tid, t.tid_time,g.name as goodsName,f.pname order_from_n,decode(t.refund_deal_type, '02','订单退单', "
				   + " decode(t.flow_trace_id, 'J', '处理完成', 'L', '处理完成', null, '流程启动失败','处理中--' || n.pname)) order_status, "
				   + " n.pname flow_trace_name,iet.phone_num ,v.phone_owner_name ,pt.cert_card_num ,d.ship_name,d.ship_mobile, "
				   + " (case when t.flow_trace_id in ('H', 'J', 'L') and t.order_model = '06' then '成功' when t.flow_trace_id = 'B' and t.order_model = '02' then "
				   + " '失败' else '其他' end) card_status, "
				   + " (case when t.flow_trace_id = 'B' and t.order_model = '02' then t.exception_desc end) exception_desc "
				   + " from es_order_ext t "
				   + " left join es_queue_card_action_log l "
				   + " on t.order_id = l.order_id "
				   + " left join (select min(create_time) as create_time, order_id from es_queue_write_card_his group by order_id) w "
				   + " on w.order_id = l.order_id "
				   + " left join es_order o on o.order_id=t.order_id "
				   + " left join es_goods g on g.goods_id=o.goods_id "
				   + " left join es_dc_public_ext f on f.stype='source_from' and f.pkey=t.order_from "
				   + " left join es_dc_public_ext n on n.stype='DIC_ORDER_NODE' and n.pkey=t.flow_trace_id "
				   + " left join es_order_items_ext iet on iet.order_id=t.order_id "
				   + " left join es_order_extvtl v on v.order_id=t.order_id "
				   + " left join es_order_items_prod_ext pt on pt.order_id=t.order_id "
				   + " left join es_delivery d on d.order_id=t.order_id "
				   + " where 1 = 1 "
				   + " and t.order_id = l.order_id "
				   + " and w.order_id = l.order_id "
				   + " and iet.order_id=t.order_id "
				   + " and v.order_id=t.order_id "
				   + " and pt.order_id=t.order_id "
				   + " and d.order_id=t.order_id "
				   + " and l.source_from='"+ManagerUtils.getSourceFrom()+"' ";
		if(!StringUtils.isEmpty(sta_time)){
			sql += " and trunc(w.create_time)>=to_date('"+sta_time+"','yyyy-mm-dd') ";
		}
		if(!StringUtils.isEmpty(end_time)){
			sql += " and trunc(w.create_time)<=to_date('"+end_time+"','yyyy-mm-dd') ";
		}
		if(!StringUtils.isEmpty(end_time)){
			sql += " and l.machine_no = '"+cardmateid+"' ";
		}
		if(!StringUtils.isEmpty(sucflag)&&!"all".equals(sucflag)){
			if("suc".equals(sucflag)){
				sql += " and t.flow_trace_id in ('H', 'J', 'L') and t.order_model = '06' ";
			}else if("fail".equals(sucflag)){
				sql += " and t.flow_trace_id = 'B' and t.order_model = '02' ";
			}
		}
		List list  = this.baseDaoSupport.queryForList(sql, CardMateOrdItemByStatusVO.class, null);
		return list;
		
	}
	
}
