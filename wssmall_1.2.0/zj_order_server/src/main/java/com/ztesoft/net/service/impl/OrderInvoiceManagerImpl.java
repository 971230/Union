/**
 * 
 */
package com.ztesoft.net.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderItemsInvPrintsBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.FieldModelVO;
import com.ztesoft.net.model.InvoiceDto;
import com.ztesoft.net.model.InvoiceModeFieldVO;
import com.ztesoft.net.model.InvoiceModeVO;
import com.ztesoft.net.model.InvoiceModelVO;
import com.ztesoft.net.model.InvoicePrintInfo;
import com.ztesoft.net.service.IOrderInvoiceManager;

/**
 * @author ZX OrderInvoiceManagerImpl.java 2014-11-10
 */
public class OrderInvoiceManagerImpl extends BaseSupport implements IOrderInvoiceManager {
	
	@Override
	public String doPrintInvoice(String order_id,String realname,String order_is_his, String ctx) throws Exception {
		boolean isHis=false;
		String goods_type = "";
		if(order_is_his!=null&&EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//历史单
			isHis=true;
			 goods_type =CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, "goods_type");
		}else{
			isHis=false;
			 goods_type =CommonDataFactory.getInstance().getAttrFieldValue(order_id, "goods_type");
		}
		
		
		
		if (goods_type == null)
			goods_type = "";
		String htmlCode = "";
		InvoicePrintInfo vo = getInvoicePrintInfo(order_id, realname,isHis);
		if (goods_type.equals("20000")) { // 号卡
			htmlCode = dealHtmlHaoKa(vo, htmlHaoKa);
		} else if (goods_type.equals("20001")) { // 上网卡
			htmlCode = dealHtmlHaoKa(vo, htmlShangWangKa);
		} else if (goods_type.equals("20002")) { // 合约机
			htmlCode = dealHtmlHaoKa(vo, htmlHeYueji);
		} else {
			String message = "只支持续号卡、合约机、上网卡类型商品的打印";
			throw new Exception(message);
		}
		htmlCode = getHead(ctx)+htmlCode+button+tail;
		return htmlCode;
	}
	
	public boolean saveInvoicePrintDetail(InvoicePrintInfo invoicePrintInfo,boolean isHis,String realname) {
		boolean flag = true;
		String es_order_items_inv_prints_table="es_order_items_inv_prints";
		if(isHis){
			es_order_items_inv_prints_table=es_order_items_inv_prints_table+"_his";
		}
		if (invoicePrintInfo != null) {
			try {
				OrderItemsInvPrintsBusiRequest po = new OrderItemsInvPrintsBusiRequest();
				po.setOrder_id(invoicePrintInfo.getWaibudingdanbianhao()!=null?invoicePrintInfo.getWaibudingdanbianhao():"");
				po.setItem_id("");
				po.setInv_inst_id("");
				po.setSeq(0);
				po.setCreate_time(DateUtil.getTime1());
				po.setUname(invoicePrintInfo.getShouhuorenxingming()!=null?invoicePrintInfo.getShouhuorenxingming():"");
				po.setUtel(invoicePrintInfo.getShouhuorendianhua()!=null?invoicePrintInfo.getShouhuorendianhua():"");
				po.setOrder_amount(invoicePrintInfo.getYingshoujine()!=null?invoicePrintInfo.getYingshoujine():0);
				po.setPaymoney(invoicePrintInfo.getShishoujine()!=null?invoicePrintInfo.getShishoujine():0);
				po.setTerminal_code(invoicePrintInfo.getZhongduanchuanhao()!=null?invoicePrintInfo.getZhongduanchuanhao():"");
				po.setPrint_flag(EcsOrderConsts.IS_PRINT_1); // 1: 已打印  0: 未打印  2：以前打印
				po.setPrint_time(DateUtil.getTime2());
				String print_user="";
				try {//外部系统调用时，发票打印人为空!
					print_user=ManagerUtils.getAdminUser().getUsername();
				} catch (Exception e) {
					//e.printStackTrace();
				}
				
				if(realname!=null&&!realname.equals("")&&print_user.equals("")){
					print_user=realname;
				}
				
				po.setPrint_user(print_user);
				po.setInvoice_num(invoicePrintInfo.getInvoice_num()!=null?invoicePrintInfo.getInvoice_num():"");
				po.setMemo(invoicePrintInfo.getMemo()!=null?invoicePrintInfo.getMemo():"");
				Map<String,String> fields = new HashMap<String,String>();
				fields.put("print_flag",EcsOrderConsts.IS_PRINT_2);//将之前的记录设置为以前打印
				String order_id = invoicePrintInfo.getWaibudingdanbianhao()!=null?invoicePrintInfo.getWaibudingdanbianhao():"";
				String where = "order_id='"+order_id+"' and print_flag='"+EcsOrderConsts.IS_PRINT_1+"'";//查找已打印的记录
				baseDaoSupport.update(es_order_items_inv_prints_table, fields, where);
				baseDaoSupport.insert(es_order_items_inv_prints_table, po);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
		}
		return flag;
	}
	
	private String dealHtmlHaoKa(InvoicePrintInfo vo,String h) {
		String htmlCode;
		String replace = h.replace("$name", n(vo.getShouhuorenxingming()));
		replace = replace.replace("$tel", n(vo.getShouhuorendianhua()));
		String shiShou = n2(vo.getShishoujine() == null ? 0 : vo.getShishoujine());
		replace = replace.replace("$shiShou", shiShou);
		String actualPay = n2(vo.getYingshoujine()==null ? 0 : vo.getYingshoujine());
		replace = replace.replace("$yingShou", actualPay);
		replace = replace.replace("$daXie", CNValueOf(actualPay));
		replace = replace.replace("$chuanMa", n(vo.getZhongduanchuanhao()));
		replace = replace.replace("$order_id", n(vo.getWaibudingdanbianhao()));
		replace = replace.replace("$order_is_his", n(vo.getOrder_is_his()));
		String format = "";
		try {
			format = DateUtil.getTime1();
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, String> hashMap = new HashMap<String,String>();
		hashMap.put("printTime", format);
		hashMap.put("htmlCode", replace);
		hashMap.put("printUser", vo.getKaipiaoren());
		//hashMap.put("waiBuDingDanHao",n(vo.getWaibudingdanbianhao()));
		hashMap.put("faPiaoInput",faPiaoInput);
		hashMap.put("invoice_num_print",faPiaoPrint);
		htmlCode = doGetPJPrintHtmlCodes(hashMap);
		return htmlCode;
	}
	
	private String doGetPJPrintHtmlCodes (Map<String, String> paramMap) {
		String htmlCode = "";
		List<InvoiceModeFieldVO> list = getModeList(EcsOrderConsts.INVOICE_PRINT_MODE_CD);
		// 包含模板与域
		List<InvoiceDto> invoiceDtoList = new ArrayList<InvoiceDto>();
		InvoiceDto invoiceDto = new InvoiceDto();
		List<FieldModelVO> fieldModelVOList = new ArrayList<FieldModelVO>();
		// 模板值与id
		InvoiceModelVO invoiceModelVO = new InvoiceModelVO();
		
		if (list != null && list.size() > 0) {
			for (InvoiceModeFieldVO invoiceModeFieldVO : list) {				
				
				invoiceModelVO.setModelCd(invoiceModeFieldVO.getModel_cd());
				invoiceModelVO.setValue(invoiceModeFieldVO.getModel_cd());
				
				FieldModelVO fieldModelVO = new FieldModelVO();
				fieldModelVO.setFieldModelId(invoiceModeFieldVO.getField_cd());
				if (invoiceModeFieldVO.getField_cd().contains("0077")) {
					fieldModelVO.setValue(paramMap.get("printTime").toString());
				}
				if (invoiceModeFieldVO.getField_cd().contains("0078")) {
					fieldModelVO.setValue(paramMap.get("htmlCode").toString());
				}
				if (invoiceModeFieldVO.getField_cd().contains("0079")) {
					fieldModelVO.setValue(paramMap.get("printUser").toString());
				}
				/*if (invoiceModeFieldVO.getField_cd().contains("0081")) {
					fieldModelVO.setValue(paramMap.get("waiBuDingDanHao").toString());
				}*/
				if (invoiceModeFieldVO.getField_cd().contains("0082")) {
					fieldModelVO.setValue(paramMap.get("faPiaoInput").toString());
				}
				if (invoiceModeFieldVO.getField_cd().contains("0083")) {//备注信息区域
					fieldModelVO.setValue(paramMap.get("invoice_num_print").toString());
				}
				fieldModelVOList.add(fieldModelVO);
			}
			// 将值放入
			invoiceDto.setInvoiceModelVO(invoiceModelVO);
			invoiceDto.setFieldModelVoList(fieldModelVOList);
			// 将一个模板需要的信息加入到list
			invoiceDtoList.add(invoiceDto);
			
			htmlCode = invoicePrint(invoiceDtoList);
		}
		return htmlCode;
	}
	
	@Override
	public String invoicePrint(List<InvoiceDto> invoiceDtoList) {
		String html = "";
		try
		{
			for(int i=0; i<invoiceDtoList.size(); i++)
			{
				InvoiceDto iDto = invoiceDtoList.get(i);
				InvoiceModelVO modelVO = iDto.getInvoiceModelVO();
				//模板信息
				StringBuilder sql = new StringBuilder("SELECT T.* FROM ES_INVOICE_PRINT_MODEL T");
				sql.append(" WHERE T.MODEL_CD='"+modelVO.getModelCd()+"'");
				List<InvoiceModeVO> printModelList = baseDaoSupport.queryForList(sql.toString(), InvoiceModeVO.class);
				InvoiceModeVO printModelVo = printModelList.get(0);
				String imgUrl = printModelVo.getImg_url();
				String paperWidth = printModelVo.getPaper_width();
				String paperHeight = printModelVo.getPaper_height();

				StringBuffer htmlBuffer = new StringBuffer();
				
				List<FieldModelVO> fieldModelVoList = iDto.getFieldModelVoList();
				for(int n=0; n<fieldModelVoList.size(); n++)
				{
					FieldModelVO fieldModelVO = (FieldModelVO)fieldModelVoList.get(n);
					//打印域信息
					StringBuilder mode_field_sql = new StringBuilder("SELECT T.* FROM ES_INVOICE_MODEL_FIELD T");
					mode_field_sql.append(" where T.MODEL_CD='"+modelVO.getModelCd()+"'");
					mode_field_sql.append(" AND T.FIELD_CD='"+fieldModelVO.getFieldModelId()+"'");
					List<InvoiceModeFieldVO> modeFieldList = baseDaoSupport.queryForList(mode_field_sql.toString(), InvoiceModeFieldVO.class);
					//params.get
					InvoiceModeFieldVO modelFieldVO = modeFieldList.get(0);
					if (modelFieldVO == null)
						modelFieldVO = new InvoiceModeFieldVO();
					//String fontStyle = modelFieldVO.getFontStyle();
					int left = Integer.valueOf(modelFieldVO.getPos_x());
					int height = Integer.valueOf(modelFieldVO.getPos_y());
					String value = fieldModelVO.getValue();
					htmlBuffer.append("<div nowrap style='CURSOR:hand;#DEFFAC;PADDING-LEFT: 0px;");
					htmlBuffer.append("PADDING-TOP: 0px;PADDING-RIGHT: 0px; PADDING-BOTTOM: 0px;");
					htmlBuffer.append("position:absolute;top:");
					htmlBuffer.append(height+25);
					htmlBuffer.append("px;left:");
					htmlBuffer.append(left+10);
					htmlBuffer.append("px;'>\n");
					htmlBuffer.append(value+"\n");
					htmlBuffer.append("</div>\n");
				}
				html = htmlBuffer.toString();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return html;
	}
	
	@Override
	public Map<String,String> getHotFreeModelParams() {
		StringBuilder mode_field_sql = new StringBuilder("SELECT T.* FROM ES_INVOICE_MODEL_FIELD T where T.MODEL_CD='10025'");
		List<InvoiceModeFieldVO> modeFieldList = baseDaoSupport.queryForList(mode_field_sql.toString(), InvoiceModeFieldVO.class);
		Map<String,String> modeMap = new HashMap<String, String>();
		for(InvoiceModeFieldVO modeField:modeFieldList){
			int left = Integer.valueOf(modeField.getPos_x());
			int height = Integer.valueOf(modeField.getPos_y());
			StringBuffer htmlBuffer = new StringBuffer();
			htmlBuffer.append("CURSOR:hand;#DEFFAC;PADDING-LEFT: 0px;");
			htmlBuffer.append("PADDING-TOP: 0px;PADDING-RIGHT: 0px; PADDING-BOTTOM: 0px;");
			htmlBuffer.append("position:absolute;top:");
			htmlBuffer.append(height);
			htmlBuffer.append("px;left:");
			htmlBuffer.append(left);
			htmlBuffer.append("px;");
			modeMap.put(modeField.getField_cd(), htmlBuffer.toString());
		}
		return modeMap;
	}
	
	@Override
	public Map<String,String> getModelParams(String model_id) {
		StringBuilder mode_field_sql = new StringBuilder("SELECT T.* FROM ES_INVOICE_MODEL_FIELD T where T.MODEL_CD='"+model_id+"'");
		List<InvoiceModeFieldVO> modeFieldList = baseDaoSupport.queryForList(mode_field_sql.toString(), InvoiceModeFieldVO.class);
		Map<String,String> modeMap = new HashMap<String, String>();
		for(InvoiceModeFieldVO modeField:modeFieldList){
			int left = Integer.valueOf(modeField.getPos_x());
			int height = Integer.valueOf(modeField.getPos_y());
			StringBuffer htmlBuffer = new StringBuffer();
			htmlBuffer.append("CURSOR:hand;#DEFFAC;PADDING-LEFT: 0px;");
			htmlBuffer.append("PADDING-TOP: 0px;PADDING-RIGHT: 0px; PADDING-BOTTOM: 0px;");
			htmlBuffer.append("position:absolute;top:");
			htmlBuffer.append(height);
			htmlBuffer.append("px;left:");
			htmlBuffer.append(left);
			htmlBuffer.append("px;");
			htmlBuffer.append("font-weight: bold;");
			modeMap.put(modeField.getField_cd(), htmlBuffer.toString());
		}
		return modeMap;
	}
	
	/**
	 * 根据mode_cd查询域集合
	 * @param mode_cd(EcsOrderConsts.INVOICE_PRINT_MODE_CD)
	 * @return
	 */
	private List<InvoiceModeFieldVO> getModeList (String mode_cd) {
		List<InvoiceModeFieldVO> list = null;
		StringBuilder sql = new StringBuilder("SELECT T.* FROM ES_INVOICE_MODEL_FIELD T");
		sql.append(" WHERE T.MODEL_CD='"+mode_cd+"'");
		list = baseDaoSupport.queryForList(sql.toString(), InvoiceModeFieldVO.class);
		return list;
	}
	
	/**
	 * 根据order_id获取打印界面的数据
	 * @param order_id
	 * @return
	 */
	private InvoicePrintInfo getInvoicePrintInfo(String order_id,String realname,boolean isHis) {
		InvoicePrintInfo invoicePrintInfo = new InvoicePrintInfo();
		// 收货人姓名
		String shouhuorenxingming ="";
		// 商品类型
		String goods_type = "";
		// 收货人电话
		String shouhuorendianhua = "";
		// 实收金额
		String shishoujine = "";
		
		// 应收金额
		String yingshoujine = "";
		
		// 终端串号
		String zhongduanchuanhao = "";
		// 活动预存款
		String huodongyucunkuan = "";
		String kaihuhaoma = "";
		// 靓号预存款
		String lianghaoyucun = "";
		OrderTreeBusiRequest orderTree =null;
		if(isHis){
			 orderTree =CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		}else{
			 orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}
	    if(orderTree!=null){
	    	if(orderTree.getOrderBusiRequest().getOrder_amount()!=null){
	    		yingshoujine	=orderTree.getOrderBusiRequest().getOrder_amount().toString();
	    	}
	    	if(orderTree.getOrderBusiRequest().getPaymoney()!=null){
	    		shishoujine=orderTree.getOrderBusiRequest().getPaymoney().toString();
	    	}
	    	
	    }
		
		if(isHis){//历史数据
			invoicePrintInfo.setOrder_is_his(EcsOrderConsts.IS_ORDER_HIS_YES);//设置信息是历史数据
			// 收货人姓名
			 shouhuorenxingming = CommonDataFactory.getInstance()
					.getAttrFieldValueHis(order_id, "ship_name");
			// 商品类型
		    goods_type = CommonDataFactory.getInstance().getAttrFieldValueHis(
					order_id, "goods_type");
			// 收货人电话
			 shouhuorendianhua = CommonDataFactory.getInstance()
					.getAttrFieldValueHis(order_id, "reference_phone");
			// 实收金额
			// shishoujine = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, "pro_realfee");
			 
			 
			if (StringUtils.isBlank(shishoujine))
				shishoujine = "0";
			// 应收金额
			// yingshoujine = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, "pro_origfee");
			
			if (StringUtils.isBlank(yingshoujine))
				yingshoujine = "0";
			// 终端串号
			 zhongduanchuanhao = CommonDataFactory.getInstance()
					.getAttrFieldValueHis(order_id, "terminal_num");
			// 活动预存款
			 huodongyucunkuan = CommonDataFactory.getInstance()
					.getAttrFieldValueHis(order_id, "phone_deposit");
			 kaihuhaoma = CommonDataFactory.getInstance().getAttrFieldValueHis(
					order_id, "phone_num");
			// 靓号预存款
			 lianghaoyucun = "";
			if (StringUtils.isNotBlank(kaihuhaoma))
				lianghaoyucun = CommonDataFactory.getInstance().getNumberSpec(
						kaihuhaoma, SpecConsts.NUMERO_DEPOSIT);
			if (StringUtils.isBlank(lianghaoyucun))
				lianghaoyucun = "0";
		}else{
			
			// 收货人姓名
			 shouhuorenxingming = CommonDataFactory.getInstance()
					.getAttrFieldValue(order_id, "ship_name");
			// 商品类型
		    goods_type = CommonDataFactory.getInstance().getAttrFieldValue(
					order_id, "goods_type");
			// 收货人电话
			 shouhuorendianhua = CommonDataFactory.getInstance()
					.getAttrFieldValue(order_id, "reference_phone");
			// 实收金额
			// shishoujine = CommonDataFactory.getInstance().getAttrFieldValue(
			//		order_id, "pro_realfee");
			if (StringUtils.isBlank(shishoujine))
				shishoujine = "0";
			// 应收金额
		//	 yingshoujine = CommonDataFactory.getInstance()
		//			.getAttrFieldValue(order_id, "pro_origfee");
			if (StringUtils.isBlank(yingshoujine))
				yingshoujine = "0";
			// 终端串号
			 zhongduanchuanhao = CommonDataFactory.getInstance()
					.getAttrFieldValue(order_id, "terminal_num");
			// 活动预存款
			 huodongyucunkuan = CommonDataFactory.getInstance()
					.getAttrFieldValue(order_id, "phone_deposit");
			 kaihuhaoma = CommonDataFactory.getInstance().getAttrFieldValue(
					order_id, "phone_num");
			// 靓号预存款
			 lianghaoyucun = "";
			if (StringUtils.isNotBlank(kaihuhaoma))
				lianghaoyucun = CommonDataFactory.getInstance().getNumberSpec(
						kaihuhaoma, SpecConsts.NUMERO_DEPOSIT);
			if (StringUtils.isBlank(lianghaoyucun))
				lianghaoyucun = "0";
		
			
		}
		
		
		//set value
		invoicePrintInfo
				.setShouhuorenxingming(shouhuorenxingming != null ? shouhuorenxingming
						: "");
		invoicePrintInfo
				.setShouhuorendianhua(shouhuorendianhua != null ? shouhuorendianhua
						: "");
		invoicePrintInfo.setShishoujine(Float.valueOf(shishoujine));
		invoicePrintInfo.setYingshoujine(Float.valueOf(yingshoujine));
		invoicePrintInfo.setGoods_type(goods_type != null ? goods_type : "");
		invoicePrintInfo.setWaibudingdanbianhao(order_id != null ? order_id
				: "");
		invoicePrintInfo.setDaxie(CNValueOf(yingshoujine));
		invoicePrintInfo
				.setZhongduanchuanhao(zhongduanchuanhao != null ? zhongduanchuanhao
						: "");
		invoicePrintInfo
				.setHuodongyucunkuan(huodongyucunkuan != null ? huodongyucunkuan
						: "");
		invoicePrintInfo.setLianghaoyucun(Float.valueOf(lianghaoyucun));
	
		String Kaipiaoren="";
		try {//外部系统调用时，发票人为空!
			Kaipiaoren=ManagerUtils.getAdminUser().getUsername();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		if(realname!=null&&!realname.equals("")&&(Kaipiaoren==null||Kaipiaoren.equals(""))){
			Kaipiaoren=realname;
		}
		
		invoicePrintInfo.setKaipiaoren(Kaipiaoren);
		try {
			invoicePrintInfo.setKaipiaoshijian(DateUtil.getTime1());
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invoicePrintInfo;
	}
	
	public static String n(String s)
	{
		if(StringUtils.isEmpty(s))
		{
			return "";
		}
		return s;
	}
	
	public static String n2(Float f)
	{
		String dd="";
		if(null == f)
		{
			return "";
		}else{
			DecimalFormat fnum=new DecimalFormat("##0.00");
			dd=fnum.format(f);
		}

		return dd;

	}

	
	private final static String[] CN_Digits = { "零", "壹", "貳", "叁", "肆", "伍",
			"陆", "柒", "捌", "玖", };

	/**
	 * 将数字型货币转换为中文型货币 <br/>
	 * 作者：ZX　时间：2014-11-13<br/>
	 * 
	 * @param moneyValue
	 *            　字符串形式的金额，小数部分，将多于3位部分舍去，不做四舍五入
	 * @return
	 */
	private static String CNValueOf(String moneyValue) {
		// 使用正则表达式，去除前面的零及数字中的逗号
		String value = moneyValue.replaceFirst("^0+", "");
		value = value.replaceAll(",", "");
		// 分割小数部分与整数部分
		int dot_pos = value.indexOf('.');
		String int_value;
		String fraction_value;
		if (dot_pos == -1) {
			int_value = value;
			fraction_value = "00";
		} else {
			int_value = value.substring(0, dot_pos);
			fraction_value = value.substring(dot_pos + 1, value.length())
					+ "00".substring(0, 2);// 也加两个0，便于后面统一处理
		}

		int len = int_value.length();
		if (len > 16)
			return "值过大";
		StringBuffer cn_currency = new StringBuffer();
		String[] CN_Carry = new String[] { "", "万", "亿", "万" };
		// 数字分组处理，计数组数
		int cnt = len / 4 + (len % 4 == 0 ? 0 : 1);
		// 左边第一组的长度
		int partLen = len - (cnt - 1) * 4;
		String partValue = null;
		boolean bZero = false;// 有过零
		String curCN = null;
		for (int i = 0; i < cnt; i++) {
			partValue = int_value.substring(0, partLen);
			int_value = int_value.substring(partLen);
			curCN = Part2CN(partValue, i != 0 && !"零".equals(curCN));
			// logger.info(partValue+":"+curCN);
			// 若上次为零，这次不为零，则加入零
			if (bZero && !"零".equals(curCN)) {
				cn_currency.append("零");
				bZero = false;
			}
			if ("零".equals(curCN))
				bZero = true;
			// 若数字不是零，加入中文数字及单位
			if (!"零".equals(curCN)) {
				cn_currency.append(curCN);
				cn_currency.append(CN_Carry[cnt - 1 - i]);
			}
			// 除最左边一组长度不定外，其它长度都为4
			partLen = 4;
			partValue = null;
		}
		cn_currency.append("元");
		// 处理小数部分
		int fv1 = Integer.parseInt(fraction_value.substring(0, 1));
		int fv2 = Integer.parseInt(fraction_value.substring(1, 2));
		if (fv1 + fv2 == 0) {
			cn_currency.append("整");
		} else {
			cn_currency.append(CN_Digits[fv1]).append("角");
			cn_currency.append(CN_Digits[fv2]).append("分");
		}
		return cn_currency.toString();
	}

	/**
	 * 将一组数字（不多于四个）转化成中文表示 <br/>
	 * 作者：ZX　时间：2014-11-13<br/>
	 *
	 * @param partValue
	 *            字符串形式的数字
	 * @param bInsertZero
	 *            是否在前面添加零
	 * @return
	 */
	private static String Part2CN(String partValue, boolean bInsertZero) {
		// 使用正则表达式，去除前面的0
		partValue = partValue.replaceFirst("^0+", "");
		int len = partValue.length();
		if (len == 0)
			return "零";
		StringBuffer sbResult = new StringBuffer();
		int digit;
		String[] CN_Carry = new String[] { "", "拾", "佰", "仟" };
		for (int i = 0; i < len; i++) {
			digit = Integer.parseInt(partValue.substring(i, i + 1));
			if (digit != 0) {
				sbResult.append(CN_Digits[digit]);
				sbResult.append(CN_Carry[len - 1 - i]);
			} else {
				// 若不是最后一位，且下不位不为零，追加零
				if (i != len - 1
						&& Integer.parseInt(partValue.substring(i + 1, i + 2)) != 0)
					sbResult.append("零");
			}
		}
		if (bInsertZero && len != 4)
			sbResult.insert(0, "零");
		return sbResult.toString();
	}

	String button = "<div style='text-align:center;margin-top:100px;CURSOR:hand;#DEFFAC;position:absolute;bottom:0px;"
			+ "padding:0 auto;padding-left:45%;'><input type='button' name='button' id='button' value='打印' "
			+ "class='noprint' onclick='clickPrintBtn();' style='background:url(../images/btnback.jpg) "
			+ "repeat-x 0 0 #f8f8f8; color:#333; border:1px solid #aaa; height:22px;' /></div>";

	private String getHead(String ctx) {
		String head = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>"
				+ "<html xmlns='http://www.w3.org/1999/xhtml'>                                                                              "
				+ "<head>                                                                                                                   "
				+ "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />                                                    "
				+ "<script>var ctx ='"+ctx+"';</script>"
				+ "<script src='"+ctx+"/ecs_ord/js/invoice_print.js'></script>" 
				+ "<script type='text/javascript' src='"+ctx+"/publics/js/jquery-1.8.1.min.js'></script>"
				+ "<script type='text/javascript' src='"+ctx+"/publics/js/common/common.js'></script>"
				+ "</head>                                                                                                                  "
				+ "<body>";
		return head;
	}

	String tail = "</body>" + "</html>";

	String htmlHaoKa = "<table width='100%' border='0'>                                          "
			+ "  <tr>                                                                   "
			+ "    <th scope='row' width='200'><div align='left'></div></th>           "
			+ "    <td width='200'>&nbsp;</td>                                                      "
			+ "  </tr>                                                                  "
			+ "  <tr>                                                                   "
			+ "    <th scope='row'><div align='left'>$name</div></th>                 "
			+ "    <td><div style='padding-left:50px;'>$tel</div></td>           "
			+ "  </tr>                                                                  "
			+ "  <tr>                                                                   "
			+ "    <th scope='row' width='200'><div align='left'></div></th>           "
			+ "    <td>&nbsp;</td>                                                      "
			+ "  </tr>                                                                  "
			+ "  <tr>                                                                   "//备注信息
			+ "    <th scope='row' colspan='2'><div align='left'><input type='text' id='memo' value='备注信息' disabled='disabled'/><input type='text' id='memo_info' name='memo_info' value='' /></div></th>                 "
//			+ "    <td><div style='padding-left:50px;'></div></td>           "
			+ "  </tr>                                                                  "//
			+ "  <tr>                                                                   "
			+ "    <th scope='row'><div align='left'></div></th>                       "
			+ "    <td>&nbsp;</td>                                                      "
			+ "  </tr>                                                                  "
			+ "  <tr>                                                                   "
			+ "    <th scope='row'><div align='left'></div></th>                       "
			+ "    <td>&nbsp;</td>                                                      "
			+ "  </tr>                                                                  "
			+ "  <tr>                                                                   "
			+ "    <th scope='row'><div align='left'>实收</div></th>            "
			+ "    <td><div style='padding-left:50px;'>$shiShou</div></td>                                                      "
			+ "  </tr>                                                                  "
			+ "  <tr>                                                                   "
			+ "    <th scope='row'><div align='left'>$daXie</div></th>               "
			+ "    <td><div style='padding-left:50px;'>$yingShou</div></td>                "
			+ "  </tr>                                                                  "
			+ "<input type='hidden' id='name' name='name' value='$name' />"
			+ "<input type='hidden' id='tel' name='tel' value='$tel' />"
			+ "<input type='hidden' id='shishou' name='shishou' value='$shiShou' />"
			+ "<input type='hidden' id='daxie' name='daxie' value='$daXie' />"
			+ "<input type='hidden' id='yingshou' name='yingshou' value='$yingShou' />"
			+ "<input type='hidden' id='chuanma' name='chuanma' value='$chuanMa' />"
			+ "<input type='hidden' id='order_id' name='order_id' value='$order_id' />"
			+ "<input type='hidden' id='order_is_his' name='order_is_his' value='$order_is_his' />"
			+ "</table>";

	String htmlHeYueji = "<table width='100%' border='0'>                                            "
			+ "  <tr>                                                                     "
			+ "    <th scope='row' width='200'><div align='left'></div></th>             "
			+ "    <td width='200'>&nbsp;</td>                                                      "
			+ "  </tr>                                                                    "
			+ "  <tr>                                                                     "
			+ "    <th scope='row'><div align='left'>$name</div></th>                   "
			+ "    <td><div style='padding-left:50px;'>$tel</div></td>             "
			+ "  </tr>                                                                    "
			+ "  <tr>                                                                   "
			+ "    <th scope='row' width='200'><div align='left'></div></th>           "
			+ "    <td>&nbsp;</td>                                                      "
			+ "  </tr>                                                                  "
			+ "  <tr>                                                                   "//备注信息
			+ "    <th scope='row' colspan='2'><div align='left'><input type='text' id='memo' value='备注信息' disabled='disabled'/><input type='text' id='memo_info' name='memo_info' value='' /></div></th>                 "
//			+ "    <td><div style='padding-left:50px;'></div></td>           "
			+ "  </tr>                                                                  "//
			+ "  <tr>                                                                     "
			+ "    <th scope='row'><div align='left'></div></th>                         "
			+ "    <td>&nbsp;</td>                                                        "
			+ "  </tr>                                                                    "
			+ "  <tr>                                                                     "
			+ "    <th scope='row'><div align='left'></div></th>                         "
			+ "    <td>&nbsp;</td>                                                        "
			+ "  </tr>                                                                    "
			+ "  <tr>                                                                     "
			+ "    <th scope='row'><div align='left'>$chuanMa</div></th>                 "
			+ "    <td>&nbsp;</td>                                                        "
			+ "  </tr>                                                                    "
			+ "  <tr>                                                                     "
			+ "    <th scope='row'><div align='left'></div></th>                         "
			+ "    <td>&nbsp;</td>                                                        "
			+ "  </tr>                                                                    "
			+ "  <tr>                                                                     "
			+ "    <th scope='row'><div align='left'>实收</div></th>              "
			+ "    <td><div style='padding-left:50px;'>$shiShou</div></td>                                                        "
			+ "  </tr>                                                                    "
			+ "  <tr>                                                                     "
			+ "    <th scope='row'><div align='left'>$daXie</div></th>                 "
			+ "    <td><div style='padding-left:50px;'>$yingShou</div></td>                  "
			+ "  </tr>                                                                    "
			+ "<input type='hidden' id='name' name='name' value='$name' />"
			+ "<input type='hidden' id='tel' name='tel' value='$tel' />"
			+ "<input type='hidden' id='shishou' name='shishou' value='$shiShou' />"
			+ "<input type='hidden' id='daxie' name='daxie' value='$daXie' />"
			+ "<input type='hidden' id='yingshou' name='yingshou' value='$yingShou' />"
			+ "<input type='hidden' id='chuanma' name='chuanma' value='$chuanMa' />"
			+ "<input type='hidden' id='order_id' name='order_id' value='$order_id' />"
			+ "<input type='hidden' id='order_is_his' name='order_is_his' value='$order_is_his' />"
			+ "</table>";
	
	String htmlShangWangKa = "<table width='100%' border='0'>                                "
			+ "  <tr>                                                         "
			+ "    <th scope='row' width='200'><div align='left'></div></th> "
			+ "    <td width='200'>&nbsp;</td>                                                      "
			+ "  </tr>                                                        "
			+ "  <tr>                                                         "
			+ "    <th scope='row'><div align='left'>$name</div></th>       "
			+ "    <td><div style='padding-left:50px;'>$tel</div></td> "
			+ "  </tr>                                                        "
			+ "  <tr>                                                                   "
			+ "    <th scope='row' width='200'><div align='left'></div></th>           "
			+ "    <td>&nbsp;</td>                                                      "
			+ "  </tr>                                                                  "
			+ "  <tr>                                                                   "//备注信息
			+ "    <th scope='row' colspan='2'><div align='left'><input type='text' id='memo' value='备注信息' disabled='disabled'/><input type='text' id='memo_info' name='memo_info' value='' /></div></th>                 "
//			+ "    <td><div style='padding-left:50px;'></div></td>           "
			+ "  </tr>                                                                  "//
			+ "  <tr>                                                         "
			+ "    <th scope='row'><div align='left'></div></th>             "
			+ "    <td>&nbsp;</td>                                            "
			+ "  </tr>                                                        "
			+ "  <tr>                                                         "
			+ "    <th scope='row'><div align='left'></div></th>             "
			+ "    <td>&nbsp;</td>                                            "
			+ "  </tr>                                                        "
			+ "  <tr>                                                         "
			+ "    <th scope='row'><div align='left'>实收</div></th>  "
			+ "    <td><div style='padding-left:50px;'>$shiShou</div></td>                                            "
			+ "  </tr>                                                        "
			+ "  <tr>                                                         "
			+ "    <th scope='row'><div align='left'>$daXie</div></th>     "
			+ "    <td><div style='padding-left:50px;'>$yingShou</div></td>      "
			+ "  </tr>                                                        "
			+ "<input type='hidden' id='name' name='name' value='$name' />"
			+ "<input type='hidden' id='tel' name='tel' value='$tel' />"
			+ "<input type='hidden' id='shishou' name='shishou' value='$shiShou' />"
			+ "<input type='hidden' id='daxie' name='daxie' value='$daXie' />"
			+ "<input type='hidden' id='yingshou' name='yingshou' value='$yingShou' />"
			+ "<input type='hidden' id='chuanma' name='chuanma' value='$chuanMa' />"
			+ "<input type='hidden' id='order_id' name='order_id' value='$order_id' />"
			+ "<input type='hidden' id='order_is_his' name='order_is_his' value='$order_is_his' />"
			+ "</table>";
	


   String faPiaoInput =  "<input type='text' id='invoice' value='发票号' disabled='disabled' width='30'/><input type='text' id='invoice_num' name='invoice_num' value='' />";
   String faPiaoPrint =  "<input type='hidden' id='invoice_num_print' name='invoice_num_print'/>";
	
	
	@Override
	public OrderItemsInvPrintsBusiRequest getPrintReq(String order_id,boolean isHis) {
		String es_order_items_inv_prints_table="es_order_items_inv_prints";
		if(isHis){
			es_order_items_inv_prints_table=es_order_items_inv_prints_table+"_his";
		}
		
		StringBuilder sql = new StringBuilder("select t.* from "+es_order_items_inv_prints_table+" t where t.order_id='"+order_id+"'");
		OrderItemsInvPrintsBusiRequest req = (OrderItemsInvPrintsBusiRequest) baseDaoSupport.queryForObject(sql.toString(), OrderItemsInvPrintsBusiRequest.class);
		return req;
	}

}
