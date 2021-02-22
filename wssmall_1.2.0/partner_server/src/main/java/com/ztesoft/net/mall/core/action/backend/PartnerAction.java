package com.ztesoft.net.mall.core.action.backend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import params.adminuser.req.AdminUserReq;
import params.adminuser.req.AdminUserUpdateReq;
import params.adminuser.resp.AdminUserEditResp;
import params.adminuser.resp.AdminUserResp;
import services.AdminUserInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.model.PartnerAddress;
import com.ztesoft.net.app.base.core.model.PartnerShop;
import com.ztesoft.net.app.base.core.model.PartnerStaff;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IPartnerAlbumManager;
import com.ztesoft.net.mall.core.service.IPartnerManager;
import com.ztesoft.net.mall.core.service.IPartnerShopManager;
import com.ztesoft.net.mall.core.service.IPartnerStaffManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import commons.CommonTools;

/**
 * 分销商管理
 * 
 * @author dengxiuping
 * 
 */
public class PartnerAction extends WWAction {

	private IPartnerManager partnerManager;
	private IPartnerShopManager partnerShopManager;
	private IPartnerStaffManager partnerStaffManager;

	private IPartnerAlbumManager partnerAlbumManager;
	private AdminUserInf adminUserServ;

	private Partner partner, partView;
	private Partner part_serch;
	private Partner partAudit_s;
	private Partner partBlock_s;
	private Partner partLogout_s;
	private PartnerShop partShopView, partShop_s;
	private PartnerStaff partStaff;
	private PartnerAddress partAddrView;
	private AdminUser adminuser;

	private List<PartnerStaff> partStafList;
	private List lvlist;
	private List goodsUsageList;// 分销商业务

	protected Map tabs;
	private List columnNamelist, histroyList,historyOldList;
	private Map partners = new HashMap();
	private String[] picnames;

	private Integer addMonths;
	private String id;
	private String name;
	private String uname;
	private String order;
	private String partner_id;
	private String staff_code;
	private String flag;// add-增加 edit-修改 view-查看
	private String flag2;
	private Boolean is_edit = false;// 增加或修改
	private String currUserId;// 当前用户id
	private Integer currFounder;// 当前用户类型
	private String currentPartnerId;
	private String[] usage;// 业务
	private String block_reason;// 冻结原因
	private String oldPassword, newPassword, newPassword2, userid;

	private String level;
	private String state;
	private Integer sequ,msequ,ssequ;
	protected String actionName;
	private String stateType;
	private String acc_nbr;
	private String[] detail_imgage_arr;//店铺详情图片
	public String checkLlkjAccNbr(){
		boolean b = this.partnerManager.checkLlkjAccNbr(acc_nbr);
		if(b){
			json = "{'result':1,'message':'是连连手机号！'}";
		}else{
			json = "{'result':0,'message':'不是连连手机号！'}";
		}
		return JSON_MESSAGE;
	}
	
	public int getCurrFounder() {
		currFounder = ManagerUtils.getFounder();
		return currFounder;
	}

	/**
	 * 审核列表
	 */
	public String auditlist() {
		this.webpage = this.partnerManager.auditlist(partAudit_s, order, this
				.getPage(), this.getPageSize());
		return "auditlist";
	}

	/**
	 * 分销商列表
	 * 
	 * @return
	 */
	public String list() {
		if (stateType == null) {
			stateType = Consts.PARTNER_STATE_ALL;
		}
		currentPartnerId = this.partnerManager.currentLoginPartnerId();

		this.webpage = this.partnerManager.list(part_serch, stateType, this
				.getPage(), this.getPageSize());
		
		
		/**
		 * 以下代码是为了导出excel功能所编写
		 * title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性
		 * set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = {"分销商名称","分销商编码","店铺类型","联系人","联系电话","达量","分值","预存金","冻结预存金","服务保证金","生效时间","失效时间","状态","关联工号"};
		String[] content = {"partner_name","partner_code","shop_type_desc","linkman","phone_no"
				,"need_amount","score","deposit","frost_deposit","service_cash","eff_date","exp_date","state_name","username"};
		String fileTitle = "分销商数据导出";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		request.setAttribute("fileTitle", fileTitle);
		
		if(excel != null && !"".equals(excel)){
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		}else{
			return "list";
		}
		
	}
	
	/**
	 * 代理商列表
	 * 
	 * @return
	 */
	public String listAgent() {
		if (stateType == null) {
			stateType = Consts.PARTNER_STATE_ALL;
		}
		currentPartnerId = this.partnerManager.currentLoginPartnerId();

		this.webpage = this.partnerManager.list(part_serch, stateType, this
				.getPage(), this.getPageSize());
		
		
		/**
		 * 以下代码是为了导出excel功能所编写
		 * title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性
		 * set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = {"代理商名称","代理商编码","店铺类型","联系人","联系电话","服务保证金","生效时间","失效时间","状态","关联工号"};
		String[] content = {"partner_name","userno","shop_type_desc","linkman","phone_no"
				,"service_cash","eff_date","exp_date","state_name","username"};
		String fileTitle = "代理商数据导出";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		request.setAttribute("fileTitle", fileTitle);
		
		if(excel != null && !"".equals(excel)){
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		}else{
			return "list";
		}
		
	}
	
	
	/**
	 * 行业用户列表
	 * 
	 * @return
	 */
	public String listCmsAgent() {
		if (stateType == null) {
			stateType = Consts.PARTNER_STATE_ALL;
		}
		currentPartnerId = this.partnerManager.currentLoginPartnerId();

		this.webpage = this.partnerManager.list(part_serch, stateType, this
				.getPage(), this.getPageSize());
		
		
		/**
		 * 以下代码是为了导出excel功能所编写
		 * title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性
		 * set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = {"行业用户名称","行业用户编码","店铺类型","联系人","联系电话","服务保证金","生效时间","失效时间","状态","关联工号"};
		String[] content = {"partner_name","userno","shop_type_desc","linkman","phone_no"
				,"service_cash","eff_date","exp_date","state_name","username"};
		String fileTitle = "行业用户数据导出";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		request.setAttribute("fileTitle", fileTitle);
		
		if(excel != null && !"".equals(excel)){
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		}else{
			return "list";
		}
		
	}

	/**
	 * 变更资料列表
	 */
	public String historylist() {

		if (stateType == null) {
			stateType = this.partnerManager.getDefaltType();
		}
		currentPartnerId = this.partnerManager.currentLoginPartnerId();
		this.webpage = this.partnerManager.fieldAuditlist(part_serch,
				stateType, this.getPage(), this.getPageSize());

		return "historylist";
	}

	/**
	 * 变更网店资料列表
	 * 
	 * public String historyShoplist() { if(flag2!=null && "Y".equals(flag2)){
	 * //待审核 if(ManagerUtils.isNetStaff()){ stateType="A0"; }else
	 * if(ManagerUtils.isFirstPartner()){ stateType="B0"; } }else{
	 * if(stateType==null){ stateType=this.partnerManager.getDefaltType(); } }
	 * this.webpage =
	 * partnerShopManager.shopHistorylist(partShop_s,stateType,this.getPage(),this.getPageSize());
	 * 
	 * return "historyShoplist"; }
	 */

	public String getCurrentPartnerId() {
		return currentPartnerId;
	}

	public void setCurrentPartnerId(String currentPartnerId) {
		this.currentPartnerId = currentPartnerId;
	}

	/**
	 * 去修改密码页面
	 */
	public String updpassword() {
		String currUserId = ManagerUtils.getUserId();
		AdminUserReq adminUserReq = new AdminUserReq();
		adminUserReq.setUser_id(currUserId);
		
		AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
		adminuser = new AdminUser();
		if(adminUserResp != null){
			adminuser = adminUserResp.getAdminUser();
		}
		return "updpassword";
	}

	/**
	 * 关联分销商
	 * 
	 * @return
	 */
	public String showPartnerList() {

		Map map = new HashMap();
		this.webpage = this.partnerManager.searchPartner(map, this
				.getPage(), this.getPageSize());

		return "showPartnerList";
	}

	/**
	 * 去添加
	 * 
	 * @return
	 */
	public String add() {

		is_edit = false;
		partView = null;
		sequ = -1;
		state = Consts.PARTNER_STATE_APPLY;

		return "detail";
	}

	/**
	 * 增加或修改 去tab页面
	 * 
	 * @return
	 */
	public String detail() {

		is_edit = true;

		return "detail";

	}

	public String applyAgain() {

		try {
			Partner old = this.partnerManager.getPartner(partner_id, sequ,
					state); // 基本信息
			if (old != null) {
				old.setSequ(Consts.PARTNER_SEQ_W1);
				old.setState(Consts.PARTNER_STATE_APPLY);
				partnerManager.editPartner(old, state, sequ);
			}

			PartnerShop oldshop = partnerShopManager.getPartnerShop(partner_id,
					state, sequ); // 网店信息
			if (oldshop != null) {
				oldshop.setSequ(Consts.PARTNER_SEQ_W1);
				oldshop.setState(Consts.PARTNER_STATE_APPLY);
				partnerShopManager.editPartnerShop(oldshop, state, sequ);
			}
			json = "{'result':0,'message':'操作成功！'}";
		} catch (RuntimeException e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}

		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 是否已被审核
	 * @return
	 */
	public String isAudit(){
		partView = partnerManager.getPartnerSequM1AndState0(partner_id);
		if(null != partView){
			this.json = "{'result':0,'message':'未被审核'}";
		}else {
			this.json = "{'result':1,'message':'已被审核'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 审核窗口
	 */
	public String showaudit() {

		partView = partnerManager.getPartnerSequM1AndState0(partner_id); // 基本信息

		return "showaudit";
	}

	/**
	 * 审核字段变更窗口
	 */
	public String showAuditAlter() {

		partView = partnerManager.getPartner(partner_id, Consts.PARTNER_SEQ_0,null); // 基本信息

		columnNamelist = this.partnerManager.columnAuditList();
		columnNamelist = this.partnerManager.histroyList(columnNamelist,partner_id);
		historyOldList=(List) ServletActionContext.getRequest().getAttribute("listVal1");
		histroyList=(List) ServletActionContext.getRequest().getAttribute("listVal2");

		return "showAuditAlter";
	}

	/**
	 * 审核字段变更窗口
	 */
	public String showAuditAlterHistory() {

		partView = partnerManager.getPartner(partner_id, Consts.PARTNER_SEQ_W1,
				Consts.PARTNER_STATE_NORMAL); // 基本信息
		columnNamelist = this.partnerManager.columnAuditList();
		columnNamelist = this.partnerManager.histroyList(columnNamelist,partner_id);
		historyOldList=(List) ServletActionContext.getRequest().getAttribute("listVal1");
		histroyList=(List) ServletActionContext.getRequest().getAttribute("listVal2");

		return "showAuditAlterHistory";
	}
	/**
	 * 查看原因
	 * @throws ParseException 
	 */
	public String showLookReson() throws ParseException{
		
		/*partView = partnerManager.getPartner(partner_id, Consts.PARTNER_SEQ_W2,
				Consts.PARTNER_STATE_NORMAL); // 基本信息
*/		
		partView=new Partner();
		List<Map> list=partnerManager.auditResonAndDateList(partner_id,msequ,ssequ);
		if(list!=null){
			String m_last_date=null,s_last_date=null;
			Object m_last_dateO=null,s_last_dateO=null;
			String m_audit="",s_audit="";
			for (Map  map : list ) {
				partView.setPartner_name((String)map.get("partner_name"));
				
				m_last_dateO=map.get("m_last_date");
				s_last_dateO=map.get("s_last_date");
				m_audit=(String) map.get("m_audit");
				s_audit=(String) map.get("s_audit");
				break;
			}
			s_last_date=s_last_dateO.toString();
			m_last_date=m_last_dateO.toString();
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!StringUtil.isEmpty(s_last_date)){
				Date datem=sf.parse(m_last_date);
				Date dates=sf.parse(s_last_date);
				if(dates.getTime()>datem.getTime()){
					partView.setLast_update_date(s_last_date);
					partView.setAudit_idea(s_audit);
				}
				
			}else{
				partView.setLast_update_date(m_last_date);
				partView.setAudit_idea(m_audit);
			}
		}
		
		return "showLookReson";
	}

	/**
	 * 冻结窗口
	 */
	public String showblock() {

		//partView = partnerManager.getPartner(partner_id); // 基本信息
		partView = partnerManager.getPartnerByCurrentUserId(userid, partner);
		return "showblock";
	}
	
	/**
	 * 恢复窗口
	 */
	public String showrenew() {

		partView = partnerManager.getPartner(partner_id, Consts.PARTNER_SEQ_0,
				Consts.PARTNER_STATE_CONGELATION); // 基本信息
		level = partnerManager.getPartnerLevel(partView);

		return "showrenew";
	}

	/**
	 * 续签窗口
	 */
	public String showkeepon() {

		partView = partnerManager.getPartner(partner_id, sequ, state); // 基本信息

		return "showkeepon";
	}

	/**
	 * 分级冻结
	 */
	public String showGrading() {

		partView = partnerManager.getPartner(partner_id, sequ, state); // 基本信息
		goodsUsageList = partnerManager.partnerGoodsUsageList(partView.getUserid(),Consts.GOODS_USAGE_STATE_0);

		return "showGrading";
	}
	/**
	 * 分级解冻
	 */
	public String showGradingOpen() {

		partView = partnerManager.getPartner(partner_id, sequ, state); // 基本信息
		goodsUsageList = partnerManager.partnerGoodsUsageList(partView.getUserid(),Consts.GOODS_USAGE_STATE_1);

		return "showGradingOpen";
	}
	/**
	 * 去添加分销商员工页面
	 * 
	 * @return
	 */
	public String showStaffAdd() {
		if(null != flag && flag.equals("edit")){	//编辑模式
			partStaff = partnerStaffManager.getPartnerStaffByIdAndCode(partner_id, staff_code);
		}else {
			if(null == partStaff)
				partStaff = new PartnerStaff();
			String staff_code = partnerStaffManager.generateStaffCode(partner_id);
			partStaff.setStaff_code(staff_code);
		}
		return "showStaffAdd";
	}

	public String baseTab() {

		if (is_edit) {

			partView = partnerManager.getPartner(partner_id, sequ, state); // 基本信息
			if (partView != null) {
				if (!StringUtil.isEmpty(partView.getParent_userid())) {
					AdminUserReq adminUserReq = new AdminUserReq();
					adminUserReq.setUser_id(partView.getParent_userid());
					
					AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
					adminuser = new AdminUser();
					if(adminUserResp != null){
						adminuser = adminUserResp.getAdminUser();
					}
				}
				if (!StringUtil.isEmpty(partView.getShop_type())) {
					partView.setShop_type_desc(partnerManager.getDcpublicName(
							Consts.DC_PUBLIC_STYPE_8007.toString(), partView.getShop_type().toString()));
				}

				Map goods = null;

				goods = ReflectionUtil.po2Map(partView);

				partnerAlbumManager.onFillGoodsEditInput(goods, this
						.getRequest());
			}

		} else {

			partnerAlbumManager.onFillGoodsAddInput(this.getRequest());
			adminuser = ManagerUtils.getAdminUser();
			partView = null;
		}
		
		return "baseTab";
	}

	public String shopTab() {
		if (is_edit) {
			state=state.trim();
			 if(state.equals(Consts.PARTNER_STATE_APPLY)){
				 sequ=Consts.PARTNER_SEQ_W1;
			 }else if(state.equals(Consts.PARTNER_STATE_CONGELATION)){
				 sequ=Consts.PARTNER_SEQ_0;
			 }else if(state.equals(Consts.PARTNER_STATE_NORMAL)){
				 sequ=Consts.PARTNER_SEQ_0;
			 }else {
				 sequ=Consts.PARTNER_SEQ_0;
			 }
			partShopView = partnerShopManager.getPartnerShop(partner_id, null,sequ);// 网店信息
			if (partShopView != null) {
				if (!StringUtil.isEmpty(partShopView.getStar())) {
					partShopView.setStar_desc(partnerManager.getDcpublicName(
							Consts.DC_PUBLIC_STYPE_8008.toString(),
							partShopView.getStar().toString()));
				}
				String shop_detail_image = partShopView.getShop_detail_image();
				if(!StringUtils.isEmpty(shop_detail_image)){
					this.detail_imgage_arr = shop_detail_image.split(",");
				}
			}
		} else {
			partShopView = null;
		}
		return "shopTab";
	}

	public String staffTab() {

		if (is_edit) {
			partStafList = partnerStaffManager.list(partner_id);// 员工列表
		} else {
			partStafList = null;
		}
		return "staffTab";
	}

	public String checkSaveVary(){
		if(partnerManager.isFieldAuditIngExits(partner_id)){
			json = "{'result':1,'message':'该分销商存在变更单，请先处理变更单后再操作'}";
		}else{
			json = "{'result':0,'message':'可以操作'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	/**
	 * 添加 或修改 资料
	 */
	public String savePartner() {
		
		try {
			
			if (is_edit) { // 修改

				if(partnerManager.isFieldAuditIngExits(partView.getPartner_id())){
					json = "{'result':1,'message':'你有已变更的数据需审核，请等待您的上级审核完毕才能进行修改！'}";
					return WWAction.JSON_MESSAGE; 
				}
			}
			if (picnames != null) {

				try {
					partnerAlbumManager.proessPhoto(picnames, partners);
					
				} catch (RuntimeException e) {
					logger.info("图片读取失败");
				}
				partView.setImage_default(partners.get("image_default") != null ? partners.get("image_default").toString(): "");
		        partView.setImage_file(partners.get("image_file") != null ? partners.get("image_file").toString(): "");

			}
			if (is_edit) { // 修改

				/*if(partnerManager.isFieldAuditIngExits(partView.getPartner_id())){
					json = "{'result':1,'message':'你有已变更的数据需审核，请等待您的上级审核完毕才能进行修改！'}";
				}else{*/
				Integer sequ = partView.getSequ();
				if("LLKJ_AGENT".equals(ManagerUtils.getSourceFrom())){
					partnerManager.edit_llkj(partView);
				}else{
					partnerManager.edit(partView);
				}
				

				if (partView.getSequ().equals(sequ)) {// 未变更

					json = "{'result':0,'message':'保存成功！','id':'"
							+ partView.getPartner_id() + "','sequ':"
							+ partView.getSequ() + ",'state':'"
							+ partView.getState() + "'}";
				} else {// 变更
					json = "{'result':3,'message':'已变更！'}";
					//smsManager.addPartnerAuditMsg(partView.getLinkman(),partView.getPhone_no(),Consts.PARNTER_FIELD_AUDIT_MESS);
				}
//				}
			} else { // 增加

				if (partnerManager.isPartnerExits(null,partView.getPartner_code())) {
					json = "{'result':1,'message':'分销商编号" + partView.getPartner_code() +"已被使用！'}";
				} else if (partnerManager.isPartnerExits(partView.getPartner_name(),null)) {
					json = "{'result':1,'message':'分销商名称" + partView.getPartner_name() + "已被使用！'}";
				} else {
					//by liqingyi 分销商列表查询条件appendSql2()中partner_userid is null
//					partView.setParent_userid(ManagerUtils.getUserId());
					partView.setParent_userid(CommonTools.getUserId());
					partnerManager.addNotAudit(partView);

					//smsManager.addPartnerAuditMsg(partView.getLinkman(),partView.getPhone_no(), Consts.PARNTER_OPEN);

					json = "{'result':0,'message':'保存成功！','id':'"
							+ partView.getPartner_id() + "','sequ':"
							+ partView.getSequ() + ",'state':'"
							+ partView.getState() + "'}";
				}
			}
		} catch (Exception e) {
			if (is_edit) {
				json = "{'result':1,'message':'修改失败！'}";
			} else {
				json = "{'result':1,'message':'添加失败！'}";
			}
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 添加 或修改 网店 资料
	 */
	public String savePartnerShop() {
		try {
			if (is_edit) {

				if(partnerManager.isFieldAuditIngExits(partShopView.getPartner_id())){
					json = "{'result':1,'message':'你有已变更的数据需审核，请等待您的上级审核完毕才能进行修改！'}";
				}else{
					Integer sequ = partShopView.getSequ();
					partnerShopManager.edit(partShopView);

					if (partShopView.getSequ().equals(sequ)) {// 未变更
						json = "{'result':0,'message':'修改成功！'}";
					} else {// 变更
						/*smsManager.addPartnerAuditMsg(partShopView.getLinknam(),partShopView.getPhone_no(),
								Consts.PARNTER_FIELD_AUDIT_MESS);*/
						json = "{'result':3,'message':'已变更！'}";
					}

				}
				
			} else {
				partnerShopManager.add(partShopView);
				json = "{'result':0,'message':'添加成功！','':}";
			}
		} catch (Exception e) {
			if (is_edit) {
				json = "{'result':1,'message':'修改失败！'}";
			} else {
				json = "{'result':1,'message':'添加失败！'}";
			}
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 保存分销商员工
	 * 
	 * @return
	 */
	public String saveAddStaff() {

		try {
			if(null != flag && flag.equals("edit")){
				partnerStaffManager.update(partStaff);
			}else {
				partnerStaffManager.add(partStaff);
			}
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;

	}
	
	public String editPartnerStaff(){
		
		return "";
	}

	/**
	 * 审核分销商
	 * 
	 * @return
	 */
	public String saveAuditPartner() {
		try {
			if(partnerManager.isSaveExits(partView.getPartner_id(), Consts.PARTNER_STATE_NORMAL, Consts.PARTNER_SEQ_0)){
				this.json = "{'result':2,'message':'操作失败!'}";
			}else{

				partnerManager.saveAuditPartner(partView);
				partnerShopManager.saveAuditPartnerShop(partView);
	
				
				if(partView.getState().equals(Consts.PARTNER_STATE_NORMAL)){
					// 发送短信
					Partner p_partner = partnerManager.getPartnerById(partView.getPartner_id());
					//smsManager.addPartnerAuditMsg(p_partner.getLinkman(),p_partner.getPhone_no(),Consts.PARNTER_APPLY_SUCCESS);
					this.json = "{'result':2,'message':'操作成功!还需要为此分销商在【系统管理】里新增工号'}";
				}else{
					Partner p_partner = partnerManager.getPartnerById(partView.getPartner_id());
					this.json = "{'result':2,'message':'操作成功!'}";
					//smsManager.addPartnerAuditMsg(p_partner.getLinkman(),p_partner.getPhone_no(), Consts.PARNTER_APPLY_FAIL);// 发送短信
				}
			}
		} catch (RuntimeException e) {
			
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 审核分销商修改过的字段
	 */
	public String saveAuditAlterPartner() {
		try {

			int i=partnerManager.saveAuditAlterPartner(partView);
			if(i==0){
				this.json = "{'result':0,'message':'操作失败'}";
			}else{
//				smsManager.addPartnerAuditMsg(partView.getLinkman(),partView.getPhone_no(),
//						Consts.PARNTER_AUDIT_FIELD_SUCCESS);
				this.json = "{'result':0,'message':'操作成功'}";
				
			}

			
		} catch (RuntimeException e) {
			/*smsManager.addPartnerAuditMsg(partView.getLinkman(),partView.getPhone_no(),
					Consts.PARNTER_AUDIT_FIELD_FAIL);*/
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 审核分销商网店修改过的字段
	 * 
	 * public String saveAuditAlterPartnerShop(){ try {
	 * partnerShopManager.saveAuditAlterPartnerShop(partShopView); this.json =
	 * "{'result':0,'message':'审核成功'}"; } catch (RuntimeException e) {
	 * smsManager.addPartnerAuditMsg(partView, Consts.PARNTER_AUDIT_FIELD_FAIL);
	 * this.json = "{'result':1,'message':'审核失败'}"; }
	 * 
	 * return this.JSON_MESSAGE; }
	 */
	/**
	 * 冻结分销商
	 * 
	 * @return
	 */
	public String saveBlockPartner() {
		try {
			if(partnerManager.isSaveExits(partView.getPartner_id(),Consts.PARTNER_STATE_CONGELATION,Consts.PARTNER_SEQ_0)){
				json = "{'result':1,'message':'操作失败'}";
			}else{
			partnerManager.saveBlockPartner(partView);
			this.json = "{'result':2,'message':'操作成功'}";
			}
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 恢复分销商
	 * 
	 * @return
	 */
	public String saveRenewPartner() {
		try {
			if(partnerManager.isSaveExits(partView.getPartner_id(), Consts.PARTNER_STATE_CONGELATION, Consts.PARTNER_SEQ_0)){
				partnerManager.saveRenewPartner(partView);
				this.json = "{'result':2,'message':'操作成功'}";
			}else{
				this.json = "{'result':2,'message':'操作失败'}";
			}

			
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 续签代理商
	 * 
	 * @return
	 */
	public String saveKeeponAgent() {

		try {
			if(partnerManager.isSaveExits(partView.getPartner_id(),partView.getState(), partView.getSequ())){
				//上传文件
				//基于myFile创建一个文件输入流  
		       /* InputStream is = new FileInputStream(myFile);  
		        // 设置上传文件目录  
		        String uploadPath = EopSetting.IMG_SERVER_DOMAIN;//ServletActionContext.getServletContext().getRealPath("/upload");  
		        // 设置目标文件  
		        File toFile = new File(uploadPath, this.getMyFileFileName());  
		        // 创建一个输出流  
		        OutputStream os = new FileOutputStream(toFile);  
		        //设置缓存  
		        byte[] buffer = new byte[1024];  
		        int length = 0;  
		        //读取myFile文件输出到toFile文件中  
		        while ((length = is.read(buffer)) > 0) {  
		            os.write(buffer, 0, length);  
		        }  
		        logger.info("上传文件名"+myFileFileName);  
		        logger.info("上传文件类型"+myFileContentType);  
		        //关闭输入流  
		        is.close();  
		        //关闭输出流  
		        os.close();*/  
				
				String arr[] = new String[1];

				arr[0] = this.getRequest().getParameter("myFile");
				partnerAlbumManager.proessPhoto(arr, partners);
				partView.setImage_default(arr[0].replaceAll(EopSetting.IMG_SERVER_DOMAIN+EopContext.getContext().getContextPath(),EopSetting.FILE_STORE_PREFIX));

				if (partView.getImage_file() != null) {
					partView.setImage_file(partView.getImage_file() + ','+ partView.getImage_default());
				} else {
					partView.setImage_file(arr[0].replaceAll(
							EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath(),
							EopSetting.FILE_STORE_PREFIX));
				}

				partnerManager.saveKeeponPartner(partView,addMonths);
				this.json = "{'result':2,'message':'操作成功'}";
			}else{
				this.json = "{'result':2,'message':'操作失败'}";
			}
			
		} catch (Exception e) {
			this.json = "{'result':1,'message':'操作失败'}";
			e.printStackTrace();
		}

		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 续签
	 * 
	 * @return
	 */
	public String saveKeeponPartner() {

		try {
			if(partnerManager.isSaveExits(partView.getPartner_id(),partView.getState(), partView.getSequ())){
				String arr[] = new String[1];

				arr[0] = this.getRequest().getParameter("image_file");
				partnerAlbumManager.proessPhoto(arr, partners);
				partView.setImage_default(arr[0].replaceAll(EopSetting.IMG_SERVER_DOMAIN+EopContext.getContext().getContextPath(),EopSetting.FILE_STORE_PREFIX));

				if (partView.getImage_file() != null) {
					partView.setImage_file(partView.getImage_file() + ','
							+ partView.getImage_default());
				} else {
					partView.setImage_file(arr[0].replaceAll(
							EopSetting.IMG_SERVER_DOMAIN
									+ EopContext.getContext().getContextPath(),
							EopSetting.FILE_STORE_PREFIX));
				}

				partnerManager.saveKeeponPartner(partView,addMonths);
				this.json = "{'result':2,'message':'操作成功'}";
			}else{
				this.json = "{'result':2,'message':'操作失败'}";
			}
			
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 分级冻结
	 * 
	 * @return
	 */
	public String saveGradingPartner() {
		try {
			if(partnerManager.isSaveExits(partView.getPartner_id(), Consts.PARTNER_STATE_CONGELATION, Consts.PARTNER_SEQ_0)){
				json = "{'result':1,'message':'该分销商已冻结不能分级冻结！'}";
			}else{
			partnerManager.editGoodsUsage(partView.getUserid(), usage,
					block_reason);
			this.json = "{'result':2,'message':'操作成功'}";
			}
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}
	/**
	 * 分级解冻
	 * 
	 * @return
	 */
	public String saveGradingOpenPartner() {
		try {
			if(partnerManager.isSaveExits(partView.getPartner_id(), Consts.PARTNER_STATE_CONGELATION, Consts.PARTNER_SEQ_0)){
				json = "{'result':1,'message':'该分销商已冻结不能分级解结！'}";
			}else{
				partnerManager.editGoodsUsageOpen(partView.getUserid(), usage,
						block_reason);
				this.json = "{'result':2,'message':'操作成功'}";
			}
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}
	public String getFlag2() {
		return flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	/**
	 * 密码修改
	 * 
	 * @return
	 */
	public String editPassword() {
		try {
			String currUserId = ManagerUtils.getUserId();
			AdminUserUpdateReq adminUserEditReq = new AdminUserUpdateReq();
			adminUserEditReq.setOldPassword(oldPassword);
			adminUserEditReq.setNewpassword(newPassword);
			adminUserEditReq.setUserid(currUserId);
			
			AdminUserEditResp adminUserEditResp = new AdminUserEditResp();
			adminUserEditResp = adminUserServ.updatePassword(adminUserEditReq);
			
			int i = 0;
			if(adminUserEditResp != null){
				i = adminUserEditResp.getCount();
			}
			if (i == 0) {
				this.json = "{'result':0,'message':'旧密码错误！'}";
			} else {
				this.json = "{'result':1,'message':'密码修改成功'}";
			}
		} catch (RuntimeException e) {
			this.json = "{'result':0,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String editScore() {
		try {
			partner.setLast_update_date(DBTUtil.current());

			partnerManager.editUpdatePartner(ReflectionUtil.po2Map(partner),
					partner.getPartner_id());
			this.json = "{'result':0,'message':'修改成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'修改失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String delete() {
		try {
			String a[] = id.split(",");
			String idstr = "";
			for (int i = 0; i < a.length; i++) {
				idstr += a[i].replaceAll(a[i], "'"+ a[i].trim() +"'") +',';
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			partnerManager.delete(idstr);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String deleteStaff() {
		try {
			String a[] = id.split(",");
			String idstr = "";
			for (int i = 0; i < a.length; i++) {
				idstr += a[i].replaceAll(a[i], "'" + a[i] + "'") + ',';
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			partnerStaffManager.delete(idstr,this.partner_id);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String getPartnerCount() {
		int suminfo = 0;// 待审核资料变更
		int sumb = 0;// 已冻结分销商
		int sumapply = 0;// 待审核分销商
		String userid = ManagerUtils.getUserId();
		try {
			int founder = ManagerUtils.getFounder();
			if (ManagerUtils.isNetStaff()) {
				suminfo = this.partnerManager.getCountSql(Consts.CURR_FOUNDER0,
						Consts.PARTNER_STATE_NORMAL, Consts.PARTNER_SEQ_W1,
						userid, Consts.FIELD_AUDIT_LIST_1);
				sumb = this.partnerManager.getCountSql(Consts.CURR_FOUNDER0,
						Consts.PARTNER_STATE_CONGELATION, Consts.PARTNER_SEQ_0,
						userid, Consts.BLOCK_LIST_2);
			} else if (ManagerUtils.isFirstPartner()) {
				sumapply = this.partnerManager.getCountSql(
						Consts.CURR_FOUNDER3, Consts.PARTNER_STATE_APPLY,
						Consts.PARTNER_SEQ_W1, userid,
						Consts.AUDIT_PATNER_LIST_0);
				suminfo = this.partnerManager.getCountSql(Consts.CURR_FOUNDER3,
						Consts.PARTNER_STATE_NORMAL, Consts.PARTNER_SEQ_W1,
						userid, Consts.FIELD_AUDIT_LIST_1);
				sumb = this.partnerManager.getCountSql(Consts.CURR_FOUNDER3,
						Consts.PARTNER_STATE_CONGELATION, Consts.PARTNER_SEQ_0,
						userid, Consts.BLOCK_LIST_2);

			}

			Map map = new HashMap();
			map.put("sumapply", sumapply + "");
			map.put("suminfo", suminfo + "");
			map.put("sumb", sumb + "");
			String json = ManagerUtils.toJsonString(map);
			this.json = "{'result':1," + json + "}";
		} catch (RuntimeException e) {
			this.json = "{'result':0,'message':'查询异常'}";
			e.printStackTrace();
		}

		return WWAction.JSON_MESSAGE;
	}

	public List getGoodsUsageList() {
		return goodsUsageList;
	}

	public void setGoodsUsageList(List goodsUsageList) {
		this.goodsUsageList = goodsUsageList;
	}

	public String[] getUsage() {
		return usage;
	}

	public void setUsage(String[] usage) {
		this.usage = usage;
	}


	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	public AdminUser getAdminuser() {
		return adminuser;
	}

	public void setAdminuser(AdminUser adminuser) {
		this.adminuser = adminuser;
	}

	public String getBlock_reason() {
		return block_reason;
	}

	public void setBlock_reason(String block_reason) {
		this.block_reason = block_reason;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSequ() {
		return sequ;
	}

	public void setSequ(Integer sequ) {
		this.sequ = sequ;
	}

	public String getStateType() {
		return stateType;
	}

	public void setStateType(String stateType) {
		this.stateType = stateType;
	}

	public List getColumnNamelist() {
		return columnNamelist;
	}

	public void setColumnNamelist(List columnNamelist) {
		this.columnNamelist = columnNamelist;
	}

	public PartnerStaff getPartStaff() {
		return partStaff;
	}

	public void setPartStaff(PartnerStaff partStaff) {
		this.partStaff = partStaff;
	}

	public IPartnerManager getPartnerManager() {
		return partnerManager;
	}

	public void setPartnerManager(IPartnerManager partnerManager) {
		this.partnerManager = partnerManager;
	}

	public List getLvlist() {
		return lvlist;
	}

	public void setLvlist(List lvlist) {
		this.lvlist = lvlist;
	}

	public String getName() {
		return name;
	}

	public Map getPartners() {
		return partners;
	}

	public void setPartners(Map partners) {
		this.partners = partners;
	}

	public String[] getPicnames() {
		return picnames;
	}

	public void setPicnames(String[] picnames) {
		this.picnames = picnames;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public PartnerShop getPartShop_s() {
		return partShop_s;
	}

	public void setPartShop_s(PartnerShop partShop_s) {
		this.partShop_s = partShop_s;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrUserId() {
		return currUserId;
	}

	public IPartnerShopManager getPartnerShopManager() {
		return partnerShopManager;
	}

	public void setPartnerShopManager(IPartnerShopManager partnerShopManager) {
		this.partnerShopManager = partnerShopManager;
	}

	public IPartnerStaffManager getPartnerStaffManager() {
		return partnerStaffManager;
	}

	public List getHistoryOldList() {
		return historyOldList;
	}

	public void setHistoryOldList(List historyOldList) {
		this.historyOldList = historyOldList;
	}
	public void setPartnerStaffManager(IPartnerStaffManager partnerStaffManager) {
		this.partnerStaffManager = partnerStaffManager;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Boolean getIs_edit() {
		return is_edit;
	}

	public void setIs_edit(Boolean is_edit) {
		this.is_edit = is_edit;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Map getTabs() {
		return tabs;
	}

	public void setTabs(Map tabs) {
		this.tabs = tabs;
	}

	public void setCurrUserId(String currUserId) {
		this.currUserId = currUserId;
	}

	public void setCurrFounder(Integer currFounder) {
		this.currFounder = currFounder;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Integer getAddMonths() {
		return addMonths;
	}

	public void setAddMonths(Integer addMonths) {
		this.addMonths = addMonths;
	}

	public String getOrder() {
		return order;
	}

	public Partner getPart_serch() {
		return part_serch;
	}

	public void setPart_serch(Partner part_serch) {
		this.part_serch = part_serch;
	}

	public Partner getPartLogout_s() {
		return partLogout_s;
	}

	public void setPartLogout_s(Partner partLogout_s) {
		this.partLogout_s = partLogout_s;
	}

	public Partner getPartBlock_s() {
		return partBlock_s;
	}

	public void setPartBlock_s(Partner partBlock_s) {
		this.partBlock_s = partBlock_s;
	}

	public Integer getMsequ() {
		return msequ;
	}

	public void setMsequ(Integer msequ) {
		this.msequ = msequ;
	}

	public Integer getSsequ() {
		return ssequ;
	}

	public void setSsequ(Integer ssequ) {
		this.ssequ = ssequ;
	}

	public Partner getPartView() {
		return partView;
	}

	public void setPartView(Partner partView) {
		this.partView = partView;
	}

	public PartnerShop getPartShopView() {
		return partShopView;
	}

	public void setPartShopView(PartnerShop partShopView) {
		this.partShopView = partShopView;
	}

	public PartnerAddress getPartAddrView() {
		return partAddrView;
	}

	public void setPartAddrView(PartnerAddress partAddrView) {
		this.partAddrView = partAddrView;
	}

	public List<PartnerStaff> getPartStafList() {
		return partStafList;
	}

	public Partner getPartAudit_s() {
		return partAudit_s;
	}

	public void setPartAudit_s(Partner partAudit_s) {
		this.partAudit_s = partAudit_s;
	}

	public void setPartStafList(List<PartnerStaff> partStafList) {
		this.partStafList = partStafList;
	}

	public String getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}

	public String getId() {
		return id;
	}

	public List getHistroyList() {
		return histroyList;
	}

	public void setHistroyList(List histroyList) {
		this.histroyList = histroyList;
	}

	public IPartnerAlbumManager getPartnerAlbumManager() {
		return partnerAlbumManager;
	}

	public void setPartnerAlbumManager(IPartnerAlbumManager partnerAlbumManager) {
		this.partnerAlbumManager = partnerAlbumManager;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getStaff_code() {
		return staff_code;
	}

	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}

	public String[] getDetail_imgage_arr() {
		return detail_imgage_arr;
	}

	public void setDetail_imgage_arr(String[] detail_imgage_arr) {
		this.detail_imgage_arr = detail_imgage_arr;
	}
	
	
}