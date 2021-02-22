package com.ztesoft.net.mall.plugin.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.GoodsRelAttConvert;
import com.ztesoft.net.mall.core.model.GoodsServiceTypeEnum;
import com.ztesoft.net.mall.plugin.service.IGoodsContractManager;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-11-05 14:23
 * To change this template use File | Settings | File Templates.
 */
public class GoodsContractAction extends WWAction {
    private final String LIST = "list";

    private final String LIST_BUY_WAY = "listBuyWay";

    private String name;//搜索条件

    private String[] goodsid;

    @Resource
    private IGoodsContractManager goodsContractManager;

    private String  editGoodsId;
    
    private String a_goods_id;
    
    List<GoodsRelAttConvert>  listSetContract;
    
    private String rel_contract_inst;
    //查询套餐类型
    public String query() {
        this.webpage= goodsContractManager.query(name,getPage(), getPageSize(), GoodsServiceTypeEnum.OFFER);

        return LIST;
    }

    //查询购买方式
    public String queryBuyWay() {
        this.webpage= goodsContractManager.query(name,getPage(), getPageSize(),GoodsServiceTypeEnum.BUYWAY);

        return LIST_BUY_WAY;
    }

    public String listBuyWay() {
        List list=goodsContractManager.query(goodsid,GoodsServiceTypeEnum.BUYWAY);
        this.json = JSONArray.fromObject(list).toString();

        return WWAction.JSON_MESSAGE;
    }
    //查询合约  (设置合约)
    public String queryContract() {
    	String editGoodsId = this.editGoodsId;
        listSetContract = goodsContractManager.getSetContract(a_goods_id, editGoodsId);
           return "selContract";
    }
    public String selContractList() {
    	
        this.webpage= goodsContractManager.query(name,getPage(), 4,GoodsServiceTypeEnum.CONTRACT);
           return "listContract";
    }
    //查询合约
    public String queryContractSearch() {
    	 List list=goodsContractManager.query(goodsid,GoodsServiceTypeEnum.CONTRACT);
         this.json = JSONArray.fromObject(list).toString();
         return WWAction.JSON_MESSAGE;
    }
    public String list() {
        List list=goodsContractManager.query(goodsid,GoodsServiceTypeEnum.OFFER);
        this.json = JSONArray.fromObject(list).toString();

        return WWAction.JSON_MESSAGE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String[] goodsid) {
        this.goodsid = goodsid;
    }

	public String getEditGoodsId() {
		return editGoodsId;
	}

	public void setEditGoodsId(String editGoodsId) {
		this.editGoodsId = editGoodsId;
	}

	public IGoodsContractManager getGoodsContractManager() {
		return goodsContractManager;
	}

	public void setGoodsContractManager(IGoodsContractManager goodsContractManager) {
		this.goodsContractManager = goodsContractManager;
	}

	public List<GoodsRelAttConvert> getListSetContract() {
		return listSetContract;
	}

	public void setListSetContract(List<GoodsRelAttConvert> listSetContract) {
		this.listSetContract = listSetContract;
	}

	public String getRel_contract_inst() {
		return rel_contract_inst;
	}

	public void setRel_contract_inst(String rel_contract_inst) {
		this.rel_contract_inst = rel_contract_inst;
	}

	public String getA_goods_id() {
		return a_goods_id;
	}

	public void setA_goods_id(String a_goods_id) {
		this.a_goods_id = a_goods_id;
	}
    
}
