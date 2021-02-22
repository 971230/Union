package params.resp;

import params.ZteResponse;
import zte.params.order.resp.OrderAddResp;

public class OrderStandingResp extends ZteResponse{
		 private String validate_type;//校验系统返回的校验类型
	     private String validate_msg;//校验消息
	     private OrderAddResp orderAddResp;
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
		public OrderAddResp getOrderAddResp() {
			return orderAddResp;
		}
		public void setOrderAddResp(OrderAddResp orderAddResp) {
			this.orderAddResp = orderAddResp;
		}
}
