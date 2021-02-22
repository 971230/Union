package com.ztesoft.net.app.base.core.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.app.base.core.service.ICommonRegionManager;
import com.ztesoft.net.framework.action.WWAction;

public class CommonRegionAction extends WWAction{
   private ICommonRegionManager commonRegionManager;
   private String region_id;
   private String parent_region_id;
   private String region_code;
   private String region_name;
   private String region_desc;
   private String region_level;
  
  
   public String getRegion(){
		return "region_list";
   }
   public String getRootRegion(){
	    Map map = new HashMap();
		List region_list = new ArrayList();
		
		region_list =this.commonRegionManager.getRootRegion();
		
		if(!ListUtil.isEmpty(region_list)){
			for(int i=0; i< region_list.size(); i++){
				Map o_map = (Map)region_list.get(i);
				String code = Const.getStrValue(o_map, "region_id");
				
				int cnt = commonRegionManager.qryChildrenRegionCount(code);
				if( cnt > 0){
					o_map.put("state", "closed");
				}else{
					o_map.put("state", "open");
				}
			}
		}
		map.put("total", region_list.size());
		map.put("rows", region_list);
		this.json = JSON.toJSONString(map);
		return JSON_MESSAGE;
   }
   public String getChildrenRegion(){
		
		if(StringUtils.isEmpty(region_id)) return "error";
		
		List children_list = commonRegionManager.getChildrenOrg(region_id);
		
		if(!ListUtil.isEmpty(children_list)){
			for(int i=0; i< children_list.size(); i++){
				Map o_map = (Map)children_list.get(i);
				String code = Const.getStrValue(o_map, "region_id");
				
				int cnt = commonRegionManager.qryChildrenRegionCount(code);
				if( cnt > 0 &&!"4".equals(this.region_level)){
					o_map.put("state", "closed");
				}else{
					o_map.put("state", "open");
				}
			}
		}
		
		this.json = JSON.toJSONString(children_list);
		return WWAction.JSON_MESSAGE;
    }
   public String addRegion() throws UnsupportedEncodingException{
		
		try{
			if(!this.commonRegionManager.ifExistsRegion(region_code)){
				region_name = java.net.URLDecoder.decode(region_name, "utf-8");
				
				if(StringUtils.isNotEmpty(region_desc)){
					region_desc = java.net.URLDecoder.decode(region_desc, "utf-8");
				}
				commonRegionManager.addRegion(parent_region_id, region_id, region_code, region_name,region_desc,region_level);
			}else{
				this.json="存在相同的区域编码";//
				return WWAction.JSON_MESSAGE;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			this.json="新增异常";//
			return JSON_MESSAGE;
		}
		this.json="操作成功";//
		return JSON_MESSAGE;
	}
	
	public String modRegion(){
		try{
		    region_name = java.net.URLDecoder.decode(region_name, "utf-8");
		
			if(StringUtils.isNotEmpty(region_desc)){
				region_desc = java.net.URLDecoder.decode(region_desc, "utf-8");
			}
			commonRegionManager.modRegion(region_id, region_code, region_name, region_desc);
		}catch(Exception e){
			e.printStackTrace();
			this.json="修改异常";//
			return JSON_MESSAGE;
		}
		this.json = "操作成功";//
		return JSON_MESSAGE;
	}
	
	public String delRegion(){
		try{
			int cnt = commonRegionManager.qryChildrenRegionCount(region_id);
			if (cnt > 0) {
				this.json = "存在下级组织";
				return  WWAction.JSON_MESSAGE;
			}
			this.commonRegionManager.delRegion(region_id);
			this.json ="操作成功";
			
		}catch(Exception e){
			e.printStackTrace();
			this.json="删除异常:"+e.getMessage();
			return JSON_MESSAGE;
		}
		return WWAction.JSON_MESSAGE;
	}
public ICommonRegionManager getCommonRegionManager() {
	return commonRegionManager;
}
public void setCommonRegionManager(ICommonRegionManager commonRegionManager) {
	this.commonRegionManager = commonRegionManager;
}
public String getRegion_id() {
	return region_id;
}
public void setRegion_id(String region_id) {
	this.region_id = region_id;
}
public String getRegion_code() {
	return region_code;
}
public void setRegion_code(String region_code) {
	this.region_code = region_code;
}
public String getRegion_name() {
	return region_name;
}
public void setRegion_name(String region_name) {
	this.region_name = region_name;
}
public String getRegion_desc() {
	return region_desc;
}
public void setRegion_desc(String region_desc) {
	this.region_desc = region_desc;
}
public String getRegion_level() {
	return region_level;
}
public void setRegion_level(String region_level) {
	this.region_level = region_level;
}
public String getParent_region_id() {
	return parent_region_id;
}
public void setParent_region_id(String parent_region_id) {
	this.parent_region_id = parent_region_id;
}
 
}
