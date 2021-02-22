package params.resp;

import params.orderqueue.resp.OrderCollectionResp;

public class TemplatesOrderStandardResp extends OrderCollectionResp {
	 private String validate_type;//校验系统返回的校验类型
     private String validate_msg;//校验消息
		public String getValidate_type() {
			return validate_type;
		}
		public void setValidate_type(String validate_type) {
			this.validate_type = validate_type;
		}
		public String getValidate_msg() {
			return validate_msg;
		}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CenterMallOrderStandardResp [validate_type=").append(validate_type).append(", validate_msg=")
				.append(validate_msg).append(", error_code=").append(error_code).append(", error_msg=")
				.append(error_msg).append("]");
		return builder.toString();
	}

	
	
	

}
