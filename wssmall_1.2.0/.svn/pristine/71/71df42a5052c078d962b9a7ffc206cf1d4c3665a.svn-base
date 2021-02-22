package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.app.base.core.model.Adv;
import com.ztesoft.net.framework.database.NotDbField;

import java.io.Serializable;
import java.util.List;
import java.util.Map;





/**
 * 商品类别
 * @author apexking
 *
 */
public class Cat  implements Serializable {


  
	 protected String floor_list_show;//是否展示在楼层
	 protected String cat_id;
     protected String name;
     protected String parent_id;
     protected String cat_path;
     protected Integer goods_count;
     protected int cat_order;
     protected String type_id;
     protected String list_show;
     protected String image;
     protected String type;
     protected String user_id;//分销商ID
     
     /************以下为非数据库字段*************/
     private boolean hasChildren ;
     private List<Cat> children;  //子类别
     private String type_name; //类型名称    
     private String hot_type; //是否热词
     private String hot_name;
     /******会员等级*******/
     private String lvid0;
     private String lvid1;
     private String lvid2;
     private String lvid3;
     
     private List catGoodsList; //分类商品 
     private Adv adv;
     private List<Adv> advs;
     private List<Map> hotCats;
      
     
    public Adv getAdv() {
		return adv;
	}


	public void setAdv(Adv adv) {
		this.adv = adv;
	}


	public Cat() {
    	hasChildren =false;
    }


	public String getCat_id() {
		return cat_id;
	}


	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}


	public int getCat_order() {
		return cat_order;
	}


	public void setCat_order(int cat_order) {
		this.cat_order = cat_order;
	}


	public String getCat_path() {
		return cat_path;
	}


	public void setCat_path(String cat_path) {
		this.cat_path = cat_path;
	}


 


 

	public Integer getGoods_count() {
		return goods_count;
	}


	public void setGoods_count(Integer goods_count) {
		this.goods_count = goods_count;
	}


 


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getParent_id() {
		return parent_id;
	}


	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
 


	public String getType_id() {
		return type_id;
	}


	public void setType_id(String type_id) {
		this.type_id = type_id;
	}


	public String getList_show() {
		return list_show;
	}


	public void setList_show(String listShow) {
		list_show = listShow;
	}

	@NotDbField
	public List<Cat> getChildren() {
		return children;
	}


	public void setChildren(List<Cat> children) {
		this.children = children;
	}



	@NotDbField
	public String getType_name() {
		return type_name;
	}


	public void setType_name(String typeName) {
		type_name = typeName;
	}

	@NotDbField
	public boolean getHasChildren() {
		hasChildren = this.children==null|| this.children.isEmpty() ?false:true;
		return hasChildren;
	}


	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getHot_type() {
		return hot_type;
	}


	public void setHot_type(String hot_type) {
		this.hot_type = hot_type;
	}

	@SuppressWarnings("unchecked")
	@NotDbField
	public List getCatGoodsList() {
		return catGoodsList;
	}

	@SuppressWarnings("unchecked")
	@NotDbField
	public void setCatGoodsList(List catGoodsList) {
		this.catGoodsList = catGoodsList;
	}


	public String getFloor_list_show() {
		return floor_list_show;
	}


	public void setFloor_list_show(String floor_list_show) {
		this.floor_list_show = floor_list_show;
	}

	@NotDbField
	public List<Adv> getAdvs() {
		return advs;
	}

	@NotDbField
	public void setAdvs(List<Adv> advs) {
		this.advs = advs;
	}

	@NotDbField
	public List<Map> getHotCats() {
		return hotCats;
	}

	@NotDbField
	public void setHotCats(List<Map> hotCats) {
		this.hotCats = hotCats;
	}

	@NotDbField
	public String getLvid0() {
		return lvid0;
	}

	@NotDbField
	public void setLvid0(String lvid0) {
		this.lvid0 = lvid0;
	}

	@NotDbField
	public String getLvid1() {
		return lvid1;
	}

	@NotDbField
	public void setLvid1(String lvid1) {
		this.lvid1 = lvid1;
	}

	@NotDbField
	public String getLvid2() {
		return lvid2;
	}

	@NotDbField
	public void setLvid2(String lvid2) {
		this.lvid2 = lvid2;
	}

	@NotDbField
	public String getLvid3() {
		return lvid3;
	}

	@NotDbField
	public void setLvid3(String lvid3) {
		this.lvid3 = lvid3;
	}

	@NotDbField
	public String getHot_name() {
		return hot_name;
	}

	@NotDbField
	public void setHot_name(String hot_name) {
		this.hot_name = hot_name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
}