package zte.net.ecsord.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

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
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.pub.req.DcPublicExtListReq;
import zte.net.ecsord.params.pub.req.DictRelationListReq;
import zte.net.ecsord.params.pub.req.DropdownDataReq;
import zte.net.ecsord.params.pub.req.LogiCompPersonGetReq;
import zte.net.ecsord.params.pub.req.ZbDictCodeValueGetReq;
import zte.net.ecsord.params.pub.resp.DcPublicExtListResp;
import zte.net.ecsord.params.pub.resp.DictRelationListResp;
import zte.net.ecsord.params.pub.resp.DropdownDataResp;
import zte.net.ecsord.params.pub.resp.LogiCompPersonGetResp;
import zte.net.ecsord.params.pub.resp.ZbDictCodeValueGetResp;
import zte.net.ecsord.params.zb.vo.orderattr.Activity;
import zte.net.ecsord.params.zb.vo.orderattr.Fee;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAtt;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttHK;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttHKHY;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttZBLJZD;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttZBPJ;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttZBWXSWK;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAttZDHY;
import zte.net.ecsord.params.zb.vo.orderattr.Package;
import zte.net.ecsord.params.zb.vo.orderattr.Resources;
import zte.net.ecsord.params.zb.vo.orderattr.SubProd;
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
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.model.AttrTableCache;

import consts.ConstsCore;

public class AttrUtils {
	IOrderServices orderServices;
	public static AttrUtils attrUtils;
	public static AttrUtils getInstance(){
		if(attrUtils==null)
			attrUtils = new AttrUtils();
		return attrUtils;
		
	}
	
	/**
	 * 获取属性值
	 * @param order_id 订单ID
	 * @param field_name 属性字段名
	 * 此方法建议废弃，后续根据field_name拿值
	 * @return
	 */
	@Deprecated
	public AttrInstBusiRequest getAttrInstByAttrInstId(String order_id,String attr_inst_id){
		//得到订单树
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		//从订单树种获取属性信息
		List<AttrInstBusiRequest> attrs = tree.getAttrInstBusiRequests();
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		for(AttrInstBusiRequest attr : attrs){
			if(attr_inst_id.equals(attr.getAttr_inst_id())){//如果field_name等于传入的key，返回其属性值
				return attr;
			}
		}
		return null;
	}
	

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
	public String getAttrFieldValue(String order_id,String field_name,String table_name,String spec_id){
		//得到订单树
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		//从订单树种获取属性信息
		List<AttrInstBusiRequest> attrs = tree.getAttrInstBusiRequests();
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		for(AttrInstBusiRequest attr : attrs){
			if(attr ==null)
				return "";
			if(field_name.equalsIgnoreCase(attr.getField_name()) 
				&& ( StringUtils.isEmpty(table_name) || table_name.equalsIgnoreCase(attr.getT_table_name()))  ){//如果field_name等于传入的key，返回其属性值
				value = attr.getField_value();
				break;
			}
		}
		
		//add by wui转换后拿值
		if(StringUtils.isEmpty(value)){
			String s_field_name = getFieldNameByAttrTableFieldName(field_name,table_name) ;
			if(!StringUtils.isEmpty(s_field_name) && !s_field_name.equals(field_name)){
				for(AttrInstBusiRequest attr : attrs){
					if(attr ==null)
						return "";
					if(s_field_name.equalsIgnoreCase(attr.getField_name())){//如果field_name等于传入的key，返回其属性值
						value = attr.getField_value();
						break;
					}
				}
			}
		}
		return value == null ? EcsOrderConsts.EMPTY_STR_VALUE : value;
	}
	/**
	 * 从历史订单树获取属性值
	 * @param order_id 订单ID
	 * @param field_name 属性字段名
	 * @return
	 */
	public String getAttrFieldValueHis(String order_id,String field_name,String table_name,String spec_id){
		//得到订单树
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		//从订单树种获取属性信息
		List<AttrInstBusiRequest> attrs = tree.getAttrInstBusiRequests();
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		for(AttrInstBusiRequest attr : attrs){
			if(field_name.equalsIgnoreCase(attr.getField_name())
					&& ( StringUtils.isEmpty(table_name) || table_name.equalsIgnoreCase(attr.getT_table_name())) ){//如果field_name等于传入的key，返回其属性值
				value = attr.getField_value();
				break;
			}
		}
		//add vy wui二次处理
		//add by wui转换后拿值
		if(StringUtils.isEmpty(value)){
			String s_field_name = getFieldNameByAttrTableFieldName(field_name,table_name) ;
			if(!StringUtils.isEmpty(s_field_name) && !s_field_name.equals(field_name)){
				for(AttrInstBusiRequest attr : attrs){
					if(attr ==null)
						return "";
					if(s_field_name.equalsIgnoreCase(attr.getField_name())){//如果field_name等于传入的key，返回其属性值
						value = attr.getField_value();
						break;
					}
				}
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
		//得到订单树
		//从订单树种获取属性信息
		List<AttrInstBusiRequest> attrs = tree.getAttrInstBusiRequests();
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		for(AttrInstBusiRequest attr : attrs){
			if(field_name.equalsIgnoreCase(attr.getField_name())
					&& ( StringUtils.isEmpty(table_name) || table_name.equalsIgnoreCase(attr.getT_table_name())) ){//如果field_name等于传入的key，返回其属性值
				value = attr.getField_value();
				break;
			}
		}
		//add by wui转换后拿值
		if(StringUtils.isEmpty(value)){
			String s_field_name = getFieldNameByAttrTableFieldName(field_name,table_name) ;
			if(!StringUtils.isEmpty(s_field_name) && !s_field_name.equals(field_name)){
				for(AttrInstBusiRequest attr : attrs){
					if(attr ==null)
						return "";
					if(s_field_name.equalsIgnoreCase(attr.getField_name())){//如果field_name等于传入的key，返回其属性值
						value = attr.getField_value();
						break;
					}
				}
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
			//得到订单树
			OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
			//从订单树种获取属性信息
			List<AttrInstBusiRequest> attrs = tree.getAttrInstBusiRequests();
			for(int i = 0; i < field_name.length; i++){
				String fieldName = field_name[i];
				String fieldValue = field_value[i];
				boolean isExist = false;
				for(AttrInstBusiRequest attr : attrs){
					if(fieldName.equals(attr.getField_name())){//如果field_name等于传入的key，返回其属性值
						isExist= true;
						break;
					}
				}
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
					for(AttrInstBusiRequest attr : attrs){
						if(fieldName.equals(attr.getField_name())){//如果field_name等于传入的key，返回其属性值
							attr.setField_value(fieldValue);
							attr.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							attr.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							attr.store();
							break;
						}
					}
					
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
		List<AttrInstBusiRequest> attrInstList = CommonDataFactory.getInstance()
				.getOrderTree(order_id).getAttrInstBusiRequests();
		if(null != attrInstList && !attrInstList.isEmpty()){
			for(int i = 0; i < attrInstList.size(); i++){
				AttrInstBusiRequest attrInst = attrInstList.get(i);
				if(field_name.equals(attrInst.getField_name())){
					zbValue = attrInst.getZBValue();
					break;//匹配到立马跳出，否则会遍历总部400多数据
				}
			}
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
		if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(package_sale)){
			sale_mode = EcsOrderConsts.SALE_MODE_FCPB;
		}
		else{
			sale_mode = EcsOrderConsts.SALE_MODE_CPB;
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
		goodsAtt.setPhoneNum(this.getAttrFieldValue(order_id, AttrConsts.PHONE_NUM,"",""));
		goodsAtt.setReliefPresFlag(this.getAttrFieldValue(order_id, AttrConsts.RELIEF_PRES_FLAG,"",""));
		goodsAtt.setSaleMode(this.getSaleMode(order_id));
		goodsAtt.setSimId(this.getAttrFieldValue(order_id, AttrConsts.SIMID,"",""));
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
		goodsAtt.setSubProdInfo(new ArrayList<SubProd>());
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
			String terminal_num = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getTerminal_num();
			if(StringUtils.isEmpty(terminal_num)){
				terminal_num = CommonDataFactory.getInstance().getTerminalNum(order_id);
			}
			resources.setResourcesCode(terminal_num);
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
		goodsAtt.setPhoneNum(this.getAttrFieldValue(order_id, AttrConsts.PHONE_NUM,"",""));
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
		goodsAtt.setSubProdInfo(new ArrayList<SubProd>());
		Resources resources = getResources(order_id);
	    if(resources!=null){
	    	goodsAtt.setResourcesInfo(resources);
	    }
		goodsAtt.setActivityInfo(null);
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
		goodsAtt.setPhoneNum(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
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
				.getAttrFieldValue(order_id, AttrConsts.SIMID));

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
		goodsAtt.setSubProdInfo(new ArrayList<SubProd>());
		list.add(goodsAtt);
		return list;
	}
	
	private List getPackages(String order_id){
//		List<AttrPackageBusiRequest> packages = CommonDataFactory.getInstance().getOrderTree(order_id).getPackageBusiRequests();
		List<Package> packageList = new ArrayList<Package>();
//		for(AttrPackageBusiRequest packageBusi :packages){
//			Package pkg = new Package();
//			pkg.setPackageCode(packageBusi.getPackageCode());
//			pkg.setPackageName(packageBusi.getPackageName());
//			pkg.setProductCode(packageBusi.getProductCode());
//			pkg.setElementCode(packageBusi.getElementCode());
//			pkg.setElementName(packageBusi.getElementName());
//			pkg.setElementType(packageBusi.getElementType());
//			pkg.setOperType(packageBusi.getOperType());
//			pkg.setChageType(packageBusi.getChageType());
//			pkg.setBizType(packageBusi.getBizType());
//			packageList.add(pkg);
//		}
		return packageList;
	}
	
	/**
	 * 合约计划信息
	 * @param order_id
	 * @return
	 */
	private List getActivityList(String order_id){
		List<Activity> activities = new ArrayList<Activity>();
		Activity activity = new Activity();
		activity.setActivityType(CommonDataFactory.getInstance().getZbValue(order_id, AttrConsts.PROD_CAT_ID));
		activity.setActivityCode(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_PACKAGE_ID));
		String actProtPer = CommonDataFactory.getInstance().getProductSpec(order_id, 
				SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT);
		activity.setActProtPer(actProtPer == null ? EcsOrderConsts.EMPTY_STR_VALUE : actProtPer);
		String activityName = CommonDataFactory.getInstance().getProductSpec(order_id, 
				SpecConsts.TYPE_ID_10001, null, SpecConsts.GOODS_NAME);
		activity.setActivityName(activityName == null ? EcsOrderConsts.EMPTY_STR_VALUE : activityName);
		activities.add(activity);
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
		goodsAtt.setPhoneNum(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
		goodsAtt.setProductBrand(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.PROD_BRAND));
		goodsAtt.setProductCode(CommonDataFactory.getInstance()
				.getProductSpec(order_id, EcsOrderConsts.PRODUCT_TYPE_OFFER, null, SpecConsts.ESS_CODE));
		goodsAtt.setProductName(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.GOODSNAME));
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
				.getAttrFieldValue(order_id, AttrConsts.SIMID));
		List<SubProd> SubProds = new ArrayList<SubProd>();				//附加产品信息
		goodsAtt.setSubProdInfo(SubProds);

		list.add(goodsAtt);
		return list;
	}
	
	public List<Fee> getFeeInfo(String order_id){
		List<Fee> fees = new ArrayList<Fee>();

//		List<AttrFeeInfoBusiRequest> attrFeeInfoBusiRequests = CommonDataFactory
//				.getInstance().getOrderTree(order_id).getFeeInfoBusiRequests();
//		if(attrFeeInfoBusiRequests!=null && attrFeeInfoBusiRequests.size()>0){
//			for (AttrFeeInfoBusiRequest attrFee : attrFeeInfoBusiRequests) {
//				Fee fee = new Fee();
//				fee.setFeeID(attrFee.getFee_item_code());
//				fee.setFeeDes(attrFee.getFee_item_name());
//				fee.setOrigFee(attrFee.getO_fee_num()==null?0D:attrFee.getO_fee_num()*1000);
//				fee.setRealFee(attrFee.getN_fee_num()==null?0D:attrFee.getN_fee_num()*1000);
//				fee.setReliefFee(attrFee.getDiscount_fee()==null?0D:attrFee.getDiscount_fee()*1000);
//				fee.setReliefResult(attrFee.getDiscount_reason()==null?EcsOrderConsts.EMPTY_STR_VALUE:attrFee.getDiscount_reason());
//				fees.add(fee);
//			}
//		}
		return fees;
	}
	
	public void insertOrderHandlerLogs(InsertOrderHandLogReq req){
		ZteClient client = getDubboZteClient();
		InsertOrderHandLogResp resp = client.execute(req, InsertOrderHandLogResp.class);
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

	/*判断是否需要展示物流信息*/
	public static String isShowShipVisiable(String order_id){
		//判断配送方式如果是无需配送，自提，现场交互，现场提货  不需要物流信息
		String str = EcsOrderConsts.IS_DEFAULT_VALUE;
	    String send_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.SENDING_TYPE);
	    if(EcsOrderConsts.SHIP_TYPE_ZT.equals(send_type)||EcsOrderConsts.SHIP_TYPE_XCTH.equals(send_type)||EcsOrderConsts.SHIPPING_TYPE_XJ.equals(send_type)||EcsOrderConsts.SHIP_TYPE_WX.equals(send_type)){
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
				refund_status = "退单确认通过";
			}else if(EcsOrderConsts.REFUND_STATUS_02.equals(status)){
				refund_status = "BSS已返销";
			}else if(EcsOrderConsts.REFUND_STATUS_03.equals(status)){
				refund_status = "ESS已返销";
			}else if(EcsOrderConsts.REFUND_STATUS_04.equals(status)){
				refund_status = "已退款";
			}else if(EcsOrderConsts.REFUND_STATUS_05.equals(status)){
				refund_status = "退单完成";
			}else if(EcsOrderConsts.REFUND_STATUS_06.equals(status)){
				refund_status = "已归档";
			}else if(EcsOrderConsts.REFUND_STATUS_07.equals(status)){
				refund_status = "退单结果已通知商城";
			}else if(EcsOrderConsts.REFUND_STATUS_08.equals(status)){
				refund_status = "退单申请";
			}else{
				refund_status = "正常单";
			}
		}
		return refund_status;
	}
	
	/**
	 * 获取属性值
	 * @param order_id 订单ID
	 * @param field_name 属性字段名
	 * @return
	 */
	public String getJsAttrFieldValue(String order_id,String field_name,String table_name){
		//得到订单树
//		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		//从订单树种获取属性信息
//		List<AttrInstBusiRequest> attrs = tree.getAttrInstBusiRequests();
//		String value = EcsOrderConsts.EMPTY_STR_VALUE;
//		for(AttrInstBusiRequest attr : attrs){
//			if(attr ==null)
//				return "";
//			if(field_name.equalsIgnoreCase(attr.getField_name()) 
//				&& ( StringUtils.isEmpty(table_name) || table_name.equalsIgnoreCase(attr.getT_table_name()))  ){//如果field_name等于传入的key，返回其属性值
//				value = attr.getField_value();
//				break;
//			}
//		}
		
		//add by wui转换后拿值
//		if(StringUtils.isEmpty(value)){
//			String s_field_name = getFieldNameByAttrTableFieldName(field_name,table_name) ;
//			if(!StringUtils.isEmpty(s_field_name) && !s_field_name.equals(field_name)){
//				for(AttrInstBusiRequest attr : attrs){
//					if(attr ==null)
//						return "";
//					if(s_field_name.equalsIgnoreCase(attr.getField_name())){//如果field_name等于传入的key，返回其属性值
//						value = attr.getField_value();
//						break;
//					}
//				}
//			}
//		}
		
		String value = EcsOrderConsts.EMPTY_STR_VALUE;
		//判断是否扩展属性...
		
        //非扩展属性...
		  
		//扩展属性(dyMap)
		AttrTableCache attrTableCache = getAttrTableByAttrTableFieldName(field_name,table_name);
		
		return value == null ? EcsOrderConsts.EMPTY_STR_VALUE : value;
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
}
