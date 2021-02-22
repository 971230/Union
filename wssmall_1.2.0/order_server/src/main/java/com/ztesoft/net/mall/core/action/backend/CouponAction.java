package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import params.member.req.MemberLvListReq;
import params.member.resp.MemberLvListResp;
import services.CouponInf;
import services.MemberLvInf;

import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.CouponsSearch;
import com.ztesoft.net.mall.core.plugin.promotion.IPromotionPlugin;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.service.promotion.IPromotionMethod;
import com.ztesoft.net.mall.core.service.promotion.PromotionConditions;

/**
 * 优惠券
 * 
 * @author lzf<br/>
 *         2010-3-26 下午01:58:19<br/>
 *         version 1.0<br/>
 */
public class CouponAction extends PromotionAction {
	
	//private ICouponManager couponManager;
	private IDcPublicInfoManager dcPublicInfoManager;
	private CouponInf couponServ;
	private String order;
	private Coupons coupons;
	private String[] id;
	private String[] pmtIdArray;
	private String cpnsid;
	private Integer point;
	private CouponsSearch couponsSearch;
	private List typeList;
	private List statusList;
	/*private Promotion promotion;
	private List lvList;
	private List goodsList;
	private String solutionHtml;
	private  PromotionConditions conditions;
	private IMemberLvManager memberLvManager;*/
	
	private List listCanExchange;
	
	private MemberLvInf memberLvServ;
	public String add(){
		return "add";
	}
	
	public String saveAdd(){
		if(coupons.getCpns_type()==0){
			coupons.setCpns_prefix("A" + coupons.getCpns_prefix());
		}else{
			coupons.setCpns_prefix("B" + coupons.getCpns_prefix());
		}
		
		ThreadContextHolder.getSessionContext().setAttribute("coupons", coupons);
		
		try {
			couponServ.add(coupons);
			this.msgs.add("优惠卷添加成功");
		} catch (Exception e) {
			this.msgs.add("优惠卷添加失败");
			e.printStackTrace();
		}
		this.urls.put("优惠卷列表", "#");

//		coupons.setPmt_id(1);//注意！这处要改！
		
//		return this.MESSAGE;
		return this.select_plugin();
	}
	
	public String edit(){
		coupons = couponServ.getCoupon(cpnsid);
		return "edit";
	}
	
	/**
	 * 查看优惠券详细
	 * @author mochunrun
	 */
	@Override
	public String show(){
		coupons = couponServ.getCoupon(cpnsid);
		promotion= 	this.promotionManager.get(coupons.getPmt_id());
		try{
			MemberLvListReq req = new MemberLvListReq();
			MemberLvListResp resp = memberLvServ.getMemberLvList(req);
			if(resp != null){
				this.lvList = resp.getMemberLvs();
			}
			
			String solution = this.promotion.getPmt_solution();
			
			//使关联的会员级别id选中
			List<Integer> dbLvList  = this.promotionManager.listMemberLvId(coupons.getPmt_id());
			for(int i=0;i<lvList.size();i++){
				MemberLv lv =(MemberLv)lvList.get(i);
				for(Integer dbLvid :dbLvList ){
					if(Integer.parseInt(lv.getLv_id()) == dbLvid.intValue()){
						lv.setSelected(true);
					}
				}
			}
			
			this.goodsList=this.promotionManager.listGoodsId(coupons.getPmt_id());
			
			IPromotionPlugin plugin = this.promotionManager.getPlugin(promotion.getPmts_id());
			conditions = new PromotionConditions( plugin.getConditions());
			String methodBeanName = plugin.getMethods();
			IPromotionMethod pmtMethod=  SpringContextHolder.getBean(methodBeanName);
			solutionHtml = pmtMethod.getInputHtml(coupons.getPmt_id(), solution);
			return "show";
		}catch(RuntimeException e){
			logger.info(e);
			this.msgs.add(e.getMessage());
			return WWAction.MESSAGE;
		}
	}
	
	public String saveEdit(){
		//coupons.setPmt_id(1);//注意！这处要改！
//		try {
//			couponManager.edit(coupons);
//			this.msgs.add("优惠卷修改成功");
//		} catch (Exception e) {
//			this.msgs.add("优惠卷修改失败");
//			e.printStackTrace();
//		}
//		this.urls.put("优惠卷列表", "#");
//		return this.MESSAGE;
		couponServ.updateCouponName(coupons);
		ThreadContextHolder.getSessionContext().setAttribute("coupons", coupons);
		return this.select_plugin();
	}
	
	@Override
	public String delete(){
		try{
			this.couponServ.delete(id, pmtIdArray);
			this.json = "{'result':0,'message':'删除成功'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{'result':1,'message':'删除失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	@Override
	public String list(){
		
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
	    this.typeList = dcPublicCache.getList("1059");
	    this.statusList = dcPublicCache.getList("1055");
		if(couponsSearch == null){
			couponsSearch = new CouponsSearch();
		}
		
		this.webpage = couponServ.list(couponsSearch ,this.getPage(), this.getPageSize(), order);
		return "list";
	}
	
	public String addExchange(){
		listCanExchange = couponServ.listCanExchange();
		return "addExchange";
	}
	
	public String saveAddExchange(){
		
		try {
			couponServ.saveExchange(cpnsid, point);
			this.msgs.add("操作成功");
		} catch (Exception e) {
			this.msgs.add("操作失败");
			e.printStackTrace();
		}
		this.urls.put("积分兑换优惠卷列表", "coupon!exlist.do");
		return WWAction.MESSAGE;
	}
	
	public String editExchange(){
		coupons = this.couponServ.getCoupon(cpnsid);
		return "editExchange";
	}
	
	public String deleteExchange(){
		try{
			this.couponServ.deleteExchange(id);
			this.json = "{'result':0,'message':'删除成功'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{'result':1,'message':'删除失败'}";
		}
		return WWAction.JSON_MESSAGE;
		
	}
	
	public String exlist(){
		this.webpage = couponServ.listExchange(this.getPage(), this.getPageSize());
		return "exlist";
	}


	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Coupons getCoupons() {
		return coupons;
	}

	public void setCoupons(Coupons coupons) {
		this.coupons = coupons;
	}

	

	@Override
	public String[] getId() {
		return id;
	}

	@Override
	public void setId(String[] id) {
		this.id = id;
	}


	public String[] getPmtIdArray() {
		return pmtIdArray;
	}

	public void setPmtIdArray(String[] pmtIdArray) {
		this.pmtIdArray = pmtIdArray;
	}

	public String getCpnsid() {
		return cpnsid;
	}

	public void setCpnsid(String cpnsid) {
		this.cpnsid = cpnsid;
	}

	public List getListCanExchange() {
		return listCanExchange;
	}

	public void setListCanExchange(List listCanExchange) {
		this.listCanExchange = listCanExchange;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Override
	public MemberLvInf getMemberLvServ() {
		return memberLvServ;
	}

	@Override
	public void setMemberLvServ(MemberLvInf memberLvServ) {
		this.memberLvServ = memberLvServ;
	}

	@Override
	public CouponInf getCouponServ() {
		return couponServ;
	}

	@Override
	public void setCouponServ(CouponInf couponServ) {
		this.couponServ = couponServ;
	}

	public CouponsSearch getCouponsSearch() {
		return couponsSearch;
	}

	public void setCouponsSearch(CouponsSearch couponsSearch) {
		this.couponsSearch = couponsSearch;
	}

	@Override
	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	@Override
	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public List getTypeList() {
		return typeList;
	}

	public void setTypeList(List typeList) {
		this.typeList = typeList;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

}
