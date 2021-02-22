package com.ztesoft.net.ecsord.params.ecaop.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.GoodsInfoVO;

import params.ZteResponse;

public class GoodsListQueryResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="1失败、0成功")
	private String resp_code;
	 
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="失败原因")
	private String resp_msg;
	
	@ZteSoftCommentAnnotationParam(name="返回报文",type="String",isNecessary="Y",desc="返回结果组")
	private List<GoodsInfoVO> goods_info;

	public String getResp_code() {
		return resp_code;
	}

	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}

	public String getResp_msg() {
		return resp_msg;
	}

	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}

	public List<GoodsInfoVO> getGoods_info() {
		return goods_info;
	}

	public void setGoods_info(List<GoodsInfoVO> goods_info) {
		this.goods_info = goods_info;
	}


	
	
}
