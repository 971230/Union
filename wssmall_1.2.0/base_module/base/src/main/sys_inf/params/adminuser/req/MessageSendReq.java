package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.SendMsgResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;

/**
 * 发送消息传参实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class MessageSendReq extends ZteRequest<SendMsgResp>{

	
	private String reciverId;
	private String content;
	
	
	
	public String getReciverId() {
		return reciverId;
	}
	public void setReciverId(String reciverId) {
		this.reciverId = reciverId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(reciverId) || StringUtil.isEmpty(content)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "messageServ.sendMsg";
	}
}
