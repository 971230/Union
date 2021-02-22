<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form id="memberLvFrm">

<div style="clear:both;padding-top:5px;"></div>

<div class="grid" id="memberLvList">

<grid:grid  from="webpage" ajax="yes" >
	<grid:header>
		<grid:cell width="120px"><input type="hidden" name="toggleChk"/>ID</grid:cell> 
		<grid:cell width="200px">商品名称</grid:cell>
		<grid:cell width="100px">商品编码</grid:cell> 
		<grid:cell >价格销售对象</grid:cell>
		<grid:cell >原价格</grid:cell>
		<grid:cell >修改价格</grid:cell>
	</grid:header>
  <grid:body item="memberLv" >
  		<grid:cell><input type="hidden" name="goodsLvPriceList[${memberLv.index}].id" value="${memberLv.id}" datatype="filed" dateFileName="id"/>${memberLv.id}</grid:cell>
        <grid:cell><input type="hidden" name="goodsLvPriceList[${memberLv.index}].prodname" value="${memberLv.prodname}" datatype="filed" dateFileName="prodname"/>${memberLv.prodname}</grid:cell> 
        <grid:cell><input type="hidden" name="goodsLvPriceList[${memberLv.index}].productid" value="${memberLv.productid}" datatype="filed" dateFileName="productid"/>${memberLv.productid} </grid:cell> 
        <grid:cell><input type="hidden" name="goodsLvPriceList[${memberLv.index}].membername" value="${memberLv.membername}" datatype="filed" dateFileName="membername"/>${memberLv.membername}</grid:cell>
        <grid:cell>${memberLv.price}</grid:cell> 
        <grid:cell><input type="text" name="goodsLvPriceList[${memberLv.index}].price" value="${memberLv.modprice}" datatype="filed" dateFileName="price" readonly="readonly"/></grid:cell> 
  </grid:body>
</grid:grid>
</div>

<div class="submitlist" align="center" style="display:none;">
 <table>
  <tr><td>
  <input type="button" value=" 确    定   "  class="submitBtn" />
  </td></tr>
 </table>
</div>	

</form>
<script>
$(function(){
	$("#searchBtn").bind("click",function(){
		$("#searchDiv").find("input[type='button']").trigger("click");
	});
	
});
</script>