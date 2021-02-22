package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.powerise.ibss.util.DBTUtil;

import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.PromotionActivity;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.FieldVO;
import com.ztesoft.net.model.InvoiceFieldVO;
import com.ztesoft.net.model.InvoiceModeFieldParams;
import com.ztesoft.net.model.InvoiceModeFieldVO;
import com.ztesoft.net.model.InvoiceModeVO;
import com.ztesoft.net.model.ModeSelectFieldVO;
import com.ztesoft.net.service.IInvoicePrintManager;
import com.ztesoft.net.sqls.SF;

public class InvoicePrintManager  extends BaseSupport implements IInvoicePrintManager {
	
	public Page search(InvoiceModeVO invoice,int pageNo, int pageSize) {
	
		String sql = SF.printSql("TEMPLET_NAME_INFORAMTION");
		if(!StringUtil.isEmpty(invoice.getModel_nam())){
		   sql+=" and a.model_nam like '%"+invoice.getModel_nam()+"%'";
		}
		Page pageInformation=this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
		return pageInformation;
	}

	@Override
	public List templateInvoiceFieldInformation() {
			
		String sql  = SF.printSql("INVOICE_PRINT_FILED");
				
		return this.baseDaoSupport.queryForList(sql);
	}
	
	public InvoiceModeFieldVO templateInvoiceModelFieldInformation(String model_cd,String field_cd) {
		
		InvoiceModeFieldVO invoiceModeFieldVO=new InvoiceModeFieldVO();
		String sql  = SF.printSql("INVOICEMODEFIELD");
		 
		invoiceModeFieldVO=(InvoiceModeFieldVO) this.daoSupport.queryForObject(sql, InvoiceModeFieldVO.class, model_cd,field_cd);
			
		return invoiceModeFieldVO;
		
	}
	
	public List<FieldVO> templateFieldInformation(InvoiceModeVO invoiceModeVO){
		String sql  = SF.printSql("Had_PRINT_FIELD");
		return this.baseDaoSupport.queryForList(sql,FieldVO.class,invoiceModeVO.getModel_cd());
	}
	
				
   	@Override
	public InvoiceModeVO templateInvoiceModeInformation(
			InvoiceModeVO invoiceModeVO) {
		String sql  = SF.printSql("INVOICEMODE");
		return (InvoiceModeVO) this.baseDaoSupport.queryForObject(sql, InvoiceModeVO.class, invoiceModeVO.getModel_cd());
		
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void editTemplateInformation(List<InvoiceModeFieldVO> invoiceModeFieldVOList) {
		String sql  = SF.printSql("DELETE_TEMPLATE");
		String sql_update  = SF.printSql("INSERT_TEMPLATE_INFORMATION");
	
		for(InvoiceModeFieldVO invoiceModeFieldVO:invoiceModeFieldVOList)
		{
			this.baseDaoSupport.execute(sql, invoiceModeFieldVO.getModel_cd());
		}
		
		for(InvoiceModeFieldVO invoiceModeFieldVO:invoiceModeFieldVOList)
		{
			this.daoSupport.execute(sql_update,invoiceModeFieldVO.getModel_cd(),invoiceModeFieldVO.getField_cd(),invoiceModeFieldVO.getPos_x(),invoiceModeFieldVO.getPos_y(),ManagerUtils.getSourceFrom());
		}
		
		
	}
	
	public ModeSelectFieldVO selectOne(InvoiceModeFieldParams invoiceModeFieldParams) 
	{
		ModeSelectFieldVO modeSelectFieldVO=new ModeSelectFieldVO();
		String sql  = SF.printSql("SELECT_PRINT_FILED");
		String sql_backGround  = SF.printSql("PRINT_MODE_BACKGROUND");
	    Map info=this.baseDaoSupport.queryForMap(sql_backGround, invoiceModeFieldParams.getModel_cd());
	   	List<FieldVO> fieldVo=this.daoSupport.queryForList(sql, FieldVO.class, invoiceModeFieldParams.getModel_cd());
	   	for(FieldVO fieldVO:fieldVo)
	   	{
	   		logger.info(fieldVO.getPos_x()+" "+fieldVO.getPos_y());
	   	}
	  	modeSelectFieldVO.setModel_cd(info.get("model_cd").toString());
	    modeSelectFieldVO.setImg_url(info.get("img_url").toString());
	    modeSelectFieldVO.setPaper_height(info.get("paper_height").toString());
	    modeSelectFieldVO.setPaper_width(info.get("paper_width").toString());
	    if(fieldVo.size()>0)
	    {
	    	modeSelectFieldVO.setFieldVOList(fieldVo);
	    }
	    else
	    {
	    	modeSelectFieldVO.setFieldVOList(new ArrayList<FieldVO>());
	    }
	    
	    return modeSelectFieldVO;
	    
		  
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void editFieldPosition(InvoiceModeFieldVO invoiceModeFieldVO) {
		String sql  = SF.printSql("UPDATE_FIELD_POSITION");
		this.daoSupport.execute(sql,invoiceModeFieldVO.getPos_x(),invoiceModeFieldVO.getPos_y(),invoiceModeFieldVO.getModel_cd(),invoiceModeFieldVO.getField_cd());
				
			}
		
	
	
	
	
	
}
