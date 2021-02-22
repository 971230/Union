<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form id="appgoodsserchform" method="post">
<div class="input">
<form id="serchform">
<table class="form-table">
	<tr>
		<th>
		商品名称：
		</th>
		<td>
		<input type="text" class="ipttxt" id="s_goodsName" name="goodsName"  value="${goodsName }"/>
		<a href="javascript:void(0)" id="goodssearchBtn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>		
</div>
</form>
<div style="clear:both;padding-top:5px;"></div>

<div class="grid" id="goodslist">

<grid:grid  from="webpage" ajax="yes" formId="appgoodsserchform">

	<grid:header>
		<grid:cell  width="50px"><input type="checkbox" id="toggleChk" onChange="selectChange()" /></grid:cell> 
		<grid:cell  width="89px">商品编号</grid:cell> 
		<grid:cell  width="250px">商品名称</grid:cell>
		<grid:cell >销售价格</grid:cell>
		<grid:cell >库存</grid:cell>
	</grid:header>


  <grid:body item="goods" >
  
  		<grid:cell><input type="checkbox" name="product_ids" value="${goods.productid }" productid="${goods.productid }"
  						goodssn="${goods.sn }" goodsName="${goods.name }" goodsId="${goods.goods_id }" price="${goods.price }" store="${goods.store}"/></grid:cell>
        <grid:cell>&nbsp;${goods.sn } </grid:cell>
        <grid:cell>&nbsp;<span name="goodsname">${goods.name }</span></grid:cell> 
        <grid:cell>&nbsp;<fmt:formatNumber value="${goods.price}" type="currency" pattern="￥.00"/> </grid:cell> 
        <grid:cell>&nbsp;${goods.store} </grid:cell> 
         
  </grid:body>
  
</grid:grid>
</div>

<div class="submitlist" align="center">
 <table>
 <tr><td >
  <input id="app_goods_confirm_btn" type="button" value=" 确    定   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	

</form>
