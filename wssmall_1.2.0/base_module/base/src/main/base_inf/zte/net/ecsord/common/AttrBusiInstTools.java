package zte.net.ecsord.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.drools.core.util.StringUtils;
import org.springframework.beans.BeanUtils;

import utils.UUIDUtil;
import zte.net.ecsord.common.element.jsoninst.AttrIsEditVo;
import zte.net.ecsord.common.element.jsoninst.AttrIsNullVo;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.IOrderServices;
import zte.params.order.req.AttrDefGetReq;
import zte.params.order.resp.AttrDefGetResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.util.CacheUtils;
import commons.CommonTools;

import consts.ConstsCore;



/**
 * 
 * @author wu.i
 * 订单属性工具类
 * 
 *
 */
public class AttrBusiInstTools {
	
	public static final int CACHE_SPACE = 444;
	/**
	 * 属性实例数据转换为map对象
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map attrsInstToMap(String order_id){
		Map attrInstMap = new HashMap();
		//OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrInstBusiRequest> attrInstBusiRequests = CommonDataFactory.getInstance().getAttrInstBusiRequests(order_id);
		for (AttrInstBusiRequest attrInstBusiRequest:attrInstBusiRequests) {
			attrInstMap.put(genAttrsInstElementId(order_id,attrInstBusiRequest.getAttr_inst_id(),attrInstBusiRequest.getAttr_spec_id(),attrInstBusiRequest.getField_name()), attrInstBusiRequest.getField_value());
		}
		return attrInstMap;
	}
	
	/**
	 * 根据订单id获取页面属性实例数据
	 * @param order_id
	 * @return
	 */
	private static Map getPageOrderAttrMap(String order_id){
    	Map attrInstMap = new HashMap();
    	HttpServletRequest request = ThreadContextHolder.getHttpRequest();
    	if(request ==null)
    		return new HashMap();
        Map parameterMap = request.getParameterMap();
        for (Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            if(key.indexOf(getAttrInstElementPrefix(order_id))>-1){
            	String values []= request.getParameterValues(key);
            	String value = stingArrayToStr(values);
            	attrInstMap.put(key,value);
            }
        }
        return attrInstMap;
	}
	
	/**
	 *数组转字符串 
	 * @param values
	 * @return
	 */
	private static String stingArrayToStr(String values []){
		String str = "";
		String prefix =",";
		for (int i = 0; i < values.length; i++) {
			if(i==values.length-1)
				prefix="";
			str += values[i]+prefix;
		}
		return str;
	}
	
	
	/**
	 * 获取必填，但属性值为空字段，add by wui优化处理
	 * @param order_id
	 * @return
	 */
	public static  List<AttrInstBusiRequest> getTraceMustAttrInst(String order_id,OrderTreeBusiRequest tree,List<AttrInstBusiRequest> attrInstBusiRequests,List<AttrInstBusiRequest> dirtyaAttrInstBusiRequests){
		List<AttrInstBusiRequest> mustAttrInsts = new ArrayList<AttrInstBusiRequest>();
		
		for (AttrInstBusiRequest attrInstBusiRequest:attrInstBusiRequests) {
			if(StringUtil.isEmpty(attrInstBusiRequest.getField_value()))
			{
				AttrInstLoadResp resp = new AttrInstLoadResp();
				resp.setIs_null(attrInstBusiRequest.getSpec_is_null());
				String flow_trace_id = tree.getOrderExtBusiRequest().getFlow_trace_id();
				AttrBusiInstTools.genTraceIsNullByAttrDef(flow_trace_id,resp);
				
				String dealStr =  getAttrIsDeal(attrInstBusiRequest.getField_name()); //判断是否需要进属性处理器处理
				if(ConstsCore.IS_NULL_N.equals(resp.getIs_null())||(ConstsCore.DEAL_STR.equals(dealStr)))  {
					boolean isExist = false;
					for (AttrInstBusiRequest dirtyaAttrInstBusiRequest:dirtyaAttrInstBusiRequests) {
						if(dirtyaAttrInstBusiRequest.getAttr_inst_id().equals(attrInstBusiRequest.getAttr_inst_id())){
							isExist  =true;
							break;
						}
					}
					if(!isExist)
						mustAttrInsts.add(attrInstBusiRequest);
				}
			}else{//不为空的时候进属性处理器做特殊校验
				String dealStr =  getAttrIsDeal(attrInstBusiRequest.getField_name()); //判断是否需要进属性处理器处理
				if(ConstsCore.DEAL_STR.equals(dealStr)){
					boolean isExist = false;
					for (AttrInstBusiRequest dirtyaAttrInstBusiRequest:dirtyaAttrInstBusiRequests) {
						if(dirtyaAttrInstBusiRequest.getAttr_inst_id().equals(attrInstBusiRequest.getAttr_inst_id())){
							isExist  =true;
							break;
						}
					}
					if(!isExist)
						mustAttrInsts.add(attrInstBusiRequest);
				}
			}
		}
		return mustAttrInsts;
	}
	
	public static  List<AttrInstBusiRequest> getPageDirtyOrderAttrInst(String order_id,OrderTreeBusiRequest tree){
		 List<AttrInstBusiRequest> attrInstBusiRequests = CommonDataFactory.getInstance().getAttrInstBusiRequests(order_id);
		 return getPageDirtyOrderAttrInst( order_id, tree,attrInstBusiRequests);
	}
	/**
	 * 获取脏数据页面属性实例数据
	 * @param order_id
	 * @return
	 */
	public static  List<AttrInstBusiRequest> getPageDirtyOrderAttrInst(String order_id,OrderTreeBusiRequest tree,List<AttrInstBusiRequest> attrInstBusiRequests){
		  Map parameterMap = getPageOrderAttrMap(order_id);
		  List<AttrInstBusiRequest> dirtyAttrInstBusiRequests = new ArrayList<AttrInstBusiRequest>();
          for (Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
	            String page_attr_name = (String) iterator.next();
	            String attr_inst_id = page_attr_name.split("_")[2];
	            String value = (String)parameterMap.get(page_attr_name);
	            String field_name =page_attr_name.split("#")[1];
	          //根据规格设置是否为空，是否可编辑
	            AttrInstLoadResp resp = new AttrInstLoadResp();
	            AttrInstBusiRequest attrInstBusiRequest =  getAttrInstBusiRequestByAttrInstIdF(order_id, field_name,attrInstBusiRequests);
	            //add by wui为空，首次插入空信息
	            if(attrInstBusiRequest ==null){
	            	String field_attr_id =page_attr_name.split("_")[3];
	            	field_attr_id = field_attr_id.substring(0, field_attr_id.indexOf("#"));
		        	AttrBusiInstTools.setAttrNullAndEditByAttrDef(order_id, field_attr_id, resp,tree);
		        	if(ConstsCore.IS_NULL_N.equals(resp.getIs_null()) || (!StringUtil.isEmpty(value))){ //必填则写入
		            	attrInstBusiRequest = new AttrInstBusiRequest();
		            	attrInstBusiRequest.setAttr_inst_id(UUIDUtil.getUUID());
		        		attrInstBusiRequest.setOrder_id(order_id);
		        		attrInstBusiRequest.setField_name(page_attr_name.split("#")[1]);
		        		attrInstBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		        		attrInstBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
		        		attrInstBusiRequest.setCreate_date(Consts.SYSDATE);
		        		attrInstBusiRequest.store();
		        	}else{
		        		continue; //允许为空，则不插入，继续处理
		        	}
	            }
	            //页面脏数据设置
	            AttrInstBusiRequest copyAttrInstBusiRequest = new AttrInstBusiRequest();
	            BeanUtils.copyProperties(attrInstBusiRequest, copyAttrInstBusiRequest);
	            
				AttrBusiInstTools.setAttrNullAndEditByAttrDef(order_id, attrInstBusiRequest.getField_attr_id(), resp,tree);
				String field_value =attrInstBusiRequest.getField_value();
				if("null".equals(field_value)  || StringUtils.isEmpty(field_value))
					field_value ="";
				//空值判断
	            if((
	            		ConstsCore.IS_NULL_Y.equals(resp.getIs_null()) || 
	            		(ConstsCore.IS_NULL_N.equals(resp.getIs_null()) && !StringUtils.isEmpty(value))
	               ) && (!value.equalsIgnoreCase(field_value)))
	            {
	            	copyAttrInstBusiRequest.setField_value(value);
	            	dirtyAttrInstBusiRequests.add(copyAttrInstBusiRequest);
	            }
	    }
        return dirtyAttrInstBusiRequests;
	}
	
	
	/**
	 * 场景：加载环节页面，获取html:orderattr定义属性数据，通过field_name,order_id获取属性实例
	 * @param attr_spec_id
	 * @param req
	 * @return
	 */
	public static AttrInstLoadResp invokeAttrInstPageLoad(String attr_spec_id,AttrInstLoadReq req){
		//根据规格设置是否可编辑，是否为空
		AttrInstLoadResp invokeResp = new AttrInstLoadResp();
		IAttrHandler attrHandler = getIAttrHandler(attr_spec_id);
		if(attrHandler !=null)
			 invokeResp =  attrHandler.attrInitForPageLoadSetting(req);
		if(invokeResp  == null)
			 return null;
		return invokeResp;
	}
	
	
	
	/**
	 *场景：页面保存，循环处理待保存属性实例数据 
	 * @param attr_spec_id
	 * @param req
	 * @return
	 */
	public static AttrInstLoadResp invokeAttrInstPageUpdate(String attr_spec_id,AttrInstLoadReq req){
		//根据规格设置是否可编辑，是否为空
		AttrInstLoadResp invokeResp = new AttrInstLoadResp();
		IAttrHandler attrHandler = getIAttrHandler(attr_spec_id);
		if(attrHandler !=null)
			 invokeResp =  attrHandler.attrInitForPageUpdateSetting(req);
		if(invokeResp  == null)
			 return null;
		return invokeResp;
	}
	
	/**
	 * 获取属性处理器
	 * @param attr_spec_id
	 * @return
	 */
	public static IAttrHandler getIAttrHandler(String attr_spec_id){
//		if("1000400".equals(attr_spec_id))
//			logger.info("1111111111");
		AttrDefGetReq defreq = new AttrDefGetReq();
		defreq.setField_attr_id(attr_spec_id);
		IOrderServices orderServices = SpringContextHolder.getBean("orderServices");
		AttrDefGetResp defresp =orderServices.getAttrDef(defreq);
		IAttrHandler attrHandler = SpringContextHolder.getBean(defresp.getAttrDef().getHandler_class());
		
		return attrHandler;
			
	}
	
	/**
	 * 所有属性验证
	 * @param order_id
	 * @return
	 */
	public static List<AttrInstLoadResp> validateOrderAttrInstsForPage(String order_id){
		List<AttrInstLoadResp>  allAttrInstLoadResps  = new ArrayList<AttrInstLoadResp>();
		List<AttrInstLoadResp> attrInstLoadResps = AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,ConstsCore.ATTR_ACTION_lOAD);
		allAttrInstLoadResps.addAll(attrInstLoadResps);
		
		attrInstLoadResps =AttrBusiInstTools.validateOrderAttrInstsForPage(order_id, ConstsCore.ATTR_ACTION_UPDATE);
		allAttrInstLoadResps.addAll(attrInstLoadResps);
		
		return allAttrInstLoadResps;
	}
	
//	/**
//	 * 重置处理值
//	 * @param order_id
//	 * @return
//	 */
//	public static void resetOrderAttrCheckMessage(String order_id){
//		OrderTreeBusiRequest tree  = CommonDataFactory.getInstance().getOrderTree(order_id);
//		List<AttrInstBusiRequest> attrInstBusiRequests = tree.getAttrInstBusiRequests();
//	}
	
	/**
	 * 调用PageLoad处理器，校验信息
	 * @param order_id
	 * @return
	 */
	public static List<AttrInstLoadResp> validateOrderAttrInstsForPage(String order_id,String action_name){
		
		//获取脏数据
		OrderTreeBusiRequest orderTree  = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrInstBusiRequest> attrInstBusiRequests=CommonDataFactory.getInstance().getAttrInstBusiRequests(order_id);
		
//		List<AttrInstBusiRequest> attrInstBusiRequests = orderTree.getAttrInstBusiRequests();
		List<AttrInstLoadResp> attrInstLoadResps =new ArrayList<AttrInstLoadResp>();
		List<AttrInstBusiRequest> dirtyaAttrInstBusiRequests = getPageDirtyOrderAttrInst(order_id,orderTree,attrInstBusiRequests);
		long start = System.currentTimeMillis();
		
		
		//add by wui 二次过滤处理,性能优化
		List<AttrInstBusiRequest> traceMustAttrInst = getTraceMustAttrInst(order_id, orderTree,attrInstBusiRequests,dirtyaAttrInstBusiRequests);
		long end = System.currentTimeMillis();
		traceMustAttrInst.addAll(dirtyaAttrInstBusiRequests);
		
		/**
		 * 脏数据+必填数据校验处理
		 */
		
		//脏数据属性验证,正常属性先不验证，每个属性循环，字段太多了
		//List<AttrInstBusiRequest> attrInstBusiRequests = orderTree.getAttrInstBusiRequests();
		for (AttrInstBusiRequest dirtyAttrInstBusiRequest:traceMustAttrInst) {
			AttrInstLoadReq req = new AttrInstLoadReq();
			req.setField_name(dirtyAttrInstBusiRequest.getField_name());
			req.setOrder_id(order_id);
			req.setAction_name(action_name);//触发更新函数
			
			//判断传入字段是否被修改
//			AttrInstBusiRequest dirtyAttrInstBusiRequest = getAttrInstBusiRequestByAttrInstId(order_id, attrInstBusiRequest.getAttr_inst_id(), traceMustAttrInst);
			if(dirtyAttrInstBusiRequest == null)
				 continue;
//			if(dirtyAttrInstBusiRequest !=null)
//				req.setNew_value(dirtyAttrInstBusiRequest.getField_value());
//			else
				req.setNew_value(dirtyAttrInstBusiRequest.getField_value());
			req.setOrderTree(orderTree);
			req.setAttrInsts(attrInstBusiRequests); //add by wui优化处理
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
			
			long end2 = System.currentTimeMillis();
			AttrInstLoadResp resp = client.execute(req, AttrInstLoadResp.class);
			
			long end3 = System.currentTimeMillis();
//			logger.info("单个属性执行时间===================================="+(end3-end2));
			if(resp ==null)
				resp = new AttrInstLoadResp();
			
			//返回结果值
			if(StringUtil.isEmpty(resp.getField_value()))
				resp.setField_value(req.getNew_value());
			
			//处理器返回信息校验
			if(!StringUtil.isEmpty(resp.getCheck_message()) ){
				attrInstLoadResps.add(resp);
			}
			
			//空验证
//			if(ConstsCore.IS_NULL_N.equals(resp.getIs_null()) && StringUtil.isEmpty(resp.getField_value())){
//				resp.setCheck_message("字段名"+attrInstBusiRequest.getFiled_desc()+"属性值不能为空！");
//				attrInstLoadResps.add(resp);
//			}
		}
		long end1 = System.currentTimeMillis();
//		logger.info("校验属性花费时间===================================="+(end1-end));
		return attrInstLoadResps;
	}
	
	
	
	/**
	 * 根据规格设置是否为空，是否可编辑值
	 * @param order_id
	 * @param attr_spec_id
	 * @param resp
	 */
	public static void setAttrNullAndEditByAttrDef(String order_id,String field_attr_id,AttrInstLoadResp resp,OrderTreeBusiRequest orderTree){
		//根据规格设置是否可编辑，是否为空
		IOrderServices orderServices = SpringContextHolder.getBean("orderServices");
		AttrDefGetReq defreq = new AttrDefGetReq();
		defreq.setField_attr_id(field_attr_id);
		AttrDefGetResp defresp = orderServices.getAttrDef(defreq);
		String flow_trace_id =orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		resp.setIs_edit(defresp.getAttrDef().getIs_edit());
		resp.setIs_null(defresp.getAttrDef().getIs_null());
		AttrBusiInstTools.genTraceIsEditByAttrDef(flow_trace_id, resp);
		AttrBusiInstTools.genTraceIsNullByAttrDef(flow_trace_id, resp);
	}
	
	
	
	/**
	 * 订单id，属性实例id获取属性实例对象
	 * @param order_id
	 * @param attr_inst_id
	 * @return
	 */
	public static AttrInstBusiRequest getAttrInstBusiRequestByAttrInstIdF(String order_id,String field_name,List<AttrInstBusiRequest> attrInstBusiRequests){
		for (AttrInstBusiRequest attrInstBusiRequest:attrInstBusiRequests) {
			if(attrInstBusiRequest.getField_name().equals(field_name))
				return attrInstBusiRequest;
		}
		return null;
	}
	
	/**
	 * 页面展示属性，构造element field_id
	 * @param order_id
	 * @param attr_spec_id
	 * @param field_name
	 * @return
	 */
	public static String genAttrsInstElementId(String order_id,String attr_inst_id,String attr_spec_id,String field_name){
		if(StringUtil.isEmpty(attr_inst_id))
			attr_inst_id ="-1"; //add by wui为空，则值为-1
		return getAttrInstElementPrefix(order_id)+attr_inst_id+"_"+attr_spec_id+"#"+field_name;
	}
	
	/**
	 * 定义属性前缀
	 * @param order_id
	 * @return
	 */
	public static String getAttrInstElementPrefix(String order_id){
		return "oattrinstspec_"+order_id+"_";
	}
	/**
	 *设置是否为空属性值
	 * @param order_id
	 * @param attr_spec_id
	 * @param field_name
	 * @return
	 */
	public static void genTraceIsNullByAttrDef(String flow_trace_id,AttrInstLoadResp resp){
		//TODO 此处按JSON对象转换处理
		String is_null_json = resp.getIs_null();
		if(!StringUtil.isEmpty(is_null_json) && is_null_json.length() !=1){
			List<AttrIsNullVo> attrIsNullVos = CommonTools.jsonToList(is_null_json,AttrIsNullVo.class);
			resp.setIs_null(AttrBusiInstTools.getTraceIsNull(flow_trace_id, attrIsNullVos));
		}else {
			resp.setIs_null(ConstsCore.IS_NULL_Y);
		}
	}
	
	/**
	 *设置是否为空属性值
	 * @param order_id
	 * @param attr_spec_id
	 * @param field_name
	 * @return
	 */
	public static void genTraceIsEditByAttrDef(String flow_trace_id,AttrInstLoadResp resp){
		//TODO 此处按JSON对象转换处理
		String is_edit_josn = resp.getIs_edit();
		if(!StringUtil.isEmpty(is_edit_josn) && is_edit_josn.length() !=1){
			List<AttrIsEditVo> attrIsEditVos = CommonTools.jsonToList(is_edit_josn,AttrIsEditVo.class);
			resp.setIs_edit(AttrBusiInstTools.getTraceIsEdit(flow_trace_id, attrIsEditVos));
		}else{
			resp.setIs_edit(ConstsCore.IS_EDIT_Y);
		}
	}
	
	
	/**
	 * 判断是否允许为空
	 * @param order_id
	 * @param attr_spec_id
	 * @param field_name
	 * @return
	 */
	public static String getTraceIsNull(String trace_id,List<AttrIsNullVo> attrIsNullVos){
		for (AttrIsNullVo attrIsNullVo:attrIsNullVos) {
			if(ConstsCore.TRACE_ALL.equals(attrIsNullVo.getTrace_id()))
					return attrIsNullVo.getIs_null();
			if(attrIsNullVo.getTrace_id().equals(trace_id))
				return attrIsNullVo.getIs_null();
		}
		return ConstsCore.IS_NULL_Y;
	}
	
	
	/**
	 * 判断是否允许 编辑
	 * @param order_id
	 * @param attr_spec_id
	 * @param field_name
	 * @return
	 */
	public static String getTraceIsEdit(String trace_id,List<AttrIsEditVo> attrIsEditVos){
		for (AttrIsEditVo attrIsEditVo:attrIsEditVos) {
			if(ConstsCore.TRACE_ALL.equals(attrIsEditVo.getTrace_id()))
					return attrIsEditVo.getIs_edit();
			if(attrIsEditVo.getTrace_id().equals(trace_id))
				return attrIsEditVo.getIs_edit();
		}
		return ConstsCore.IS_NULL_N;
	}
	/**
	 * 判断属性是否有特殊的属性处理器
	 * @return
	 */
	public static String getAttrIsDeal(String field_name){
		String dealStr = "";
		AttrDef attrDef = new AttrDef();
		attrDef = CacheUtils.getCacheAttrDef(field_name+EcsOrderConsts.ATTR_SPEC_ID_999); 
		if(attrDef!=null){
			dealStr = attrDef.getLay_out();
		}
		return dealStr;
	}
}
