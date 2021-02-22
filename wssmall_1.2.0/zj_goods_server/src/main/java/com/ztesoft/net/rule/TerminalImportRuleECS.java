package com.ztesoft.net.rule;

import com.ztesoft.net.mall.core.consts.Consts;

import rule.impl.ImportBaseRule;
import zte.params.req.MidDataProcessReq;
import zte.params.resp.MidDataProcessResp;

public class TerminalImportRuleECS extends ImportBaseRule {

	@Override
	public MidDataProcessResp process(MidDataProcessReq req) {
		try {
			Thread.sleep(3000);
			logger.info("33333333终端数据导入处理："+req.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		MidDataProcessResp resp = new MidDataProcessResp();
		resp.setResp_code(Consts.MID_DATA_STATUS_1);
		resp.setResp_msg("处理成功");
		return resp;
	}

}