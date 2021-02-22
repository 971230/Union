package zte.net.iservice;

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

@ZteSoftCommentAnnotation(type="class",desc="信息管理API",summary="信息管理API[模板查询、模板转入、模板转出]")
public interface IInfoServices {
	
	/**
	 * 获取模板信息
	 * @param request
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type="method",desc="模板查询",summary="订单模板查询")
	public TemplateQryResp templateQry(TemplateQryReq req) throws ApiBusiException;
	
	/**
	 * 报文接入
	 * 以Map的形式接入
	 * 外围系统报文接入后,转为订单系统的业务对象
	 * 业务对象创建完成后store
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type="method",desc="模板转入",summary="模板转入")
	public TemplateAccessConvertResp tplAccessConvert(TemplateAccessConvertReq req) throws ApiBusiException;
	
	/**
	 * 报文转出
	 * 事件中心，根据业务场景，业务实例
	 * 将模板转为外围系统需要的报文
	 * 以Map的形式输出
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type="method",desc="模板转出",summary="模板转出")
	public TemplateOuterConvertResp tplOuterConvert(TemplateOuterConvertReq req) throws ApiBusiException;
	
	/**
	 * 报文转出
	 * 事件中心，根据业务场景，业务实例
	 * 将模板转为外围系统需要的报文
	 * 以xml的形式输出
	 * @param req
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteSoftCommentAnnotation(type="method",desc="模板转出",summary="模板转出")
	public TemplateOuter2XmlConvertResp tplOuter2XmlConvert(TemplateOuter2XmlConvertReq req) throws ApiBusiException;
	
	/**
	 * 会员登录中心信息汇总
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="会员登录中心信息汇总",summary="会员登录中心信息汇总")
	public WelcomInfoResp mapWelcomInfo(WelcomInfoReq req) throws ApiBusiException;
	
	
	@ZteSoftCommentAnnotation(type="method",desc="联通获取属性定义",summary="联通获取属性定义")
	public GetAttrDefTableResp getAttrDefTableReq(GetAttrDefTableReq req) throws ApiBusiException;
}