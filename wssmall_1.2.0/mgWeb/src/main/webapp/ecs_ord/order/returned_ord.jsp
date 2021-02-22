<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="zte.net.ecsord.common.StypeConsts"%>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.utils.AttrUtils"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单预处理</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>

<%
  String order_id = (String)request.getAttribute("order_id");
String bss_refund_satus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS);
bss_refund_satus = StringUtils.isEmpty(bss_refund_satus)?"0":bss_refund_satus;
String refund_name = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.DIC_BSS_REFUND_STATUS, bss_refund_satus);
String pay_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_TYPE);
if(StringUtils.equals(pay_type, EcsOrderConsts.PAY_TYPE_HDFK)){
	  refund_name ="";
}
%>

</head>
<body>
<form action="javascript:void(0);" id="returned_ord_app_fm" method="post">

<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
        
            <!-- 顶部公共 -->
        	<jsp:include page="auto_flows.jsp?order_id=${order_id }&btn=RETURNED"/>
        	<!-- 订单基本信息开始-->
        	<div class="grid_n_div">
            	 <h2><a href="#" class="openArrow"></a>订单基本信息</h2>
              	<div class="grid_n_cont">
              	     <div class="grid_n_cont_sub">
                        <h3>订单信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
	                         <tr>
	                          <th style="color:red;">退款状态：</th>
	                          <td><%=refund_name%></td>
	                          <th style="color:red;">BSS返销状态：</th>
	                          <td>${orderTree.orderExtBusiRequest.bss_cancel_status=='1'?'已返销':'未返销' }</td>
	                          <th style="color:red;">ESS返销状态：</th>
	                          <td>${orderTree.orderExtBusiRequest.ess_cancel_status=='1'?'已返销':'未返销' }</td>
	                         </tr>
	                        <%--  <tr>
	                           <th>订单子类型11111111：</th>
	                           <td><input class="ipt_new"  value="新商城选号入网" disabled="disabled"/></td>
	                           
	                            <th>分销单号11111111：</th> 
	                           <td><input class="ipt_new"  value="" disabled="disabled"/></td>
	                           </tr> --%>
	                        <%-- <tr>
	                            <th>一级代理商11111111：</th>
	                           <html:orderattr disabled="disabled"  order_id="${order_id}" field_name="one_agents_id"  field_desc ="一级代理商" field_type="input"></html:orderattr></td>
	                           <th>二级代理商11111111：</th>
	                           <td><input type="text"  value="taobao" disabled="disabled" class="ipt_new"/></td>
	                           <th>&nbsp;</th>
	                           <td>&nbsp;</td>
	                         </tr> --%>
	                         <tr>
	                           <th>发展人编码：</th>
                               <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="development_code"  field_desc ="发展人编码" field_type="input"></html:orderattr></td>
	                           <th>内部订单编号：</th>
                               <td>${order_id}</td>
	                           <th>外部订单编号：</th>
                               <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="out_tid"  field_desc ="外部订单编号" field_type="input"></html:orderattr></td>
                               <%-- <th>第三方外部单号11111111：</th>
	                           <td><input type="text"  value="" disabled="disabled" class="ipt_new"/></td> --%>
	                         </tr>
	                         <tr>
	                           <th>归属地市：</th>
                                <td>
                                    <html:orderattr disabled="disabled" attr_code="DC_MODE_REGION" order_id="${order_id}" field_name="order_city_code"  field_desc ="归属地市" field_type="select"></html:orderattr>
                            	</td>
	                          <%--  <th>交易类型11111111：</th>
	                           <td>
	                               <select disabled="disabled"  class="ipt_new" style="width:200px;">
	                                  <option>普通</option>
	                               </select>
	                            </td> --%>
	                            <th>订单来源：</th>
                                <td>
                                    <html:orderattr disabled="disabled"  attr_code="ORDER_FROM" order_id="${order_id}" field_name="order_from"  field_desc ="订单来源" field_type="select"    ></html:orderattr>
                                </td>
                                <th>推荐人姓名：</th>
	                            <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="recommended_name"  field_desc ="推荐人名称" field_type="input"></html:orderattr></td>
                                 
	                         </tr>
	                         <tr>
	                          <th>资料上传：</th>
	                          <td>
                                    <html:orderattr disabled="disabled" attr_code="DIC_IS_UPLOAD"  order_id="${order_id}" field_name="is_upload"  field_desc ="是否已上传" field_type="select" ></html:orderattr>
                              </td>
	                           <%-- <th>优先级别11111111：</th>
	                           <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                           --%> 
	                           <th>订单发展归属：</th>
                               <td><html:orderattr attr_code ="DIC_CHANNEL_MARK" disabled="disabled" order_id="${order_id}" field_name="channel_mark"  field_desc ="推广渠道" field_type="select"></html:orderattr></td>
                                <th>接收时间：</th>
	                           <td>${orderTree.orderBusiRequest.create_time }</td>
	                           
	                         </tr>
	                         <%-- <tr>
	                           <th>催办次数11111111：</th>
	                           <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                           <th>内部状态11111111：</th>
	                           <td>
	                             <select disabled="disabled" class="ipt_new" style="width:200px;">
	                                <option>订单生成</option>  
	                             </select>
	                           </td>
	                           <th>&nbsp;</th>
                               <td>&nbsp;</td>
	                         </tr> --%>
	                         <%-- <tr>
	                            <th>生成时间11111111：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                            <th>完成时间11111111</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                            <th>外部状态：</th>
                                <td><html:orderattr  attr_code="DIC_ORDER_EXT_STATUS" order_id="${order_id}" field_name="platform_status"  field_desc ="外部状态" field_type="text"></html:orderattr></td>
                             </tr> --%>
	                        <%--  <tr>
	                            <th>推荐方式11111111：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                            <th>推荐对象11111111：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                            <th>销售经理：</th>
	                            <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="sales_manager"  field_desc ="销售经理" field_type="text"></html:orderattr></td>
	                         </tr> --%>
	                         <tr>
	                         	<th>外部状态：</th>
                                <td><html:orderattr disabled="disabled"  attr_code="DIC_ORDER_EXT_STATUS" order_id="${order_id}" field_name="platform_status"  field_desc ="外部状态" field_type="select"></html:orderattr></td>
	                            <th>是否锁定：</th>
	                            <td><html:orderattr  disabled="disabled"  attr_code="LOCK_STATUS" order_id="${order_id}" field_name="lock_status"  field_desc ="是否锁定" field_type="select"></html:orderattr></td>
	                            <th>创建时间：</th>
	                            <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="tid_time"   field_desc ="下单时间" field_type="date"></html:orderattr></td>
	                            <%-- <th>创建人11111111：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td> --%>
	                         </tr>
	                        <%--  <tr>
	                            <th>修改时间11111111：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                            <th>修改人：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td> --%>
	                             
	                         <%-- <tr>
	                            <th>订单环节11111111: </th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                            <th>是否已补差价11111111: </th>
	                            <td>
	                             <select disabled="disabled" class="ipt_new" style="width:200px;">
	                                <option value=''>--请选择--</option>
	                             </select>
	                            </td>
	                            <th>差价单编号11111111: : </th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                         </tr>   --%>
	                         <tr>
	                            <%-- <th>老带新号码11111111：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td> --%>
	                            <th>资料回收方式：</th>
                                <td><html:orderattr attr_code="DIC_RECOVERTY_TYPE" disabled="disabled" order_id="${order_id}" field_name="file_return_type"  field_desc ="资料回收方式" field_type="select"></html:orderattr></td>
                                
	                            <th>发票打印：</th>
                                <td>
                                    <html:orderattr disabled="disabled" attr_code="DIC_ORDER_INVOICE_PRINT_TYPE"  order_id="${order_id}" field_name="invoice_print_type"  field_desc ="发票打印方式" field_type="select" ></html:orderattr>
                              	</td><th>发票名称：</th>
                                <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="reserve8"  field_desc ="发票抬头" field_type="input"></html:orderattr></td>
                           </tr>
	                         <%-- <tr>        
	                            <th>资料回收方式：</th>
                                <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="file_return_type"  field_desc ="资料回收方式" field_type="text"></html:orderattr></td>
                                <!-- <!-- <th>统计时间11111111: </th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td> --> 
	                            <th>交易时间: </th>
	                            <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="tid_time"  field_desc ="交易时间" field_type="input"></html:orderattr></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                             </tr>    --%>
	                         <%-- <tr>
	                            <th>子订单11111111:</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                            <th>社会机11111111:</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                            <th> 预约单11111111:</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                         </tr>    --%>
	                         <tr>
	                            <th>下单时间：</th>
                                <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="tid_time"  field_desc ="下单时间" field_type="input"></html:orderattr></td>
                                <th>订单处理类型：</th>
                                <td><html:orderattr disabled="disabled" order_id="${order_id}" attr_code="DIC_ORDER_DEAL_TYPE" field_name="order_deal_type"  field_desc ="订单处理类型" field_type="select"></html:orderattr></td>
                                <th>订单类型：</th>
                                <td>
                                 <html:orderattr disabled="disabled" attr_code="DC_ORDER_NEW_TYPE" order_id="${order_id}" field_name="order_type"  field_desc ="订单类型" field_type="select" ></html:orderattr>
                            	</td>
                               <%--  <th>第三方处理机构11111111: </th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                          --%>
	                         </tr>       
	                         <%-- <tr>
	                            <th>第三方处理状态:</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                           
	                           
	                            
	                            <th>&nbsp;</th>
                                <td>&nbsp;</td>
	                         </tr>   --%>  
	                         <tr>
	                            <th>所属用户: </th>
	                            <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="reserve7"  field_desc ="所属用户" field_type="input" ></html:orderattr>
                            	</td>
	                            <th>推广渠道：</th>
                                <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="reserve4"  field_desc ="推广渠道" field_type="input"></html:orderattr></td>
                                <th>业务凭证号：</th>
	                            <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="busi_credence_code"  field_desc ="业务凭证号" field_type="input"></html:orderattr></td>
	                         </tr>    
	                         <tr>
	                            <%-- <th>三方协议编码11111111:</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                           --%>  
	                           <th>渠道ID: </th>
	                            <td>
                                   <html:orderattr disabled="disabled"  attr_code="DIC_CHANNEL_MARK" order_id="${order_id}" field_name="reserve3"  field_desc ="渠道标识" field_type="select"></html:orderattr>
                            	 </td>
	                            <th>集团编码：</th>
                              	<td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="group_code"  field_desc ="集团编码" field_type="input"></html:orderattr></td>
                                <th>异常单：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
                              </tr>
	                         <tr>
                              	<th>买家留言：</th>
                              	<td><html:orderattr disabled="disabled"  cols="45"  order_id="${order_id}" field_name="buyer_message"  rows="5" field_desc ="买家留言" field_type="input" ></html:orderattr></td>
                            	<th>卖家留言：</th>
                              	<td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="seller_message" cols="45"  rows="5" field_desc ="卖家留言" field_type="input" ></html:orderattr></td>
                            	<th>备注信息：</th><!-- 取客服备注 -->
                              	<td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="service_remarks" cols="45"   field_desc ="订单备注" field_type="input"></html:orderattr></td>
                            </tr>
                          	<tr>
                                <th>商品原价（系统价）：</th>
                                <td>${good_price_system}</td>
                                <th>靓号预存（元）：</th>
                                <td>${num_price}</td>
                                <th>多缴预存（元）：</th>
                                <td>${deposit_price}</td>
                          	</tr>
                            <tr>   
                                <th>开户费（元）：</th>
                                <td>${openAcc_price}</td> 
                            	<th>实收金额（元）：</th>
                                <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="order_realfee"  field_desc ="实收金额（元）" field_type="input"></html:orderattr></td>
                          	
	                          <th>特殊业务类型：</th>
	                             <td><html:orderattr disabled="disabled" attr_code="DC_BUSINESS_TYPE" order_id="${order_id }" field_name="special_busi_type" field_desc="业务类型" field_type="select" ></html:orderattr></td>
	                      </tr>
                        </table>
                     </div>
              	</div>
            <div>
        	<!-- 订单基本信息结束 -->
        	<!-- 客户信息开始 -->
            <div class="grid_n_div">
            	 <h2><a href="#" class="openArrow"></a>客户信息</h2>
              	<div class="grid_n_cont">
              	     <div class="grid_n_cont_sub">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                               <th>开户人姓名: </th>
                               <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="phone_owner_name"  field_desc ="客户名称" field_type="input"></html:orderattr></td>
                               <th>开户人证件类型：</th>
                               <td><html:orderattr disabled="disabled" attr_code="DIC_CARD_TYPE"  order_id="${order_id}" field_name="certi_type"  field_desc ="证件类型" field_type="select"></html:orderattr></td>
                                <th>开户人证件有效期：</th>
                               <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="cert_failure_time"  field_desc ="证件有效期" field_type="date"></html:orderattr></td>
                            </tr>
                            <tr>
                               <th>靓号金额：</th>
                               <td><html:orderattr   disabled="disabled"    order_id="${order_id}" field_name="liang_price"  field_desc ="靓号金额" field_type="input"></html:orderattr></td>
                               <th>开户人证件号码：</th>
                               <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="cert_card_num"  field_desc ="证件号码" field_type="input"></html:orderattr></td>
                               <th>开户人证件地址：  </th>
                               <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="cert_address"  field_desc ="证件地址" field_type="input"></html:orderattr></td>
                            </tr>                             
                        </table>
                     </div>
                </div>
             </div>
            <!-- 客户信息结束 -->    
            <div class="grid_n_div">
            	 <h2><a href="#" class="openArrow"></a>商品信息</h2>
              	<div class="grid_n_cont">
              	     <div class="grid_n_cont_sub">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                             <tr>
	                            <th>商品名称：</th>
                                <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="GoodsName"  field_desc ="商品名称" field_type="input"></html:orderattr></td>
                                <th>商品类型：</th>
                                <td><html:orderattr  disabled="disabled" attr_code="DC_MODE_GOODS_TYPE"  order_id="${order_id}" field_name="goods_type"  field_desc ="商品包类型 " field_type="select"></html:orderattr></td>
                                <th>是否靓号：</th>
                                <td>
                                  <html:orderattr  disabled="disabled"  attr_code="DIC_IS_LOVER"    order_id="${order_id}" field_name="is_loves_phone"  field_desc ="是否情侣号" field_type="select"></html:orderattr>
                                </td>
                               </tr>
                                <%-- <tr>
                                   <!-- <th>是否已补差价1111111:</th>
                                   <td>
                                     <select disabled="disabled" class="ipt_new" style="width:200px;">
	                                   <option value=''>--请选择--</option>
	                                 </select >
	                               </td>  
                                   <th>靓号单编号1111111: </th>
                                   <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
                                  -->
                                   <th>靓号金额：</th>
                                   <td><html:orderattr    order_id="${order_id}" field_name="liang_price"  field_desc ="靓号金额" field_type="input"></html:orderattr></td>
                                   <th>&nbsp;</th>
                                   <td>&nbsp;</td>
                                   <th>&nbsp;</th>
                                   <td>&nbsp;</td>
                                </tr>  --%>   
                                <tr>
                                   <th>品牌：</th>
                                   <td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.BRAND_NAME) %></td>
                                   <th>机型：</th>
                                   <td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, AttrConsts.MODEL_CODE)%></td>
                                   <th>串号：</th>
                              	   <td>
                                    <html:orderattr  disabled="disabled"   order_id="${order_id}" field_name="terminal_num"  field_desc ="证件有效期" field_type="text"></html:orderattr>
                              	  </td>
                                </tr>    
                                <tr>
                                   <th>套餐名称：</th>
                                   <td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE) %>/td>
                              	   <th>商品价格：</th>
                                   <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="pro_origfee"  field_desc ="商品价格" field_type="input"></html:orderattr></td>
                                   <th>优惠金额： </th>
                                    <td>
                                    <html:orderattr  disabled="disabled"   order_id="${order_id}" field_name="discountrange"  field_desc ="优惠价格" field_type="input"></html:orderattr>
                                    </td>
                                </tr>
                                 <tr>
                                   <!-- <th>调整金额111111:</th>
                                   <td><input type="text" value='' disabled="disabled" class="ipt_new"/></td> -->
                                    <th>优惠劵金额：</th>
                                   <td>${orderTree.orderBusiRequest.order_coupon }</td>
                                   <th>应付金额：</th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="order_origfee"  field_desc ="应付金额" field_type="input"></html:orderattr></td>
                                   <th>活动类型：</th>
                                    <td><html:orderattr  disabled="disabled" field_name="ative_type" field_value="<%=CommonDataFactory.getInstance().getActivitySpec(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DISCOUNT_ID), SpecConsts.PMT_TYPE)%>" attr_code="DC_ACT_PMT_TYPE" field_type="select"></html:orderattr></td>
                                 </tr>      
                                 <tr>
                                   <th>首月资费：</th>
                                   <td>
                                	 <html:orderattr disabled="disabled" attr_code="DIC_OFFER_EFF_TYPE"  order_id="${order_id}" field_name="first_payment"  field_desc ="首月资费方式" field_type="select"></html:orderattr>
                                   </td>
                                   <th>号码预存款：</th>
                                   <td><%=CommonDataFactory.getInstance().getGoodSpec(order_id,  null,SpecConsts.DEPOSIT_FEE) %></td>
                                   <th>生效方式：</th>
                                   <td><html:orderattr disabled="disabled" attr_code="active_sort"  order_id="${order_id}" field_name="active_sort"  field_desc ="生效方式" field_type="select"></html:orderattr></td>
                                </tr>      
                                 <tr>
                                   <th>是否托收：</th>
                                   <td><html:orderattr disabled="disabled" attr_code="DIC_IS_COLLECTION"  order_id="${order_id}" field_name="collection"  field_desc ="是否托收" field_type="select"></html:orderattr></td>
                                   <th>托收银行：</th>
                                  <td><html:orderattr disabled="disabled"  attr_code="DIC_BANK_NAME"  order_id="${order_id}" field_name="bank_code"  field_desc ="托收银行" field_type="select"></html:orderattr></td>
                              	  <th>优惠劵金额：</th>
                                   <td>${orderTree.orderBusiRequest.order_coupon }</td>
                                </tr>
                                 <tr>
                                   <th>合约期：</th>
                                   <td><%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT) %></td>
                                   <th>社会代理商：</th>
                                   <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="society_name"  field_desc ="社会代理商名称" field_type="input"></html:orderattr></td>
                                   <th>代理商终端结算价(元)：</th>
                                   <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="society_price"  field_desc ="代理商终端结算价格(元)" field_type="input"></html:orderattr></td>
                                </tr>
                        </table>
                     </div>
                     
                     <!-- ZX add 2015-12-30 start 副卡信息开始 -->
               	  	<jsp:include page="include/phone_info_fuka.jsp?order_id=${order_id }"/>
                </div>
             </div>
            <!-- 付款信息开始 -->    	
             <div class="grid_n_div">
            	 <h2><a href="#" class="openArrow"></a>支付信息</h2>
              	<div class="grid_n_cont">
              	     <div class="grid_n_cont_sub">
                        
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                                <tr>
                                   <th>支付交易号：</th>
                                   <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="payplatformorderid"  field_desc ="支付流水号" field_type="input"></html:orderattr></td>
                              	   <th> 支付类型：</th>
                                   <td>
                                    <html:orderattr disabled="disabled" attr_code="DIC_PAY_TYPE" order_id="${order_id}" field_name="paytype"  field_desc ="支付类型"  field_type="select"></html:orderattr>
                            	   </td>
                                   <th>支付方式：</th>
                                   <td>
                                   <html:orderattr disabled="disabled" attr_code="DIC_PAY_METHOD" order_id="${order_id}" field_name="pay_method"   field_desc ="支付方式" field_type="select"></html:orderattr>
                            	</td>
                                </tr>       
                                <tr>
                                   <th>支付状态：</th>
                                   <td>
                                     <html:orderattr disabled="disabled" attr_code="DIC_PAY_STATUS" order_id="${order_id}" field_name="pay_status"  field_desc ="支付状态" field_type="select"></html:orderattr>
                            	   </td>
                                   <%-- <th>支付银行111111：</th>
                                   <td><input type="text" value='' disabled="disabled" class="ipt_new"/></td>
                                   <th>支付账号111111：</th>
                                   <td><input type="text" value='' disabled="disabled" class="ipt_new"/></td> --%>
                                   <th>&nbsp;</th>
                                   <td>&nbsp;</td>
                                   <th>&nbsp;</th>
                                   <td>&nbsp;</td>
                                </tr>        
                                <tr>
                                   <th>付款时间：</th>
                                   <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="pay_time"  field_desc ="支付时间" field_type="date"></html:orderattr>
                            	   </td>
                                   <th>优惠金额： </th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="order_disfee"  field_desc ="优惠金额（元）" field_type="input"></html:orderattr></td>
                                   <th>调整金额：</th>
                                   <td><input type="text" value='' disabled="disabled" class="ipt_new"/></td>
                                </tr>      
                                <tr>
                                   <th>订单金额： </th>
                                   <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="order_origfee"  field_desc ="订单总价（元）" field_type="input"></html:orderattr></td>
                                   <th>付款金额： </th>
                                   <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="order_realfee"  field_desc ="实收金额（元）" field_type="input"></html:orderattr></td>
                                  </tr>
                                <tr>   
                                	<th>发票信息： </th>                                  
                                   	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="invoice_group_content"  field_desc ="发票内容" attr_code="DIC_INVOICE_GROUP_CONTENT" field_type="select"></html:orderattr></td>
                                    <th>发票号码：</th>
	                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="invoice_no"  field_desc ="发票号码" field_type="input"></html:orderattr></td>
	                             	<th>发票代码：</th>
	                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="invoice_code"  field_desc ="发票代码" field_type="input"></html:orderattr></td>
                           
                                </tr>
                                <tr>
                                   <!-- <th>修改人111111：</th>
                                   <td></td> -->
                                   <th>代金券编号：</th>
                                   <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="vouchers_code"  field_desc ="代金券编号" field_type="input"></html:orderattr></td>
                                  <th>代金券面值： </th>
                                  <td colspan="3"><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="vouchers_money" field_desc="代金券面值" field_type="input"></html:orderattr></td>
                                </tr>
                        </table>
                      
                    </div>
                 </div>
             </div>
            <!-- 付款信息结束-->    	
            <!-- 配送信息开始 -->
              <div class="grid_n_div">
            	 <h2><a href="#" class="openArrow"></a>配送信息</h2>
              	<div class="grid_n_cont">
              	     <div class="grid_n_cont_sub">
                            
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                                <tr>
                                   <th>收货人姓名：</th>
                                   <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="ship_name"  field_desc ="收货人姓名" field_type="input"></html:orderattr></td>
                              	   <th> 收货邮编： </th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="ship_zip"  field_desc ="收货邮编" field_type="input"></html:orderattr></td>
                              	   <th>归属地市： </th>
                                   <td>
                                    <html:orderattr disabled="disabled" attr_code="DC_MODE_REGION" order_id="${order_id}" field_name="order_city_code"  field_desc ="归属地市" field_type="select" ></html:orderattr>
                            	   </td>
                                </tr>       
                                <tr>
                                   <th>收货人手机：</th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="receiver_mobile"  field_desc ="手机号码" field_type="input"></html:orderattr></td>
                              	   <th>收货人电话： </th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="reference_phone"  field_desc ="固定电话" field_type="input"></html:orderattr></td>
                                   <th>配送方式： </th>
                                   <td>
                                    <html:orderattr disabled="disabled"   attr_code="DC_MODE_SHIP_TYPE" order_id="${order_id}" field_name="sending_type"  field_desc ="配送方式" field_type="select" ></html:orderattr>
                            	   </td>
                                </tr>     
                                <tr>
                                   <th>配送时间：</th>
                                   <td><html:orderattr disabled="disabled"  attr_code="DIC_SHIPPING_TIME" order_id="${order_id}" field_name="shipping_time"  field_desc ="配送时间" field_type="select"></html:orderattr></td>
                              	   <th>配送区域：</th>
                                   <td>${delivery.region }</td>
                                   <th>配送地址：</th>
                                   <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="ship_addr"  field_desc ="详细地址" field_type="input"></html:orderattr></td>
                              	</tr>       
                                <tr>
                                   <th>配送状态：</th>
                                   <td>${delivery.ship_status=='1'?'已配送':'未配送' }</td>
                                   <th>创建人：</th>
                                   <td>${delivery.ship_name }</td>
                                   <th>修改时间：</th>
                                   <td>${orderTree.orderBusiRequest.ship_time }</td>
                                </tr>
                                <tr>
                                   <th>修改人：</th>
                                   <td>${delivery.op_name }</td>
                                   <th>创建时间：</th>
                                   <td>${delivery.create_time }</td>
                                   <th>&nbsp;</th>
                                   <td>&nbsp;</td>
                                </tr>
                                <tr>
                              	<th>描述：</th><!-- 取客服备注 -->
                              	<td colspan="5"><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="service_remarks"  field_desc ="客服备注" field_type="input"></html:orderattr></td>
                            </tr>
                        </table>
                      
                    </div>
                 </div>
             </div>    
            <!-- 配送信息结束 -->
            <!-- 礼品信息开始 -->
               	  <c:if test="${giftInfoList!=null&&giftInfoSize>0}">
               	  	<div class="grid_n_cont_sub">
                    	<h3>礼品（多个记录）：</h3>
                    	<div class="netWarp">
                        	<a href="#" class="icon_close">展开</a>
                        	<c:forEach var = "gift" items="${giftInfoList}" varStatus="i_gift">
                        	    <div class="goodTit">礼品${i_gift.index+1}<a href="#" class="icon_del"></a></div>
	                        	<div class="goodCon">
	                            	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="goodTable">
	                              		<tr>
	                                		<th>赠品类型：</th>
	                                		<td>
	                                		  <html:selectdict  name="gift.goods_type" curr_val="${gift.goods_type}" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
	                                        </td>
	                               			<th>赠品编码：</th>
	                                		<td>${gift.gift_id}</td>
	                                		<th>赠品名称：</th>
	                                		<td>${gift.goods_name}</td>
	                              		</tr>
	                              		<tr>
	                                		<th>赠品面值：</th>
	                                		<td>${gift.gift_value}</td>
	                                		<th>赠品面值单位：</th>
	                                		<td>
	                                        	 <html:selectdict  name="gift.gift_unit" curr_val="${gift.gift_unit}" appen_options="<option value=''>---请选择---</option>"   disabled="disabled"   attr_code="DIC_GIFT_UNIT" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
	                                  		</td>
	                                  		
	                                		<th>赠品数量：</th>
	                                		<td>${gift.sku_num}</td>
	                              		</tr>
	                              		<tr>
	                                		<th>赠品描述：</th>
	                                		<td colspan="5"><textarea name="gift.gift_desc"   disabled="disabled" cols="30" rows="5">${gift.gift_desc}</textarea></td>
	                              		</tr>
	                              		<tr>
	                                		<th>赠品品牌：</th>
	                                		<td>
	                                		    
	                                        	<select name="gift.gift_brand"  disabled="disabled" style="width:200px;">
	                                        		<option value="">--请选择--</option>
	                                  				<c:forEach var="giftBrand" items="${giftBrandList}">
	                                  				   <option <c:if test="${gift.gift_brand!=null && gift.gift_brand==giftBrand.brand_code}">selected="selected"</c:if> value="${giftBrand.brand_code}">${giftBrand.name}</option>
	                                  				</c:forEach>
	                                			</select>
	                                			
	                                        </td>
	                                		<th>赠品型号：</th>
	                                		<td>
	                                        	<select name="gift.gift_model"  disabled="disabled"  style="width:200px;">
	                                  				<option value="1"></option>
	                                			</select>
	                                        </td>
	                                		<th>赠品颜色：</th>
	                                		<td>${gift.gift_color}</td>
	                              		</tr>
	                              		<tr>
	                                		<th>赠品机型：</th>
	                                		<td>
	                                        	${gift.gift_sku }
	                                        </td>
	                                		<th>是否需要加工：</th>
	                                		<td>
	                                		<html:selectdict  disabled="disabled"  name="gift.is_process" curr_val="${gift.is_process}"    attr_code="DIC_IS_PROCESS" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
	                                		</td>
	                                		<th>加工类型：</th>
	                                		<td>
	                                        	<html:selectdict  disabled="disabled" appen_options="<option value=''>---请选择---</option>"  name="gift.process_type" curr_val="${gift.process_type}"    attr_code="DIC_PROCESS_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
	                                        </td>
	                              		</tr>
	                              		<tr>
	                                		<th>加工内容：</th>
	                                		<td>${gift.process_desc}</td>
	                                		<th>&nbsp;</th>
	                                		<td>&nbsp;</td>
	                                		<th>&nbsp;</th>
	                                		<td>&nbsp;</td>
	                              		</tr>
	                            	</table>
	                      		</div>
                        	</c:forEach>
                        	
                      	   </div>
                      	 </div>
                      	</c:if>
               	  	<!-- 礼品信息结束 -->
               	  	
            <div class="grid_n_div">
			    <div class="grid_n_div">
					<div class="grid_n_cont">
			  		<div class="grid_n_cont_sub">
			              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="blue_grid">
			              <tr>
			                     <th style="text-align: right"><span style="color:red;">*</span>退单描述：</th>
			                     <td> 
				                     <select name="returnedReasonCode" class="ipt_new" style="width:140px;">
				               		   <c:forEach items="${ordReturnedReasonList }" var="ds">
				               			 <option value="${ds.pkey }"  >${ds.pname }</option>
				               		   </c:forEach>
								     </select> 
						        </td>
			                   </tr>
			                <tr>
			                     <th style="text-align: right"><span style="color:red;">*</span> 处理意见：</th>
			                     <td><textarea id="node_deal_message" name="dealDesc" cols="45" rows="5" class='ipt_new' style="height:100px;width:400px;"></textarea></td>
			                   </tr>
			              </tbody></table>
			      	</div>
			   		</div>
				</div>
			</div>
			<br />
			<br />
        </div>
    </div>
</div>
</form>
</body>
</html>