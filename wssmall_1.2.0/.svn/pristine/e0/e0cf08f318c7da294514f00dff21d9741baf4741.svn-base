package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Flow;
import com.ztesoft.net.mall.core.model.FlowInst;
import com.ztesoft.net.mall.core.model.FlowItem;
import com.ztesoft.net.mall.core.model.FlowItemInst;
import com.ztesoft.net.mall.core.model.FlowNextItem;
import com.ztesoft.net.mall.core.workflow.core.InitFlowParam;
import com.ztesoft.net.mall.core.workflow.util.ModInfoAudit;

public interface IFlowManager {
	
	//对基本表信息进行维护
	public boolean saveFlow(Flow flow) ;
	public boolean saveFlowItem(FlowItem item) ;
	public boolean saveFlowNextItem(FlowNextItem nextItem) ;
	public boolean saveFlowInst(FlowInst flowInst) ;
	public boolean saveFlowItemInst(FlowItemInst itemInst) ;
	
	
	public boolean delFlow(Integer flowId) ;
	public boolean delFlowItem(Integer itemId) ;
	public boolean delFlowNextItem(Integer nextId) ;
	
	//查询我的待办
	public Page qryMyWorkList(String audit_user,String flow_name,String startTime,String endTime,String username, int page, int pageSize);
    
    //查询我发起的流程
    public Page qryMyApplykList(String audit_user,String flow_name,String startTime,String endTime,String username, int page, int pageSize);
    
    
    //更新环节状态
    public boolean upateFlowItemState(Map parmeteMap);
    
	//查询流程下一个环节
	public Map qryNextItem(Map parmeteMap);
	
	//查询流程下一个处理人
	public String qryNextPerson(Map parmeteMap);
	
	//审核信息列表
	public List qryAuditInfo(String flow_inst_id); 
	
	//下一步
	public boolean doNext(ModInfoAudit modInfoAudit);
	
	public List qryFlowItemById(String flow_inst_id);
	
	public String startFlow(InitFlowParam flowParam);
}
