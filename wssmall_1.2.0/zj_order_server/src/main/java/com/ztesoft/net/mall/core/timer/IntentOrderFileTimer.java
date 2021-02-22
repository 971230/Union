package com.ztesoft.net.mall.core.timer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.ztesoft.common.exception.SystemException;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.util.SFTPChannel;

public class IntentOrderFileTimer {
	private static Logger logger = Logger.getLogger(IntentOrderFileTimer.class);
	/*
	 *  select (eoi.order_id || '|' || eoi.intent_order_id || '|' ||
        to_char(eoi.create_time, 'yyyy/mm/dd HH24:mi:ss') || '|' ||
        eoi.goods_name || '|' || eoi.order_city_code || '|' ||
        eoi.referee_num || '|' || eoi.referee_name || '|' ||
        eoi.develop_point_code || '|' || eoi.develop_code || '|' ||
        eoi.ship_tel || '|' || eoi.ship_name || '|' ||
        regexp_replace(replace(eoi.ship_addr, chr(10), ''), '[[:punct:]]') || '|' ||
        eoi.status || '|' || case
          when eoi.status = '03' then
           eoi.order_id
          else
           ''
        end || '|' || eoie.phone_num || '|' || eoi.goods_name || '|' ||
        to_char(eoe.tid_time, 'yyyy/mm/dd HH24:mi:ss') || '|' || case
          when eoi.status = '03' then
           to_char((select max(m.create_time) as create_time
                     from es_rule_exe_log m
                    where m.obj_id = eoi.order_id
                      and m.exe_result = '0'
                      and m.flow_inst_id in ('J', 'L')),
                   'yyyy/mm/dd HH24:mi:ss')
          else
           ''
        end || '|' || eoe.flow_trace_id || '|' || eovtl.out_office || '|' ||
        eovtl.out_operator || '|' || case
          when eoie.bssorderid is null or trim(eoie.bssorderid) = '' then
           eoie.active_no
          else
           eoie.bssorderid
        end) as info
   from es_order_intent eoi
   left join es_order_items_ext eoie
     on eoi.order_id = eoie.order_id
   left join es_order_ext eoe
     on eoi.order_id = eoe.order_id
   left join es_order_extvtl eovtl
     on eoi.order_id = eovtl.order_id
  where eoi.source_from = 'ECS';
	 */
	
	private static String ITENTORDER_SQL = "  select (eoi.order_id || '|' || eoi.intent_order_id || '|' || "
			+ "  to_char(eoi.create_time, 'yyyy/mm/dd HH24:mi:ss') || '|' ||"
			+ "  eoi.goods_name || '|' || "
			+ "  eoi.order_city_code || '|' || eoi.referee_num || '|' ||"
			+ "  eoi.referee_name || '|' || eoi.develop_point_code || '|' ||"
			+ "  eoi.develop_code || '|' || eoi.ship_tel || '|' || eoi.ship_name || '|' ||"
			+ "  regexp_replace(replace(eoi.ship_addr, chr(10), ''), '[[:punct:]]+') || '|' || "
			+ "  eoi.status || '|' || case "
			+ "  when eoi.status = '03' then" 
			+ "  eoi.order_id else '' end "
			+ "  || '|' || "
			+ "  eoie.phone_num || '|' || eoi.goods_name || '|' ||"
			+ "  to_char(eoe.tid_time, 'yyyy/mm/dd HH24:mi:ss') || '|' || case"
			+ "  when eoi.status = '03' then "
			+ "  to_char((select max(m.create_time) as create_time"
			+ "  from (select * from es_rule_exe_log union select * from es_rule_exe_log_bak) m  where m.obj_id = eoi.order_id and m.flow_inst_id in ('J', 'L')),'yyyy/mm/dd HH24:mi:ss')"
			+ "  else '' end"
			+ "  || '|' ||"
			+ "  eoe.flow_trace_id || '|' || eovtl.out_office || '|' ||"
			+ "  eovtl.out_operator || '|' || case"
			+ "  when eoie.bssorderid is null or trim(eoie.bssorderid) = '' then"
			+ "  eoie.active_no else eoie.bssorderid end"
			+ ") "
			+ "as info" + "  from es_order_intent eoi"
			+ "  left join es_order_items_ext eoie" + "  on eoi.order_id = eoie.order_id"
			+ "  left join es_order_ext eoe" + "  on eoi.order_id = eoe.order_id" + "  left join es_order_extvtl eovtl"
			+ "  on eoi.order_id = eovtl.order_id" + "  where eoi.source_from ='" + ManagerUtils.getSourceFrom() + "'"
			+ "  and eoi.create_time >= sysdate - ?" + " and eoi.create_time < sysdate";

	private ChannelSftp sftpchannel;
	private SFTPChannel channel;

	public void upload() throws Exception {
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "upload")) {
  			return;
		}
		PrintWriter writer = null;
		SimpleDateFormat new_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*PrintWriter writer = null;*/
		try {
			logger.info("------------------------begin---------------------------");
			String time = "";
			String url = "";
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			
			
			//获取一体化上传配置
			String cycle = cacheUtil.getConfigInfo("ORDER_DETAILS_CYCLE"); //多少天的数据
			String num = cacheUtil.getConfigInfo("ORDER_DETAILS_NUM"); //30000行
			String order_txt_login = cacheUtil.getConfigInfo("ORDER_TXT_LOGIN"); //登陆信息
			String url1 = cacheUtil.getConfigInfo("ORDER_TXT_URL");
			String port = cacheUtil.getConfigInfo("UNIFY_PORT");
			Map<String,String> login_map = new HashMap<String,String>();
			String[] txt_login = order_txt_login.split("\\|");
			login_map.put("ip", txt_login[0]);
			login_map.put("port", port);
			login_map.put("userName", txt_login[2]);
			login_map.put("passWord", txt_login[3]);
			
			channel = new SFTPChannel();
			
			sftpchannel = channel.getChannel(login_map, 20000);

			logger.info("---------------------------------数据查询开始时间："+new_time.format(new java.util.Date())+"--------------------------------");
			List queueList = support.queryForList(ITENTORDER_SQL,cycle);
//			List queueList = new ArrayList();	
			logger.info("---------------------------------数据查询结束时间："+new_time.format(new java.util.Date())+"--------------------------------");
						
			time=DateUtil.getTime3();
			url = url1+"/"+time;
			
			//创建目录，并且cd到改目录下
			createDir(url, sftpchannel);
							
//			List new_list = new ArrayList();
	////	
//		for(int y=0;y<Integer.parseInt("160");y++){
////			Map map = (Map)queueList.get(0);
//			Map map = new HashMap();
//			map.put("info", "ZBWO170681613030000103|20170417002|2017/04/1716:13:01|2017/04/1703:16:02||||18557500035|X|01|0|0|90063345|任全康|15658119769|杭州市滨江区滨安路1336号\n杭州市滨江区滨安路1336号\n杭州市滨江区滨安路1336号");
//			new_list.add(map);
//		}
//		queueList = new_list;
			int j = 0;
			String str = "";
			logger.info("-----------------------生成TXTfor循环开始时间："+new_time.format(new java.util.Date())+"--------------------------");
			for(int i=1;i<=queueList.size();i++){
				if(i%Integer.parseInt(num)==1){
					j++;
					str = "";
					logger.info("----------------------第"+j+"个文件内容开始时间:"+new_time.format(new java.util.Date())+"--------------------------");
					/*File f = new File("E:\\WSSMALL_ORDER_INFO_"+time+String.format("%02d", j)+".txt"); 
					if(f.exists()) f.delete();
					writer = new PrintWriter("E:\\WSSMALL_ORDER_INFO_"+time+String.format("%02d", j)+".txt");*/
			}
			String new_str = Const.getStrValue((Map)queueList.get(i-1), "info");

			str += new_str+"\n";
			if(i%Integer.parseInt(num)==0||i==queueList.size()){
				logger.info("----------------------第"+j+"个文件内容结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
				logger.info("----------------------第"+j+"个文件生成开始时间:"+new_time.format(new java.util.Date())+"--------------------------");
				InputStream is = new ByteArrayInputStream(str.getBytes("utf-8"));
				//上传文件
				sftpchannel.put(is, "INTENT_ORDER_INFO"+time+String.format("%02d", j)+".txt");
				logger.info("----------------------第"+j+"个文件生成结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
			}
		}
		logger.info("-----------------------生成TXTfor循环结束时间："+new_time.format(new java.util.Date())+"--------------------------");
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			String time1 = DateUtil.getTime3();
			if(sftpchannel != null) {
				InputStream is = new ByteArrayInputStream("".getBytes("utf-8"));
				sftpchannel.put(is, "INTENT_ORDER_INFO"+time1+"_DONE"+".txt");
				sftpchannel.disconnect();
			}
			try {
				if(channel != null) {
					channel.closeChannel();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.info("------------------------end---------------------------");
	}

	/** 
     * 创建一个文件目录 
     */  
    public void createDir(String createpath, ChannelSftp sftp) {
    	try {
    		if(isDirExist(createpath,sftp)) {
    			sftp.cd(createpath);
    			return;
    		}
    		String pathArry[] = createpath.split("/");  
    	    StringBuffer filePath = new StringBuffer("/");
    	    for (String path : pathArry) {
				if(path.equals("")) {
					continue;
				}
				filePath.append(path+"/");
				if(isDirExist(filePath.toString(),sftp)) {
					sftp.cd(filePath.toString());
				} else {
					sftp.mkdir(filePath.toString());  
			        // 进入并设置为当前目录  
			        sftp.cd(filePath.toString()); 
				}
			}
    	    sftp.cd(createpath);
    	}catch(SftpException e) {
    		throw new SystemException("创建该目录:"+createpath+"出错");
    	}
    }

	/**        
	 *    判断目录是否存在
	 */
	 public boolean isDirExist(String directory,ChannelSftp sftp) {
		 boolean isDirExistFlag = false;
		 try {
			 SftpATTRS attrs = sftp.lstat(directory);
			 isDirExistFlag = true;
			 
			 return attrs.isDir();
		 }catch(Exception e) {
			 if(e.getMessage().toLowerCase().equals("no such file")) {
				 isDirExistFlag = false;
			 }
		 }
		 return isDirExistFlag;   
	}

	/** 
	  * sftp断开连接 
	  */  
	 public void disconnect() {
		 if(this.sftpchannel != null) {
			 if(this.sftpchannel.isConnected()) {
				 this.sftpchannel.disconnect();
			 } else if(this.sftpchannel.isClosed()) {
				 logger.info("sftp is closed already");
			 }
		 }	 
	 }

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		UnifyFileTimer a = new UnifyFileTimer();
		a.upload();
		// String url = "Order_Audit_yyyymmdd_"+String.format("%04d", 1)+".REQ";
		// logger.info(url);
	}
}