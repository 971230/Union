package com.ztesoft.net.framework.graphics2d;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.ztesoft.net.framework.blog.IStoreProcesser;
import com.ztesoft.net.framework.blog.StoreProcesser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

/**
 * 
 * 订单二维码生成和读取
 * 
 */
public class OrdersMatrixImage implements IMatrixImage {
	@Override
	public String toBufferedImage(String content) {
		String[] names = {};
		try {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String temp_path = cacheUtil
					.getConfigInfo(Consts.GOODS_IMAGE_TEMP_PATH);
			File file = new File(temp_path);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			String fileName = "graphics2d_" + System.currentTimeMillis()
					+ ".jpg";

			File tmpfile = File.createTempFile(
					"upload_"
							+ DateUtil.toString(
									new Date(System.currentTimeMillis()),
									"yyyyMMddhhmmss")
							+ StringUtil.getRandStr(4)
							+ fileName.substring(0, fileName.indexOf(".")),
					".tmp", file);

//			content = "120605181003;http://www.cnblogs.com/jtmjx";
			String path = "C:/Users/Administrator/Desktop";
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			// BitMatrix bitMatrix = multiFormatWriter.encode(content,
			// BarcodeFormat.QR_CODE, 400, 400,hints);
			BitMatrix bitMatrix = multiFormatWriter.encode(content,
					BarcodeFormat.QR_CODE, 400, 400);
//			File file1 = new File(path, "餐巾纸.jpg");
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", tmpfile);
			names = upload(tmpfile, fileName);
			tmpfile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return names[0];
	}

	@Override
	public String decodeImage(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 上传二维码图片<br>
	 * 生成二维码图片名称，并且在用户上下文的目录里生成图片<br>
	 * 返回以静态资源服务器地址开头+用户上下文路径的全路径<br>
	 * 在保存入数据库时应该将静态资源服务器地址替换为fs:开头，并去掉上下文路径,如:<br>
	 * http://static.enationsoft.com/user/1/1/attachment/graphics2d/1.jpg，存库应该为:
	 * fs:/attachment/goods/1.jpg
	 */
	private String[] upload(File file, String fileFileName) {
		String[] path = new String[2];
		if (file != null && fileFileName != null) {
			IStoreProcesser netBlog = StoreProcesser.getFileProcesser();
			path[0] = netBlog.upload(file, fileFileName, "goods");
			path[0] = netBlog.replaceUrl(path[0]);
		}
		return path;
	}

}
