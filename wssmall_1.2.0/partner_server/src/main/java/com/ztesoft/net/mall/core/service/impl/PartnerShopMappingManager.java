package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.model.PartnerShopMapping;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.blog.IStoreProcesser;
import com.ztesoft.net.framework.blog.StoreProcesser;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IPartnerManager;
import com.ztesoft.net.mall.core.service.IpartnerShopMappingManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class PartnerShopMappingManager extends BaseSupport<PartnerShopMapping> implements IpartnerShopMappingManager{

	IPartnerManager partnerManager;
	public String db_table_name(){
		return this.getTableName("partner_shop_mapping");
	}

	
	//列表
	@Override
	public Page list(PartnerShopMapping obj, int page, int pageSize) {
		
		String userid=ManagerUtils.getUserId();
		ArrayList arr=new ArrayList();
		 
		String sql="select a.partner_id,a.shop_code, a.shop_name,(select p.pname from  es_dc_public p where p.pkey=a.shop_type and p.stype='8027' and p.source_from = '" 
		+ ManagerUtils.getSourceFrom() + "' ) shop_type, a.create_date, a.shop_desc from ";
			   
        String whereSql= " es_partner_shop_mapping a left join es_partner p on p.partner_id=a.partner_id and p.source_from = '" + ManagerUtils.getSourceFrom() + "' left join es_adminuser u " +
        		" on u.userid=p.userid and u.source_from = '" + ManagerUtils.getSourceFrom() + "'"+
	    		" where u.userid='"+userid+"' and p.sequ=0 and a.source_from = '" + ManagerUtils.getSourceFrom() + "'";//and p.state=1 
       
		if(obj!=null){
			
			if(obj.getShop_name()!=null && obj.getShop_name().trim().length()>0){
				whereSql+=" and a.shop_name like ? ";
				arr.add(obj.getShop_name().trim()+"%");
			}
		}
        
		sql+=whereSql;
		sql+=" order by a.create_date desc ";
		String countSql = "select count(*) from "+whereSql;
	    Page webpage = this.daoSupport.queryForCPage(sql, page, pageSize,PartnerShopMapping.class,countSql,arr.toArray());
	    
	  return webpage;
	}
	
	@Override
	public Map getUserInfoByShopCode(String shopCode){
		String sql = "select a.partner_id,a.shop_code,a.shop_name,a.shop_type,a.create_date," +
				"a.ip,b.userid from es_partner_shop_mapping a, es_partner b where a.partner_id = b.partner_id and a.shop_code = ? " +
				"and b.state = '1' and b.sequ = 0";
		return this.baseDaoSupport.queryForMap(sql, shopCode);
	}
	
	
	@Override
	public Map getUserShopingInfoByUserId(String userid){
		String sql = "select a.partner_id,a.shop_code,a.shop_name,a.shop_type,a.create_date," +
				"a.ip,b.userid from es_partner_shop_mapping a, es_partner b where a.partner_id = b.partner_id and b.userid = ? " +
				"and b.state = '1' and b.sequ = 0";
		return this.baseDaoSupport.queryForMap(sql, userid);
	}
	
	
	@Override
	public  PartnerShopMapping getParnterShopMappingByShopCode(String shop_code){
		String sql=" select * from  es_partner_shop_mapping where shop_code = ?";
		PartnerShopMapping  partnerShopMapping =  this.baseDaoSupport.queryForObject(sql, PartnerShopMapping.class, shop_code);
		if(partnerShopMapping!=null){
			IStoreProcesser  netBlog  = null;
			String obj = partnerShopMapping.getShop_desc();
	        if(null != obj && !"".equals(obj)){
	        	netBlog = StoreProcesser.getProcesser("es_goods_plantform_info","other_intors",partnerShopMapping.getSource_from(),obj);
	    	}
			obj = netBlog.getFileUrl(obj);
			partnerShopMapping.setShop_desc(obj);
		}
		return partnerShopMapping;
	}
	@Override
	public void add(PartnerShopMapping obj){
		obj.setCreate_date(Consts.SYSDATE);
		obj.setPartner_id(partnerManager.currentLoginPartnerId());
		this.baseDaoSupport.insert(this.db_table_name(), obj);
	}
	@Override
	public int delete(String shop_code){
		
		String partner_id=partnerManager.currentLoginPartnerId();
		
		if(shop_code==null || shop_code.equals("")){
			return 0;
		}else{
			String sql="delete from "+db_table_name()+" where shop_code in ("+shop_code+") and partner_id='"+partner_id+"' ";
			this.baseDaoSupport.execute(sql);
			return 1;
		}
	}
	
	@Override
	public List listShopType(){
		
		String sql = "select p.pkey shop_type,p.pname shop_type_name from es_dc_public p where p.stype=8027";
		
		return this.baseDaoSupport.queryForList(sql);
	}


	public IPartnerManager getPartnerManager() {
		return partnerManager;
	}


	public void setPartnerManager(IPartnerManager partnerManager) {
		this.partnerManager = partnerManager;
	}
	
}