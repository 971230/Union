package com.ztesoft.net.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrdPostModePrintManager;
import com.ztesoft.net.service.IOrdVisitTacheManager;



/**
 * 物流单打印
 * @作者 zhangJun
 * @创建日期 2014-11-3
 * @版本 V 1.0
 */
public class OrderPostModePrintAction extends WWAction {

	private static final long serialVersionUID = 1L;
	@Resource
	private IOrdPostModePrintManager postModePrintManager;
	@Resource
	private IOrdVisitTacheManager ordVisitTacheManager;
	
	private String order_id;
	private String delivery_id;
	private String delvery_print_id;
	private String itmesIds; 
	private String post_type;
	
	private String postId;
	private String wlnum;
	private String order_is_his;//quality_psot_print_add.jsp传过来
	private String is_receipt;
	private String accept_form;//华盛B2C装箱单打印页面
	private String print_type;
	
	//打印
	public void invoicePostPrint() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter invoiceWrite = response.getWriter();
		try {
			
			response.setCharacterEncoding("GBK");
	    	response.setContentType("text/html;charset=GBK;");
			String printHtml = postModePrintManager.doPrintInvoice(order_id, delivery_id, delvery_print_id, itmesIds, post_type,wlnum,postId,this.getRequest().getContextPath(),order_is_his);
			invoiceWrite.write(printHtml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			invoiceWrite.write(e.getMessage());
		}
	}
	
	
	//配货打印热敏单
	public void invoiceHotFeePrint() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter invoiceWrite = response.getWriter();
		try {			
			response.setCharacterEncoding("GBK");
	    	response.setContentType("text/html;charset=GBK;");
			String printHtml = postModePrintManager.doPrintHotFee(order_id,print_type,this.getRequest().getContextPath());
			invoiceWrite.write(printHtml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			invoiceWrite.write(e.getMessage());
		}
	}
	
	//打印
	public void hotFreePostModelNew() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter invoiceWrite = response.getWriter();
		try {
			
			response.setCharacterEncoding("GBK");
	    	response.setContentType("text/html;charset=GBK;");
	    	
			String printHtml = postModePrintManager.doHotFreeModelNew(order_id, delivery_id, delvery_print_id, itmesIds, post_type,wlnum,postId,this.getRequest().getContextPath(),order_is_his,is_receipt);
			invoiceWrite.write(printHtml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			invoiceWrite.write(e.getMessage());
		}
	}
	
	//打印
	public void hotFreePostModel() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter invoiceWrite = response.getWriter();
		try {
			
			response.setCharacterEncoding("GBK");
	    	response.setContentType("text/html;charset=GBK;");
	    	
			String printHtml = postModePrintManager.doHotFreeModel(order_id, delivery_id, delvery_print_id, itmesIds, post_type,wlnum,postId,this.getRequest().getContextPath(),order_is_his,is_receipt);
			invoiceWrite.write(printHtml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			invoiceWrite.write(e.getMessage());
		}
	}
	//华盛B2C装箱单
	public String hsB2CPacking() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
//		PrintWriter invoiceWrite = response.getWriter();
//		try {
			
			response.setCharacterEncoding("utf-8");
	    	response.setContentType("text/html;charset=utf-8;");
	    	
	    	Map<String,String> params = new HashMap<String,String>();
	    	params.put("order_id", order_id);
	    	params.put("cxt", this.getRequest().getContextPath());
	    	params.put("order_is_his", order_is_his);
	    	
			this.accept_form = postModePrintManager.hsB2CPacking(params);
			return "hs_packing_print";
//			invoiceWrite.write(printHtml);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			invoiceWrite.write(e.getMessage());
//		}
	}

	
	/**
	 * 更新赠品的打印状态
	 * 
	 * @return
	 */
	public String updateItemsPrintStatus() {
		try {
			if(EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//是历史单
				postModePrintManager.updateItemsPrintStatus(post_type,delivery_id,itmesIds,true);
			}else{
				postModePrintManager.updateItemsPrintStatus(post_type,delivery_id,itmesIds,false);
			}
			
			json = "{'result':0,'message':'更新补寄状态成功！'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'更新补寄状态失败！'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}
	
	public String toPrintConfirm() {
	
		return "quality_psot_print_confirm";
	}
	
	

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

	public String getDelvery_print_id() {
		return delvery_print_id;
	}

	public void setDelvery_print_id(String delvery_print_id) {
		this.delvery_print_id = delvery_print_id;
	}

	





	public String getItmesIds() {
		return itmesIds;
	}






	public void setItmesIds(String itmesIds) {
		this.itmesIds = itmesIds;
	}






	public String getPost_type() {
		return post_type;
	}






	public void setPost_type(String post_type) {
		this.post_type = post_type;
	}






	public IOrdPostModePrintManager getPostModePrintManager() {
		return postModePrintManager;
	}






	public void setPostModePrintManager(
			IOrdPostModePrintManager postModePrintManager) {
		this.postModePrintManager = postModePrintManager;
	}






	public String getPostId() {
		return postId;
	}






	public void setPostId(String postId) {
		this.postId = postId;
	}






	public String getWlnum() {
		return wlnum;
	}

	public String getIs_receipt(){
		return is_receipt;
	}
	public void setIs_receipt(String is_receipt){
		this.is_receipt = is_receipt;
	}



	public void setWlnum(String wlnum) {
		this.wlnum = wlnum;
	}
	public String getOrder_is_his() {
		return order_is_his;
	}
	public void setOrder_is_his(String order_is_his) {
		this.order_is_his = order_is_his;
	}

	public String getAccept_form() {
		return accept_form;
	}

	public void setAccept_form(String accept_form) {
		this.accept_form = accept_form;
	}


	public String getPrint_type() {
		return print_type;
	}


	public void setPrint_type(String print_type) {
		this.print_type = print_type;
	}

	
	
}
