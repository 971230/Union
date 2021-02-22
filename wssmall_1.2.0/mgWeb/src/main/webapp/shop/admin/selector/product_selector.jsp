<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form id="serchformProd" method="post">
<div class="input">
<table class="form-table">
	<tr>
		<th>
		<select  class="ipttxt"  name="sType">
			<option value="sn" <c:if test="${sType=='sn'}">selected="selected" </c:if> >货号</option>
			<option value="name" <c:if test="${sType=='name'}">selected="selected"</c:if> >商品名称</option>
		</select>
		：</th>
		<td><input type="text" class="ipttxt"  name="keyword"  value="${keyword }"/><input type="button" style="margin-left:6px;" class="graybtn1" name="searchBtn" id="searchBtnProd" value="搜索"/></td>
	</tr>
</table>		
</div>

<div style="clear:both;padding-top:5px;"></div>

<div class="grid" id="goodslist">

<grid:grid  from="webpage"  ajax="yes" >

	<grid:header>
		<grid:cell  width="50px"><input type="checkbox" id="toggleChk"/></grid:cell> 
		<grid:cell sort="sn" width="80px">货号</grid:cell> 
		<grid:cell sort="name" width="250px">商品名称</grid:cell>
		<grid:cell sort="price">销售价格</grid:cell>
		<grid:cell sort="store">库存</grid:cell>
	</grid:header>


  <grid:body item="product" >
  
  		<grid:cell><input type="checkbox" name="productid" value="${product.product_id }" /><%--${product.product_id }--%></grid:cell>
        <grid:cell>&nbsp;${product.sn } </grid:cell>
        <grid:cell>&nbsp;<span name="goodsname">${product.name }</span></grid:cell> 
        <grid:cell>&nbsp;<fmt:formatNumber value="${product.price}" type="currency" pattern="￥.00"/> </grid:cell> 
        <grid:cell>&nbsp;${product.store} </grid:cell> 
         
  </grid:body>
  
</grid:grid>
</div>
</form>
<div style="clear:both;margin-top:5px;"></div>
<div align="center" style="margin-top:25px;margin-bottom:5px;">
	<input name="saveBtn" type="button" value="确定" class="submitBtn comBtn" />
</div>