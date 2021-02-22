package test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.remote.params.req.SmsTempleteRequest;
import com.ztesoft.remote.params.resp.SmsTempleteResponse;

import consts.ConstsCore;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.bss.req.BSSActivateOperReq;
import zte.net.ecsord.params.bss.req.CustomerCheckReq;
import zte.net.ecsord.params.bss.req.LocalGoodsStatusSynchronizationReq;
import zte.net.ecsord.params.bss.req.PreCommitReq;
import zte.net.ecsord.params.bss.resp.BSSActivateOperRsp;
import zte.net.ecsord.params.bss.resp.CustomerCheckResp;
import zte.net.ecsord.params.bss.resp.LocalGoodsStatusSynchronizationResp;
import zte.net.ecsord.params.bss.resp.PreCommitResp;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.req.BareMachineSaleReq;
import zte.net.ecsord.params.ecaop.req.CannelOrder23to4Request;
import zte.net.ecsord.params.ecaop.req.CardDataQryRequest;
import zte.net.ecsord.params.ecaop.req.CardDataSynRequest;
import zte.net.ecsord.params.ecaop.req.ChangeMachineSubReq;
import zte.net.ecsord.params.ecaop.req.Check23to4Request;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFee4GReq;
import zte.net.ecsord.params.ecaop.req.CunFeeSongFee4GSubmitReq;
import zte.net.ecsord.params.ecaop.req.CustomerInfoCheckReq;
import zte.net.ecsord.params.ecaop.req.DevelopPersonQueryReq;
import zte.net.ecsord.params.ecaop.req.EmployeesInfoCheckRequest;
import zte.net.ecsord.params.ecaop.req.OldUserBuyApplyReq;
import zte.net.ecsord.params.ecaop.req.OldUserBuySubmitReq;
import zte.net.ecsord.params.ecaop.req.OldUserCheck3GReq;
import zte.net.ecsord.params.ecaop.req.OldUserRenActivityReq;
import zte.net.ecsord.params.ecaop.req.OldUserRenActivitySubmitReq;
import zte.net.ecsord.params.ecaop.req.Open23to4ApplyReq;
import zte.net.ecsord.params.ecaop.req.OpenDealApplyReq;
import zte.net.ecsord.params.ecaop.req.OpenDealSubmitReq;
import zte.net.ecsord.params.ecaop.req.OrderActiveReq;
import zte.net.ecsord.params.ecaop.req.OrderQueryReq;
import zte.net.ecsord.params.ecaop.req.OrderReverseSalesReq;
import zte.net.ecsord.params.ecaop.req.ProdChangeApplyReq;
import zte.net.ecsord.params.ecaop.req.ProdChangeCannelRequest;
import zte.net.ecsord.params.ecaop.req.ReturnMachineApplyReq;
import zte.net.ecsord.params.ecaop.req.TerminalStatusQueryChanageReq;
import zte.net.ecsord.params.ecaop.req.TrafficOrderRequest;
import zte.net.ecsord.params.ecaop.req.UserInfoCheck3BackReq;
import zte.net.ecsord.params.ecaop.req.WriteCardResultNoticeReq;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;
import zte.net.ecsord.params.ecaop.resp.BareMachineSaleResp;
import zte.net.ecsord.params.ecaop.resp.CannelOrder23to4Resp;
import zte.net.ecsord.params.ecaop.resp.CardDataQryResponse;
import zte.net.ecsord.params.ecaop.resp.CardDataSynResponse;
import zte.net.ecsord.params.ecaop.resp.ChangeMachineSubResp;
import zte.net.ecsord.params.ecaop.resp.Check23to4Resp;
import zte.net.ecsord.params.ecaop.resp.CunFeeSongFee4GResp;
import zte.net.ecsord.params.ecaop.resp.CunFeeSongFee4GSubmitResp;
import zte.net.ecsord.params.ecaop.resp.CustomerInfoResponse;
import zte.net.ecsord.params.ecaop.resp.DevelopPersonResponse;
import zte.net.ecsord.params.ecaop.resp.EmployeesInfoResponse;
import zte.net.ecsord.params.ecaop.resp.OldUserBuyApplyResp;
import zte.net.ecsord.params.ecaop.resp.OldUserBuySubmitResp;
import zte.net.ecsord.params.ecaop.resp.OldUserCheck3GResp;
import zte.net.ecsord.params.ecaop.resp.OldUserRenActivityResp;
import zte.net.ecsord.params.ecaop.resp.OldUserRenActivitySubmitResp;
import zte.net.ecsord.params.ecaop.resp.OpenDealApplyResp;
import zte.net.ecsord.params.ecaop.resp.OpenDealSubmitResp;
import zte.net.ecsord.params.ecaop.resp.OrderActiveResp;
import zte.net.ecsord.params.ecaop.resp.OrderReverseSalesResp;
import zte.net.ecsord.params.ecaop.resp.ProdChangeApplyResp;
import zte.net.ecsord.params.ecaop.resp.ProdChangeCannelResp;
import zte.net.ecsord.params.ecaop.resp.ReturnMachineApplyResp;
import zte.net.ecsord.params.ecaop.resp.TerminalStatusQueryChanageResp;
import zte.net.ecsord.params.ecaop.resp.TrafficOrderResponse;
import zte.net.ecsord.params.ecaop.resp.UserInfoCheck3BackResp;
import zte.net.ecsord.params.ecaop.resp.WriteCardResultNoticeResp;
import zte.net.ecsord.params.zb.req.NumberStateChangeZBRequest;
import zte.net.ecsord.params.zb.resp.NumberStateChangeZBResponse;
import zte.net.ecsord.params.zb.vo.ResourcesInfo;

/**
 * @Description aop接口连通测试
 * @author  zhangJun
 * @date    2016年11月4日
 */
@SuppressWarnings("all")
public class TestAipAop{
	private static String order_id="";
	ZteClient  client =null;
	
	
	public  void test() {
		client= ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		order_id="ZBWO163361702307000025";
		//一阶段
		//this.test1();//通过 -查询
		//this.test2();//通过 -查询
		//this.test3();//通过-查询
		//this.test4();//通过
		//this.test41();//通过
		//this.test5();//通过
		//this.test6();//通过
		//this.test7();//通过
		//this.test8();//没完成报错  -查询
		//this.test9();//通过
		//this.test10();//通过
		
		//四阶段
		//this.test11();//通过
		//this.test12();//该接口暂时不支持
		//this.test13();//通过
		
		//二阶段
		//this.test14();//通过
		//this.test15();//通过
		//this.test16();//通过
		//this.test17();//少字段  之前都没有获取数据
		//this.test18();//通过
		//this.test19();//没这个接口
		//this.test20();//通过
		//this.test21();//通过
		//this.test22();//通过
		
		//1阶段
		//this.test23();//通过
		//this.test24();//通过
		
		//四阶段
		//this.test25();//通过
		
		//二阶段
		//this.test26();//通过
		//this.test27();//通过
		//this.test28();//通过
		//this.test29();//通过
		
		//this.test30();//先不管
		//this.test31();//先不管
		//this.test32();
		//this.test33();
		//this.test34();

		//this.test35();
		
		//this.sms();
	}

	
	/**
	 * 工号查询---完成
	 */
	private  void test1(){//
		List<String> list=new ArrayList<String>();
		list.add("AEDQ0033");
		list.add("KEDQ0033");
		list.add("BEDQ0033");
		list.add("GEDQ0033");
		list.add("IEDQ0033");
		list.add("EEDQ0033");
		list.add("FEDQ0033");
		list.add("DEdq0033");
		list.add("HEDQ0033");
		list.add("CEDQ0033");
		list.add("JEDQ0033");
        for (String string : list) {

		String essID=string;//测试库工号：AQGSY522  生产库工号：AEDQ0033
		EmployeesInfoCheckRequest req=new EmployeesInfoCheckRequest();
		req.setOperatorId(essID);
		req.setNotNeedReqStrOrderId(order_id);
		EmployeesInfoResponse rsp = client.execute(req, EmployeesInfoResponse.class);//调用接口 
		//System.out.println("工号查询");
		String sql="insert into Es_OPERATOR_REL_EXT (ORDER_GONGHAO, ESS_EMP_ID, PROVINCE, CITY, DISTRICT, DEP_ID, DEP_NAME, CHANNEL_ID, CHANNEL_TYPE, CHANNEL_NAME, SOURCE_FROM, STAFFNUMBER, STAFFSEX, STAFFPSPTTYPE, STAFFPSPTID, STAFFNAME)";
		sql=sql+" values ('我们的工号','"+rsp.getOperatorId()+"','"+rsp.getProvince()+"','"+rsp.getCity()+"','"+rsp.getDistrict()+"','"+rsp.getDepartId()+"','"+rsp.getDepartName()+"','"+rsp.getChannelId()+"','"+rsp.getChannelType()+"','"
				+rsp.getChannelName()+"','ECS','"+rsp.getStaffInfo().getStaffNumber()+"','"+rsp.getStaffInfo().getStaffSex()+"','"+rsp.getStaffInfo().getStaffPsptType()+"','"+rsp.getStaffInfo().getStaffPsptId()+"','"+rsp.getStaffInfo().getStaffName()+"')";
	
		System.out.println(sql);
        }
	}
	/**
	 * 发展人查询--完成
	 */
	private  void test2(){
		DevelopPersonQueryReq req=new DevelopPersonQueryReq ();
		req.setNotNeedReqStrOrderId(order_id);
		DevelopPersonResponse rsp = client.execute(req, DevelopPersonResponse.class);//调用接口 
		System.out.println("发展人查询");
	}
	
	/**
	 * 客户资料校验   -完成
	 */
	private  void test3(){
		CustomerInfoCheckReq req=new CustomerInfoCheckReq();		
		req.setNotNeedReqStrOrderId(order_id);	
		CustomerInfoResponse rsp=client.execute(req, CustomerInfoResponse.class);
		System.out.println("客户资料校验");
	}
	/**
	 * 号码变更简单版-完成
	 */
	private  void test4(){//预占
		String proKey="20161124162011000025";
		String resourceCode="17506520088";//手机号码55
		String occupiedFlag="1";
		String changeTag="0";
		String old_pro_key="";
		String old_phone_num="";
		String  occupiedTime="";
		String keyChangeTag="0";
		String proKeyMode="0";
		String resourcesType="02";//资源类型:01 预付费号码；02 后付费号码
		String snChangeTag="0";//0:不修改；1：修改   写死 ，不修改
		/*try {
			occupiedTime=DateUtil.getTime5();
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		NumberStateChangeZBRequest req=new NumberStateChangeZBRequest();
	    req.setNotNeedReqStrOrderId(order_id);//订单外部单号   
	    List<ResourcesInfo> list = new ArrayList<ResourcesInfo>();
	    ResourcesInfo resourcesInfo = new ResourcesInfo();
	    
	    resourcesInfo.setProKey(proKey);
	    resourcesInfo.setResourcesCode(resourceCode);
	    resourcesInfo.setOccupiedFlag(occupiedFlag);//0 不预占；1 预占；2 预订（未付费）；3 预订（付费）；4 释放资源【一键开户的才用此接口】
	    resourcesInfo.setSnChangeTag(changeTag);//0：号码不变更；1：号码变更
	    resourcesInfo.setOldKey(old_pro_key);//原资源预占关键字(修改时必传)
	    resourcesInfo.setOldResourcesCode(old_phone_num);//原资源唯一标识（ SnChangeTag=1生效）
	    resourcesInfo.setCertNum("");
	   
	    resourcesInfo.setKeyChangeTag(keyChangeTag);
	    resourcesInfo.setProKeyMode(proKeyMode);
	    
	    list.add(resourcesInfo);
	    req.setResourcesInfo(list);
	    NumberStateChangeZBResponse resp =  client.execute(req,NumberStateChangeZBResponse.class);
	    System.out.println("dd");
	}
	private  void test41(){//预订
		String proKey="20161124162011000025";
		String resourceCode="17506520088";//手机号码55
		String occupiedFlag="2";
		String changeTag="0";
		String old_pro_key="";
		String old_phone_num="";
		String  occupiedTime="";
		String keyChangeTag="0";
		String proKeyMode="0";
		String resourcesType="02";//资源类型:01 预付费号码；02 后付费号码
		String snChangeTag="0";//0:不修改；1：修改   写死 ，不修改
		String  certNum="37152219850914053X";
		/*try {
			occupiedTime=DateUtil.getTime5();
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		NumberStateChangeZBRequest req=new NumberStateChangeZBRequest();
	    req.setNotNeedReqStrOrderId(order_id);//订单外部单号   
	    List<ResourcesInfo> list = new ArrayList<ResourcesInfo>();
	    ResourcesInfo resourcesInfo = new ResourcesInfo();
	    
	    resourcesInfo.setProKey(proKey);
	    resourcesInfo.setResourcesCode(resourceCode);
	    resourcesInfo.setOccupiedFlag(occupiedFlag);//0 不预占；1 预占；2 预订（未付费）；3 预订（付费）；4 释放资源【一键开户的才用此接口】
	    resourcesInfo.setSnChangeTag(changeTag);//0：号码不变更；1：号码变更
	    resourcesInfo.setOldKey(old_pro_key);//原资源预占关键字(修改时必传)
	    resourcesInfo.setOldResourcesCode(old_phone_num);//原资源唯一标识（ SnChangeTag=1生效）
	    resourcesInfo.setCertNum("");
	   
	    resourcesInfo.setKeyChangeTag(keyChangeTag);
	    resourcesInfo.setProKeyMode(proKeyMode);
	    resourcesInfo.setCertNum(certNum);
	    
	    list.add(resourcesInfo);
	    req.setResourcesInfo(list);
	    NumberStateChangeZBResponse resp =  client.execute(req,NumberStateChangeZBResponse.class);
	    System.out.println("dd");
	}
	/**
	 * 终端状态变更-完成
	 */
	private  void test5(){
		String occupiedFlag="0";//操作标识：0 不预占1 预占2 预订3 释放4 故障改空闲

		TerminalStatusQueryChanageReq req = new TerminalStatusQueryChanageReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOperFlag(occupiedFlag);

		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrTmResourceInfoBusiRequest> oldlist = orderTree.getTmResourceInfoBusiRequests();
		String orderMode = orderTree.getOrderExtBusiRequest().getOrder_model();
		
		TerminalStatusQueryChanageResp infResp = client.execute(req,TerminalStatusQueryChanageResp.class);
		System.out.println("终端状态变更");
	}
	/**
	 * 开户申请---完成
	 */
	private  void test6(){
		OpenDealApplyReq openActReq = new OpenDealApplyReq();	
		openActReq.setNotNeedReqStrOrderId(order_id);
		//接口调用开户申请
		OpenDealApplyResp rsp=client.execute(openActReq, OpenDealApplyResp.class);
		System.out.println("开户申请");
	}
	
	/**
	 * 开户提交-完成
	 */
	private  void test7(){
		OpenDealSubmitReq req = new OpenDealSubmitReq();
		req.setNotNeedReqStrOrderId(order_id);
		OpenDealSubmitResp rsp = client.execute(req, OpenDealSubmitResp.class);
		System.out.println("开户提交");
		
	}
	
	/**
	 * 卡数据查询-没完成报错
	 * 
	 */
	private  void test8(){
		CardDataQryRequest req = new CardDataQryRequest();
		req.setNotNeedReqStrOrderId(order_id);
		CardDataQryResponse rsp = client.execute(req, CardDataQryResponse.class);
		System.out.println("卡数据查询");
	}
	
	/**
	 * 写卡结果通知-完成
	 */
	private  void test9(){
		WriteCardResultNoticeReq req = new WriteCardResultNoticeReq();
		req.setNotNeedReqStrOrderId(order_id);
		WriteCardResultNoticeResp infResp = client.execute(req, WriteCardResultNoticeResp.class);//调用接口
		System.out.println("写卡结果通知");
	}
	
	/**
	 * 卡数据同步-完成
	 */
	private  void test10(){
		CardDataSynRequest req = new CardDataSynRequest();
		req.setNotNeedReqStrOrderId(order_id);
		CardDataSynResponse rsp = client.execute(req, CardDataSynResponse.class);
		System.out.println("卡数据同步");
	}
	
	/**
	 * 订单返销
	 */
	private  void test11(){
		OrderReverseSalesReq req=new OrderReverseSalesReq ();
		req.setNotNeedReqStrOrderId(order_id);
		OrderReverseSalesResp rsp = client.execute(req, OrderReverseSalesResp.class);//调用接口 
	}
	/**
	 * 退机申请
	 */
	private  void test12(){
		ReturnMachineApplyReq req=new ReturnMachineApplyReq ();
		req.setNotNeedReqStrOrderId(order_id);
		ReturnMachineApplyResp rsp = client.execute(req, ReturnMachineApplyResp.class);//调用接口 
	}
	/**
	 * 退机提交
	 */
	private  void test13(){
		ChangeMachineSubReq req=new ChangeMachineSubReq ();
		req.setNotNeedReqStrOrderId(order_id);
		ChangeMachineSubResp rsp = client.execute(req, ChangeMachineSubResp.class);//调用接口 
	}
	/**
	 * 23转4校验
	 */
	private  void test14(){
		Check23to4Request req = new Check23to4Request ();
		req.setNotNeedReqStrOrderId(order_id);
		Check23to4Resp rsp = client.execute(req, Check23to4Resp.class);//调用接口 
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("23转4校验");
	}
	/**
	 * 23转4申请
	 */
	private  void test15(){
		Open23to4ApplyReq openActReq = new Open23to4ApplyReq();	
		openActReq.setNotNeedReqStrOrderId(order_id);
		//接口调用开户申请
		OpenDealApplyResp infResp=client.execute(openActReq, OpenDealApplyResp.class);
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("23转4申请");
	}
	/**
	 * 23转4撤单
	 */
	private  void test16(){
		CannelOrder23to4Request req=new CannelOrder23to4Request ();
		req.setNotNeedReqStrOrderId(order_id);
		CannelOrder23to4Resp rsp = client.execute(req, CannelOrder23to4Resp.class);//调用接口 
		System.out.println("23转4撤单");
	}
	/**
	 * 流量包订购
	 */
	private  void test17(){
		TrafficOrderRequest req=new TrafficOrderRequest ();
		req.setNotNeedReqStrOrderId(order_id);
		TrafficOrderResponse rsp = client.execute(req, TrafficOrderResponse.class);//调用接口 
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("流量包订购");
	}

	/**
	 * 套餐变更申请
	 */
    private  void test18(){
    	ProdChangeApplyReq req = new ProdChangeApplyReq();
		req.setNotNeedReqStrOrderId(order_id);
		ProdChangeApplyResp resp = client.execute(req, ProdChangeApplyResp.class);	
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("流套餐变更申请");
	}
    /**
     * 套餐变更撤单
     */
    private  void test19(){
    	ProdChangeCannelRequest req=new ProdChangeCannelRequest ();
		req.setNotNeedReqStrOrderId(order_id);
		ProdChangeCannelResp rsp = client.execute(req, ProdChangeCannelResp.class);//调用接口 
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("套餐变更撤单");
	}
    /**
     * 3G老用户校验
     */
    private  void test20(){
    	OldUserCheck3GReq req = new OldUserCheck3GReq();
		req.setNotNeedReqStrOrderId(order_id);
		OldUserCheck3GResp resp = client.execute(req, OldUserCheck3GResp.class);//调用接口 
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("3G老用户校验");
	}
    /**
     * 老用户优惠购机申请
     */
    private  void test21(){
    	OldUserBuyApplyReq req = new OldUserBuyApplyReq();
		req.setNotNeedReqStrOrderId(order_id);
		OldUserBuyApplyResp resp = client.execute(req, OldUserBuyApplyResp.class);	
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("老用户优惠购机申请");
	}
    /**
     * 老用户优惠购机提交
     */
    private  void test22(){
    	OldUserBuySubmitReq req = new OldUserBuySubmitReq();
		req.setNotNeedReqStrOrderId(order_id);
		OldUserBuySubmitResp resp = client.execute(req, OldUserBuySubmitResp.class);
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("老用户优惠购机提交");
	}
    /**
     * 三户查询
     */
    private  void test23(){
    	UserInfoCheck3BackReq req = new UserInfoCheck3BackReq();
		req.setNotNeedReqStrOrderId(order_id);
		UserInfoCheck3BackResp resp = client.execute(req, UserInfoCheck3BackResp.class);
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println(" 三户查询");
	}
    /**
     * 订单查询
     */
    private  void test24(){
    	
    	OrderQueryReq req=new OrderQueryReq();
		req.setNotNeedReqStrOrderId(order_id);
		//OrderQueryRespone rsp = client.execute(req, OrderQueryRespone.class);//调用接口 
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		OrderTreeBusiRequest t=CommonDataFactory.getInstance().getOrderTree(order_id);
		System.out.println(" 订单查询");
	}
    /**
     * 裸机销售
     */
    private  void test25(){
    	BareMachineSaleReq req = new BareMachineSaleReq();
		req.setNotNeedReqStrOrderId(order_id);
		BareMachineSaleResp rsp = client.execute(req, BareMachineSaleResp.class);
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println(" 裸机销售");
	}
    /**
     * 4G存费送费
     */
    private  void test26(){
    	CunFeeSongFee4GReq req = new CunFeeSongFee4GReq();
		req.setNotNeedReqStrOrderId(order_id);
		CunFeeSongFee4GResp resp = client.execute(req, CunFeeSongFee4GResp.class);
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("4G存费送费");
	}
    /**
     * 4G存费送费正式提交
     */
    private  void test27(){
    	CunFeeSongFee4GSubmitReq req = new CunFeeSongFee4GSubmitReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		CunFeeSongFee4GSubmitResp rsp = client.execute(req, CunFeeSongFee4GSubmitResp.class);
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("4G存费送费正式提交");
	}
    
    /**
     * 老用户续约申请
     */
    private  void test28(){
    	OldUserRenActivityReq req = new OldUserRenActivityReq();
		req.setNotNeedReqStrOrderId(order_id);
		OldUserRenActivityResp resp = client.execute(req, OldUserRenActivityResp.class);	
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("老用户续约申请");
	}
    /**
     * 老用户续约提交
     */
    private  void test29(){
    	OldUserRenActivitySubmitReq req = new OldUserRenActivitySubmitReq();
		req.setNotNeedReqStrOrderId(order_id);
		OldUserRenActivitySubmitResp resp = client.execute(req, OldUserRenActivitySubmitResp.class);
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("老用户续约提交");
	}
    /**
     * CBSS订单激活  ecaop.trades.sell.mob.cbss.order.act
     */
    private  void test30(){
    	OrderActiveReq  req = new OrderActiveReq();
		req.setNotNeedReqStrOrderId(order_id);
		OrderActiveResp resp = client.execute(req, OrderActiveResp.class);
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("CBSS订单激活");
	}
    
    /**
     * CBSS压单订单撤单
     */
    private  void test31(){
    	BSSActivateOperReq   req = new BSSActivateOperReq();
		req.setNotNeedReqStrOrderId(order_id);
		BSSActivateOperRsp resp = client.execute(req, BSSActivateOperRsp.class);
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		System.out.println("CBSS压单订单撤单");
	}
    /**
     * 发送短信
     */
    private  void test32(){
    	String num="18319969062";
    	String valid_code="666666";
    	AopSmsSendReq smsSendReq = new AopSmsSendReq();
		smsSendReq.setService_num(num);
		smsSendReq.setSms_data(valid_code);
		smsSendReq.setBill_num(null);
		AopSmsSendResp smsSendResp = client.execute(smsSendReq, AopSmsSendResp.class);
	}
   
    /**
     * 订单预提交
     */
    private  void test33(){
    	
    	PreCommitReq req = new PreCommitReq();
		req.setNotNeedReqStrOrderId(order_id);
		PreCommitResp resp = client.execute(req, PreCommitResp.class);
	}
    
    /**
     * 资料校验
     */
    private  void test34(){
    	
    	CustomerCheckReq req = new CustomerCheckReq();
		req.setNotNeedReqStrOrderId(order_id);
		CustomerCheckResp resp = client.execute(req, CustomerCheckResp.class);
	}
    
    /**
     * 本地商城状态同步
     */
    private  void test35(){
    	
    	LocalGoodsStatusSynchronizationReq req = new LocalGoodsStatusSynchronizationReq();
		req.setNotNeedReqStrOrderId(order_id);
		LocalGoodsStatusSynchronizationResp resp = client.execute(req, LocalGoodsStatusSynchronizationResp.class);
	}
    
    private  void sms(){
    	//发送短信aip
    	ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
    	String receive_phonenums=cacheUtil.getConfigInfo("SMS_RECEIVE_PHONENUMS");//接收号码，配置在数据库表es_config_info
		if(!StringUtils.isEmpty(receive_phonenums)){
			String[] receive_phonenums_array=receive_phonenums.split(",");
			for (String receive_phonenum : receive_phonenums_array) {
				SmsTempleteRequest smsTemp = new SmsTempleteRequest();
				smsTemp.setCode("LOGIN_OUT_ZS");//短信模板，LOGIN_OUT_ZS  ,配置在表es_sms_templete
				smsTemp.setAccNbr(receive_phonenum);//接收号码
				SmsTempleteResponse smsTempResp = client.execute(smsTemp, SmsTempleteResponse.class);
				if(smsTempResp!=null&&ConstsCore.ERROR_SUCC.equals(smsTempResp.getError_code())){
					System.out.println("成功！");
				}else{
					System.out.println("失败！");
				}
			}
		}
    	
		
	}
	public static String getOrder_id() {
		return order_id;
	}


	public static void setOrder_id(String order_id) {
		TestAipAop.order_id = order_id;
	}
	
	
	public static void main(String[] args) {
		try {
			String [] orderIds={"2457685144","3735501803"};//外部单号
			for (String orderId : orderIds) {
				String orderSource = "10003";	
				String key = new StringBuffer().append(orderSource).append(orderId).toString();
				
				key = "5000_"+MD5Util.MD5(key);
				System.out.println(orderId  +"   "+key);
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}