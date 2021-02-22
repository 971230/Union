<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>历史单详情</title>
<script src="<%=request.getContextPath()%>/ecs_ord/js/autoord.js"></script>

</head>
<%
	String order_id = (String) request.getAttribute("order_id");
%>
<body>
	<form id="qualityAuditForm" method="post">
	<input type="hidden" value="<%=order_id %>" id="order_id"/>
		<div class="gridWarp">
			<div class="new_right">
				<div class="right_warp">
					<jsp:include page="auto_flows.jsp?order_id=${order_id }" />
					<div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="closeArrow"></a>订单基本信息<input
								type="checkbox" name="checkbox_quality" id="checkbox" />
						</h2>
						<div class="grid_n_cont">
						<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
						
							<div class="grid_n_cont_sub">
								<h3>基本信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_form">
									<tr>
										<th><span>*</span>内部订单编号：</th>
										<td>${orderTree.orderExtBusiRequest.order_id }</td>
										<input type="hidden"
											name="orderTree.orderExtBusiRequest.out_tid"
											value="${orderTree.orderExtBusiRequest.out_tid }" />
										<th><span>*</span>外部订单编号：</th>
										<td>${orderTree.orderExtBusiRequest.out_tid }</td>
										<input type="hidden"
											name="orderTree.orderExtBusiRequest.order_id"
											value="${orderTree.orderExtBusiRequest.order_id }" />
										<th><span>*</span>生产模式：</th>
										<td><html:orderattr  attr_code="DC_MODE_OPER_MODE" order_id="${order_id}" disabled="disabled" field_name="order_model"  field_desc ="生产模式" field_type="select"></html:orderattr></td>
										<input type="hidden"
											name="orderTree.orderExtBusiRequest.order_model"
											value="${orderTree.orderExtBusiRequest.order_model }" />
									</tr>
									<tr>
										<th><span>*</span>外部订单状态：</th>
										<td><html:orderattr  attr_code="DIC_ORDER_EXT_STATUS" order_id="${order_id}" field_name="platform_status"  field_desc ="外部状态" field_type="select" disabled="disabled" ></html:orderattr></td>

										<th><span>*</span>归属区域：</th>
										<td><html:orderattr attr_code="DC_MODE_REGION"
												order_id="${order_id}" field_name="order_city_code"
												field_desc="归属地市" field_type="select" disabled="disabled" ></html:orderattr></td>
										<th><span>*</span>订单来源：</th>
										<td> <html:orderattr disabled="disabled"  attr_code="ORDER_FROM"  order_id="${order_id}" field_name="order_from"  field_desc ="订单来源" field_type="select" ></html:orderattr></td>
										<input type="hidden"
											name="orderTree.orderExtBusiRequest.order_from"
											value="${orderTree.orderExtBusiRequest.order_from }" />
									</tr>
									<tr>
										<th><span>*</span>下单时间：</th>
										<td>${orderTree.orderExtBusiRequest.tid_time }</td>
										<input type="hidden"
											name="orderTree.orderExtBusiRequest.tid_time"
											value="${orderTree.orderExtBusiRequest.tid_time }" />
										<th>订单处理类型：</th>
										<td><html:orderattr attr_code="DIC_ORDER_DEAL_TYPE"
												order_id="${order_id }" field_name="order_deal_type"
												field_desc="订单处理类型" field_type="select">
											</html:orderattr></td>
									</tr>
								</table>
							</div>
							<div class="grid_n_cont_sub">
								<h3>支付信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_form">
									<tr>
										<th><span>*</span>支付时间：</th>
										<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="pay_time"  field_desc ="支付时间" field_type="date"></html:orderattr></td>
										<input type="hidden"
											name="orderTree.orderBusiRequest.pay_time"
											value="${orderTree.orderBusiRequest.pay_time }" />
										<th><span>*</span>支付类型：</th>
										<td><html:orderattr attr_code="DIC_PAY_TYPE"
												order_id="${order_id}" field_name="paytype"
												field_desc="支付类型" field_type="select" disabled="disabled" ></html:orderattr></td>
										<th><span>*</span>支付方式：</th>
										<td> <html:orderattr attr_code="DIC_PAY_WAY" order_id="${order_id}" field_name="pay_method"   field_desc ="支付方式" field_type="select" disabled="disabled" ></html:orderattr></td>
									</tr>
									<tr>
										<th><span>*</span>支付状态：</th>
										<td><html:orderattr attr_code="DIC_PAY_STATUS"
												order_id="${order_id}" field_name="pay_status"
												field_desc="支付状态" field_type="select" disabled="disabled" ></html:orderattr></td>
									</tr>
								</table>
							</div>
						</div>
					</div>

					<div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="openArrow"></a>商品信息<input
								type="checkbox" name="checkbox_quality" id="checkbox" />
						</h2>
						<div class="grid_n_cont">
							<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
							<div class="grid_n_cont_sub">
								<h3>商品信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_form">
									<tr>
										<th><span>*</span>商品名称：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="GoodsName" field_desc="商品名称" field_type="input" disabled="disabled" ></html:orderattr></td>
										<th><span>*</span>商品类型：</th>
										 <td><html:orderattr  order_id="${order_id}" attr_code="DC_MODE_GOODS_TYPE" field_name="goods_type"  field_desc ="商品类型" field_type="select" disabled="disabled"></html:orderattr></td>
										<th><span>*</span>仓库名称：</th>
										<td><html:orderattr attr_code="DIC_MT_STOREHOUSE"
												order_id="${order_id}" field_name="house_id"
												field_desc="仓库名称" field_type="select"></html:orderattr></td>
									</tr>
									<tr>
										<th>上网时长：</th>
										<td><input name="card_time" type="text" class="ipt_new" readonly="readonly" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id,SpecConsts.TYPE_ID_20001,null,SpecConsts.CARD_TIME) %>" style="width:200px;" /></td>
										<th>商品备注：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="service_remarks" cols="45" field_desc="订单备注"
												field_type="input" style="width:200px;"></html:orderattr></td>
										<th>&nbsp;</th>
										<td>&nbsp;</td>
									</tr>
								</table>
							</div>
							<div class="grid_n_cont_sub">
								<h3>用户证件信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_form">
									<tr>
										<th>证件类型：</th>
										<td><html:orderattr attr_code="DIC_CARD_TYPE"
												order_id="${order_id}" field_name="certi_type"
												field_desc="证件类型" field_type="select"></html:orderattr></td>
										<th>证件号码：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="cert_card_num" field_desc="证件号码"
												field_type="input"></html:orderattr></td>
										<th>证件地址：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="cert_address" field_desc="证件地址"
												field_type="input"></html:orderattr></td>
									</tr>
									<tr>
										<th>客户名称：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="phone_owner_name" field_desc="客户名称"
												field_type="input"></html:orderattr></td>
										<th>客户类型：</th>
										<td><html:orderattr attr_code="DIC_CUSTOMER_TYPE"
												order_id="${order_id}" field_name="CustomerType"
												field_desc="客户类型" field_type="select"></html:orderattr></td>
										<th>证件有效期：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="cert_failure_time" field_desc="证件有效期"
												field_type="date"></html:orderattr></td>
									</tr>
								</table>
							</div>
							<%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10000))){
                           	%>
							<div class="grid_n_cont_sub">
								<h3>终端信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_form">
									<tr>
										<th><span>*</span>颜色：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id,
					SpecConsts.TYPE_ID_10000, null, SpecConsts.COLOR_NAME)%></td>
										<th><span>*</span>容量：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id,
					SpecConsts.TYPE_ID_10000, null, SpecConsts.SIZE)%></td>
									</tr>
									<tr>
										<th><span>*</span>型号：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, AttrConsts.MODEL_NAME) %></td>
										<th><span>*</span>品牌：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.BRAND_NAME) %></td>
										<th><span>*</span>串号：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="terminal_num" field_desc="串号" field_type="input"></html:orderattr></td>
									</tr>
									
									</table>
									</div>
								<%} %>
								<div class="grid_n_cont_sub">
								<h3>套餐信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_form">
									<tr>
										<th><span>*</span>套餐名称：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id,
					SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE)%></td>
										<th><span>*</span>首月资费方式：</th>
										<td><html:orderattr attr_code="DIC_OFFER_EFF_TYPE"
												order_id="${order_id}" field_name="first_payment"
												field_desc="首月资费方式" field_type="select" disabled="disabled" ></html:orderattr></td>
									</tr>
									<tr>
										<th><span>*</span>合约名称：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id,
					SpecConsts.TYPE_ID_10001, null, SpecConsts.GOODS_NAME)%></td>
										<th><span>*</span>合约类型：</th>
										<td><html:selectdict name="package_type"
												curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE) %>"
												attr_code="DIC_ORDER_PAY_TYPE"
												style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
										<th><span>*</span>合约期限：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id,
					SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT)%></td>
									</tr>
									<tr>
										<th>号码开户信息：</th>
										<td><input name="textfield" type="text" class="ipt_new"
											id="textfield" style="width:200px;" /></td>
										<th><span>*</span>用户号码：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="phone_num" field_desc="用户号码" field_type="input" disabled="disabled" ></html:orderattr></td>
										<th><span>*</span>卡类型：</th>
										<td><html:orderattr attr_code="DC_PRODUCT_CARD_TYPE"
												order_id="${order_id}" field_name="white_cart_type"
												field_desc="卡类型" field_type="select" disabled="disabled"></html:orderattr></td>
									</tr>
									<tr>
										<th><span>*</span>入网地区：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="net_region" attr_code="DC_MODE_REGION" field_desc="入网地区" field_type="select" disabled="disabled" ></html:orderattr></td>
										<th><span>*</span>是否靓号：</th>
										<td><html:orderattr attr_code="DIC_IS_LIANG"
												order_id="${order_id}" field_name="is_liang"
												field_desc="是否靓号" field_type="select" disabled="disabled"></html:orderattr></td>
										<th>靓号预存：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="liang_price" field_desc="靓号预存"
												field_type="input" disabled="disabled" ></html:orderattr></td>
									</tr>
									<tr>
										<th>靓号低消：</th>
										<td><input name="lowest" type="text"  disabled="disabled" class="ipt_new" value="<%=CommonDataFactory.getInstance().getNumberSpec(CommonDataFactory.getInstance().getAttrFieldValue(order_id, "phone_num"), SpecConsts.NUMERO_LOWEST) %>" style="width:200px;" /></td>
									</tr>
								</table>
							</div>
						</div>
					</div>

					<div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="openArrow"></a>物流信息<input
								type="checkbox" name="checkbox_quality" id="checkbox" />
								 <input type="hidden"  id="post_type" name="post_type" value="0"/> <!-- 物流类型 -->
							
						</h2>
						<div class="grid_n_cont">
							<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
							<div class="grid_n_cont_sub">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_form">
									<tr>
										<th><span>*</span>收货人姓名：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="ship_name" field_desc="收货人姓名" field_type="input"></html:orderattr></td>
										<th><span>*</span>手机号码：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="receiver_mobile" field_desc="手机号码"
												field_type="input"></html:orderattr></td>
										<th>电子邮件：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="ship_email" field_desc="电子邮件" field_type="input"></html:orderattr></td>
									</tr>
									<tr>
										<th>固定电话：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="reference_phone" field_desc="固定电话"
												field_type="input"></html:orderattr></td>
										<th><span>*</span>收货省份：</th>
										<td><select name="provinc_code" field_desc="收货省份"
											id="provinc_code" class="ipt_new" style="width:200px;">
												<c:forEach var="pro" items="${provList}">
													<c:if test="${provinc_code==pro.code}">
														<option value="${pro.code}" selected="selected">${pro.name}</option>
													</c:if>
													<c:if test="${provinc_code!=pro.code}">
														<option value="${pro.code}">${pro.name}</option>
													</c:if>
												</c:forEach>
										</select></td>
										<th><span>*</span>收货地市：</th>
										<td><select name="city_code" field_desc="收货地市"
											id="city_code" class="ipt_new" style="width:200px;">
												<c:forEach var="cityList" items="${cityList}">
													<c:if test="${city_code==cityList.code}">
														<option value="${cityList.code}" selected="selected">${cityList.name}</option>
													</c:if>
													<c:if test="${provinc_code!=cityList.code}">
														<option value="${cityList.code}">${cityList.name}</option>
													</c:if>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th><span>*</span>收货区县：</th>
										<td><select name="district_id" field_desc="收货区县"
											id="district_id" class="ipt_new" style="width:200px;">
												<c:forEach var="districtList" items="${districtList}">
													<c:if test="${district_id==districtList.code}">
														<option value="${districtList.code}" selected="selected">${districtList.name}</option>
													</c:if>
													<c:if test="${provinc_code!=districtList.code}">
														<option value="${districtList.code}">${districtList.name}</option>
													</c:if>
												</c:forEach>
										</select></td>
										<th><span>*</span>详细地址：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="ship_addr" field_desc="详细地址" field_type="input"></html:orderattr></td>
										<th>邮政编码：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="ship_zip" field_desc="邮政编码" field_type="input"></html:orderattr></td>
									</tr>
									<tr>
										<th><span>*</span>物流公司名称：</th>
										<td>
										<select name="shipping_company" field_desc="物流公司" 
											id="logi_company" class="ipt_new" style="width:200px;">
												<option value="">---请选择---</option>
												<c:forEach var="logicompany" items="${logiCompanyList }">
													<option key_str="${logicompany.key_str }"
														value="${logicompany.id }">${logicompany.name }</option>
												</c:forEach>
										</select> 
									
										<script type="text/javascript">
										  var  ship_company = '<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_COMPANY) %>';
										  $("#logi_company option[value='"+ship_company+"']").attr("selected","selected");
										</script>
										</td>
										<th><span>*</span>配送方式：</th>
										<td><html:orderattr attr_code="DC_MODE_SHIP_TYPE"
												order_id="${order_id}" field_name="sending_type"
												field_desc="配送方式" field_type="select"></html:orderattr></td>
										<th><span>*</span>配送时间：</th>
										<td><html:orderattr order_id="${order_id}" attr_code ='DIC_SHIPPING_TIME'
												field_name="shipping_time" field_desc="配送时间"
												field_type="select"></html:orderattr></td>
									</tr>
									<tr>
										<th><span>*</span>物流单号：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="logi_no" field_desc="物流单号" field_type="input"></html:orderattr></td>
										<th>运费：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="n_shipping_amount" field_desc="实收金额（元）"
												field_type="input"></html:orderattr>
												</td>
										<th>保费：</th>
										<td><input type="text" class="ipt_new"
											style="width:200px;" id="protect_fee"  
											name="protect_free"
											value="0" /></td>
									</tr>
									<tr>
										<th>物流人员：</th>
										<td><input type="text" class="ipt_new"
											style="width:200px;" id="logi_receiver" 
											name="logi_receiver"
											value="${logi_receiver }" /></td>
										<th>物流电话：</th>
										<td><input type="text" class="ipt_new"
											style="width:200px;" id="logi_receiver_phone"
											name="logi_receiver_phone" value="${logi_receiver_phone }" /></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="ps_div" style ="display:none;">
						<h2>BSS业务办理日志</h2>
						<div class="netWarp">
							<a href="javascript:void(0);" class="icon_close">展开</a>
							<div class="goodTit">GZTB20130717666248</div>
							<div class="goodCon">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_s">
									<tr>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>服务名称</th>
										<th>发送内容</th>
										<th>是否成功</th>
										<th>详细信息</th>
									</tr>
									<tr>
										<td>2013-07-17 14:35:27</td>
										<td>2013-07-17 14:35:27</td>
										<td>用户资料</td>
										<td>用户资料接口</td>
										<td>成功</td>
										<td><a href="javascript:void(0);">查看</a></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="ps_div">
						<h2>补发信息录入</h2>
						<div class="netWarp">
							<a href="javascript:void(0);" class="icon_close">展开</a>
							<div class="goodTit">已检测到可能需要补寄的物品，请选择</div>
							<div class="goodCon">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_s">
									<tr>
									    <th>操作</th> 
										<th>赠品名称</th>
										<th>赠品数量</th>
									</tr>
									
										<c:choose>
											<c:when test="${orderTree.giftInfoBusiRequests!=null}">
												<c:forEach var="attrGiftInfoBusiRequest"
													items="${orderTree.giftInfoBusiRequests }">
													<tr>
													 <td>
													    <%int k=0; %>
													    <c:forEach var="deliveryItem" items="${deliveryItemsQueryResp.deliveryItems }">
													      <c:if test="${attrGiftInfoBusiRequest.gift_inst_id==deliveryItem.col2 }">
													      		<% ++k;%>
													      		
													      </c:if>
													    </c:forEach>
													     <a href="javascript:void(0);"  id="items_add_btn_${attrGiftInfoBusiRequest.gift_inst_id }" <% if(k>0){%> style="display: none;"<%} %> 
													   class="items_add_btns"  title="${attrGiftInfoBusiRequest.gift_inst_id }"  >确认补寄</a>
													     <span id="items_add_text_${attrGiftInfoBusiRequest.gift_inst_id }" <% if(k==0){%> style="display: none ;"<%} %> >已确认</span>
													    <input type="hidden" id="gif_name_${attrGiftInfoBusiRequest.gift_inst_id  }" value="${attrGiftInfoBusiRequest.goods_name  }">
													 <input type="hidden" id="sku_num_${attrGiftInfoBusiRequest.gift_inst_id  }" value="${attrGiftInfoBusiRequest.sku_num  }">
													 </td> 
														<td>${attrGiftInfoBusiRequest.goods_name }</td>
														<td>${attrGiftInfoBusiRequest.sku_num }</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
											<tr>
												<td colspan="2">未查找到记录</td>
											</tr>
											</c:otherwise>
										</c:choose>
									
								</table>
							</div>
							<div class="goodTit">已确认需要补寄的物品</div>
							<div class="goodCon">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_s" id="reissue_tab">
									<tr id="reissue_tr">
										<th>操作</th>
										<th>补寄状态</th>
										<th>赠品名称</th>
									</tr>
									
									<c:forEach var="deliveryItem" items="${deliveryItemsQueryResp.deliveryItems }">
										<tr>
										 <c:if test="${deliveryItem.col1=='0'||deliveryItem.col1==null }">
											<td><a id="a_${deliveryItem.item_id }" title="${deliveryItem.col2}"  attr_del="attr_del" href="javascript:void(0);" attr_sc="${deliveryItem.item_id }">删除</a></td>
											<td>待补寄</td>
											<td>${deliveryItem.name }</td>
										 </c:if>
										  <c:if test="${deliveryItem.col1=='1' }">
											<td></td>
											<td>已打印</td>
											<td>${deliveryItem.name }</td>
										 </c:if>
										 <c:if test="${deliveryItem.col1=='2' }">
											<td></td>
											<td>补寄完成</td>
											<td>${deliveryItem.name }</td>
										 </c:if>
										 <span id="items_row_flag"></span>
										</tr>
									</c:forEach>
									
									<tr>
										<td colspan="3" style=" text-align:left; padding-left:5px;"><a
											href="javascript:void(0);" id="xinzeng"><i
												class="icon_add"></i>新增</a>
											<div id="zengpinneirong" style="display: none">
												赠品名称:<input class="ipt_new" type="text" id="reissue_info"
													name="reissue_info" /> 赠品数量:<input class="ipt_new"
													type="text" id="reissue_num" name="reissue_num" /> <a
													href="javascript:void(0);" class="blueBtns" id="luru"><span>录
														入</span></a> <a href="javascript:void(0);" class="blueBtns"
													id="quxiao"><span>取 消</span></a>
											</div></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<input type="hidden" id="prod_audit_status"
						name="prod_audit_status" />
					<%-- <jsp:include page="order_handler.jsp?order_id=${order_id }" /> --%>
					<div class="clear"></div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
<script type="text/javascript">
  $(function(){
	 AutoFlow.checkMsg();
  });
</script>