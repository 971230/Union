package com.ztesoft.net.mall.core.timer;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;


 

 
  
  

import org.apache.log4j.Logger;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.util.FTPUtil;


public class OrderPayInfoTimer {
	private static Logger logger = Logger.getLogger(OrderPayInfoTimer.class);
	private static String QUERY_SQL = 
			"SELECT distinct (to_char(eo.pay_time, 'YYYYMMDD') || '|' ||                                   "+
					"                to_char(eo.pay_time, 'YYYYMMDDHH24MISS') || '|' ||                            "+
					"                eddr.other_field_value || '|' || eoel.out_office || '|' ||                    "+
					"                decode(eoel.out_operator,                                                     "+
					"                        null,                                                                 "+
					"                        decode(eoe.order_from,                                                "+
					"                               '10071',                                                       "+
					"                               (select a.pname                                                "+
					"                                  from es_dc_public_ext a                                     "+
					"                                 where a.stype = 'DIC_BSS_GONGHAO'                            "+
					"                                   and a.pkey = eoe.order_city_code),                         "+
					"                               '10078',                                                       "+
					"                               (select a.pname                                                "+
					"                                  from es_dc_public_ext a                                     "+
					"                                 where a.stype = 'DIC_BSS_GONGHAO_NEW'                        "+
					"                                   and a.pkey = eoe.order_city_code)),                        "+
					"                        eoel.out_operator) || '|' ||                                          "+
					"                decode(eoe.order_from,                                                        "+
					"                        '10071',                                                              "+
					"                        '00000004',                                                           "+
					"                        '10078',                                                              "+
					"                        '00000006',                                                           "+
					"                        '10082',                                                              "+
					"                        '00000007',                                                           "+
					"                        '10072',                                                              "+
					"                        '00000008',                                                           "+
					"                        '10083',                                                              "+
					"                        '00000005','10093','00000012') || '|' ||                                                 "+
					"                decode(eoe.is_aop, '1', 'CBSS', '2', 'BSS')) || '|' ||                        "+
					"                substr(eoel.payplatformorderid,                                               "+
					"                       1,                                                                     "+
					"                       instr(eoel.payplatformorderid, '|', 1, 1) - 1) || '|' ||               "+
					"                eoie.phone_num || '|' || '0' || '|' || '1' || '|' || '' || '|' ||             "+
					"                eo.paymoney * 1000 || '|' || epl.pay_method as info                           "+
					"  FROM es_order eo                                                                            "+
					"  LEFT JOIN es_order_zhwq_adsl eozd                                                           "+
					"    ON eo.order_id = eozd.order_id                                                            "+
					"  LEFT JOIN es_order_ext eoe                                                                  "+
					"    ON eo.order_id = eoe.order_id                                                             "+
					"  left join es_order_items_ext eoie                                                           "+
					"    on eoie.order_id = eo.order_id                                                            "+
					"  LEFT JOIN es_order_extvtl eoel                                                              "+
					"    ON eo.order_id = eoel.order_id                                                            "+
					"  LEFT JOIN es_dc_public_dict_relation eddr                                                   "+
					"    ON eoe.order_city_code = eddr.field_value                                                 "+
					"  left join es_goods eg                                                                       "+
					"    on eg.goods_id = eo.goods_id                                                              "+
					"  LEFT JOIN es_payment_logs epl                                                               "+
					"    ON epl.order_id = eo.order_id                                                             "+
					" WHERE trunc(eo.create_time, 'dd') >= trunc(sysdate - 1, 'dd')                               "+
					"   and trunc(eo.create_time, 'dd') < trunc(sysdate, 'dd')                                     "+
					"   AND eddr.stype = 'bss_area_code'                                                           "+
					"   and eo.pay_status = '1'                                                                    "+
					"   AND eoel.payplatformorderid IS NOT NULL                                                    "+
					"   AND epl.pay_method IN ('51', '52')                                                         "+
					"    AND (eoel.refundplatformorderid is NULL or                                                 "+                        
                    "   (eoel.refundplatformorderid IS NOT NULL and                                                   "+                
                    "     TRUNC(eoe.refund_time, 'dd') = TRUNC(eo.create_time, 'dd')))                              "+
					"   AND regexp_substr(eoel.payplatformorderid, '[^|]', 1, 1, 'i') != 'null'                    "+
					"   AND eo.paymoney > 0                                                                        "+
					"UNION ALL                                                                                     "+
					"SELECT distinct (to_char(eoe.refund_time, 'YYYYMMDD') || '|' ||                                   "+
					"                to_char(eoe.refund_time, 'YYYYMMDDHH24MISS') || '|' ||                            "+
					"                eddr.other_field_value || '|' || eoel.out_office || '|' ||                    "+
					"                decode(eoel.out_operator,                                                     "+
					"                        null,                                                                 "+
					"                        decode(eoe.order_from,                                                "+
					"                               '10071',                                                       "+
					"                               (select a.pname                                                "+
					"                                  from es_dc_public_ext a                                     "+
					"                                 where a.stype = 'DIC_BSS_GONGHAO'                            "+
					"                                   and a.pkey = eoe.order_city_code),                         "+
					"                               '10078',                                                       "+
					"                               (select a.pname                                                "+
					"                                  from es_dc_public_ext a                                     "+
					"                                 where a.stype = 'DIC_BSS_GONGHAO_NEW'                        "+
					"                                   and a.pkey = eoe.order_city_code)),                        "+
					"                        eoel.out_operator) || '|' ||                                          "+
					"                decode(eoe.order_from,                                                        "+
					"                        '10071',                                                              "+
					"                        '00000004',                                                           "+
					"                        '10078',                                                              "+
					"                        '00000006',                                                           "+
					"                        '10082',                                                              "+
					"                        '00000007',                                                           "+
					"                        '10072',                                                              "+
					"                        '00000008',                                                           "+
					"                        '10083',                                                              "+
					"                        '00000005','10093','00000012') || '|' ||                                                 "+
					"                decode(eoe.is_aop, '1', 'CBSS', '2', 'BSS')) || '|' ||                        "+
					"                      eoel.refundplatformorderid             || '|' ||                        "+
					"                eoie.phone_num || '|' || CASE                                                 "+
					"                  WHEN trunc(eoe.refund_time, 'dd') =                                         "+
					"                       TRUNC(eo.create_time, 'dd') THEN                                       "+
					"                   '1'                                                                        "+
					"                  ELSE                                                                        "+
					"                   '2'                                                                        "+
					"                END || '|' || '1' || '|' ||                                                   "+
					"                substr(eoel.payplatformorderid,                                               "+
					"                       1,                                                                     "+
					"                       instr(eoel.payplatformorderid, '|', 1, 1) - 1) || '|' ||               "+
					"                eo.paymoney * 1000 || '|' || epl.pay_method as info                           "+
					"  FROM es_order eo                                                                            "+
					"  LEFT JOIN es_order_zhwq_adsl eozd                                                           "+
					"    ON eo.order_id = eozd.order_id                                                            "+
					"  LEFT JOIN es_order_ext eoe                                                                  "+
					"    ON eo.order_id = eoe.order_id                                                             "+
					"  left join es_order_items_ext eoie                                                           "+
					"    on eoie.order_id = eo.order_id                                                            "+
					"  LEFT JOIN es_order_extvtl eoel                                                              "+
					"    ON eo.order_id = eoel.order_id                                                            "+
					"  LEFT JOIN es_dc_public_dict_relation eddr                                                   "+
					"    ON eoe.order_city_code = eddr.field_value                                                 "+
					"  left join es_goods eg                                                                       "+
					"    on eg.goods_id = eo.goods_id                                                              "+
					"  LEFT JOIN es_payment_logs epl                                                               "+
					"    ON epl.order_id = eo.order_id                                                             "+
					" where ((trunc(eo.create_time, 'dd') >= trunc(sysdate - 1, 'dd') and                          "+
					"       trunc(eo.create_time, 'dd') < trunc(sysdate, 'dd')) or                                 "+
					"       (trunc(eoe.refund_time, 'dd') >= trunc(sysdate - 1, 'dd') and                          "+
					"       trunc(eoe.refund_time, 'dd') < trunc(sysdate, 'dd')))                                  "+
					"   AND eddr.stype = 'bss_area_code'                                                           "+
					"   and eo.pay_status = '1'                                                                    "+
					"   AND epl.pay_method IN ('51', '52')                                                         "+
					"   AND eoel.refundplatformorderid IS NOT NULL                                                 "+
					"   AND eo.paymoney > 0                                                                        ";

	private String getCfgSql(String cfg_pre_fix) throws Exception{
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		StringBuilder builder = new StringBuilder();
		
		for(int i=1;i<=10;i++){
			String key = cfg_pre_fix+i;
			
			String str = cacheUtil.getConfigInfo(key);
			
			if(org.apache.commons.lang.StringUtils.isBlank(str))
				continue;
			else
				builder.append(str);
		}
		
		return builder.toString();
	}
	
	public void upload() {
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "upload")) {
  			return;
		}
		PrintWriter writer = null;

		logger.info("------------------------begin---------------------------");

		String time = "";
		String url = "";
		//Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		//Matcher m = null;
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String num = cacheUtil.getConfigInfo("ORDER_DETAILS_NUM");
		
		//获取稽核文件上传配置
		String url1 = cacheUtil.getConfigInfo("ORDERPAY_PATH");
		String idAndPass = cacheUtil.getConfigInfo("AUDIT_ID_PASSWORD");
		String ip = cacheUtil.getConfigInfo("AUDIT_IP");
		String port = cacheUtil.getConfigInfo("AUDIT_PORT");
		String local_url = cacheUtil.getConfigInfo("ORDER_AUDIT_LOCAL");
//		String local_url = "D://";
		try{
			SimpleDateFormat new_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String[] ids = idAndPass.split("/");
			String userName  = "";
			String passWord = "";
			if(ids != null && ids.length >= 2) {
				userName = ids[0];
				passWord = ids[1];
			}
			
			logger.info("---------------------------------数据查询开始时间："+new_time.format(new java.util.Date())+"--------------------------------");
			//稽核SQL配置化
			String sql = this.getCfgSql("ORDER_PAY_SQL_");
			
			//未找到配置，取静态变量的SQL
			if(StringUtils.isBlank(sql))
				sql = QUERY_SQL;
			
			logger.error("OrderPayInfoTimer query sql:"+sql);
			
			List queueList = support.queryForList(sql);
			
			logger.info("---------------------------------数据查询结束时间："+new_time.format(new java.util.Date())+"--------------------------------");
			Map<String,String> login_map = new HashMap<String,String>();
			login_map.put("ip", ip);
			login_map.put("port", port);
			login_map.put("userName", userName);
			login_map.put("passWord", passWord);
			FTPUtil.setArg(login_map);
			FTPUtil.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件 
			/*Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyyMMdd");*/
			
			time=DateUtil.getTime3();
			url = url1+time;
			File f1 = new File(local_url+"/new"+time); 
			if(f1.exists()) delFolder(local_url+"/new"+time); f1.delete();
			f1.mkdirs();

			//FTPUtil.closeConnect();
			//boolean is_del = FTPUtil.isDirExist(url);
			//FTPUtil.closeConnect();
			//FTPUtil.makeDirectoryToPath(time,url1,is_del);
			//FTPUtil.closeConnect();
			

//			List new_list = new ArrayList();
//			
//			for(int y=0;y<40;y++){
//				//Map map = (Map)queueList.get(0);
//				Map map = new HashMap();
//				map.put("info", "ZBWO170681613030000103|20170417002|2017/04/1716:13:01|2017/04/1703:16:02||||18557500035|X|01|0|0|90063345|任全康|15658119769|杭州市滨江区滨安路1336号\n杭州市滨江区滨安路1336号\n杭州市滨江区滨安路1336号");
//				new_list.add(map);
//			}
//			queueList = new_list;
			int j = 0;
			String str ="";
			logger.info("-----------------------生成REQfor循环开始时间："+new_time.format(new java.util.Date())+"--------------------------");
			if(queueList == null || queueList.size() == 0 ) {
				File f = new File(local_url+"/new"+time+"/Order_Pay_"+time+"_"+"0001"+".REQ"); 
				if(f.exists()) f.delete();
				//Order_Pay_yyyymmdd_xxxx.REQ；
				writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(local_url+"/new"+time+"/Order_Pay_"+time+"_"+"0001"+".REQ"), "GBK"));
				//FTPUtil.uploadFile("".getBytes("GBK"), url1, "Order_Audit_"+time+"_"+"0001"+".REQ");
			}
			int otherNum = queueList.size();
			for(int i=1;i<=queueList.size();i++){
				if(i%Integer.parseInt(num)==1){
					j++;
					//查出的数据量大于num,分多个文件,文件头
					if(otherNum >= Integer.parseInt(num) ) {
						str = String.format("%010d", Integer.parseInt(num))+"\n";
						otherNum = otherNum-Integer.parseInt(num);
					}else {
						str = String.format("%010d", otherNum)+"\n";
					}
					logger.info("----------------------第"+j+"个文件内容开始时间:"+new_time.format(new java.util.Date())+"--------------------------");
//					File f = new File("D:\\Order_Pay_"+time+"_"+String.format("%04d", j)+".REQ","GBK"); 
//					if(f.exists()) f.delete();
//					writer = new PrintWriter("D:\\Order_Pay_"+time+"_"+String.format("%04d", j)+".REQ","GBK");
					File f = new File(local_url+"/new"+time+"/Order_Pay_"+time+"_"+String.format("%04d", j)+".REQ"); 
					if(f.exists()) f.delete();
					writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(local_url+"/new"+time+"/Order_Pay_"+time+"_"+String.format("%04d", j)+".REQ"), "GBK"));
					
				}
				String new_str = Const.getStrValue((Map)queueList.get(i-1), "info");
				/*m = p.matcher(new_str);
				new_str = m.replaceAll("");*/
				str += new_str+"\n";
				//writer.print(Const.getStrValue((Map)queueList.get(i-1), "info")+"\n");
				if(i%Integer.parseInt(num)==0||i==queueList.size()){
					logger.info("----------------------第"+j+"个文件内容结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
					logger.info("----------------------第"+j+"个文件生成开始时间:"+new_time.format(new java.util.Date())+"--------------------------");
					FTPUtil.uploadFile(str.getBytes("GBK"), url1, "Order_Pay_"+time+"_"+String.format("%04d", j)+".REQ");
					logger.info("----------------------第"+j+"个文件生成结束时间:"+new_time.format(new java.util.Date())+"--------------------------");
					//FTPUtil.closeConnect();
					writer.print(str);
					writer.close();
				}
			}
			logger.info("-----------------------生成REQfor循环结束时间："+new_time.format(new java.util.Date())+"--------------------------");  		
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			url = url1+"/"+time;
			//FTPUtil.closeConnect();
			boolean is_del = FTPUtil.isDirExist(url);
			FTPUtil.closeConnect();
			FTPUtil.makeDirectoryToPath(time,url1,is_del);
			//FTPUtil.closeConnect();
			FTPUtil.uploadManyFile(local_url+"/new"+time,url1);
			FTPUtil.closeConnect();// 关闭连接 
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
		OrderPayInfoTimer a = new OrderPayInfoTimer();
		a.upload();
	}              
}
