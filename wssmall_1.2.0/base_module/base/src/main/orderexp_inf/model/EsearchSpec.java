package model;

import java.io.Serializable;

/**
 * 规格定义实体
 * @author qin.yingxiong
 */
public class EsearchSpec implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//搜索规格编码
	private String search_code;
	//搜索规格id
	private String search_id;
	//搜索规格名称
	private String search_name;
	//接续处理器
	private String hander_class;
	//解析报文字段名
	private String search_field;
	//创建时间
	private String create_time;
	//开关标识（0：开 1：关）
	private String flag;
	
	private String col1; 
	private String col2;
	private String col3;
	private String col4;
	private String col5;
	
	public String getSearch_code() {
		return search_code;
	}
	public void setSearch_code(String search_code) {
		this.search_code = search_code;
	}
	public String getSearch_id() {
		return search_id;
	}
	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}
	public String getSearch_name() {
		return search_name;
	}
	public void setSearch_name(String search_name) {
		this.search_name = search_name;
	}
	public String getHander_class() {
		return hander_class;
	}
	public void setHander_class(String hander_class) {
		this.hander_class = hander_class;
	}
	public String getSearch_field() {
		return search_field;
	}
	public void setSearch_field(String search_field) {
		this.search_field = search_field;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		this.col4 = col4;
	}
	public String getCol5() {
		return col5;
	}
	public void setCol5(String col5) {
		this.col5 = col5;
	}
}
