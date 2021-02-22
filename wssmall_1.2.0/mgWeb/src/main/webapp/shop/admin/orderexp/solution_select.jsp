<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/core/admin/auth/checktree.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/public/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/easyui/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/orderexp/js/solution_select.js"></script>
<form method="post" id='work_folw_list_form' action="javascript:void(0);">
	<input type="hidden" name="ajax" value="yes"/>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>方案类型:&nbsp;&nbsp;</th>
					<td>
						<select class="ipttxt" id="solution_type" name="solution_type">
							<option value="">--请选择--</option>
							<option value="DEFAULT" <c:if test="${solution_type=='DEFAULT' }">selected</c:if>>DEFAULT</option>
							<option value="PLAN" <c:if test="${solution_type=='PLAN' }">selected</c:if>>PLAN</option>
							<option value="RULE" <c:if test="${solution_type=='RULE' }">selected</c:if>>RULE</option>
							<option value="URL" <c:if test="${solution_type=='URL' }">selected</c:if>>URL</option>
							<option value="SQL" <c:if test="${solution_type=='SQL' }">selected</c:if>>SQL</option>
							<option value="DOC" <c:if test="${solution_type=='DOC' }">selected</c:if>>DOC</option>	
						</select>
					</td>
					<th>方案名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 240px;"
						id="solution_name" name="solution_name" value="${solution_name}" class="searchipt" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="graybtn1" style="margin-left:5px;" id="qry_btn" href="javascript:void(0);">搜索</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<div class="grid" id="solutionlist">
     <form method="POST"  id="solutionform" >
       <grid:grid from="webpage" formId="work_folw_list_form" ajax="yes">
      <grid:header>
	    <grid:cell width="5%">选择</grid:cell>
		<grid:cell width="15%">方案类型</grid:cell>
		<grid:cell width="15%">方案名称</grid:cell>
		<grid:cell width="20%">配置值</grid:cell>
	  </grid:header>
	 <grid:body item="solution" >
	    <grid:cell>
			<input type="radio" name="solution_id" solution_id="${solution.solution_id}" solution_type="${solution.solution_type}" solution_value="${solution.solution_value}" solution_desc="${solution.solution_desc}"/>
		</grid:cell>
		<grid:cell>&nbsp;${solution.solution_type }
		</grid:cell>
		<grid:cell>&nbsp;${solution.solution_name }</grid:cell>
		<grid:cell>&nbsp;${solution.solution_value }
		</grid:cell>
	</grid:body>
  </grid:grid>
  </form>
 
</div>
<div style="text-align: center; padding-top: 50px;">
	<input type="hidden" id="catalog_id" name="catalog_id" value="${catalog_id }">
	<a class="blueBtns" id="confirm_solution" href="javascript:void(0);"><span>确认</span></a>
</div>
