<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单预处理</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

<%
  String order_id = (String)request.getAttribute("order_id");
  String order_from  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
  String customerType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
  boolean custFlag = EcsOrderConsts.CUSTOMER_CUST_TYPE_JTKH.equals(customerType);
  String d_type = (String)request.getAttribute("d_type");
  String yclbtn = "";
  if("ycl".equals(d_type)){
	  yclbtn = "ycl";
	  request.setAttribute("yclbtn", yclbtn);
  }
%>

</head>

<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<body>
<form action="javascript:void(0);" id="preDealOrderForm" method="post">

<input type="hidden"  value="${query_type}" name="query_type"/>
<input type="hidden"  value="${d_type}" name="d_type"/>
<input type="hidden" id="custFlag" value="<%=custFlag%>"/>
<input type="hidden" name="oldCertNum" value="<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM)%>" />
<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
            <!-- 顶部公共 -->
            
        	<jsp:include page="auto_flows.jsp?order_id=${order_id }&btn=${yclbtn }"/>
        	
        	<!-- 关联订单显示开始-->
            <!-- <div class="grid_n_div">
	            <h2><a href="javascript:void(0);" class="openArrow"></a>关联订单信息</h2> 
	           	<div class="grid_n_cont">
	    		  	<div id="relations_info" style="height: 40px"></div>
	            </div>
            </div> -->
            <!-- 关联订单显示结束 -->
			
          	<!-- 订单全部信息开始 -->  
        	<div class="grid_n_div">
            	 <h2><a href="javascript:void(0);" class="closeArrow"></a>订单基本信息<!--<a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
            		<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
					<div id="order_base">
						<jsp:include page="include/order_base.jsp?order_id=${order_id }&order_amount=${order_amount }"/>
					</div>
					<div id="order_ext" style="height: 80px"></div>
        	    </div>
        	</div>
        	<!-- 订单全部信息结束 -->
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
        	<!-- 商品信息开始 -->
        	<div class="grid_n_div">
             <h2><a href="javascript:void(0);" class="openArrow"></a>商品信息<!-- <a href="#" class="editBtn">编辑</a> --></h2> 
              	<div class="grid_n_cont">
       		  	  	<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span id="specValidateMsg_Span"></span></div>
					<div id="prod_info" style="height: 80px"></div>
                </div>
            </div>
              <!-- 商品信息结束 -->
              
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
              
              <!-- 总部反馈费用项减免开始 <div id="fee_info" style="height: 80px"></div>-->
              <div class="grid_n_div">
              <h2><a href="#" class="closeArrow"></a>费用项减免</h2> 
              <div class="grid_n_cont">
              	<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16" /><span></span></div>
				<div id="fee_info" style="height: 80px"></div>
              </div>
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
              <!-- 总部反馈费用项减免结束 -->
              <jsp:include page="order_handler.jsp?order_id=${order_id }"/>
        </div>
        
    </div>
</div>

</form>
<br />
<br /><br /><br />
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
	  //CommonLoad.loadJSP('order_base','< %=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"order_base"},false,null,true);
	  //CommonLoad.loadJSP('relations_info','< %=request.getContextPath() %>/shop/admin/orderFlowAction!getRealtionOrders.do',{order_id:"${order_id }",ajax:"yes",includePage:"relations_info"},false,function(){AutoFlow.checkMsg();},true);
  	  CommonLoad.loadJSP('order_ext','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"order_ext"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('prod_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"prod_info"},false,function(){AutoFlow.checkMsg();},true);
	  //CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
	  if(${!(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')}){//非多商品
		  CommonLoad.loadJSP('goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_info"},false,function(){AutoFlow.checkMsg();},true);
		  CommonLoad.loadJSP('goods_open','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_open"},false,function(){AutoFlow.checkMsg();},true);
		  CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
		  CommonLoad.loadJSP('goods_gift','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_gift"},false,function(){AutoFlow.checkMsg();},true);
	  }
	  if(${orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053'}){//多商品
		  CommonLoad.loadJSP('goods_info_more','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"view_goods_info_more"},false,function(){AutoFlow.checkMsg();},true);
	  }
	  CommonLoad.loadJSP('fee_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"fee_info"},false,function(){AutoFlow.checkMsg();},true);
	   //校验信息
	  AutoFlow.checkMsg();
	   
  });
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

