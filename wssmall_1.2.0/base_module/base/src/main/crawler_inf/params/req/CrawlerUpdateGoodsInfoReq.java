package params.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;

public class CrawlerUpdateGoodsInfoReq extends ZteRequest{
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.updateGoodsInfo";
	}
	
	private String custName;//客户姓名

	private String firstMonthFee;//首月资费编码A000011V000001：全月，A000011V000002：半月，A000011V000003：套餐包外
	
	private String firstMonthFeeName;//首月资名称，如：全月
	
	private String orderNo;//总商订单ID
	
	private String psptType;//证件类型 01:15位身份证，02:18位身份证
	
	private String psptNo;//证件号码
	
	private String certAddr;//证件地址
	
	private String preNum;//订购号码  号卡类订单必传

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getFirstMonthFee() {
		return firstMonthFee;
	}

	public void setFirstMonthFee(String firstMonthFee) {
		this.firstMonthFee = firstMonthFee;
	}

	public String getFirstMonthFeeName() {
		return firstMonthFeeName;
	}

	public void setFirstMonthFeeName(String firstMonthFeeName) {
		this.firstMonthFeeName = firstMonthFeeName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPsptType() {
		return psptType;
	}

	public void setPsptType(String psptType) {
		this.psptType = psptType;
	}

	public String getPsptNo() {
		return psptNo;
	}

	public void setPsptNo(String psptNo) {
		this.psptNo = psptNo;
	}

	public String getPreNum() {
		return preNum;
	}

	public void setPreNum(String preNum) {
		this.preNum = preNum;
	}

	public String getCertAddr() {
		return certAddr;
	}

	public void setCertAddr(String certAddr) {
		this.certAddr = certAddr;
	}
	
	
}
