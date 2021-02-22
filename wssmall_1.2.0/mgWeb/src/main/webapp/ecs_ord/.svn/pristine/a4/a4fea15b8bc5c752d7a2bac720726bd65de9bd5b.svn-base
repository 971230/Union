<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form action="${pageContext.request.contextPath}/shop/admin/ordInf!compareInf.do" method="post" id="inf_form">
	<div>
		<table border="1" style="border-spacing: 2px;text-align: center;">
			<tbody>
				<tr>
					<th>订单号</th>
					<td>
						<input name="orderId" value="${orderId}" />
					</td>
				</tr>
				<tr>
					<th>接口编码</th>
					<td>
						<input name="opCode" value="${opCode}" />
					</td>
				</tr>
				<tr>
					<th>订单系统报文</th>
					<td>
						<textarea rows="20" cols="150" name="new_xml" >${new_xml}</textarea>
					</td>
				</tr>
				<tr>
					<th>现网系统报文</th>
					<td>
						<textarea rows="20" cols="150" name="old_xml" >${old_xml}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div style="text-align: center;">
		<a href="javascript:void(0)" id="compare_inf" style="margin-right:10px;" class="graybtn1"><span>对比报文</span></a>
	</div>
</form>
<script type="text/javascript">
	$(function(){
		$("#compare_inf").bind("click", function(){
			$("#inf_form").submit();
		});
	});
</script>