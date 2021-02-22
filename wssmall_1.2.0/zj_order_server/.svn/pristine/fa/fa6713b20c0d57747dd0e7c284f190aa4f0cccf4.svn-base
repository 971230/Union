package com.ztesoft.net.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.model.FieldVO;
import com.ztesoft.net.model.InvoiceFieldVO;
import com.ztesoft.net.model.InvoiceModeFieldParams;
import com.ztesoft.net.model.InvoiceModeFieldVO;
import com.ztesoft.net.model.InvoiceModeVO;
import com.ztesoft.net.model.ModeSelectFieldVO;
import com.ztesoft.net.service.IInvoicePrintManager;

public class InvoicePrintAction extends WWAction {
	private InvoiceModeVO invoice;
	private IInvoicePrintManager  invoicePrintManager;
	private List<InvoiceFieldVO> invoiceFieldList;
	private InvoiceModeVO invoiceModeVO;
	private InvoiceModeFieldParams invoiceModeFieldParams;
	private ModeSelectFieldVO modeSelectFieldVO;
	private List<FieldVO> templateFieldInformation;
	private List<InvoiceModeFieldVO> invoiceModeFieldVOList;
    
	

	/**
	 * 查询模板
	 * @return
	 */
	public String search() {
		
		if(invoice== null)
		{
			invoice = new InvoiceModeVO();
		}
		this.webpage = invoicePrintManager.search(invoice,this.getPage(), this.getPageSize());
		return "list";
	}
	
	public String templateInformation()
	{
		if(invoice== null)
		{
			invoice = new InvoiceModeVO();
		}
		invoiceFieldList=invoicePrintManager.templateInvoiceFieldInformation();
		templateFieldInformation=invoicePrintManager.templateFieldInformation(invoice);
		for(FieldVO fieldVO:templateFieldInformation)
		{
			logger.info(fieldVO.getField_cd()+" "+fieldVO.getField_nam()+" "+fieldVO.getPos_x()+" "+fieldVO.getPos_y());
		}
		invoiceModeVO=invoicePrintManager.templateInvoiceModeInformation(invoice);
		logger.info(invoiceModeVO.getModel_nam()+"   "+invoiceModeVO.getModel_dsc());
		return "template_Info";
	}
	
	
	public String editTemplateInformation(){
	
		try {
			String[] field_cdArrs= invoiceModeFieldParams.getField_cdArr();
			
			 List<InvoiceModeFieldVO> invoiceModeFieldVOList=new ArrayList<InvoiceModeFieldVO>();
			for(int i=0;i<field_cdArrs.length;i++)
			{
				InvoiceModeFieldVO invoiceModeFieldVO;
				
				logger.info(field_cdArrs[i]+"........");
				invoiceModeFieldVO=	invoicePrintManager.templateInvoiceModelFieldInformation(invoiceModeFieldParams.getModel_cd(), field_cdArrs[i]);
				if(invoiceModeFieldVO!=null)
				{
					invoiceModeFieldVOList.add(invoiceModeFieldVO);
					
				}
				else
				{
					invoiceModeFieldVO=new InvoiceModeFieldVO();
					invoiceModeFieldVO.setPos_x("0");
					invoiceModeFieldVO.setPos_y("0");
					invoiceModeFieldVO.setField_cd(field_cdArrs[i]);
					invoiceModeFieldVO.setModel_cd(invoiceModeFieldParams.getModel_cd());
					invoiceModeFieldVOList.add(invoiceModeFieldVO);
				}
				
				
			}
			
			 invoicePrintManager.editTemplateInformation(invoiceModeFieldVOList);
			
			this.json = "{'result':1,'message':'修改成功'}";
			logger.info("活动修改成功。。。。。");
		} catch (Exception e) {
			this.json = "{'result':0;'message':'修改失败'}";
			logger.info("活动修改失败。。。。。");
			e.printStackTrace();
		}
		
		
		return this.JSON_MESSAGE;
	}
	public String selectOne(){
		
		if(invoiceModeFieldParams== null)
		{
			invoiceModeFieldParams = new InvoiceModeFieldParams();
		}
		 modeSelectFieldVO=invoicePrintManager.selectOne(invoiceModeFieldParams);
		
		return "select";
	}
	public String  editFieldPosition() {
		try {
			List<InvoiceModeFieldVO> invoiceModeFieldVO_List=new ArrayList<InvoiceModeFieldVO>();
			String modelCd = ServletActionContext.getRequest().getParameter("model_cd");
			logger.info(modelCd+"  +++++");
		
			Map mapTemp = ServletActionContext.getRequest().getParameterMap();
			List parameterName = new ArrayList();
			Set keys = mapTemp.keySet();
			if(keys != null) 
			{
				Iterator iterator = keys.iterator(); 
				while(iterator.hasNext())
				{ 
					Object key = iterator.next();
					if(StringUtils.equals(key.toString().substring(0,3),"hmf"))
					{
						parameterName.add(key.toString());
					}
				}
			}
			if(parameterName != null && parameterName.size()>0)
			{
				InvoiceModeFieldVO invoiceModeFieldVO=new InvoiceModeFieldVO();
				for(int i=0 ; i<parameterName.size() ; i++ )
				{
					String modelFieldId = parameterName.get(i).toString().substring(3,parameterName.get(i).toString().length());
					String parameter = ServletActionContext.getRequest().getParameter(parameterName.get(i).toString());
					logger.info(parameter+" .........++++...");
					if(!"".equals(parameter))
					{
						
						String width;
						String height;
						String sParameters[] = parameter.split("#");
						width = sParameters[0];
						if(width.equals(""))
						{
							width = "0";
						}
						height = sParameters[1];
						if(height.equals(""))
						{
							height = "0";
						}
						logger.info("modelCd="+modelCd+";modelFieldId="+modelFieldId+";width="+width+";height="+height);
						logger.info("-------------------------------------");
						invoiceModeFieldVO.setField_cd(modelFieldId);
						invoiceModeFieldVO.setPos_x(width);
						invoiceModeFieldVO.setPos_y(height);
						invoiceModeFieldVO.setModel_cd(modelCd);
						invoicePrintManager.editFieldPosition(invoiceModeFieldVO);
						
					}
				}
			}
			
			this.msgs.add("发票字段位置修改成功！");
			this.urls.put("返回发票模板列表","invoicePrintAction!search.do");
			return this.MESSAGE;
			
		} catch (Exception e) {
			this.msgs.add("发票字段位置修改失败");
			e.printStackTrace();
			return this.MESSAGE;
		}
		
	}
	
	
	public InvoiceModeVO getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceModeVO invoice) {
		this.invoice = invoice;
	}

	public IInvoicePrintManager getInvoicePrintManager() {
		return invoicePrintManager;
	}

	public void setInvoicePrintManager(IInvoicePrintManager invoicePrintManager) {
		this.invoicePrintManager = invoicePrintManager;
	}
	public List<InvoiceFieldVO> getInvoiceFieldList() {
		return invoiceFieldList;
	}
	public void setInvoiceFieldList(List<InvoiceFieldVO> invoiceFieldList) {
		this.invoiceFieldList = invoiceFieldList;
	}
	public InvoiceModeVO getInvoiceModeVO() {
		return invoiceModeVO;
	}
	public void setInvoiceModeVO(InvoiceModeVO invoiceModeVO) {
		this.invoiceModeVO = invoiceModeVO;
	}
	public InvoiceModeFieldParams getInvoiceModeFieldParams() {
		return invoiceModeFieldParams;
	}
	public void setInvoiceModeFieldParams(
			InvoiceModeFieldParams invoiceModeFieldParams) {
		this.invoiceModeFieldParams = invoiceModeFieldParams;
	}
	public ModeSelectFieldVO getModeSelectFieldVO() {
		return modeSelectFieldVO;
	}
	public void setModeSelectFieldVO(ModeSelectFieldVO modeSelectFieldVO) {
		this.modeSelectFieldVO = modeSelectFieldVO;
	}
	public List getTemplateFieldInformation() {
		return templateFieldInformation;
	}

	public void setTemplateFieldInformation(List templateFieldInformation) {
		this.templateFieldInformation = templateFieldInformation;
	}


}
