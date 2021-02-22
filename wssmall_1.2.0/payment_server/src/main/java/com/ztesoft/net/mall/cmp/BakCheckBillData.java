package com.ztesoft.net.mall.cmp;

import java.util.ArrayList;
import java.util.List;

import params.checkacctconfig.req.CheckAcctConfigReq;
import params.checkacctconfig.resp.CheckAcctConfigResp;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.cmp.CompBillResult;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.CheckAcctConfig;

public class BakCheckBillData extends BaseSupport implements ICMPBill{
	
	@Override
	@SuppressWarnings("unchecked")
	public void perform(CheckAcctConfigReq checkAcctConfigReq,CheckAcctConfigResp checkAcctConfigResp){
		
		CheckAcctConfig acctConfig = checkAcctConfigReq.getCheckAcctConfig();
		CompBillResult compBillResult = checkAcctConfigResp.getCompBillResult();
		
			
		String sqlbak =   " insert into ES_CHECKACCT_HIS select * from ES_CHECKACCT where system_id = '" + acctConfig.getSystem_id() + "'";
		String sqlbakinfo =   " insert into ES_CHECKACCT_INFO_HIS select * from ES_CHECKACCT_INFO where system_id = '" +  acctConfig.getSystem_id() + "'";
		
		String sqlDel = "delete from ES_CHECKACCT where system_id = '" +  acctConfig.getSystem_id() + "'";
		String sqlDelInto = "delete from ES_CHECKACCT_INFO where system_id = '" +  acctConfig.getSystem_id() + "'";

		try {
			List<Object[]> sqlParam = new ArrayList<Object[]>();
			
			this.baseDaoSupport.batchExecute(sqlbak, null);
			this.baseDaoSupport.batchExecute(sqlbakinfo, null);
			
			this.baseDaoSupport.batchExecute(sqlDel, null);
			this.baseDaoSupport.batchExecute(sqlDelInto, null);
			
		} catch (Exception e) {
			compBillResult.setErr_type(Consts.COMP_BILL_ERROR_00A);
			compBillResult.setErr_message("归档历史数据异常");
			e.printStackTrace();
			throw new RuntimeException("归档历史数据异常：" + e.getMessage());
		}
	}	
}
