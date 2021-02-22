package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

/**
 * 商品列表查询
 * @作者 MoChunrun
 * @创建日期 2013-10-28 
 * @版本 V 1.0
 */
public class GoodsPageListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品类型ID",type="String",isNecessary="N",desc="type_id：商品类型ID。")
	private String type_id;//商品类型ID
	@ZteSoftCommentAnnotationParam(name="排序",type="String",isNecessary="N",desc="order_by：排序。1:按最后修改时间顺序,2:按最后修改时间倒序,3:按销售价倒序,4:按销售价顺序,5:按访问次数倒序,6:按购买数量顺序,7:按购买数量倒序,8:按折扣倒序,10:按库存顺序排序,11:按库存倒序排序,其他:sord desc")
	private String order_by;//排序
	@ZteSoftCommentAnnotationParam(name="品牌ID",type="String",isNecessary="N",desc="brand：品牌ID。")
	private String brand;//品牌ID
	@ZteSoftCommentAnnotationParam(name="商品属性",type="String",isNecessary="N",desc="prop：关于属性的过滤 属性值示例: 0_1,0_2。")
	private String prop;//关于属性的过滤 属性值示例: 0_1,0_2
	//@ZteSoftCommentAnnotationParam(name="关键字",type="String",isNecessary="N",desc="keyword：关键字，搜索es_goods的name字段")
	private String keyword;//关键字，搜索es_goods的name字段
	@ZteSoftCommentAnnotationParam(name="最小价格",type="String",isNecessary="N",desc="minPrice：最小价格。")
	private String minPrice;//最小价格
	@ZteSoftCommentAnnotationParam(name="最大价格",type="String",isNecessary="N",desc="maxPrice：最大价格。")
	private String maxPrice;//最大价格
	@ZteSoftCommentAnnotationParam(name="商家ID",type="String",isNecessary="N",desc="agn：商家ID。")
	private String agn;//商家ID
	@ZteSoftCommentAnnotationParam(name="类型路径",type="String",isNecessary="N",desc="cat_path：类型路径  格式0|1000901008|1000951035|1000961278|。")
	private String cat_path;//类型路径  格式0|1000901008|1000951035|1000961278|
	@ZteSoftCommentAnnotationParam(name="会员等级ID",type="String",isNecessary="N",desc="member_lv_id：会员等级ID。")
	private String member_lv_id = "0";
	@ZteSoftCommentAnnotationParam(name="商品标签ID",type="String",isNecessary="N",desc="tagids：商品标签ID。")
	private String tagids;
	@ZteSoftCommentAnnotationParam(name="商品自定义属性",type="String",isNecessary="N",desc="attrStr：商品自定义属性，格式为is_group_1,is_limitbuy_1。")
	private String attrStr;
	@ZteSoftCommentAnnotationParam(name="搜索关键字（福建电商）",type="String",isNecessary="N",desc="search_key结构：_商家_分类_品牌_商品名称_，为like %关键字%")
	private String search_key;
	@ZteSoftCommentAnnotationParam(name="是否有库存",type="String",isNecessary="N",desc="has_stock：是否有库存，1：是，0：否")
	private String has_stock;
	@ZteSoftCommentAnnotationParam(name="分销商ID",type="String",isNecessary="Y",desc="has_stock：分销商ID")
	private String user_id ;
	@ZteSoftCommentAnnotationParam(name="是否参与活动",type="String",isNecessary="N",desc="is_sale：是否参与活动，1参与，0不参与")
	private String is_sale;
	private int pageNo=1;
	private int pageSize=10;
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getOrder_by() {
		return order_by;
	}
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getAgn() {
		return agn;
	}
	public void setAgn(String agn) {
		this.agn = agn;
	}
	public String getCat_path() {
		return cat_path;
	}
	public void setCat_path(String cat_path) {
		this.cat_path = cat_path;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getMember_lv_id() {
		return member_lv_id;
	}
	public void setMember_lv_id(String member_lv_id) {
		this.member_lv_id = member_lv_id;
	}
	public String getTagids() {
		return tagids;
	}
	public void setTagids(String tagids) {
		this.tagids = tagids;
	}
	public String getAttrStr() {
		return attrStr;
	}
	public void setAttrStr(String attrStr) {
		this.attrStr = attrStr;
	}
	public String getSearch_key() {
		return search_key;
	}
	public void setSearch_key(String search_key) {
		this.search_key = search_key;
	}
	public String getHas_stock() {
		return has_stock;
	}
	public void setHas_stock(String has_stock) {
		this.has_stock = has_stock;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getIs_sale() {
		return is_sale;
	}
	public void setIs_sale(String is_sale) {
		this.is_sale = is_sale;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goods.page";
	}
}
