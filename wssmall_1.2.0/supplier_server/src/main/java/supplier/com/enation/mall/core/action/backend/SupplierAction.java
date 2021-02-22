package supplier.com.enation.mall.core.action.backend;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import params.adminuser.req.AdminUserCountReq;
import params.adminuser.req.AdminUserDelReq;
import params.adminuser.req.AdminUserIdReq;
import params.adminuser.req.AdminUserReq;
import params.adminuser.req.AdminUserUpdateReq;
import params.adminuser.req.SupplierAddReq;
import params.adminuser.resp.AdminUserCountResp;
import params.adminuser.resp.AdminUserEditResp;
import params.adminuser.resp.AdminUserIdResp;
import params.adminuser.resp.AdminUserResp;
import services.AdminUserInf;
import services.ModHandlerInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Supplier;
import com.ztesoft.net.app.base.core.model.SupplierAccount;
import com.ztesoft.net.app.base.core.model.SupplierAgent;
import com.ztesoft.net.app.base.core.model.SupplierCertificate;
import com.ztesoft.net.app.base.core.model.SupplierResources;
import com.ztesoft.net.app.base.core.model.SupplierStaff;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.AllAgreement;
import com.ztesoft.net.mall.core.model.CompanyInfo;
import com.ztesoft.net.mall.core.service.ICompanyInfoManager;
import com.ztesoft.net.mall.core.service.ISupplierAccountManager;
import com.ztesoft.net.mall.core.service.ISupplierAgentManager;
import com.ztesoft.net.mall.core.service.ISupplierCertificateManager;
import com.ztesoft.net.mall.core.service.ISupplierManager;
import com.ztesoft.net.mall.core.service.ISupplierResourcesManager;
import com.ztesoft.net.mall.core.service.ISupplierStaffManager;
import com.ztesoft.net.mall.core.service.SupplierStatus;
import com.ztesoft.net.mall.core.service.impl.AgreementManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;


/*
 * 
 * 供货商管理
 */
public class SupplierAction extends WWAction {

	private String id, supplier_id;
	private String parent_id;
	private String password;
	private String email;
	private String qq;
	private String user_name;
	private String sex;
	private String id_card;
	private String phone;
	private String other_phone;
	private String supplier_state;
	private String supplier_type;

	private String number;
	private String company_id;
	private String supplier_id_edit;

	private String english_name;
	private int enterprise_type;
	private String abbreviation;
	private String website;
	private int is_abroad;
	private String register_address;
	private String register_date;
	private String legal_name;
	private String legal_id_card;
	private String register_funds;
	private int currency;
	private String period_validity;
	private String license_number;
	private String license_url;
	private String country_registr_number;
	private String general_tax_url;
	private String place_registr_number;
	private String place_registr_url;
	private String ticket_type;
	private String organ_code;

	private String account_id;
	private String open_bank;
	private String address;
	private String accoun_name;
	private String bank_account;
	private String account_attachment;
	private String is_default;

	private String agent_id;
	private String agent_name;
	private String agent_type;
	private String agent_certificate_number;
	private String agent_attachment;

	private String certificate_id;
	private String certificate_number;
	private String certificate_name;
	private String license_issuing_organs;
	private String certificate_period_validity;
	private String certificate_url;

	private String resources_id;
	private String year;
	private String employees_number;
	private String production;
	private String administration;
	private String research;
	private String support;
	private String marketing_sales;
	private String company_desc;
	private Boolean is_edit = false;// 增加或修改
	private String flag;// add-增加 edit-修改 view-查看
	private String is_administrator;
	private String oldPassword, newPassword;
	private String staff_id;
	private String[] license_url_thumbnail_images;
	private String[] general_tax_url_thumbnail_images;
	private String[] place_registr_url_thumbnail_images;
	private String[] agent_attachment_thumbnail_images;
	private String[] certificate_url_thumbnail_images;
	private String[] account_attachment_thumbnail_images;
	private String default_account_attachment;
	private String default_license_url;
	private String default_general_tax_url;
	private String default_place_registr_url;
	private String default_agent_attachment;
	private String default_certificate_url;

	private String company_name, account_number;
	private List abroadList;// 是否境外企业
	private List currencyList;// 币种
	private List enterpriseTypeList;// 企业类型
	private List ticketTypeList;// 开票类型
	private List companyList;// 公司列表
	private List showAgentTypeList;// 产品类别
	private ISupplierManager supplierManager;
	private ICompanyInfoManager companInfoManager;
	private ISupplierAccountManager supplierAccountManager;
	private ISupplierAgentManager supplierAgentManager;
	private ISupplierCertificateManager supplierCertificateManager;
	private ISupplierResourcesManager supplierResourcesManager;
	private ISupplierStaffManager supplierStaffManager;
	private AdminUserInf adminUserServ;
	private Supplier supplier;
	private AdminUser adminUser;
	private SupplierStaff supplierStaff;
	private CompanyInfo companyInfo1;
	private SupplierAccount supplierAccount;
	private SupplierAgent supplierAgent;
	private SupplierResources supplierResources;
	private SupplierCertificate supplierCertificate;
	private List<SupplierStaff> supplierStaffList;
	private List<SupplierAccount> supplierAccountList;
	private List<SupplierAgent> supplierAgentList;
	private List<SupplierResources> supplierResourcesList;
	private List<SupplierCertificate> supplierCertificateList;
	private List<Supplier> suppliersList;
	private ModHandlerInf modInfoHandler ;

	private AgreementManager supplierAgreementManager;
	private AllAgreement supplierAgreement;
	private File file_url;
	private String ref_obj_id;
	private String new_change;
	private String old_change;
	private Integer flow_inst_id;
	private List modAudit;
	private String date_type;//日期类型（昨天和今天）
	
	private String beginTime;
	private String endTime;

	private HttpServletRequest request;

	public String isAbroad() {
		abroadList = supplierManager.isAbroad();

		return "is_abroad";
	}

	public String showCurrency() {
		currencyList = supplierManager.showCurrency();
		return "show_currency";
	}

	public String showEnterpriseType() {
		enterpriseTypeList = supplierManager.showEnterpriseType();
		return "show_enterprise_type";
	}

	public String showTicketType() {
		ticketTypeList = supplierManager.showTicketType();
		return "show_ticket_type";
	}

	public String supplierSequences() {

		try {
			supplier_id = supplierManager.getSupplierSequences();
			json = "{'result':0,'message':'" + supplier_id + "'}";
		} catch (RuntimeException e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}

		return WWAction.JSON_MESSAGE;
	}

	public String isExits() {

		try {

			if (!StringUtil.isEmpty(company_name)) {
				try {
					company_name = URLDecoder.decode(company_name, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (supplierManager.isCompanyNameExits(company_name)) {
					json = "{'result':2,'message':'公司名称已存在！'}";
				} else {
					json = "{'result':0,'message':'可以添加！'}";
				}
			} else if (!StringUtil.isEmpty(account_number)) {
				try {
					account_number = URLDecoder.decode(account_number, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (supplierManager.isAccountNumberExits(account_number)) {
					json = "{'result':2,'message':'账号已存在！'}";
				} else {
					String regex = "^\\w+$";
					Pattern pat = Pattern.compile(regex);
					Matcher mat = pat.matcher(account_number); 
					boolean rs = mat.find();
					if(rs){
						json = "{'result':0,'message':'可以添加！'}";
					} else {
						json = "{'result':2,'message':'必须是英文、数字、下划线！'}";
					}
				}

			}
		} catch (RuntimeException e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	public String showParentDialogByName() {
		if (StringUtils.isNotEmpty(company_name)) {
			try {
				company_name = URLDecoder.decode(company_name, "utf8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		this.webpage = companInfoManager.fineCompanyByName(company_name, this
				.getPage(), this.getPageSize());

		return "show_parent_dialog";
	}

	public String showAgentType() {
		showAgentTypeList = supplierManager.showAgentType();
		return "show_agent_type";
	}

	public String add() {
		try {
			AdminUserIdResp adminUserIdResp = new AdminUserIdResp();
			AdminUserIdReq adminUserIdReq = new AdminUserIdReq();
			adminUserIdResp = this.adminUserServ.getAdminUserSequences(adminUserIdReq);
			
			String adminUserId = "";
			if(adminUserIdResp != null){
				adminUserId = adminUserIdResp.getAdminUserId();
			}

			// 供货商基本资料
			supplierManager.dataDeal(supplier_id, company_name, parent_id,
					email, id_card, other_phone, password, qq, sex, user_name,
					account_number, phone, adminUserId, supplier_type);

			SupplierAddReq supplierAddReq = new SupplierAddReq();
			supplierAddReq.setAdminUserId(adminUserId);
			supplierAddReq.setSupplier_id(supplier_id);
			supplierAddReq.setAccount_number(account_number);
			supplierAddReq.setPassword(password);
			supplierAddReq.setPhone(phone);
			supplierAddReq.setCompany_name(company_name);
			supplierAddReq.setSupplier_type(supplier_type);
			
			AdminUserIdResp adminUserIdResp2 = new AdminUserIdResp();
			adminUserIdResp2 = this.adminUserServ.addSupplier(supplierAddReq);
			
			if(adminUserIdResp2 != null){
				adminUserId = adminUserIdResp2.getAdminUserId();
			}

			// 公司信息
			companInfoManager.dataDeal(company_name, english_name, ticket_type,
					enterprise_type, abbreviation, website, is_abroad,
					register_address, register_date, legal_name, legal_id_card,
					register_funds, currency, period_validity, license_number,
					license_url, country_registr_number, general_tax_url,
					place_registr_number, place_registr_url, organ_code,
					supplier_id);

			// 供货商账户
			supplierAccountManager.dataDeal(open_bank, address, accoun_name,
					bank_account, account_attachment, is_default, supplier_id);

			// 供货商代理商
			supplierAgentManager.dataDeal(agent_name, agent_certificate_number,
					agent_attachment, agent_type, supplier_id);

			// 供货商证书
			supplierCertificateManager.dataDeal(certificate_number,
					certificate_name, certificate_period_validity,
					certificate_url, license_issuing_organs, supplier_id);

			// 供货商资源情况
			supplierResourcesManager.dataDeal(administration, company_desc,
					employees_number, marketing_sales, production, research,
					year, support, supplier_id);

			// 起流程
			this.supplierManager.registerApply(adminUserId, supplier_id);
		} catch (Exception e) {
			e.printStackTrace();
			json = "{'result':1,'message':'注册失败！'}";
			return WWAction.JSON_MESSAGE;
		}
		json = "{'result':2,'message':'注册成功！'}";

		return WWAction.JSON_MESSAGE;
	}

	public String supplierEdit() {
		try {
			// 供货商基本资料修改
			supplierManager.modifySupplier(supplier_id_edit, company_name,
					parent_id, email, id_card, other_phone, qq, sex, user_name,
					account_number, phone, supplier_type);

			// 公司信息
			companInfoManager.modifyCompany(company_id, company_name,
					english_name, ticket_type, enterprise_type, abbreviation,
					website, is_abroad, register_address, register_date,
					legal_name, legal_id_card, register_funds, currency,
					period_validity, license_number, license_url,
					country_registr_number, general_tax_url,
					place_registr_number, place_registr_url, organ_code);

			// 供货商账户
			supplierAccountManager.dataDeal(open_bank, address, accoun_name,
					bank_account, account_attachment, is_default,
					supplier_id_edit);

			// 供货商代理商
			supplierAgentManager.dataDeal(agent_name, agent_certificate_number,
					agent_attachment, agent_type, supplier_id_edit);

			// 供货商证书
			supplierCertificateManager.dataDeal(certificate_number,
					certificate_name, certificate_period_validity,
					certificate_url, license_issuing_organs, supplier_id_edit);

			// 供货商资源情况
			supplierResourcesManager.dataDeal(administration, company_desc,
					employees_number, marketing_sales, production, research,
					year, support, supplier_id_edit);

		} catch (Exception e) {
			e.printStackTrace();
			json = "{'result':1,'message':'修改失败！'}";
			return WWAction.JSON_MESSAGE;
		}
		json = "{'result':2,'message':'修改成功！'}";

		return WWAction.JSON_MESSAGE;
	}

	public String findSupplieridByName() {
		try {
			suppliersList = supplierManager
					.findSupplieridByAccount_number(number);
		} catch (Exception e) {
			e.printStackTrace();
		}
		json = JSONArray.fromObject(suppliersList).toString();
		return WWAction.JSON_MESSAGE;
	}

	// 查询要修改的经销商注册信息
	public String queryDataToEditById() {
		Map map = new HashMap();
		try {
			map.put("supplier", supplierManager.findSupplierById(supplier_id));// 供应商资料
			map.put("companyInfo", companInfoManager
					.findCompanyInfoBySupplierId(supplier_id));// 企业公司信息
			map.put("supplierAccount", supplierAccountManager
					.findAccountBySupplierId(supplier_id));// 供应商账户
			map.put("supplierAgent", supplierAgentManager
					.findAgentBySupplierId(supplier_id));// 代理商
			map.put("supplierCertificate", supplierCertificateManager
					.findCertificateBySupplierId(supplier_id));// 认证证书
			map.put("supplierResources", supplierResourcesManager
					.findResourcesBySupplierId(supplier_id));// 人力资源
		} catch (Exception e) {
			e.printStackTrace();
		}
		json = JSONArray.fromObject(map).toString();
		return WWAction.JSON_MESSAGE;
	}

	public String list() {
		if (supplier_state == null) {
			supplier_state = SupplierStatus.SUPPLIER_STATE_ALL;
		}
		
		String currUserId = ManagerUtils.getUserId();
		AdminUserReq adminUserReq = new AdminUserReq();
		adminUserReq.setUser_id(currUserId);
		
		AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
	    adminUser = new AdminUser();
		if(adminUserResp != null){
			adminUser = adminUserResp.getAdminUser();
		}
		
		if(StringUtils.isEmpty(is_administrator)){
			is_administrator=SupplierStatus.is_administrator_1;
		}else{
			if(ManagerUtils.getFounder()==1){
				is_administrator=SupplierStatus.is_administrator_1;
			}else{
				is_administrator=SupplierStatus.is_administrator_0;
			}
		}
		int pageSize = 10;
		this.webpage = supplierManager.list(company_name, user_name, phone,
				supplier_state, supplier_type, is_administrator,date_type,
				"","",this.getPage(), pageSize);

		/**
		 * 以下代码是为了导出excel功能所编写 title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性 set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = { "公司名称", "电子邮件", "QQ号", "注册人姓名", "性别", "身份证号码",
				"联系电话", "状态", "创建时间" };
		String[] content = { "company_name", "email", "qq", "user_name", "sex",
				"id_card", "phone", "supplier_state", "register_time" };
		String fileTitle = "供货商数据导出";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		request.setAttribute("supplier_state", supplier_state);
		request.setAttribute("is_administrator", is_administrator);

		request.setAttribute("webpage", this.webpage);
		if (excel != null && !"".equals(excel)) {
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		} else {
			return "list";
		}
	}
	
	/**
	 * 广东联通ECS
	 * @return
	 */
	public String listECS() {
		if (supplier_state == null) {
			supplier_state = SupplierStatus.SUPPLIER_STATE_ALL;
		}
		
		String currUserId = ManagerUtils.getUserId();
		AdminUserReq adminUserReq = new AdminUserReq();
		adminUserReq.setUser_id(currUserId);
		
		AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
	    adminUser = new AdminUser();
		if(adminUserResp != null){
			adminUser = adminUserResp.getAdminUser();
		}
		
		if(StringUtils.isEmpty(is_administrator)){
			is_administrator=SupplierStatus.is_administrator_1;
		}
		int pageSize = 10;
		this.webpage = supplierManager.list(company_name, user_name, phone,
				supplier_state, supplier_type, is_administrator,date_type,
				"","",this.getPage(), pageSize);

		/**
		 * 以下代码是为了导出excel功能所编写 title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性 set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = { "公司名称", "电子邮件", "QQ号", "注册人姓名", "性别", "身份证号码",
				"联系电话", "状态", "创建时间" };
		String[] content = { "company_name", "email", "qq", "user_name", "sex",
				"id_card", "phone", "supplier_state", "register_time" };
		String fileTitle = "供货商数据导出";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		request.setAttribute("supplier_state", supplier_state);
		request.setAttribute("is_administrator", is_administrator);

		request.setAttribute("webpage", this.webpage);
		if (excel != null && !"".equals(excel)) {
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		} else {
			return "list_ecs";
		}
	}

	/***
	 * 商家审核列表
	 */
	public String list_check(){
		
		if (supplier_state == null) {
			supplier_state = SupplierStatus.SUPPLIER_STATE_ALL;
		}
		
		String currUserId = ManagerUtils.getUserId();
		AdminUserReq adminUserReq = new AdminUserReq();
		adminUserReq.setUser_id(currUserId);
		
		AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
	    adminUser = new AdminUser();
		if(adminUserResp != null){
			adminUser = adminUserResp.getAdminUser();
		}
		
		if(StringUtils.isEmpty(is_administrator)){
			is_administrator=SupplierStatus.is_administrator_1;
		}
		int pageSize = 10;
		this.webpage = supplierManager.list_check(company_name, user_name, qq,
				email, beginTime, endTime,this.getPage(), pageSize);

		/**
		 * 以下代码是为了导出excel功能所编写 title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性 set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = { "公司名称", "电子邮件", "QQ号", "注册人姓名", "性别",
				"联系电话", "状态", "创建时间" };
		String[] content = { "company_name", "email", "qq", "user_name", "sex",
				 "phone", "state", "register_time" };
		String fileTitle = "供货商数据导出";

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		request.setAttribute("state", supplier_state);
		request.setAttribute("is_administrator", is_administrator);

		request.setAttribute("webpage", this.webpage);
		if (excel != null && !"".equals(excel)) {
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		} else {
			return "list_check";
		}
		
	}
	/**
	 * 审核商家
	 */
	public String set_check(){
		
		supplierManager.setSupplierState(supplier_id);
		return "list_check";
	}
	/**
	 * 去修改密码页面
	 */
	public String updpassword() {
		String currUserId = ManagerUtils.getUserId();
		AdminUserReq adminUserReq = new AdminUserReq();
		adminUserReq.setUser_id(currUserId);
		
		AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
	    adminUser = new AdminUser();
		if(adminUserResp != null){
			adminUser = adminUserResp.getAdminUser();
		}
		return "updpassword";
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

	// 删除供货商
	public String delete() {
		try {
			String a[] = id.split(",");
			String idstr = "";
			for (int i = 0; i < a.length; i++) {
				idstr += a[i].replaceAll(a[i], "'" + a[i] + "'") + ',';
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			supplierManager.delete(idstr.trim());
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	// 注销供货商
	public String cancellation() {
		try {
			String a[] = id.split(",");
			String idstr = "";
			for (int i = 0; i < a.length; i++) {
				idstr += a[i].replaceAll(a[i], "'" + a[i] + "'") + ',';
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			supplierManager.cancellation(idstr.trim());
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 待审核供货商和经销商列表
	 */
	public String auditlist() {
		this.webpage = this.supplierManager.auditlist(company_name, user_name,
				supplier_type, phone, this.getPage(), this.getPageSize());
		return "auditlist";
	}

	/**
	 * 是否已被审核
	 * 
	 * @return
	 */
	public String isAudit() {
		supplier = supplierManager.getSupplierSequM1AndState0(supplier_id);
		if (null != supplier) {
			this.json = "{'result':0,'message':'未被审核'}";
		} else {
			this.json = "{'result':1,'message':'已被审核'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 供货商审核窗口
	 */
	public String showaudit() {
		supplier = supplierManager.getSupplierSequM1AndState0(supplier_id); // 基本信息
		return "showaudit";
	}

	/**
	 * 经销商审核窗口
	 */
	public String showauditdealer() {
		supplier = supplierManager.getSupplierSequM1AndState0(supplier_id); // 基本信息
		return "showauditdealer";
	}

	/**
	 * 审核供货商
	 * 
	 * @return
	 */
	public String saveAuditSupplier() {
		try {
			if (supplierManager.isSaveExits(supplier_id,
					Consts.PARTNER_STATE_NORMAL)) {
				this.json = "{'result':2,'message':'操作失败!'}";
			} else {

				if (supplier_state.equals(Consts.PARTNER_STATE_NORMAL)) {
					// 发送短信
					supplierManager.saveAuditSupplier(supplier_id,
							supplier_state);
					supplier = supplierManager.getSupplierById(supplier_id);
					/*smsManager.addSupplierAuditMsg(supplier.getUser_name(),
							supplier.getPhone(), Consts.PARNTER_APPLY_SUCCESS);*/
					this.json = "{'result':2,'message':'操作成功!'}";
				} else {
					supplierManager.saveAuditSupplier(supplier_id,
							supplier_state);
					supplier = supplierManager.getSupplierById(supplier_id);
					this.json = "{'result':2,'message':'操作成功!'}";
					/*smsManager.addSupplierAuditMsg(supplier.getUser_name(),
							supplier.getPhone(), Consts.PARNTER_APPLY_FAIL);// 发送短信*/
				}
			}
		} catch (RuntimeException e) {

			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 审核经销商
	 * 
	 * @return
	 */
	public String saveAuditDealer() {
		try {
			if (supplierManager.isSaveExits(supplier_id,
					Consts.PARTNER_STATE_NORMAL)) {
				this.json = "{'result':2,'message':'操作失败!'}";
			} else {

				if (supplier_state.equals(Consts.PARTNER_STATE_NORMAL)) {
					// 发送短信
					supplier = supplierManager.getSupplierById(supplier_id);
					supplierManager.addMember(supplier);
					supplierManager.saveAuditSupplier(supplier_id,
							supplier_state);
					/*smsManager.addSupplierAuditMsg(supplier.getUser_name(),
							supplier.getPhone(), Consts.PARNTER_APPLY_SUCCESS);*/
					this.json = "{'result':2,'message':'操作成功!系统将会给该经销商默认注册一个会员账号!!'}";
				} else {
					supplierManager.saveAuditSupplier(supplier_id,
							supplier_state);
					supplier = supplierManager.getSupplierById(supplier_id);
					this.json = "{'result':2,'message':'操作成功!'}";
					/*smsManager.addSupplierAuditMsg(supplier.getUser_name(),
							supplier.getPhone(), Consts.PARNTER_APPLY_FAIL);// 发送短信*/
				}
			}
		} catch (RuntimeException e) {

			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 增加或修改 去tab页面
	 * 
	 * @return
	 */
	public String detail() {
		if (StringUtils.isNotEmpty(ref_obj_id)) {
			supplier_id = ref_obj_id;
		}
		if("add".equals(flag)){
			supplier_id=this.supplierManager.getSupplierSequences();
		}
		is_edit = true;

		//广东联通ECS
		if ("ECS".equalsIgnoreCase(ManagerUtils.getSourceFrom())) {
			return "detail_ecs";
		}
		
		return "detail";

	}

	// 基本信息
	public String baseTab() {

		if (is_edit) {

			supplier = supplierManager.getSupplierById(supplier_id); // 基本信息

		} else {
			supplier = null;
		}

		return "baseTab";
	}

	// 修改基本信息
	public String editBase() {
		try {
			if("edit".equals(flag)){
				supplierManager.edit(supplier); // 修改基本信息
				json = "{'result':0,'message':'修改成功！'}";
			}else if("add".equals(flag)){
				
				AdminUserIdResp adminUserIdResp = new AdminUserIdResp();
				AdminUserIdReq adminUserIdReq = new AdminUserIdReq();
				adminUserIdResp = this.adminUserServ.getAdminUserSequences(adminUserIdReq);
				
				String adminUserId = "";
				if(adminUserIdResp != null){
					adminUserId = adminUserIdResp.getAdminUserId();
				}
				String passwordold=supplier.getPassword();
				// 添加供货商基本资料
				supplier.setSupplier_id(supplier_id);
				supplier.setPassword(StringUtil.md5(supplier.getPassword()));
				supplier.setSupplier_state(SupplierStatus.SUPPLIER_STATE_2);
				supplier.setRegister_time(DBTUtil.current());
				supplier.setSupplier_type(SupplierStatus.TYPE_1);
				supplier.setUserid(adminUserId);
				supplierManager.add(supplier);
				
				
				SupplierAddReq supplierAddReq = new SupplierAddReq();
				supplierAddReq.setAdminUserId(adminUserId);
				supplierAddReq.setSupplier_id(supplier_id);
				supplierAddReq.setAccount_number(supplier.getAccount_number());
				supplierAddReq.setPassword(passwordold);
				supplierAddReq.setPhone(supplier.getPhone());
				supplierAddReq.setCompany_name(supplier.getCompany_name());
				supplierAddReq.setSupplier_type(supplier.getSupplier_type());
				
				AdminUserIdResp supplierAddResp = new AdminUserIdResp();
				supplierAddResp = this.adminUserServ.addSupplier(supplierAddReq);
				
				if(supplierAddResp != null){
					adminUserId = supplierAddResp.getAdminUserId();
				}
				
				json = "{'result':0,'message':'添加成功！'}";
			}
			
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	//审核的基本历史字段信息
	public String  auditSupplier(){
		modAudit=this.modInfoHandler.qryModInfo(flow_inst_id,ref_obj_id);
		
		return "showaudit";
	}

	// 公司信息
	public String companyTab() {
		if (is_edit) {
			companyInfo1 = companInfoManager
					.findCompanyInfoBySupplierId(supplier_id); // 公司信息
			if(companyInfo1==null){//没有添加公司信息
				companyInfo1 = new CompanyInfo();
				companyInfo1.setSupplier_id(supplier_id);
				companyInfo1.setCompany_id(this.companInfoManager.getCompanyInfoSequence());
				is_edit = false;
				flag = "add";
			}
		} else {
			companyInfo1 = null;
		}

		if (companyInfo1 != null) {
			Map companyInfo = null;

			companyInfo = ReflectionUtil.po2Map(companyInfo1);

			license_url_thumbnail_images = companInfoManager
					.onFillCompanyInfoEditInput(companyInfo, "license_url");// 营业执照附件
			
			if(!ArrayUtils.isEmpty(license_url_thumbnail_images)){
				default_license_url = license_url_thumbnail_images[0];
			}

			general_tax_url_thumbnail_images = companInfoManager
					.onFillCompanyInfoEditInput(companyInfo, "general_tax_url");// 一般纳税人证件附件
			
			if(!ArrayUtils.isEmpty(general_tax_url_thumbnail_images)){
				default_general_tax_url = general_tax_url_thumbnail_images[0];
			}
			

			place_registr_url_thumbnail_images = companInfoManager
					.onFillCompanyInfoEditInput(companyInfo,
							"place_registr_url");// 地方税务登记号附件
			
			if(!ArrayUtils.isEmpty(place_registr_url_thumbnail_images)){
				default_place_registr_url = place_registr_url_thumbnail_images[0];
			}
		}
		return "companyTab";
	}

	// 修改公司信息
	public String editCompany() {
		try {
			if("edit".equals(flag)){
				supplier = this.supplierManager.findSupplierById(companyInfo1
						.getSupplier_id());
				companInfoManager.edit(companyInfo1, supplier.getSupplier_state()); // 修改公司信息
				json = "{'result':0,'message':'修改成功！'}";
			}else if("add".equals(flag)){
				companyInfo1.setSupplier_id(supplier_id);
				companyInfo1.setState(SupplierStatus.SUPPLIER_STATE_2);
				companyInfo1.setRegister_time(SupplierStatus.SYSDATE);
				this.companInfoManager.add(companyInfo1);
				json = "{'result':0,'message':'添加成功！'}";
			}
			
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！请先保存基本信息！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	
	public String isStaffAccountNumberExits() {

		try {

			try {
				account_number = URLDecoder.decode(account_number, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (supplierStaffManager.isStaffAccountNumberExits(account_number)) {
				json = "{'result':2,'message':'账号已存在！'}";
			} else {
				json = "{'result':0,'message':'可以添加！'}";
			}
		} catch (RuntimeException e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	// 员工信息
	public String staffTab() {

		if (is_edit) {
			supplierStaffList = supplierStaffManager.list(supplier_id);// 员工列表
		} else {
			supplierStaffList = null;
		}
		return "staffTab";
	}

	public String isExitStaffAccountNumber(){
		try {
			account_number = URLDecoder.decode(account_number, "utf-8");
			AdminUserCountReq adminUserCountReq = new AdminUserCountReq();
			adminUserCountReq.setUsername(account_number);
			
			AdminUserCountResp adminUserCountResp = new AdminUserCountResp();
			adminUserCountResp = this.adminUserServ.getUserCountByUserName(adminUserCountReq);
			
			int count = 0;
			if(adminUserCountResp != null){
				count = adminUserCountResp.getCount();
			}
			
			
			if (count == 0) {
				this.json = "{'result':0,'message':'可以添加'}";
			}else{
				this.json = "{'result':1,'message':'账号已存在!'}";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			this.json = "{'result':2,'message':'操作失败!'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	// 绑定员工工号
	public String bindingstaff() {
		supplierStaff = this.supplierStaffManager
				.findSupplierStaffById(staff_id);

		AdminUserCountReq adminUserCountReq = new AdminUserCountReq();
		adminUserCountReq.setUsername(supplierStaff.getAccount_number());
		
		AdminUserCountResp adminUserCountResp = new AdminUserCountResp();
		adminUserCountResp = this.adminUserServ.getUserCountByUserName(adminUserCountReq);
		
		int count = 0;
		if(adminUserCountResp != null){
			count = adminUserCountResp.getCount();
		}
		
		
		if (count == 0) {
			
			AdminUserIdResp supplierStaffResp = new AdminUserIdResp();
			supplierStaffResp = this.adminUserServ.bindingstaff(supplierStaff);
			
			String adminUserId = "";
			if(supplierStaffResp != null){
				adminUserId = supplierStaffResp.getAdminUserId();
			}
			

			this.supplierStaffManager.bindingstaff(staff_id, adminUserId);
			this.json = "{'result':0,'message':'绑定成功'}";
		} else {
			this.json = "{'result':1,'message':'用户名已存在,请修改员工账号信息'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	// 解绑员工工号
	public String unbundlingstaff() throws Exception {
		supplierStaff = this.supplierStaffManager
				.findSupplierStaffById(staff_id);

		AdminUserCountReq adminUserCountReq = new AdminUserCountReq();
		adminUserCountReq.setUsername(supplierStaff.getAccount_number());
		
		AdminUserCountResp adminUserCountResp = new AdminUserCountResp();
		adminUserCountResp = this.adminUserServ.getUserCountByUserName(adminUserCountReq);
		
		int count = 0;
		if(adminUserCountResp != null){
			count = adminUserCountResp.getCount();
		}
		
		if (count == 1) {
			this.supplierStaffManager.unbundlingstaff(staff_id);

			AdminUserDelReq adminUserDelReq = new AdminUserDelReq();
			adminUserDelReq.setUser_id(supplierStaff.getUserid());
			
			this.adminUserServ.deleteAdminUsesr(adminUserDelReq);
			this.json = "{'result':0,'message':'解绑成功'}";
		} else {
			this.json = "{'result':1,'message':'解绑失败,工号不存在'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 去添加供货商员工页面
	 * 
	 * @return
	 */
	public String showStaffAdd() {
		return "showStaffAdd";
	}

	/**
	 * 添加员工信息
	 * 
	 * @return
	 */
	public String saveAddStaff() {

		try {
			supplierStaffManager.add(account_number, user_name, sex, password,
					phone, email, supplier_id, qq, id_card);
			this.json = "{'result':0,'message':'添加成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'添加失败'}";
		}

		return WWAction.JSON_MESSAGE;

	}

	/**
	 * 去修改供货商员工页面
	 * 
	 * @return
	 */
	public String showStaffEdit() {
		supplierStaff = this.supplierStaffManager
				.findSupplierStaffById(staff_id);
		return "showStaffEdit";
	}

	/**
	 * 修改员工信息
	 * 
	 * @return
	 */
	public String saveStaffEdit() {

		try {
			supplierStaffManager.update(account_number, user_name, sex,
					password, phone, email, staff_id, qq, id_card);
			this.json = "{'result':0,'message':'修改成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'修改失败'}";
		}

		return WWAction.JSON_MESSAGE;

	}

	// 删除员工
	public String deleteStaff() {
		try {
			String a[] = id.split(",");
			String idstr = "";
			for (int i = 0; i < a.length; i++) {
				idstr += a[i].replaceAll(a[i], "" + a[i] + "") + ',';
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			supplierStaffManager.delete(idstr);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	// 账户信息
	public String accountTab() {
		if (is_edit) {
			supplierAccountList = supplierAccountManager
					.findAccountBySupplierId(supplier_id);// 账户信息列表
		} else {
			supplierAccountList = null;
		}
		return "accountTab";
	}

	// 查看账户信息
	public String accountDetail() {
		if (StringUtils.isNotEmpty(ref_obj_id)) {
			account_id = ref_obj_id;
		}
		supplierAccount = supplierAccountManager.findAccountById(account_id);

		if (supplierAccount != null) {
			Map companyInfo = null;

			companyInfo = ReflectionUtil.po2Map(supplierAccount);

			account_attachment_thumbnail_images = companInfoManager
					.onFillCompanyInfoEditInput(companyInfo,
							"account_attachment");
			
			if(!ArrayUtils.isEmpty(account_attachment_thumbnail_images)){
				default_account_attachment = account_attachment_thumbnail_images[0];
			}
		}
		return "accountDetail";
	}

	// 修改账户信息
	public String editAccount() {
		try {
			supplier = this.supplierManager.findSupplierById(supplierAccount
					.getSupplier_id());
			supplierAccountManager.edit(supplierAccount, supplier
					.getSupplier_state()); // 修改账户信息
			json = "{'result':0,'message':'修改成功！'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'修改失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 去添加账号页面
	 * 
	 * @return
	 */
	public String showAccountAdd() {

		return "showAccountAdd";
	}

	/**
	 * 添加账户信息
	 * 
	 * @return
	 */
	public String saveAddAccount() {
		if(StringUtils.isEmpty(supplier_id)){
			String currUserId = ManagerUtils.getUserId();
			Supplier supplier = supplierManager
					.findSupplierIdBycurrUserId(currUserId);
			supplier_id = supplier.getSupplier_id();
		}
		
		try {
			supplierAccount.setSupplier_id(supplier_id);
			supplierAccountManager.add(supplierAccount,SupplierStatus.REGISTER_APPLY);
			this.json = "{'result':0,'message':'添加成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'添加失败'}";
		}

		return WWAction.JSON_MESSAGE;

	}

	// 删除账户
	public String deleteAccount() {
		try {
			String a[] = id.split(",");
			String idstr = "";
			for (int i = 0; i < a.length; i++) {
				idstr += a[i].replaceAll(a[i], "" + a[i] + "") + ',';
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			supplierAccountManager.delete(idstr);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	// 代理原厂商信息
	public String agentTab() {
		if (is_edit) {
			supplierAgentList = supplierAgentManager
					.findAgentBySupplierId(supplier_id);// 代理原厂商信息列表
		} else {
			supplierAgentList = null;
		}
		return "agentTab";
	}

	/**
	 * 去添加代理原厂商信息页面
	 * 
	 * @return
	 */
	public String showAgentAdd() {

		return "showAgentAdd";
	}

	/**
	 * 添加代理原产商信息
	 * 
	 * @return
	 */
	public String saveAddAgent() {
		if(StringUtils.isEmpty(supplier_id)){
			String currUserId = ManagerUtils.getUserId();
			Supplier supplier = supplierManager
					.findSupplierIdBycurrUserId(currUserId);
			supplier_id = supplier.getSupplier_id();
		}
		
		try {
			supplierAgent.setSupplier_id(supplier_id);
			supplierAgentManager.add(supplierAgent,SupplierStatus.REGISTER_APPLY);
			this.json = "{'result':0,'message':'添加成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'添加失败'}";
		}

		return WWAction.JSON_MESSAGE;

	}

	// 查看代理原产商信息
	public String agentDetail() {
		if (StringUtils.isNotEmpty(ref_obj_id)) {
			agent_id = ref_obj_id;
		}
		supplierAgent = supplierAgentManager.findAgentById(agent_id);

		if (supplierAgent != null) {
			Map companyInfo = null;

			companyInfo = ReflectionUtil.po2Map(supplierAgent);

			agent_attachment_thumbnail_images = companInfoManager
					.onFillCompanyInfoEditInput(companyInfo, "agent_attachment");// 营业执照附件
			
			if(!ArrayUtils.isEmpty(agent_attachment_thumbnail_images)){
				default_agent_attachment = agent_attachment_thumbnail_images[0];
			}
		}

		return "agentDetail";
	}

	// 修改代理原产商信息
	public String editAgent() {
		try {
			supplier = this.supplierManager.findSupplierById(supplierAgent
					.getSupplier_id());
			supplierAgentManager.edit(supplierAgent, supplier
					.getSupplier_state()); // 修改代理原产商信息
			json = "{'result':0,'message':'修改成功！'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'修改失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	// 删除代理原产商信息
	public String deleteAgent() {
		try {
			String a[] = id.split(",");
			String idstr = "";
			for (int i = 0; i < a.length; i++) {
				idstr += a[i].replaceAll(a[i], "" + a[i] + "") + ',';
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			supplierAgentManager.delete(idstr);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	// 认证证书信息
	public String certificateTab() {
		if (is_edit) {
			supplierCertificateList = supplierCertificateManager
					.findCertificateBySupplierId(supplier_id);// 认证证书信息列表
		} else {
			supplierCertificateList = null;
		}
		return "certificateTab";
	}

	/**
	 * 去添加认证证书信息页面
	 * 
	 * @return
	 */
	public String showCertificateAdd() {

		return "showCertificateAdd";
	}

	/**
	 * 添加认证证书信息
	 * 
	 * @return
	 */
	public String saveAddCertificate() {
		if(StringUtils.isEmpty(supplier_id)){
			String currUserId = ManagerUtils.getUserId();
			Supplier supplier = supplierManager
					.findSupplierIdBycurrUserId(currUserId);
			supplier_id = supplier.getSupplier_id();
		}
		
		try {
			supplierCertificate.setSupplier_id(supplier_id);
			supplierCertificateManager.add(supplierCertificate,SupplierStatus.REGISTER_APPLY);
			this.json = "{'result':0,'message':'添加成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'添加失败'}";
		}

		return WWAction.JSON_MESSAGE;

	}

	// 查看认证证书信息
	public String certificateDetail() {
		if (StringUtils.isNotEmpty(ref_obj_id)) {
			certificate_id = ref_obj_id;
		}
		supplierCertificate = supplierCertificateManager
				.findCertificateById(certificate_id);

		if (supplierCertificate != null) {
			Map companyInfo = null;

			companyInfo = ReflectionUtil.po2Map(supplierCertificate);

			certificate_url_thumbnail_images = companInfoManager
					.onFillCompanyInfoEditInput(companyInfo, "certificate_url");// 营业执照附件
			
			if(!ArrayUtils.isEmpty(certificate_url_thumbnail_images)){
				default_certificate_url = certificate_url_thumbnail_images[0];
			}
		}
		return "certificateDetail";
	}

	// 修改认证证书信息
	public String editCertificat() {
		try {
			supplier = this.supplierManager
					.findSupplierById(supplierCertificate.getSupplier_id());
			supplierCertificateManager.edit(supplierCertificate, supplier
					.getSupplier_state()); // 修改认证证书信息
			json = "{'result':0,'message':'修改成功！'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'修改失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	// 删除认证证书信息
	public String deleteCertificate() {
		try {
			String a[] = id.split(",");
			String idstr = "";
			for (int i = 0; i < a.length; i++) {
				idstr += a[i].replaceAll(a[i], "" + a[i] + "") + ',';
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			supplierCertificateManager.delete(idstr);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	// 人力资源信息
	public String resourcesTab() {
		// String currUserId = ManagerUtils.getUserId();
		if (is_edit) {
			supplierResourcesList = supplierResourcesManager
					.findResourcesBySupplierId(supplier_id);// 人力资源信息列表
		} else {
			supplierResourcesList = null;
		}
		return "resourcesTab";
	}

	/**
	 * 去添加人力资源信息页面
	 * 
	 * @return
	 */
	public String showResourcesAdd() {

		return "showResourcesAdd";
	}

	/**
	 * 添加人力资源信息
	 * 
	 * @return
	 */
	public String saveAddResources() {
		if(StringUtils.isEmpty(supplier_id)){
			String currUserId = ManagerUtils.getUserId();
			Supplier supplier = supplierManager
					.findSupplierIdBycurrUserId(currUserId);
			supplier_id = supplier.getSupplier_id();
		}
		
		try {
			supplierResources.setSupplier_id(supplier_id);
			supplierResourcesManager.add(supplierResources,SupplierStatus.REGISTER_APPLY);
			this.json = "{'result':0,'message':'添加成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'添加失败'}";
		}

		return WWAction.JSON_MESSAGE;

	}

	// 查看人力资源信息
	public String resourcesDetail() {
		if (StringUtils.isNotEmpty(ref_obj_id)) {
			resources_id = ref_obj_id;
		}
		supplierResources = supplierResourcesManager
				.findResourcesById(resources_id);
		return "resourcesDetail";
	}

	// 修改人力资源信息
	public String editResources() {
		try {
			supplier = this.supplierManager.findSupplierById(supplierResources
					.getSupplier_id());
			supplierResourcesManager.edit(supplierResources, supplier
					.getSupplier_state()); // 修改人力资源信息
			json = "{'result':0,'message':'修改成功！'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'修改失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}

	// 删除人力资源信息
	public String deleteResources() {
		try {
			String a[] = id.split(",");
			String idstr = "";
			for (int i = 0; i < a.length; i++) {
				idstr += a[i].replaceAll(a[i], "" + a[i] + "") + ',';
			}
			idstr = idstr.substring(0, idstr.length() - 1);
			supplierResourcesManager.delete(idstr);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'操作失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String uploadFile() {
		try {
			AllAgreement agreement = new AllAgreement();
			
			agreement.setAgt_rel_id(supplier_id);// 供货商ID
			agreement.setAgt_rel_desc("供货商协议");
			agreement.setAgt_rel_table("es_supplier");
			this.supplierAgreementManager.insertAgreement(agreement,file_url);
			this.json = "{result:0,message:'上传廉政协议成功'}";

		} catch (RuntimeException e) {
			this.json = "{result:0,message:\"上传廉政协议失败：" + e.getMessage()
					+ "\"}";

		}

		return WWAction.JSON_MESSAGE;

	}

	public ISupplierManager getSupplierManager() {
		return supplierManager;
	}

	public void setSupplierManager(ISupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}

	public List getAbroadList() {
		return abroadList;
	}

	public void setAbroadList(List abroadList) {
		this.abroadList = abroadList;
	}

	public List getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List currencyList) {
		this.currencyList = currencyList;
	}

	public List getEnterpriseTypeList() {
		return enterpriseTypeList;
	}

	public void setEnterpriseTypeList(List enterpriseTypeList) {
		this.enterpriseTypeList = enterpriseTypeList;
	}

	public List getTicketTypeList() {
		return ticketTypeList;
	}

	public void setTicketTypeList(List ticketTypeList) {
		this.ticketTypeList = ticketTypeList;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public List getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List companyList) {
		this.companyList = companyList;
	}

	public List getShowAgentTypeList() {
		return showAgentTypeList;
	}

	public void setShowAgentTypeList(List showAgentTypeList) {
		this.showAgentTypeList = showAgentTypeList;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOther_phone() {
		return other_phone;
	}

	public void setOther_phone(String other_phone) {
		this.other_phone = other_phone;
	}

	public String getEnglish_name() {
		return english_name;
	}

	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}

	public int getEnterprise_type() {
		return enterprise_type;
	}

	public void setEnterprise_type(int enterprise_type) {
		this.enterprise_type = enterprise_type;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getIs_abroad() {
		return is_abroad;
	}

	public void setIs_abroad(int is_abroad) {
		this.is_abroad = is_abroad;
	}

	public String getRegister_address() {
		return register_address;
	}

	public void setRegister_address(String register_address) {
		this.register_address = register_address;
	}

	public String getRegister_date() {
		return register_date;
	}

	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}

	public String getLegal_name() {
		return legal_name;
	}

	public void setLegal_name(String legal_name) {
		this.legal_name = legal_name;
	}

	public String getLegal_id_card() {
		return legal_id_card;
	}

	public void setLegal_id_card(String legal_id_card) {
		this.legal_id_card = legal_id_card;
	}

	public String getRegister_funds() {
		return register_funds;
	}

	public void setRegister_funds(String register_funds) {
		this.register_funds = register_funds;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public String getPeriod_validity() {
		return period_validity;
	}

	public void setPeriod_validity(String period_validity) {
		this.period_validity = period_validity;
	}

	public String getLicense_number() {
		return license_number;
	}

	public void setLicense_number(String license_number) {
		this.license_number = license_number;
	}

	public String getLicense_url() {
		return license_url;
	}

	public void setLicense_url(String license_url) {
		this.license_url = license_url;
	}

	public String getCountry_registr_number() {
		return country_registr_number;
	}

	public void setCountry_registr_number(String country_registr_number) {
		this.country_registr_number = country_registr_number;
	}

	public String getGeneral_tax_url() {
		return general_tax_url;
	}

	public void setGeneral_tax_url(String general_tax_url) {
		this.general_tax_url = general_tax_url;
	}

	public String getPlace_registr_number() {
		return place_registr_number;
	}

	public void setPlace_registr_number(String place_registr_number) {
		this.place_registr_number = place_registr_number;
	}

	public String getPlace_registr_url() {
		return place_registr_url;
	}

	public void setPlace_registr_url(String place_registr_url) {
		this.place_registr_url = place_registr_url;
	}

	public String getTicket_type() {
		return ticket_type;
	}

	public void setTicket_type(String ticket_type) {
		this.ticket_type = ticket_type;
	}

	public String getOrgan_code() {
		return organ_code;
	}

	public void setOrgan_code(String organ_code) {
		this.organ_code = organ_code;
	}

	public ICompanyInfoManager getCompanInfoManager() {
		return companInfoManager;
	}

	public void setCompanInfoManager(ICompanyInfoManager companInfoManager) {
		this.companInfoManager = companInfoManager;
	}

	public ISupplierAccountManager getSupplierAccountManager() {
		return supplierAccountManager;
	}

	public void setSupplierAccountManager(
			ISupplierAccountManager supplierAccountManager) {
		this.supplierAccountManager = supplierAccountManager;
	}

	public ISupplierAgentManager getSupplierAgentManager() {
		return supplierAgentManager;
	}

	public void setSupplierAgentManager(
			ISupplierAgentManager supplierAgentManager) {
		this.supplierAgentManager = supplierAgentManager;
	}

	public ISupplierCertificateManager getSupplierCertificateManager() {
		return supplierCertificateManager;
	}

	public void setSupplierCertificateManager(
			ISupplierCertificateManager supplierCertificateManager) {
		this.supplierCertificateManager = supplierCertificateManager;
	}

	public ISupplierResourcesManager getSupplierResourcesManager() {
		return supplierResourcesManager;
	}

	public void setSupplierResourcesManager(
			ISupplierResourcesManager supplierResourcesManager) {
		this.supplierResourcesManager = supplierResourcesManager;
	}

	public ISupplierStaffManager getSupplierStaffManager() {
		return supplierStaffManager;
	}

	public void setSupplierStaffManager(
			ISupplierStaffManager supplierStaffManager) {
		this.supplierStaffManager = supplierStaffManager;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getOpen_bank() {
		return open_bank;
	}

	public void setOpen_bank(String open_bank) {
		this.open_bank = open_bank;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAccoun_name() {
		return accoun_name;
	}

	public void setAccoun_name(String accoun_name) {
		this.accoun_name = accoun_name;
	}

	public String getBank_account() {
		return bank_account;
	}

	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}

	public String getAccoun_attachment() {
		return account_attachment;
	}

	public void setAccoun_attachment(String account_attachment) {
		this.account_attachment = account_attachment;
	}

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getAgent_type() {
		return agent_type;
	}

	public void setAgent_type(String agent_type) {
		this.agent_type = agent_type;
	}

	public String getAgent_certificate_number() {
		return agent_certificate_number;
	}

	public void setAgent_certificate_number(String agent_certificate_number) {
		this.agent_certificate_number = agent_certificate_number;
	}

	public String getAgent_attachment() {
		return agent_attachment;
	}

	public void setAgent_attachment(String agent_attachment) {
		this.agent_attachment = agent_attachment;
	}

	public String getCertificate_number() {
		return certificate_number;
	}

	public void setCertificate_number(String certificate_number) {
		this.certificate_number = certificate_number;
	}

	public String getCertificate_name() {
		return certificate_name;
	}

	public void setCertificate_name(String certificate_name) {
		this.certificate_name = certificate_name;
	}

	public String getLicense_issuing_organs() {
		return license_issuing_organs;
	}

	public void setLicense_issuing_organs(String license_issuing_organs) {
		this.license_issuing_organs = license_issuing_organs;
	}

	public String getCertificate_period_validity() {
		return certificate_period_validity;
	}

	public void setCertificate_period_validity(
			String certificate_period_validity) {
		this.certificate_period_validity = certificate_period_validity;
	}

	public String getCertificate_url() {
		return certificate_url;
	}

	public void setCertificate_url(String certificate_url) {
		this.certificate_url = certificate_url;
	}

	public String getResources_id() {
		return resources_id;
	}

	public void setResources_id(String resources_id) {
		this.resources_id = resources_id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEmployees_number() {
		return employees_number;
	}

	public void setEmployees_number(String employees_number) {
		this.employees_number = employees_number;
	}

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public String getAdministration() {
		return administration;
	}

	public void setAdministration(String administration) {
		this.administration = administration;
	}

	public String getResearch() {
		return research;
	}

	public void setResearch(String research) {
		this.research = research;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getMarketing_sales() {
		return marketing_sales;
	}

	public void setMarketing_sales(String marketing_sales) {
		this.marketing_sales = marketing_sales;
	}

	public String getCompany_desc() {
		return company_desc;
	}

	public void setCompany_desc(String company_desc) {
		this.company_desc = company_desc;
	}

	public String getSupplier_state() {
		return supplier_state;
	}

	public void setSupplier_state(String supplier_state) {
		this.supplier_state = supplier_state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}


	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	public Boolean getIs_edit() {
		return is_edit;
	}

	public void setIs_edit(Boolean is_edit) {
		this.is_edit = is_edit;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public CompanyInfo getCompanyInfo1() {
		return companyInfo1;
	}

	public void setCompanyInfo1(CompanyInfo companyInfo1) {
		this.companyInfo1 = companyInfo1;
	}

	public List<SupplierStaff> getSupplierStaffList() {
		return supplierStaffList;
	}

	public void setSupplierStaffList(List<SupplierStaff> supplierStaffList) {
		this.supplierStaffList = supplierStaffList;
	}

	public SupplierStaff getSupplierStaff() {
		return supplierStaff;
	}

	public void setSupplierStaff(SupplierStaff supplierStaff) {
		this.supplierStaff = supplierStaff;
	}

	public SupplierAccount getSupplierAccount() {
		return supplierAccount;
	}

	public void setSupplierAccount(SupplierAccount supplierAccount) {
		this.supplierAccount = supplierAccount;
	}

	public List<SupplierAccount> getSupplierAccountList() {
		return supplierAccountList;
	}

	public void setSupplierAccountList(List<SupplierAccount> supplierAccountList) {
		this.supplierAccountList = supplierAccountList;
	}

	public List<SupplierAgent> getSupplierAgentList() {
		return supplierAgentList;
	}

	public void setSupplierAgentList(List<SupplierAgent> supplierAgentList) {
		this.supplierAgentList = supplierAgentList;
	}

	public List<SupplierResources> getSupplierResourcesList() {
		return supplierResourcesList;
	}

	public void setSupplierResourcesList(
			List<SupplierResources> supplierResourcesList) {
		this.supplierResourcesList = supplierResourcesList;
	}

	public List<SupplierCertificate> getSupplierCertificateList() {
		return supplierCertificateList;
	}

	public void setSupplierCertificateList(
			List<SupplierCertificate> supplierCertificateList) {
		this.supplierCertificateList = supplierCertificateList;
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

	public SupplierAgent getSupplierAgent() {
		return supplierAgent;
	}

	public void setSupplierAgent(SupplierAgent supplierAgent) {
		this.supplierAgent = supplierAgent;
	}

	public SupplierResources getSupplierResources() {
		return supplierResources;
	}

	public void setSupplierResources(SupplierResources supplierResources) {
		this.supplierResources = supplierResources;
	}

	public String getCertificate_id() {
		return certificate_id;
	}

	public void setCertificate_id(String certificate_id) {
		this.certificate_id = certificate_id;
	}

	public SupplierCertificate getSupplierCertificate() {
		return supplierCertificate;
	}

	public void setSupplierCertificate(SupplierCertificate supplierCertificate) {
		this.supplierCertificate = supplierCertificate;
	}

	public String getIs_administrator() {
		return is_administrator;
	}

	public void setIs_administrator(String is_administrator) {
		this.is_administrator = is_administrator;
	}

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getSupplier_type() {
		return supplier_type;
	}

	public void setSupplier_type(String supplier_type) {
		this.supplier_type = supplier_type;
	}

	public String[] getLicense_url_thumbnail_images() {
		return license_url_thumbnail_images;
	}

	public void setLicense_url_thumbnail_images(
			String[] license_url_thumbnail_images) {
		this.license_url_thumbnail_images = license_url_thumbnail_images;
	}

	public String[] getGeneral_tax_url_thumbnail_images() {
		return general_tax_url_thumbnail_images;
	}

	public void setGeneral_tax_url_thumbnail_images(
			String[] general_tax_url_thumbnail_images) {
		this.general_tax_url_thumbnail_images = general_tax_url_thumbnail_images;
	}

	public String[] getPlace_registr_url_thumbnail_images() {
		return place_registr_url_thumbnail_images;
	}

	public void setPlace_registr_url_thumbnail_images(
			String[] place_registr_url_thumbnail_images) {
		this.place_registr_url_thumbnail_images = place_registr_url_thumbnail_images;
	}

	public String getDefault_license_url() {
		return default_license_url;
	}

	public void setDefault_license_url(String default_license_url) {
		this.default_license_url = default_license_url;
	}

	public String getDefault_general_tax_url() {
		return default_general_tax_url;
	}

	public void setDefault_general_tax_url(String default_general_tax_url) {
		this.default_general_tax_url = default_general_tax_url;
	}

	public String getDefault_place_registr_url() {
		return default_place_registr_url;
	}

	public void setDefault_place_registr_url(String default_place_registr_url) {
		this.default_place_registr_url = default_place_registr_url;
	}

	public String[] getAgent_attachment_thumbnail_images() {
		return agent_attachment_thumbnail_images;
	}

	public void setAgent_attachment_thumbnail_images(
			String[] agent_attachment_thumbnail_images) {
		this.agent_attachment_thumbnail_images = agent_attachment_thumbnail_images;
	}

	public String getDefault_agent_attachment() {
		return default_agent_attachment;
	}

	public void setDefault_agent_attachment(String default_agent_attachment) {
		this.default_agent_attachment = default_agent_attachment;
	}

	public String[] getCertificate_url_thumbnail_images() {
		return certificate_url_thumbnail_images;
	}

	public void setCertificate_url_thumbnail_images(
			String[] certificate_url_thumbnail_images) {
		this.certificate_url_thumbnail_images = certificate_url_thumbnail_images;
	}

	public String getDefault_certificate_url() {
		return default_certificate_url;
	}

	public void setDefault_certificate_url(String default_certificate_url) {
		this.default_certificate_url = default_certificate_url;
	}

	public String[] getAccoun_attachment_thumbnail_images() {
		return account_attachment_thumbnail_images;
	}

	public void setAccoun_attachment_thumbnail_images(
			String[] account_attachment_thumbnail_images) {
		this.account_attachment_thumbnail_images = account_attachment_thumbnail_images;
	}

	public String getDefault_account_attachment() {
		return default_account_attachment;
	}

	public void setDefault_account_attachment(String default_account_attachment) {
		this.default_account_attachment = default_account_attachment;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<Supplier> getSuppliersList() {
		return suppliersList;
	}

	public void setSuppliersList(List<Supplier> suppliersList) {
		this.suppliersList = suppliersList;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getSupplier_id_edit() {
		return supplier_id_edit;
	}

	public void setSupplier_id_edit(String supplier_id_edit) {
		this.supplier_id_edit = supplier_id_edit;
	}

	public AgreementManager getSupplierAgreementManager() {
		return supplierAgreementManager;
	}

	public void setSupplierAgreementManager(
			AgreementManager supplierAgreementManager) {
		this.supplierAgreementManager = supplierAgreementManager;
	}

	public AllAgreement getSupplierAgreement() {
		return supplierAgreement;
	}

	public void setSupplierAgreement(AllAgreement supplierAgreement) {
		this.supplierAgreement = supplierAgreement;
	}



	public File getFile_url() {
		return file_url;
	}

	public void setFile_url(File file_url) {
		this.file_url = file_url;
	}

	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getRef_obj_id() {
		return ref_obj_id;
	}

	public void setRef_obj_id(String ref_obj_id) {
		this.ref_obj_id = ref_obj_id;
	}

	public ModHandlerInf getModInfoHandler() {
		return modInfoHandler;
	}

	public void setModInfoHandler(ModHandlerInf modInfoHandler) {
		this.modInfoHandler = modInfoHandler;
	}

	public String getNew_change() {
		return new_change;
	}

	public void setNew_change(String new_change) {
		this.new_change = new_change;
	}

	public String getOld_change() {
		return old_change;
	}

	public void setOld_change(String old_change) {
		this.old_change = old_change;
	}

	public Integer getFlow_inst_id() {
		return flow_inst_id;
	}

	public void setFlow_inst_id(Integer flow_inst_id) {
		this.flow_inst_id = flow_inst_id;
	}

	public List getModAudit() {
		return modAudit;
	}

	public void setModAudit(List modAudit) {
		this.modAudit = modAudit;
	}

	public String getAccount_attachment() {
		return account_attachment;
	}

	public void setAccount_attachment(String account_attachment) {
		this.account_attachment = account_attachment;
	}

	public String[] getAccount_attachment_thumbnail_images() {
		return account_attachment_thumbnail_images;
	}

	public void setAccount_attachment_thumbnail_images(
			String[] account_attachment_thumbnail_images) {
		this.account_attachment_thumbnail_images = account_attachment_thumbnail_images;
	}

	public String getDate_type() {
		return date_type;
	}

	public void setDate_type(String date_type) {
		this.date_type = date_type;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
