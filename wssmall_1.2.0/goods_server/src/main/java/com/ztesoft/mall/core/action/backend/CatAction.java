package com.ztesoft.mall.core.action.backend;

import com.alibaba.fastjson.JSON;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.PermssionRuntimeException;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.DBRuntimeException;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.CatComplex;
import com.ztesoft.net.mall.core.service.*;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类action
 * 
 * @author apexking
 * 
 */
public class CatAction extends WWAction {

	private IGoodsCatManager goodsCatManager;
	private IGoodsTypeManager goodsTypeManager;
	private ICatComplexManager catComplexManager;
	private IBrandManager brandManager;
	private ITagManager tagManager;

	protected List catList;
	protected List goodCatList,productCatList;
	private List typeList,typeListProd;
	private Cat cat;
	private CatComplex catCom;
	private File image;
	private String imageFileName;
	private String floor_list_show;
	protected String cat_id;
	private String[] cat_ids; // 分类id 数组,用于保存排序
	private int[] cat_sorts; // 分类排序值

	private String parentid;
	private Integer catid;
	private String is_show;
	private String lv_id;
	private String arrayCatId;
	private List memberLvList;
	
	private String type;//广东联通ECS：货品product，商品goods

	// 便捷商品类型选择对话框
	public String findCatListByParentId() {
		List<Cat> catNodes = this.goodsCatManager
				.findCatNodesByParentid(parentid);
		json = JSON.toJSONString(catNodes);
		return WWAction.JSON_MESSAGE;
	}
	
	// 便捷商品类型选择对话框
	public String findCatListByParentIdEcs() {
		List<Cat> catNodes = this.goodsCatManager
				.findCatNodesByParentid(parentid);
		List<Cat> cats = new ArrayList<Cat>();
		for(Cat cat : catNodes){
			if(type.equals(cat.getType())){
				cats.add(cat);
			}
		}
		json = JSON.toJSONString(cats);
		return WWAction.JSON_MESSAGE;
	}

	public String showCatList() {
		return "showCatList";
	}
	
	public String showCatListEcs() {
		return "showCatListEcs";
	}

	// 检测类别是否重名
	public String checkname() {
		//base.src.main.java.com.ztesoft.common.util.StringUtils.java
		//校验并且替换字特殊符
		String catName = StringUtils.repalceSpecialCharacter(cat.getName());
		String catId =getCat_id();
		if (this.goodsCatManager.checkname(catName, catId)) {
			this.json = "{result:1}";
		} else {
			this.json = "{result:0}";
		}
		return WWAction.JSON_MESSAGE;
	}
	// 显示列表
	public String list() {
		catList = goodsCatManager.listAllChildren("0");
		memberLvList=goodsCatManager.listMemberLv();
		if("ECS".equals(ManagerUtils.getSourceFrom())){
			return "cat_list_ecs";
		}else{
			return "cat_list";
		}
		
	}

	// 显示列表
	public String list_select() {
		catList = goodsCatManager.listAllChildrenForSecond("0");
		return "cat_list_select";
	}

	// 到添加页面
	public String add() {
		typeList = goodsTypeManager.listAll();
		typeListProd = goodsTypeManager.listAllProduct();
		catList = goodsCatManager.listAllChildren("0");

		return "cat_add";
	}
	
	// 到添加页面
	public String addEcs() {
		typeList = goodsTypeManager.ListAllGoodTypes();
		typeListProd = goodsTypeManager.listAllProduct();
		goodCatList = goodsCatManager.listAllChildrenEcs("0","goods");
		productCatList = goodsCatManager.listAllChildrenEcs("0","product");
		return "cat_add_ecs";
	}
	
	// 编辑
	public String edit() {
		try {
			typeList = goodsTypeManager.listAll();
			typeListProd = goodsTypeManager.listAllProduct();
			catList = goodsCatManager.listAllChildren("0");
			cat = goodsCatManager.getById(cat_id);
			return "cat_edit";
		} catch (DBRuntimeException ex) {
			this.msgs.add("您查询的商品不存在");
			return MESSAGE;
		}
	}
	
	// 编辑
	public String editEcs() {
		try {
			typeList = goodsTypeManager.ListAllGoodTypes();
			typeListProd = goodsTypeManager.listAllProduct();
			goodCatList = goodsCatManager.listAllChildrenEcs("0","goods");
			productCatList = goodsCatManager.listAllChildrenEcs("0","product");
			cat = goodsCatManager.getById(cat_id);
			return "cat_edit_ecs";
		} catch (DBRuntimeException ex) {
			this.msgs.add("您查询的商品不存在");
			return MESSAGE;
		}
	}

	// 保存添加
	public String saveAdd() {
		if (image != null) {
			if (FileBaseUtil.isAllowUp(imageFileName)) {
				cat.setImage(UploadUtil
						.upload(image, imageFileName, "goodscat"));

			} else {
				this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
				return WWAction.MESSAGE;
			}
		}
		cat.setGoods_count(0);
		cat.setName(StringUtils.repalceSpecialCharacter(cat.getName()));
		goodsCatManager.saveAdd(cat);
		this.msgs.add("分类添加成功");
		this.urls.put("分类列表", "cat!list.do");
		return WWAction.MESSAGE;
	}

	public String editFloorListShow() {
		int i = this.goodsCatManager.editFloorListShowById(cat_id,
				floor_list_show);
		if (i > 0) {
			json = "{'result':1,'message':'修改成功'}";
		} else {
			json = "{'result':0,'message':'修改失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	public String editMemberLv(){
		
		try {
			if("0".equals(is_show)){
				this.goodsCatManager.deleteMemberLvById(arrayCatId,lv_id);
			}else if("1".equals(is_show)){
				this.goodsCatManager.deleteMemberLvById(arrayCatId,lv_id);
				this.goodsCatManager.addMemberLvById(arrayCatId,lv_id);
			}
			json = "{'result':1,'message':'修改成功'}";
		} catch (RuntimeException e) {
			e.printStackTrace();
			json = "{'result':0,'message':'修改失败'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}

	// 保存修改
	public String saveEdit() {
		if (image != null) {
			if (FileBaseUtil.isAllowUp(imageFileName)) {
				cat.setImage(UploadUtil
						.upload(image, imageFileName, "goodscat"));

			} else {
				this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
				return MESSAGE;
			}
		}
		try {
			// 保存类型列表中关联商品
			if (catCom != null) {
				catCom.setCat_id(cat.getCat_id());
				catCom.setCreate_time(DBTUtil.current());
				catCom.setRate(1);
				catComplexManager.save(catCom);

			}

			String parent_id = cat.getParent_id();
			if (parent_id.equals("0")) {
				this.goodsCatManager.update(cat);
				this.msgs.add("分类修改成功");
				this.urls.put("分类列表", "cat!list.do");
				return MESSAGE;
			}
			Cat targetCat = goodsCatManager.getById(parent_id);// 将要修改为父分类的对象
			if (parent_id.equals(cat.getCat_id())
					|| targetCat.getParent_id().equals(cat.getCat_id())) {
				this.msgs.add("保存失败：上级分类不能选择当前分类或其子分类");
				this.urls.put("分类列表", "cat!list.do");
				return MESSAGE;
			} else {
				this.goodsCatManager.update(cat);
				this.msgs.add("分类修改成功");
				this.urls.put("分类列表", "cat!list.do");
				return this.list();
			}

		} catch (PermssionRuntimeException ex) {
			this.msgs.add("非法操作");
			return MESSAGE;
		}

	}

	// 删除
	public String delete() {

		try {
			int r = this.goodsCatManager.delete(cat_id);

			if (r == 0) {
				json = "{'result':0,'message':'删除成功'}";
			} else if (r == 1) {
				json = "{'result':1,'message':'此类别下存在子类别不能删除!'}";
			} else if (r == 2) {
				json = "{'result':1,'message':'此类别下存在商品不能删除!'}";
			}
		} catch (PermssionRuntimeException ex) {
			json = "{'result':1,'message':'非法操作!'}";
			return JSON_MESSAGE;
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String doPublish(){
		try{
			Map params = new HashMap();
			this.goodsCatManager.doPublish(params);
			this.json = "{'result':0,'message':'发布成功!'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{'result':1,'message':'发布失败!'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	public String saveSort() {
		this.goodsCatManager.saveSort(cat_ids, cat_sorts);
		json = "{'result':0,'message':'保存成功'}";
		return WWAction.JSON_MESSAGE;
	}

	public String findBrandByCatId() {
		List brand = this.brandManager.listByCatId(catid);
		json = JSON.toJSONString(brand);
		return WWAction.JSON_MESSAGE;
	}
	
	public String findTagList(){
		List tagList = this.tagManager.list();
		json = JSON.toJSONString(tagList);
		return WWAction.JSON_MESSAGE;
	}

	public List getCatList() {
		return catList;
	}

	public void setCatList(List catList) {
		this.catList = catList;
	}

	public List getGoodCatList() {
		return goodCatList;
	}

	public void setGoodCatList(List goodCatList) {
		this.goodCatList = goodCatList;
	}

	public List getProductCatList() {
		return productCatList;
	}

	public void setProductCatList(List productCatList) {
		this.productCatList = productCatList;
	}

	public Cat getCat() {
		return cat;
	}

	public void setCat(Cat cat) {
		this.cat = cat;
	}

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String[] getCat_ids() {
		return cat_ids;
	}

	public void setCat_ids(String[] cat_ids) {
		this.cat_ids = cat_ids;
	}

	public int[] getCat_sorts() {
		return cat_sorts;
	}

	public void setCat_sorts(int[] cat_sorts) {
		this.cat_sorts = cat_sorts;
	}

	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}

	public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
		this.goodsTypeManager = goodsTypeManager;
	}

	public List getTypeList() {
		return typeList;
	}

	public void setTypeList(List typeList) {
		this.typeList = typeList;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getFloor_list_show() {
		return floor_list_show;
	}

	public void setFloor_list_show(String floor_list_show) {
		this.floor_list_show = floor_list_show;
	}

	public CatComplex getCatCom() {
		return catCom;
	}

	public void setCatCom(CatComplex catCom) {
		this.catCom = catCom;
	}

	public void setCatComplexManager(ICatComplexManager catComplexManager) {
		this.catComplexManager = catComplexManager;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public IBrandManager getBrandManager() {
		return brandManager;
	}

	public void setBrandManager(IBrandManager brandManager) {
		this.brandManager = brandManager;
	}

	public Integer getCatid() {
		return catid;
	}

	public void setCatid(Integer catid) {
		this.catid = catid;
	}

	public ITagManager getTagManager() {
		return tagManager;
	}

	public void setTagManager(ITagManager tagManager) {
		this.tagManager = tagManager;
	}
	
	public String getIs_show() {
		return is_show;
	}

	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}

	public String getLv_id() {
		return lv_id;
	}

	public void setLv_id(String lv_id) {
		this.lv_id = lv_id;
	}

	public List getMemberLvList() {
		return memberLvList;
	}

	public void setMemberLvList(List memberLvList) {
		this.memberLvList = memberLvList;
	}

	public String getArrayCatId() {
		return arrayCatId;
	}

	public void setArrayCatId(String arrayCatId) {
		this.arrayCatId = arrayCatId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List getTypeListProd() {
		return typeListProd;
	}

	public void setTypeListProd(List typeListProd) {
		this.typeListProd = typeListProd;
	}
	
}
