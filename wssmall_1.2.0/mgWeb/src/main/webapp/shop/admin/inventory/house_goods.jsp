<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="inventorygoodsform" action="<%=request.getContextPath() %>/shop/admin/inventory!qryHouseGoods.do?ajax=yes">
<div class="searchformDiv">
<table class="form-table">
	<tr>
		<th>
		商品名称：
		</th>
		<td>
		<input type="hidden" name="house_id" value="${house_id }" />
		<input type="text" class="ipttxt"  name="goodsName"  value="${goodsName }"/>
		<a href="javascript:void(0)" id="inventoryGoodsSearchBtn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>		
</div>
<div style="clear:both;padding-top:5px;"></div>
<div class="grid">
<grid:grid  from="goodsPage" ajax="yes" formId="inventorygoodsform">
	<grid:header>
		<grid:cell ></grid:cell>
		<grid:cell >商品ID</grid:cell>
		<grid:cell >商品名称</grid:cell>
		<grid:cell >商品单价</grid:cell>
	</grid:header>
    <grid:body item="hi">
    	<grid:cell>
    		<input type="checkbox" name="goods_checkbox" value="${hi.goods_id}" goodsName="${hi.name}" />
    	</grid:cell>
        <grid:cell>${hi.goods_id}</grid:cell>
        <grid:cell>${hi.name}</grid:cell>
        <grid:cell>${hi.price}</grid:cell>
    </grid:body>  
</grid:grid>  
<div style="clear:both;padding-top:5px;"></div>
</div>
</form>	
<div class="comBtnDiv">
	<a href="javascript:;" id="confirmaddgoodsBtn" style="margin-right: 10px;" class="graybtn1"><span>确定</span></a>
</div>