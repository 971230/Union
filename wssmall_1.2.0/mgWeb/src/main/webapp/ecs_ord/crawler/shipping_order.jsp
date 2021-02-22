<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发货</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
</head>
<body>
<form action="javascript:void(0);" id="shipping_order_form" method="post">
<input type="hidden" name="delivery_id" value="${delivery.delivery_id }" />
<div class="gridWarp">
	<div class="new_right">
	
        <div class="right_warp">
            <!-- 顶部公共 -->
        	<jsp:include page="auto_flows.jsp?order_id=${order_id }"/>
          <!-- 订单全部信息开始 -->  
        	<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>发货信息</h2>
            	<input type="hidden"  id="post_type" name="post_type" value="0"/>
                <div class="grid_n_cont">
            		<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
                  	<div class="grid_n_cont_sub">
                    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
                          <tbody>
                          <tr>
                            <th>物流单：</th>
                            <td colspan="3">
                            	<html:orderattr  order_id ="${order_id }" field_name="logi_no"  field_desc ="物流单号" field_type="text"></html:orderattr>
                            </td>
                          </tr>
                          <c:if test="${deal_type =='01'}">   
                          <tr>
                            <th>&nbsp;</th>
                            <td colspan="3" style="color:#F00;">
	                               订单处理类型：${deal_type} 闪电送物流公司：${quick_shipping_cop} 闪电送状态：${quick_shipping_status }
                            </td>
                          </tr>
                          </c:if>
                          <tr>
                            <th>物流公司：</th>
                            <td>
                                <html:orderattr disabled="disabled" attr_code="DIC_LOGI_COMPANY"  order_id="${order_id}" field_name="shipping_company"  field_desc ="物流公司" field_type="select"></html:orderattr>
                            </td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <c:if test="${shipping_cop_id=='ZT0002' || shipping_cop_id=='ZY0002'}">
	                          <tr>
	                            <th>取货人：</th>
	                            <td>
	                            	<html:orderattr  order_id ="${order_id }" field_name="carry_person"  field_desc ="取货人" field_type="input"></html:orderattr>
	                            	<%-- <input name="logi_receiver" type="text" class="ipt_new" style="width:150px;" value="${delivery.logi_receiver }" > --%>
	                            </td>
	                            <th>取货人电话：</th>
	                            <td>
	                            	<html:orderattr  order_id ="${order_id }" field_name="carry_person_mobile"  field_desc ="取货人电话" field_type="input"></html:orderattr>
	                           		<%-- <input name="logi_receiver_phone" type="text" class="ipt_new" style="width:150px;" value="${delivery.logi_receiver_phone }" > --%>
	                           	</td>
	                          </tr>
                          </c:if>
                          
                        </tbody></table>
                  	</div>
                </div>
            </div>
            
            <div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>订单信息</h2>
                <div class="grid_n_cont">
            		<%-- <div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div> --%>
                  	<div class="grid_n_cont_sub">
                    	<div class="form_g">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_a">
                              <tbody>
                              <tr>
                                <td>
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="">
                                      <tbody><tr>
                                        <th>内部订单号：</th>
                                        <td>${orderTree.order_id }</td>
                                        <th>外部订单号：</th>
                                        <td>${orderTree.orderExtBusiRequest.out_tid }</td>
                                      </tr>
                                      <tr>
                                        <th>物流单号：</th>
                                        <td>${delivery.logi_no }</td>
                                        <th>收货人姓名：</th>
                                        <td>${orderTree.orderBusiRequest.ship_name }</td>
                                      </tr>
                                      <tr>
                                        <th>配送区域：</th>
                                        <td>${delivery.province }${delivery.city }${delivery.region }</td>
                                        <th>收货人电话：</th>
                                        <td>${orderTree.orderBusiRequest.ship_tel }</td>
                                      </tr>
                                      <tr>
                                        <th>收货人地址：</th>
                                        <td>${delivery.ship_addr }${delivery.ship_zip}</td>
                                        <th>&nbsp;</th>
                                        <td>&nbsp;</td>
                                      </tr>
                                      <tr>
                                        <th>是否已上传：</th>
                                        <td>
                                        	<html:orderattr disabled="disabled" attr_code="DIC_IS_UPLOAD" order_id ="${order_id }" field_name="is_upload"  field_desc ="资料回收方式" field_type="select" ></html:orderattr>
                                        </td>
                                        <th>资料回收类型：</th>
                                        <td>
                                        	<html:orderattr disabled="disabled" order_id ="${order_id }" field_name="file_return_type"  field_desc ="资料回收方式" field_type="text"></html:orderattr>
                                        </td>
                                      </tr>
                                      <tr>
                                        <th>发票号码：</th>
		                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="invoice_no"  field_desc ="发票号码" field_type="input"></html:orderattr></td>
		                             	<th>发票代码：</th>
		                              	<td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="invoice_code"  field_desc ="发票代码" field_type="input"></html:orderattr></td>
                           			  </tr>
                                    </tbody></table>
                                </td>
                              </tr>
                            </tbody></table>
                        </div>
                  	</div>
            		<!-- ZX add 2015-12-30 start 副卡信息开始 -->
               	  	<div class="grid_n_cont_sub">
               	  	 	<jsp:include page="include/phone_info_fuka.jsp?order_id=${order_id }"/>
               	  	</div>
               	  	<!-- ZX add 2015-12-30 end 副卡信息结束 -->
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
	$("#table_show").toggle();
	//$("#order_his").show();
   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};

  
  $(function(){
	  AutoFlow.checkMsg();
	//先加载总数和按钮页
	  CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);
	  //CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
	  if(${orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053'}){//多商品
		  CommonLoad.loadJSP('goods_info_more','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"view_goods_info_more"},false,function(){AutoFlow.checkMsg();},true);
	  }
  });
 
  //物流单打印前校验
  function checkPrintData () {
	  
	  return true;
  }
</script>

