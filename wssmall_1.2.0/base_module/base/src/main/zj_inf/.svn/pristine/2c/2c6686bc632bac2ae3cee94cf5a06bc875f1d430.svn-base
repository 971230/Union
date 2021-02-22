package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Goods implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "商品编码", type = "String", isNecessary = "Y", desc = "GoodsCode：商品编码")
	private String GoodsCode;

	@ZteSoftCommentAnnotationParam(name = "商品名称", type = "String", isNecessary = "Y", desc = "GoodsName：商品名称")
	private String GoodsName;
	
	@ZteSoftCommentAnnotationParam(name = "商品应收", type = "int", isNecessary = "Y", desc = "GoodsOrigFee：商品应收，单位为厘")
	private int GoodsOrigFee;
	
	@ZteSoftCommentAnnotationParam(name = "商品实收", type = "int", isNecessary = "Y", desc = "GoodsRealFee：商品名称，单位为厘")
	private int GoodsRealFee;
	
	@ZteSoftCommentAnnotationParam(name = "商品减免原因", type = "String", isNecessary = "Y", desc = "GoodsReliefRes：商品减免原因")
	private String GoodsReliefRes;
	
	@ZteSoftCommentAnnotationParam(name = "商品类型", type = "String", isNecessary = "Y", desc = "GoodsType：商品类型")
	private String GoodsType;

	@ZteSoftCommentAnnotationParam(name = "商品附属信息", type = "List<GoodsAtt>", isNecessary = "Y", desc = "GoodsAtt：商品附属信息")
	private List<GoodsAtt> GoodsAttInfo;

	@ZteSoftCommentAnnotationParam(name = "费用明细", type = "String", isNecessary = "Y", desc = "FeeInfo：费用明细")
	private List<Fee> FeeInfo;

	public String getGoodsCode() {
		return GoodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		GoodsCode = goodsCode;
	}

	public String getGoodsName() {
		return GoodsName;

	}

	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}

	public List<GoodsAtt> getGoodsAttInfo() {
		return GoodsAttInfo;
	}

	public void setGoodsAttInfo(List<GoodsAtt> goodsAttInfo) {
		GoodsAttInfo = goodsAttInfo;
	}

	public List<Fee> getFeeInfo() {
		return FeeInfo;
	}

	public void setFeeInfo(List<Fee> feeInfo) {
		FeeInfo = feeInfo;
	}

	public int getGoodsOrigFee() {
		return GoodsOrigFee;
	}

	public void setGoodsOrigFee(int goodsOrigFee) {
		GoodsOrigFee = goodsOrigFee;
	}

	public int getGoodsRealFee() {
		return GoodsRealFee;
	}

	public void setGoodsRealFee(int goodsRealFee) {
		GoodsRealFee = goodsRealFee;
	}

	public String getGoodsReliefRes() {
		return GoodsReliefRes;
	}

	public void setGoodsReliefRes(String goodsReliefRes) {
		GoodsReliefRes = goodsReliefRes;
	}

	public String getGoodsType() {
		return GoodsType;
	}

	public void setGoodsType(String goodsType) {
		GoodsType = goodsType;
	}

}
