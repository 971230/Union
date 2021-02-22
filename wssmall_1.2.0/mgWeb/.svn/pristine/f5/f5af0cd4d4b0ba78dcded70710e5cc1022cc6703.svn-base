<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="inventorygoosaddorsaveform" >
<input type="hidden" name="action" value="${action }" />
<input type="hidden" name="house_id" value="${house_id }" />
<div class="searchformDiv">
<table class="form-table">
	<tr>
		<th>
			盘点名称：
		</th>
		<td>
			<input type="hidden" name="warhouseInventory.inventory_id" value="${warhouseInventory.inventory_id }" />
			<input type="hidden" name="warhouseInventory.house_id" value="${warhouseInventory.house_id }" />
			<input type="text" class="ipttxt" name="warhouseInventory.name" value="${warhouseInventory.name }" />
		</td>
	</tr>
</table>		
</div>

<div class="comBtnDiv">
	<a href="javascript:;" id="addgoodsBtn" style="margin-right: 10px;" class="graybtn1"><span>添加商品</span></a>
</div>

<div style="clear:both;padding-top:5px;"></div>
<div class="grid">
<div class="gridbody">
	<table>
	  <thead>
		<tr>
			<th>商品ID</th>
			<th>商品名称</th>
			<th>库存数量</th>
			<th>操作</th>
		</tr>
	   </thead>
	   <tbody id="inventory_goods_body">
	      <c:forEach items="${goodsInventory }" var="gi">
	          <tr>
	          	<td>${gi.goods_id }</td>
	          	<td>${gi.goodsName }</td>
	          	<td>
	          		<input type="hidden" name="goods_idArray" value="${gi.goods_id }" />
	          		<input type="text" name="goods_numArray" value="${gi.store }" />
	          	</td>
	          	<td>
	          		<a href="javascript:void(0);" edit="1" goodsid="${gi.goods_id }" name="goods_delete" style="margin-right: 10px;" class="graybtn1"><span>删除</span></a>
	          	</td>
	          </tr>
	      </c:forEach>
	   </tbody>	
	</table>
</div>
<div style="clear:both;padding-top:5px;"></div>
</div>

<div class="submitlist" align="center">
 <table>
 <tr>
 <td >
   <input  type="button" id="inventory_submit_btn" value=" 提 交 " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>

</form>	

<div id="inventorydialog" >
</div>

