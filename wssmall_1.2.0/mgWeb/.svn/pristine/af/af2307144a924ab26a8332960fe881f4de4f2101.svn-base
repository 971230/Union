<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='goods_search_form' action='commission!listGoods.do'>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>
						商品名称：
					</th>
					<td>
						<input type='text' name='goods.name' id='goods.name'
							value="${goods.name}" style="width: 120px;" />
					<td>
					<td>
						<input  style="margin-right: 10px;" class="comBtn"
							value="搜&nbsp;索"  id="goodsSearch" name="button">
					</td>
					<td>
						<input  style="margin-right: 10px;" class="comBtn"
							value="确&nbsp;定"  id="insureBtn" name="button">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<div class="grid">
	<form method="POST" id="commission_list_form">
		<grid:grid from="webpage" ajax="yes">
			<grid:header>
				<grid:cell sort="sn" style="width: 20px;">选择</grid:cell>
				<grid:cell sort="sn" style="width: 100px;">商品id</grid:cell>
				<grid:cell style="width: 100px;">商品名称</grid:cell>
			</grid:header>
			<grid:body item="goods">
				<grid:cell>
					<input type="radio" class="selectGoods"
						attr="${goods.goods_id}_${goods.name}" />
				</grid:cell>
				<grid:cell>${goods.goods_id}</grid:cell>
				<grid:cell>${goods.name}</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>
