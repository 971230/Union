package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.plugin.fileUpload.BatchResult;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.AccNbr;
import com.ztesoft.net.mall.core.model.Cloud;
import com.ztesoft.net.mall.core.model.CustReturned;
import com.ztesoft.net.mall.core.service.IAccNbrManager;
import com.ztesoft.net.mall.core.service.ICloudManager;
import com.ztesoft.net.mall.core.service.ICustReturnedManager;
import com.ztesoft.net.sqls.SF;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资料返档处理类
 * 
 * @author wu.i
 */

public class CustReturnedManager extends BaseSupport implements
		ICustReturnedManager {

	IAccNbrManager accNbrManager;
	ICloudManager cloudManager;


	/**
	 * 资料返档处理
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void date_returned(OrderRequst orderRequst, OrderResult orderResult,
			String type) {
		CustReturned custReturned = orderRequst.getCustReturned();
		Map retMap = new HashMap();
		String crmMsg = "";
		// 调用接口
		try {
			retMap = null;

		} catch (RuntimeException e) {
			// TODO: handle exception
			crmMsg = e.getMessage();
		}
		String isSucc = (String) retMap.get("result_code"); // 接口返回标志
		if (retMap.get("result_desc") != null) {
			crmMsg = retMap.get("result_desc").toString(); // crm接口返回信息
		}
		
		//判断数据库是否已存在相关记录
		String acc_nbr = custReturned.getAcc_nbr();
		StringBuffer sql = new StringBuffer();
		
		sql.append(SF.orderSql("SERVICE_CUST_RET_COUNT"));
		sql.append(" where acc_nbr = '"+ acc_nbr +"'");
		sql.append(" and state = '"+ Consts.ACCEPT_STATE_SUCC +"'");
		
		String count = this.baseDaoSupport.queryForString(sql.toString());
		
		
		if(StringUtils.isEmpty(count) || (!StringUtils.isEmpty(count) && Integer.valueOf(count) == 0)){
			
			if ("0".equals(isSucc) || "B".equals(isSucc)) // 成功
			{
				if (!StringUtil.isEmpty(custReturned.getTerminal_code())) {
					retMap = null;
					String resultCode = retMap.get("result_code").toString();
					crmMsg = retMap.get("result_desc").toString();
					if (!resultCode.equals("0000")) {
						datereturnedFail(orderResult, custReturned, crmMsg);
					} else {
						dateReturnedSuc(orderRequst, custReturned);
					}
				} else {
					dateReturnedSuc(orderRequst, custReturned); // 资料反档成功
				}
			} else {
				datereturnedFail(orderResult, custReturned, crmMsg); // 资料返档失败
			}
		}else{
			
			crmMsg = "资料返档失败：业务号码为"+ acc_nbr +"的数据已成功返档，不需要进行资料返档";
			datereturnedFail(orderResult, custReturned, crmMsg); // 资料返档失败
		}
		
		// 更新合约机受理状态
		custReturned.setStatus_date(DBTUtil.current());
		this.baseDaoSupport.insert("cust_returned", custReturned);

	}

	private void datereturnedFail(OrderResult orderResult,
			CustReturned custReturned, String crmMsg) {

		custReturned.setComments(crmMsg);
		custReturned.setState(Consts.ACCEPT_STATE_FAIL);

		// 设置异常
		orderResult.setCode(Consts.CODE_FAIL);
		orderResult.setMessage("crm调用接口失败" + crmMsg);
	}

	private void dateReturnedSuc(OrderRequst orderRequst,
			CustReturned custReturned) {
		// 合约机受理成功后更新号码订单
		String acc_nbr = custReturned.getAcc_nbr();

		AccNbr accNbr = accNbrManager.getAccNbr(acc_nbr);
		if (accNbr != null) {
			accNbr.setState(Consts.ACC_NBR_STATE_2);
			accNbr.setState_date(DBTUtil.current());

			accNbrManager.updateAccNbr(accNbr);
		}

		custReturned.setState(Consts.ACCEPT_STATE_SUCC);
		custReturned.setComments("资料反档成功");

		// 更新云卡状态为已激活
		Cloud cloud = cloudManager.getCloudByAccNbr(custReturned.getAcc_nbr());
		// 加入cloud不为空的判断
		if (cloud != null) {
			cloud.setState(Consts.ACC_NBR_STATE_2);
			cloud.setState_date(DBTUtil.current());
			cloud.setSec_order_id(orderRequst.getOrderId());
			cloudManager.updateCloud(cloud);
		}

	}

	@Override
	public BatchResult batchReturned(List inList) {

		BatchResult batchResult = new BatchResult();
		OrderRequst orderRequst = new OrderRequst();
		OrderResult orderResult = new OrderResult();
		String batchId = this.baseDaoSupport.getSequences("s_es_cust_returned");
		Map map = new HashMap();
		int sucNum = 0; // 成功返档条数
		int failNum = 0; // 返档失败条数

		for (int i = 0; i < inList.size(); i++) {
			map = (Map) inList.get(i);
			// TODO lan_id,cust_adr
			CustReturned custReturned = new CustReturned();
			custReturned.setCust_name(map.get("cust_name").toString());
			custReturned.setCerti_type(map.get("certi_type").toString());
			custReturned.setCerti_number(map.get("certi_number").toString());
			custReturned.setAcc_nbr(map.get("acc_nbr").toString());
			custReturned.setLan_id(map.get("lan_id").toString());
			custReturned.setCust_addr(map.get("ship_addr").toString());
			custReturned.setBatch_id(batchId); // 批次号
			custReturned.setCreate_date(DBTUtil.current());
			custReturned.setStatus_date(DBTUtil.current());
			orderRequst.setCustReturned(custReturned);

			date_returned(orderRequst, orderResult, "batch");
			if (Consts.ACCEPT_STATE_SUCC.equals(custReturned.getState())) {
				sucNum++;
			} else {
				failNum++;
			}
		}
		batchResult.setBatchId(batchId);
		batchResult.setSucNum(sucNum);
		batchResult.setFailNum(failNum);
		return batchResult;
	}

	@Override
	public Page getCustReturnByBatchId(CustReturned custReturned, int page,
			int pageSize) {
		String batchId = "";
		String acc_nbr = "";
		String cust_name = "";
		String state = "";
		String startTime = "";
		String endTime = "";

		if (custReturned != null) {
			batchId = custReturned.getBatch_id();
			acc_nbr = custReturned.getAcc_nbr();
			cust_name = custReturned.getCust_name();
			state = custReturned.getState();
			startTime = custReturned.getStartTime();
			endTime = custReturned.getEndTime();
		}

		StringBuffer sql = new StringBuffer();
		if(DBTUtil.databaseType.equals(DBTUtil.DB_TYPE_MYSQL)){
			sql.append(SF.orderSql("SERVICE_CUST_RET_SELECT").replaceAll("#dateformat", "date_format(t.status_date,'%Y-%m-%d %H:%i:%s')"));
		}
		else if(DBTUtil.databaseType.equals(DBTUtil.DB_TYPE_INFORMIX)){
			sql.append(SF.orderSql("SERVICE_CUST_RET_SELECT").replaceAll("#dateformat", "to_char(t.status_date,'%Y-%m-%d %H:%M:%S')"));
		}
		else if(DBTUtil.databaseType.equals(DBTUtil.DB_TYPE_ORACLE)){
			sql.append(SF.orderSql("SERVICE_CUST_RET_SELECT").replaceAll("#dateformat", "to_char(t.status_date,'yyyy-mm-dd hh24:mi:ss')"));
		}

		if (!StringUtils.isEmpty(batchId)) {
			sql.append(" and t.batch_id = '" + batchId + "'");
		}

		if (!StringUtils.isEmpty(acc_nbr)) {
			sql.append(" and t.acc_nbr like '%" + acc_nbr + "%'");
		}

		if (!StringUtils.isEmpty(cust_name)) {
			sql.append(" and t.cust_name like '%" + cust_name + "%'");
		}

		if (!StringUtils.isEmpty(state)) {
			sql.append(" and t.state = '" + state + "'");
		}
		
		if(!StringUtils.isEmpty(startTime)){
			if (startTime.length() < 11)  // yyyy-mm-dd 格式 补上时分秒
				startTime += " 00:00:00";
			sql.append(" and t.status_date >= "+DBTUtil.to_date(startTime, 2));
		}
		
		if(!StringUtils.isEmpty(endTime)){
			if (endTime.length() < 11)  // yyyy-mm-dd 格式 补上时分秒
				endTime += " 23:59:59";
			sql.append(" and t.status_date <= "+DBTUtil.to_date(endTime, 2));
		}

		sql.append(" order by t.status_date desc");

		return this.daoSupport.queryForPage(sql.toString(), page, pageSize,
				CustReturned.class);
	}

	public IAccNbrManager getAccNbrManager() {
		return accNbrManager;
	}

	public void setAccNbrManager(IAccNbrManager accNbrManager) {
		this.accNbrManager = accNbrManager;
	}

	public ICloudManager getCloudManager() {
		return cloudManager;
	}

	public void setCloudManager(ICloudManager cloudManager) {
		this.cloudManager = cloudManager;
	}

}
