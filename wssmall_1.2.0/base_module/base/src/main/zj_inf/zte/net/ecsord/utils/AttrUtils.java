package zte.net.ecsord.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.adminuser.req.ZbAdminUserGetReq;
import params.adminuser.resp.ZbAdminUserGetResp;
import utils.UUIDUtil;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageFukaBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderActivityBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoFukaBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.pub.req.BPackageDependElementGetReq;
import zte.net.ecsord.params.pub.req.DcPublicExtListReq;
import zte.net.ecsord.params.pub.req.DictRelationListReq;
import zte.net.ecsord.params.pub.req.DropdownDataReq;
import zte.net.ecsord.params.pub.req.KPackageDependElementGetReq;
import zte.net.ecsord.params.pub.req.LogiCompPersonGetReq;
import zte.net.ecsord.params.pub.req.ZbDictCodeValueGetReq;
import zte.net.ecsord.params.pub.resp.BPackageDependElementListResp;
import zte.net.ecsord.params.pub.resp.DcPublicExtListResp;
import zte.net.ecsord.params.pub.resp.DictRelationListResp;
import zte.net.ecsord.params.pub.resp.DropdownDataResp;
import zte.net.ecsord.params.pub.resp.KPackageDependElementListResp;
import zte.net.ecsord.params.pub.resp.LogiCompPersonGetResp;
import zte.net.ecsord.params.pub.resp.ZbDictCodeValueGetResp;
import zte.net.ecsord.params.zb.vo.NiceInfo;
import zte.net.ecsord.params.zb.vo.PhoneInfo;
import zte.net.ecsord.params.zb.vo.orderattr.Activity;
import zte.net.ecsord.params.zb.vo.orderattr.ActivityInfo;
import zte.net.ecsord.params.zb.vo.orderattr.ActivityInfoPackage;
import zte.net.ecsord.params.zb.vo.orderattr.Fee;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAtt;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttHK;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttHKHY;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttHK_FK;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttZBLJZD;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttZBPJ;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttZBWXSWK;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttZBWXSWK_FK;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttZDHY;
import zte.net.ecsord.params.zb.vo.orderattr.Package;
import zte.net.ecsord.params.zb.vo.orderattr.Resources;
import zte.net.ecsord.params.zb.vo.orderattr.SubPackage;
import zte.net.ecsord.params.zb.vo.orderattr.SubProd;
import zte.net.iservice.ILogsServices;
import zte.net.iservice.IOrderServices;
import zte.params.ecsord.req.AttrTableCacheGetReq;
import zte.params.ecsord.req.InsertOrderHandLogReq;
import zte.params.ecsord.resp.AttrTableCacheGetResp;
import zte.params.ecsord.resp.InsertOrderHandLogResp;
import zte.params.goods.req.DcPublicInfoCacheProxyReq;
import zte.params.goods.resp.DcPublicInfoCacheProxyResp;
import zte.params.order.req.AttrValueGetReq;
import zte.params.order.resp.AttrValueGetResp;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Order;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.model.AttrTableCache;

import consts.ConstsCore;

public class AttrUtils {
	IOrderServices orderServices;
	public static AttrUtils attrUtils;
	ILogsServices logsServices;
	public static AttrUtils getInstance(){
		if(attrUtils==null)
			attrUtils = new AttrUtils();
		return attrUtils;
		
	}
	
	private static Logger logger = Logger.getLogger(AttrUtils.class);
	

	/**
	 * 获取属性值
	 * @param order_id 订单ID
	 * @param field_name 属性字段名
	 * @return
	 */
	private String getFieldNameByAttrTableFieldName(String field_name,String table_name){
		AttrTableCacheGetReq attrTableCache = new AttrTableCacheGetReq();
		attrTableCache.setField_name(field_name);
//		if("shipping_type".equals(field_name)) //add by wui
//			logger.info("111111111111");
		if(orderServices ==null)
			orderServices  = SpringContextHolder.getBean("orderServices");
		AttrTableCacheGetResp attrta = orderServices.getCacheAttrTable(attrTableCache);
		if(attrta !=null && attrta.getAttrTable() !=null)
			field_name = attrta.getAttrTable().getDef_field_name();
		
		return field_name;
	}
	 
	/**
	 * 获取属性值
	 * @param order_id 订单ID
	 * @param field_name 属性字段名
	 * @return
	 */
	public AttrInstBusiRequest getAttrBusiRequest(String order_id,String field_name,String table_name,String spec_id){
		//从订单树种获取属性信息
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		if(logsServices ==null)
			logsServices = SpringContextHolder.getBean("logsServices");
		AttrInstBusiRequest pqueryAttrInst = logsServices.getAttrInstBusiRequestByOrderId(order_id, field_name);
		return pqueryAttrInst;
	}
	
	
	/**
	 * 获取属性值
	 * @param order_id 订单ID
	 * @param field_name 属性字段名
	 * @return
	 */
	public String getAttrFieldValue(List<AttrInstBusiRequest> attrs,String order_id,String field_name,String table_name,String spec_id){
		//从订单树种获取属性信息
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		if(logsServices ==null)
			logsServices = SpringContextHolder.getBean("logsServices");
		AttrInstBusiRequest queryAttrInst = logsServices.getAttrInstBusiRequestByOrderId(order_id, field_name);
		if(queryAttrInst !=null)
			value = queryAttrInst.getField_value();
		//add by wui转换后拿值
		if(StringUtils.isEmpty(value)){
			String s_field_name = getFieldNameByAttrTableFieldName(field_name,table_name) ;
			if(!StringUtils.isEmpty(s_field_name) && !s_field_name.equals(field_name)){
				queryAttrInst = logsServices.getAttrInstBusiRequestByOrderId(order_id, s_field_name);
				if(queryAttrInst !=null)
					value = queryAttrInst.getField_value();
			}
		}
		return value == null ? EcsOrderConsts.EMPTY_STR_VALUE : value;
	}
	
	/**
	 * 获取属性值
	 * @param order_id 订单ID
	 * @param field_name 属性字段名
	 * @return
	 */
	public String getAttrFieldValue(String order_id,String field_name,String table_name,String spec_id){
		//得到订单树
		//从订单树种获取属性信息
		return getAttrFieldValue(new ArrayList<AttrInstBusiRequest>(), order_id, field_name, table_name, spec_id);
	}
	/**
	 * 从历史订单树获取属性值
	 * @param order_id 订单ID
	 * @param field_name 属性字段名
	 * @return
	 */
	public String getAttrFieldValueHis(String order_id,String field_name,String table_name,String spec_id){
		//从订单树种获取属性信息
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		if(logsServices ==null)
			logsServices = SpringContextHolder.getBean("logsServices");
		AttrInstBusiRequest queryAttrInst = logsServices.getAttrInstBusiRequestByOrderId(order_id, field_name);
		if(queryAttrInst !=null)
			value = queryAttrInst.getField_value();
		//add by wui转换后拿值
		if(StringUtils.isEmpty(value)){
			String s_field_name = getFieldNameByAttrTableFieldName(field_name,table_name) ;
			if(!StringUtils.isEmpty(s_field_name) && !s_field_name.equals(field_name)){
				queryAttrInst = logsServices.getAttrInstBusiRequestByOrderId(order_id, s_field_name);
				if(queryAttrInst !=null)
					value = queryAttrInst.getField_value();
			}
		}
		return value == null ? EcsOrderConsts.EMPTY_STR_VALUE : value;
	}
	
	/**
	 * 获取属性值,适用固定值
	 * @param order_id 订单ID
	 * @param field_name 属性字段名
	 * @return
	 */
	@Deprecated
	public String getAttrFieldValue(OrderTreeBusiRequest tree,String order_id,String field_name,String table_name,String spec_id){
		//从订单树种获取属性信息
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		if(logsServices ==null)
			logsServices = SpringContextHolder.getBean("logsServices");
		AttrInstBusiRequest queryAttrInst = logsServices.getAttrInstBusiRequestByOrderId(order_id, field_name);
		if(queryAttrInst !=null)
			value = queryAttrInst.getField_value();
		//add by wui转换后拿值
		if(StringUtils.isEmpty(value)){
			String s_field_name = getFieldNameByAttrTableFieldName(field_name,table_name) ;
			if(!StringUtils.isEmpty(s_field_name) && !s_field_name.equals(field_name)){
				queryAttrInst = logsServices.getAttrInstBusiRequestByOrderId(order_id, s_field_name);
				if(queryAttrInst !=null)
					value = queryAttrInst.getField_value();
			}
		}
		return value == null ? EcsOrderConsts.EMPTY_STR_VALUE : value;
	}
	
	public String getAttrFieldValueFromDB(String order_id,String field_name){
		AttrValueGetReq req = new AttrValueGetReq();
		req.setOrder_id(order_id);
		req.setField_name(field_name);
		
		ZteClient client = getDubboZteClient();
		AttrValueGetResp response = client.execute(req, AttrValueGetResp.class);
		String field_value = response.getField_value();
		return field_value;
	}
	
	/**
	 * 更新属性值
	 * @param order_id 订单ID
	 * @param field_name 属性字段名
	 * @return
	 */
	public boolean updateAttrFieldValue(String order_id,String[] field_name, String[] field_value){
		boolean result = true;
		try{
			//从订单树种获取属性信息
			for(int i = 0; i < field_name.length; i++){
				String fieldName = field_name[i];
				String fieldValue = field_value[i];
				boolean isExist = false;
//				for(AttrInstBusiRequest attr : attrs){
//					if(fieldName.equals(attr.getField_name())){//如果field_name等于传入的key，返回其属性值
//						isExist= true;
//						break;
//					}
//				}
				if(logsServices ==null)
					logsServices = SpringContextHolder.getBean("logsServices");
				AttrInstBusiRequest queryAttrInst = logsServices.getAttrInstBusiRequestByOrderId(order_id, fieldName);
				if(queryAttrInst !=null)
					isExist = true;
				
				if(!isExist) //不存在，则首次直接插入 ,add by wui
				{
					AttrInstBusiRequest attrInstBusiRequest = new AttrInstBusiRequest();
					attrInstBusiRequest.setAttr_inst_id(UUIDUtil.getUUID());
					attrInstBusiRequest.setOrder_id(order_id);
					attrInstBusiRequest.setField_name(fieldName);
					attrInstBusiRequest.setField_value(fieldValue);
					attrInstBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrInstBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrInstBusiRequest.setCreate_date(Consts.SYSDATE);
					attrInstBusiRequest.store();
				}else{
//					for(AttrInstBusiRequest attr : attrs){
//						if(fieldName.equals(attr.getField_name())){//如果field_name等于传入的key，返回其属性值
							queryAttrInst.setField_value(fieldValue);
							queryAttrInst.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							queryAttrInst.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							queryAttrInst.store();
//						}
//					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * 根据字典编码取静态数据【es_dc_public_ext】
	 * @param stype
	 * @return
	 */
	public List listDcPublicData(String stype){
		ZteClient client = getDubboZteClient();
		DcPublicExtListReq req = new DcPublicExtListReq();
		req.setStype(stype);
		DcPublicExtListResp response = client.execute(req, DcPublicExtListResp.class);
		List datas = response.getStaticList();
		return datas;
	}
	/**
	 * 根据字典编码和pkey取静态数据pname【es_dc_public_ext】
	 * @param stype
	 * @param pkey
	 * @return pname
	 */
	public String  getDcPublicDataByPkey(String stype,String pkey){
		String pname="";
		try {
			if(stype!=null&&!stype.equals("")&&pkey!=null&&!pkey.equals("")){
				List<Map> list=this.listDcPublicData(stype);
				if(list!=null){
					for (Map map : list) {
						 if(map!=null&&!map.isEmpty()){
							 if(map.get("pkey")!=null&&map.get("pname")!=null){
								 if(pkey.equals(map.get("pkey").toString())){
									 pname=map.get("pname").toString();
									 break;
								 }
							 }
							
						 }
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pname;
	}
	
	/**
	 * 根据字典编码取静态数据【es_dc_sql】
	 * @param stype
	 * @return
	 */
	public List listDropDownData(String attrCode){
		ZteClient client = getDubboZteClient();
		DropdownDataReq req = new DropdownDataReq();
		req.setAttrCode(attrCode);
		DropdownDataResp response = client.execute(req, DropdownDataResp.class);
		List datas = response.getDropDownList();
		return datas;
	}	
	
	/**
	 * 取系统映射值
	 * @param stype 字典编码
	 * @param dict_code 原始值
	 * @return
	 */
	public String getDictCodeValue(String stype, String other_dict_code){
		if(StringUtils.isEmpty(stype) || StringUtils.isEmpty(other_dict_code))
			return null;
		ZteClient client = getDubboZteClient();
		DcPublicInfoCacheProxyReq req = new DcPublicInfoCacheProxyReq();
		req.setStype(stype);
		req.setValue(other_dict_code);
		req.setValue_from(EcsOrderConsts.VALUE_FROM_COL_1);
		
		DcPublicInfoCacheProxyResp response = client.execute(req, DcPublicInfoCacheProxyResp.class);
		String dict_code = response.getDict_relation_value();
		return dict_code;
	}
	
	/**
	 * 取外系统映射值
	 * @param stype 字典编码
	 * @param dict_code 原始值
	 * @return
	 */
	public String getOtherDictCodeValue(String stype, String dict_code){
		if(StringUtils.isEmpty(stype) || StringUtils.isEmpty(dict_code))
			return null;
		ZteClient client = getDubboZteClient();
		DcPublicInfoCacheProxyReq req = new DcPublicInfoCacheProxyReq();
		req.setStype(stype);
		req.setValue(dict_code);
		req.setValue_from(EcsOrderConsts.VALUE_FROM_COL_2);
		
		DcPublicInfoCacheProxyResp response = client.execute(req, DcPublicInfoCacheProxyResp.class);
		String other_dict_code = response.getDict_relation_value();
		return other_dict_code;
	}
	
	public String getOtherDictCodeValueDesc(String stype, String dict_code){
		if(StringUtils.isEmpty(stype) || StringUtils.isEmpty(dict_code))
			return null;
		ZteClient client = getDubboZteClient();
		DcPublicInfoCacheProxyReq req = new DcPublicInfoCacheProxyReq();
		req.setStype(stype);
		req.setValue(dict_code);
		req.setValue_from(EcsOrderConsts.VALUE_FROM_COL_2);
		
		DcPublicInfoCacheProxyResp response = client.execute(req, DcPublicInfoCacheProxyResp.class);
		String other_dict_code_desc = response.getDict_relation_value_desc();
		return other_dict_code_desc;
	}
	
	public String getZbDictCodeValue(String other_system, String field_name, String field_attr_id, String field_value){
		ZteClient client = getDubboZteClient();
		ZbDictCodeValueGetReq req = new ZbDictCodeValueGetReq();
		req.setOther_system(other_system);
		req.setField_name(field_name);
		req.setField_attr_id(field_attr_id);
		req.setField_value(field_value);
		ZbDictCodeValueGetResp response = client.execute(req, ZbDictCodeValueGetResp.class);
		return response.getZb_field_value();
	}

	/**
	 * 取物流公司联系人
	 * @param post_id 物流公司编码
	 * @param city_code 地市编码
	 * @return
	 */
	public Map getLogiCompPersonData(String post_id, String city_code){
		if(StringUtils.isEmpty(post_id) || StringUtils.isEmpty(city_code))
			return null;
		ZteClient client = getDubboZteClient();
		LogiCompPersonGetReq req = new LogiCompPersonGetReq();
		req.setCity_code(city_code);
		req.setPost_id(post_id);
		
		LogiCompPersonGetResp response = client.execute(req, LogiCompPersonGetResp.class);
		Map datas = response.getStaticList();
		return datas;
	}
	/**
	 * 取订单外部状态
	 * @param stype 字典编码，见EcsOrderConsts.java
	 * @return  	
	 */
	public List getDictRelationListData(String stype){
		if(StringUtils.isEmpty(stype))
			return null;
		ZteClient client = getDubboZteClient();
		DictRelationListReq req=new DictRelationListReq();
		req.setStype(stype);
		DictRelationListResp resp= client.execute(req, DictRelationListResp.class);
		List datas=resp.getAttrList();
		return datas;
	}
	
	/**
	 * 
	 * @param type_id 商品大类
	 * @return 总部映射值
	 */
	public String getZbGoodsType(String type_id){
		String goods_type = EcsOrderConsts.EMPTY_STR_VALUE;
		if(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(type_id)){
			goods_type = EcsOrderConsts.ZB_GOODS_TYPE_ZHKL;
		}
		else if(EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(type_id)){
			goods_type = EcsOrderConsts.ZB_GOODS_TYPE_ZSWK;
		}
		else if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)){
			goods_type = EcsOrderConsts.ZB_GOODS_TYPE_ZZDL;
		}
		else if(EcsOrderConsts.GOODS_TYPE_PHONE.equals(type_id)){
			goods_type = EcsOrderConsts.ZB_GOODS_TYPE_ZLZD;
		}
		else if(EcsOrderConsts.GOODS_TYPE_ADJUNCT.equals(type_id)){
			goods_type = EcsOrderConsts.ZB_GOODS_TYPE_ZPJL;
		}
		
		return goods_type;
	}
	
	/**
	 * 根据商品type_id获取总部实物货品类型
	 * @param type_id
	 * @return
	 */
	public String getZbProductType(String type_id){
		String product_type = EcsOrderConsts.EMPTY_STR_VALUE;
		if(EcsOrderConsts.GOODS_TYPE_PHONE.equals(type_id) || EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)){
			product_type = EcsOrderConsts.ZB_PRODUCT_TYPE_SJL;
		}
		else if(EcsOrderConsts.GOODS_TYPE_ADJUNCT.equals(type_id)){
			product_type = EcsOrderConsts.ZB_PRODUCT_TYPE_PJL;
		}
		else if(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(type_id)){
			product_type = EcsOrderConsts.ZB_PRODUCT_TYPE_OTH;
		}
		else if(EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(type_id)){
			product_type = EcsOrderConsts.ZB_PRODUCT_TYPE_SWK;
		}
		return product_type;
	}
	

	/**
	 * 根据商品field_name查询映射到总部的值
	 * @param field_name 属性名称
	 * @return
	 */
	public String getZbValue(String order_id, String field_name){
		String zbValue = EcsOrderConsts.EMPTY_STR_VALUE;
		AttrInstBusiRequest attrInstBusiRequest =  AttrUtils.getInstance().getAttrBusiRequest(order_id, field_name,"","");
		if (attrInstBusiRequest != null) {
			zbValue = attrInstBusiRequest.getZBValue();
		}

		return zbValue == null ? EcsOrderConsts.EMPTY_STR_VALUE : zbValue;
	}
	
	/**
	 * 获取销售方式
	 * @param order_id
	 * @return
	 */
	public String getSaleMode(String order_id){
		String sale_mode = EcsOrderConsts.EMPTY_STR_VALUE;
		String package_sale = getAttrFieldValue(order_id,AttrConsts.PACKAGE_SALE,"","");
		if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(package_sale)||StringUtils.isEmpty(package_sale)){
			sale_mode = EcsOrderConsts.SALE_MODE_FCPB;
		}else if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(package_sale)){
			sale_mode = EcsOrderConsts.SALE_MODE_CPB;
		}else{
			sale_mode = EcsOrderConsts.SALE_MODE_FCPB;
		}
		return sale_mode;
	}
	
	/**
	 * 判断是否总部订单
	 * @param plat_type
	 * @return
	 */
	public boolean isZbOrder(String plat_type){
		String dict_value = this.getDictCodeValue(StypeConsts.ISZBMALL, plat_type);
		boolean isZbOrder = false;
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(dict_value)){
			isZbOrder = true;
		}
		return isZbOrder;
	}
	
	public String getOperatorCode(String order_id){
		String order_city_code = this.getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE,"","");
		String lan_code = SpecUtils.getLanCode(order_city_code);
		if("440100".equals(order_city_code))
			lan_code="0020";
		if("330100".equals(order_city_code))
			lan_code="0571";
		List<Map> codes = this.listDcPublicData(StypeConsts.OPERATOR_CODE);
		String operator_code = null;
		if(codes!=null && codes.size()>0){
			for(Map code : codes){
				String pkey = code.get("pkey").toString();
				if(!StringUtils.isEmpty(lan_code) && lan_code.equals(pkey)){
					operator_code = (String) code.get("pname");
					break ;
				}
			}
		}
		return operator_code;
	}
	
	public AdminUser getOperatorInfo(String userid){
		ZbAdminUserGetReq req = new ZbAdminUserGetReq();
		req.setUserid(userid);
		ZteClient client = getDubboZteClient();
		ZbAdminUserGetResp response = client.execute(req, ZbAdminUserGetResp.class);
		return response.getAdminUser();
	}
	
	/**
	 * 商品附属信息
	 * @param order_id
	 * @return
	 */
	public List<GoodsAtt> getGoodsAttrInfo(String order_id){
		String goods_type = CommonDataFactory.getInstance().getGoodsType(order_id);
		List<GoodsAtt> goodsAtt = null;
		if(EcsOrderConsts.ZB_GOODS_TYPE_ZHKL.equals(goods_type)){
			goodsAtt = getGoodsAttHK(order_id);
		}
		else if(EcsOrderConsts.ZB_GOODS_TYPE_ZHYL.equals(goods_type)){
			goodsAtt = getGoodsAttHKHY(order_id);
		}
		else if(EcsOrderConsts.ZB_GOODS_TYPE_ZSWK.equals(goods_type)){
			//上网卡
			goodsAtt = getGoodsAttZBWXSWK(order_id);
		}
		else if(EcsOrderConsts.ZB_GOODS_TYPE_ZZDL.equals(goods_type)){
			//合约机
			goodsAtt = getGoodsAttZDHY(order_id);
		}
		else if(EcsOrderConsts.ZB_GOODS_TYPE_ZLZD.equals(goods_type)){
			//裸机
			goodsAtt = getGoodsAttZBLJZD(order_id);
		}
		else if(EcsOrderConsts.ZB_GOODS_TYPE_ZPJL.equals(goods_type)){
			//配件
			goodsAtt = getGoodsAttZBPJ(order_id);
		}
		/*
		 * ZX add 2016-01-06 start 组装模版
		 */
		assembleTmp(goodsAtt, order_id);
		/*
		 * ZX add 2016-01-06 end 组装模版
		 */
		return goodsAtt;
	}
	
	/**
	 * 终端合约【合约机】
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<GoodsAtt> getGoodsAttZDHY(String order_id){
		List<GoodsAtt> list = new ArrayList<GoodsAtt>();
		GoodsAttZDHY goodsAtt = new GoodsAttZDHY();
		goodsAtt.setCertType(this.getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE,"",""));
		goodsAtt.setCertNum(this.getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM,"",""));
		goodsAtt.setCertAddr(this.getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS,"",""));
		goodsAtt.setCertAddr(this.getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS,"",""));
		goodsAtt.setCustomerName(this.getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME,"",""));
		goodsAtt.setCustomerType(this.getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE,"",""));
		
		SimpleDateFormat simpleFormat = new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cert_failure_time = null;
		try {
			cert_failure_time = simpleFormat.format(dateFormat.parse(this.getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME,"","")));
		} catch (ParseException e) {
			e.printStackTrace();
			cert_failure_time = EcsOrderConsts.DEFAULT_CERT_FAILURE_TIME;
		}
		goodsAtt.setCertLoseTime(cert_failure_time);
		String RefereeName = this.getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_NAME,"","");
		goodsAtt.setRefereeName(RefereeName==null?EcsOrderConsts.EMPTY_STR_VALUE:RefereeName);
		String RefereeNum = this.getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_PHONE,"","");
		goodsAtt.setRefereeNum(RefereeNum==null?EcsOrderConsts.EMPTY_STR_VALUE:RefereeNum);
		goodsAtt.setDevelopCode(this.getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_CODE,"",""));
		goodsAtt.setDevelopName(this.getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_NAME,"",""));
		// goodsAtt.setPhoneNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
		PhoneInfo phoneInfo = this.getPhoneInfo(order_id);
		goodsAtt.setPhoneInfo(phoneInfo);
		goodsAtt.setReliefPresFlag(this.getAttrFieldValue(order_id, AttrConsts.RELIEF_PRES_FLAG,"",""));
		goodsAtt.setSaleMode(this.getSaleMode(order_id));
		goodsAtt.setSimId(this.getAttrFieldValue(order_id, AttrConsts.ICCID,"",""));
		String cardType = this.getAttrFieldValue(order_id, AttrConsts.WHITE_CART_TYPE,"","");
		cardType = (EcsOrderConsts.WHITE_CARD_TYPE_NONE.equals(cardType) || StringUtils.isEmpty(cardType)) ? "": cardType;
		goodsAtt.setCardType(cardType);
		goodsAtt.setProductCode(this.getAttrFieldValue(order_id, AttrConsts.OUT_PLAN_ID_ESS,"",""));
		goodsAtt.setProductName(SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE));
		goodsAtt.setProductNet(SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE));
		goodsAtt.setProductBrand(this.getAttrFieldValue(order_id, AttrConsts.PROD_BRAND,"",""));
		goodsAtt.setFirstMonBillMode(this.getAttrFieldValue(order_id, AttrConsts.FIRST_PAYMENT,"",""));
		goodsAtt.setSerType(SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE));
		String net_type = SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		//取套餐货品cat_id
		String tc_cat_id = SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.CAT_ID);
		if(EcsOrderConsts.NET_TYPE_4G.equals(net_type)){
			String diy_taocan = this.getDictCodeValue(StypeConsts.IS_4G_DIYTAOCAN, tc_cat_id);
			String base_taocan = this.getDictCodeValue(StypeConsts.IS_3G4GYTH, tc_cat_id);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(diy_taocan)){
				goodsAtt.setProductType(EcsOrderConsts.OFFER_TYPE_4G_DIY);
			}
			else if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(base_taocan)){
				goodsAtt.setProductType(EcsOrderConsts.OFFER_TYPE_4G_MAIN);
			}
		}
		else{
			goodsAtt.setProductType(EcsOrderConsts.EMPTY_STR_VALUE);
		}
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		goodsAtt.setUserType(is_old==null?EcsOrderConsts.ZB_USER_TYPE_NEW:EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_old)?EcsOrderConsts.ZB_USER_TYPE_OLD:EcsOrderConsts.ZB_USER_TYPE_NEW);
		String goods_cat_id = SpecUtils.getGoodsSpec(order_id, null, SpecConsts.CAT_ID);
		String is_customized = getDictCodeValue(StypeConsts.IS_CUSTOMIZED,goods_cat_id);
	    goodsAtt.setIsCustomized(EcsOrderConsts.IS_CUSTOMIZED_YES.equals(is_customized)?EcsOrderConsts.ZB_IS_CUSTOMIZED_TRUE:EcsOrderConsts.ZB_IS_CUSTOMIZED_FALSE);
		goodsAtt.setResourcesInfo(getResources(order_id));
		goodsAtt.setActivityInfo(getActivityList(order_id));
		goodsAtt.setPackage(getPackages(order_id));
		List<SubProd> subProds = getSubProductInfos(order_id, EcsOrderConsts.ZB_CARD_TYPE_ZHU); // ZX add 2016-01-11 增加附加信息
		goodsAtt.setSubProdInfo(/*new ArrayList<SubProd>()*/subProds);
		goodsAtt.setSex(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_SEX));
		String is_examine_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_EXAMINE_CARD);
		String checktype = "03";
		if("1".equals(is_examine_card)){
			checktype = "02";
		}else if("0".equals(is_examine_card)){
			checktype = "03";
		}
		goodsAtt.setCheckType(checktype);
		
		/**
		 * ZX add 2015-12-28 start 集团编码
		 */
		goodsAtt.setGroupId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GROUP_CODE));
		goodsAtt.setGoodsType(CommonDataFactory.getInstance().getGoodsType(order_id));
		goodsAtt.setNumType(EcsOrderConsts.ZB_CARD_TYPE_ZHU);
		/**
		 * ZX add 2015-12-28 end 集团编码
		 */
		
		list.add(goodsAtt);
		return list;
	}
	
	/**
	 * 获取终端信息
	 * @return
	 */
	private Resources getResources(String order_id){
		Resources resources = null;
		List<Goods> products = SpecUtils.getEntityProducts(order_id);
		Goods entity = null;
		for(int i=0;i<products.size();i++){
			Goods product = products.get(i);
			//取手机终端，上网卡硬件，配件货品
			if(SpecConsts.TYPE_ID_10000.equals(product.getType_id()) 
					|| SpecConsts.TYPE_ID_10006.equals(product.getType_id())
					|| SpecConsts.TYPE_ID_10003.equals(product.getType_id())){
				entity = product;
				break;
			}
		}
		if(entity!=null && !StringUtils.isEmpty(entity.getBrand_code()) 
				&& !StringUtils.isEmpty(entity.getColor()) && !StringUtils.isEmpty(entity.getModel_code()) && !StringUtils.isEmpty(entity.getSn())){
			resources = new Resources();
			resources.setResourcesBrand(entity.getBrand_code());//SpecUtils.getProductSpec(order_id, type_id, null, SpecConsts.BRAND_CODE)
//			String terminal_num = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getTerminal_num();
//			if(StringUtils.isEmpty(terminal_num)){
//				terminal_num = CommonDataFactory.getInstance().getTerminalNum(order_id);
//			}
			resources.setResourcesCode(CommonDataFactory.getInstance().getTerminalNum(order_id));
			resources.setResourcesColor(entity.getColor());
			resources.setResourcesFittings(EcsOrderConsts.EMPTY_STR_VALUE);
			resources.setResourcesModel(entity.getModel_code());//this.getAttrFieldValue(order_id, AttrConsts.SPECIFICATION_CODE)
			resources.setResourcesTypeId(entity.getSn());//SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.GOODS_SN)
		}
		return resources;
	}
	
	/**
	 * 裸机
	 * @param order_id
	 * @return
	 */
	private List<GoodsAtt> getGoodsAttZBLJZD(String order_id){
		List<GoodsAtt> list = new ArrayList<GoodsAtt>();
		GoodsAttZBLJZD goodsAtt = new GoodsAttZBLJZD();
		String cat_id = SpecUtils.getGoodsSpec(order_id, null, SpecConsts.CAT_ID);
		String is_customized = getDictCodeValue(StypeConsts.IS_CUSTOMIZED,cat_id);
	    goodsAtt.setIsCustomized(EcsOrderConsts.IS_CUSTOMIZED_YES.equals(is_customized)?EcsOrderConsts.ZB_IS_CUSTOMIZED_TRUE:EcsOrderConsts.ZB_IS_CUSTOMIZED_FALSE);
	    Resources resources = getResources(order_id);
	    if(resources!=null){
	    	goodsAtt.setResourcesInfo(resources);
	    }
	    goodsAtt.setGoodsType(CommonDataFactory.getInstance().getGoodsType(order_id));
		list.add(goodsAtt);
		return list;
	}
	
	/**
	 * 配件
	 * @param order_id
	 * @return
	 */
	private List<GoodsAtt> getGoodsAttZBPJ(String order_id){
		List<GoodsAtt> list = new ArrayList<GoodsAtt>();
		GoodsAttZBPJ goodsAtt = new GoodsAttZBPJ();
		String prod_cat_id = SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, SpecConsts.CAT_ID);
		String accessory_type = AttrUtils.getInstance().getDictCodeValue(StypeConsts.ACCESSORY_TYPE, prod_cat_id);
		goodsAtt.setPartsType(accessory_type);
		Resources resources = getResources(order_id);
	    if(resources!=null){
	    	goodsAtt.setResourcesInfo(resources);
	    }
	    goodsAtt.setGoodsType(CommonDataFactory.getInstance().getGoodsType(order_id));
		list.add(goodsAtt);
		return list;
	}
	
	/**
	 * 上网卡
	 * @param order_id
	 * @return
	 */
	private List<GoodsAtt> getGoodsAttZBWXSWK(String order_id){
		List<GoodsAtt> list = new ArrayList<GoodsAtt>();
		GoodsAttZBWXSWK goodsAtt = new GoodsAttZBWXSWK();
		goodsAtt.setCustomerName(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME));
		goodsAtt.setCertType(this.getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE,"",""));
		goodsAtt.setCertNum(this.getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM,"",""));
		goodsAtt.setCustomerType(this.getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE,"",""));
		String certLoseTime = CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME);
				if(null != certLoseTime && !EcsOrderConsts.EMPTY_STR_VALUE.equals(certLoseTime)){
					certLoseTime = certLoseTime.replaceAll("-", "");
					certLoseTime = certLoseTime.replaceAll(":", "");
					certLoseTime = certLoseTime.replaceAll(" ", "");
				}
		goodsAtt.setCertLoseTime(certLoseTime);
		goodsAtt.setCertAddr(this.getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS,"",""));
		goodsAtt.setRefereeName(this.getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_NAME,"",""));
		goodsAtt.setRefereeNum(this.getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_PHONE,"",""));
		goodsAtt.setDevelopCode(this.getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_CODE,"",""));
		goodsAtt.setDevelopName(this.getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_NAME,"",""));
		//goodsAtt.setPhoneNum(this.getAttrFieldValue(order_id, AttrConsts.PHONE_NUM,"",""));
		PhoneInfo phoneInfo = this.getPhoneInfo(order_id);
		goodsAtt.setPhoneInfo(phoneInfo);
		goodsAtt.setSaleMode(this.getSaleMode(order_id));
		String cardType = this.getAttrFieldValue(order_id, AttrConsts.WHITE_CART_TYPE,"","");
		cardType = (EcsOrderConsts.WHITE_CARD_TYPE_NONE.equals(cardType) || StringUtils.isEmpty(cardType)) ? "": cardType;
		goodsAtt.setCardType(cardType);
		goodsAtt.setProductCode(this.getAttrFieldValue(order_id, AttrConsts.OUT_PLAN_ID_ESS,"",""));
		
		String product_name = SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE);
		goodsAtt.setProductName(product_name==null?EcsOrderConsts.EMPTY_STR_VALUE:product_name);
		String net_type = SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		String product_net = StringUtils.isEmpty(net_type)?EcsOrderConsts.NET_TYPE_3G:net_type;
		goodsAtt.setProductNet(product_net);
		goodsAtt.setProductBrand(product_net+"WK");
		goodsAtt.setSerType(SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE));
		goodsAtt.setProPacCode(EcsOrderConsts.EMPTY_STR_VALUE);
		goodsAtt.setProPacDesc(EcsOrderConsts.EMPTY_STR_VALUE);
		List<SubProd> subProds = getSubProductInfos(order_id, EcsOrderConsts.ZB_CARD_TYPE_ZHU); // ZX add 2016-01-11 增加附加信息
		goodsAtt.setSubProdInfo(/*new ArrayList<SubProd>()*/subProds);
		Resources resources = getResources(order_id);
	    if(resources!=null){
	    	goodsAtt.setResourcesInfo(resources);
	    }
		goodsAtt.setActivityInfo(null);
		goodsAtt.setSex(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_SEX));
		String is_examine_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_EXAMINE_CARD);
		String checktype = "03";
		if("1".equals(is_examine_card)){
			checktype = "02";
		}else if("0".equals(is_examine_card)){
			checktype = "03";
		}
		goodsAtt.setCheckType(checktype);
		
		/**
		 * ZX add 2015-12-28 start 集团编码
		 */
		goodsAtt.setGroupId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GROUP_CODE));
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		if (EcsOrderConsts.IS_OLD_0.equals(is_old)) {
			goodsAtt.setUserType(EcsOrderConsts.ZB_USER_TYPE_NEW);
		} else if (EcsOrderConsts.IS_OLD_1.equals(is_old)) {
			goodsAtt.setUserType(EcsOrderConsts.ZB_USER_TYPE_OLD);
		}
		goodsAtt.setGoodsType(CommonDataFactory.getInstance().getGoodsType(order_id));
		goodsAtt.setNumType(EcsOrderConsts.ZB_CARD_TYPE_ZHU);
		/**
		 * ZX add 2015-12-28 end 集团编码
		 */
		
		list.add(goodsAtt);
		
		return list;
	}
	
	/**
	 * 号卡合约
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<GoodsAtt> getGoodsAttHKHY(String order_id){

		List<GoodsAtt> list = new ArrayList<GoodsAtt>();
		GoodsAttHKHY goodsAtt = new GoodsAttHKHY();
		String cardType = this.getAttrFieldValue(order_id, AttrConsts.WHITE_CART_TYPE,"","");
		cardType = (EcsOrderConsts.WHITE_CARD_TYPE_NONE.equals(cardType) || StringUtils.isEmpty(cardType)) ? "": cardType;
		goodsAtt.setCardType(cardType);
		
		goodsAtt.setCertAddr(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS));
		String certLoseTime = CommonDataFactory.getInstance()
		.getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME);
		if(null != certLoseTime && !EcsOrderConsts.EMPTY_STR_VALUE.equals(certLoseTime)){
			certLoseTime = certLoseTime.replaceAll("-", "");
			certLoseTime = certLoseTime.replaceAll(":", "");
			certLoseTime = certLoseTime.replaceAll(" ", "");
		}
		goodsAtt.setCertLoseTime(certLoseTime);
		goodsAtt.setCertNum(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM));
		goodsAtt.setCertType(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE));
		goodsAtt.setCustomerName(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME));
		goodsAtt.setCustomerType(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE));
		goodsAtt.setDevelopCode(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_CODE));
		goodsAtt.setDevelopName(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_NAME));
		goodsAtt.setFirstMonBillMode(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.FIRST_PAYMENT));
		// goodsAtt.setPhoneNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
		PhoneInfo phoneInfo = this.getPhoneInfo(order_id);
		goodsAtt.setPhoneInfo(phoneInfo);
		goodsAtt.setProductBrand(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.PROD_BRAND));
		goodsAtt.setProductCode(CommonDataFactory.getInstance()
				.getProductSpec(order_id, EcsOrderConsts.PRODUCT_TYPE_OFFER, null, SpecConsts.ESS_CODE));
		goodsAtt.setProductName(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.PLAN_TITLE));
		goodsAtt.setProductNet(CommonDataFactory.getInstance()
				.getProductSpec(order_id, EcsOrderConsts.PRODUCT_TYPE_OFFER, null, SpecConsts.NET_TYPE));
		goodsAtt.setRefereeName(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_NAME));
		
		goodsAtt.setRefereeNum(CommonDataFactory.getInstance()
				.getZbValue(order_id, AttrConsts.RECOMMENDED_PHONE));
		
		goodsAtt.setReliefPresFlag(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.RELIEF_PRES_FLAG));
		goodsAtt.setSaleMode(CommonDataFactory.getInstance().getSaleMode(order_id));
		goodsAtt.setSerType(CommonDataFactory.getInstance()
				.getProductSpec(order_id, EcsOrderConsts.PRODUCT_TYPE_OFFER, null, SpecConsts.PAY_TYPE));
		goodsAtt.setSimId(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.ICCID));

		String net_type = SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		//取套餐货品cat_id
		String tc_cat_id = SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.CAT_ID);
		if(EcsOrderConsts.NET_TYPE_4G.equals(net_type)){
			String diy_taocan = this.getDictCodeValue(StypeConsts.IS_4G_DIYTAOCAN, tc_cat_id);
			String base_taocan = this.getDictCodeValue(StypeConsts.IS_3G4GYTH, tc_cat_id);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(diy_taocan)){
				goodsAtt.setProductType(EcsOrderConsts.OFFER_TYPE_4G_DIY);
			}
			else if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(base_taocan)){
				goodsAtt.setProductType(EcsOrderConsts.OFFER_TYPE_4G_MAIN);
			}
			goodsAtt.setPackage(getPackages(order_id));
		}
		else{
			goodsAtt.setProductType(EcsOrderConsts.EMPTY_STR_VALUE);
			goodsAtt.setPackage(null);
		}
		goodsAtt.setActivityInfo(getActivityList(order_id));
		List<SubProd> subProds = getSubProductInfos(order_id, EcsOrderConsts.ZB_CARD_TYPE_ZHU); // ZX add 2016-01-11 增加附加信息
		goodsAtt.setSubProdInfo(/*new ArrayList<SubProd>()*/subProds);
		goodsAtt.setSex(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_SEX));
		String is_examine_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_EXAMINE_CARD);
		String checktype = "03";
		if("1".equals(is_examine_card)){
			checktype = "02";
		}else if("0".equals(is_examine_card)){
			checktype = "03";
		}
		goodsAtt.setCheckType(checktype);
		
		/**
		 * ZX add 2015-12-28 start 集团编码
		 */
		goodsAtt.setGroupId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GROUP_CODE));
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		if (EcsOrderConsts.IS_OLD_0.equals(is_old)) {
			goodsAtt.setUserType(EcsOrderConsts.ZB_USER_TYPE_NEW);
		} else if (EcsOrderConsts.IS_OLD_1.equals(is_old)) {
			goodsAtt.setUserType(EcsOrderConsts.ZB_USER_TYPE_OLD);
		}
		goodsAtt.setGoodsType(CommonDataFactory.getInstance().getGoodsType(order_id));
		goodsAtt.setNumType(EcsOrderConsts.ZB_CARD_TYPE_ZHU);
		/**
		 * ZX add 2015-12-28 end 集团编码
		 */
		
		list.add(goodsAtt);
		return list;
	}
	
	private List<Package> getPackages(String order_id){
		List<AttrPackageBusiRequest> packages = CommonDataFactory.getInstance().getOrderTree(order_id).getPackageBusiRequests();
		List<Package> packageList = new ArrayList<Package>();
		for(AttrPackageBusiRequest packageBusi :packages){
			Package pkg = new Package();
			pkg.setPackageCode(packageBusi.getPackageCode());
			pkg.setPackageName(packageBusi.getPackageName());
			//pkg.setProductCode(packageBusi.getProductCode());
			pkg.setElementCode(packageBusi.getElementCode());
			pkg.setElementName(packageBusi.getElementName());
			pkg.setElementType(packageBusi.getElementType());
			//pkg.setOperType(packageBusi.getOperType());
			//pkg.setChageType(packageBusi.getChageType());
			//pkg.setBizType(packageBusi.getBizType());
			packageList.add(pkg);
		}
		return packageList;
	}
	
	/**
	 * 合约计划信息
	 * @param order_id
	 * @return
	 */
	private List getActivityList(String order_id){
		List<Activity> activities = new ArrayList<Activity>();
		Activity activity = null;
		
		List<OrderActivityBusiRequest> orderActivityList = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderActivityBusiRequest();
		if (orderActivityList != null && orderActivityList.size() > 0) {
			for (OrderActivityBusiRequest orderActivity : orderActivityList) {
				if (!StringUtil.isEmpty(orderActivity.getActivity_code())) {
					activity = new Activity();
					String activeType = orderActivity.getActivity_type();
					String actProtPer = orderActivity.getAct_prot_per();
					//合约类型为空时取商品配置数据
					if(StringUtils.isEmpty(activeType)){
						activeType = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, SpecConsts.PACKAGE_TYPE);
					}
					//合约期限为空时取商品配置数据
					if(StringUtils.isEmpty(actProtPer)){
						actProtPer = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, SpecConsts.PACKAGE_LIMIT);
					}
					activity.setActivityType(activeType);
					activity.setActivityCode(orderActivity.getActivity_code());
					activity.setActProtPer(actProtPer);
					activity.setActivityName(orderActivity.getActivity_name());
					/*
					 * ZX add 2016-01-08 start 送总部传空数组
					 */
					List<Package> packages = new ArrayList<Package>();
					activity.setPackage(packages);
					/*
					 * ZX add 2016-01-08 end 送总部传空数组
					 */
					activities.add(activity);
				}
			}
		}
		
		// 如果之前未记录总部传下来的数据，则保留原始逻辑
		if (activities == null || activities.size() <= 0) {
			activity = new Activity();
			String prod_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PROD_CAT_ID);
			activity.setActivityType(CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_ACTIVITY_TYPE, prod_cat_id));
			activity.setActivityCode(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_PACKAGE_ID));
			String actProtPer = CommonDataFactory.getInstance().getProductSpec(order_id, 
					SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT);
			activity.setActProtPer(actProtPer == null ? EcsOrderConsts.EMPTY_STR_VALUE : actProtPer);
			String activityName = CommonDataFactory.getInstance().getProductSpec(order_id, 
					SpecConsts.TYPE_ID_10001, null, SpecConsts.GOODS_NAME);
			activity.setActivityName(activityName == null ? EcsOrderConsts.EMPTY_STR_VALUE : activityName);
			/*
			 * ZX add 2016-01-08 start 送总部传空数组
			 */
			List<Package> packages = new ArrayList<Package>();
			activity.setPackage(packages);
			/*
			 * ZX add 2016-01-08 end 送总部传空数组
			 */
			activities.add(activity);
		}
		return activities;
	}
	
	/**
	 * 号卡
	 * @param order_id
	 * @return
	 */
	private List<GoodsAtt> getGoodsAttHK(String order_id){
		List<GoodsAtt> list = new ArrayList<GoodsAtt>();
		GoodsAttHK goodsAtt = new GoodsAttHK();
		String cardType = this.getAttrFieldValue(order_id, AttrConsts.WHITE_CART_TYPE,"","");
		cardType = (EcsOrderConsts.WHITE_CARD_TYPE_NONE.equals(cardType) || StringUtils.isEmpty(cardType)) ? "": cardType;
		goodsAtt.setCardType(cardType);
		goodsAtt.setCertAddr(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS));
		String certLoseTime = CommonDataFactory.getInstance()
		.getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME);
		if(null != certLoseTime && !EcsOrderConsts.EMPTY_STR_VALUE.equals(certLoseTime)){
			certLoseTime = certLoseTime.replaceAll("-", "");
			certLoseTime = certLoseTime.replaceAll(":", "");
			certLoseTime = certLoseTime.replaceAll(" ", "");
		}
		goodsAtt.setPackage(getPackages(order_id));
		goodsAtt.setCertLoseTime(certLoseTime);
		goodsAtt.setCertNum(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM));
		goodsAtt.setCertType(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE));
		goodsAtt.setCustomerName(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME));
		goodsAtt.setCustomerType(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE));
		goodsAtt.setDevelopCode(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_CODE));
		goodsAtt.setDevelopName(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_NAME));
		goodsAtt.setFirstMonBillMode(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.FIRST_PAYMENT));
		// goodsAtt.setPhoneNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
		PhoneInfo phoneInfo = this.getPhoneInfo(order_id);
		goodsAtt.setPhoneInfo(phoneInfo);
		goodsAtt.setProductBrand(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.PROD_BRAND));
		goodsAtt.setProductCode(CommonDataFactory.getInstance()
				.getProductSpec(order_id, EcsOrderConsts.PRODUCT_TYPE_OFFER, null, SpecConsts.ESS_CODE));
		String productName = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PRODUCTNAME);
		if(StringUtils.isEmpty(productName)){
			productName = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODSNAME);
		}
		goodsAtt.setProductName(productName);
		goodsAtt.setProductNet(CommonDataFactory.getInstance()
				.getProductSpec(order_id, EcsOrderConsts.PRODUCT_TYPE_OFFER, null, SpecConsts.NET_TYPE));
		String net_type = SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		//取套餐货品cat_id
		String tc_cat_id = SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.CAT_ID);
		if(EcsOrderConsts.NET_TYPE_4G.equals(net_type)){
			String diy_taocan = this.getDictCodeValue(StypeConsts.IS_4G_DIYTAOCAN, tc_cat_id);
			String base_taocan = this.getDictCodeValue(StypeConsts.IS_3G4GYTH, tc_cat_id);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(diy_taocan)){
				goodsAtt.setProductType(EcsOrderConsts.OFFER_TYPE_4G_DIY);
			}
			else if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(base_taocan)){
				goodsAtt.setProductType(EcsOrderConsts.OFFER_TYPE_4G_MAIN);
			}
			goodsAtt.setPackage(getPackages(order_id));
		}
		else{
			goodsAtt.setProductType(EcsOrderConsts.EMPTY_STR_VALUE);
			goodsAtt.setPackage(null);
		}
		goodsAtt.setRefereeName(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_NAME));
		
		goodsAtt.setRefereeNum(CommonDataFactory.getInstance()
				.getZbValue(order_id, AttrConsts.RECOMMENDED_PHONE));
		
		goodsAtt.setReliefPresFlag(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.RELIEF_PRES_FLAG));
		goodsAtt.setSaleMode(CommonDataFactory.getInstance().getSaleMode(order_id));
		goodsAtt.setSerType(CommonDataFactory.getInstance()
				.getProductSpec(order_id, EcsOrderConsts.PRODUCT_TYPE_OFFER, null, SpecConsts.PAY_TYPE));
		goodsAtt.setSimId(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.ICCID));
		List<SubProd> subProds = new ArrayList<SubProd>();				//附加产品信息
		subProds = getSubProductInfos(order_id, EcsOrderConsts.ZB_CARD_TYPE_ZHU); // ZX add 2016-01-11 增加附加信息
		goodsAtt.setSubProdInfo(subProds);
		goodsAtt.setSex(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_SEX));
		String is_examine_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_EXAMINE_CARD);
		String checktype = "03";
		if("1".equals(is_examine_card)){
			checktype = "02";
		}else if("0".equals(is_examine_card)){
			checktype = "03";
		}
		goodsAtt.setCheckType(checktype);

		/**
		 * ZX add 2015-12-28 start 集团编码
		 */
		goodsAtt.setGroupId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GROUP_CODE));
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		if (EcsOrderConsts.IS_OLD_0.equals(is_old)) {
			goodsAtt.setUserType(EcsOrderConsts.ZB_USER_TYPE_NEW);
		} else if (EcsOrderConsts.IS_OLD_1.equals(is_old)) {
			goodsAtt.setUserType(EcsOrderConsts.ZB_USER_TYPE_OLD);
		}
		goodsAtt.setGoodsType(CommonDataFactory.getInstance().getGoodsType(order_id));
		goodsAtt.setNumType(EcsOrderConsts.ZB_CARD_TYPE_ZHU);
		/**
		 * ZX add 2015-12-28 end 集团编码
		 */
		
		list.add(goodsAtt);
		
		return list;
	}
	
	public List<Fee> getFeeInfo(String order_id){
		List<Fee> fees = new ArrayList<Fee>();

		List<AttrFeeInfoBusiRequest> attrFeeInfoBusiRequests = CommonDataFactory
				.getInstance().getOrderTree(order_id).getFeeInfoBusiRequests();
		if(attrFeeInfoBusiRequests!=null && attrFeeInfoBusiRequests.size()>0){
			for (AttrFeeInfoBusiRequest attrFee : attrFeeInfoBusiRequests) {
				Fee fee = new Fee();
				fee.setFeeID(attrFee.getFee_item_code());
				fee.setFeeDes(attrFee.getFee_item_name());
				fee.setOrigFee(attrFee.getO_fee_num()==null?0D:attrFee.getO_fee_num()*1000);
				fee.setRealFee(attrFee.getN_fee_num()==null?0D:attrFee.getN_fee_num()*1000);
				fee.setReliefFee(attrFee.getDiscount_fee()==null?0D:attrFee.getDiscount_fee()*1000);
				fee.setReliefResult(attrFee.getDiscount_reason()==null?EcsOrderConsts.EMPTY_STR_VALUE:attrFee.getDiscount_reason());
				fees.add(fee);
			}
		}
		return fees;
	}
	
	public String getSimType(String order_id, String order_from, String value, Map orderAttrValues){
		String sim_type = EcsOrderConsts.SIM_TYPE_BK;
		//上网卡-卡类型判断
		String is_set = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.SWK_IS_SET);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_set)){
			sim_type =EcsOrderConsts.SIM_TYPE_CK;//成卡
		} else if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_set)){
			sim_type = EcsOrderConsts.SIM_TYPE_BK;//白卡
		}
		String type_id = (String) orderAttrValues.get("type_id");
		String hasNumber = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.ISHAVEMSISDN, type_id);
		String lipin_prod_cat = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10007, null, SpecConsts.CAT_ID);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(hasNumber)
				|| (EcsOrderConsts.GOODS_TYPE_LIPIN.equals(type_id) && EcsOrderConsts.PRODUCT_CAT_ID_4.equals(lipin_prod_cat))){
			String is_old =(String) orderAttrValues.get(AttrConsts.IS_OLD);
			String vicecard_no = (String) orderAttrValues.get(AttrConsts.VICECARD_NO);
			//合约机默认白卡
			if (Consts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)) {
				sim_type = EcsOrderConsts.SIM_TYPE_BK;//白卡
			}
			//VIP商城默认成卡 checkFieldValueExists("ischengka", order_from)
			if (EcsOrderConsts.ORDER_FROM_10034.equals(order_from) ) {
				sim_type =EcsOrderConsts.SIM_TYPE_CK;//成卡
			}
			//老用户默认成卡
			if (EcsOrderConsts.IS_OLD_1.equals(is_old) && StringUtils.isEmpty(vicecard_no)) {
				sim_type =EcsOrderConsts.SIM_TYPE_CK;//成卡
			}
			else if(EcsOrderConsts.IS_OLD_1.equals(is_old) && !StringUtils.isEmpty(vicecard_no)){
				sim_type = EcsOrderConsts.SIM_TYPE_BK;//白卡
			}
			//如果商城有值,以商城的为准
			if (StringUtils.isNotEmpty(value)) {
				sim_type = value;
			}
		}
		
		// 淘宝订单(默认取商品配置)
		if (CommonDataFactory.getInstance().isTbOrder(order_from)) {
			if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_set)) {
				sim_type =EcsOrderConsts.SIM_TYPE_CK;//成卡
			} else if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_set)){
				sim_type = EcsOrderConsts.SIM_TYPE_BK;//白卡
			}
		}
		
		if(!(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(type_id)||EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(type_id)
				||EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id))){
			sim_type =EcsOrderConsts.SIM_TYPE_CK;//成卡
		}
		//宽带默认白卡
		if(EcsOrderConsts.GOODS_TYPE_KD.equals(type_id)){
			sim_type =EcsOrderConsts.SIM_TYPE_BK;//白卡
		}
		return sim_type;
	}
	
	/**
	 * 查询全部信息
	 * @作者 MoChunrun
	 * @创建日期 2014-4-14 
	 * @param tid
	 * @param appkey
	 * @param secret
	 * @param sessionKey
	 * @param url
	 * @throws ApiException
	 */
	public TradeFullinfoGetResponse getFullTrade(long tid,String appkey,String secret,String sessionKey,String url) throws ApiException{
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		TradeFullinfoGetRequest req=new TradeFullinfoGetRequest();
		req.setFields("seller_nick,buyer_nick,title,type,created,tid,seller_rate,buyer_flag,buyer_rate,status,payment,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,buyer_memo,seller_memo,alipay_no,alipay_id,buyer_message,pic_path,num_iid,num,price,buyer_alipay_no,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,seller_flag,seller_alipay_no,seller_mobile,seller_phone,seller_name,seller_email,available_confirm_fee,has_post_fee,timeout_action_time,snapshot_url,cod_fee,cod_status,shipping_type,trade_memo,is_3D,buyer_email,buyer_area,trade_from,is_lgtype,is_force_wlb,is_brand_sale,buyer_cod_fee,discount_fee,seller_cod_fee,express_agency_fee,invoice_name,service_orders,credit_cardfee,step_trade_status,step_paid_fee,mark_desc,has_yfx,yfx_fee,yfx_id,yfx_type,trade_source,eticket_ext,send_time,is_daixiao,is_part_consign,arrive_interval,arrive_cut_time,consign_interval,orders");
		req.setTid(tid);
		TradeFullinfoGetResponse response = client.execute(req , sessionKey);
		return response;
	}
	
	public String getFeatureFromTaobao(String order_id,String out_tid){
		String url = "http://gw.api.taobao.com/router/rest";
		String appkey_wt = "12469285";
		String secret_wt = "d7f3540761ae620397baaa27afc1c035";
		String sessionKey_wt = "6100a00011dbbb3e5cb6fe62abb830bbad19b4277093977747143122";
		TradeFullinfoGetResponse resp = null;
		String feature = EcsOrderConsts.EMPTY_STR_VALUE;
		try {
			resp = getFullTrade(Long.valueOf(out_tid), appkey_wt, secret_wt, sessionKey_wt, url);
			List<Order> orders = resp.getTrade().getOrders();
			if(orders != null && orders.size()>0){
				Order order = orders.get(0);
				long oid = order.getOid();
				feature = dealOrderProductInfo(order_id, oid);
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return feature;
	}
	
	private String dealOrderProductInfo(String order_id, long oid){
		String[] s={EcsOrderConsts.EMPTY_STR_VALUE,EcsOrderConsts.EMPTY_STR_VALUE};
		String goods_type = CommonDataFactory.getInstance().getGoodsType(order_id);
		String contract_cat_id = SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID);
		List<Goods> products = SpecUtils.getAllOrderProducts(order_id);
		if(products == null || products.size()==0){
			return EcsOrderConsts.EMPTY_STR_VALUE;
		}
		for(Goods product : products){
			String cat_id = product.getCat_id();
			String type_id = product.getType_id();
			String is_physical = this.getDictCodeValue(StypeConsts.IS_PHYSICAL, type_id);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_physical)){//实物货品
				if(EcsOrderConsts.GOODS_TYPE_PHONE.equals(goods_type)
						|| EcsOrderConsts.GOODS_TYPE_ADJUNCT.equals(goods_type)
						|| EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)
						|| EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type)){
					String token = EcsOrderConsts.EMPTY_STR_VALUE.equals(s[1])?EcsOrderConsts.EMPTY_STR_VALUE:",";
					s[1] = s[1] + token + this.getAttrFieldValue(order_id, AttrConsts.TERMINAL_NUM,"","");
				}
			}
			else if(EcsOrderConsts.PRODUCT_TYPE_NUMBER.equals(type_id)){//号码
				if(EcsOrderConsts.PRODUCT_CAT_ID_6.equals(goods_type) || EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type)){
					String token = EcsOrderConsts.EMPTY_STR_VALUE.equals(s[0]) ? EcsOrderConsts.EMPTY_STR_VALUE : ",";
					s[0] = s[0] + token + this.getAttrFieldValue(order_id, AttrConsts.TERMINAL_NUM,"","");
				}
			}
		}
		return contractTypeDeal(contract_cat_id,oid,s);
	}
	
	private String contractTypeDeal(String contract_cat_id, long oid, String[] s){
		String s1 = s[0];
		String s2 = s[1];
		String result = EcsOrderConsts.EMPTY_STR_VALUE;
		String ic = "identCode=";
		Integer int_cat_id = Integer.valueOf(contract_cat_id);
		switch(int_cat_id)
		{			
			case 690303000:
				result = ic+oid+":"+s2;
				break;
			case 690302000:case 690306000://确定
				result = ic+oid+":"+s2;
				break;
			case 690305000://确定
				result = ic+oid+":"+s1;
				break;
			case 690301000:
				result = ic+oid+":"+s2;
				break;
			default:
				break;
		}
		return result;
	}
	
	private static ZteClient getDubboZteClient(){
    	return ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
    }

	/**
	 * 根据订单配送方式判断是否需要展示物流信息
	 * @param order_id
	 * @return
	 */
	public static String isShowShipVisiable(String order_id){
	    String send_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.SENDING_TYPE);
	    return isShow(send_type);
	}
	/**
	 * 根据指定配送方式判断是否需要展示物流信息
	 * @param new_send_type
	 * @return
	 */
	public static String isShowShipVisiableBySendType(String new_send_type){
	    return isShow(new_send_type);
	}
	
	private static String isShow(String send_type){
		//判断配送方式如果是无需配送，自提，现场交互，现场提货  不需要物流信息
		String str = EcsOrderConsts.IS_DEFAULT_VALUE;
	    if(EcsOrderConsts.SHIP_TYPE_ZT.equals(send_type)
	    		||EcsOrderConsts.SHIP_TYPE_XCTH.equals(send_type)
	    		||EcsOrderConsts.SHIPPING_TYPE_XJ.equals(send_type)
	    		||EcsOrderConsts.SHIP_TYPE_WX.equals(send_type)){
	    	str = EcsOrderConsts.NO_DEFAULT_VALUE;
	    }
	    return str;
	}
	//退单状态
	public String getRefund_status(String order_id) {
		String refund_status = "";
		 OrderTreeBusiRequest  orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(orderTree!=null){
			String status = orderTree.getOrderExtBusiRequest().getRefund_status();
			if(EcsOrderConsts.REFUND_STATUS_00.equals(status)){
				refund_status = "正常单";
			}else if(EcsOrderConsts.REFUND_STATUS_01.equals(status)){
				refund_status = "退单确认";
			}else if(EcsOrderConsts.REFUND_STATUS_02.equals(status)){
//				refund_status = "BSS已返销";
				refund_status = "退单确认";
			}
			else if(EcsOrderConsts.REFUND_STATUS_03.equals(status)){
//				refund_status = "ESS已返销";
				refund_status = "退单确认";
			}
			else if(EcsOrderConsts.REFUND_STATUS_04.equals(status)){
//				refund_status = "已退款";
				refund_status = "已退单";
			}else if(EcsOrderConsts.REFUND_STATUS_05.equals(status)){
//				refund_status = "退单完成";
				refund_status = "已退单";
			}else if(EcsOrderConsts.REFUND_STATUS_06.equals(status)){
//				refund_status = "已归档";
				refund_status = "已退单";
			}else if(EcsOrderConsts.REFUND_STATUS_07.equals(status)){
//				refund_status = "退单结果已通知商城";
				refund_status = "退单确认";
			}else if(EcsOrderConsts.REFUND_STATUS_08.equals(status)){
				refund_status = "退单申请";
			}else{
				refund_status = "正常单";
			}
		}
		return refund_status;
	}
	
	public void insertOrderHandlerLogs(InsertOrderHandLogReq req){
		ZteClient client = getDubboZteClient();
		InsertOrderHandLogResp resp = client.execute(req, InsertOrderHandLogResp.class);
	}
	
	/**
	 * 获取 AttrTable 
	 * @param field_name 字段名称
	 * @param table_name 表名称
	 * @return
	 */
	public AttrTableCache getAttrTableByAttrTableFieldName(String field_name,String table_name){
		AttrTableCacheGetReq attrTableCache = new AttrTableCacheGetReq();
		attrTableCache.setField_name(field_name);
		if(orderServices ==null)
			orderServices  = SpringContextHolder.getBean("orderServices");
		AttrTableCacheGetResp attrta = orderServices.getCacheAttrTable(attrTableCache);
		if(attrta !=null && attrta.getAttrTable() !=null)
			return attrta.getAttrTable();
		return null;
	}
	
	public static boolean isTestWeb(){
		boolean flag = false;
		String test = CommonDataFactory.getInstance().getOtherDictVodeValue("testKey", "test_or_prod");
		if("test".equals(test)){
		flag = true;
		}
		return flag;
		}
	
	/**
	 * 商品号码靓号节点信息拼装
	 */
	@SuppressWarnings("unchecked")
	public PhoneInfo getPhoneInfo(String order_id){
		PhoneInfo phoneInfo = new PhoneInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		OrderPhoneInfoBusiRequest  orderPhoneInfo = CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest();
        if(orderPhoneInfo==null){
        	orderPhoneInfo = new OrderPhoneInfoBusiRequest();
        }
		String phoneNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
		phoneInfo.setPhoneNum(phoneNum);
		
		
		NiceInfo niceInfo = new NiceInfo();
		niceInfo.setAdvanceCom(orderPhoneInfo.getAdvanceCom());
		niceInfo.setAdvancePay(orderPhoneInfo.getAdvancePay());
		niceInfo.setAdvanceSpe(orderPhoneInfo.getAdvanceSpe());
		niceInfo.setBremonChe(orderPhoneInfo.getBremonChe());
		niceInfo.setBroMonPro(orderPhoneInfo.getBroMonPro());
		niceInfo.setCancelTagChe(orderPhoneInfo.getCancelTagChe());
		niceInfo.setCancelTagPro(orderPhoneInfo.getCancelTagPro());
		niceInfo.setChangeTagChe(orderPhoneInfo.getChangeTagChe());
		niceInfo.setChangeTagPro(orderPhoneInfo.getCancelTagPro());
		niceInfo.setClassId(orderPhoneInfo.getClassId());
		niceInfo.setLowCostChe(orderPhoneInfo.getLowCostChe());
		niceInfo.setLowCostPro(orderPhoneInfo.getLowCostPro());
		niceInfo.setNumThawPro(orderPhoneInfo.getNumThawPro());
		niceInfo.setNumThawTim(orderPhoneInfo.getNumThawTim());
		niceInfo.setTimeDurChe(orderPhoneInfo.getTimeDurChe());
		niceInfo.setTimeDurPro(orderPhoneInfo.getTimeDurPro());
		phoneInfo.setNiceInfo(niceInfo);
		phoneInfo.setOccupiedTime(orderPhoneInfo.getOccupiedTime());
		phoneInfo.setProKey(orderPhoneInfo.getProKey());
		phoneInfo.setProKeyMode(orderPhoneInfo.getProKeyMode()); 
		phoneInfo.setOccupiedFlag(orderPhoneInfo.getOccupiedFlag());
		phoneInfo.setOperatorState(orderPhoneInfo.getOperatorState());
		return phoneInfo;
	}
	
	/**
	 * 根据map中的key去掉list相同的map元素
	 * @param data
	 * @param key
	 * @return
	 */
	public static List<Map> removeDuplicateMap (List<Map> data1,List<Map> data2,String... keys){
		List<Map> resultList = new LinkedList<Map>();
		 Set<Map> setMap = new HashSet<Map>(); 
		 for(Map map1: data1){
			 Map tempMap1 = new HashMap();
			 for(String key:keys){
				 tempMap1.put(key, map1.get(key));
			 }
			 if(setMap.add(tempMap1)){
				 resultList.add(map1);
			 }
		 }
		 for(Map map2: data2){
			 Map tempMap2 = new HashMap();
			 for(String key:keys){
				 tempMap2.put(key, map2.get(key));
			 }
			 if(setMap.add(tempMap2)){
				 resultList.add(map2);
			 }
		 }
		 logger.info("处理过的可选包:"+resultList.toString());
		 return resultList;
	}

	public List doGetDependElementList(List packagelist, String product_id,String first_payment){
		List<Map> list = new ArrayList<Map>();
		if (!StringUtils.isEmpty(first_payment)) {
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			//必选包依赖元素获取
			BPackageDependElementGetReq breq = new BPackageDependElementGetReq();
			breq.setProduct_id(product_id);
			breq.setFirst_payment(first_payment);
			BPackageDependElementListResp bresponse = client.execute(breq, BPackageDependElementListResp.class);
			List bdatas = bresponse.getbPackageDependElementList();
			//可选包依赖元素获取
			KPackageDependElementGetReq kreq = new KPackageDependElementGetReq();
			kreq.setPackageBusiRequests(packagelist);
			kreq.setProduct_id(product_id);
			kreq.setFirst_payment(first_payment);
			KPackageDependElementListResp kresponse = client.execute(kreq, KPackageDependElementListResp.class);
			List kdatas = kresponse.getkPackageDependElementList();
			list = removeDuplicateMap(bdatas,kdatas,"element_id","element_id");//去重
		} else { // 如果首月资费为空,则不转换
			if(packagelist.size()>0){
				List<AttrPackageBusiRequest> apl = packagelist;
				Map<String, String> m = null;
				for(AttrPackageBusiRequest req : apl){
					m = new HashMap<String, String>();
					m.put("package_id",req.getPackageCode());
					m.put("element_id",req.getElementCode());
					m.put("element_type_code",req.getElementType());
					list.add(m);
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 根据来源取工号
	 * @param order_id
	 * @return
	 */
	public String getOperatorCodeByOrderFrom(String order_id){
		String order_from = this.getAttrFieldValue(order_id, AttrConsts.ORDER_FROM,"","");
		List<Map> codes = this.listDcPublicData(StypeConsts.OPERATOR_CODE);
		String operator_code = null;
		if(codes!=null && codes.size()>0){
			for(Map code : codes){
				String pkey = code.get("pkey").toString();
				if(!StringUtils.isEmpty(order_from) && order_from.equals(pkey)){
					operator_code = (String) code.get("pname");
					break ;
				}
			}
		}
		return operator_code;
	}
	
	/**
	 * 获取指定号码信息
	 * @param phonelist
	 * @param m
	 * @return
	 */
	public static List<OrderPhoneInfoFukaBusiRequest> checkPhonNumInfo(List<OrderPhoneInfoFukaBusiRequest> phonelist,Map map,String occupiedflag,String operSystem){
		//当有指定目标号码时，移除其他号码
		List<OrderPhoneInfoFukaBusiRequest> rslist = new ArrayList<OrderPhoneInfoFukaBusiRequest>();
		if(map.get("fukaInstId")!=null){//
			String fukaInstId = map.get("fukaInstId").toString();
			for(OrderPhoneInfoFukaBusiRequest vo : phonelist){
				if(StringUtils.equals(fukaInstId, vo.getInst_id())) {
					rslist.add(vo);					
				}
			}	
		}else{
			rslist = phonelist;
		}
		
		//当有指定目标号码时，移除其他号码
		List<OrderPhoneInfoFukaBusiRequest> rslist2 = new ArrayList<OrderPhoneInfoFukaBusiRequest>();
		
		//只有未预占、预占失败、释放成功的才可以预占
		if(StringUtils.equals(operSystem, EcsOrderConsts.AOP) && 
				StringUtils.equals(EcsOrderConsts.OCCUPIEDFLAG_1, occupiedflag)){
			for(OrderPhoneInfoFukaBusiRequest vo : rslist){
				if(!StringUtils.isEmpty(vo.getOccupiedflag())){
					if(StringUtils.equals(vo.getOccupiedflag(), EcsOrderConsts.OCCUPIEDFLAG_0)){
						rslist2.add(vo);
					}
					if(StringUtils.equals(vo.getOccupiedflag(), EcsOrderConsts.OCCUPIEDFLAG_5)){
						rslist2.add(vo);
					}
					if(StringUtils.equals(vo.getOccupiedflag(), EcsOrderConsts.OCCUPIEDFLAG_4)){
						rslist2.add(vo);
					}
				}else{
					rslist2.add(vo);					
				}
			}
		}
				
		//只有预占成功、预定失败的才可以预定
		if(StringUtils.equals(operSystem, EcsOrderConsts.AOP) && 
				StringUtils.equals(EcsOrderConsts.OCCUPIEDFLAG_2, occupiedflag)){
			for(OrderPhoneInfoFukaBusiRequest vo : rslist){
				if(!StringUtils.isEmpty(vo.getOccupiedflag())){
					if(StringUtils.equals(vo.getOccupiedflag(), EcsOrderConsts.OCCUPIEDFLAG_1)){
						rslist2.add(vo);
					}
					if(StringUtils.equals(vo.getOccupiedflag(), EcsOrderConsts.OCCUPIEDFLAG_6)){
						rslist2.add(vo);
					}
				}
			}
		}
				

		//只有预占成功、预定成功、释放失败的才可以释放
		if(StringUtils.equals(operSystem, EcsOrderConsts.AOP) && 
				StringUtils.equals(EcsOrderConsts.OCCUPIEDFLAG_4, occupiedflag)){
			for(OrderPhoneInfoFukaBusiRequest vo : rslist){
				if(!StringUtils.isEmpty(vo.getOccupiedflag())){
					if(StringUtils.equals(vo.getOccupiedflag(), EcsOrderConsts.OCCUPIEDFLAG_1)){
						rslist2.add(vo);
					}
					if(StringUtils.equals(vo.getOccupiedflag(), EcsOrderConsts.OCCUPIEDFLAG_2)){
						rslist2.add(vo);
					}
					if(StringUtils.equals(vo.getOccupiedflag(), EcsOrderConsts.OCCUPIEDFLAG_3)){
						rslist2.add(vo);
					}
					if(StringUtils.equals(vo.getOccupiedflag(), EcsOrderConsts.OCCUPIEDFLAG_7)){
						rslist2.add(vo);
					}
				}
			}
		}
		

		//只有未预占、预占失败、释放成功的186号码才可以调bss预占接口
		if(StringUtils.equals(operSystem, EcsOrderConsts.ESS) && 
				StringUtils.equals(EcsOrderConsts.BSS_OCCUPIEDFLAG_1, occupiedflag)){
			for(OrderPhoneInfoFukaBusiRequest vo : rslist){
				if(vo.getPhoneNum().startsWith("186")){
					if(!StringUtils.isEmpty(vo.getBss_occupied_flag())){
						if(StringUtils.equals(vo.getBss_occupied_flag(), EcsOrderConsts.OCCUPIEDFLAG_0)){
							rslist2.add(vo);
						}
						if(StringUtils.equals(vo.getBss_occupied_flag(), EcsOrderConsts.OCCUPIEDFLAG_5)){
							rslist2.add(vo);
						}
						if(StringUtils.equals(vo.getBss_occupied_flag(), EcsOrderConsts.OCCUPIEDFLAG_4)){
							rslist2.add(vo);
						}
					}else{
						rslist2.add(vo);
					}				
				}
			}
		}
		

		//只有预占成功、释放失败的186号码才可以调bss释放接口
		if(StringUtils.equals(operSystem, EcsOrderConsts.ESS) && 
				StringUtils.equals(EcsOrderConsts.BSS_OCCUPIEDFLAG_2, occupiedflag)){
			for(OrderPhoneInfoFukaBusiRequest vo : rslist){
				if(vo.getPhoneNum().startsWith("186")){
					if(!StringUtils.isEmpty(vo.getBss_occupied_flag())){
						if(StringUtils.equals(vo.getBss_occupied_flag(), EcsOrderConsts.OCCUPIEDFLAG_1)){
							rslist2.add(vo);
						}
						if(StringUtils.equals(vo.getBss_occupied_flag(), EcsOrderConsts.OCCUPIEDFLAG_7)){
							rslist2.add(vo);
						}
					}else{
						rslist2.add(vo);
					}				
				}
			}
		}
		 
		return rslist2;
	}
	
	private List<GoodsAtt> assembleTmp (List<GoodsAtt> goodsAtt, String order_id) {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaBusiRequests = orderTree.getOrderPhoneInfoFukaBusiRequests();
		if (orderPhoneInfoFukaBusiRequests != null && orderPhoneInfoFukaBusiRequests.size() > 0) {
			for (OrderPhoneInfoFukaBusiRequest req : orderPhoneInfoFukaBusiRequests) {
				if (req.getGoodsType().equals(EcsOrderConsts.ZB_GOODS_TYPE_HKFK)) { // 号卡副卡
					goodsAtt = getGoodsAttHK_FK(goodsAtt, order_id,req);
				} else if (req.getGoodsType().equals(EcsOrderConsts.ZB_GOODS_TYPE_ZSWK)) { // 上网卡副卡
					goodsAtt = getGoodsAttZBWXSWK_FK(goodsAtt, order_id,req);
				}
			}
		}
		return goodsAtt;
	}
	
	private List<GoodsAtt> getGoodsAttHK_FK(List<GoodsAtt> goodsAtt, String order_id , OrderPhoneInfoFukaBusiRequest orderPhoneInfoFukaBusiRequest) {
		/**
		 * ZX Add 2015-12-28 Add HK_FK Info Start
		 */
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
//		List<OrderPhoneInfoFukaBusiRequest> fukaBusiRequests = orderTree.getOrderPhoneInfoFukaBusiRequests();
//		if (fukaBusiRequests != null && fukaBusiRequests.size() > 0) {
//			for (OrderPhoneInfoFukaBusiRequest orderPhoneInfoFukaBusiRequest : fukaBusiRequests) {
				GoodsAttHK_FK goodsAttHK_FK = new GoodsAttHK_FK();
				goodsAttHK_FK.setCustomerName(orderPhoneInfoFukaBusiRequest.getCustomerName()!=null?orderPhoneInfoFukaBusiRequest.getCustomerName():"");
				goodsAttHK_FK.setCertType(orderPhoneInfoFukaBusiRequest.getCertType()!=null?orderPhoneInfoFukaBusiRequest.getCertType():"");
				goodsAttHK_FK.setCertNum(orderPhoneInfoFukaBusiRequest.getCertNum()!=null?orderPhoneInfoFukaBusiRequest.getCertNum():"");
				goodsAttHK_FK.setCustomerType(getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE,"",""));
				goodsAttHK_FK.setGroupId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GROUP_CODE));
				goodsAttHK_FK.setAppType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.APP_TYPE));
				goodsAttHK_FK.setSubAppType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SUB_APP_TYPE));
				goodsAttHK_FK.setCertLoseTime(orderPhoneInfoFukaBusiRequest.getCertLoseTime());
				goodsAttHK_FK.setCertAddr(orderPhoneInfoFukaBusiRequest.getCertAddr()!=null?orderPhoneInfoFukaBusiRequest.getCertAddr():"");
				goodsAttHK_FK.setCheckType(orderPhoneInfoFukaBusiRequest.getCheckType()!=null?orderPhoneInfoFukaBusiRequest.getCheckType():"");
				goodsAttHK_FK.setRealnameType(orderPhoneInfoFukaBusiRequest.getRealNameType()!=null?orderPhoneInfoFukaBusiRequest.getRealNameType():"");
				goodsAttHK_FK.setSex(orderPhoneInfoFukaBusiRequest.getSex()!=null?orderPhoneInfoFukaBusiRequest.getSex():"");
				goodsAttHK_FK.setRefereeName(getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_NAME,"",""));
				goodsAttHK_FK.setRefereeNum(getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_PHONE,"",""));
				goodsAttHK_FK.setDevelopCode(getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_CODE,"",""));
				goodsAttHK_FK.setDevelopName(getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_NAME,"",""));
				goodsAttHK_FK.setDevelopDepId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DEVELOPDEP_ID));
				
				PhoneInfo phoneInfo_fk = new PhoneInfo();
				NiceInfo niceInfo = new NiceInfo();
				niceInfo.setAdvancePay(orderPhoneInfoFukaBusiRequest.getAdvancePay()!=null?orderPhoneInfoFukaBusiRequest.getAdvancePay():"");
				niceInfo.setAdvanceCom(orderPhoneInfoFukaBusiRequest.getAdvanceCom()!=null?orderPhoneInfoFukaBusiRequest.getAdvanceCom():"");
				niceInfo.setAdvanceSpe(orderPhoneInfoFukaBusiRequest.getAdvanceSpe()!=null?orderPhoneInfoFukaBusiRequest.getAdvanceSpe():"");
				niceInfo.setNumThawTim(orderPhoneInfoFukaBusiRequest.getNumThawTim()!=null?orderPhoneInfoFukaBusiRequest.getNumThawTim():"");
				niceInfo.setNumThawPro(orderPhoneInfoFukaBusiRequest.getNumThawPro()!=null?orderPhoneInfoFukaBusiRequest.getNumThawPro():"");
				niceInfo.setClassId(orderPhoneInfoFukaBusiRequest.getClassId()!=null?Integer.valueOf(orderPhoneInfoFukaBusiRequest.getClassId()):0);
				niceInfo.setLowCostChe(orderPhoneInfoFukaBusiRequest.getLowCostChe()!=null?orderPhoneInfoFukaBusiRequest.getLowCostChe():"");
				niceInfo.setTimeDurChe(orderPhoneInfoFukaBusiRequest.getTimeDurChe()!=null?orderPhoneInfoFukaBusiRequest.getTimeDurChe():"");
				niceInfo.setChangeTagChe(orderPhoneInfoFukaBusiRequest.getChangeTagChe()!=null?Integer.valueOf(orderPhoneInfoFukaBusiRequest.getChangeTagChe()):0);
				niceInfo.setCancelTagChe(orderPhoneInfoFukaBusiRequest.getCancelTagChe()!=null?Integer.valueOf(orderPhoneInfoFukaBusiRequest.getCancelTagChe()):0);
				niceInfo.setBremonChe(orderPhoneInfoFukaBusiRequest.getBreMonChe()!=null?orderPhoneInfoFukaBusiRequest.getBreMonChe():"");
				niceInfo.setLowCostPro(orderPhoneInfoFukaBusiRequest.getLowCostPro()!=null?orderPhoneInfoFukaBusiRequest.getLowCostPro():"");
				niceInfo.setTimeDurPro(orderPhoneInfoFukaBusiRequest.getTimeDurPro()!=null?orderPhoneInfoFukaBusiRequest.getTimeDurPro():"");
				niceInfo.setChangeTagPro(orderPhoneInfoFukaBusiRequest.getChangeTagPro()!=null?Integer.valueOf(orderPhoneInfoFukaBusiRequest.getChangeTagPro()):0);
				niceInfo.setCancelTagPro(orderPhoneInfoFukaBusiRequest.getCancelTagPro()!=null?Integer.valueOf(orderPhoneInfoFukaBusiRequest.getCancelTagPro()):0);
				niceInfo.setBroMonPro(orderPhoneInfoFukaBusiRequest.getBroMonPro()!=null?orderPhoneInfoFukaBusiRequest.getBroMonPro():"");
				phoneInfo_fk.setNiceInfo(niceInfo);
				phoneInfo_fk.setOperatorState(orderPhoneInfoFukaBusiRequest.getOperatorstate()!=null?orderPhoneInfoFukaBusiRequest.getOperatorstate():"");
				phoneInfo_fk.setOccupiedFlag(orderPhoneInfoFukaBusiRequest.getOccupiedflag()!=null?orderPhoneInfoFukaBusiRequest.getOccupiedflag():"");
				phoneInfo_fk.setOccupiedTime(orderPhoneInfoFukaBusiRequest.getOccupiedTime()!=null?orderPhoneInfoFukaBusiRequest.getOccupiedTime():"");
				phoneInfo_fk.setOperatorState(orderPhoneInfoFukaBusiRequest.getOperatorstate()!=null?orderPhoneInfoFukaBusiRequest.getOperatorstate():"");
				phoneInfo_fk.setPhoneNum(orderPhoneInfoFukaBusiRequest.getPhoneNum()!=null?orderPhoneInfoFukaBusiRequest.getPhoneNum():"");
				phoneInfo_fk.setProKey(orderPhoneInfoFukaBusiRequest.getProkey()!=null?orderPhoneInfoFukaBusiRequest.getProkey():"");
				phoneInfo_fk.setProKeyMode(orderPhoneInfoFukaBusiRequest.getProKeyMode()!=null?orderPhoneInfoFukaBusiRequest.getProKeyMode():"");
				goodsAttHK_FK.setPhoneInfo(phoneInfo_fk);
				
				goodsAttHK_FK.setReliefPresFlag(orderPhoneInfoFukaBusiRequest.getReliefPresFlag()!=null?orderPhoneInfoFukaBusiRequest.getReliefPresFlag():"");
				goodsAttHK_FK.setSaleMode(orderPhoneInfoFukaBusiRequest.getSaleMode()!=null?orderPhoneInfoFukaBusiRequest.getSaleMode():"");
				goodsAttHK_FK.setSimId(orderPhoneInfoFukaBusiRequest.getSimId()!=null?orderPhoneInfoFukaBusiRequest.getSimId():"");
				goodsAttHK_FK.setCardType(orderPhoneInfoFukaBusiRequest.getCardType()!=null?orderPhoneInfoFukaBusiRequest.getCardType():"");
				goodsAttHK_FK.setUserType(orderPhoneInfoFukaBusiRequest.getUserType()!=null?orderPhoneInfoFukaBusiRequest.getUserType():"");
				goodsAttHK_FK.setNumType(orderPhoneInfoFukaBusiRequest.getNumType()!=null?orderPhoneInfoFukaBusiRequest.getNumType():"");
				goodsAttHK_FK.setGoodsType(orderPhoneInfoFukaBusiRequest.getGoodsType()!=null?orderPhoneInfoFukaBusiRequest.getGoodsType():"");
				goodsAttHK_FK.setProductCode(orderPhoneInfoFukaBusiRequest.getProductCode()!=null?orderPhoneInfoFukaBusiRequest.getProductCode():"");
				goodsAttHK_FK.setProductName(orderPhoneInfoFukaBusiRequest.getProductName()!=null?orderPhoneInfoFukaBusiRequest.getProductName():"");
				goodsAttHK_FK.setProductType(orderPhoneInfoFukaBusiRequest.getProductType()!=null?orderPhoneInfoFukaBusiRequest.getProductType():"");
				goodsAttHK_FK.setProductNet(orderPhoneInfoFukaBusiRequest.getProductNet()!=null?orderPhoneInfoFukaBusiRequest.getProductNet():"");
				goodsAttHK_FK.setProductBrand(orderPhoneInfoFukaBusiRequest.getProductBrand()!=null?orderPhoneInfoFukaBusiRequest.getProductBrand():"");
				List<Package> packages = new ArrayList<Package>();
				List<AttrPackageFukaBusiRequest> attrPackageFukaBusiRequests = orderTree.getAttrPackageFukaBusiRequests();
				for (AttrPackageFukaBusiRequest attrPackageFukaBusiRequest : attrPackageFukaBusiRequests) {
					Package pageage = new Package();
					pageage.setPackageCode(attrPackageFukaBusiRequest.getPackage_code()!=null?attrPackageFukaBusiRequest.getPackage_code():"");
					pageage.setPackageName(attrPackageFukaBusiRequest.getPackage_name()!=null?attrPackageFukaBusiRequest.getPackage_name():"");
					pageage.setElementCode(attrPackageFukaBusiRequest.getElement_code()!=null?attrPackageFukaBusiRequest.getElement_code():"");
					pageage.setElementType(attrPackageFukaBusiRequest.getElement_type()!=null?attrPackageFukaBusiRequest.getElement_type():"");
					pageage.setElementName(attrPackageFukaBusiRequest.getElement_name()!=null?attrPackageFukaBusiRequest.getElement_name():"");
					packages.add(pageage);
				}
				goodsAttHK_FK.setPackage(packages);
				goodsAttHK_FK.setFirstMonBillMode(orderPhoneInfoFukaBusiRequest.getFirstMonbillMode()!=null?orderPhoneInfoFukaBusiRequest.getFirstMonbillMode():"");
				goodsAttHK_FK.setSerType(SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE));
				goodsAttHK_FK.setSubProdInfo(getSubProductInfos(order_id, EcsOrderConsts.ZB_CARD_TYPE_FU));
				List<ActivityInfo> activityInfos = new ArrayList<ActivityInfo>();
				ActivityInfo activityInfo = new ActivityInfo();
				activityInfo.setActivityCode(orderPhoneInfoFukaBusiRequest.getActivityCode()!=null?orderPhoneInfoFukaBusiRequest.getActivityCode():"");
				activityInfo.setActivityName(orderPhoneInfoFukaBusiRequest.getActivityName()!=null?orderPhoneInfoFukaBusiRequest.getActivityName():"");
				activityInfo.setActivityType(orderPhoneInfoFukaBusiRequest.getActivityType()!=null?orderPhoneInfoFukaBusiRequest.getActivityType():"");
				activityInfo.setActProtPer(orderPhoneInfoFukaBusiRequest.getActProtPer()!=null?orderPhoneInfoFukaBusiRequest.getActProtPer():"");
				List<ActivityInfoPackage> pk_list = new ArrayList<ActivityInfoPackage>();
				activityInfo.setPackage(pk_list);
				activityInfos.add(activityInfo);
				goodsAttHK_FK.setActivityInfo(activityInfos);
				goodsAtt.add(goodsAttHK_FK);
//			}
//		}
		/**
		 * ZX Add 2015-12-28 Add FuKa Info End
		 */
		return goodsAtt;
	}
	
	private List<GoodsAtt> getGoodsAttZBWXSWK_FK(List<GoodsAtt> goodsAtt, String order_id , OrderPhoneInfoFukaBusiRequest orderPhoneInfoFukaBusiRequest) {
		/**
		 * ZX add 2015-12-28 Add WXSWK_FK Info Start
		 */
//		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
//		List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaBusiRequests  = orderTree.getOrderPhoneInfoFukaBusiRequests();
//		if (orderPhoneInfoFukaBusiRequests!=null && orderPhoneInfoFukaBusiRequests.size()>0) {
//			for (OrderPhoneInfoFukaBusiRequest orderPhoneInfoFukaBusiRequest : orderPhoneInfoFukaBusiRequests) {
				GoodsAttZBWXSWK_FK goodsAttZBWXSWK_FK = new GoodsAttZBWXSWK_FK();
				goodsAttZBWXSWK_FK.setCustomerName(orderPhoneInfoFukaBusiRequest.getCustomerName());
				goodsAttZBWXSWK_FK.setCertType(orderPhoneInfoFukaBusiRequest.getCertType());
				goodsAttZBWXSWK_FK.setCertNum(orderPhoneInfoFukaBusiRequest.getCertNum());
				goodsAttZBWXSWK_FK.setCustomerType(getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE,"",""));
				goodsAttZBWXSWK_FK.setGroupId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GROUP_CODE));
				goodsAttZBWXSWK_FK.setAppType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.APP_TYPE));
				goodsAttZBWXSWK_FK.setSubAppType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SUB_APP_TYPE));
				goodsAttZBWXSWK_FK.setCertLoseTime(orderPhoneInfoFukaBusiRequest.getCertLoseTime());
				goodsAttZBWXSWK_FK.setCertAddr(orderPhoneInfoFukaBusiRequest.getCertAddr());
				goodsAttZBWXSWK_FK.setCheckType(orderPhoneInfoFukaBusiRequest.getCheckType());
				goodsAttZBWXSWK_FK.setRealnameType(orderPhoneInfoFukaBusiRequest.getRealNameType());
				goodsAttZBWXSWK_FK.setSex(orderPhoneInfoFukaBusiRequest.getSex());
				goodsAttZBWXSWK_FK.setRefereeName(getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_NAME,"",""));
				goodsAttZBWXSWK_FK.setRefereeNum(getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_PHONE,"",""));
				goodsAttZBWXSWK_FK.setDevelopCode(getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_CODE,"",""));
				goodsAttZBWXSWK_FK.setDevelopName(getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_NAME,"",""));
				goodsAttZBWXSWK_FK.setDevelopDepId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DEVELOPDEP_ID));
				PhoneInfo phoneInfo_fk = new PhoneInfo();
				NiceInfo niceInfo = new NiceInfo();
				niceInfo.setAdvancePay(orderPhoneInfoFukaBusiRequest.getAdvancePay()!=null?orderPhoneInfoFukaBusiRequest.getAdvancePay():"");
				niceInfo.setAdvanceCom(orderPhoneInfoFukaBusiRequest.getAdvanceCom()!=null?orderPhoneInfoFukaBusiRequest.getAdvanceCom():"");
				niceInfo.setAdvanceSpe(orderPhoneInfoFukaBusiRequest.getAdvanceSpe()!=null?orderPhoneInfoFukaBusiRequest.getAdvanceSpe():"");
				niceInfo.setNumThawTim(orderPhoneInfoFukaBusiRequest.getNumThawTim()!=null?orderPhoneInfoFukaBusiRequest.getNumThawTim():"");
				niceInfo.setNumThawPro(orderPhoneInfoFukaBusiRequest.getNumThawPro()!=null?orderPhoneInfoFukaBusiRequest.getNumThawPro():"");
				niceInfo.setClassId(orderPhoneInfoFukaBusiRequest.getClassId()!=null?Integer.valueOf(orderPhoneInfoFukaBusiRequest.getClassId()):0);
				niceInfo.setLowCostChe(orderPhoneInfoFukaBusiRequest.getLowCostChe()!=null?orderPhoneInfoFukaBusiRequest.getLowCostChe():"");
				niceInfo.setTimeDurChe(orderPhoneInfoFukaBusiRequest.getTimeDurChe()!=null?orderPhoneInfoFukaBusiRequest.getTimeDurChe():"");
				niceInfo.setChangeTagChe(orderPhoneInfoFukaBusiRequest.getChangeTagChe()!=null?Integer.valueOf(orderPhoneInfoFukaBusiRequest.getChangeTagChe()):0);
				niceInfo.setCancelTagChe(orderPhoneInfoFukaBusiRequest.getCancelTagChe()!=null?Integer.valueOf(orderPhoneInfoFukaBusiRequest.getCancelTagChe()):0);
				niceInfo.setBremonChe(orderPhoneInfoFukaBusiRequest.getBreMonChe()!=null?orderPhoneInfoFukaBusiRequest.getBreMonChe():"");
				niceInfo.setLowCostPro(orderPhoneInfoFukaBusiRequest.getLowCostPro()!=null?orderPhoneInfoFukaBusiRequest.getLowCostPro():"");
				niceInfo.setTimeDurPro(orderPhoneInfoFukaBusiRequest.getTimeDurPro()!=null?orderPhoneInfoFukaBusiRequest.getTimeDurPro():"");
				niceInfo.setChangeTagPro(orderPhoneInfoFukaBusiRequest.getChangeTagPro()!=null?Integer.valueOf(orderPhoneInfoFukaBusiRequest.getChangeTagPro()):0);
				niceInfo.setCancelTagPro(orderPhoneInfoFukaBusiRequest.getCancelTagPro()!=null?Integer.valueOf(orderPhoneInfoFukaBusiRequest.getCancelTagPro()):0);
				niceInfo.setBroMonPro(orderPhoneInfoFukaBusiRequest.getBroMonPro()!=null?orderPhoneInfoFukaBusiRequest.getBroMonPro():"");
				phoneInfo_fk.setNiceInfo(niceInfo);
				phoneInfo_fk.setOperatorState(orderPhoneInfoFukaBusiRequest.getOperatorstate()!=null?orderPhoneInfoFukaBusiRequest.getOperatorstate():"");
				phoneInfo_fk.setOccupiedFlag(orderPhoneInfoFukaBusiRequest.getOccupiedflag()!=null?orderPhoneInfoFukaBusiRequest.getOccupiedflag():"");
				phoneInfo_fk.setOccupiedTime(orderPhoneInfoFukaBusiRequest.getOccupiedTime()!=null?orderPhoneInfoFukaBusiRequest.getOccupiedTime():"");
				phoneInfo_fk.setOperatorState(orderPhoneInfoFukaBusiRequest.getOperatorstate()!=null?orderPhoneInfoFukaBusiRequest.getOperatorstate():"");
				phoneInfo_fk.setPhoneNum(orderPhoneInfoFukaBusiRequest.getPhoneNum()!=null?orderPhoneInfoFukaBusiRequest.getPhoneNum():"");
				phoneInfo_fk.setProKey(orderPhoneInfoFukaBusiRequest.getProkey()!=null?orderPhoneInfoFukaBusiRequest.getProkey():"");
				phoneInfo_fk.setProKeyMode(orderPhoneInfoFukaBusiRequest.getProKeyMode()!=null?orderPhoneInfoFukaBusiRequest.getProKeyMode():"");
				goodsAttZBWXSWK_FK.setPhoneInfo(phoneInfo_fk);
				goodsAttZBWXSWK_FK.setSaleMode(orderPhoneInfoFukaBusiRequest.getSaleMode()!=null?orderPhoneInfoFukaBusiRequest.getSaleMode():"");
				goodsAttZBWXSWK_FK.setCardType(orderPhoneInfoFukaBusiRequest.getCardType()!=null?orderPhoneInfoFukaBusiRequest.getCardType():"");
				goodsAttZBWXSWK_FK.setUserType(orderPhoneInfoFukaBusiRequest.getUserType()!=null?orderPhoneInfoFukaBusiRequest.getUserType():"");
				goodsAttZBWXSWK_FK.setNumType(orderPhoneInfoFukaBusiRequest.getNumType()!=null?orderPhoneInfoFukaBusiRequest.getNumType():"");
				goodsAttZBWXSWK_FK.setGoodsType(orderPhoneInfoFukaBusiRequest.getGoodsType()!=null?orderPhoneInfoFukaBusiRequest.getGoodsType():"");
				goodsAttZBWXSWK_FK.setProductCode(orderPhoneInfoFukaBusiRequest.getProductCode()!=null?orderPhoneInfoFukaBusiRequest.getProductCode():"");
				goodsAttZBWXSWK_FK.setProductName(orderPhoneInfoFukaBusiRequest.getProductName()!=null?orderPhoneInfoFukaBusiRequest.getProductName():"");
				goodsAttZBWXSWK_FK.setProductNet(orderPhoneInfoFukaBusiRequest.getProductNet()!=null?orderPhoneInfoFukaBusiRequest.getProductNet():"");
				goodsAttZBWXSWK_FK.setProductBrand(orderPhoneInfoFukaBusiRequest.getProductBrand()!=null?orderPhoneInfoFukaBusiRequest.getProductBrand():"");
				goodsAttZBWXSWK_FK.setSerType(SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE));
				goodsAttZBWXSWK_FK.setSubProdInfo(getSubProductInfos(order_id, EcsOrderConsts.ZB_CARD_TYPE_FU));
				goodsAttZBWXSWK_FK.setProPacCode(orderPhoneInfoFukaBusiRequest.getProPacCode()!=null?orderPhoneInfoFukaBusiRequest.getProPacCode():"");
				goodsAttZBWXSWK_FK.setProPacDesc(orderPhoneInfoFukaBusiRequest.getProPacDesc()!=null?orderPhoneInfoFukaBusiRequest.getProPacDesc():"");
				List<ActivityInfo> activityInfos = new ArrayList<ActivityInfo>();
				ActivityInfo activityInfo = new ActivityInfo();
				activityInfo.setActivityType(orderPhoneInfoFukaBusiRequest.getActivityType()!=null?orderPhoneInfoFukaBusiRequest.getActivityType():"");
				activityInfo.setActivityCode(orderPhoneInfoFukaBusiRequest.getActivityCode()!=null?orderPhoneInfoFukaBusiRequest.getActivityCode():"");
				activityInfo.setActivityName(orderPhoneInfoFukaBusiRequest.getActivityName()!=null?orderPhoneInfoFukaBusiRequest.getActivityName():"");
				activityInfo.setActProtPer(orderPhoneInfoFukaBusiRequest.getActProtPer()!=null?orderPhoneInfoFukaBusiRequest.getActProtPer():"");
				goodsAttZBWXSWK_FK.setActivityInfo(activityInfos);
				List<Resources> ResourcesInfo = new ArrayList<Resources>();
				Resources resources1 = new Resources();
				resources1.setResourcesBrand(orderPhoneInfoFukaBusiRequest.getResourcesBrand()!=null?orderPhoneInfoFukaBusiRequest.getResourcesBrand():"");
				resources1.setResourcesModel(orderPhoneInfoFukaBusiRequest.getResourcesModel()!=null?orderPhoneInfoFukaBusiRequest.getResourcesModel():"");
				resources1.setResourcesTypeId(orderPhoneInfoFukaBusiRequest.getResourcesTypeId()!=null?orderPhoneInfoFukaBusiRequest.getResourcesTypeId():"");
				resources1.setResourcesColor(orderPhoneInfoFukaBusiRequest.getResourcesColor()!=null?orderPhoneInfoFukaBusiRequest.getResourcesColor():"");
				resources1.setResourcesCode(orderPhoneInfoFukaBusiRequest.getResourcesCode()!=null?orderPhoneInfoFukaBusiRequest.getResourcesCode():"");
				ResourcesInfo.add(resources1);
				
				goodsAtt.add(goodsAttZBWXSWK_FK);
//			}
//		}
		/**
		 * ZX add 2015-12-28 Add WXSWK_FK Info End
		 */
		return goodsAtt;
	}
	/**
	 * 附加产品信息
	 * @param order_id
	 * @return
	 */
	private List<SubProd> getSubProductInfos(String order_id, String zhu_fu_flag) {
		List<SubProd> subProdList = new ArrayList<SubProd>();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderSubProductBusiRequest> subProds = orderTree.getOrderSubProductBusiRequest();
		//总部变更不再传附加产品
		if(!StringUtils.equals(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM), EcsOrderConsts.ORDER_FROM_10003)){
			if (subProds != null) {
				for (OrderSubProductBusiRequest subProdReq : subProds) {
					if (zhu_fu_flag.equals(subProdReq.getSub_prod_type())) { // 主副标识（1-主卡，2-副卡）
						SubProd subProd = new SubProd();
						subProd.setSubProCode(subProdReq.getSub_pro_code()!=null?subProdReq.getSub_pro_code():""); // 附加产品编码
						subProd.setSubProName(subProdReq.getSub_pro_name()!=null?subProdReq.getSub_pro_name():""); // 附加产品名称
						subProd.setSubProDesc(subProdReq.getSub_pro_desc()!=null?subProdReq.getSub_pro_desc():""); // 附加产品说明
						List<AttrPackageSubProdBusiRequest> attrSubProds = orderTree.getAttrPackageSubProdBusiRequest();
						List<SubPackage> attrSubProdList = new ArrayList<SubPackage>();
						if (attrSubProds != null) {
							for (AttrPackageSubProdBusiRequest attrSubProdReq : attrSubProds) {
								if (attrSubProdReq.getOrder_id().equals(subProdReq.getOrder_id())
										&& attrSubProdReq.getSubProd_inst_id().equals(subProdReq.getSub_pro_inst_id())) {
									SubPackage subPackage = new SubPackage();
									subPackage.setSubPackageCode(attrSubProdReq.getProduct_code()!=null?attrSubProdReq.getProduct_code():""); // 产品编号
									subPackage.setSubPackageName(attrSubProdReq.getPackage_name()!=null?attrSubProdReq.getPackage_name():""); // 可选包名称
									subPackage.setSubElementCode(attrSubProdReq.getElement_code()!=null?attrSubProdReq.getElement_code():""); // 元素编码
									subPackage.setSubElementType(attrSubProdReq.getElement_type()!=null?attrSubProdReq.getElement_type():""); // 元素类型
									subPackage.setSubElementName(attrSubProdReq.getElement_name()!=null?attrSubProdReq.getElement_name():""); // 元素名称
									attrSubProdList.add(subPackage);
									subProd.setSubPackage(attrSubProdList);
								}
							}
						}
						subProdList.add(subProd);
					}
				}
			}
			
		}
		return subProdList;
	}
	
	/**
	 * 获取订单的退款方案信息
	 * @param order_id
	 * @return Map：plan_id 方案id，app_rule_id 退单申请规则id，confirm_rule_id 退单确认规则id
	 */
	public Map<String, String> getRefundPlanInfo(String order_id){
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
		String shippingType = orderTree.getOrderBusiRequest().getShipping_type();
		String plan_id="";
		String app_rule_id="";
		String confirm_rule_id="";
		String refund_rule_id="";
		Map<String, String> m = new HashMap<String, String>();
//		OrderBroadBusiRequest broadBusiRequest = orderTree.getOrderBroadBusiRequest();
		boolean isBroad = false;	//是否宽带订单，宽带订单退单走自动规则
//		if(null != broadBusiRequest){
//			isBroad = true;
//		}
		
		String goods_type =  CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		if(StringUtils.equals(goods_type, EcsOrderConsts.GOODS_TYPE_KD)){//宽带
			isBroad = true;
		}
		//取得订单的退款模式
		String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
		String refundMode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_AUDIT_MODE);
		if((EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(is_aop)&& EcsOrderConsts.SENDING_TYPE_XJ.equals(shippingType))
				|| (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)&& EcsOrderConsts.SENDING_TYPE_XJ.equals(shippingType))
				|| StringUtils.equals(refundMode, EcsOrderConsts.REFUND_AUDIT_AUTO) || isBroad){
			//AOP+(现场交付or总商)
			plan_id = EcsOrderConsts.ORDER_RETURNED_PLAN_AUTO;
			app_rule_id = EcsOrderConsts.ORDER_RETURN_APPLY_RULE_AUTO;
			confirm_rule_id = EcsOrderConsts.ORDER_RETURN_CONFIRM_RULE_AUTO;
			refund_rule_id = EcsOrderConsts.ORDER_RETURN_REFUND_RULE_AUTO;
		}else if(EcsOrderConsts.ORDER_MODEL_07.equals(order_model)||EcsOrderConsts.ORDER_MODEL_08.equals(order_model)){//爬虫订单
			//其他
			plan_id = ZjEcsOrderConsts.ORDER_RETURNED_PLAN_PC;
			app_rule_id = ZjEcsOrderConsts.ORDER_RETURN_APPLY_RULE_PC;
			confirm_rule_id = ZjEcsOrderConsts.ORDER_RETURN_CONFIRM_RULE_PC;
			//refund_rule_id = "";  没有退款的
		}else if("170601900021724272".equals(goods_type)) { //add by wcl 缴费订单 退单详情展示
			
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			
			plan_id = cacheUtil.getConfigInfo("JIAOFEI_REFUND_PLANID");
			app_rule_id = cacheUtil.getConfigInfo("JIAOFEI_REFUND_RULEID");
			confirm_rule_id = cacheUtil.getConfigInfo("JIAOFEI_REFUND_CONFIRM_RULE");
		}else{
			//其他
			plan_id = EcsOrderConsts.ORDER_RETURNED_PLAN;
			app_rule_id = EcsOrderConsts.ORDER_RETURN_APPLY_RULE;
			confirm_rule_id = EcsOrderConsts.ORDER_RETURN_CONFIRM_RULE;
			refund_rule_id = EcsOrderConsts.ORDER_RETURN_REFUND_RULE;
		}
		if(StringUtils.equals(goods_type, EcsOrderConsts.GOODS_TYPE_DKD)||"170502124302000763".equals(goods_type)||"170502123552000755".equals(goods_type)
					||"170502125012000771".equals(goods_type)||"170502112412000711".equals(goods_type)||"201407023948000418".equals(goods_type)
					||"170401633552001805".equals(goods_type)||"171261219032000253".equals(goods_type)
					||"170801435262003016".equals(goods_type)||"170801434582003010".equals(goods_type)){//单宽带
			plan_id = EcsOrderConsts.ORDER_RETURNED_PLAN_KD;
			app_rule_id = EcsOrderConsts.ORDER_RETURN_APPLY_RULE_KD;
			confirm_rule_id = EcsOrderConsts.ORDER_RETURN_CONFIRM_RULE_KD;
			refund_rule_id = EcsOrderConsts.ORDER_RETURN_REFUND_RULE_KD;
		}
		
		m.put("plan_id", plan_id);
		m.put("app_rule_id", app_rule_id);
		m.put("confirm_rule_id", confirm_rule_id);
		m.put("refund_rule_id", refund_rule_id);
		return m;
	}
	
	/**
	 * 手动、自动退单模式判断（规则性的，非接口指定）
	 * @param order_id
	 * @return
	 */
	public String getRefundModeByRule(String order_id){
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
		String shippingType = orderTree.getOrderBusiRequest().getShipping_type();
		String refundmode="";
		if((EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(is_aop)&& EcsOrderConsts.SENDING_TYPE_XJ.equals(shippingType))
				|| (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)&& EcsOrderConsts.SENDING_TYPE_XJ.equals(shippingType))){
			//AOP+(现场交付or总商)
			refundmode = EcsOrderConsts.REFUND_AUDIT_AUTO;
		}else{
			//其他
			refundmode = EcsOrderConsts.REFUND_AUDIT_HAND;
		}
		
		return refundmode;
	}
	
	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public ILogsServices getLogsServices() {
		return logsServices;
	}

	public void setLogsServices(ILogsServices logsServices) {
		this.logsServices = logsServices;
	}
	
}
