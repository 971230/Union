package com.ztesoft.net.server.jsonserver.mobilebusipojo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * seedUser_info节点（种子用户信息节点）
 * 
 * @author yu.tianqi 2019年3月26日
 *
 */
public class MobileBusiOrderSeedUserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "market_user_id", type = "String", isNecessary = "N", desc = "自传播营销id")
	private String market_user_id;

	@ZteSoftCommentAnnotationParam(name = "seed_user_id", type = "String", isNecessary = "N", desc = "种子用户id")
	private String seed_user_id;

	@ZteSoftCommentAnnotationParam(name = "share_svc_num", type = "String", isNecessary = "N", desc = "种子用户号码")
	private String share_svc_num;

	@ZteSoftCommentAnnotationParam(name = "top_share_userid", type = "String", isNecessary = "N", desc = "顶级种子用户id")
	private String top_share_userid;//同步沃创富时对应的引流受益人ID

	@ZteSoftCommentAnnotationParam(name = "top_share_num", type = "String", isNecessary = "N", desc = "顶级种子用户号码")
	private String top_share_num;// 同步沃创富时对应的引流受益人号码

	@ZteSoftCommentAnnotationParam(name = "activity_name", type = "String", isNecessary = "N", desc = "自传播活动名称")
	private String activity_name;
	
	@ZteSoftCommentAnnotationParam(name = "top_seed_professional_line", type = "String", isNecessary = "N", desc = "顶级种子专业线,0（公众），1（集客），2（客服）")
	private String top_seed_professional_line;//0（公众），1（集客），2（客服）
	
	@ZteSoftCommentAnnotationParam(name = "top_seed_type", type = "String", isNecessary = "N", desc = "顶级种子类型,0（豌豆荚）,1（满天星）")
	private String top_seed_type;//0（豌豆荚）,1（满天星）
	
	@ZteSoftCommentAnnotationParam(name = "top_seed_group_id", type = "String", isNecessary = "N", desc = "顶级种子分组,GS(国寿),KFLH_CHONGQING(客服来话-重庆),KFLH_GUANGZHOU(客服来话-广州),KFLH_HANGZHOU(客服来话-杭州),KFLH_JINHUA(客服来话-金华),KFQH(客服去话),SQ(集团商企),ZQZQ(政企专区)")
	private String top_seed_group_id;//GS(国寿),KFLH_CHONGQING(客服来话-重庆),KFLH_GUANGZHOU(客服来话-广州),KFLH_HANGZHOU(客服来话-杭州),KFLH_JINHUA(客服来话-金华),KFQH(客服去话),SQ(集团商企),ZQZQ(政企专区)

	public String getTop_seed_group_id() {
		return top_seed_group_id;
	}
	
	public void setTop_seed_group_id(String top_seed_group_id) {
		this.top_seed_group_id = top_seed_group_id;
	}
	
	public String getMarket_user_id() {
		return market_user_id;
	}

	public void setMarket_user_id(String market_user_id) {
		this.market_user_id = market_user_id;
	}

	public String getSeed_user_id() {
		return seed_user_id;
	}

	public void setSeed_user_id(String seed_user_id) {
		this.seed_user_id = seed_user_id;
	}

	public String getShare_svc_num() {
		return share_svc_num;
	}

	public void setShare_svc_num(String share_svc_num) {
		this.share_svc_num = share_svc_num;
	}

	public String getTop_share_userid() {
		return top_share_userid;
	}

	public void setTop_share_userid(String top_share_userid) {
		this.top_share_userid = top_share_userid;
	}

	public String getTop_share_num() {
		return top_share_num;
	}

	public void setTop_share_num(String top_share_num) {
		this.top_share_num = top_share_num;
	}

	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	
	public String getTop_seed_professional_line() {
		return top_seed_professional_line;
	}

	public void setTop_seed_professional_line(String top_seed_professional_line) {
		this.top_seed_professional_line = top_seed_professional_line;
	}
	
	public String getTop_seed_type() {
		return top_seed_type;
	}

	public void setTop_seed_type(String top_seed_type) {
		this.top_seed_type = top_seed_type;
	}

}
