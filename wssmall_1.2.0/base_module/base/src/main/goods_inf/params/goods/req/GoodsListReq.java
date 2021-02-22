package params.goods.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

/**
 * 商品列表查询
 * @作者 MoChunrun
 * @创建日期 2013-10-28 
 * @版本 V 1.0
 */
public class GoodsListReq extends ZteRequest {

	private String type_id;//商品类型ID
	private String order_by = "1";//排序
	private String brand;//品牌ID
	private String prop;//关于属性的过滤 属性值示例: 0_1,0_2
	private String keyword;//关键字
	private String minPrice;//最小价格
	private String maxPrice;//最大价格
	private String agn;//商家ID
	private String cat_path;//类型路径  格式0|1000901008|1000951035|1000961278|
	private String member_lv_id = "0";
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
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
}
