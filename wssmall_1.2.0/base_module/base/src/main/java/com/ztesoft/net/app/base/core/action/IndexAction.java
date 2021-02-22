package com.ztesoft.net.app.base.core.action;

import java.util.List;

import com.ztesoft.net.eop.resource.IIndexItemManager;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.resource.model.IndexItem;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 后台首页
 * @author kingapex
 * 2010-10-12下午04:53:52
 */
public class IndexAction extends WWAction {

	  private IIndexItemManager indexItemManager;
	  private List<IndexItem> itemList;
	  private AdminUser adminUser;
	  private String pro_lanId;


	


	public AdminUser getAdminUser() {
		return adminUser;
	}


	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	@Override
	public String execute(){
    	  itemList  =  indexItemManager.list();
    	 
    	  //this.listGoodsApply=this.goodsManager.listGoodsApply();
    	 // System.out.print(this.listGoodsApply.size());
    	  
    	  this.adminUser = ManagerUtils.getAdminUser();
    	  
     	  String userid=ManagerUtils.getUserId();
//     	  if(adminUser.getFounder()==2 ||adminUser.getFounder()==3){
//     		  PartnerInfoReq partnerInfoReq = new PartnerInfoReq();
//     		  partnerInfoReq.setUserid(userid);
//     		  partnerInfoReq.setPartner(partner);
//     		  PartnerInfoResp partnerInfoResp = partnerServ.getPartnerByCurrentUserId(partnerInfoReq);
//     		  
//     		  partner = new Partner();
//     		  if(partnerInfoResp != null){
//     			 partner = partnerInfoResp.getPartner();
//     		  }
//     	  }
//    	  this.pro_lanId = Consts.LAN_PROV;
    	  return "index";
      }
      
            
	public IIndexItemManager getIndexItemManager() {
		return indexItemManager;
	}
	public void setIndexItemManager(IIndexItemManager indexItemManager) {
		this.indexItemManager = indexItemManager;
	}
	public List<IndexItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<IndexItem> itemList) {
		this.itemList = itemList;
	}

	public String getPro_lanId() {
		return pro_lanId;
	}

	public void setPro_lanId(String pro_lanId) {
		this.pro_lanId = pro_lanId;
	}

	
     
}