<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='goods_list_form' action="javascript:void(0);">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>业务名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="goodsName" value="${goodsName}" class="searchipt" /></td>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" id="searchGoods"></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<div>
	<div class="grid">
		<form method="POST" id="goods_list_form">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell style="width: 30px;">选择</grid:cell>
					<grid:cell style="width: 100px;">业务名称</grid:cell>
					<grid:cell style="width: 100px;">销售价格</grid:cell>
				</grid:header>
				<grid:body item="goods">
					<grid:cell>
						<input type="radio" name="select_rule"
							goodsname="${goods.name}" goodsid="${goods.goods_id}"/>
					</grid:cell>
					<grid:cell>${goods.name}</grid:cell>
					<grid:cell>${goods.price}</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<div class="comBtnDiv" style="text-align: center;">
	<a style="margin-right:10px;" class="graybtn1 selectGoods"
		href="javascript:void(0);"><span>确定</span></a>
</div>