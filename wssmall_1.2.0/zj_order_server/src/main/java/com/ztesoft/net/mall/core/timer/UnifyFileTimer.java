package com.ztesoft.net.mall.core.timer;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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


public class UnifyFileTimer {
	private static Logger logger = Logger.getLogger(UnifyFileTimer.class);
	private static String UNIFY_SQL = "select eo.order_id ||'|'||eoe.out_tid ||'|'||to_char(eoe.tid_time,'yyyy/mm/dd HH24:mi:ss') ||'|'||eoie.phone_num ||'|'||			"+
			" eoe.order_city_code ||'|'||eoel.order_provinc_code ||'|'||eo.ship_name ||'|'||eo.ship_mobile ||'|'||ed.province_id ||'|'||         "+
			" ed.city_id ||'|'||ed.region_id ||'|'||regexp_replace(replace(eo.ship_addr,chr(10),''),'[[:punct:]]+') ||'|'||                      "+
			" eoip.cert_address ||'|'||to_char(eoip.cert_failure_time,'yyyy/mm/dd HH24:mi:ss') ||'|'||eoip.cert_card_num ||'|'||                 "+
			" eoip.certi_type ||'|'||eoel.phone_owner_name ||'|'||eoel.customertype ||'|'||egp.sn                                                "+
			" ||'|'||eoel.goodsname ||'|'||                                                                                                     "+
			" to_char(eos.j_begin_time,'yyyy/mm/dd HH24:mi:ss') ||'|'||                                                                          "+
			" case when eol.active_flag in ('2','3')                                                                                             "+
			"  then '1'                                                                                                                         "+
			"  else '0'                                                                                                                         "+
			" end                                                                                                                                "+
			" ||'|'||to_char(eos.h_end_time,'yyyy/mm/dd HH24:mi:ss') ||'|'||to_char(eorl.route_acceptime,'yyyy/mm/dd HH24:mi:ss') ||'|'||       "+
			" ed.logi_no ||'|'||eoel.iccid as info                                                                                              "+
			" from es_order eo left join es_order_ext eoe                                                                                        "+
			"     on eoe.order_id=eo.order_id                                                                                                   "+
			" left join es_order_items_ext eoie                                                                                                  "+
			"     on eoie.order_id=eo.order_id                                                                                                  "+
			" left join es_delivery ed                                                                                                           "+
			"     on ed.order_id=eoe.order_id                                                                                                   "+
			" left join es_order_realname_info eol                                                                                               "+
			"     on eol.order_id=eo.order_id                                                                                                   "+
			" left join es_order_extvtl eoel                                                                                                     "+
			"     on eoel.order_id=eo.order_id                                                                                                  "+
			" right join                                                                                                                         "+
			"     (select es.order_id,max(es.route_acceptime) route_acceptime from es_order_route_log es                                        "+
			"             left join es_order eo on es.order_id=eo.order_id                                                                      "+
			"             where es.op_code in ('10','80')                                                                                       "+
			"             and trunc(es.route_acceptime,'dd') =trunc(sysdate-1,'dd') group by es.order_id ) eorl                                 "+
			"     on eorl.order_id=eo.order_id                                                                                                  "+
			" left join                                                                                                                          "+
			"     (select distinct(eot.order_id) , eot.cert_address,eot.cert_card_num,eot.cert_failure_time,eot.certi_type                      "+
			"             from es_order_items_prod_ext eot                                                                                      "+
			"             left join es_order eo on eot.order_id=eo.order_id ) eoip                                                              "+
			"     on eoip.order_id=eo.order_id                                                                                                  "+
			" left join es_goods_package egp                                                                                                     "+
			"     on egp.goods_id=eo.goods_id                                                                                                   "+
			" left join                                                                                                                          "+
			"     (select ee.order_id,max(ee.h_end_time) h_end_time ,max(ee.j_begin_time) j_begin_time from es_order_stats_tache ee             "+
			"             left join es_order eo                                                                                                 "+
			"             on ee.order_id=eo.order_id                                                                                            "+
			"             group by ee.order_id ) eos                                                                                            "+
			"     on eos.order_id =eo.order_id                                                                                                  "+
			" where ed.sign_status='1'                                                                                                           "+
            " and ed.source_from = '"+ManagerUtils.getSourceFrom()+"' ";
	
	private ChannelSftp sftpchannel;
	private SFTPChannel channel;
	public void upload() {

		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "upload")) {
  			return;
		}
		PrintWriter writer = null;
		/*PrintWriter writer = null;*/
		logger.info("------------------------begin---------------------------");
		String time = "";
		String url = "";
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String num = cacheUtil.getConfigInfo("ORDER_DETAILS_NUM");
		
		//获取一体化上传配置
		String url1 = cacheUtil.getConfigInfo("UNIFY_PATH");
		String idAndPass = cacheUtil.getConfigInfo("UNIFY_ID_PASSWORD");
		String ip = cacheUtil.getConfigInfo("UNIFY_IP");
		String port = cacheUtil.getConfigInfo("UNIFY_PORT");
		String local_url = cacheUtil.getConfigInfo("ORDER_UNIFY_LOCAL");
		//Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		//Matcher m = null;
		Map<String,String> login_map = new HashMap<String,String>();
		try{
				
			SimpleDateFormat new_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String[] ids = idAndPass.split("/");
			String userName  = "";
			String passWord = "";
			if(ids != null && ids.length >= 2) {
				userName = ids[0];
				passWord = ids[1];
			}
			
			
			login_map.put("ip", ip);
			login_map.put("port", port);
			login_map.put("userName", userName);
			login_map.put("passWord", passWord);
			
			

			logger.info("---------------------------------数据查询开始时间："+new_time.format(new java.util.Date())+"--------------------------------");
			List queueList = support.queryForList(UNIFY_SQL);
//			List queueList = new ArrayList();	
			logger.info("---------------------------------数据查询结束时间："+new_time.format(new java.util.Date())+"--------------------------------");
						
			time=DateUtil.getTime3();
			url = url1+"/"+time;
			File f1 = new File(local_url+"/"+time); 
			if(f1.exists()) delFolder(local_url+"/"+time); f1.delete();
			f1.mkdirs();
			
			/*List new_list = new ArrayList();
			
			for(int y=0;y<Integer.parseInt("160");y++){
				//Map map = (Map)queueList.get(0);
				Map map = new HashMap();
				map.put("info", "ZBWO170681613030000103|20170417002|2017/04/1716:13:01|2017/04/1703:16:02||||18557500035|X|01|0|0|90063345|任全康|15658119769|杭州市滨江区滨安路1336号\n杭州市滨江区滨安路1336号\n杭州市滨江区滨安路1336号");
				new_list.add(map);
			}
			queueList = new_list;*/
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
					File f = new File(local_url+"/"+time+"/WSSMALL_ORDER_INFO_"+time+String.format("%02d", j)+".txt"); 
					if(f.exists()) f.delete();
					writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(local_url+"/"+time+"/WSSMALL_ORDER_INFO_"+time+String.format("%02d", j)+".txt"), "UTF-8"));
					
					
				}
				String new_str = Const.getStrValue((Map)queueList.get(i-1), "info");

				str += new_str+"\n";
				if(i%Integer.parseInt(num)==0||i==queueList.size()){
					logger.info("----------------------第"+j+"个文件内容结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
					logger.info("----------------------第"+j+"个文件生成开始时间:"+new_time.format(new java.util.Date())+"--------------------------");
					/*InputStream is = new ByteArrayInputStream(str.getBytes("utf-8"));
					//上传文件
					sftpchannel.put(is, "WSSMALL_ORDER_INFO_"+time+String.format("%02d", j)+".txt");*/
					writer.print(str);
					writer.close();
					logger.info("----------------------第"+j+"个文件生成结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
				}
			}
			logger.info("-----------------------生成TXTfor循环结束时间："+new_time.format(new java.util.Date())+"--------------------------");
			//获取sftp连接
			channel = new SFTPChannel();
			sftpchannel = channel.getChannel(login_map, 20000);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//创建目录，并且cd到改目录下
			createDir(url, sftpchannel);
			//sftpchannel.disconnect();
			channel.bacthUploadFile(url,local_url+"/"+time+"/",false,login_map, 20000);
			sftpchannel.disconnect();
			try {
				channel.closeChannel();
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
	 
	//删除文件夹
		//param folderPath 文件夹完整绝对路径

		     public static void delFolder(String folderPath) {
		     try {
		        delAllFile(folderPath); //删除完里面所有内容
		        String filePath = folderPath;
		        filePath = filePath.toString();
		        java.io.File myFilePath = new java.io.File(filePath);
		        myFilePath.delete(); //删除空文件夹
		     } catch (Exception e) {
		       e.printStackTrace(); 
		     }
		}

		//删除指定文件夹下所有文件
		//param path 文件夹完整绝对路径
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
		             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
		             delFolder(path + "/" + tempList[i]);//再删除空文件夹
		             flag = true;
		          }
		       }
		       return flag;
		     }
	    
	public static void main(String[] args) {
		UnifyFileTimer a = new UnifyFileTimer();
		a.upload();
//		String url = "Order_Audit_yyyymmdd_"+String.format("%04d", 1)+".REQ";
//		logger.info(url);
	}	 
}