package com.ztesoft.net.mall.core.timer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.net.ftp.FTP;












import org.apache.log4j.Logger;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.util.FTPUtil;


public class OrderInfoToTXTTimer {
	private static Logger logger = Logger.getLogger(OrderInfoToTXTTimer.class);
	private static String INFO_SQL = " select  (eoe.order_id ||'|'||eoe.out_tid||'|'||to_char(eo.create_time,'yyyy/mm/dd HH24:mi:ss')||'|'||to_char(eoe.tid_time,'yyyy/mm/dd HH24:mi:ss')||'|'||to_char(eos.h_end_time,'yyyy/mm/dd HH24:mi:ss')||'|'|| "
								   + " case when exists(select 1 from es_order_route_log eor where eor.op_code in ('80','10')and eor.order_id=eo.order_id) "
								   + " then to_char((select max(distinct(eor.route_acceptime)) from es_order_route_log eor,es_order eo  where eor.op_code in ('80','10')and eor.order_id=eo.order_id),'yyyy/mm/dd HH24:mi:ss') "
								   + " else null  "
								   + " end ||'|'||to_char(eos.j_begin_time,'yyyy/mm/dd HH24:mi:ss')||'|'|| "
								   + " eoie.phone_num||'|'||eoe.flow_trace_id||'|'||eoe.refund_deal_type||'|'|| "
								   + " nvl(ed.sign_status,0)||'|'||decode(eoe.flow_trace_id,'J','1','0')||'|'|| "
								   + " egp.sn||'|'||eoe.order_city_code||'|'||ed.ship_mobile||'|'|| "
								   + " ed.ship_name||'|'||regexp_replace(ed.ship_addr,'(chr(10))|(\\s+)|([[:punct:]]+)','') "
								   + " ||'|'||eoev.iccid||'|'||ed.logi_no||'|'|| "
								   + " decode(ed.shipping_company,'EMS','EMS','EMS0001','EMS','EMS0002','EMS国内','ND0001','南都','NULL_VAL','空值','SF-FYZQYF','顺丰集中生产','SF0001','顺丰','SF0002','顺丰-国内','WSFW','WSFW','ZT0002','自提','ZY0001','自有配送-联通','ZY0002','自有配送-联通','SF0003','顺丰-国内','SF-HS','顺丰-华盛','ND0002','南都-省内','EMS0003','EMS省内') "
								   + " ||'|'||eoe.order_from) as info "
								   + " from es_order eo left join es_order_ext eoe  "
								   + " on eoe.order_id=eo.order_id  "
								   + " left join es_order_items_ext eoie  "
								   + " on eoie.order_id=eo.order_id "
								   + " left join es_delivery ed "
								   + " on ed.order_id=eoe.order_id "
								   + " left join es_order_extvtl eoev "
								   + " on eo.order_id=eoev.order_id "
								   + " left join es_dc_public_ext t "
								   + " on t.pkey=eoe.flow_trace_id "
								   + " left join  "
								   + " (select ee.order_id,max(ee.h_end_time) h_end_time ,max(ee.j_begin_time) j_begin_time from es_order_stats_tache ee "
								   + " left join es_order eo  "
								   + " on ee.order_id=eo.order_id "
								   + " group by ee.order_id ) eos "
								   + " on eos.order_id =eo.order_id "
								   + " left join es_goods_package egp "
								   + " on egp.goods_id=eo.goods_id "
								   + " where  eoe.order_from='10003' "
								   + " and t.stype = 'DIC_ORDER_NODE' "
								   + " and trunc(eo.create_time, 'dd') >= trunc(sysdate - ?, 'dd') "
								   + " and trunc(eo.create_time, 'dd') < trunc(sysdate, 'dd') ";
	
	public void create() {
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "create")) {
  			return;
		}
		PrintWriter writer = null;
		logger.info("------------------------begin---------------------------");
		String time = "";
		String url = "";
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String cycle = cacheUtil.getConfigInfo("ORDER_DETAILS_CYCLE");
		String num = cacheUtil.getConfigInfo("ORDER_DETAILS_NUM");
		String order_txt_login = cacheUtil.getConfigInfo("ORDER_TXT_LOGIN");
		//Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		//Matcher m = null;
		String local_url = cacheUtil.getConfigInfo("ORDER_TXT_LOCAL");
		String dir_url = cacheUtil.getConfigInfo("ORDER_TXT_URL");
		String[] txt_login = order_txt_login.split("\\|");
		try{
			SimpleDateFormat new_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			logger.info("---------------------------------数据查询开始时间："+new_time.format(new java.util.Date())+"--------------------------------");
			List queueList = support.queryForList(INFO_SQL,cycle);
			logger.info("---------------------------------数据查询结束时间："+new_time.format(new java.util.Date())+"--------------------------------");
			time=DateUtil.getTime3();
			logger.info(local_url+"/"+time);
			File f1 = new File(local_url+"/"+time); 
			if(f1.exists()) delFolder(local_url+"/"+time); f1.delete();
			f1.mkdirs();
			/*List new_list = new ArrayList();
			
			for(int y=0;y<600000;y++){
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
					logger.info(local_url+"/"+time+"/WSSMALL_ORDER_INFO_"+time+String.format("%02d", j)+".txt");
					File f = new File(local_url+"/"+time+"/WSSMALL_ORDER_INFO_"+time+String.format("%02d", j)+".txt"); 
					if(f.exists()) f.delete();
					writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(local_url+"/"+time+"/WSSMALL_ORDER_INFO_"+time+String.format("%02d", j)+".txt"), "UTF-8"));
					
				}
				String new_str = Const.getStrValue((Map)queueList.get(i-1), "info");
				/*m = p.matcher(new_str);
				new_str = m.replaceAll("");*/
				str += new_str+"\n";
				//writer.print(new_str);
				if(i%Integer.parseInt(num)==0||i==queueList.size()){
					/*logger.info("----------------------第"+j+"个文件内容结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
					logger.info("----------------------第"+j+"个文件生成开始时间:"+new_time.format(new java.util.Date())+"--------------------------");
					FTPUtil.uploadFile(str.getBytes("utf-8"), url, "WSSMALL_ORDER_INFO_"+time+String.format("%02d", j)+".txt");
					logger.info("----------------------第"+j+"个文件生成结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
					*///FTPUtil.closeConnect();
					logger.info("----------------------第"+j+"个文件内容结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
					logger.info("----------------------第"+j+"个文件写入开始时间:"+new_time.format(new java.util.Date())+"--------------------------");
					writer.print(str);
					writer.close();
					logger.info("----------------------第"+j+"个文件写入结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
					
				}
				
			}
			logger.info("-----------------------生成TXTfor循环结束时间："+new_time.format(new java.util.Date())+"--------------------------");
    		
    		
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				//FTPUtil.uploadFile("".getBytes("utf-8"), url, "WSSMALL_ORDER_INFO_"+time+"_DONE.txt");
				//.closeConnect();
				logger.info(local_url+"/"+time+"/WSSMALL_ORDER_INFO_"+time+"_DONE.txt");
				writer = new PrintWriter(local_url+"/"+time+"/WSSMALL_ORDER_INFO_"+time+"_DONE.txt");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				writer.close();
				Map login_map = new HashMap();
				login_map.put("ip", txt_login[0]);
				login_map.put("port", txt_login[1]);
				login_map.put("userName", txt_login[2]);
				login_map.put("passWord", txt_login[3]);
				FTPUtil.setArg(login_map);
				FTPUtil.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件 
				Date date=new Date();
				//DateFormat format=new SimpleDateFormat("yyyyMMdd");
				url = dir_url+"/"+time;
				//FTPUtil.closeConnect();
				boolean is_del = FTPUtil.isDirExist(url);
				FTPUtil.closeConnect();
				FTPUtil.makeDirectoryToPath(time,dir_url,is_del);
				//FTPUtil.closeConnect();
				FTPUtil.uploadManyFile(local_url+"/"+time,dir_url+"/"+time);
				FTPUtil.closeConnect();
				//FTPUtil.closeConnect();// 关闭连接 
			}
		}
		
		logger.info("------------------------end---------------------------");
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
		OrderInfoToTXTTimer a = new OrderInfoToTXTTimer();
		a.create();
	}
}
