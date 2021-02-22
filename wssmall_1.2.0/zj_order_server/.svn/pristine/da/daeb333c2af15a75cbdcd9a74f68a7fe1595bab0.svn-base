package com.ztesoft.net.mall.core.timer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

public class CNPCOrderInfoTimer {
	private static Logger logger = Logger.getLogger(CNPCOrderInfoTimer.class);

	@SuppressWarnings("null")
	public void upload() {
		
		if (!CheckTimerServer
				.isMatchServer(this.getClass().getName(), "upload")) {
			return;
		}
		 
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String count = cacheUtil.getConfigInfo("CNPC_CONTROL");
		if (count.equals("0")) {
			return;
		}

		PrintWriter writer = null;
		logger.info("------------------------begin---------------------------");
		String time = "";
		File f = null;

		String sql = cacheUtil.getConfigInfo("CNPC_ORDER_INFO_SQL");
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		String num = cacheUtil.getConfigInfo("ORDER_DETAILS_NUM");
		String token = cacheUtil.getConfigInfo("CNPC_UPLOAD_TOKEN");
		String version = cacheUtil.getConfigInfo("CNPC_UPLOAD_VERSION");
		String method = cacheUtil.getConfigInfo("CNPC_UPLOAD_METHOD");

		// 获取文件上传配置
		String ability_url = cacheUtil.getConfigInfo("ABILITY_PATH_TEST");
		// 生产上传文件路径
		String local_url = cacheUtil.getConfigInfo("ORDER_CNPC_LOCAL");
		// 本机测试上传路径
		//String local_url = "D://CNPC_ORDER_INFO//";
		try {
			SimpleDateFormat new_time = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			logger.info("---------------------------------数据查询开始时间："
					+ new_time.format(new java.util.Date())
					+ "--------------------------------");
			List queueList = support.queryForListNoSourceFrom(sql);
			logger.info("---------------------------------数据查询结束时间："
					+ new_time.format(new java.util.Date())
					+ "--------------------------------");
			time = DateUtil.getTime3();
			File f1 = new File(local_url + "/" + time);
			if (f1.exists())
				delFolder(local_url + "/" + time);
			f1.delete();
			f1.mkdirs();
			logger.info("CNPC本地上传路径：" + f1.getPath());
			int j = 0;
			String str = "";
			logger.info("-----------------------生.TXTfor循环开始时间："
					+ new_time.format(new java.util.Date())
					+ "--------------------------");

			if (!queueList.isEmpty() || queueList.size() > 0) {
				for (int i = 1; i <= queueList.size(); i++) {
					if (i % Integer.parseInt(num) == 1) {
						j++;
						str = "";
						logger.info("----------------------第" + j
								+ "个文件内容开始时间:"
								+ new_time.format(new java.util.Date())
								+ "--------------------------");
						f = new File(local_url + "/" + time
								+ "/WSSMALL_CNPC_ORDER_INFO_" + time
								+ String.format("%02d", j) + ".TXT");
						if (f.exists())
							f.delete();
						writer = new PrintWriter(new OutputStreamWriter(
								new FileOutputStream(local_url + "/" + time
										+ "/WSSMALL_CNPC_ORDER_INFO_" + time
										+ String.format("%02d", j) + ".TXT"),
								"GBK"));
					}
					String order_id = Const.getStrValue(
							(Map) queueList.get(i - 1), "order_id");
					String phone_num = Const.getStrValue(
							(Map) queueList.get(i - 1), "phone_num");
					String order_type = Const.getStrValue(
							(Map) queueList.get(i - 1), "order_type");
					String status = Const.getStrValue(
							(Map) queueList.get(i - 1), "status");
					str += order_id + "|" + phone_num + "|" + order_type + "|"
							+ status + "\r\n";
					System.out.println("CNPC:" + str);
					if (i % Integer.parseInt(num) == 0 || i == queueList.size()) {
						writer.print(str);
						writer.close();
					}

				}
			} else {
				System.out.println("未查询到所要上传的数据");
				f = new File(local_url + "/" + time
						+ "/WSSMALL_CNPC_ORDER_INFO_" + time + "_" + "01"
						+ ".TXT");
				if (f.exists())
					f.delete();
				writer = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream(local_url + "/" + time
								+ "/WSSMALL_CNPC_ORDER_INFO_" + time + "_"
								+ "01" + ".TXT"), "GBK"));
			}

			logger.info("-----------------------生.TXTfor循环结束时间："
					+ new_time.format(new java.util.Date())
					+ "--------------------------");
			System.out.println("CNPC本地上传结束");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Map<String, File> fileFields = new HashMap<String, File>();
			fileFields.put("WSSMALL_CNPC_ORDER_INFO_" + time + ".TXT", f);

			String uploadurl = StringUtils.trim(ability_url);
			JSONObject json = new JSONObject();

			json.put("access_token", token);
			json.put("method", method);
			json.put("version", version);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			// 避免中文文件名乱码
			builder.setCharset(Charset.forName("UTF-8"));
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			if (json != null && !json.isEmpty()) {
				// 文本域属性
				Iterator<Entry<String, Object>> itr = json.entrySet()
						.iterator();
				while (itr.hasNext()) {
					Entry<String, Object> entry = itr.next();
					String key = entry.getKey();

					if (StringUtils.isBlank(key)) {
						continue;
					}

					String value = "";
					if (entry.getValue() != null) {
						if (entry.getValue() instanceof String) {
							value = (String) entry.getValue();
						} else {
							value = JSON.toJSONString(entry.getValue());
						}
					}

					builder.addPart(
							key,
							new StringBody(value, ContentType.create(
									"text/plain", "UTF-8")));
				}
			}

			if(fileFields.size()>0 && !fileFields.isEmpty()){
				// 文件域属性
				Iterator<Entry<String, File>> itr = fileFields.entrySet()
						.iterator();
				while (itr.hasNext()) {
					Entry<String, File> entry = itr.next();
					String key = entry.getKey();
					File tempfile = entry.getValue();

					if (StringUtils.isBlank(key)) {
						continue;
					}

					if (tempfile != null && tempfile.exists()) {
						builder.addPart(key, new FileBody(tempfile));
					}
				}
			}

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpEntity httpEntity = builder.build();
			HttpPost post = new HttpPost(uploadurl);
			post.setEntity(httpEntity);
			try {
				HttpResponse response = httpClient.execute(post);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("------------------------end---------------------------");
	}

	// 删除文件夹

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
}
