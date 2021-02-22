<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form id="goodsserchform" method="post">
<input type="hidden" name="member_lv_id" value="${member_lv_id }"/>
<div class="input">
<table class="form-table">
	<tr>
		<th>
		商品名称：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="goodsName"  value="${goodsName }"/>
		<a href="javascript:void(0)" id="goodssearchBtn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>		
</div>

<div style="clear:both;padding-top:5px;"></div>

<div class="grid" id="goodslist">

<grid:grid  from="goodsPage" ajax="yes" formId="goodsserchform">

	<grid:header>
		<grid:cell  width="50px"><input type="checkbox" id="toggleChk" onChange="selectChange()" /></grid:cell> 
		<grid:cell sort="sn" width="89px">货号</grid:cell> 
		<grid:cell sort="name" width="250px">商品名称</grid:cell>
		<grid:cell sort="cat_id"  width="100px">分类</grid:cell> 
		<grid:cell sort="price">销售价格</grid:cell>
		<grid:cell sort="store">库存</grid:cell>
	</grid:header>


  <grid:body item="goods" >
  
  		<grid:cell><input type="checkbox" id="product_id" name="product_id" value="${goods.productid }" 
  						goodssn="${goods.sn }" goodsName="${goods.name }" /></grid:cell>
        <grid:cell>&nbsp;${goods.sn } </grid:cell>
        <grid:cell>&nbsp;<span name="goodsname">${goods.name }</span></grid:cell> 
        <grid:cell>&nbsp;${goods.cat_name} </grid:cell> 
        <grid:cell>&nbsp;<fmt:formatNumber value="${goods.price}" type="currency" pattern="￥.00"/> </grid:cell> 
        <grid:cell>&nbsp;${goods.store} </grid:cell> 
         
  </grid:body>
  
</grid:grid>
</div>

<div class="submitlist" align="center">
 <table>
 <tr><td >
  <input name="goods_confirm_btn" type="button" value=" 确    定   " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	

</form>
<script type="text/javascript">
function selectChange(){
	$("#goodslist").find("input[name=product_id]").attr("checked",$("#toggleChk").is(":checked"));	
}
</script>