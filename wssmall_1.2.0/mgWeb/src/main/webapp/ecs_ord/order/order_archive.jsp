<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="zte.net.ecsord.common.StypeConsts"%>
<%@page import="zte.net.ecsord.utils.AttrUtils"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderFileBusiRequest"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ztesoft.soc.fastdfs.IDfsManager"%>
<%@page import="com.ztesoft.net.framework.context.spring.SpringContextHolder"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/artZoom.min.js"></script>
<link href="<%=request.getContextPath() %>/public/common/control/css/lrtk.css" rel="stylesheet" type="text/css" />
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单预处理</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<%
  String order_id = (String)request.getAttribute("order_id");
	String bss_refund_satus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS);
	String refund_name = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.DIC_BSS_REFUND_STATUS, bss_refund_satus);
	String pay_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_TYPE);
	  if(StringUtils.equals(pay_type, EcsOrderConsts.PAY_TYPE_HDFK)){
		  refund_name ="";
	  }
%>

</head>
<body>
<form action="javascript:void(0);" id="preDealOrderForm" method="post">

<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
        
            <!-- 顶部公共 -->
            <div id="auto_flows_div">
	            <c:choose>
	            	<c:when test="${orderTree.orderExtBusiRequest.is_work_custom=='1'}">
	            		<jsp:include page="custom_flow.jsp?order_id=${order_id }"/>
	            	</c:when>
	            	<c:otherwise>
	            		<jsp:include page="auto_flows.jsp?order_id=${order_id }"/>
	            	</c:otherwise>
	            </c:choose>
     		</div>
     		
        	<!-- 订单基本信息开始-->
        	<div class="grid_n_div">
            	 <h2><a href="#" class="openArrow"></a>订单基本信息</h2>
              	<div class="grid_n_cont">
              	     <div class="grid_n_cont_sub">
                        <h3>订单信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
	                         <tr>	                         
	                          <th style="color:red;">退款状态：</th>
	                          <td><%=refund_name %></td>
	                          <th style="color:red;">BSS返销状态：</th>
	                          <td>${orderTree.orderExtBusiRequest.bss_cancel_status=='1'?'已返销':'未返销' }</td>
	                          <th style="color:red;">ESS返销状态：</th>
	                          <td>${orderTree.orderExtBusiRequest.ess_cancel_status=='1'?'已返销':'未返销' }</td>
	                         </tr>
	                        <!--  <tr>
	                           <th>订单子类型11111111：</th>
	                           <td><input class="ipt_new"  value="新商城选号入网" disabled="disabled"/></td>
	                           
	                            <th>分销单号11111111：</th> 
	                           <td><input class="ipt_new"  value="" disabled="disabled"/></td>
	                           </tr> -->
	                        <!-- <tr>
	                            <th>一级代理商11111111：</th>
	                           <html:orderattr disabled="disabled"  order_id="${order_id}" field_name="one_agents_id"  field_desc ="一级代理商" field_type="input"></html:orderattr></td>
	                           <th>二级代理商11111111：</th>
	                           <td><input type="text"  value="taobao" disabled="disabled" class="ipt_new"/></td>
	                           <th>&nbsp;</th>
	                           <td>&nbsp;</td>
	                         </tr> -->
	                         <tr>
	                           <th>发展人编码：</th>
                               <td>${developmentCode }</td>
	                           <th>内部订单编号：</th>
                               <td>${order_id}</td>
	                           <th>外部订单编号：</th>
                               <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="out_tid"  field_desc ="外部订单编号" field_type="input"></html:orderattr></td>
                               <!-- <th>第三方外部单号11111111：</th>
	                           <td><input type="text"  value="" disabled="disabled" class="ipt_new"/></td> -->
	                         </tr>
	                         <tr>
	                           <th>发展人名称：</th>
                               <td>${developmentName }</td>
	                           <th>发展点编码：</th>
                               <td>${development_point_code}</td>
	                           <th>发展点名称：</th>
                               <td>${development_point_name}</td>
	                         </tr>
	                         <tr>
	                           <th><span>*</span>归属地市：</th>
                                <td>
                                    <html:orderattr disabled="disabled" attr_code="DC_MODE_REGION" order_id="${order_id}" field_name="order_city_code"  field_desc ="归属地市" field_type="select" ></html:orderattr>
                            	</td>
	                          <!--  <th>交易类型11111111：</th>
	                           <td>
	                               <select disabled="disabled"  class="ipt_new" style="width:200px;">
	                                  <option>普通</option>
	                               </select>
	                            </td> -->
	                            <th><span>*</span>订单来源：</th>
                                <td>
                                    <html:orderattr disabled="disabled"  attr_code="ORDER_FROM" order_id="${order_id}" field_name="order_from"  field_desc ="订单来源" field_type="select"    ></html:orderattr>
                                </td>
                                <th>推荐人姓名：</th>
	                            <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="recommended_name"  field_desc ="推荐人名称" field_type="input"></html:orderattr></td>
                                 
	                         </tr>
	                         <tr>
	                          <th>资料上传：</th>
	                          <td>
                                    <html:orderattr disabled="disabled" attr_code="DIC_IS_UPLOAD"  order_id="${order_id}" field_name="is_upload"  field_desc ="是否已上传" field_type="select" ></html:orderattr>
                              </td>
	                           <!-- <th>优先级别11111111：</th>
	                           <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                           --> 
	                           <th>订单发展归属：</th>
                               <td> <html:orderattr disabled="disabled"  attr_code="DIC_CHANNEL_MARK" order_id="${order_id}" field_name="channel_mark"  field_desc ="订单发展归属" field_type="select"></html:orderattr>
                               </td>
                                <th>接收时间：</th>
	                           <td>${orderTree.orderBusiRequest.create_time }</td>
	                           
	                         </tr>
	                         
	                         
	                         <!-- <tr>
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
	                         </tr> -->
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
	                            <td><html:orderattr attr_code="LOCK_STATUS" disabled="disabled"  order_id="${order_id}" field_name="lock_status"  field_desc ="是否锁定" field_type="select"></html:orderattr></td>
	                            <th>创建时间：</th>
	                            <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="tid_time"   field_desc ="下单时间" field_type="date"></html:orderattr></td>
	                            <!-- <th>创建人11111111：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td> -->
	                            

	                        <!--  <tr>
	                            <th>修改时间11111111：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                            <th>修改人：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td> -->
	                             
	                         <!-- <tr>
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
	                         </tr>   -->
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
	                         <!-- <tr>
	                            <th>子订单11111111:</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                            <th>社会机11111111:</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                            <th> 预约单11111111:</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                         </tr>    -->
	                         <tr>
	                            <th>下单时间：</th>
                                <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="tid_time"  field_desc ="下单时间" field_type="input"></html:orderattr></td>
                                <th>订单处理类型：</th>
                                <td><html:orderattr disabled="disabled" order_id="${order_id}" attr_code="DIC_ORDER_DEAL_TYPE" field_name="order_deal_type"  field_desc ="订单处理类型" field_type="select"></html:orderattr></td>
                                <th><span>*</span>订单类型：</th>
                                <td>
                                 <html:orderattr disabled="disabled" attr_code="DC_ORDER_NEW_TYPE" order_id="${order_id}" field_name="order_type"  field_desc ="订单类型" field_type="select" ></html:orderattr>
                            	</td>
                               <!--  <th>第三方处理机构11111111: </th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                          -->
	                         </tr>       
	                         <!-- <tr>
	                            <th>第三方处理状态:</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                           
	                           
	                            
	                            <th>&nbsp;</th>
                                <td>&nbsp;</td>
	                         </tr>   -->  
	                         <tr>
	                            <th>所属用户: </th>
	                            <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="reserve7"  field_desc ="所属用户" field_type="input" ></html:orderattr>
                            	</td>
	                            <th>推广渠道：</th>
                                <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="reserve4"  field_desc ="推广渠道" field_type="input"></html:orderattr></td>
                                <th>业务凭证号: </th>
	                            <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="busi_credence_code"  field_desc ="业务凭证号" field_type="input"></html:orderattr></td>
	                         </tr>    
	                         <tr>
	                            <!-- <th>三方协议编码11111111:</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
	                           -->  
	                           <th>渠道ID: </th>
	                            <td>
                                   <html:orderattr disabled="disabled"  attr_code="DIC_CHANNEL_MARK" order_id="${order_id}" field_name="reserve3"  field_desc ="渠道标识" field_type="select"></html:orderattr>
                            	 </td>
	                            <th>集团编码：</th>
                              	<td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="group_code"  field_desc ="集团编码" field_type="input"></html:orderattr></td>
                                <th>异常单:</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td>
                              </tr>
	                         <tr>
                              	<th>买家留言：</th>
                              	<td><html:orderattr disabled="disabled"  cols="45"  order_id="${order_id}" field_name="buyer_message"  rows="5" field_desc ="买家留言" field_type="textarea" ></html:orderattr></td>
                            	<th>卖家留言：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="seller_message" cols="45"  rows="5" field_desc ="卖家留言" field_type="textarea" ></html:orderattr></td>
                            	<th>备注信息：</th><!-- 取客服备注 -->
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="service_remarks" cols="45"   field_desc ="订单备注" field_type="input" style="width:200px;"></html:orderattr></td>
                            </tr>
                          	<tr>
                          		<th>特殊业务类型：</th>
	                          	<td><html:orderattr disabled="disabled" attr_code="DC_BUSINESS_TYPE" order_id="${order_id }" field_name="special_busi_type" field_desc="业务类型" field_type="select" /></td>
	                           <!-- <th>老带新号码11111111：</th>
	                            <td><input type="text"   value="" disabled="disabled" class="ipt_new"/></td> -->
	                            <th>资料回收方式：</th>
                                <td><html:orderattr attr_code="DIC_RECOVERTY_TYPE" disabled="disabled" order_id="${order_id}" field_name="file_return_type"  field_desc ="资料回收方式" field_type="select"></html:orderattr></td>
                             </tr>
	                         <tr>
	                            <th>发票打印：</th>
                                <td>
                                    <html:orderattr disabled="disabled" attr_code="DIC_ORDER_INVOICE_PRINT_TYPE"  order_id="${order_id}" field_name="invoice_print_type"  field_desc ="发票打印方式" field_type="select" ></html:orderattr>
                              	</td><th>发票名称：</th>
                                <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="reserve8"  field_desc ="发票抬头" field_type="input"></html:orderattr></td>
                           </tr>
                           <tr>
                                <th>发票号码：</th>
                             	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="invoice_no"  field_desc ="发票号码" field_type="input"></html:orderattr></td>
                            	<th>发票代码：</th>
                             	<td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="invoice_code"  field_desc ="发票代码" field_type="input"></html:orderattr></td>
                          	                             <th>稽核信息:</th>
                            <td>
                             <c:if  test="${orderTree.orderExtBusiRequest.audit_type==0}">稽核退单</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==1}">稽核退款</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==2}">稽核补单</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==3}">稽核补款</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==4}">稽核退现金</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==null || orderTree.orderExtBusiRequest.audit_type==''}">暂无稽核信息</c:if>
                            </td>
	                         </tr>
                            </tr>
                            <tr>
                          		<th><span>*</span>主卡号码：</th>
                                <td>${mainnumber}</td>
                                
                                <th><span>*</span>操作类型：</th>
                                <td>
                                  	<html:orderattr disabled="disabled" attr_code="DC_OPT_TYPE" order_id="${order_id}" field_name="optType"  field_desc ="操作类型" field_type="select" ></html:orderattr>
                            	</td>
                          	</tr>
                    
                        </table>
                     </div>
              	</div>
            <div>
        	<!-- 订单基本信息结束 -->
        	<div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>客户历史订单</h2> 
              	<div class="grid_n_cont">
              		              	 <div id="order_his_before" style="height: 80px;" ></div>
              		<div id="order_his" style="height: 80px;display:none" ></div>
                </div>
              </div>
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>电话外呼记录</h2> 
              	  
              	<div class="grid_n_cont">
              		<div id="calllog_info_before" style="height: 80px;" ></div>
              		<div id="calllog_info" style="height: 80px;display:none" ></div>
                </div>
              </div> 
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>订单修改记录</h2> 
              	<div class="grid_n_cont">
              		<div id="order_change" style="height: 80px"></div>
                </div>
              </div>
        	<!-- 客户信息开始 -->
            <div class="grid_n_div">
            	 <h2><a href="#" class="openArrow"></a>客户信息</h2>
              	<div class="grid_n_cont">
              	     <div class="grid_n_cont_sub">
                  
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                             <tr>
	                            <th>商品名称：</th>
                                <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="GoodsName"  field_desc ="商品名称" field_type="input"></html:orderattr></td>
                                <th>商品类型:</th>
                                <td><html:orderattr  disabled="disabled" attr_code="DC_MODE_GOODS_TYPE"  order_id="${order_id}" field_name="pack_type"  field_desc ="商品包类型 " field_type="select"></html:orderattr></td>
                                <th>是否靓号：</th>
                                <td>
                                  <html:orderattr disabled="disabled" attr_code="DIC_IS_LOVER"    order_id="${order_id}" field_name="is_loves_phone"  field_desc ="是否情侣号" field_type="select"></html:orderattr>
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
                                <%--  <th>品牌:</th>
                                   <td><input  name="termianl.brand" field_desc="品牌"  style="width:200px;" class="ipt_new" type="text" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.BRAND_NAME) %>" readonly="readonly" /></td>
                                   <th>机型: </th>
                                   <td><input  name="termianl.model_code" field_desc="机型编码"  style="width:200px;" class="ipt_new" type="text" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, AttrConsts.MODEL_CODE)%>" readonly="readonly" /></td>
                                   --%> 
                                   <th><span>*</span>串号：</th>
                              	   <td>
                                    <html:orderattr disabled="disabled" style="width:400px;"  order_id="${order_id}" field_name="terminal_num"  field_desc ="串号" field_type="text"></html:orderattr>
                              	  </td>
                              	  <th>&nbsp;</th>
                              	  <td>&nbsp;</td>
                              	  <th>&nbsp;</th>
                              	  <td>&nbsp;</td>
                                </tr>      
                                <tr>
                                   <th>开户人姓名: </th>
                                   <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="phone_owner_name"  field_desc ="客户名称" field_type="input"></html:orderattr></td>
                                   <th>开户人证件类型:</th>
                                   <td><html:orderattr disabled="disabled" attr_code="DIC_CARD_TYPE"  order_id="${order_id}" field_name="certi_type"  field_desc ="证件类型" field_type="select"></html:orderattr></td>
                                    <th>开户人证件有效期:</th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="cert_failure_time"  field_desc ="证件有效期" field_type="date"></html:orderattr></td>
                                </tr>
                                <tr>
                                   <th>靓号金额：</th>
                                   <td><html:orderattr  disabled="disabled"   order_id="${order_id}" field_name="liang_price"  field_desc ="靓号金额" field_type="input"></html:orderattr></td>
                                   <th>开户人证件号码:  </th>
                                   <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="cert_card_num"  field_desc ="证件号码" field_type="input"></html:orderattr></td>
                                   <th>开户人证件地址:  </th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="cert_address"  field_desc ="证件地址" field_type="input"></html:orderattr></td>
                                </tr>    
                                <tr>
                                  <%-- <th>套餐名称:</th>
                                   <td><input type="text" disabled="disabled"  class="ipt_new" name="plan_title" readonly="readonly"  value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE) %>" /></td>
                              	    --%>
                              	   <th>商品价格:</th>
                                   <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="pro_origfee"  field_desc ="商品价格" field_type="input"></html:orderattr></td>
                                   <th>优惠金额: </th>
                                    <td>
                                    <html:orderattr  order_id="${order_id}" field_name="discountrange"  field_desc ="优惠价格" field_type="input"></html:orderattr>
                                    </td>
                                    <th>应付金额:</th>
                                    <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="order_origfee"  field_desc ="应付金额" field_type="input"></html:orderattr></td>
                                   
                                </tr>
                                 <tr>
                                   <!-- <th>调整金额111111:</th>
                                   <td><input type="text" value='' disabled="disabled" class="ipt_new"/></td> -->
                                   <th>银行账号：</th>
                                   <td><html:orderattr  disabled="disabled"   order_id="${order_id}" field_name="bank_user"  field_desc ="银行账户名" field_type="input"></html:orderattr></td>
                              	   
                                   <th>是否托收:</th>
                                   <td><html:orderattr disabled="disabled" attr_code="DIC_IS_COLLECTION"  order_id="${order_id}" field_name="collection"  field_desc ="是否托收" field_type="select"></html:orderattr></td>
                                   <th>托收银行:</th>
                                  <td><html:orderattr  disabled="disabled" attr_code="DIC_BANK_NAME"  order_id="${order_id}" field_name="bank_code"  field_desc ="托收银行" field_type="select"></html:orderattr></td>
                              	  
                                   <%--<th>活动类型:</th>
                                   <td><html:selectdict  disabled="disabled" name="ative_type" curr_val="<%=CommonDataFactory.getInstance().getGoodSpec(order_id,null, AttrConsts.ACTIVE_TYPE)%>"    attr_code="DC_PACKEGE_LEVEL" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                                   --%>
                                 </tr>      
                                 <tr>
                                   <th>首月资费:</th>
                                   <td>
                                	 <html:orderattr disabled="disabled" attr_code="DIC_OFFER_EFF_TYPE"  order_id="${order_id}" field_name="first_payment"  field_desc ="首月资费方式" field_type="select"></html:orderattr>
                                   </td>
                                   <%--
                                   <th>号码预存款:</th>
                                   <td><input disabled="disabled" name="deposit_fee" type="text" class="ipt_new" readonly="readonly" value="<%=CommonDataFactory.getInstance().getGoodSpec(order_id,null,SpecConsts.DEPOSIT_FEE) %>" style="width:200px;" /></td>
                                    --%>
                                   <th>生效方式:</th>
                                   <td><html:orderattr disabled="disabled" attr_code="active_sort"  order_id="${order_id}" field_name="active_sort"  field_desc ="生效方式" field_type="select"></html:orderattr></td>
                                   <th>优惠劵金额: </th>
                                   <td>${orderTree.orderBusiRequest.order_coupon }</td>
                                
                                </tr>      
                                
                                 <tr>
                                   <%--
                                   <th>合约期:</th>
                                   <td><input disabled="disabled" name="package_limit" type="text" readonly="readonly" class="ipt_new" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT) %>" id="textfield" style="width:200px;" /></td>
                                    --%>
                                   <th>社会代理商名称:</th>
                                   <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="society_name"  field_desc ="社会代理商名称" field_type="input"></html:orderattr></td>
                                   <th>代理商终端结算价格(元): </th>
                                   <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="society_price"  field_desc ="代理商终端结算价格(元)" field_type="input"></html:orderattr></td>
                                   <th>协议信息：</th>
                                   <td><img src="images/transparent.gif" id="agreement" order_id="${order_id}" name="agreement" class="add"></td>
                                </tr>
                                
                        </table>
                     </div>
                     <div class="grid_n_cont_sub"  id="certPhotoDiv">
						<h3>用户证件照片：</h3>
						<%
						List<String> img_list = new ArrayList<String>();
						List<OrderFileBusiRequest> orderFileBusiRequests = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderFileBusiRequests();
						if (orderFileBusiRequests != null && orderFileBusiRequests.size() > 0) {
							for (OrderFileBusiRequest obj : orderFileBusiRequests) {
								String status = obj.getStatus();
								String file_type = obj.getFile_type();
								if("1".equals(status)&&"jpg".equals(file_type)){
									String imgPath = request.getContextPath() + "/servlet/OrderShowPhotoServlet?file_path=" + obj.getFile_path();
									img_list.add(imgPath);
								}
							}
						}
						request.setAttribute("img_list", img_list);
						%>
						<div class="picListDiv" id="show_id_cart">
							<ul class="imgList">
								<c:forEach items="${img_list}" var="img">
									<td>
										<a class="artZoom" href="${img}" rel="${img}">
											<img src="${img}" style="width: 180px;height: 180px" attr="${img}"/>
										</a>
									</td>
								</c:forEach>
							</ul>
						</div>
					</div>
                </div>
             </div>             
            <!-- 客户信息结束 -->    
            <!-- 付款信息开始 -->    	
             <div class="grid_n_div">
            	 <h2><a href="#" class="openArrow"></a>支付信息</h2>
              	<div class="grid_n_cont">
              	     <div class="grid_n_cont_sub">
                        
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                                <tr>
                                   <th>支付交易号:</th>
                                   <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="payplatformorderid"  field_desc ="支付流水号" field_type="input"></html:orderattr></td>
                              	   <th> 支付类型:</th>
                                   <td>
                                    <html:orderattr disabled="disabled" attr_code="DIC_PAY_TYPE" order_id="${order_id}" field_name="paytype"  field_desc ="支付类型"  field_type="select"></html:orderattr>
                            	   </td>
                                   <th>支付方式:</th>
                                   <td>
                                   <html:orderattr disabled="disabled" attr_code="DIC_PAY_METHOD" order_id="${order_id}" field_name="pay_mothed"   field_desc ="支付方式" field_type="select"></html:orderattr>
                            	</td>
                                </tr>       
                                <tr>
                                   <th>支付状态:</th>
                                   <td>
                                     <html:orderattr disabled="disabled" attr_code="DIC_PAY_STATUS" order_id="${order_id}" field_name="pay_status"  field_desc ="支付状态" field_type="select"></html:orderattr>
                            	   </td>
                                   <th>支付银行:</th>
                                   <td><input type="text" value='' disabled="disabled" class="ipt_new"/></td>
                                   <th>支付账号:</th>
                                   <td><input type="text" value='' disabled="disabled" class="ipt_new"/></td>
                                </tr>        
                                <tr>
                                   <th>付款时间:</th>
                                   <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="pay_time"  field_desc ="支付时间" field_type="date"></html:orderattr>
                            	   </td>
                                   <th>优惠金额: </th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="order_disfee"  field_desc ="优惠金额（元）" field_type="input"></html:orderattr></td>
                                   <th>调整金额:</th>
                                   <td><input type="text" value='' disabled="disabled" class="ipt_new"/></td>
                                </tr>      
                                <tr>
                                   <th>订单金额: </th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="order_origfee"  field_desc ="订单总价（元）" field_type="input"></html:orderattr></td>
                                   <th>付款金额: </th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="order_realfee"  field_desc ="实收金额（元）" field_type="input"></html:orderattr></td>
                                </tr>
                                <!-- <tr>      
                                   <th>创建人111111:</th>
                                   <td></td>
                                   <th>创建时间111111:</th>
                                   <td></td>
                                   <th>修改时间111111: </th>
                                   <td></td>
                                </tr> -->
                                <tr>
                                   <!-- <th>修改人111111:</th>
                                   <td></td> -->
                                   <th>代金券编号:</th>
                                   <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="vouchers_code"  field_desc ="代金券编号" field_type="input"></html:orderattr></td>
                                  <th>代金券面值: </th>
                                  <td colspan="3"><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="vouchers_money" field_desc="代金券面值" field_type="input"></html:orderattr></td>
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
                                   <th>收货人姓名:</th>
                                   <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="ship_name"  field_desc ="收货人姓名" field_type="input"></html:orderattr></td>
                              	   <th> 收货邮编: </th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="ship_zip"  field_desc ="收货邮编" field_type="input"></html:orderattr></td>
                              	   <th>归属地市: </th>
                                   <td>
                                    <html:orderattr disabled="disabled" attr_code="DC_MODE_REGION" order_id="${order_id}" field_name="order_city_code"  field_desc ="归属地市" field_type="select" ></html:orderattr>
                            	   </td>
                                </tr>       
                                <tr>
                                   <th>收货人手机:</th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="receiver_mobile"  field_desc ="手机号码" field_type="input"></html:orderattr></td>
                              	   <th>收货人电话: </th>
                                   <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="reference_phone"  field_desc ="固定电话" field_type="input"></html:orderattr></td>
                                   <th>配送方式: </th>
                                   <td>
                                    <html:orderattr disabled="disabled"   attr_code="DC_MODE_SHIP_TYPE" order_id="${order_id}" field_name="sending_type"  field_desc ="配送方式" field_type="select" ></html:orderattr>
                            	   </td>
                                </tr>     
                                <tr>
                                   <th>配送时间:</th>
                                   <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="shipping_time"  field_desc ="配送时间" field_type="input"></html:orderattr></td>
                              	   <th>配送区域:</th>
                                   <td><input type="text" disabled="disabled" class="ipt_new"  value="${delivery.region }" /></td>
                                   <th>配送地址:</th>
                                   <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="ship_addr"  field_desc ="详细地址" field_type="input"></html:orderattr></td>
                              	</tr>       
                                <tr>
                                   <th>配送状态:</th>
                                   <td><input type="text" disabled="disabled"  class="ipt_new"  value="${delivery.ship_status=='1'?'已配送':'未配送' }" /></td>
                                   <th>创建人:</th>
                                   <td><input type="text" disabled="disabled" class="ipt_new"  value="${delivery.ship_name }" /></td>
                                   <th>修改时间:</th>
                                   <td><input type="text" disabled="disabled" class="ipt_new"  value="${orderTree.orderBusiRequest.ship_time }" /></td>
                                </tr>
                                <tr>
                                   <th>修改人：</th>
                                   <td><input type="text" disabled="disabled" class="ipt_new"  value="${delivery.op_name }" /></td>
                                   <th>创建时间：</th>
                                   <td><input type="text" disabled="disabled" class="ipt_new"  value="${delivery.create_time }" /></td>
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
               	  <c:if test="${giftInfoList!=null}">
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
	                                		<td><input name="gift.gift_id" disabled="disabled" type="text" class="ipt_new" value="${gift.gift_id}" style="width:200px;" /></td>
	                                		<th>赠品名称：</th>
	                                		<td><input name="gift.goods_name"  disabled="disabled" type="text" class="ipt_new" value="${gift.goods_name}" style="width:200px;" /></td>
	                              		</tr>
	                              		<tr>
	                                		<th>赠品面值：</th>
	                                		<td><input name="gift.gift_value"  disabled="disabled" type="text" class="ipt_new" value="${gift.gift_value}" style="width:200px;" /></td>
	                                		<th>赠品面值单位：</th>
	                                		<td>
	                                        	 <html:selectdict  name="gift.gift_unit" curr_val="${gift.gift_unit}" appen_options="<option value=''>---请选择---</option>"   disabled="disabled"   attr_code="DIC_GIFT_UNIT" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
	                                  		</td>
	                                  		
	                                		<th>赠品数量：</th>
	                                		<td><input name="gift.sku_num"  disabled="disabled" type="text" class="ipt_new"  value="${gift.sku_num}" style="width:200px;" /></td>
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
	                                		<td><input name="gift.gift_color" value="${gift.gift_color}" type="text" class="ipt_new"  disabled="disabled" id="textfield" style="width:200px;" /></td>
	                              		</tr>
	                              		<tr>
	                                		<th>赠品机型：</th>
	                                		<td>
	                                        	<input name="gift.gift_sku"  disabled="disabled" value="${gift.gift_sku }" class="ipt_new" style="width:200px;"/>
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
	                                		<td><input name="gift.process_desc"  disabled="disabled"type="text" class="ipt_new" value="${gift.process_desc}" style="width:200px;" /></td>
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
										<h3>&nbsp;&nbsp;宽带信息：</h3>
										<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
			                               <tr>
				                            <th>宽带账号：</th>
			                                 			<td>${orderTree.orderAdslBusiRequest.adsl_account }</td>
			                                <th>宽带号码：</th>
			                                 			<td>${orderTree.orderAdslBusiRequest.adsl_number }</td>
			                                <th>用户种类：</th>
			                                	<td><html:selectdict name="user_kind"  disabled="true"  curr_val="${orderTree.orderAdslBusiRequest.user_kind}"    attr_code="DIC_USER_KIND"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
			                               </tr>
			                               <tr>
				                            <th>网格：</th>
			                                 			<td>${orderTree.orderAdslBusiRequest.adsl_grid }</td>
			                                <th>局向编码：</th>
			                                 			<td>${orderTree.orderAdslBusiRequest.exch_code }</td>
			                                <th>预约装机时间：</th>
			                                	<td>${orderTree.orderAdslBusiRequest.appt_date }</td>
			                               </tr>
			                               <tr>
				                            <th>合约编码：</th>
			                                 			<td>${p_code }</td>
			                                <th>合约名称：</th>
			                                 			<td>${p_name }</td>
			                                <th>合约期：</th>
			                                	<td>
			                                	<html:selectdict disabled="true" name="packge_limit" curr_val="${packge_limit}" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_CONTRACT_MONTH" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
			                                	</td>
			                               </tr>
			                               
			                               <tr>
			                                <th>接入方式：</th>
			                                	<td>
			                                		<html:selectdict disabled="true" name="access_type" curr_val="${access_type}" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_ACCESS_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
			                                	</td>
			                                <th>地市：</th>
			                                	<td>
			                                		<html:selectdict disabled="true" name="city" curr_val="${city}" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_CITY_ZJ" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
			                                	</td>
			                                <th>终端类型：</th>
			                                	<td>
													<html:selectdict disabled="true" name="terminal_type" curr_val="${terminal_type}" appen_options="<option value=''>---请选择---</option>"   attr_code="DIC_TERMINAL_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
			                                	</td>			                               
			                                </tr>
			                               
			                               <tr>
				                            <th>套餐编码：</th>
			                                 			<td>${ess_code }</td>
			                                <th>套餐名称：</th>
			                                 			<td>${plan_title }</td>
			                                <th>速率：</th>
			                                 			<td>${net_speed }</td>
			                                
			                               </tr>
			                               
			                                <tr>
				                            <th>标准地址：</th>
			                                 			<td>${orderTree.orderAdslBusiRequest.adsl_addr }</td>
			                                <th>用户地址：</th>
			                                 			<td>${orderTree.orderAdslBusiRequest.user_address }</td>
			                                </tr>
			                               
										</table>
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
               	  	
            <jsp:include page="order_handler.jsp?order_id=${order_id }"/>
        </div>
    </div>
</div>
</form>

<div id="agreementDig"  ></div>

<!--  <div class="dialogIf jqmID1" style="display: none; width: 850px;" id="dlg_agreement_relation_dialog">
	             <div class="dialog_box">
		             <div class="head">
			             <div class="title">关联商品</div>
			             <span class="closeBtn"></span>
		             </div>
		             <div class="body dialogContent">
		             <div id="agreement_relation_dialog">
		             	<c:forEach var="fileName" items="${ord_agrt_img }" varStatus="i_fileName">
		             		<img src="${fileName}" />
		             	</c:forEach>
		             </div>
		             </div>
		             </div>
	             </div>
             </div> -->


</body>
</html>
<script type="text/javascript">
jQuery('a.artZoom').artZoom();
Eop.Dialog.init({id:"agreementDig",modal:true,title:"协议信息"});
$("#agreement").click(function(){
	var order_id = $(this).attr("order_id");
	var url = ctx+"/shop/admin/ordAuto!queryOrdAgrtImg.do?ajax=yes&order_id="+order_id;
	Eop.Dialog.open("agreementDig");
	$("#agreementDig").load(url,{},function(){});
	
});

if(${!(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')}){//非多商品
	  CommonLoad.loadJSP('goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_info"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('goods_open','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_open"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('goods_gift','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_gift"},false,function(){AutoFlow.checkMsg();},true);
}

function load_ord_his(){
	//$("#table_show").show();
	//$("#order_his").show();
	  $("#table_show").toggle();
   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};
function load_calllog(){
	//$("#table_show").show();
     // $("#table_show").toggle();
  	 $("#log_table_show").toggle();
	//$("#order_his").show();
};


/* $("#agreement").unbind("click").bind("click",function(){
	var community_code = $(this).attr("community_code");
	var url = ctx + "/shop/admin/communityAction!toGoodsCommunity.do?ajax=yes&activity.community_code="+community_code;
	Eop.Dialog.open("agreement_relation_dialog");
	$("#agreement_relation_dialog").load(url,{},function(){});
});
 */
$(function() {
	//先加载总数和按钮页
		  CommonLoad.loadJSP('calllog_info_before','<%=request.getContextPath() %>/shop/admin/ordCall!queryCalllog.do',{order_id:"${order_id }",first_load:"yes",call_order_type:"order",ajax:"yes",includePage:"calllog_info_before"},false,null,true);
	  CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);
	//CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
	
});
</script>
