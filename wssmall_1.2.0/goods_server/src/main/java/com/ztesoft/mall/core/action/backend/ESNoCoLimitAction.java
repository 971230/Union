package com.ztesoft.mall.core.action.backend;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.exception.NameRepetirException;
import com.ztesoft.net.mall.core.model.EsCoNoLimitEntity;
import com.ztesoft.net.mall.core.service.impl.ESNoCoLimitManager;

public class ESNoCoLimitAction extends BaseAction {
	private ESNoCoLimitManager esnclM;
	private EsCoNoLimitEntity esCnEntity;

	

	private String org_id; // 销售组织标识
	private String region_id;// 地市编码
	private String org_name;
	private String local_name;
	private String flag;
	private Integer max_ordinary;
	private Integer max_lucky;
	Map params = new HashMap();
	
	public void getParas(){
		params.put("org_id", org_id);
		params.put("org_name", org_name);
		params.put("region_id", region_id);
	}
	
	//自动生成发布清单
	public String autoCreatePublish(){
		try {
			esnclM.autoCreatePublish(org_id,region_id,max_ordinary,max_lucky);
			this.json = "{result:1,message:'操作成功'}";
		} catch (Exception e) {
			this.json = "{result:0,message:'操作失败'}";
			e.printStackTrace();
		}
		
		return JSON_MESSAGE;
	}
	
	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public ESNoCoLimitManager getEsnclM() {
		return esnclM;
	}

	public void setEsnclM(ESNoCoLimitManager esnclM) {
		this.esnclM = esnclM;
	}

	public EsCoNoLimitEntity getEsCnEntity() {
		return esCnEntity;
	}

	public void setEsCnEntity(EsCoNoLimitEntity esCnEntity) {
		this.esCnEntity = esCnEntity;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getLocal_name() {
		return local_name;
	}

	public void setLocal_name(String local_name) {
		this.local_name = local_name;
	}

	@Override
	public Map getParams() {
		return params;
	}

	@Override
	public void setParams(Map params) {
		this.params = params;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	
	public String goadd(){
		return "goaddEsCoNo";
	}

	public String edit() {
		if (org_id!=null&&region_id!=null) {
			esCnEntity=esnclM.get(org_id,region_id);
			
//			esCnEntity.setMax_lucky(map.get("max_lucky").toString());
//			esCnEntity.setMin_lucky((String)map.get("min_lucky"));
//			esCnEntity.setMax_ordinary((String)map.get("max_ordinary"));
//			esCnEntity.setMin_ordinary((String)map.get("min_ordinary"));
//			esCnEntity.setOrg_id((String)map.get("org_id"));
//			esCnEntity.setRegion_id((String)map.get("region_id"));
//			esCnEntity.setReplenish_factor(Float.parseFloat((String)map.get("replenish_factor")));
//			esCnEntity.setSource_from((String)map.get("source_from"));
//			esCnEntity.setWarning_lucky((String)map.get("warning_lucky"));
//			esCnEntity.setWarning_ordinary((String)map.get("warning_ordinary"));
//			esCnEntity.setWarning_phone((String)map.get("warning_phone"));
		}
		return "edit";
		
	}
	
	public String doAdds(){
		try {
			if(!flag.equals("true")){
				save();
				json = "{'result':0,'message':'添加成功！','url':'esconolimit!list.do'}";
			}else{
				update(esCnEntity);
				json = "{'result':0,'message':'修改成功！','url':'esconolimit!list.do'}";
			}
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public void doAdd() {
		if (!flag.equals("true")) {
			save();
		} else {
			try {
				update(esCnEntity);
			} catch (NameRepetirException e) {
				e.printStackTrace();
			}

		}
	}
	
	// 新增----------------------------
	public String save() {

		try {
			esnclM.addorUpdate(params, esCnEntity);
			msg.addResult(MESSAGE, "成功");
			msg.addResult("url", "esconolimit!list.do");
			msg.setSuccess(true);
			msg.setResult(0);
		} catch (Exception e) {
			e.printStackTrace();
			msg.addResult(MESSAGE, e.getMessage());
			msg.setSuccess(false);
			msg.setResult(1);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}
	
	public String  update(EsCoNoLimitEntity esCnEntity) throws NameRepetirException {
		try {
			esnclM.update(esCnEntity);
			msg.addResult(MESSAGE, "成功");
			msg.addResult("url", "esconolimit!list.do");
			msg.setSuccess(true);
			msg.setResult(0);
		} catch (Exception e) {
			e.printStackTrace();
			msg.addResult(MESSAGE, e.getMessage());
			msg.setSuccess(false);
			msg.setResult(1);
		}
		json = JSON.toJSONString(msg).toString();
		return "update";
		
	}

	// 查询------------------------------
	public String list() {
		getParas();//by liqingyi
		webpage = esnclM.getFormList(getPage(), getPageSize(), params);
		return "list";
	}

	public Integer getMax_ordinary() {
		return max_ordinary;
	}

	public void setMax_ordinary(Integer max_ordinary) {
		this.max_ordinary = max_ordinary;
	}

	public Integer getMax_lucky() {
		return max_lucky;
	}

	public void setMax_lucky(Integer max_lucky) {
		this.max_lucky = max_lucky;
	}
	
	
}
