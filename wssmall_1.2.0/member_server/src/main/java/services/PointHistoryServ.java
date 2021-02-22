package services;

import params.member.req.PointHistoryReq;
import params.member.resp.PointHistoryResp;

import com.ztesoft.net.mall.core.service.IPointHistoryManager;

public class PointHistoryServ extends ServiceBase implements PointHistoryInf{
	
	private IPointHistoryManager pointHistoryManager;

	public IPointHistoryManager getPointHistoryManager() {
		return pointHistoryManager;
	}

	public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
		this.pointHistoryManager = pointHistoryManager;
	}

	@Override
	public PointHistoryResp addPointHistory(PointHistoryReq req) {
		PointHistoryResp resp = new PointHistoryResp();
		try{
			pointHistoryManager.addPointHistory(req.getPointHistory());
			resp.setError_code("0");
			resp.setError_msg("新增积分历史成功");
		}catch(Exception e){
			e.printStackTrace();
			resp.setError_code("-1");
			resp.setError_msg("新增积分历史失败");
			addReturn(req, resp);
			return resp;
		}
		addReturn(req, resp);
		return resp;
	}
	
}
