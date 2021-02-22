package params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import params.ZteRequest;

import java.util.List;

/**
 * 发货请求
 * @作者 MoChunrun
 * @创建日期 2013-11-12 
 * @版本 V 1.0
 */
public class ConfirmShippingReq extends ZteRequest {

	private String shippingType;//1发货 2退货
	private List<DeliveryItem> deliveryItems;
	private List<DeliveryItem> giftitemList;
	private String delivery_id;
	private String logi_id;
	private String logi_name;
	private String logi_no;
	private Delivery delivery;
	private String house_id;//仓库用，没有不填
	
	private String confirm_userid;
	private String confirm_username;
	
	//private String next_deal_user_id;
	//private String next_deal_group_id;
	
	public String getShippingType() {
		return shippingType;
	}
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}
	public List<DeliveryItem> getDeliveryItems() {
		return deliveryItems;
	}
	public void setDeliveryItems(List<DeliveryItem> deliveryItems) {
		this.deliveryItems = deliveryItems;
	}
	public String getDelivery_id() {
		return delivery_id;
	}
	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}
	public List<DeliveryItem> getGiftitemList() {
		return giftitemList;
	}
	public void setGiftitemList(List<DeliveryItem> giftitemList) {
		this.giftitemList = giftitemList;
	}
	public String getLogi_id() {
		return logi_id;
	}
	public void setLogi_id(String logi_id) {
		this.logi_id = logi_id;
	}
	public String getLogi_name() {
		return logi_name;
	}
	public void setLogi_name(String logi_name) {
		this.logi_name = logi_name;
	}
	public String getLogi_no() {
		return logi_no;
	}
	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}
	public Delivery getDelivery() {
		return delivery;
	}
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getConfirm_userid() {
		return confirm_userid;
	}
	public void setConfirm_userid(String confirm_userid) {
		this.confirm_userid = confirm_userid;
	}
	public String getConfirm_username() {
		return confirm_username;
	}
	public void setConfirm_username(String confirm_username) {
		this.confirm_username = confirm_username;
	}
	/*public String getNext_deal_user_id() {
		return next_deal_user_id;
	}
	public void setNext_deal_user_id(String next_deal_user_id) {
		this.next_deal_user_id = next_deal_user_id;
	}
	public String getNext_deal_group_id() {
		return next_deal_group_id;
	}
	public void setNext_deal_group_id(String next_deal_group_id) {
		this.next_deal_group_id = next_deal_group_id;
	}*/
	
}
