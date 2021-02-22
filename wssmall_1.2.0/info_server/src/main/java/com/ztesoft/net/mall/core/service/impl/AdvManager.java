package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.app.base.core.model.Adv;
import com.ztesoft.net.app.base.core.model.AdvMapper;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IAdvManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 广告管理
 * 
 * @author 李志富 lzf<br/> 2010-2-4 下午03:55:33<br/> version 1.0<br/> <br/>
 */
public class AdvManager extends BaseSupport<Adv> implements IAdvManager {

	@Override
	public void addAdv(Adv adv) {
		this.baseDaoSupport.insert("adv", adv);

	}

	@Override
	public void delAdvs(String ids) {
		if (ids == null || ids.equals(""))
			return;
//		String sql = "INSERT into es_adv_his SELECT * FROM es_adv where source_from='"+ManagerUtils.getSourceFrom()+"' and aid in("+ids+")";
//		this.baseDaoSupport.execute(sql);
		String sql = "update es_adv set disabled='true' where source_from='"+ManagerUtils.getSourceFrom()+"' and aid in (" + ids + ")";
		this.baseDaoSupport.execute(sql);
	}

	/**
	 * 根据工号删除文章信息并归档到历史表
	 * 
	 * @param staff_no
	 */
	@Override
	public void delAdvByStaffNo(String staff_no) {
		if (staff_no == null || "".equals(staff_no))
			return;
		String sql = "INSERT into es_adv_his SELECT * FROM es_adv where staff_no=?";
		this.baseDaoSupport.execute(sql, staff_no);
		sql = "delete from adv where staff_no=?";
		this.baseDaoSupport.execute(sql, staff_no);
	}

	@Override
	public Adv getAdvDetail(String advid) {
		List<Adv> advs = this.baseDaoSupport.queryForList(
				"select t.*,'' cname from es_adv t where t.source_from=? and t.aid = ?", new AdvMapper(), ManagerUtils.getSourceFrom(), advid);
		Adv adv = null;
		if(advs!=null && advs.size()>0){
			adv = advs.get(0);
			String pic = adv.getAtturl();
			if (pic != null) {
				pic = UploadUtil.replacePath(pic);
				adv.setAtturl(pic);
			}
		}
		
		return adv;
	}

	@Override
	public Adv getAdvDetail(String advid,String source_from) {
		Adv adv = this.baseDaoSupport.queryForObject(
				"select * from adv where aid = ? and source_from=? ", Adv.class, advid, source_from);
		String pic = adv.getAtturl();
		if (pic != null) {
			pic = UploadUtil.replacePath(pic);
			adv.setAtturl(pic);
		}
		return adv;
	}
	
	@Override
	public Page pageAdv(String order, int page, int pageSize) {
		order = order == null ? " aid desc" : order;
		String sql = "select v.*, c.cname   cname from "
				+ this.getTableName("adv") + " v left join "
				+ this.getTableName("adcolumn") + " c on c.acid = v.acid";
		sql += " order by " + order;
		Page rpage = this.daoSupport.queryForPage(sql, page, pageSize,
				new AdvMapper());
		return rpage;
	}

	@Override
	public void updateAdv(Adv adv) {

		
		this.baseDaoSupport.update("adv", adv, "aid = " + adv.getAid());

	}
	
	@Override
	public void updateAdvIsClose(String aid,int isClose){
		String sql="update "+this.getTableName("adv")+" set isclose=? where aid=?";
		this.baseDaoSupport.execute(sql, isClose,aid);
	}

	@Override
	public List listAdv(String acid) {
	
		String sql = "select a.*,'' cname from adv a where acid = ? and begintime<="+DBTUtil.current()+" and endtime>="+DBTUtil.current()+" and isclose = 0";
		if (!StringUtil.isEmpty(StringUtil.getAgnId()))
			sql += " and user_id ='" + StringUtil.getAgnId()+"'";
		else
			sql += " and (user_id is null or user_id ='')";
		List<Adv> list = this.baseDaoSupport.queryForList(sql, new AdvMapper(),
				acid);
		return list;
	}
	
	@Override
	public List listAdv(String acid, String user_id) {
		
		String sql = "select a.*,'' cname from adv a "
				+ " where acid = ? "
				+ " and begintime <= " + DBTUtil.current()
				+ " and endtime >= " + DBTUtil.current()
				+ " and isclose = 0";
		if (!StringUtil.isEmpty(user_id)) {
			sql += " and user_id = '" + user_id + "'";
		} else {
			sql += " and (user_id is null or user_id = '')";
		}
		List<Adv> list = this.baseDaoSupport.queryForList(sql, new AdvMapper(), acid);
		
		return list;
	}

	@Override
	public Page search(String acid, String cname,int state,int pageNo, int pageSize,
			String order) {
		AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
		int founder = adminUser.getFounder();
		StringBuffer term = new StringBuffer();
		StringBuffer sql = new StringBuffer("select v.*, c.cname   cname from "
				+ this.getTableName("adv") + " v left join "
				+ this.getTableName("adcolumn") + " c on c.acid = v.acid and c.source_from = v.source_from ");

		if (!StringUtil.isEmpty(acid)&&acid != null) {
			term.append(" where  c.acid=" + acid);
		}

		if (!StringUtil.isEmpty(cname)) {
			if (term.length() > 0) {
				term.append(" and ");
			} else {
				term.append(" where ");
			}

			term.append(" aname like'%" + cname + "%'");
		}
		if(state!=-1){
			if (term.length() > 0) {
				term.append(" and ");
			} else {
				term.append(" where ");
			}
			term.append(" state="+state);
		}
		if (Consts.CURR_FOUNDER0!=founder&&Consts.CURR_FOUNDER1!=founder) {
			if (term.length() > 0) {
				term.append(" and ");
			} else {
				term.append(" where ");
			}
			term.append(" user_id='"+adminUser.getUserid()+"'");
		}
		sql.append(term);
		//AdminUser adminUser = ManagerUtils.getAdminUser();
		String regCheck = ".*(?i)\\bwhere\\b.*";
		
		if(sql.toString().matches(regCheck)){
			sql.append(" and v.source_from = '" + ManagerUtils.getSourceFrom() + "' ");
		}else {
			sql.append(" where v.source_from = '" + ManagerUtils.getSourceFrom() + "' ");
		}		
		order = order == null ? " aid desc" : order;
		sql.append(" order by " + order);
		Page page = this.daoSupport.queryForPage(sql.toString(), pageNo,
				pageSize);
		return page;
	}

	@Override
	public Page search(String acid, String source_from, String cname,int state,int pageNo, int pageSize,
			String order) {
		AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
		int founder = adminUser.getFounder();
		StringBuffer term = new StringBuffer();
		StringBuffer sql = new StringBuffer("select v.*, c.cname   cname from "
				+ this.getTableName("adv") + " v left join "
				+ this.getTableName("adcolumn") + " c on c.acid = v.acid and c.source_from = v.source_from ");

		if (!StringUtil.isEmpty(acid)&&acid != null) {
			term.append(" where  c.acid=" + acid);
		}

		if (!StringUtil.isEmpty(cname)) {
			if (term.length() > 0) {
				term.append(" and ");
			} else {
				term.append(" where ");
			}

			term.append(" aname like'%" + cname + "%'");
		}
		if(state!=-1){
			if (term.length() > 0) {
				term.append(" and ");
			} else {
				term.append(" where ");
			}
			term.append(" state="+state);
		}
		if (Consts.CURR_FOUNDER0!=founder&&Consts.CURR_FOUNDER1!=founder) {
			if (term.length() > 0) {
				term.append(" and ");
			} else {
				term.append(" where 1=1");
			}
			term.append(" and (v.user_id='"+adminUser.getUserid()+"' or v.user_id in " +
					"(select b.userid from es_adminuser b where b.paruserid = '"+ManagerUtils.getUserId()+"'" +
							" "+ManagerUtils.apSFromCond("b")+"))");
		}
		if(!StringUtil.isEmpty(source_from)){
			if (term.length() > 0) {
				term.append(" and ");
			} else {
				term.append(" where ");
			}
			if("ALL".equals(source_from)){
				term.append(" v.source_from is not null ");
			}else{
				term.append(" v.source_from='"+source_from+"' ");
			}
		}else {
			if (term.length() > 0) {
				term.append(" and ");
			} else {
				term.append(" where ");
			}			
			term.append(" v.source_from is not null ");
		}			
		sql.append(term);
		//AdminUser adminUser = ManagerUtils.getAdminUser();
		order = order == null ? " aid desc" : order;
		sql.append(" order by " + order);
		Page page = this.daoSupport.queryForPage(sql.toString(), pageNo,
				pageSize);
		return page;
	}
	
    @Override
	public int getNoAuditAdvNum(){
    	int result=0;
    	String sql="select count(*) from "+this.getTableName("adv")+" where state=?";
    	result=this.daoSupport.queryForInt(sql, 0);
    	return result;
    }
	
	@Override
	public int getAllAdvNum(){
		int result=0;
    	String sql="select count(*) from "+this.getTableName("adv");
    	result=this.daoSupport.queryForInt(sql);
    	return result;
		
	}
	
	
	@Override
	public Page listAdvPage(Adv adv,int pageNo,int pageSize,String isPublic){
		
		StringBuffer sql = new StringBuffer();
		StringBuffer cSql = new StringBuffer();
		StringBuffer where = new StringBuffer();
		
		sql.append("SELECT b.* FROM es_adv b");
		cSql.append("SELECT count(1) FROM es_adv b");
		
		where.append(" WHERE b.source_from = '"+ManagerUtils.getSourceFrom()+"' AND b.isclose='0'");
		if(StringUtils.isNotEmpty(isPublic)){
			if("1".equals(isPublic)){//公有素材
				where.append(" AND ( b.user_id='-1') ");
			}else{
				where.append(" AND (b.user_id = '"+ManagerUtils.getUserId()+"' ");
				where.append(" OR b.user_id in (SELECT a.userid FROM es_adminuser a WHERE a.paruserid = '"+ManagerUtils.getUserId()+"' "+ManagerUtils.apSFromCond("a")+"))");
			}
		}else{
			where.append(" AND (b.user_id = '"+ManagerUtils.getUserId()+"' OR b.user_id='-1' OR b.user_id in (SELECT a.userid FROM es_adminuser a WHERE a.paruserid = '"+ManagerUtils.getUserId()+"' "+ManagerUtils.apSFromCond("a")+")) ");
		}
		
		if(StringUtils.isNotEmpty(adv.getAtype())){
			where.append(" AND b.atype = '"+adv.getAtype()+"'");
		}
		if(StringUtils.isNotEmpty(adv.getSubtype())){
			where.append(" AND b.subtype = '"+adv.getSubtype()+"'");
		}
		if(StringUtils.isNotEmpty(adv.getAname())){
			where.append(" AND b.aname LIKE '%"+adv.getAname()+"%'");
		}
		
		where.append(" ORDER BY b.create_date desc");
		
		return this.baseDaoSupport.queryForCPage(sql.append(where).toString(), pageNo, pageSize, 
						Adv.class, cSql.append(where).toString());
	}

	@Override
	public List<Adv> listAdv(Map<String, String> params){
		String staff_no = Const.getStrValue(params, "staff_no");
		String sql = "select a.*,'' cname from es_adv a where a.disabled='false' and a.state=1 ";
		List pList = new ArrayList();
		if(!StringUtils.isEmpty(staff_no)){
			sql += " and a.staff_no=? ";
			pList.add(staff_no);
		}
		sql += " order by create_date desc ";
		List<Adv> advList = this.baseDaoSupport.queryForList(sql, new AdvMapper(), pList.toArray());
		return advList;
	}
}