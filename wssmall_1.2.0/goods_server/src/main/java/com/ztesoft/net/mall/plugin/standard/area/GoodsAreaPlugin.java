package com.ztesoft.net.mall.plugin.standard.area;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.net.app.base.core.model.Lan;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.GoodsArea;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsPlugin;
import com.ztesoft.net.mall.core.service.IGoodsAreaManager;
import com.ztesoft.net.mall.core.service.ILanManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 商品发布区域插件
 * 
 * @author kingapex 2010-1-18下午02:56:43
 */
public class GoodsAreaPlugin extends AbstractGoodsPlugin {
	private ILanManager lanManager;

	protected IGoodsAreaManager goodsAreaManager;

	@Override
	public void addTabs() {

	}

	/* 为添加商品和修改商品页面填充必要的数据 */
	@Override
	public String onFillGoodsAddInput(HttpServletRequest request) {
		List<Lan> arealist = this.lanManager.listEdit();
		FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
		freeMarkerPaser.setPageName("area");
		freeMarkerPaser.putData("areaList", arealist);
		return freeMarkerPaser.proessPageContent();
	}

	@Override
	public String onFillGoodsEditInput(Map goods, HttpServletRequest request) {
		List<Lan> arealist = this.lanManager.listEdit(); // 编辑页面设置
		String goods_id = goods.get("goods_id").toString();
		List<String> areaIds = this.lanManager.list(goods_id);

		FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser(getClass());
		freeMarkerPaser.setPageName("area");
		freeMarkerPaser.putData("areaList", arealist);
		freeMarkerPaser.putData("areaRelList", areaIds);
		return freeMarkerPaser.proessPageContent();
	}

	/* 在保存添加和保存更新的时候，将tagid的数组和goodsid对应起来保存在库里 */

	@Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
			throws RuntimeException {
		this.save(goods, request);

	}

	/* 商品发布区域修改后执行* */
	@Override
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {
		this.save(goods, request);

	}

	private void save(Map goods, HttpServletRequest request) {
		
		String goods_id = goods.get("goods_id").toString();
		request.getParameterMap();
		String[] areastr = request.getParameterValues("lan_ids");// 页面传送过来的lan_id集合==>1
		// 根据发布地区同时插入数据 到es_goods_area表中,可同时发布多个区域
		// 如果修改商品发布区域后,先获取原表中商品所有的发布区域
		List<String> dropList = new ArrayList<String>();//要设置为审核不通过的商品id
		List<GoodsArea> list = goodsAreaManager.serach(goods_id);// 根据商品ID查询DB中原有的ID集合==>2
		int m = list.size();
		
		AdminUser adminUser = ManagerUtils.getAdminUser(); // 获取登录用户
		String userlanid=adminUser.getLan_id();
		if(!"10000000".equals(userlanid)){//不是省级的
			GoodsArea goodsArea = new GoodsArea();
			goodsArea.setApply_userid(adminUser.getUserid());
			goodsArea.setGoods_id(goods_id);
			goodsArea.setLan_id(userlanid);
			goodsArea.setCreate_date(DBTUtil.current());
			goodsArea.setApply_id(DateFormatUtils
					.formatDate("yyyyMMddHHmmss"));
			goodsArea.setState(Consts.PARTNER_STATE_NORMAL);
			goodsArea.setApply_username(adminUser.getUsername());
			//es_goods_area有就修改，无则添加
			if(m>0){
				goodsAreaManager.edit(goodsArea);
			}else{
				goodsAreaManager.add(goodsArea);
			}
			
		}else{
			List<String> templist1 = new ArrayList<String>();
			List<String> templist2 = new ArrayList<String>();
			if (areastr != null) {
				int j = areastr.length;
				for (int i = 0; i < j; i++) {
					templist1.add(areastr[i]);
				}
				for (int i = 0; i < j; i++) {
					GoodsArea goodsArea = new GoodsArea();
					goodsArea.setApply_userid(adminUser.getUserid());
					goodsArea.setGoods_id(goods_id);
					goodsArea.setLan_id(areastr[i]);
					goodsArea.setCreate_date(DBTUtil.current());
					goodsArea.setApply_id(DateFormatUtils
							.formatDate("yyyyMMddHHmmss"));
					goodsArea.setState(Consts.PARTNER_STATE_APPLY);
					goodsArea.setApply_username(adminUser.getUsername());
					if (!list.contains(goodsArea)) {
						goodsAreaManager.deleteByLanIdAndGoodsId(goodsArea);//不包含先删除再插入
						goodsAreaManager.add(goodsArea);
					}
				}
			} else {// 默认发布全省---->可能修改全省
				List<Lan> arealist = this.lanManager.listEdit();
				int n = arealist.size();
				if(n==m){
					
				}else{
					for (Lan l : arealist) {
						GoodsArea goodsArea = new GoodsArea();
						goodsArea.setApply_userid(adminUser.getUserid());
						goodsArea.setGoods_id(goods_id);
						goodsArea.setLan_id(l.getLan_id());
						goodsArea.setCreate_date(DBTUtil.current());
						goodsArea.setApply_id(DateFormatUtils
								.formatDate("yyyyMMddHHmmss"));
						goodsArea.setState(Consts.PARTNER_STATE_APPLY);
						goodsArea.setApply_username(adminUser.getUsername());
						if (!list.contains(goodsArea)) {
							goodsAreaManager.deleteByLanIdAndGoodsId(goodsArea);//不包含先删除再插入
							goodsAreaManager.add(goodsArea);
						}
					}
				}
			}
			for (int i = 0; i < m; i++) {
				templist2.add(list.get(i).getLan_id().toString());
			}
			for (int i = 0; i < m; i++) {
				String xx = list.get(i).getLan_id().toString();
				if (!templist1.contains(xx)) {//找出在新区域中不存在的原区域
					dropList.add(xx);
				}
			}
			// 1在2中没有的取消显示,修改状态为审核不通过,0待审核,1上架,2审核不通过,3下架
			for (String gs : dropList) {
				// logger.info("要取消显示的区域: "+gs);
				goodsAreaManager.editAreaState(gs, goods_id);
			}
		}
	}

	@Override
	public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {
	}

	@Override
	public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {

	}

	@Override
	public String getAuthor() {
		return "kingapex";
	}

	@Override
	public String getId() {
		return "goodsarea";
	}

	@Override
	public String getName() {
		return "发布区域";
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void perform(Object... params) {

	}

	public void setLanManager(ILanManager lanManager) {
		this.lanManager = lanManager;
	}

	public IGoodsAreaManager getGoodsAreaManager() {
		return goodsAreaManager;
	}

	public void setGoodsAreaManager(IGoodsAreaManager goodsAreaManager) {
		this.goodsAreaManager = goodsAreaManager;
	}

	public ILanManager getLanManager() {
		return lanManager;
	}

}