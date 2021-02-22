package com.ztesoft.net.mall.core.action.backend;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import services.FlowInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.AdColumn;
import com.ztesoft.net.app.base.core.model.Adv;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IAdColumnManager;
import com.ztesoft.net.mall.core.service.IAdvManager;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * @author lzf 2010-3-2 上午09:46:16 version 1.0
 */
public class AdvAction extends WWAction {

	private IAdColumnManager adColumnManager;
	private IAdvManager advManager;
	private List<AdColumn> adColumnList;
	private Adv adv;
	private String acid;
	private String source_from;
	private String sourcefrom;
	private String advname; //搜索的广告名
	private String advid;
	private String id;
	private File pic;
	private String picFileName;
	private String picFileFileName;
	private String picFileContentType;
	private String mstartdate;
	private String menddate;
	private String order;
	private Integer state=-1;
	private int user_founder=-1;
	private FlowInf flowServ;
	private List resolList;
	private IDcPublicInfoManager dcPublicInfoManager;
	private List sourceFromList;

	public String list() {
		adColumnList = this.adColumnManager.listAllAdvPos();
		/*System.out.print("/////////////");
		System.out.print(this.advname);
		System.out.print("/////////////");*/
		adColumnList = this.adColumnManager.listAllAdvPos();
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(
				dcPublicInfoManager);
		resolList = dcPublicCache.getList("3434");;
		this.sourceFromList = dcPublicCache.getList("10003");
		this.webpage = advManager.search(acid,source_from, advname,state,this.getPage(), this.getPageSize(),this.order);
		AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
		user_founder = adminUser.getFounder();
//		if(source_from==null){
//			source_from = ManagerUtils.getSourceFrom();
//		}
		sourcefrom = this.source_from;
		return "list";
	}
	
	public String audit(){
		state=0;    //待审核
		this.webpage=advManager.search(acid, advname, state, this.getPage(), this.getPageSize(), this.order);
		AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
		user_founder = adminUser.getFounder();
		return "audit";
	}

	public String detail() {
		adv = this.advManager.getAdvDetail(advid);
		return "detail";
	}
	
	public String updateAdvState(){
		try {
			adv=advManager.getAdvDetail(advid);
			adv.setState(state);
			advManager.updateAdv(adv);
			this.json = "{'result':1,'message':'操作成功'}";
		} catch (Exception e) {
			this.json = "{'result':0,'message':'操作失败'}";
		}
		advManager.updateAdv(adv);
		return WWAction.JSON_MESSAGE;
	}

	public String click() {
		if (advid.equals("0")) {
			this.getRequest().setAttribute("gourl", "/eop/shop/adv/zhaozu.jsp");
		} else {
			adv = this.advManager.getAdvDetail(advid);
			// 避免同一客户重复点击导致点击数被重复计算
			// 以客户的session做为依据。同一session生命期内只计一次对某一广告的点击数
			if (this.getRequest().getSession().getAttribute(
					"AD" + advid.toString()) == null) {
				this.getRequest().getSession().setAttribute(
						"AD" + advid.toString(), "clicked");
				adv.setClickcount(adv.getClickcount() + 1);
				this.advManager.updateAdv(adv);
			}
			// 不管此session生命期内是否已经计过点击数，点击后的页面还是要转的
			this.getRequest().setAttribute("gourl", adv.getUrl());
		}
		return "go";
	}
	
	//add by wui 首页轮播页面跳转
	public String to_page() {
		return click();
	}
	public String delete() {
		try {
			this.advManager.delAdvs(id);
			this.json = "{'result':0,'message':'删除成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'删除失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	public String add() {
		adColumnList = this.adColumnManager.listAllAdvPos();
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(
				dcPublicInfoManager);
		this.sourceFromList = dcPublicCache.getList("10003");
		this.resolList =  dcPublicCache.getList("3434");
		return "add";
	}

	public String addSave() {
		if (pic != null) {

			if (FileBaseUtil.isAllowUp(picFileName)) {
				String path = UploadUtil.upload(this.pic,this.picFileName, "adv");
				adv.setAtturl(path);
			} else {
				this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp,swf格式文件。");
				return WWAction.MESSAGE;
			}
		}
		
		AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
		int founder = adminUser.getFounder();
		String user_no = "-1";
		if(!ManagerUtils.isAdminUser()){
            user_no =adminUser.getUserid()+"";
        }
		adv.setAtype("1");
		adv.setUser_id(user_no); //设置登录工号
		adv.setBegintime(mstartdate);
		adv.setEndtime(menddate);
		adv.setDisabled("false");
		adv.setAid(String.valueOf(System.currentTimeMillis()));
		//adv.setSource_from(ManagerUtils.getSourceFrom());前台已经设置值了，怎么还要设置
		if(Consts.CURR_FOUNDER0 == founder || Consts.CURR_FOUNDER1 == founder){     //电信员工或系统管理员
			adv.setState(Consts.ADV_STATE_1);   //已发布
		}else {
			adv.setState(Consts.ADV_STATE_0);   //待审核
		}
		try {
			String[] str= new String[2];
			if(StringUtils.isNotEmpty(adv.getAcid())){//分隔广告位置的ID和所处的位置 如123-lb
				str=adv.getAcid().split("-");
			}
			
			adv.setAcid(str[0]);
			//在商品类别goods_cat增加一条，会在es_adcolumn增加2条数据：一条楼层和类别 ;像左上右上这种情况的数据，已经固化，不能动
			if(2 == str.length && this.adColumnManager.getADcolumnDetail(adv.getAcid())==null){
				AdColumn adColumn=new AdColumn();
				//adColumn.setAcid(adv.getAcid());
				
				adColumn.setCname(str[2]+"-类别");
				
				adColumn.setWidth("765");
				adColumn.setHeight("300");
				adColumn.setAtype(0);
				
				acid=this.adColumnManager.addAdvc(adColumn);//
				this.adColumnManager.addAdcolumnCat(acid,adv.getAcid(), "lb");
				
				adColumn.setCname(str[2]+"-楼层");
				acid=this.adColumnManager.addAdvc(adColumn);//
				this.adColumnManager.addAdcolumnCat(acid,adv.getAcid(), "lc");
			}
			
			this.advManager.addAdv(adv);
			this.msgs.add("新增广告成功");
			this.urls.put("广告列表", "adv!list.do");
		} catch (RuntimeException e) {
			this.msgs.add("新增广告失败");
			this.urls.put("广告列表", "adv!list.do");
		}	
//		审核的代码暂时注释掉 
//		try{
//			if(!(Consts.CURR_FOUNDER0==founder)){  //非电信员工要发起流程进行审核
//				flowServ.startFlow(new InitFlowParam(FlowType.ADVICE_MANAGE  , ManagerUtils.getAdminUser().getUserid() ,adv.getAid() )) ;
//			}
//		} catch (RuntimeException e) {		
//			this.msgs.add("非电信员工要发起流程进行审核失败!");
//			this.urls.put("广告列表", "adv!list.do");
//		}

		return WWAction.MESSAGE;
	}

	public String edit() {
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(
				dcPublicInfoManager);
		this.resolList =  dcPublicCache.getList("3434");
		adColumnList = this.adColumnManager.listAllAdvPos();
		adv = this.advManager.getAdvDetail(advid,source_from);
		return "edit";
	}
	
	public String getAdvById(){
		adv = this.advManager.getAdvDetail(advid);
		return "getAdvById";
	}

	public String editSave() {
		if (pic != null) {
			if (FileBaseUtil.isAllowUp(picFileName)) {
				String path = UploadUtil.upload(this.pic,this.picFileName, "adv");
				adv.setAtturl(path);
			} else {
				this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp,swf格式文件。");
				return WWAction.MESSAGE;
			}
		}
		adv.setBegintime(mstartdate);
		adv.setEndtime(menddate);
		adv.setCreate_date(DBTUtil.current());
		
		this.advManager.updateAdv(adv);
		this.msgs.add("修改广告成功");
		this.urls.put("广告列表", "adv!list.do");
		return WWAction.MESSAGE;
	}

	public String stop() {
		adv = this.advManager.getAdvDetail(advid,source_from);
		adv.setIsclose(1);
		try {
			//this.advManager.updateAdv(adv);
			this.advManager.updateAdvIsClose(advid, 1);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	public String start() {
		adv = this.advManager.getAdvDetail(advid,source_from);
		adv.setIsclose(0);
		try {
			//this.advManager.updateAdv(adv);
			this.advManager.updateAdvIsClose(advid, 0);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	

	public IAdColumnManager getAdColumnManager() {
		return adColumnManager;
	}

	public void setAdColumnManager(IAdColumnManager adColumnManager) {
		this.adColumnManager = adColumnManager;
	}

	public IAdvManager getAdvManager() {
		return advManager;
	}

	public void setAdvManager(IAdvManager advManager) {
		this.advManager = advManager;
	}

	public Adv getAdv() {
		return adv;
	}

	public void setAdv(Adv adv) {
		this.adv = adv;
	}

	public String getAcid() {
		return acid;
	}

	public void setAcid(String acid) {
		this.acid = acid;
	}

	public String getAdvid() {
		return advid;
	}

	public void setAdvid(String advid) {
		this.advid = advid;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	public String getSourcefrom() {
		return sourcefrom;
	}

	public void setSourcefrom(String sourcefrom) {
		this.sourcefrom = sourcefrom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<AdColumn> getAdColumnList() {
		return adColumnList;
	}

	public void setAdColumnList(List<AdColumn> adColumnList) {
		this.adColumnList = adColumnList;
	}

	public File getPic() {
		return pic;
	}

	public void setPic(File pic) {
		this.pic = pic;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public String getMstartdate() {
		return mstartdate;
	}

	public void setMstartdate(String mstartdate) {
		this.mstartdate = mstartdate;
	}

	public String getMenddate() {
		return menddate;
	}

	public void setMenddate(String menddate) {
		this.menddate = menddate;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getAdvname() {
		return advname;
	}

	public void setAdvname(String advname) {
		this.advname = advname;
	}

	

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getPicFileFileName() {
		return picFileFileName;
	}

	public void setPicFileFileName(String picFileFileName) {
		this.picFileFileName = picFileFileName;
	}

	public String getPicFileContentType() {
		return picFileContentType;
	}

	public void setPicFileContentType(String picFileContentType) {
		this.picFileContentType = picFileContentType;
	}

	public int getUser_founder() {
		return user_founder;
	}

	public void setUser_founder(int user_founder) {
		this.user_founder = user_founder;
	}
	

	public List getResolList() {
		return resolList;
	}

	public void setResolList(List resolList) {
		this.resolList = resolList;
	}

	public FlowInf getFlowServ() {
		return flowServ;
	}

	public void setFlowServ(FlowInf flowServ) {
		this.flowServ = flowServ;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}	
	
	public List getSourceFromList() {
		return sourceFromList;
	}

	public void setSourceFromList(List sourceFromList) {
		this.sourceFromList = sourceFromList;
	}
}
