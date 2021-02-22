package com.ztesoft.net.app.base.core.model;

/**
 * 供货商证书
 * 
 * @author chenlijun
 * 
 */
public class SupplierCertificate {

	private String certificate_id;
	private String certificate_number;
	private String certificate_name;
	private String license_issuing_organs;
	private String certificate_period_validity;
	private String certificate_url;
	private String record_state;

	private String supplier_id;

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

	public String getRecord_state() {
		return record_state;
	}

	public void setRecord_state(String record_state) {
		this.record_state = record_state;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getCertificate_id() {
		return certificate_id;
	}

	public void setCertificate_id(String certificate_id) {
		this.certificate_id = certificate_id;
	}

	
}
