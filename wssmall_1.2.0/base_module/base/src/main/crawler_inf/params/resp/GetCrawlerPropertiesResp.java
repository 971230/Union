package params.resp;

import params.ZteResponse;
import params.req.SaveCrawlerPropertiesReq;

public class GetCrawlerPropertiesResp extends ZteResponse{

	private SaveCrawlerPropertiesReq crawlerPropertes;

	public SaveCrawlerPropertiesReq getCrawlerPropertes() {
		return crawlerPropertes;
	}

	public void setCrawlerPropertes(SaveCrawlerPropertiesReq crawlerPropertes) {
		this.crawlerPropertes = crawlerPropertes;
	}
	
}
