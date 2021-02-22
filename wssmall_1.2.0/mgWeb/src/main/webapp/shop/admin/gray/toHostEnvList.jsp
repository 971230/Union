<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<form id="serchform" action="/shop/admin/grayDataSyncAction!toHostEnvList.do" method="post">
	<div class="searchformDiv">
		<table>
			<tr>
				<th>环境编码：</th>
				<td><input type="text" class="ipttxt" style="width:90px" id="hostEnvVo.env_code" name="hostEnvVo.env_code" value="${hostEnvVo.env_code}" /></td>
				<th>环境分类：</th>
				<td><input type="text" class="ipttxt" style="width:90px" id="hostEnvVo.env_type" name="hostEnvVo.env_type" value="${hostEnvVo.env_type}" /></td>
				<th>环境名称：</th>
				<td><input type="text" class="ipttxt" style="width:90px" id="hostEnvVo.env_name" name="hostEnvVo.env_name" value="${hostEnvVo.env_name}" /></td>
				<th>运行状态：</th>
				<td><select class="ipttxt" id="env_status" name="hostEnvVo.env_status">
						<option>--请选择--</option>
						<option value="00A">生产环境</option>
						<option value="00X">灰度环境</option>
					</select>
					<input type="hidden" id="lv_env_status" value="${hostEnvVo.env_status }">
				</td>
				<th></th>
				<td><input class="comBtn" type="submit" name="searchBtn"
					id="searchBtn" value="搜索" style="margin-right:10px;" /></td>
			</tr>
		</table>
		<div style="clear:both"></div>
	</div>
</form>
	<div class="grid">
		<grid:grid from="webpage" ajax="yes">
			<grid:header>

				<grid:cell>环境名称</grid:cell>
				<grid:cell>环境编码</grid:cell>
				<grid:cell>环境分类</grid:cell>
				<grid:cell>主机描述</grid:cell>
				<grid:cell>运行状态</grid:cell>
				<grid:cell>管理nginx端口</grid:cell>
				<grid:cell>关联upstream</grid:cell>
				<grid:cell>创建时间</grid:cell>
			</grid:header>
			<grid:body item="hostEnvVo">
				<grid:cell>${hostEnvVo.env_name} </grid:cell>
				<grid:cell>${hostEnvVo.env_code} </grid:cell>
				<grid:cell>${hostEnvVo.env_type }</grid:cell>
				<grid:cell>${hostEnvVo.host_info }</grid:cell>
				<grid:cell><c:if test="${hostEnvVo.env_status eq '00A'}">生产环境</c:if>
						   <c:if test="${hostEnvVo.env_status eq '00X'}">灰度环境</c:if></grid:cell>
				<grid:cell>${hostEnvVo.nginx_port }</grid:cell>
				<grid:cell>${hostEnvVo.upstream }</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd"
						d_time="${hostEnvVo.insert_time }"></html:dateformat>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</div>
<script type="text/javascript">
	$(function() {
		$("#env_status").val($("#lv_env_status").val());
	});
</script>
