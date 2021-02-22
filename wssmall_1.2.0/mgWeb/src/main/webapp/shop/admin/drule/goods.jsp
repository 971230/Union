<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="searchGoodsListForm">
	<div class="searchformDiv">
		<table class="form-table">
			<tr>
				<th>
					商品名称：
				</th>
				<td>
					<input type="text" class="ipttxt"  id="goods_name" name="name"  value="${name}"/>
					<a href="javascript:void(0)" id="goods_search" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
				</td>
			</tr>
		</table>		
	</div>
</form>
<form id="gridform" class="grid" >
	<grid:grid from="webpage" formId="searchGoodsListForm" ajax="yes">
		<grid:header>
			<grid:cell width="30px">
					选择
			</grid:cell>
			<grid:cell>商品名称</grid:cell>
			<grid:cell>商品编号</grid:cell>
			<grid:cell>创建时间</grid:cell>
			<grid:cell>创建人</grid:cell>
			<grid:cell>是否上下架</grid:cell>
		</grid:header>
		<grid:body item="goods">
			<grid:cell><input type="radio" name="goods_attr" goods_id="${goods.goods_id}" goods_name="${goods.name }" /></grid:cell>
			<grid:cell>${goods.name}</grid:cell>
			<grid:cell>${goods.sn } </grid:cell>
			<grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${goods.create_time}"></html:dateformat></grid:cell>
			<grid:cell>${goods.apply_username}</grid:cell>
			<grid:cell><c:if test="${goods.market_enable == 1}">上架</c:if><c:if test="${goods.market_enable != 1}">下架</c:if></grid:cell>
		</grid:body>
	</grid:grid>
</form>