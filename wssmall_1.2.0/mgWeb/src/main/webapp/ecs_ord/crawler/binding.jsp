<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/js/binding.js"></script>
<%@ page import = "net.sf.json.*" %>

<form id="do_bindingForm" method="post">
<div class="searchformDiv">
	<table>
		<input type="hidden" value="<%=request.getAttribute("username") %>" id="username"/>
		<tr>
		<th>工号：</th>
		<td><input type="text" id="gonghao" name="order_gonghao" value="<%= request.getAttribute("userid") %>" class="ipttxt" readonly="readonly"></td>
		<th>CB工号：</th>
		<td><input type="text" name="ess_emp_id" class="ipttxt" id="cb_gonghao"></td>
		</tr>
		<tr>
		<th>省份：</th>
		<td>
			<select name="province" class="ipt_new" style="width:150px;" id="province">
				<option value ="36">浙江</option>
			</select>
		</td>
		<th>城市：</th>
		<td>
			<select name="city" class="ipttxt" style="width:150px;" id="city">
				<option value="nochoice">请选择</option>
			</select>
		</td>
		</tr>
		<tr>
			<th></th>
			<td>
			<input type="button" value="绑&nbsp定" class="comBtn" id="do_binding_btn"/>
			<input type="button" value="返&nbsp回" class="comBtn" id="goback_btn"/>
			<span id="warning" style="color:red;"></span>
			</td>
		</tr>
	</table>
	</div>
</form>
<form id="goback"></form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchOperator_RelForm" action="${listFormActionVal}" >
			<grid:header>
				<grid:cell width="30px">工号</grid:cell>
				<grid:cell width="110px">CB工号</grid:cell>
				<grid:cell width="150px">省份</grid:cell>
				<grid:cell width="100px">城市</grid:cell>
				<grid:cell width="50px">工号解绑</grid:cell>
			</grid:header>
			<grid:body item="queue">
				<grid:cell>
					<span class="gonghao">${queue.order_gonghao}</span>
				</grid:cell>
				<grid:cell>
					${queue.ess_emp_id}
				</grid:cell>
				<grid:cell>
					<%-- ${queue.province} --%>
					广东
				</grid:cell>
				<grid:cell>				
					<span class="city_name">${queue.city }</span>
				</grid:cell>
				<grid:cell>
					<a href="javascript:void(0);" class="unbinding_gonghao">工号解绑</a>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
