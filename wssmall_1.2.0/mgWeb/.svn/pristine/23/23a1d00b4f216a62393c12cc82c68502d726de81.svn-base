<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form id="serchform">
<div class="input">
<table class="form-table">
	<tr>
		<th>
		<select  class="ipttxt"  name="sType">
			<option value="sn" <c:if test="${sType=='sn'}">selected="selected" </c:if> >货号</option>
			<option value="name" <c:if test="${sType=='name'}">selected="selected"</c:if> >商品名称</option>
		</select>
		：</th>
		<td>
		<input type="text" class="ipttxt"  name="keyword"  value="${keyword }"/>
		<a href="javascript:void(0)" id="searchBtn" style="margin-left:5px;margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>		
</div>

<div style="clear:both;padding-top:5px;"></div>

<div class="grid" id="goodslist">

<grid:grid  from="webpage"  ajax="yes" >

	<grid:header>
		<grid:cell  width="50px"><input type="checkbox" id="toggleChk" onChange="selectChange()" /></grid:cell> 
		<grid:cell sort="sn" width="80px">货号</grid:cell> 
		<grid:cell sort="name" width="250px">商品名称</grid:cell>
		<grid:cell sort="cat_id"  width="100px">分类</grid:cell> 
		<grid:cell sort="price">销售价格</grid:cell>
		<grid:cell sort="store">库存</grid:cell>
 
	</grid:header>


  <grid:body item="goods" >
  
  		<grid:cell><input type="checkbox" name="goodsid" value="${goods.goods_id }" /><%--${goods.goods_id } --%></grid:cell>
        <grid:cell>&nbsp;${goods.sn } </grid:cell>
        <grid:cell>&nbsp;<span name="goodsname">${goods.name }</span></grid:cell> 
        <grid:cell>&nbsp;${goods.cat_name} </grid:cell> 
        <grid:cell>&nbsp;<fmt:formatNumber value="${goods.price}" type="currency" pattern="￥.00"/> </grid:cell> 
        <grid:cell>&nbsp;${goods.store} </grid:cell> 
         
  </grid:body>
  
</grid:grid>
</div>
<div style="clear:both;margin-top:5px;"></div>
<div align="center" style="margin-top:5px;margin-bottom:5px;">
	<input name="btn" type="button" value="确定"  style="margin-right:10px;" class="submitBtn comBtn" />
</div>


</form>
<script type="text/javascript">
function selectChange(){
	$("#goodslist").find("input[name=goodsid]").attr("checked",$("#toggleChk").is(":checked"));	
}
</script>