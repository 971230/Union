<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="rule_audit_form"
	action="${pageContext.request.contextPath}/shop/admin/rule!ruleAuditList.do">
	<div class="searchformDiv">
		<table class="form-table">
			<tr>
				<th>规则名称：</th>
				<td><input type="text" class="ipttxt"
					name="ruleConfigAudit.rule_name"
					value="${ruleConfigAudit.rule_name }" /></td>
				<th>规则编码：</th>
				<td><input type="text" class="ipttxt"
					name="ruleConfigAudit.rule_code"
					value="${ruleConfigAudit.rule_code }" />
				
				<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" id="rule_config_searchBtn"
						name="ruleSubBtn"></td>
			</tr>
		</table>
	</div>
	<div id="auditList">
		<div class="grid">
			<form method="POST" id="rule_audit_form">
				<grid:grid from="webpage" ajax="yes">
					<grid:header>
						<grid:cell>规则名称</grid:cell>
						<grid:cell>规则编码</grid:cell>
						<grid:cell>资源文件类型</grid:cell>
						<grid:cell>实现方式</grid:cell>
						<grid:cell>生效时间</grid:cell>
						<grid:cell>失效时间</grid:cell>
						<grid:cell>状态</grid:cell>
						<grid:cell>描述</grid:cell>
						<grid:cell>创建时间</grid:cell>
						<grid:cell>操作</grid:cell>
					</grid:header>
					<grid:body item="ruleAudit">
						<grid:cell>${ruleAudit.rule_name}</grid:cell>
						<grid:cell>${ruleAudit.rule_code}</grid:cell>
						<grid:cell>${ruleAudit.resource_type=='DRF'?'规则流':'未知类型'}</grid:cell>
						<grid:cell>${ruleAudit.impl_type=='CD'?'编码实现':'界面配置'}</grid:cell>
						<grid:cell>${ruleAudit.eff_date}</grid:cell>
						<grid:cell>${ruleAudit.exp_date}</grid:cell>
						<grid:cell>
							<c:if test="${ruleAudit.status_cd=='00N' }">新建</c:if>
							<c:if test="${ruleAudit.status_cd=='00X' }">无效</c:if>
							<c:if test="${ruleAudit.status_cd=='00A' }">有效</c:if>
							<c:if test="${ruleAudit.status_cd=='00M' }">审核中</c:if>
							<c:if test="${ruleAudit.status_cd=='00H' }">历史数据</c:if>
						</grid:cell>
						<grid:cell>${ruleAudit.rule_desc}</grid:cell>
						<grid:cell>${ruleAudit.create_date}</grid:cell>
						<grid:cell>
							<a href="javascript:void(0);" class="audit_rule" rule_id="${ruleAudit.rule_id}">审核</a>
						</grid:cell>
					</grid:body>
				</grid:grid>
			</form>
		</div>
	</div>
</form>
<div id="rule_audit_div"></div>
<script type="text/javascript">
	$(function() {
		$(".audit_rule").bind("click",function(){
			if(confirm("确认提交审核？")){
				var rule_id = $(this).attr("rule_id");
				var url = ctx + "/shop/admin/rule!ruleAudit.do?ajax=yes&rule_id="+rule_id;
				Cmp.ajaxSubmit('rule_audit_div', '', url, {},RuleAudit.jsonBack, 'json');
			}
		});
	});
	
	var RuleAudit = {
		jsonBack : function(responseText) {
			if (responseText.result == 1) {
				alert("操作成功");
				$("#rule_config_searchBtn").trigger("click");
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}
		}
	}
</script>
