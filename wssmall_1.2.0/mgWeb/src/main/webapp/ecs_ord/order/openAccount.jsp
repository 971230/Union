<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderExtBusiRequest"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
.finish{display:inline-block; width:18px; height:21px; background:url(${context}/images/icon_03.png) no-repeat;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预拣货</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

<script type="text/javascript" >
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
  String order_id = (String)request.getAttribute("order_id");
%>

</head>
<body>
<form action="javascript:void(0);" id="openAccountForm" method="post">

<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
             
             <!-- 顶部公共 -->
        	<jsp:include page="auto_flows.jsp?order_id=${order_id}"/>
        	<!-- 关联订单显示开始-->
<%-- 	            <div class="grid_n_div">
		            <h2><a href="javascript:void(0);" class="openArrow"></a>订单关联信息</h2> 
		           	<div class="grid_n_cont">
		    		  	<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span id="specValidateMsg_Span"></span></div>
						<div id="relations_info" style="height: 40px"></div>
		            </div>
	            </div> --%>
	            <!-- 关联订单显示结束 -->
        	<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>订单基本信息</h2>
                <div class="grid_n_cont">
            		<div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
        	        <!-- 基本信息开始 -->
        	        <div class="grid_n_cont_sub">
                        <h3>基本信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                           <tr>
	                        <th>内部订单编号：</th>
	                        <td>${order_id}</td>
	                        <th>外部订单编号：</th>
	                        <td><html:orderattr  order_id="${order_id}" field_name="out_tid"  field_desc ="外部订单编号" field_type="text"></html:orderattr></td>
	                        <th>生产模式：</th>
	                        <td><html:orderattr disabled="disabled" attr_code="DC_MODE_OPER_MODE" order_id="${order_id}" field_name="order_model"  field_desc ="生产模式" field_type="select"></html:orderattr></td>
	                      </tr>
	                      <tr>
	                        <th>外部状态：</th>
	                        <td><html:orderattr  attr_code="DIC_ORDER_EXT_STATUS" order_id="${order_id}" field_name="platform_status"  field_desc ="外部状态" field_type="select"></html:orderattr></td>
	                        <th>归属区域：</th>
	                        <td><html:orderattr attr_code="DC_MODE_REGION" order_id="${order_id}" field_name="order_city_code"  field_desc ="归属地市" field_type="select" disabled="disabled"></html:orderattr></td>
	                        <th>订单来源：</th>
	                        <td><html:orderattr attr_code="ORDER_FROM" order_id="${order_id}" field_name="order_from"  field_desc ="订单来源" field_type="select"></html:orderattr></td>
	                      </tr>
	                      <tr>
	                        <th>订单来源系统：</th>
	                        <td><html:orderattr attr_code="DIC_PLAT_TYPE" order_id="${order_id}" field_name="plat_type"  field_desc ="订单来源系统" field_type="select"></html:orderattr></td>
	                        <th>下单时间：</th>
	                        <td><html:orderattr disabled="disabled"  order_id="${order_id}"  field_name="tid_time"  field_desc ="下单时间" field_type="date"></html:orderattr></td>
	                        <th>订单处理类型：</th>
                            <td><html:orderattr  order_id="${order_id}" attr_code="DIC_ORDER_DEAL_TYPE" field_name="order_deal_type"  field_desc ="订单处理类型" field_type="select"></html:orderattr></td>
                        </tr>
                        <!-- 新补的 -->
                        <tr>
                          <th>bss开户工号: </th>
	                      <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="bss_operator"  field_desc ="bss开户工号" field_type="text"></html:orderattr></td>
	                       <th><span>*</span>订单总价（元）：</th>
                          <td>  
                             <input class='ipt_new' style="width:200px;" name="order_amount" value="<%= CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest().getOrder_amount()%>"/>
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
                                <th>ESS/BSS营业款：</th>
	                             <td><html:orderattr  order_id="${order_id}" field_name="busi_money"  field_desc ="ESS/BSS营业款" field_type="input"></html:orderattr></td>
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
	                    <tr>
	                   		 <th>特殊业务类型：</th>
	                         <td><html:orderattr disabled="disabled" attr_code="DC_BUSINESS_TYPE" order_id="${order_id }" field_name="special_busi_type" field_desc="业务类型" field_type="select" /></td>
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
	                    <%-- 
	                    <tr>
	                       <th><span>*</span>优惠金额（元）：</th>
                           <td><html:orderattr  order_id="${order_id}" field_name="order_disfee"  field_desc ="优惠金额（元）" field_type="input"></html:orderattr></td>
                        </tr>
	                   --%>
	                    <!-- 新补的 -->
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
        	        <!-- 支付信息开始 -->
        	        <div class="grid_n_cont_sub">
                    	<h3>支付信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              <th>支付时间：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="pay_time"  field_desc ="支付时间" field_type="date"></html:orderattr></td>
                              <th><span>*</span>支付类型：</th>
                              <td><html:orderattr attr_code="DIC_PAY_TYPE" order_id="${order_id}" field_name="paytype"  field_desc ="支付类型" field_type="select"></html:orderattr></td>
                              <th><span>*</span>支付方式：</th>
                              <td> <html:orderattr attr_code="DIC_PAY_WAY" order_id="${order_id}" field_name="pay_method"   field_desc ="支付方式" field_type="select"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th><span>*</span>支付状态：</th>
                              <td><html:orderattr disabled="disabled" attr_code="DIC_PAY_STATUS" order_id="${order_id}" field_name="pay_status"  field_desc ="支付状态" field_type="select"></html:orderattr></td>
                              <th>基金类型：</th>
                              <td><html:orderattr disabled="disabled"  attr_code="DC_ISFUND_TYPE" order_id="${order_id}" field_name="fund_type"  field_desc ="基金类型" field_type="select"></html:orderattr></td>
                              <th>兑换积分：</th>
                              <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="order_points"  field_desc ="兑换积分" field_type="input"></html:orderattr></td>
                            </tr>
                        </table>
                    </div>
        	        <!-- 支付信息结束 -->
        	        <!-- 发票信息开始 -->
        	        <div class="grid_n_cont_sub">
                    	<h3>发票信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>发票类型：</th>
                              	<td>
                                    <html:orderattr attr_code="DIC_ORDER_INVOICE_TYPE"  order_id="${order_id}" field_name="invoice_type"  field_desc ="发票类型" field_type="select"></html:orderattr>
                              	</td>
                              	<th>发票打印方式：</th>
                              	<td>
                                    <html:orderattr attr_code="DIC_ORDER_INVOICE_PRINT_TYPE"  order_id="${order_id}" field_name="invoice_print_type"  field_desc ="发票打印方式" field_type="select"></html:orderattr>
                              	</td>
                              	<th>发票抬头：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="reserve8"  field_desc ="发票抬头" field_type="input"></html:orderattr></td>
                          	</tr>
                            <tr>
                              	<th>发票内容：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="invoice_group_content"  field_desc ="发票内容" attr_code="DIC_INVOICE_GROUP_CONTENT" field_type="select"></html:orderattr></td>
                               	<th>发票号码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="invoice_no"  field_desc ="发票号码" field_type="input"></html:orderattr></td>
                             	<th>发票代码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="invoice_code"  field_desc ="发票代码" field_type="input"></html:orderattr></td>
                           
                            </tr>
                        </table>
                    </div>
        	        <!-- 发票信息结束 -->
        	        
        	        <!-- 物流信息开始 -->
                   <%-- <%if((EcsOrderConsts.IS_DEFAULT_VALUE).equals(AttrUtils.isShowShipVisiable(order_id))){ %> --%>
                    <div class="grid_n_cont_sub">
                      	<h3>物流信息：</h3> 
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>应收运费（元）：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="post_fee"  field_desc ="应收运费（元）" field_type="input"></html:orderattr></td>
                              	<th>实收运费（元）：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="n_shipping_amount"  field_desc ="实收运费（元）" field_type="input"></html:orderattr></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                          	</tr>
                            <tr>
                              	<th>物流公司名称：</th>
                              	<td><c:forEach var="logicompany" items="${logiCompanyList }">
										<c:if test="${logicompany.id==shipping_company}">${logicompany.name }</c:if>
									</c:forEach>
							    </td>
                               <th>是否闪电送：</th>
                              	<td>
                                    <html:orderattr disabled="disabled"  attr_code="DIC_SHIPPING_QUICK" order_id="${order_id}" field_name="shipping_quick"  field_desc ="是否闪电送" field_type="select"  ></html:orderattr>
                            	</td>
                              	<th>配送方式：</th>
                              	<td>
                                    <html:orderattr disabled="disabled"  attr_code="DC_MODE_SHIP_TYPE" order_id="${order_id}" field_name="sending_type"  field_desc ="配送方式" field_type="select" ></html:orderattr>
                            	</td>
                            </tr>
                            <tr>
                              	<th>配送时间：</th>
                              	<td><html:orderattr disabled="disabled" attr_code ='DIC_SHIPPING_TIME'   order_id="${order_id}" field_name="shipping_time"  field_desc ="配送时间" field_type="select"></html:orderattr></td>
                              	<th>收货人姓名：</th>
                              	<td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="ship_name"  field_desc ="收货人姓名" field_type="input"></html:orderattr></td>
                              	<th>收货省份：</th>
                              	<td><c:forEach var="pro" items="${provList}">
                              	        <c:if test="${provinc_code==pro.code}">${pro.name}</c:if>
                              	     </c:forEach>
                              	</td>
                            </tr>
                            <tr>
                              	<th>收货地市：</th>
                              	<td><c:forEach var="cityList" items="${cityList}">
                              	        <c:if test="${city_code==cityList.code}">${cityList.name}</c:if>
                              	     </c:forEach>
                              	</td>
                              	<th>收货区县：</th>
                              	<td><c:forEach var="districtList" items="${districtList}">
                              	        <c:if test="${district_id==districtList.code}">${districtList.name}</c:if>
                              	     </c:forEach>
                                </td>
                              	<th>收货商圈：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="ship_area"  field_desc ="收货商圈" field_type="input"></html:orderattr></td>
                          	</tr>
                            <tr>
                              	<th>详细地址：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="ship_addr"  field_desc ="详细地址" field_type="input"></html:orderattr></td>
                              	<th>邮政编码：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="ship_zip"  field_desc ="邮政编码" field_type="input"></html:orderattr></td>
                              	<th>固定电话：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="reference_phone"  field_desc ="固定电话" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th>收货电话：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="receiver_mobile"  field_desc ="手机号码" field_type="input"></html:orderattr></td>
                              	<th>电子邮件：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="ship_email"  field_desc ="电子邮件" field_type="input"></html:orderattr></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                      	</table>
                  	</div>
        	    </div>
        	</div>
        	<!-- 订单基本信息结束-->
        	
        	<!-- 商品信息开始 -->
        	<div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>商品信息</h2>
              	<div class="grid_n_cont">
           		  <div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
                    <div class="grid_n_cont_sub">
                        <h3>商品信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              <th><span>*</span>商品名称：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="GoodsName"  field_desc="商品名称" field_type="text"></html:orderattr></td>
                              <th><span>*</span>商品类型：</th>
                              <td><html:orderattr  order_id="${order_id}" attr_code="DC_MODE_GOODS_TYPE" field_name="goods_type"  field_desc ="商品类型" field_type="select"></html:orderattr></td>
                              <th>仓库名称：</th>
                              <td><html:orderattr attr_code="DIC_MT_STOREHOUSE" order_id="${order_id}" field_name="house_id"  field_desc ="仓库名称" field_type="select"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th>上网时长：</th>
                              <td><%=CommonDataFactory.getInstance().getProductSpec(order_id,SpecConsts.TYPE_ID_20001,null,SpecConsts.CARD_TIME) %></td>
                              
                            <th>是否老用户：</th>
                            <td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="is_old"  field_desc ="是否老用户" field_type="select"></html:orderattr></td>
                            </tr>
                            <tr>
                             <!-- <th>商品备注：</th>
                              <td>&nbsp;</td>
                               --> 
                              <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_20002))){ %>
                              <th>sim卡号</th>
                              <td><html:orderattr order_id="${order_id}" field_name="simid"  field_desc ="sim卡号" field_type="input" ></html:orderattr></td>
                              <%}%>
                              
                               <%
                                   String isOld = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
							      if(EcsOrderConsts.IS_OLD_1.equals(isOld)){
							   %>
							    <th>用户号码：</th>
                              	<td><html:orderattr  disabled="disabled"   order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="input"></html:orderattr></td>
                              	     
                               <%} %>
                            </tr>
                          <tr>
                          <th><c:if test="${orderTree.orderRealNameInfoBusiRequest.later_active_flag=='1'  }">
			      			<!-- <span>*</span> -->
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
                              	<th><span>*</span>颜色：</th>
                              	<td>
                              	 <%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.COLOR_NAME) %>
                              	</td>
                              	<th><span>*</span>容量：</th>
                              	<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.SIZE) %></td>
                              	<th>型号：</th>
                              	<td>
                                	<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, AttrConsts.MODEL_NAME) %>
                               </td>
                         </tr>
                         <tr>
                                <th><span>*</span>品牌：</th>
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
                              	<th><span>*</span>首月资费方式：</th>
                              	<td>
                                	 <html:orderattr attr_code="DIC_OFFER_EFF_TYPE"  order_id="${order_id}" field_name="first_payment"  field_desc ="首月资费方式" field_type="select"></html:orderattr>
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
                              	<th><span>*</span>合约编码：</th>
                              	<td>
                              	<input  type="text" disabled="disabled" class="ipt_new" value="<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.OUT_PACKAGE_ID) %>" id="textfield" style="width:200px;" />
                              	</td>
                              	<th><span>*</span>合约名称：</th>
                              	<td><input  type="text" disabled="disabled" class="ipt_new" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.GOODS_NAME)%>" id="textfield" style="width:200px;" /></td>
                                <th><span>*</span>合约期限：</th>
                              	<td><input name="package_limit" type="text" disabled="disabled" class="ipt_new" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT) %>" id="textfield" style="width:200px;" /></td>
                           
                             
                          	</tr>
                            <tr>
                              	<th><span>*</span>货品类型：</th>
                              	<td>
                              	   <html:selectdict name="contract.type"  disabled="true" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID)%>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                              	</td>
                               	<th><span>*</span>货品小类：</th><!-- 就是合约类型 -->
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
                            <td><html:orderattr    order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="input"></html:orderattr></td>
                            <th>卡类型：</th>
                            <td>
                               <html:orderattr  attr_code="DC_PRODUCT_CARD_TYPE"  order_id="${order_id}" field_name="white_cart_type"  field_desc ="卡类型" field_type="select"></html:orderattr>
                            </td>
                            <th>入网地区：</th>
                            <td><html:orderattr   attr_code="DC_MODE_REGION"   order_id="${order_id}" field_name="net_region"  field_desc ="入网地区" field_type="select"></html:orderattr></td>
                          </tr>
                          <tr>
                                <th>是否靓号：</th>
                              	<td>
                              	   <html:orderattr   attr_code="DIC_IS_LIANG" order_id="${order_id}" field_name="is_liang"  field_desc ="是否靓号" field_type="select"></html:orderattr>
                                </td>
                              	<th>靓号预存：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="liang_price"  field_desc ="靓号预存" field_type="text"></html:orderattr></td>
                                <th>靓号低消：</th>
                                <td><%=CommonDataFactory.getInstance().getNumberSpec(CommonDataFactory.getInstance().getAttrFieldValue(order_id, "phone_num"), SpecConsts.NUMERO_LOWEST) %></td>
                          </tr>
                          <tr>
                            <th>SIM卡号：</th>
                            <td colspan="3"><html:orderattr style="width:400px;" order_id="${order_id}" field_name="ICCID"  field_desc ="卡串号" field_type="input"></html:orderattr></td>
                            <th>共享子号：</th>
                            <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="sub_no"  field_desc ="共享子号" field_type="input"></html:orderattr></td>
                          </tr>
                      	</table>
                   </div>
                   <!-- 号码开户信息结束 -->
               	  	<div id="sp_product" style="height: 80px"></div>
	               	<div id="sub_product">
               	  	     <jsp:include page="include/sub_product.jsp"/>
					</div>               	  	
                    <!-- ZX add 2015-12-30 start 副卡信息开始 -->
               	  	<div class="grid_n_cont_sub">
               	  	 	<jsp:include page="include/phone_info_fuka.jsp?order_id=${order_id }"/>
               	  	</div>
               	  	<!-- ZX add 2015-12-30 end 副卡信息结束 -->
                 </div>
                 <%} %>
             </div>
             
             <!-- 账户信息开始 -->
        	 <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>账户信息</h2>
              	<div class="grid_n_cont">
           		  <div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
                    <div class="grid_n_cont_sub">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th>账户付费方式：</th>
                              	<td>
                                	<html:orderattr  attr_code="ACC_CON_PAY_MODE"  order_id="${order_id}" field_name="bill_type"  field_desc ="账户付费方式" field_type="select"></html:orderattr>
                               	</td>
                              	<th>上级银行编码：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="superiors_bankcode"  field_desc ="上级银行编码" field_type="text"></html:orderattr></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                          <!--  <th>银行编码：</th>
                              <td><html:orderattr    order_id="${order_id}" field_name="bank_code"  field_desc ="银行编码" field_type="text"></html:orderattr></td>
                            -->   
                             </tr>
                            <tr>
                              <th>银行名称：</th>
                              <td><html:orderattr  attr_code="DIC_BANK_NAME"  order_id="${order_id}" field_name="bank_code"  field_desc ="银行名称" field_type="select"></html:orderattr></td>
                              <th>银行账号：</th>
                              <td><html:orderattr    order_id="${order_id}" field_name="bank_account"  field_desc ="银行账号" field_type="text"></html:orderattr></td>
                              <th>银行账户名：</th>
                              <td><html:orderattr    order_id="${order_id}" field_name="bank_user"  field_desc ="银行账户名" field_type="text"></html:orderattr></td>
                            </tr>
                        </table>
                    </div>
       	  	  	</div>
   	  	  	</div>
        	<!-- 账户信息结束 -->
        	<!-- 赠品信息开始 -->
        	  <c:if test="${giftInfoList!=null&&giftInfoSize>0}">
        	 <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>赠品信息</h2>
              	<div class="grid_n_cont">
           		  <div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
                    <div class="grid_n_cont_sub">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                           <c:forEach var="gift" items="${giftInfoList}">
	                            <tr>
	                              <th>赠品名称：</th>
	                              <td>${gift.goods_name}</td>
	                              <th>赠品数量：</th>
	                              <td>${gift.sku_num}</td>
	                              <th>&nbsp;</th>
	                              <td>&nbsp;</td>
	                            </tr>
                           </c:forEach>
                          </table>
                    </div>
                </div>
             </div>
             </c:if>
        	<!-- 赠品信息结束 -->
        	
        	<!-- 货品信息开始 -->
			              <c:if test="${!(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')}">
			              <div class="grid_n_div">
			            	<h2><a href="#" class="openArrow"></a>货品信息<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
			              	<div class="grid_n_cont">
			            		<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16" /><span></span></div>
								<div id="goods_info" style="height: 80px"></div>
								
								<c:if test="${goods_type =='20021'}">
									<!-- FQJ add 2017-03-03 宽带信息 start -->
									<div id="kd_detail">
										<h3>宽带信息：</h3>
										<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
			                             	<tr>
				                            <th>合约编码：</th>
	                                   			<td>${p_code }</td>
			                                <th>合约名称:</th>
	                                   			<td>${p_name }</td>
			                                <th>合约期：</th>
			                                	<td>
			                                	<html:selectdict disabled="true" name="packge_limit" curr_val="${packge_limit}" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_CONTRACT_MONTH" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
			                                	</td>
			                               </tr>
			                               
			                               <tr>
				                            <th>速率：</th>
	                                   			<td>${net_speed }</td>
			                                <th>接入方式:</th>
			                                	<td>
			                                		<html:selectdict disabled="true" name="access_type" curr_val="${access_type}" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_ACCESS_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
			                                	</td>
			                                <th>地市：</th>
			                                	<td>
			                                		<html:selectdict disabled="true" name="city" curr_val="${city}" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_CITY_ZJ" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
			                                	</td>			                               
			                                </tr>
			                               
			                               <tr>
				                            <th>套餐编码：</th>
	                                   			<td>${plan_title }</td>
			                                <th>套餐名称:</th>
	                                   			<td>${ess_code }</td>
			                                <th>终端类型：</th>
			                                	<td>
													<html:selectdict disabled="true" name="terminal_type" curr_val="${terminal_type}" appen_options="<option value=''>---请选择---</option>"   attr_code="DIC_TERMINAL_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
			                                	</td>
			                               </tr>
			                               
			                               <tr>
				                            <th>标准地址：</th>
			                                 			<td>${orderTree.orderAdslBusiRequest.adsl_addr }</td>
			                                <th>用户地址：</th>
			                                 			<td>${orderTree.orderAdslBusiRequest.user_address }</td>
			                                </tr>
										</table>
									</div>
									<!-- FQJ add 2017-03-03 宽带信息 end -->
			              		</c:if>
								
								<div id="goods_open" style="height: 80px"></div>
								<div id="sp_product" style="height: 80px"></div>
				               	<div id="sub_product">
			               	  	     <jsp:include page="include/sub_product.jsp"/>
								</div>               	  	
			               	  	<!-- ZX add 2015-12-30 start 副卡信息开始 -->
			               	  	<div id="phoneInfoFukaDiv">
			               	  	 	<jsp:include page="include/phone_info_fuka.jsp?order_id=${order_id }&isChangePhone=${isChangePhone}"/>
			               	  	</div>
			               	  	<!-- ZX add 2015-12-30 end 副卡信息结束 -->
								<div id="goods_gift" style="height: 80px"></div>
			                </div>
			              </div>
			              </c:if>
              		<!-- 货品信息结束 --> 
        	
        	<!-- 引用买家和卖家留言 -->
        	<jsp:include page="buyerAndSalerInfo.jsp?order_id=${order_id}"/>
        	<jsp:include page="order_handler.jsp?order_id=${order_id }"/>
        </div>
    </div>
</div>
</form>
</body>
</html>

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
	//先加载总数和按钮页
	  CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);
	  CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
	//CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
 });
 
if(${!(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')}){//非多商品
	  CommonLoad.loadJSP('goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_info"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('goods_open','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_open"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('goods_gift','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_gift"},false,function(){AutoFlow.checkMsg();},true);
}
</script>