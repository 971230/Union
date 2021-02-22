<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>过滤校验列表</title>
</head>
<body>
	<form action="/core/admin/ecc/checkFilter!checkFilterEdit.do" id="filter_edit_fm" name="filter_edit_fm" method="post">
	<div class="new_right">
		<div style="display: block;" class="goods_input">
			<div class="tab-bar" style="position: relative;">
				<ul class="tab">
					<li tabid="0" class="active">基本信息</li>
				</ul>
			</div>
			<div class="tab-page">
				<div class="searchformDiv">
					<table cellspacing="0" cellpadding="0" border="0" class="form-table" style="width: 100%; float: left" id='base_table'>
						<tbody>
							<tr>
								<th>业务类型：</th>
								<td><input name="params.biz_name" value="${filterView.biz_name }" disabled="disabled" class="top_up_ipt"/></td>
							</tr>
							<tr>
								<th>序号：</th>
								<td><input name="params.opt_index" value="${filterView.opt_index }" disabled="disabled" class="top_up_ipt"/></td>
							</tr>
							<tr>
								<th>条件名称：</th>
								<td><input name="params.attr_name" value="${filterView.attr_name }" disabled="disabled" class="top_up_ipt"/></td>
							</tr>
							<tr>
								<th>条件编码：</th>
								<td><input name="params.attr_code" value="${filterView.attr_code }" disabled="disabled" class="top_up_ipt"/></td>
							</tr>
							<tr>
								<th><span class="red">*</span>条件值：</th>
								<td><input name="params.z_value" value="${filterView.z_value }" required="true" class="top_up_ipt"/></td>
							</tr>
							<tr>
								<th>条件值说明：</th>
								<td><input name="params.z_cvalue" value="${filterView.z_cvalue }" class="top_up_ipt"/></td>
							</tr>
							<tr>
								<th><span class="red">*</span>条件关系：</th>
								<td>
									<select class="ipttxt" name="params.pre_log" style="width: 155px">
										<c:if test="${filterView.pre_log=='AND' }">
											<option value="0" selected="selected">AND</option>
											<option value="1">OR</option>
											<option value="2">无</option>
										</c:if>
										<c:if test="${filterView.pre_log=='OR' }">
											<option value="0">AND</option>
											<option value="1" selected="selected">OR</option>
											<option value="2">无</option>
										</c:if>
										<c:if test="${filterView.pre_log !='OR' && filterView.pre_log != 'AND' }">
											<option value="0">AND</option>
											<option value="1">OR</option>
											<option value="2" selected="selected">无</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr>
								<th><span class="red">*</span>状态：</th>
								<td>
									<select class="ipttxt" name="params.status" style="width: 155px">
										<c:if test="${filterView.status=='00A' }">
											<option value="00A" selected="selected">有效</option>
											<option value="00X">无效</option>
										</c:if>
										<c:if test="${filterView.status=='00X' }">
											<option value="00A">有效</option>
											<option value="00X" selected="selected">无效</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<input name="params.required_id" value="${filterView.required_id }" type="hidden"/>
									<input type="submit" style="margin-right:10px;" class="comBtn" value="确&nbsp;定"  id="buttonOK" name="buttonOK">
									<input type="submit" style="margin-right:10px;" class="comBtn" value="返&nbsp;回"  id="buttonReturn" name="buttonReturn" onclick="buttonReturn();">
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	</form>
<br />
<br />
<br />
<script type="text/javascript">
	function buttonReturn(){
		document.filter_edit_fm.action = "/core/admin/ecc/checkFilter!showCheckFilterList.do";
		document.filter_edit_fm.submit();
	}
</script>
</body>
</html>