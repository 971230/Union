package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ztesoft.net.app.base.core.model.PartnerAddress;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IPartnerAddressManager;
import com.ztesoft.net.mall.core.service.IPartnerManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
/**
 * 
 * 分销商地址
 * @author dengxiuping
 *
 */
public class PartnerAddressManager extends BaseSupport<PartnerAddress> implements IPartnerAddressManager{
	
	private IPartnerManager partnerManager;
	
	public String db_table_name(){
		return this.getTableName("partner_address");
	}

	
	//列表
	@Override
	public Page list(PartnerAddress obj, int page, int pageSize) {

		String userid=ManagerUtils.getUserId();
		ArrayList arr=new ArrayList();
		String sql="select a.addr_id,a.partner_id,a.name,a.addr,a.zip,a.tel,a.mobile,a.def_addr from ";
			   
        String whereSql= " es_partner_address a left join es_partner p on p.partner_id=a.partner_id and p.source_from = a.source_from left join es_adminuser u " +
        		" on u.userid=p.userid and u.source_from = p.source_from "+
	    		" where u.userid='"+userid+"' and p.sequ=0 and a.source_from = '" + ManagerUtils.getSourceFrom() + "' ";//and p.state=1
       
		if(obj!=null){
			
			if(obj.getName()!=null && obj.getName().trim().length()>0){
				whereSql+=" and a.name like ? ";
				arr.add(obj.getName()+"%");
			}
			if(obj.getMobile()!=null && obj.getMobile().trim().length()>0){
				whereSql+=" and a.mobile like ? ";
				arr.add(obj.getMobile()+"%");
			}
		}
		 sql+=whereSql;
		
		sql+=" order by a.partner_id desc ";
		String countSql = "select count(*) from "+whereSql;
	    Page webpage = this.baseDaoSupport.queryForCPage(sql, page, pageSize,PartnerAddress.class,countSql,arr.toArray());
	    
	  return webpage;
	}
	
	/**
	 * 根据id得到对象
	 */
	@Override
	public PartnerAddress getPartnerAddrByAddrId(String id){
		String sql="select * from es_partner_address where addr_id=? ";
		return this.baseDaoSupport.queryForObject(sql, PartnerAddress.class, id);
	}
	@Override
	public PartnerAddress getPartnerAddressByAddr_id(String id){
		String sql="select * from es_partner_address where addr_id=? ";
		return this.baseDaoSupport.queryForObject(sql, PartnerAddress.class, id);
	}
	//根据id得到对象
	@Override
	public PartnerAddress getPartnerAddressByParId(String id){
		String sql="select * from "+db_table_name()+" where partner_id=? ";
		return this.baseDaoSupport.queryForObject(sql, PartnerAddress.class, id);
	}
	//通过id获取列表
	@Override
	public List<PartnerAddress> getPartnerAddress(String id) {
		String sql="select * from es_partner_address where partner_id=? order by addr_id desc ";
		return this.baseDaoSupport.queryForList(sql, PartnerAddress.class, id);
	}
	//删除
	@Override
	public int deleteAddr(String id){
		if(id==null || id.equals("")){
			return 0;
		}else{
			String sql="delete from es_partner_address where addr_id in ("+id+") ";
			this.baseDaoSupport.execute(sql);
			return 1;
		}
	}
	//插入
	@Override
	public int add(PartnerAddress paraddr) {
		paraddr.setPartner_id(partnerManager.currentLoginPartnerId());
		paraddr.setDef_addr(Consts.DEFAULT_ADDR);
		this.baseDaoSupport.insert(db_table_name(), paraddr);
		return 1;
	}
	//修改
	@Override
	public int edit(PartnerAddress partner){
		
		this.baseDaoSupport.update(db_table_name(), partner, "addr_id="+ partner.getAddr_id());
		return 1;
	}


	public IPartnerManager getPartnerManager() {
		return partnerManager;
	}


	public void setPartnerManager(IPartnerManager partnerManager) {
		this.partnerManager = partnerManager;
	}
	
	

}
