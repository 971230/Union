package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.processor.core.EopException;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Comments;
import com.ztesoft.net.mall.core.model.support.CommentDTO;
import com.ztesoft.net.mall.core.service.ICommentsManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 咨询、评论管理
 * 
 * @author 李志富 lzf<br/>
 *         2010-1-26 下午03:47:54<br/>
 *         version 1.0<br/>
 * <br/>
 */
public class CommentsManager extends BaseSupport<Comments> implements
		ICommentsManager {
	
	
	@Override
	public void addComments(Comments comments) {
		this.baseDaoSupport.insert("comments", comments);
	}
	
	
	@Override
	public void deleteComments(String ids){
		if (ids == null || ids.equals(""))
			return;
		String sql = SF.goodsSql("DELETE_COMMENTS") + " and comment_id in (" + ids + ")";
		this.baseDaoSupport.execute(sql);
	}

	
	@Override
	public void cleanComments(String ids) {
		if (ids == null || ids.equals(""))
			return;
		String sql = SF.goodsSql("CLEAN_COMMENTS") + " and comment_id in (" + ids + ") or for_comment_id in ("+ ids + ")";
		this.baseDaoSupport.execute(sql);
	}

	
	@Override
	public CommentDTO getComments(String commentId) {
		String sql = SF.goodsSql("GET_COMMENTS");
		Comments comments = baseDaoSupport.queryForObject(sql, Comments.class,
				commentId);

		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setComments(comments);
		List<Comments> list = baseDaoSupport.queryForList(SF.goodsSql("GET_COMMENTS_BY_FOR_COMMENT_ID")
				,
				Comments.class, commentId);
		commentDTO.setList(list);
		return commentDTO;
	}

	
	@Override
	public void revert(String ids) {
		if (ids == null || ids.equals(""))
			return;
		String sql = SF.goodsSql("COMMENTS_REVERT") + " and comment_id in (" + ids + ")";
		this.baseDaoSupport.execute(sql);
	}

	
	@Override
	public void updateComments(Comments comments) {
		this.baseDaoSupport.update("comments", comments, "comment_id="
				+ comments.getComment_id());

	}
	
	
	@Override
	public Page pageComments_Display(int pageNo, int pageSize,
			Long object_id, String object_type){
		String sql = SF.goodsSql("PAGE_COMMENTS_DISPLAY_0");
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, object_id, object_type);
		/*String userid = EopContext.getContext().getCurrentSite().getUserid();
		String siteid = EopContext.getContext().getCurrentSite().getId();*/
		String sql_id_list = SF.goodsSql("PAGE_COMMENTS_DISPLAY_1");
		List<Comments> listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, object_id, object_type);
		List<Map> list = (page.getResult());
		for (Map comments : list) {
			List<Comments> child = new ArrayList<Comments>();
			for(Comments reply:listReply){
				if(reply.getFor_comment_id().equals(comments.get("comment_id").toString())){
					child.add(reply);
				}
			}
			comments.put("list", child);
			comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
		}
		return page;
	}
	
	/**
	 * 读取某个类型的评论列表
	 * @param pageNo
	 * @param pageSize
	 * @param object_id
	 * @param commenttype
	 * @return
	 */
	@Override
	public Page listAll(int pageNo, int pageSize,
			Integer object_id, String commenttype){
		String sql = SF.goodsSql("COMMENTS_LIST_ALL_0");
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, object_id, commenttype);
	/*	String userid = EopContext.getContext().getCurrentSite().getUserid();
		String siteid = EopContext.getContext().getCurrentSite().getId();*/
		String sql_id_list = SF.goodsSql("COMMENTS_LIST_ALL_1");
		List<Comments> listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, object_id, commenttype);
		List<Map> list = (page.getResult());
		for (Map comments : list) {
			Timestamp ts = (Timestamp)comments.get("time");
			Date date = new Date();
			date = ts;
			comments.put("date", date);
			List<Comments> child = new ArrayList<Comments>();
			for(Comments reply:listReply){
				if(reply.getFor_comment_id().equals(comments.get("comment_id").toString())){
					child.add(reply);
				}
			}
			comments.put("list", child);
			comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
		}
		return page;
		
	}

    @Override
    public Page listAll(int pageNo, int pageSize, String object_id, String commenttype) {
        String sql = SF.goodsSql("COMMENTS_LIST_ALL_0");
        Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, object_id, commenttype);
        String sql_id_list = SF.goodsSql("COMMENTS_LIST_ALL_1");
        List<Comments> listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, object_id, commenttype);
        List<Map> list = (page.getResult());
        for (Map comments : list) {
            Timestamp ts = (Timestamp)comments.get("time");
            Date date = new Date();
            date = ts;
            comments.put("date", date);
            List<Comments> child = new ArrayList<Comments>();
            for(Comments reply:listReply){
                if(reply.getFor_comment_id().equals(comments.get("comment_id").toString())){
                    child.add(reply);
                }
            }
            comments.put("list", child);
            comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
        }
        return page;
    }

    @Override
	public Page pageComments(int pageNo, int pageSize, String object_type){
		String founderSql = "";
		AdminUser adminUser = ManagerUtils.getAdminUser();
		int founder = adminUser.getFounder();
		//0:电信员工 1：超级管理员
		if(!(founder == 0 || founder == 1)&&!"leavewords".equals(object_type)){//客户留言暂时不过滤工号权限
			String staffNo = ManagerUtils.getAdminUser().getUserid();
			founderSql = " and exists(select 1 from " + this.getTableName("goods") 
				+ " gd where goods_id=c.object_id and staff_no="+ staffNo +" and c.source_from = gd.source_from)";
		}
		String sql = SF.goodsSql("PAGE_COMMENTS_0") + founderSql + " order by time desc";
		
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, object_type);
		
		String sql_id_list = SF.goodsSql("SQL_ID_LIST") + founderSql + " order by time desc ";
		
		List<Comments> listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, object_type);
		List<Map> list = (page.getResult());
		for (Map comments : list) {
			List<Comments> child = new ArrayList<Comments>();
			for(Comments reply:listReply){
				if(reply.getFor_comment_id().equals(comments.get("comment_id").toString())){
					child.add(reply);
				}
			}
			comments.put("list", child);
			comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
		}
		return page;
	}
	
	@Override
	public int iCountComments(String object_type){
		String founderSql = "";
		AdminUser adminUser = ManagerUtils.getAdminUser();
		int founder = adminUser.getFounder();
		//0:电信员工 1：超级管理员
		if(!(founder == 0 || founder == 1)&&!"leavewords".equals(object_type)){//客户留言暂时不过滤工号权限
			String staffNo = ManagerUtils.getAdminUser().getUserid();
			founderSql = " and exists(select 1 from " + this.getTableName("goods") 
				+ " where source_from = '" + ManagerUtils.getSourceFrom() + "' and goods_id=c.object_id and staff_no="+ staffNo +")";
		}
		String sql = SF.goodsSql("ICOUNT_COMMENTS") + founderSql + " and c.source_from = '" + ManagerUtils.getSourceFrom() + 
				"' and comment_id not in(select for_comment_id from "+ this.getTableName("comments")+" where source_from = '" + ManagerUtils.getSourceFrom() + "')";
		int count = this.daoSupport.queryForInt(sql, object_type);
		return count;
	}
	
	@Override
	public Page pageCommentsTrash(int pageNo, int pageSize, String object_type){
		String sql = SF.goodsSql("PAGE_COMMENTS_TRASH");
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, object_type);

		String sql_id_list = SF.goodsSql("COMMENTS_SQL_ID_LIST");
		List<Comments> listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, object_type);
		List<Map> list = (page.getResult());
		for (Map comments : list) {
			List<Comments> child = new ArrayList<Comments>();
			for(Comments reply:listReply){
				if(reply.getFor_comment_id().equals(comments.get("comment_id").toString())){
					child.add(reply);
				}
			}
			comments.put("list", child);
			comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
		}
		return page;
	}

	
	@Override
	public Page pageComments_Display(int pageNo, int pageSize, Long object_id, String object_type, String commentType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
		String sql = SF.goodsSql("PAGE_COMMENTS_DISPLAY");
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, object_id, object_type, commentType);
 
		String sql_id_list = SF.goodsSql("DISPLAY_SQL_ID_LIST");
		List<Comments> listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, object_id, object_type, commentType);
		List<Map> list = (page.getResult());
		for (Map comments : list) {
			Timestamp ts = (Timestamp)comments.get("time");
			Date date = new Date();
			date = ts;
			comments.put("date", date);
			List<Comments> child = new ArrayList<Comments>();
			for(Comments reply:listReply){
				if(reply.getFor_comment_id().equals(comments.get("comment_id").toString())){
					child.add(reply);
				}
			}
			comments.put("list", child);
			comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
		}
		return page;
	}

	

	
	@Override
	public Page pageComments_Display(int pageNo, int pageSize) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		if(member == null){
			throw new EopException("您没有登录或已过期，请重新登录！");
		}
		String sql = SF.goodsSql("PAGE_COMMENTS_DISPLAY_01");
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, member.getMember_id());
		String sql_id_list = SF.goodsSql("DISPLAY_PAGE_COMMENTS_DISPLAY_01");
		List<Comments> listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, member.getMember_id());
		List<Map> list = (page.getResult());
		for (Map comments : list) {
			Timestamp ts = (Timestamp)comments.get("time");
			Date date = new Date();
			date = ts;
			comments.put("date", date);
			List<Comments> child = new ArrayList<Comments>();
			for(Comments reply:listReply){
				if(reply.getFor_comment_id().equals(comments.get("comment_id").toString())){
					child.add(reply);
				}
			}
			comments.put("list", child);
			comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
		}
		return page;
	}

	@Override
	public Page getComment(String order_id, String goods_id) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		if (member == null) 
			throw new EopException("您没有登录或已过期，请重新登录！");
		
//		String sql =  "select * from comments where for_comment_id is null and display = 'true' and author_id = ? and order_id=? and object_id =? order by time desc";
		String sql =  SF.goodsSql("GET_COMMENT");
		
		
		Page page = this.baseDaoSupport.queryForPage(sql, 1, 1,
				member.getMember_id(), order_id, goods_id);
		String sql_id_list = SF.goodsSql("COMMENT_SQL_ID_LIST");
		
		List<Comments> listReply = this.daoSupport.queryForList(sql_id_list,
				Comments.class, member.getMember_id());
		
		
		List<Map> list = (page.getResult());
		
		
		for (Map comments : list) {
			Timestamp ts = (Timestamp) comments.get("time");
			Date date = new Date();
			date = ts;
			comments.put("date", date);
			List<Comments> child = new ArrayList<Comments>();
			for (Comments reply : listReply) {
				if (reply.getFor_comment_id().equals(
						comments.get("comment_id").toString())) {
					child.add(reply);
				}
			}
			comments.put("list", child);
			comments.put("image",
					UploadUtil.replacePath((String) comments.get("img")));
		}
		return page;
	 
	}
	
	@Override
	public List listComments(String member_id, String object_type) {
		String sql = SF.goodsSql("GET_LIST_COMMENTS");
		List list = this.baseDaoSupport.queryForList(sql, Comments.class, member_id, object_type);
		return list;
	}

	
	/**
	 * 计算某个对象的评价
	 */
	@Override
	public Map coutObjectGrade(String commentType, Integer objectid) {
		String sql = SF.goodsSql("COUT_OBJECT_GRADE");
		List<Map> gradeNumList = this.baseDaoSupport.queryForList(sql, commentType,objectid);
		Map gradeMap = new HashMap();
		for(Map map :gradeNumList){
			gradeMap.put(  "grade_"+map.get("grade"),map.get("num"));
		}
		return gradeMap;
	}
	

	@Override
	public Map coutObejctNum(String commentType, Integer objectid) {
		String sql = SF.goodsSql("COUT_OBEJCT_NUM");
		List<Map> gradeNumList = this.baseDaoSupport.queryForList(sql, commentType,objectid);
		Map numMap = new HashMap();
		for(Map map :gradeNumList){
			numMap.put(  map.get("object_type")+"_num",map.get("num"));
		}
		return numMap;
	}

 
}
