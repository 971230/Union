package services;

import java.util.List;

import javax.annotation.Resource;

import params.RuleParams;
import vo.Rule;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.GoodsUsers;
import com.ztesoft.net.mall.core.service.IOrderRuleManager;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-26 16:09
 * To change this template use File | Settings | File Templates.
 */
public class OrderRuleServ implements OrderRuleInf {
    @Resource
    public IOrderRuleManager orderRuleManager;

    @Override
    public List<Rule> getRulesByGoodsIdAndRuleType(String goods_id, String rule_type,String service_code) {
        return this.orderRuleManager.getRulesByGoodsIdAndRuleType(goods_id,rule_type,service_code);
    }

    @Override
    public List<Rule> getRulesByGoodsId(String goods_id,String service_code) {
        return this.orderRuleManager.getRulesByGoodsId(goods_id,service_code);
    }

    @Override
    public GoodsUsers getGoodsUsersByGoodsId(String goods_id, String source_from, String user_type) {
        return this.orderRuleManager.getGoodsUsersByGoodsId(goods_id,source_from,user_type);
    }

    @Override
    public GoodsUsers dealTransferGoodsUsers(GoodsUsers goodsUser) {
        return this.orderRuleManager.dealTransferGoodsUsers(goodsUser);
    }
    
    @Override
	public List<Rule> getRulesByServiceId(String service_id){
    	return this.orderRuleManager.getRulesByServiceId(service_id);
    }
    
    public List<Rule> queryRulesByService(String service_code, String type,
			String goods_id){
    	return orderRuleManager.queryRulesByService(service_code, type, goods_id);
    }

	@Override
	public List<Rule> getRulesByCond(RuleParams ruleParams){
		if(StringUtil.isEmpty(ruleParams.getService_code()))
			ruleParams.setService_code(OrderStatus.SERVICE_CODE_ORDER);
		List<Rule> rules = this.queryRulesByService(ruleParams.getService_code(), ruleParams.getRule_type(), ruleParams.getGoods_id());//new ArrayList<Rule>();
		/*//TODO 此处需要合并处理add by wui
    	if(StringUtil.isEmpty(ruleParams.getGoods_id()) && !StringUtil.isEmpty(ruleParams.getService_code())){
    		rules = this.getRulesByServiceId(ruleParams.getService_code());
        }else{
        	rules = this.getRulesByGoodsIdAndRuleType(ruleParams.getGoods_id(), ruleParams.getRule_type(),ruleParams.getService_code());
        }*/
		return rules;
	}
}