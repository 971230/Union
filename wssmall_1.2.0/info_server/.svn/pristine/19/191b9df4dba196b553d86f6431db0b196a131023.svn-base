package zte.net.service;

import javax.annotation.Resource;

import services.ServiceBase;
import zte.net.iservice.IInfoServices;
import zte.params.template.req.GetAttrDefTableReq;
import zte.params.template.req.TemplateAccessConvertReq;
import zte.params.template.req.TemplateOuter2XmlConvertReq;
import zte.params.template.req.TemplateOuterConvertReq;
import zte.params.template.req.TemplateQryReq;
import zte.params.template.req.WelcomInfoReq;
import zte.params.template.resp.GetAttrDefTableResp;
import zte.params.template.resp.TemplateAccessConvertResp;
import zte.params.template.resp.TemplateOuter2XmlConvertResp;
import zte.params.template.resp.TemplateOuterConvertResp;
import zte.params.template.resp.TemplateQryResp;
import zte.params.template.resp.WelcomInfoResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.template.service.ITemplateConvertManager;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version = "1.0")
@ZteSoftCommentAnnotation(type = "class", desc = "信息管理服务", summary = "信息管理服务")
public class InfoServices extends ServiceBase implements IInfoServices {
	
	@Resource
	private ITemplateConvertManager templateConvertManager;

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "模板查询", summary = "订单模板查询")
	public TemplateQryResp templateQry(TemplateQryReq req)
			throws ApiBusiException {
		TemplateQryResp resp = new TemplateQryResp();
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "模板转入", summary = "模板转入")
	public TemplateAccessConvertResp tplAccessConvert(TemplateAccessConvertReq req) throws ApiBusiException {
		TemplateAccessConvertResp resp = new TemplateAccessConvertResp();
//		try{
//			//获取报文模板
//			TemplateQryReq tplQryReq = new TemplateQryReq();
//			tplQryReq.setTemplate_code(req.getTpl_code());
//			tplQryReq.setTemplate_version(req.getTpl_ver());
//			TemplateQryResp tplQryResp = this.templateQry(tplQryReq);
//			Map<String, NodeModel> tplMap = tplQryResp.getTplMap();
//			if(null == tplMap || tplMap.isEmpty()){
//				resp.setError_msg(tplQryResp.getError_msg());
//				return resp;
//			}
//			//构建业务对象并保存
//			resp = templateConvertManager.tplAccessConvert(req.getInterfaceInfo(), tplMap);
//		}catch(Exception e){
//			resp.setError_msg("模板接入异常");
//			e.printStackTrace();
//		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "报文转出", summary = "模板转出")
	public TemplateOuterConvertResp tplOuterConvert(TemplateOuterConvertReq req)throws ApiBusiException {
		TemplateOuterConvertResp resp = new TemplateOuterConvertResp();
//		try{
//			//获取模板
//			TemplateQryReq tplQryReq = new TemplateQryReq();
//			tplQryReq.setTemplate_code(req.getTemplate_code());
//			tplQryReq.setTemplate_version(req.getTemplate_version());
//			TemplateQryResp tplQryResp = this.templateQry(tplQryReq);
//			Map<String, NodeModel> tplMap = tplQryResp.getTplMap();
//			if(null == tplMap || tplMap.isEmpty()){
//				resp.setError_msg(tplQryResp.getError_msg());
//				return resp;
//			}
//			//拼装接口数据
//			resp = templateConvertManager.tplOuterConvert(tplMap, req.getInst_id());
//		}catch(Exception e){
//			resp.setError_msg("报文转出异常");
//		}
		return resp;
	}

	@Override
	public TemplateOuter2XmlConvertResp tplOuter2XmlConvert(TemplateOuter2XmlConvertReq req) throws ApiBusiException {
		TemplateOuter2XmlConvertResp resp = new TemplateOuter2XmlConvertResp();
		return resp;
	}

	@Override
	public WelcomInfoResp mapWelcomInfo(WelcomInfoReq req) throws ApiBusiException {
		WelcomInfoResp resp = new WelcomInfoResp();
		return resp;
	}

	@Override
	public GetAttrDefTableResp getAttrDefTableReq(GetAttrDefTableReq req) throws ApiBusiException {
		GetAttrDefTableResp resp = new GetAttrDefTableResp();
		return resp;
	}
	
	
	
}
