package com.ztesoft.net.mall.core.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.net.consts.GoodsConsts;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.service.ITagManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

/**
 * Saas式的标签管理
 * @author kingapex
 * 2010-1-18上午10:57:35
 */
public class TagManager extends BaseSupport<Tag> implements ITagManager {
	
	private IDaoSupport  daoSupport;
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean checkname(String name,String tagid){
		if(name!=null)name=name.trim();
		if(tagid==null) tagid="0";
		String sql = SF.goodsSql("TAGS_GET_COUNT");
		int count  = this.baseDaoSupport.queryForInt(sql, name,tagid);
		if(count>0)
			return true;
		else
			return false;
	}
	
	@Override
	public void add(Tag tag) {
		tag.setRel_count(0);
		this.baseDaoSupport.insert("tags", tag);
		
	}

	@Override
	public boolean checkJoinGoods(String[] tagids) {
		if(tagids==null ) return false;
		String ids =StringUtil.implode(",", tagids);
		String sql = SF.goodsSql("CHECK_JOIN_GOODS")+ " and tag_id in(" + ids + ")";
		int count  = this.baseDaoSupport.queryForInt(sql);
		if(count>0)
			return true;
		else
			return false;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public void delete(String[] tagids) {
		String ids =StringUtil.implode(",", tagids);
		if(ids==null || ids.equals("")){return ;}
		//删除标签，同时删除标签的引用对照表
		this.baseDaoSupport.execute(SF.goodsSql("TAGS_DEL_BY_ID") + " and tag_id in(" + ids +")");
		this.daoSupport.execute(SF.goodsSql("TAG_REL_DEL_BY_IDS") + " and tag_id in(" + ids + ")");
	}

	
	@Override
	public Page list(int pageNo, int pageSize) {
		
		String sql  = SF.goodsSql("QRY_TAGS_LIST_GET");
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, 
				GoodsConsts.DC_PUBLIC_STYPE_BQLX, ManagerUtils.getSourceFrom());
	}

	@Override
	public Page goodlist(int pageNo,int pageSize) {
		//拼接商品标签sql
//		String sql  = SF.goodsSql("QRY_GOODS_TAG_LIST_GET");
		return this.baseDaoSupport.queryForPage("select * from es_goods_tag", pageNo, pageSize, null);
	}
	
	@Override
	public Page shoplist(int pageNo,int pageSize) {
		//拼接营销标签sql
		return this.baseDaoSupport.queryForPage("select * from es_sale_tag", pageNo, pageSize, null);
	}
	
	@Override
	public Page showTag(String table,int pageNo,int pageSize) {
		String sql = "select * from " + table + " where tag_type = 'T'";
		
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, null);
	}
	
	@Override
	public Page showTagList(String table,int pageNo,int pageSize) {
		String sql = "select * from " + table + " where tag_type = 'G'";
		
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, null);
	}
	
	@Override
	public Page searchTag(String table,int pageNo,int pageSize,String tag_name,String tag_type) {
		StringBuffer sb = new StringBuffer("select * from "+table+" where 1=1 ");
		List<String> paramsList = new ArrayList<String>();
		
		if(tag_name != null && !"".equals(tag_name)) {
			sb.append(" and tag_name = ? ");
			paramsList.add(tag_name);
		}
		
		if(tag_type != null && !"".equals(tag_type)) {
			sb.append(" and tag_type = ? ");
			paramsList.add(tag_type);
		}
		
		return this.baseDaoSupport.queryForPage(sb.toString(), pageNo, pageSize, paramsList.toArray());
		
	}
	
	@Override
	public void update(Tag tag) {

		this.baseDaoSupport.update("tags", tag, "tag_id="+tag.getTag_id());
		
	}

	@Override
	public IDaoSupport<Tag> getDaoSupport() {
		return daoSupport;
	}

	@Override
	public void setDaoSupport(IDaoSupport<Tag> daoSupport) {
		this.daoSupport = daoSupport;
	}

	
	@Override
	public Tag getById(String tagid) {
		String sql  = SF.goodsSql("TAGS_GET_BY_ID");
		Tag tag = this.baseDaoSupport.queryForObject(sql, Tag.class, tagid);
		return tag;
	}

	
	@Override
	public List<Tag> list() {
		String sql  = SF.goodsSql("TAGS_LIST_ALL");
		return this.baseDaoSupport.queryForList(sql,Tag.class);
	}
	

	@Override
	public List<Tag> listEdit() {
//		String appendSql ="";
//		if(!ManagerUtils.isAdminUser())
//		{
//			appendSql =" where ower_type in('agent','public')";
//		}else{
//			appendSql =" where ower_type in('admin','public')"; //管理员选择
//		}
//		String sql  ="select * from tags" +appendSql;
		String sql = SF.goodsSql("TAGS_LIST_ALL_EDIT");
		return this.baseDaoSupport.queryForList(sql,Tag.class);
	}
	
	

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)  
	public void saveRels(String relid, String[] tagids) {
		//清空原有引用
		String sql = SF.goodsSql("TAG_REL_DEL_BY_ID");
//		if(!ManagerUtils.isAdminUser()) //商户不能删除管理员的标签
//			sql = "delete from "+ this.getTableName("tag_rel") +"   where rel_id=? and tag_id not in (1,3)"; //add by wui商户关联时不能删除主页的
//		else //管理员不能删除商户的
//			sql = "delete from "+ this.getTableName("tag_rel") +"   where rel_id=? and tag_id not in (91,92)"; //add by wui管理员不能删除商户的
		this.daoSupport.execute(sql, relid);
		if(tagids!=null ){
			
			//重新对照新的引用
			sql = SF.goodsSql("TAG_REL_INSERT");
			for(String tagid:tagids){
				this.daoSupport.execute(sql, tagid,relid);
			}
		}
	}

	
	@Override
	public List<String> list(String relid) {
		String sql= SF.goodsSql("TAG_REL_GET_BY_ID");
		List<String> tagids = this.jdbcTemplate.queryForList(sql, new String[]{relid}, String.class);
		return tagids;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Tag> ListByCatType(String catType) {
		String sql = SF.goodsSql("TAG_GET_BY_CAT_TYPE");
		List<Tag> tagLst = this.daoSupport.queryForList(sql, Tag.class, new String[]{catType});
		return tagLst;
	}
	
	@Override
	public void insertTag(String table,Tag tag) {
		this.baseDaoSupport.insert(table, tag);
	}
	
	@Override
	public long countTagByCode(String table,String tag_code) {
		String sql = "select count(*) from "+table+" where tag_code = ?";
		
		logger.info(sql);
		return this.baseDaoSupport.queryForLong(sql, tag_code);
		
	}
		
	@Override
	public void invaliTag(String table,String[] tag_codes) {
		String ids =StringUtil.implode(",", tag_codes);

		String sb = new String();
		for (String code : tag_codes) {
			sb +="'"+code+"',";
		}
		sb = sb.substring(0, sb.length()-1);
		String sql = "update "+table+" set tag_status = '1' where tag_code in ("+sb.toString()+")";
		
		this.baseDaoSupport.execute(sql);
	}
	
	@Override
	public void syncTag(String table ,String[] tag_ids) {
		for (String tag_id : tag_ids) {
			String serial_no = this.baseDaoSupport.getSequences("tag_serial_no");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = sdf.format(new Date());
			
			
		}
	}
	
	@Override
	public void deleteTag(String table,String tag_code) {
		String sql = "delete from "+table+" where tag_code = ?";
		
		this.baseDaoSupport.execute(sql, tag_code);
	}
	
	@Override
	public Tag getByCode(String table,String tag_code) {
		return this.baseDaoSupport.queryForObject("select * from " + table + " where 1=1 and tag_id = ?", Tag.class, tag_code);
	}
	
	@Override
	public void updateTagById(String table,String tag_id,Map tagMap) {
		Map map = new HashMap();
		map.put("tag_id", tag_id);
		this.baseDaoSupport.update(table,tagMap , map);
	}
	
	@Override
	public List qryTree() {
		List list = new ArrayList();
		try {
			String sql = SF.goodsSql("GOODS_ORG_TREE");
			list = baseDaoSupport.queryForList(sql,ManagerUtils.getSourceFrom());
			if(list.size()>0){
				childList(list);
			}
		} catch (Exception e) {
			logger.info(e);
		}
		return list;
	}
	
	public void childList(List list){
		HashMap fm = new HashMap();
        for(int i = 0,lsize = list.size();i<lsize;i++){
        	fm = (HashMap)list.get(i);
        	String level =  fm.get("org_level").toString();
        	List listChild = new ArrayList();
        	String sql1 = SF.goodsSql("GOODS_ORG_TREE_1");
         	String sql2 = SF.goodsSql("GOODS_ORG_TREE_2");
        	if(level.equals("1")){
        		listChild = baseDaoSupport.queryForList(sql1,fm.get("party_id").toString(),ManagerUtils.getSourceFrom());
        	}else{
        		listChild = baseDaoSupport.queryForList(sql2,fm.get("party_id").toString(),ManagerUtils.getSourceFrom());
        	}
        	if(listChild.size()>0){
        		fm.put("sub_node", listChild);
				childList(listChild);
			}
        }
	}
}
