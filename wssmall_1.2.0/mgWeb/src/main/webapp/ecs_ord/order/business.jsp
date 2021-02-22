<%@page import="zte.net.ecsord.utils.SpecUtils"%>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="zte.net.ecsord.common.StypeConsts"%>
<%@page import="zte.net.ecsord.utils.AttrUtils"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>业务办理</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

<%
  String order_id = (String)request.getAttribute("order_id");
  String order_from  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
%>

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
<body>
<form action="javascript:void(0);" id="businessForm" method="post">

<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
             
             <!-- 顶部公共 -->
        	<jsp:include page="auto_flows.jsp?order_id=${order_id}"/>
        	<!-- 关联订单显示开始-->
	            <%-- <div class="grid_n_div">
		            <h2><a href="javascript:void(0);" class="openArrow"></a>订单关联信息</h2> 
		           	<div class="grid_n_cont">
		    		  	<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span id="specValidateMsg_Span"></span></div>
						<div id="relations_info" style="height: 40px"></div>
		            </div>
	            </div> --%>
	            <!-- 关联订单显示结束 -->
        	<!-- 订单基本信息开始 -->
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
	                        <td><html:orderattr  order_id="${order_id}" disabled="disabled" field_name="out_tid"  field_desc ="外部订单编号" field_type="text"></html:orderattr></td>
	                        <th>生产模式：</th>
	                        <td><html:orderattr  attr_code="DC_MODE_OPER_MODE" order_id="${order_id}" disabled="disabled" field_name="order_model"  field_desc ="生产模式" field_type="select"></html:orderattr></td>
	                      </tr>
	                      <tr>
	                        <th>外部状态：</th>
	                        <td><html:orderattr attr_code="DIC_ORDER_EXT_STATUS"  order_id="${order_id}" disabled="disabled" field_name="platform_status"  field_desc ="外部状态" field_type="select"></html:orderattr></td>
	                        <th>归属区域：</th>
	                        <td><html:orderattr attr_code="DC_MODE_REGION" disabled="disabled" order_id="${order_id}" field_name="order_city_code"  field_desc ="归属地市" field_type="select" ></html:orderattr></td>
	                        <th>订单来源：</th>
	                        <td><html:orderattr  attr_code="ORDER_FROM" order_id="${order_id}" disabled="disabled" field_name="order_from"  field_desc ="订单来源" field_type="select" ></html:orderattr></td>
	                      </tr>
	                      <tr>
	                        <th>订单来源系统：</th>
	                        <td><html:orderattr attr_code="DIC_PLAT_TYPE" order_id="${order_id}" disabled="disabled" field_name="plat_type"  field_desc ="订单来源系统" field_type="select"></html:orderattr></td>
	                        <th>下单时间：</th>
	                        <td><html:orderattr  order_id="${order_id}" field_name="tid_time" disabled="disabled"  field_desc ="下单时间" field_type="date"></html:orderattr></td>
	                        <th>订单处理类型：</th>
                            <td><html:orderattr  order_id="${order_id}" attr_code="DIC_ORDER_DEAL_TYPE"  field_name="order_deal_type"  field_desc ="订单处理类型" field_type="select"></html:orderattr></td>
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
                         <tr>
                            <th>特殊业务类型：</th>
		                     <td><html:orderattr disabled="disabled" attr_code="DC_BUSINESS_TYPE" order_id="${order_id }" field_name="special_busi_type" field_desc="业务类型" field_type="select" ></html:orderattr></td>
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
        	        <!-- 物流信息开始 -->
        	        <div class="grid_n_cont_sub">
                    	<h3>物流信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              <th><span>*</span>收货人姓名：</th>
                              <td><html:orderattr   order_id="${order_id}" field_name="ship_name"  field_desc ="收货人姓名" field_type="input" disabled="disabled" ></html:orderattr></td>
                           	  <th><span>*</span>收货电话：</th>
                              <td><html:orderattr  order_id="${order_id}" field_name="receiver_mobile"  field_desc ="手机号码" field_type="input" disabled="disabled" ></html:orderattr></td>
                            	<th><span>*</span>详细地址：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="ship_addr"  field_desc ="详细地址" field_type="input" disabled="disabled" ></html:orderattr></td>
                            </tr>
                        </table>
                    </div>
        	        <!-- 物流信息结束 -->
        	        <!-- 支付信息开始 -->
        	        <div class="grid_n_cont_sub">
                    	<h3>支付信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              <th><span>*</span>支付时间：</th>
                              <td><html:orderattr  order_id="${order_id}" disabled="disabled" field_name="pay_time"  field_desc ="支付时间" field_type="date"></html:orderattr></td>
                              <th><span>*</span>支付类型：</th>
                              <td><html:orderattr disabled="disabled" attr_code="DIC_PAY_TYPE" order_id="${order_id}" field_name="paytype"  field_desc ="支付类型" field_type="select"></html:orderattr></td>
                              <th><span>*</span>支付方式：</th>
                              <td> <html:orderattr disabled="disabled" attr_code="DIC_PAY_WAY" order_id="${order_id}" field_name="pay_method"   field_desc ="支付方式" field_type="select"></html:orderattr></td>
                            </tr>
                            <tr>
                              <th><span>*</span>支付状态：</th>
                              <td><html:orderattr disabled="disabled" attr_code="DIC_PAY_STATUS" order_id="${order_id}" field_name="pay_status"  field_desc ="支付状态" field_type="select"></html:orderattr></td>
                              <th>基金类型：</th>
                              <td><html:orderattr disabled="disabled"  attr_code="DC_ISFUND_TYPE" order_id="${order_id}" field_name="fund_type"  field_desc ="基金类型" field_type="select"></html:orderattr></td>
                              <th><span>*</span>订单金额：</th>
                              <td><span>￥<html:orderattr  order_id="${order_id}"  disabled="disabled" field_name="order_amount"  field_desc ="订单金额" field_type="input"></html:orderattr></span></td>
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
                         	 <th><span>*</span>实收金额（元）：</th>
                             <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="order_realfee"  field_desc ="实收金额（元）" field_type="input"></html:orderattr></td>
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
                                    <html:orderattr attr_code="DIC_ORDER_INVOICE_TYPE"  disabled="disabled" order_id="${order_id}" field_name="invoice_type"  field_desc ="发票类型" field_type="select"></html:orderattr>
                              	</td>
                              	<th>发票打印方式：</th>
                              	<td>
                                    <html:orderattr attr_code="DIC_ORDER_INVOICE_PRINT_TYPE"  disabled="disabled" order_id="${order_id}" field_name="invoice_print_type"  field_desc ="发票打印方式" field_type="select"></html:orderattr>
                              	</td>
                              	<th>发票抬头：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="reserve8" disabled="disabled"  field_desc ="发票抬头" field_type="input"></html:orderattr></td>
                          	</tr>
                            <tr>
                              	<th>发票内容：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="invoice_group_content"  disabled="disabled" field_desc ="发票内容" attr_code="DIC_INVOICE_GROUP_CONTENT" field_type="select"></html:orderattr></td>
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
                              <td><html:orderattr order_id="${order_id}" field_name="GoodsName"  field_desc="商品名称" field_type="text"></html:orderattr></td>
                              <th><span>*</span>商品类型：</th>
                              <td><html:orderattr disabled="disabled" order_id="${order_id}" attr_code="DC_MODE_GOODS_TYPE" field_name="goods_type"  field_desc ="商品类型" field_type="select"></html:orderattr></td>
                              <th>仓库名称：</th>
                              <td><html:orderattr disabled="disabled" attr_code="DIC_MT_STOREHOUSE" order_id="${order_id}" field_name="house_id"  field_desc ="仓库名称" field_type="select"></html:orderattr></td>
                            </tr>
                            <tr>
                             <!-- <th>商品备注：</th>
                              <td>&nbsp;</td>
                               --> 
                              <%  
                              String terminal_type = CommonDataFactory.getInstance().hasTerminal(order_id);
	                          String str2_type = CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10004);
	                          String str3_type =  SpecConsts.TYPE_ID_20001 ;
	                          String str4_type = SpecConsts.TYPE_ID_20004 ;
	                          String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
	                          String  iSold = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
                              String str_is = EcsOrderConsts.DEFAULT_STR_ONE;
	                          if(str_is.equals(str2_type)||str3_type.equals(goods_type)||SpecConsts.TYPE_ID_20000.equals(goods_type)||str4_type.equals(goods_type))
	                          {%>
	                             <th>上网时长：</th>
	                             <td><%=CommonDataFactory.getInstance().getProductSpec(order_id,SpecConsts.TYPE_ID_20001,null,SpecConsts.CARD_TIME) %></td>
	                             <c:if test="${orderTree.orderRealNameInfoBusiRequest.later_active_flag!='1'  }">
	                             <!-- 后向实名制订单已有另处显示此标签 -->
	                             <th>sim卡号：</th>
	                             <td><html:orderattr order_id="${order_id}" field_name="ICCID"  field_desc ="卡串号" field_type="input" ></html:orderattr></td>
	                             </c:if>
	                             <th>卡类型：</th>
	                             <td><html:orderattr attr_code="DC_PRODUCT_CARD_TYPE"  order_id="${order_id}" field_name="white_cart_type"  field_desc ="卡类型" field_type="select" /></td>
	                          <%} if(str_is.equals(terminal_type)){%>
                              <c:choose>
                              	<c:when test="${orderTree.orderExtBusiRequest.is_aop == '1' }">
                              	<th>终端串号：</th>
                              	<td>
                              		<html:orderattr  order_id="${order_id}" field_name="terminal_num"  field_desc ="串号" field_type="text"></html:orderattr>
                              	</td>
                              	</c:when>
                              	<c:otherwise>
                              		<th><span>*</span>终端串号：</th>
                              		<td>
                              		<html:orderattr order_id="${order_id}" field_name="terminal_num"  field_desc ="串号" field_type="input"  ></html:orderattr>
                              		</td>
                              	</c:otherwise>
                              </c:choose>
                              	<th>终端颜色：</th>
                              	<td><%=CommonDataFactory.getInstance().getProductSpec(order_id,SpecConsts.TYPE_ID_10000,null,SpecConsts.COLOR_NAME)%></td>
                               <%}%>                            
                            </tr>
							<tr>
                            <th>是否老用户：</th>
                            <td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="is_old"  field_desc ="是否老用户" field_type="select"></html:orderattr></td>
                            <%
							  //  老用户展示号码
							  String  is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
							  String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SIM_TYPE);
                                    if(EcsOrderConsts.IS_OLD_1.equals(is_old)){
							%>
                               <th>用户号码：</th>
                       	      <td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="input"></html:orderattr></td>
                       	    <%}else if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(sim_type)){
                       	    	%>
                       	    	<th>用户号码：</th>
	                            <td><html:orderattr order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码"  field_type="input" ></html:orderattr></td>
                       	    	<%
                       	    } %>
                            </tr>
                          <tr>
                          <th><c:if test="${orderTree.orderRealNameInfoBusiRequest.later_active_flag=='1'  }">
		      			<span>*</span>
		      			</c:if>开户流水号：</th>
                          <td><html:orderattr order_id="${order_id}" field_name="active_no"  field_desc ="开户流水号" field_type="input"></html:orderattr></td>
                          <c:if test="${orderTree.orderRealNameInfoBusiRequest.later_active_flag=='1'  }">
                          <!-- 只有后向实名制订单才出现此标签 -->
                          <th><span>*</span>ICCID：</th>
                          <td><html:orderattr order_id="${order_id}" field_name="ICCID"  field_desc ="卡串号" field_type="input"></html:orderattr></td>
		      			  </c:if>
                          </tr>
                        </table>
                    </div>
                     <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10002))){%>
                  
                     <div class="grid_n_cont_sub">
                      	<h3>套餐信息：</h3>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              <th>套餐名称：</th>
                              	<td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE) %></td>
                              	<th><span>*</span>首月资费方式：</th>
                              	<td>
                                	 <html:orderattr attr_code="DIC_OFFER_EFF_TYPE"  order_id="${order_id}" field_name="first_payment"  field_desc ="首月资费方式" field_type="select" ></html:orderattr>
                                </td>
                              <th>&nbsp;</th>
                              <td>&nbsp;</td>
                            </tr>
                        </table>
                     </div>
                     <%} %>
                    <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10001))){%>
               	 
                     <div class="grid_n_cont_sub">
                      	<h3>合约计划：</h3>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th><span>*</span>合约类型：</th>
                              	<td>
                                	<html:selectdict  name="package_type"  disabled="disabled" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE) %>"    attr_code="DIC_ORDER_PAY_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                                </td>
                                <th><span>*</span>合约期限：</th>
                              	<td colspan="5"><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT) %></td>
                              <th><span>*</span>合约编码：</th>
                              	<td>
                              	<input  type="text"  class="ipt_new" value="<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.OUT_PACKAGE_ID) %>" id="textfield" style="width:200px;" />
                              	</td>
                            </tr>
                            <tr>
                                <th><span>*</span>合约名称：</th>
                              	<td>
                                	<input  type="text" class="ipt_new" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.GOODS_NAME) %>" id="textfield" style="width:200px;" />
                                </td>
                                <th><span>*</span>货品类型：</th>
                              	<td colspan="5"><html:selectdict name="contract.type"  disabled="true" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID)%>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict></td>
                              <th><span>*</span>货品小类：</th>
                              	<td>
                               	  <select name="contract.cat" disabled="disabled"  style="width:200px;" class="ipt_new" >
                               	     <option value='<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID)%>' selected="selected"></option>
                               	  </select>
                               	</td>
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
                     <%} %>
               	  	<div class="grid_n_cont_sub" id="sp_product" style="height: 80px"></div>
	               	<div class="grid_n_cont_sub" id="sub_product">
               	  	     <jsp:include page="include/sub_product.jsp"/>
					</div>               	  	
               	  	
                    <!-- ZX add 2015-12-30 start 副卡信息开始 -->
                    <div class="grid_n_cont_sub">
               	  	<jsp:include page="include/phone_info_fuka.jsp?order_id=${order_id }"/>
               	  	</div>
       	  	  	</div>
   	  	  	</div>
        	<!-- 商品信息结束 -->
              <!-- 多商品货品信息开始 -->
              <c:if test="${orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053'}">
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>货品信息<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
					<div id="goods_info_more" style="height: 80px"></div>
                </div>
                
              </div>
              </c:if>
              <!-- 多商品货品信息结束 --> 
        	 <!-- 用户证件信息开始 -->
		      <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>用户证件信息</h2>
              	<div class="grid_n_cont">
           		  <div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
                    <div class="grid_n_cont_sub">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th>证件类型：</th>
                                <td>
                                    <html:orderattr attr_code="DIC_CARD_TYPE" disabled="disabled"  order_id="${order_id}" field_name="certi_type"  field_desc ="证件类型" field_type="select" ></html:orderattr>
                                </td>
                                <th>证件号码：</th>
                                <td><html:orderattr  order_id="${order_id}" disabled="disabled" field_name="cert_card_num"  field_desc ="证件号码" field_type="input" ></html:orderattr></td>
                                <th>证件地址：</th>
                                <td><html:orderattr  order_id="${order_id}" disabled="disabled" field_name="cert_address"  field_desc ="证件地址" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                                <th>客户名称：</th>
                                <td><html:orderattr  order_id="${order_id}" disabled="disabled" field_name="phone_owner_name"  field_desc ="客户名称" field_type="input" ></html:orderattr></td>
                                <th>客户类型：</th>
                                <td>
                                   <html:orderattr attr_code="DIC_CUSTOMER_TYPE" disabled="disabled"  order_id="${order_id}" field_name="CustomerType"  field_desc ="客户类型" field_type="select"  ></html:orderattr>
                                </td>
                                <th>证件有效期：</th>
                                <td><html:orderattr  order_id="${order_id}" disabled="disabled" field_name="cert_failure_time"  field_desc ="证件有效期" field_type="date"></html:orderattr></td>
                            </tr>
                        </table>
                    </div>
                  </div>
                 </div>
            		<!-- 用户证件信息结束 -->
        	<!-- 账户信息开始 -->
        	 <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>账户信息</h2>
              	<div class="grid_n_cont">
           		  <div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
                    <div class="grid_n_cont_sub">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th><span>*</span></>账户付费方式：</th>
                              	<td>
                                	<html:orderattr  attr_code="ACC_CON_PAY_MODE"  order_id="${order_id}" field_name="bill_type"  field_desc ="账户付费方式" field_type="select"></html:orderattr>
                               	</td>
                              	<th>上级银行编码：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="superiors_bankcode"  field_desc ="上级银行编码" field_type="text"></html:orderattr></td>
                               
                              <th>&nbsp;</th>
                              <td>&nbsp;</td>
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
	CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
	if(${orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053'}){//多商品
	  CommonLoad.loadJSP('goods_info_more','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"view_goods_info_more"},false,function(){AutoFlow.checkMsg();},true);
  }
});
</script>