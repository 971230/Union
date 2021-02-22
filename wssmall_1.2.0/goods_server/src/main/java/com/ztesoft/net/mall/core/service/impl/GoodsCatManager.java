package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.cache.common.GoodsNetCacheRead;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.mapper.CatMapper;
import com.ztesoft.net.mall.core.model.mapper.GoodsListMapper;
import com.ztesoft.net.mall.core.model.support.GoodsView;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsCatManager extends BaseSupport implements IGoodsCatManager {
	private GoodsNetCacheRead read = new GoodsNetCacheRead();
	
	@Override
	public List<Cat> findCatNodesByParentid(String parentid) {
		String sql = SF.goodsSql("FIND_CATNODES_BY_PARENTID");	
		return this.baseDaoSupport.queryForList(sql, Cat.class, parentid);
	}
	
	@Override
	public boolean checkname(String name,String catid){
		if(name!=null)name=name.trim();
		String sql = SF.goodsSql("GOODS_CAT_CHECK_NAME");
		if(catid==null){
			catid="0";
		}
		
		int count  = this.baseDaoSupport.queryForInt(sql, name,catid);
		if(count>0) return true;
		else 		return false;
	}
	
	@Override
	public int delete(String catId) {
		String sql = SF.goodsSql("GET_GOODS_CAT_COUNT");
		int count = this.baseDaoSupport.queryForInt(sql,  catId );
		if (count > 0) {
			return 1; // 有子类别
		}

		sql = SF.goodsSql("GET_GOODS_COUNT_BY_CAT_ID");
		count = this.baseDaoSupport.queryForInt(sql,  catId );
		if (count > 0) {
			return 2; // 有商品
		}
		sql = SF.goodsSql("GOODS_CAT_DELETE");
		this.baseDaoSupport.execute(sql,  catId );

		return 0;
	}
	
	/**
	 * 获取类别详细，将图片加上静态资源服务器地址
	 */
	@Override
	public Cat getById(String catId) {
		String sql = SF.goodsSql("GET_GOODS_CAT_BY_ID");
		Cat cat =(Cat) baseDaoSupport.queryForObject(sql, Cat.class, catId);
		if(cat == null)
			cat = new Cat();
		String image = cat.getImage();
		if(image!=null){
			image  =UploadUtil.replacePath(image); 
			cat.setImage(image);
		}
		String hot_type = cat.getHot_type();
		if(StringUtil.isEmpty(hot_type)){
			cat.setHot_type("0");
		}
		return cat;
	}
	
	
	/**
	 * 根据分类获取关联商品信息
	 * @param catId
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<GoodsView> getComplexGoodsByCatId(String catId) {
        String sql=" select b.* from es_cat_complex g,es_goods b where g.goods_id = b.goods_id  and " +
                " b.disabled='"+ Consts.GOODS_DISABLED_0+"' and b.market_enable=1 AND g.source_from=g.source_from AND " +
                " g.source_from='"+ManagerUtils.getSourceFrom()+"' "+_getComplexGoodsByCatId(catId)+" ORDER BY g.create_time DESC";

        return  daoSupport.queryForList(sql.toString(), 1, 50,new GoodsListMapper()); //queryForList(sql,new Object []{});
	}
	
	//ManagerUtils.getGoodsId()
	@Override
	@SuppressWarnings("unused")
	public String _getComplexGoodsByCatId(String cat_id) {

		String appendSql = "";
		HttpServletRequest request  =ThreadContextHolder.getHttpRequest(); 
		//String url = request.getServletPath();
		if (!StringUtil.isEmpty(cat_id)) {
			appendSql += " and  g.cat_id in(";
			appendSql += "select c.cat_id from "
					+ this.getTableName("goods_cat")
					+ " c where c.source_from='"+ManagerUtils.getSourceFrom()+"' and c.cat_path like '" + getCatPathById(cat_id) + "%')";
		}
		return appendSql;
	}
	
	
	@Override
	public String getCatPathById(String catId) {
		String sql = SF.goodsSql("GET_GOODS_CAT_PATH");
		String cat_path=baseDaoSupport.queryForString(sql, catId);
		return cat_path;
	}
	

	public String getCatPathByGoodsId(String goodsId) {
		String sql = SF.goodsSql("GET_CATPATH_BY_GOODSID");
		String cat_path=baseDaoSupport.queryForString(sql, goodsId);
		return cat_path;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List getCatsByGoodsId(String goodsId) {
		String sql = SF.goodsSql("GET_CATS_BY_GOODSID") + _getCatsByGoodsId(goodsId);
		return baseDaoSupport.queryForList(sql, new Object []{});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List getHotCatsByCatId(String cat_id) {
		String sql = SF.goodsSql("GET_HOT_CATS_BY_CATID") + _getComplexGoodsByCatId(cat_id)+"  and hot_type =1 ORDER BY cat_path";
		return baseDaoSupport.queryForList(sql, new Object []{});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getCatsByCond(String cat_path) {
		String whereCond = "";
		if(StringUtils.isNotEmpty(cat_path)){
			whereCond = " and cat_path = '"+cat_path+"'";
		}
		String sql = SF.goodsSql("GET_CATS_BY_COND") +whereCond+" ORDER BY cat_path";
		return baseDaoSupport.queryForList(sql, new Object []{});
	}
	
	@Override
	public List<Cat> listCatsByUserid(String userid){
		String whereCond = "";
		if(StringUtils.isNotEmpty(userid)){
			whereCond = " and user_id = '"+userid+"'";
		}
		String sql = SF.goodsSql("GET_CATS_BY_COND") +whereCond+" ORDER BY cat_path";
		return baseDaoSupport.queryForList(sql, Cat.class, new Object []{});
	}
	
	@Override
	public List getCatsByWhereCond(String whereCond){
		String sql = SF.goodsSql("GET_CATS_BY_COND");
		if(StringUtils.isNotEmpty(whereCond)){
			sql += whereCond;
		}
		sql += " ORDER BY cat_path";
		return baseDaoSupport.queryForList(sql, new Object []{});
	}
	
	
	//ManagerUtils.getGoodsId()
	@SuppressWarnings("unused")
	public String _getCatsByGoodsId(String goods_id) {

		String appendSql = "";
		HttpServletRequest request  =ThreadContextHolder.getHttpRequest(); 
		//String url = request.getServletPath();
		if (!StringUtil.isEmpty(goods_id)) {
			appendSql += " and  g.cat_id in(";
			appendSql += "select c.cat_id from "
					+ this.getTableName("goods_cat")
					+ " c where c.cat_path like '" + getCatPathByGoodsId(goods_id) + "%')";
		}
		return appendSql;
	}
	
	@Override
	public String getParentIdById(String catId) {
		String sql = SF.goodsSql("GET_PARENTID_BYID");
		String parentId=baseDaoSupport.queryForString(sql, catId);
		return parentId;
	}
	@Override
	public List listChildren(String catId) {
		 String sql = SF.goodsSql("LIST_CHILDREN");
		return this.baseDaoSupport.queryForList(sql,new CatMapper(), catId);
	}
	
	
	
	@Override
	public List<Cat> listAllChildren(String catId) {
		String agtId =StringUtil.getAgnId();
		String appendSql ="";
//		if(!StringUtil.isEmpty(agtId)){
//			String cat_ids = daoSupport.queryForString("select cat_ids from es_agent where staff_no = "+agtId);
//			appendSql +=" and cat_id in ("+cat_ids+")";
//		}
		if(ThreadContextHolder.isMemberLvCache()){
			String lv_id = ManagerUtils.getCurrentLvId();
			appendSql += " and exists (select 1 from es_goods_lv_cat gl where gl.cat_id=c.cat_id and gl.lv_id='"+lv_id+"' and gl.source_from = c.source_from) ";
		}
		String sql = SF.goodsSql("LIST_ALL_CHILDREN") + appendSql
			+ " order by parent_id,cat_order";
		List<Cat> allCatList = daoSupport.queryForList(sql, new CatMapper());
		List<Cat> topCatList  = new ArrayList<Cat>();
		if(!catId.equals("0")){
			Cat cat = this.getById(catId);
			topCatList.add(cat);
		}
		for(Cat cat :allCatList){
			if(cat.getParent_id()!=null && cat.getParent_id().compareTo(catId)==0){
				if(this.logger.isDebugEnabled()){
					this.logger.debug("发现子["+cat.getName()+"-"+cat.getCat_id() +"]"+cat.getImage());
				}
				List<Cat> children = this.getChildren(allCatList, cat.getCat_id());
				cat.setChildren(children);
				topCatList.add(cat);
			}
		}
		return topCatList;
	}
	
	@Override
	public List<Cat> listAllChildrenEcs(String catId,String type) {
		String agtId =StringUtil.getAgnId();
		String appendSql ="";
//		if(!StringUtil.isEmpty(agtId)){
//			String cat_ids = daoSupport.queryForString("select cat_ids from es_agent where staff_no = "+agtId);
//			appendSql +=" and cat_id in ("+cat_ids+")";
//		}
		if(ThreadContextHolder.isMemberLvCache()){
			String lv_id = ManagerUtils.getCurrentLvId();
			appendSql += " and exists (select 1 from es_goods_lv_cat gl where gl.cat_id=c.cat_id and gl.lv_id='"+lv_id+"' and gl.source_from = c.source_from) ";
		}
		if(StringUtils.isNotEmpty(type)){
			appendSql += " and c.type='"+type+"' ";
		}
		String sql = SF.goodsSql("LIST_ALL_CHILDREN") + appendSql
			+ " order by parent_id,cat_order";
		List<Cat> allCatList = daoSupport.queryForList(sql, new CatMapper());
		List<Cat> topCatList  = new ArrayList<Cat>();
		if(!catId.equals("0")){
			Cat cat = this.getById(catId);
			topCatList.add(cat);
		}
		for(Cat cat :allCatList){
			if(cat.getParent_id()!=null && cat.getParent_id().compareTo(catId)==0){
				if(this.logger.isDebugEnabled()){
					this.logger.debug("发现子["+cat.getName()+"-"+cat.getCat_id() +"]"+cat.getImage());
				}
				List<Cat> children = this.getChildren(allCatList, cat.getCat_id());
				cat.setChildren(children);
				topCatList.add(cat);
			}
		}
		return topCatList;
	}
 
	
	@Override
	public List<Cat> listAllChildrenByAgentNo(String catId) {

		AdminUser adminUser =ManagerUtils.getAdminUser();
		String appendSql ="";
//		if(!ManagerUtils.isAdminUser()){
//			String cat_ids = daoSupport.queryForString("select cat_ids from es_agent where staff_no = "+adminUser.getUserid());
//			appendSql +=" and cat_id in ("+cat_ids+")";
//		}
		String sql = SF.goodsSql("LIST_ALL_CHILDREN_BY_AGENTNO");
		
		List<Cat> allCatList = daoSupport.queryForList(sql, new CatMapper());
		List<Cat> topCatList  = new ArrayList<Cat>();
		if(!catId.equals("0")){
			Cat cat = this.getById(catId);
			topCatList.add(cat);
		}
		for(Cat cat :allCatList){
			if(cat.getParent_id()!=null && cat.getParent_id().compareTo(catId)==0){
				if(this.logger.isDebugEnabled()){
					this.logger.debug("发现子["+cat.getName()+"-"+cat.getCat_id() +"]"+cat.getImage());
				}
				List<Cat> children = this.getChildren(allCatList, cat.getCat_id());
				cat.setChildren(children);
				topCatList.add(cat);
			}
		}
		return topCatList;
	}
	

	@Override
	public List<Cat> listAllChildrenForSecond(String catId) {

		String sql = SF.goodsSql("LIST_ALL_CHILDRENFOR_SECOND"); 		// this.findSql("all_cat_list");

		List<Cat> allCatList = daoSupport.queryForList(sql, new CatMapper());
		List<Cat> topCatList  = new ArrayList<Cat>();
		if(!catId.equals("0")){
			Cat cat = this.getById(catId);
			topCatList.add(cat);
		}
		for(Cat cat :allCatList){
			if(cat.getParent_id().equals(catId)){
				if(this.logger.isDebugEnabled()){
					this.logger.debug("发现子["+cat.getName()+"-"+cat.getCat_id() +"]"+cat.getImage());
				}
				List<Cat> children = this.getChildren(allCatList, cat.getCat_id());
//				for (Cat level1_cat:children) { //add by wui选择分类的时候只到两级分类
//					level1_cat.setChildren(new ArrayList<Cat>());
//				}
				cat.setChildren(children);
				topCatList.add(cat);
			}
		}
		return topCatList;
	}
	
	
	private List<Cat> getChildren(List<Cat> catList ,String parentid){
		if(this.logger.isDebugEnabled()){
			this.logger.debug("查找["+parentid+"]的子");
		}
		List<Cat> children =new ArrayList<Cat>();
		for(Cat cat :catList){
			if(cat!=null && parentid!=null && cat.getParent_id()!=null && parentid.equals(cat.getParent_id())){
				if(this.logger.isDebugEnabled()){
					this.logger.debug(cat.getName()+"-"+cat.getCat_id()+"是子");
				}
			 	cat.setChildren(this.getChildren(catList, cat.getCat_id()));
				children.add(cat);
			}
		}
		return children;
	}
	

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveAdd(Cat cat) {
		 
		baseDaoSupport.insert("es_goods_cat", cat);
		String cat_id = cat.getCat_id();
		String sql = "";
		//判断是否是顶级类似别，如果parentid为空或为0则为顶级类似别
		//注意末尾都要加|，以防止查询子孙时出错
		if (cat.getParent_id() != null && !cat.getParent_id().equals("0")) { //不是顶级类别，有父
			sql = SF.goodsSql("GET_GOODS_CAT_BY_ID_0");
			Cat parent = (Cat) baseDaoSupport.queryForObject(sql, Cat.class, cat
					.getParent_id());
			cat.setCat_path(parent.getCat_path()  + cat_id+"|"); 
		} else {//是顶级类别
			cat.setCat_path(cat.getParent_id() + "|" + cat_id+"|");
		}

		sql = SF.goodsSql("UPDATE_GOODS_CAT_PATH");
		baseDaoSupport.execute(sql, new Object[] { cat.getCat_path(), cat_id });

	}

	
	@Override
	public void update(Cat cat) {
		checkIsOwner(cat.getCat_id());
		// 如果有父类别，根据父的path更新这个类别的path信息
		if (cat.getParent_id() != null && !cat.getParent_id().equals("0")) {

			String sql = SF.goodsSql("GOODS_CAT_SELECT_BY_ID");
			Cat parent = (Cat) baseDaoSupport.queryForObject(sql, Cat.class, cat
					.getParent_id());
			cat.setCat_path(parent.getCat_path() + cat.getCat_id()+"|");
		} else {
			// 顶级类别，直接更新为parentid|catid
			cat.setCat_path(cat.getParent_id() + "|" + cat.getCat_id()+"|");
		}

		HashMap map = new HashMap();
		map.put("type", cat.getType());
		map.put("name", cat.getName());
		map.put("parent_id", cat.getParent_id());
		map.put("cat_order", cat.getCat_order());
		map.put("type_id", cat.getType_id());
		map.put("floor_list_show", cat.getFloor_list_show());
		map.put("cat_path", cat.getCat_path());
		map.put("list_show", cat.getList_show());
		map.put("image", StringUtil.isEmpty(cat.getImage().trim())?null:cat.getImage());
		map.put("hot_type", cat.getHot_type());
		baseDaoSupport.update("es_goods_cat", map, "cat_id=" + cat.getCat_id());
	}

	
	protected void checkIsOwner(String catId) {
//		String sql = "select userid from  goods_cat  where cat_id=?";
//		int userid = saasDaoSupport.queryForInt(sql, catId);
//		super.checkIsOwner(userid);
	}

	

	/**
	 * 保存分类排序
	 * 
	 * @param cat_ids
	 * @param cat_sorts
	 */
	@Override
	public void saveSort(String[] cat_ids, int[] cat_sorts) {
		String sql = "";
		if (cat_ids != null) {
			for (int i = 0; i < cat_ids.length; i++) {
			    sql = SF.goodsSql("SAVE_SORT");
			    baseDaoSupport.execute(sql,  cat_sorts[i], cat_ids[i] );
			}
		}
	}

	@Override
	public List getNavpath(String catId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * 获取热词
	 */
	public List<Cat> getHotList() {
		String sql = SF.goodsSql("GET_HOT_LIST");
        String countSql = SF.goodsSql("GET_HOT_LIST_COUNT");
        Page page=daoSupport.queryForPage(sql,countSql,1,6,new CatMapper());
        List<Cat> hotCatList=page.getResult();
		return hotCatList;
	}

	@Override
	public int editFloorListShowById(String id, String floor_list_show) {
		String tableName =this.getTableName("es_goods_cat");
		HashMap map = new HashMap();
		map.put("floor_list_show",floor_list_show);
		return baseDaoSupport.update(tableName, map, "cat_id="+id,null);
		
	}
	
	@Override
	public void deleteMemberLvById(String id, String lv_id) {
		String[] arrayCatId=id.split(",");
		for(int i=0;i<arrayCatId.length;i++){
			String sql = SF.goodsSql("DELET_EMEMBERLV_BY_ID");
			baseDaoSupport.execute(sql, arrayCatId[i].trim(),lv_id);
		}
		
	}
	
	@Override
	public void addMemberLvById(String id, String lv_id) {
		String[] arrayCatId=id.split(",");
		for(int i=0;i<arrayCatId.length;i++){
			String tableName =this.getTableName("es_goods_lv_cat");
			HashMap fields = new HashMap();
			fields.put("cat_id",arrayCatId[i].trim());
			fields.put("lv_id",lv_id);
			baseDaoSupport.insert(tableName, fields);
		}
	}
	
	@Override
	public Cat getCatByGoodId(String goodId){
		Cat result=null;
		String sql = SF.goodsSql("GET_CAT_BY_GOODID");
		result=(Cat) this.baseDaoSupport.queryForObject(sql, Cat.class, goodId);
		String[] catPathArray=result.getCat_path().split("\\|");
		String cat_id="";
		if (catPathArray.length>1) {
			cat_id=catPathArray[1];
		}else {
			cat_id=result.getCat_id();
		}
		result=(Cat) this.baseDaoSupport.queryForObject(SF.goodsSql("GET_GOODS_CAT_BY_ID_1"), Cat.class, cat_id);
		return result;
	}

	@Override
	public List listMemberLv() {
		String sql = SF.goodsSql("LIST_MEMBER_LV");
		return this.baseDaoSupport.queryForList(sql);
	}

	@Override
	public List listCatGoodsCount(String agn,String parent_cat_id) {
		String sql = SF.goodsSql("LIST_CAT_GOODS_COUNT");
		String cond = "";
		if(agn!=null || !"".equals(agn)){
			cond = " and a.staff_no='"+agn+"'";
		}
		sql = sql.replace("#cond", cond);
		List cats = this.baseDaoSupport.queryForList(sql, Cat.class,parent_cat_id);
		return cats;
	}

	@Override
	public List<Cat> listCats(String cat_id) {
		String sql = SF.goodsSql("CAT_LIST");
		List pList = new ArrayList();
		if(!"-1".equals(cat_id)){
			sql += " and cat_id=? ";
			pList.add(cat_id);
		}
		List<Cat> cats = this.baseDaoSupport.queryForList(sql, Cat.class, pList.toArray());
		return cats;
	}

	@Override
	public void doPublish(Map params) {
		String org_id_str = ManagerUtils.getStrValue(params, "orgIds");
		this.doCoQueue(org_id_str);  //全量发布
	}
	
	/**
	 * 写同步消息队列
	 * @param org_id_str  销售组织标识串
	 */
	private void doCoQueue(String org_id_str) {
		
		//写消息队列
		CoQueueAddReq coQueueAddReq = new CoQueueAddReq();
		coQueueAddReq.setCo_name("分类同步");
		coQueueAddReq.setBatch_id("-1");
		coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_FENLEI);
		coQueueAddReq.setAction_code(Consts.ACTION_CODE_A);
		coQueueAddReq.setObject_id("-1");
		coQueueAddReq.setObject_type("FENLEI");
		coQueueAddReq.setContents("-1");
		coQueueAddReq.setOrg_id_str("10008");
		coQueueAddReq.setOrg_id_belong("10008");  //新商城
		coQueueAddReq.setSourceFrom(ManagerUtils.getSourceFrom());
		coQueueAddReq.setOper_id(ManagerUtils.getAdminUser().getUserid());
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		client.execute(coQueueAddReq, CoQueueAddResp.class);
	}

	@Override
	public List<Cat> listCatsByTypeId(String type_id) {
		String sql = SF.goodsSql("GOODS_CAT_LIST_BY_TYPE");
		return this.baseDaoSupport.queryForList(sql, Cat.class, type_id);
	}

	@Override
	public List<Cat> listCacheCatByTypeId(String type_id) {
		List<Cat> cats = read.getCatsByTypeId(type_id);
		return cats;
	}
}
