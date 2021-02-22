package zte.net.ecsord.params.sf.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderOptionAddedService implements Serializable {

	private static final long serialVersionUID = 5342771420079882561L;

	@ZteSoftCommentAnnotationParam(name="增值服务1",type="String",isNecessary="Y",desc="name：增值服务1")
	private String name="增值服务1";
	
	@ZteSoftCommentAnnotationParam(name="值1",type="String",isNecessary="Y",desc="value:值1")
	private String value="值1"; 
	
	@ZteSoftCommentAnnotationParam(name="值2",type="String",isNecessary="Y",desc="value1:值2")
	private String value1="值2";
	
	@ZteSoftCommentAnnotationParam(name="值3",type="String",isNecessary="Y",desc="value2:值3")
	private String value2="值3";
	
	@ZteSoftCommentAnnotationParam(name="值4",type="String",isNecessary="Y",desc="value3:值4")
	private String value3="值4";
	
	@ZteSoftCommentAnnotationParam(name="值5",type="String",isNecessary="Y",desc="value4:值5")
	private String value4="值5";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}
	
}
