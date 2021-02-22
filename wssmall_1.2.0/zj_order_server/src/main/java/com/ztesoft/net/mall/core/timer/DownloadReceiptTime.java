package com.ztesoft.net.mall.core.timer;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.util.FTPUtil;

import sun.net.ftp.FtpClient;

public class DownloadReceiptTime {
	private static Logger logger = Logger.getLogger(DownloadReceiptTime.class);

//	public static void main(String[] args) {
//		DownloadReceiptTime downloadReceiptTime = new DownloadReceiptTime();
//		downloadReceiptTime.downFile();
//	}

	/**
	 * 从文件服务器读取文件
	 * 
	 * @param filename
	 */
	public void downFile() {
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "downFile")) {
  			return;
		}
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		FTPUtil ftpUtil = new FTPUtil();
		String ip = cacheUtil.getConfigInfo("zqydd_ip");
		String port = cacheUtil.getConfigInfo("zqydd_port");
		String username = cacheUtil.getConfigInfo("zqydd_username");
		String password = cacheUtil.getConfigInfo("zqydd_password");
		String url = cacheUtil.getConfigInfo("zqydd_url");
		String sql = cacheUtil.getConfigInfo("zqydd_sql");
		
		
//		String ip = "130.36.21.71";
//		String port = "21";
//		String username = "aibss";
//		String password = "aibss123";
//		String url = "/aibss/bin/files/one_button_regul/";
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHH");
		Date date = new Date();
		String time = String.valueOf(sdf.format(date));
		String file = url + "FORMAL_ACCEPT_SYN_" + time + ".txt";
		int porter=Integer.parseInt(port);
		FtpClient ftpClient = FTPUtil.connectFTP(ip, porter, username, password);
		List<String> list = null;
		if (ftpClient.isLoggedIn()) {
			list = FTPUtil.download(file, ftpClient);
		} 
		
		for (int i = 0; i < list.size(); i++) {
			String strOut = null;
			if (list.get(i).toString() != null) {
				try {
					byte[] bs = list.get(i).toString().getBytes();

					strOut = new String(bs, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logger.info("政企预订单文件内容----"+strOut);
			String[] orderId=StringUtils.splitByWholeSeparatorPreserveAllTokens(strOut,"|");
			System.out.println(orderId[1]);
			sql = sql.replace("{order_id}", orderId[1]);
			System.out.println("sql----------"+sql);
			baseDaoSupport.execute(sql, null);
		}
		

	}

}
