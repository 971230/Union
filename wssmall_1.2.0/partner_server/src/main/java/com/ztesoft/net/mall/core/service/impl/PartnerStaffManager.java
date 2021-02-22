package com.ztesoft.net.mall.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.model.PartnerStaff;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.IPartnerStaffManager;
import com.ztesoft.net.sqls.SF;
/**
 * 
 * 分销商申请
 * @author dengxiuping
 *
 */
public class PartnerStaffManager extends BaseSupport<PartnerStaff> implements IPartnerStaffManager{
	
	public String db_table_name(){
		return this.getTableName("partner_staff");
	}
	//插入
	@Override
	public int add(PartnerStaff partner) {
		this.baseDaoSupport.insert(db_table_name(), partner);
		return 1;
	}
	//修改
	@Override
	public int edit(PartnerStaff partner){
		Map edit = new HashMap();
		/*editMem.put("lastlogin", member.getLastlogin());
		editMem.put("logincount", member.getLogincount());
		*/
		this.baseDaoSupport.update(db_table_name(), edit, "partner_id = "+ partner.getPartner_id());
		return 1;
	}
	//根据id得到对象
	@Override
	public PartnerStaff getPartnerStaffByParId(String id){
		String sql="select * from "+db_table_name()+" where partner_id=? ";
		return this.baseDaoSupport.queryForObject(sql, PartnerStaff.class, id);
	}
	//删除
	@Override
	public int delete(String id,String partner_id){
		if(id==null || id.equals("")){
			return 0;
		}else{
			String sql="delete from "+db_table_name()+" where staff_code in ("+id+") and partner_id = "+partner_id;
			this.baseDaoSupport.execute(sql);
			return 1;
		}
	}
	
	//列表
	public Page list(PartnerStaff obj, String order, int page, int pageSize) {
		
//		String currUserId=ManagerUtils.getUserId();
//		ArrayList partm=new ArrayList();
//		order = order == null ? " m.partner_id desc" : order;
		String sql = "select m.* from "+db_table_name()+" m ";
		
		sql += " order by  " + order;
	    Page webpage = this.daoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}
    @Override
	public List list(String part_id){
    	String sql="select * from "+db_table_name()+" where partner_id=? order by staff_code asc ";
    	return this.baseDaoSupport.queryForList(sql, PartnerStaff.class, part_id);
    }
    
    /**
     * 根据当前行业用户组的id生成 行业用户编码
     * 异常则返回空自行填写
     * @param parter_id
     * @return
     */
    @Override
	public String generateStaffCode(String parter_id){
    	String staff_code = "";
    	try{
    		String sql = SF.partnerSql("PARTNER_STAFF_CODE");
        	Integer count = this.baseDaoSupport.queryForInt(sql, parter_id);
        	staff_code = String.valueOf(count + 1);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return staff_code;
    }
    
    /**
     * 根据当前行业用户组的id和用户编码查找行业用户
     * @param parter_id, staff_code
     * @return PartnerStaff
     */
    @Override
	public PartnerStaff getPartnerStaffByIdAndCode(String partnerId, String staff_code){
    	PartnerStaff partnerStaff = null;
    	try{
    		String sql = SF.partnerSql("QRY_PARTER_STAFF_BY_ID_CODE");
    		partnerStaff = this.baseDaoSupport.queryForObject(sql, PartnerStaff.class, partnerId, staff_code);
    	}catch(Exception e){
    		partnerStaff = new PartnerStaff();
    		e.printStackTrace();
    	}
    	return partnerStaff;
    }
    
    @Override
	public void update(PartnerStaff partner){
    	String sqlWhere = " partner_id = '" + partner.getPartner_id() + "' " +
    			"and staff_code = '" + partner.getStaff_code() + "'";
    	this.baseDaoSupport.update(db_table_name(), partner, sqlWhere);
	}
}
