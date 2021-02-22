package com.ztesoft.net.framework.graphics2d;

public interface IMatrixImage {

	/**
	 * 生成二维码
	 * @param content 要转化成二维码的内容
	 * @return 二维码图片地址
	 */
	public String toBufferedImage(String content);

	/**
	 * 二维码解析
	 * @param path 二维码地址
	 * @return 二维码内容
	 */
	public String decodeImage(String path); 
}
