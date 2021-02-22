package com.ztesoft.parameters.accept.bean;

import com.ztesoft.parameters.ListType;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;


/**
 * @author Reason.Yea 
 * @version 创建时间：May 20, 2013
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mainOrdBean")
public class MainOrdBean {
	
	private String acc_nbr;
	
	private String area_code;
	
	private String ord_desc;
	
	private String test_flag;

	@ListType(name="com.ztesoft.parameters.accept.bean.OrdItemBean")
	@XmlElementWrapper(name = "itemLists")
    @XmlElement(name = "itemList")
	private ArrayList items = new ArrayList();
	
	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	
	public String getOrd_desc() {
		return ord_desc;
	}

	public void setOrd_desc(String ord_desc) {
		this.ord_desc = ord_desc;
	}

	public ArrayList getItems() {
		return items;
	}

	public void setItems(ArrayList items) {
		this.items = items;
	}

	public String getTest_flag() {
		return test_flag;
	}

	public void setTest_flag(String test_flag) {
		this.test_flag = test_flag;
	}
	
}
