<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/shop/admin/goods/listExmple/js/test_list.js"></script>
<!-- demo界面，仅供参考 -->
<form method="post" id='admin_list_form' action="javascript:void(0);">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>查询条件:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="userName" value="" class="searchipt" /></td>
					<td><a class="graybtn1" style="margin-left:5px;" href="javascript:void(0);">搜索</a></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<div>
	<div class="grid" id="goods_list">
		<form method="POST">
			<grid:grid from="webpage" ajax="yes" formId="admin_list_form">
				<grid:header>
					<grid:cell style="width: 30px;">选择</grid:cell>
					<grid:cell style="width: 100px;">商品ID</grid:cell>
					<grid:cell style="width: 100px;">商品名称</grid:cell>
				</grid:header>
				<grid:body item="goods">
					<grid:cell>
						<input type="checkbox" name="select_goods" goods_id="${goods.goods_id}" goods_name="${goods.name}" />
					</grid:cell>
					<grid:cell>${goods.goods_id}</grid:cell>
					<grid:cell>${goods.name}</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<div style="text-align: center; padding-top: 50px;">
	<a class="blueBtns" id="save_list_condition" href="javascript:void(0);"><span>确认</span></a>
</div>
