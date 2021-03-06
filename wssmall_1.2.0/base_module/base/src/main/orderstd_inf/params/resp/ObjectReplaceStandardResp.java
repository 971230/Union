package params.resp;

import java.util.List;

import params.orderqueue.resp.OrderCollectionResp;

import com.ztesoft.net.model.Outer;

public class ObjectReplaceStandardResp extends OrderCollectionResp {
	 private String validate_type;//校验系统返回的校验类型
     private String validate_msg;//校验消息
     private List<Outer> out_order_List;
		public String getValidate_type() {
			return validate_type;
		}
		public void setValidate_type(String validate_type) {
			this.validate_type = validate_type;
		}
		public String getValidate_msg() {
			return validate_msg;
		}
		public void setValidate_msg(String validate_msg) {
			this.validate_msg = validate_msg;
		}
	    public List<Outer> getOut_order_List() {
			return out_order_List;
		}
		public void setOut_order_List(List<Outer> out_order_List) {
			this.out_order_List = out_order_List;
		}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ObjectReplaceStandardResp [validate_type=").append(validate_type).append(", validate_msg=")
				.append(validate_msg).append(", error_code=").append(error_code).append(", error_msg=")
				.append(error_msg).append("]");
		return builder.toString();
	}

	
	
	

}
