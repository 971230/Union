package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.InvoiceApply;
import com.ztesoft.net.mall.core.service.IInvoiceApplyManager;
import com.ztesoft.net.sqls.SF;

import java.util.List;

public class InvoiceApplyManager extends BaseSupport<InvoiceApply> implements IInvoiceApplyManager {


	@Override
	public void add(InvoiceApply invoiceApply) {
		invoiceApply.setAdd_time(DBTUtil.current());
		invoiceApply.setState(0);
		this.baseDaoSupport.insert("invoice_apply", invoiceApply);
	}


	@Override
	public void delete(Integer id) {
		this.baseDaoSupport.execute(SF.goodsSql("INVOICE_APPLY_DEL"), id);
	}


	@Override
	public void edit(InvoiceApply invoiceApply) {
		this.baseDaoSupport.update("invoice_apply", invoiceApply, "id="+invoiceApply.getId());
	}


	@Override
	public InvoiceApply get(Integer id) {
		
		return this.daoSupport.queryForObject(SF.goodsSql("INVOICE_APPLY_SELECT"), InvoiceApply.class, id);
	}


	@Override
	public Page list(int pageNo, int pageSize) {
		
		return this.daoSupport.queryForPage(SF.goodsSql("INVOICE_APPLY_SELECT_0"), pageNo, pageSize,InvoiceApply.class);
	}


	@Override
	public List listMember() {
		Member member = UserServiceFactory.getUserService().getCurrentMember();
		
		return this.daoSupport.queryForList(SF.goodsSql("LIST_MEMBER"),  InvoiceApply.class,member.getMember_id());
	}


	@Override
	public void updateState(Integer id, int state) {
		this.baseDaoSupport.execute(SF.goodsSql("UPDATE_INVOICE_APPLY_STATE"), state,id);
	}


	@Override
	public void refuse(Integer id, String reson) {
		this.baseDaoSupport.execute(SF.goodsSql("REFUSE_INVOICE_APPLY"), 2,reson,id);
	}

}
