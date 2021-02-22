package zte.net.common.rule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.wyg.req.NotifyOrderInfoWYGRequset;
import zte.net.ecsord.params.wyg.resp.NotifyOrderInfoWYGResponse;
import zte.net.ecsord.rule.tache.TacheFact;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.ICommonManager;
import com.ztesoft.net.service.IOrdArchiveTacheManager;

import commons.CommonTools;
import consts.ConstsCore;

/**
 *
 * 资料归档
 * @author wu.i
 */
@ZteServiceAnnontion(trace_name="ZteOrderArchiveTraceRule",trace_id="1",version="1.0",desc="资料归档环节业务通知组件")
public  class ZteOrderArchiveTraceRule extends ZteTraceBaseRule {
	private static Logger logger = Logger.getLogger(ZteOrderArchiveTraceRule.class);
	@Resource
	private IOrdArchiveTacheManager ordArchiveTacheManager;
	@Resource
	private ICommonManager commonManager;
	/**
	 *资料归档环节入口
	 */
	@Override
	@ZteMethodAnnontion(name="资料归档环节入口",group_name="资料归档",order="1",page_show=true,path="ZteOrderArchiveTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {
		String orderId = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
		OrderBusiRequest orderBusiRequest = orderTree.getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_91);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 资料归档入口(环节跳转)
	 */
	@ZteMethodAnnontion(name="资料归档(资料归档)",group_name="资料归档",order="2",page_show=true,path="ZteOrderArchiveTraceRule.fileArchive")
	public BusiCompResponse fileArchive(BusiCompRequest busiCompRequest){
		this.init();
		String orderId = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
		OrderBusiRequest orderBusiRequest = orderTree.getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_92);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();
		//设置回单时间
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String backTime = df.format(new Date());
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setBack_time(backTime);
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		CommonDataFactory.getInstance().updateAttrFieldValue(orderId, new String[]{AttrConsts.BACK_TIME}, new String[]{backTime});
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 *资料归档环节入口
	 */
	@ZteMethodAnnontion(name="数据归档时间获取",group_name="资料归档",order="2",page_show=true,path="ZteOrderArchiveTraceRule.ordArchiveTime")
	public BusiCompResponse ordArchiveTime(BusiCompRequest busiCompRequest) {
		//OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(getOrderId(busiCompRequest));
		//获取自定义归档天数---zengxianlian
		String order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		
		String archive_time = "";
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		
		if("1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())){
			Date now = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			archive_time = df.format(now);
		}else{
			Map params = busiCompRequest.getQueryParams();
			TacheFact fact = (TacheFact) params.get("fact");
			archive_time = fact.getCalc_input();
		}
		
		orderExtBusiRequest.setArchive_time(archive_time);
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.ARCHIVE_TIME}, new String[]{archive_time});
		
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		//CommonTools.addFailError("执行失败AAA");
		return resp;
	}
	
	@ZteMethodAnnontion(name = "数据归档校验", group_name = "通用组件", order = "3", page_show = true, path = "ZteOrderArchiveTraceRule.ordArchiveVali")
	public BusiCompResponse ordArchiveVali(BusiCompRequest busiCompRequest) throws ApiBusiException {
		/*********************************************************
		 ** 注意：该方法中用于判断归档条件有属性，不从缓存中取，因为1.0、2.0缓存不一样
		 *********************************************************/
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		Map<String, String> conditionMap = ordArchiveTacheManager.getArchiveCondition(order_id);
		if (conditionMap != null) {
			String lastDealTime = conditionMap.get(AttrConsts.BACK_TIME);
			String archiveTimeTxt = conditionMap.get(AttrConsts.ARCHIVE_TIME);
			String existsBusinessToDealWith = conditionMap.get("exists_business_to_deal_with");//sql定义别名，非属性、非字段名
			
			int archiveTime = 0;
			if(null != archiveTimeTxt && !"".equals(archiveTimeTxt)){
				archiveTime = Integer.parseInt(archiveTimeTxt);
			}
			if(!"".equals(lastDealTime)){
				Date lastDealDate = DateUtil.parseAsDate(lastDealTime);
				Date archeiveDate = DateUtil.addDay(lastDealDate, archiveTime);
				if(archeiveDate.getTime()>new Date().getTime()){
					CommonTools.addBusiError("-99999", "需要在回单"+archiveTime+"天以后再归档");
				}
			}
			//暂时保留eoie.bss_status BSS业务办理状态 的判断,已保证历史数据的归档校验。
			//待历史数据归档完毕,再去除eoie.bss_status BSS业务办理状态 的判断
			//仕鹏确认去掉旧的业务办理状态校验条件
//			if(EcsOrderConsts.BSS_STATUS_0.equals(bssStatus)){
//				CommonTools.addBusiError("-99999", "BSS业务办理未成功");
//			}
			if(EcsOrderConsts.EXISTS_BUSINESS_TO_DEAL_WITH_1.equals(existsBusinessToDealWith)){
				CommonTools.addBusiError("-99999", "尚有业务未办理成功");
			}
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 订单数据归档
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="订单数据归档(资料归档)",group_name="资料归档",order="4",page_show=true,path="ZteOrderArchiveTraceRule.orderDataArchive")
	public BusiCompResponse orderDataArchive(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		try {
			ordArchiveTacheManager.ordLogisArchive(order_id);
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			CommonTools.addBusiError("-99999","归档失败:"+e.getMessage());
		}
		return resp;
		
	}
	/**
	 * 退库存
	 */
	@ZteMethodAnnontion(name="退库存(资料归档)",group_name="资料归档",order="5",page_show=true,path="ZteOrderArchiveTraceRule.removeRepertory")
	public BusiCompResponse removeRepertory(BusiCompRequest busiCompRequest){
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	/**
	 * 归档完成状态通知新商城
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="归档完成状态通知新商城(资料归档)",group_name="资料归档",order="6",page_show=true,path="ZteOrderArchiveTraceRule.orderArchiveCompleteSynToMall")
	public BusiCompResponse orderArchiveCompleteSynToMall(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		NotifyOrderInfoWYGRequset req = new NotifyOrderInfoWYGRequset();
		req.setTrace_code(EcsOrderConsts.NEW_SHOP_ORDER_STATE_13);		//正常归档
		req.setNotNeedReqStrOrderId(order_id);
		NotifyOrderInfoWYGResponse infResp = client.execute(req, NotifyOrderInfoWYGResponse.class);
		if(!EcsOrderConsts.NEW_SHOP_INF_SUCC_CODE.equals(infResp.getResp_code())){
			CommonTools.addBusiError(infResp.getResp_code(),infResp.getResp_msg());
		}
		resp.setResponse(infResp);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 *资料归档环节入口(设置回单时间)
	 */
	@ZteMethodAnnontion(name="资料归档环节入口(设置回单时间)",group_name="资料归档",order="8",page_show=true,path="ZteorderArchiveTraceRule.exec")
	public BusiCompResponse engineDoNew(BusiCompRequest busiCompRequest) {
		//OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(getOrderId(busiCompRequest));
		BusiCompResponse resp = new BusiCompResponse();
		//记录回单时间---zengxianlian	--20160830从回单下一步移植到归档入口
	    String order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String backTime = df.format(new Date());
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setBack_time(backTime);
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.BACK_TIME}, new String[]{backTime});
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		//CommonTools.addFailError("执行失败AAA");
		return resp;
	}
	
	/**
	 * 结束流程
	 */
	@ZteMethodAnnontion(name="结束流程(资料归档)",group_name="资料归档",order="4",page_show=true,path="ZteOrderBackTraceRule.flowFinish")
	public BusiCompResponse flowFinish(BusiCompRequest busiCompRequest){
		BusiCompResponse resp = this.nextflow(busiCompRequest,true,"J");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		long start = System.currentTimeMillis();
		logger.info(busiCompRequest.getQueryParams().get("start"));
		logger.info("==============end========="+start);
		return resp;
		
	}
	@ZteMethodAnnontion(name="[总商爬虫同步]订单数据归档",group_name="资料归档",order="4",page_show=true,path="ZteOrderArchiveTraceRule.zbOrderDataArchive")
	public BusiCompResponse zbOrderDataArchive(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		try {
			ordArchiveTacheManager.ordLogisArchive(order_id);
			ordArchiveTacheManager.zbOrderDataArchive(order_id);
			
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			CommonTools.addBusiError("-99999","归档失败:"+e.getMessage());
		}
		return resp;
		
	}
}
