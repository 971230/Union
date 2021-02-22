package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.app.base.core.model.Message;

import params.ZteRequest;
import params.adminuser.resp.MessageResp;

public class MessageReq extends ZteRequest<MessageResp>{

	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public void check() throws ApiRuleException {
		if(null == message){
			throw new ApiRuleException("-1","message实体不能为空");
		}
	}

	@Override
	public String getApiMethodName() {
		return "messageServ.addMessage";
	}
	
	
}
