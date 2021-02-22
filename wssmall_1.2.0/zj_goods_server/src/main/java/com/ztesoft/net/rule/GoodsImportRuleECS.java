package com.ztesoft.net.rule;

import com.ztesoft.net.mall.core.consts.Consts;

import rule.impl.ImportBaseRule;
import zte.params.req.MidDataProcessReq;
import zte.params.resp.MidDataProcessResp;

public class GoodsImportRuleECS extends ImportBaseRule {

	@Override
	public MidDataProcessResp process(MidDataProcessReq req) {
		try {
			String json = req.getJson();
			//处理数据过程省略
			logger.info("商品导入处理过程>>>>>>>>>>>>>");
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		MidDataProcessResp resp = new MidDataProcessResp();
		if(req.getId().endsWith("1")){
			resp.setResp_code(Consts.MID_DATA_STATUS_2);//处理失败
		}
		else{
			resp.setResp_code(Consts.MID_DATA_STATUS_1);//处理成功
		}
		resp.setResp_msg("处理成功");
		return resp;
	}

}
