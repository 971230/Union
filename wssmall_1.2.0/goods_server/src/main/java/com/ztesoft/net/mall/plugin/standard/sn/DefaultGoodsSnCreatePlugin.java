package com.ztesoft.net.mall.plugin.standard.sn;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.plugin.goods.AbstractGoodsSnCreator;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import java.util.Date;
import java.util.Map;


/**
 * 默认商品货号生成插件
 * 
 * @author enation
 * 
 */
/**
 * @author kingapex
 * 2010-6-3下午02:52:56
 */
public class DefaultGoodsSnCreatePlugin extends AbstractGoodsSnCreator {

	private IDaoSupport baseDaoSupport;
	
 


	
	@Override
	public IDaoSupport getBaseDaoSupport() {
		return baseDaoSupport;
	}


	@Override
	public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
		this.baseDaoSupport = baseDaoSupport;
	}


	@Override
	public void register() {
	 
		
	}


	/**
	 * 由系统时间得到一long数定生成货号
	 */
	
	@Override
	public String createSn(Map goods) {

		
		//add by wui去除编码唯一性验证
		if (goods.get("sn") != null && !goods.get("sn").equals("")) {
			
			if(goods.get("goods_id") == null){ //添加
			
				if(!Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
					if(   checkSn( goods.get("sn").toString() ) ==1 ){
						throw new RuntimeException("商品货号" +goods.get("sn") +"已存在" );
					}
				}
				else{
//					if(   checkSku( goods.get("sku").toString() ) ==1 ){
//						throw new RuntimeException("商品货号" +goods.get("sku") +"已存在" );
//					}
				}
				
			}else{
				
					try{
						String goods_id = (String)goods.get("goods_id");
						
//						if(   checkSn( goods.get("sn").toString(),goods_id ) ==1 ){
//							throw new RuntimeException("商品货号" +goods.get("sn") +"已存在" );
//						}
						
					}
					catch(NumberFormatException e){
						throw new RuntimeException("商品id格式错误");
					}
				
			}
			
			return goods.get("sn").toString();
		 
		}
		
			
		String sn = "G" + DateUtil.toString( new Date(System.currentTimeMillis()) ,"yyyyMMddhhmmss" )+StringUtil.getRandStr(4);
		if(Consts.ECS_SOURCE_FROM.equals(ManagerUtils.getSourceFrom())){
			sn = "";
		}
		return sn;
	}

	
	/**
	 * 检测货号是否已经存在
	 * 
	 * @param sn
	 * @return 如果存在返回1 ，如果不存在返回0
	 */
	private int checkSn(String sn) {
		String sql = SF.goodsSql("NUM_GET_COUNT");
		int count = this.baseDaoSupport.queryForInt(sql, sn);
		count = count > 0 ? 1 : 0;
		return count;
	}
	
	private int checkSku(String sku){
		String sql = SF.goodsSql("SKU_NUM_GET_COUNT");
		int count = this.baseDaoSupport.queryForInt(sql, sku);
		count = count > 0 ? 1 : 0;
		return count;
	}

	/**
	 * 用于修改时检测
	 * 
	 * @param sn
	 * @param goods_id
	 * @return
	 */
	private int checkSn(String sn, String goods_id) {
		String sql = SF.goodsSql("NUM_GET_COUNT_BY_ID");
		int count = this.baseDaoSupport.queryForInt(sql, sn, goods_id);
		count = count > 0 ? 1 : 0;
		return count;
	}

	@Override
	public String getAuthor() {
		return "kingapex";
	}

	@Override
	public String getId() {
		return "goods.sn_creator";
	}

	@Override
	public String getName() {

		return "默认商品货号生成插件";
	}

	@Override
	public String getType() {
		return "";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void perform(Object... params) {

	}

}