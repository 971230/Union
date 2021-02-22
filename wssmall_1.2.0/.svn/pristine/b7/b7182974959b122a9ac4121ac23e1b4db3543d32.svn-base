package params.goodscats.req;

import com.ztesoft.api.ApiRuleException;
import commons.CommonTools;
import params.ZteRequest;
/**
 * 
 * 商品详情页面，商品搜索条件参数
 * @author wui
 *
 */
public class GoodsQueryReq extends ZteRequest {

	private String cat_id ="0"; //举例商品分类为：1000961280
	private String distype ="index";
	private String order ="3";
	private String prop;
	private String brand_id; //品牌
	private String keyword; //关键字
	private String minprice; //最小价格
	private String maxprice; //最大价格
	private String tagids; //商品标签id
	private String supper_id; //商品供货商
	
	private int page=1;
	private String pagesize ="20";
	
	
	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getDistype() {
		return distype;
	}

	public void setDistype(String distype) {
		this.distype = distype;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public String getProp() {
		return CommonTools.getUTF8Value(prop);
	}

	public void setProp(String prop) {
		this.prop = prop;
	}


	public String getKeyword() {
		return CommonTools.getUTF8Value(keyword);
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMinprice() {
		return minprice;
	}

	public void setMinprice(String minprice) {
		this.minprice = minprice;
	}

	public String getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(String maxprice) {
		this.maxprice = maxprice;
	}

	

	public String getSupper_id() {
		return supper_id;
	}

	public void setSupper_id(String supper_id) {
		this.supper_id = supper_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getTagids() {
		return tagids;
	}

	public void setTagids(String tagids) {
		this.tagids = tagids;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
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
