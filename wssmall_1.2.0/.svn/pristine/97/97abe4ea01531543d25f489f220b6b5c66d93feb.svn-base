package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.adminuser.resp.WorkLogAddResp;

public class WordLogAddReq extends ZteRequest<WorkLogAddResp>{

	private MblWorkLogVO mblWorkLogVO;

	public MblWorkLogVO getMblWorkLogVO() {
		return mblWorkLogVO;
	}

	public void setMblWorkLogVO(MblWorkLogVO mblWorkLogVO) {
		this.mblWorkLogVO = mblWorkLogVO;
	}

	@Override
	public void check() throws ApiRuleException {
		if(null == mblWorkLogVO){
			throw new ApiRuleException("-1","缺失参数");
		}
	}

	@Override
	public String getApiMethodName() {
		return "workLogServ.addWork";
	}
}
