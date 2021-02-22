package services;

import com.ztesoft.net.mall.core.model.GoodsUsers;
import vo.Rule;

import java.util.List;

import params.RuleParams;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-26 16:08
 * To change this template use File | Settings | File Templates.
 * 商品规则管理接口
 */
public interface OrderRuleInf {
	
	
	  //根据商品获取指定类型规则
    public List<Rule> getRulesByCond(RuleParams ruleParams);
    
    //根据商品获取指定类型规则
    public List<Rule> getRulesByGoodsIdAndRuleType(String goods_id,String rule_type,String service_code);

    //获取商品指定规则
    public List<Rule> getRulesByGoodsId(String goods_id,String service_code);


    //获取商品指定工号配置数据信息
    public GoodsUsers getGoodsUsersByGoodsId(String goods_id,String source_from,String user_type);

    //商品用户处理转划拨器
    public GoodsUsers dealTransferGoodsUsers(GoodsUsers goodsUser);
    
    /**
     * 根据服务获取规则
     * @param service_id
     * @return
     */
    public List<Rule> getRulesByServiceId(String service_id);
}
