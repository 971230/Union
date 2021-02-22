package com.ztesoft.net.mall.core.model.support;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.framework.util.CurrencyUtil;
import com.ztesoft.net.mall.core.model.Promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物项
 * @author kingapex
 *2010-5-5上午10:13:27
 */
public class CartItem {

	@ZteSoftCommentAnnotationParam(name="购物车ID",type="String",isNecessary="Y",desc="购物车ID")
	private String id;
	@ZteSoftCommentAnnotationParam(name="产品ID",type="String",isNecessary="Y",desc="产品ID")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="商品ID")
	private String goods_id;
	@ZteSoftCommentAnnotationParam(name="商品名称",type="String",isNecessary="Y",desc="商品名称")
	private String name;
	@ZteSoftCommentAnnotationParam(name="市场单价",type="String",isNecessary="Y",desc="市场单价")
	private Double mktprice;
	@ZteSoftCommentAnnotationParam(name="购买单价",type="String",isNecessary="Y",desc="市场单价")
	private Double price;
	@ZteSoftCommentAnnotationParam(name="优惠后单价",type="String",isNecessary="Y",desc="优惠后单价")
	private Double coupPrice; // 优惠后的价格
	private Double subtotal; // 小计

	@ZteSoftCommentAnnotationParam(name="购买数量",type="String",isNecessary="Y",desc="购买数量")
	private int num;
	private Integer limitnum; //限购数量(对于赠品)
	private String image_default;
	private Integer point;
	private Integer itemtype; //物品类型(0商品，1捆绑商品，2赠品)
	private String sn; // 捆绑促销的货号 
	private String addon; //配件字串
	private String specs;
	private int catid; //是否货到付款
	private Map others; //扩展项,可通过 ICartItemFilter 进行过滤修改
	private String is_checked; //购物车中选中结算的商品
	private String staff_no; //管理员编码
	private String agent_name; //商家编码
	
	private List<HashMap> addonList; //配件列表
	
	//此购物项所享有的优惠规则
	private List<Promotion > pmtList;
	
	private int ctn;
	private String unit;
	
	private String member_lv_id;
	private String spec_id;

	@NotDbField
	public String getMember_lv_id() {
		return member_lv_id;
	}

	public void setMember_lv_id(String member_lv_id) {
		this.member_lv_id = member_lv_id;
	}

	@NotDbField
	public int getCtn() {
		return ctn;
	}

	public void setCtn(int ctn) {
		this.ctn = ctn;
	}
	@NotDbField
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setPmtList(List<Promotion > pmtList){
		this.pmtList = pmtList;
	}
	
	@NotDbField
	public List<Promotion > getPmtList(){
		return this.pmtList;
	}
	
	
	@NotDbField
	public Map getOthers() {
		if(others==null ) others = new HashMap();
		return others;
	}

	public void setOthers(Map others) {
		this.others = others;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String productId) {
		product_id = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMktprice() {
		return mktprice;
	}

	public void setMktprice(Double mktprice) {
		this.mktprice = mktprice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCoupPrice() {
		return coupPrice;
	}

	public void setCoupPrice(Double coupPrice) {
		this.coupPrice = coupPrice;
	}

	public Double getSubtotal() {
//		this.subtotal= this.num * this.coupPrice;
		this.subtotal= CurrencyUtil.mul(this.num , this.coupPrice);
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getImage_default() {
		return image_default;
	}

	public void setImage_default(String imageDefault) {
		image_default = imageDefault;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goodsId) {
		goods_id = goodsId;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

 
	public Integer getLimitnum() {
		return limitnum;
	}

	public void setLimitnum(Integer limitnum) {
		this.limitnum = limitnum;
	}

	public Integer getItemtype() {
		return itemtype;
	}

	public void setItemtype(Integer itemtype) {
		this.itemtype = itemtype;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getAddon() {
		return addon;
	}

	public void setAddon(String addon) {
		this.addon = addon;
	}

	public String getSpecs() {
		return specs;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	public String getIs_checked() {
		return is_checked;
	}

	public void setIs_checked(String is_checked) {
		this.is_checked = is_checked;
	}

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	@NotDbField
	public List<HashMap> getAddonList() {
		return addonList;
	}
	@NotDbField
	public void setAddonList(List<HashMap> addonList) {
		this.addonList = addonList;
	}

	public String getSpec_id() {
		return spec_id;
	}

	public void setSpec_id(String spec_id) {
		this.spec_id = spec_id;
	}
	

}
