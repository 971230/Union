package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.AgreementControl;
import com.ztesoft.net.mall.core.model.GoodsAgreement;
import com.ztesoft.net.mall.core.service.IGoodsAgreementManager;
import com.ztesoft.net.sqls.SF;

import java.util.List;

public class GoodsAgreementManager extends BaseSupport implements IGoodsAgreementManager {

	@Override
	public void addAgreement(GoodsAgreement agreement) {
		// TODO Auto-generated method stub
		String agreement_id = this.baseDaoSupport.getSequences("SEQ_ES_GOODS_AGREEMENT");
	    agreement.setAgreement_id(agreement_id);
	    
	    agreement.setStatus("OOA");
	    agreement.setCreate_time(DBTUtil.current());
	    this.baseDaoSupport.insert("es_goods_agreement",agreement);
	}

	@Override
	public void insertAgreementControl(AgreementControl agreementControl) {
		agreementControl.setCreate_date(DBTUtil.current());
		this.baseDaoSupport.insert("es_agreement_controls", agreementControl);
	}

	@Override
	public Page listAgreement(String agreement_name, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		
		String sql = SF.goodsSql("LIST_AGREEMENT");
		if(agreement_name!=null&&!"".equals(agreement_name)){
			sql += " and agreement_name like '%"+agreement_name+"%'";
		}
	     sql += " order by create_time desc";
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
		  return page;
	}

	@Override
	public void updateAgreement(GoodsAgreement agreement) {
		String whereSql = "agreement_id="+agreement.getAgreement_id();
		this.baseDaoSupport.update("goods_agreement", agreement, whereSql);
	}

	@Override
	public void deleteAgreement(String agreement_id) {
		String sql = SF.goodsSql("DELETE_AGREEMENT");
	 this.baseDaoSupport.execute(sql, agreement_id);
	}

	@Override
	public Page ListValidateSuppliers() {
		Page page = null;
		return page;
	}
	@Override
	public GoodsAgreement editAgreement(String agreement_id){
		String sql = SF.goodsSql("GOODS_AGREEMENT");
		GoodsAgreement goodsAgreement = (GoodsAgreement) this.baseDaoSupport.queryForObject(sql, GoodsAgreement.class, agreement_id);
		
		return  goodsAgreement ;
	}

    @Override
	public List getControlById(String agreementId){
    	String sql = SF.goodsSql("GET_CONTROL_BY_ID");
    	List<AgreementControl> agreementControlList = this.baseDaoSupport.queryForList(sql, agreementId);
    	return agreementControlList;
    }

	@Override
	public void delAllControlByAgtId(String agreementId){
		String sql = SF.goodsSql("AGREEMENT_CONTROLS_DEL");
		this.baseDaoSupport.execute(sql, agreementId);
	}
    @Override
	public String getNameById(String agreement_Id){
    	String sql = SF.goodsSql("GET_NAME_BY_ID");
    	String name = this.baseDaoSupport.queryForString(sql, agreement_Id);
    	return name;
    }
}
