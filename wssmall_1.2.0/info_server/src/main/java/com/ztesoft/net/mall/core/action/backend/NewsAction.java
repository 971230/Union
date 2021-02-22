package com.ztesoft.net.mall.core.action.backend;

import java.util.List;
import java.util.Map;

import params.member.req.MemberLvListReq;
import params.member.resp.MemberLvListResp;
import services.FlowInf;
import services.MemberLvInf;

import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.app.base.core.model.NewsLv;
import com.ztesoft.net.app.base.core.model.NewsVO;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.impl.NewsManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.workflow.core.FlowType;
import com.ztesoft.net.mall.core.workflow.core.InitFlowParam;

public class NewsAction extends WWAction {
	private NewsVO newsVO;
	private NewsManager newsManager;
	private Integer state=-1;
	private String news_id;
	private FlowInf flowServ;
	private String id;
	private int currFounder;
	private List<MemberLv> lvList;
	private String [] lvids;
	private MemberLvInf memberLvServ;
	
	
	public MemberLvInf getMemberLvServ() {
		return memberLvServ;
	}

	public void setMemberLvServ(MemberLvInf memberLvServ) {
		this.memberLvServ = memberLvServ;
	}

	public void setNewsVO(NewsVO newsVO) {
		this.newsVO = newsVO;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public NewsManager getNewsManager() {
		return newsManager;
	}

	public void setNewsManager(NewsManager newsManager) {
		this.newsManager = newsManager;
	}

	public String list(){
	    this.currFounder = ManagerUtils.getFounder();
		this.webpage=newsManager.search(state, this.getPage(), this.getPageSize());
		return "list";
	}
	
	public String add(){
		
		MemberLvListReq req = new MemberLvListReq();
		MemberLvListResp resp = memberLvServ.getMemberLvList(req);
		if(resp != null){
			this.lvList = resp.getMemberLvs();
		}
		
		MemberLv ml = new MemberLv();
		ml.setLv_id("-1");
		ml.setName("向社会开放");
		lvList.add(0, ml);
		return "add";
	}
	
	public String edit(){
		
		MemberLvListReq req = new MemberLvListReq();
		MemberLvListResp resp = memberLvServ.getMemberLvList(req);
		if(resp != null){
			this.lvList = resp.getMemberLvs();
		}
		MemberLv ml = new MemberLv();
		ml.setLv_id("-1");
		ml.setName("向社会开放");
		lvList.add(0, ml);
		newsVO=newsManager.getNewsDetail(news_id);
		if(newsVO!=null){
			List<Map> list = newsManager.liseNewsLv(newsVO.getNews_id());
			for(Map map:list){
				for(MemberLv lv:lvList){
					if(lv.getLv_id().equals(map.get("lv_id").toString())){
						lv.setSelected(true);
					}
				}
			}
		}
		return "edit";
	}
	
	public String editSave() {
		if(lvids==null || lvids.length==0){
			this.msgs.add("请选择会员");
		}else{
			newsManager.updateNews(newsVO);
			newsManager.delNewsLv(newsVO.getNews_id());
			for(String s:lvids){
				NewsLv nl = new NewsLv();
				nl.setLv_id(s);
				nl.setNews_id(newsVO.getNews_id());
				newsManager.insertNewsLv(nl);
			}
		}
		
		this.msgs.add("修改快讯成功");
		this.urls.put("快讯列表", "news!list.do");
		return WWAction.MESSAGE;
	}
	public String delete(){
		
		try {
			this.newsManager.delNews(id);
			this.json = "{'result':0,'message':'删除成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'删除失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	public String addSave(){
		AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
		int founder = adminUser.getFounder();
		String user_no = "";
		//if(!ManagerUtils.isAdminUser())
		user_no =adminUser.getUserid()+"";
		newsVO.setUser_id(user_no);
		if(Consts.CURR_FOUNDER0==founder){     //电信员工
			newsVO.setState(Consts.ADV_STATE_1);   //已发布
		}else {
			newsVO.setState(Consts.ADV_STATE_0);   //待审核
		}
		try {
			if(lvids==null || lvids.length==0){
				this.msgs.add("请选择会员等级");
			}else{
				this.newsManager.addNews(newsVO);
				for(String s:lvids){
					NewsLv nl = new NewsLv();
					nl.setLv_id(s);
					nl.setNews_id(newsVO.getNews_id());
					newsManager.insertNewsLv(nl);
				}
				this.msgs.add("新增快讯成功");
				this.urls.put("快讯列表", "news!list.do");
				
				if(!(Consts.CURR_FOUNDER0==founder)){  //非电信员工要发起流程进行审核
					flowServ.startFlow(new InitFlowParam(FlowType.NEWS_MANAGE  , ManagerUtils.getAdminUser().getUserid() ,newsVO.getNews_id() )) ;
				}
			}
		} catch (RuntimeException e) {
			this.msgs.add("新增快讯失败");
			this.urls.put("快讯列表", "news!list.do");
		}
		return WWAction.MESSAGE;
	}

	public String getNews_id() {
		return news_id;
	}

	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}
	

	public NewsVO getNewsVO() {
		return newsVO;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCurrFounder() {
		return currFounder;
	}

	public void setCurrFounder(int currFounder) {
		this.currFounder = currFounder;
	}

	public List<MemberLv> getLvList() {
		return lvList;
	}

	public void setLvList(List<MemberLv> lvList) {
		this.lvList = lvList;
	}

	public String[] getLvids() {
		return lvids;
	}

	public void setLvids(String[] lvids) {
		this.lvids = lvids;
	}

	public FlowInf getFlowServ() {
		return flowServ;
	}

	public void setFlowServ(FlowInf flowServ) {
		this.flowServ = flowServ;
	}

}
