package com.ztesoft.net.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.logistics.LogisticsFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.iservice.IOrderServices;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.params.order.req.DeliveryItemsQueryByDeIdReq;
import zte.params.order.resp.DeliveryItemsQueryByDeIdResp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.DataUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.StrTools;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.DeliveryPrints;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.utils.MoneyUtil;
import com.ztesoft.net.model.FieldModelVO;
import com.ztesoft.net.model.InvoiceDto;
import com.ztesoft.net.model.InvoiceModeFieldVO;
import com.ztesoft.net.model.InvoiceModelVO;
import com.ztesoft.net.service.IDeliveryPrintsManager;
import com.ztesoft.net.service.IOrdPostModePrintManager;
import com.ztesoft.net.service.IOrderInvoiceManager;
import com.ztesoft.net.service.IOrderSupplyManager;
import com.ztesoft.net.sqls.SF;

import consts.ConstsCore;

/**
 * 物流打印处理类
 */
public class OrdPostModePrintManager extends BaseSupport implements IOrdPostModePrintManager {
	@Resource
	private IOrderServices orderServices;

	@Resource
	private IDeliveryPrintsManager deliveryPrintsManager;
	@Resource
	private IOrderInvoiceManager iOrderInvoiceManager;
	@Resource
	private IOrderSupplyManager iOrderSupplyManager;

	private int substr20 = 20;
	private int substr18 = 18;
	private int substr16 = 16;
	private int substr12 = 12;
	private int substr8 = 8;
	private boolean isHis = false;
	public DecimalFormat df = new DecimalFormat("0000");

	/**
	 * @param order_id         --订单id
	 * @param es_delivery      --物流表es_delivery ID
	 * @param delvery_print_id --物流打印表es_delivery_prints ID
	 * @param reissueIds       --勾选的补寄品es_delivery_item id
	 * @param postType         --物流类型 0-正常 发货 1-补寄 2-重发 * @param wlnum --物流ID
	 *                         * @param postId --物流公司ID * @param cxt -跟路径
	 * @param order_is_his     --是否是历史数据 1-是
	 */
	@Override
	public String doPrintInvoice(String order_id, String delivery_id, String delvery_print_id, String reissueIds,
			String postType, String wlnum, String postId, String cxt, String order_is_his) {
		String htmlCode = "";

		String rs0018 = "";
		String rs0019 = "";
		String rs0020 = "";
		String rs0021 = "";
		String rs0022 = "";
		String rs0023 = "";
		String rs0024 = "";
		String rs0025 = "";
		String rs0026 = "";
		String rs0027 = "";
		String rs0028 = "";
		String rs0029 = "";
		String rs0030 = "";
		String rs0031 = "";
		String rs0032 = "";
		String rs0033 = "";
		String rs0034 = "";
		String rs0035 = "";
		String rs0036 = "";
		String rs0037 = "";
		String rs0038 = "";
		String rs0039 = "";
		String rs0040 = "";
		String rs0041 = "";
		String rs0042 = "";
		String rs0043 = "";
		String rs0044 = "";
		String rs0045 = "";
		String rs0046 = "";
		String rs0047 = "";
		String rs0048 = "";
		String rs0049 = "";
		String rs0050 = "";
		String rs0051 = "";
		String rs0052 = "";
		String rs0053 = "";
		String rs0054 = "";
		String rs0055 = "";
		String rs0056 = "";
		String rs0057 = "";
		String rs0058 = "";
		String rs0059 = "";
		String rs0060 = "";
		String rs0061 = "";
		String rs0062 = "";
		String rs0063 = "";
		String rs0064 = "";
		String rs0065 = "";
		String rs0066 = "";
		String rs0067 = "";
		String rs0068 = "";
		String rs0069 = "";
		String rs0070 = "";
		String rs0071 = "";
		String rs0072 = "";
		String rs0073 = "";
		String rs0089 = "";

		String postCompCode = "";
		String modelId = "";
		isHis = false;
		if (EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)) {// 是历史单
			isHis = true;
		}

		// 获取订单信息
		OrderTreeBusiRequest orderTree = null;
		if (isHis) {// 是历史单
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		} else {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}

		OrderDeliveryBusiRequest delivery = new OrderDeliveryBusiRequest();
		if (isHis) {// 是历史订单（），则不能从订单树获取
			// 根据delivery_id查询数据库表delivery
			List<OrderDeliveryBusiRequest> dlist = queryDelieryById(delivery_id);// 更改从his表查询
			if (dlist != null && dlist.size() > 0) {
				delivery = dlist.get(0);
				// ship_name 放在ES_ORDER表
				String ship_name = orderTree.getOrderBusiRequest().getShip_name();
				delivery.setShip_name(ship_name);
			}

		} else {
			// 获取本次物流信息 es_delivery 根据页面传送过来的delivery_id从列表中过滤
			List<OrderDeliveryBusiRequest> deList = orderTree.getOrderDeliveryBusiRequests();
			for (OrderDeliveryBusiRequest orderDelivery : deList) {
				if (orderDelivery.getDelivery_id().equals(delivery_id)) {
					delivery = orderDelivery;
					// ship_name 放在ES_ORDER表
					String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
							AttrConsts.SHIP_NAME);
					delivery.setShip_name(ship_name);
				}
			}
		}

		List<OrderDeliveryItemBusiRequest> deliveryItemslist = new ArrayList<OrderDeliveryItemBusiRequest>();
		if (postType.equals(EcsOrderConsts.LOGIS_SUPPLY)) {// 补寄才管补寄品
			if (isHis) {
				deliveryItemslist = iOrderSupplyManager.queryDeliveryItemsByDeId(delivery_id, true);
			} else {
				DeliveryItemsQueryByDeIdReq ItemsReq = new DeliveryItemsQueryByDeIdReq();
				ItemsReq.setDelivery_id(delivery_id);
				DeliveryItemsQueryByDeIdResp ItemsResp = orderServices.queryDeliveryItemsByDeId(ItemsReq);
				List<DeliveryItem> deliveryItemslistAll = ItemsResp.getDeliveryItems();
				for (DeliveryItem deliveryItem : deliveryItemslistAll) {
					OrderDeliveryItemBusiRequest items = new OrderDeliveryItemBusiRequest();
					items.setBuy_num(deliveryItem.getNum());
					items.setName(deliveryItem.getName());
					deliveryItemslist.add(items);

				}
			}

		}

		// 获取es_delivery_prints表的数据 根据页面传送过来 的主键delivery_prints_id查询
		DeliveryPrints print = deliveryPrintsManager.get(delvery_print_id);
		if (print == null) {
			print = new DeliveryPrints();
		}

		if (wlnum == null || wlnum.equals("")) {
			// 自动生成的物流单号
			wlnum = this.getUniqueRandomNum();
		}

		// 物流公司编码
		postCompCode = getComCodeByComId(postId);
		modelId = getModelId(postId, order_id);
		// modelId="10008";//测试代码

		// 属性取值
		rs0018 = this.getRs0018(delivery);
		// rs0019="";//总金额
		// rs0020="";//取货时间
		// rs0021="";//拣货单表格
		rs0022 = this.getRs0022(delivery, postCompCode);
		rs0023 = this.getRs0023(delivery);
		rs0024 = this.getRs0024(delivery);
		rs0025 = this.getRs0025(print);
		rs0026 = this.getRs0026(print);
		rs0027 = this.getRs0027(print);
		rs0028 = this.getRs0028(order_id);
		rs0029 = this.getRs0029(orderTree);
		// rs0030="";协议单表格
		rs0031 = this.getRs0031(delivery, wlnum, postId, cxt);
		// rs0032="";回收资料
		rs0033 = this.getRs0033(print);
		rs0034 = this.getRs0034(print);
		rs0035 = this.getRs0035(print);
		rs0036 = this.getRs0036(print);
		rs0037 = this.getRs0037(print);
		rs0038 = this.getRs0038(print);
		rs0039 = this.getRs0039(print);
		rs0040 = this.getRs0040(print);
		rs0041 = this.getRs0041(print);
		rs0042 = this.getRs0042(print);
		rs0043 = this.getRs0043(print);
		rs0089 = this.getRs0026(print);
		if (!(EcsOrderConsts.IS_COD_YES).equals(print.getIs_cod())) {
			print.setReceiv_account("");
		}
		rs0044 = this.getRs0044(print);
		rs0045 = this.getRs0045(print);
		// rs0046 --rs0049
		if (print != null) {
			String postMode = print.getPostage_mode();
			if (EcsOrderConsts.POSTAGE_MODE_JF.equals(postMode)) {
				rs0046 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else if (EcsOrderConsts.POSTAGE_MODE_SF.equals(postMode)) {
				rs0047 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else if (EcsOrderConsts.POSTAGE_MODE_DSF.equals(postMode)) {
				rs0048 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else if (EcsOrderConsts.POSTAGE_MODE_JZZF.equals(postMode)) {
				rs0049 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else if (EcsOrderConsts.POSTAGE_MODE_JFDSF.equals(postMode)) {
				rs0046 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
				rs0048 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			}
		}
		String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME);
		rs0050 = this.getRs0050(print, postCompCode, ship_name);
		rs0051 = this.getRs0051(print);
		rs0052 = this.getRs0052(print);
		rs0053 = this.getRs0053(print);
		rs0054 = this.getRs0054(print);
		rs0055 = this.getRs0055(delivery, deliveryItemslist);
		rs0056 = this.getRs0056(print);
		rs0057 = this.getRs0057(print);
		rs0058 = this.getRs0058(print);
		rs0059 = this.getRs0059(print);
		rs0060 = this.getRs0060(print);
		rs0061 = this.getRs0061(print, delivery);
		rs0062 = this.getRs0062(print);

		// rs0063 --rs0068
		if (print != null) {// 63-68 print -sp_del没有入库 待改动
			String spdel = print.getSp_del();
			if ("01".equals(spdel)) {
				rs0063 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else if ("02".equals(spdel)) {
				rs0064 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else if ("03".equals(spdel)) {
				rs0065 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else if ("04".equals(spdel)) {
				rs0066 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else if ("05".equals(spdel)) {
				rs0067 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else if ("06".equals(spdel)) {
				rs0068 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else
				;
		}
		rs0069 = this.getRs0069(print);
		// rs0070="";// 预留
		rs0071 = this.getRs0071(print);
		;
		// rs0072 --rs0073
		if (print != null) {// deliveryPrint - ds_Payment没有入库 待改动
			String dspayment = print.getDs_payment();
			if ("01".equals(dspayment)) {
				rs0072 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else if ("02".equals(dspayment)) {
				rs0073 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			} else
				;
		}

		// 转换

		List<InvoiceModeFieldVO> list = getModeList(modelId);// 模板10008展示有问题
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

				if (invoiceModeFieldVO.getField_cd().contains("0018")) {
					fieldModelVO.setValue(rs0018);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0019")) {
					fieldModelVO.setValue(rs0019);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0020")) {
					fieldModelVO.setValue(rs0020);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0021")) {
					fieldModelVO.setValue(rs0021);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0022")) {
					fieldModelVO.setValue(rs0022);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0023")) {
					fieldModelVO.setValue(rs0023);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0024")) {
					fieldModelVO.setValue(rs0024);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0025")) {
					fieldModelVO.setValue(rs0025);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0026")) {
					fieldModelVO.setValue(rs0026);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0027")) {
					fieldModelVO.setValue(rs0027);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0028")) {
					fieldModelVO.setValue(rs0028);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0029")) {
					fieldModelVO.setValue(rs0029);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0030")) {
					fieldModelVO.setValue(rs0030);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0031")) {
					fieldModelVO.setValue(rs0031);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0032")) {
					fieldModelVO.setValue(rs0032);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0033")) {
					fieldModelVO.setValue(rs0033);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0034")) {
					fieldModelVO.setValue(rs0034);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0035")) {
					if (invoiceModeFieldVO.getModel_cd().equals(EcsOrderConsts.INVOICE_PRINT_MODE_CD_SZEMS)
							&& print.getWeight() != null)
						fieldModelVO.setValue("计费重量：" + rs0035 + "克");
					else
						fieldModelVO.setValue(rs0035);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0036")) {
					fieldModelVO.setValue(rs0036);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0037")) {
					fieldModelVO.setValue(rs0037);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0038")) {
					fieldModelVO.setValue(rs0038);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0039")) {
					fieldModelVO.setValue(rs0039);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0040")) {
					fieldModelVO.setValue(rs0040);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0041")) {
					fieldModelVO.setValue(rs0041);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0042")) {
					fieldModelVO.setValue(rs0042);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0043")) {
					fieldModelVO.setValue(rs0043);
					if (invoiceModeFieldVO.getModel_cd().equals(EcsOrderConsts.INVOICE_PRINT_MODE_CD_SZEMS)
							&& print.getCod_price() != null)
						fieldModelVO.setValue("代收款：" + rs0043 + "元");
					else
						fieldModelVO.setValue(rs0043);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0044")) {
					fieldModelVO.setValue(rs0044);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0045")) {
					fieldModelVO.setValue(rs0045);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0046")) {
					fieldModelVO.setValue(rs0046);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0047")) {
					fieldModelVO.setValue(rs0047);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0048")) {
					fieldModelVO.setValue(rs0048);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0049")) {
					fieldModelVO.setValue(rs0049);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0050")) {
					fieldModelVO.setValue(rs0050);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0051")) {
					fieldModelVO.setValue(rs0051);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0052")) {
					if (rs0052 != null && rs0052 != "") {
						if (invoiceModeFieldVO.getModel_cd().equals(EcsOrderConsts.INVOICE_PRINT_MODE_CD_SZEMS)) {
							rs0052 = MoneyUtil.toRmb(rs0052, 40) + "  " + rs0052;
						} else {
							rs0052 = MoneyUtil.toRmb(rs0052, 20);
						}
						fieldModelVO.setValue(rs0052);
					}
				}
				if (invoiceModeFieldVO.getField_cd().contains("0053")) {
					rs0053 = this.getRs0053ByModelId(rs0053, invoiceModeFieldVO.getModel_cd());
					fieldModelVO.setValue(rs0053);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0054")) {
					fieldModelVO.setValue(rs0054);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0055")) {
					fieldModelVO.setValue(rs0055);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0056")) {
					fieldModelVO.setValue(rs0056);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0057")) {
					fieldModelVO.setValue(rs0057);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0058")) {
					fieldModelVO.setValue(rs0058);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0059")) {
					fieldModelVO.setValue(rs0059);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0060")) {
					fieldModelVO.setValue(rs0060);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0061")) {
					fieldModelVO.setValue(rs0061);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0062")) {
					fieldModelVO.setValue(rs0062);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0063")) {
					fieldModelVO.setValue(rs0063);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0064")) {
					fieldModelVO.setValue(rs0064);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0065")) {
					fieldModelVO.setValue(rs0065);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0066")) {
					fieldModelVO.setValue(rs0066);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0067")) {
					fieldModelVO.setValue(rs0067);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0068")) {
					fieldModelVO.setValue(rs0068);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0069")) {
					fieldModelVO.setValue(rs0069);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0070")) {
					fieldModelVO.setValue(rs0070);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0071")) {
					fieldModelVO.setValue(rs0071);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0072")) {
					fieldModelVO.setValue(rs0072);
				}
				if (invoiceModeFieldVO.getField_cd().contains("0073")) {
					fieldModelVO.setValue(rs0073);
				}
				if (fieldModelVO.getValue() == null) {
					fieldModelVO.setValue("");
				}
				if (invoiceModeFieldVO.getField_cd().contains("0089")) {
					fieldModelVO.setValue(rs0089);
				}
				fieldModelVOList.add(fieldModelVO);
			}
			// 将值放入
			invoiceDto.setInvoiceModelVO(invoiceModelVO);
			invoiceDto.setFieldModelVoList(fieldModelVOList);
			// 将一个模板需要的信息加入到list
			invoiceDtoList.add(invoiceDto);
			htmlCode = iOrderInvoiceManager.invoicePrint(invoiceDtoList);
			htmlCode = this.getHead(cxt) + htmlCode + button
					+ this.getForm(delivery_id, reissueIds, postType, order_is_his) + tail;
		}
		return htmlCode;

	}

	/**
	 * 根据mode_cd查询域集合(表es_invoice_model_field)
	 * 
	 * @param mode_cd
	 * @return
	 */
	public List<InvoiceModeFieldVO> getModeList(String mode_cd) {
		List<InvoiceModeFieldVO> list = null;
		StringBuilder sql = new StringBuilder("SELECT T.* FROM ES_INVOICE_MODEL_FIELD T");
		sql.append(" WHERE T.MODEL_CD='" + mode_cd + "'");
		list = baseDaoSupport.queryForList(sql.toString(), InvoiceModeFieldVO.class);
		return list;
	}

	/**
	 * 根据物流公司ID获取物流公司编码
	 * 
	 * @param company_code
	 * @return
	 */
	public String getComCodeByComId(String company_id) {
		String company_code = "";
		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy proxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> logiCompanies = proxy.getLogiCompanyList();
		company_code = company_id;
		for (Map company : logiCompanies) {
			if (company_id.equals(Const.getStrValue(company, "id"))) {
				company_code = Const.getStrValue(company, "company_code");
			}
		}
		return company_code;
	}

	/**
	 * 收件人地址
	 * 
	 * @return
	 */
	public String getRs0022(OrderDeliveryBusiRequest delivery, String postCompCode) {
		String result = "";
		String sAddr = "";
		if (delivery != null && delivery.getShip_addr() != null) {
			sAddr = delivery.getShip_addr();
		}
		if (sAddr != null && !sAddr.equals("")) {
			int strLength = sAddr.length();
			int len = postCompCode.contains(EcsOrderConsts.POST_COMPANY_CODE_ZJS) ? 26 : substr16;
			for (int j = 0; j < strLength; j += len) {
				if (strLength - j > len) {
					result += sAddr.substring(j, (j + len)) + "<br/>";
				} else {
					result += sAddr.substring(j) + "<br/>";
				}
			}
		}
		return result;
	}

	/**
	 * 收件人地址带参
	 * 
	 * @return
	 */
	public String getRs0022(OrderDeliveryBusiRequest delivery, String postCompCode, int substr) {
		String result = "";
		String sAddr = "";
		if (delivery != null && delivery.getShip_addr() != null) {
			sAddr = delivery.getShip_addr();
		}
		if (sAddr != null && !sAddr.equals("")) {
			int strLength = sAddr.length();
			int len = postCompCode.contains(EcsOrderConsts.POST_COMPANY_CODE_ZJS) ? 26 : substr;
			for (int j = 0; j < strLength; j += len) {
				if (strLength - j > len) {
					result += sAddr.substring(j, (j + len)) + "<br/>";
				} else {
					result += sAddr.substring(j) + "<br/>";
				}
			}
		}
		return result;
	}

	/**
	 * 发件人地址
	 * 
	 * @return
	 */
	public String getRs0025(DeliveryPrints print) {
		String result = "";
		String fAddr = "";
		if (print != null && print.getPost_address() != null) {
			fAddr = print.getPost_address();
		}
		if (fAddr != null && !fAddr.equals("")) {
			int strLength = fAddr.length();
			for (int j = 0; j < strLength; j += substr16) {
				if (strLength - j > substr16) {
					result += fAddr.substring(j, (j + substr16)) + "<br/>";
				} else {
					result += fAddr.substring(j) + "<br/>";
				}
			}
		}
		return result;
	}

	/**
	 * 发件人地址，带参
	 * 
	 * @return
	 */
	public String getRs0025(DeliveryPrints print, int substr) {
		String result = "";
		String fAddr = "";
		if (print != null && print.getPost_address() != null) {
			fAddr = print.getPost_address();
		}
		if (fAddr != null && !fAddr.equals("")) {
			int strLength = fAddr.length();
			for (int j = 0; j < strLength; j += substr) {
				if (strLength - j > substr) {
					result += fAddr.substring(j, (j + substr)) + "<br/>";
				} else {
					result += fAddr.substring(j) + "<br/>";
				}
			}
		}
		return result;
	}

	/**
	 * 发件人姓名
	 * 
	 * @return
	 */
	public String getRs0026(DeliveryPrints print) {
		String result = "";
		if (print != null && print.getPost_link_man() != null) {
			result = print.getPost_link_man();
		}
		return result;
	}

	/**
	 * 发件人联系电话
	 * 
	 * @return
	 */
	public String getRs0027(DeliveryPrints print) {
		String result = "";
		String fTel = "";
		if (print != null && print.getPost_tel() != null) {
			fTel = print.getPost_tel();
		}
		if (fTel != null && !fTel.equals("")) {
			int strLength = fTel.length();
			for (int j = 0; j < strLength; j += substr12) {
				if (strLength - j > substr12) {
					String aa = fTel.substring(j, (j + 1));
					if (aa.equals("") || aa.equals(",") || aa.equals("，"))
						result += fTel.substring((j + 1), (j + 1 + substr12)) + "<br/>";
					else
						result += fTel.substring(j, (j + substr12)) + "<br/>";
				} else {
					result += fTel.substring(j) + "<br/>";
				}
			}
		}
		return result;
	}

	/**
	 * 声明物品
	 * 
	 * @return
	 */
	public String getRs0028(String order_id) {
		String result = "";
		String phoneNum = null;
		if (isHis) {
			phoneNum = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.PHONE_NUM);
		} else {
			phoneNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
		}

		if (phoneNum != null) {
			result = "号码：" + phoneNum + "";
		}
		return result;
	}

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getRs0029(OrderTreeBusiRequest orderTree) {
		String result = "";
		OrderBusiRequest orderBusiRequest = orderTree.getOrderBusiRequest();
		if (orderBusiRequest.getRemark() != null) {
			result = orderBusiRequest.getRemark();
		}
		return result;
	}

	/**
	 * 个性化提示区，例如COD,POD
	 * 
	 * @return
	 */
	public String getRs0070(DeliveryPrints print) {
		String result = "";
		if (StringUtil.equals(print.getIs_cod(), EcsOrderConsts.IS_COD_YES)) {
			result = "COD";// 目前只有COD
		}
		return result;
	}

	/**
	 * 收件人姓名 --完成
	 * 
	 * @return
	 */
	public String getRs0023(OrderDeliveryBusiRequest delivery) {
		String ship_name = "";
		if (delivery != null && delivery.getShip_name() != null) {
			ship_name = delivery.getShip_name();
		}
		return ship_name;
	}

	/* 获取指定打印机 */
	public String getPrinter(String pkey) {
		return CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_PRINTER", pkey);
	}

	/**
	 * 总数 --完成
	 * 
	 * @return
	 */
	public String getRs0018(OrderDeliveryBusiRequest delivery) {
		String goods_num = "";
		if (delivery != null) {
			try {
				if (isHis) {
					goods_num = CommonDataFactory.getInstance().getAttrFieldValueHis(delivery.getOrder_id(),
							AttrConsts.GOODS_NUM);
				} else {
					goods_num = CommonDataFactory.getInstance().getAttrFieldValue(delivery.getOrder_id(),
							AttrConsts.GOODS_NUM);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return goods_num;
	}

	/**
	 * 收件人联系电话 --完成
	 * 
	 * @return
	 */
	public String getRs0024(OrderDeliveryBusiRequest delivery) {
		String sTel = "";
		String rs0024 = "";
		String ship_mobile = delivery.getShip_mobile() != null ? delivery.getShip_mobile() : "";
		String ship_tel = delivery.getShip_tel() != null ? delivery.getShip_tel() : "";
		String ship_company_code = null;
		if (isHis) {
			ship_company_code = CommonDataFactory.getInstance().getOrderTreeHis(delivery.getOrder_id())
					.getOrderExtBusiRequest().getQuick_ship_company_code();
		} else {
			ship_company_code = CommonDataFactory.getInstance().getOrderTree(delivery.getOrder_id())
					.getOrderExtBusiRequest().getQuick_ship_company_code();

		}
		if (ship_company_code != null && ship_company_code.contains("ZJS")) {
			rs0024 = ship_tel.equals(ship_mobile) ? ship_tel : ship_tel + "/" + ship_mobile;
		} else { // if(!ship_tel.endsWith("")&&!ship_mobile.equals("")){
			sTel += ship_tel.equals(ship_mobile) ? ship_tel : ship_tel + " " + ship_mobile;
			if (!("").equals(sTel) && sTel != null) {
				int strLength = sTel.length();
				for (int j = 0; j < strLength; j += substr12) {
					if (strLength - j > substr12) {
						String aa = sTel.substring(j, (j + 1));
						if (aa.equals(" ") || aa.equals(",") || aa.equals("，"))
							rs0024 += sTel.substring((j + 1), (j + 1 + substr12)) + "<br/>";
						else
							rs0024 += sTel.substring(j, (j + substr12)) + "<br/>";
					} else {
						rs0024 += sTel.substring(j) + "<br/>";
					}
				}
			}
			// }
		}

		return rs0024;
	}

	public String getRs0024SF(OrderDeliveryBusiRequest delivery) {
		String ship_mobile = delivery.getShip_mobile() != null ? delivery.getShip_mobile() : "";
		String ship_tel = delivery.getShip_tel() != null ? delivery.getShip_tel() : "";
		if (StrTools.isNotEmpty(ship_mobile)) {
			if (ship_mobile.equals(ship_tel)) {
				return ship_mobile;
			}
		}
		return ship_mobile + " " + ship_tel;
	}

	/**
	 * 收件人联系电话 --带参
	 * 
	 * @return
	 */
	public String getRs0024(OrderDeliveryBusiRequest delivery, int substr) {
		String sTel = "";
		String rs0024 = "";
		String ship_mobile = delivery.getShip_mobile() != null ? delivery.getShip_mobile() : "";
		String ship_tel = delivery.getShip_tel() != null ? delivery.getShip_tel() : "";
		String ship_company_code = null;
		if (isHis) {
			ship_company_code = CommonDataFactory.getInstance().getOrderTreeHis(delivery.getOrder_id())
					.getOrderExtBusiRequest().getQuick_ship_company_code();
		} else {
			ship_company_code = CommonDataFactory.getInstance().getOrderTree(delivery.getOrder_id())
					.getOrderExtBusiRequest().getQuick_ship_company_code();

		}
		if (ship_company_code != null && ship_company_code.contains("ZJS")) {
			rs0024 = ship_tel.equals(ship_mobile) ? ship_tel : ship_tel + "/" + ship_mobile;
		} else { // if(!ship_tel.endsWith("")&&!ship_mobile.equals("")){
			sTel += ship_tel.equals(ship_mobile) ? ship_tel : ship_tel + " " + ship_mobile;
			if (!("").equals(sTel) && sTel != null) {
				int strLength = sTel.length();
				for (int j = 0; j < strLength; j += substr) {
					if (strLength - j > substr) {
						String aa = sTel.substring(j, (j + 1));
						if (aa.equals(" ") || aa.equals(",") || aa.equals("，"))
							rs0024 += sTel.substring((j + 1), (j + 1 + substr)) + "<br/>";
						else
							rs0024 += sTel.substring(j, (j + substr)) + "<br/>";
					} else {
						rs0024 += sTel.substring(j) + "<br/>";
					}
				}
			}
			// }
		}

		return rs0024;
	}

	/**
	 * 投递要求
	 * 
	 * @return
	 */
	public String getRs0050(DeliveryPrints print, String postCompCode, String shipName) {
		String result = "";
		if (print != null) {
			String deliverInfo = print.getDeliver_info() == null ? "" : print.getDeliver_info();
			if (!deliverInfo.equals("")) {
				if (deliverInfo.contains("$姓名$")) {
					deliverInfo = deliverInfo.replace("$姓名$", shipName);
				}
				result = deliverInfo;
			}
		}
		result = "<span style='font-size: 12px'>" + result + "</span>";
		return result;
	}

	/**
	 * 邮费
	 * 
	 * @return
	 */
	public String getRs0051(DeliveryPrints print) {
		String result = "";
		if (print != null) {
			result = print.getCarry_fee();
		}
		return result;
	}

	/**
	 * 大写代收款
	 * 
	 * @return
	 */
	public String getRs0052(DeliveryPrints print) {
		String result = "";
		if (print != null) {
			if (EcsOrderConsts.IS_COD_YES.equals(print.getIs_cod())) {
				result = print.getCod_price();
			}
		}
		return result;
	}

	/**
	 * 发件单位
	 * 
	 * @return
	 */
	public String getRs0053(DeliveryPrints print) {
		String result = "";
		if (print != null && print.getPost_comp() != null) {
			result = print.getPost_comp();
		} else {
			List<Map<String, String>> staticList = CommonDataFactory.getInstance()
					.listDcPublicData(StypeConsts.POST_COMP);
			for (Map<String, String> map : staticList) {
				String pkey = Const.getStrValue(map, "pkey");
				String pname = Const.getStrValue(map, "pname");
				if (EcsOrderConsts.POST_COMP_ZGLT.equals(pkey)) {
					result = pname;
					break;
				}

			}
		}
		return result;
	}

	/**
	 * 发件单位-2
	 * 
	 * @return
	 */
	public String getRs0053ByModelId(String defaultVal, String modelId) {
		String result = defaultVal;
		List<Map<String, String>> staticList = CommonDataFactory.getInstance().listDcPublicData(StypeConsts.POST_COMP);
		for (Map<String, String> map : staticList) {
			String pkey = Const.getStrValue(map, "pkey");
			String pname = Const.getStrValue(map, "pname");
			if (EcsOrderConsts.POST_COMP_ZGLTSZ.equals(pkey)) {
				if (modelId.equals(EcsOrderConsts.INVOICE_PRINT_MODE_CD_SZEMS)) {
					result = pname;
					break;
				}
			}
			if (EcsOrderConsts.POST_COMP_SZLT.equals(pkey)) {
				if (modelId.equals(EcsOrderConsts.INVOICE_PRINT_MODE_CD_SZYX)) {
					result = pname;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 自定义声明物品
	 * 
	 * @return
	 */
	public String getRs0054(DeliveryPrints print) {
		String result = "";
		String postItems = "";
		if (print != null && print.getPost_items() != null) {
			postItems = print.getPost_items();
		}
		if (!postItems.equals("")) {
			int strLength = postItems.length();
			for (int j = 0; j < strLength; j += substr16) {
				if (strLength - j > substr16) {
					result += postItems.substring(j, (j + substr16)) + "<br/>";
				} else {
					result += postItems.substring(j) + "<br/>";
				}
			}
		}
		return result;
	}

	/**
	 * 是否签回单
	 * 
	 * @return
	 */
	public String getRs0056(DeliveryPrints print) {
		String result = "";
		if (print != null && EcsOrderConsts.IS_SIGNBACK.equals(print.getIs_signback())) {
			result = EcsOrderConsts.POST_PRINT_SIGN_GOU;
		}
		return result;
	}

	/**
	 * 发件区号
	 * 
	 * @return
	 */
	public String getRs0057(DeliveryPrints print) {
		String result = "";
		if (print != null && print.getPost_code() != null) {
			result = print.getPost_code();
		}
		return result;
	}

	/**
	 * 收款账号
	 * 
	 * @return
	 */
	public String getRs0058(DeliveryPrints print) {
		String result = "";
		if (print != null && print.getReceiv_account() != null) {
			result = print.getReceiv_account();
		}
		return result;
	}

	/**
	 * 收件地址备注
	 * 
	 * @return
	 */
	public String getRs0059(DeliveryPrints print) {
		String result = "";
		if (print != null && print.getAdd_remarks() != null) {
			result = print.getAdd_remarks();
		}
		return result;
	}

	/**
	 * 收件单位备注
	 * 
	 * @return
	 */
	public String getRs0060(DeliveryPrints print) {
		String result = "";
		if (print != null && print.getCmp_remarks() != null) {
			result = print.getCmp_remarks();
		}
		return result;
	}

	/**
	 * 保费
	 * 
	 * @return
	 */
	public String getRs0061(DeliveryPrints print, OrderDeliveryBusiRequest delivery) {
		String result = "";
		if (print != null && EcsOrderConsts.IS_INSUR_YES.equals(print.getIs_insur()) && print.getInsur_value() != null
				&& !print.getInsur_value().equals("")) {
			Double dresult = delivery.getProtect_price() == null ? 0.00
					: (delivery.getProtect_price() * Double.parseDouble(print.getInsur_value()));
			java.text.DecimalFormat df = new java.text.DecimalFormat("#.#");
			result = df.format(dresult).toString();
		}
		return result;
	}

	/**
	 * 件数
	 * 
	 * @return
	 */
	public String getRs0062(DeliveryPrints print) {
		String result = "";
		if (print != null && print.getPost_num() != null) {
			result = print.getPost_num();
		}
		return result;
	}

	// 63-68在主方法取值
	/**
	 * 打印时间
	 * 
	 * @return
	 */
	public String getRs0069(DeliveryPrints print) {
		String result = "";
		SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd");
		result = fdate.format(new Date());
		return result;
	}

	/**
	 * 是否报价(文字)
	 * 
	 * @return
	 */
	public String getRs0071(DeliveryPrints print) {
		String result = "";
		if (print != null && print.getIs_insur() != null) {
			List<Map<String, String>> staticList = CommonDataFactory.getInstance()
					.listDcPublicData(StypeConsts.IS_INSUR);
			for (Map<String, String> map : staticList) {
				String pkey = Const.getStrValue(map, "pkey");
				String pname = Const.getStrValue(map, "pname");
				if (print.getIs_insur().equals(pkey)) {
					result = pname;
				}

			}
		}
		return result;
	}

	/**
	 * 发件人签名 就是发件人姓名
	 * 
	 * @return
	 */
	public String getRs0033(DeliveryPrints print) {
		String result = "";
		if (print != null) {
			result = print.getPost_link_man();
		}
		return result;

	}

	//////////////////////////////// 你在下面写，我在上面写=============
	/**
	 * 客户编码
	 * 
	 * @return
	 */
	public String getRs0034(DeliveryPrints print) {
		String customer_code = "";
		if (print != null) {
			customer_code = print.getCustomer_account();
		}
		return customer_code;

	}

	/**
	 * 计费重量
	 * 
	 * @return
	 */
	public String getRs0035(DeliveryPrints print) {
		String weight = "";
		if (print != null) {
			weight = print.getWeight();
		}
		return weight;

	}

	/**
	 * 原寄地
	 * 
	 * @return
	 */
	public String getRs0036(DeliveryPrints print) {
		String origin = "";
		if (print != null) {
			origin = print.getOrigin();
		}
		return origin;

	}

	/**
	 * 月结账号
	 * 
	 * @return
	 */
	public String getRs0037(DeliveryPrints print) {
		String monthly_payment = "";
		if (print != null) {
			monthly_payment = print.getMonthly_payment();
		}
		return monthly_payment;

	}

	/**
	 * 物流公司收件人
	 * 
	 * @return
	 */
	public String getRs0038(DeliveryPrints print) {
		String pickup_user = "";
		if (print != null) {
			pickup_user = print.getPickup_user();
		}
		return pickup_user;

	}

	/**
	 * 不保价
	 * 
	 * @return
	 */
	public String getRs0039(DeliveryPrints print) {
		String rs0039 = "";
		if (print != null) {
			if ((EcsOrderConsts.IS_INSUR_YES).equals(print.getIs_insur())) {
			} else {
				rs0039 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			}
		}
		return rs0039;

	}

	/**
	 * 保价
	 * 
	 * @return
	 */
	public String getRs0040(DeliveryPrints print) {
		String rs0040 = "";
		if (print != null) {
			if ((EcsOrderConsts.IS_INSUR_YES).equals(print.getIs_insur())) {
				rs0040 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
			}
		}
		return rs0040;

	}

	/**
	 * 保价金额
	 * 
	 * @return
	 */
	public String getRs0041(DeliveryPrints print) {
		String rs0041 = "";
		if (print != null) {
			if ((EcsOrderConsts.IS_INSUR_YES).equals(print.getIs_insur())) {
				rs0041 = print.getInsur_value();
			}
		}
		return rs0041;
	}

	/**
	 * 是否代收款
	 * 
	 * @return
	 */
	public String getRs0042(DeliveryPrints print) {
		String rs0042 = "";
		if (print != null) {
			if ((EcsOrderConsts.IS_COD_YES).equals(print.getIs_cod())) {
				rs0042 = EcsOrderConsts.POST_PRINT_SIGN_GOU;

			}
		}
		return rs0042;

	}

	/**
	 * 代收货款
	 * 
	 * @return
	 */
	public String getRs0043(DeliveryPrints print) {
		String rs0043 = "";
		if (print != null) {
			if ((EcsOrderConsts.IS_COD_YES).equals(print.getIs_cod())) {
				String codPrice = print.getCod_price() == null ? "" : print.getCod_price();
				if (!codPrice.equals("")) {
					int strLength = codPrice.length();
					for (int j = 0; j < strLength; j += substr8) {
						if (strLength - j > substr8) {
							rs0043 += codPrice.substring(j, (j + substr8)) + "<br/>";
						} else {
							rs0043 += codPrice.substring(j) + "<br/>";
						}
					}
				}
			} else {
				print.setReceiv_account("");
			}
		}
		return rs0043;

	}

	/**
	 * 详情单回执联
	 * 
	 * @return
	 */
	public String getRs0044(DeliveryPrints print) {
		String rs0044 = "";
		if (print != null && (EcsOrderConsts.RECEIPTDETAIL).equals(print.getReceipt_detail())) {
			rs0044 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
		}
		return rs0044;

	}

	/**
	 * 代收指定回执
	 * 
	 * @return
	 */
	public String getRs0045(DeliveryPrints print) {
		String rs0045 = "";
		if (print != null && (EcsOrderConsts.RECEIPTDESIGNATED).equals(print.getReceipt_designated_())) {
			rs0045 = EcsOrderConsts.POST_PRINT_SIGN_GOU;
		}
		return rs0045;

	}

	/**
	 * 寄付方式
	 * 
	 * @param print
	 * @return
	 */
	public String getRs0083(DeliveryPrints print) {
		String rs0083 = CommonDataFactory.getInstance().getDcPublicDataByPkey(StypeConsts.EXPRESS_TYPE,
				print.getExpress_type());
		return rs0083;
	}

	/**
	 * 寄付方式
	 * 
	 * @param print
	 * @return
	 */
	public String getRs0085(DeliveryPrints print, OrderDeliveryBusiRequest delivery) {
		String rs0085 = "";
		if (print != null && delivery != null) {
			rs0085 = rs0085 + "<table cellspacing='0'>";
			String postmode = print.getPostage_mode();
			if (EcsOrderConsts.IS_INSUR_YES.equals(print.getIs_insur())) {
				rs0085 = rs0085 + "<tr><td>" + "保价声明价值：" + emptyString(print.getInsur_value()) + "元          保费：   元"
						+ "   包装费：    元</td></tr>";
			}
			if (EcsOrderConsts.POSTAGE_MODE_JZZF.equals(postmode)) {
				rs0085 = rs0085 + "<tr><td>" + "付款方式：寄付月结          月结账号：" + emptyString(print.getMonthly_payment())
						+ "</td></tr>";
			}
			rs0085 = rs0085 + "<tr><td>" + "实际重量：  kg    计费重量：1kg</td></tr>";
			if (StringUtils.isNotEmpty(print.getReceipt_no())) {
				rs0085 = rs0085 + "<tr><td>" + "回单单号：" + print.getReceipt_no() + "</td></tr>";
			}
			rs0085 = rs0085 + "<tr><td width='250'>" + print.getPost_items() + "</td></tr>";
//			if(EcsOrderConsts.IS_COD_YES.equals(print.getIs_cod())){
//				rs0085 = rs0085 +"<p>"+ "代收货款金额："+print.getCod_price()+"元"+"</p>";
//			}
			rs0085 = rs0085 + "</table>";
		}
		return rs0085;
	}

	/**
	 * 目的地
	 * 
	 * @return
	 */
	public String getRs0098(DeliveryPrints print) {
		String destcode = "";
		if (print != null) {
			destcode = print.getDestcode();
		}
		return destcode;

	}

	/**
	 * 自有物流单据表格
	 * 
	 * @return
	 */
	public String getRs0031(OrderDeliveryBusiRequest delivery, String wlnum, String ship_company_code, String cxt) {
		String rs0031 = "";
		// String
		// wlnum=CommonDataFactory.getInstance().getAttrFieldValue(delivery.getOrder_id(),
		// AttrConsts.LOGI_NO);
		// String
		// ship_company_code=CommonDataFactory.getInstance().getOrderTree(delivery.getOrder_id()).getOrderExtBusiRequest().getQuick_ship_company_code();
		String ship_addr = null;
		if (isHis) {
			ship_addr = CommonDataFactory.getInstance().getAttrFieldValueHis(delivery.getOrder_id(),
					AttrConsts.SHIP_ADDR);
		} else {
			ship_addr = CommonDataFactory.getInstance().getAttrFieldValue(delivery.getOrder_id(), AttrConsts.SHIP_ADDR);
		}
		if (ship_company_code != null && ship_company_code.contains("ZY")) {
			String projectRealPath = ServletActionContext.getServletContext().getRealPath("/");
			String picFormat = "png";
			String path = projectRealPath + "/shop/admin/printTempImg/" + wlnum;
			String imgPath = cxt + "/shop/admin/printTempImg/" + wlnum + ".png";
			Hashtable hints = new Hashtable();
			try {
				BitMatrix bitMatrix;

				bitMatrix = new MultiFormatWriter().encode(wlnum, BarcodeFormat.CODE_128, 300, 200, hints);

				File file = new File(path + "." + picFormat);

				BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

				int width = bitMatrix.getWidth();
				int height = bitMatrix.getHeight();

				int infoWidth = height / 4;

				Graphics2D g2 = (Graphics2D) image.getGraphics();
				g2.setBackground(Color.WHITE);

				Font f = new Font(delivery.getOrder_id(), Font.BOLD, 20);
				FontMetrics fm = g2.getFontMetrics(f);
				int strLen = fm.stringWidth(delivery.getOrder_id());
				int fontHeight = fm.getHeight();

				g2.setFont(f);

				g2.clearRect(0, height - infoWidth - fontHeight, width, infoWidth + fontHeight);
				g2.setPaint(Color.BLACK);

				double x1 = width / 2 - strLen / 2;
				double y2 = height - (fontHeight + infoWidth) / 2;

				g2.drawString(delivery.getOrder_id(), (int) x1, (int) y2);

				ImageIO.write(image, picFormat, file);

				String Remark = delivery.getRemark() != null ? delivery.getRemark() : "";

				String tableStr = "";// 打印内容串

				// 表头
				tableStr += "<table style='border-collapse:collapse; width: 96%; margin-top:10px; margin-left:5px; border:1px solid #c3c3c3;'>"
						+ "<tr>"
						+ "<td colspan='5' style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>自有物流团队配送清单</td>"
						+ "</tr>" + "<tr>"
						+ "<td colspan='2' style='width: 50%;border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>订单号："
						+ delivery.getOrder_id() + "</td>"
						+ "<td colspan='2' style='width: 50%;border-bottom:1px solid #ccc; padding:5px; text-align:left;border-left:1px solid #ccc;'><image style='width:300px;height:60px;' src='"
						+ imgPath + "'/></td>" + "</tr>" + "<tr>"
						+ "<td style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>收货公司</td>"
						+ "<td colspan='3' style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'></td>"
						+ "</tr>" + "<tr>"
						+ "<td style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>收货地址</td>"
						+ "<td colspan='3' style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>"
						+ ship_addr + "</td>" + "</tr>" + "<tr>"
						+ "<td style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>收货人</td>"
						+ "<td style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>"
						+ delivery.getShip_name() + "</td>"
						+ "<td style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>电话</td>"
						+ "<td style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>"
						+ delivery.getShip_tel() + "</td>" + "</tr>" + "<tr>"
						+ "<td style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>收货时间</td>"
						+ "<td colspan='3' style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'></td>"
						+ "</tr>" + "<tr>"
						+ "<td style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>备注</td>"
						+ "<td colspan='3' style='border-bottom:1px solid #ccc; padding:5px; text-align:center;border-left:1px solid #ccc;'>"
						+ Remark + "</td>" + "</tr></table>";
				rs0031 = tableStr;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs0031;

	}

	/**
	 * 托寄物，带参
	 * 
	 * @return
	 */
	public String getRs0054(DeliveryPrints print, int substr) {
		String result = "";
		String item = "";
		if (print != null && print.getPost_items() != null) {
			item = print.getPost_items();
		}
		if (item != null && !item.equals("")) {
			int strLength = item.length();
			for (int j = 0; j < strLength; j += substr) {
				if (strLength - j > substr) {
					result += item.substring(j, (j + substr)) + "<br/>";
				} else {
					result += item.substring(j) + "<br/>";
				}
			}
		}
		return result;
	}

	/**
	 * 礼品卡 --目前没有用到的
	 * 
	 * @param delivery
	 * @param deliveryItemslist
	 * @return
	 */
	public String getRs0055(OrderDeliveryBusiRequest delivery, List<OrderDeliveryItemBusiRequest> deliveryItemslist) {
		String cardTpye = "未知";
		String cardValue = "未知";
		String cardCnt = "未知";

		String rs0055tmp = "";
		String rs0055 = "";

		if (delivery.getDelivery_type().equals(EcsOrderConsts.DELIVERY_TYPE_NORMAL)
				|| delivery.getDelivery_type().equals(EcsOrderConsts.DELIVERY_TYPE_READD)) {
			List<AttrGiftInfoBusiRequest> attrGiftInfo = new ArrayList<AttrGiftInfoBusiRequest>();
			if (isHis) {
				attrGiftInfo = CommonDataFactory.getInstance().getOrderTreeHis(delivery.getOrder_id())
						.getGiftInfoBusiRequests();
			} else {
				attrGiftInfo = CommonDataFactory.getInstance().getOrderTree(delivery.getOrder_id())
						.getGiftInfoBusiRequests();
			}
			for (AttrGiftInfoBusiRequest attrGiftInfoBusiRequest : attrGiftInfo) {
				cardValue = attrGiftInfoBusiRequest.getGift_value() == null ? ""
						: attrGiftInfoBusiRequest.getGift_value() + "元";
				cardCnt = "×" + attrGiftInfoBusiRequest.getSku_num();

				if (attrGiftInfoBusiRequest.getGoods_name() != null
						&& !"未知".equals(attrGiftInfoBusiRequest.getGoods_name())) {
					rs0055tmp += attrGiftInfoBusiRequest.getGoods_name() + "" + cardCnt + "";
				}
			}
			/*
			 * for(OrderDeliveryItemBusiRequest
			 * orderDeliveryItemBusiRequest:deliveryItemslist) { String num="未知";
			 * if(orderDeliveryItemBusiRequest.getBuy_num()!=null){
			 * num=Integer.toString(orderDeliveryItemBusiRequest.getBuy_num()); }
			 * cardCnt="×"+num; rs0055tmp += orderDeliveryItemBusiRequest.getName()+
			 * ""+cardCnt+"; ";
			 * 
			 * }
			 */
			if (rs0055tmp != "") {
				int strLength = rs0055tmp.length();
				for (int j = 0; j < strLength; j += substr20) {
					if (strLength - j > substr20) {
						rs0055 += rs0055tmp.substring(j, (j + substr20)) + "<br/>";
					} else {
						rs0055 += rs0055tmp.substring(j) + "<br/>";
					}
				}
			}
		}

		else {
			for (OrderDeliveryItemBusiRequest orderDeliveryItemBusiRequest : deliveryItemslist) {
				String num = "未知";
				if (orderDeliveryItemBusiRequest.getBuy_num() != null) {
					num = Integer.toString(orderDeliveryItemBusiRequest.getBuy_num());
				}
				cardCnt = "×" + num;
				rs0055tmp += orderDeliveryItemBusiRequest.getName() + "" + cardCnt + "; ";

			}
			if (rs0055tmp != "") {
				int strLength = rs0055tmp.length();
				for (int j = 0; j < strLength; j += substr20) {
					if (strLength - j > substr20) {
						rs0055 += rs0055tmp.substring(j, (j + substr20)) + "<br/>";
					} else {
						rs0055 += rs0055tmp.substring(j) + "<br/>";
					}
				}
			}

		}

		return rs0055;

	}

	/**
	 * 根据物流公司id匹配到打印模板ID
	 * 
	 * @param shipping_company 物流公司ID
	 * @param order_id
	 * @return
	 */
	public String getModelId(String shipping_company, String order_id) {

		LogisticsFact logisticsFact = new LogisticsFact();
		logisticsFact.setCompany_code(shipping_company);
		logisticsFact.setObj_id(order_id);
		logisticsFact.setOrder_id(order_id);
		PlanRuleTreeExeReq planReq = new PlanRuleTreeExeReq();
		planReq.setFact(logisticsFact);
		planReq.setPlan_id(EcsOrderConsts.ORDER_LOGISTICS_FLOW);
		planReq.setDeleteLogs(true);
		PlanRuleTreeExeResp presp = CommonDataFactory.getInstance().exePlanRuleTree(planReq);
		LogisticsFact logistics = (LogisticsFact) presp.getFact();
		String model_id = logistics.getModel_id();

		return model_id;
	}

	/**
	 * 更新赠品的补寄状态 正常、重发不做任何操作、补寄则更新补寄的物
	 */
	public void updateItemsPrintStatus(String post_type, String delivery_id, String itemIds, boolean isHis) {
		String sql = "";
		if (EcsOrderConsts.LOGIS_NORMAL.equals(post_type)) {// 正常
			// 根据delivery_id更新所有items
			// sql=SF.ecsordSql("UPDATE_ES_DELIVERY_ITEM_BY_DEID");
			// this.baseDaoSupport.execute(sql,
			// EcsOrderConsts.ITEM_PRINT_STATUS_1,delivery_id);
		} else if (EcsOrderConsts.LOGIS_AGAIN.equals(post_type)) {// 整单重发

		} else if (EcsOrderConsts.LOGIS_SUPPLY.equals(post_type)) {// 补寄
			// 根据itemIds更新
			if (isHis) {
				sql = "update es_delivery_item_his a  set a.col1=? where a.item_id=? and a.source_from='"
						+ ManagerUtils.getSourceFrom() + "'";
				;
			} else {
				sql = SF.ecsordSql("UPDATE_ES_DELIVERY_ITEM_BY_ID");
			}

			if (itemIds != null) {
				String[] ids = itemIds.split(",");
				for (String id : ids) {
					this.baseDaoSupport.execute(sql, EcsOrderConsts.ITEM_PRINT_STATUS_1, id);
				}
			}
		}
	}

	/**
	 * 得到一个18位唯一的随机数,生成规则为当前时间yyMMddHHmmss+6位随机数
	 * 
	 * @return String
	 */
	private synchronized String getUniqueRandomNum() {
		String date = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		StringBuilder sb = new StringBuilder(date);
		for (int i = 0; i < 6; i++) {
			sb.append((int) (10 * Math.random()));
		}
		return sb.toString();
	}

	String button = "<div style='text-align:center;margin-top:100px;CURSOR:hand;#DEFFAC;position:absolute;bottom:0px;"
			+ "padding:0 auto;padding-left:45%;'><input type='button' name='button' id='printButton' value='打印' "
			+ "class='Noprint' onclick='clickPrintBtn();' style='background:url(../images/btnback.jpg) "
			+ "repeat-x 0 0 #f8f8f8; color:#333; border:1px solid #aaa; height:22px;' /></div>";

	String buttonNew = "<div style='text-align:center;margin-top:100px;CURSOR:hand;#DEFFAC;position:absolute;bottom:0px;"
			+ "padding:0 auto;padding-left:45%;'><input type='button' name='button' id='printButton' value='打印' "
			+ "class='Noprint' onclick='print();' style='background:url(../images/btnback.jpg) "
			+ "repeat-x 0 0 #f8f8f8; color:#333; border:1px solid #aaa; height:22px;' /></div>";

	/*
	 * String buttonNew =
	 * "<div style='text-align:center;margin-top:100px;CURSOR:hand;#DEFFAC;position:absolute;bottom:0px;padding:0 auto;padding-left:45%;'>"
	 * + "<input type=\"button\" value=\"打印预览...\" onClick=\"print('打印预览...')\">"+
	 * "<input type=\"button\" value=\"打印...\" onClick=\"print('打印...')\">"+
	 * "<input type=\"button\" value=\"打印\" onClick=\"print('打印')\"></div>";
	 */

	String tail = "</body>" + "</html>";

	String closeEnd = "<script type=\"text/javascript\">" + " setTimeout('print()',500);	" + " </script> ";

	/* String closeEnd = ""; */

	public String getHead(String cxt) {

		String head = " <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>"
				+ "<html xmlns='http://www.w3.org/1999/xhtml'>                                                                              "
				+ "<head>                                                                                                                   "
				+ "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />                                                    "
				+ "<script>var ctx ='" + cxt + "';</script>" + "<script src='" + cxt
				+ "/ecs_ord/js/post_print.js'></script>" + "<script type='text/javascript' src='" + cxt
				+ "/ecs_ord/js/jquery-1.4.2.min.js'></script>" + "<script type='text/javascript' src='" + cxt
				+ "/publics/js/common/common.js'></script>"
				+ "<style media='print' type='text/css' >.Noprint{  display: none; } </style>"
				+ "</head>                                                                                                                  "
				+ "<body>";
		return head;
	}

	/**
	 * 用于更新赠品状态的表单
	 * 
	 * @param delivery_id
	 * @param itmesIds
	 * @param post_type
	 * @return
	 */
	public String getForm(String delivery_id, String itmesIds, String post_type, String order_is_his) {
		String form = "<form id ='formUpdate' name ='formUpdate' method='post'  enctype='multipart/form-data'>"
				+ "<input type='hidden' id='delivery_id' name='delivery_id'  value='" + delivery_id + "' />"
				+ "<input type='hidden' id='itmesIds' name='itmesIds'  value='" + itmesIds + "' />"
				+ "<input type='hidden' id='post_type' name='post_type'  value='" + post_type + "' />"
				+ "<input type='hidden' id='order_is_his' name='order_is_his'  value='" + order_is_his + "' />"
				+ "</form>";
		return form;
	}

	private List<OrderDeliveryBusiRequest> queryDelieryById(String delieryId) {

		String sql = "select a.* from es_delivery_his a where a.delivery_id=? and source_from=?";
		return this.baseDaoSupport.queryForList(sql, OrderDeliveryBusiRequest.class, delieryId,
				ManagerUtils.getSourceFrom());
	}

	/**
	 * @param order_id         --订单id
	 * @param es_delivery      --物流表es_delivery ID
	 * @param delvery_print_id --物流打印表es_delivery_prints ID
	 * @param reissueIds       --勾选的补寄品es_delivery_item id
	 * @param postType         --物流类型 0-正常 发货 1-补寄 2-重发 * @param wlnum --物流ID
	 *                         * @param postId --物流公司ID * @param cxt -跟路径
	 * @param order_is_his     --是否是历史数据 1-是
	 */
	@Override
	public String doHotFreeModel(String order_id, String delivery_id, String delvery_print_id, String reissueIds,
			String postType, String wlnum, String postId, String cxt, String order_is_his, String is_receipt) {
		String htmlCode = "";

		String rs0018 = "";
		String rs0019 = "";
		String rs0020 = "";
		String rs0021 = "";
		String rs0022 = "";
		String rs0023 = "";
		String rs0024 = "";
		String rs0025 = "";
		String rs0026 = "";
		String rs0027 = "";
		String rs0028 = "";
		String rs0029 = "";
		String rs0030 = "";
		String rs0031 = "";
		String rs0032 = "";
		String rs0033 = "";
		String rs0034 = "";
		String rs0035 = "";
		String rs0036 = "";
		String rs0037 = "";
		String rs0038 = "";
		String rs0039 = "";
		String rs0040 = "";
		String rs0041 = "";
		String rs0042 = "";
		String rs0043 = "";
		String rs0044 = "";
		String rs0045 = "";
		String rs0046 = "";
		String rs0047 = "";
		String rs0048 = "";
		String rs0049 = "";
		String rs0050 = "";
		String rs0051 = "";
		String rs0052 = "";
		String rs0053 = "";
		String rs0054 = "";
		String rs0055 = "";
		String rs0056 = "";
		String rs0057 = "";
		String rs0058 = "";
		String rs0059 = "";
		String rs0060 = "";
		String rs0061 = "";
		String rs0062 = "";
		String rs0063 = "";
		String rs0064 = "";
		String rs0065 = "";
		String rs0066 = "";
		String rs0067 = "";
		String rs0068 = "";
		String rs0069 = "";
		String rs0070 = "";
		String rs0071 = "";
		String rs0072 = "";
		String rs0073 = "";
		String rs0085 = "";
		String rs0098 = "";

		String postCompCode = "";
		String modelId = "";
		isHis = false;
		if (EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)) {// 是历史单
			isHis = true;
		}

		// 获取订单信息
		OrderTreeBusiRequest orderTree = null;
		if (isHis) {// 是历史单
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		} else {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}

		OrderDeliveryBusiRequest delivery = new OrderDeliveryBusiRequest();
		if (isHis) {// 是历史订单（），则不能从订单树获取
			// 根据delivery_id查询数据库表delivery
			List<OrderDeliveryBusiRequest> dlist = queryDelieryById(delivery_id);// 更改从his表查询
			if (dlist != null && dlist.size() > 0) {
				delivery = dlist.get(0);
				// ship_name 放在ES_ORDER表
				String ship_name = orderTree.getOrderBusiRequest().getShip_name();
				delivery.setShip_name(ship_name);
			}

		} else {
			// 获取本次物流信息 es_delivery 根据页面传送过来的delivery_id从列表中过滤
			List<OrderDeliveryBusiRequest> deList = orderTree.getOrderDeliveryBusiRequests();
			for (OrderDeliveryBusiRequest orderDelivery : deList) {
				if (orderDelivery.getDelivery_id().equals(delivery_id)) {
					delivery = orderDelivery;
					// ship_name 放在ES_ORDER表
					String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
							AttrConsts.SHIP_NAME);
					delivery.setShip_name(ship_name);
				}
			}
		}

		List<OrderDeliveryItemBusiRequest> deliveryItemslist = new ArrayList<OrderDeliveryItemBusiRequest>();
		if (postType.equals(EcsOrderConsts.LOGIS_SUPPLY)) {// 补寄才管补寄品
			if (isHis) {
				deliveryItemslist = iOrderSupplyManager.queryDeliveryItemsByDeId(delivery_id, true);
			} else {
				DeliveryItemsQueryByDeIdReq ItemsReq = new DeliveryItemsQueryByDeIdReq();
				ItemsReq.setDelivery_id(delivery_id);
				DeliveryItemsQueryByDeIdResp ItemsResp = orderServices.queryDeliveryItemsByDeId(ItemsReq);
				List<DeliveryItem> deliveryItemslistAll = ItemsResp.getDeliveryItems();
				for (DeliveryItem deliveryItem : deliveryItemslistAll) {
					OrderDeliveryItemBusiRequest items = new OrderDeliveryItemBusiRequest();
					items.setBuy_num(deliveryItem.getNum());
					items.setName(deliveryItem.getName());
					deliveryItemslist.add(items);

				}
			}

		}

		// 获取es_delivery_prints表的数据 根据页面传送过来 的主键delivery_prints_id查询
		DeliveryPrints print = deliveryPrintsManager.get(delvery_print_id);
		if (print == null) {
			print = new DeliveryPrints();
		}

		if (wlnum == null || wlnum.equals("")) {
			// 自动生成的物流单号
			wlnum = this.getUniqueRandomNum();
		}

		// 物流公司编码
		postCompCode = getComCodeByComId(postId);
		modelId = "10025";// 热免单测试
		// modelId="10008";//测试代码
		rs0070 = this.getRs0070(print);// 个性化提示区，例如COD,POD
		rs0022 = this.getRs0022(delivery, postCompCode, 52);// 收件人地址
		String rs0092 = this.getRs0022(delivery, postCompCode, 23);// 收件人地址
		rs0023 = this.getRs0023(delivery);// 收件人姓名
		rs0024 = this.getRs0024(delivery);// 收件人电话
		rs0060 = "";// 收件人单位
		rs0025 = this.getRs0025(print, substr16);// 发件人地址
		String province_name = "";
		String city_name = "";
		String region_name = "";
//		if(!EcsOrderConsts.PLAT_TYPE_10061.equals(orderTree.getOrderExtBusiRequest().getPlat_type())){
//			province_name = CommonDataFactory.getInstance().getLocalName(delivery.getProvince_id().toString());
//			city_name = CommonDataFactory.getInstance().getLocalName(delivery.getCity_id().toString());
//			region_name = CommonDataFactory.getInstance().getLocalName(delivery.getRegion_id().toString());
//		}else{
//		}
		province_name = baseDaoSupport.queryForString(
				"select c.name from es_regions_zb c where c.code='" + delivery.getProvince_id() + "' and rownum<2");
		city_name = baseDaoSupport.queryForString(
				"select c.name from es_regions_zb c where c.code='" + delivery.getCity_id() + "' and rownum<2");
		region_name = baseDaoSupport.queryForString(
				"select c.name from es_regions_zb c where c.code='" + delivery.getRegion_id() + "' and rownum<2");
		String address = province_name + "  " + city_name + "  " + region_name;
		if (StringUtils.isNotEmpty(region_name)) {
			if (rs0022.indexOf(region_name) > 0) {
				rs0022 = rs0022.substring(rs0022.indexOf(region_name) + region_name.length()).trim();
			}
		}
		rs0022 = "<span style='font-size:18pt'>" + address + "</span><br>" + rs0022;
		rs0026 = this.getRs0026(print);// 发件人姓名
		rs0027 = this.getRs0027(print);// 发件人电话

		rs0054 = this.getRs0054(print, 40);// 声明物品
		rs0036 = this.getRs0036(print);// 原寄地
		/**
		 * 现在打印的是顺丰返回值，不用再做处理 if(null!=rs0036&&rs0036.length()>=4){ rs0036 =
		 * rs0036.substring(1); }
		 */
		rs0069 = this.getRs0069(print);// 大写代收货款
		rs0053 = this.getRs0053(print);// 发件公司
		rs0056 = this.getRs0056(print);// 是否签回单
		rs0057 = this.getRs0057(print);// 发件人邮编
		rs0098 = this.getRs0098(print);// 目的地
		/**
		 * 现在打印的是顺丰返回值，不用再做处理 if(null!=rs0098&&rs0098.length()>=4){ rs0098 =
		 * rs0098.substring(1); }
		 */
		String rs0083 = this.getRs0083(print);
		rs0043 = this.getRs0043(print);// 代收货款
		if (!StringUtil.isEmpty(rs0043)) {
			rs0043 = "代收货款<br>&nbsp;&nbsp;&nbsp;&nbsp;￥" + rs0043;
		}
		rs0038 = this.getRs0038(print);// 物流公司收件人
		rs0069 = this.getRs0069(print);// 打印时间
		String rs0094 = orderTree.getOrderExtBusiRequest().getOut_tid();
		String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME);
		rs0050 = this.getRs0050(print, postCompCode, ship_name);// 投递要求
		Map<String, String> modeMap = iOrderInvoiceManager.getHotFreeModelParams();
		if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_receipt)) {
			String postmode = print.getPostage_mode();
			if (EcsOrderConsts.POSTAGE_MODE_JZZF.equals(postmode)) {
				rs0085 = rs0085 + "<p>" + "付款方式：到付          月结账号：" + emptyString(print.getMonthly_payment()) + "</p>"
						+ "<p>" + "实际重量：  kg    计费重量：1kg</p>";
			}
//			rs0054 = "回收身份证复印件";
			rs0054 = "目录";
			rs0050 = "正向单号：" + print.getPost_no();
			String rs00921 = this.getRs0022(delivery, postCompCode, substr16);// 收件人地址
			htmlCode = "<div style='position:relative; width:386px; height:579px; margin:0 auto; font-family:\"微软雅黑\"; color:#000;'>";
			htmlCode = htmlCode
					+ "<div style='CURSOR:hand;#DEFFAC;PADDING-LEFT: 0px;PADDING-TOP: 0px;PADDING-RIGHT: 0px; PADDING-BOTTOM: 0px;position:absolute;top:0px;left:0px; font-size:7pt;'><img src='"
					+ cxt + "/ecs_ord/image/invoice/invoice10025.png' width='100%'/></div>";// 底图
			htmlCode = htmlCode + "<div style='" + modeMap.get("10053").toString() + " font-size:7pt;'>" + rs0060
					+ "</div>";// 收件人单位
			htmlCode = htmlCode + "<div style='" + modeMap.get("10027").toString() + " font-size:7pt;'>" + rs0024
					+ "</div>";// 收件人电话
			htmlCode = htmlCode + "<div style='" + modeMap.get("10025").toString() + " font-size:7pt;'>" + rs0092
					+ "</div>";// 收件人地址
			htmlCode = htmlCode + "<div style='" + modeMap.get("10026").toString() + " font-size:7pt;'>" + rs0023
					+ "</div>";// 收件人姓名
			htmlCode = htmlCode + "<div style='" + modeMap.get("10098").toString() + "font-size:18pt;'>"
					+ emptyString(rs0036) + "</div>";// 原寄地
			htmlCode = htmlCode + "<div style='" + modeMap.get("10036").toString() + "font-size:12pt;'>"
					+ emptyString(rs0098) + "</div>";// 目的地
			htmlCode = htmlCode + "<div style='" + modeMap.get("10060").toString() + " font-size:7pt;'>" + rs0053
					+ "</div>";// 发件公司
			htmlCode = htmlCode + "<div style='" + modeMap.get("10024").toString() + " font-size:7pt;'>" + rs0027
					+ "</div>";// 发件人电话
			htmlCode = htmlCode + "<div style='" + modeMap.get("10022").toString() + " font-size:7pt;'>" + rs0025
					+ "</div>";// 发件人地址
			htmlCode = htmlCode + "<div style='" + modeMap.get("10023").toString() + " font-size:7pt;'>" + rs0026
					+ "</div>";// 发件人姓名
			htmlCode = htmlCode + "<div style='" + modeMap.get("10085").toString() + " font-size:7pt;'>" + rs0085
					+ "</div>";// 回收身份证复印件
			htmlCode = htmlCode + "<div style='" + modeMap.get("10086").toString() + " font-size:7pt;'>" + rs0060
					+ "</div>";// 收件人单位
			htmlCode = htmlCode + "<div style='" + modeMap.get("10087").toString() + " font-size:7pt;'>" + rs0024
					+ "</div>";// 收件人电话
			htmlCode = htmlCode + "<div style='" + modeMap.get("10088").toString() + " font-size:7pt;'>" + rs00921
					+ "</div>";// 收件人地址
			htmlCode = htmlCode + "<div style='" + modeMap.get("10089").toString() + " font-size:7pt;'>" + rs0023
					+ "</div>";// 收件人姓名
			htmlCode = htmlCode + "<div style='" + modeMap.get("10090").toString() + " font-size:7pt;'>" + rs0053
					+ "</div>";// 发件公司
			htmlCode = htmlCode + "<div style='" + modeMap.get("10091").toString() + " font-size:7pt;'>" + rs0027
					+ "</div>";// 发件人电话
			htmlCode = htmlCode + "<div style='" + modeMap.get("10092").toString() + " font-size:7pt;'>" + rs0025
					+ "</div>";// 发件人地址
			htmlCode = htmlCode + "<div style='" + modeMap.get("10093").toString() + " font-size:7pt;'>" + rs0026
					+ "</div>";// 发件人姓名
			htmlCode = htmlCode + "<div style='" + modeMap.get("10050").toString() + " font-size:7pt;'>" + rs0050
					+ "</div>";// 备注：正向单号
			htmlCode = htmlCode + "<div style='" + modeMap.get("10054").toString() + " font-size:12pt;'>" + rs0054
					+ "</div>";
			htmlCode = htmlCode + "<div style='" + modeMap.get("10094").toString() + " font-size:7pt;'>" + rs0094
					+ "</div>";// 外部订单号
			htmlCode = htmlCode + "<div style='" + modeMap.get("10099").toString() + " font-size:7pt;'>" + rs0038
					+ "</div>";// 物流公司收件人,回单派件人
			htmlCode = htmlCode + "<div style='" + modeMap.get("10019").toString() + " font-size:7pt;'>5元</div>";
			htmlCode = htmlCode + "<div style='" + modeMap.get("10049").toString() + " font-size:7pt;'>5元</div>";
			// htmlCode = htmlCode + "<div style='"+modeMap.get("10095").toString()+"
			// width:108px;height:45px;color:#000;'><img
			// src=\""+cxt+"/ecs_ord/image/invoice/userlogo1.gif\"
			// width='100%'></div>";//background:url(\""+cxt+"/ecs_ord/image/invoice/userlogo1.gif\")
			// no-repeat;
			htmlCode = htmlCode + "<div style='" + modeMap.get("10096").toString()
					+ " ' class='barcode' id='codeImg1'><script>createBarcode('codeImg1','" + print.getReceipt_no()
					+ "','C');</script></div>";
			htmlCode = htmlCode + "<div style='" + modeMap.get("10097").toString() + " font-size:12pt;'>"
					+ print.getReceipt_no() + "</div>";
			htmlCode = htmlCode + "</div>";
		} else {
			rs0085 = this.getRs0085(print, delivery);
			htmlCode = "<div style='position:relative; width:386px; height:579px; margin:0 auto; font-family:\"微软雅黑\"; color:#000;'>";
			htmlCode = htmlCode
					+ "<div style='CURSOR:hand;#DEFFAC;PADDING-LEFT: 0px;PADDING-TOP: 0px;PADDING-RIGHT: 0px; PADDING-BOTTOM: 0px;position:absolute;top:0px;left:0px; font-size:7pt;'><img src='"
					+ cxt + "/ecs_ord/image/invoice/invoice10025.png' width='100%'/></div>";// 底图
			htmlCode = htmlCode + "<div style='" + modeMap.get("10053").toString() + " font-size:7pt;'>" + rs0053
					+ "</div>";// 发件公司
			htmlCode = htmlCode + "<div style='" + modeMap.get("10027").toString() + " font-size:7pt;'>" + rs0027
					+ "</div>";// 发件人电话
			htmlCode = htmlCode + "<div style='" + modeMap.get("10025").toString() + " font-size:7pt;'>" + rs0025
					+ "</div>";// 发件人地址
			htmlCode = htmlCode + "<div style='" + modeMap.get("10026").toString() + " font-size:7pt;'>" + rs0026
					+ "</div>";// 发件人姓名
			htmlCode = htmlCode + "<div style='" + modeMap.get("10036").toString() + "font-size:12pt;'>"
					+ emptyString(rs0036) + "</div>";// 原寄地
			htmlCode = htmlCode + "<div style='" + modeMap.get("10098").toString() + "font-size:18pt;'>"
					+ emptyString(rs0098) + "</div>";// 目的地
			htmlCode = htmlCode + "<div style='" + modeMap.get("10060").toString() + " font-size:7pt;'>" + rs0060
					+ "</div>";// 收件人单位
			htmlCode = htmlCode + "<div style='" + modeMap.get("10024").toString() + " font-size:7pt;'>"
					+ getRs0024(delivery, 30) + "</div>";// 收件人电话
			htmlCode = htmlCode + "<div style='" + modeMap.get("10022").toString() + " font-size:7pt;'>" + rs0022
					+ "</div>";// 收件人地址
			htmlCode = htmlCode + "<div style='" + modeMap.get("10023").toString() + " font-size:7pt;'>" + rs0023
					+ "</div>";// 收件人姓名
			htmlCode = htmlCode + "<div style='" + modeMap.get("10085").toString() + " font-size:7pt;'>" + rs0085
					+ "</div>";// 回收身份证复印件
			htmlCode = htmlCode + "<div style='" + modeMap.get("10086").toString() + " font-size:7pt;'>" + rs0053
					+ "</div>";// 发件公司
			htmlCode = htmlCode + "<div style='" + modeMap.get("10087").toString() + " font-size:7pt;'>" + rs0027
					+ "</div>";// 发件人电话
			htmlCode = htmlCode + "<div style='" + modeMap.get("10088").toString() + " font-size:7pt;'>" + rs0025
					+ "</div>";// 发件人地址
			htmlCode = htmlCode + "<div style='" + modeMap.get("10089").toString() + " font-size:7pt;'>" + rs0026
					+ "</div>";// 发件人姓名
			htmlCode = htmlCode + "<div style='" + modeMap.get("10090").toString() + " font-size:7pt;'>" + rs0060
					+ "</div>";// 收件人单位
			htmlCode = htmlCode + "<div style='" + modeMap.get("10091").toString() + " font-size:7pt;'>"
					+ getRs0024(delivery, 20) + "</div>";// 收件人电话
			htmlCode = htmlCode + "<div style='" + modeMap.get("10092").toString() + " font-size:7pt;'>" + rs0092
					+ "</div>";// 收件人地址
			htmlCode = htmlCode + "<div style='" + modeMap.get("10093").toString() + " font-size:7pt;'>" + rs0023
					+ "</div>";// 收件人姓名
			htmlCode = htmlCode + "<div style='" + modeMap.get("10054").toString() + " font-size:7pt;'>" + rs0054
					+ "</div>";// 声明物品
			htmlCode = htmlCode + "<div style='" + modeMap.get("10070").toString() + " font-size:28pt;'>" + rs0070
					+ "</div>";// 个性化提示区，例如COD,POD
			htmlCode = htmlCode + "<div style='" + modeMap.get("10094").toString() + " font-size:7pt;'>" + rs0094
					+ "</div>";// 外部订单号
			htmlCode = htmlCode + "<div style='" + modeMap.get("10083").toString() + " font-size:14pt;'>" + rs0083
					+ "</div>";
			htmlCode = htmlCode + "<div style='" + modeMap.get("10043").toString() + " font-size:12pt;'>" + rs0043
					+ "</div>";
			htmlCode = htmlCode + "<div style='" + modeMap.get("10069").toString() + " font-size:7pt;'>" + rs0069
					+ "</div>";
			htmlCode = htmlCode + "<div style='" + modeMap.get("10077").toString() + " font-size:7pt;'>" + rs0069
					+ "</div>";
			htmlCode = htmlCode + "<div style='" + modeMap.get("10050").toString() + " font-size:7pt;'>" + rs0050
					+ "</div>";// 投递要求(备注)
			htmlCode = htmlCode + "<div style='" + modeMap.get("10038").toString() + " font-size:7pt;'>" + rs0038
					+ "</div>";// 物流公司收件人
			// htmlCode = htmlCode + "<div style='"+modeMap.get("10095").toString()+"
			// width:108px;height:45px;color:#000;'><img
			// src=\""+cxt+"/ecs_ord/image/invoice/userlogo1.gif\"
			// width='100%'></div>";//background:url(\""+cxt+"/ecs_ord/image/invoice/userlogo1.gif\")
			// no-repeat;
			htmlCode = htmlCode + "<div style='" + modeMap.get("10096").toString()
					+ " ' class='barcode' id='codeImg1'><script>createBarcode('codeImg1','" + print.getPost_no()
					+ "','C');</script></div>";
			htmlCode = htmlCode + "<div style='" + modeMap.get("10097").toString() + " font-size:12pt;'>"
					+ print.getPost_no() + "</div>";
			htmlCode = htmlCode + "</div>";
		}
		htmlCode = this.getHotHead(cxt) + htmlCode + button
				+ this.getForm(delivery_id, reissueIds, postType, order_is_his) + tail;

		return htmlCode;

	}

	public String getHotHead(String cxt) {

		String head = " <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>"
				+ "<html xmlns='http://www.w3.org/1999/xhtml'>                                                                              "
				+ "<head>                                                                                                                   "
				+ "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />                                                    "
				+ "<script>var ctx ='" + cxt + "';</script>" + "<script src='" + cxt
				+ "/ecs_ord/js/post_print.js'></script>" + "<script type='text/javascript' src='" + cxt
				+ "/ecs_ord/js/jquery-1.4.2.min.js'></script>" + "<script type='text/javascript' src='" + cxt
				+ "/ecs_ord/js/code128.js'></script>" + "<script type='text/javascript' src='" + cxt
				+ "/publics/js/common/common.js'></script>"
				+ "<style media='print' type='text/css' >.Noprint{  display: none; } </style>"
				+ "<link rel='stylesheet' href='" + cxt + "/ecs_ord/js/code.css' type='text/css' charset='utf-8'>"
				+ "</head>                                                                                                                  "
				+ "<body>";
		return head;
	}

	public String getHotHeadNew(String cxt) {
		String printer = getPrinter("02");// 获取发货单打印机
		StringBuffer sb = new StringBuffer();
		sb.append("<html> ");
		sb.append("<head>");
		sb.append("<META content=\"IE=9,IE=8\" http-equiv=\"X-UA-Compatible\">");
		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=gbk' /> ");
		sb.append("<script>var ctx ='" + cxt + "';</script>");
		sb.append("<script type='text/javascript' src='" + cxt + "/ecs_ord/js/jquery-1.4.2.min.js'></script>");
		sb.append("<script type='text/javascript' src='" + cxt + "/ecs_ord/js/code128.js'></script>");
		sb.append("<script type='text/javascript' src='" + cxt + "/publics/js/common/common.js'></script>");
		sb.append("<style media='print' type='text/css' >.Noprint{  display: none; } </style>");
		sb.append("<link rel='stylesheet' href='" + cxt + "/ecs_ord/js/code.css' type='text/css' charset='gbk'>");
		sb.append(
				"<OBJECT  ID='jatoolsPrinter' CLASSID='CLSID:B43D3361-D075-4BE2-87FE-057188254255' codebase='jatoolsPrinter.cab#version=8,6,0,0'></OBJECT>");
		sb.append(" <script> ");
		sb.append("function print(){    ");
		sb.append(" myDoc = { ");
		sb.append("	settings:{topMargin:10, ");
		sb.append("				leftMargin:10, ");
		sb.append("				bottomMargin:10, ");
		sb.append(" 				rightMargin:10, ");
		sb.append("				paperName:'领取单', ");
		if (StrTools.isNotEmpty(printer)) {
			sb.append("          printer:'" + printer + "',");
		}
		sb.append("  				orientation:1}, ");
		sb.append("     documents: document, ");
		sb.append("     copyrights: '杰创软件拥有版权  www.jatools.com'  ");
		sb.append("  }; ");
		sb.append(" ");
		sb.append("	 document.getElementById(\"jatoolsPrinter\").print(myDoc ,false);      ");
		sb.append("	 window.close();  	");
		sb.append("	} ");
		sb.append(" </script> ");
		sb.append("</head> ");
		sb.append("<body>");
		return sb.toString();
	}

	/*
	 * public String getHotHeadNew(String cxt){ String printer =
	 * getPrinter("02");//获取发货单打印机 StringBuffer sb = new StringBuffer();
	 * sb.append("<html> "); sb.append("<head>");
	 * sb.append("<META content=\"IE=9 IE=8\" http-equiv=\"X-UA-Compatible\">"); sb.
	 * append("<meta http-equiv='Content-Type' content='text/html; charset=gbk' /> "
	 * ); sb.append("<script>var ctx ='"+cxt+"';</script>");
	 * sb.append("<script type='text/javascript' src='"+cxt+
	 * "/ecs_ord/js/jquery-1.4.2.min.js'></script>");
	 * sb.append("<script type='text/javascript' src='"+cxt+
	 * "/ecs_ord/js/code128.js'></script>");
	 * sb.append("<script type='text/javascript' src='"+cxt+
	 * "/publics/js/common/common.js'></script>"); sb.
	 * append("<style media='print' type='text/css' >.Noprint{  display: none; } </style>"
	 * ); sb.append("<link rel='stylesheet' href='"
	 * +cxt+"/ecs_ord/js/code.css' type='text/css' charset='gbk'>"); sb.
	 * append("<OBJECT  ID='jatoolsPrinter' CLASSID='CLSID:B43D3361-D075-4BE2-87FE-057188254255' codebase='jatoolsPrinter.cab#version=8,6,0,0'></OBJECT>"
	 * ); sb.append(" <script> "); sb.append("function print(how){    ");
	 * sb.append(" myDoc = { "); sb.append("	settings:{topMargin:10, ");
	 * sb.append("				leftMargin:10, ");
	 * sb.append("				bottomMargin:10, ");
	 * sb.append(" 				rightMargin:10, ");
	 * sb.append("				paperName:'领取单', ");
	 * if(StrTools.isNotEmpty(printer)){
	 * sb.append("             printer:'"+printer+"',"); }
	 * sb.append("  				orientation:1}, ");
	 * sb.append("     documents: document, ");
	 * sb.append("     copyrights: '杰创软件拥有版权  www.jatools.com'  ");
	 * sb.append("  }; "); sb.append("  	if(how == '打印预览...'){ 			 "); sb.
	 * append("			document.getElementById(\"jatoolsPrinter\").printPreview(myDoc );    	"
	 * ); sb.append("	}else if(how == '打印...'){ 		    "); sb.
	 * append("			document.getElementById(\"jatoolsPrinter\").print(myDoc ,true);   	"
	 * ); sb.append(" 	}else { 		    "); sb.
	 * append("			document.getElementById(\"jatoolsPrinter\").print(myDoc ,false);      "
	 * ); sb.append("	   }	"); sb.append("	} "); sb.append(" </script> ");
	 * sb.append( "</head> "); sb.append( "<body>"); return sb.toString(); }
	 */

	public String getHotHeadDelivery(String cxt) {
		String printer = getPrinter("01");
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE HTML><html>");
		sb.append("<head>  ");
		sb.append("<META content=\"IE=9,IE=8\" http-equiv=\"X-UA-Compatible\">");
		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=gbk' />  ");
		sb.append("<script>var ctx ='" + cxt + "';</script>");
		sb.append("<script type='text/javascript' src='" + cxt + "/ecs_ord/js/jquery-1.4.4.min.js?1=1'></script>");
		sb.append("<script type='text/javascript' src='" + cxt + "/ecs_ord/js/jquery-barcode.js?2=1'></script>");
		// sb.append("<script type='text/javascript'
		// src='"+cxt+"/editor/ckeditor/adapters/jquery.js?1=1'></script>");
		// sb.append("<script type='text/javascript'
		// src='"+cxt+"/publics/js/common/common.js'></script>");
		sb.append("<style media='print' type='text/css' >.Noprint{  display: none; } </style>");
		sb.append("<link rel='stylesheet' href='" + cxt + "/ecs_ord/js/code.css' type='text/css' charset='gbk'>");
		sb.append(
				"<OBJECT  id='jatoolsPrinter' CLASSID='CLSID:B43D3361-D075-4BE2-87FE-057188254255' codebase='jatoolsPrinter.cab#version=8,6,0,0'></OBJECT>");
		sb.append(" <script > ");
		sb.append("function print(){  ");
		sb.append(" myDoc = { ");
		sb.append("	settings:{topMargin:10, ");
		sb.append("				leftMargin:10, ");
		sb.append("				bottomMargin:10, ");
		sb.append(" 			rightMargin:10, ");
		sb.append("				paperName:'物流单', ");
		if (StrTools.isNotEmpty(printer)) {
			sb.append("             printer:'" + printer + "',");
		}
		sb.append("  				orientation:1}, ");
		sb.append("      documents: document, ");
		sb.append("     copyrights: '杰创软件拥有版权  www.jatools.com'  ");
		sb.append("  }; ");
		sb.append("			document.getElementById(\"jatoolsPrinter\").print(myDoc ,false);      ");
		sb.append("			window.close();");
		sb.append("	} ");
		sb.append(" </script> ");
		sb.append("</head> ");
		sb.append("<body>");
		return sb.toString();
	}

	/*
	 * public String getHotHeadDelivery(String cxt){ String printer =
	 * getPrinter("01"); StringBuffer sb = new StringBuffer(); sb.append("<html>");
	 * sb.append( "<head>  ");
	 * sb.append("<META content=\"IE=9  IE=8\" http-equiv=\"X-UA-Compatible\">");
	 * sb.
	 * append("<meta http-equiv='Content-Type' content='text/html; charset=gbk' />  "
	 * ); sb.append("<script>var ctx ='"+cxt+"';</script>");
	 * sb.append("<script type='text/javascript' src='"+cxt+
	 * "/ecs_ord/js/jquery-1.4.2.min.js'></script>");
	 * sb.append("<script type='text/javascript' src='"+cxt+
	 * "/ecs_ord/js/code128.js'></script>");
	 * sb.append("<script type='text/javascript' src='"+cxt+
	 * "/publics/js/common/common.js'></script>"); sb.
	 * append("<style media='print' type='text/css' >.Noprint{  display: none; } </style>"
	 * ); sb.append("<link rel='stylesheet' href='"
	 * +cxt+"/ecs_ord/js/code.css' type='text/css' charset='gbk'>"); sb.
	 * append("<OBJECT  ID='jatoolsPrinter' CLASSID='CLSID:B43D3361-D075-4BE2-87FE-057188254255' codebase='jatoolsPrinter.cab#version=8,6,0,0'></OBJECT>"
	 * ); sb.append(" <script> "); sb.append("function print(how){    ");
	 * sb.append(" myDoc = { "); sb.append("	settings:{topMargin:10, ");
	 * sb.append("				leftMargin:10, ");
	 * sb.append("				bottomMargin:10, ");
	 * sb.append(" 			rightMargin:10, ");
	 * sb.append("				paperName:'物流单', ");
	 * if(StrTools.isNotEmpty(printer)){
	 * sb.append("             printer:'"+printer+"',"); }
	 * sb.append("  				orientation:1}, ");
	 * sb.append("      documents: document, ");
	 * sb.append("     copyrights: '杰创软件拥有版权  www.jatools.com'  ");
	 * sb.append("  }; "); sb.append("  	if(how == '打印预览...'){ 			 "); sb.
	 * append("			document.getElementById(\"jatoolsPrinter\").printPreview(myDoc );    	"
	 * ); sb.append("	}else if(how == '打印...'){ 		    "); sb.
	 * append("			document.getElementById(\"jatoolsPrinter\").print(myDoc ,true);   	"
	 * ); sb.append(" 	}else { 		    "); sb.
	 * append("			document.getElementById(\"jatoolsPrinter\").print(myDoc ,false);      "
	 * ); sb.append("	   }	"); sb.append("	} "); sb.append(" </script> ");
	 * sb.append("</head> "); sb.append("<body>"); return sb.toString(); }
	 */

	public String getHotHeadSF(String cxt) {
		String printer = getPrinter("03");
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE HTML><html><head>");
		sb.append("<META content=\"IE=9,IE=8,chrome=1\" http-equiv=\"X-UA-Compatible\">");
		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=gbk' /> ");
		sb.append("<script>var ctx ='" + cxt + "';</script>");
		sb.append("<script type='text/javascript' src='" + cxt + "/ecs_ord/js/code128.js'></script>");
		sb.append("<link rel='stylesheet' href='" + cxt + "/ecs_ord/js/code.css' type='text/css' charset='gbk'>");
		sb.append("<LINK rel='stylesheet' href='" + cxt + "/ecs_ord/sf/common.css'>");
		sb.append("<LINK rel='stylesheet' href='" + cxt + "/ecs_ord/sf/style.css'>");
		sb.append(
				"<OBJECT  ID='jatoolsPrinter' CLASSID='CLSID:B43D3361-D075-4BE2-87FE-057188254255' codebase='jatoolsPrinter.cab#version=8,6,0,0'></OBJECT>");
		sb.append(" <script> ");
		sb.append("function print(){    ");
		sb.append(" myDoc = { ");
		sb.append("	settings:{topMargin:10, ");
		sb.append("				leftMargin:10, ");
		sb.append("				bottomMargin:10, ");
		sb.append(" 			rightMargin:10, ");
		sb.append("				paperName:'物流单', ");
		if (StrTools.isNotEmpty(printer)) {
			sb.append("             printer:'" + printer + "',");
		}
		sb.append("  				orientation:1}, ");
		sb.append("      documents: document, ");
		sb.append("     copyrights: '杰创软件拥有版权  www.jatools.com'  ");
		sb.append("  }; ");
		sb.append("			document.getElementById(\"jatoolsPrinter\").print(myDoc ,false);      ");
		sb.append("			window.close();");
		sb.append("	} ");
		sb.append(" </script> ");
		sb.append("</head> ");
		sb.append("<body>");
		return sb.toString();
	}

	/*
	 * public String getHotHeadSF(String cxt){ String printer =
	 * "";//getPrinter("01"); StringBuffer sb = new StringBuffer();
	 * sb.append("<!DOCTYPE HTML><html><head>");
	 * sb.append("<META content=\"IE=9  IE=8\" http-equiv=\"X-UA-Compatible\">");
	 * sb.append(
	 * "<meta http-equiv='Content-Type' content='text/html; charset=gbk' /> ");
	 * sb.append( "<script>var ctx ='"+cxt+"';</script>" );
	 * sb.append("<script type='text/javascript' src='"+cxt+
	 * "/ecs_ord/js/code128.js'></script>");
	 * sb.append("<link rel='stylesheet' href='"
	 * +cxt+"/ecs_ord/js/code.css' type='text/css' charset='gbk'>");
	 * sb.append("<LINK rel='stylesheet' href='"+cxt+"/ecs_ord/sf/common.css'>");
	 * sb.append("<LINK rel='stylesheet' href='"+cxt+"/ecs_ord/sf/style.css'>"); sb.
	 * append("<OBJECT  ID='jatoolsPrinter' CLASSID='CLSID:B43D3361-D075-4BE2-87FE-057188254255' codebase='jatoolsPrinter.cab#version=8,6,0,0'></OBJECT>"
	 * ); sb.append(" <script> "); sb.append("function print(how){    ");
	 * sb.append(" myDoc = { "); sb.append("	settings:{topMargin:10, ");
	 * sb.append("				leftMargin:10, ");
	 * sb.append("				bottomMargin:10, ");
	 * sb.append(" 			rightMargin:10, ");
	 * sb.append("				paperName:'物流单', ");
	 * if(StrTools.isNotEmpty(printer)){
	 * sb.append("             printer:'"+printer+"',"); }
	 * sb.append("  				orientation:1}, ");
	 * sb.append("      documents: document, ");
	 * sb.append("     copyrights: '杰创软件拥有版权  www.jatools.com'  ");
	 * sb.append("  }; "); sb.append("  	if(how == '打印预览...'){ 			 "); sb.
	 * append("			document.getElementById(\"jatoolsPrinter\").printPreview(myDoc );    	"
	 * ); sb.append("	    }else if(how == '打印...'){ 		    "); sb.
	 * append("			document.getElementById(\"jatoolsPrinter\").print(myDoc ,true);   	"
	 * ); sb.append(" 	}else { 		    "); sb.
	 * append("			document.getElementById(\"jatoolsPrinter\").print(myDoc ,false);      "
	 * ); sb.append("	   }	"); sb.append("	} "); sb.append(" </script> ");
	 * sb.append("</head> "); sb.append("<body>"); return sb.toString(); }
	 */

	private String emptyString(String s) {
		return s == null ? "" : s;
	}

	@Override
	public String hsB2CPacking(Map<String, String> params) {
		StringBuffer bf = new StringBuffer();
		// 屏蔽无用代码 xiao.ruidan 20180518
		/*
		 * String order_id = params.get("order_id"); String cxt = params.get("cxt");
		 * String order_is_his = params.get("order_is_his"); //
		 * bf.append(getHotHead(cxt));
		 * 
		 *//**
			 * 主体内容开始
			 */
		/*
		 * //获取订单信息 OrderTreeBusiRequest orderTree=null;
		 * if(EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//是历史单 orderTree =
		 * CommonDataFactory.getInstance().getOrderTreeHis(order_id); }else{ orderTree =
		 * CommonDataFactory.getInstance().getOrderTree(order_id); }
		 * List<OrderDeliveryBusiRequest> list=orderTree.getOrderDeliveryBusiRequests();
		 * OrderDeliveryBusiRequest deliverBusiReq = new OrderDeliveryBusiRequest(); for
		 * (OrderDeliveryBusiRequest de : list) {
		 * if(EcsOrderConsts.DELIVERY_TYPE_NORMAL.equals(de.getDelivery_type())){//
		 * 肯定有一条记录是正常发货的 deliverBusiReq=de; break; } } String rs0015 =
		 * deliverBusiReq.getLogi_no();//物流单号 String rs0016 =
		 * "顺丰快递";//deliverBusiReq.getLogi_name();//物流公司名称 String rs0022 =
		 * deliverBusiReq.getShip_addr();//收件人地址 String rs0023 =
		 * orderTree.getOrderBusiRequest().getShip_name();//收件人姓名 String rs0024 =
		 * deliverBusiReq.getShip_mobile();//收件人联系电话 String rs0033 =
		 * "感谢您使用联通业务！欢迎登陆www.10010.com了解联通公司的产品与服务！";//发件人签名 StringBuffer bf54 = new
		 * StringBuffer(); bf54.
		 * append("<table class='grid_s' ><tr><td align='center' width='50'>序号</td><td align='center' width='250'>商品名称</td><td align='center' width='250'>物料编码（SKU）</td><td align='center' width='50'>数量</td></tr>"
		 * ); List<HuashengOrderItemBusiRequest> hsOrderItems =
		 * orderTree.getHuashengOrderItemBusiRequest(); List<OrderItemExtvtlBusiRequest>
		 * orderItems = orderTree.getOrderItemExtvtlBusiRequests(); int seri = 1;
		 * for(HuashengOrderItemBusiRequest hsOrderItem:hsOrderItems){ String
		 * product_name = ""; for(OrderItemExtvtlBusiRequest orderItem:orderItems){
		 * if(StringUtils.equals(hsOrderItem.getSku(), orderItem.getSku())){
		 * product_name = orderItem.getGoods_name(); break; } }
		 * bf54.append("<tr><td align='center'>").append(seri++).
		 * append("</td><td align='center'>");
		 * bf54.append(product_name).append("</td><td align='center'>");
		 * bf54.append(hsOrderItem.getMatnr()).append("</td><td align='center'>");
		 * bf54.append(hsOrderItem.getMenge()).append("</td></tr>"); }
		 * bf54.append("</table><br/><br/><br/>").append(rs0033); String rs0054 =
		 * bf54.toString();//自定义声明物品 String rs0069 = ""; try { rs0069 =
		 * DateUtil.getTime2(); } catch (FrameException e) { e.printStackTrace();
		 * }//打印时间 String rs0081 =
		 * orderTree.getOrderExtBusiRequest().getOut_tid();//外部订单编号 String rs0083 =
		 * "广东联通发货装箱单";//业务类型 String rs0096 =
		 * "<script>createBarcodenew('codeImg','"+rs0081+"','C');</script>";//条形码一
		 * Map<String,String> modeMap = iOrderInvoiceManager.getModelParams("10027");
		 * bf.append("<div style='").append(modeMap.get("10015").toString()).
		 * append(" font-size:12pt;'>物流单号：").append(rs0015).append("</div>");
		 * bf.append("<div style='").append(modeMap.get("10016").toString()).
		 * append(" font-size:12pt;'>物流公司：").append(rs0016).append("</div>");
		 * bf.append("<div style='").append(modeMap.get("10022").toString()).
		 * append(" font-size:12pt;'>收货人地址：").append(rs0022).append("</div>");
		 * bf.append("<div style='").append(modeMap.get("10023").toString()).
		 * append(" font-size:12pt;'>收货人：").append(rs0023).append("</div>");
		 * bf.append("<div style='").append(modeMap.get("10024").toString()).
		 * append(" font-size:12pt;'>收货人电话：").append(rs0024).append("</div>"); //
		 * bf.append("<div style='").append(modeMap.get("10033").toString()).
		 * append(" font-size:12pt;'>").append(rs0033).append("</div>");
		 * bf.append("<div style='").append(modeMap.get("10054").toString()).
		 * append(" font-size:12pt;'>").append(rs0054).append("</div>");
		 * bf.append("<div style='").append(modeMap.get("10069").toString()).
		 * append(" font-size:12pt;'>发货日期：").append(rs0069).append("</div>");
		 * bf.append("<div style='").append(modeMap.get("10081").toString()).
		 * append(" font-size:12pt;'>订单编号：").append(rs0081).append("</div>");
		 * bf.append("<div style='").append(modeMap.get("10083").toString()).
		 * append(" font-size:22pt;'>").append(rs0083).append("</div>");
		 * bf.append("<div style='").append(modeMap.get("10096").toString()).
		 * append("' class='barcode' id='codeImg'>").append(rs0096).append("</div>");
		 *//**
			 * 主体内容结束
			 *//*
				 * 
				 * bf.append(button);
				 */
//		bf.append(tail);
		return bf.toString();
	}

	/**
	 * 配货热敏单打印
	 * 
	 * @param order_id
	 * @param cxt
	 * @return
	 */
	public String doPrintHotFee(String order_id, String print_type, String cxt) {
		StringBuffer bf = new StringBuffer();
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String sn = orderTree.getOrderBusiRequest().getSn();// 条形码号
		String element_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "element_id");// 元素ID
		String mobile_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "mobile_type");// 终端类型
		String goods_id = orderTree.getOrderBusiRequest().getGoods_id();
		String cat_id = "";
		if (!StringUtils.isEmpty(goods_id)) {
			String sql = "select a.cat_id from es_goods a where a.goods_id = " + goods_id;
			List list = new ArrayList();
			list = baseDaoSupport.queryForList(sql);
			if (null != list && list.size() > 0) {
				Map map = (Map) list.get(0);
				if (map.containsKey("cat_id")) {
					cat_id = map.get("cat_id").toString();
				}
			}
		}

		String terminalName = "";
		if (!StringUtils.isEmpty(element_id) && !StringUtils.isEmpty(mobile_type)) {// 根据元素ID，终端类型，商品ＩＤ查找终端名称。
			String sql = "SELECT terminal_name FROM ES_GOODS_ACTION_ELEMENT WHERE ELEMENT_ID = '" + element_id
					+ "' and goods_id = '" + goods_id + "' AND mobile_type = '" + mobile_type + "'";
			List list = new ArrayList();
			list = baseDaoSupport.queryForList(sql);
			if (null != list && list.size() > 0) {
				Map map = (Map) list.get(0);
				if (map.containsKey("terminal_name")) {
					terminalName = map.get("terminal_name").toString();
				}
			}
		}

		// 获取固话号码
		String guhuaNum = "";
		String sql = "select a.guhua_num from es_order_extvtl a where a.order_id  = '" + order_id
				+ "'and a.source_from = 'ECS'";
		List list = new ArrayList();
		list = baseDaoSupport.queryForList(sql);
		if (null != list && list.size() > 0) {
			Map map = (Map) list.get(0);
			if (map.containsKey("guhua_num")) {
				guhuaNum = String.valueOf(map.get("guhua_num"));
			}
		}
		
		if (StrTools.isEmpty(sn)) {
			sn = "";
		}
		String iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		if (!StringUtils.isEmpty(iccid)) {
			iccid = iccid.substring(iccid.length() - 8, iccid.length() - 1);// iccid取后6位
		}
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String mode_print = cacheUtil.getConfigInfo("mode_print");// 新架构未存在sn 需要进行补充
		if ("1".equals(mode_print) && !StringUtil.isEmpty(iccid) && StringUtil.isEmpty(sn)) {
			sn = "0" + iccid;
			OrderBusiRequest ord = orderTree.getOrderBusiRequest();
			ord.setSn(sn);
			ord.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			ord.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			ord.store();
		}

		String rs0094 = orderTree.getOrderExtBusiRequest().getOut_tid();// 外部订单号
		String receive_num = orderTree.getOrderExtBusiRequest().getReceive_num();// 领取号
		if (StringUtil.isEmpty(receive_num)) {
			receive_num = this.createRevceiveNum();
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "receive_num" },
					new String[] { receive_num });
		}
		String rs0096 = "<script>createBarcodeCom('codeImg','" + sn + "','C');</script>";// 条形码一
		// String custName = orderTree.getOrderBusiRequest().getShip_name();
		String custName = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NAME);// 修改为使用开户人姓名
		String mobile = orderTree.getPhoneInfoBusiRequest().getPhone_num();
		String goodsType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);// 商品类型
		if (StrTools.isEmpty(print_type) || "undefined".equals(print_type)) {
			if (SpecConsts.TYPE_ID_20000.equals(goodsType) || SpecConsts.TYPE_ID_20001.equals(goodsType)) { // 号卡 上网卡
				print_type = "1";
			}
			if (SpecConsts.TYPE_ID_20002.equals(goodsType) || SpecConsts.TYPE_ID_20003.equals(goodsType)) { // 合约机 裸机
				print_type = "2";
			}
		}
		String terminal = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null,
				SpecConsts.GOODS_NAME); // 货品-手机
		String planTitle = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PLAN_TITLE);// 套餐名称
		String groupFlowKg = cacheUtil.getConfigInfo("kg_plan_title");
		String goods_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODSNAME);
		String object_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "object_name");
		if ("1".equals(groupFlowKg) && !StringUtil.isEmpty(goods_name) && !goods_name.equals(planTitle)) {
			planTitle = goods_name;
		}
		String kg_old_printtype = cacheUtil.getConfigInfo("kg_old_printtype");

		String cityCode = AttrUtils.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE, "", "");
		String cityName = getCacheName("DC_MODE_REGION", cityCode);
		String terminalNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "terminal_num"); // 终端串码
		if (StringUtils.isEmpty(terminalNum)) {
			terminalNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "object_esn");
		}
		Map<String, String> modeMap = iOrderInvoiceManager.getModelParams("10022");
		if ("2".equals(print_type)) {// 终端
			bf.append("<div id='page1'  style='width:180px;height:150px;'>");
			bf.append("<div style='").append(modeMap.get("10096").toString())
					.append("' class='barcode3' id ='codeImg'>").append(rs0096).append("</div>");// 条形码
			bf.append("<div style='").append(modeMap.get("10100").toString()).append(" font-size:8pt'>").append(mobile)
					.append("</div>");// 手机号码
			bf.append("<div style='").append(modeMap.get("10101").toString()).append(" font-size:8pt'>")
					.append(cityName).append("</div>");// 城市
			bf.append("<div style='").append(modeMap.get("10102").toString()).append(" font-size:8pt'>(")
					.append(custName).append(")</div>");// 客户姓名
			bf.append("<div style='").append(modeMap.get("10103").toString()).append(" font-size:8pt'>领取号：")
					.append(receive_num).append("</div>");// 领取号
			bf.append("<div style='").append(modeMap.get("10104").toString()).append(" font-size:8pt'>订单号：")
					.append(rs0094).append("</div>");// 订单号
			bf.append("<div style='").append(modeMap.get("10105").toString()).append(" font-size:8pt'>型   号：")
					.append(terminal).append("</div>");// 型号
			bf.append("<div style='").append(modeMap.get("10012").toString()).append(" font-size:8pt'>(终端号)：")
					.append(emptyString(terminalNum)).append("</div>");// 终端串号
			bf.append("<div style='").append(modeMap.get("10106").toString()).append(" font-size:8pt'>ICCID：")
					.append(iccid).append("</div>");// iccid
			bf.append("</div>");
		} else if ("1".equals(print_type)) {// 号卡
			if (!StringUtil.isEmpty(kg_old_printtype) && "1".equals(kg_old_printtype)) {// 为了避免影响 特加该判断 若上线后无误 进行代码删除
				bf.append("<div id='page1'  style='width:180px;height:150px;'>");
				bf.append("<div style='").append(modeMap.get("10096").toString())
						.append("' class='barcode3' id ='codeImg'>").append(rs0096).append("</div>");// 条形码
				bf.append("<div style='").append(modeMap.get("10100").toString()).append(" font-size:8pt'>")
						.append(mobile).append("</div>");// 手机号码
				bf.append("<div style='").append(modeMap.get("10101").toString()).append(" font-size:8pt'>")
						.append(cityName).append("</div>");// 城市
				bf.append("<div style='").append(modeMap.get("10102").toString()).append(" font-size:8pt'>(")
						.append(custName).append(")</div>");// 客户姓名
				if ("90000000000000901".equals(cat_id)) {
					bf.append("<div style='").append(modeMap.get("10109").toString()).append(" font-size:8pt'>领取号：")
							.append(receive_num).append("</div>");// 领取号
					bf.append("<div style='").append(modeMap.get("10013").toString()).append(" font-size:7pt'>订单号：")
							.append(rs0094).append("</div>");// 订单号
				} else {
					bf.append("<div style='").append(modeMap.get("10103").toString()).append(" font-size:8pt'>领取号：")
							.append(receive_num).append("</div>");// 领取号
					bf.append("<div style='").append(modeMap.get("10104").toString()).append(" font-size:7pt'>订单号：")
							.append(rs0094).append("</div>");// 订单号
				}
				if (SpecConsts.TYPE_ID_20000.equals(goodsType)) {// 号卡
					bf.append("<div style='").append(modeMap.get("10105").toString())
							.append(" font-size:8pt'>型   号：号卡(").append(planTitle).append(")</div>");// 型号
				} else if (SpecConsts.TYPE_ID_20001.equals(goodsType)) {// 上网卡
					bf.append("<div style='").append(modeMap.get("10105").toString())
							.append(" font-size:8pt'>型   号：上网卡(").append(planTitle).append(")</div>");// 型号
				} else if ("170801435262003016".equals(goodsType) || "170801434582003010".equals(goodsType)) {
					bf.append("<div style='").append(modeMap.get("10105").toString())
							.append(" font-size:8pt'>型   号：号卡(").append(planTitle).append(")</div>");// 型号
				} else if ("170502112412000711".equals(goodsType)) {// 商品小类 待处理
					bf.append("<div style='").append(modeMap.get("10105").toString())
							.append(" font-size:7pt'>终  端  型   号：").append(object_name).append("</div>");// 型号
				} else if ("90000000000000901".equals(cat_id)) {
					bf.append("<div style='").append(modeMap.get("10108").toString()).append(" font-size:7pt'>固话号码：")
							.append(guhuaNum).append("</div>");
					if (null != terminalName && terminalName.length() <= 12) {
						bf.append("<div style='").append(modeMap.get("10012").toString())
								.append(" font-size:7pt'>终端名称：").append(terminalName).append("</div>");
					} else if (null != terminalName && terminalName.length() > 12) {
						String terminalNameUp = terminalName.substring(0, 12);
						String terminalNameDown = terminalName.substring(12);
						bf.append("<div style='").append(modeMap.get("10012").toString())
								.append(" font-size:7pt'>终端名称：").append(terminalNameUp).append("</div>");
						bf.append("<div style='").append(modeMap.get("10106").toString()).append(" font-size:7pt'>")
								.append(terminalNameDown).append("</div>");
					}
				}
				if ("90000000000000901".equals(cat_id)) {
					bf.append("<div style='").append(modeMap.get("10105").toString()).append(" font-size:8pt'>终端串号: ")
							.append(terminalNum).append("</div>");// 终端串号
				} else {
					bf.append("<div style='").append(modeMap.get("10012").toString()).append(" font-size:8pt'>终端串号: ")
							.append(terminalNum).append("</div>");// 终端串号
				}
				if (!"90000000000000901".equals(cat_id)) {
					bf.append("<div style='").append(modeMap.get("10106").toString()).append(" font-size:8pt'>ICCID：")
							.append(iccid).append("</div>");// iccid
				}
				bf.append("</div>");
			} else {
				bf.append("<div id='page1'  style='width:180px;height:150px;'>");
				bf.append("<div style='").append(modeMap.get("10096").toString())
						.append("' class='barcode3' id ='codeImg'>").append(rs0096).append("</div>");// 条形码
				bf.append("<div style='").append(modeMap.get("10100").toString()).append(" font-size:8pt'>")
						.append(mobile).append("</div>");// 手机号码
				bf.append("<div style='").append(modeMap.get("10101").toString()).append(" font-size:8pt'>")
						.append(cityName).append("</div>");// 城市
				bf.append("<div style='").append(modeMap.get("10102").toString()).append(" font-size:8pt'>(")
						.append(custName).append(")</div>");// 客户姓名
				bf.append("<div style='").append(modeMap.get("10103").toString()).append(" font-size:8pt'>领取号：")
						.append(receive_num).append("</div>");// 领取号
				bf.append("<div style='").append(modeMap.get("10104").toString()).append(" font-size:7pt'>订单号：")
						.append(rs0094).append("</div>");// 订单号
				if (SpecConsts.TYPE_ID_20000.equals(goodsType)) {// 号卡
					bf.append("<div style='").append(modeMap.get("10105").toString())
							.append(" font-size:8pt'>型   号：号卡(").append(planTitle).append(")</div>");// 型号
				} else if (SpecConsts.TYPE_ID_20001.equals(goodsType)) {// 上网卡
					bf.append("<div style='").append(modeMap.get("10105").toString())
							.append(" font-size:8pt'>型   号：上网卡(").append(planTitle).append(")</div>");// 型号
				} else if ("170801435262003016".equals(goodsType) || "170801434582003010".equals(goodsType)) {// bss 和
																												// cbss
					bf.append("<div style='").append(modeMap.get("10105").toString())
							.append(" font-size:8pt'>型   号：号卡(").append(planTitle).append(")</div>");// 型号
				}
				bf.append("<div style='").append(modeMap.get("10012").toString()).append(" font-size:8pt'>终端串号: ")
						.append(terminalNum).append("</div>");// 终端串号
				bf.append("<div style='").append(modeMap.get("10106").toString()).append(" font-size:8pt'>ICCID：")
						.append(iccid).append("</div>");// iccid
				bf.append("</div>");
			}
		}
		return this.getHotHeadNew(cxt) + bf.toString() + buttonNew + tail + closeEnd;
	}

	private synchronized String createRevceiveNum() {
		String today = DataUtil.formatDate(new Date());
		String sql = "select a.cf_value from es_config_info a where a.cf_id='ORDER_RECEIVE_NUMBER'";
		String revceiveNum = this.baseDaoSupport.queryForString(sql);
		String[] strs = revceiveNum.split("---");
		String numStr = strs[1];
		if (!today.equals(strs[0])) {
			numStr = "0001";
			String update = "update es_config_info a set a.cf_value='" + today
					+ "---0001' where a.cf_id='ORDER_RECEIVE_NUMBER'";
			this.baseDaoSupport.execute(update);
		}
		int newNum = Integer.valueOf(numStr) + 1;
		String newReceiveNum = today.concat("---").concat(df.format(newNum));
		String update = "update es_config_info a set a.cf_value='" + newReceiveNum
				+ "' where a.cf_id='ORDER_RECEIVE_NUMBER'";
		this.baseDaoSupport.execute(update);
		return newReceiveNum;
	}

	public String getCacheName(String code, String key) {
		String cacheName = "";
		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
		DictManagerCacheProxy dc = new DictManagerCacheProxy(dictManager);
		List list = dc.loadData(code);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String value = (String) map.get("value");
				if (value.equals(key)) {
					cacheName = (String) map.get("value_desc");
				}
			}
		}
		return cacheName;
	}

	// EMS物流热敏单页面拼接
	public String doHotFreeModelNew(String order_id, String delivery_id, String delvery_print_id, String reissueIds,
			String postType, String wlnum, String postId, String cxt, String order_is_his, String is_receipt) {
		String htmlCode = "";

		String rs0018 = "";
		String rs0019 = "";
		String rs0020 = "";
		String rs0021 = "";
		String rs0022 = "";
		String rs0023 = "";
		String rs0024 = "";
		String rs0025 = "";
		String rs0026 = "";
		String rs0027 = "";
		String rs0028 = "";
		String rs0029 = "";
		String rs0030 = "";
		String rs0031 = "";
		String rs0032 = "";
		String rs0033 = "";
		String rs0034 = "";
		String rs0035 = "";
		String rs0036 = "";
		String rs0037 = "";
		String rs0038 = "";
		String rs0039 = "";
		String rs0040 = "";
		String rs0041 = "";
		String rs0042 = "";
		String rs0043 = "";
		String rs0044 = "";
		String rs0045 = "";
		String rs0046 = "";
		String rs0047 = "";
		String rs0048 = "";
		String rs0049 = "";
		String rs0050 = "";
		String rs0051 = "";
		String rs0052 = "";
		String rs0053 = "";
		String rs0054 = "";
		String rs0055 = "";
		String rs0056 = "";
		String rs0057 = "";
		String rs0058 = "";
		String rs0059 = "";
		String rs0060 = "";
		String rs0061 = "";
		String rs0062 = "";
		String rs0063 = "";
		String rs0064 = "";
		String rs0065 = "";
		String rs0066 = "";
		String rs0067 = "";
		String rs0068 = "";
		String rs0069 = "";
		String rs0070 = "";
		String rs0071 = "";
		String rs0072 = "";
		String rs0073 = "";
		String rs0085 = "";
		String rs0098 = "";

		String postCompCode = "";
		String modelId = "";
		isHis = false;
		if (EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)) {// 是历史单
			isHis = true;
		}
		if (StrTools.isEmpty(postType)) {
			postType = "0";
		}
		// 获取订单信息
		OrderTreeBusiRequest orderTree = null;
		if (isHis) {// 是历史单
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		} else {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}
		if (StrTools.isEmpty(delivery_id)) {
			List<OrderDeliveryBusiRequest> deliveryList = orderTree.getOrderDeliveryBusiRequests();
			for (OrderDeliveryBusiRequest delivery : deliveryList) {
				if (postType.equals(delivery.getDelivery_type())) {
					delivery_id = delivery.getDelivery_id();// 正常物流只有一个
				}
			}
		}
		OrderDeliveryBusiRequest delivery = new OrderDeliveryBusiRequest();
		if (isHis) {// 是历史订单（），则不能从订单树获取
			// 根据delivery_id查询数据库表delivery
			List<OrderDeliveryBusiRequest> dlist = queryDelieryById(delivery_id);// 更改从his表查询
			if (dlist != null && dlist.size() > 0) {
				delivery = dlist.get(0);
				// ship_name 放在ES_ORDER表
				String ship_name = orderTree.getOrderBusiRequest().getShip_name();
				delivery.setShip_name(ship_name);
			}

		} else {
			// 获取本次物流信息 es_delivery 根据页面传送过来的delivery_id从列表中过滤
			List<OrderDeliveryBusiRequest> deList = orderTree.getOrderDeliveryBusiRequests();
			for (OrderDeliveryBusiRequest orderDelivery : deList) {
				if (orderDelivery.getDelivery_id().equals(delivery_id)) {
					delivery = orderDelivery;
					// ship_name 放在ES_ORDER表
					String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
							AttrConsts.SHIP_NAME);
					delivery.setShip_name(ship_name);
				}
			}
		}

		List<OrderDeliveryItemBusiRequest> deliveryItemslist = new ArrayList<OrderDeliveryItemBusiRequest>();
		if (postType.equals(EcsOrderConsts.LOGIS_SUPPLY)) {// 补寄才管补寄品
			if (isHis) {
				deliveryItemslist = iOrderSupplyManager.queryDeliveryItemsByDeId(delivery_id, true);
			} else {
				DeliveryItemsQueryByDeIdReq ItemsReq = new DeliveryItemsQueryByDeIdReq();
				ItemsReq.setDelivery_id(delivery_id);
				DeliveryItemsQueryByDeIdResp ItemsResp = orderServices.queryDeliveryItemsByDeId(ItemsReq);
				List<DeliveryItem> deliveryItemslistAll = ItemsResp.getDeliveryItems();
				for (DeliveryItem deliveryItem : deliveryItemslistAll) {
					OrderDeliveryItemBusiRequest items = new OrderDeliveryItemBusiRequest();
					items.setBuy_num(deliveryItem.getNum());
					items.setName(deliveryItem.getName());
					deliveryItemslist.add(items);
				}
			}
		}

		// 获取es_delivery_prints表的数据 根据页面传送过来 的主键delivery_prints_id查询
		DeliveryPrints print = deliveryPrintsManager.get(delvery_print_id);
		if (print == null) {
			print = new DeliveryPrints();
		}

		if (wlnum == null || wlnum.equals("")) {
			// 自动生成的物流单号
			wlnum = this.getUniqueRandomNum();
		}

		// 物流公司编码
		postCompCode = getComCodeByComId(postId);
		if (postCompCode.indexOf("SF") > -1) {
			return this.doHotFreeModelSFNew(order_id, delivery_id, delvery_print_id, reissueIds, postType, wlnum,
					postId, cxt, order_is_his, is_receipt);
		}
		modelId = "10030";// 热免单测试
		rs0070 = "  网址 ： www.ems.com.cn     客服电话 ： 11183 ";// 个性区域
		rs0022 = this.getRs0022(delivery, postCompCode, 52);// 收件人地址
		rs0023 = this.getRs0023(delivery);// 收件人姓名
		rs0024 = this.getRs0024(delivery);// 收件人电话
		rs0025 = this.getRs0025(print, substr16);// 发件人地址
		String province_name = "";
		String city_name = "";
		String region_name = "";
		province_name = baseDaoSupport.queryForString(
				"select c.name from es_regions_zb c where c.code='" + delivery.getProvince_id() + "' and rownum<2");
		city_name = baseDaoSupport.queryForString(
				"select c.name from es_regions_zb c where c.code='" + delivery.getCity_id() + "' and rownum<2");
		region_name = baseDaoSupport.queryForString(
				"select c.name from es_regions_zb c where c.code='" + delivery.getRegion_id() + "' and rownum<2");
		String address = province_name + "  " + city_name + "  " + region_name;
		if (StringUtils.isNotEmpty(region_name)) {
			if (rs0022.indexOf(region_name) > 0) {
				rs0022 = rs0022.substring(rs0022.indexOf(region_name) + region_name.length()).trim();
			}
		}
		String plat_type = orderTree.getOrderExtBusiRequest().getPlat_type();
		// 码上购订单不拼接省市县
		if (plat_type.equals("10070")) {

		} else {
			rs0022 = address + "<br>" + rs0022;
		}
		String rs0092 = rs0022;
		rs0026 = this.getRs0026(print);// 发件人姓名
		rs0027 = this.getRs0027(print);// 发件人电话
		rs0054 = this.getRs0054(print, 40);// 声明物品
		rs0098 = region_name;
		String rs0094 = orderTree.getOrderExtBusiRequest().getOut_tid();
		String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME);
		String payment_name = orderTree.getOrderBusiRequest().getPayment_name();
		String pay_type = orderTree.getOrderExtBusiRequest().getPay_type();
		double payMoney = orderTree.getOrderBusiRequest().getPaymoney();

		String iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		if (!StringUtils.isEmpty(iccid)) {
			iccid = iccid.substring(iccid.length() - 8, iccid.length() - 1);// iccid取后6位
		}

		rs0050 = this.getRs0050(print, postCompCode, ship_name);// 投递要求
		Map<String, String> modeMap = iOrderInvoiceManager.getModelParams(modelId);
		htmlCode = "<div id='page1'  style='width:360px;height:540px;'>";
		if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_receipt)) {// 回单
			htmlCode = htmlCode
					+ "<div style='position:relative; width:375px; height:555px; margin:0 auto; font-family:\"黑体\"; color:#000;'>";
			htmlCode = htmlCode + "<div style='" + modeMap.get("10013").toString() + " font-size:8pt;'>付款方式："
					+ payment_name + "</div>";// 支付方式
			htmlCode = htmlCode + "<div style='" + modeMap.get("10022").toString() + " font-size:10pt;'>" + rs0022
					+ "</div>";// 收件人地址
			htmlCode = htmlCode + "<div style='" + modeMap.get("10023").toString() + " font-size:10pt;'>收件：" + rs0023
					+ "</div>";// 收件人姓名
			htmlCode = htmlCode + "<div style='" + modeMap.get("10024").toString() + " font-size:10pt;'>"
					+ getRs0024(delivery, 30) + "</div>";// 收件人电话
			htmlCode = htmlCode + "<div style='" + modeMap.get("10025").toString() + " font-size:8pt;'>" + rs0025
					+ "</div>";// 发件人地址
			htmlCode = htmlCode + "<div style='" + modeMap.get("10026").toString() + " font-size:8pt;'>寄件：" + rs0026
					+ "</div>";// 发件人姓名
			htmlCode = htmlCode + "<div style='" + modeMap.get("10027").toString() + " font-size:8pt;'>" + rs0027
					+ "</div>";// 发件人电话
			htmlCode = htmlCode + "<div style='" + modeMap.get("10054").toString() + " font-size:8pt;'>配货信息: " + rs0054
					+ "</div>";// 声明物品
			htmlCode = htmlCode + "<div style='" + modeMap.get("10029").toString()
					+ " font-size:8pt;'>备注: 收货前请确认包装是否完好,<br> 有无破损.如有问题，请拒绝签收</div>";// 备注
			htmlCode = htmlCode + "<div style='" + modeMap.get("10035").toString() + " font-size:8pt;'>计费重量(KG):"
					+ emptyString(print.getWeight()) + "</div>";// 计费重量
			htmlCode = htmlCode + "<div style='" + modeMap.get("10041").toString() + " font-size:8pt;'>保价金额(元):"
					+ emptyString(print.getInsur_value()) + "</div>";// 保价金额
			htmlCode = htmlCode + "<div style='" + modeMap.get("10062").toString() + " font-size:8pt;'>件数: "
					+ emptyString(print.getPost_num()) + "</div>";// 件数
			htmlCode = htmlCode + "<div style='" + modeMap.get("10070").toString()
					+ " font-size:8pt;'>  网址：www.ems.com.cn  客服电话：11183</div>";// 个性化提示区

			htmlCode = htmlCode + "<div style='" + modeMap.get("10111").toString() + " font-size:8pt;'>标准快递</div>";// 业务副标题
			htmlCode = htmlCode + "<div style='" + modeMap.get("10083").toString() + " font-size:18pt;'>实物返单</div>";// 业务类型
			htmlCode = htmlCode + "<div style='" + modeMap.get("10087").toString() + " font-size:8pt;'>" + rs0027
					+ "</div>";// 发件人电话二
			htmlCode = htmlCode + "<div style='" + modeMap.get("10088").toString() + " font-size:8pt;'>" + rs0025
					+ "</div>";// 发件人地址二
			htmlCode = htmlCode + "<div style='" + modeMap.get("10089").toString() + " font-size:8pt;'>寄件：" + rs0026
					+ "</div>";// 发件人姓名二
			htmlCode = htmlCode + "<div style='" + modeMap.get("10091").toString() + " font-size:8pt;'>"
					+ getRs0024(delivery, 30) + "</div>";// 收件人电话二
			htmlCode = htmlCode + "<div style='" + modeMap.get("10092").toString() + " font-size:8pt;'>" + rs0022
					+ "</div>";// 收件人地址二
			htmlCode = htmlCode + "<div style='" + modeMap.get("10093").toString() + " font-size:8pt;'>收件：" + rs0023
					+ "</div>";// 收件人姓名二
			htmlCode = htmlCode + "<div style='" + modeMap.get("10112").toString() + " font-size:8pt;'>已验视</div>";// 已验视
			htmlCode = htmlCode + "<div style='" + modeMap.get("10113").toString() + " font-size:8pt;'>滨江03</div>";// 滨江03

			htmlCode = htmlCode + "<div style='" + modeMap.get("10095").toString() + " font-size:8pt;'></div>";// 客户LOGO
			htmlCode = htmlCode + "<div style='" + modeMap.get("10096").toString()
					+ "'  id=\"codeimg1\" ><div id=\"img1\"></div><script >$(document).ready(function(){$(\"#img1\").barcode('"
					+ print.getPost_no() + "', 'code128',{barWidth:1, barHeight:30,showHRI:false});});</script>"
					+ print.getPost_no() + "</div>";// 条形码一
			htmlCode = htmlCode + "<div style='" + modeMap.get("10097").toString()
					+ "'  id=\"codeimg2\"><div id=\"img2\"></div><script >$(document).ready(function(){$(\"#img2\").barcode('"
					+ print.getPost_no() + "', 'code128',{barWidth:1, barHeight:30,showHRI:false});});</script>"
					+ print.getPost_no() + "</div>";// 条形码二
			/*
			 * htmlCode = htmlCode + "<div style='"+modeMap.get("10096").toString()
			 * +" ' class='barcode3' id='codeImg1'><script>createBarcodeCom('codeImg1','"
			 * +print.getPost_no()+"','C');</script></div>";//条形码一 htmlCode = htmlCode +
			 * "<div style='"+modeMap.get("10097").toString()
			 * +" ' class='barcode3' id='codeImg2'><script>createBarcodeCom('codeImg2','"
			 * +print.getPost_no()+"','C');</script></div>";
			 */// 条形码二
			htmlCode = htmlCode + "<div style='" + modeMap.get("10098").toString() + "font-size:32pt;'>"
					+ emptyString(rs0098) + "</div>";// 目的地
			htmlCode = htmlCode + "<div style='" + modeMap.get("10104").toString() + "font-size:8pt;'>订单号</div>";// 订单号
			htmlCode = htmlCode + "<div style='" + modeMap.get("10081").toString()
					+ "'  id=\"codeimg3\"><div id=\"img3\"></div><script >$(document).ready(function(){$(\"#img3\").barcode('"
					+ rs0094 + "', 'code128',{barWidth:1, barHeight:25,showHRI:false});});</script>" + rs0094
					+ "</div>";
			/*
			 * htmlCode = htmlCode + "<div style='"+modeMap.get("10081").toString()
			 * +" ' class='barcode2' id='codeImg3'><script>createBarcodeCom('codeImg3','"
			 * +rs0094+"','A');</script></div>";
			 */// 外部订单号
			htmlCode = htmlCode + "<div style='" + modeMap.get("10106").toString() + " font-size:8pt;'><img src='" + cxt
					+ "/ecs_ord/image/invoice/qrcode_for_ems.jpg' width='50%'/></div>";// 二维码

			htmlCode = htmlCode + "<div style='" + modeMap.get("10107").toString() + " font-size:8pt;'>重量(KG)"
					+ emptyString(print.getWeight()) + "</div>";// 计费重量二
			htmlCode = htmlCode + "<div style='" + modeMap.get("10108").toString() + " font-size:8pt;'>收件人\\代收人</div>";// 收件人签名
			htmlCode = htmlCode + "<div style='" + modeMap.get("10109").toString()
					+ " font-size:8pt;'>签收时间:&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;&nbsp;时 </div>";// 签收时间
			htmlCode = htmlCode + "<div style='" + modeMap.get("10110").toString()
					+ " font-size:7pt;'>快递送达收货人地址,经收件人或收件人允许的代收人签字,视为送达.</div>";// 签收说明
			htmlCode = htmlCode + "<div style='" + modeMap.get("10114").toString() + " font-size:8pt;'>ICCID:" + iccid
					+ "</div>";// iccid
			htmlCode = htmlCode + "</div>";
		} else {
			if (EcsOrderConsts.PAY_TYPE_HDFK.equals(pay_type)) {// 货到付款
				htmlCode = htmlCode
						+ "<div style='position:relative; width:375px; height:555px; margin:0 auto; font-family:\"黑体\"; color:#000;'>";
				htmlCode = htmlCode + "<div style='" + modeMap.get("10022").toString() + " font-size:10pt;'>" + rs0022
						+ "</div>";// 收件人地址
				htmlCode = htmlCode + "<div style='" + modeMap.get("10023").toString() + " font-size:10pt;'>收件："
						+ rs0023 + "</div>";// 收件人姓名
				htmlCode = htmlCode + "<div style='" + modeMap.get("10024").toString() + " font-size:10pt;'>"
						+ getRs0024(delivery, 30) + "</div>";// 收件人电话
				htmlCode = htmlCode + "<div style='" + modeMap.get("10025").toString() + " font-size:8pt;'>" + rs0025
						+ "</div>";// 发件人地址
				htmlCode = htmlCode + "<div style='" + modeMap.get("10026").toString() + " font-size:8pt;'>寄件：" + rs0026
						+ "</div>";// 发件人姓名
				htmlCode = htmlCode + "<div style='" + modeMap.get("10027").toString() + " font-size:8pt;'>" + rs0027
						+ "</div>";// 发件人电话
				htmlCode = htmlCode + "<div style='" + modeMap.get("10054").toString() + " font-size:8pt;'>配货信息: "
						+ rs0054 + "</div>";// 声明物品
				htmlCode = htmlCode + "<div style='" + modeMap.get("10029").toString()
						+ " font-size:8pt;'>备注: 收货前请确认包装是否完好, <br>有无破损.如有问题，请拒绝签收</div>";// 备注
				htmlCode = htmlCode + "<div style='" + modeMap.get("10013").toString() + " font-size:8pt;'>计费重量(KG):"
						+ emptyString(print.getWeight()) + "</div>";// 计费重量
				htmlCode = htmlCode + "<div style='" + modeMap.get("10035").toString() + " font-size:8pt;'>保价金额(元):"
						+ emptyString(print.getInsur_value()) + "</div>";// 保价金额
				htmlCode = htmlCode + "<div style='" + modeMap.get("10041").toString() + " font-size:10pt;'>应收货款：￥("
						+ payMoney + ")</div>";// 邮费
				htmlCode = htmlCode + "<div style='" + modeMap.get("10062").toString() + " font-size:8pt;'>件数: "
						+ emptyString(print.getPost_num()) + "</div>";// 件数
				htmlCode = htmlCode + "<div style='" + modeMap.get("10070").toString()
						+ " font-size:8pt;'>  网址：www.ems.com.cn  客服电话：11183</div>";// 个性化提示区
				htmlCode = htmlCode + "<div style='" + modeMap.get("10111").toString()
						+ " font-size:8pt;'>国内标准快递</div>";// 业务副标题
				htmlCode = htmlCode + "<div style='" + modeMap.get("10083").toString() + " font-size:18pt;'>代收货款</div>";// 业务类型
				htmlCode = htmlCode + "<div style='" + modeMap.get("10087").toString() + " font-size:8pt;'>" + rs0027
						+ "</div>";// 发件人电话二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10088").toString() + " font-size:8pt;'>" + rs0025
						+ "</div>";// 发件人地址二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10089").toString() + " font-size:8pt;'>寄件：" + rs0026
						+ "</div>";// 发件人姓名二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10091").toString() + " font-size:8pt;'>"
						+ getRs0024(delivery, 30) + "</div>";// 收件人电话二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10092").toString() + " font-size:8pt;'>" + rs0022
						+ "</div>";// 收件人地址二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10093").toString() + " font-size:8pt;'>收件：" + rs0023
						+ "</div>";// 收件人姓名二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10112").toString() + " font-size:8pt;'>已验视</div>";// 已验视
				htmlCode = htmlCode + "<div style='" + modeMap.get("10113").toString() + " font-size:8pt;'>滨江03</div>";// 滨江03

				htmlCode = htmlCode + "<div style='" + modeMap.get("10095").toString() + " font-size:8pt;'></div>";// 客户LOGO
				htmlCode = htmlCode + "<div style='" + modeMap.get("10096").toString()
						+ "'  id=\"codeimg1\" ><div id=\"img1\"></div><script >$(document).ready(function(){$(\"#img1\").barcode('"
						+ print.getPost_no() + "', 'code128',{barWidth:1, barHeight:30,showHRI:false});});</script>"
						+ print.getPost_no() + "</div>";// 条形码一
				htmlCode = htmlCode + "<div style='" + modeMap.get("10097").toString()
						+ "'  id=\"codeimg2\"><div id=\"img2\"></div><script >$(document).ready(function(){$(\"#img2\").barcode('"
						+ print.getPost_no() + "', 'code128',{barWidth:1, barHeight:30,showHRI:false});});</script>"
						+ print.getPost_no() + "</div>";// 条形码二
				/*
				 * htmlCode = htmlCode + "<div style='"+modeMap.get("10096").toString()
				 * +" ' class='barcode3' id='codeImg1'><script>createBarcodeCom('codeImg1','"
				 * +print.getPost_no()+"','C');</script></div>";//条形码一 htmlCode = htmlCode +
				 * "<div style='"+modeMap.get("10097").toString()
				 * +" ' class='barcode3' id='codeImg2'><script>createBarcodeCom('codeImg2','"
				 * +print.getPost_no()+"','C');</script></div>";
				 */// 条形码二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10098").toString() + "font-size:32pt;'>"
						+ emptyString(rs0098) + "</div>";// 目的地
				htmlCode = htmlCode + "<div style='" + modeMap.get("10104").toString() + "font-size:8pt;'>订单号</div>";// 订单号
				htmlCode = htmlCode + "<div style='" + modeMap.get("10081").toString()
						+ "'  id=\"codeimg3\"><div id=\"img3\"></div><script >$(document).ready(function(){$(\"#img3\").barcode('"
						+ rs0094 + "', 'code128',{barWidth:1, barHeight:25,showHRI:false});});</script>" + rs0094
						+ "</div>";
				/*
				 * htmlCode = htmlCode + "<div style='"+modeMap.get("10081").toString()
				 * +" ' class='barcode2' id='codeImg3'><script>createBarcodeCom('codeImg3','"
				 * +rs0094+"','A');</script></div>";
				 */// 外部订单号
				htmlCode = htmlCode + "<div style='" + modeMap.get("10106").toString() + " font-size:8pt;'><img src='"
						+ cxt + "/ecs_ord/image/invoice/qrcode_for_ems.jpg' width='50%'/></div>";// 二维码

				htmlCode = htmlCode + "<div style='" + modeMap.get("10107").toString() + " font-size:8pt;'>重量(KG)"
						+ emptyString(print.getWeight()) + "</div>";// 计费重量二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10108").toString()
						+ " font-size:8pt;'>收件人\\代收人</div>";// 收件人签名
				htmlCode = htmlCode + "<div style='" + modeMap.get("10109").toString()
						+ " font-size:8pt;'>签收时间:&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;&nbsp;时 </div>";// 签收时间
				htmlCode = htmlCode + "<div style='" + modeMap.get("10110").toString()
						+ " font-size:7pt;'>快递送达收货人地址,经收件人或收件人允许的代收人签字,视为送达.</div>";// 签收说明
				htmlCode = htmlCode + "<div style='" + modeMap.get("10114").toString() + " font-size:8pt;'>ICCID:"
						+ iccid + "</div>";// iccid
				htmlCode = htmlCode + "</div>";

			} else {

				htmlCode = htmlCode
						+ "<div style='position:relative; width:375px; height:555px; margin:0 auto; font-family:\"黑体\"; color:#000;'>";
				htmlCode = htmlCode + "<div style='" + modeMap.get("10013").toString() + " font-size:8pt;'>付款方式："
						+ payment_name + "</div>";// 支付方式
				htmlCode = htmlCode + "<div style='" + modeMap.get("10022").toString() + " font-size:10pt;'>" + rs0022
						+ "</div>";// 收件人地址
				htmlCode = htmlCode + "<div style='" + modeMap.get("10023").toString() + " font-size:10pt;'>收件："
						+ rs0023 + "</div>";// 收件人姓名
				htmlCode = htmlCode + "<div style='" + modeMap.get("10024").toString() + " font-size:10pt;'>"
						+ getRs0024(delivery, 30) + "</div>";// 收件人电话
				htmlCode = htmlCode + "<div style='" + modeMap.get("10025").toString() + " font-size:8pt;'>" + rs0025
						+ "</div>";// 发件人地址
				htmlCode = htmlCode + "<div style='" + modeMap.get("10026").toString() + " font-size:8pt;'>寄件：" + rs0026
						+ "</div>";// 发件人姓名
				htmlCode = htmlCode + "<div style='" + modeMap.get("10027").toString() + " font-size:8pt;'>" + rs0027
						+ "</div>";// 发件人电话
				htmlCode = htmlCode + "<div style='" + modeMap.get("10054").toString() + " font-size:8pt;'>配货信息: "
						+ rs0054 + "</div>";// 声明物品
				htmlCode = htmlCode + "<div style='" + modeMap.get("10029").toString()
						+ " font-size:8pt;'>备注: 收货前请确认包装是否完好,<br> 有无破损.如有问题，请拒绝签收</div>";// 备注
				htmlCode = htmlCode + "<div style='" + modeMap.get("10035").toString() + " font-size:8pt;'>计费重量(KG):"
						+ emptyString(print.getWeight()) + "</div>";// 计费重量
				htmlCode = htmlCode + "<div style='" + modeMap.get("10041").toString() + " font-size:8pt;'>保价金额(元):"
						+ emptyString(print.getInsur_value()) + "</div>";// 保价金额
				htmlCode = htmlCode + "<div style='" + modeMap.get("10062").toString() + " font-size:8pt;'>件数: "
						+ emptyString(print.getPost_num()) + "</div>";// 件数
				htmlCode = htmlCode + "<div style='" + modeMap.get("10070").toString()
						+ " font-size:8pt;'>  网址：www.ems.com.cn  客服电话：11183</div>";// 个性化提示区

				htmlCode = htmlCode + "<div style='" + modeMap.get("10111").toString() + " font-size:8pt;'></div>";// 业务副标题
				htmlCode = htmlCode + "<div style='" + modeMap.get("10083").toString() + " font-size:18pt;'>标准快递</div>";// 业务类型
				htmlCode = htmlCode + "<div style='" + modeMap.get("10087").toString() + " font-size:8pt;'>" + rs0027
						+ "</div>";// 发件人电话二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10088").toString() + " font-size:8pt;'>" + rs0025
						+ "</div>";// 发件人地址二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10089").toString() + " font-size:8pt;'>寄件：" + rs0026
						+ "</div>";// 发件人姓名二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10091").toString() + " font-size:8pt;'>"
						+ getRs0024(delivery, 30) + "</div>";// 收件人电话二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10092").toString() + " font-size:8pt;'>" + rs0022
						+ "</div>";// 收件人地址二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10093").toString() + " font-size:8pt;'>收件：" + rs0023
						+ "</div>";// 收件人姓名二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10112").toString() + " font-size:8pt;'>已验视</div>";// 已验视
				htmlCode = htmlCode + "<div style='" + modeMap.get("10113").toString() + " font-size:8pt;'>滨江03</div>";// 滨江03

				htmlCode = htmlCode + "<div style='" + modeMap.get("10095").toString() + " font-size:8pt;'></div>";// 客户LOGO
				htmlCode = htmlCode + "<div style='" + modeMap.get("10096").toString()
						+ "'  id=\"codeimg1\" ><div id=\"img1\"></div><script >$(document).ready(function(){$(\"#img1\").barcode('"
						+ print.getPost_no() + "', 'code128',{barWidth:1, barHeight:30,showHRI:false});});</script>"
						+ print.getPost_no() + "</div>";// 条形码一
				htmlCode = htmlCode + "<div style='" + modeMap.get("10097").toString()
						+ "'  id=\"codeimg2\"><div id=\"img2\"></div><script >$(document).ready(function(){$(\"#img2\").barcode('"
						+ print.getPost_no() + "', 'code128',{barWidth:1, barHeight:30,showHRI:false});});</script>"
						+ print.getPost_no() + "</div>";// 条形码二
				// htmlCode = htmlCode + "<div style='"+modeMap.get("10097").toString()+" '
				// class='barcode3' id='codeImg2'><input type='button'
				// onclick=\"bar('Img1','"+print.getPost_no()+"','code39',{barWidth:2,
				// barHeight:30,showHRI:false})\" ></div>";//条形码二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10098").toString() + "font-size:32pt;'>"
						+ emptyString(rs0098) + "</div>";// 目的地
				htmlCode = htmlCode + "<div style='" + modeMap.get("10104").toString() + "font-size:8pt;'>订单号</div>";// 订单号
				htmlCode = htmlCode + "<div style='" + modeMap.get("10081").toString()
						+ "'  id=\"codeimg3\"><div id=\"img3\"></div><script >$(document).ready(function(){$(\"#img3\").barcode('"
						+ rs0094 + "', 'code128',{barWidth:1, barHeight:25,showHRI:false});});</script>" + rs0094
						+ "</div>";// 外部订单号
				htmlCode = htmlCode + "<div style='" + modeMap.get("10106").toString() + " font-size:8pt;'><img src='"
						+ cxt + "/ecs_ord/image/invoice/qrcode_for_ems.jpg' width='50%'/></div>";// 二维码

				htmlCode = htmlCode + "<div style='" + modeMap.get("10107").toString() + " font-size:8pt;'>重量(KG)"
						+ emptyString(print.getWeight()) + "</div>";// 计费重量二
				htmlCode = htmlCode + "<div style='" + modeMap.get("10108").toString()
						+ " font-size:8pt;'>收件人\\代收人</div>";// 收件人签名
				htmlCode = htmlCode + "<div style='" + modeMap.get("10109").toString()
						+ " font-size:8pt;'>签收时间:&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;&nbsp;时 </div>";// 签收时间
				htmlCode = htmlCode + "<div style='" + modeMap.get("10110").toString()
						+ " font-size:7pt;'>快递送达收货人地址,经收件人或收件人允许的代收人签字,视为送达.</div>";// 签收说明
				htmlCode = htmlCode + "<div style='" + modeMap.get("10114").toString() + " font-size:8pt;'>ICCID:"
						+ iccid + "</div>";// iccid
				htmlCode = htmlCode + "</div>";
			}
		}
		htmlCode = htmlCode + "</div>";

		htmlCode = this.getHotHeadDelivery(cxt) + htmlCode + buttonNew + tail + closeEnd;

		return htmlCode;

	}

	/**
	 * 顺丰的热敏单处理
	 * 
	 * @param order_id
	 * @param delivery_id
	 * @param delvery_print_id
	 * @param reissueIds
	 * @param postType
	 * @param wlnum
	 * @param postId
	 * @param cxt
	 * @param order_is_his
	 * @param is_receipt
	 * @return
	 */
	public String doHotFreeModelSF(String order_id, String delivery_id, String delvery_print_id, String reissueIds,
			String postType, String wlnum, String postId, String cxt, String order_is_his, String is_receipt) {
		String htmlCode = "";

		String rs0018 = "";
		String rs0019 = "";
		String rs0020 = "";
		String rs0021 = "";
		String rs0022 = "";
		String rs0023 = "";
		String rs0024 = "";
		String rs0025 = "";
		String rs0026 = "";
		String rs0027 = "";
		String rs0028 = "";
		String rs0029 = "";
		String rs0030 = "";
		String rs0031 = "";
		String rs0032 = "";
		String rs0033 = "";
		String rs0034 = "";
		String rs0035 = "";
		String rs0036 = "";
		String rs0037 = "";
		String rs0038 = "";
		String rs0039 = "";
		String rs0040 = "";
		String rs0041 = "";
		String rs0042 = "";
		String rs0043 = "";
		String rs0044 = "";
		String rs0045 = "";
		String rs0046 = "";
		String rs0047 = "";
		String rs0048 = "";
		String rs0049 = "";
		String rs0050 = "";
		String rs0051 = "";
		String rs0052 = "";
		String rs0053 = "";
		String rs0054 = "";
		String rs0055 = "";
		String rs0056 = "";
		String rs0057 = "";
		String rs0058 = "";
		String rs0059 = "";
		String rs0060 = "";
		String rs0061 = "";
		String rs0062 = "";
		String rs0063 = "";
		String rs0064 = "";
		String rs0065 = "";
		String rs0066 = "";
		String rs0067 = "";
		String rs0068 = "";
		String rs0069 = "";
		String rs0070 = "";
		String rs0071 = "";
		String rs0072 = "";
		String rs0073 = "";
		String rs0085 = "";
		String rs0098 = "";

		String postCompCode = "";
		String modelId = "";
		isHis = false;
		if (EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)) {// 是历史单
			isHis = true;
		}

		// 获取订单信息
		OrderTreeBusiRequest orderTree = null;
		if (isHis) {// 是历史单
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		} else {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}

		OrderDeliveryBusiRequest delivery = new OrderDeliveryBusiRequest();
		if (isHis) {// 是历史订单（），则不能从订单树获取
			// 根据delivery_id查询数据库表delivery
			List<OrderDeliveryBusiRequest> dlist = queryDelieryById(delivery_id);// 更改从his表查询
			if (dlist != null && dlist.size() > 0) {
				delivery = dlist.get(0);
				// ship_name 放在ES_ORDER表
				String ship_name = orderTree.getOrderBusiRequest().getShip_name();
				delivery.setShip_name(ship_name);
			}

		} else {
			// 获取本次物流信息 es_delivery 根据页面传送过来的delivery_id从列表中过滤
			List<OrderDeliveryBusiRequest> deList = orderTree.getOrderDeliveryBusiRequests();
			for (OrderDeliveryBusiRequest orderDelivery : deList) {
				if (orderDelivery.getDelivery_id().equals(delivery_id)) {
					delivery = orderDelivery;
					// ship_name 放在ES_ORDER表
					String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
							AttrConsts.SHIP_NAME);
					delivery.setShip_name(ship_name);
				}
			}
		}

		List<OrderDeliveryItemBusiRequest> deliveryItemslist = new ArrayList<OrderDeliveryItemBusiRequest>();
		if (postType.equals(EcsOrderConsts.LOGIS_SUPPLY)) {// 补寄才管补寄品
			if (isHis) {
				deliveryItemslist = iOrderSupplyManager.queryDeliveryItemsByDeId(delivery_id, true);
			} else {
				DeliveryItemsQueryByDeIdReq ItemsReq = new DeliveryItemsQueryByDeIdReq();
				ItemsReq.setDelivery_id(delivery_id);
				DeliveryItemsQueryByDeIdResp ItemsResp = orderServices.queryDeliveryItemsByDeId(ItemsReq);
				List<DeliveryItem> deliveryItemslistAll = ItemsResp.getDeliveryItems();
				for (DeliveryItem deliveryItem : deliveryItemslistAll) {
					OrderDeliveryItemBusiRequest items = new OrderDeliveryItemBusiRequest();
					items.setBuy_num(deliveryItem.getNum());
					items.setName(deliveryItem.getName());
					deliveryItemslist.add(items);
				}
			}
		}

		// 获取es_delivery_prints表的数据 根据页面传送过来 的主键delivery_prints_id查询
		DeliveryPrints print = deliveryPrintsManager.get(delvery_print_id);
		if (print == null) {
			print = new DeliveryPrints();
		}

		if (wlnum == null || wlnum.equals("")) {
			// 自动生成的物流单号
			wlnum = this.getUniqueRandomNum();
		}

		// 物流公司编码
		postCompCode = getComCodeByComId(postId);

		String rs0092 = delivery.getShip_addr();// 收件人地址
		rs0023 = this.getRs0023(delivery);// 收件人姓名
		rs0024 = this.getRs0024SF(delivery);// 收件人电话
		rs0060 = "";// 收件人单位
		rs0025 = print.getPost_address();// 发件人地址

		rs0026 = this.getRs0026(print);// 发件人姓名
		rs0027 = print.getPost_tel();// 发件人电话

		rs0054 = this.getRs0054(print, 40);// 声明物品
		rs0036 = this.getRs0036(print);// 原寄地

//		rs0069=this.getRs0069(print);//大写代收货款
		rs0053 = this.getRs0053(print);// 发件公司
//		rs0056=this.getRs0056(print);//是否签回单 
		rs0057 = this.getRs0057(print);// 发件人邮编

		rs0038 = this.getRs0038(print);// 物流公司收件人
		rs0069 = this.getRs0069(print);// 打印时间
		String rs0094 = orderTree.getOrderExtBusiRequest().getOut_tid();
		String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME);
		rs0050 = this.getRs0050(print, postCompCode, ship_name);// 投递要求
		Map<String, String> modeMap = iOrderInvoiceManager.getHotFreeModelParams();
		if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_receipt)) {// 返单

			htmlCode = " <DIV id='page1'>" + "	<DIV class='page'>" + "	<DIV class='m1 b'>"
					+ "	<P><SPAN class='s1'>POD</SPAN><SPAN class='s2'></SPAN></P>" + "	</DIV>" + "	<DIV class='m2 b'>"
					+ "	<DIV class='left'>"
					+ "	<div class='barcode4' id='codeImg1'><script>createBarcodeCom('codeImg1','"
					+ print.getReceipt_no() + "','C');</script></div>" + "	</DIV>" + "	<DIV class='right'>"
					+ "	<DIV class='r1 b'>" + "	<P>签单返还</P></DIV>" + "	<DIV class='r2'>" + "	<P class='p1'></P>"
					+ "	<P class='p2'></P>" + "	<P class='p3'></P></DIV></DIV></DIV>" + "	<DIV class='m3 b'>"
					+ "	<DIV class='left'><P>目的地</P></DIV>" + "	<DIV class='right'><P>" + print.getOrigin()
					+ "</P></DIV>" + "</DIV>" + "	<DIV class='m4 b'>" + "	<DIV class='left'><P>收件人</P></DIV>"
					+ "	<DIV class='right'>" + "	<P>" + rs0026 + " " + rs0027 + "</P>" + "	<P>" + rs0025
					+ "</P></DIV></DIV>" + "	<DIV class='m5 b'>" + "	<DIV class='left'><P>寄件人</P></DIV>"
					+ "	<DIV class='right-1'>" + "	<P>" + rs0023 + " " + rs0024 + "</P>" + "	<P>" + rs0092
					+ "</P></DIV>" + "	<DIV class='right-2'>" + "	<P class='p1'></P>"
					+ "	<P class='p2'></P></DIV></DIV>" + "	<DIV class='m6 cl'>" + "	<DIV class='left'>"
					+ "	<DIV class='l1 b'>" + "	<UL>" + "	  <LI>付款方式：到付</LI>" + "	  <LI>月结账号："
					+ emptyString(print.getMonthly_payment()) + "</LI>" + "	  <LI>第三方地区：</LI>"
					+ "	  <LI>实际重量：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KG</LI>" + "	</UL>" + "	<UL>"
					+ "	  <LI>计费重量：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KG</LI>"
					+ "	  <LI>声明价值：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + emptyString(print.getInsur_value())
					+ "元</LI>" + "	  <LI>保价费用：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</LI>" + "	  <LI>定时派送：</LI>"
					+ "	</UL>" + "	<UL>" + "	  <LI>包装费用：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</LI>"
					+ "	  <LI>运费：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</LI>"
					+ "	  <LI>费用合计：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</LI>" + "	</UL>" + "	<P>转寄协议客户</P>"
					+ "</DIV>" + "	<DIV class='l2'>" + "	<DIV class='l2-a'>" + "	<P>托寄物</P></DIV>"
					+ "	<DIV class='l2-b'><P>签单返还</P></DIV>" + "	<DIV class='l2-c'>" + "	<UL>" + "	  <LI>收件员：</LI>"
					+ "	  <LI>收件日期：&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</LI>" + "	  <LI>派件员：</LI>"
					+ "</UL>" + "</DIV></DIV></DIV>" + "	<DIV class='right'>" + "	<P>签名：</P>"
					+ "	<P class='p2'>月<SPAN>日</SPAN></P></DIV></DIV>" + "	<DIV class='item'>" + "	<DIV class='m7 cl'>"
					+ "	<DIV class='left'>" + "		<IMG class='img1' src='" + cxt + "/ecs_ord/sf/logo.jpg'>"
					+ "		<IMG src='" + cxt + "/ecs_ord/sf/tel.jpg'>" + "	</DIV>" + "	<DIV class='right'>"
					+ "		<div class='barcode4' id='codeImg2'><script>createBarcodeCom('codeImg2','"
					+ print.getReceipt_no() + "','C');</script></div>" + "	</DIV>" + "	</DIV>" + "	<DIV class='m4'>"
					+ "	<DIV class='left'>" + "	<P>寄件人</P></DIV>" + "	<DIV class='right'>" + "	<P>" + rs0023 + " "
					+ rs0024 + "</P>" + "	<P>" + rs0092 + "</P></DIV></DIV>" + "<div class='b'></div>"
					+ "	<DIV class='m4'>" + "	<DIV class='left'>" + "	<P>收件人</P></DIV>" + "	<DIV class='right'>"
					+ "	<P>" + rs0026 + " " + rs0027 + "</P>" + "	<P>" + rs0025 + "</P></DIV></DIV>"
					+ "	<DIV class='m10'></DIV>" + "</DIV>" + "</DIV>" + "	</DIV>";
		} else {
			boolean typeFlag = false;
			String paymoney = "";
			if (StringUtil.equals(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_TYPE),
					EcsOrderConsts.PAY_TYPE_HDFK)) {// 货到付款传增值服务
				paymoney = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_REAL_FEE);// 实收金额
				// 0元货到付款订单不同步代收款信息给顺丰
				if (!StringUtils.equals(paymoney, "0")) {
					typeFlag = true;
				}
			}
			if (typeFlag) {// 代收货款
				htmlCode = " <DIV id='page1'>" + "	<DIV class='page'>" + "	<DIV class='m1 b'>"
						+ "	<P><SPAN class='s1'>COD</SPAN><SPAN class='s2'></SPAN></P>" + "	</DIV>"
						+ "	<DIV class='m2 b'>" + "	<DIV class='left'>"
						+ "	<div class='barcode4' id='codeImg1'><script>createBarcodeCom('codeImg1','"
						+ print.getPost_no() + "','C');</script></div>" + "	</DIV>" + "	<DIV class='right'>"
						+ "	<DIV class='r1 b'>" + "	<P>顺丰次日</P></DIV>" + "	<DIV class='r2'>"
						+ "	<P class='p1'>代收货款</P>" + "	<P class='p2'>卡号:" + emptyString(print.getMonthly_payment())
						+ "</P>" + "	<P class='p3'>金额:" + paymoney + "</P></DIV></DIV></DIV>";
			} else {
				htmlCode = " <DIV id='page1'>" + "	<DIV class='page'>" + "	<DIV class='m1 b'>"
						+ "	<P><SPAN class='s1'></SPAN><SPAN class='s2'></SPAN></P>" + "	</DIV>"
						+ "	<DIV class='m2 b'>" + "	<DIV class='left'>"
						+ "	<div class='barcode4' id='codeImg1'><script>createBarcodeCom('codeImg1','"
						+ print.getPost_no() + "','C');</script></div>" + "	</DIV>" + "	<DIV class='right'>"
						+ "	<DIV class='r1 b'>" + "	<P>顺丰次日</P></DIV>" + "	<DIV class='r2'>" + "	<P class='p1'></P>"
						+ "	<P class='p2'></P>" + "	<P class='p3'></P></DIV></DIV></DIV>";
			}

			htmlCode = htmlCode + "	<DIV class='m3 b'>" + "	<DIV class='left'><P>目的地</P></DIV>"
					+ "	<DIV class='right'><P>" + print.getDestcode() + "</P></DIV>" + "</DIV>" + "	<DIV class='m4 b'>"
					+ "	<DIV class='left'><P>收件人</P></DIV>" + "	<DIV class='right'>" + "	<P>" + rs0023 + " " + rs0024
					+ "</P>" + "	<P>" + rs0092 + "</P></DIV></DIV>" + "	<DIV class='m5 b'>"
					+ "	<DIV class='left'><P>寄件人</P></DIV>" + "	<DIV class='right-1'>" + "	<P>" + rs0026 + " " + rs0027
					+ "</P>" + "	<P>" + rs0025 + "</P></DIV>" + "	<DIV class='right-2'>"
					+ "	<P class='p1'>定时派送</P>" + "	<P class='p2'>自寄 自取</P></DIV></DIV>" + "	<DIV class='m6 cl'>"
					+ "	<DIV class='left'>" + "	<DIV class='l1 b'>" + "	<UL>" + "	  <LI>付款方式：寄付月结</LI>"
					+ "	  <LI>月结账号：" + emptyString(print.getMonthly_payment()) + "</LI>" + "	  <LI>第三方地区：</LI>"
					+ "	  <LI>实际重量：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KG</LI>" + "	</UL>" + "	<UL>"
					+ "	  <LI>计费重量：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;KG</LI>"
					+ "	  <LI>声明价值：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + emptyString(print.getInsur_value())
					+ "元</LI>" + "	  <LI>保价费用：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</LI>" + "	  <LI>定时派送：</LI>"
					+ "	</UL>" + "	<UL>" + "	  <LI>包装费用：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</LI>"
					+ "	  <LI>运费：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</LI>"
					+ "	  <LI>费用合计：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元</LI>" + "	</UL>" + "	<P>转寄协议客户</P>"
					+ "</DIV>" + "	<DIV class='l2'>" + "	<DIV class='l2-a'>" + "	<P>托寄物</P></DIV>"
					+ "	<DIV class='l2-b'></DIV>" + "	<DIV class='l2-c'>" + "	<UL>" + "	  <LI>收件员：</LI>"
					+ "	  <LI>收件日期：&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</LI>" + "	  <LI>派件员：</LI>"
					+ "</UL>" + "</DIV></DIV></DIV>" + "	<DIV class='right'>" + "	<P>签名：</P>"
					+ "	<P class='p2'>月<SPAN>日</SPAN></P></DIV></DIV>" + "	<DIV class='item '>"
					+ "	<DIV class='m7 cl'>" + "	<DIV class='left'>" + "		<IMG class='img1' src='" + cxt
					+ "/ecs_ord/sf/logo.jpg'>" + "		<IMG src='" + cxt + "/ecs_ord/sf/tel.jpg'>" + "	</DIV>"
					+ "	<DIV class='right'>"
					+ "		<div class='barcode4' id='codeImg2'><script>createBarcodeCom('codeImg2','"
					+ print.getPost_no() + "','C');</script></div>" + "	</DIV>" + "	</DIV>" + "	<DIV class='m4 '>"
					+ "	<DIV class='left'>" + "	<P>寄件人</P></DIV>" + "	<DIV class='right'>" + "	<P>" + rs0026 + " "
					+ rs0027 + "</P>" + "	<P>" + rs0025 + "</P></DIV></DIV>" + "<div class='b'></div>"
					+ "	<DIV class='m4 '>" + "	<DIV class='left'>" + "	<P>收件人</P></DIV>" + "	<DIV class='right'>"
					+ "	<P>" + rs0023 + " " + rs0024 + "</P>" + "	<P>" + rs0092 + "</P></DIV></DIV>"
					+ "	<DIV class='m10'></DIV>" + "</DIV>" + "</DIV>" + "	</DIV>";
		}

		htmlCode = this.getHotHeadSF(cxt) + htmlCode + buttonNew + tail + closeEnd;

		return htmlCode;

	}

	/* 使用新的页面 */
	public String doHotFreeModelSFNew(String order_id, String delivery_id, String delvery_print_id, String reissueIds,
			String postType, String wlnum, String postId, String cxt, String order_is_his, String is_receipt) {
		String htmlCode = "";

		String rs0018 = "";
		String rs0019 = "";
		String rs0020 = "";
		String rs0021 = "";
		String rs0022 = "";
		String rs0023 = "";
		String rs0024 = "";
		String rs0025 = "";
		String rs0026 = "";
		String rs0027 = "";
		String rs0028 = "";
		String rs0029 = "";
		String rs0030 = "";
		String rs0031 = "";
		String rs0032 = "";
		String rs0033 = "";
		String rs0034 = "";
		String rs0035 = "";
		String rs0036 = "";
		String rs0037 = "";
		String rs0038 = "";
		String rs0039 = "";
		String rs0040 = "";
		String rs0041 = "";
		String rs0042 = "";
		String rs0043 = "";
		String rs0044 = "";
		String rs0045 = "";
		String rs0046 = "";
		String rs0047 = "";
		String rs0048 = "";
		String rs0049 = "";
		String rs0050 = "";
		String rs0051 = "";
		String rs0052 = "";
		String rs0053 = "";
		String rs0054 = "";
		String rs0055 = "";
		String rs0056 = "";
		String rs0057 = "";
		String rs0058 = "";
		String rs0059 = "";
		String rs0060 = "";
		String rs0061 = "";
		String rs0062 = "";
		String rs0063 = "";
		String rs0064 = "";
		String rs0065 = "";
		String rs0066 = "";
		String rs0067 = "";
		String rs0068 = "";
		String rs0069 = "";
		String rs0070 = "";
		String rs0071 = "";
		String rs0072 = "";
		String rs0073 = "";
		String rs0085 = "";
		String rs0098 = "";

		String postCompCode = "";
		String modelId = "";
		isHis = false;
		if (EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)) {// 是历史单
			isHis = true;
		}

		// 获取订单信息
		OrderTreeBusiRequest orderTree = null;
		if (isHis) {// 是历史单
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		} else {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}

		OrderDeliveryBusiRequest delivery = new OrderDeliveryBusiRequest();
		if (isHis) {// 是历史订单（），则不能从订单树获取
			// 根据delivery_id查询数据库表delivery
			List<OrderDeliveryBusiRequest> dlist = queryDelieryById(delivery_id);// 更改从his表查询
			if (dlist != null && dlist.size() > 0) {
				delivery = dlist.get(0);
				// ship_name 放在ES_ORDER表
				String ship_name = orderTree.getOrderBusiRequest().getShip_name();
				delivery.setShip_name(ship_name);
			}

		} else {
			// 获取本次物流信息 es_delivery 根据页面传送过来的delivery_id从列表中过滤
			List<OrderDeliveryBusiRequest> deList = orderTree.getOrderDeliveryBusiRequests();
			for (OrderDeliveryBusiRequest orderDelivery : deList) {
				if (orderDelivery.getDelivery_id().equals(delivery_id)) {
					delivery = orderDelivery;
					// ship_name 放在ES_ORDER表
					String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
							AttrConsts.SHIP_NAME);
					delivery.setShip_name(ship_name);
				}
			}
		}

		List<OrderDeliveryItemBusiRequest> deliveryItemslist = new ArrayList<OrderDeliveryItemBusiRequest>();
		if (postType.equals(EcsOrderConsts.LOGIS_SUPPLY)) {// 补寄才管补寄品
			if (isHis) {
				deliveryItemslist = iOrderSupplyManager.queryDeliveryItemsByDeId(delivery_id, true);
			} else {
				DeliveryItemsQueryByDeIdReq ItemsReq = new DeliveryItemsQueryByDeIdReq();
				ItemsReq.setDelivery_id(delivery_id);
				DeliveryItemsQueryByDeIdResp ItemsResp = orderServices.queryDeliveryItemsByDeId(ItemsReq);
				List<DeliveryItem> deliveryItemslistAll = ItemsResp.getDeliveryItems();
				for (DeliveryItem deliveryItem : deliveryItemslistAll) {
					OrderDeliveryItemBusiRequest items = new OrderDeliveryItemBusiRequest();
					items.setBuy_num(deliveryItem.getNum());
					items.setName(deliveryItem.getName());
					deliveryItemslist.add(items);
				}
			}
		}

		// 获取es_delivery_prints表的数据 根据页面传送过来 的主键delivery_prints_id查询
		DeliveryPrints print = deliveryPrintsManager.get(delvery_print_id);
		if (print == null) {
			print = new DeliveryPrints();
		}

		if (wlnum == null || wlnum.equals("")) {
			// 自动生成的物流单号
			wlnum = this.getUniqueRandomNum();
		}

		// 物流公司编码
		postCompCode = getComCodeByComId(postId);

		String rs0092 = delivery.getShip_addr();// 收件人地址
		rs0023 = this.getRs0023(delivery);// 收件人姓名
		rs0024 = this.getRs0024SF(delivery);// 收件人电话
		rs0060 = "";// 收件人单位
		rs0025 = print.getPost_address();// 发件人地址

		rs0026 = this.getRs0026(print);// 发件人姓名
		rs0027 = print.getPost_tel();// 发件人电话

		rs0054 = this.getRs0054(print, 40);// 声明物品
		rs0036 = this.getRs0036(print);// 原寄地

//		rs0069=this.getRs0069(print);//大写代收货款
		rs0053 = this.getRs0053(print);// 发件公司
//		rs0056=this.getRs0056(print);//是否签回单 
		rs0057 = this.getRs0057(print);// 发件人邮编

		rs0038 = this.getRs0038(print);// 物流公司收件人
		rs0069 = this.getRs0069(print);// 打印时间
		String rs0094 = orderTree.getOrderExtBusiRequest().getOut_tid();
		String ship_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME);
		rs0050 = this.getRs0050(print, postCompCode, ship_name);// 投递要求

		String iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		if (!StringUtils.isEmpty(iccid)) {
			iccid = iccid.substring(iccid.length() - 8, iccid.length() - 1);// iccid取后6位
		}

		Map<String, String> modeMap = iOrderInvoiceManager.getHotFreeModelParams();
		StringBuffer sb = new StringBuffer();
		if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_receipt)) {// 返单
			sb.append("<DIV id='page1'>");
			sb.append("<div class='page'>");
			sb.append("<div class='m1 b'>");
			sb.append("<div class='logo'></div>");
			sb.append("<div class='em-txt'></div>");
			sb.append("<div class'tell'></div>");
			sb.append("</div>");
			sb.append("<div class='m2 b'>");
			sb.append("<div class='left'>");
			sb.append("<div class='barcode4' id='codeImg1'><script>createBarcodeCom('codeImg1','"
					+ print.getReceipt_no() + "','C');</script>");
			sb.append("<div align='left' style='font-size:11px'>遇收件电话异常,请按收件地址派送3次</div></div>");
			sb.append("</div>");
			sb.append("<div class='right'>");
			sb.append("<div class='r1 b'>签单返还</div>");
			sb.append("<div class='r2'>");
			sb.append("<p class='p1'></p>");
			sb.append("<p class='p2'></p>");
			sb.append("<p class='p3'></p>");
			sb.append("</div></div></div>");

			sb.append("<div class='m3 b'>");
			sb.append("<div class='left'><p>目的地</p></div>");
			sb.append("<div class='right'><p>" + print.getOrigin() + "</p></div>");
			sb.append("</div>");
			sb.append("<div class='m4 b'>");
			sb.append("<div class='left'>	<p>收件人</p></div>");
			sb.append("<div class='right'>");
			sb.append("<div class='right-con'>");
			sb.append("<p>" + rs0026 + " " + rs0027 + "</p>");
			sb.append("<p>" + rs0025 + "</p>");
			sb.append("</div>	</div></div>");
			sb.append("<div class='m5 b'>");
			sb.append("<div class='left'><p>寄件人</p></div>");
			sb.append("<div class='right-1'>");
			sb.append("<div class='r-con'>");
			sb.append("<p>" + rs0023 + " " + rs0024 + "</p>");
			sb.append("<p>" + rs0092 + "</p>");
			sb.append("</div></div>");
			sb.append("<div class='right-2'>");
			sb.append("<div class='r-con'>");
			sb.append("<p class='p1'>定时派送</p>");
			sb.append("<p class='p2'>自寄 自取</p>");
			sb.append("</div></div></div>");
			sb.append("<div class='m6 b'>");
			sb.append("<div class='left'>");
			sb.append("<div class='l1 b'>");
			sb.append("<div class='info-table'>");
			sb.append("<table><tr>");
			sb.append("<td>付款方式：到付</td>");
			sb.append("<td>计费重量：&nbsp;&nbsp;&nbsp;&nbsp;KG</td>");
			sb.append("<td>包装费用：&nbsp;&nbsp;&nbsp;&nbsp;元</td></tr>");
			sb.append("<tr><td>月结账号：" + emptyString(print.getMonthly_payment()) + "</td>");
			sb.append("<td>声明价值：&nbsp;&nbsp;" + emptyString(print.getInsur_value()) + "元</td>");
			sb.append("<td>运费：&nbsp;&nbsp;&nbsp;&nbsp;元</td></tr>");
			sb.append("<tr><td>第三方地区：&nbsp;&nbsp;&nbsp;&nbsp;</td>");
			sb.append("<td>保价费用：&nbsp;&nbsp;&nbsp;&nbsp;元</td>");
			sb.append("<td>费用合计：&nbsp;&nbsp;&nbsp;&nbsp;元</td></tr>");
			sb.append("<tr><td>实际重量：&nbsp;&nbsp;&nbsp;&nbsp;KG</td>");
			sb.append("<td colspan='2'>定时派送：</td>");
			sb.append("</tr></table>");
			sb.append("</div></div>");
			sb.append("<div class='l2'>");
			sb.append("<div class='l2-a'><p>托寄物</p></div>");
			sb.append("<div class='l2-b'><p>签单返还</p></div>");
			sb.append("<div class='l2-c'>");
			sb.append("<ul><li>收件员：</li><li>收件日期：</li><li>派件员：</li>	</ul>");
			sb.append("</div></div></div>");
			sb.append("<div class='right'><p>签名：</p><p class='p2'>月    日</p></div>");
			sb.append("</div>");
			sb.append("<div class='m7 b'>");
			sb.append("<div class='left'>");
			sb.append("<div class='logo'><img src='" + cxt + "/ecs_ord/sf/logo.jpg'></div>");
			sb.append("<div class='tell'><img src='" + cxt + "/ecs_ord/sf/tel.jpg'></div>");
			sb.append("</div>");
			sb.append("<div class='right'>");
			sb.append("<div class='barcode4' id='codeImg2'><script>createBarcodeCom('codeImg2','"
					+ print.getReceipt_no() + "','C');</script></div>");
			sb.append("</div></div>");
			sb.append("<div class='m8 b'>");
			sb.append("<div class='left'><p>寄件人</p></div>");
			sb.append("<div class='right'><p>" + rs0023 + " " + rs0024 + "</p><p>" + rs0092 + "</p></div>");
			sb.append("</div>");
			sb.append("<div class='m8 b'>");
			sb.append("<div class='left'><p>收件人</p></div>");
			sb.append("<div class='right big-size'><p>" + rs0026 + " " + rs0027 + "</p><p>" + rs0025 + "</p></div>");
			sb.append("</div>");
			sb.append("<div class='m9'><p>ICCID:" + iccid + "</p></div></div>");
			sb.append("</DIV>");
		} else {
			boolean typeFlag = false;
			String paymoney = "";
			if (StringUtil.equals(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_TYPE),
					EcsOrderConsts.PAY_TYPE_HDFK)) {// 货到付款传增值服务
				paymoney = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_REAL_FEE);// 实收金额
				// 0元货到付款订单不同步代收款信息给顺丰
				if (!StringUtils.equals(paymoney, "0")) {
					typeFlag = true;
				}
			}
			if (typeFlag) {// 代收货款
				sb.append("<DIV id='page1'>");
				sb.append("<div class='page'>");
				sb.append("<div class='m1 b'>");
				sb.append("<div class='logo'></div>");
				sb.append("<div class='em-txt'>COD</div>");
				sb.append("<div class'tell'></div>");
				sb.append("</div>");
				sb.append("<div class='m2 b'>");
				sb.append("<div class='left'>");
				sb.append("<div class='barcode4' id='codeImg1'><script>createBarcodeCom('codeImg1','"
						+ print.getPost_no() + "','C');</script>");
				sb.append("<div align='left' style='font-size:11px'>遇收件电话异常,请按收件地址派送3次</div></div>");
				sb.append("</div>");
				sb.append("<div class='right'>");
				sb.append("<div class='r1 b'>顺丰次日</div>");
				sb.append("<div class='r2'>");
				sb.append("<p class='p1'>代收货款</p>");
				sb.append("<p class='p2'>卡号:" + emptyString(print.getMonthly_payment()) + "</p>");
				sb.append("<p class='p3'>金额:" + paymoney + "</p>");
				sb.append("</div></div></div>");

			} else {
				sb.append("<DIV id='page1'>");
				sb.append("<div class='page'>");
				sb.append("<div class='m1 b'>");
				sb.append("<div class='logo'></div>");
				sb.append("<div class='em-txt'>E</div>");
				sb.append("<div class'tell'></div>");
				sb.append("</div>");
				sb.append("<div class='m2 b'>");
				sb.append("<div class='left'>");
				sb.append("<div class='barcode4' id='codeImg1'><script>createBarcodeCom('codeImg1','"
						+ print.getPost_no() + "','C');</script>");
				sb.append("<div align='left' style='font-size:11px'>遇收件电话异常,请按收件地址派送3次</div></div>");
				sb.append("</div>");
				sb.append("<div class='right'>");
				sb.append("<div class='r1 b'>顺丰次日</div>");
				sb.append("<div class='r2'>");
				sb.append("<p class='p1'></p>");
				sb.append("<p class='p2'></p>");
				sb.append("<p class='p3'></p>");
				sb.append("</div></div></div>");

			}

			sb.append("<div class='m3 b'>");
			sb.append("<div class='left'><p>目的地</p></div>");
			sb.append("<div class='right'><p>" + print.getDestcode() + "</p></div>");
			sb.append("</div>");
			sb.append("<div class='m4 b'>");
			sb.append("<div class='left'>	<p>收件人</p></div>");
			sb.append("<div class='right'>");
			sb.append("<div class='right-con'>");
			sb.append("<p>" + rs0023 + " " + rs0024 + "</p>");
			sb.append("<p>" + rs0092 + "</p>");
			sb.append("</div>	</div></div>");
			sb.append("<div class='m5 b'>");
			sb.append("<div class='left'><p>寄件人</p></div>");
			sb.append("<div class='right-1'>");
			sb.append("<div class='r-con'>");
			sb.append("<p>" + rs0026 + " " + rs0027 + "</p>");
			sb.append("<p>" + rs0025 + "</p>");
			sb.append("</div></div>");
			sb.append("<div class='right-2'>");
			sb.append("<div class='r-con'>");
			sb.append("<p class='p1'>定时派送</p>");
			sb.append("<p class='p2'>自寄 自取</p>");
			sb.append("</div></div></div>");
			sb.append("<div class='m6 b'>");
			sb.append("<div class='left'>");
			sb.append("<div class='l1 b'>");
			sb.append("<div class='info-table'>");
			sb.append("<table><tr>");
			sb.append("<td>付款方式：寄付月结</td>");
			sb.append("<td>计费重量：&nbsp;&nbsp;&nbsp;&nbsp;KG</td>");
			sb.append("<td>包装费用：&nbsp;&nbsp;&nbsp;&nbsp;元</td></tr>");
			sb.append("<tr><td>月结账号：" + emptyString(print.getMonthly_payment()) + "</td>");
			sb.append("<td>声明价值：&nbsp;&nbsp;&nbsp;&nbsp;" + emptyString(print.getInsur_value()) + "元</td>");
			sb.append("<td>运费：&nbsp;&nbsp;元</td></tr>");
			sb.append("<tr><td>第三方地区：&nbsp;&nbsp;&nbsp;&nbsp;</td>");
			sb.append("<td>保价费用：&nbsp;&nbsp;&nbsp;&nbsp;元</td>");
			sb.append("<td>费用合计：&nbsp;&nbsp;&nbsp;&nbsp;元</td></tr>");
			sb.append("<tr><td>实际重量：&nbsp;&nbsp;&nbsp;&nbsp;KG</td>");
			sb.append("<td colspan='2'>定时派送：</td>");
			sb.append("</tr></table>");
			sb.append("</div></div>");
			sb.append("<div class='l2'>");
			sb.append("<div class='l2-a'><p>托寄物</p></div>");
			sb.append("<div class='l2-b'><p></p></div>");
			sb.append("<div class='l2-c'>");
			sb.append("<ul><li>收件员：</li><li>收件日期：</li><li>派件员：</li>	</ul>");
			sb.append("</div></div></div>");
			sb.append("<div class='right'><p>签名：</p><p class='p2'>月    日</p></div>");
			sb.append("</div>");
			sb.append("<div class='m7 b'>");
			sb.append("<div class='left'>");
			sb.append("<div class='logo'><img src='" + cxt + "/ecs_ord/sf/logo.jpg'></div>");
			sb.append("<div class='tell'><img src='" + cxt + "/ecs_ord/sf/tel.jpg'></div>");
			sb.append("</div>");
			sb.append("<div class='right'>");
			sb.append("<div class='barcode4' id='codeImg2'><script>createBarcodeCom('codeImg2','" + print.getPost_no()
					+ "','C');</script></div>");
			sb.append("</div></div>");
			sb.append("<div class='m8 b'>");
			sb.append("<div class='left'><p>寄件人</p></div>");
			sb.append("<div class='right'><p>" + rs0026 + " " + rs0027 + "</p><p>" + rs0025 + "</p></div>");
			sb.append("</div>");
			sb.append("<div class='m8 b'>");
			sb.append("<div class='left'><p>收件人</p></div>");
			sb.append("<div class='right big-size'><p>" + rs0023 + " " + rs0024 + "</p><p>" + rs0092 + "</p></div>");
			sb.append("</div>");
			sb.append("<div class='m9'><p>ICCID:" + iccid + "</p></div></div>");
			sb.append("</DIV>");
		}

		htmlCode = this.getHotHeadSF(cxt) + sb.toString() + buttonNew + tail + closeEnd;

		logger.info(this.getHotHeadSF(cxt) + sb.toString());

		return htmlCode;

	}
}
