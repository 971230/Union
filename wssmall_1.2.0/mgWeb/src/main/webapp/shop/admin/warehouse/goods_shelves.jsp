<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
.noborder {
	border-style: none;
}

.gray_form {
	background: none repeat scroll 0 0 #EEEEEE;
	border: 1px solid #CCCCCC;
	padding: 6px 10px;
}

.division {
	line-height: normal;
	margin: 5px;
	padding: 10px;
	white-space: normal;
}
-->
</style>
<div style="width: 100%; height: 500px;">
	<div class="rightDiv">
		<div class="stat_graph">
			<h3>
				<div class="graph_tab">
					<ul>
						<li id="show_click_1" class="selected">
							<span class="word">请选择上架仓库</span><span class="bg"></span>
						</li>
						<div class="clear"></div>
					</ul>
				</div>
			</h3>
		</div>
		<form class="validate" method="post" action="warehouseSeatAction!goodsShelvesNext.do" id='editWareBaseform'
		validate="true">
		<div class="gray_form">
			<ul class="list_a">
				<c:forEach items="${warehouseList}" var="warehouse">
					<li>
						<input type="radio" name="house_id" value="${warehouse.house_id}">
						${warehouse.house_name}
					</li>
				</c:forEach>
				<div class="clear"></div>
			</ul>
		</div>

		<div class="submitlist" align="center">
			<table >
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<input id="goods_shelves_next" type="submit" value=" 保存 "
							class="submitBtn" />
					</td>
				</tr>
			</table>
		</div>
		<div class="clear"></div>
	</form>
	</div>
</div>

<script type="text/javascript"> 
</script>