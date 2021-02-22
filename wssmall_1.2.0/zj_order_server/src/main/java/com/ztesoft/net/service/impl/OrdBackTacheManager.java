package com.ztesoft.net.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteRequest;
import params.ZteResponse;
import params.req.ZbOrderAuditStatusReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderFileBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.nd.req.OrderResultNotifyReq;
import zte.net.ecsord.params.nd.resp.OrderResultNotifyRsp;
import zte.net.ecsord.params.nd.resp.WechatRetBean;
import zte.net.ecsord.params.zb.req.StateSynchronizationToSystemRequest;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AttrUtils;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.FileUtil;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderPhotoInfoReq;
import com.ztesoft.net.ecsord.params.ecaop.vo.PhotoInfoVO;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AopQueryDataVo;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdBackTacheManager;

import consts.ConstsCore;

/**
 * 回单环节处理类
 * 
 * @author xuefeng
 */
public class OrdBackTacheManager extends BaseSupport implements IOrdBackTacheManager {
	static INetCache cache;
	static {
		cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	@Override
	@SuppressWarnings("unchecked")
	public BusiDealResult recReceiptFromZB(BusiCompRequest busiCompRequest) throws Exception {
		BusiDealResult result = new BusiDealResult();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");

		StateSynchronizationToSystemRequest req = (StateSynchronizationToSystemRequest) fact.getRequest();
		if (null == req) {
			params.put(EcsOrderConsts.TRACE_TRIGGER_TYPE, EcsOrderConsts.TRACE_TRIGGER_PAGE);
		} else {
			params.put(EcsOrderConsts.TRACE_TRIGGER_TYPE, EcsOrderConsts.TRACE_TRIGGER_INF);
		}
		// 调用订单回单完成业务组件
		busiCompRequest.setEnginePath("zteCommonTraceRule.recBackFinsh");
		busiCompRequest.setQueryParams(params);
		CommonDataFactory.getInstance().execBusiComp(busiCompRequest);
		return result;
	}

	@Override
	public void insertAopQuery(String order_id) {
		AopQueryDataVo aopquery = new AopQueryDataVo();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		aopquery.setId(this.baseDaoSupport.getSequences("ES_ORDER_AOP_QUERY_SEQ"));
		aopquery.setOrder_id(order_id);
		aopquery.setOut_tid(orderTree.getOrderExtBusiRequest().getOut_tid());
		aopquery.setBack_time(orderTree.getOrderExtBusiRequest().getBack_time());
		aopquery.setDeal_status(0);
		aopquery.setDeal_num(0);
		aopquery.setArchive_time(orderTree.getOrderExtBusiRequest().getArchive_time());
		this.baseDaoSupport.insert("ES_ORDER_AOP_QUERY", aopquery);
	}

	@Override
	public BusiDealResult noticeActiveStatusToZS(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZbOrderAuditStatusReq req = new ZbOrderAuditStatusReq();
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
		// 证件类型01:15位身份证,02:18位身份证
		String cert_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);
		cert_type = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_CERT_TYPE_RELATION, cert_type);
		req.setCertType(cert_type);
		String active_flag = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_FLAG);
		if (EcsOrderConsts.LATER_ACTIVE_STATUS_1.equals(active_flag)) {
			active_flag = EcsOrderConsts.NO_DEFAULT_VALUE;
		} else if (EcsOrderConsts.LATER_ACTIVE_STATUS_2.equals(active_flag)
				|| EcsOrderConsts.LATER_ACTIVE_STATUS_3.equals(active_flag)) {
			active_flag = EcsOrderConsts.IS_DEFAULT_VALUE;
		} else {
			active_flag = EcsOrderConsts.NO_DEFAULT_VALUE;
		}
		req.setIsAuditPass(active_flag);
		req.setContactsPhoneNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REVEIVER_MOBILE));
		req.setIsSendMessage("1");
		String active_fail_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
				AttrConsts.ACTIVE_FAIL_TYPE);
		active_fail_type = AttrUtils.getInstance().getOtherDictCodeValueDesc(StypeConsts.ACTIVE_FAIL_TYPE,
				active_fail_type);
		req.setSendMessage(active_fail_type);
		req.setIsOutboundCall("0");// 暂时默认未外呼

		ZteResponse resp = client.execute(req, ZteResponse.class);
		if (StringUtils.equals(resp.getError_code(), ConstsCore.ERROR_SUCC)) {
			result.setError_code(ConstsCore.ERROR_SUCC);
			result.setError_msg("执行成功");
		} else {
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息：" + resp.getError_msg());
			result.setError_code(resp.getError_code());
		}
		return result;
	}

	@Override
	public BusiDealResult receiveCodePurchaseAgrtImgs(BusiCompRequest busiCompRequest) {
		String orderId = busiCompRequest.getOrder_id();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		OrderPhotoInfoReq req = (OrderPhotoInfoReq) fact.getRequest();
		List<PhotoInfoVO> imgList = req.getPhoto_list();// 码上购协议照片list
		BusiDealResult result = new BusiDealResult();
		// 获取当前环节编码
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(orderId);
		if(null==tree){
			result.setError_code(ConstsCore.ERROR_FAIL);
			result.setError_msg("订单号没有对应订单");
			return result;
		}
		String tacheCode = tree.getOrderExtBusiRequest().getFlow_trace_id();

		// 码上购协议照片入库处理
		handleAgrtImgs(orderId, tacheCode, imgList);
		tree = CommonDataFactory.getInstance().getOrderTree(orderId);
		String if_send_photos = tree.getOrderExtBusiRequest().getIf_Send_Photos();
		String new_if_send_photos = "";
		if(!StringUtil.isEmpty(if_send_photos)&&"8".equals(if_send_photos)){
			new_if_send_photos = "2";
			
		}else{
			new_if_send_photos = "1";
		}
		tree.getOrderExtBusiRequest().setIf_Send_Photos(new_if_send_photos);
		String update_sql = " update es_order_ext set if_send_photos='"+new_if_send_photos+"' where order_id='"+orderId+"' " ;
		this.baseDaoSupport.execute(update_sql);
		cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY + "order_id" + orderId,
				tree, RequestStoreManager.time);
		
		result.setError_code(ConstsCore.ERROR_SUCC);
		result.setError_msg("执行成功");
		return result;
	}

	private void handleAgrtImgs(String orderId, String tacheCode, List<PhotoInfoVO> imgList) {
		// 上传协议照片到文件系统
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(orderId);
		List list = new ArrayList();
		for (int i = 0; i < imgList.size(); i++) {
			PhotoInfoVO vo = imgList.get(i);
			String action = vo.getAction();
			String photoId = vo.getPhoto_id();// 取图片序号
			String base64Str = vo.getPhoto_stream();
			String fileName = orderId + "_" + photoId + ".jpg";
			String suffix = "jpg";// 默认jpg格式
			if(!StringUtil.isEmpty(base64Str)){
				if (ZjEcsOrderConsts.AGRT_IMG_ACTION_A.equals(action)) {
					// 新增
					saveOrdAgrtImgNew(list,tree,orderId, tacheCode, fileName, suffix, base64Str);
					
				} else if (ZjEcsOrderConsts.AGRT_IMG_ACTION_M.equals(action)) {
					// 更新
					updateOrdAgrtImgNew(list,tree,orderId, tacheCode, fileName, suffix, base64Str);
				}
			}
			
		}
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				String sql = (String) list.get(i);
				this.baseDaoSupport.execute(sql);
			}
			cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY + "order_id" + orderId,
					tree, RequestStoreManager.time);
		}
		
	}

	/**
	 * 保存码上购协议照片
	 * 
	 * @param order_id
	 *            内部订单号
	 * @param tache_code
	 *            环节编码
	 * @param fileName
	 *            图片文件名
	 * @param suffix
	 *            图片扩展名
	 * @param base64Str
	 *            图片Base64字符串
	 */
	private void saveOrdAgrtImg(String orderId, String tacheCode, String fileName, String suffix, String base64Str) {
		InputStream is = FileUtil.getInst().base64StrToInputStream(base64Str);
		String path = FileUtil.getInst().uploadFileToFileSystem(is, fileName);

		OrderFileBusiRequest file = new OrderFileBusiRequest();
		file.setOrder_id(orderId);
		file.setFile_id(SequenceTools.getDefaultPrimaryKey());
		file.setFile_path(path);
		file.setFile_type(suffix);
		file.setFile_name(fileName);
		file.setCreate_time(Consts.SYSDATE);
		file.setOperator_id("1");// 默认超级管理员
		file.setStatus("1");// 正常
		file.setTache_code(tacheCode);// 订单归档环节
		file.setSource_from(ManagerUtils.getSourceFrom());
		file.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		file.setDb_action(ConstsCore.DB_ACTION_INSERT);
		file.store();
	}

	/**
	 * 更新码上购协议照片
	 * 
	 * @param order_id
	 *            内部订单号
	 * @param tache_code
	 *            环节编码
	 * @param fileName
	 *            图片文件名
	 * @param suffix
	 *            图片扩展名
	 * @param base64Str
	 *            图片Base64字符串
	 */
	private void updateOrdAgrtImg(String orderId, String tacheCode, String fileName, String suffix, String base64Str) {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(orderId);
		List<OrderFileBusiRequest> orderFileBusiRequests = tree.getOrderFileBusiRequests();
		//boolean is_exist = false;// 图片是否已存在，默认不存在
		if (orderFileBusiRequests != null && orderFileBusiRequests.size() > 0) {
			for (OrderFileBusiRequest file : orderFileBusiRequests) {
				if (fileName.equals(file.getFile_name())) {
					/*// 如果图片存在
					is_exist = true;
					// 删除旧图片
					String file_path = file.getFile_path();
					if (!StringUtils.isEmpty(file_path)) {
						FileUtil.getInst().deleteFileFromFileSystem(file_path);
					}
					// 上传新图片
					InputStream is = FileUtil.getInst().base64StrToInputStream(base64Str);
					String newPath = FileUtil.getInst().uploadFileToFileSystem(is, fileName);

					file.setFile_type(suffix);
					file.setFile_path(newPath);*/
					file.setStatus("2");
					file.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					file.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					file.store();
					break;
				}
			}
		}
		// 如果图片不存在，则新上传图片
		//if (!is_exist) {
			// 上传新图片
			this.saveOrdAgrtImg(orderId, tacheCode, fileName, suffix, base64Str);
		//}
	}

	/**
	 * 保存码上购协议照片
	 * 
	 * @param order_id
	 *            内部订单号
	 * @param tache_code
	 *            环节编码
	 * @param fileName
	 *            图片文件名
	 * @param suffix
	 *            图片扩展名
	 * @param base64Str
	 *            图片Base64字符串
	 */
	private void saveOrdAgrtImgNew(List list,OrderTreeBusiRequest tree,String orderId, String tacheCode, String fileName, String suffix, String base64Str) {
		InputStream is = FileUtil.getInst().base64StrToInputStream(base64Str);
		String path = FileUtil.getInst().uploadFileToFileSystem(is, fileName);

		OrderFileBusiRequest file = new OrderFileBusiRequest();
		String file_id=SequenceTools.getDefaultPrimaryKey();
		file.setOrder_id(orderId);
		file.setFile_id(file_id);
		file.setFile_path(path);
		file.setFile_type(suffix);
		file.setFile_name(fileName);
		file.setCreate_time(Consts.SYSDATE);
		file.setOperator_id("1");// 默认超级管理员
		file.setStatus("1");// 正常
		file.setTache_code(tacheCode);// 订单归档环节
		file.setSource_from(ManagerUtils.getSourceFrom());
		/*file.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		file.setDb_action(ConstsCore.DB_ACTION_INSERT);
		file.store();*/
		String sql = " insert into es_order_file (order_id,file_id,file_path,file_type,file_name,create_time,operator_id,status,tache_code,source_from) "
				   + " values ('"+orderId+"','"+file_id+"','"+path+"','"+suffix+"','"+fileName+"',sysdate,'1','1','"+tacheCode+"','ECS') ";
		list.add(sql);
		tree.getOrderFileBusiRequests().add(file);
	}
	
	/**
	 * 更新码上购协议照片
	 * 
	 * @param order_id
	 *            内部订单号
	 * @param tache_code
	 *            环节编码
	 * @param fileName
	 *            图片文件名
	 * @param suffix
	 *            图片扩展名
	 * @param base64Str
	 *            图片Base64字符串
	 */
	private void updateOrdAgrtImgNew(List list,OrderTreeBusiRequest tree, String orderId, String tacheCode, String fileName, String suffix, String base64Str) {
		//OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(orderId);
		List<OrderFileBusiRequest> orderFileBusiRequests = tree.getOrderFileBusiRequests();
		//boolean is_exist = false;// 图片是否已存在，默认不存在
		if (orderFileBusiRequests != null && orderFileBusiRequests.size() > 0) {
			for (OrderFileBusiRequest file : orderFileBusiRequests) {
				if (fileName.equals(file.getFile_name())&&"1".equals(file.getStatus())) {
					file.setStatus("2");
					String sql = " update es_order_file set status='2' where file_id='"+file.getFile_id()+"' ";
					list.add(sql);
				}
			}
		}
		// 如果图片不存在，则新上传图片
		//if (!is_exist) {
			// 上传新图片
			this.saveOrdAgrtImgNew(list,tree,orderId, tacheCode, fileName, suffix, base64Str);
		//}
	}
	
	/**
	 * 关闭工单、意向单
	 */
	@Override
	public BusiDealResult orderBack(String order_id) {
		BusiDealResult result = new BusiDealResult();
		try {
			String back_yxd_sql = " update es_order_intent s set s.status='03',s.intent_finish_time=sysdate where  order_id='" + order_id + "' ";
			String back_work_order_sql = "update es_work_order a set a.update_time = sysdate,status='1',result_remark='默认关闭' where a.status in('0','4') and a.order_id='"
					+ order_id + "' ";
			String save_work_id_sql = "select work_order_id from es_work_order where order_id='" + order_id + "'";

			this.baseDaoSupport.execute(back_yxd_sql);
			this.baseDaoSupport.execute(back_work_order_sql);

			List list_work_id = baseDaoSupport.queryForList(save_work_id_sql);
			if (list_work_id != null && list_work_id.size() != 0) {
				for (int i = 0; i < list_work_id.size(); i++) {
					Map map = (Map) list_work_id.get(i);
					String work_id = map.get("work_order_id").toString();
					String update_logs_sql = "update es_intent_handle_logs l set l.work_result_time =sysdate,l.work_result_remark='默认关闭' where work_order_id='"
							+ work_id + "'";
					this.baseDaoSupport.execute(update_logs_sql);
				}
			}
		} catch (Exception e) {
			result.setError_code(ConstsCore.ERROR_FAIL);
			result.setError_msg("执行失败：" + e.getMessage());
			return result;
		}
		result.setError_code(ConstsCore.ERROR_SUCC);
		result.setError_msg("执行成功");
		return result;
	}

	@Override
	public BusiDealResult orderResultNotify(String order_id) {
		BusiDealResult dealResult = new BusiDealResult();
		OrderResultNotifyReq req = new OrderResultNotifyReq();

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		String method = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "result_url");

		if (org.apache.commons.lang.StringUtils.isBlank(method)) {
			dealResult.setError_code("-9999");
			dealResult.setError_msg("未沉淀异步通知方法result_url");

			return dealResult;
		}

		// 设置参数
		req.setOrder_id(order_id);
		req.setOut_order_id(orderExt.getOut_tid());
		req.setResult_code("0");
		// 取异步通知接口的服务名
		req.setNotify_method(method);

		// 访问的方法ZteInfOpenService--doOrderResultNotify（wssmall.order.result.notify）
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderResultNotifyRsp rsp = client.execute(req, OrderResultNotifyRsp.class);

		// 返回信息处理
		if (rsp == null) {
			dealResult.setError_code("-9999");
			dealResult.setError_msg("调用接口异常:能力开放平台返回的信息为空");
		} else if (!"200".equals(rsp.getError_code())) {
			// 访问能力开发平台失败
			dealResult.setError_code(rsp.getError_code());
			dealResult.setError_msg("访问能力开发平台异常:" + rsp.getError_msg());
		} else if (!"00000".equals(rsp.getRes_code())) {
			// 能力开发平台返回失败
			dealResult.setError_code(rsp.getRes_code());
			dealResult.setError_msg("能力开发平台异常:" + rsp.getRes_message());
		} else {
			WechatRetBean webchatBean = rsp.getResultMsg();

			if (null == webchatBean) {
				// 微信公众号平台返回信息为空
				dealResult.setError_code("-9999");
				dealResult.setError_msg("调用接口异常:微信公众号平台返回的信息为空");
			} else if ("0".equals(webchatBean.getResp_code())) {
				// 微信公众号平台返回成功
				dealResult.setError_code("0");
				dealResult.setError_msg("成功");
			} else {
				// 微信公众号平台返回失败
				dealResult.setError_code("-9999");
				dealResult.setError_msg("微信公众号平台返回：" + webchatBean.getResp_msg());
			}
		}
		return dealResult;
	}
}
