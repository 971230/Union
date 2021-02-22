package zte.params.template.resp;


public class AttrDefTableVO {

	/**
	 * 
	 */
	private String attr_def_id;
	/**
	 * 
	 */
	private String table_name;
	/**
	 * 
	 */
	private String field_name;
	/**
	 * 
	 */
	private String source_from;
	/**
	 * 
	 */
	private String def_field_name;
	/**
	 * 
	 */
	private String rel_table_name;
	
	private String handler_id;
	/**
	 * 
	 */
	private String handler_class;
	

	public String getAttr_def_id() {
		return attr_def_id;
	}

	public void setAttr_def_id(String attr_def_id) {
		this.attr_def_id = attr_def_id;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}


	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getDef_field_name() {
		return def_field_name;
	}

	public void setDef_field_name(String def_field_name) {
		this.def_field_name = def_field_name;
	}

	public String getRel_table_name() {
		return rel_table_name;
	}

	public void setRel_table_name(String rel_table_name) {
		this.rel_table_name = rel_table_name;
	}


	public String getHandler_id() {
		return handler_id;
	}

	public void setHandler_id(String handler_id) {
		this.handler_id = handler_id;
	}

	public String getHandler_class() {
		return handler_class;
	}

	public void setHandler_class(String handler_class) {
		this.handler_class = handler_class;
	}

}
