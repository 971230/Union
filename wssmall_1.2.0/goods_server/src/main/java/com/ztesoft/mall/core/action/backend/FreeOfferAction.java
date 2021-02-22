package com.ztesoft.mall.core.action.backend;

import java.io.File;
import java.util.List;

import params.member.req.MemberLvListReq;
import params.member.resp.MemberLvListResp;
import services.MemberLvInf;

import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.FreeOffer;
import com.ztesoft.net.mall.core.service.IFreeOfferCategoryManager;
import com.ztesoft.net.mall.core.service.IFreeOfferManager;

/**
 * 赠品
 * 
 * @author 李志富 lzf<br/>
 *         2010-1-15 下午02:38:00<br/>
 *         version 1.0<br/>
 * <br/>
 */
public class FreeOfferAction extends WWAction {

	private String name;
	private String order;
	private FreeOffer freeOffer;
	private Integer fo_id;
	private String id;
	private String mstartdate;
	private String menddate;
	private File thumb;
	private String thumbFileName;
	private File pic;
	private String picFileName;
	private String oldthumb;
	private String oldpic;
	private Integer[] lv_ids;
	
	private List categoryList;
	private List member_lvList;

	private IFreeOfferManager freeOfferManager;
	private IFreeOfferCategoryManager freeOfferCategoryManager;
	private MemberLvInf memberLvServ;
	
	

	public String list() throws Exception {
		this.webpage = freeOfferManager.list(name, order, this
				.getPage(), this.getPageSize());
		return "list";
	}

	// 后台品牌回收站列表
	public String trash_list() {

		this.webpage = this.freeOfferManager.pageTrash(name, order, this
				.getPage(), this.getPageSize());
		return "trash_list";
	}

	public String add() {
		categoryList = this.freeOfferCategoryManager.getFreeOfferCategoryList();
		MemberLvListReq req = new MemberLvListReq();
		MemberLvListResp resp = memberLvServ.getMemberLvList(req);
		if(resp != null){
			this.member_lvList = resp.getMemberLvs();
		}
		
		return "add";
	}

	public String edit() {
		categoryList = this.freeOfferCategoryManager.getFreeOfferCategoryList();
		MemberLvListReq req = new MemberLvListReq();
		MemberLvListResp resp = memberLvServ.getMemberLvList(req);
		if(resp != null){
			this.member_lvList = resp.getMemberLvs();
		}
		freeOffer = this.freeOfferManager.get(fo_id);
		return "edit";
	}

	public String save() {
		
		if (pic != null) {
			if (FileBaseUtil.isAllowUp(picFileName)) {
				String[] path =UploadUtil.upload(pic, picFileName, "gift", 120,120);
				freeOffer.setPic(path[0]);
				freeOffer.setList_thumb(path[1]);
			} else {
				this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
				return WWAction.MESSAGE;
			}
		}
		
		freeOffer.setLv_ids(StringUtil.arrayToString(lv_ids, ","));
		freeOffer.setStartdate(mstartdate);
		freeOffer.setEnddate(menddate);
		freeOffer.setDisabled(0);
		freeOfferManager.saveAdd(freeOffer);
		this.msgs.add("赠品添加成功");
		this.urls.put("赠品列表", "freeOffer!list.do");
		return WWAction.MESSAGE;
	}

	//	
	public String saveEdit() {
 
		if (pic != null) {
			if (FileBaseUtil.isAllowUp(picFileName)) {
				
				String[] path =UploadUtil.upload(pic, picFileName, "gift", 120,120);
				freeOffer.setPic(path[0]);
				freeOffer.setList_thumb(path[1]);				
				UploadUtil.deleteFileAndThumb(oldpic);

			} else {
				this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
				return WWAction.MESSAGE;
			}
		}
		freeOffer.setLv_ids(StringUtil.arrayToString(lv_ids, ","));
		freeOffer.setStartdate(mstartdate);
		freeOffer.setEnddate(menddate);
		freeOfferManager.update(freeOffer);
		this.msgs.add("赠品修改成功");
		this.urls.put("赠品列表", "freeOffer!list.do");
		return WWAction.MESSAGE;
	}

	/**
	 * 将品牌放入回收站
	 * 
	 * @return
	 */
	public String delete() {
		try {
			this.freeOfferManager.delete(id);
			this.json = "{'result':0,'message':'删除成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'删除失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 将品牌从回收站中还原
	 * 
	 * @return
	 */
	public String revert() {
		try {
			freeOfferManager.revert(id);
			this.json = "{'result':0,'message':'还原成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'还原失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 清空回收站中的品牌
	 * 
	 * @return
	 */
	public String clean() {
		try{
			freeOfferManager.clean(id);
			this.json = "{'result':0,'message':'清除成功'}";
		}catch(RuntimeException e){
			 this.json="{'result':1,'message':'清除失败'}";
		 }
		return WWAction.JSON_MESSAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public IFreeOfferManager getFreeOfferManager() {
		return freeOfferManager;
	}

	public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
		this.freeOfferManager = freeOfferManager;
	}

	public FreeOffer getFreeOffer() {
		return freeOffer;
	}

	public void setFreeOffer(FreeOffer freeOffer) {
		this.freeOffer = freeOffer;
	}

	public Integer getFo_id() {
		return fo_id;
	}

	public void setFo_id(Integer foId) {
		fo_id = foId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMstartdate() {
		return mstartdate;
	}

	public void setMstartdate(String mstartdate) {
		this.mstartdate = mstartdate;
	}

	public String getMenddate() {
		return menddate;
	}

	public void setMenddate(String menddate) {
		this.menddate = menddate;
	}

	public File getThumb() {
		return thumb;
	}

	public void setThumb(File thumb) {
		this.thumb = thumb;
	}

	public String getThumbFileName() {
		return thumbFileName;
	}

	public void setThumbFileName(String thumbFileName) {
		this.thumbFileName = thumbFileName;
	}

	public File getPic() {
		return pic;
	}

	public void setPic(File pic) {
		this.pic = pic;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public String getOldthumb() {
		return oldthumb;
	}

	public void setOldthumb(String oldthumb) {
		this.oldthumb = oldthumb;
	}

	public String getOldpic() {
		return oldpic;
	}

	public void setOldpic(String oldpic) {
		this.oldpic = oldpic;
	}

	public IFreeOfferCategoryManager getFreeOfferCategoryManager() {
		return freeOfferCategoryManager;
	}

	public void setFreeOfferCategoryManager(
			IFreeOfferCategoryManager freeOfferCategoryManager) {
		this.freeOfferCategoryManager = freeOfferCategoryManager;
	}

	public List getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List categoryList) {
		this.categoryList = categoryList;
	}

	public List getMember_lvList() {
		return member_lvList;
	}

	public void setMember_lvList(List memberLvList) {
		member_lvList = memberLvList;
	}

	public Integer[] getLv_ids() {
		return lv_ids;
	}

	public void setLv_ids(Integer[] lvIds) {
		lv_ids = lvIds;
	}

	public MemberLvInf getMemberLvServ() {
		return memberLvServ;
	}

	public void setMemberLvServ(MemberLvInf memberLvServ) {
		this.memberLvServ = memberLvServ;
	}


}
