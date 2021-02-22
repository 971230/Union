package zte.net.ecsord.common.element.jsoninst;



/**
 * 
 * @author wu.i
 * 构造实体元素
 *
 */
public class AttrIsNullVo  implements java.io.Serializable{
	private String trace_id;
	private String is_null; //Y N
	public String getTrace_id() {
		return trace_id;
	}
	public void setTrace_id(String trace_id) {
		this.trace_id = trace_id;
	}
	public String getIs_null() {
		return is_null;
	}
	public void setIs_null(String is_null) {
		this.is_null = is_null;
	}
	
}
