package zte.net.iservice.impl;

import params.req.EsearchAddReq;
import params.req.EsearchGetReq;
import params.req.EsearchLogInfoDateReq;
import params.req.EsearchLogInfoIdsReq;
import params.req.EsearchSearchReq;
import params.req.EsearchUpdateClassReq;
import params.req.EsearchUpdateReq;
import params.resp.EsearchAddResp;
import params.resp.EsearchGetResp;
import params.resp.EsearchLogInfoDateResp;
import params.resp.EsearchLogInfoIdsResp;
import params.resp.EsearchSearchResp;
import params.resp.EsearchUpdateClassResp;
import params.resp.EsearchUpdateResp;
import zte.net.iservice.IESearchService;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version="1.0")
public class ZteEsearchOpenService implements IESearchService{

	private IESearchService esService;
	
	private void init(){
		esService= SpringContextHolder.getBean("esearchService");
    }
	
	@Override
	@ServiceMethod(method="zte.esearchService.esearch.insert",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public EsearchAddResp insert(EsearchAddReq req) throws Exception{
		this.init();
		return esService.insert(req);
	}

	@Override
	@ServiceMethod(method="zte.esearchService.esearch.update",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public EsearchUpdateResp update(EsearchUpdateReq req) throws Exception {
		this.init();
		return esService.update(req);
	}

	@Override
	@ServiceMethod(method="zte.esearchService.esearch.updateClass",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public EsearchUpdateClassResp updateClassByKeyword(EsearchUpdateClassReq req) throws Exception {
		this.init();
		return esService.updateClassByKeyword(req);
	}

	@Override
	@ServiceMethod(method="zte.esearchService.esearch.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public EsearchGetResp get(EsearchGetReq req) throws Exception {
		this.init();
		return esService.get(req);
	}

	@Override
	@ServiceMethod(method="zte.esearchService.esearch.indexByDate",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public EsearchLogInfoDateResp logInfoIndexByDateRange(EsearchLogInfoDateReq req) throws Exception {
		this.init();
		return esService.logInfoIndexByDateRange(req);
	}

	@Override
	@ServiceMethod(method="zte.esearchService.esearch.indexByIds",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public EsearchLogInfoIdsResp logInfoIndexByIds(EsearchLogInfoIdsReq req) throws Exception {
		this.init();
		return esService.logInfoIndexByIds(req);
	}

	@Override
	@ServiceMethod(method="zte.esearchService.esearch.search",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public EsearchSearchResp search(EsearchSearchReq req) throws Exception {
		this.init();
		return esService.search(req);
	}
	
}
