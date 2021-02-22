package com.ztesoft.component.common.staticdata.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class BillingCycleTypeVO extends ValueObject implements VO {

	private String billingCycleTypeId = "";
	private String regionId = "";
	private String billingCycleTypeName = "";
	private String cycleUnit = "";
	private String unitCount = "";
	private String cycleDuration = "";
	private String cycleDurationDays = "";

	public BillingCycleTypeVO() {}

	public BillingCycleTypeVO( String pbillingCycleTypeId, String pregionId, String pbillingCycleTypeName, String pcycleUnit, String punitCount, String pcycleDuration, String pcycleDurationDays ) {
		billingCycleTypeId = pbillingCycleTypeId;
		regionId = pregionId;
		billingCycleTypeName = pbillingCycleTypeName;
		cycleUnit = pcycleUnit;
		unitCount = punitCount;
		cycleDuration = pcycleDuration;
		cycleDurationDays = pcycleDurationDays;
	}

	public String getBillingCycleTypeId() {
		return billingCycleTypeId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getBillingCycleTypeName() {
		return billingCycleTypeName;
	}

	public String getCycleUnit() {
		return cycleUnit;
	}

	public String getUnitCount() {
		return unitCount;
	}

	public String getCycleDuration() {
		return cycleDuration;
	}

	public String getCycleDurationDays() {
		return cycleDurationDays;
	}

	public void setBillingCycleTypeId(String pBillingCycleTypeId) {
		billingCycleTypeId = pBillingCycleTypeId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setBillingCycleTypeName(String pBillingCycleTypeName) {
		billingCycleTypeName = pBillingCycleTypeName;
	}

	public void setCycleUnit(String pCycleUnit) {
		cycleUnit = pCycleUnit;
	}

	public void setUnitCount(String pUnitCount) {
		unitCount = pUnitCount;
	}

	public void setCycleDuration(String pCycleDuration) {
		cycleDuration = pCycleDuration;
	}

	public void setCycleDurationDays(String pCycleDurationDays) {
		cycleDurationDays = pCycleDurationDays;
	}

	@Override
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("BILLING_CYCLE_TYPE_ID",this.billingCycleTypeId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("BILLING_CYCLE_TYPE_NAME",this.billingCycleTypeName);
		hashMap.put("CYCLE_UNIT",this.cycleUnit);
		hashMap.put("UNIT_COUNT",this.unitCount);
		hashMap.put("CYCLE_DURATION",this.cycleDuration);
		hashMap.put("CYCLE_DURATION_DAYS",this.cycleDurationDays);
		return hashMap;
	}

	@Override
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.billingCycleTypeId = (String) hashMap.get("BILLING_CYCLE_TYPE_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.billingCycleTypeName = (String) hashMap.get("BILLING_CYCLE_TYPE_NAME");
			this.cycleUnit = (String) hashMap.get("CYCLE_UNIT");
			this.unitCount = (String) hashMap.get("UNIT_COUNT");
			this.cycleDuration = (String) hashMap.get("CYCLE_DURATION");
			this.cycleDurationDays = (String) hashMap.get("CYCLE_DURATION_DAYS");
		}
	}

	@Override
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("BILLING_CYCLE_TYPE_ID");
		return arrayList;
	}

	@Override
	public String getTableName() {
		return "BILLING_CYCLE_TYPE";
	}

}
