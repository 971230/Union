package zte.net.workCustomBean.common;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.rop.common.ServiceMethodHandler;

import utils.CoreThreadLocalHolder;
import zte.net.common.annontion.context.action.DefaultActionBeanDefine;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;

public class RuleBusiBean extends BasicBusiBean {
	private Logger logger = Logger.getLogger(this.getClass());

	private BusiCompResponse doRule(BusiCompRequest prequest) throws Exception {
		//组装参数
		String enginePath = prequest.getEnginePath();
		DefaultActionBeanDefine context = DefaultActionBeanDefine.getInstance();
		String version="1.0";
		BusiCompResponse resp = new BusiCompResponse();
		
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteError(null);
		if (context.isValidMethodVersion(enginePath, version)) {
			ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(enginePath, version);
			resp =  (BusiCompResponse) serviceMethodHandler.getHandlerMethod().invoke(serviceMethodHandler.getHandler(),prequest);
		}else {
			//找不到服务,报错
			throw new Exception("找不到规则对应的服务" + enginePath);
		}
		
		//规则执行失败的抛出异常
		if(!"0".equals(resp.getError_code())){
			throw new Exception(resp.getError_msg());
		}
		
		return resp;
	}
	
	@Override
	public String doBusi() throws Exception {
		if(StringUtils.isBlank(this.getNode_id()))
			throw new Exception("传入的环节编号为空");
		
		if(this.flowData == null)
			throw new Exception("传入的流程数据为空");
		
		if(this.getFlowData().getOrderTree() == null)
			throw new Exception("传入的订单树对象为空");
		
		if(this.getFlowData().getInsMap() == null)
			throw new Exception("传入的流程实例为空");
		
		if(!this.getFlowData().getInsMap().containsKey(node_id))
			throw new Exception(node_id+"环节的流程实例为空");
		
		ES_WORK_CUSTOM_NODE_INS ins = this.getFlowData().getInsMap().get(node_id);
		
		String deal_type = ins.getDeal_type();
		String method = ins.getDeal_bean();
		String order_id = flowData.getOrderTree().getOrder_id();
		
		if(!"rule".equals(deal_type))
			throw new Exception("当前环节的处理方式不为规则");
		
		if(StringUtils.isBlank(method))
			return "";
		
		BusiCompRequest prequest = new BusiCompRequest();
		prequest.setOrder_id(order_id);
		prequest.setEnginePath(method);
		
		try{
			this.doRule(prequest);
		}catch(InvocationTargetException e){
			logger.error(e.getMessage(), e);
			Throwable te = e.getTargetException();
			throw new Exception(te.getMessage());	
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return "";
	}
}
