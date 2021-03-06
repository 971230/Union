package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import services.FlowInf;
import services.ModHandlerInf;

import com.ztesoft.net.app.base.core.model.SupplierCertificate;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.mall.core.service.ISupplierCertificateManager;
import com.ztesoft.net.mall.core.service.SupplierStatus;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.workflow.core.FlowType;
import com.ztesoft.net.mall.core.workflow.core.InitFlowParam;
import com.ztesoft.net.mall.core.workflow.util.ModParams;
import com.ztesoft.net.mall.core.workflow.util.ModVO;

public class SupplierCertificateManager extends
		BaseSupport<SupplierCertificate> implements ISupplierCertificateManager {

	private ModHandlerInf modInfoHandler ;
	private FlowInf flowServ;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(SupplierCertificate supplierCertificate,String state) {
		this.daoSupport.insert("es_supplier_certificate", supplierCertificate);
		
		
//		// 起流程
		if(StringUtils.isNotEmpty(state)){
			String currUserId = ManagerUtils.getUserId();
			flowServ.startFlow(new InitFlowParam(FlowType.CERTIFICATE_APPLY,
					currUserId, supplierCertificate.getCertificate_id()));
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void dataDeal(String certificate_number, String certificate_name,
			String certificate_period_validity, String certificate_url,
			String license_issuing_organs, String supplier_id) {
		this.baseDaoSupport
				.execute("delete from es_supplier_certificate where supplier_id="
						+ supplier_id);
		String[] sizes = certificate_name.split(",");
		for (int i = 0; i < sizes.length; i++) {
			SupplierCertificate supplierCertificate = new SupplierCertificate();
			supplierCertificate.setCertificate_number(certificate_number
					.split(",")[i]);
			supplierCertificate.setCertificate_name(sizes[i]);
			supplierCertificate
					.setCertificate_period_validity(certificate_period_validity
							.split(",")[i]);
			supplierCertificate.setCertificate_url(certificate_url);
			supplierCertificate
					.setLicense_issuing_organs(license_issuing_organs
							.split(",")[i]);
			supplierCertificate
					.setRecord_state(SupplierStatus.SUPPLIER_STATE_2);
			supplierCertificate.setSupplier_id(supplier_id);
			add(supplierCertificate,null);
		}
	}

	@Override
	public List list(String supplier_id) {
		String sql = "select * from es_supplier_certificate where supplier_id=? order by register_time asc ";
		return this.baseDaoSupport.queryForList(sql, SupplierCertificate.class,
				supplier_id);
	}

	@Override
	public List findCertificateByUserId(String currUserId) {
		String sql = "SELECT C.* FROM ES_SUPPLIER A, ES_ADMINUSER B, es_supplier_certificate C WHERE A.USERID = B.USERID"
				+ " AND C.SUPPLIER_ID = A.SUPPLIER_ID AND a.userid=? order by a.register_time asc";
		return this.baseDaoSupport.queryForList(sql, SupplierCertificate.class,
				currUserId);
	}

	@Override
	public SupplierCertificate findCertificateById(String certificate_id) {
		String sql = "SELECT *FROM es_supplier_certificate WHERE certificate_id =? order by CERTIFICATE_PERIOD_VALIDITY asc";
		return this.baseDaoSupport.queryForObject(sql,
				SupplierCertificate.class, certificate_id);
	}

	// 删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int delete(String certificate_id) {
		if (certificate_id == null || certificate_id.equals("")) {
			return 0;
		} else {
			String sql = "delete from es_supplier_certificate where certificate_id in ("
					+ certificate_id + ") ";
			this.baseDaoSupport.execute(sql);
			return 1;
		}
	}

	@Override
	public List<SupplierCertificate> findCertificateBySupplierId(
			String suplierid) {
		String sql = "SELECT * FROM es_supplier_certificate WHERE supplier_id =? ";
		return this.baseDaoSupport.queryForList(sql, SupplierCertificate.class,
				suplierid);
	}

	/*
	 * 修改信息
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void edit(SupplierCertificate supplierCertificate,
			String supplier_state) {
		// if (SupplierStatus.SUPPLIER_STATE_2
		// .equals(supplier_state)) {// 如果是审核后修改
		//
		// } else {
		// //supplier.setSupplier_state(SupplierStatus.SUPPLIER_STATE_1);
		//			
		// HashMap fields=new HashMap();
		// fields.put("supplier_state", SupplierStatus.SUPPLIER_STATE_1);
		// this.baseDaoSupport.update("es_supplier", fields, " supplier_id='" +
		// supplierCertificate.getSupplier_id()+"'");
		//			
		// this.baseDaoSupport.update("es_supplier_certificate",
		// supplierCertificate,
		// " certificate_id=" + supplierCertificate.getCertificate_id());
		// }

		Map supplierCertificateMap = null;

		supplierCertificateMap = ReflectionUtil.po2Map(supplierCertificate);

		List<ModVO> modInfos = new ArrayList<ModVO>();
		ModVO modVO = new ModVO();

		modVO.setTableName("ES_SUPPLIER_CERTIFICATE");
		modVO.setObj(supplierCertificate);

		modInfos.add(modVO);

		ModParams param = new ModParams(FlowType.SUPP_MOD_CERTIFICATE, ManagerUtils
				.getAdminUser().getUserid(), supplierCertificate
				.getCertificate_id(), modInfos);

		this.modInfoHandler.checkAndStartFlow(param);

		if (modVO.getChangedFields() != null) {
			for (String afield_name : modVO.getChangedFields()) {
				if (StringUtils.isNotEmpty((String) supplierCertificateMap
						.get(afield_name))) {
					supplierCertificateMap.remove(afield_name);
				}
			}

			supplierCertificateMap.put("record_state", 0);
		}

		this.baseDaoSupport.update("es_supplier_certificate",
				supplierCertificateMap, "certificate_id='"
						+ supplierCertificateMap.get("certificate_id") + "'");
	}

	public ModHandlerInf getModInfoHandler() {
		return modInfoHandler;
	}

	public void setModInfoHandler(ModHandlerInf modInfoHandler) {
		this.modInfoHandler = modInfoHandler;
	}

	public FlowInf getFlowServ() {
		return flowServ;
	}

	public void setFlowServ(FlowInf flowServ) {
		this.flowServ = flowServ;
	}

	
	
}
