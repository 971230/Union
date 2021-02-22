package params.resp;

import java.util.List;

import params.ZteResponse;

public class CrawlerResp extends ZteResponse{
	private String resp_code;//返回码 0：成功 1：失败 2：老用户直接提交下卡操作 3：是否把终端从原渠道调拨到自己所在渠道
    private String resp_msg;//返回消息
 	private String cookie;//登录成功之后返回cookie
    
    
    //======爬虫获取总商的费用信息===========
    private String otherFee;//多缴预存(注：为空不展示,不为空可以编辑)
    private String usimFee;//应收卡费(注：为空不展示)
    private String cardFeeDisable;//应收卡费是否可编辑 1:不可编辑，其他值则可以编辑应收卡费
    private String totleFee;//费用实收合计
    private List<CrawlerFeeInfo> crawlerFeeInfo;//费用明细(注：为空不展示)
    
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
	public String getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}
	public String getUsimFee() {
		return usimFee;
	}
	public void setUsimFee(String usimFee) {
		this.usimFee = usimFee;
	}
	public String getCardFeeDisable() {
		return cardFeeDisable;
	}
	public void setCardFeeDisable(String cardFeeDisable) {
		this.cardFeeDisable = cardFeeDisable;
	}
	public String getTotleFee() {
		return totleFee;
	}
	public void setTotleFee(String totleFee) {
		this.totleFee = totleFee;
	}
	public List<CrawlerFeeInfo> getCrawlerFeeInfo() {
		return crawlerFeeInfo;
	}
	public void setCrawlerFeeInfo(List<CrawlerFeeInfo> crawlerFeeInfo) {
		this.crawlerFeeInfo = crawlerFeeInfo;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
}
