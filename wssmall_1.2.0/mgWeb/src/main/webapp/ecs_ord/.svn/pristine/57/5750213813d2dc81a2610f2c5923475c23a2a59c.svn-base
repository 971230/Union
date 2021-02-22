<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_g">
    <tbody>
		<tr>
		   <th>商品名称</th>
		   <th>货品类型</th>
		   <th>机型编码</th>
		   <th>品牌名称</th>
		   <th>型号名称</th>
		   <th>颜色</th>
		   <th>数量</th>
		</tr>
	<c:forEach var="item" items="${list }">
		<tr>
		   <td>${item.goods_name}</td>
		   <td>${item.goods_type_name}</td>
		   <td>${item.machine_type_name}</td>
		   <td>${item.resources_brand_name}</td>
		   <td>${item.resources_model_name}</td>
		   <td>${item.resources_color}</td>
		   <td>${item.menge}</td>
		</tr>
	</c:forEach>
	<c:if test="${orderTree.orderExtBusiRequest.flow_trace_id!='C'||query_type=='view' }">
		<tr>
			<td colspan="7"><input class="graybtn1" value="串号明细导出" type="button" name="TermDetailExp" /></td>
		</tr>
	</c:if>
	</tbody>
</table>
<form id="exportTer" method="post">
<input type="hidden" name="order_id" value="${order_id }">
<input type="hidden" name="is_his_order" value="${order_is_his }">
</form>

<script type="text/javascript">
$(function(){
	$("input[name=TermDetailExp]").bind("click",function(){
		var url = ctx+"/shop/admin/ordAuto!expTerminalNum.do?ajax=yes&excel=yes";
		$("#exportTer").attr("action",url);
		$("#exportTer").submit();
	});
});
</script>