<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户回访</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>

<%
  String order_id = (String)request.getAttribute("order_id");
%>

</head>
<body>
<form action="javascript:void(0);" id="preDealOrderForm" method="post">

<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
           
             <!-- 顶部公共 -->
        	<jsp:include page="auto_flows.jsp?order_id=${order_id}"/>
        　　　　　<!-- 订单基本信息展示开始 -->
        　　　　　<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>订单基本信息<a href="#" class="editBtn">编辑</a></h2>
            	 <div class="grid_n_cont">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form" name="ordDetailTable">
                      <tr>
                        <th>内部订单编号：</th>
                        <td>${order_id}</td>
                        <th>外部订单编号：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="out_tid"  field_desc ="外部订单编号" field_type="text"></html:orderattr></td>
                        <th>生产模式：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="order_model"  field_desc ="生产模式" field_type="text"></html:orderattr></td>
                      </tr>
                      <tr>
                        <th>外部状态：</th>
                        <td><html:orderattr  attr_code="DIC_ORDER_EXT_STATUS" order_id="${order_id}" field_name="platform_status"  field_desc ="外部状态" field_type="text"></html:orderattr></td>
                        <th>归属区域：</th>
                        <td><html:orderattr attr_code="DC_MODE_REGION" order_id="${order_id}" field_name="order_city_code"  field_desc ="归属地市" field_type="select" disabled="disabled"></html:orderattr></td>
                        <th>订单来源：</th>
                        <td><html:orderattr attr_code="ORDER_FROM" order_id="${order_id}" field_name="order_from"  field_desc ="订单来源" field_type="select"></html:orderattr></td>
                      </tr>
                      <tr>
                        <th>订单来源系统：</th>
                        <td><html:orderattr attr_code="DIC_ORDER_ORIGIN" order_id="${order_id}" field_name="order_channel"  field_desc ="订单来源系统" field_type="select"></html:orderattr></td>
                        <th>下单时间：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="tid_time"  field_desc ="下单时间" field_type="text"></html:orderattr></td>
                        <th>客户名称：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="buyer_name"  field_desc ="客户名称" field_type="text"></html:orderattr></td>
                      </tr>
                      <tr>
                        <th>客户电话：</th>
                        <td>${buyInfoMap.tel}</td>
                        <th>客户手机：</th>
                        <td>${buyInfoMap.mobile}</td>
                        <th>客户联系地址：</th>
                        <td>${buyInfoMap.address}</td>
                      </tr>
                      <tr>
                        <th>发展人编码：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="development_code"  field_desc ="发展人编码" field_type="text"></html:orderattr></td>
                        <th>推广渠道：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="reserve4"  field_desc ="推广渠道" field_type="text"></html:orderattr></td>
                        <th>订单发展归属：</th>
                        <td> <html:orderattr disabled="disabled"  attr_code="DIC_CHANNEL_MARK" order_id="${order_id}" field_name="channel_mark"  field_desc ="订单发展归属" field_type="select"></html:orderattr>
                        </td>
                      </tr>
                      <tr>
                        <th>集团编码：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="group_code"  field_desc ="集团编码" field_type="text"></html:orderattr></td>
                        <th>集团名称：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="group_name"  field_desc ="集团名称" field_type="text"></html:orderattr></td>
                        <th>行业应用类别：</th>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <th>应用子类别：</th>老带新号码
                        <td>&nbsp;</td>
                        <th>推荐人名称：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="recommended_name"  field_desc ="推荐人名称" field_type="text"></html:orderattr></td>
                        <th>推荐人手机：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="recommended_phone"  field_desc ="推荐人手机" field_type="text"></html:orderattr></td>
                      </tr>
                      <tr>
                        <th>老带新号码：</th>
                        <td>&nbsp;</td>
                        <th>订单处理类型：</th>
                        <td><html:orderattr  order_id="${order_id}" attr_code="DIC_ORDER_DEAL_TYPE" field_name="order_deal_type"  field_desc ="订单处理类型" field_type="select"></html:orderattr></td>
                        <th>资料回收方式：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="file_return_type"  field_desc ="资料回收方式" field_type="text"></html:orderattr></td>
                      </tr>
                      <tr>
                        <th>发票打印方式：</th>
                        <td><html:orderattr attr_code="DIC_ORDER_INVOICE_PRINT_TYPE"  order_id="${order_id}" field_name="invoice_print_type"  field_desc ="发票打印方式" field_type="select"></html:orderattr></td>
                        <th>发票抬头：</th>
                        <td><html:orderattr  order_id="${order_id}" field_name="reserve8"  field_desc ="发票抬头" field_type="text"></html:orderattr></td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                      </tr>
                    </table>
                </div>
            </div>
              <!-- 订单基本信息展示开始 -->
              <!-- 支付配送信息开始 -->
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>支付配送信息<a href="#" class="editBtn">编辑</a></h2>
              	<div class="grid_n_cont">
            		<div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
            	    <!-- 支付信息开始 -->
            	    <div class="grid_n_cont_sub">
                        <h3>支付信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form" name="ordPayTable">
                            <tr>
                              <th>订单总价：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="order_origfee"  field_desc ="订单总价（元）" field_type="text"></html:orderattr></td>
                              <th>优惠金额：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="order_disfee"  field_desc ="优惠金额（元）" field_type="text"></html:orderattr></td>
                              <th>实收金额：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="order_realfee"  field_desc ="实收金额（元）" field_type="text"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th>支付类型：</th>
                              <td> <html:orderattr attr_code="DIC_PAY_TYPE" order_id="${order_id}" field_name="paytype"  field_desc ="支付类型" field_type="select"></html:orderattr></td>
                              <th>支付方式：</th>
                              <td> <html:orderattr attr_code="DIC_PAY_METHOD" order_id="${order_id}" field_name="pay_mothed"  field_desc ="支付方式" field_type="select"></html:orderattr></td>
                              <th>支付状态：</th>
                              <td><html:orderattr attr_code="DIC_PAY_STATUS" order_id="${order_id}" field_name="pay_status"  field_desc ="支付状态" field_type="select"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th>支付时间：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="pay_time"  field_desc ="支付时间" field_type="text"></html:orderattr></td>
                              <th>支付流水号：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="payplatformorderid"  field_desc ="支付流水号" field_type="text"></html:orderattr></td>
                              <th>支付机构编码：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="payproviderid"  field_desc ="支付机构编码" field_type="text"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th>支付渠道编码：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="paychannelid"  field_desc ="支付渠道编码" field_type="text"></html:orderattr></td>
                              <th>支付渠道名称：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="paychannelname"  field_desc ="支付渠道名称" field_type="text"></html:orderattr></td>
                              <th>&nbsp;</th>
                              <td>&nbsp;</td>
                            </tr>
                        </table>
                    </div>
            	    <!-- 支付信息结束 -->
            	    <!-- 配送信息开始 -->
            	    <div class="grid_n_cont_sub">
                        <h3>配送信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              <th>配送方式：</th>
                              <td>
                                 <html:orderattr  attr_code="DC_MODE_SHIP_TYPE" order_id="${order_id}" field_name="sending_type"  field_desc ="配送方式" field_type="select"></html:orderattr>
                              </td>
                              <th>物流公司：</th>
                              <td>
                                <html:orderattr attr_code="DIC_SHIPPING_COMPANY"  order_id="${order_id}" field_name="shipping_company"  field_desc ="物流公司" field_type="select"></html:orderattr>
                              </td>
                              <th>取件人：</th>
                              <td>
                                <html:orderattr  order_id="${order_id}" field_name="carry_person"  field_desc ="取件人" field_type="input"></html:orderattr>
                              </td>
                            </tr>
                            <tr>
                              <th>取件人电话：</th>
                              <td>
                                 <html:orderattr  order_id="${order_id}" field_name="carry_person_mobile"  field_desc ="取件人电话" field_type="input"></html:orderattr>
                              </td>
                              <th>配送时间：</th>
                              <td>
                               <html:orderattr  order_id="${order_id}" field_name="shipping_time"  field_desc ="配送时间" field_type="date"></html:orderattr>
                              </td>
                              <th>收货人姓名：</th>
                              <td><html:orderattr   order_id="${order_id}" field_name="ship_name"  field_desc ="收货人姓名" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th>收货人省份：</th>
                              <td>
                                <select name="provinc_code" field_desc="收货省份" id="provinc_code" class="ipt_new" style="width:200px;" >
                              	     <c:forEach var="pro" items="${provList}">
                              	        <c:if test="${provinc_code==pro.code}">
                              	          <option value="${pro.code}" selected="selected">${pro.name}</option>
                              	        </c:if>
                              	         <c:if test="${provinc_code!=pro.code}">
                              	          <option value="${pro.code}">${pro.name}</option>
                              	        </c:if>
                              	     </c:forEach>
                              	   </select>
                              </td>
                              <th>收货人地市：</th>
                              <td>
                                 <select name="city_code" field_desc="收货地市" id="city_code" class="ipt_new" style="width:200px;" >
                              	     <c:forEach var="cityList" items="${cityList}">
                              	        <c:if test="${city_code==cityList.code}">
                              	          <option value="${cityList.code}" selected="selected">${cityList.name}</option>
                              	        </c:if>
                              	         <c:if test="${provinc_code!=cityList.code}">
                              	          <option value="${cityList.code}">${cityList.name}</option>
                              	        </c:if>
                              	     </c:forEach>
                              	   </select>
                              </td>
                              <th>收货人区县：</th>
                              <td>
                                <select name="district_id" field_desc="收货区县" id="district_id" class="ipt_new" style="width:200px;" >
                              	     <c:forEach var="districtList" items="${districtList}">
                              	        <c:if test="${district_id==districtList.code}">
                              	          <option value="${districtList.code}" selected="selected">${districtList.name}</option>
                              	        </c:if>
                              	         <c:if test="${provinc_code!=districtList.code}">
                              	          <option value="${districtList.code}">${districtList.name}</option>
                              	        </c:if>
                              	     </c:forEach>
                              	   </select>
                              </td>
                            </tr>
                            <tr>
                              <th>收货商圈：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="ship_area"  field_desc ="收货商圈" field_type="input"></html:orderattr></td>
                              <th>详细地址：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="ship_addr"  field_desc ="详细地址" field_type="input"></html:orderattr></td>
                              <th>邮政编码：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="ship_zip"  field_desc ="邮政编码" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th>固定电话：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="reference_phone"  field_desc ="固定电话" field_type="input"></html:orderattr></td>
                              <th>手机号码：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="receiver_mobile"  field_desc ="手机号码" field_type="input"></html:orderattr></td>
                              <th>应收运费：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="post_fee"  field_desc ="应收运费" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th>实收运费：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="n_shipping_amount"  field_desc ="实收运费 " field_type="input"></html:orderattr></td>
                              <th>&nbsp;</th>
                              <td>&nbsp;</td>
                              <th>&nbsp;</th>
                              <td>&nbsp;</td>
                            </tr>
                        </table>
                    </div>
            	    <!-- 配送信息结束 -->
            	</div>
              </div>
              <!-- 支付配送信息结束-->
              <!-- 商品包开始 -->
              <div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>商品包<span>*开户人身份未认证！</span><a href="#" class="editBtn">编辑</a></h2>
              	<div class="grid_n_cont">
            		<div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
                    <!-- 商品信息开始 -->
                    <div class="grid_n_cont_sub">
                        <h3>商品信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>  
                              <th>商品包类型：</th>
                              <td><html:orderattr attr_code="DC_MODE_GOODS_TYPE"  order_id="${order_id}" field_name="pack_type"  field_desc ="商品包类型 " field_type="select"></html:orderattr></td>
                              <th>商品名称：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="GoodsName"  field_desc ="商品名称" field_type="input"></html:orderattr></td>
                              <th>卡类型：</th>
                              <td><html:orderattr  attr_code="DC_PRODUCT_CARD_TYPE"  order_id="${order_id}" field_name="white_cart_type"  field_desc ="卡类型" field_type="select"></html:orderattr></td>
                          </tr>
                            <tr>
                              <th><span>*</span>套餐名称：</th>
                              <td><input type="text"  name="plan_title" readonly="readonly"  value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE) %>" /></td>
                              <th><span>*</span>套餐档次：</th>
                              <td>
                              <html:selectdict  name="month_fee" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.MONTH_FEE)%>"    attr_code="DC_PACKEGE_LEVEL" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                              </td>
                              <th><span>*</span>资费方式：</th>
                              <td>
                                 <html:orderattr attr_code="DIC_OFFER_EFF_TYPE"  order_id="${order_id}" field_name="first_payment"  field_desc ="首月资费方式" field_type="select"></html:orderattr>
                              </td>
                            </tr>
                            <tr>
                              <th><span>*</span>活动类型：</th>
                              <td><html:selectdict  name="ative_type" curr_val="<%=CommonDataFactory.getInstance().getGoodSpec(order_id,null, AttrConsts.ACTIVE_TYPE)%>"    attr_code="DC_PACKEGE_LEVEL" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                              <th>合约期：</th>
                              <td><input name="package_limit" type="text" readonly="readonly" class="ipt_new" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT) %>" id="textfield" style="width:200px;" /></td>
                              <th>&nbsp;</th>
                              <td>&nbsp;</td>
                            </tr>
                            <tr>
                              <th><span>*</span>品牌：</th>
                              <td>
                                <select name="select" id="select" style="width:200px;">
                                  <option value="1">罗珍</option>
                                </select>
                              </td>
                              <th><span>*</span>机型：</th>
                              <td>
                                <select name="select" id="select" style="width:200px;">
                                  <option value="1">档次</option>
                                </select>
                              </td>
                              <th><span>*</span>颜色：</th>
                              <td>
                                <select name="select" id="select" style="width:200px;">
                                  <option value="1">白色</option>
                                </select>
                              </td>
                            </tr>
                      </table>
                  	</div>
                    <!-- 商品信息结束 -->
                    <!-- 开户信息开始 -->
                     <div class="grid_n_cont_sub">
                        <h3>开户信息：<a href="#" class="newBtn" style="margin-left:5px;"><span>身份证验证</span></a><a href="#" class="newBtn" style="margin-left:5px;"><span>预校验</span></a></h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              <th><span>*</span>所选号码：</th>
                              <td>
                                <html:orderattr    order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="input"></html:orderattr>
                              </td>
                              <th>是否靓号：</th>
                              <td>
                                <html:orderattr attr_code="DIC_IS_LOVER"    order_id="${order_id}" field_name="is_loves_phone"  field_desc ="是否情侣号" field_type="select"></html:orderattr>
                              </td>
                              <th>靓号金额：</th>
                              <td><html:orderattr    order_id="${order_id}" field_name="liang_price"  field_desc ="靓号金额" field_type="input"></html:orderattr></td>
                          </tr>
                            <tr>
                              <th><span>*</span>是否情侣卡：</th>
                              <td>
                                <html:orderattr attr_code="DIC_IS_LOVER"    order_id="${order_id}" field_name="is_loves_phone"  field_desc ="是否情侣号" field_type="select"></html:orderattr>
                              </td>
                              <th>客户名称：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="phone_owner_name"  field_desc ="客户名称" field_type="input"></html:orderattr></td>
                              <th>证件类型：</th>
                              <td><html:orderattr attr_code="DIC_CARD_TYPE"  order_id="${order_id}" field_name="certi_type"  field_desc ="证件类型" field_type="select"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th>证件号码：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="cert_card_num"  field_desc ="证件号码" field_type="input"></html:orderattr></td>
                              <th>证件有效期：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="cert_failure_time"  field_desc ="证件有效期" field_type="date"></html:orderattr></td>
                              <th>证件地址：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="cert_address"  field_desc ="证件地址" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th>预存款：</th>
                              <td><input name="deposit_fee" type="text" class="ipt_new" readonly="readonly" value="<%=CommonDataFactory.getInstance().getGoodSpec(order_id, null,SpecConsts.DEPOSIT_FEE) %>" style="width:200px;" /></td>
                              <th>&nbsp;</th>
                              <td>&nbsp;</td>
                              <th>&nbsp;</th>
                              <td>&nbsp;</td>
                            </tr>
                            <tr>
                              <th><span>*</span>是否托收：</th>
                              <td>
                               <html:orderattr attr_code="DIC_IS_COLLECTION"  order_id="${order_id}" field_name="collection"  field_desc ="是否托收" field_type="select"></html:orderattr>
                              </td>
                              <th>上级银行名称：</th>
                              <td>
                                <html:orderattr  attr_code="DIC_BANK_NAME"  order_id="${order_id}" field_name="superiors_bankcode"  field_desc ="上级银行名称" field_type="select"></html:orderattr>
                              </td>
                              <th>托收银行名称：</th>
                              <td>
                               <html:orderattr  attr_code="DIC_BANK_NAME"  order_id="${order_id}" field_name="bank_code"  field_desc ="托收银行" field_type="select"></html:orderattr>
                              </td>
                            </tr>
                            <tr>
                              <th>托收银行帐号：</th>
                              <td><html:orderattr    order_id="${order_id}" field_name="bank_account"  field_desc ="托收银行账号" field_type="input"></html:orderattr></td>
                              <th>托收银行户名：</th>
                              <td><html:orderattr    order_id="${order_id}" field_name="bank_user"  field_desc ="银行账户名" field_type="input"></html:orderattr></td>
                              <th>&nbsp;</th>
                              <td>&nbsp;</td>
                            </tr>
                      </table>
                  	</div>
                    <!-- 开户信息结束 -->
                </div>
              </div>
              <!-- 商品包结束 -->
        </div>
     </div>
 </div>
 </form>
</body>
</html>

<script type="text/javascript">
$("table[name='ordDetailTable']").find("select").each(function(){
	$(this).attr("disabled","disabled");
});
$("table[name='ordPayTable']").find("select").each(function(){
	$(this).attr("disabled","disabled");
});

//attr("disabled",disabled)
//alert($(".grid_n_div > .grid_n_cont:first-child").length);

</script>