package com.ztesoft.remote.inf.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import params.ZteRequest;

import services.ServiceBase;
import zte.params.order.req.OrderAddReq;
import zte.params.order.resp.OrderAddResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.app.base.core.model.AdColumn;
import com.ztesoft.net.app.base.core.model.Adv;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IAdColumnManager;
import com.ztesoft.net.mall.core.service.IAdvManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.remote.inf.IAdvService;
import com.ztesoft.remote.params.adv.req.AdColumnReq;
import com.ztesoft.remote.params.adv.req.AdvAddReq;
import com.ztesoft.remote.params.adv.req.AdvCountReq;
import com.ztesoft.remote.params.adv.req.AdvDeleteReq;
import com.ztesoft.remote.params.adv.req.AdvHomeReq;
import com.ztesoft.remote.params.adv.req.AdvListReq;
import com.ztesoft.remote.params.adv.req.AdvPageReq;
import com.ztesoft.remote.params.adv.req.AdvPosReq;
import com.ztesoft.remote.params.adv.req.AdvReq;
import com.ztesoft.remote.params.adv.req.DelAdvReq;
import com.ztesoft.remote.params.adv.resp.AdColumnResp;
import com.ztesoft.remote.params.adv.resp.AdvAddResp;
import com.ztesoft.remote.params.adv.resp.AdvCountResp;
import com.ztesoft.remote.params.adv.resp.AdvDeleteResp;
import com.ztesoft.remote.params.adv.resp.AdvHomeResp;
import com.ztesoft.remote.params.adv.resp.AdvListResp;
import com.ztesoft.remote.params.adv.resp.AdvPageResp;
import com.ztesoft.remote.params.adv.resp.AdvResp;
import com.ztesoft.remote.params.adv.resp.EmptyParamOutResp;
import commons.CommonTools;

/**
 * 广告分类service
* @作者 wu.i 
* @创建日期 2013-9-23 
* @版本 V 1.0
* 
 */
@SuppressWarnings("unchecked")
public class AdvService extends ServiceBase implements IAdvService {

	@Resource
	private IAdvManager advManager;
	
	@Resource
	private IAdColumnManager adColumnManager;
	
	@Override
	public AdvResp queryAdByAcId(AdvReq advReq) {
		AdvResp advResp = new AdvResp();
		String acid = advReq.getAcid();
		acid = acid == null ? "0" : acid;
		try{
			AdColumn adc = adColumnManager.getADcolumnDetail(acid);
			List<Adv> advList = advManager.listAdv(acid, advReq.getUser_id());
			advList = advList == null ? new ArrayList<Adv>():advList;
			String width = advReq.getWidth();
			String height =advReq.getHeight();
			
			advResp.setWidth(width);
			advResp.setHeight(height);
			advResp.setAdColumn(adc);
			advResp.setAdvList(advList);
				
			this.addReturn(advReq, advResp);
			
			
		}catch(RuntimeException e){
			e.printStackTrace();
			CommonTools.addFailError(e.getMessage());
		}
		return advResp;
		
	}
	
	@Override
	public EmptyParamOutResp delAdvbyStaffNo(DelAdvReq delAdvReq) {
		 String staff_no = delAdvReq.getStaff_no();
		 this.advManager.delAdvByStaffNo(staff_no);
		 EmptyParamOutResp emptyParamOutResp = new EmptyParamOutResp();
		 this.addReturn(delAdvReq, emptyParamOutResp);
		 return emptyParamOutResp;
	}
	
	@Override
	public AdvCountResp getAdvCount(AdvCountReq advCountReq) {
		AdvCountResp  advCountResp = new AdvCountResp();
		int wait_audit_adv  = this.advManager.getNoAuditAdvNum();
		int adv_Count = this.advManager.getAllAdvNum();
		advCountResp.setWait_audit_adv(wait_audit_adv);
		advCountResp.setAdv_Count(adv_Count);
		this.addReturn(advCountReq, advCountResp);
		return advCountResp;
	}

	@Override
	public AdColumnResp getAdColumnDetail(AdColumnReq adColumnReq) {
		AdColumnResp adColumnResp  = new AdColumnResp();
		AdColumn adColumn  = new AdColumn();
		if(adColumnReq.getAcid()!=null&&!"".equals(adColumnReq.getAcid())){
			 adColumn = this.adColumnManager.getADcolumnDetail(adColumnReq.getAcid());
		}
		else if(adColumnReq.getCatid()!=null&&!"".equals(adColumnReq.getCatid())&&adColumnReq.getPosition()!=null&&!"".equals(adColumnReq.getPosition())){
			 adColumn = this.adColumnManager.getADcolumnDetail(adColumnReq.getCatid(), adColumnReq.getPosition());
		}else{
			adColumn = null;
		}
		adColumnResp.setAdColumn(adColumn);
		this.addReturn(adColumnReq, adColumnResp);
		return adColumnResp;
	}

	@Override
	public AdColumnResp listAllAdvPos(AdvPosReq advPosReq) {
		AdColumnResp adColumnResp = new AdColumnResp();
		List<AdColumn> listColumn = this.adColumnManager.listAllAdvPos();
		adColumnResp.setAdColumnList(listColumn);
		this.addReturn(advPosReq, adColumnResp);
		return adColumnResp;
	}
	
	@Override
	public AdvHomeResp getHomeAdv(AdvHomeReq advHomeReq) {
		
		return (AdvHomeResp)this.getOrderResp(advHomeReq).getZteResponse();
	}
	
	/**
	 * 调用订单规则
	 * @param request
	 * @return
	 */
	private OrderAddResp getOrderResp(ZteRequest request){
		
		OrderAddReq orderAddReq = new OrderAddReq();
		OrderAddResp resp = new OrderAddResp();
		List<Map> paramsl =new ArrayList<Map>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try{
			
			Class reqClass = request.getClass();
			Method reqProduct = reqClass.getMethod("getProduct_id");
			String product_id = (String)reqProduct.invoke(request);
			
			map.put("product_id", product_id); //所定义的充值商品
			map.put("userid", "1");
			map.put("app_code", AppKeyEnum.APP_KEY_WSSMALL_LLKJ_WT.getAppKey());
			paramsl.add(map);
			
			Method reqCode = reqClass.getMethod("getService_code");
			String service_code = (String)reqCode.invoke(request);
			orderAddReq.setService_code(service_code);			//所定义的编码 es_service_offer表
			
			orderAddReq.setParamsl(paramsl);
			orderAddReq.setZteRequest(request);
			ZteClient zteClient = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_LLKJ_WT);
			resp = zteClient.execute(orderAddReq, OrderAddResp.class);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return resp;
	}
	
	
	@Override
	public AdvPageResp listAdvPage(AdvPageReq advPageReq){
		
		Page page = advManager.listAdvPage(advPageReq.getAdv(), 
				advPageReq.getPageNo(), advPageReq.getPageSize(),advPageReq.getIsPublic());
		
		AdvPageResp advPageResp = new AdvPageResp();
		advPageResp.setPage(page);
		
		addReturn(advPageReq, advPageResp);
		return advPageResp;
	}
	
	@Override
	public AdvAddResp addAdv(AdvAddReq req) {
		Adv adv = new Adv();
		AdvAddResp resp = new AdvAddResp();
		try {
			BeanUtils.copyProperties(adv, req);
			AdminUser adminUser = ManagerUtils.getAdminUser();
			int founder = adminUser.getFounder();
			if(Consts.CURR_FOUNDER0 == founder || Consts.CURR_FOUNDER1 == founder || Consts.CURR_FOUNDER3 == founder){     //电信员工或系统管理员
				adv.setState(Consts.ADV_STATE_1);   //已发布
			}else {
				adv.setState(Consts.ADV_STATE_0);   //待审核
			}
			String user_no = "-1";
			if(!ManagerUtils.isAdminUser()){
	            user_no =adminUser.getUserid()+"";
	        }
			adv.setUser_id(user_no);
			adv.setStaff_no(req.getStaff_no()==null?0:Long.valueOf(req.getStaff_no()));
			adv.setAtype("1");
			adv.setDisabled("false");
			String[] str= new String[2];
			if(StringUtils.isNotEmpty(adv.getAcid())){//分隔广告位置的ID和所处的位置 如123-lb
				str=adv.getAcid().split("-");
			}
			
			adv.setAcid(str[0]);
			//在商品类别goods_cat增加一条，会在es_adcolumn增加2条数据：一条楼层和类别 ;像左上右上这种情况的数据，已经固化，不能动
			if(2 == str.length && this.adColumnManager.getADcolumnDetail(adv.getAcid())==null){
				AdColumn adColumn=new AdColumn();
				adColumn.setCname(str[2]+"-类别");
				adColumn.setWidth("765");
				adColumn.setHeight("300");
				adColumn.setAtype(0);
				
				String acid=this.adColumnManager.addAdvc(adColumn);//
				this.adColumnManager.addAdcolumnCat(acid,adv.getAcid(), "lb");
				
				adColumn.setCname(str[2]+"-楼层");
				acid=this.adColumnManager.addAdvc(adColumn);//
				this.adColumnManager.addAdcolumnCat(acid,adv.getAcid(), "lc");
			}
			advManager.addAdv(adv);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败："+e.getMessage());
		}
		addReturn(req,resp);
		return resp;
	}
	
	@Override
	public AdvDeleteResp deleteAdv(AdvDeleteReq req) {
		AdvDeleteResp resp = new AdvDeleteResp();
		AdminUser adminUser = ManagerUtils.getAdminUser();
		if(adminUser!=null){
			String user_id = adminUser.getUserid();
			String aid = req.getAid();
			Adv adv = this.advManager.getAdvDetail(aid);
			if(adv!=null){
				if(user_id.equals(adv.getUser_id())){
					advManager.delAdvs(aid);
					resp.setResult(true);
					resp.setError_code("0");
					resp.setError_msg("成功");
				}
				else{
					resp.setResult(false);
					resp.setError_code("-1");
					resp.setError_msg("失败：只能删除分销商所属的广告！");
				}
			}
			else{
				resp.setResult(false);
				resp.setError_code("-1");
				resp.setError_msg("失败：广告不存在！");
			}
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：请先登录！");
		}
		addReturn(req,resp);
		return resp;
	}
	
	@Override
	public AdvListResp listAdv(AdvListReq req) {
		AdvListResp resp = new AdvListResp();
		String staff_no = req.getStaff_no();
		Map<String,String> params = new HashMap<String, String>();
		params.put("staff_no", staff_no);
		List<Adv> advList = this.advManager.listAdv(params);
		if(advList!=null && advList.size()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setAdvList(advList);
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		addReturn(req, resp);
		return resp;
	}

	public IAdvManager getAdvManager() {
		return advManager;
	}

	public void setAdvManager(IAdvManager advManager) {
		this.advManager = advManager;
	}

	public IAdColumnManager getAdColumnManager() {
		return adColumnManager;
	}

	public void setAdColumnManager(IAdColumnManager adColumnManager) {
		this.adColumnManager = adColumnManager;
	}

}