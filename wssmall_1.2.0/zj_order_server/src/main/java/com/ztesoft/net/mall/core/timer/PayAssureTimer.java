package com.ztesoft.net.mall.core.timer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.log4j.Logger;
import org.springframework.web.util.FTPUtil;

import com.ztesoft.common.util.ListUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.CheckAcctConfig;
import com.ztesoft.net.model.CheckAcctLog;
import com.ztesoft.net.model.PayAssureVo;
import com.ztesoft.net.service.IAgentMoneyManager;
import com.ztesoft.net.service.IPayAssureManager;


/**
 * @Description 生成担保支付冻结文件
 * @author  zhangJun
 * @date    2015-1-23
 * @version 1.0
 */
public class PayAssureTimer {

	private static final Logger log = Logger.getLogger(PayAssureTimer.class);
	public static final String CHECK_ACC_SYSTEM_NAME = "baidu";//
	public static int seq = 1;
	
	
	/**
	 * 把昨天支付方式为担保免支付的订单，在退单归档后需要生成冻结文件，并上传到FTP
	 *  
	 */
	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}

		try {
			IPayAssureManager payAssureManager = SpringContextHolder.getBean("payAssureManager");
			List<CheckAcctConfig> config = payAssureManager.getCheckConfig(CHECK_ACC_SYSTEM_NAME);
			if(config!=null){
				for (CheckAcctConfig checkAcctConfig : config) {//一个支付机构一条记录
					if(!StringUtil.isEmpty(checkAcctConfig.getA_shop_no())){//配置有支付机构
						int record_num=this.createTxtFile(checkAcctConfig);
						this.uploadToFTP(checkAcctConfig,record_num);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
       
    
	}

	/**
	 * 在本地创建txt文件 ,返回号码记录条数
	 */
	private int createTxtFile(CheckAcctConfig config) throws Exception{
		if(config==null)throw new Exception("FTP没有配置");
		int record_num=0;
		
		String payProviderid=config.getA_shop_no();//支付机构id
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = sdf.format(new Date())+"_reject";
		String url = config.getBns_local_file_path()+File.separator;
        BufferedWriter writer = null;
       
       
        String path = null;
        try {
        	FileBaseUtil.createFolder(url);
			path = url + filename + ".txt.tmp";
            writer = new BufferedWriter(new FileWriter(path));
            IPayAssureManager payAssureManager = SpringContextHolder.getBean("payAssureManager");
            List<PayAssureVo> listotl = payAssureManager.YesterdayPayAssureList(payProviderid);
            record_num=listotl.size();
			if(!ListUtil.isEmpty(listotl)){
				for (PayAssureVo his : listotl) {
					StringBuffer rowStr = new StringBuffer("");
                	//用户手机号码（开户号码）
                	if(!StringUtil.isEmpty(his.getMobileTel())){
                		rowStr.append(his.getMobileTel());
                	}/*else{//如果开户号码为空，过滤本条数据，不导出
                		continue;
                	}*/
                	rowStr.append(",");//每条数据以","连接而成
                	//用户证件号码
                	if(!StringUtil.isEmpty(his.getCardNo())){
                		rowStr.append(his.getCardNo());
                	}else{
                		rowStr.append("");
                	}
                	rowStr.append(",");
                	//是3G或4G
                	if(!StringUtil.isEmpty(his.getCardNo())){
                		String is3gOr4g = EcsOrderConsts.NET_TYPE_3G.equals(his.getMobileNet()) ? "0" : "1";
                		rowStr.append(is3gOr4g);
                	}else{
                		rowStr.append("");
                	}
                	rowStr.append(",");
                	//用户编码 设为空
                	rowStr.append("");
                	rowStr.append(",");
                	//百度账号
                	if(!StringUtil.isEmpty(his.getBaiduId())){
                		rowStr.append(his.getBaiduId());
                	}else{
                		rowStr.append("");
                	}
                	rowStr.append(",");
                	//百度流水号
                	if(!StringUtil.isEmpty(his.getSerialNumber())){
                		rowStr.append(his.getSerialNumber());
                	}else{
                		rowStr.append("");
                	}
                	rowStr.append(",");
                	//违约金-
                	if(!StringUtil.isEmpty(his.getFrozenSection())){
                		rowStr.append(his.getFrozenSection());
                	}else{
                		rowStr.append("");
                	}
                	rowStr.append(",");
                	//违约类型 设置为3
                	rowStr.append("3");
                	rowStr.append(",");
                	//是否单独购买理财产品标志 设置为2
                	rowStr.append("2");
                	rowStr.append(",");
                	//订单号
                	if(!StringUtil.isEmpty(his.getOrderId())){
                		rowStr.append(his.getOrderId());
                	}else{
                		rowStr.append("");
                	}
                	
                	writer.write(new String(rowStr.toString().getBytes("UTF-8"), "UTF-8"));  
                	writer.newLine();
                	writer.flush();
                	log.info("**");
           }  

        } 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
				File tmpfile = new File(url + filename + ".txt.tmp");
				File realfile = new File(url + filename + ".txt");
				tmpfile.renameTo(realfile); //重命名
            }
        }
       return record_num;
	}
	

	
	/**
	 * 上传到FTP并记录日志
	* @Description: 
	* @param @throws Exception   
	* @return void    
	* @throws
	 */
	private void uploadToFTP(CheckAcctConfig config,int record_num) throws Exception{
		if(config==null)throw new Exception("FTP没有配置");
		IAgentMoneyManager manager = SpringContextHolder.getBean("agentMoneyManager");
		List<String> fileNameList=new ArrayList<String>();
		try {
			String upload_wait = config.getBns_local_file_path();//本地目录"E:\\baidu\\upload_wait";
			String upload_his = upload_wait+"_his";//本地归档目录 "E:\\baidu\\upload_his";
			String remote_file_path =config.getBns_remote_file_path();//远程目录
			Map map = new HashMap();
			map.put("ip", config.getUp_ftp_addr());
			map.put("port", config.getUp_ftp_port());
			map.put("userName", config.getUp_ftp_user());
			map.put("passWord", config.getUp_ftp_passwd());
			FTPUtil.setArg(map);
			FTPUtil.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件 
			
			File file=new File(upload_wait);//目录
			if (file.exists()){
				if(file.isDirectory()){
					File[] txtList=file.listFiles();
					for (File txtFile : txtList) {
						//上传
						String filePath=txtFile.getPath();//单个文件路径
						String fileIo=FileBaseUtil.read(filePath,null);////单个文件字符
						FTPUtil.uploadFile(fileIo.getBytes("UTF-8"),remote_file_path , txtFile.getName());
						
						//拷贝到归档的文件夹
						FileUtils.copyFile(txtFile,new File(upload_his+File.separator+txtFile.getName()));
						//删除
						txtFile.delete();
						fileNameList.add(txtFile.getName());
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("上传失败");
		}finally{
			try{
				FTPUtil.closeConnect();// 关闭连接 
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		//写日志
		try{
			for (String fileName : fileNameList) {
				CheckAcctLog log = new CheckAcctLog();
				log.setSystem_id(config.getSystem_id());
				log.setFile_name(fileName);
				log.setRecord_sum(record_num);
				//log.setMoney_sum((int)total_money);
				log.setEnd_date("sysdate");
				log.setState("0");
				log.setSeq(seq);
				log.setAccounts_id(String.valueOf(System.currentTimeMillis()));
				manager.insertCheckAccLog(log);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		
	}
	
	
	public static void main(String[] args) throws IOException {
		PayAssureTimer t=new PayAssureTimer();
		/*String url = "E:\\baidu\\upload_wait";//E:\\baidu\\upload_his
		FileBaseUtil.createFolder(url);
		List<PayAssureVo> list=new ArrayList<PayAssureVo>();
		PayAssureTimer t=new PayAssureTimer();
		for (int i = 0; i < 3; i++) {
			
		
		PayAssureVo pay=new PayAssureVo();
		pay.setMobileTel("111111");
		pay.setCardNo("222222");
		pay.setMobileNet("3G");//2G、4G、3G
		pay.setBaiduId("333333");
		pay.setSerialNumber("4444");
		pay.setFrozenSection("5");
		pay.setOrderId(String.valueOf(i));
		list.add(pay);
		}
		try {
			t.test(list);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		//上传ftp服务器
		//t.readFileList();
	}
}
