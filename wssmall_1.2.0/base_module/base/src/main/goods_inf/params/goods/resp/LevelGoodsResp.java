package params.goods.resp;

import com.ztesoft.net.mall.core.model.Cat;

import params.ZteResponse;

import java.util.List;
/**
 * 
 * @author wui
 * 楼层商品数据
 * 
 * 数据结构：
 * 目录树
 * 	---一级目录树包含广告
 * 	---二级目录树包含商品
 *
 */
public class LevelGoodsResp extends ZteResponse {
	List<Cat> cat_tree;

	public List<Cat> getCat_tree() {
		return cat_tree;
	}

	public void setCat_tree(List<Cat> cat_tree) {
		this.cat_tree = cat_tree;
	}
	
}
