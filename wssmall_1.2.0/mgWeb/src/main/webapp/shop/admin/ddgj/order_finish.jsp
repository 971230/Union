<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!-- <div class="btnWarp">
 </div> -->
<form  method="post" id="order_finish_form" > 
<div class="formWarp">
<input type="hidden" name="order_id" value="${order_id }" />
<input type="hidden" name="orderId" value="${order_id }" />
  <div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="6000" class="closeArrow"></a>订单信息
  <div class="dobtnDiv"></div></div>
    <div id="order_tag_6000" class="formGridDiv" >
    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
          <tr>
            <th>订单编号：</th>
            <td>${order.order_id }</td>
            <th>下单日期：</th>
            <td><html:dateformat pattern="yy-MM-dd hh:mm:ss" d_time="${order.create_time}"></html:dateformat></td>
            <th>订单总价：</th>
            <td><fmt:formatNumber value="${order.order_amount }" type="currency" pattern="￥.00"/></td>
          </tr>
          <tr>
          	<th>配送费用：</th>
            <td><fmt:formatNumber value="${order.shipping_amount }" type="currency" pattern="￥.00"/></td>
            <th>物流公司：</th>
            <td>${delivery.logi_name }</td>
            <th>物流单号：</th>
            <td>${delivery.logi_no }</td>
          </tr>
          <tr>
          	<th>收货人姓名：</th>
            <td>${order.ship_name}</td>
            <th>电话号码：</th>
            <td>${order.ship_tel}</td>
            <th>手机号码：</th>
            <td>${order.ship_mobile}</td>
          </tr>
          <tr>
            <th>邮政编码：</th>
            <td>${order.ship_zip}</td>
            <th>收货地址：</th>
            <td colspan="3">${order.ship_addr}</td>
          </tr>
        </table>
    </div>
</div> 

<div class="formWarp">
<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="6001" class="closeArrow"></a>商品明细<div class="dobtnDiv"></div></div>
<div id="order_tag_6001" class="formGridDiv">
    <table id="goods_items_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
      <tr>
        <th>商品ID</th>
        <th>商品名称</th>
        <th>购买数量</th>
        <th>已发货数量</th>
      </tr>
      <tbody>
      <c:forEach items="${itemList }" var="item">
	      <tr>
	        <td>
	        	<a href="javascript:void(0);">${item.goods_id}</a>
	        </td>
	        <td>${item.name}</td>
	        <td>
	        	${item.num}
	        </td>
	        <td>${item.ship_num}</td>
	      </tr>
      </c:forEach>
      </tbody>
    </table>
</div>    
</div>

<div class="formWarp">
	<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="5009" class="closeArrow"></a>环节信息<div class="dobtnDiv"></div></div>
    <div id="order_tag_5009" class="formGridDiv" >
    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
          <tr>
          	<%-- <th>选择环节:</th>
            <td>
            	<select id="order_flow_select" name="flow_def_id">
            		<c:forEach items="${flowDefList }" var="flow">
            			<option value="${flow.flow_def_id }" ${flowDef.flow_def_id==flow.flow_def_id?'selected':'' }>${flow.flow_name }</option>
            		</c:forEach>
            	</select>
            </td>
            <th>订单处理组:</th>
            <td>
            	<select id="order_group_select" name="flow_group_id">
            		<c:forEach items="${orderGroupList }" var="og">
            			<option value="${og.group_id }">${og.group_name }</option>
            		</c:forEach>
            	</select>
            </td>
            <th>订单处理人:</th>
            <td>
            	<input type="hidden"  name="flow_user_id" value="" />
            	<input name="flow_user_name" type="text" class="formIpt" value="" readonly="readonly" />
            </td>
          </tr> --%>
          <tr>
          	<th>处理意见：</th>
          	<td colspan="5">
          		<input type="radio" name="flag_status" value="1" checked="checked" />通过&nbsp;&nbsp;<input type="radio" name="flag_status" value="2" />不通过&nbsp;&nbsp;
          	</td>
          </tr>
          <tr>
            <th>备注：</th>
            <td colspan="5">
            	<textarea  style="width: 95%;" name="hint" type="textarea"></textarea>
            </td>
          </tr>
          <tr>
	         <th colspan="6" style="text-align: center;">
	         	<a href="javascript:void(0);" id="order_finish_btn_submit" class="dobtn" style="margin-right:5px;">完成订单</a>
	         </th>
	       </tr>
        </table>
    </div>
</div> 

</form>
<div id="select_admin_dialog"></div>

<script type="text/javascript">
	$(function(){
		Eop.Dialog.init({id:"select_admin_dialog",modal:true,title:"选择处理用户", height:"600px",width:"600px"});
		
	});
</script>