<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form action="${pageContext.request.contextPath}/shop/admin/ordInf!getInf.do" method="post" id="inf_form">
	<div class="searchformDiv">
		<table class="form-table">
			<tr>
				<th>订单号：</th>
				<td>
					<input type="text" class="ipttxt"  name="orderId"  value="${orderId }"/>
				</td>
				<th>规则id：</th>
				<td>
					<input type="text" class="ipttxt"  name="ruleId"  value="${ruleId }"/>
				</td>
				<th>接口编码：</th>
				<td>
					<input type="text" class="ipttxt"  name="opCode"  value="${opCode}"/>
				</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" id="get_inf_content" style="margin-right:10px;" class="graybtn1"><span>生成报文</span></a>
				</td>
			</tr>
		</table>		
	</div>
	<div>
		<table border="1" style="border-spacing: 2px;text-align: center;">
			<tbody>
				<tr>
					<th>订单系统报文</th>
					<td>
						<textarea rows="20" cols="150" >${rsp_content}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<script type="text/javascript">
	$(function(){
		$("#get_inf_content").bind("click", function(){
			$("#inf_form").submit();
		});
	});
</script>