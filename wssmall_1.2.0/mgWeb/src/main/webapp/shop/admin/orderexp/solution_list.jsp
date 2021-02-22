<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<script type="text/javascript">
loadScript("orderexp/js/solution_list.js");
</script>
<div>
	<form action="${ctx}/shop/admin/solutionManage!list.do" method="post" id="searchSolutionNoSegForm">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	    	<th>方案类型:&nbsp;&nbsp;</th>
					<td width="150px">
						<select class="ipttxt" id="solution_type" name="solutionInner.solution_type" style="width: 110px">
							<option value="">--请选择--</option>
							<option value="DEFAULT" <c:if test="${solutionInner.solution_type=='DEFAULT' }">selected</c:if>>DEFAULT</option>
							<option value="PLAN" <c:if test="${solutionInner.solution_type=='PLAN' }">selected</c:if>>PLAN</option>
							<option value="RULE" <c:if test="${solutionInner.solution_type=='RULE' }">selected</c:if>>RULE</option>
							<option value="URL" <c:if test="${solutionInner.solution_type=='URL' }">selected</c:if>>URL</option>
							<option value="SQL" <c:if test="${solutionInner.solution_type=='SQL' }">selected</c:if>>SQL</option>
							<option value="DOC" <c:if test="${solutionInner.solution_type=='DOC' }">selected</c:if>>DOC</option>	
						</select>
					</td>
	    	      	<th>解决方案名称：</th>
	    	      	<td>
	    	          	<input type="text" class="ipttxt" name="solutionInner.solution_name" value="${solutionInner.solution_name }" />&nbsp;&nbsp;&nbsp;&nbsp; 
	    	          	<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button">
	    	      	</td>	 
				 </tr>
	  	    </tbody>
	  	    </table>
	  	 </div>
	  	 <div class="comBtnDiv">
  	     	<input class="comBtn" type="button" id="searchBtn" value="添&nbsp;加" style="margin-right:10px;"
				onclick="window.location.href='${ctx}/shop/admin/solutionManage!toAdd.do'" />
		</div>			
	</form>

	<form class="grid">
	<grid:grid from="webpage" formId="searchSolutionNoSegForm">
	    <grid:header>
		    <grid:cell width="100px">id</grid:cell>
		    <grid:cell width="300px">解决方案名称</grid:cell>
		    <grid:cell width="250px">类型</grid:cell>
		    <grid:cell width="250px">配置值</grid:cell>
			<grid:cell width="200px">操作</grid:cell>
		</grid:header>
		<grid:body item="objeto">
		    <grid:cell>${objeto.solution_id } </grid:cell>
		    <grid:cell>${objeto.solution_name}</grid:cell>
		    <grid:cell>${objeto.solution_type } </grid:cell>
		    <grid:cell>${objeto.solution_value}</grid:cell>
			<grid:cell>
				<c:if test="${objeto.solution_type != 'DEFAULT' }">
					<a href="javascript:void(0);" onclick="Solution.del(this,'${objeto.solution_id}')" >删除</a>
				</c:if>
				<a href="javascript:void(0);" onclick="window.location.href='${ctx}/shop/admin/solutionManage!toUpdate.do?newView=1&solution_id=${objeto.solution_id}'" >修改</a>
				<a href="javascript:void(0);" onclick="Solution.detail(this,'${objeto.solution_id}')" >查看</a>
				<%-- <c:if test="${objeto.solution_type eq 'DOC' && (not empty objeto.solution_value)}"><a href="${ctx}/servlet/downloadFile?solution_id=${objeto.solution_id }">下载</a></c:if>--%>
				<c:if test="${objeto.solution_type eq 'DOC' && (not empty objeto.solution_value)}"><a href="javascript:void(0);" onclick="downloadFile('${objeto.solution_value }','${objeto.solution_name}')">下载</a></c:if>
			</grid:cell>
		</grid:body>
	</grid:grid>
    </form>
	<div style="clear:both;padding-top:5px;"></div>
</div>
<div id="solutionDetailDialog"></div>

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