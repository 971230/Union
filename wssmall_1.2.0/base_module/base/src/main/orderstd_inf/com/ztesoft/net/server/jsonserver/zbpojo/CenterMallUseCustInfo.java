/**
 * 
 */
package com.ztesoft.net.server.jsonserver.zbpojo;

/**
 * @author ZX
 * @version 2015-12-29
 * @see 责任人使用人信息(集客开户传)
 * 
 */
public class CenterMallUseCustInfo {
	
	private String usecustname; // 使用人责任人姓名
	private String usecustpspttype; // 使用人或责任人客户证件类型
	private String usecustpsptcode; // 使用人或责任人客户证件号码
	private String usecustpsptaddress; // 使用人或责任人证件地址
	private String itmprdgrouptype; // B2B、B2C类集客产品标识(责任人时传'B2B'、'B2C'、'B2B2C'等)
	private String itmprdrespobsible; // 责任人标识(责任人时传1)
	
	public String getUsecustname() {
		return usecustname;
	}
	public void setUsecustname(String usecustname) {
		this.usecustname = usecustname;
	}
	public String getUsecustpspttype() {
		return usecustpspttype;
	}
	public void setUsecustpspttype(String usecustpspttype) {
		this.usecustpspttype = usecustpspttype;
	}
	public String getUsecustpsptcode() {
		return usecustpsptcode;
	}
	public void setUsecustpsptcode(String usecustpsptcode) {
		this.usecustpsptcode = usecustpsptcode;
	}
	public String getUsecustpsptaddress() {
		return usecustpsptaddress;
	}
	public void setUsecustpsptaddress(String usecustpsptaddress) {
		this.usecustpsptaddress = usecustpsptaddress;
	}
	public String getItmprdgrouptype() {
		return itmprdgrouptype;
	}
	public void setItmprdgrouptype(String itmprdgrouptype) {
		this.itmprdgrouptype = itmprdgrouptype;
	}
	public String getItmprdrespobsible() {
		return itmprdrespobsible;
	}
	public void setItmprdrespobsible(String itmprdrespobsible) {
		this.itmprdrespobsible = itmprdrespobsible;
	}
	
}
