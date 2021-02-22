<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单归集</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/publics/css/reset.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/publics/css/style.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/publics/css/top.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/publics/css/tab.css" type="text/css" />
<script src="<%=request.getContextPath() %>/shop/admin/order_his/js/his_order.js"></script>
<script type="text/javascript">
	local_order_url = "";
</script>
</head>

<body>

<!-- 顶部菜单  start  -->
<div class="searchWarp clearfix">
    <!-- 搜索 start -->
    <div class="searchBox">
	    <form id="search_form_common" method="post">
	        <span class="searchIpt">
	            <a class="search_round" href="javascript:void(0);">商品名称</a>
	            <input type="text" name="orderName" value="">
	        </span>
	        <span class="orderZt">
	        	订单状态:
		        <a id="order_ststus_select" href="javascript:void(0);" class="search_more" action_code="all">全部</a>
		        <div id="order_ststus_div" class="selectDiv" style="top:20px;left:55px;display: none;">
	                <ul>
	                	<li><a href="javascript:void(0);" action_code="all">全部</a></li>
	                    <li><a href="javascript:void(0);" action_code="pay">待支付</a></li>
	                    <li><a href="javascript:void(0);" action_code="audit">待审核</a></li>
	                    <li><a href="javascript:void(0);" action_code="openAccount">待开户单</a></li>
	                    <li><a href="javascript:void(0);" action_code="package">待备货</a></li>
	                    <li><a href="javascript:void(0);" action_code="delivery">待发货</a></li>
	                    <li><a href="javascript:void(0);" action_code="received">待确认收化</a></li>
	                    <li><a href="javascript:void(0);" action_code="finish">待完成</a></li>
	                </ul>
	            </div>
            </span>
	        <a href="javascript:void(0);" class="searchBtnN" style="margin-left:5px;" name="list_order_btn" search_flag="yes" common_search="yes" his="yes">搜索</a>
	        <a href="javascript:void(0);" class="seniorBtn" id="search_dialog_show">高级搜索</a>
	    </form>   
    </div>
     <!-- 搜索 end -->
     
     <!-- 高级搜索 start -->
    <div id="search_dialog" class="seniorPop" style="display:none;">
    	<form method="post">
    	<div class="popTit"><h2>高级搜索</h2><a href="javascript:void(0);" id="search_dialog_close_btn" class="popClose">关闭</a></div>
        <div class="seniorPopCon">
        	<div>
            	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="seniorCond">
                  <tr>
                    <td>
                    	<div class="seniorSelTit">订单状态</div>
                        <div class="seniorSel">
                        	<select name="orderstatus" size="8" id="orderstatus">
                        	  <option value="-1">-全部-</option>
                        	  <option value="0">待审核</option>
                        	  <option value="1">审核不通过</option>
                        	  <option value="2">待付款</option>
                        	  <option value="3">已支付</option>
                        	  <option value="4">已备货</option>
                        	  <option value="5">已发货</option>
                        	  <option value="6">已收货</option>
                        	  <option value="7">已完成</option>
                        	  <option value="8">已作废</option>
                        	  <option value="-9">已撤单</option>
                        	  <option value="-10">已取消</option>
                        	  <option value="99">异常单</option>
                        	  <option value="-7">换货</option>
                        	  <option value="-2">退货</option>
                        	  <option value="-1">退款</option>
							</select>
                        </div>
                    </td>
                    <td>
                    	<div class="seniorSelTit">订单配送方式</div>
                        <div class="seniorSel">
                        	<select name="dlyTypeId" size="8" id="dlyTypeId">
                        	  <option value="-1">--全部--</option>
                        	  <c:forEach items="${dlyTypeList }" var="dly">
                        	  	<option value="${dly.type_id }">${dly.name }</option>
                        	  </c:forEach>
							</select>
                        </div>
                    </td>
                    <td>
                    	<div class="seniorSelTit">支付方式</div>
                        <div class="seniorSel">
                        	<select name="payment_id" size="8" id="payment_id">
                        	  <option value="-1">--全部--</option>
                        	  <c:forEach items="${paymentList }" var="cfg">
                        	  	<option value="${cfg.id }">${cfg.name }</option>
                        	  </c:forEach>
							</select>
                        </div>
                    </td>
                  </tr>
                </table>
            </div>
            <div class="seniorForm">
            	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formGrid">
                  <tr>
                    <th>订单编号：</th>
                    <td><input name="orderCode" type="text" class="formIpt" id="orderCode" value="" /></td>
                    <th>收货人：</th>
                    <td><input name="username" type="text" class="formIpt" id="username_g" value="" /></td>
                    <th>联系电话：</th>
                    <td><input name="phoneNo" type="text" class="formIpt" id="phoneNo" value="" /></td>
                    <!-- <th>物流单号：</th>
                    <td><input name="deliveryNo" type="text" class="formIpt" id="deliveryNo" value="" /></td> -->
                  </tr>
                  <tr>
                    <th>开始时间：</th>
                    <td>
                    <input type="text"  name="startTime" id="startTime" value="" readonly="readonly"
						maxlength="60" class="formIpt dateinput"  dataType="date"/> 
					</td>
                    <th>结束：</th>
                    <td>
                    <input type="text"  name="endTime" id="endTime" value="" readonly="readonly"
						maxlength="60" class="formIpt dateinput"  dataType="date"/> 
                    </td>
                    <th>商品名称：</th>
                    <td><input name="orderName" type="text" class="formIpt" id="orderName_g" value="" /></td>
                  </tr>
                  <!-- <tr>
                    <th>入网号码：</th>
                    <td><input name="netPhoneNo" type="text" class="formIpt" id="netPhoneNo" value="" /></td>
                    <th>终端串码：</th>
                    <td><input name="terminalNo" type="text" class="formIpt" id="terminalNo" value="" /></td>
                    <th>&nbsp;</th>
                    <td>&nbsp;</td>
                    <th>&nbsp;</th>
                    <td>&nbsp;</td>
                  </tr> -->
                </table>
            </div>
            <div class="btns">
            	<a href="javascript:void(0);" name="list_order_btn" search_flag="yes" his="yes" class="blueBigBtn">搜索</a>
            </div>
        </div>
        </form>
    </div>
     <!-- 高级搜索 start -->
    
</div>
<!-- 顶部菜单  end  -->


<div id="order_list_main" class="gridWarp">
	<!-- 订单列表 start -->
	<div id="order_list_dv" class="gridContent">
    	<form class="grid" id="gridform_order">
    	<grid:grid from="webpage" formId="gridform_order" ajax="yes" >
			<grid:header>
				<grid:cell width="30px">选择</grid:cell>
				<grid:cell width="150px">订单号&nbsp;&nbsp;<span class="help_icon" helpid="order_showdetail"></span></grid:cell>
				<grid:cell width="160px;">订单总额</grid:cell>
				<%-- <grid:cell >申请区域</grid:cell> --%>
				<grid:cell >购买人</grid:cell>
				<grid:cell >收货人</grid:cell>  
				<grid:cell >订单状态</grid:cell> 
				<grid:cell >支付状态</grid:cell> 
				<grid:cell >发货状态</grid:cell>
				<grid:cell width="120px">下单日期</grid:cell> 
			</grid:header>
		    <grid:body item="order">
		  	    <grid:cell width="30px">
		  	    	<input type="radio" name="order_id" order_id="${order.order_id }" remark="${order.remark }"
		  	    		ship_name="${order.ship_name}" ship_addr="${order.shipping_area } ${order.ship_addr}"  order_status="${order.status }"
		  	    		pay_status="${order.payStatus}" ship_status="${order.shipStatus}" order_amount="${order.order_amount}" 
		  	    		ship_mobile="${order.ship_mobile }" />
		  	    </grid:cell>
		        <grid:cell>
		        	<input type="checkbox" name="id" value="${order.order_id }" style='display:none;'/>
		        	<c:if test="${order.status<3}"><B></c:if>${order.order_id}<c:if test="${order.status<3}"></B></c:if>
		        	<c:if test="${order.source_from =='00C'}"> <img src='${ctx}/shop/admin/images/w_a.jpg' style='float:right;'/></c:if>
		        </grid:cell>
		       <grid:cell>￥${order.order_amount}</grid:cell> 
		       <%-- <grid:cell>${order.lan_name}</grid:cell> --%>
		       <grid:cell>
			        ${order.uname}
		         </grid:cell>
		         <grid:cell>
			        ${order.ship_name}
		         </grid:cell>
		         <grid:cell><span style="color: red;" name="order_status_sp">${order.orderStatus}</span></grid:cell> 
		         <grid:cell><span style="color: blue;">${order.payStatus}</span></grid:cell> 
		         <grid:cell><span style="color: blue;">${order.shipStatus}</span></grid:cell> 
		        <grid:cell><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${order.create_time}"></html:dateformat></grid:cell> 
		  </grid:body>
      </grid:grid>	
	 </form>
    </div>
    
    <!-- 订单列表 end -->
    
    <!-- 操作菜单 start -->
	<div class="gridInfoWarp" id="right_order_info_div">
        <div style="margin: 200px 0px 0px 150px;">订单信息窗口</div>
    </div>
    <!-- 操作菜单 end -->
</div>


<!-- 订单详细 操作 start-->
<div id="order_detail_div" class="gridWarp" style="display: none;">
    <div class="gridInfoPop">
        <div class="tabBg">
        	<a href="javascript:void(0);" id="order_detail_close" class="backico"></a>
            <div class="stat_graph">
                <h3>
                    <div class="graph_tab">
                        <ul id="order_header_tag">
                            <li id="show_click_1" ev_type="tag" class="selected"><span class="word">基本信息</span><span class="bg"></span></li>
                            <li id="show_click_2" ev_type="tag" class=""><span class="word">订单明细</span><span class="bg"></span></li>
                            <li id="show_click_3" ev_type="tag" class=""><span class="word">收退款记录</span><span class="bg"></span></li>
                            <li id="show_click_4" ev_type="tag" class=""><span class="word">发退货记录</span><span class="bg"></span></li>
                            <li id="show_click_5" ev_type="tag" class=""><span class="word">优惠方案</span><span class="bg"></span></li>
                            <li id="show_click_6" ev_type="tag" class=""><span class="word">订单日志</span><span class="bg"></span></li>
                            <li id="show_click_7" ev_type="tag" class=""><span class="word">订单附言</span><span class="bg"></span></li>
                            <li id="show_click_8" ev_type="tag" class=""><span class="word">异常信息</span><span class="bg"></span></li>
                            <li id="show_click_9" ev_type="tag" class=""><span class="word">订单备注</span><span class="bg"></span></li>
                            <li id="show_click_10" ev_type="tag" class=""><span class="word">流程日志</span><span class="bg"></span></li>
                            <div class="clear"></div>
                        </ul>
                    </div>
                </h3>
            </div>
        </div>
        <!-- 订单详细信息 -->   
	   <div id="order_info_dv">  
	   		<div style="text-align: center;height: 100px;margin-top: 200px;"></div>
	   </div>
	   <!-- 订单详细信息 -->  
    </div>
</div>
<!-- 订单详细 操作  end-->

<!-- 订单附言 -->
<div id="member_remark" style="display: none;">
	<div class="btnWarp">
	 </div>
	<div class="formWarp" style="border-bottom: none;">
		<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="4000" class="closeArrow"></a>订单附言
	  <div class="dobtnDiv"></div></div>
	    <div id="order_tag_4000" class="formGridDiv" >
	    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
	          <tr>
	          	<td>
	          		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea class="ipttxt" style="width: 95%;height: 200px;" name="member_remark" readonly="readonly"></textarea>
	          	</td>
	          </tr>
	        </table>
	    </div>
	</div> 
</div>

<div id="orderApply_dialog"></div>
<div id="selectOrderDlg"></div>
<div id="selectgoodsDlg"></div>
<div id="auditOrderApplyDlg"></div>
<!-- 订单异常 -->
<div id="order_exception_dlg"></div>
<!-- 订单备注 -->
<div id="order_remak_dlg"></div>

<div id="order_flows_log_dlg"></div>

</body>
</html>