package com.ztesoft.net.mall.core.action.backend;

import com.opensymphony.xwork2.Action;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.GroupBuy;
import com.ztesoft.net.mall.core.model.GroupBuyCount;
import com.ztesoft.net.mall.core.service.IGroupBuyManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupBuyAction extends WWAction {
	
	private IGroupBuyManager groupBuyManager;
	private boolean  isEdit= false;
	private GroupBuy groupBuy;
	private String groupid;
	private String[] start;
	private String[] end;
	private int[] num;
	private String start_time;
	private String end_time;
	private String imgFileFileName;
	private File imgFile;

	
	private List<GroupBuyCount> ruleList;
	public String list(){
		//this.webpage = this.groupBuyManager.list(this.getPage(),this.getPageSize());
		if(groupBuy == null){
			groupBuy = new GroupBuy();
			groupBuy.setDisabled(-1);
		}
		Map<String,String> map = new HashMap<String, String>();
		map.put("title", groupBuy.getTitle());
		map.put("start_time", start_time);
		map.put("end_time", end_time);
		map.put("disabled", groupBuy.getDisabled()+"");
		map.put("goodsid", groupBuy.getGoodsid());
		this.webpage = this.groupBuyManager.queryList(map, this.getPage(), this.getPageSize());
		return "list";
	}
	
	public String add(){
		this.isEdit = false;
		
		return Action.INPUT;
	}
	
	public String edit(){
		this.isEdit = true;
		this.groupBuy= this.groupBuyManager.get(groupid);
		ruleList =groupBuyManager.listRule(groupid);
		return Action.INPUT;
	}
	
	public String saveAdd(){
		try{
			if(imgFile!=null && imgFileFileName!=null){
				String imgPath = UploadUtil.upload(imgFile, imgFileFileName, "goods");
				this.groupBuy.setImg(imgPath);
			}
			groupBuy.setStart_time(start_time);
			groupBuy.setEnd_time( end_time );
			groupBuy.setCountRuleList(this.createRule());
			this.groupBuyManager.add(groupBuy);
			this.msgs.add("团购添加成功");
			this.urls.put("团购列表", "groupBuy!list.do");
		}catch(RuntimeException e){
			this.msgs.add(e.getMessage());
			this.urls.put("重新输入", "javascript:history.back();");
		}
		return WWAction.MESSAGE;
	}
	
	public String saveEdit(){
		try{
			
			if(imgFile!=null && imgFileFileName!=null){
				String imgPath = UploadUtil.upload(imgFile, imgFileFileName, "goods");
				this.groupBuy.setImg(imgPath);
			}
			
			
			groupBuy.setStart_time(start_time);
			groupBuy.setEnd_time( end_time );
			groupBuy.setCountRuleList(this.createRule());
			this.groupBuyManager.edit(groupBuy);
			this.msgs.add("团购修改成功");
			this.urls.put("团购列表", "groupBuy!list.do");
		}catch(RuntimeException e){
			e.printStackTrace();
			this.msgs.add(e.getMessage());
			this.urls.put("重新输入", "javascript:history.back();");
		}
		return WWAction.MESSAGE;
	}
	
	private List<GroupBuyCount> createRule(){
		
		//if(start==null || end==null || num==null) throw new RuntimeException("规则数据输入不正确");
		
		List<GroupBuyCount>  list  = new ArrayList<GroupBuyCount>();
		if(start != null && end != null && num !=null){
			for(int i=0;i<start.length;i++){
				GroupBuyCount groupBuyCount = new GroupBuyCount();
				groupBuyCount.setStart_time(start[i]);
				groupBuyCount.setEnd_time(end[i]);
				groupBuyCount.setNum(num[i]);
				list.add(groupBuyCount);
			}
		}
		
		return list;
	}
	
	public String delete(){
		//this.groupBuyManager.delete(groupid);
		this.groupBuyManager.editGroupState(groupid, groupBuy.getDisabled());
		if(1 == groupBuy.getDisabled()){
			this.msgs.add("团购停用成功");
		}else if(0 == groupBuy.getDisabled()){
			this.msgs.add("团购启用成功");
		}
		this.urls.put("团购列表", "groupBuy!list.do");
		return WWAction.MESSAGE;
	}


	public  IGroupBuyManager getGroupBuyManager() {
		return groupBuyManager;
	}


	public  void setGroupBuyManager(IGroupBuyManager groupBuyManager) {
		this.groupBuyManager = groupBuyManager;
	}


	public  boolean getIsEdit() {
		return isEdit;
	}


	public  void setIsEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}


	public  String[] getStart() {
		return start;
	}


	public  void setStart(String[] start) {
		this.start = start;
	}


	public  String[] getEnd() {
		return end;
	}


	public  void setEnd(String[] end) {
		this.end = end;
	}


	public  int[] getNum() {
		return num;
	}


	public  void setNum(int[] num) {
		this.num = num;
	}


	public  GroupBuy getGroupBuy() {
		return groupBuy;
	}


	public  void setGroupBuy(GroupBuy groupBuy) {
		this.groupBuy = groupBuy;
	}


	public  String getGroupid() {
		return groupid;
	}


	public  void setGroupid(String groupid) {
		this.groupid = groupid;
	}


	public  List<GroupBuyCount> getRuleList() {
		return ruleList;
	}


	public  void setRuleList(List<GroupBuyCount> ruleList) {
		this.ruleList = ruleList;
	}


	public  String getStart_time() {
		return start_time;
	}


	public  void setStart_time(String startTime) {
		start_time = startTime;
	}


	public  String getEnd_time() {
		return end_time;
	}


	public  void setEnd_time(String endTime) {
		end_time = endTime;
	}


	public  void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}


	public String getImgFileFileName() {
		return imgFileFileName;
	}


	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}


	public File getImgFile() {
		return imgFile;
	}


	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	
}
