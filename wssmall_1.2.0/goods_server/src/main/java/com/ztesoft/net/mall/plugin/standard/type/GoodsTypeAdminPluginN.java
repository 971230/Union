package com.ztesoft.net.mall.plugin.standard.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.powerise.ibss.framework.Const;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.eop.processor.core.freemarker.RepeatDirective;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPluginN;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;


/**
 * 类型管理插件
 * @author zou.qh
 *
 */
public class GoodsTypeAdminPluginN extends AbstractGoodsPluginN{

	private IGoodsCatManager goodsCatManager;
	private IGoodsTypeManager goodsTypeManager;
	private String parent_id;
	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	@Override
	public void addTabs() {
	}

	@Override
	public void onBeforeGoodsEdit(Map goods, GoodsExtData goodsExtData)  {
	
	}
	
	@Override
	public void onBeforeGoodsAdd(Map goods, GoodsExtData goodsExtData) {
		
	}

	
	@Override
	public String onFillGoodsAddInput() {
		List typeList = this.goodsTypeManager.ListAllGoodTypes();
		List catList = goodsCatManager.listAllChildrenByAgentNo("0"); //add by wui根据工号过滤
		List stypeList = this.goodsTypeManager.childListStype(String.valueOf(-1));
		List childStypeList=this.goodsTypeManager.childListStype(parent_id);
		List newCatList = new ArrayList();//货品或商品分类
		for(int i=0;i<catList.size();i++){//根据type过滤货品或商品分类
			Cat cat = (Cat) catList.get(i);
			if(StringUtils.isEmpty(cat.getType()) || "goods".equals(cat.getType())){
				newCatList.add(cat);
			}
		}
		List newTypeList = new ArrayList();
		for(int i=0;i<typeList.size();i++){
			GoodsType type = (GoodsType) typeList.get(i);
			if(StringUtils.isEmpty(type.getType()) || "goods".equals(type.getType())){
				newTypeList.add(type);
			}
		}
		HttpServletRequest request =  ThreadContextHolder.getHttpRequest();
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();// new FreeMarkerPaser(getClass(),"/plugin/type");
		freeMarkerPaser.setPageName("type_inputn");
		freeMarkerPaser.putData("catList", newCatList);
		freeMarkerPaser.putData("typeList", newTypeList);
		freeMarkerPaser.putData("stypeList", stypeList);
		freeMarkerPaser.putData("childListStype", childStypeList);
		freeMarkerPaser.putData("ctx",request.getContextPath());
		freeMarkerPaser.putData("is_edit", false);
		freeMarkerPaser.putData("repeat", new RepeatDirective());
		freeMarkerPaser.putData("founder", ManagerUtils.getAdminUser().getFounder()+"");
//		String catid = goodsExtData.getCatid();
//		if(!StringUtil.isEmpty(catid)){
//			freeMarkerPaser.putData("catid",Integer.valueOf(catid));
//		}
		return freeMarkerPaser.proessPageContent();
	}
 
	@Override
	public void onAfterGoodsEdit(Map goods, GoodsExtData goodsExtData)  {
		
	}

	@Override
	public void onAfterGoodsAdd(Map goods, GoodsExtData goodsExtData)  {

	}
		

	@Override
	public String onFillGoodsEditInput(Map goods, GoodsExtData goodsExtData) {
		String type = Const.getStrValue(goods, "type");//广东联通ECS添加，区分商品货品
		List catList = goodsCatManager.listAllChildren("0");
		List typeList = this.goodsTypeManager.listAll();
		List stypeList = this.goodsTypeManager.listStype();
        String stype_id=(goods.get("stype_id")==null?"":String.valueOf(goods.get("stype_id")));
        List childListStype=this.goodsTypeManager.childListStype(stype_id);
        List newCatList = new ArrayList();//货品或商品分类
		for(int i=0;i<catList.size();i++){//根据type过滤货品或商品分类
			Cat cat = (Cat) catList.get(i);
			if(StringUtils.isEmpty(cat.getType()) || "goods".equals(cat.getType())){
				newCatList.add(cat);
			}
		}
		List newTypeList = new ArrayList();
		for(int i=0;i<typeList.size();i++){
			GoodsType goodsType = (GoodsType) typeList.get(i);
			if(StringUtils.isEmpty(goodsType.getType()) || "goods".equals(goodsType.getType())){
				newTypeList.add(goodsType);
			}
		}
		
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
		freeMarkerPaser.setPageName("type_inputn");
		freeMarkerPaser.putData("cat_id", Const.getStrValue(goods, "cat_id"));
		freeMarkerPaser.putData("type_id", Const.getStrValue(goods, "type_id"));
		freeMarkerPaser.putData("brand_id", Const.getStrValue(goods, "brand_id"));
		freeMarkerPaser.putData("catList", newCatList);
		freeMarkerPaser.putData("typeList", newTypeList);
		freeMarkerPaser.putData("stypeList", stypeList);
        freeMarkerPaser.putData("childListStype",childListStype);//子服务类型
		freeMarkerPaser.putData("is_edit", true);
		freeMarkerPaser.putData("founder", ManagerUtils.getAdminUser().getFounder()+"");
		freeMarkerPaser.putData("repeat", new RepeatDirective());
		freeMarkerPaser.putData("type", type);
		return freeMarkerPaser.proessPageContent();
	}

	@Override
	public void perform(Object... params) {

	}
	
	@Override
	public String getAuthor() {
		return "zou.qh";
	}

	@Override
	public String getId() {
		return "goodstypeN";
	}

	@Override
	public String getName() {
		return "商品类型插件";
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
	
	public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
		this.goodsTypeManager = goodsTypeManager;
	}

	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}

	public IGoodsCatManager getGoodsCatManager() {
		return goodsCatManager;
	}

	public IGoodsTypeManager getGoodsTypeManager() {
		return goodsTypeManager;
	}
	
}
