<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderExtBusiRequest"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.ztesoft.net.mall.core.utils.ICacheUtil"%>
<%@page import="com.ztesoft.net.framework.context.spring.SpringContextHolder"%>

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
	String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
	OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
    String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
    
	String order_model = orderExt.getOrder_model();
	Boolean isShowWriteCardContents = true;//控制是否展示写卡信息以及脚本加载
	Boolean isShowOpenAcct = true;//控制是否显示开户按钮
	Boolean isShowWriteCardBtn = true;//控制是否显示写卡按钮
	Boolean isShowRefreshBtn = true;//控制是否显示写卡按钮
	Boolean isShowGetICCID = false;//控制是否显示获取卡信息按钮
	Boolean isGetCardInfo = false;//控制是否获取卡信息
	Boolean isGroupBroad = false;
	if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(orderExt.getAbnormal_type())) { //自动化异常订单不允许写卡 
		isShowWriteCardBtn =  false;
	}
	if (!EcsOrderConsts.OPER_MODE_PCSG.equals(order_model) && !EcsOrderConsts.DIC_ORDER_NODE_X.equals(orderExt.getFlow_trace_id())) { //非写卡环节不允许写卡 
		isShowRefreshBtn =  false;
	}
	if (!EcsOrderConsts.DIC_ORDER_NODE_X.equals(orderExt.getFlow_trace_id())) { //非写卡环节不允许写卡 
		isShowWriteCardBtn =  false;
	}
	
	String order_from_source=cacheUtil.getConfigInfo("get_iccid_source_from");
	String[] order_from_sources=order_from_source.split(",");

 	for(int i=0;order_from_sources.length>i;i++){
		if(orderExt.getOrder_from().equals(order_from_sources[i])){
			isGetCardInfo = true;	
		}
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
	if(StringUtils.isNotBlank(goods_type) && EcsOrderConsts.GOODS_TYPE_DKD.equals(goods_type)){//如果是单宽带订单不显示写卡
		isShowWriteCardContents =  false;
	}
	if (EcsOrderConsts.OPER_MODE_XK.equals(order_model)) { //集中写卡模式不显示写卡信息
		isShowWriteCardContents =  false;
	}
	if(orderExt.getOrder_from().equals("10093")&&StringUtils.equals("221668199563784192",goods_cat)){
	    isShowWriteCardContents =  false;
	    isGetCardInfo = false;
	    isGroupBroad = true;
	}
	Boolean isTerminalCheck = false;
	if(StringUtils.equals("90000000000000901",goods_cat)){
	 	isShowWriteCardContents =  false;
	 	isGetCardInfo = false;
		isTerminalCheck = true;
	}
%>

</head>
<body>
<input type="hidden" id=isShowOpenAcct value="<%=isShowOpenAcct %>">
<form action="javascript:void(0);" id="writeCardFormMK" method="post">

	<div class="new_right">
        <div class="right_warp">
             <!-- 顶部公共 -->
            <input type="hidden" value='${order_id}' id="orderId"/>
            
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
     	   <% if (isGroupBroad) { %>
        	<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>卡信息录入</h2>
                <div class="grid_n_cont">
                	<div class="grid_n_cont_sub">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                          <tr>
                            <th><span>*</span>成卡卡号：</th>
                            <td>
                            	<input type="text" value="${ICCID_INFO}"  id = "ICCID_INFO" style="border:1px solid #a4a4a4;height:18px;line-height:18px">
                            	<input type="button" value="校验" id="iccidcheck" class="graybtn1"/>
                            	
                            </td>                          
                            <th><span>*</span>终端型号：</th>
                            <!-- select * from es_dc_sql c where c.dc_name='ORDER_FROM';  如 订单审核按钮  添加前置校验  从这里配置 -->
	                        <td>
	                        <select name="old_terminal_nums" id="old_terminal_nums" class="ipt_new" style="width:200px;">
								<option value="">--请选择--</option>
								<c:forEach var="of" items="${Termain_list}">
										<option value="${of.value}" ${old_terminal_num==of.value?'selected':''}>${of.value_desc}</option>
							   </c:forEach>
							</select> 
	                        </td>
                            <th><span>*</span>MAC_ID：</th>
                            <td>
                            	<input type="text" value="${terminal_num}"  id ="terminal_num" style="border:1px solid #a4a4a4;height:18px;line-height:18px">
                            	<input type="button" value="校验" id="terminalcheck" class="graybtn1"/>
                            </td>
                          </tr>
                        </table>
                    </div>
                </div>
            </div>
            <% } %>
     		<% if (isShowWriteCardContents) { //控制加载写卡信息 %>
        	<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>写卡信息</h2>
                <div class="grid_n_cont">
                	<div class="grid_n_cont_sub">
                    	<h3>
                    	   	开户对象：套卡<span id="custNum"><html:orderattr order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="text"></html:orderattr></span>
                    	  	<a href="javascript:void(0);" class="blue_b" 
	                    	  	<% if (isShowRefreshBtn) {%>
	                    	  		style="margin-left:5px;" id="refreshCardList"
	                    	  	<% }else{%>
	                    	  		style="margin-left:5px;border:1px solid #DDD;background-color:#F5F5F5;color:#ACA899;a:hover{};" id="refreshCardListNone"
	                    	  	<% }%>
                    	  	>刷新读卡器列表</a>
                    	  	<%if (!EcsOrderConsts.OPER_MODE_PCSG.equals(order_model)) { %>
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
	                    	  		show_type="ajaxSubmit" ,
	                    	  		check_fn='dealOpenCheck',
	                    	  		hide_page=",list,unvisabled," 
	                    	  		order_id="${order_id }" 
	                    	  		form_id="openAccountForm" 	                    	  		
	                    	  		callcack_fn="dealCallbackRefreshNewAcc" 
	                    	  		style="margin-left:5px;"
	                    	  	<% }else{%>
	                    	  		style="margin-left:5px;border:1px solid #DDD;background-color:#F5F5F5;color:#ACA899;a:hover{};"
	                    	  	<% }%>
	                    	>开户</a>
	                    	<% }else{%>
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
	                    	  		ac_url="shop/admin/orderCrawlerAction!flowDealOpenAcct.do" 
	                    	  		show_type="ajaxSubmit" 
	                    	  		hide_page=",list,unvisabled," 
	                    	  		order_id="${order_id }" 
	                    	  		form_id="openAccountForm" 	                    	  		
	                    	  		check_fn="dealOpenCheckCrawler",
	                    	  		callcack_fn="dealCallbackCrawler" 
	                    	  		style="margin-left:5px;"
	                    	  	<% }else{%>
	                    	  		disabled="disabled" style="margin-left:5px;border:1px solid #DDD;background-color:#F5F5F5;color:#ACA899;a:hover{};"
	                    	  	<% }%>
	                    	>开户</a>
	                    	<% }%>
	                    	
	                    	<!-- ZX add 2017年1月17日 15:34:24 start -->
	                    	<%-- <a href="javascript:void(0);" id="openAcctBtn_" class="blue_b"  
		                   		orderbtns="btn" name="o_open_acct_pc"
	                    	  	ac_url="#" 
	                    	  	show_type="ajaxSubmit" 
	                    	  	hide_page=",list,unvisabled," 
	                    	  	order_id="${order_id }" 
	                    	  	form_id="openAccountForm" 	                    	  		
	                    	  	callcack_fn="dealCallbackRefreshNewAcc" 
	                    	  	style="margin-left:5px;"
	                    	>开户</a> --%>
	                    	<!-- ZX add 2017年1月17日 15:34:24 finish -->
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
              <% if (isTerminalCheck) { %>
        	<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>泛智能终端元素信息</h2>
                <div class="grid_n_cont">
                	<div class="grid_n_cont_sub">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                          <tr>
                            <th><span></span>终端类型：</th>
                            <td>
                            	<input type="text" value="${mobile_type}" readonly  id = "mobile_type" style="border:1px solid #a4a4a4;height:18px;line-height:18px">
                            	
                            </td>                          
                            <th><span></span>活动编码：</th>
	                        <td>
	                           <input type="text" value="${scheme_id}" readonly  id = "scheme_id" style="border:1px solid #a4a4a4;height:18px;line-height:18px">
	                        
	                        </td>
                            <th><span>*</span>元素编码：</th>
                            <td>
                            	<input type="text" value="${element_id}"  id ="element_id" readonly style="border:1px solid #a4a4a4;height:18px;line-height:18px">
                            </td>
                          </tr>
                          <tr>
                          	<th><span></span>终端串号：</th>
                            <td>
                            	<input type="text" value=""  id = "mobile_imei" style="border:1px solid #a4a4a4;height:18px;line-height:18px">
                            	<input type="button" value="校验" id="orderTerminalCheck" name="orderTerminalCheck" class="graybtn1"  />
                            	
                            </td> 
                            <th><span></span>终端名称：</th>
                            <td>
                            	<input type="text" value="${terminal_name }"  id = "terminal_name" readonly style="border:1px solid #a4a4a4;height:18px;line-height:18px">
                            </td>
                             <th><span></span>固话号码：</th>
                            <td>
                            	<input type="text" value="${guhua_number }"  id = "guhua_number" name = "guhua_number" style="border:1px solid #a4a4a4;height:18px;line-height:18px" />
                            <!-- <input type="button" value="保存" id="orderGuhHuaCheck" name="orderGuhHuaCheck" class="graybtn1"  /> -->	
                            </td>
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
                        <h3>基本信息：</h3>
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
	                       <%--  <td><html:orderattr order_id="${order_id}" field_name="development_code"  field_desc ="发展人编码" field_type="input"></html:orderattr></td> --%>
	                       	<td>${developmentCode }</td>
	                        <th>发展人名称：</th>
	                        <%-- <td><html:orderattr order_id="${order_id}" field_name="development_name"  field_desc ="发展人名称" field_type="input"></html:orderattr></td> --%>
	                    	<td> ${developmentName }</td>
	                    </tr>
	                     <tr>
                        	<th>发展点编码：</th>
	                        <td>${development_point_code }</td>
	                        <th>发展点名称：</th>
	                        <td>${development_point_name }</td>
	                    </tr>
	                     <tr>
                        	<th>cbss发展点编码：</th>
	                        <td>${cbss_development_point_code }</td>
	                        <th>cbss发展人编码：</th>
	                        <td>${cbss_develop_code }</td>
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
                        <!--  
                            <th>证件审核状态：</th>
                            <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="account_vali"  field_desc ="开户人身份验证" field_type="select"></html:orderattr></td>
                             -->
                            <th>稽核信息:</th>
                            <td>
                             <c:if  test="${orderTree.orderExtBusiRequest.audit_type==0}">稽核退单</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==1}">稽核退款</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==2}">稽核补单</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==3}">稽核补款</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==4}">稽核退现金</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==null || orderTree.orderExtBusiRequest.audit_type==''}">暂无稽核信息</c:if>
                            </td>
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
                            <%--   <th><span>*</span>BSS/ESS缴费款：</th>
	                             <td><html:orderattr  order_id="${order_id}" field_name="busi_money"  field_desc ="BSS缴费款" field_type="input"></html:orderattr></td> --%>
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
        	        <!-- 基本信息结束 -->
        	        <!-- 物流信息开始 -->
                    <div class="grid_n_cont_sub">
                      	<h3>物流信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                            	<th><span>*</span>物流单号：</th>
                            	<td><html:orderattr  order_id="${order_id}" field_name="logi_no"  field_desc ="物流单号" field_type="input"></html:orderattr></td>
                              	<th><span>*</span>应收运费（元）：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="post_fee"  field_desc ="应收运费（元）" field_type="input"></html:orderattr></td>
                              	<th><span>*</span>实收运费（元）：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="n_shipping_amount"  field_desc ="实收运费（元）" field_type="input"></html:orderattr></td>
                              <!-- <th>物流公司编码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="shipping_company"  field_desc ="物流公司编码" field_type="input"></html:orderattr></td>
                          	    -->	
                          	</tr>
                            <tr>
                              	<th>物流公司名称：</th>
                              	<td>
                                <select name="shipping_company" field_desc="物流公司" 
											id="logi_company" class="ipt_new" style="width:200px;">
												<option value="">---请选择---</option>
												<c:forEach var="logicompany" items="${logiCompanyList }">
													<option key_str="${logicompany.key_str }"
														<c:if test="${order_model=='01' && logicompany.id=='SF0001'}">selected="selected"</c:if>
														value="${logicompany.id }">${logicompany.name }</option>
												</c:forEach>
										</select> 
										<script type="text/javascript">
										var sc = '${shipping_company}';
										if(sc){
											$("[name='shipping_company'] option[value='${shipping_company}']").attr("selected","selected");
										}
		                            </script>
							    </td>
                               <th><span>*</span>是否闪电送：</th>
                              	<td>
                                    <html:orderattr  attr_code="DIC_SHIPPING_QUICK" order_id="${order_id}" field_name="shipping_quick"  field_desc ="是否闪电送" field_type="select"  ></html:orderattr>
                            	</td>
                              	<th><span>*</span>配送方式：</th>
                              	<td>
                                    <html:orderattr  attr_code="DC_MODE_SHIP_TYPE" order_id="${order_id}" field_name="sending_type"  field_desc ="配送方式" field_type="select" ></html:orderattr>
                            	</td>
                            </tr>
                            <tr>
                              	<th>配送时间：</th>
                              	<td><html:orderattr attr_code ='DIC_SHIPPING_TIME'   order_id="${order_id}" field_name="shipping_time"  field_desc ="配送时间" field_type="select"></html:orderattr></td>
                              	<th><span>*</span>收货人姓名：</th>
                              	<td><html:orderattr   order_id="${order_id}" field_name="ship_name"  field_desc ="收货人姓名" field_type="input"></html:orderattr></td>
                              	<th><span>*</span>收货省份：</th>
                              	<td>
                              	   <select name="provinc_code" field_desc="收货省份" id="provinc_code" class="ipt_new" style="width:200px;" >
                              	     <option value="">--请选择--</option>
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
                            </tr>
                            <tr>
                              	<th>收货地市：</th>
                              	<td>
                                     <select name="city_code" field_desc="收货地市" id="city_code" class="ipt_new" style="width:200px;" >
                              	     <option value="">--请选择--</option>
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
                              	<th><span>*</span>收货区县：</th>
                              	<td>
                                    <select name="district_id" field_desc="收货区县" id="district_id" class="ipt_new" style="width:200px;" >
                              	     <option value="">--请选择--</option>
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
                              	<th>收货商圈：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="ship_area"  field_desc ="收货商圈" field_type="input"></html:orderattr></td>
                          	</tr>
                            <tr>
                              	<th><span>*</span>详细地址：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="ship_addr"  field_desc ="详细地址" field_type="input"></html:orderattr></td>
                              	<th>邮政编码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="ship_zip"  field_desc ="邮政编码" field_type="input"></html:orderattr></td>
                              	<th>固定电话：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="reference_phone"  field_desc ="固定电话" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th><span>*</span>手机号码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="receiver_mobile"  field_desc ="手机号码" field_type="input"></html:orderattr></td>
                              	<th>电子邮件：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="ship_email"  field_desc ="电子邮件" field_type="input"></html:orderattr></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                      	</table>
                  	</div>
                    <!-- 物流信息结束 -->
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
										<c:forEach var="adsl" items="${orderTree.orderAdslBusiRequest}" >
										<tr>
				                            <th>宽带账号：</th>
			                                 			<td>${adsl.adsl_account }</td>
			                                <th>宽带号码：</th>
			                                 			<td>${adsl.adsl_number }</td>
			                                <th>用户种类：</th>
			                                	<td><html:selectdict name="user_kind"  disabled="true"  curr_val="${adsl.user_kind}"    attr_code="DIC_USER_KIND"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
			                               </tr>
			                               <tr>
				                            <th>网格：</th>
			                                 			<td>${adsl.adsl_grid }</td>
			                                <th>局向编码：</th>
			                                 			<td>${adsl.exch_code }</td>
			                                <th>预约装机时间：</th>
			                                	<td>${adsl.appt_date }</td>
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
			                                 			<td>${adsl.adsl_addr }</td>
			                                <th>用户地址：</th>
			                                 			<td>${adsl.user_address }</td>
			                                </tr>
										</c:forEach>
			                               
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
						  //String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
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
                 <!-- 外呼记录 -->
        	        <c:if test="${outcall_type_c!=null}">
        	        <div class="grid_n_div">
        	        <h2><a href="#" class="openArrow"></a>外呼记录：</h2>
        	        <div class="grid_n_cont">
  		   				<div id="context_outcall">
        	        	
  		   				</div>
  		   				</div>
  		   				</div>
        	        </c:if>
                 <%} %>
             </div>
        	<!-- 处理意见 -->
        	<jsp:include page="order_handler.jsp?order_id=${order_id }"/>
         </div>
    </div>

</form>
<div id="open_acct_fee_list"></div>
</body>
<% if (isShowWriteCardContents) { //控制加载写卡模块 %>
	<OBJECT id="Ocxtest" classid=clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93><PARAM NAME="_Version" VALUE="65536"><PARAM NAME="_ExtentX" VALUE="2646"><PARAM NAME="_ExtentY" VALUE="1323"><PARAM NAME="_StockProps" VALUE="0"></OBJECT>
	<script src="<%=request.getContextPath() %>/ecs_ord/js/RwCardExtract.js"></script>
	<script src="<%=request.getContextPath() %>/ecs_ord/js/writeCard.js"></script> 
<%} %>


<script type="text/javascript">
$("#orderTerminalCheck").click(function(){
   var mobile_imei = $("#mobile_imei").val().trim();
	var order_id = $("#orderId").val();
		if(mobile_imei==null || mobile_imei=='' ){
			alert("请输入终端串号");
			return;
		}

		$.ajax({
         	url:ctx + "/shop/admin/orderFlowAction!orderTerminalCheck.do?ajax=yes&mobile_imei=" +mobile_imei+"&order_id="+order_id,
         	dataType:"json",
         	async:false,
         	data:{},
         	success:function(reply){
         		if(reply.result != 0){
         			alert(reply.message);
         		}else{
         			alert(reply.message);
         		}
         	}
		});
});
/*
$("#orderGuhHuaCheck").click(function(){
	var guhua = $("#guhua_number").val().trim();
	var order_id = $("#orderId").val();
	if(guhua==null || guhua=='' ){
		alert("请输入固话号码");
		return;
	}
	$.ajax({
     	url:ctx + "/shop/admin/orderFlowAction!orderGuhHuaCheck.do?ajax=yes&guhua_number=" +guhua+"&order_id="+order_id,
     	dataType:"json",
     	async:false,
     	data:{},
     	success:function(reply){
     		if(reply.result != 0){
     			alert(reply.message);
     		}else{
     			alert(reply.message);
     		}
     	}
	});
});

*/

/* $("#orderTerminalCheck").bind("click",function(){
		var mobile_imei = $("#mobile_imei").val().trim();
		var order_id = $("#orderId").val();
		if(terminal_num==null || terminal_num=='' ){
			alert("请输入终端串号");
			return;
		}
		alert()
		

		$.ajax({
         	url:ctx + "/shop/admin/orderFlowAction!orderTerminalCheck.do?ajax=yes&mobile_imei=" +mobile_imei+"&order_id="+order_id,
         	dataType:"json",
         	async:false,
         	data:{},
         	success:function(reply){
         		if(reply.result != 0){
         			alert(reply.message);
         		}else{
         			alert(reply.message);
         		}
         	}
		});
	}); 
}); */
<%-- function dealOpenCheck(){
	var busi_money = $("input[field_name='busi_money']").val();
	if(''==busi_money){
		alert("请先输入BSS/ESS缴费款并保存");
		return false;
	}else{
		if(!isNaN(busi_money)){
			if(<%=isGetCardInfo%>){
				return getICCID();
			}else{
				return true;
			}
		}else{
			alert("BSS/ESS缴费款不能为空");
			return false;
		}
	}
	
	return false;
} --%>

function getICCID(){
	var result = false;
	
	RwCard.getReaderName();
	var result = RwCard.ConnetReader(); // 连接写卡器
   	if ("0" != result) {
   		alert("连接写卡器失败，请尝试重新插入白卡！");
   		return false;
     	RwCard.DisConnetReader();//读取失败断开写卡链接
   	}
	var cardNum = RwCard.queryUsimNo();
   	if(cardNum!=null&&cardNum.length>0&&cardNum!=''){
   		var order_id = $("#orderId").val();
   		$.ajax({
         	url:app_path+"/shop/admin/orderCrawlerAction!saveICCID.do?ajax=yes&order_id="+order_id+"&ICCID="+cardNum,
         	dataType:"json",
         	async:false,
         	data:{},
         	success:function(reply){
         		if(reply.result != 0){
         			alert(reply.message);
         			throw new error(reply.message);
         		}else{
         			result = true;
         		}
         		//getCardInfo(cardNum);
         	}
		});
   		
   	}else{
   		alert("获取ICCID卡号失败");
   		RwCard.DisConnetReader();//读取失败断开写卡链接
   		return false;
   	}
   	
   	RwCard.DisConnetReader();
   	$("#ICCID").empty().html(cardNum);
   	
   	return result;
}

function getCardInfo(iccid){
	var order_id = $("#orderId").val();
	alert(iccid);
	if(<%=isGetCardInfo%>){
		$.ajax({
         	url:ctx+"shop/admin/orderFlowAction!cardInfoGet.do?order_id="+order_id,
         	contentType: "text/html",
         	type: "POST",
         	dataType:"json",
         	success:function(reply){
         		alert(reply);
         	}
		});
	}	
}

function dealOpenCheckCrawler(){
	var read_name = $("#cardList").val(); //获取读卡器列表值
    if(read_name==null||read_name.length==0||read_name=='undefined'){
 	   alert("此订单为爬虫生产模式订单，请插入并选择写卡器");
 	   return ;
    }else{
 	   readerName = read_name ;//把获取的值存到全局变量中
    }
   	var result = RwCard.ConnetReader(); // 连接写卡器
   	if ("0" != result) {
   		alert("连接写卡器失败，请尝试重新插入白卡！");
     	RwCard.DisConnetReader();//读取失败断开写卡链接
     	return false;
   	}
   	var cardImsi = "";
  	cardImsi = RwCard.queryCardImsi();
   	if ("-1" == cardImsi) {
     	// 解绑读卡
     	alert("此卡已写，请更换白卡！");
     	RwCard.DisConnetReader();
     	return false;
   	}
   	var cardNum = RwCard.queryUsimNo();
   	if(cardNum!=null&&cardNum.length>0&&cardNum!=''){
   		var order_id = $("#orderId").val();
   		$.ajax({
         	url:app_path+"/shop/admin/orderCrawlerAction!saveICCID.do?ajax=yes&order_id="+order_id+"&ICCID="+cardNum,
         	dataType:"json",
         	data:{},
         	success:function(reply){
         		
         	}
		});
   	}else{
   		alert("获取ICCID卡号失败");
 	  	RwCard.DisConnetReader();//读取失败断开写卡链接
 	  	return false;
   	}
   	return true;
}
function dealCallbackCrawler(data){
	if(data.result=='0'){
		var url = ctx+"/shop/admin/orderFlowAction!toAutoFlow.do?ajax=yes&order_id="+data.order_id;
		//刷新顶部流程图
		$.ajax({			
	    	url: url,
	        dataType: "html",
	        success: function(data){
	            $('#auto_flows_div').html(data);
	        }
	    });
		//刷新开户写卡按钮
		//火狐对disable支持不好，暂时先这么坑爹的实现，以后优化
		$("#refreshCardListNone").attr("id","refreshCardList");
		$("#refreshCardList").attr("style","margin-left:5px;");

		$("#writeCardBtnNone").attr("id","writeCardBtn");
		$("#writeCardBtn").attr("style","margin-left:5px;");
		//alert("dd");
		$("#openAcctBtn").attr("style","margin-left:5px;border:1px solid #DDD;background-color:#F5F5F5;color:#ACA899;a:hover{};");
		$("#openAcctBtn").removeAttr("orderbtns");//上面只是样式伪装disable效果。实际上还是可以点击的，需要把控制点击的元素remove掉
		//刷新底部按钮
		OrdBtns.showBtns(data.order_id,",detail,exception,");
		var order_id = $("#orderId").val();
		$.ajax({
	     	url:app_path+"/shop/admin/orderCrawlerAction!hasFeeInfoCheck.do?ajax=yes&order_id="+order_id,
	     	dataType:"json",
	     	data:{},
	     	success:function(reply){
	     		if(reply.result=='0'){
	     			var order_id = $("#orderId").val();
	         		var url = ctx + "/shop/admin/orderCrawlerAction!flowDealOpenAcct_pc.do?ajax=yes&order_id="+order_id;
	         		Eop.Dialog.open("open_acct_fee_list");
	         		$("#open_acct_fee_list").load(url, {}, function() {});
	     		}
	     	},
	     	error:function(a,b){
	     		alert("操作失败，请重试");
	     	}
		});
	}
	else{
		if(data.validateMsgList){
			var list = data.validateMsgList;
			if(list.length){
			  	for(var i=0;i<list.length;i++){
				  	var field_name = list[i].field_name;
				  	var check_msg = list[i].check_msg;
				  	$("[field_name='"+field_name+"']").attr("check_message",check_msg);
				  	$("[field_name='"+field_name+"']").addClass("errorBackColor");
			  	}
			}
		}
	}
}
function load_ord_his(){
	//$("#table_show").show();
	//$("#order_his").show();
	  $("#table_show").toggle();
   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};
$(function(){
 AutoFlow.checkMsg();
 openAccount_online_pc.init();
/*  $("a[name='getICCIDBtn']").unbind().bind("click", function() {
	 getICCID();
	}); */
});
//
$(function(){
	$("#ICCID_INFO").focus(function(){
		var ICCID_INFO = $("#ICCID_INFO").val().trim();
		var order_id = $("#orderId").val();
		if(ICCID_INFO == null || ICCID_INFO == ''){
			return;
		}else{
			 $.ajax({  
	                type: "POST",  
	                data:"ajax=yes&order_id="+order_id+"&ICCID_INFO="+ICCID_INFO,  
	                url: ctx + "/shop/admin/orderFlowAction!CheckChange.do",  
	                dataType: "json",  
	                cache: false,  
	                success: function(responseText){   
	                	if (responseText.result == 0) {
	                		return;
						} else {
							if (window.confirm("成卡卡号已校验通过,你确定要修改成卡卡号？")) {
								 $("#ICCID_INFO").val("");
								 $.ajax({  
						                type: "POST",  
						                data:"ajax=yes&order_id="+order_id+"&ICCID_INFO="+ICCID_INFO,  
						                url: ctx + "/shop/admin/orderFlowAction!cleanCheckInfo.do",  
						                dataType: "json",  
						                cache: false,  
						                success: function(responseText){   
						                	if (responseText.result == 0) {
						                		return;
						                 }  
						              }
						        }); 
						    }else{
						    	$('#ICCID_INFO').unbind();
						    }
						}  
	                 }  
	        	}); 
				
		}
	});
 	$("#iccidcheck").bind("click",function(){
		var ICCID_INFO = $("#ICCID_INFO").val().trim();
		if(ICCID_INFO==null || ICCID_INFO=='' ){
			alert("请输入成卡卡号");
			return;
		}
		var order_id = $("#orderId").val();
		$.ajax({
         	url:ctx + "/shop/admin/orderFlowAction!iccidcheck.do?ajax=yes&order_id="+order_id+"&ICCID_INFO="+ICCID_INFO,
         	dataType:"json",
         	async:false,
         	data:{},
         	success:function(reply){
         		debugger;
         		if(reply.result != 0){
         			alert(reply.message);
         		}else{
         			alert(reply.message);
         		}
         	}
		});
	}); 
 	
 	$("#terminal_num").focus(function(){
		var terminal_num = $("#terminal_num").val().trim();
		var order_id = $("#orderId").val();
		if(terminal_num == null || terminal_num == ''){
			return;
		}else{
		/* 	if (window.confirm("你确定要修改终端串号？")) {
	       		$("#terminal_num").val("");
				$.ajax({  
	                type: "POST",  
	                data:"ajax=yes&order_id="+order_id+"&terminal_num="+terminal_num,  
	                url: ctx + "/shop/admin/orderFlowAction!CheckChange.do",  
	                dataType: "json",  
	                cache: false,  
	                success: function(responseText){   
	                	if (responseText.result == 0) {
	                		 $("#terminal_num").val("");
	                		 return;
						} else {
							$("#terminal_num").val("");
							return;
						}  
	            }  
	        	});
			} */
			 $.ajax({  
	                type: "POST",  
	                data:"ajax=yes&order_id="+order_id+"&terminal_num="+terminal_num,  
	                url: ctx + "/shop/admin/orderFlowAction!CheckChange.do",  
	                dataType: "json",  
	                cache: false,  
	                success: function(responseText){   
	                	if (responseText.result == 0) {
	                		return;
						} else {
							if (window.confirm("终端串号已校验通过,你确定要修改终端串号？")) {
								 $("#terminal_num").val("");
								 $.ajax({  
						                type: "POST",  
						                data:"ajax=yes&order_id="+order_id+"&terminal_num="+terminal_num,  
						                url: ctx + "/shop/admin/orderFlowAction!cleanCheckInfo.do",  
						                dataType: "json",  
						                cache: false,  
						                success: function(responseText){   
						                	if (responseText.result == 0) {
						                		return;
						                 } 
						             }
						        }); 
						    }else{
						    	$('#terminal_num').unbind();
						    }
						}  
	                 }  
	        	}); 
		}
	});
 	$("#terminalcheck").bind("click",function(){
		var terminal_num = $("#terminal_num").val().trim();
		var old_terminal_nums = $("#old_terminal_nums").val();
		var order_id = $("#orderId").val();
		if(terminal_num==null || terminal_num=='' ){
			alert("请输入终端串号");
			return;
		}
		if (old_terminal_nums == null || old_terminal_nums == '') {
			alert("请选择终端型号");
			return;
		}
		var options=$("#old_terminal_nums option:selected");//获取当前选择项.
		var object_name = options.text();//获取当前选择项的文本.
		object_name = encodeURI(encodeURI(object_name));
		$.ajax({
         	url:ctx + "/shop/admin/orderFlowAction!terminalcheck.do?ajax=yes&terminal_num=" + terminal_num+"&old_terminal_num="+old_terminal_nums+"&order_id="+order_id+"&object_name="+object_name,
         	dataType:"json",
         	async:false,
         	data:{},
         	success:function(reply){
         		if(reply.result != 0){
         			alert(reply.message);
         		}else{
         			alert(reply.message);
         		}
         	}
		});
	}); 
});


$(function(){
	//CommonLoad.loadJSP('relations_info','< %=request.getContextPath() %>/shop/admin/orderFlowAction!getRealtionOrders.do',{order_id:"${order_id }",ajax:"yes",includePage:"relations_info"},false,null,true);
	CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
	CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);
	//CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
 });

if(${!(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')}){//非多商品
	  CommonLoad.loadJSP('goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_info"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('goods_open','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_open"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('goods_gift','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_gift"},false,function(){AutoFlow.checkMsg();},true);
}

var openAccount_online_pc = {
	init : function() {
		Eop.Dialog.init({
			id : "open_acct_fee_list",
			modal : false,
			title : "费用明细",
			height : "300px",
			width : "900px"
		});
		openAccount_online_pc.open_acct_fee();
	},
	open_acct_fee : function() {
		$("a[name='o_open_acct_pc']").unbind().bind("click", function() {
			var order_id = $("#orderId").val();
			var url = ctx + "/shop/admin/orderCrawlerAction!flowDealOpenAcct_pc.do?ajax=yes&order_id="+order_id;
			Eop.Dialog.open("open_acct_fee_list");
			$("#open_acct_fee_list").load(url, {}, function() {});
		});
	}
}
//加载处理日志
$.ajax({
	url:ctx+"/shop/admin/ordAuto!queryOutcallLogs.do?ajax=yes&order_id=<%=order_id%>",
	async : false,
	dataType:"html",
	type:"post",
	success:function(data){
		$("#context_outcall").empty().append(data);
	}
});

</script>

