package com.ztesoft.parameters.accept.bean;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Reason.Yea 
 * @version 创建时间：May 20, 2013
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ordItemBean")
public class OrdItemBean implements Serializable{
	@NotNull
	@XmlElement
	private String plan_id;

	@XmlElement
	private String plan_num;
	
	@XmlElement
	private String item_type;
	
	@XmlElement
	private String item_fee;
	
	@XmlElement
	private String item_desc;
	
	@XmlElement
	private String ord_id;

    @XmlElement
    private String rel_ord_code;

    public String getRel_ord_code() {
        return rel_ord_code;
    }

    public void setRel_ord_code(String rel_ord_code) {
        this.rel_ord_code = rel_ord_code;
    }

    public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getPlan_num() {
		return plan_num;
	}

	public void setPlan_num(String plan_num) {
		this.plan_num = plan_num;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getItem_fee() {
		return item_fee;
	}

	public void setItem_fee(String item_fee) {
		this.item_fee = item_fee;
	}

	public String getItem_desc() {
		return item_desc;
	}

	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
	}

	public String getOrd_id() {
		return ord_id;
	}

	public void setOrd_id(String ord_id) {
		this.ord_id = ord_id;
	}
	
	
}
