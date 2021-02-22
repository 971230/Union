package params.goodscats.resp;

import com.ztesoft.net.mall.core.model.Cat;

import params.ZteResponse;

import java.util.List;
@SuppressWarnings("unchecked")
/**
 * wu.i
 * 查询所有商品分类，包括
 * 1、树形商品分类
 * 2、商品分类关联品牌
 * 3、商品分类关联图片
 */
public class GoodsCatsResp extends ZteResponse {
	private List listCat; //分类相关信息，如：分类品牌，分类图片等
	List<Cat> cat_tree;
	
	private boolean  showimage; //是否展示分类图片
	public List getListCat() {
		return listCat;
	}
	public void setListCat(List listCat) {
		this.listCat = listCat;
	}
	public List<Cat> getCat_tree() {
		return cat_tree;
	}
	public void setCat_tree(List<Cat> cat_tree) {
		this.cat_tree = cat_tree;
	}
	public boolean isShowimage() {
		return showimage;
	}
	public void setShowimage(boolean showimage) {
		this.showimage = showimage;
	}
	
	
}
