package com.ztesoft.net.mall.core.timer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.log4j.Logger;
import org.springframework.web.util.FTPUtil;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.bss.req.AgencyAcctPayNotifyReq;
import zte.net.ecsord.params.bss.vo.ACCT_REFUND_INFO;
import zte.net.ecsord.params.bss.vo.AGENCY_ACCT_PAY_NOTIFY_REQ;
import zte.net.ecsord.params.bss.vo.UNI_BSS_HEAD;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.model.CheckAcctConfig;
import com.ztesoft.net.model.CheckAcctLog;
import com.ztesoft.net.model.OrderItemAgentMoney;
import com.ztesoft.net.service.IAgentMoneyManager;

/**
 * 对账定时任务
 * @作者 MoChunrun
 * @创建日期 2015-1-18 
 * @版本 V 1.0
 */
public class AgentMoneySyncTimer {
	
	public static final String CHECK_ACC_SYSTEM_ID = "8899"; //8799 测试
	public static final String SPLIT = ",";
	public static int seq = 1;
	public static String FROM_SYSTEM_CODE = "9900";
	public static String TO_SYSTEM_CODE = "51";
	private static Logger logger = Logger.getLogger(AgentMoneySyncTimer.class);
	public void sync(){
		//if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"sync"))
  		//	return ;
		int record_num = 0;
		double total_money = 0d;
		IAgentMoneyManager manager = SpringContextHolder.getBean("agentMoneyManager");
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String day = df.format(new Date());
		String from_sys_code = "";
		String to_sys_code = "";
		try{
			List<OrderItemAgentMoney> list = manager.listPreDayAgentItems();
			record_num = list.size();
			StringBuffer sb = new StringBuffer(10000);
			for(OrderItemAgentMoney oi:list){
				AgencyAcctPayNotifyReq req = new AgencyAcctPayNotifyReq();
				req.setNotNeedReqStrOrderId(oi.getOrder_id());
				OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(oi.getOrder_id()).getOrderExtBusiRequest();
				OrderItemBusiRequest orderItemExt = CommonDataFactory.getInstance().getOrderTree(oi.getOrder_id()).getOrderItemBusiRequests().get(0);
				final String tradeId =  orderItemExt.getOrderItemExtBusiRequest().getActive_no(); 
				req.setORDER_ID(orderExt.getCol2());     
				req.setORG_ORDER_ID(orderExt.getCol2());
				String fee = oi.getCol6();
				//如果扣减金额为空侧为返销金额
				//if(StringUtil.isEmpty(fee))fee = oi.getCol7();
				req.setPAY_FEE(fee);
				if(!StringUtil.isEmpty(fee)){
					try{
						total_money += Double.parseDouble(fee);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
				//交易类型 0：扣款；1：返销
				String fee_type = "0";//StringUtil.isEmpty(oi.getCol8())?"1":"0";
				req.setNotNeedReqTRADE_TYPE(fee_type);
				if(StringUtil.isEmpty(from_sys_code)){
					UNI_BSS_HEAD uh = req.getUNI_BSS_HEAD();
					from_sys_code = uh.getMSG_SENDER();
				}
				if(StringUtil.isEmpty(to_sys_code)){
					UNI_BSS_HEAD uh = req.getUNI_BSS_HEAD();
					to_sys_code = uh.getMSG_RECEIVER();
				}
				AGENCY_ACCT_PAY_NOTIFY_REQ acct = req.getAGENCY_ACCT_PAY_NOTIFY_REQ();
				sb.append(tradeId==null?"":tradeId).append(SPLIT).append("").append(SPLIT).append(acct.getSERVICE_CLASS_CODE()==null?"":acct.getSERVICE_CLASS_CODE()).append(SPLIT)
					.append(acct.getAREA_CODE()==null?"":acct.getAREA_CODE()).append(SPLIT).append(acct.getSERIAL_NUMBER()==null?"":acct.getSERIAL_NUMBER()).append(SPLIT).append(acct.getTRADE_TYPE()==null?"":acct.getTRADE_TYPE()).append(SPLIT)
					.append(acct.getTRADE_DATE()==null?"":acct.getTRADE_DATE()).append(SPLIT).append(acct.getTRADE_TIME()==null?"":acct.getTRADE_TIME()).append(SPLIT).append(oi.getCol8()==null?"0":oi.getCol8()).append(SPLIT)
					.append(acct.getCHANNEL_ID()==null?"":acct.getCHANNEL_ID()).append(SPLIT).append(acct.getCHANNEL_NAME()==null?"":acct.getCHANNEL_NAME()).append(SPLIT).append(acct.getEPARCHY_CODE()==null?"":acct.getEPARCHY_CODE()).append(SPLIT)
					.append(acct.getCITY_CODE()).append(SPLIT).append(acct.getPAY_FEE()).append(SPLIT).append(oi.getO_order_id()==null?"":oi.getO_order_id()).append(SPLIT)
					.append("").append(SPLIT).append(SPLIT).append("\r\n");
				
				//返销
				fee = oi.getCol7();
				if(!StringUtil.isEmpty(fee)){
					record_num += 1;
					fee_type = "1";
					AgencyAcctPayNotifyReq dreq = new AgencyAcctPayNotifyReq();
					dreq.setNotNeedReqStrOrderId(oi.getOrder_id());
					dreq.setORDER_ID(oi.getC_order_id());
					dreq.setORG_ORDER_ID(oi.getO_order_id());
					//如果扣减金额为空侧为返销金额
					//if(StringUtil.isEmpty(fee))fee = oi.getCol7();
					dreq.setPAY_FEE(fee);
					if(!StringUtil.isEmpty(fee)){
						try{
							total_money += Double.parseDouble(fee);
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
					//交易类型 0：扣款；1：返销
					dreq.setNotNeedReqTRADE_TYPE(fee_type);
					
					acct = dreq.getAGENCY_ACCT_PAY_NOTIFY_REQ();
					ACCT_REFUND_INFO rbacc = acct.getACCT_REFUND_INFO();
					sb.append(tradeId==null?"":tradeId).append(SPLIT).append("").append(SPLIT).append(acct.getSERVICE_CLASS_CODE()==null?"":acct.getSERVICE_CLASS_CODE()).append(SPLIT)
						.append(acct.getAREA_CODE()==null?"":acct.getAREA_CODE()).append(SPLIT).append(acct.getSERIAL_NUMBER()==null?"":acct.getSERIAL_NUMBER()).append(SPLIT).append(acct.getTRADE_TYPE()==null?"":acct.getTRADE_TYPE()).append(SPLIT)
						.append(acct.getTRADE_DATE()==null?"":acct.getTRADE_DATE()).append(SPLIT).append(acct.getTRADE_TIME()==null?"":acct.getTRADE_TIME()).append(SPLIT).append(oi.getCol9()==null?"0":oi.getCol9()).append(SPLIT)
						.append(acct.getCHANNEL_ID()==null?"":acct.getCHANNEL_ID()).append(SPLIT).append(acct.getCHANNEL_NAME()==null?"":acct.getCHANNEL_NAME()).append(SPLIT).append(acct.getEPARCHY_CODE()==null?"":acct.getEPARCHY_CODE()).append(SPLIT)
						.append(acct.getCITY_CODE()).append(SPLIT).append(acct.getPAY_FEE()).append(SPLIT).append(rbacc.getORG_ORDER_ID()==null?"":rbacc.getORG_ORDER_ID()).append(SPLIT)
						.append(rbacc.getORG_PROVINCE_ORDER_ID()==null?"":rbacc.getORG_PROVINCE_ORDER_ID()).append(SPLIT).append(SPLIT).append("\r\n");
				}
			}
			String length = String.valueOf(record_num);
			if(length.length()<10){
				length = "00000000000"+length;
				length = length.substring(length.length()-10);
			}
			sb.insert(0, length+"\r\n");
			//logger.info(sb.toString());
			CheckAcctConfig config = manager.getCheckConfig(CHECK_ACC_SYSTEM_ID);
			if(config==null)throw new Exception("FTP没有配置");
			String name = "";
			
			try{
				Map map = new HashMap();
	    		map.put("ip", config.getUp_ftp_addr());
	    		map.put("port", config.getUp_ftp_port());
	    		map.put("userName", config.getUp_ftp_user());
	    		map.put("passWord", config.getUp_ftp_passwd());
	    		//请求文件名按如下规则命名：yyyymmdd_xx_woyungouAgencyAcctPayOrRefund_iiii_xxxx.REQ；
	    		//yyyymmdd为文件生成日期，xx为接收系统代码，iiii为发送系统代码，xxxx为文件序列号
	    		//xx就是51 iiii 就是9900  文件序号一定要4位
	    		from_sys_code = FROM_SYSTEM_CODE;
	    		to_sys_code = TO_SYSTEM_CODE;
	    		name = day+"_"+to_sys_code/*config.getSystem_name()*/+"_woyungouAgencyAcctPayOrRefund_"+from_sys_code+"_0001"+config.getA_file_tail();
	    		//logger.info(name);
	    		FTPUtil.setArg(map);
	    		FTPUtil.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件 
	    		logger.info(sb.toString());
	    		FTPUtil.uploadFile(sb.toString().getBytes("GBK"), config.getA_file_remote_path(), name);
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception("上传失败");
			}finally{
				try{
					FTPUtil.closeConnect();// 关闭连接 
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			try{
				CheckAcctLog log = new CheckAcctLog();
				log.setSystem_id(CHECK_ACC_SYSTEM_ID);
				log.setFile_name(name);
				log.setRecord_sum(record_num);
				log.setMoney_sum((int)total_money);
				log.setEnd_date("sysdate");
				log.setState("0");
				log.setSeq(seq);
				log.setAccounts_id(String.valueOf(System.currentTimeMillis()));
				manager.insertCheckAccLog(log);
			}catch(Exception ex){
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
			try{
				CheckAcctLog log = new CheckAcctLog();
				log.setSystem_id(CHECK_ACC_SYSTEM_ID);
				log.setFile_name("");
				log.setRecord_sum(record_num);
				log.setMoney_sum((int)total_money);
				log.setEnd_date("sysdate");
				log.setState("1");
				log.setSeq(seq);
				log.setAccounts_id(String.valueOf(System.currentTimeMillis()));
				manager.insertCheckAccLog(log);
			}catch(Exception w){
				
			}
		}finally{
			seq ++;
			if(seq>10000){
				seq=0;
			}
		}
		
	}
	
	public static void main(String[] args) {
		String str = "abvchehdh";
		logger.info(str.substring(str.length()-3));
	}
	
}
