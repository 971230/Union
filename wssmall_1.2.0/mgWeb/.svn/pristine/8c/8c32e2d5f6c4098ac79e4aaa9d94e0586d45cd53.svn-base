<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<script type="text/javascript" src="${ctx}/shop/admin/orderexp/js/order_exp.js"></script>
<script type="text/javascript">
loadScript("orderexp/js/solution_update.js");
</script>
<div class="input">
	<form action="javascript:void(0)" class="validate" method="post"
		name="solutiontheUpdateForm" id="solutiontheUpdateForm" >
		<input type="hidden" name="ajax" value="yes"/>
		<input type="hidden" name="solution_id" value="${returnList[0].solution_id }"/>
		<table class="form-table">
			<tr>
				<th><label class="text"><span class='red'>*</span>方案id：</label></th>
				<td>
					<input type="text" class="ipttxt"  value = "${returnList[0].solution_id}" name = "solutionInner.solution_id" id="solution_id" required="true" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>方案类型：</label></th>
				<td>
					<%-- <s:select list="{'DEFAULT','PLAN','RULE','URL','SQL','DOC'}"  listValue="solutionInner.solution_type" 
					listKey="solutionInner.solution_type" value="solutionInner.solution_type" name="solutionInner.solution_type" ></s:select> --%>
						${returnList[0].solution_type}
				</td>
			</tr>
			<c:if test="${returnList[0].solution_type eq 'RULE' || returnList[0].solution_type eq 'PLAN'}">
				<tr>
					<th><label class="text"><span class='red'>*</span>是否删除日志：</label></th>
					<td>
						<c:if test="${returnList[0].is_delete_rule_log == 'N' }">否</c:if>
						<c:if test="${returnList[0].is_delete_rule_log == 'Y' }">是</c:if>
					</td>
				</tr>
				<tr>
					<th><label class="text"><span class='red'>*</span>执行所需的Fact：</label></th>
					<td>
						${returnList[0].hander_fact }
					</td>
				</tr>
			</c:if>
			<tr>
				<th><label class="text"><span class='red'>*</span>解决方案名称：</label></th>
				<td>
					${returnList[0].solution_name }
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>配置值：</label></th>
				<td>
					<c:choose>
						<c:when test="${returnList[0].solution_type eq 'DOC' }">
							<div>
								<a class="comBtn" onclick="downloadFile('${returnList[0].solution_value }','${returnList[0].solution_name}')" href="javascript:void(0);">下载</a>
							</div>	
						</c:when>
						<c:when test="${(returnList[0].solution_type eq 'URL' || returnList[0].solution_type eq 'DEFAULT') && not empty rel_obj_id }">
							<div>
								<%--<a class="comBtn" href="${returnList[0].solution_value }">${returnList[0].solution_value }</a> --%>
								<a class="comBtn" onclick="OrderExp.redirectUrl(this);" solution_value="${returnList[0].solution_value}" rel_obj_id="${rel_obj_id }" href="javascript:void(0);">点击此处进行异常单处理</a>
							</div>	
						</c:when>
						<c:otherwise>
							<textarea rows="10" cols="50" value ="" name ="solutionInner.solution_value" id="solution_value" required="true" disabled="disabled">${returnList[0].solution_value}</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>方案描述：</label></th>
				<td>
					<textarea  rows="10" cols="50" value = "" name = "solutionInner.solution_desc" id="solution_desc" required="true" disabled="disabled">${returnList[0].solution_desc}</textarea>
				</td>
			</tr>
			<c:if test="${returnList[0].solution_type eq 'RULE' || returnList[0].solution_type eq 'PLAN' || returnList[0].solution_type eq 'SQL'}">
				<tr>
					<th><label class="text"><span class='red'>*</span>是否支持批处理：</label></th>
					<td>
						<c:if test="${returnList[0].is_batch_process == 'N' }">否</c:if>
						<c:if test="${returnList[0].is_batch_process == 'Y' }">是</c:if>
					</td>
				</tr>
			</c:if>
		</table>
		
	</form>
</div>