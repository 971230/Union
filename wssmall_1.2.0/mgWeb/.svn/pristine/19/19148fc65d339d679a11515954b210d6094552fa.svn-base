<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
var offer_id = '${goods.crm_offer_id}'; 
var goods_id = '${goods.goods_id}'; 
var type_code ='${goodsType.type_code}';
loadScript("js/goods_apply_dialog.js");
</script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">
<div class="division">
<form  class="validate" method="post" action="" id='goods_apply_form' validate="true">
	   <input type="hidden" id="hidden_lan_id" value=""/>
	   <input type="hidden" id="hidden_b_acc_type" value=""/>
	   <input type="hidden" id="hidden_e_acc_type" value=""/>
	   <input type="hidden" id="hidden_acc_type" value=""/>
	   <input type="hidden" id="hidden_sel_type" value=""/>
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
<table width="100%">
  <tbody>
	  <tr>
	    <th>商品名称：</th><input type="hidden" name="goodsname" value="${goods.name }" />
	    <td>${goods.name}</td>
	  
	  </tr>
	  <c:if test="${goodsType.have_price==1}">
		  <tr>
		    <th>商品价格：</th><input type="hidden" name="goodsApply.sales_price" value="${goods.price }" />
		    <td>${goods.price}</td>
		  </tr>
	  </c:if>
	  
	  
	 <c:if test="${goodsType.type_code=='CLOUD' or goodsType.type_code=='CONTRACT'}"> 
	 
	 	 <tr>
		    <th>号码归属本地网：</th> 
		    <!-- 修改name为goodsApply.lan_id 云卡、合约卡订单需要 -->
		    <td><select  class="ipttxt"  style="width: 100px" id="out_lan_id" name="goodsApply.lan_id" >
					<c:forEach var="lan" items="${lanList}">
					 	<option  value="${lan.lan_id }" ${lan_id == lan.lan_id ? ' selected="selected" ' : ''}>${lan.lan_name }</option>
					 </c:forEach>
			    </select></td>
		  </tr>
		  
		  <c:if test="${goodsType.type_code=='CONTRACT'}"> 
		  <tr>
		    <th>开始号码：</th>
		    <td><input type="text" class="ipttxt"  attr="p_begin_nbr" class="input_text"  autocomplete="off" dataType="mobile" name="goodsApply.begin_nbr" value="${begin_nbr }" readonly="readonly" />
		    	<c:if test="${goodsType.type_code=='CLOUD'}">
			    	<a href="javascript:void(0)" class="sgreenbtn" > 
			    		<span name="cloudStockView" sel_type='begin'  style='color:#fff;'>查看可用库存</span>
			    	</a>
		    	</c:if>
		    	<c:if test="${goodsType.type_code=='CONTRACT'}">
			    	<a href="javascript:void(0)" class="sgreenbtn" > 
			    		<span name="cloudStockView" sel_type='begin'  style='color:#fff;'>查看可用号码</span>
			    	</a>
		    	</c:if>
		    </td>
		  </tr>
		  
		  <tr>
		    <th>结束号码：</th>
		    <td><input type="text" class="ipttxt"  attr="p_end_nbr"  class="input_text"  autocomplete="off" dataType="mobile" name="goodsApply.end_nbr" value="${end_nbr }" readonly="true"  />
		    	<c:if test="${goodsType.type_code=='CLOUD'}">
			    	<a href="javascript:void(0)" class="sgreenbtn" >
			    	 <span  sel_type='end'  name="cloudStockView" style='color:#fff;'>查看可用库存</span>
			    	</a>
		    	</c:if>
		    	<c:if test="${goodsType.type_code=='CONTRACT'}">
			    	<a href="javascript:void(0)" class="sgreenbtn" >
			    	 <span  sel_type='end'  name="cloudStockView" style='color:#fff;'>查看可用号码</span>
			    	</a>
		    	</c:if>
		    	
		    </td>
		  </tr>
		  </c:if>
		  
	  </c:if>
	  
	   <c:if test="${goodsType.have_stock==1 or (goodsType.have_stock==0 and goodsType.type_code=='CONTRACT')}">
		  <tr>
		    <th>订购数量：</th>
		    <td><input type="text" class="ipttxt" attr="goods_num" class="input_text" required="true" dataType="int" name="goodsApply.goods_num" value="${goods_num }"  />
		    	<span class='red' id='order_goods_count_span'></span>
		    </td>
		  </tr>
	  </c:if>
	  
	  
	  
      <tr><input type="hidden" name="goodsApply.goods_id" value="${goods.goods_id }" />
	    <th>申请说明：</th><input type="hidden" id="hide_apply_desc" value="${apply_desc}" />
	   	 <td colspan="3"><textarea value="${apply_desc}" autocomplete="on" attr="apply_desc" required="true" rows="4" cols="36" name="goodsApply.apply_desc"></textarea></td>
	  </tr>
	  <!-- <tr>
	    <th>手机号码：</th>
	    <td><input type="text" class="ipttxt"  required="true" class="input_text" dataType=int name="goodsApply.ship_tel" value="${partner.legal_phone_no}" />
	    </td>
	  
	  </tr> -->
	  <tr><input type="hidden" name="goodsid" value="${goods.goods_id }" />
	    <th>收货地址：</th>
	    <td colspan="3">
	    <!-- <input type="text" class="ipttxt"  name="goodsApply.ship_addr" value="${add.addr }" readonly="readonly" /> 
	    	<input type="radio" id="newAdd" name="goodsApply.ship_addr"/>新地址:&nbsp;<span id="newInput" style="display:none"><input type="text" class="ipttxt"  id="newspace" dataType="int" value="" /></span><br />
	   	 <c:forEach items="${addressList}" var="add" varStatus="status">   
			<input  <c:if test="${status.index+1==1}">checked="checked"</c:if> type="radio" name="goodsApply.ship_addr" value="${add.addr}" />地址&nbsp;${status.index+1}&nbsp;:&nbsp;${add.addr }<br />
		</c:forEach>
		-->
		<div class="partnerInfo">
		<table cellspacing="0" cellpadding="0" border="0" width="100%" class="">
			<thead>
				<tr>
					<th style="width:6px;"></th>
<%--				<th>电话号码</th>--%>
					<th>收货人</th>
					<th>收货地址</th>
					<th>邮编</th>
					
					<th>手机号码</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${addressList}" var="addr" varStatus="status">   
				<tr>
					<td><input type="radio" <c:if test="${status.index+1==1}">checked="checked"</c:if> value="${addr.addr_id}" name="addressNum"/></td>
<%--				<td>${addr.tel}</td>--%>
					<td>${addr.name }</td>
					<td>${addr.addr }</td>
					<td>${addr.zip}</td>
					
					<td>${addr.mobile}</td>
				</tr>
				</c:forEach>          
			</tbody>
		</table>
	</div>   
		</td>
	  </tr>
	  

    </tbody>
</table>


<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
	 		<input type="button" value="配置新地址" name="newAddress" goodsid="${goods.goods_id }" class="submitBtn" />
           <input  type="button"  value="提交申请" class="submitBtn" name='submitBtn'/>
           <input name="reset" type="reset"  value=" 重  置 " class="submitBtn"/>
	   </td>
	</tr>
 </table>
</div>
</form>
</div>
<div id="stock_dialog">
</div>
<div id="accnbr_dialog">
</div>
<!-- 配置新地址页面 -->
<div id="newAddr_dialog" >
</div>

<form  class="validate" method="post" action="" id='acc_number_form' validate="true">
</form>
