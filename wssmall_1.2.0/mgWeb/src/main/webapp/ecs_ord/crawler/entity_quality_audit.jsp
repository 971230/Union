<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>质检稽核</title>
<script src="<%=request.getContextPath()%>/ecs_ord/js/autoord.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<style type="text/css">
	.form_div{ background:#f6f6f6; border:1px solid #eee; margin:0 20px; margin-bottom:3px; height:32px; line-height:32px; padding-left:10px;}
	.grid_new{}
	.grid_new th{ text-align:right ; border: 1px solid #FFF;background:#FFF;}
	.grid_new td{ text-align:left ; border: 1px solid #FFF;}
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
<form id="qualityAuditForm" method="post">
	<div class="gridWarp">
		<div class="new_right">
			<div class="right_warp">
				<c:if test="${not empty order_id}">
					<jsp:include page="auto_flows.jsp?order_id=${order_id}&btn=entity_quality" />
				</c:if>
				<!-- 关联订单显示开始-->				
	            <%-- 
	            <c:if test="${order_id!=null}">
	            <div class="grid_n_div">
		            <h2><a href="javascript:void(0);" class="openArrow"></a>订单关联信息</h2> 
		           	<div class="grid_n_cont">
		    		  	<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span id="specValidateMsg_Span"></span></div>
						<div id="relations_info" style="height: 40px"></div>
		            </div>
	            </div> 
	            </c:if>
	            --%>
	            <!-- 关联订单显示结束 -->
				<div class="grid_n_div">
				<br/>
					<c:choose>
						<c:when test="${check_type == 'physics'}">
							<div class="form_div">
								料箱号：
								<input value="${order_id}" type="hidden" class="ipt_new" id="order_id" style="width:200px;"/>
								<input value="${boxCode}" type="text" class="ipt_new" id="boxCode" name="boxCode" style="width:200px;" />
								<a href="javascript:void(0);" class="blue_b" style="margin-left:5px;" id="lr_lx">录入</a>
							</div>
							<!--div class="form_div">
								测试，同料箱号查询权限，可做保存流转，订单号：
								<input value="${boxCodeTest}" type="text" class="ipt_new" id="boxCodeTest" name="boxCodeTest" style="width:200px;" />
								<a href="javascript:void(0);" class="blue_b" style="margin-left:5px;" id="lr_lx2">录入</a>
							</div>
							<div class="form_div">
								测试，只能用来测试页面显示，没有权限、环节等各种校验，最好不要做保存和流转动作，订单号：
								<input value="${boxCodeTest}" type="text" class="ipt_new" id="boxCodeTest2" name="boxCodeTest2" style="width:200px;" />
								<a href="javascript:void(0);" class="blue_b" style="margin-left:5px;" id="lr_lx3">录入</a>
							</div-->
						</c:when>
						<c:otherwise>
							<input value="${order_id}" type="hidden" class="ipt_new" id="order_id" style="width:200px;" />
						</c:otherwise>
					</c:choose>
					<c:if test="${order_id!=null}">
					<input type="hidden" value="n" name="q_check" /> 
					<div class="grid_n_cont">
						<div class="grid_n_cont_sub">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_a">
								<tr>
									<th>订单信息</th>
									<th>商品信息</th>
									<th>号码开户信息</th>
									<th>发票信息</th>
									<th>其他信息</th>
									<c:if test="${query_type=='' }">
									<th>查看详情</th>
									</c:if>
								</tr>
								<tr>
									<td>
										<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_new">
											<tr>
				                              <th>外部编号：</th><td>${autoOrderTree.orderTree.orderExtBusiRequest.out_tid}</td>
				                            </tr>
											<tr>
				                              <th>内部编号：</th><td>${autoOrderTree.orderTree.order_id }</td>
				                            </tr>
											<tr>
				                              <th>订单来源：</th><td>${autoOrderTree.order_from_c}</td>
				                            </tr>
											<tr>
				                              <th>成交时间：</th><td>${autoOrderTree.orderTree.orderExtBusiRequest.tid_time }</td>
				                            </tr>
											<tr>
				                              <th>开户人：</th><td><%=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME)%></td>
				                            </tr>
											<tr>
				                              <th>兑换积分：</th><td>${autoOrderTree.orderTree.orderExtBusiRequest.order_points }</td>
				                            </tr>
										</table>
									</td>
									<td>
										${autoOrderTree.goods_package }<br/>
										￥${autoOrderTree.orderTree.orderBusiRequest.order_amount }<br/>
										${autoOrderTree.shipping_type_name }
									</td>
									<td>
										<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_new">
											<tr>
				                              <th>用户号码：</th>
				                              <td><%=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM)%>
				                              <% String isOld = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD); 
				                              	 if(StringUtils.equals(EcsOrderConsts.IS_OLD_1, isOld)){%>
				                              <img src="<%=request.getContextPath()%>/ecs_ord/order/images/olduser.png" />
				                              <%} %>
				                              </td>
				                            </tr>
											<tr>
				                              <th>卡类型：</th><td>${autoOrderTree.zk_white_cart_type_name}</td>
												</tr>
												<tr>
				                              <th>成白卡：</th><td>${autoOrderTree.zk_sim_type_name}</td>
				                            </tr>
				                            <c:forEach var="orderPhoneInfoFukaBusiRequest" items="${autoOrderTree.orderPhoneInfoFukaList}">	
												<tr>
													<td colspan="2">
													<div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;"/>
													</td>
												</tr>
												<tr>
													<th>副卡号码：</th>
													<td>${orderPhoneInfoFukaBusiRequest.phoneNum}
													</td>
												</tr>
												<tr>
													<th>卡类型：</th>
													<td>${orderPhoneInfoFukaBusiRequest.userType }</td>
												</tr>
												<tr>
				                              		<th>成白卡：</th><td></td>
												</tr>
											</c:forEach>
										</table>
									</td>					        	    
									<td>
										<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_new">
											<tr>
				                              <th>发票类型：</th><td>${autoOrderTree.invoice_type_name}</td>
				                            </tr>
											<tr>
				                              <th>发票打印方式：</th><td>${autoOrderTree.invoice_print_type_name}</td>
				                            </tr>
											<tr>
				                              <th>发票抬头：</th><td>${autoOrderTree.invoice_title}</td>
				                            </tr>
											<tr>
				                              <th>发票内容：</th><td>${autoOrderTree.invoice_group_content_name}</td>
				                            </tr>
										</table>
									</td>
									<td style="width: 15%">
										<div class="new_div">买家留言：</div>
										<div class="new_div"><%=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BUYER_MESSAGE)%></div>
										<div class="new_div">卖家留言：</div>
										<div class="new_div"><%=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SELLER_MESSAGE)%></div>
									</td>
									<c:if test="${query_type=='' }">
									<td>
										<c:if test="${autoOrderTree.orderTree != null }">
											<a id="xiangqing" href="javascript:void(0)" style="color: blue;">详情</a>
										</c:if>
									</td>
									</c:if>
								</tr>
							</table>
						</div>
					</div>
					</c:if>
				</div>
				
				<c:if test="${order_id!=null}">
				<div class="form_div">
					串号/SKU录入：<input name="textfield" type="text" class="ipt_new" id="serial_num" style="width:200px;" />
						<a href="javascript:void(0)" class="blue_b" style="margin-left:5px;" id="lr_ch">录入</a>
				</div>
				<div class="grid_n_div">
					<h2>订单实物信息</h2>
					<div class="grid_n_cont">
						<div class="grid_n_cont_sub">
	                      <c:choose>
	                      <c:when test="${orderTree.orderExtBusiRequest.order_from!='10062' }">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_a">
								<tr>
									<th>实物类型</th>
									<th>SKU</th>
									<th>实物名称</th>
									<th>串号</th>
									<th>录入串号/SKU</th>
									<th>状态</th>
								</tr>
								<c:forEach var="giftInfo" items="${entityInfoList }">
								<tr>
									<td>${giftInfo.entity_type }</td>
									<td>${giftInfo.sku }</td>
									<td>${giftInfo.entity_name }</td>
									<td>${giftInfo.serial_number }</td>
									<td attr_ser="attr_ser" id="${giftInfo.serial_number }"></td>
									<td attr_sta="attr_sta" id="state_${giftInfo.serial_number }" style="color: red"></td>
								</tr>
								</c:forEach>
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
					<h2>物流配送信息</h2>
					<div class="grid_n_cont">
						<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span id="specValidateMsg_Span"></span></div>
				        <div class="grid_n_cont_sub">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
								<input type="hidden"  id="post_type" name="post_type" value="0"/> <!-- 物流类型 -->
								<tr>
									<th style="width: 20%">收货人姓名：</th>
									<td required="true"  style="width: 20%">
									<%if(order_id!=null&&!order_id.equals("")){ %>
										<html:orderattr order_id="${order_id}"
											field_name="ship_name" field_desc="收货人姓名" field_type="input">
										</html:orderattr>
									<% }%>
									</td>
									<th style="width: 10%">收货电话：</th>
									<td required="true" style="width: 50%">
									<%if(order_id!=null&&!order_id.equals("")){ %>
										<html:orderattr order_id="${order_id}"
											field_name="receiver_mobile" field_desc="收货手机"
											field_type="input" field_value="${delivery.ship_mobile }">
										</html:orderattr>
									<% }%>
									</td>
								</tr>
								<tr>
									<th>收货地址：</th>
									<td required="true" colspan="3">
									<%if(order_id!=null&&!order_id.equals("")){ %>
										<html:orderattr order_id="${order_id}" field_name="ship_addr" field_desc="详细地址" field_type="textarea" field_value="${delivery.ship_addr }" style="width:550px;">
										</html:orderattr>
										<% }%>
									</td>
								</tr>
								<tr>
									<th>配送区域：</th>
									<td required="true">
										<%if(order_id!=null&&!order_id.equals("")){ %>
                                		<html:orderattr order_id ="${order_id }"  field_name="district"  field_desc ="配送区域" field_type="input" field_value="${delivery.region }"></html:orderattr>
                                		<% }%>
                                	</td>
									<th>是否需要回单：</th>
										<td required="true">
											<%if(order_id!=null&&!order_id.equals("")){ %>
											<html:orderattr  attr_code="DC_IS_RECEIPT" order_id="${order_id}" field_name="need_receipt"  field_desc ="是否需要回单" field_type="select" field_value="${delivery.need_receipt }"></html:orderattr>
											<% }%> 
										</td>
								</tr>
								<tr>
									<th>物流单号：</th>
									<td required="true">
									<%if(order_id!=null&&!order_id.equals("")){ %>
										<html:orderattr order_id="${order_id}"
											field_name="logi_no" field_desc="物流单号" field_type="input" field_value="${delivery.logi_no }"></html:orderattr>
									<%} %>
									</td>
									<th>配送方式：</th>
									<td required="sending_type">
									<%if(order_id!=null&&!order_id.equals("")){ %>
										<html:orderattr order_id="${order_id}" attr_code="DC_MODE_SHIP_TYPE"
											field_name="sending_type" field_desc="配送方式" field_type="select" disabled="disabled">
										</html:orderattr>
										<input type="hidden" name="sending_type_id" value="${orderTree.orderBusiRequest.shipping_type }" />
										<%} %>
									</td>
								</tr>
								<tr>
									<th>联系电话：</th><!-- 物流公司取件人电话 -->
									<td>
									<%if(order_id!=null&&!order_id.equals("")){ %>
										<html:orderattr order_id="${order_id}"
											field_name="carry_person_mobile" field_desc="物流公司取件人电话" field_type="input" field_value="${delivery.logi_receiver_phone }">
										</html:orderattr>
										<%} %>
									</td>
									<th>物流公司：</th>
									<td required="true"><select name="shipping_company" id="logi_company"
										class="ipt_new" style="width:200px;">
											<option value="">请选择物流公司</option>
											<c:forEach var="logicompany" items="${logiCompanyList }">
												<option key_str="${logicompany.key_str }"
													value="${logicompany.id }">${logicompany.name }</option>
											</c:forEach>
									</select></td>
									
									<%if(order_id!=null&&!order_id.equals("")){ %>
										<html:orderattr order_id="${order_id}"
											field_name="shipping_company" field_desc="物流公司" 
											field_type="input" style="display:none;" field_value="${delivery.shipping_company }">
										</html:orderattr>
									<% }%>
									
								</tr>
								<tr>
									<th>保费：</th>
									<td>
										<input type="text" class="ipt_new" style="width:200px;"
											id="protect_fee" name="protect_free" value="0" />
									</td>
									<th>运费：</th>
									<td>
									<%if(order_id!=null&&!order_id.equals("")){ %>
										<html:orderattr order_id="${order_id}"  field_name="n_shipping_amount" field_desc="实收金额（元）" field_type="input" field_value="${delivery.n_shipping_amount }">
										</html:orderattr>
										<input type="hidden" id="n_shipping_amount" name="n_shipping_amount" />
									<%} %>
									</td>
								</tr>
								<tr name="quhuorenxinxi" style="display: none;">
									<th>物流人员：</th>
									<td><input type="text" class="ipt_new" style="width:200px;" id="logi_receiver"  name="logi_receiver" value="${logi_receiver }" /></td>
									<th>物流电话：</th>
									<td><input type="text" class="ipt_new" style="width:200px;" id="logi_receiver_phone" name="logi_receiver_phone" value="${logi_receiver_phone }" /></td>
								</tr>
								<tr>
								<td colspan="4" style="text-align: center;">
								<div style="border-top:1px dashed #cccccc;height: 1px;overflow:hidden;"/>
								</td>
								</tr>
								<tr>
								  	<th>发票号码：</th>
	                              	<td><html:orderattr order_id="${order_id}" field_name="invoice_no"  field_desc ="发票号码" field_type="input"></html:orderattr></td>
	                             	<th>发票代码：</th>
	                              	<td><html:orderattr order_id="${order_id}" field_name="invoice_code"  field_desc ="发票代码" field_type="input"></html:orderattr></td>
	                            </tr>
							</table>
						</div>
					</div>
				</div>
				
				<div class="grid_n_div">
					<h2>订单基本信息</h2>
					<div class="grid_n_cont">
                      	<h3>基本信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
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
                        </table>
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
					<h2>商品信息</h2>
					<div class="grid_n_cont">
               	  	<!-- 合约计划开始 -->
               	  	  <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10001))){%>
               	  	<div class="grid_n_cont_sub">
                      	<h3>合约计划：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th><span>*</span>合约编码：</th>
                              	<td><%=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.OUT_PACKAGE_ID) %></td>
                              	<th><span>*</span>合约名称：</th>
                              	<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.GOODS_NAME) %></td>
                                <th><span>*</span>合约期限：</th>
                              	<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT) %></td>
                           
                          	</tr>
                            <tr>
                              	<th><span>*</span>货品类型：</th>
                              	<td>
                              	   <html:selectdict name="contract.type"  disabled="true" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID)%>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                              	</td>
                               	<th><span>*</span>货品小类：</th>
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
                    <%}%>
               	  	<!-- 合约计划结束 -->
               	  	<div class="grid_n_cont_sub">
					<h3>优惠信息：</h3>
					<table width="98%" border="0" cellspacing="0" cellpadding="0"class="grid_s">
						<tr>
						<th>优惠活动类型</th>
						<th>代金活动编码</th>
						<th>代金活动名称</th>
						</tr>
						<c:forEach var="discount" items="${orderTree.discountInfoBusiRequests}">
							<tr>
								<td><html:selectdict disabled="true"  curr_val="${discount.activity_type }" attr_code="DC_DISCOUNT_TYPE"  appen_options="<option>---请选择---</option>" ></html:selectdict></td>
								<td>${discount.activity_code}</td>
								<td>${discount.activity_name}</td>
							</tr>
						</c:forEach>
						</table>
					</div>
               	  	<div class="grid_n_cont_sub" id="sp_product" style="height: 80px"></div>
	               	<div class="grid_n_cont_sub" id="sub_product">
               	  	     <jsp:include page="include/sub_product.jsp"/>
					</div>               	  	
				</div>
				</div>
				<%if(order_id!=null&&!order_id.equals("")) { %>
				<jsp:include page="order_handler.jsp?order_id=${order_id }" />
				<%} %>
				<input type="hidden" value="${order_id }" id="order_ids" />
				<input type="hidden" value="${entity_quality }" id="entity_quality" />
				<input type="hidden" id="prod_audit_status" name="prod_audit_status" />
				<div class="clear"></div>
				</c:if>
			</div>
		</div>
		<br/><br/>
	</div>
	</form>
	<c:if test="${order_id!=null}">
	<div id="btns_div"></div>
	</c:if>	
</body>
</html>

<script>
	$("#logi_company").bind("change", function() {
		
		var logi_post_id = $("#logi_company").val();
		if(logi_post_id == "ZT0002" || logi_post_id == "ZY0002" || logi_post_id == "ZY0001") {
			$("tr[name='quhuorenxinxi']").css("display","");
			$.ajax({
				type : "post",
				async : false,
				url : "ordAuto!logi_no.do?ajax=yes",
				data : {},
				dataType : "json",
				success : function(data) {
					if (data != null) {
						$("input[field_name=logi_no]").val(data);
					}
				}
			});
		} else {
			$("tr[name='quhuorenxinxi']").css("display", "none");
			$("input[field_name=logi_no]").val("");
		}
		$.ajax({
			type : "post",
			async : false,
			url : "ordAuto!logi_company_regions.do?ajax=yes&logi_post_id="+logi_post_id,
			data : {},
			dataType : "json",
			success : function(data) {
				if (data != null && data.length > 0) {
					data = eval(data);
					$("input[field_name=n_shipping_amount]").val(data[0].carray_fee);
					$("#n_shipping_amount").val(data[0].carray_fee);
					$("#protect_fee").val(data[0].protect_fee);
				} else {
					$("input[field_name=n_shipping_amount]").val("0");
					$("#protect_fee").val("0");
				}
			}
		});
	});
	
/*	$("#serial_num").blur(function() {
		if ($("#serial_num").val() == "") {
			alert("请录入串号/SKU");return;
		}
		$("#serial_num").removeAttr("background");
		$("#lr_ch").click();
		$("#serial_num").val("")
	});
*/
//	$("#boxCode").blur(function() {
//		$("#lr_lx").click();
//		
//	});
	
	$("#lr_ch").bind("click", function() {
		if ($("#serial_num").val() == "") {
			alert("请录入串号/SKU");return;
		}
		var serial_num = $("#serial_num").val();
		if(serial_num.indexOf("+")>-1){
			serial_num = serial_num.replace("+","A");
		}
		$.ajax({
			type : "post",
			async : false,
			url : "ordAuto!entity_quality_lrch.do?ajax=yes&check_type=physics&order_id="+$("#order_ids").val()+"&serial_num="+serial_num,
			data : {},
			dataType : "json",
			success : function(data) {
				if (data.length > 0) {
					var data = eval(data);
					var dt = data[0];
					var attr_ser = $("td[attr_ser=attr_ser]");
					if (attr_ser.length > 0) {
						for (var i=0;i<attr_ser.length;i++) {
							var attr_s = attr_ser[i];
							if ($(attr_s).attr("id") == dt.serial_number) {
								$(attr_s).text(dt.input_serial_number);
								$("td[id=state_"+dt.serial_number+"]").text(dt.state=="1"?"ok":"");
							}
						}	
					}
				} else {
					alert("未匹配到对应的实物串号.");
				}
				$("#serial_num").select();
			}
		});
	});
	
	$("#lr_lx").bind("click", function() {
		if($("#boxCode").val() == "") {
			alert("请输入料箱号!");return;
		}
		
		$.ajax({
			type : "post",
			async : false,
			url : "ordAuto!check_order_id.do?ajax=yes&boxCode="+$("#boxCode").val(),
			data : {},
			dataType : "json",
			success : function(data) {
				if (data.result == "1") {
					alert(data.message);return;
				}
				$("#qualityAuditForm").attr("action",ctx+"/shop/admin/ordAuto!entity_quality_audit.do?check_type=physics&order_id="+data.order_id).submit();	
				$("#serial_num").select(); 
			}
		});
	});
	
	//这个方法是提供给测试用的
	$("#lr_lx2").bind("click", function() {
		if($("#boxCodeTest").val() == "") {
			alert("请输入订单号 !");return;
		}
		
		$.ajax({
			type : "post",
			async : false,
			url : "ordAuto!check_order_id_test.do?ajax=yes&boxCodeTest="+$("#boxCodeTest").val(),
			data : {},
			dataType : "json",
			success : function(data) {
				if (data.result == "1") {
					alert(data.message);return;
				}
				$("#qualityAuditForm").attr("action",ctx+"/shop/admin/ordAuto!entity_quality_audit.do?check_type=physics&order_id="+data.order_id).submit();	
				$("#serial_num").select(); 
			}
		});
	});
	//这个方法是提供给测试用的
	$("#lr_lx3").bind("click", function() {
		if($("#boxCodeTest2").val() == "") {
			alert("请输入订单号 !");return;
		}
		
		$("#qualityAuditForm").attr("action",ctx+"/shop/admin/ordAuto!entity_quality_audit.do?check_type=physics&order_id="+$("#boxCodeTest2").val()).submit();	
		$("#serial_num").select(); 
	});
	
	$(function() {
		var order_id = $("#order_id").val();
		if (order_id != null && order_id != "") {
			var ship_company = $("input[field_name=shipping_company]").val()
			$("#logi_company option[value='"+ship_company+"']").attr("selected","selected");
			
			$("#serial_num").select();
		} else {
			$("#logi_company option[value='SF-FYZQYF']").attr("selected","selected");
			$("#boxCode").focus();
		}
	});
	
	function checkData () {
		$("input[name='q_check']").val("n");
		$("#n_shipping_amount").val($("input[field_name=n_shipping_amount]").val());
		var attr_stas = $("td[attr_sta=attr_sta]");
		if (attr_stas.length > 0) {
			for (var i =0; i<attr_stas.length; i++) {
				var att = attr_stas[i];
				var att_val = $(att).text();
				if (att_val != "ok") {
					alert("请确认实物质检完成！");
					return false;
				}
			}
		}
		
		var logi_no = $("input[field_name=logi_no]").val();
		var key_str0 = $("#logi_company option:selected").attr("key_str");
	    if (key_str0 != undefined && key_str0 != "") {
		    var key_str1 = "/"+key_str0+"/";
		    var key_str2 = eval(key_str1);
		    if (!key_str2.test(logi_no)) {
		    	alert("物流单号不正确！");
		    	return false;
		    }
	    }
		
		//目前只有select 和 input 所以不做其他判断，有问题再处理
		var required_field = "";
		$("td[required='true']").each(function(){
			var requiredObj = $(this).children(":visible").eq(0);
			if(requiredObj instanceof Object){
				var required_val = requiredObj.val();
				if(required_val == null || required_val == "" || required_val == "undefined"){
					required_field = $(this).prev("th").html();
					return false;
				}
			}else {
				required_field = $(this).prev("th").html();
				return false;
			}
		});
		$("td[required='sending_type']").each(function(){
			var sending_type = $(this).html();
			if(null!=sending_type){
				sending_type = sending_type.replace(/^\s+|\s+$/g, '');
			}
			if(sending_type == null || sending_type == "" || sending_type == "undefined"){
				required_field = $(this).prev("th").html();
				return false;				
			}
		});
		if(required_field != null && required_field != "" && required_field != "undefined"){
			alert(required_field + "不能为空");
			return false;
		}
		$("#prod_audit_status").val("1");
		return true;
	}

	function hotFreeCheck(){
		var logi_company = $("#logi_company").val();
		if (logi_company=='') {		
			alert("请先选择物流公司名称！");
			return false;
		}else if(logi_company=="SF-FYZQYF"||logi_company=="SF0001"||logi_company=="SF0002"||logi_company=="SF0003"||logi_company=="SF-HS"){
			return true;
		}else{
			alert("热敏单打印功能只支持顺丰物流公司");
			return false;
		}
	}
	
	function dealCallback(data) {
		alert(data.message);
		if(data.result == '0'){
			var url = ctx+ "/shop/admin/ordAuto!entity_quality_audit.do?check_type=physics";
			window.location.href = url;
		}
	}

	function saveBack(e) {
		alert(e.message);
	}
	$("#xiangqing").bind("click", function() {
		var order_id = $("#order_id").val();
		var url = ctx+"/shop/admin/ordAuto!showDetail.do?order_id="+order_id;
		window.location.href=url;
	});
	$("#boxCode").bind("keydown", function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){ // enter 键
             //要做的事情
             $("#lr_lx").trigger("click");
        }
    });
	$("#serial_num").bind("keydown", function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){ // enter 键
             //要做的事情
             $("#lr_ch").trigger("click");
        }
    });
	$(function(){
		AutoFlow.checkMsg();
	});
	

	function load_ord_his(){
		//$("#table_show").show();
		//$("#order_his").show();
		$("#table_show").toggle();
	   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};

	

	$(function(){
		//先加载总数和按钮页
		  CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);
		//CommonLoad.loadJSP('relations_info','< %=request.getContextPath() %>/shop/admin/orderFlowAction!getRealtionOrders.do',{order_id:"${order_id }",ajax:"yes",includePage:"relations_info"},false,null,true);
		//CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
		<%if(order_id!=null&&!order_id.equals("")) { %>
		CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
		<%}%>
	  if(${orderTree.orderExtBusiRequest.order_from=='10062'}){//华盛B2B
		  CommonLoad.loadJSP('hsB2C_goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!hsB2Cgoods.do',{order_id:"${order_id }",ajax:"yes",includePage:"hsB2C_goods_info"},false,function(){AutoFlow.checkMsg();},true);
	  }
	});
</script>

