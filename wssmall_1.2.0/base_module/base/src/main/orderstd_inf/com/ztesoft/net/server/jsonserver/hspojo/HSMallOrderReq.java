package com.ztesoft.net.server.jsonserver.hspojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HSMallOrderReq implements Serializable{
	
	private String VBELN = "";	//出库单号
	private String VBESO = "";	//B2C订单编号 
	private String MJAHR = "";	//年度
	private String WERKS = "";	//地点代码
	private String companyId = "";	//客户编码
	private String companyName = "";	//客户名称
	private String disvendorCode = "";	//分销编码
	private String Traficnum = "";	//物流单号
	private String AreaCode	= "";	//目的地代码
	private String province = "";	//收货省分
	private String city	= "";	//收货地市
	private String county = "";	//收货区县
	private String consignee = "";	//收货人姓名
	private String consigneePhone = "";	//收货人电话
	private String TELPhone = "";	//收货人手机
	private String shipToAddress = "";	//收货地址
	private String createDate = "";	//创建日期
	private String status = "";	//单据状态
	private String remark = "";	//备注
	private String RetailerID = "";	//售点编码
	private String RetailerName = "";	//售点名称
	private String CompanyId1 = "";	//客户编码（备用）
	private String CompanyName1 = "";	//客户名称（备用）
	private String Province1 = "";	//省分（备用）
	private String City1 = "";	//地市（备用）
	private String County1 = "";	//区县（备用）
	private String Consignee1 = "";	//联系人（备用）
	private String consigneePhone1 = "";	//联系电话（备用）
	private String shipToAddress1 = "";	//联系地址（备用）
	private String FORGO1 = "";	//预留字段
	private String FORGO2 = "";	//预留字段
	private String FORGO3 = "";	//预留字段
	private String FORGO4 = "";	//预留字段
	private String FORGO5 = "";	//预留字段
	private String FORGO6 = "";	//预留字段
	private String FORGO7 = "";	//预留字段
	private List<HSMallOrderReqItems> Items = new ArrayList<HSMallOrderReqItems>();	//出库指令传输行项目	
	private List<HSMallOrderInvoice> Invoice = new ArrayList<HSMallOrderInvoice>();	//发票信息
	private String provinceCode = "";
	private String cityCode = "";
	private String countyCode = "";
	
	private String source_from = "";
	private String order_type = "";

	public String getVBELN() {
		return VBELN;
	}

	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}

	public String getVBESO() {
		return VBESO;
	}

	public void setVBESO(String vBESO) {
		VBESO = vBESO;
	}

	public String getMJAHR() {
		return MJAHR;
	}

	public void setMJAHR(String mJAHR) {
		MJAHR = mJAHR;
	}

	public String getWERKS() {
		return WERKS;
	}

	public void setWERKS(String wERKS) {
		WERKS = wERKS;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDisvendorCode() {
		return disvendorCode;
	}

	public void setDisvendorCode(String disvendorCode) {
		this.disvendorCode = disvendorCode;
	}

	public String getTraficnum() {
		return Traficnum;
	}

	public void setTraficnum(String traficnum) {
		Traficnum = traficnum;
	}

	public String getAreaCode() {
		return AreaCode;
	}

	public void setAreaCode(String areaCode) {
		AreaCode = areaCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getTELPhone() {
		return TELPhone;
	}

	public void setTELPhone(String tELPhone) {
		TELPhone = tELPhone;
	}

	public String getShipToAddress() {
		return shipToAddress;
	}

	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRetailerID() {
		return RetailerID;
	}

	public void setRetailerID(String retailerID) {
		RetailerID = retailerID;
	}

	public String getRetailerName() {
		return RetailerName;
	}

	public void setRetailerName(String retailerName) {
		RetailerName = retailerName;
	}

	public String getCompanyId1() {
		return CompanyId1;
	}

	public void setCompanyId1(String companyId1) {
		CompanyId1 = companyId1;
	}

	public String getCompanyName1() {
		return CompanyName1;
	}

	public void setCompanyName1(String companyName1) {
		CompanyName1 = companyName1;
	}

	public String getProvince1() {
		return Province1;
	}

	public void setProvince1(String province1) {
		Province1 = province1;
	}

	public String getCity1() {
		return City1;
	}

	public void setCity1(String city1) {
		City1 = city1;
	}

	public String getCounty1() {
		return County1;
	}

	public void setCounty1(String county1) {
		County1 = county1;
	}

	public String getConsignee1() {
		return Consignee1;
	}

	public void setConsignee1(String consignee1) {
		Consignee1 = consignee1;
	}

	public String getConsigneePhone1() {
		return consigneePhone1;
	}

	public void setConsigneePhone1(String consigneePhone1) {
		this.consigneePhone1 = consigneePhone1;
	}

	public String getShipToAddress1() {
		return shipToAddress1;
	}

	public void setShipToAddress1(String shipToAddress1) {
		this.shipToAddress1 = shipToAddress1;
	}

	public String getFORGO1() {
		return FORGO1;
	}

	public void setFORGO1(String fORGO1) {
		FORGO1 = fORGO1;
	}

	public String getFORGO2() {
		return FORGO2;
	}

	public void setFORGO2(String fORGO2) {
		FORGO2 = fORGO2;
	}

	public String getFORGO3() {
		return FORGO3;
	}

	public void setFORGO3(String fORGO3) {
		FORGO3 = fORGO3;
	}

	public String getFORGO4() {
		return FORGO4;
	}

	public void setFORGO4(String fORGO4) {
		FORGO4 = fORGO4;
	}

	public String getFORGO5() {
		return FORGO5;
	}

	public void setFORGO5(String fORGO5) {
		FORGO5 = fORGO5;
	}

	public String getFORGO6() {
		return FORGO6;
	}

	public void setFORGO6(String fORGO6) {
		FORGO6 = fORGO6;
	}

	public String getFORGO7() {
		return FORGO7;
	}

	public void setFORGO7(String fORGO7) {
		FORGO7 = fORGO7;
	}

	public List<HSMallOrderInvoice> getInvoice() {
		return Invoice;
	}

	public void setInvoice(List<HSMallOrderInvoice> invoice) {
		Invoice = invoice;
	}
	
	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public List<HSMallOrderReqItems> getItems() {
		return Items;
	}

	public void setItems(List<HSMallOrderReqItems> items) {
		Items = items;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
	
}
