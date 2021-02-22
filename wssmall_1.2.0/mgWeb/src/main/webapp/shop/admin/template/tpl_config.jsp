<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/template/css/tpl_main.css"/>

<!-- 订单模板信息配置 -->
<div class="input">
<form action="" method="post" id="queryForm">
	<table>
		<tr>
			<th><span class="red">*</span>模板编码</th>
			<td><input type="text" id="template_code" class="ipttxt" value="${ordTplDTO.order_template_code}" />
			</td>
		</tr>
		<tr>
			<th><span class="red">*</span>模版名称</th>
			<td><input type="text" id="template_name" class="ipttxt" value="${ordTplDTO.order_template_name}"/>
			</td>
		</tr>
		<tr>
			<th><span class="red">*</span>版本号</th>
			<td><input type="text" id="template_version" class="ipttxt" value="${ordTplDTO.order_template_version}"/>
			</td>
		</tr>
		<tr>
			<th><span class="red">*</span>模板类型</th>
			<td>
				<select id="typeSel" class="ipttxt">
					<option value="0">---请选择---</option>
					<option value="1">原子类型</option>
					<option value="2">复合类型</option>
				</select>
			</td>
		</tr>
		<!-- <tr>
			<th><span class="red">*</span>适合业务类型</th>
			<td>
				<select id="business_type" class="ipttxt" >
					<option value="0">---请选择---</option>
					<option value="-1">通用业务</option>
				</select>
			</td>
		</tr>
		<tr>
			<th><span class="red">*</span>适合地区</th>
			<td>
				<select id="area" class="ipttxt" >
					<option value="0">---请选择---</option>
					<option value="1">JS</option>
				</select>
			</td>
		</tr>
		<tr>
			<th><span class="red">*</span>适合调用来源</th>
			<td>
				<select id="source_system" class="ipttxt" >
					<option value="0">---请选择---</option>
					<option value="1">原子类型</option>
				</select>
			</td>
		</tr> -->
		<!-- <tr>
			<th><span class="red">*</span>交互协议类型</th>
			<td>
				<select id="exchange_protocol" class="ipttxt" >
					<option value="0">---请选择---</option>
					<option value="1">webservice</option>
					<option value="2">http</option>
				</select>
			</td>
		</tr>
		<tr>
			<th><span class="red">*</span>子模板列表</th>
			<td>
				<select id="template_ids" class="ipttxt" >
					<option value="0">---请选择---</option>
					<option value="1">webservice</option>
					<option value="2">http</option>
				</select>
			</td>
		</tr> 
		<tr>
			<th>默认反馈内容</th>
			<td>
				<textarea id="callback_info" id="callback_info" class="ipttxt" value="${ordTplDTO.callback_info}"></textarea>
			</td>
		</tr>
		<tr>
			<th><span class="red">*</span>报文标准内容</th>
			<td>
				<textarea id="response_content" id="response_content" class="ipttxt">${ordTplDTO.basic_response_content}</textarea>
			</td>
		</tr>
		-->
		<tr>
			<td><input id="saveBtn" class="comBtn submitBtn" type="button" value="保存" name="saveBtn"></td>
			<td><input id="cloBtn" class="comBtn" type="button" value="取消" name="cloBtn"></td>
		</tr>
	</table>
	<input type="hidden" value="${ordTplDTO.flag}" id="flag">
	<input type="hidden" value="${ordTplDTO.order_template_id}" id="templateId">
	<input type="hidden" value="${ordTplDTO.order_template_type}" id="tplType">
</form>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/template/js/tpl_config.js"></script>