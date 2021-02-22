package com.ztesoft.mall.core.action.backend;

import com.opensymphony.xwork2.Action;
import com.powerise.ibss.framework.Const;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.SpecValue;
import com.ztesoft.net.mall.core.model.Specification;
import com.ztesoft.net.mall.core.service.ISpecManager;
import com.ztesoft.net.mall.core.service.ISpecValueManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 规格ation
 * @author kingapex
 *2010-3-7下午06:50:20
 */
public class SpecAction extends WWAction {
	
	private ISpecManager specManager;
	private ISpecValueManager specValueManager;
	private String spec_id;
	private Map specView;
	private List specList ;
	private List valueList;
	private Specification spec;
	private String[] valueArray;
	private String[] imageArray;
	private String[] id;
	
	public String checkUsed(){
		if(this.specManager.checkUsed(id)){
			this.json ="{result:1}";
		}else{
			this.json ="{result:0}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	public String specList(){
		specList = specManager.list(); 
		if(specList == null) specList = new ArrayList();
		if(specList!=null && specList.size()>0){
			Map spec = (Map)specList.get(0);
			valueList = this.specValueManager.list(Const.getStrValue(spec, "spec_id"));
		}
		return "spec_listn";
	}
	
	public String getSpecValues(){
		valueList = this.specValueManager.list(spec_id);
		return "spec_listn";
	}
	
	public String list(){
		specList = specManager.list();
		return "list";
	}
	
	public String add(){
		return Action.INPUT;
	}
	
	public String saveAdd(){
		this.fillSpecValueList();
		this.specManager.add(spec, valueList);
		this.msgs.add("规格添加成功");
		this.urls.put("规格列表", "spec!list.do");
		return WWAction.MESSAGE;
	}
	
	public String edit(){
		specView = this.specManager.get(spec_id);
		this.valueList = this.specValueManager.list(spec_id);
		return Action.INPUT;
	}
	
	public String saveEdit(){
		this.fillSpecValueList();
		this.specManager.edit(spec, valueList);
		this.msgs.add("规格添加成功");
		this.urls.put("规格列表", "spec!list.do");
		return WWAction.MESSAGE;
	}
	
	private List<SpecValue> fillSpecValueList(){
		valueList = new ArrayList<SpecValue>();
		
		if(valueArray!=null ){
			for(int i=0;i<valueArray.length;i++){
				String value =valueArray[i];
	
				SpecValue specValue = new SpecValue();
				specValue.setSpec_value(value);
				if( imageArray!=null){
					String image = imageArray[i];
					if(image == null || image.equals("")) image ="../shop/admin/spec/image/spec_def.gif";
					else
					image  =UploadUtil.replacePath(image);
					specValue.setSpec_image(image);
				}else{
					specValue.setSpec_image( "../shop/admin/spec/image/spec_def.gif" );
				}
				valueList.add(specValue);
			}
		}
		return valueList;
	}
	
		
	public String delete(){
		this.specManager.delete(id);
		this.json ="{'result':0,'message':'规格删除成功'}";
		return WWAction.JSON_MESSAGE;
	}

 
	
	public ISpecManager getSpecManager() {
		return specManager;
	}

	public void setSpecManager(ISpecManager specManager) {
		this.specManager = specManager;
	}

	public ISpecValueManager getSpecValueManager() {
		return specValueManager;
	}

	public void setSpecValueManager(ISpecValueManager specValueManager) {
		this.specValueManager = specValueManager;
	}

	public List getSpecList() {
		return specList;
	}

	public void setSpecList(List specList) {
		this.specList = specList;
	}

	public Specification getSpec() {
		return spec;
	}

	public void setSpec(Specification spec) {
		this.spec = spec;
	}

	public String[] getValueArray() {
		return valueArray;
	}

	public void setValueArray(String[] valueArray) {
		this.valueArray = valueArray;
	}

	public String[] getImageArray() {
		return imageArray;
	}

	public void setImageArray(String[] imageArray) {
		this.imageArray = imageArray;
	}

	public String getSpec_id() {
		return spec_id;
	}

	public void setSpec_id(String specId) {
		spec_id = specId;
	}

	public Map getSpecView() {
		return specView;
	}

	public void setSpecView(Map specView) {
		this.specView = specView;
	}

	public List getValueList() {
		return valueList;
	}

	public void setValueList(List valueList) {
		this.valueList = valueList;
	}

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}
	
	
	
	
}
