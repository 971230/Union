package com.ztesoft.net.framework.graphics2d;

public class MatrixImageProcesser {

	public static IMatrixImage getProcesser(String type){
		IMatrixImage matrixImg = null;
		if("ORDER".equals(type)){
			matrixImg = new OrdersMatrixImage();
		}else if("GOODS".equals(type)){
			matrixImg = new GoodsMatrixImage();
		}else if("PROXY".equals(type)){
			matrixImg = new ProxyMatrixImage();
		}
		return matrixImg;
	}
}
