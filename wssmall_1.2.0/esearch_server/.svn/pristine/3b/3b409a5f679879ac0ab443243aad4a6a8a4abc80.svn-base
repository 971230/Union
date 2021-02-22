package zte.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import services.ServiceBase;
import zte.net.iservice.IESearchService;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.model.ESearchData;
import com.ztesoft.net.search.service.IESearchManager;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version = "1.0")
@ZteSoftCommentAnnotation(type = "class", desc = "esearch搜索引擎服务", summary = "esearch搜索引擎服务")
public class EsearchService extends ServiceBase implements IESearchService {

	@Resource
	private IESearchManager esManager;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@ZteSoftCommentAnnotation(type = "method", desc = "插入一条报文纪录", summary = "插入一条报文纪录")
	public EsearchAddResp insert(EsearchAddReq req) throws Exception{
		ESearchData esearch = req.getEsData();
		EsearchAddResp resp = new EsearchAddResp();
		
		try{
			String id = esManager.insert(esearch);
			
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("insert接口调用出错:"+e.getMessage());
			resp.setError_code("-1");
			resp.setError_msg(e.getMessage());
		}
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@ZteSoftCommentAnnotation(type = "method", desc = "更新单条报文纪录(不更新空值)", summary = "更新单条报文纪录(不更新空值)")
	public EsearchUpdateResp update(EsearchUpdateReq req) throws Exception {
		ESearchData esearch = req.getEsData();
		boolean updateNull = req.isUpdateNull();
		boolean updateDelay = req.isUpdateDelay();
		
		EsearchUpdateResp resp = new EsearchUpdateResp();
		
		try{
			String id = esManager.update(esearch,updateNull,updateDelay);
			
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("update接口调用出错:"+e.getMessage());
			resp.setError_code("-1");
			resp.setError_msg(e.getMessage());
		}
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@ZteSoftCommentAnnotation(type = "method", desc = "根据关键字信息更新归类信息", summary = "根据关键字信息更新归类信息")
	public EsearchUpdateClassResp updateClassByKeyword(EsearchUpdateClassReq req) throws Exception {
		ESearchData esearch = req.getEsData();
		boolean updateDelay = req.isUpdateDelay();
		
		EsearchUpdateClassResp resp = new EsearchUpdateClassResp();
		
		try{
			String id = esManager.updateClassByKeyword(esearch,updateDelay);
			
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("updateClassByKeyword接口调用出错:"+e.getMessage());
			resp.setError_code("-1");
			resp.setError_msg(e.getMessage());
		}
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@ZteSoftCommentAnnotation(type = "method", desc = "根据id取出单条报文纪录", summary = "根据id取出单条报文纪录")
	public EsearchGetResp get(EsearchGetReq req) throws Exception {
		ESearchData esearch = req.getEsData();
		EsearchGetResp resp = new EsearchGetResp();
		
		try{
			ESearchData esData = esManager.get(esearch);
			
			resp.setEsData(esData);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("get接口调用出错:"+e.getMessage());
			resp.setError_code("-1");
			resp.setError_msg(e.getMessage());
		}
		return resp;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@ZteSoftCommentAnnotation(type = "method", desc = "全量导入接口日志报文", summary = "全量导入接口日志报文")
	public EsearchLogInfoDateResp logInfoIndexByDateRange(EsearchLogInfoDateReq req) throws Exception {
		String fromTime = req.getFromTime();
		String toTime = req.getToTime();
		Map values = new HashMap();
		values.put("fromDate", fromTime);
		values.put("toDate", toTime);
		EsearchLogInfoDateResp resp = new EsearchLogInfoDateResp();
		
		try{
			esManager.logInfoIndexByDateRange(values);
			
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("logINfoIndexByDateRange接口调用出错:"+e.getMessage());
			resp.setError_code("-1");
			resp.setError_msg(e.getMessage());
		}
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@ZteSoftCommentAnnotation(type = "method", desc = "根据id集合插入日志报文", summary = "根据id集合插入日志报文")
	public EsearchLogInfoIdsResp logInfoIndexByIds(EsearchLogInfoIdsReq req) throws Exception {
		List idList = req.getIdList();
		
		EsearchLogInfoIdsResp resp = new EsearchLogInfoIdsResp();
		
		try{
			esManager.logInfoIndexByIds(idList);
			
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("logInfoIndexByIds接口调用出错:"+e.getMessage());
			resp.setError_code("-1");
			resp.setError_msg(e.getMessage());
		}
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@ZteSoftCommentAnnotation(type = "method", desc = "根据匹配规则查询报文", summary = "根据匹配规则查询报文")
	public EsearchSearchResp search(EsearchSearchReq req) throws Exception {
		String indexName = req.getIndexName();
		String typeName = req.getTypeName();
		Map<String,String> qsParams = req.getQsParams();
		Map<String,String> boolParams = req.getBoolParams();
		
		EsearchSearchResp resp = new EsearchSearchResp();
		
		try{
			List<ESearchData> esList = esManager.search(indexName, typeName, qsParams, boolParams);
			resp.setEsList(esList);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("search接口调用出错:"+e.getMessage());
			resp.setError_code("-1");
			resp.setError_msg(e.getMessage());
		}
		return resp;
	}

}
