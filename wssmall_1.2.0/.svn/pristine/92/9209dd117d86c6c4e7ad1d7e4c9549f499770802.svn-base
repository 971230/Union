package zte.net.ecsord.params.sf.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Option implements Serializable {

	private static final long serialVersionUID = 5373505755768154842L;

	@ZteSoftCommentAnnotationParam(name="商品名称",type="String",isNecessary="Y",desc="name:商品名称")
	private String name;

	@ZteSoftCommentAnnotationParam(name="商品数量",type="String",isNecessary="N",desc="count:商品数量")
	private Integer count;

	@ZteSoftCommentAnnotationParam(name="单位",type="String",isNecessary="N",desc="unit:单位")
	private String unit;

	@ZteSoftCommentAnnotationParam(name="重量",type="String",isNecessary="N",desc="weight:重量")
	private Double weight;

	@ZteSoftCommentAnnotationParam(name="货品单价",type="String",isNecessary="N",desc="amount:货品单价")
	private Double amount;	

	@ZteSoftCommentAnnotationParam(name="币别",type="String",isNecessary="N",desc="currency:币别")
	private String currency;	

	@ZteSoftCommentAnnotationParam(name="原产地国别",type="String",isNecessary="N",desc="source_area:原产地国别。")
	private String source_area;	

	@ZteSoftCommentAnnotationParam(name="增值服务",type="String",isNecessary="Y",desc="AddedService:增值服务")
	private List<OrderOptionAddedService> AddedService;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSource_area() {
		return source_area;
	}

	public void setSource_area(String source_area) {
		this.source_area = source_area;
	}

	public List<OrderOptionAddedService> getAddedService() {
		return AddedService;
	}

	public void setAddedService(List<OrderOptionAddedService> addedService) {
		AddedService = addedService;
	}
	
}
