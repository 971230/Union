<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div>
<form method="post" id="serchOrderform" action="${ctx}/shop/admin/ordAuto!inputOrderInfo.do?ajax=yes&excel=yes" ajax="yes">
 <div class="searchformDiv">
	 <table>
	  <input type="submit" style="margin-left:5px;margin-top:10px;" class="comBtn" value="导&nbsp;&nbsp;出"  id="inputOrderInfoBtn"/>
	  <input type="hidden" name="textInfo" id="textInfo" value="${params.bat_id}" />
	 </table>
  </div>
</form>

<div class="grid" >
<div class="comBtnDiv">
<grid:grid from="webpage"  formId="serchOrderform" ajax="yes">
	<grid:header>
				<grid:cell width="90px">外系统单号</grid:cell>
				<grid:cell width="90px">用户号码</grid:cell>
				<grid:cell width="90px">用户姓名</grid:cell>
				<grid:cell width="90px">证件类型</grid:cell>
				<grid:cell width="80px">证件号码</grid:cell>
				<grid:cell width="60px">地市</grid:cell>
				<grid:cell width="60px">来源</grid:cell>
				<grid:cell width="80px">商品名称</grid:cell>
				<grid:cell width="80px">商城实收</grid:cell>
				<grid:cell width="80px">收货人姓名</grid:cell>
				<grid:cell width="80px">收货人电话</grid:cell>
				<grid:cell width="80px">收货地址</grid:cell>
				<grid:cell width="80px">收货地市</grid:cell>
				<grid:cell width="80px">收货县分</grid:cell>
				<grid:cell width="80px">订单状态</grid:cell>
				<grid:cell width="80px">失败原因</grid:cell>
			</grid:header>
	<grid:body item="inputOrderInfo">
				<grid:cell>${inputOrderInfo.out_order_id}</grid:cell>
				<grid:cell>${inputOrderInfo.acc_nbr}</grid:cell>
				<grid:cell>${inputOrderInfo.cust_name}</grid:cell>
				<grid:cell>${inputOrderInfo.certi_type}</grid:cell>
				<grid:cell>${inputOrderInfo.certi_num}</grid:cell>
				<grid:cell>${inputOrderInfo.order_city_code}</grid:cell>
				<grid:cell>${inputOrderInfo.source_from}</grid:cell>
				<grid:cell>${inputOrderInfo.prod_offer_code}</grid:cell>
				<grid:cell>${inputOrderInfo.pay_money}</grid:cell>
				<grid:cell>${inputOrderInfo.ship_name}</grid:cell>
				<grid:cell>${inputOrderInfo.ship_phone}</grid:cell>
				<grid:cell>
					<div class="icoFontlist" title="${inputOrderInfo.ship_addr}" >${inputOrderInfo.ship_addr}</div>	
				</grid:cell>
				<grid:cell>${inputOrderInfo.ship_city}</grid:cell>
				<grid:cell>${inputOrderInfo.ship_county}</grid:cell>
				<grid:cell>${inputOrderInfo.status}</grid:cell>
				<grid:cell>
					<div class="icoFontlist" title="${inputOrderInfo.exception_desc}" >${inputOrderInfo.exception_desc}</div>	
				</grid:cell>
			</grid:body>
</grid:grid>
</div>
</div>
</div>

<script type="text/javascript">

</script>