<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="btnWarp">
</div>

<div class="formWarp" style="border-bottom: none;">
	<div class="tit">
		<a href="javascript:void(0);" name="show_close_btn" tag_id="8801" class="closeArrow"></a>发货记录
	    <div class="dobtnDiv"></div>
	</div>
	<div id="order_tag_8801" class="formGridDiv" >
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
	      <tr>
	        <th>选择</th>
	        <th style="width: 100px;">物流编码</th>
	        <th>物流公司</th>
	        <th style="width: 80px;">配送方式</th>
	        <th style="width: 80px;">时间</th>
	      </tr>
	      <tbody id="send_goods_body_list">
	      <c:forEach items="${shipLogList }" var="ship" varStatus="status">
	      	<tr>
		        <td style="text-align: center;"><input type="radio" name="send_radio" delivery_id="${ship.delivery_id }" logi_no="${ship.logi_no }" logi_id="${ship.logi_id }" ${status.index==0?'checked':''} /></td>
		        <td style="text-align: center;"><a href="javascript:void(0);">${ship.logi_no }</a></td>
		        <td style="text-align: center;">${ship.logi_name }</td>
		        <td class="red" style="text-align: center;">${ship.ship_type }</td>
		        <td style="text-align: center;">${ship.create_time}</td>
		      </tr>
	      </c:forEach>
	      </tbody>
	      <tbody>
	      	<tr>
	      		<th id="send_goods_tb" colspan="5" style="width: 100%;"></th>
	      	</tr>
	      </tbody>
	    </table>
	</div>
	
	<div class="tit">
		<a href="javascript:void(0);" name="show_close_btn" tag_id="8802" class="closeArrow"></a>退货记录
	    <div class="dobtnDiv"></div>
	</div>
	<div id="order_tag_8802" class="formGridDiv" >
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
	      <tr>
	        <th>选择</th>
	        <th style="width: 100px;">物流编码</th>
	        <th>物流公司</th>
	        <th style="width: 80px;">配送方式</th>
	        <th style="width: 80px;">时间</th>
	      </tr>
	      
	      <tbody id="returned_goods_body_list">
	      <c:forEach items="${reshipLogList }" var="ship" varStatus="status">
	      	<tr>
		        <td style="text-align: center;"><input type="radio" name="returned_radio" delivery_id="${ship.delivery_id }" logi_no="${ship.logi_no }" logi_id="${ship.logi_id }" ${status.index==0?'checked':''} /></td>
		        <td style="text-align: center;"><a href="javascript:void(0);">${ship.logi_no }</a></td>
		        <td style="text-align: center;">${ship.logi_name }</td>
		        <td class="red" style="text-align: center;">${ship.ship_type }</td>
		        <td style="text-align: center;">${ship.create_time}</td>
		      </tr>
	      </c:forEach>
	      </tbody>
	      
	      <tbody>
	      	<tr>
	      		<td id="returned_goods_tb" colspan="5" style="width: 100%;"></td>
	      	</tr>
	      </tbody>
	    </table>
	</div>
	
	<div class="tit">
		<a href="javascript:void(0);" name="show_close_btn" tag_id="8803" class="closeArrow"></a>换货记录
	    <div class="dobtnDiv"></div>
	</div>
	<div id="order_tag_8803" class="formGridDiv" >
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
	      <tr>
	        <th>选择</th>
	        <th style="width: 100px;">物流编码</th>
	        <th>物流公司</th>
	        <th style="width: 80px;">配送方式</th>
	        <th style="width: 80px;">时间</th>
	      </tr>
	      <tbody id="exchange_goods_body_list">
	      <c:forEach items="${chshipLogList }" var="ship" varStatus="status">
	      	<tr>
		        <td style="text-align: center;"><input type="radio" name="exchange_radio" delivery_id="${ship.delivery_id }" logi_no="${ship.logi_no }" logi_id="${ship.logi_id }" ${status.index==0?'checked':''} /></td>
		        <td style="text-align: center;"><a href="javascript:void(0);">${ship.logi_no }</a></td>
		        <td style="text-align: center;">${ship.logi_name }</td>
		        <td class="red" style="text-align: center;">${ship.ship_type }</td>
		        <td style="text-align: center;">${ship.create_time}"</td>
		      </tr>
	      </c:forEach>
	      </tbody>
	      
	      <tbody>
	      	<tr>
	      		<td id="exchange_goods_tb" colspan="5" style="width: 100%;"></td>
	      	</tr>
	      </tbody>
	    </table>
	</div>
</div>

<div id="order_delivery_info_add_dialog"></div>

<script type="text/javascript">
	$(function(){
		Eop.Dialog.init({id:"order_delivery_info_add_dialog",modal:true,title:"添加物流信息", height:"600px",width:"600px"});
		
	});
</script>
