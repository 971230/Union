package com.ztesoft.crm.pass.ecsord_server;

import java.util.ArrayList;
import java.util.List;


import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusFromWMSReq;
import zte.net.ecsord.params.wms.req.SynCardInfoWMSReq;
import zte.net.ecsord.params.wms.req.SynSerialNumWMSReq;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusFromWMSResp;
import zte.net.ecsord.params.wms.resp.SynCardInfoWMSResp;
import zte.net.ecsord.params.wms.resp.SynSerialNumWMSResp;
import zte.net.ecsord.params.wms.vo.NotifyStatusFromWMSOrderInfoVo;
import zte.net.ecsord.params.wms.vo.SynCardInfoOrderInfoVo;
import zte.net.ecsord.params.wms.vo.SynSerialGoodInfoVo;
import zte.net.ecsord.params.wms.vo.SynSerialOrderInfoVo;
import zte.net.ecsord.params.zb.req.NotifyOpenAccountGDRequest;
import zte.net.ecsord.params.zb.resp.NotifyOpenAccountGDResponse;
import zte.net.ecsord.params.zb.vo.AccountInfo;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.params.req.RuleTreeExeReq;


/**
 * 规则单元测试代码
 */
public class RuleTest {

	private String Order_id ="DG201410254639860716";//测试的订单ID
	private String plan_id="";//方案ID
	private String rule_id="";//规则ID
	
	private String model_zd="01";//自动化集中生产模式
	private String model_rg="02";//人工集中生产模式
	private String model_xc="03";//现场生产模式
	private String model_wl="04";//物流生产模式
	private String model_dl="05";//独立生产模式
	
	private String is_physics="1";//是实物
	private String is_send_zb="1";//是否与总部互交
	
	private String goods_type="";//好卡
	
	
	

	/**
	 * 功能：测试入口
	 * 总测试结果：测试中
	 */
    public void test(){
    	System.out.println("star test!");
		OrderTreeBusiRequest orderTree=CommonDataFactory.getInstance().getOrderTree(Order_id);//加载订单树
		
//		this.pick();//预检货--没通过
//		this.act();//开户--没通过
		this.writeCard();//写卡--没有通过
		//this.BSS();//bss--没通过
		
		//this.getValue();//取值错误
    }
    /**
     * @描述：预检货
     * @所有测试结果：
     */
    public void pick(){
    	this.picksynToWMS();//没通过，接口报错
    	//this.pickGetNo1();//通过
    	//this.pickGetNo2();//通过
    	//pickNotifyToZB();//通过
    }
    
    /**
     * @环节：预检货
     * @描述：订单信息同步到WMS(自动)-1
     * @测试结果：没通过
     * @测试描述：{9999}调用服务[WMS.notifyOrderInfoWMS]出错:没有返回报文nullJ31.xml没有返回报文nullJ31.xml
     **/
    public void picksynToWMS(){
    	plan_id="101";
		rule_id="201410257194000080";//自动模式下面的
	    TacheFact fact =new TacheFact();
		fact.setOrder_id(Order_id);
//		fact.setIs_physics(is_physics);
		fact.setOrder_model(model_zd);
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setPlan_id(plan_id);
		req.setRule_id(rule_id);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
    }
    
    /**
     * @环节：预检货
     * @描述：获取终端串号-2.1
     * @测试结果：通过
     * @测试描述：订单数CommonDataFactory.getInstance().getOrderItemProdBusi(order_id,EcsOrderConsts.GOODS_TYPE_TERMINAL) 无数据
     **/
    public void pickGetNo1(){
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			SynSerialNumWMSReq  snw=new SynSerialNumWMSReq();
			snw.setActiveNo("wmsSynPickNotify-test");
			snw.setReqId("J32");
			snw.setReqType("at_syn_series_num");
			snw.setReqTime("2014-10-28 11:15:35");
			   SynSerialOrderInfoVo  vo=new SynSerialOrderInfoVo();
			      vo.setOrderId(Order_id);
				      List<SynSerialGoodInfoVo>  goodList=new ArrayList<SynSerialGoodInfoVo>();
				              SynSerialGoodInfoVo  goodVo=new SynSerialGoodInfoVo();
				              goodVo.setTerminalImei("DG201410236062860650CH");
				       goodList.add(goodVo);
				  vo.setGoodInfo(goodList);
		    snw.setOrderInfo(vo);  
			SynSerialNumWMSResp resp=client.execute(snw, SynSerialNumWMSResp.class);
    }
    /**
     * @环节：预检货
     * @描述：获取终端串号-2.2  缺货
     * @测试结果：通过
     * @测试描述：
     **/
    public void pickGetNo2(){
    			ZteClient client1 = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
    			NotifyOrderStatusFromWMSReq req=new NotifyOrderStatusFromWMSReq();
    			req.setActiveNo("wmsSynPickNotify-test2");
    			req.setReqId("J32");
    			req.setReqType("at_syn_series_num");
    			req.setReqTime("2014-10-28 11:15:35");
    				List<NotifyStatusFromWMSOrderInfoVo> list=new ArrayList<NotifyStatusFromWMSOrderInfoVo>();
    					NotifyStatusFromWMSOrderInfoVo infoVo=new NotifyStatusFromWMSOrderInfoVo();
    					infoVo.setOrderId(Order_id);
    					//infoVo.setStatus("");//不缺货情况
    					infoVo.setStatus(EcsOrderConsts.ORDER_STATUS_WMS_1);//缺货情况
    			   list.add(infoVo);
//    			req.setOrderInfo(list);
    			NotifyOrderStatusFromWMSResp resp=client1.execute(req, NotifyOrderStatusFromWMSResp.class);
    }
    
    /**
     * @环节：预检货
     * @描述：拣货完成通知总部-3
     * @测试结果：通过
     * @测试描述：无报错
     **/
    public void pickNotifyToZB(){
    	plan_id="101";
		rule_id="201410259913000082";//自动模式下面的
	    TacheFact fact =new TacheFact();
		fact.setOrder_id(Order_id);
//		fact.setIs_physics(is_physics);
		fact.setIs_send_zb(is_send_zb);
		fact.setOrder_model(model_zd);
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setPlan_id(plan_id);
		req.setRule_id(rule_id);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
    }
    
    /**
     * @描述：开户环节
     * @所有测试结果：还没通过
     */
    public void act(){
    	//actTZFromZB();//没通过,更新订单数报错
    	//actGetData();//通过
    	//actWMS();//没通过,接口报错
    }
    
    /**
     * @环节：开户
     * @描述：总部开户结果接收 -1
     * @测试结果：还没通过
     * @测试描述：业务组件报错：orderItemExtBusi.store(); 报错，不知道是不是数据问题。
     */
    public void actTZFromZB(){
    	ZteClient client= ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		NotifyOpenAccountGDRequest  req =new NotifyOpenAccountGDRequest();
		req.setOrderId(Order_id);
		req.setActiveNo("zbSynAccountNotify-test1");
		     List<AccountInfo>  list=new ArrayList<AccountInfo>();
		            AccountInfo info=new AccountInfo();
		     list.add(info);
	    req.setAccountInfo(list);
	    NotifyOpenAccountGDResponse resp=client.execute(req, NotifyOpenAccountGDResponse.class);
    }
    /**
     * @环节：开户
     * @描述：获取总部写卡数据-2
     * @测试结果：通过
     * @测试描述：
     **/
    public void actGetData(){
    	plan_id="102";
		rule_id="201410256877000093";//自动模式下面的
	    TacheFact fact =new TacheFact();
		fact.setOrder_id(Order_id);
		fact.setOrder_model(model_zd);
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setPlan_id(plan_id);
		req.setRule_id(rule_id);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
    }
    
    /**
     * @环节：开户
     * @描述：通知WMS开户完成-3
     * @测试结果：没通过
     * @测试描述：{9999}调用服务[WMS.notifyOrderInfoWMS]出错:没有返回报文nullJ31.xml没有返回报文nullJ31.xml
     **/
    public void actWMS(){
    	plan_id="102";
		rule_id="201410259803000094";
	    TacheFact fact =new TacheFact();
		fact.setOrder_id(Order_id);
		fact.setOrder_model(model_zd);
//		fact.setIs_physics(is_physics);
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setPlan_id(plan_id);
		req.setRule_id(rule_id);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
    }
    
    /**
     * @描述：写卡环节
     * @所有测试结果：测试中
     */
    public void writeCard(){
    	//writeCardReCardNo();//通过
    	//writeCardGetICCID();//通过
    	//writeCardGetData();//通过
    	//writeCardRuiShen();//通过
    	//writeCardNotifyToZB();//通过
    //	writeCardNotifyWMSBackCard();//还没有业务组件
    	//writeCardNotifyToWMS();//没有通过，接口报错
    }
    
    /**
     * @环节：写卡
     * @描述：接收写卡机编号-1
     * @测试结果：通过
     * @测试描述：通过
     **/
    public void writeCardReCardNo(){
    	ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
    	SynCardInfoWMSReq  snw=new SynCardInfoWMSReq();
		snw.setActiveNo("wmsSynMachineNumNotify-test");
		snw.setReqId("J34");
		snw.setReqType("at_syn_readId");
		snw.setReqTime("2014-10-28 11:15:35");
		     List<SynCardInfoOrderInfoVo> orderInfoList = new ArrayList<SynCardInfoOrderInfoVo>();
		        SynCardInfoOrderInfoVo orderInfo = new SynCardInfoOrderInfoVo();
		        orderInfo.setOrderId(Order_id);
		     orderInfoList.add(orderInfo) ;  
//		snw.setOrderInfo(orderInfoList);
		
		SynCardInfoWMSResp resp=client.execute(snw, SynCardInfoWMSResp.class);
    }
    
    /**
     * @环节：写卡
     * @描述：获取ICCID-2
     * @测试结果：通过
     * @测试描述：
     **/
    public void writeCardGetICCID(){
    	plan_id="103";
		rule_id="201410255887000106";
	    TacheFact fact =new TacheFact();
		fact.setOrder_id(Order_id);
		fact.setOrder_model(model_zd);
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setPlan_id(plan_id);
		req.setRule_id(rule_id);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
    }
    
    /**
     * @环节：写卡
     * @描述：获取总部写卡数据-3
     * @测试结果： 通过
     * @测试描述：  
     **/
    public void writeCardGetData(){
    	plan_id="103";
		rule_id="201410258918000107";//
	    TacheFact fact =new TacheFact();
		fact.setOrder_id(Order_id);
		fact.setOrder_model(model_zd);
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setPlan_id(plan_id);
		req.setRule_id(rule_id);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
    }
    
    /**
     * @环节：写卡
     * @描述：同步写卡数据到森锐系统-4
     * @测试结果：通过
     * @测试描述：
     **/
    public void writeCardRuiShen(){
    	plan_id="103";
		rule_id="201410257750000108";//
	    TacheFact fact =new TacheFact();
		fact.setOrder_id(Order_id);
		fact.setOrder_model(model_zd);
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setPlan_id(plan_id);
		req.setRule_id(rule_id);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
    }
    /**
     * @环节：写卡
     * @描述：写卡结果通知总部-5
     * @测试结果：通过
     * @测试描述：
     **/
    public void writeCardNotifyToZB(){
    	plan_id="103";
		rule_id="201410259624000109";//
	    TacheFact fact =new TacheFact();
		fact.setOrder_id(Order_id);
		fact.setOrder_model(model_zd);
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setPlan_id(plan_id);
		req.setRule_id(rule_id);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
    }
    
    /**
     * @环节：写卡
     * @描述：通知WMS退卡-6
     * @测试结果：没通过
     * @测试描述：还没有业务组件
     **/
    public void writeCardNotifyWMSBackCard(){
    	plan_id="103";
		rule_id="201410255011000110";//
	    TacheFact fact =new TacheFact();
		fact.setOrder_id(Order_id);
		fact.setOrder_model(model_zd);
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setPlan_id(plan_id);
		req.setRule_id(rule_id);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
    }
    
    /**
     * @环节：写卡
     * @描述：写卡完成通知WMS(自动)-7
     * @测试结果：还没有通过
     * @测试描述：接口错误：{9999}调用服务[      ]出错:没有返回报文nullJ31.xml没有返回报文nullJ31.xml
     **/
    public void writeCardNotifyToWMS(){
    	plan_id="103";
		rule_id="201410259920000111";//
	    TacheFact fact =new TacheFact();
		fact.setOrder_id(Order_id);
		fact.setOrder_model(model_zd);
		fact.setGoods_type("20000");
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setPlan_id(plan_id);
		req.setRule_id(rule_id);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
    }
    
    /**
     * @描述：BSS环节
     * @所有测试结果：没通过
     */
    public void BSS(){
    	this.BSSStatus();//
    }
    
    /**
     * @环节：BSS
     * @描述：BSS办理状态设置（未办理）-1
     * @测试结果：没通过
     * @测试描述：业务组件报错：CommonDataFactory.getInstance().updateAttrFieldValue(Order_id,name, value);
     **/
    public void BSSStatus(){
    	plan_id="105";
		rule_id="201410248766000059";//
	    TacheFact fact =new TacheFact();
		fact.setOrder_id(Order_id);
		fact.setOrder_model(model_zd);
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setPlan_id(plan_id);
		req.setRule_id(rule_id);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
    }
    
    
    /**
     * 取值报错
     */
    public void getValue(){
    	try {
    		System.out.println("in getVlaue");
    		//取值报错
    		OrderItemProdExtBusiRequest orderItemProdBusi = CommonDataFactory.getInstance()
    				.getOrderItemProdBusi(Order_id, EcsOrderConsts.PRODUCT_TYPE_NUMBER)
    				.getOrderItemProdExtBusiRequest();
    		
    		///////////////////////bbs更新报错
    		String [] name={AttrConsts.BSS_STATUS};
			String [] value={EcsOrderConsts.BSS_STATUS_0};
    		CommonDataFactory.getInstance().updateAttrFieldValue(Order_id,name, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
