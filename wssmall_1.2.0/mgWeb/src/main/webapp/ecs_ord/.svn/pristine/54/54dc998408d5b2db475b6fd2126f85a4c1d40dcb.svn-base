<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderExtBusiRequest"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<head>
<style type="text/css">
.finish{display:inline-block; width:18px; height:21px; background:url(${context}/images/icon_03.png) no-repeat;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>写卡</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>


<%
  	String order_id = (String)request.getAttribute("order_id");
	String isWirteCard = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_WRITE_CARD);
	OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
	Boolean isShowWriteCardContents = true;//控制是否展示写卡信息以及脚本加载
	Boolean isShowOpenAcct = true;//控制是否显示开户按钮
	Boolean isShowWriteCardBtn = true;//控制是否显示写卡按钮
	if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(orderExt.getAbnormal_type())) { //自动化异常订单不允许写卡 
		isShowWriteCardBtn =  false;
	}
	if (!EcsOrderConsts.DIC_ORDER_NODE_X.equals(orderExt.getFlow_trace_id())) { //非写卡环节不允许写卡 
		isShowWriteCardBtn =  false;
	}
	if (!EcsOrderConsts.DIC_ORDER_NODE_D.equals(orderExt.getFlow_trace_id())) { //非开户环节不允许开户
		isShowOpenAcct =  false;
	}
	if (StringUtils.equals(orderExt.getIs_aop(), EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB)) { //总商通道不显示写卡
		isShowWriteCardContents =  false;
	}
	if (!StringUtils.equals(isWirteCard, EcsOrderConsts.IS_DEFAULT_VALUE)){//不需要写卡的不显示写卡
		isShowWriteCardContents =  false;
	}
%>

</head>
<body>
<input type="hidden" id=isShowOpenAcct value="<%=isShowOpenAcct %>">
<form action="javascript:void(0);" id="writeCardForm" method="post">

	<div class="new_right">
        <div class="right_warp">
             <!-- 顶部公共 -->
            <input type="hidden" value='${order_id}' id="orderId"/>
            <div id="auto_flows_div">
     			<jsp:include page="auto_flows.jsp?order_id=${order_id }" />
     		</div>
     		<% if (isShowWriteCardContents) { //控制加载写卡信息 %>
        	<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>写卡信息</h2>
                <div class="grid_n_cont">
                	<div class="grid_n_cont_sub">
                    	<h3>
                    	   	开户对象：套卡<span id="custNum"><html:orderattr order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="text"></html:orderattr></span>
                    	  	<a href="javascript:void(0);" class="blue_b" 
	                    	  	<% if (isShowWriteCardBtn) {%>
	                    	  		style="margin-left:5px;" id="refreshCardList"
	                    	  	<% }else{%>
	                    	  		style="margin-left:5px;border:1px solid #DDD;background-color:#F5F5F5;color:#ACA899;a:hover{};" id="refreshCardListNone"
	                    	  	<% }%>
                    	  	>刷新读卡器列表</a>
                    	  	<a href="javascript:void(0);" class="blue_b" 
	                    	  	<% if (isShowWriteCardBtn) {%>
	                    	  		style="margin-left:5px;" id="writeCardBtn"
	                    	  	<% }else{%>
	                    	  		style="margin-left:5px;border:1px solid #DDD;background-color:#F5F5F5;color:#ACA899;a:hover{};" id="writeCardBtnNone"
	                    	  	<% }%>
                    	  	>写卡</a> 
                    	  	<a href="javascript:void(0);" id="openAcctBtn" class="blue_b"  
	                    	  	<% if (isShowOpenAcct) {%>
	                    	  		orderbtns="btn" name="o_open_acct"
	                    	  		ac_url="shop/admin/orderFlowAction!flowDealOpenAcct.do" 
	                    	  		show_type="ajaxSubmit" 
	                    	  		hide_page=",list,unvisabled," 
	                    	  		order_id="${order_id }" 
	                    	  		form_id="openAccountForm" 	                    	  		
	                    	  		callcack_fn="dealCallbackRefreshNewAcc" 
	                    	  		style="margin-left:5px;"
	                    	  	<% }else{%>
	                    	  		style="margin-left:5px;border:1px solid #DDD;background-color:#F5F5F5;color:#ACA899;a:hover{};"
	                    	  	<% }%>
	                    	>开户</a>
                    	</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                          <tr>
                            <th><span>*</span>请选择写卡器：</th>
                            <td class="title_desc">
		                    <select id="cardList" class="ipt_new" style="width:120px;"></select>
		                    </td>
		                    <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <th><span>*</span>卡ICCID：</th>
                            <td id="ICCID"></td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <th><span>*</span>读卡：</th>
                            <td id="readCard" class="step01">未开始</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            
                            <th><span>*</span>开户：</th>
                            <td id="openAccount" class="step02">未开始</td>
                            <th>&nbsp;</th>
                            <td><input id="openAccountStatus" type="hidden" value='<%=CommonDataFactory.getInstance().getOrdOpenActStatus(order_id)%>'/></td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <th><span>*</span>获取写卡数据：</th>
                            <td id="getCardInfo" class="step03">未开始</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <th><span>*</span>写卡：</th>
                            <td id="writeCard" class="step04">未开始</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <th><span>*</span>完成：</th>
                            <td id="finish" class="step05">未完成</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                        </table>
                    </div>
                </div>
            </div>
            <% } %>
            <div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>订单基本信息</h2>
                <div class="grid_n_cont">
            		<div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
        	        <!-- 基本信息开始 -->
        	        <div class="grid_n_cont_sub">
                        <h3>基本信息1：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                           <tr>
	                        <th>内部订单编号：</th>
	                        <td>${order_id}</td>
	                        <th>外部订单编号：</th>
	                        <td><html:orderattr order_id="${order_id}" field_name="out_tid"  field_desc ="外部订单编号" field_type="text"></html:orderattr></td>
	                        <th>生产模式：</th>
	                        <td><html:orderattr disabled="disabled" attr_code="DC_MODE_OPER_MODE" order_id="${order_id}" field_name="order_model"  field_desc ="生产模式" field_type="select"></html:orderattr></td>
	                      </tr>
	                      <tr>
	                        <th>外部状态：</th>
	                        <td><html:orderattr disabled="disabled" attr_code="DIC_ORDER_EXT_STATUS" order_id="${order_id}" field_name="platform_status"  field_desc ="外部状态" field_type="select"></html:orderattr></td>
	                        <th>归属地市：</th>
	                        <td><html:orderattr disabled="disabled" attr_code="DC_MODE_REGION" order_id="${order_id}" field_name="order_city_code"  field_desc ="归属地市" field_type="select"></html:orderattr></td>
	                        <th>订单来源：</th>
	                        <td><html:orderattr disabled="disabled" attr_code="ORDER_FROM" order_id="${order_id}" field_name="order_from"  field_desc ="订单来源" field_type="select"></html:orderattr></td>
	                      </tr>
	                      <tr>
	                        <th>订单来源系统：</th>
	                        <td><html:orderattr disabled="disabled"  attr_code="DIC_PLAT_TYPE" order_id="${order_id}" field_name="plat_type"  field_desc ="订单来源系统" field_type="select"></html:orderattr></td>
	                        <th>订单类型：</th>
	                        <td><html:orderattr disabled="disabled"  attr_code="DC_ORDER_NEW_TYPE" order_id="${order_id}" field_name="order_type"  field_desc ="订单类型" field_type="select" ></html:orderattr></td>
	                        <th>下单时间：</th>
	                        <td><html:orderattr disabled="disabled"  order_id="${order_id}"  field_name="tid_time"  field_desc ="下单时间" field_type="date"></html:orderattr></td>
	                    </tr>
                        <tr>
	                        <th>订单处理类型：</th>
                            <td><html:orderattr disabled="disabled"  order_id="${order_id}" attr_code="DIC_ORDER_DEAL_TYPE" field_name="order_deal_type"  field_desc ="订单处理类型" field_type="select"></html:orderattr></td>
                        	<th>发展人编码：</th>
	                        <td><html:orderattr order_id="${order_id}" field_name="development_code"  field_desc ="发展人编码" field_type="input"></html:orderattr></td>
	                        <th>发展人名称：</th>
	                        <td><html:orderattr order_id="${order_id}" field_name="development_name"  field_desc ="发展人名称" field_type="input"></html:orderattr></td>
	                    </tr>
                        <tr>
	                        <th>推广渠道：</th>
	                        <td><html:orderattr  order_id="${order_id}" field_name="reserve4"  field_desc ="推广渠道" field_type="text"></html:orderattr></td>
	                        <th>订单发展归属：</th>
	                        <td>
                               <html:orderattr disabled="disabled"  attr_code="DIC_CHANNEL_MARK" order_id="${order_id}" field_name="channel_mark"  field_desc ="订单发展归属" field_type="select"></html:orderattr>
                            </td>
                            <th>特殊业务类型：</th>
                            <td><html:orderattr disabled="disabled" attr_code="DC_BUSINESS_TYPE" order_id="${order_id }" field_name="special_busi_type" field_desc="业务类型" field_type="select" ></html:orderattr></td>
	                    </tr>
                       <tr>
                            <th>实名制方式：</th>
                            <td><html:selectdict disabled="true" curr_val="${orderTree.orderRealNameInfoBusiRequest.later_active_flag }" attr_code="DC_LATER_ACTIVE_FLAG"  appen_options="<option>-----请选择-----</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                            <th>证件照上传状态：</th>
                            <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="if_Send_Photos"  field_desc ="照片上传状态" field_type="select"></html:orderattr></td>
                            <th>激活状态：</th>
                            <td><html:selectdict disabled="true" curr_val="${orderTree.orderRealNameInfoBusiRequest.active_flag }" attr_code="DC_ACTIVE_FLAG"  appen_options="<option>-----请选择-----</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                      	</tr>
                        <tr>
                            <th>证件审核状态：</th>
                            <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="account_vali"  field_desc ="开户人身份验证" field_type="select"></html:orderattr></td>
                            <th>撤单状态：</th>
                            <td><html:selectdict disabled="true" curr_val="${orderTree.orderRealNameInfoBusiRequest.cancel_flag }" attr_code="DC_CANCEL_FLAG"  appen_options="<option>-----请选择-----</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                            <th>订单签收状态：</th>
                            <td><html:selectdict disabled="true" curr_val="${delivery.sign_status }" attr_code="DC_SIGN_STATUS"  appen_options="<option>-----请选择-----</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                      	</tr>
                        <!-- 新补的 -->
                        <tr>
	                          <th>bss开户工号：</th>
		                      <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="bss_operator"  field_desc ="bss开户工号" field_type="text"></html:orderattr></td>
		                      <th>订单总价(元)：</th>
	                          <td><%= CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest().getOrder_amount()%></td>
	                          <th>实收金额(元)：</th>
	                          <td><html:orderattr order_id="${order_id}" field_name="order_realfee"  field_desc ="实收金额（元）" field_type="text"></html:orderattr></td>
                    	 </tr>
                         <tr>
                              <th>商品原价(系统价)：</th>
                              <td>${good_price_system}</td>
                              <th>靓号预存(元)：</th>
                              <td>${num_price}</td>
                              <th>多缴预存(元)：</th>
                              <td>${deposit_price}</td>
                         </tr>
                         <tr>                          	
                              <th><span>*</span>开户费（元）：</th>
                                <td>${openAcc_price}</td>
                                <th><span>*</span>ESS营业款：</th>
	                             <td><html:orderattr  order_id="${order_id}" field_name="ess_money"  field_desc ="ESS营业款" field_type="input"></html:orderattr></td>
                              	<th><span>*</span>BSS缴费款：</th>
	                             <td><html:orderattr  order_id="${order_id}" field_name="busi_money"  field_desc ="BSS缴费款" field_type="input"></html:orderattr></td>
                                </tr>
                                 
                         </tr>
                        </table>
                    </div>
        	        <!-- 基本信息结束 -->
        	         <div class="grid_n_div">
		            	<h2><a href="#" class="openArrow"></a>客户历史订单</h2> 
		              	<div class="grid_n_cont">
		              		<div id="order_his_before" style="height: 80px;" ></div>
              		<div id="order_his" style="height: 80px;display:none" ></div>
		                </div>
		              </div>
		              <div class="grid_n_div">
		            	<h2><a href="#" class="openArrow"></a>订单修改记录</h2> 
		              	<div class="grid_n_cont">
		              		<div id="order_change" style="height: 80px"></div>
		                </div>
		              </div>
        	    </div>
        	</div>
        	<!-- 订单基本信息结束-->
        	<!-- 引用买家和卖家留言 -->
        	<jsp:include page="buyerAndSalerInfo.jsp?order_id=${order_id}"/>
        	<!-- 商品信息开始 -->
            <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>商品信息</h2>
              	<div class="grid_n_cont">
           		  <div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
                    <div class="grid_n_cont_sub">
                        <h3>商品信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              <th>商品名称：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="GoodsName"  field_desc="商品名称" field_type="text"></html:orderattr></td>
                              <th>商品类型：</th>
                              <td><html:orderattr disabled="disabled" order_id="${order_id}" attr_code="DC_MODE_GOODS_TYPE" field_name="goods_type"  field_desc ="商品类型" field_type="select"></html:orderattr></td>
                              <th>仓库名称：</th>
                              <td><html:orderattr disabled="disabled" attr_code="DIC_MT_STOREHOUSE" order_id="${order_id}" field_name="house_id"  field_desc ="仓库名称" field_type="select"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th>上网时长：</th>
                              <td><%=CommonDataFactory.getInstance().getProductSpec(order_id,SpecConsts.TYPE_ID_20001,null,SpecConsts.CARD_TIME) %></td>
                              
                            <th>是否老用户：</th>
                            <td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="is_old"  field_desc ="是否老用户" field_type="select"></html:orderattr></td>
                            </tr>
                            <tr>
                              <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_20002))){ %>
                              <th>sim卡号</th>
                              <td><html:orderattr   order_id="${order_id}" field_name="simid"  field_desc ="sim卡号" field_type="input" ></html:orderattr></td>
                              <%}%>                              
                              <%
                                  String isOld = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
							      if(EcsOrderConsts.IS_OLD_1.equals(isOld)){
							  %>
							  <th>用户号码：</th>
                              <td><html:orderattr  disabled="disabled"   order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="text"></html:orderattr></td>
                              	     
                              <%} %>
	                        <th><c:if test="${orderTree.orderRealNameInfoBusiRequest.later_active_flag=='1'  }">
			      			<span>*</span>
			      			</c:if>开户流水号：</th>
                          	<td><html:orderattr order_id="${order_id}" field_name="active_no"  field_desc ="开户流水号" field_type="input"></html:orderattr></td>
                         </tr>
                        </table>
                    </div>
                    <!-- 用户证件信息开始 -->
                     <div class="grid_n_cont_sub">
                    	<h3>用户证件信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th>证件类型：</th>
                                <td>
                                    <html:orderattr attr_code="DIC_CARD_TYPE"  order_id="${order_id}" disabled="disabled" field_name="certi_type"  field_desc ="证件类型" field_type="select"></html:orderattr>
                                </td>
                                <th>证件号码：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="cert_card_num"  field_desc ="证件号码" field_type="text"></html:orderattr></td>
                                <th>证件地址：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="cert_address"  field_desc ="证件地址" field_type="text"></html:orderattr></td>
                            </tr>
                            <tr>
                                <th>客户名称：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="phone_owner_name"  field_desc ="客户名称" field_type="text"></html:orderattr></td>
                                <th>客户类型：</th>
                                <td>
                                   <html:orderattr attr_code="DIC_CUSTOMER_TYPE"  order_id="${order_id}" field_name="CustomerType" disabled="disabled"  field_desc ="客户类型" field_type="select"></html:orderattr>
                                </td>
                                <th>证件有效期：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="cert_failure_time"  field_desc ="证件有效期" disabled="disabled" field_type="date"></html:orderattr></td>
                            </tr>
                            <tr>
                            
                        </table>
                    </div>
                    <!-- 用户证件信息结束 -->
                    <!-- 终端信息开始 -->
                    <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id,SpecConsts.TYPE_ID_10000))){
                	%>
                     <div class="grid_n_cont_sub">
                    	<h3>终端信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                        <tr>
                           	<th>颜色：</th>
                           	<td>
                           	 <%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.COLOR_NAME) %>
                           	</td>
                           	<th>容量：</th>
                           	<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.SIZE) %></td>
                           	<th>型号：</th>
                           	<td>
                             	<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, AttrConsts.MODEL_NAME) %>
                            </td>
                         </tr>
                         <tr>
                            <th>品牌：</th>
                          	<td>
                              <%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.BRAND_NAME) %>
                          	</td>
                          	<th><span>*</span>终端串号：</th>
                          	<td>
                             <html:orderattr  order_id="${order_id}" field_name="terminal_num"  field_desc ="串号" field_type="input" ></html:orderattr>
                          	</td>
                         </tr>
                        </table>
                     </div>
                     <%} %>
                    <!-- 终端信息结束 -->
                    <!-- 套餐信息开始 -->
                    <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10002))){%>
                   
                      <div class="grid_n_cont_sub">
                      	<h3>套餐信息：</h3>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              <th>套餐名称：</th>
                              	<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE) %></td>
                              	<th>首月资费方式：</th>
                              	<td><html:orderattr disabled="disabled" attr_code="DIC_OFFER_EFF_TYPE"  order_id="${order_id}" field_name="first_payment"  field_desc ="首月资费方式" field_type="select"></html:orderattr>
                                </td>
                              <th>&nbsp;</th>
                              <td>&nbsp;</td>
                            </tr>
                         <tr>
                               <th>可选包：</th>
                               <td colspan="5">
                                   <% 
                                   int  packageListSize = CommonDataFactory.getInstance().getOrderTree(order_id).getPackageBusiRequests().size();
                                   for(int i=0;i<packageListSize;i++){
                                	    String  packageName = CommonDataFactory.getInstance().getOrderTree(order_id).getPackageBusiRequests().get(i).getPackageName();
                                     	String  elementName = CommonDataFactory.getInstance().getOrderTree(order_id).getPackageBusiRequests().get(i).getElementName();
                                     	out.print(packageName+"："+elementName);
                                     	if(i!=packageListSize-1){
                                     		out.print("，");
                                     	}
                                     		
                                    }
                                   %>
                                 
                               </td>
                            </tr>
                        </table>
                     </div>
                     <%} %>
                      <!-- 套餐信息结束 -->
                       <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10001))){%>
               	  
                     <div class="grid_n_cont_sub">
                      	<h3>合约计划：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>合约编码：</th>
                              	<td><%=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.OUT_PACKAGE_ID) %></td>
                              	<th>合约名称：</th>
                              	<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.GOODS_NAME)%></td>
                                <th>合约期限：</th>
                              	<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT) %></td>
                            </tr>
                            <tr>
                              	<th>货品类型：</th>
                              	<td>
                              	   <html:selectdict name="contract.type"  disabled="true" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID)%>"  appen_options="<option vlaue=''>-----请选择-----</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                              	</td>
                               	<th>货品小类：</th><!-- 就是合约类型 -->
                               	<td>
                               	  <select name="contract.cat" disabled="disabled"  style="width:200px;" class="ipt_new" >
                               	     <option value='<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID)%>' selected="selected"></option>
                               	  </select>
                               	</td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
                            <script type="text/javascript">
                              var contract_type_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID)%>';
                              var contract_cat_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID)%>';
                             $("[name='contract.type'] option[value='"+contract_type_id+"']").attr("selected","selected");
                             TypeFun.getCatList("contract.type","contract.cat");
                            </script>
                    	</table>
                     </div>
                     <%}%>
                   <!-- 号码开户信息开始 -->
                      <%
						  //存在成品卡和白卡类型  和号码类型
						  String  is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
						  String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
						  String str1 = SpecConsts.TYPE_ID_20000;//号卡
						  String str2 = SpecConsts.TYPE_ID_20001;//上网卡
						  String str3 = SpecConsts.TYPE_ID_20002;//合约机
						  if((goods_type.equals(str1)||goods_type.equals(str2)||goods_type.equals(str3))&&!EcsOrderConsts.IS_OLD_1.equals(is_old)){
						%>
                   <div class="grid_n_cont_sub">
                      	<h3>号码开户信息：</h3>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                      	  <tr>
                      	    <th>用户号码：</th>
                            <td><html:orderattr  order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="text"></html:orderattr></td>
                            <th>卡类型：</th>
                            <td>
                               <html:orderattr  disabled="disabled" attr_code="DC_PRODUCT_CARD_TYPE"  order_id="${order_id}" field_name="white_cart_type"  field_desc ="卡类型" field_type="select"></html:orderattr>
                            </td>
                            <th>入网地区：</th>
                            <td><html:orderattr  disabled="disabled"  attr_code="DC_MODE_REGION"   order_id="${order_id}" field_name="net_region"  field_desc ="入网地区" field_type="select"></html:orderattr></td>
                          </tr>
                          <tr>
                                <th>是否靓号：</th>
                              	<td>
                              	   <html:orderattr  disabled="disabled"  attr_code="DIC_IS_LIANG" order_id="${order_id}" field_name="is_liang"  field_desc ="是否靓号" field_type="select"></html:orderattr>
                                </td>
                              	<th>靓号预存：</th>
                              	<td><html:orderattr  disabled="disabled"   order_id="${order_id}" field_name="liang_price"  field_desc ="靓号预存" field_type="text"></html:orderattr></td>
                                <th>靓号低消：</th>
                                <td><%=CommonDataFactory.getInstance().getNumberSpec(CommonDataFactory.getInstance().getAttrFieldValue(order_id, "phone_num"), SpecConsts.NUMERO_LOWEST) %></td>
                          </tr>
                          <tr> 
                          		<th>SIM卡号：</th>
	                            <td colspan="3" id="iccidInput"><html:orderattr style="width:400px;" order_id="${order_id}" field_name="ICCID"  field_desc ="卡串号" field_type="input"></html:orderattr></td>
	                          	<th>共享子号：</th>
                            	<td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="sub_no"  field_desc ="共享子号" field_type="text"></html:orderattr></td>
                          </tr>
                      	</table>
                   </div>
                   <!-- 号码开户信息结束 -->
                 </div>
                 <%} %>
             </div>
        	<!-- 处理意见 -->
        	<jsp:include page="order_handler.jsp?order_id=${order_id }"/>
         </div>
    </div>

</form>
</body>
<% if (isShowWriteCardContents) { //控制加载写卡模块 %>
	<OBJECT id="Ocxtest" classid=clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93><PARAM NAME="_Version" VALUE="65536"><PARAM NAME="_ExtentX" VALUE="2646"><PARAM NAME="_ExtentY" VALUE="1323"><PARAM NAME="_StockProps" VALUE="0"></OBJECT>
	<script src="<%=request.getContextPath() %>/ecs_ord/js/RwCardExtract.js"></script>
	<script src="<%=request.getContextPath() %>/ecs_ord/js/writeCard.js"></script> 
<%} %>


<script type="text/javascript">

function load_ord_his(){
	//$("#table_show").show();
	//$("#order_his").show();
	  $("#table_show").toggle();
   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};

$(function(){
 AutoFlow.checkMsg();
});
$(function(){
	//CommonLoad.loadJSP('relations_info','< %=request.getContextPath() %>/shop/admin/orderFlowAction!getRealtionOrders.do',{order_id:"${order_id }",ajax:"yes",includePage:"relations_info"},false,null,true);
	CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
	CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);
	//CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
 });
</script>