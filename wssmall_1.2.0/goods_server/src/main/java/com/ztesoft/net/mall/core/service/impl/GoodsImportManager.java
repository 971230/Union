package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.cms.page.file.UploadTools;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.app.base.plugin.fileUpload.BatchResult;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.model.GoodsPackage;
import com.ztesoft.net.mall.core.model.GoodsRules;
import com.ztesoft.net.mall.core.service.IGoodsImportManager;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.impl.batchimport.util.GoodsImageReader;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.plugin.standard.adjunct.GoodsAdjunctPlugin;
import com.ztesoft.net.sqls.SF;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Saas式Goods业务管理
 * 
 * @author kingapex 2010-1-13下午12:07:07
 */
public class GoodsImportManager extends BaseSupport implements
		IGoodsImportManager {
 
	private IGoodsManager goodsManager;
	private GoodsImageReader goodsImageReader;
	private CacheUtil cacheUtil;

	@Override
	@SuppressWarnings("unchecked")
	public BatchResult importAdjunct(List inList,String goods_id) {
		BatchResult batchResult = new BatchResult();

		String batchId = this.baseDaoSupport.getSequences("S_ES_GOODS_ADJUNCT");
		int sucNum = 0; // 成功条数
		int failNum = 0; // 失败条数
		String err_msg = "";
		
		List productIds = new ArrayList();
		List goodsIds  = new ArrayList();
		
		HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();	
		int succ_count =0;
		List<Object[]> list = new ArrayList<Object[]>();
		String [] price_1 =null;//电信员工价格
		String [] price_2 =null;//经销商价格
		String [] price_0 =null;//普通会员价格
		try {
			if (inList != null && !inList.isEmpty()) {
				price_1 = new String[inList.size()-1];
				price_2 = new String[inList.size()-1];
				price_0 = new String[inList.size()-1];
				for (int i = 1; i < inList.size(); i++) {//跳过标题和模板数据 ,从第3行开始读取
					Map<String, Object> map = (Map<String, Object>) inList
							.get(i);
					String folderUrl = map.get("image_default").toString();
					String imageName = map.get("imageName").toString();
					Goods goods = new Goods();
					goods.setName(map.get("name").toString());
					goods.setSn(map.get("sn").toString());
					goods.setImage_default(folderUrl+imageName);
					goods.setMktprice(Double.parseDouble(map.get("mktprice")
							.toString()));
					goods.setPrice(Double.parseDouble(map.get("price")
							.toString()));
//					goods.setTaxes_ratio(Integer.parseInt(map
//							.get("taxes_ratio").toString()));
					goods.setUnit(map.get("unit").toString());
					goods.setIntro(map.get("specs").toString());
					goods.setCat_id("1000961090");// 这里先写死
					goods.setMarket_enable(1);
					goods.setDisabled(0);
					goods.setHave_spec("0");
					 
					httpRequest.setAttribute("haveSpec", "0");
					httpRequest.setAttribute("lvidArray", new String[]{Const.MEMBER_LV_COMMON, Const.MEMBER_LV_CHINA_TELECOM_STAFF,Const.MEMBER_LV_SUPPLIER});
					
					//设置电信员工、经销商、普通工号
//					String[] lvPriceStr = httpRequest.getParameterValues("lvPrice");
//					String[] lvidStr = httpRequest.getParameterValues("lvid");
					price_1[succ_count]=map.get("lv_id1").toString();
					price_2[succ_count]=map.get("lv_id2").toString();
					price_0[succ_count]=map.get("price").toString();
					String prices1A2 [] =new String[]{price_0[succ_count],price_1[succ_count],price_2[succ_count]};
					
					succ_count++;
					goods.setCost(0.0);
					goods.setStore(0);
					goods.setWeight(0.0);
					goods.setView_count(0);
					goods.setBuy_count(0);
					
					try {
						AdminUser adminUser = ManagerUtils.getAdminUser(); // 获取登录用户
						//							
						String user_no = adminUser.getUserid();

						// add by wui
						String userId = adminUser.getParuserid();

						if (ManagerUtils.isProvStaff()) {
							userId = "-1";
						} else {
							if (Consts.CURR_FOUNDER4 == ManagerUtils
									.getAdminUser().getFounder().intValue()) // 供货商
								userId = ManagerUtils.getAdminUser()
										.getUserid();
							else if (Consts.CURR_FOUNDER5 == ManagerUtils
									.getAdminUser().getFounder().intValue()) // 供货商员工
								userId = ManagerUtils.getAdminUser()
										.getParuserid();
						}
						goods.setStaff_no(userId);

						goods.setAudit_state("00A");

						goods.setCreator_user(user_no);
						goods.setGoods_type("adjunct");
						// goods.setMarket_enable(1);
						// if(goods.getMarket_enable()==1){
						goods.setLast_modify(DBTUtil.current());
						// }

						goodsManager.add(goods,new GoodsRules(),new GoodsPackage(),"","","","","","","","");
						
						goodsIds.add(goods.getGoods_id());
						//写配件价格,目前就3条记录，普通会员、经销商和电信员工,这里和excel模板对应
						for(int ii=0;ii<3;ii++){
							GoodsLvPrice lv = new GoodsLvPrice();
							lv.setGoodsid(goods.getGoods_id());
							lv.setLvid(Integer.toString(ii));
							lv.setPrice(Double.parseDouble(prices1A2[ii].toString()));
							lv.setProductid(httpRequest.getAttribute("imp_product_id").toString());
							lv.setStatus("00A");
							this.baseDaoSupport.insert("es_goods_lv_price", lv);
						}
						
						productIds.add(httpRequest.getAttribute("imp_product_id"));
						
						
						//图片上传
						String[] images= goodsImageReader.readStr(folderUrl, goods_id, "");
						if(images!=null)
						this.daoSupport.execute(SF.goodsSql("GOODS_UPDATE_IMG"), images[0],images[1],goods_id);
						
						if(this.logger.isDebugEnabled()){
							logger.debug(" 商品["+goods_id+"]图片导入完成");
						}
						
						//同步FTP
						String filePath = EopSetting.IMG_SERVER_PATH +EopContext.getContext().getContextPath()+  "\\attachment\\goods\\"+goods_id+"\\"+imageName;
						UploadTools.syToFtpFile(imageName, filePath, "/attachment/goods/" + goods_id + "/");

//						cacheUtil.refreshGoodsCache();
					} catch (RuntimeException e) {
						e.printStackTrace();
						// this.msgs.add(e.getMessage());
						if (logger.isDebugEnabled()) {
							logger.debug(e);
						}

					}
				}
				
				//配件处理
				GoodsAdjunctPlugin goodsAdjunctPlugin = SpringContextHolder.getBean("goodsAdjunctPlugin");
				Map pMap = new HashMap();
				pMap.put("productIds", productIds);
				pMap.put("goods_id", goods_id);//TODO
				goodsAdjunctPlugin.importAdjust(pMap);
				
				//写es_goods_lv_price表
//				for(int m=0;m<price_1.length;m++){
//					GoodsLvPrice lv = new GoodsLvPrice();
//					lv.setGoodsid(goods_id);
//					lv.setLvid(Const.MEMBER_LV_CHINA_TELECOM_STAFF);
//					lv.setPrice(Double.parseDouble(price_1[m]));
//					lv.setProductid(productIds.get(m).toString());
//					lv.setStatus("00A");
//					this.baseDaoSupport.insert("es_goods_lv_price", lv);
//				}
//				for(int n=0;n<price_2.length;n++){
//					GoodsLvPrice lv = new GoodsLvPrice();
//					lv.setGoodsid(goods_id);
//					lv.setLvid(Const.MEMBER_LV_SUPPLIER);
//					lv.setPrice(Double.parseDouble(price_2[n]));
//					lv.setProductid(productIds.get(n).toString());
//					lv.setStatus("00A");
//					this.baseDaoSupport.insert("es_goods_lv_price", lv);
//				}
//				for(int k=0;k<price_0.length;k++){
//					GoodsLvPrice lv = new GoodsLvPrice();
//					lv.setGoodsid(goods_id);
//					lv.setLvid(Const.MEMBER_LV_COMMON);
//					lv.setPrice(Double.parseDouble(price_0[k]));
//					lv.setProductid(productIds.get(k).toString());
//					lv.setStatus("00A");
//					this.baseDaoSupport.insert("es_goods_lv_price", lv);
//				}
				
				
			}

			sucNum = inList.size();
			failNum = 0;
		} catch (Exception e) {
			err_msg = e.getMessage();
			failNum = inList.size();
			sucNum = 0;
			e.printStackTrace();
		}

		batchResult.setErr_msg(err_msg);
		batchResult.setBatchId(batchId);
		batchResult.setSucNum(sucNum);
		batchResult.setFailNum(failNum);
		return batchResult;
	}
	
	@Override
	public Map getAdjunct(String adjunctid){
		String sql = SF.goodsSql("GET_GOODS_ADJUNCT");//"select * from es_goods_adjunct t where t.adjunct_id=?";
    	return this.baseDaoSupport.queryForMap(sql, adjunctid);
    }

	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}

	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public CacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(CacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	public GoodsImageReader getGoodsImageReader() {
		return goodsImageReader;
	}

	public void setGoodsImageReader(GoodsImageReader goodsImageReader) {
		this.goodsImageReader = goodsImageReader;
	}

}
