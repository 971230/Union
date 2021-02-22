package com.ztesoft.net.mall.core.plugin.field;

import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsAfterAddEvent;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsAfterEditEvent;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsBeforeAddEvent;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsBeforeEditEvent;
import com.ztesoft.remote.inf.IRemoteGoodsFieldService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 商品自字义字段插件
 * 
 * @author kingapex
 * 
 */
public class GoodsCustomFieldPlugin extends AutoRegisterPlugin implements
		IGoodsBeforeAddEvent, IGoodsBeforeEditEvent, IGoodsAfterAddEvent,
		IGoodsAfterEditEvent {

    @Resource
	private IRemoteGoodsFieldService remoteGoodsFieldService;

	private GoodsFieldPluginBundle goodsFieldPluginBundle;


	/**
	 * 处理商品数据字段保存事件
	 * 
	 * @param goods
	 */
	private void save(Map goods) {
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		String typeid = request.getParameter("goods.type_id");
		if (typeid == null){
			typeid=(String)goods.get("type_id");//
		}
		if (typeid == null)
			throw new RuntimeException("保存商品时typeid不能为空");

		List<GoodsField> fieldList = remoteGoodsFieldService.list(typeid);
		if(fieldList ==null)
			return;
		for (GoodsField goodsField : fieldList) {
			goodsFieldPluginBundle.onSave(goods, goodsField);
		}
	}
	
	private void afterSave(Map goods){
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		String typeid = request.getParameter("goods.type_id");
		if (typeid == null){
			typeid=(String)goods.get("type_id");//
		}
		if (typeid == null)
			throw new RuntimeException("保存商品时typeid不能为空");

		List<GoodsField> fieldList = remoteGoodsFieldService.list(typeid);
		if(fieldList ==null)
			return;
		for (GoodsField goodsField : fieldList) {
			goodsFieldPluginBundle.onAfterSave(goods, goodsField);
		}
	}

	@Override
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest r) {
		this.save(goods);
	}

	@Override
	public void onBeforeGoodsEdit(Map goods, HttpServletRequest r) {
		this.save(goods);
	}

	@Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
			throws RuntimeException {
		 this.afterSave(goods);
	}

	@Override
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {
		 this.afterSave(goods);

	}

	public void setGoodsFieldPluginBundle(
			GoodsFieldPluginBundle goodsFieldPluginBundle) {
		this.goodsFieldPluginBundle = goodsFieldPluginBundle;
	}

	@Override
	public void register() {
	}

	@Override
	public String getAuthor() {

		return "kingapex";
	}

	@Override
	public String getId() {

		return "customfield";
	}

	@Override
	public String getName() {

		return "商品自定义字段插件";
	}

	@Override
	public String getType() {

		return "goods";
	}

	@Override
	public String getVersion() {

		return "1.0";
	}

	@Override
	public void perform(Object... params) {
	}

}