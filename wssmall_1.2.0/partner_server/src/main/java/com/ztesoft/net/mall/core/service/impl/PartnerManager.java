package com.ztesoft.net.mall.core.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import params.adminuser.req.AdminAddReq;
import params.adminuser.req.AdminUserReq;
import params.adminuser.resp.AdminUserResp;
import params.org.req.OrgReq;
import params.req.PartnerWdAddReq;
import params.resp.PartnerWdAddResp;
import services.AdminUserInf;
import zte.params.req.GetPartnerReq;
import zte.params.resp.GetPartnerResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.model.PartnerShop;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.desposit.IDespostHander;
import com.ztesoft.net.mall.core.action.desposit.model.AgentDepositRespDto;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.GoodsUsage;
import com.ztesoft.net.mall.core.service.IPartnerManager;
import com.ztesoft.net.mall.core.service.IPartnerShopManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import commons.CommonTools;

import consts.ConstsCore;
/**
 * 
 * 分销商申请
 * @author dengxiuping
 *
 */
public class PartnerManager extends BaseSupport<Partner> implements IPartnerManager{
	
	private IPartnerShopManager partnerShopManager;
	protected IDespostHander despostHander;
	private AdminUserInf adminUserServ;
	
	private String sqlstr="(select p.pname from es_dc_public p where p.pkey=m.shop_type and p.stype='8007' and m.source_from = p.source_from and p.source_from = '" + ManagerUtils.getSourceFrom() + "' ) shop_type_desc " ;
	
	//to_date(to_char(m.exp_date,'yyyy-MM-dd'),'yyyy-MM-dd')
	//to_date(to_char("+DBTUtil.current()+",'yyyy-MM-dd'),'yyyy-MM-dd')
	//DBTUtil.to_sql_date(DBTUtil.to_char(DBTUtil.current(), 1), 1);
	private StringBuffer selectSql=new StringBuffer(
		"select " +
		     "(select u.username from es_adminuser u where u.userid=m.userid) username ," +
		     "(case when "+DBTUtil.to_sql_date(DBTUtil.to_char("m.exp_date", 1), 1)+"-"+DBTUtil.to_sql_date(DBTUtil.to_char(DBTUtil.current(), 1), 1)+" <=30 then '0' else '1' end ) edflag,"+
			"m.partner_id,m.partner_code,m.score,m.partner_name,m.need_amount,m.service_cash,m.deposit/100 deposit,m.frost_deposit/100 frost_deposit," +
			 "m.partner_type,m.partner_level,m.partner_cat,"+
			"m.invoice_cash,m.parent_userid,m.legal_name,m.legal_id_card, " +
			"m.legal_phone_no, m.address, m.linkman, m.phone_no,m.email, m.buss_license, m.eff_date, m.exp_date,m.create_date,m.userid,m.state,m.sequ ");
	
	
	private StringBuffer selectDistSql=new StringBuffer("select distinct s.last_update_date s_last_date,m.last_update_date m_last_date,s.sequ s_sequ,m.sequ m_sequ,m.partner_id,m.partner_code,m.score,m.partner_name,m.need_amount,m.service_cash," +
			"m.partner_cat,m.invoice_cash,m.parent_userid,m.legal_name,m.legal_id_card, m.legal_phone_no, m.address, m.linkman,m.email," +
			"m.phone_no, m.buss_license, m.eff_date, m.exp_date,m.create_date,m.userid,m.state," +
			"(select p.pname from  es_dc_public p where p.pkey=m.shop_type and p.stype='8007' and p.source_from = '" + ManagerUtils.getSourceFrom() + "' ) shop_type_desc  " );
	
	
	private StringBuffer whereSql=new StringBuffer(" from es_partner m " +
			" left join es_adminuser u " +
			" on  m.userid=u.userid " );
	
	private StringBuffer whereDistSql=new StringBuffer(" from es_partner m,es_adminuser u,es_partner_shop s where m.userid=u.userid and m.partner_id=s.partner_id and m.state=1 and" +
			" m.source_from = u.source_from and u.source_from = s.source_from and m.source_from = '" + ManagerUtils.getSourceFrom() +"' " );
	
	private StringBuffer wherePSql=new StringBuffer(" from es_partner m ");
	
	
	private StringBuffer auditresonSql=new StringBuffer("select m.partner_id,m.partner_code,m.partner_name ,m.last_update_date m_last_date,s.last_update_date s_last_date," +
			"m.sequ m_sequ, s.sequ s_sequ,m.audit_idea m_audit,s.audit_idea s_audit " +
			" from es_partner m,es_partner_shop s where m.partner_id=s.partner_id and m.state=1 ");
	
	public String db_table_name(){
	   return this.getTableName("partner");	
	}
	@Override
	public String getDcpublicName(String type,String key){
		return this.baseDaoSupport.queryForString("select p.pname from  es_dc_public p where p.pkey='"+key+"' and p.stype='"+type+"'");
	}
	@Override
	public String getPartnerLevel(Partner partner){
		return this.getDcpublicName("8005", partner.getPartner_level());
	}
	/**
	 * 是否已操作
	 */
	@Override
	public boolean isSaveExits(String partnerId,String state,Integer sequ){
		StringBuffer sb=new StringBuffer(1000);
		sb.append("select p.sequ,p.state,p.partner_id, p.partner_code ");
		sb.append(" from es_partner p");
		sb.append(" where");
		sb.append("  p.partner_id=? ");
		if(state!=null){
			sb.append(" and p.state="+state);
		}
		if(sequ!=null){
			sb.append(" and p.sequ="+sequ);
		}
		List list=this.daoSupport.queryForList(sb.toString(),partnerId);
		sb.delete(0,sb.length());
		return list.size()>0?true:false;
	}
	/**
	 * 是否有在途业务
	 */
	@Override
	public boolean isFieldAuditIngExits(String partnerId){
		StringBuffer sb=new StringBuffer(1000);
		//by liqingyi 原有的报异常:Caused by: java.sql.SQLException: ORA-00918: 未明确定义列
//		sb.append("select p.sequ,s.sequ,p.state,s.state,p.partner_id, p.partner_code ");
//		sb.append(" from es_partner p,es_partner_shop s ");
//		sb.append(" where");
//		sb.append(" p.partner_id=s.partner_id and p.partner_id=? and p.state=1 and s.state=1");
//		sb.append(" and (p.sequ =-1 or s.sequ=-1)");
		sb.append("select p.sequ, p.state, p.partner_id, p.partner_code ");
		sb.append(" from es_partner p ");
		sb.append(" inner join es_partner_shop s on");
		sb.append(" p.partner_id=s.partner_id and p.partner_id=? and p.state=1 and s.state=1");
		sb.append(" and (p.sequ =-1 or s.sequ=-1)");
		List list=this.daoSupport.queryForList(sb.toString(),partnerId);
		sb.delete(0,sb.length());
		return list.size()>0?true:false;
	}
	@Override
	public boolean isPartnerCodeAndNameExits(String code,String name){
		String sql="select partner_id, partner_code from "+db_table_name()+" where partner_code=? and partner_name=? ";
		List partm=new ArrayList();
		partm.add(0, code);
		partm.add(1, name);
		List list=this.daoSupport.queryForList(sql, partm.toArray());
		return list.size()>0?true:false;
	}
	@Override
	public boolean isPartnerExits(String name, String code){
		if(!StringUtil.isEmpty(name)){
			String sql="select partner_id, partner_name from "+db_table_name()+" where partner_name=? ";
			List list=this.daoSupport.queryForList(sql, name);
			return list.size()>0?true:false;
		}else{
			String sql="select partner_id, partner_name from "+db_table_name()+" where partner_code=? ";
			List list=this.baseDaoSupport.queryForList(sql, code);
			return list.size()>0?true:false;
		}
		
	}
	
	/**
	 * 当前登录用户 对应的分销商id
	 */
	@Override
	public String currentLoginPartnerId(){
		
		String uid=ManagerUtils.getUserId();
		String sql="select distinct p.partner_id from es_adminuser a,es_partner p where " +
				"a.source_from = p.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + 
				"' and a.userid=p.userid and a.userid='"+uid+"'";
		return this.baseDaoSupport.queryForString(sql);
		
	}
	
	@Override
	public int getMaxPartnerSequ(Partner r){
		String sql="select max(a.sequ)  from partner a where a.partner_id=? ";
		int maxsequ=this.baseDaoSupport.queryForInt(sql, r.getPartner_id());
		return maxsequ;
	}
	@Override
	public int getMaxPartnerSequ(String partnerid){
		String sql="select max(a.sequ)  from partner a where a.partner_id=? ";
		int maxsequ=this.baseDaoSupport.queryForInt(sql, partnerid);
		return maxsequ;
	}
	
	/**
	 *一般浏览 多表 权限sql 电信看一级 ，一级看自己和下级，二级看自己
	 */
	@Override
	public String appendSql(){
		
		String userid=ManagerUtils.getUserId();
		boolean is_super=ManagerUtils.isNetStaff();
		boolean is_first=ManagerUtils.isFirstPartner();
		boolean is_secound=ManagerUtils.isSecondPartner();
		String sql="";
		if(is_secound){
			sql=" and (u.userid='"+userid+"')";
			
		}else if(is_first){
			sql=" and (u.userid='"+userid+"' or u.userid in (select userid from es_adminuser uu where uu.paruserid='"+userid+"'))";
		}else{
			sql=" and u.founder="+Consts.CURR_FOUNDER_3+" ";
		}
		return sql;
		
	}
	/**
	 * 单表查询 一般浏览 权限sql 电信看一级 ，一级看自己和下级，二级看自己
	 */
	@Override
	public String appendSql2(){
		
		String userid=ManagerUtils.getUserId();
		boolean is_first=ManagerUtils.isFirstPartner();
		boolean is_secound=ManagerUtils.isSecondPartner();
		String sql="";
		if(is_secound){
			sql=" and m.userid='"+userid+"' ";
		}else if(is_first){
//			sql=" and (m.parent_userid='"+userid+"' or m.userid='"+userid+"') ";
			sql ="and ( exists (select 1  from es_adminuser adm where adm.relcode = m.partner_id and (adm.paruserid = '"+userid+"' or adm.userid = '"+userid+"')) or (m.parent_userid='"+userid+"' or m.userid='"+userid+"') )";
		}
//		else{
//			sql=" and m.parent_userid is null ";
//		}
		//add by wui
		
		return sql;
		
	}
	/**
	 * 浏览权限sql 电信看一级 ，一级看下级 审核变更资料
	 * @return
	 */
	public String appendAuditSql(){
		
		String userid=ManagerUtils.getUserId();
		boolean is_super=ManagerUtils.isNetStaff();
		boolean is_first=ManagerUtils.isFirstPartner();
		boolean is_secound=ManagerUtils.isSecondPartner();
		String sql="";
		if(is_secound){
			sql=" and 1=2 ";
			
		}else if(is_first){
			sql=" and u.userid in (select userid from es_adminuser uu where uu.paruserid='"+userid+"') ";
		}else{
			sql=" and u.founder="+Consts.CURR_FOUNDER_3+" ";
		}
		return sql;
		
	}
    @Override
	public String appendParentUseridSql(){
		
		String userid=ManagerUtils.getUserId();
//		boolean is_super=ManagerUtils.isNetStaff();
		boolean is_first=ManagerUtils.isFirstPartner();
//		boolean is_secound=ManagerUtils.isSecondPartner();
		String sql="";
		if(is_first){
			sql=" and m.parent_userid='"+userid+"' ";
			//获取当前的登录
			AdminUserReq adminUserReq = new AdminUserReq();
			adminUserReq.setUser_id(CommonTools.getUserId());
			
			AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
			AdminUser user = new AdminUser();
			if(adminUserResp != null){
				user = adminUserResp.getAdminUser();
				if(user!=null&&user.getFounder()==3){
					sql = "and ( m.parent_userid='"+userid+"' or m.partner_id ='"+user.getRelcode()+"') ";
				}
			}
		}
		
		return sql;
		
	}
	
	/**   ++++++++++++++++++++++++++++获取对象 ++++++++++++++++++++++ */
   
	/**
	 * 获取正常有效的分销商资料
	 */
	@Override
	public Partner getPartnerSequ0AndState1(String id){
		String sql="select * from "+db_table_name()+" where partner_id=? and sequ=0 and state='1' ";
		return this.baseDaoSupport.queryForObject(sql, Partner.class, id);
	}
	/**
	 * 获取待审核还未生效的分销商资料
	 */
	@Override
	public Partner getPartnerSequM1AndState0(String id){
		String sql="select * from "+db_table_name()+" where partner_id=? and sequ=-1 and state='0' ";
		return this.baseDaoSupport.queryForObject(sql, Partner.class, id);
	}
	/**
	 * 获取正常有效的分销商资料
	 */
	@Override
	public Partner getPartner(String id){
		String sql="select * from "+db_table_name()+" where partner_id=? and sequ=0 and state='1' ";
		return this.baseDaoSupport.queryForObject(sql, Partner.class, id);
	}
	@Override
	public void updateUserid(String partnerId,String userid){
		String sql="update es_partner set userid=? where partner_id=? ";
		ArrayList partm=new ArrayList();
		partm.add(userid);
		partm.add(partnerId);
		this.baseDaoSupport.execute(sql, partm.toArray());
	}
	/**
	 * 根据id得到对象
	 */
	@Override
	public Partner getPartner(String id,Integer sequ,String state){
		String sql="select * from "+db_table_name()+" where partner_id=? ";
		if(sequ!=null){
			sql+=" and sequ="+sequ+" ";
		}
		if(state!=null){
			sql+=" and state="+state+" ";
		}
		Partner partner =  this.baseDaoSupport.queryForObject(sql, Partner.class, id);
		if(null != partner){
			if(!StringUtil.isEmpty(partner.getUserid()))
				getPartnerByCurrentUserId(partner.getUserid(),partner);
		}
		return partner;
	}
	
	// 没办法 so ugly
	@Override
	@SuppressWarnings("unchecked")
	public Partner getPartnerByCurrentUserId(String userId,Partner partner ){

//		if(true)
//			 return partner;
		
		String sql = "select a.partner_id,a.partner_name,a.partner_type,a.need_amount,a.service_cash," +
				"a.invoice_cash,a.parent_userid,a.legal_name,a.legal_id_card,a.legal_phone_no,a.address,a.linkman,buss_license_ads,tax_number," +
				"a.phone_no,a.buss_license,a.state,a.eff_date,a.exp_date,a.apply_reason,a.comments,a.create_date," +
				"a.state_date,a.partner_code,a.partner_level,a.partner_cat,a.shop_type,nvl(b.account_amount,0)/100 deposit,nvl(b.frost_deposit,0)/100 frost_deposit,a.userid, " +
				"(select t.username from es_adminuser t where t.state = '1' and t.source_from = a.source_from and t.userid = a.userid)username," +
				"a.sequ,a.image_file,a.image_default,a.audit_idea,a.block_reason,a.block_detail,a.renew_reason,a.last_update_date," +
				"a.score,a.email from ES_PARTNER a,ES_PARTNER_ACCOUNT b where a.partner_id = b.partner_id(+) and b.account_type = '00A' and a.userid = ?" +
				" and a.sequ = 0 and a.source_from = b.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "' ";
		
		if(null != partner && null != partner.getPartner_id() && !"".equals(partner.getPartner_id())){
			sql += " and a.partner_id = '" + partner.getPartner_id() + "'";
		}
		Partner par=this.daoSupport.queryForObject(sql, Partner.class, userId);
		if(par == null){
			par = new Partner();
		}
		AdminUserReq adminUserReq = new AdminUserReq();
		adminUserReq.setUser_id(userId);
		
		AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
		AdminUser user = new AdminUser();
		if(adminUserResp != null){
			user = adminUserResp.getAdminUser();
		}
		if(user == null || StringUtil.isEmpty(user.getUserid()))
			return par;
		
		
		
		if(Consts.CURR_FOUNDER_3.equals(user.getFounder() + "")){
			try {
				
				Map agent = new HashMap();
				agent.put("requestTime", StringUtil.getTransDate());
				agent.put("requestId", StringUtil.getTransDate() + (int)(Math.random()*10000));
				
				OrgReq orgReq = new OrgReq();
				orgReq.setStaff_code(user.getRelcode());
//				Map agenInfo = adminUserServ.getAgentOrgInfo(orgReq).getOrgInfo();
//				agent.put("unionOrgCode",agenInfo.get("union_org_code").toString());
				//agent.put("unionOrgCode","7310118222");
				AgentDepositRespDto retObj = null;
				
				String sqlMoney = "select nvl(sum(b.account_amount), 0)/100 deposit from es_partner a, " +
						"es_partner_account b where a.parent_userid = ? " +
						"and a.partner_id = b.partner_id(+) and b.account_type = '00A' " +
						"and a.sequ = 0 and a.source_from = '" + ManagerUtils.getSourceFrom() + "'";
				
				int childMoney = this.baseDaoSupport.queryForInt(sqlMoney, user.getUserid());
				
				if(null != retObj && retObj.getResult().equals("0")){
					int banlence = Integer.parseInt(retObj.getBalance())/100;
					par.setDeposit((banlence - childMoney));
				}else {
					par.setDeposit(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (partner == null) {
			partner = new Partner();
		}
		partner.setDeposit(par.getDeposit());
		partner.setUsername(par.getUsername());
		return par;
	}
	@Override
	@SuppressWarnings("unchecked")
	public Partner getPartnerByCurrentUserId2(String userId){
		String sql = "select p.* from es_partner p where p.sequ=0 and p.state=1 and p.userid=?";
		return this.daoSupport.queryForObject(sql, Partner.class, userId);
	}
	/**
	 * 根据id得到对象 （）
	 */
	@Override
	public Partner getPartnerById(String id){
		String sql="select * from "+db_table_name()+" where partner_id=? ";
		return this.baseDaoSupport.queryForObject(sql, Partner.class, id);
	}
	@Override
	public Partner getPartnerIdByUserId(String uId){
		String sql="select  * from es_partner a where a.userid='"+uId+"' p.sequ=0   ";
		List list=this.daoSupport.queryForList(sql);
		Partner partner= (Partner) list.get(0);
		return partner;
		
	}
	
//	/**
//	 * 
//	 * @function 根据userId获取分销商信息
//	 */
//	public Partner getPartnerByUserId(String userId){
//		
//		String sql = "select p.* from es_adminuser a,es_partner p where a.userid=p.userid and a.userid=? and p.sequ=0 ";
//		return this.baseDaoSupport.queryForObject(sql, Partner.class, userId);
//	}
	
	/**+++++++++++++++++++++++ 保存 审核，冻结，恢复, 修改 删除  ++++++++++++++++++++++= */
	/**
	 * 审核
	 * @return
	 */
	@Override
	public int saveAuditPartner(Partner partner){
		
		Partner old=this.getPartnerSequM1AndState0(partner.getPartner_id()); //基本信息
		
		if(old!=null){
			
			old.setLast_update_date(Consts.SYSDATE);
			old.setState(partner.getState());
			old.setAudit_idea(partner.getAudit_idea());
			
			if(partner.getState().equals(Consts.PARTNER_STATE_NORMAL)){//通过
				old.setSequ(Consts.PARTNER_SEQ_0);//0
			}else{//不通过
				old.setSequ(Consts.PARTNER_SEQ_W2);//-2
			}
			this.editPartner(old);
			
			if(old.getState().equals(Consts.PARTNER_STATE_NORMAL)){
				//为预存金开户
				if(despostHander.getAccountById(partner.getPartner_id())==null){
					despostHander.openAccount(partner.getPartner_id());//预存金开户
				}
			}
			
		}
		
		
		return  1;
	}
	/**
	 * 审核变更字段
	 * @return
	 */
	@Override
	public int saveAuditAlterPartner(Partner partner){
		
		String state=Consts.PARTNER_STATE_NORMAL;
		int sequ= Consts.PARTNER_SEQ_W1;
		
		
		//基本资料
		Partner old=this.getPartner(partner.getPartner_id(),sequ, null); //基本信息
		PartnerShop oldshop=partnerShopManager.getPartnerShop(partner.getPartner_id(), null,sequ); //基本信息
		if(old==null && oldshop==null){
			return 0;
		}
		Partner old_0=this.getPartner(partner.getPartner_id(),Consts.PARTNER_SEQ_0, null); //为0的那条数据 审核通过后sequ改为最大值
		if(old!=null && old_0!=null){
			
			old.setLast_update_date(Consts.SYSDATE);
			old.setAudit_idea(partner.getAudit_idea());
			
			if(partner.getSequ().equals(Consts.PARTNER_SEQ_0)){//通过
				old.setSequ(Consts.PARTNER_SEQ_0);//0
				old_0.setSequ(getMaxPartnerSequ(old)+1);
				this.editPartner(old_0,null,Consts.PARTNER_SEQ_0);
			}else{//不通过
				old.setSequ(Consts.PARTNER_SEQ_W2);//-2
			}
			this.editPartner(old,null,sequ);
			
		}
		
		
		
		//网店资料
		
		PartnerShop oldshop_0=partnerShopManager.getPartnerShop(partner.getPartner_id(), null,Consts.PARTNER_SEQ_0);
		if(oldshop!=null && oldshop_0!=null){
			
			oldshop.setLast_update_date(Consts.SYSDATE);
			oldshop.setAudit_idea(partner.getAudit_idea());
			
			if(partner.getSequ().equals(Consts.PARTNER_SEQ_0)){//通过
				oldshop.setSequ(Consts.PARTNER_SEQ_0);//0
				oldshop_0.setSequ(partnerShopManager.getMaxPartnerShopSequ(oldshop)+1);
				partnerShopManager.editPartnerShop(oldshop_0,null,Consts.PARTNER_SEQ_0);//0改为最大值
			}else{//不通过
				oldshop.setSequ(Consts.PARTNER_SEQ_W2);//-2
			}
			
			partnerShopManager.editPartnerShop(oldshop,null,sequ);//-1改为0
			
		}
		
		
		return  1;
	}
	/**
	 * 解冻分销商业务
	 */
	@Override
	public int editGoodsUsageOpen(String userid,String [] goodsid,String block_reason){
		GoodsUsage go=new GoodsUsage();
		go.setState("0");//可用
		go.setBlock_reason(block_reason);
		go.setState_date(Consts.SYSDATE);
		Map map=ReflectionUtil.po2Map(go);
		
		this.baseDaoSupport.update("es_goods_usage", map, " userid='"+userid+"' and goods_id in("+this.arrToString(goodsid)+") and state='1' ");
		return 1;
	}
	/**
	 * 冻结分销商业务
	 */
	@Override
	public int editGoodsUsage(String userid,String [] goodsid,String block_reason){
		GoodsUsage go=new GoodsUsage();
		go.setState("1");//冻结
		go.setBlock_reason(block_reason);
		go.setState_date(Consts.SYSDATE);
		Map map=ReflectionUtil.po2Map(go);
		
		this.baseDaoSupport.update("es_goods_usage", map, " userid='"+userid+"' and goods_id in("+this.arrToString(goodsid)+") and state='0' ");
		return 1;
	}
	/**
	 * 冻结
	 * @return
	 */
	@Override
	public int saveBlockPartner(Partner partner){
		
		Partner old=this.getPartner(partner.getPartner_id()); //基本信息
		
		if(old!=null){
			//备份
			old.setSequ(this.getMaxPartnerSequ(old)+1);//最大值加1
			this.baseDaoSupport.insert(db_table_name(),old);
			
			old.setState(Consts.PARTNER_STATE_CONGELATION);
			old.setBlock_detail(partner.getBlock_detail());
			old.setBlock_reason(partner.getBlock_reason());
			old.setSequ(Consts.PARTNER_SEQ_0);//
			old.setLast_update_date(Consts.SYSDATE);
			
			this.editUpdatePartner(ReflectionUtil.po2Map(old),old.getPartner_id());
		}
		
		
		return  1;
	}
	/**
	 * 恢复
	 * @return
	 */
	@Override
	public int saveRenewPartner(Partner partner){
		
		Partner old=this.getPartner(partner.getPartner_id(),Consts.PARTNER_SEQ_0,Consts.PARTNER_STATE_CONGELATION);
		
		if(old!=null){
			//备份
//			old.setSequ(this.getMaxPartnerSequ(old)+1);//最大值加1
//			this.baseDaoSupport.insert(db_table_name(),old);
			
//			old.setState(Consts.PARTNER_STATE_APPLY);//0
//			old.setSequ(Consts.PARTNER_SEQ_W1);//-1
			old.setState(Consts.PARTNER_STATE_NORMAL);//1
			old.setSequ(Consts.PARTNER_SEQ_0);//0
			old.setRenew_reason(partner.getRenew_reason());
			old.setLast_update_date(Consts.SYSDATE);
			this.editPartner(old, Consts.PARTNER_STATE_CONGELATION, Consts.PARTNER_SEQ_0);
		}
		
		
		return  1;
	}
	public void bakOldPartnerSequ0(Partner old,String setState,Integer setSequ){
		if(old!=null){
			//备份
			if(setSequ!=null){
				old.setSequ(setSequ);//设置sequ=0 变更前数据
			}
			if(setState!=null){
				old.setState(setState);
			}
			old.setLast_update_date(Consts.SYSDATE);
			this.baseDaoSupport.insert(db_table_name(),old);
			
			
		}
	}
	/**
	 * 备份一条记录
	 * @param partner 新的数据
	 * @param setState设置分销商状态
	 * @param setSequ设置数据状态
	 * @param old 旧的分销商数据
	 * @return 新的报存数据
	 */
	@Override
	public Partner bakPartner(Partner old,String setState,Integer setSequ,Partner partner){
		if(old!=null){
			
			if(setState!=null){
				partner.setState(setState);
			}
			if(setSequ!=null){
				partner.setSequ(setSequ);
			}
			
			partner.setLast_update_date(Consts.SYSDATE);
		}
		return partner;
	}
	@Override
	public void bakPartner(String partnerId,String setState,Integer setSequ){
		Partner old=getPartner(partnerId);
		if(old!=null){
			//备份
			old.setSequ(this.getMaxPartnerSequ(old)+1);//最大值加1
			old.setLast_update_date(Consts.SYSDATE);
			this.baseDaoSupport.insert(db_table_name(),old);
			
			if(setState!=null){
				old.setState(setState);
			}
			if(setSequ!=null){
				old.setSequ(setSequ);
			}
			
			old.setLast_update_date(Consts.SYSDATE);
			editPartner(old, Consts.PARTNER_STATE_NORMAL,Consts.PARTNER_SEQ_0 );
		}
	}
	
	/**
	 * 续签
	 */
	@Override
	public int saveKeeponPartner(Partner partner,Integer addMonths){
       
		Partner old=this.getPartner(partner.getPartner_id(),partner.getSequ(),partner.getState());
		
		if(old!=null){
			//备份
			old.setSequ(this.getMaxPartnerSequ(old)+1);//最大值加1
			this.baseDaoSupport.insert(db_table_name(),old);
			
			old.setImage_default(partner.getImage_default());
			old.setImage_file(partner.getImage_file());
			this.updateKeepon(old, addMonths);
			
			this.updateShopKeepon(old, addMonths);
		}
		return 1;
	}
	
	//删除
	@Override
	public int delete(String id){
		if(id==null || id.equals("")){
			return 0;
		}else{
			String sql="delete from "+db_table_name()+" where partner_id in ("+id+") ";
			String shopsql="delete from es_partner_shop where partner_id in ("+id+") ";
			String staff="delete from es_partner_staff where partner_id in ("+id+") ";
			this.baseDaoSupport.execute(sql);
			this.baseDaoSupport.execute(shopsql);
			this.baseDaoSupport.execute(staff);
			
			//记录删除日志
			String[] ids = id.split(",");
			for(int i=0;i<ids.length;i++){
				String logsql = "insert into es_partner_dellog(logid,partner_id,logtime,userid,source_from)values(";
				logsql += getES_PARTNER_DELLOG() +","+ids[i];
				logsql += ",sysdate,'"+ManagerUtils.getUserId()+"','"+ManagerUtils.getSourceFrom()+"') ";
				this.baseDaoSupport.execute(logsql);
			}
			return 1;
		}
	}
	private int getES_PARTNER_DELLOG(){
		String sql = "select max(logid) from es_partner_dellog ";
		int maxlogid = baseDaoSupport.queryForInt(sql, null);
		return  maxlogid+1;
	}
	/**
	 * 修改 待审核的分销商数据
	 */
	@Override
	public int editPartner(Partner partner){
		this.baseDaoSupport.update(db_table_name(), partner, " partner_id='"+partner.getPartner_id()+"' and state='0' and sequ=-1 ");
		return 1;
	}
	@Override
	public int editPartner(Partner partner,String state,Integer sequ){
		String sql=" partner_id='"+partner.getPartner_id()+"'";
		if(state!=null){
			sql+=" and state="+state+" ";
		}
		if(sequ!=null){
			sql+=" and sequ="+sequ+" ";
		}
		this.baseDaoSupport.update(db_table_name(), partner, sql);
		return 1;
	}
	public void updateKeepon(Partner partner,Integer addmonth){
		String sql="update es_partner p set p.exp_date="+DBTUtil.currentMonth("exp_date", addmonth)+",p.last_update_date="+DBTUtil.current()+",p.image_file='"+partner.getImage_file()+"',p.image_default='"+partner.getImage_default()+"' where p.partner_id='"+partner.getPartner_id()+"' and p.sequ=0";
	    this.baseDaoSupport.execute(sql);
	}
	public void updateShopKeepon(Partner partner,Integer addmonth){
		String sql="update es_partner_shop p set p.exp_date="+DBTUtil.currentMonth("exp_date", addmonth)+",p.last_update_date="+DBTUtil.current()+" where p.partner_id='"+partner.getPartner_id()+"' and p.sequ=0";
	    this.baseDaoSupport.execute(sql);
	}

	//修改正常状态的数据
	@Override
	public int editUpdatePartner(Map map,String parid){
		this.baseDaoSupport.update(db_table_name(), map, " partner_id='"+parid+"' and state='1' and sequ=0 ");
		return 1;
	}
	@Override
	public int countRowParByPid(String parid){
		return this.baseDaoSupport.queryForInt("select count(partner_id) from es_partner where partner_id =?", parid);
	}
	
	/**
	 * 后台添加方法 无需审核
	 * 
	 */
	@Override
	public int addNotAudit(Partner partner) {
	     String id=this.baseDaoSupport.getSequences("S_ES_PARTNER");
	     partner.setPartner_id(id);
	     partner.setCreate_date(Consts.SYSDATE);
	     partner.setState(Consts.PARTNER_STATE_NORMAL);//1
	     partner.setSequ(Consts.PARTNER_SEQ_0);//0
	     copyProperties(partner);
	     this.baseDaoSupport.insert(db_table_name(),partner);
	    
	     PartnerShop shop =new PartnerShop();
		 shop.setPartner_id(partner.getPartner_id());
		 shop.setSend_address(partner.getAddress());
		 shop.setExp_date(partner.getExp_date());
		 shop.setEff_date(partner.getEff_date());
		 shop.setLinknam(partner.getLinkman());
		 shop.setPhone_no(partner.getPhone_no());
		 shop.setCreate_date(Consts.SYSDATE);
		 shop.setState(Consts.PARTNER_STATE_NORMAL);//
		 shop.setSequ(Consts.PARTNER_SEQ_0);//
		 this.baseDaoSupport.insert(getTableName("partner_shop"), shop);
		 
	     despostHander.openAccount(partner.getPartner_id());//预存金开户
		return 1;
	}
	@Override
	public String get30yearDate(){
		 Calendar c=Calendar.getInstance();
	     c.add(Calendar.YEAR, 30);
	     SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     return f.format(c.getTime());
	}
	/**
	 * 工号增加一级分销商时调用  无需审核
	 * @param partner
	 * @return
	 */
	@Override
	public int add(Partner partner) {
		
	     String id=this.baseDaoSupport.getSequences("S_ES_PARTNER");
	     partner.setPartner_id(id);
	     partner.setCreate_date(Consts.SYSDATE);
	     partner.setState(Consts.PARTNER_STATE_NORMAL);//1
	     partner.setSequ(Consts.PARTNER_SEQ_0);//0
	     partner.setEff_date(Consts.SYSDATE);//生效
	     partner.setExp_date(get30yearDate());//失效
	     copyProperties(partner);
	      
	     this.baseDaoSupport.insert(db_table_name(),partner);
	     
	     PartnerShop shop =new PartnerShop();
		 shop.setPartner_id(partner.getPartner_id());
		 shop.setSend_address(partner.getAddress());
		 shop.setCreate_date(Consts.SYSDATE);
		 shop.setEff_date(Consts.SYSDATE);//生效
		 shop.setExp_date(get30yearDate());//失效
		 shop.setCreate_date(Consts.SYSDATE);
		 shop.setState(Consts.PARTNER_STATE_NORMAL);//
		 shop.setSequ(Consts.PARTNER_SEQ_0);//
		 this.baseDaoSupport.insert("es_partner_shop", shop);
		 
	     despostHander.openAccount(partner.getPartner_id());//预存金开户
		return 1;
	}
	public Partner copyProperties(Partner partner1){
		Partner partner=partner1;
		List<Map> list =this.defaultValue();
	    for (Map dcmap :  list) {
			String keys=(String) dcmap.get("pkey");
			String val=(String) dcmap.get("codea");
			
			if(partner.getNeed_amount()==null){
				if("need_amount".equals(keys)){
					Integer valu=Integer.parseInt(val);
					partner.setNeed_amount(valu);
				} 
			}else if(partner.getService_cash()==null){
				if("service_cash".equals(keys)){
					Integer valu=Integer.parseInt(val);
					partner.setService_cash(valu);
			    }
			}else if(partner.getInvoice_cash()==null){
				if("invoice_cash".equals(keys)){
					Integer valu=Integer.parseInt(val);
					partner.setInvoice_cash(valu);
			   }
			}else if(partner.getPartner_type()==null){
				if("partner_type".equals(keys)){
					partner.setPartner_type(val);
			    }
			}else if(partner.getPartner_level()==null){
				if("partner_level".equals(keys)){
					partner.setPartner_level(val);
			    }
			}else if(partner.getPartner_cat()==null){
				if("partner_cat".equals(keys)){
					partner.setPartner_cat(val);
			    }
			}
		}
	    return partner;
	}
	/**
	 * 商户申请时调用
	 * @param partner
	 * @return
	 */
	@Override
	public int applyadd(Partner partner) {
		
	     String id=this.baseDaoSupport.getSequences("S_ES_PARTNER");
	     partner.setPartner_id(id);
	     partner.setCreate_date(Consts.SYSDATE);
	     partner.setState(Consts.PARTNER_STATE_APPLY);//0
	     partner.setSequ(Consts.PARTNER_SEQ_W1);//0
	     copyProperties(partner);
	     this.baseDaoSupport.insert(db_table_name(),partner);
	     
		return 1;
	}
	/**
	 * 查询缺省值
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List defaultValue(){
		
		 List list=this.baseDaoSupport.queryForList("select p.pkey,p.codea  from es_dc_public p where p.stype="+Consts.DC_PUBLIC_STYPE_8025);
		
		 return list;
	}
	
	/**
	 * 修改llkj
	 */
	@Override
	public int edit_llkj(Partner partner){
		 //修改当前记录
		partner.setSequ(Consts.PARTNER_SEQ_0);//
		partner.setLast_update_date(Consts.SYSDATE);
		Map edit = ReflectionUtil.po2Map(partner);
		
		this.editUpdatePartner(edit,partner.getPartner_id());
		return 1;
	}
	/**
	 * 修改
	 */
	@Override
	public int edit(Partner partner){
		
		//如果是审核后修改
		if(Consts.PARTNER_STATE_NORMAL.equals(partner.getState())){
			  //需走变更流程的 ：证件号、网店地址、营业执照，达量
		
			  Partner oldpar=this.getPartner(partner.getPartner_id()); 
			  if(oldpar!=null){
				  
				  boolean is_amount=true;//达量
				  boolean is_license=true;//营业执照
				  boolean is_idcard=true;//证件号
				  boolean is_partner_name=true;//分销商名称
				  boolean is_partner_type=true;;///分销商类型
				  boolean is_legal_name=true;;//法人姓名
				  boolean is_invoice_cash=true;;///发票押金
				  boolean is_score=true;//分值
				  boolean is_eff_date=true;//生效
				  boolean is_exp_date=true;//失效
				  boolean is_email=true;//邮箱
				  
				  //查找待审核字段
				  List list=this.baseDaoSupport.queryForList("select p.pkey  from es_dc_public p where p.stype="+Consts.DC_PUBLIC_STYPE_8016);
				  Map key=null;
				  /////////如有修改 请记得修改PartnerAction.showAuditAlter()方法
				  for (int i = 0; i < list.size(); i++) {
					  key=(Map) list.get(i);
					    if(("need_amount").equals(key.get("pkey"))){
							  is_amount=partner.getNeed_amount().equals(oldpar.getNeed_amount())?true:false;//达量
						  }else if(("buss_license").equals(key.get("pkey"))){
							  is_license=partner.getBuss_license().equals(oldpar.getBuss_license())?true:false;//营业执照
						  }else if(("legal_id_card").equals(key.get("pkey"))){
							  is_idcard=partner.getLegal_id_card().equals(oldpar.getLegal_id_card())?true:false;//证件号
						  }else if(("partner_name").equals(key.get("pkey"))){
							  is_partner_name=partner.getPartner_name().equals(oldpar.getPartner_name())?true:false;//
						  }else if(("partner_type").equals(key.get("pkey"))){
							  is_partner_type=partner.getPartner_type().equals(oldpar.getPartner_type())?true:false;//
						  }else if(("legal_name").equals(key.get("pkey"))){
							  is_legal_name=partner.getLegal_name().equals(oldpar.getLegal_name())?true:false;//
						  }else if(("invoice_cash").equals(key.get("pkey"))){
							  is_invoice_cash=partner.getInvoice_cash().equals(oldpar.getInvoice_cash())?true:false;//
						  }else if("score".equals(key.get("pkey"))){
							  is_score=partner.getScore().equals(oldpar.getScore())?true:false;
						  }else if("eff_date".equals(key.get("pkey"))){   // oldpar.getEff_date() 2015-05-25 00:00:00.0
							  is_eff_date=partner.getEff_date().substring(0, 10).equals(oldpar.getEff_date().substring(0, 10))?true:false;
						  }else if("exp_date".equals(key.get("pkey"))){
							  is_exp_date=partner.getExp_date().substring(0, 10).equals(oldpar.getExp_date().substring(0, 10))?true:false;
						  }else if("email".equals(key.get("pkey"))){
							  is_email=partner.getEmail().equals(oldpar.getEmail())?true:false;
						  }
				   }
				  
				  
					
				  if(is_score && is_amount && is_license && is_idcard &&is_eff_date &&is_exp_date
						  && is_partner_name && is_partner_type && is_email
						  && is_legal_name &&is_invoice_cash){//无审核字段变更
						
					  //修改当前记录
						partner.setSequ(Consts.PARTNER_SEQ_0);//
						partner.setLast_update_date(Consts.SYSDATE);
						Map edit = ReflectionUtil.po2Map(partner);
						
						this.editUpdatePartner(edit,partner.getPartner_id());
						
						
				  }else{//有审核字段变更
					  
						//修改当前记录
					    String curloginpar=this.currentLoginPartnerId();
					    
					    if(curloginpar.equals(partner.getPartner_id())){ //备份 修改自己的资料 需审核
					    	partner.setSequ(Consts.PARTNER_SEQ_W1);
						 }else{//备份 无需审核
							 partner.setSequ(Consts.PARTNER_SEQ_0);
					    }
						Map edit = ReflectionUtil.po2Map(partner);
						this.editUpdatePartner(edit,partner.getPartner_id());
						
						if(curloginpar.equals(partner.getPartner_id())){
							this.bakOldPartnerSequ0(oldpar, null, Consts.PARTNER_SEQ_0);
						}else{
							this.bakOldPartnerSequ0(oldpar, null, getMaxPartnerSequ(oldpar)+1);
						}
						//if(curloginpar.equals(partner.getPartner_id())){ 
						//	partnerShopManager.bakNormalPartnershop(partner.getPartner_id(), null, Consts.PARTNER_SEQ_W1);
						//}
							
						}
		}}else{
			partner.setLast_update_date(Consts.SYSDATE);
			Map edit = ReflectionUtil.po2Map(partner);
			this.editPartner(partner, partner.getState(), partner.getSequ());
		}
		
		return 1;
	}
	/**
	 * 资料变更查询时 设置默认type
	 * @return
	 */
	@Override
	public String getDefaltType(){
		boolean is_super=ManagerUtils.isNetStaff();
		boolean is_first=ManagerUtils.isFirstPartner();
		boolean is_secound=ManagerUtils.isSecondPartner();
		String sql="";
		if(is_super){
			sql=Consts.A_0;
		}else if(is_first){
			sql=Consts.B_0;
		}else{
			sql=Consts.PARTNER_STATE_TOW_ALL;
		}
		return sql;
	}
	
	//首页查询账户的分销商任务条数
	@Override
	public int getCountSql(int founder,String state,int sequ,String userid,String type){
		String countSql="select count(*)  ";
		String whereSqlto ="";
		if(Consts.AUDIT_PATNER_LIST_0.equals(type)){//待审核分销商
			countSql+=wherePSql;
			whereSqlto=" where m.state=0 and m.sequ=-1  "+this.appendSql2();
			
		}else if(Consts.FIELD_AUDIT_LIST_1.equals(type)){//待审核资料变更
			countSql+=whereDistSql;
			whereSqlto  = " and ( (m.sequ="+sequ+" and s.sequ=0) or (m.sequ=0 and s.sequ="+sequ+")) "+this.appendAuditSql(); 
		}else if(Consts.BLOCK_LIST_2.equals(type)){//已冻结分销商
			countSql+=whereSql;
			whereSqlto=" where m.state=2 and m.sequ=0  "+this.appendSql();
		}
		countSql = countSql+whereSqlto;
		 return this.baseDaoSupport.queryForInt(countSql, null);
	}
	
	/**  ++++++++++++++++++++++ List+++++++++++++++++++++++++++++++++    */
	
	
	/**
	 * 分销商正常状态 列表
	 */
	@Override
	public Page list(Partner obj, String state, int page, int pageSize) {
		StringBuffer sqlAccount = new StringBuffer(
				//to_date(to_char(m.exp_date,'yyyy-MM-dd'),'yyyy-MM-dd')
				//to_date(to_char("+DBTUtil.current()+",'yyyy-MM-dd'),'yyyy-MM-dd')
				//DBTUtil.to_date(DBTUtil.to_char(DBTUtil.current(), 1), 1)
				"select " +
				     "(select u.username from es_adminuser u where u.userid=m.userid and u.source_from = '" + ManagerUtils.getSourceFrom() + "') username ," +
				     "(select u.founder from es_adminuser u where u.userid=m.userid and u.source_from = '" + ManagerUtils.getSourceFrom() +  "') founder ," +
				     "(select u.userid from es_adminuser u where u.userid=m.userid and u.source_from = '" + ManagerUtils.getSourceFrom() +  "') userid ," +				     "buss_license_ads,tax_number," +
				     "(case when " + DBTUtil.to_sql_date(DBTUtil.to_char("m.exp_date", 1), 1)+"-"+DBTUtil.to_sql_date(DBTUtil.to_char(DBTUtil.current(), 1), 1)+" <=30 then '0' else '1' end ) edflag,"+
					"m.partner_id,m.partner_code,m.score,m.partner_name,m.need_amount,m.service_cash,"+DBTUtil.nvl()+"(n.account_amount,0)/100 deposit,"+DBTUtil.nvl()+"(n.frost_deposit,0)/100 frost_deposit," +
					 "m.partner_type,m.partner_level,m.partner_cat,"+
					"m.invoice_cash,m.parent_userid,m.legal_name,m.legal_id_card, " +
					"m.legal_phone_no, m.address, m.linkman, m.phone_no,m.email, m.buss_license, m.eff_date, m.exp_date,m.create_date,m.state,m.sequ ");
		
		StringBuffer sql = new StringBuffer(sqlAccount.toString()+","+sqlstr);
		
		StringBuffer whereSql2=new StringBuffer();
		ArrayList partm=new ArrayList();
		whereSql2.append(wherePSql.toString() + "left join es_partner_account n on(m.partner_id = n.partner_id and n.account_type = '00A' and m.source_from = n.source_from and n.source_from = '" 
							+ ManagerUtils.getSourceFrom() + "') " + " where 1=1 and m.source_from = '" + ManagerUtils.getSourceFrom() + "' and ");
	 	int sequ=Consts.PARTNER_SEQ_0;
		
	 	if(Consts.PARTNER_STATE_ALL.equals(state)){ //全部只需要查询正常、已冻结、注销
	 		whereSql2.append(" m.sequ in("+Consts.PARTNER_SEQ_0+") "); //","+Consts.PARTNER_SEQ_W1+","+Consts.PARTNER_SEQ_W2+"
	 	}else{
	 		if(state.equals(Consts.PARTNER_STATE_APPLY)){//申请中
				sequ=Consts.PARTNER_SEQ_W1;
			}else if(state.equals(Consts.PARTNER_STATE_NORMAL)){//正常
				sequ=Consts.PARTNER_SEQ_0;
			}else if(state.equals(Consts.PARTNER_STATE_NOTGO)){//审核不通过
				sequ=Consts.PARTNER_SEQ_W2;
			}else if(state.equals(Consts.PARTNER_STATE_CONGELATION)){//冻结
				sequ=Consts.PARTNER_SEQ_0;
			}else if(state.equals(Consts.PARTNER_STATE_LOGOUT)){//注销
				sequ=Consts.PARTNER_SEQ_0;
			}else if(state.equals(Consts.PARTNER_AUDIT_ING)){
				state=Consts.PARTNER_STATE_NORMAL;
				sequ=Consts.PARTNER_SEQ_W1;
			}else if(state.equals(Consts.PARTNER_AUDIT_NOTGO)){
				state=Consts.PARTNER_STATE_NORMAL;
				sequ=Consts.PARTNER_SEQ_W2;
			}
	 		whereSql2.append(" m.state='"+state+"' and m.sequ="+sequ);
	 	}
		whereSql2.append(this.appendSql2());
		//whereSql2.append(" and m.exp_date >="+DBTUtil.current()+" ");
		
		if(obj!=null){
			if(!StringUtil.isEmpty(obj.getPartner_code())){
				whereSql2.append(" and m.partner_code like ? ");
				partm.add("%"+obj.getPartner_code().trim()+"%");
			}
			if(!StringUtil.isEmpty(obj.getPartner_name())){
				whereSql2.append(" and m.partner_name like ? ");
				partm.add("%"+obj.getPartner_name().trim()+"%");
			}
			if(!StringUtil.isEmpty(obj.getLinkman())){
				whereSql2.append(" and m.linkman like ? ");
				partm.add("%"+obj.getLinkman().trim()+"%");
			}
			if(!StringUtil.isEmpty(obj.getPhone_no())){
				whereSql2.append(" and m.phone_no like ? ");
				partm.add("%"+obj.getPhone_no().trim()+"%");
			}
			
			if(!StringUtil.isEmpty(obj.getStaff_contract_phone())){	//行业用户联系电话
				whereSql2.append(" and exists" +
						"(select 1 from es_partner_staff ps " +
						"	where ps.partner_id = m.partner_id and ps.phone_no like ? " + ManagerUtils.apSFromCond("ps") + ")");
				partm.add("%"+obj.getStaff_contract_phone().trim()+"%");
			}
			
			if(!StringUtil.isEmpty(obj.getStaff_mac())){	//行业用户MAC地址
				whereSql2.append(" and exists" +
						"(select 1 from es_partner_staff ps " +
						"	where ps.partner_id = m.partner_id and ps.mac like ? " + ManagerUtils.apSFromCond("ps") + ")");
				
				partm.add("%"+obj.getStaff_mac().toUpperCase().trim()+"%");
			}
			
		}
		sql.append(whereSql2);
		sql.append(" order by  m.create_date desc ") ;
		
		String countSql = "select count(*)  "+whereSql2.toString();
		
	    Page webpage = this.baseDaoSupport.queryForCPage(sql.toString(), page, pageSize,Partner.class,countSql,partm.toArray());
	    List<Partner> partList = webpage.getResult();
	    for(int i = 0; i < partList.size(); i++){
	    	Partner partner = partList.get(i);
	    	String userId = partner.getUserid();
	    	if(null != userId && !"".equals(userId)){
	    		AdminUserReq adminUserReq = new AdminUserReq();
	    		adminUserReq.setUser_id(userId);
	    		
	    		AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
	    		AdminUser user = new AdminUser();
	    		if(adminUserResp != null){
	    			user = adminUserResp.getAdminUser();
	    		}
	    		if(null != user && Consts.CURR_FOUNDER_3.equals(user.getFounder() + "")){
	    			Partner temp = getPartnerByCurrentUserId(userId, partner);
	    			partner.setDeposit(temp.getDeposit());
	    			partner.setFrost_deposit(temp.getFrost_deposit());
	    		}
	    	}
	    }
	    sql.delete(0,sql.length());
	    whereSql2.delete(0,whereSql2.length());
	    
		return webpage;
	}
	/**
	 * 获取冻结的分销商信息userid
	 * */
	@Override
	public List<Partner> searchPartneridList(){
		String sql="select distinct userid,partner_id from es_partner where partner_id in(select distinct p.partner_id from es_adminuser a,es_partner p where a.userid=p.userid and p.state in(?,?,?)) and sequ='0'";
		return this.baseDaoSupport.queryForList(sql, Partner.class, Consts.PARTNER_STATE_CONGELATION,Consts.PARTNER_STATE_NOTGO,Consts.PARTNER_STATE_LOGOUT);
	}
	
	/**
	 * 连连手机号检查
	 * */
	@Override
	public boolean checkLlkjAccNbr(String acc_nbr){
		String sql = "select  * from es_ll_access_prod_inst where acc_nbr = ? and statuc_cd = ‘1’;";
//		return this.baseDaoSupport.queryForInt(sql, acc_nbr)>0;
		return true;
	}
	
	/**
	 * 分销商待审核列表
	 */
	@Override
	public Page auditlist(Partner obj, String order, int page, int pageSize) {
		String userid=ManagerUtils.getUserId();
		ArrayList partm=new ArrayList();
		
		StringBuffer sql = new StringBuffer(selectSql.toString()+","+sqlstr);
		StringBuffer whereSql2  = new StringBuffer(wherePSql.toString()+" where m.state='0' and m.sequ=-1 "+this.appendSql2());
		
		if(obj!=null){
			if(!StringUtil.isEmpty(obj.getPartner_code())){
				whereSql2.append(" and m.partner_code like ? ");
				partm.add("%"+obj.getPartner_code().trim()+"%");
			}
			if(!StringUtil.isEmpty(obj.getPartner_name())){
				whereSql2.append(" and m.partner_name like ? ");
				partm.add("%"+obj.getPartner_name().trim()+"%");
			}
			if(!StringUtil.isEmpty(obj.getLinkman())){
				whereSql2.append(" and m.linkman like ? ");
				partm.add("%"+obj.getLinkman().trim()+"%");
			}
			if(!StringUtil.isEmpty(obj.getPhone_no())){
				whereSql2.append(" and m.phone_no like ? ");
				partm.add("%"+obj.getPhone_no().trim()+"%");
			}
		}
		
		whereSql2.append(" and m.source_from='"+ManagerUtils.getSourceFrom()+"' ");
		sql.append(whereSql2);
		sql.append(" order by  m.create_date desc  ") ;
		
		String countSql = "select count(*)  "+whereSql2.toString();
		
	    Page webpage = this.baseDaoSupport.queryForCPage(sql.toString(), page, pageSize,Partner.class,countSql,partm.toArray());
	    sql.delete(0,sql.length());
	    whereSql2.delete(0,whereSql2.length());
		return webpage;
	}
	public String getFieldAuditListSql(int sequ,String appstr){
		String sql = "select m.*,u.username username_us,u.realname realname_us," +sqlstr+" from ";
		String whereSql  = db_table_name()+" m left join es_adminuser u on m.userid=u.userid  where  m.state=1 and m.sequ="+sequ+" "+appstr;
		sql+=whereSql;
		return sql;
	}
	
	
	/**
	 * 分销商资料变更待审核 列表
	 */
	@Override
	public Page fieldAuditlist(Partner obj, String type, int page, int pageSize) {
		
		ArrayList partm=new ArrayList();
		StringBuffer sql = new StringBuffer(selectDistSql);
		int sequ=Consts.PARTNER_SEQ_0;
		String appstr="";
		String userid=ManagerUtils.getUserId();
		
		StringBuffer whereSql2  = new StringBuffer(whereDistSql.toString());
		
		
		//////////查看权限/////////
		if(type.indexOf("all")!=-1){
			if(type.equals(Consts.PARTNER_STATE_ONE_ALL)){
				appstr=" and (u.userid='"+userid+"' or u.userid in (select userid from es_adminuser uu where uu.source_from = '" + ManagerUtils.getSourceFrom() + "' and uu.paruserid='"+userid+"')) ";
			}else if(type.equals(Consts.PARTNER_STATE_TOW_ALL)){
				appstr=" and u.userid='"+userid+"' ";
			}
			whereSql2.append(appstr+" and ( (m.sequ in(-1,-2) and s.sequ=0) or (m.sequ=0 and s.sequ in(-1,-2))) ");
		}else{
			if(type.equals(Consts.A_0)){//管理员 待审核
				sequ=Consts.PARTNER_SEQ_W1;
				appstr=" and u.founder="+Consts.CURR_FOUNDER_3+" ";
			}else if(type.equals(Consts.B_0)){//一级 待审
				sequ=Consts.PARTNER_SEQ_W1;
				appstr=" and u.userid in (select userid from es_adminuser uu where uu.paruserid='"+userid+"' and uu.source_from = '" + ManagerUtils.getSourceFrom() + "') ";
			}else if(type.equals(Consts.B_1)){//一级 处理中
				sequ=Consts.PARTNER_SEQ_W1;
				appstr=" and u.userid='"+userid+"' ";
			}else if(type.equals(Consts.B_2)){//一级 不通过
				sequ=Consts.PARTNER_SEQ_W2;
				appstr=" and (u.userid='"+userid+"' or u.userid in (select userid from es_adminuser uu where uu.source_from = '"
						+ ManagerUtils.getSourceFrom() + "' and uu.paruserid='"+userid+"')) ";
			}else if(type.equals(Consts.C_1)){//二级 处理中
				sequ=Consts.PARTNER_SEQ_W1;
				appstr=" and u.userid='"+userid+"' ";
			}else if(type.equals(Consts.C_2)){//二级 不通过
				sequ=Consts.PARTNER_SEQ_W2;
				appstr=" and u.userid='"+userid+"' ";
			}else{
				appstr=" and 1=2 ";
			}
			whereSql2.append(appstr+" and ( (m.sequ="+sequ+" and s.sequ=0) or (m.sequ=0 and s.sequ="+sequ+")) ");
		}	
		
		if(obj!=null){
			if(!StringUtil.isEmpty(obj.getPartner_code())){
				whereSql2.append(" and m.partner_code like ? ");
				partm.add("%"+obj.getPartner_code().trim()+"%");
			}
			if(!StringUtil.isEmpty(obj.getPartner_name())){
				whereSql2.append(" and m.partner_name like ? ");
				partm.add("%"+obj.getPartner_name().trim()+"%");
			}
			if(!StringUtil.isEmpty(obj.getLinkman())){
				whereSql2.append(" and m.linkman like ? ");
				partm.add("%"+obj.getLinkman().trim()+"%");
			}
			if(!StringUtil.isEmpty(obj.getPhone_no())){
				whereSql2.append(" and m.phone_no like ? ");
				partm.add("%"+obj.getPhone_no().trim()+"%");
			}
		}
		sql.append(whereSql2);
		sql.append(" and m.exp_date >="+DBTUtil.current());
		sql.append(" order by  m.create_date desc ") ;
		
		String countSql = "select count(*) " + whereSql2;
	    Page webpage = this.baseDaoSupport.queryForCPage(sql.toString(), page, pageSize,Partner.class,countSql,partm.toArray());
	    sql.delete(0,sql.length());
	    whereSql2.delete(0,whereSql2.length());
	    return webpage;
	}
	/**
	 * 查找审核字段
	 * @param partid
	 * @param sequR
	 * @return
	 */
	@Override
	public List columnAuditList(){
		
		 List list=this.baseDaoSupport.queryForList("select p.pkey,p.stype,p.pname  from es_dc_public p where p.stype=8016 or p.stype=8017 order by p.codea");
		
		 return list;
	}
	/**
	 * 查找变更前后历时记录
	 */

	@Override
	public List histroyList(List columnNamelist,String partnerid){
		StringBuffer querySql =new StringBuffer("select ");
		StringBuffer oldSql =new StringBuffer(1000);
		StringBuffer newSql =new StringBuffer(1000);
		Long type=null;
		String field_name ="";
		String pNamestr="";
		StringBuffer asname=new StringBuffer();
		Long lon=8017l;
		 String as="p.";
		 for(Map dcMap : (List<Map>)columnNamelist){
			 
			 field_name = (String)dcMap.get("pkey");
			 type=(Long) dcMap.get("stype");
			
			 if(type.equals(lon)){
				 as="s.";
			 }else{
				 as="p.";
			 }
			 field_name=as+field_name;
			 
			 querySql.append(field_name+" "+field_name.replace(".", "_")+" ,");
			 asname.append(field_name.replace(".", "_")+" ,");
		 }
		 querySql.setLength(querySql.length()-1);
		 asname.setLength(asname.length()-1);
		
		 
		 int maxsequ=this.getMaxPartnerSequ(partnerid);
		 int maxShopsequ=this.partnerShopManager.getMaxPartnerShopSequ(partnerid);
		 
		 querySql.append(" from es_partner p,es_partner_shop s");
		 querySql.append(" where p.partner_id=s.partner_id");
		// querySql.append(" and (p.state=1 and s.state=1)");
		 
		 oldSql.append(querySql);
		 oldSql.append(" and p.sequ=s.sequ and p.sequ=0 ");//变更前
		 oldSql.append(" and p.partner_id = '"+partnerid+"' ");
		 
		 newSql.append(querySql);
		 newSql.append(" and ( (p.sequ=-1 and s.sequ=0) or (p.sequ=0 and s.sequ=-1)) ");//变更后   删除(p.sequ=-1 or s.sequ=-1) 改为
		 newSql.append(" and p.partner_id = '"+partnerid+"' ");
		 
		 querySql.delete(0,querySql.length());
		 querySql.append("select "+asname+" from ("+oldSql+" union all "+newSql+")");
		 List list=baseDaoSupport.queryForList(querySql.toString());
		 
		 
		 querySql.delete(0,querySql.length());
		 oldSql.delete(0,oldSql.length());
		 newSql.delete(0,newSql.length());
		 
		 
		 ///////////////////比较是否数据相同////////////
		 Map map1=(Map) list.get(0);
		 Map map2=(Map)list.get(1);
		 
		 logger.info("前："+map1);
		 logger.info("前："+map2);
		 
		 String a []=asname.toString().split(",");
		 for (int i = 0; i < a.length; i++) {
			if(map1.get(a[i].trim()).equals(map2.get(a[i].trim()))){
				map1.remove(a[i].trim());
				map2.remove(a[i].trim());
			}
		}
		 
		 logger.info("后："+map1);
		 logger.info("后："+map2);
		 
		 asname.delete(0,asname.length());
		 
		 
		 List listkey=new ArrayList();
		 List listVal1=new ArrayList();
		 List listVal2=new ArrayList();
		 Iterator it=map1.keySet().iterator();
		 String name="";
		 while(it.hasNext()){
			 String key=it.next().toString();
			 name=key.substring(2, key.length());
			 if(key.indexOf("s_")==0){
				 name=this.getDcpublicName("8017", name);
			 }else{
				 name=this.getDcpublicName("8016", name); 
			 }
			 listkey.add(name);
			 Object value = map1.get(key);
			 Boolean result = value instanceof Timestamp;
			 if(result){
				 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式
				 value = df.format(value);
			 }
			 
			 listVal1.add(value);
		 }
		 Iterator it2=map2.keySet().iterator();
		 while(it2.hasNext()){
			 String key=it2.next().toString();
			 
			 Object value = map2.get(key);
			 Boolean result = value instanceof Timestamp;
			 if(result){
				 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式
				 value = df.format(value);
			 }
			 
			 listVal2.add(value);
		 }
//		 ServletActionContext.getRequest().setAttribute("listkey", listkey);
		 ServletActionContext.getRequest().setAttribute("listVal1", listVal1);//变更前
		 ServletActionContext.getRequest().setAttribute("listVal2", listVal2);//变更后
		return listkey;
	}
	
	/**
	 * 关联二级分销商 分销商 查找分销商
	 */
	@Override
	public Page searchPartner(Map map ,int page,int pageSize) {

		String username = (String) map.get("userNameKey");
		String realname = (String) map.get("realNameKey");
		String partner_code = (String) map.get("partner_code");
		
		ArrayList params = new ArrayList();
		
		

		String sql = "select m.partner_id,m.partner_name,m.partner_code,m.phone_no from ";
		String whereSql=db_table_name()+" m  where m.sequ=0 and m.state='1'  "+this.appendParentUseridSql(); //and not exists (select 1 from es_adminuser b where m.userid = b.userid)
		
		if("LLKJ_AGENT".equals(ManagerUtils.getSourceFrom())){//不清楚先前为什么关联过的代理商,还能关联,这里连连的特殊处理下,不能再关联  1403
			whereSql=db_table_name()+" m  where m.sequ=0 and m.state='1' and userid is null  "+this.appendParentUseridSql(); //and not exists (select 1 from es_adminuser b where m.userid = b.userid)
		}
		
			
		if(username!=null && !"".equals(username)){
			whereSql+=" and m.partner_id like ? ";
        	params.add(username.trim()+"%");
        }
        if(realname!=null && !"".equals(realname)){
        	whereSql+=" and m.partner_name like ? ";//"+realname.trim()+"
        	params.add("%"+realname.trim()+"%");
        }
        if(partner_code!=null && !"".equals(partner_code)){
        	whereSql+=" and m.partner_code like ? ";//"+realname.trim()+"
        	params.add("%"+partner_code.trim()+"%");
        }
        sql+=whereSql;
		sql += " order by  m.partner_id desc ";
		String countSql="select count(*) from "+whereSql;
	    Page webpage = this.daoSupport.queryForPage(sql, page, pageSize,Partner.class,params.toArray());
		return webpage;
	}
	/**
	 * 关联分销商 查询所有分销商
	 * @param username
	 * @param realname
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page searchPartnerList(String username, String partner_name, int page, int pageSize){
		
		ArrayList partm=new ArrayList();
		
		StringBuffer sql = new StringBuffer(selectSql.toString());
		StringBuffer whereSql2  = new StringBuffer(whereSql.toString()+" where m.state='1' and m.sequ=0 "+this.appendSql());
		
		
		if(partner_name!=null && !"".equals(partner_name)){
			whereSql2.append(" and m.partner_name like ? ");
        	partm.add("%"+partner_name.trim()+"%");
        }
		sql.append(whereSql2);
		sql.append(" order by  m.create_date desc ") ;
		
		String countSql = "select count(*)  "+whereSql2.toString();
		
	    Page webpage = this.baseDaoSupport.queryForCPage(sql.toString(), page, pageSize,Partner.class,countSql,partm.toArray());
	    sql.delete(0,sql.length());
	    whereSql2.delete(0,whereSql2.length());
	    return webpage;
	}
/*	public List partnerlist() {
		
		ArrayList partm=new ArrayList();
		
		String sql = "select m.*,u.username username_us,u.realname realname_us from "+db_table_name()+" m left join es_adminuser u on m.userid=u.userid" +
				" where  m.state=1 and m.sequ=0 ";
		
		sql+=this.appendSql();
		
		sql += " order by   m.partner_id desc";
		return this.daoSupport.queryForList(sql);
	}
	*/
	
	/**
	 * 分销商业务
	 * @return
	 */
	@Override
	public List partnerGoodsUsageList(String userid,String state){
		String sql=" select distinct g.goods_id,g.name from es_goods g where  exists" +
				" (select 1 from es_partner p,es_goods_usage u where u.userid=p.userid and g.goods_id=u.goods_id  and p.userid=? and u.state='"+state.trim()+"')";
		return this.baseDaoSupport.queryForList(sql,userid);
	}
	
	
	
	
	/**
	 * 一级分销商账号资料
	 * @return
	 */
	@Override
	public List onelevelPartnerList() {

//		String sql = "select userid,realname from es_adminuser a where founder=0 and not exists (select 1 from es_partner b where a.userid = b.userid) ";
		String sql="select a.* from es_adminuser a where founder="+Consts.CURR_FOUNDER_3;//3为1级分销商

		return this.baseDaoSupport.queryForList(sql);
	}
	
	@Override
	public List<Map> list() {
		String sql = "select t.*  from partner t ";
		return this.baseDaoSupport.queryForList(sql);
	}
	@Override
	public List auditResonAndDateList(String partner_id,Integer msequ,Integer ssequ) {
		StringBuffer sql=new StringBuffer(auditresonSql);
		sql.append(" and m.partner_id='"+partner_id+"' ");
		sql.append("and m.sequ="+msequ+" and s.sequ="+ssequ+" ");
		List list=this.baseDaoSupport.queryForList(sql.toString());
		sql.delete(0, sql.length());
		return list;
	}
	/**
	 * 审核通过
	 * @param obj
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page partnerAuditlist(Partner obj, int page, int pageSize){
		ArrayList partm=new ArrayList();
		String sql = "select m.* from "+db_table_name()+" m where state = ?";
		partm.add(StringUtil.toUTF8(Consts.PARTNER_STATE_NORMAL));//审核通过
		 Page webpage = this.daoSupport.queryForPage(sql, page, pageSize,partm.toArray());
			return webpage;
	}
	////////////////////////////////////////////
	/**
	 * 已分配工号
	 * @return
	 */
	@Override
	public Page partnerAssignYeslist(Partner obj, int page, int pageSize){
		String sql = "select m.* from "+db_table_name()+" m where userid is not null ";
		 Page webpage = this.daoSupport.queryForPage(sql, page, pageSize);
			return webpage;
	}
	/**
	 *未分配工号
	 * @return
	 */
	@Override
	public Page partnerAssignNolist(Partner obj, int page, int pageSize){
		String sql = "select m.* from "+db_table_name()+" m where userid is null ";
		 Page webpage = this.daoSupport.queryForPage(sql, page, pageSize);
			return webpage;
	}
	
	/*
	 * 
	 * @function 根据id获取分销商信息
	 */
	@Override
	public Partner queryPartnerByID(String partnerId){
		String sql = "select partner_id, partner_name, partner_type, need_amount, service_cash," +
				" invoice_cash, parent_userid, legal_name, legal_id_card, legal_phone_no, address," +
				" linkman, phone_no, buss_license, state, eff_date, exp_date, apply_reason, comments," +
				" create_date, state_date, partner_code, partner_level, partner_cat, shop_type, deposit/100 deposit," +
				" frost_deposit/100 frost_deposit, userid, sequ, image_file from es_partner where sequ = 0 and state = '1' " ;
		
		if(partnerId != null && !"".equals(partnerId)){
			sql += " and partner_id = ?";
		}else {
			return null;
		}
		List result = this.baseDaoSupport.queryForList(sql, Partner.class, partnerId);
		
		Partner partner = null;
		if(null != result && !result.isEmpty()){
			partner = (Partner)result.get(0);
		}
		return partner;
	}
	
	public String arrToString(String [] goodsid){
		String a ="";
		for(int i=0;i<goodsid.length;i++){
		     a+=goodsid[i]+',';
		}
		if(a!=""){
			a=a.substring(0, a.length()-1);
		}
		return a;
	}
	//门户商户申请选择一级分销商
	@Override
	public List searchPartnerAdUserList() {
		
		String sql = "select u.* from "+db_table_name()+" m right join es_adminuser u on m.userid=u.userid" +
				" where u.state=1 and u.founder=3 and m.state=1  and m.sequ=0 ";
		
		sql += " order by u.userid desc";
		return this.daoSupport.queryForList(sql);
	}
	//门户商户申请选择分销商网店量级
	@Override
	public List searchPartneShopStarList() {
		
		String sql = "select p.pkey,p.pname from es_dc_public p where p.stype=8008 ";
		
		return this.daoSupport.queryForList(sql);
	}
	//门户商户申请选择分销商店铺类型
	@Override
	public List searchPartneShopTypeList() {
		
		String sql = "select p.pkey,p.pname from es_dc_public p where p.stype=8007 ";
		
		return this.daoSupport.queryForList(sql);
	}
	//门户商户申请选择分销商类型
/*	public List searchPartnerTypeList() {
		
		String sql = "select p.pkey ,p.pname from es_dc_public p where p.stype=8004 ";
		
		return this.daoSupport.queryForList(sql);
	}*/
	//门户商户申请选择分销商级别
/*	public List searchPartnerLevelList() {
		
		String sql = "select p.pkey,p.pname from es_dc_public p where p.stype=8005 ";
		
		return this.daoSupport.queryForList(sql);
	}*/
	
	//门户商户申请选择分销商类别
/*	public List searchPartneCatList() {
		
		String sql = "select p.pkey,p.pname from es_dc_public p where p.stype=8006 ";
		
		return this.daoSupport.queryForList(sql);
	}*/
	
	
	
	@Override
	public Page queryPartner(String userid, String partner_name, String parent_userid, String state,
			Integer sequ, String beginStateTime, String endStateTime,
			String founder,int pageNo, int pageSize) {
		StringBuffer sb = new StringBuffer("select t.partner_id, t.partner_name, t.userid, a.username " +
				"from es_partner t left join es_adminuser a on t.userid = a.userid where 1=1 ");
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(userid)) {
			sb.append(" and t.userid = :userid ");
			param.put("userid", userid);
		}
		if (!StringUtil.isEmpty(partner_name)) {
			sb.append(" and partner_name like :partner_name ");
			param.put("partner_name", partner_name.trim() + "%");
		}
		if (!StringUtil.isEmpty(parent_userid)) {
			sb.append(" and t.parent_userid = :parent_userid ");
			param.put("parent_userid", parent_userid);
		}
		if (!StringUtil.isEmpty(state)) {
			sb.append(" and t.state = :state ");
			param.put("state", state);
		}
		if (sequ != null) {
			sb.append(" and t.sequ = :sequ ");
			param.put("sequ", sequ);
		}
		if (!StringUtil.isEmpty(beginStateTime)) {
			sb.append(" and t.state_date  >= to_date(:start_time, 'yyyy-mm-dd hh24:mi:ss') ");
			param.put("start_time", beginStateTime + " 00:00:00");
		}
		if (!StringUtil.isEmpty(endStateTime)) {
			sb.append(" and t.state_date  <= to_date(:end_time, 'yyyy-mm-dd hh24:mi:ss') ");
			param.put("end_time", endStateTime + " 23:59:59");
		}
		if (!StringUtil.isEmpty(founder)) {
			sb.append(" and exists (select 1 from es_adminuser u where u.userid = t.userid and u.founder = :founder)");
			param.put("founder", founder);
		}
		return baseDaoSupport.queryForPageByMap(sb.toString(), pageNo, pageSize, param);
	}
	
	@Override
	public Partner getPartner(String partner_id, String userid, String parent_userid, String state,
			Integer sequ, String founder) {
		StringBuffer sb = new StringBuffer("select t.*, a.username " +
		"from es_partner t left join es_adminuser a on t.userid = a.userid where 1=1 ");
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(partner_id)) {
			sb.append(" and t.partner_id = :partner_id ");
			param.put("partner_id", partner_id);
		}
		if (!StringUtil.isEmpty(userid)) {
			sb.append(" and t.userid = :userid ");
			param.put("userid", userid);
		}
		if (!StringUtil.isEmpty(parent_userid)) {
			sb.append(" and t.parent_userid = :parent_userid ");
			param.put("parent_userid", parent_userid);
		}
		if (!StringUtil.isEmpty(state)) {
			sb.append(" and t.state = :state ");
			param.put("state", state);
		}
		if (sequ != null) {
			sb.append(" and t.sequ = :sequ ");
			param.put("sequ", sequ);
		}
		if (!StringUtil.isEmpty(founder)) {
			sb.append(" and exists (select 1 from es_adminuser u where u.userid = t.userid and u.founder = :founder)");
			param.put("founder", founder);
		}
		return daoSupport.queryForObject(sb.toString(), Partner.class, param);
	}
	
	/**
	 * 分销商合约过期：短信提醒&冻结 定时任务一天执行一次
	 */
	@Override
	public void partnerExpAndFreeze() {
		String sql = "select t.partner_id, t.linkman, t.partner_name, t.phone_no, to_char(t.exp_date, 'yyyy-mm-dd') exp_date " +
				" from es_partner t where t.sequ = " + Consts.PARTNER_SEQ_0 + " " +
				" and t.state = '" + Consts.PARTNER_STATE_NORMAL + "' and "+DBTUtil.to_date("t.exp_date", 1)+" = "+DBTUtil.to_date(DBTUtil.current(), 1)+"";
		List<Map> list = daoSupport.queryForList(sql); 
		if (!list.isEmpty()) {
			for (Map<String, String> map : list) { 
				logger.debug("===分销商===" + map + "合约今天过期。");
				//smsManager.addPartnerExpMsg(map.get("linkman"), map.get("phone_no"), map.get("exp_date"), Consts.DC_PUBLIC_8018_PKEY_2);
			}
		}
		sql = "select t.partner_id, t.linkman, t.partner_name, t.phone_no, to_char(t.exp_date, 'yyyy-mm-dd') exp_date " +
				" from es_partner t where t.sequ = "  + Consts.PARTNER_SEQ_0 + " " +
				" and t.state = '" + Consts.PARTNER_STATE_NORMAL + "' and "+DBTUtil.to_date("t.exp_date", 1)+" < "+DBTUtil.to_date(DBTUtil.current(), 1)+"";
		list = daoSupport.queryForList(sql);
		if (!list.isEmpty()) {
			for (Map<String, String> map : list) { 
				logger.debug("===分销商===" + map + "合约已经过期。冻结所有业务");
				//smsManager.addPartnerExpMsg(map.get("linkman"), map.get("phone_no"), map.get("exp_date"), Consts.DC_PUBLIC_8018_PKEY_3);
				sql = "update es_partner t set t.state = '" + Consts.PARTNER_STATE_CONGELATION + "' where t.sequ = " + Consts.PARTNER_SEQ_0 + "" +
					" and t.state = '" + Consts.PARTNER_STATE_NORMAL + "' and t.partner_id = '" + map.get("partner_id") + "'";
				daoSupport.execute(sql);
			}
		}
	}
	
	
	public IPartnerShopManager getPartnerShopManager() {
		return partnerShopManager;
	}
	public void setPartnerShopManager(IPartnerShopManager partnerShopManager) {
		this.partnerShopManager = partnerShopManager;
	}
	public String getSqlstr() {
		return sqlstr;
	}
	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
	}
	public IDespostHander getDespostHander() {
		return despostHander;
	}
	public void setDespostHander(IDespostHander despostHander) {
		this.despostHander = despostHander;
	}
	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	} public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}
	
	/**
	 * 查询分销商列表
	 */
	public GetPartnerResp getPartnerList(GetPartnerReq req) {
		GetPartnerResp resp = new GetPartnerResp();
		try{
			Partner part_serch = new Partner();
			part_serch.setPartner_name(req.getPartner_name());
			Page page = this.list(part_serch, Consts.PARTNER_STATE_ALL, 
					req.getPage_index(), req.getPage_size());
			resp.setPage(page);
			resp.setError_code("0");
		}catch(Exception e){
			e.printStackTrace();
		}
		return resp;
	}
	@Override
	public PartnerWdAddResp addParterWd(PartnerWdAddReq partnerWdAddReq) {
		// TODO Auto-generated method stub
		 PartnerWdAddResp partnerWdAddResp = new PartnerWdAddResp();
	 try{
		 AdminUserReq  adminUserReq  = new AdminUserReq();
		 AdminUserResp adminUserResp = new AdminUserResp();
		 adminUserReq.setUser_name(partnerWdAddReq.getUsername());
		 adminUserResp = adminUserServ.getAdminUserByUserName(adminUserReq);
		 if(adminUserResp.getAdminUser()!=null){
			 partnerWdAddResp.setError_code(ConstsCore.ERROR_FAIL);
			 partnerWdAddResp.setError_msg("改帐户名已经注册,请换一个用户名");
			 return partnerWdAddResp;
		 }
		 //添加分销商
		 Partner partner = new Partner();
	     String id=this.baseDaoSupport.getSequences("S_ES_PARTNER");
	     partner.setPartner_id(id);
	     partner.setPhone_no(partnerWdAddReq.getUsername());//前台用手机注册
	     partner.setPartner_name(partnerWdAddReq.getPartner_shop_name());
	     partner.setAddress(partnerWdAddReq.getPartner_shop_address());
	     partner.setImage_file(partnerWdAddReq.getPartner_image());//分销商logo图片
	     partner.setCreate_date(Consts.SYSDATE);
	     partner.setState(Consts.PARTNER_STATE_NORMAL);//1
	     partner.setSequ(Consts.PARTNER_SEQ_0);//0
	     partner.setEff_date(Consts.SYSDATE);//生效
	     partner.setExp_date(get30yearDate());//失效
	     this.baseDaoSupport.insert("es_partner",partner);
	     
	     //添加店铺信息
	     PartnerShop shop =new PartnerShop();
		 shop.setPartner_id(partner.getPartner_id());
		 shop.setSend_address(partner.getAddress());
		 shop.setName(partnerWdAddReq.getPartner_shop_name());
		 shop.setShop_detail_image(partnerWdAddReq.getPartner_ship_image());
		 shop.setCreate_date(Consts.SYSDATE);
		 shop.setEff_date(Consts.SYSDATE);//生效
		 shop.setExp_date(get30yearDate());//失效
		 shop.setCreate_date(Consts.SYSDATE);
		 shop.setState(Consts.PARTNER_STATE_NORMAL);//
		 shop.setSequ(Consts.PARTNER_SEQ_0);//
		 this.baseDaoSupport.insert("es_partner_shop", shop);
		 
		 //添加系统工号
		 AdminUser adminUser = new AdminUser();
		 adminUser.setRelcode(partner.getPartner_id());
		 adminUser.setFounder(Consts.CURR_FOUNDER3);
		 adminUser.setUsername(partnerWdAddReq.getUsername());
		 adminUser.setPassword(partnerWdAddReq.getPassword());
		 adminUser.setParuserid(ConstsCore.ADMIN_USER_ID);
		 adminUser.setRoleids(new String[]{ConstsCore.PARTNER_USER_ROLE});
		 adminUser.setState(Integer.parseInt(Consts.user_state_1));
		 adminUser.setReltype("partner");
		 AdminAddReq adminAddReq = new AdminAddReq();
		 adminAddReq.setAdminUser(adminUser);
		 adminUserServ.add(adminAddReq);
		 partnerWdAddResp.setError_code(ConstsCore.ERROR_SUCC);
		 partnerWdAddResp.setError_msg("操作成功");
		 partnerWdAddResp.setAdminUser(adminUser);
		 partnerWdAddResp.setPartnerShop(shop);
		 partnerWdAddResp.setPartner(partner);
	 }catch(Exception e){
		 e.printStackTrace();
		 partnerWdAddResp.setError_code(ConstsCore.ERROR_FAIL);
		 partnerWdAddResp.setError_msg("操作失败："+e.getMessage());
	 }
		 return partnerWdAddResp;
	}
	
}
