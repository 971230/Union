<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>质检稽核</title>
<script src="<%=request.getContextPath()%>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/quality_audit.js?ver=<%=new Date().getTime()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<style>
.goodTit{ border-bottom:1px dashed #c3c3c3; margin-left:5px; margin-right:5px; height:24px; line-height:24px;}
.goodTit .redFont{ color:#f00; font-weight:bold; margin-left:5px;}
</style>
</head>
<script type="text/javascript">
//货品类型
var TypeFun={
	 getCatList:function(typeSel,catSel){
			   var product_type = $("[name='"+typeSel+"']").val();
			   var product_cat =  $("[name='"+catSel+"']").val();
			   $.ajax({
		        	url:app_path+"/shop/admin/orderFlowAction!getCatListByProdType.do?ajax=yes&product_type="+product_type,
		        	dataType:"json",
		        	data:{},
		        	success:function(reply){
		        		var catList = reply;
		        		var catHtmlStr = "<option value=''>--请选择--</option>";
		        		for(var i=0;i<catList.length;i++){
		        			var obj = catList[i];
		        			if(product_cat==obj.cat_id){
		        				catHtmlStr += "<option value='"+obj.cat_id+"' selected='selected'>"+obj.name+"</option>";
		        			}else{
		        				catHtmlStr += "<option value='"+obj.cat_id+"'>"+obj.name+"</option>";
		        			}
		        		}
		        	    $("[name='"+catSel+"']").empty().html(catHtmlStr);
		        	}
				});
			   
	 }
}
</script>
<%
	String order_id = (String) request.getAttribute("order_id");
%>
<body>
	<form id="qualityAuditForm">
	<input type="hidden" value="<%=order_id %>" id="order_id"/>
		<div class="gridWarp">
			<div class="new_right">
				<div class="right_warp">
					<jsp:include page="auto_flows.jsp?order_id=${order_id }" />
					
					
					<!-- 关联订单显示开始-->
		         <!--    <div class="grid_n_div">
			            <h2>
			            <a href="javascript:void(0);" class="closeArrow"></a><label for="checkbox000">关联订单信息</label>
			            <input type="checkbox" name="checkbox_quality" id="checkbox000" />
			            </h2> 
			           	<div class="grid_n_cont">
			    		  	<div id="relations_info" style="height: 40px"></div>
			            </div>
		            </div> -->
		            <!-- 关联订单显示结束 -->
					
					<div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="closeArrow"></a><label for="checkbox0">订单基本信息</label>
							<input type="checkbox" name="checkbox_quality" id="checkbox0" />
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
										<input type="hidden" name="orderTree.orderExtBusiRequest.out_tid" value="${orderTree.orderExtBusiRequest.out_tid }" />
										<th><span>*</span>外部订单编号：</th>
										<td>${orderTree.orderExtBusiRequest.out_tid }</td>
										<input type="hidden" name="orderTree.orderExtBusiRequest.order_id" value="${orderTree.orderExtBusiRequest.order_id }" />
										<th><span>*</span>生产模式：</th>
										<td><html:orderattr  attr_code="DC_MODE_OPER_MODE" order_id="${order_id}" disabled="disabled" field_name="order_model"  field_desc ="生产模式" field_type="select"></html:orderattr></td>
										<input type="hidden" name="orderTree.orderExtBusiRequest.order_model" value="${orderTree.orderExtBusiRequest.order_model }" />
									</tr>
									<tr>
										<th><span>*</span>外部订单状态：</th>
										<td><html:orderattr attr_code="DIC_ORDER_EXT_STATUS" order_id="${order_id}" field_name="platform_status"  field_desc ="外部状态" field_type="select" disabled="disabled" ></html:orderattr></td>
										<th><span>*</span>归属区域：</th>
										<td><html:orderattr attr_code="DC_MODE_REGION" order_id="${order_id}" field_name="order_city_code" field_desc="归属地市" field_type="select" disabled="disabled" ></html:orderattr></td>
										<th><span>*</span>订单来源：</th>
										<td> <html:orderattr disabled="disabled"  attr_code="ORDER_FROM"  order_id="${order_id}" field_name="order_from"  field_desc ="订单来源" field_type="select" ></html:orderattr></td>
										<input type="hidden" name="orderTree.orderExtBusiRequest.order_from" value="${orderTree.orderExtBusiRequest.order_from }" />
									</tr>
									<tr>
										<th><span>*</span>下单时间：</th>
										<td>${orderTree.orderExtBusiRequest.tid_time }</td>
										<input type="hidden" name="orderTree.orderExtBusiRequest.tid_time" value="${orderTree.orderExtBusiRequest.tid_time }" />
										<th>订单处理类型：</th>
										<td><html:orderattr disabled="disabled" attr_code="DIC_ORDER_DEAL_TYPE" order_id="${order_id }" field_name="order_deal_type" field_desc="订单处理类型" field_type="select" /></td>
										<th>特殊业务类型：</th>
                          				<td><html:orderattr disabled="disabled" attr_code="DC_BUSINESS_TYPE" order_id="${order_id }" field_name="special_busi_type" field_desc="业务类型" field_type="select" /></td>
									</tr>
									  <!-- 新补的 -->
                        <tr>
	                        <th>发展人编码：</th>
	                        <td><html:orderattr  order_id="${order_id}" field_name="development_code"  field_desc ="发展人编码" field_type="text"></html:orderattr></td>
	                        <th>推广渠道：</th>
	                        <td><html:orderattr  order_id="${order_id}" field_name="reserve4"  field_desc ="推广渠道" field_type="text"></html:orderattr></td>
	                        <th>订单发展归属：</th>
	                        <td>
                               <html:orderattr disabled="disabled"  attr_code="DIC_CHANNEL_MARK" order_id="${order_id}" field_name="channel_mark"  field_desc ="订单发展归属" field_type="select"></html:orderattr>
                            </td>
                            
	                    <tr>
	                     <th>集团编码：</th>
                         <td><html:orderattr  order_id="${order_id}" field_name="group_code"  field_desc ="集团编码" field_type="text"></html:orderattr></td>
                         <th>所属用户: </th>
	                     <td>
	                      <html:orderattr  order_id="${order_id}" field_name="reserve7"  field_desc ="所属用户" field_type="text" ></html:orderattr>
                         </td>
	                     <th>业务凭证号: </th>
	                     <td><html:orderattr   order_id="${order_id}" field_name="busi_credence_code"  field_desc ="业务凭证号" field_type="text"></html:orderattr></td>
	                    </tr>
                            <tr>
                                <th>实名制方式：</th>
                                <td><html:selectdict disabled="true" curr_val="${orderTree.orderRealNameInfoBusiRequest.later_active_flag }" attr_code="DC_LATER_ACTIVE_FLAG"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                                <th>证件照上传状态：</th>
                                <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="if_Send_Photos"  field_desc ="照片上传状态" field_type="select"></html:orderattr></td>
                                <th>激活状态：</th>
                                <td><html:selectdict disabled="true" curr_val="${orderTree.orderRealNameInfoBusiRequest.active_flag }" attr_code="DC_ACTIVE_FLAG"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                          	</tr>
                            <tr>
                                <th>证件审核状态：</th>
                                <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="account_vali"  field_desc ="开户人身份验证" field_type="select"></html:orderattr></td>
                                <th>撤单状态：</th>
                                <td><html:selectdict disabled="true" curr_val="${orderTree.orderRealNameInfoBusiRequest.cancel_flag }" attr_code="DC_CANCEL_FLAG"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                                <th>订单签收状态：</th>
                                <td><html:selectdict disabled="true" curr_val="${delivery.sign_status }" attr_code="DC_SIGN_STATUS"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
	                    </tr>
	                    <%--  需要找产品里面的优惠列表 根据优惠类型是优惠券去查 暂时不展示
	                    <tr>
	                       <th><span>*</span>优惠券金额（元）：</th>
                           <td><html:orderattr  order_id="${order_id}" field_name=""  field_desc ="优惠券金额（元）" field_type="input"></html:orderattr></td>
                        </tr>
	                   --%>
	                    <!-- 新补的 -->
								</table>
							</div>
        	        <!-- 优惠信息开始 -->
        	  		<c:if test="${orderTree.discountInfoBusiRequests!=null}">
                    <div class="grid_n_cont_sub">
                    <h3>优惠信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                           <c:forEach var="discount" items="${orderTree.discountInfoBusiRequests}">
	                            <tr>
	                              <th>优惠活动类型：</th>
	                              <td>
	                              <html:selectdict disabled="true"  curr_val="${discount.activity_type }" attr_code="DC_DISCOUNT_TYPE"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
	                              </td>
	                              <th>代金活动编码：</th>
	                              <td>${discount.activity_code}</td>
	                              <th>代金活动名称：</th>
	                              <td>${discount.activity_name}</td>
	                            </tr>
                           </c:forEach>
                          </table>
                    </div>
             		</c:if>
        	        <!-- 优惠信息结束 -->
							<div class="grid_n_cont_sub">
								<h3>支付信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
									<tr>
										<th><span>*</span>支付时间：</th>
										<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="pay_time"  field_desc ="支付时间" field_type="date"></html:orderattr></td>
										<input type="hidden" name="orderTree.orderBusiRequest.pay_time" value="${orderTree.orderBusiRequest.pay_time }" />
										<th><span>*</span>支付类型：</th>
										<td><html:orderattr attr_code="DIC_PAY_TYPE" order_id="${order_id}" field_name="paytype" field_desc="支付类型" field_type="select" disabled="disabled" ></html:orderattr></td>
										<th><span>*</span>支付方式：</th>
										<td> <html:orderattr attr_code="DIC_PAY_WAY" order_id="${order_id}" field_name="pay_method"   field_desc ="支付方式" field_type="select" disabled="disabled" ></html:orderattr></td>
									</tr>
									<tr>
										<th><span>*</span>支付状态：</th>
										<td><html:orderattr disabled="disabled" attr_code="DIC_PAY_STATUS" order_id="${order_id}" field_name="pay_status" field_desc="支付状态" field_type="select"></html:orderattr></td>
									     <th>基金类型：</th>
                                         <td><html:orderattr disabled="disabled" attr_code="DC_ISFUND_TYPE" order_id="${order_id}" field_name="fund_type"  field_desc ="基金类型" field_type="select"></html:orderattr></td>
                                         <th>兑换积分：</th>
                                         <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="order_points"  field_desc ="兑换积分" field_type="input"></html:orderattr></td>
									</tr>
		                        <tr>
		                          <th><span>*</span>订单总价（元）：</th>
		                          <td>  
		                            <%= CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest().getOrder_amount()%>
		                          </td>
		                          <th><span>*</span>实收金额（元）：</th>
		                          <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="order_realfee"  field_desc ="实收金额（元）" field_type="input"></html:orderattr></td>
		                    	 </tr>
		                          	<tr>
		                                <th><span>*</span>商品原价（系统价）：</th>
		                                <td>${good_price_system}</td>
		                                <th><span>*</span>靓号预存（元）：</th>
		                                <td>${num_price}</td>
		                                <th><span>*</span>多缴预存（元）：</th>
		                                <td>${deposit_price}</td>
		                          	</tr>
		                          	<tr>
		                                <th>bss开户工号：</th>
				                      	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="bss_operator"  field_desc ="bss开户工号" field_type="text"></html:orderattr></td>
				                       	<th><span>*</span>开户费（元）：</th>
		                                <td>${openAcc_price}</td>
		                                </tr>
										</table>
									</div>
							<div class="grid_n_cont_sub">
								<h3>买家信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_form">
									<tr>
										<th>买家留言：</th>
										<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="buyer_message"  field_desc ="买家留言" field_type="textarea"></html:orderattr></td>
										<th>卖家留言：</th>
										<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="seller_message"  field_desc ="卖家留言" field_type="textarea"></html:orderattr></td>
										<th>订单备注：</th>
										<td> <html:orderattr  order_id="${order_id}" field_name="service_remarks"   field_desc ="订单备注式" field_type="textarea" disabled="disabled" ></html:orderattr></td>
									</tr>
								</table>
							</div>
							<!-- 发票信息开始 -->
		                  	<div class="grid_n_cont_sub">
		                    	<h3>发票信息：</h3>
		                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
		                            <tr>
		                              	<th>发票类型：</th>
		                              	<td>
		                                    <html:orderattr disabled="disabled" attr_code="DIC_ORDER_INVOICE_TYPE"  order_id="${order_id}" field_name="invoice_type"  field_desc ="发票类型" field_type="select"  ></html:orderattr>
		                              	</td>
		                              	<th>发票打印方式：</th>
		                              	<td>
		                                    <html:orderattr disabled="disabled" attr_code="DIC_ORDER_INVOICE_PRINT_TYPE"  order_id="${order_id}" field_name="invoice_print_type"  field_desc ="发票打印方式" field_type="select" ></html:orderattr>
		                              	</td>
		                              	<th>发票抬头：</th>
		                              	<td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="reserve8"  field_desc ="发票抬头" field_type="input"></html:orderattr></td>
		                          	</tr>
		                            <tr>
		                              	<th>发票内容：</th>
		                              	<td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="invoice_group_content"  field_desc ="发票内容" attr_code="DIC_INVOICE_GROUP_CONTENT" field_type="select"></html:orderattr></td>
		                              	<th>发票号码：</th>
		                              	<td><html:orderattr  order_id="${order_id}" field_name="invoice_no"  field_desc ="发票号码" field_type="input"></html:orderattr></td>
		                             	<th>发票代码：</th>
		                              	<td><html:orderattr  order_id="${order_id}" field_name="invoice_code"  field_desc ="发票代码" field_type="input"></html:orderattr></td>
                           
		                            </tr>
		                    	</table>
		                  	</div>
		                  	<!-- 发票信息结束 -->
						</div>
					</div>
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
					<div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="openArrow"></a><label for="checkbox1">商品信息</label>
							<input type="checkbox" name="checkbox_quality" id="checkbox1" />
						</h2>
						<div class="grid_n_cont">
							<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
							<div class="grid_n_cont_sub">
								<h3>商品信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
									<tr>
										<th><span>*</span>商品名称：</th>
										<td colspan="3"><html:orderattr order_id="${order_id}" field_name="GoodsName" field_desc="商品名称" field_type="input" disabled="disabled" ></html:orderattr></td>
										<th><span>*</span>商品类型：</th>
										<td><html:orderattr  order_id="${order_id}" attr_code="DC_MODE_GOODS_TYPE" field_name="goods_type"  field_desc ="商品类型" field_type="select" disabled="disabled"></html:orderattr></td>
										
									</tr>
									<tr>
										<th>上网时长：</th>
										<td><%=CommonDataFactory.getInstance().getGoodSpec(order_id,null,SpecConsts.CARD_TIME) %></td>
										<th>商品备注：</th>
										<td><html:orderattr order_id="${order_id}" field_name="service_remarks" cols="45" field_desc="订单备注" field_type="input" style="width:200px;"></html:orderattr></td>
										<th>仓库名称：</th>
										<td><html:orderattr attr_code="DIC_MT_STOREHOUSE" order_id="${order_id}" field_name="house_id" field_desc="仓库名称" field_type="select"></html:orderattr></td>
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
												field_desc="证件类型" field_type="select" disabled="disabled"></html:orderattr></td>
										<th>证件号码：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="cert_card_num" field_desc="证件号码"
												field_type="input" disabled="disabled"></html:orderattr></td>
										<th>证件地址：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="cert_address" field_desc="证件地址"
												field_type="input" disabled="disabled"></html:orderattr></td>
									</tr>
									<tr>
										<th>客户名称：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="phone_owner_name" field_desc="客户名称"
												field_type="input" disabled="disabled"></html:orderattr></td>
										<th>客户类型：</th>
										<td><html:orderattr attr_code="DIC_CUSTOMER_TYPE"
												order_id="${order_id}" field_name="CustomerType"
												field_desc="客户类型" field_type="select" disabled="disabled"></html:orderattr></td>
										<th>证件有效期：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="cert_failure_time" field_desc="证件有效期"
												field_type="date" disabled="disabled"></html:orderattr></td>
									</tr>
								</table>
							</div>
							<%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10000))){
                           	%>
							<div class="grid_n_cont_sub">
								<h3>终端信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
									<tr>
										<th><span>*</span>颜色：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.COLOR_NAME)%></td>
										<th><span>*</span>容量：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.SIZE)%></td>
									</tr>
									<tr>
										<th><span>*</span>型号：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, AttrConsts.MODEL_NAME) %></td>
										<th><span>*</span>品牌：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.BRAND_NAME) %></td>
										<th><span>*</span>串号：</th>
										<%String isOnly = "disabled";
										if(EcsOrderConsts.GOODS_TYPE_PHONE.equals(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE))) {
											isOnly = "enabled";
										}
										%>										
										<td><html:orderattr order_id="${order_id}" field_name="terminal_num" field_desc="串号" field_type="input" disabled="<%=isOnly %>"></html:orderattr></td>
									</tr>
									
									</table>
									</div>
								<%} %>
							<%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10002))){%>
							<div class="grid_n_cont_sub">
								<h3>套餐信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
									<tr>
										<th><span>*</span>套餐名称：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id,SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE)%></td>
										<th><span>*</span>首月资费方式：</th>
										<td><html:orderattr attr_code="DIC_OFFER_EFF_TYPE" order_id="${order_id}" field_name="first_payment" field_desc="首月资费方式" field_type="select" disabled="disabled" ></html:orderattr></td>
									 	<th></th><td></td>
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
							<%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10001))){%>
							<div class="grid_n_cont_sub">
								<h3>合约信息：</h3>
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
									<tr>
										<th><span>*</span>合约名称：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.GOODS_NAME) %></td>
										<th><span>*</span>合约类型：</th>
										<td>
			                            <html:selectdict  disabled="disabled" name="contract.type" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID)%>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
			                            </td>
										<th><span>*</span>合约期限：</th>
										<td><%=CommonDataFactory.getInstance().getProductSpec(order_id,SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT)%></td>
									</tr>
									<tr>
										<th><span>*</span>合约编码：</th>
                              			<td><%=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.OUT_PACKAGE_ID) %></td>
										<th><span>*</span>货品类型：</th>
                              			<td>
                              	   		<html:selectdict name="contract.type1" disabled="disabled" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID)%>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                              			</td>
                               			<th><span>*</span>货品小类：</th><!-- 就是合约类型 -->
                               			<td>
                               	  		<select name="contract.cat" disabled="disabled"  style="width:200px;" class="ipt_new" >
                               	   		<option value='<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID)%>' selected="selected"></option>
                               	  		</select>
                               			</td>
									</tr>
                            		<script type="text/javascript">
                              		var contract_type_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID)%>';
                              		var contract_cat_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID)%>';
                             		$("[name='contract.type1'] option[value='"+contract_type_id+"']").attr("selected","selected");
                             		TypeFun.getCatList("contract.type","contract.cat");
                            		</script>
									</table>
		                    	<h3>活动下自选包：</h3>
		                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
								<c:forEach var="orderActivityBusiRequest" items="${orderTree.orderActivityBusiRequest}">
								<c:if test="${orderActivityBusiRequest.activity_type_zhufu=='1' }">
									<%{ int activity_package_count = 0;%>
									<c:forEach var="attrPackageActivityBusiRequest" items="${orderTree.attrPackageActivityBusiRequest }">
									<c:if test="${orderActivityBusiRequest.inst_id==attrPackageActivityBusiRequest.activity_inst_id }">
									<tr>
									<th>&nbsp;</th>
									<td>第<%=++activity_package_count%>包</td>
									<th>包编号：</th>
									<td>${attrPackageActivityBusiRequest.package_code }</td>
									<th>包名称：</th>
									<td>${attrPackageActivityBusiRequest.package_name }</td>
									</tr>
									<tr>
									<th>元素编码：</th>
									<td>${attrPackageActivityBusiRequest.element_code }</td>
									<th>元素类型：</th>
									<td>
									<html:selectdict disabled="true" curr_val="${attrPackageActivityBusiRequest.element_type }" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_ELEMENT_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
									</td>
									<th>元素名称：</th>
									<td>${attrPackageActivityBusiRequest.element_name }</td>
									</tr>
									</c:if>
									</c:forEach>
									<%} %>
								</c:if>
								</c:forEach>
		                        </table>
								</div>
								<%} %>
						<%
						  //存在成品卡和白卡类型  和号码类型
						  String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
						  String str1 = SpecConsts.TYPE_ID_20000;//号卡
						  String str2 = SpecConsts.TYPE_ID_20001;//上网卡
						  String str3 = SpecConsts.TYPE_ID_20002;//合约机
						  if(goods_type.equals(str1)||goods_type.equals(str2)||goods_type.equals(str3)){
						%>
							<div class="grid_n_cont_sub">
								  <h3>号码开户信息：</h3>
								   	<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_form">
									<tr>
									  <th>开户人姓名：</th>
									  <td><%=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME)%></td>
									  <th>&nbsp;</th>
									  <td>&nbsp;</td>
									  <th>&nbsp;</th>
									  <td>&nbsp;</td>
									</tr>
									<tr>
										<th><span>*</span>用户号码：</th>
										<td><html:orderattr order_id="${order_id}" field_name="phone_num" field_desc="用户号码" field_type="input" disabled="disabled" ></html:orderattr></td>
										<th>卡类型：</th>
										<td><html:orderattr disabled="disabled" attr_code="DC_PRODUCT_CARD_TYPE" order_id="${order_id}" field_name="white_cart_type" field_desc="卡类型" field_type="select" ></html:orderattr></td>
									    <th>活动类型：</th>
										<td><html:selectdict  disabled="disabled" name="ative_type" curr_val="<%=CommonDataFactory.getInstance().getActivitySpec(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DISCOUNT_ID), SpecConsts.PMT_TYPE)%>"  appen_options="<option>---请选择---</option>"   attr_code="DC_ACT_PMT_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
									</tr>
									<tr>
										<th><span>*</span>入网地区：</th>
										<td><html:orderattr order_id="${order_id}" field_name="net_region" attr_code="DC_MODE_REGION" field_desc="入网地区" field_type="select" disabled="disabled" ></html:orderattr></td>
										<th>号码网别：</th>
                              	        <td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE) %></td>
                             			<th>SIM卡号：</th>
				                        <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="ICCID"  field_desc ="卡串号" field_type="input"></html:orderattr></td>
				                    </tr>
									<tr>
										<th>靓号低消：</th>
										<td><%=CommonDataFactory.getInstance().getNumberSpec(CommonDataFactory.getInstance().getAttrFieldValue(order_id, "phone_num"), SpecConsts.NUMERO_LOWEST) %></td>
									    <th><span>*</span>是否靓号：</th>
										<td><html:orderattr disabled="disabled" attr_code="DIC_IS_LIANG" order_id="${order_id}" field_name="is_liang" field_desc="是否靓号" field_type="select" ></html:orderattr></td>
										<th>靓号预存：</th>
										<td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="liang_price" field_desc="靓号预存" field_type="input" ></html:orderattr></td>
								 	</tr>
									<tr>
									<th>共享子号：</th>
                            		<td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="sub_no"  field_desc ="共享子号" field_type="input"></html:orderattr></td>
                            		<th>副卡号码：</th>
                            		<td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="vicecard_no"  field_desc ="副卡号码" field_type="input"></html:orderattr></td>
									</tr>
								</table>
							</div>
						<%} %>
	               	  	<div class="grid_n_cont_sub" id="sp_product" style="height: 80px"></div>
		               	<div class="grid_n_cont_sub" id="sub_product">
	               	  	     <jsp:include page="include/sub_product.jsp"/>
						</div>               	  	
	               	  	<!-- ZX add 2015-12-30 start 副卡信息开始 -->
	               	  	<div class="grid_n_cont_sub">
	               	  	 	<jsp:include page="include/phone_info_fuka.jsp?order_id=${order_id }"/>
	               	  	</div>
	               	  	<!-- ZX add 2015-12-30 end 副卡信息结束-->
						</div>
					</div>
					<div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="openArrow"></a><label for="checkbox2">物流信息</label><input
								type="checkbox" name="checkbox_quality" id="checkbox2" />
								 <input type="hidden"  id="post_type" name="post_type" value="0"/> <!-- 物流类型 -->
								 
								 <!-- 物流单打印测试 star
								 <input hidden="hidden"  id="itmesIds" name="itmesIds" value="201411106371000910,201411189782001175"/> 
								 <input type="hidden"  name="orderIds"   value="SW201411039968860898,SW201411039968860898" /> 
								<input type="hidden"  name="logi_company"   value="ZY0002" />
								<input type="hidden"  name="logi_no"   value="2222" />
								<input type="hidden"  name="sending_type"   value="MJBY" />
								<input type="hidden"  name="n_shipping_amount"   value="5" />
						                 物流单打印测试  end--> 
						</h2>
						<div class="grid_n_cont">
							<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
							<div class="grid_n_cont_sub">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_form">
									<tr>
										<th><span>*</span>收货人姓名：</th>
										<td><html:orderattr order_id="${order_id}" field_name="ship_name" field_desc="收货人姓名" field_type="input"></html:orderattr></td>
										<th><span>*</span>收货人手机号码：</th>
										<td><html:orderattr order_id="${order_id}" field_name="receiver_mobile" field_desc="手机号码" field_type="input" field_value="${delivery.ship_mobile }"></html:orderattr></td>
										<th>电子邮件：</th>
										<td><html:orderattr order_id="${order_id}" field_name="ship_email" field_desc="电子邮件" field_type="input" field_value="${delivery.ship_email }"></html:orderattr></td>
									</tr>
									<tr>
										<th>收货人电话：</th>
										<td><html:orderattr order_id="${order_id}" field_name="reference_phone" field_desc="收货人电话(固话)" field_type="input" field_value="${delivery.ship_tel }"></html:orderattr></td>
										<th><span>*</span>收货省份：</th>
										<td><select name="provinc_code" field_desc="收货省份" id="provinc_code" class="ipt_new" style="width:200px;">
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
										<td><select name="city_code" field_desc="收货地市" id="city_code" class="ipt_new" style="width:200px;">
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
										<td><select name="district_id" field_desc="收货区县" id="district_id" class="ipt_new" style="width:200px;">
												<c:forEach var="districtList" items="${districtList}">
													<c:if test="${district_id==districtList.code}">
														<option value="${districtList.code}" selected="selected">${districtList.name}</option>
													</c:if>
													<c:if test="${district_id!=districtList.code}">
														<option value="${districtList.code}">${districtList.name}</option>
													</c:if>
												</c:forEach>
										</select></td>
										<th><span>*</span>详细地址：</th>
										<td><html:orderattr order_id="${order_id}" field_name="ship_addr" field_desc="详细地址" field_type="input" field_value="${delivery.ship_addr }"></html:orderattr></td>
										<th>邮政编码：</th>
										<td><html:orderattr order_id="${order_id}" field_name="ship_zip" field_desc="邮政编码" field_type="input" field_value="${delivery.ship_zip }"></html:orderattr></td>
									</tr>
									<tr>
										<th><span>*</span>物流公司名称：</th>
										<td>
										<select name="shipping_company" field_name="shipping_company" field_desc="物流公司"  id="logi_company" class="ipt_new" style="width:200px;">
												<option value="">---请选择---</option>
												<c:forEach var="logicompany" items="${logiCompanyList }">
													<option key_str="${logicompany.key_str }" value="${logicompany.id }">${logicompany.name }</option>
												</c:forEach>
										</select> 
										<input type="hidden"  name="shipping_company_name"  id="shipping_company_name" value="${shipping_company_name}" >
										<input type="hidden"  id="shipping_company" value="${delivery.shipping_company}" >
									    <script type="text/javascript">
											  var  ship_company = $("#shipping_company").val();
											  $("#logi_company option[value='"+ship_company+"']").attr("selected","selected");
											  var shipping_name = $("#logi_company").find("option:selected").text(); 
											  $("[name='shipping_company_name']").val(shipping_name);
											  
											  $("#logi_company").change(function(){
													var shipping_name =  $(this).find("option:selected").text(); 
													$("[name='shipping_company_name']").val(shipping_name);
											 });
										  </script>
										</td>
										<th><span>*</span>配送方式：</th>
										<td>
                                          <html:orderattr  attr_code="DC_MODE_SHIP_TYPE" order_id="${order_id}" field_name="sending_type"  field_desc ="配送方式" field_type="select" disabled="disabled"></html:orderattr>
                                          <input type="hidden" name="sending_type_id" value="${orderTree.orderBusiRequest.shipping_type }" />
                            	        </td>
										<th><span>*</span>配送时间：</th>
										<td><html:orderattr order_id="${order_id}" attr_code ='DIC_SHIPPING_TIME' field_name="shipping_time" field_desc="配送时间" field_type="select" field_value="${delivery.shipping_time }"></html:orderattr></td>
									</tr>
									<tr>
										<th><span>*</span>物流单号：</th>
										<td><html:orderattr order_id="${order_id}" field_name="logi_no" field_desc="物流单号" field_type="input" field_value="${delivery.logi_no }"></html:orderattr></td>
										<th>运费：</th>
										<td><html:orderattr order_id="${order_id}" field_name="n_shipping_amount" field_desc="实收金额（元）" field_type="input" field_value="${delivery.n_shipping_amount }"></html:orderattr></td>
										<th>保费率：</th>
										<td><input type="text" class="ipt_new" style="width:200px;" id="protect_fee"  name="protect_free" value="0" /></td>
									</tr>
									<tr name="quhuorenxinxi" style="none;">
										<th>物流人员：</th>
										<td><input type="text" class="ipt_new" style="width:200px;" id="logi_receiver"  name="logi_receiver" value="${delivery.logi_receiver }" /></td>
										<th>物流电话：</th>
										<td><input type="text" class="ipt_new" style="width:200px;" id="logi_receiver_phone" name="logi_receiver_phone" value="${delivery.logi_receiver_phone }" /></td>
										<th>是否需要回单：</th>
										<td><html:orderattr attr_code="DC_IS_OR_NO"  order_id="${order_id}" field_name="need_receipt"  field_desc ="是否需要回单" field_type="select" field_value="${delivery.need_receipt }" ></html:orderattr></td>
									</tr>									
								</table>
							</div>
						</div>
					</div>
					
					<!-- 关联订单显示开始-->
		         <!--    <div class="grid_n_div">
			            <h2>
			            <a href="javascript:void(0);" class="closeArrow"></a><label for="checkbox000">关联订单信息</label>
			            <input type="checkbox" name="checkbox_quality" id="checkbox000" />
			            </h2> 
			           	<div class="grid_n_cont">
			    		  	<div id="relations_info" style="height: 40px"></div>
			            </div>
		            </div> -->
		            <!-- 关联订单显示结束 -->
					
					<div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="openArrow"></a><label for="checkbox0">订单基本信息</label>
							<input type="checkbox" name="checkbox_quality" id="checkbox0" />
						</h2>
						<div class="grid_n_cont">
						<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
						<div class="grid_n_cont_sub" id="audit_order_base">
							
						</div>
						</div>
					</div>
					
					<div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="openArrow"></a><label for="checkbox1">商品信息</label>
							<input type="checkbox" name="checkbox_quality" id="checkbox1" />
						</h2>
						<div class="grid_n_cont">
						<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
							<div class="grid_n_cont_sub" id="audit_goods_info">
							
							</div>
	               	  	</div>
	               	  	
					</div>
					<c:if test="${giftInfoList!=null&&giftInfoSize>0}">
				   <div class="grid_n_div">
					<h2>
						<a href="javascript:void(0);" class="openArrow"></a><label for="checkbox2">礼品信息</label><input
							type="checkbox" name="checkbox_quality" id="checkbox2" />
					</h2>
					<div class="grid_n_cont">
						<div class="remind_div">
							<img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
							         	  	
		               	  	<div class="grid_n_cont_sub">
		               	  	 	<jsp:include page="include/view_goods_gift.jsp?order_id=${order_id }"/>
		               	  	</div>
	               	  	</div>
               	  	</div>
               	  	</c:if>
					
					<div class="ps_div" style="display: none;">
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
											<c:forEach var="attrGiftInfoBusiRequest" items="${orderTree.giftInfoBusiRequests }">
											 <c:if test="${attrGiftInfoBusiRequest.is_virtual=='0' }">
												<tr>
												 <td>
												    <%int k=0; %>
												    <c:forEach var="deliveryItem" items="${deliveryItemsQueryResp.deliveryItems }">
												      <c:if test="${attrGiftInfoBusiRequest.gift_inst_id==deliveryItem.col2 }">
												      		<% ++k;%>
												      		
												      </c:if>
												    </c:forEach>
												     <a href="javascript:void(0);"  id="items_add_btn_${attrGiftInfoBusiRequest.gift_inst_id }" <% if(k>0){%> style="display: none;"<%} %> class="items_add_btns"  title="${attrGiftInfoBusiRequest.gift_inst_id }"  >确认补寄</a>
												     <span id="items_add_text_${attrGiftInfoBusiRequest.gift_inst_id }" <% if(k==0){%> style="display: none ;"<%} %> >已确认</span>
												    <input type="hidden" id="gif_name_${attrGiftInfoBusiRequest.gift_inst_id  }" value="${attrGiftInfoBusiRequest.goods_name  }">
												 <input type="hidden" id="sku_num_${attrGiftInfoBusiRequest.gift_inst_id  }" value="${attrGiftInfoBusiRequest.sku_num  }">
												 </td> 
													<td>${attrGiftInfoBusiRequest.goods_name }</td>
													<td>${attrGiftInfoBusiRequest.sku_num }</td>
												</tr>
											  </c:if>
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
										<td colspan="3" style=" text-align:left; padding-left:5px;">
											<a href="javascript:void(0);" id="xinzeng"><i class="icon_add"></i>新增</a>
											<div id="zengpinneirong" style="display: none">
												赠品名称：<input class="ipt_new" type="text" id="reissue_info" name="reissue_info" /> 
												赠品数量：<input class="ipt_new" type="text" id="reissue_num" name="reissue_num" /> 
												<a href="javascript:void(0);" class="blueBtns" id="luru"><span>录 入</span></a> 
												<a href="javascript:void(0);" class="blueBtns" id="quxiao"><span>取 消</span></a>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="grid_n_div">
						<h2>
							<label for="checkbox00">通过
								<input type="checkbox" name="checkbox_pass" id="checkbox00" />
							</label>
						</h2>
					</div>
					
					<input type="hidden" id="prod_audit_status" name="prod_audit_status" />
					<jsp:include page="order_handler.jsp?order_id=${order_id }" />
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
	 
	 $("input[name='checkbox_pass']").unbind().bind("click", function() {
		 var $this = this;
		 $("input[name='checkbox_quality']").each(function(){
			 if ($this.checked) {
				 $(this).attr("checked", true);
			 } else {
				 $(this).attr("checked", false);
			 }
		 });
	 });
	 
	 $("input[name='checkbox_quality']").unbind().bind("click", function() {
		 if (!$(this).attr("checked")) {
			 $("input[name='checkbox_pass']").attr("checked", false);
		 }
	 });
	 

	 $("#getRelationOrder").bind("click",function(){
	    var url= app_path+"/shop/admin/orderFlowAction!getRealtionOrders.do?ajax=yes&order_id=${order_id}";
	    $("#selPhoneDlg").load(url,{},function(){});
		Eop.Dialog.open("关联订单信息");
	 });
  });
  

  function load_ord_his(){
		//$("#table_show").show();
		$("#table_show").toggle();
		//$("#order_his").show();
	   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};

  
  $(function(){
	//	CommonLoad.loadJSP('relations_info',app_path+'/shop/admin/orderFlowAction!getRealtionOrders.do',{order_id:"${order_id }",ajax:"yes",includePage:"relations_info"},false,null,true);
	//先加载总数和按钮页
		  CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);	
	//CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
	CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
	});
  $(function(){
		 $("input[name='checkbox_pass']").attr("checked", true);
		 $("input[name='checkbox_quality']").attr("checked", true);
	  
  });
  //集中写卡、自动化等生产模式自动化打印及流转
  $(function(){
	  if("AUTO"=="${auto }"){
		  //打印热敏单
		  if(hotFreeCheck()){
			   $.ajax({
		        	url:app_path+"/shop/admin/orderPostModePrint!doAddHotFreeNew.do?ajax=yes&order_id=${order_id}",
		        	dataType:"json",
		        	data:$("#qualityAuditForm").serialize(),
					success : function(data) {
						$.Loading.hide();
						if(data.result=='0'){//接口及打印数据保存成功
							//打印
							  var rePrintUrl = app_path+"/shop/admin/orderPostModePrint!hotFreePostModelNew.do?"+
							  "ajax=yes&order_is_his=${order_is_his}&order_id=${order_id}&post_type="+$("#post_type").val()+"&postId="+$("#shipping_company").val()+"&delivery_id="+data.delivery_id+"&delvery_print_id="+data.delveryPrintId+"&is_receipt="+data.is_receipt;
							  printRe=window.open(rePrintUrl,window,'dialogHeight=630px;dialogWidth=960px');
							  $("[field_name='logi_no']").val(data.logi_no);
							//流转
							if(checkData()){
								$.Loading.show();
								var options = {
									type : "post",
									url : app_path+"/shop/admin/orderFlowAction!flowDeal.do?ajax=yes&order_id=${order_id}",
									dataType : "json",
									success : function(result) {
										$.Loading.hide();
										dealCallbackAutoFlow(result);
									},
									error : function(e,b) {
										alert("处理失败，请重试!");
										$.Loading.hide();
									}
								}
								$("#qualityAuditForm").ajaxSubmit(options);
							}
						}else{
							alert(data.message);
						}
					},
					error : function(e,b) {
						alert("处理失败，请重试!");
						$.Loading.hide();
					}
				});
		  }
	  }
  });
</script>