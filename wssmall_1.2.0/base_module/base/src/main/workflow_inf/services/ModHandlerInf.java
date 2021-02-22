package services;

import java.util.List;

import com.ztesoft.net.mall.core.workflow.util.ModInstVO;
import com.ztesoft.net.mall.core.workflow.util.ModParams;

public interface ModHandlerInf {

	/**
	 * 流程结束修改状态
	 * @param flowInstId
	 */
	public void processWhenFlowFinish(String flowInstId);
	
	/**
	 * 变更处理入口
	 * @param param
	 * @return
	 */
	public void checkAndStartFlow(ModParams param);
	
	public List qryModInfo(Integer flow_inst_id,String ref_obj_id);
	
	public List<ModInstVO> getModsBy(Integer flowInstId , String refObjId);
	
}
