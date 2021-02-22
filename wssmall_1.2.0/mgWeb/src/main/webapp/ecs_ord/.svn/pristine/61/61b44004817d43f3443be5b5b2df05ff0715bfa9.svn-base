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
<script src="<%=request.getContextPath()%>/ecs_ord/js/quality_audit.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
</head>
<%
	String order_id = (String) request.getAttribute("order_id");
	int seriaNO = 1;
%>
<body>
	<form id="qualityAuditForm">
	<input type="hidden" value="<%=order_id %>" id="order_id"/>
		<div class="gridWarp">
			<div class="new_right">
				<div class="right_warp">
					<jsp:include page="/ecs_ord/order/auto_flows.jsp?order_id=${order_id }" />
					<div class="grid_n_div">
						<h2>
							<label for="checkbox00">通过
								<input type="checkbox" name="checkbox_pass" id="checkbox00" />
							</label>
						</h2>
					</div>
					
					<!-- 关联订单显示开始-->
		            <!-- <div class="grid_n_div">
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
												field_desc="订单处理类型" field_type="select" disabled="disabled">
											</html:orderattr></td>
											<th>特殊业务类型：</th>
	                          				<td><html:orderattr disabled="disabled" attr_code="DC_BUSINESS_TYPE" order_id="${order_id }" field_name="special_busi_type" field_desc="业务类型" field_type="select" /></td>
									</tr>
									  <!-- 新补的 -->
                        <tr>
                          <th>bss开户工号: </th>
	                      <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="bss_operator"  field_desc ="bss开户工号" field_type="text"></html:orderattr></td>
	                       <th><span>*</span>订单总价（元）：</th>
                          <td>  
                            <%= CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest().getOrder_amount()%>
                          </td>
                          <th><span>*</span>实收金额（元）：</th>
                          <td><html:orderattr  order_id="${order_id}" field_name="order_realfee"  field_desc ="实收金额（元）" field_type="input"></html:orderattr></td>
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
                                <th><span>*</span>开户费（元）：</th>
                                <td>${openAcc_price}</td>
                          	</tr>
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
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="grid_form">
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
										<td><html:orderattr attr_code="DIC_PAY_STATUS" order_id="${order_id}" field_name="pay_status" field_desc="支付状态" field_type="select" disabled="disabled" ></html:orderattr></td>
									     <th>基金类型：</th>
                                         <td><html:orderattr disabled="disabled" attr_code="DC_ISFUND_TYPE" order_id="${order_id}" field_name="fund_type"  field_desc ="基金类型" field_type="select"></html:orderattr></td>
                                         <th>兑换积分：</th>
                                         <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="order_points"  field_desc ="兑换积分" field_type="input"></html:orderattr></td>
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
							<a href="javascript:void(0);" class="openArrow"></a><label for="checkbox1">商品信息</label><input
								type="checkbox" name="checkbox_quality" id="checkbox1" />
						</h2>
						<div class="grid_n_cont">
							<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
							<div class="grid_n_cont_sub">
				                      <c:choose>
				                      <c:when test="${orderTree.orderExtBusiRequest.order_from!='10062' }">
								 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_g">
				                      <tbody>
				                         <tr>
				                         	<th></th>
				                            <th>商品名称</th>
                           					<th>商品价格</th>
				                            <th>货品类型</th>
				                            <th>机型编码</th>
				                            <th>品牌名称</th>
				                            <th>型号名称</th>
				                            <th>颜色</th>
				                            <th>终端串号</th><!-- 判断是终端类型才有终端串号 -->
				                            <th>操作状态</th>
				                            <th>操作描述</th>
				                         </tr>
				                         <!-- 
				                                                                         预占成功控制串号填写框不可修改  其他状态都可以修改  是终端的必须填写串号
				                          -->
				                          <c:forEach var="itemExtvl" items="${orderItemExtvtlBusiRequests }">
					                         <tr>
	                             				 <td><%=seriaNO++ %></td>
					                             <td>${itemExtvl.goods_name}</td>
					                             <td>${itemExtvl.goods_price}</td>
					                             <td>${itemExtvl.goods_type_name}</td>
					                             <td>${itemExtvl.machine_type_name}</td>
					                             <td>${itemExtvl.resources_brand_name}</td>
					                             <td>${itemExtvl.resources_model_name}</td>
					                             <td>${itemExtvl.resources_color}</td>
					                             <!--operation_status==1  预占成功  成功了后不允许修改  -->
					                             <td style="width:220px;">${itemExtvl.resources_code}</td>
					                             <td>
					           		                <c:if test="${ itemExtvl.operation_status=='1'}">预占成功</c:if>
					           		                <c:if test="${ itemExtvl.operation_status=='0'}">待预占</c:if>
					           		                <c:if test="${ itemExtvl.operation_status=='5'}">预占失败</c:if>
					           		                <c:if test="${ itemExtvl.operation_status=='4'}">释放成功</c:if>
					           		                <c:if test="${ itemExtvl.operation_status=='7'}">释放失败</c:if>
					           		             </td>
					                             <td style="width:250px;">
					           		                <p>${itemExtvl.operation_desc}</p>
					           		             </td>
					                         </tr>
				                          </c:forEach>
				                      </tbody>
				                    </table>
				                      </c:when>
				                      <c:otherwise>
				                      <div id="hsB2C_goods_info" style="height: 80px"></div>
				                      </c:otherwise>
				                      </c:choose>
							</div>
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
										<td><html:orderattr order_id="${order_id}"
												field_name="ship_name" field_desc="收货人姓名" field_type="input"></html:orderattr></td>
										<th><span>*</span>收货人手机号码：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="receiver_mobile" field_desc="手机号码"
												field_type="input" field_value="${delivery.ship_mobile }"></html:orderattr></td>
										<th>电子邮件：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="ship_email" field_desc="电子邮件" field_type="input" field_value="${delivery.ship_email }"></html:orderattr></td>
									</tr>
									<tr>
										<th>收货人电话：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="reference_phone" field_desc="收货人电话(固话)"
												field_type="input" field_value="${delivery.ship_tel }"></html:orderattr></td>
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
													<c:if test="${district_id!=districtList.code}">
														<option value="${districtList.code}">${districtList.name}</option>
													</c:if>
												</c:forEach>
										</select></td>
										<th><span>*</span>详细地址：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="ship_addr" field_desc="详细地址" field_type="input" field_value="${delivery.ship_addr }"></html:orderattr></td>
										<th>邮政编码：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="ship_zip" field_desc="邮政编码" field_type="input" field_value="${delivery.ship_zip }"></html:orderattr></td>
									</tr>
									<tr>
										<th><span>*</span>物流公司名称：</th>
										<td>
										<select name="shipping_company" field_name="shipping_company" field_desc="物流公司" 
											id="logi_company" class="ipt_new" style="width:200px;">
												<option value="">---请选择---</option>
												<c:forEach var="logicompany" items="${logiCompanyList }">
													<option key_str="${logicompany.key_str }"
														value="${logicompany.id }">${logicompany.name }</option>
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
                            	        </td>
										<th><span>*</span>配送时间：</th>
										<td><html:orderattr order_id="${order_id}" attr_code ='DIC_SHIPPING_TIME'
												field_name="shipping_time" field_desc="配送时间"
												field_type="select" field_value="${delivery.shipping_time }"></html:orderattr></td>
									</tr>
									<tr>
										<th><span>*</span>物流单号：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="logi_no" field_desc="物流单号" field_type="input" field_value="${delivery.logi_no }"></html:orderattr></td>
										<th>运费：</th>
										<td><html:orderattr order_id="${order_id}"
												field_name="n_shipping_amount" field_desc="实收金额（元）"
												field_type="input" field_value="${delivery.n_shipping_amount }"></html:orderattr>
												</td>
										<th>保费率：</th>
										<td><input type="text" class="ipt_new"
											style="width:200px;" id="protect_fee"  
											name="protect_free"
											value="0" /></td>
									</tr>
									<tr name="quhuorenxinxi" style="none;">
										<th>物流人员：</th>
										<td><input type="text" class="ipt_new"
											style="width:200px;" id="logi_receiver" 
											name="logi_receiver"
											value="${delivery.logi_receiver }" /></td>
										<th>物流电话：</th>
										<td><input type="text" class="ipt_new"
											style="width:200px;" id="logi_receiver_phone"
											name="logi_receiver_phone" value="${delivery.logi_receiver_phone }" /></td>
										<th>是否需要回单：</th>
										<td><html:orderattr attr_code="DC_IS_OR_NO"   order_id="${order_id}" field_name="need_receipt"  field_desc ="是否需要回单" field_type="select" field_value="${delivery.need_receipt }" ></html:orderattr></td>
									</tr>
									
								</table>
							</div>
						</div>
					</div>
					
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
												<c:forEach var="attrGiftInfoBusiRequest"
													items="${orderTree.giftInfoBusiRequests }">
												 <c:if test="${attrGiftInfoBusiRequest.is_virtual=='0' }">
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
										<td colspan="3" style=" text-align:left; padding-left:5px;"><a
											href="javascript:void(0);" id="xinzeng"><i
												class="icon_add"></i>新增</a>
											<div id="zengpinneirong" style="display: none">
												赠品名称：<input class="ipt_new" type="text" id="reissue_info"
													name="reissue_info" /> 赠品数量：<input class="ipt_new"
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
					<jsp:include page="/ecs_ord/order/order_handler.jsp?order_id=${order_id }" />
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
		//$("#order_his").show();
		$("#table_show").toggle();
	   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};

  
  $(function(){
	  //CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
	  if(${orderTree.orderExtBusiRequest.order_from=='10062'}){//华盛B2B
		  CommonLoad.loadJSP('hsB2C_goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!hsB2Cgoods.do',{order_id:"${order_id }",ajax:"yes",includePage:"hsB2C_goods_info"},false,function(){AutoFlow.checkMsg();},true);
	  }
	});
</script>