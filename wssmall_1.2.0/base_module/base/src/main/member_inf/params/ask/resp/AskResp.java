package params.ask.resp;

import com.ztesoft.net.app.base.core.model.Ask;
import com.ztesoft.net.framework.database.Page;
import params.ZteResponse;

public class AskResp extends ZteResponse {
	private Ask ask;
	private Page askPage;
	
	public Ask getAsk() {
		return ask;
	}
	public void setAsk(Ask ask) {
		this.ask = ask;
	}
	public Page getAskPage() {
		return askPage;
	}
	public void setAskPage(Page askPage) {
		this.askPage = askPage;
	}
	
}
