package com.ztesoft.net.mall.plugin.standard.platform;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.powerise.ibss.framework.Const;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.params.GoodsPlatformInfo;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPluginN;
import com.ztesoft.net.mall.core.service.IGoodsManagerN;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
/**
 * 商品平台信息插件桩
 * @author zou.qh
 *
 */
public class GoodsPlatformInfoPluginN extends AbstractGoodsPluginN {
	private IGoodsManagerN goodsManagerN;
	public IGoodsManagerN getGoodsManagerN() {
		return goodsManagerN;
	}

	public void setGoodsManagerN(IGoodsManagerN goodsManagerN) {
		this.goodsManagerN = goodsManagerN;
	}

	@Override
	public String onFillGoodsAddInput() {
		FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
		HttpServletRequest request =  ThreadContextHolder.getHttpRequest();
		String ctx = request.getContextPath();
		freeMarkerPaser.putData("ctx", ctx);
		GoodsPlatformInfo goodsPlatformInfo = new GoodsPlatformInfo();
		freeMarkerPaser.putData("goodsPlatformInfo", goodsPlatformInfo);
		freeMarkerPaser.setPageName("platformInfo");
		return freeMarkerPaser.proessPageContent();
	}

	@Override
	public void onBeforeGoodsAdd(Map goods, GoodsExtData goodsExtData) {
		// TODO Auto-generated method stub

	}

	@Override
	public String onFillGoodsEditInput(Map goods, GoodsExtData goodsExtData) {
		String goods_id = Const.getStrValue(goods, "goods_id");
		FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
		HttpServletRequest request =  ThreadContextHolder.getHttpRequest();
		String ctx = request.getContextPath();
		freeMarkerPaser.putData("ctx", ctx);
		GoodsPlatformInfo goodsPlatformInfo = goodsManagerN.getGoodsPlatFormInfo(goods_id);
		if(goodsPlatformInfo!=null){
			freeMarkerPaser.putData("goodsPlatformInfo",goodsPlatformInfo);
			List pc_image_file_list = stringToList(goodsPlatformInfo.getPc_image_file());
			freeMarkerPaser.putData("pc_image_file_list", pc_image_file_list);
			List mbl_image_file_list = stringToList(goodsPlatformInfo.getMbl_image_file());
			freeMarkerPaser.putData("mbl_image_file_list", mbl_image_file_list);
			List wx_image_file_list = stringToList(goodsPlatformInfo.getWx_image_file());
			freeMarkerPaser.putData("wx_image_file_list", wx_image_file_list);
		}
		
		freeMarkerPaser.setPageName("platformInfo");
		return freeMarkerPaser.proessPageContent();
	}

	@Override
	public void onAfterGoodsAdd(Map goods, GoodsExtData goodsExtData)
			throws RuntimeException {
		GoodsPlatformInfo goodsPlatformInfo = new GoodsPlatformInfo();
		try {
			BeanUtils.copyProperties(goodsPlatformInfo, goodsExtData);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		goodsPlatformInfo.setSource_from(ManagerUtils.getSourceFrom());
		goodsPlatformInfo.setGoods_id(Const.getStrValue(goods, "goods_id"));
		if(goodsExtData.getPc_image_file()!=null){
			goodsPlatformInfo.setPc_image_default(goodsExtData.getPc_image_file().split(",")[0]);
		}
		if(goodsExtData.getMbl_image_file()!=null){
			goodsPlatformInfo.setMbl_image_default(goodsExtData.getMbl_image_file().split(",")[0]);
		}
		if(goodsExtData.getWx_image_file()!=null){
			goodsPlatformInfo.setWx_image_default(goodsExtData.getWx_image_file().split(",")[0]);
		}
		
		goodsPlatformInfo.setCreate_time(Consts.SYSDATE);
		this.baseDaoSupport.insert("es_goods_plantform_info", goodsPlatformInfo);
	}

	@Override
	public void onAfterGoodsEdit(Map goods, GoodsExtData goodsExtData) {
		String goods_id = Const.getStrValue(goods, "goods_id");
		String sql = SF.goodsSql("GOODS_PLATFORM_INFO_DELETE");
		this.baseDaoSupport.execute(sql, goods_id);
		
		GoodsPlatformInfo goodsPlatformInfo = new GoodsPlatformInfo();
		try {
			BeanUtils.copyProperties(goodsPlatformInfo, goodsExtData);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		goodsPlatformInfo.setSource_from(ManagerUtils.getSourceFrom());
		goodsPlatformInfo.setGoods_id(Const.getStrValue(goods, "goods_id"));
		if(goodsExtData.getPc_image_file()!=null){
			goodsPlatformInfo.setPc_image_default(goodsExtData.getPc_image_file().split(",")[0]);
		}
		if(goodsExtData.getMbl_image_file()!=null){
			goodsPlatformInfo.setMbl_image_default(goodsExtData.getMbl_image_file().split(",")[0]);
		}
		if(goodsExtData.getWx_image_file()!=null){
			goodsPlatformInfo.setWx_image_default(goodsExtData.getWx_image_file().split(",")[0]);
		}
		goodsPlatformInfo.setCreate_time(Consts.SYSDATE);
		this.baseDaoSupport.insert("es_goods_plantform_info", goodsPlatformInfo);
	}

	@Override
	public void onBeforeGoodsEdit(Map goods, GoodsExtData goodsExtData) {
		// TODO Auto-generated method stub

	}
	
	private List<String> stringToList(String img_url){
		if(StringUtils.isEmpty(img_url)) return null;
		List<String> imgList = new ArrayList<String>();
		String[] urls = img_url.split(",");
		for(int i=0;i<urls.length;i++){
			imgList.add(urls[i]);
		}
		return imgList;
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public String getId() {
		return "platformInfoN";
	}

	@Override
	public String getName() {
		return "商品平台信息";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "zou.qh";
	}

	@Override
	public void perform(Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTabs() {
		// TODO Auto-generated method stub

	}

}
