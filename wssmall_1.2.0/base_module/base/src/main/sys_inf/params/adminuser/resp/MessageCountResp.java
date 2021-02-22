package params.adminuser.resp;

import params.ZteResponse;

/**
 * 消息读取返回实体
 * @author hu.yi
 * @date 2013.12.24
 */
public class MessageCountResp extends ZteResponse{

	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
