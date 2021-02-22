package params.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.resp.CrawlerFeeInfo;

@SuppressWarnings("rawtypes")
public class CrawlerFreeDesc extends ZteRequest{ 
    /**
	 * 
	 */
	private static final long serialVersionUID = -8975198455613365469L;
	/**
	 * 
	 */
	//======爬虫获取总商的费用信息===========
    private String otherFee;//多缴预存应收(注：为空不展示,不为空可以编辑)
    private String usimFee;//应收卡费(注：为空不展示)
    private String totleFee;// 费用实收合计otherFee+usimFee+  list :realFee
    private List<CrawlerFeeInfo> crawlerFeeInfo;//费用明细(注：为空不展示)  非99的
    

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
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
