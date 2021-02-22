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
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

<%
  String order_id = (String)request.getAttribute("order_id");
%>

</head>
<body>
<form action="javascript:void(0);" id="prePickForm" method="post">
   <input type="hidden"  id="post_type" name="post_type" value="0"/>

<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
             
             <!-- 顶部公共 -->
        	<jsp:include page="auto_flows.jsp?order_id=${order_id}"/>
        	<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>配货信息(${order_id})<!-- <a href="#" class="editBtn">编辑</a> --></h2>
                <div class="grid_n_cont">
            		<div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
                  <div class="grid_n_cont_sub">
                    <h3>商品包1：</h3>
                      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                          <tr style="width:40px;">
                            <th><span>*</span>商品名称：</th>
                            <td><html:orderattr order_id="${order_id}" field_name="GoodsName"  field_desc ="商品名称" field_type="text" style="width:400px;"></html:orderattr></td>
                           <c:if test="${isTerm==1}">
                             <input type="hidden" value="${isTerm}"  name="isTerm"/>
                            <th><span>*</span>串号：</th>
                            <td style="width:500px;"><html:orderattr  order_id="${order_id}" field_name="terminal_num"  field_desc ="串号" field_type="input"  style="width:200px;"></html:orderattr></td>
                           </c:if>
                           <c:if test="${isTerm!=1}">
                              <th>&nbsp;</th>
                              <td>&nbsp;</td>
                            </c:if>
                           <th>&nbsp;</th>
                           <td>&nbsp;</td>
                        </tr>
                          <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10002))){%>
                        <tr>
                          <th>卡类型：</th>
                          <td><html:orderattr disabled="disabled"  attr_code="DC_PRODUCT_CARD_TYPE"  order_id="${order_id}" field_name="white_cart_type"  field_desc ="卡类型" field_type="select"></html:orderattr></td>
                        
                          <th><span>*</span>首月资费方式：</th>
                          <td>
                             <html:orderattr attr_code="DIC_OFFER_EFF_TYPE"  disabled="disabled" order_id="${order_id}" field_name="first_payment"  field_desc ="首月资费方式" field_type="select"></html:orderattr>
                          </td>
                           <th>生效方式:</th>
                           <td><html:orderattr disabled="disabled" attr_code="active_sort"  order_id="${order_id}" field_name="active_sort"  field_desc ="生效方式" field_type="select"></html:orderattr></td>
                               
                        </tr>
                        <%}%>
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
            <!-- 引用买家和卖家留言 -->
        	<jsp:include page="buyerAndSalerInfo.jsp?order_id=${order_id}"/> 
        	<jsp:include page="order_handler.jsp?order_id=${order_id }"/>
        </div>
    </div>
    <div id="order_btn_event_dialog"></div>
</div>
</form>
</body>
</html>

<script type="text/javascript">
  function checkPrePick(){
	  if(window.confirm("确定处理订单？")){
			return true;
		}else{
			return false;
		}
  }
  function prePickCallBack(data){
  	alert(data.message);
  	if(data.result==0){
  		var listBtn = $("#isListBtn");
  		if(listBtn&&listBtn.val()){
  			var url = window.location.href;
  			window.location.href  = url;
  		}else{
  			window.location.href=data.action_url;
  		}  		
  	}else{
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
  $(".ipttxt").removeClass("ipttxt");
  
  

  function load_ord_his(){
		//$("#table_show").show();
		//$("#order_his").show();
		$("#table_show").toggle();
	   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};

  
  
  $(function(){
	//先加载总数和按钮页
	  CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);
	  //CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
	  AutoFlow.checkMsg();
	  
  });
 

  $(function(){
		Eop.Dialog.init({id:"order_btn_event_dialog",modal:true,title:"订单信息", height:"600px",width:"800px"});
	});
	
	function closeDialog(){
		Eop.Dialog.close("order_btn_event_dialog");
	}
</script>