package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import services.ModHandlerInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.mall.core.model.CompanyInfo;
import com.ztesoft.net.mall.core.service.ICompanyInfoManager;
import com.ztesoft.net.mall.core.service.SupplierStatus;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.workflow.core.FlowType;
import com.ztesoft.net.mall.core.workflow.util.ModParams;
import com.ztesoft.net.mall.core.workflow.util.ModVO;

public class CompanInfoManager extends BaseSupport<CompanyInfo> implements ICompanyInfoManager {

	private ModHandlerInf modInfoHandler ;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(CompanyInfo companyInfo) {
		this.daoSupport.insert("es_company_info", companyInfo);
	}

	@Override
	public CompanyInfo findCompanyInfoBySupplierId(String supplierId) {
		String sql="select * from es_company_info where supplier_id=? ";
        CompanyInfo info=null;
        try{
           info=this.baseDaoSupport.queryForObject(sql, CompanyInfo.class, supplierId);
        }catch (Exception e){
            e.printStackTrace();
        }
		return  info;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void dataDeal(String company_name, String english_name,
			String ticket_type, int enterprise_type, String abbreviation,
			String website, int is_abroad, String register_address,
			String register_date, String legal_name, String legal_id_card,
			String register_funds, int currency, String period_validity,
			String license_number, String license_url,
			String country_registr_number, String general_tax_url,
			String place_registr_number, String place_registr_url,
			String organ_code, String supplier_id) {

		CompanyInfo companyInfo = new CompanyInfo();
		companyInfo.setCompany_name(company_name);
		companyInfo.setEnglish_name(english_name);
		companyInfo.setTicket_type(ticket_type);
		companyInfo.setEnterprise_type(enterprise_type);
		companyInfo.setAbbreviation(abbreviation);
		companyInfo.setWebsite(website);
		companyInfo.setIs_abroad(is_abroad);
		companyInfo.setRegister_address(register_address);
		companyInfo.setRegister_date(register_date);
		companyInfo.setLegal_name(legal_name);
		companyInfo.setLegal_id_card(legal_id_card);
		companyInfo.setRegister_funds(register_funds);
		companyInfo.setCurrency(currency);
		companyInfo.setPeriod_validity(period_validity);
		companyInfo.setLicense_number(license_number);
		companyInfo.setLicense_url(license_url);//license_url
		companyInfo.setCountry_registr_number(country_registr_number);
		companyInfo.setGeneral_tax_url(general_tax_url);//general_tax_url
		companyInfo.setPlace_registr_number(place_registr_number);
		companyInfo.setPlace_registr_url(place_registr_url);//place_registr_url
		companyInfo.setOrgan_code(organ_code);
		companyInfo.setState(SupplierStatus.SUPPLIER_STATE_2);
		companyInfo.setRegister_time(DBTUtil.current());
		companyInfo.setSupplier_id(supplier_id);
		
		add(companyInfo);
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void modifyCompany(String company_id, String company_name,
			String english_name, String ticket_type, int enterprise_type,
			String abbreviation, String website, int is_abroad,
			String register_address, String register_date, String legal_name,
			String legal_id_card, String register_funds, int currency,
			String period_validity, String license_number, String license_url,
			String country_registr_number, String general_tax_url,
			String place_registr_number, String place_registr_url,
			String organ_code) {

		CompanyInfo companyInfo = new CompanyInfo();
		companyInfo.setCompany_id(company_id);
		companyInfo.setCompany_name(company_name);
		companyInfo.setEnglish_name(english_name);
		companyInfo.setTicket_type(ticket_type);
		companyInfo.setEnterprise_type(enterprise_type);
		companyInfo.setAbbreviation(abbreviation);
		companyInfo.setWebsite(website);
		companyInfo.setIs_abroad(is_abroad);
		companyInfo.setRegister_address(register_address);
		companyInfo.setRegister_date(register_date);
		companyInfo.setLegal_name(legal_name);
		companyInfo.setLegal_id_card(legal_id_card);
		companyInfo.setRegister_funds(register_funds);
		companyInfo.setCurrency(currency);
		companyInfo.setPeriod_validity(period_validity);
		companyInfo.setLicense_number(license_number);
		companyInfo.setLicense_url(license_url);
		companyInfo.setCountry_registr_number(country_registr_number);
		companyInfo.setGeneral_tax_url(general_tax_url);
		companyInfo.setPlace_registr_number(place_registr_number);
		companyInfo.setPlace_registr_url(place_registr_url);
		companyInfo.setOrgan_code(organ_code);
		this.baseDaoSupport.update("es_company_info", companyInfo, "company_id='"+company_id+"'");
	}
	
	@Override
	public Page fineCompanyByName(String companyName, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer("select p.company_id company_id,p.company_name company_name,p.register_address register_address, p.register_time register_time from es_company_info p where 1=1");

		ArrayList partm = new ArrayList();
		
		StringBuffer sqlAccount = new StringBuffer(" ");
		if(StringUtils.isNotEmpty(companyName)){
			sqlAccount.append(" and p.company_name like ?");
			partm.add("%"+companyName+"%");
		}
		
		sql.append(sqlAccount);
		
		String countSql = "select count(*) from es_company_info p where 1=1 "
			+ sqlAccount.toString();
		return this.daoSupport.queryForCPage(sql.toString(), pageNo,
				pageSize, CompanyInfo.class, countSql, partm.toArray());
	}

	
	/**
	 * 在修改时处理图片的信息
	 */
	@Override
	public String[] onFillCompanyInfoEditInput(Map companyInfo,String url) {
		
		// 关于图片的处理
 		String[] thumbnail_images=null;
		if (companyInfo.get(url) != null && !"".equals(companyInfo.get(url))) {
			String image_file = companyInfo.get(url).toString();
			thumbnail_images = image_file.split(",");

			for(int i=0;i<thumbnail_images.length;i++){
				thumbnail_images[i]=UploadUtil.replacePath(thumbnail_images[i]);
			}
		}
		
		
		return thumbnail_images;
	}

	/*
	 * 修改信息
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void edit(CompanyInfo companyInfo,String supplier_state) {
//		if (SupplierStatus.SUPPLIER_STATE_2
//				.equals(supplier_state)) {// 如果是审核后修改
//
//		} else {
//			//supplier.setSupplier_state(SupplierStatus.SUPPLIER_STATE_1);
//			
//			HashMap fields=new HashMap();
//			fields.put("supplier_state", SupplierStatus.SUPPLIER_STATE_1);
//			this.baseDaoSupport.update("es_supplier", fields, " supplier_id=" + companyInfo.getSupplier_id());
//			
//			this.baseDaoSupport.update("es_company_info", companyInfo,
//					" company_id=" + companyInfo.getCompany_id());
//		}
		
		//查找审核字段
//		String field_name ="";
//		List columnAudit=this.columnAuditList();
//		boolean flag=false;

		Map companyInfoMap = null;
		
		companyInfoMap = ReflectionUtil.po2Map(companyInfo);
		
		List<ModVO> modInfos = new ArrayList<ModVO>() ;
		ModVO modVO=new ModVO();
		
		modVO.setTableName("ES_COMPANY_INFO");
		modVO.setObj(companyInfo);
		
		modInfos.add(modVO);
		
		ModParams param = new ModParams(FlowType.SUPP_MOD_COMPANY  ,
				ManagerUtils.getAdminUser().getUserid(),companyInfo.getCompany_id() ,modInfos ) ;
		
		this.modInfoHandler.checkAndStartFlow(param) ;
		
		if(modVO.getChangedFields()!=null){
			for(String afield_name :modVO.getChangedFields()){
				if(StringUtils.isNotEmpty((String)companyInfoMap.get(afield_name))){
					companyInfoMap.remove(afield_name);
				}
			 }
			
			companyInfoMap.put("STATE", 0);
		}
		
		 
		this.baseDaoSupport.update("ES_COMPANY_INFO", companyInfoMap, "company_id='"+ companyInfoMap.get("company_id")+"'");
		
	}
	
	/**
	 * 查找审核字段
	 * @param partid
	 * @param sequR
	 * @return
	 */
	public List columnAuditList(){
		
		 List list=this.baseDaoSupport.queryForList("select p.pkey,p.stype,p.pname  from es_dc_public p where p.stype=8819 order by p.codea");
		
		 return list;
	}
	
	@Override
	public String getCompanyInfoSequence(){
		return this.baseDaoSupport.getSequences("S_ES_COMPANY_INFO");
	}

	public ModHandlerInf getModInfoHandler() {
		return modInfoHandler;
	}

	public void setModInfoHandler(ModHandlerInf modInfoHandler) {
		this.modInfoHandler = modInfoHandler;
	}
}
