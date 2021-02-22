package rule.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.mall.core.consts.Consts;

import rule.AbstractRuleHander;
import rule.IImportBaseRule;
import zte.net.iservice.IImportService;
import zte.params.req.MidDataProcessReq;
import zte.params.resp.MidDataProcessResp;

public abstract class ImportBaseRule extends AbstractRuleHander implements
		IImportBaseRule {
	@Resource
	private IImportService importService;
	
	public MidDataProcessResp importPerform(MidDataProcessReq req){
		
		MidDataProcessResp resp = this.process(req);
		
		if(StringUtils.isEmpty(resp.getResp_code()) || Consts.MID_DATA_STATUS_2.equals(resp.getResp_code())){//处理失败，导入日志中失败数+1，待处理数-1
			//importService.modifyMidDataStatus(req.getLog_id(),req.getId(), Consts.MID_DATA_STATUS_2,resp.getResp_msg());
		}
		else{//处理成功，导入日志中成功数+1，待处理数-1
			//importService.modifyMidDataStatus(req.getLog_id(),req.getId(), Consts.MID_DATA_STATUS_1,resp.getResp_msg());
		}
		return resp;
	}

}
