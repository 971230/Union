<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderExtBusiRequest"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
.finish{display:inline-block; width:18px; height:21px; background:url(${context}/images/icon_03.png) no-repeat;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>写卡</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>


<%
  	String order_id = (String)request.getAttribute("order_id");
	OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
%>

</head>
<body>
<form action="javascript:void(0);" id="writeCardForm" method="post">
   <input type="hidden"  id="post_type" name="post_type" value="0"/>

<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
             <!-- 顶部公共 -->
             <input type="hidden" value='${order_id}' id="orderId"/>
     		<jsp:include page="auto_flows.jsp?order_id=${order_id }" />
        	<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>订单信息</h2>
                <div class="grid_n_cont">
                	<div class="grid_n_cont_sub">
                    	<h3>
                    	   	开户对象：套卡<span id="custNum"><html:orderattr order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="text"></html:orderattr></span>
                    	  	<a href="javascript:void(0);" id="refreshCardList" class="blue_b" style="margin-left:5px;">刷新读卡器列表</a>
                    	  	<!--  <a href="javascript:void(0);" id="readCardBtn" class="blue_b" style="margin-left:5px;">读卡</a> --> 
                    	  	<% if (!EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(orderExt.getAbnormal_type())) { //自动化异常订单不允许写卡 %>
                    	  	<a href="javascript:void(0);" id="writeCardBtn" class="blue_b" style="margin-left:5px;">写卡</a> 
                    	  	<% } %>
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
    
</div>
</form>
</body>
<OBJECT id="Ocxtest" classid=clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93><PARAM NAME="_Version" VALUE="65536"><PARAM NAME="_ExtentX" VALUE="2646"><PARAM NAME="_ExtentY" VALUE="1323"><PARAM NAME="_StockProps" VALUE="0"></OBJECT>
<script src="<%=request.getContextPath() %>/ecs_ord/js/RwCardExtract.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/writeCard.js"></script> 

<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<script type="text/javascript">

function load_ord_his(){
	//$("#table_show").show();
	$("#table_show").toggle();
	//$("#order_his").show();
   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};

$(function() {
	//先加载总数和按钮页
	  CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);
	//CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
	
});
</script>
