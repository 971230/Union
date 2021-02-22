package zte.net.ecsord.params.busi.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.treeInst.RequestStoreExector;

@RequestBeanAnnontion(primary_keys="item_id",table_name="es_delivery_item",service_desc="物流子单")
public class OrderDeliveryItemBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander{

	@RequestFieldAnnontion()
	private String item_id;   
	@RequestFieldAnnontion()
	private String delivery_id;  
	@RequestFieldAnnontion()
	private String goods_id;
	@RequestFieldAnnontion()
	private String product_id;
	@RequestFieldAnnontion()
	private String sn; 
	@RequestFieldAnnontion()
	private String name; 
	@RequestFieldAnnontion()
	private Integer num;
	@RequestFieldAnnontion()
	private Integer itemtype;  //货物类型
	@RequestFieldAnnontion()
	private String order_id;
	
	@RequestFieldAnnontion()
	private String col1;//打印状态
	
	@RequestFieldAnnontion()
	private String col2;//对应的礼品ID GIFT_INST_ID
	
	private Integer buy_num;//购买数量
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	@NotDbField
	public Integer getBuy_num() {
		return buy_num;
	}
	public void setBuy_num(Integer buy_num) {
		this.buy_num = buy_num;
	}
	private String order_item_id;
	@NotDbField
	public String getOrder_item_id() {
		return order_item_id;
	}
	public void setOrder_item_id(String order_item_id) {
		this.order_item_id = order_item_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String itemId) {
		item_id = itemId;
	}
	public String getDelivery_id() {
		return delivery_id;
	}
	public void setDelivery_id(String deliveryId) {
		delivery_id = deliveryId;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String productId) {
		product_id = productId;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
 
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goodsId) {
		goods_id = goodsId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getItemtype() {
		return itemtype;
	}
	public void setItemtype(Integer itemtype) {
		this.itemtype = itemtype;
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
	@Override
	@NotDbField
	public <T> T store() {
		return null;
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instParam) {
		return null;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return null;
	}
	/**
     * 只插入数据库，不更新缓存，在标准化入库时执行
     */
    @NotDbField
    public ZteResponse  insertNoCache(){
        return RequestStoreExector.getInstance().insertZteRequestInst(this);
    }
}
