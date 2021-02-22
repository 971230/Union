<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/orderexp/js/catalog_solution.js"></script>
<script type="text/javascript">
	//下载文件
	function downloadFile(solution_value, solution_name){
		debugger;
		var form = document.createElement('form');
		var file_name_input = document.createElement('input');
		var solution_name_input = document.createElement('input');
		
		file_name_input.type = "hidden";
		file_name_input.name = "fileName";
		file_name_input.value = solution_value;
		form.appendChild(file_name_input);
		
		solution_name_input.type = "hidden";
		solution_name_input.name = "solutionName";
		solution_name_input.value = solution_name;
		form.appendChild(solution_name_input);
		
		form.action = ctx + "/servlet/downloadFile";
		form.method = "post";
		document.body.appendChild(form);
		form.submit();
	}
</script>
<div class="msg_table grid" id="catalogSolutionList">
	<input type="hidden" name="catalog_id" value="${catalog_id }">
	    <table width="98%" border="0" cellspacing="0" cellpadding="0">
	    	<tbody>
		     	<tr align="center">
		      		<th style="text-align:center;">解决方案名称</th>
		      		<th style="width:20px;text-align:center;">操作</th>
		    	</tr>
		     	<c:forEach items="${catalogSolutionList}" var="solution">
			        <tr align="center">
			        	<input type="hidden" name="solution_value" value="${solution.solution_value }">
			            <td name="solution_name">${solution.solution_name }</td>
			      		<td>
			      			<c:if test="${solution.solution_type=='DOC' }">
			      				<a href="javascript:void(0);" onclick="downloadFile('${solution.solution_value }','${solution.solution_name}')">下载</a>
			      			</c:if>
				      		<a solution_id="${solution.solution_id }" title="删除解决方案" href="javascript:void(0);" name="deleteCatalogSolution">删除</a>
			      		</td>
			    	</tr>
				</c:forEach>
			</tbody>
	  	</table>
</div>
