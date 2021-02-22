package com.ztesoft.mall.core.action.backend;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.service.IContractConfigManager;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;

/**
 * 合约配置Action
 * @author deng.yingxiang
 *
 */
/**
 * @author Administrator
 *
 */
public class ContractConfigAction extends WWAction {
	
	private String terminal_name;
	private String terminal_type;
	private String contract_name;
	private String offer_name;
	private String selected_contracts;  //已选择的合约
	private String check_contract_id;
	private String check_offer_id;
	private String tag_name;
	private Map tag;
	private List catList;//商品分类
	private IContractConfigManager contractConfigManager;
	private IGoodsCatManager goodsCatManager;
	
	public String contractMachineConfig(){
		return "contract_machine_config";
	}
	
	public String getCatTree(){
		this.catList = goodsCatManager.listAllChildrenEcs("0","goods");
		return "cat_tree";
	}
	
    /**
     * 查询终端
     * @return
     */
    public String terminalList(){
    	webpage = this.contractConfigManager.getTerminalList(this.getPage(), this.getPageSize(),terminal_name,terminal_type);
    	return  "terminalList";
    }
    
    /**
     * 查询合约
     * @return
     */
    public String contractList(){
		try{
			if(StringUtils.isNotEmpty(contract_name)){
				contract_name = URLDecoder.decode(contract_name,"utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	webpage = this.contractConfigManager.getContractList(this.getPage(), this.getPageSize(),contract_name);
    	return  "contractList";
    }
    
    
    /**
     * 查询套餐
     * @return
     */
    public String offerList(){
		try{
			if(StringUtils.isNotEmpty(offer_name)){
				offer_name = URLDecoder.decode(offer_name,"utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	webpage = this.contractConfigManager.getOfferList(this.getPage(), this.getPageSize(),offer_name,selected_contracts);
    	return  "offerList";
    }
    
    /**
     * 查询套餐
     * @return
     */
    public String getPackegeLevel(){

    	List list = this.contractConfigManager.getPackegeLevel();
    	this.json = "{levelList:[";
    	for(int i=0;i<list.size();i++){
    		Map map = (Map)list.get(i);
    		String level = (String)map.get("value");
    		if(i>0) this.json += ",";
    		this.json += level;
    	}
    	this.json += "]}";
     	return  WWAction.JSON_MESSAGE;
    }
    
    /**
     * 查询商品包
     * @return
     */
    public String getTagsByName(){
    	//tag_name = "TCLJ320黑24月存费送机";
    	tag = this.contractConfigManager.getTagsByName(tag_name);
    	json = "{'tag_id':"+(String)tag.get("tag_id")+"}";
    	return  WWAction.JSON_MESSAGE;
    }
    
    

	public String getTerminal_name() {
		return terminal_name;
	}

	public void setTerminal_name(String terminal_name) {
		this.terminal_name = terminal_name;
	}


	public IContractConfigManager getContractConfigManager() {
		return contractConfigManager;
	}

	public void setContractConfigManager(
			IContractConfigManager contractConfigManager) {
		this.contractConfigManager = contractConfigManager;
	}

	public IGoodsCatManager getGoodsCatManager() {
		return goodsCatManager;
	}

	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}

	public String getContract_name() {
		return contract_name;
	}

	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}

	public String getOffer_name() {
		return offer_name;
	}

	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}

	public String getSelected_contracts() {
		return selected_contracts;
	}

	public void setSelected_contracts(String selected_contracts) {
		this.selected_contracts = selected_contracts;
	}

	public String getCheck_contract_id() {
		return check_contract_id;
	}

	public void setCheck_contract_id(String check_contract_id) {
		this.check_contract_id = check_contract_id;
	}

	public String getCheck_offer_id() {
		return check_offer_id;
	}

	public void setCheck_offer_id(String check_offer_id) {
		this.check_offer_id = check_offer_id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public List getCatList() {
		return catList;
	}

	public void setCatList(List catList) {
		this.catList = catList;
	}

	public String getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}
	

}
