package com.ztesoft.net.mall.cmp;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.util.FTPUtil;

import params.checkacctconfig.req.CheckAcctConfigReq;
import params.checkacctconfig.resp.CheckAcctConfigResp;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.AlarmTask;
import com.ztesoft.net.mall.core.model.CheckAcctConfig;
import com.ztesoft.net.model.BaseBill;
import com.ztesoft.net.model.BillHeader;
import com.ztesoft.net.model.DepositBill;

/**
 * 对账基础类
 * @作者 MoChunrun
 * @创建日期 2013-10-22 
 * @版本 V 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class AbsDataToFile extends BaseSupport implements ICMPBill{
	
	public Logger imlog = Logger.getLogger(DataBaseToFile.class);
	/**
	 * 场景三：第三方平台支付为未成功，我们成功了 返回数据
	 */
	protected List<? extends BaseBill> refundList;
	/**
	 * 场景一：第三方平台支付成功、分销充值失败 返回数据
	 */
	protected List<Map> needCharge;
	/**
	 * 场景二：第三方平台支付和分销平台都有订单（不管分销平台是否成功），但是金额不同的订单 返回数据
	 */
	protected List<Map> needUp;
	/**
	 * 总数量transNumAndMoney.get("trans_num")、总金额transNumAndMoney.get("trans_money")
	 */
	protected Map transNumAndMoney;
	
	protected String transDate;
	/**
	 * 总数量比对、总金额比对
	 * @作者 MoChunrun
	 * @创建日期 2013-10-22 
	 * @return
	 */
	public Map sumTransNumAndMoney(String type_code){
		String countSql = "select count(1)trans_num, sum(b.pay_amount) trans_money from es_payment_list a,es_checkacct_info b " +
				"where a.sequ = 0 and a.state = '1' and a.type_code = ? and " +
				"a.transaction_id = b.wss_transaction_id and b.seq = 0";
		Map countMap = this.baseDaoSupport.queryForMap(countSql, type_code); //需要重数据获取
		return countMap;
	}
	
	/**
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2013-10-22 
	 * @param transDate
	 * @param type_code
	 * @return
	 */
	public Map sumBankPayedAndBillSuccess(String transDate,String type_code){
		String validSql = "select count(1) apt_num,sum(a.pay_amount) apt_money " +
				"from es_payment_list a where a.transdate = ? and  " +
				"a.state = '1' and a.type_code = ? and a.deal_flag= ? ";
		Map validMap = this.baseDaoSupport.queryForMap(validSql, transDate,type_code,Consts.BANK_PAYED_AND_BILL_PAYED_SUCC);	
		return validMap;
	}
	
	/**
	 * 场景一：第三方平台支付成功、分销充值失败，修改订单、并标识（取第三方平台支付文档的信息，此处只做订单状态变更）
	 * @作者 MoChunrun
	 * @创建日期 2013-10-22 
	 * @param transDate
	 */
	public List<Map> executeNeedCharge(String transDate,String type_code){
		String needCharge = "select a.* from es_payment_list a where a.deal_flag <>  '4' " +
				"and a.sequ = 0 and a.state = '1' and a.transdate = ? and a.type_code = ? and exists " +
				"(select b.accounts_id from es_checkacct_info b where " +
				"a.transaction_id = b.wss_transaction_id)";
		List<Map> chargeList = this.baseDaoSupport.queryForList(needCharge, transDate, type_code);
		//更新订单状态
		if(chargeList != null && !chargeList.isEmpty()) {
			String upSql = "update es_payment_list a set a.deal_flag = '4',a.fld_1 = 'A' " +
			"where a.transaction_id = ? and a.state = '1' and a.sequ = 0";
			List<Object[]> sqlParam = new ArrayList<Object[]>();
			for(Map temp : chargeList){
				sqlParam.add(new Object[]{temp.get("transaction_id")});
			}
			this.baseDaoSupport.batchExecute(upSql, sqlParam);
		}
		return chargeList;
	}
	
	/**
	 * 场景二：第三方平台支付和分销平台都有订单（不管分销平台是否成功），但是金额不同的订单（取第三方平台支付文档信息、此处只做订单变更），
	 * 这种情况有可能和其他场景有交集，所以处理标识放在不同字段中（其他场景fld_1，此场景fld_2）
	 * @作者 MoChunrun
	 * @创建日期 2013-10-22 
	 * @param transDate
	 */
	public List<Map> executeNeedUp(String transDate,String type_code){
		String needUp = "select b.pay_amount,a.transaction_id from es_payment_list a,es_checkacct_info b " +
				"where a.transdate = ? and a.type_code = ?  and a.transaction_id = b.wss_transaction_id " +
				"and a.pay_amount <> b.pay_amount and a.sequ = 0 and a.state = '1'";
		List<Map> needUpList = this.baseDaoSupport.queryForList(needUp, transDate, type_code);
		//更新订单状态
		if(null != needUpList && !needUpList.isEmpty()){
			String upSql = "update es_payment_list a set a.pay_amount = ?,a.fld_2 = 'B' where " +
					"a.transaction_id = ? and a.state = '1' and a.sequ = 0";
			List<Object[]> sqlParam = new ArrayList<Object[]>();
			for(Map temp : needUpList){
				sqlParam.add(new Object[]{temp.get("pay_amount"), temp.get("transaction_id")});
			}
			this.baseDaoSupport.batchExecute(upSql, sqlParam);
		}
		return needUpList;
	}
	
	/**
	 * 场景三：第三方平台支付为未成功，我们成功了，金额等信息从我们系统获取，需要追加第三方平台支付的对账文档，并将订单状态改为未支付
	 * @作者 MoChunrun
	 * @创建日期 2013-10-22 
	 * @param transDate
	 * @return
	 */
	public List<DepositBill> executeRefund(String transDate,String type_code){
		String refundSql = "select a.transaction_id, a.pay_amount, '' party_id, a.userid unionOrgCode, " +
				"'606' busi_type from es_payment_list a  where a.transdate = ? and a.type_code = ? and a.deal_flag = '4' and a.state = '1'  and " + //and a.sequ = 0去除sequ
				"not exists(select 1 from es_checkacct_info t where a.transaction_id = t.wss_transaction_id)";
		List<DepositBill> refundList = this.baseDaoSupport.queryForList(refundSql, DepositBill.class,transDate, type_code);
		if(null != refundList && !refundList.isEmpty()){
			String upSql = "update es_payment_list a set a.deal_flag = '11',a.fld_1 = 'C' " +
					"where a.transaction_id = ? and a.state = '1' and a.sequ = 0";
			List<Object[]> sqlParam = new ArrayList<Object[]>();
			for(DepositBill temp : refundList){
				sqlParam.add(new Object[]{temp.getTransaction_id()});
			}
			this.baseDaoSupport.batchExecute(upSql, sqlParam);
		}
		return refundList;
	}
	
	/**
	 * 检查异常阀值并返回总数量map.get("trans_num")与总金额map.get("trans_money")
	 * @作者 MoChunrun
	 * @创建日期 2013-10-22 
	 * @param transDate
	 * @param type_code
	 * @param config
	 * @param compBillResult
	 * @return
	 */
	public Map checkException(String transDate,String type_code,CheckAcctConfig config,CompBillResult compBillResult){
		//=======================================某模块功能对账
		//总数量比对、总金额比对，只对账预存金充值的订单
		Map countMap = sumTransNumAndMoney(type_code); //获取网银文件成功的数量、金额
		Long transNum = Long.parseLong(countMap.get("trans_num").toString());
		Long transMoney = Long.parseLong(countMap.get("trans_money").toString());
		
		Map validMap = sumBankPayedAndBillSuccess(transDate, type_code);//获取银行支付成功,切计费已销账的记录
		Long aptNum = Long.valueOf(validMap.get("apt_num").toString());
		Long aptMoney = Long.valueOf(validMap.get("apt_money").toString());
		
		boolean res = true;
		/**
		 * change by hu.yi
		 * 2013.12.05
		 * 查询任务告警，解析数据后返回阀值进行比较，通过告警任务来进行提醒
		 */
		//查询当前对账配置是否对应了告警任务(当前时间是否在生效时间内)
		String system_id = config.getSystem_id();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(alarm_task_id) FROM es_alarm_task WHERE");
		sql.append(" task_create_time <= "+DBTUtil.current());
		sql.append(" AND task_aead_time >= "+DBTUtil.current());
		sql.append(" AND alarm_task_type = '"+Consts.ALARM_TYPE_BILL+"'");
		sql.append(" AND owner_task_id = '"+system_id+"'");
		
		List<AlarmTask> list = this.baseDaoSupport.queryForList(sql.toString(), AlarmTask.class);
		AlarmTask alarmTask = new AlarmTask();
		
		if(ListUtil.isEmpty(list)){
			res = false;
		}else{
			for (int i = 0; i < list.size(); i++) {
				alarmTask = list.get(i);
			}
		}
		
		compBillResult.setAlarmTask(alarmTask);
		
		if(res){
			Map<String, Object> map = CheckBillUtils.parseCondition(alarmTask);
			res = (Boolean) map.get("res");
			if(res){
				int bill_error_num_min = (Integer) map.get("bill_error_num_min");
				int bill_error_num_max = (Integer) map.get("bill_error_num_max");
				int bill_error_money_min = (Integer) map.get("bill_error_money_min");
				int bill_error_money_max = (Integer) map.get("bill_error_money_max");
				//数量异常判断
				if(bill_error_num_min <= Math.abs(aptNum - transNum) && bill_error_num_max >= Math.abs(aptNum - transNum)){
					compBillResult.setErr_type(Consts.COMP_BILL_ERROR_00E);
					compBillResult.setErr_message("差异数量超过设定阀值（银行对账数量：" + transNum + 
							";平台实际数量：" + aptNum + ")");
					compBillResult.setSms_content("差异数量超过设定阀值（银行对账数量：" + transNum + 
							";平台实际数量：" + aptNum + ")");
					compBillResult.setBill_num(Math.abs(aptNum - transNum));
					throw new RuntimeException("异常订单数量超过预警值，请手工处理对账");
				}
				
				//金额异常判断
				if (bill_error_money_min <= Math.abs(transMoney - aptMoney) && bill_error_money_max >= Math.abs(transMoney - aptMoney)) {
					compBillResult.setErr_type(Consts.COMP_BILL_ERROR_00F);
					compBillResult.setErr_message("差异金额超过设定阀值（银行对账金额：" + transMoney/100 + 
							";分销平台实际金额：" + aptMoney/100 + ")");
					compBillResult.setSms_content("差异金额超过设定阀值（银行对账金额：" + transMoney/100 + 
							";分销平台实际金额：" + aptMoney/100 + ")");
					compBillResult.setBill_money(Math.abs(transMoney - aptMoney)/100);
					throw new RuntimeException("异常金额超过预警值，请手工处理对账");
				}
			}
		}
		countMap.putAll(validMap);
		return countMap;
	}
	
	/**
	 * 封装ftp请求信息
	 * @作者 MoChunrun
	 * @创建日期 2013-10-22 
	 * @param config
	 * @return
	 */
	public Map packageFtpConfig(CheckAcctConfig config){
		Map ftpConfig = new HashMap();
		ftpConfig.put("ip", config.getUp_ftp_addr());
		ftpConfig.put("port", config.getUp_ftp_port());
		ftpConfig.put("userName", config.getUp_ftp_user());
		ftpConfig.put("passWord", config.getUp_ftp_passwd());
		return ftpConfig;
	}
	
	/**
	 * 封装文件内容，写计费对账文件
	 * @作者 MoChunrun
	 * @创建日期 2013-10-22 
	 * @param transDate
	 * @param type_code
	 * @return
	 */
	public List<BaseBill> qryDepositBill(String transDate,String type_code,Class<? extends BaseBill> clazz){
		//封装文件内容，写计费对账文件
		String billSql = "select b.wss_transaction_id transaction_id, '' party_id,  a.userid unionOrgCode, " +
				"b.pay_amount, '605' busi_type from es_payment_list a, es_checkacct_info b where a.transaction_id = " +
				"b.wss_transaction_id and a.transdate = ? and a.type_code = ? and a.sequ = 0 and a.state = '1'";
		List<BaseBill> billList = this.baseDaoSupport.queryForList(billSql,clazz, transDate, type_code);
		return billList;
	}
	
	/**
	 * 对账执行入口
	 * @作者 MoChunrun
	 * @创建日期 2013-10-23 
	 * @return
	 */
	@Override
	public void perform(CheckAcctConfigReq req,CheckAcctConfigResp resp){
		CompBillResult compBillResult = resp.getCompBillResult();
		CheckAcctConfig config =req.getCheckAcctConfig();
		imlog.info("生成计费要求得对帐文件");		
		try {
			transDate =resp.getTransMainVo().getTransDate();
			List<BaseBill> fileList = checkBill(req, resp);
			//写文件并上传文件
			writeAndPutFile(fileList,config);
		} catch (Exception e) {
			compBillResult.setErr_type(Consts.COMP_BILL_ERROR_00G);
			compBillResult.setErr_message("对账逻辑异常");
			e.printStackTrace();
			throw new RuntimeException("对账逻辑异常：" + e.getMessage());
		}
	}
	
	/**
	 * 对账   如果不符合业务需求请重写该方法
	 * @作者 MoChunrun
	 * @创建日期 2013-10-23 
	 * @param req
	 * @param resp
	 * @return
	 */
	public List<BaseBill> checkBill(CheckAcctConfigReq req,CheckAcctConfigResp resp){
		//=======================================某模块功能对账
		//总数量map.get("trans_num")比对、总金额map.get("trans_money")比对，出错直接抛出异常
		transNumAndMoney = checkException(transDate, getType_code(), req.getCheckAcctConfig(), resp.getCompBillResult());
		
		
		List<BaseBill> fileList = new ArrayList<BaseBill>(); 		//存放对账内容
		//对账文件、完全以翼支付为准
		fileList.add(packageHeader());
		//11:已提交银行，未收到银行支付结果,12:可疑交易
		//1:银行支付不成功,2:支付成功，待送计费销帐 
		//3:支付成功，计费销帐失败
		//4:支付成功，计费销帐成功 A:支付成功，不需后续处理
		
		//场景一：第三方平台支付成功、平台订单失败、修改订单、并标识（取第三方平台支付文档的信息，此处只做订单状态变更）
		if(isExeScenes1())needCharge = executeNeedCharge(transDate,getType_code());
		
		//场景二：第三方平台支付和分销平台都有订单（不管分销平台是否成功），但是金额不同的订单（取第三方平台支付文档信息、此处只做订单变更），
		//这种情况有可能和其他场景有交集，所以处理标识放在不同字段中（其他场景fld_1，此场景fld_2）
		if(isExeScenes2())needUp = executeNeedUp(transDate,getType_code());
		
		//606退费、605支付
		//场景三：第三方平台支付为未成功，我们成功了，金额等信息从我们系统获取，需要追加第三方平台支付的对账文档，并将订单状态改为未支付
		if(isExeScenes3())refundList = executeRefund(transDate,getType_code());
		//封装文件内容，写计费对账文件 ; 生成送给计费的对账数据，说明：（只送成功矫正的数据给计费）
		packageDetial(fileList);
		return fileList;
	}
	
	/**
	 * 是否执行 场景一
	 * @作者 MoChunrun
	 * @创建日期 2013-10-23 
	 * @return
	 */
	public abstract boolean isExeScenes1();
	/**
	 * 是否执行 场景二
	 * @作者 MoChunrun
	 * @创建日期 2013-10-23 
	 * @return
	 */
	public abstract boolean isExeScenes2();
	/**
	 * 是否执行 场景三
	 * @作者 MoChunrun
	 * @创建日期 2013-10-23 
	 * @return
	 */
	public abstract boolean isExeScenes3();
	/**
	 * 获取type_code
	 * @作者 MoChunrun
	 * @创建日期 2013-10-23 
	 * @return
	 */
	public abstract String getType_code();
	
	/**
	 * 打包头
	 * @作者 MoChunrun
	 * @创建日期 2013-10-23 
	 * @return
	 */
	public abstract BaseBill packageBillHeader();
	
	/**
	 * 把继承BaseBill的实体放到detials里面
	 * @作者 MoChunrun
	 * @创建日期 2013-10-23 
	 * @param detials
	 */
	public abstract void packageDetial(List<BaseBill> detials);
	
	/**
	 * 打包头
	 * @作者 MoChunrun
	 * @创建日期 2013-10-23 
	 * @return
	 */
	public BaseBill packageHeader() {
		BaseBill bill = packageBillHeader();
		if(bill==null){
			BillHeader billHeader = new BillHeader();
			billHeader.setTransDate(transDate);
			if(transNumAndMoney!=null){
				billHeader.setTransNum(String.valueOf(transNumAndMoney.get("trans_num")));
				billHeader.setTransMoney(String.valueOf(transNumAndMoney.get("trans_money")));
			}
			return billHeader;
		}else{
			return bill;
		}
	}
	
	/**
	 * 写本地文件并上传到ftp服务器
	 * @作者 MoChunrun
	 * @创建日期 2013-10-23 
	 * @param fileList
	 * @param config
	 * @throws FrameException
	 */

	public void writeAndPutFile(List<? extends BaseBill> fileList,CheckAcctConfig config) throws FrameException{
		wirteFile(fileList, config);
		putfile(config);
	}
	
	/**
	 * 写本地文件
	 * @作者 MoChunrun
	 * @创建日期 2013-10-22 
	 * @param fileList
	 * @param config
	 * @throws FrameException
	 */
	public void wirteFile(List<? extends BaseBill> fileList,CheckAcctConfig config) throws FrameException{
		String bsnLocalFilePath = config.getBns_local_file_path();
		String bsnFileName = config.getBilldzfilename();
		imlog.info("生成对帐文件");
		String strTempFileName = bsnLocalFilePath + bsnFileName;
		imlog.debug("生成临时文件路径为"+strTempFileName);		
		File f = new File(strTempFileName);
		FileOutputStream os = null;
		try {
			if (!f.exists())f.createNewFile();
			
			os = new FileOutputStream(f);
			for (int i = 0 ; i < fileList.size(); i++){
				os.write(fileList.get(i).toString().getBytes());
			}	
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("生成对仗文件数据失败"+e.getMessage());
		} finally {
			if (os != null){
				try {
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("关闭文件流失败："+e.getMessage());
				}
			}
		}
	}

	/**
	 * 上传文件到ftp服务器
	 * @作者 MoChunrun
	 * @创建日期 2013-10-22 
	 * @param config
	 * @throws FrameException
	 */
	private void putfile(CheckAcctConfig config) throws FrameException{
		String bsnLocalFilePath = config.getBns_local_file_path();
		String bsnRemoteFilePath = config.getBns_remote_file_path();
		String bsnFileName = config.getBilldzfilename();
		Map ftpConfig = packageFtpConfig(config);
		FTPUtil.setArg(ftpConfig);
		FTPUtil.uploadFile(new File(bsnLocalFilePath + bsnFileName), bsnRemoteFilePath);
	}
	
}
