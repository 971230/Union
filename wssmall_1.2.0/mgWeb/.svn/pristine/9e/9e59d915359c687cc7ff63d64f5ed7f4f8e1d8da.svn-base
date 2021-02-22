<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='rule_list_form' action="javascript:void(0);">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>规则名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="rule.rule_name" value="${rule.rule_name}" class="searchipt" /></td>
					<th>规则类型:</th>
					<td><select class="ipttxt" style="width: 100px"
						name="rule.rule_type" disabled="disabled">
							<c:forEach var="list" items="${ruleTypeList}">
								<option value="${list.pkey }"
									${rule.rule_type == list.pkey ? ' selected="selected" ' : ''}>${list.pname
									}</option>
							</c:forEach>
					</select></td>
					<th>开始时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="rule.rule_time_begin"
						value="${rule.rule_time_begin}" class="searchipt" /></td>
					<th>结束时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="rule.rule_time_end"
						value="${rule.rule_time_end}" class="searchipt" /></td>


					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" id="submitButton"
						name="ruleSubBtn"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
			<a style="margin-right:10px;" class="graybtn1"
				href="javascript:void(0);" id="sltRule"><span>确认</span></a>
	</div>
</form>

<div id="ruleList">
	<div class="grid">
		<form method="POST" id="rule_list_form">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell style="width: 30px;">选择</grid:cell>
					<grid:cell style="width: 100px;">规则名称</grid:cell>
					<grid:cell style="width: 100px;">规则编码</grid:cell>
					<grid:cell style="width: 60px;">规则处理类</grid:cell>
					<grid:cell style="width: 80px;">规则类型</grid:cell>
					<grid:cell style="width: 100px;">规则生效时间</grid:cell>
					<grid:cell style="width: 100px;">规则失效时间</grid:cell>
					<grid:cell style="width: 80px;">创建时间</grid:cell>
				</grid:header>
				<grid:body item="rule">
					<grid:cell>
						<input type="checkbox" name="select_rule"
							rule_name="${rule.rule_name}" rule_type="${rule.rule_type}"
							rule_code="${rule.rule_code}" rule_id="${rule.rule_id}"/>
					</grid:cell>
					<grid:cell>${rule.rule_name}</grid:cell>
					<grid:cell>${rule.rule_code}</grid:cell>
					<grid:cell>${rule.rule_java}</grid:cell>
					<grid:cell>
						<c:if test="${rule.rule_type == 'accept'}">
							受理类规则
						</c:if>
						<c:if test="${rule.rule_type == 'delvery'}">
							发货类规则
						</c:if>
						<c:if test="${rule.rule_type == 'pay'}">
							支付类规则
						</c:if>
						<c:if test="${rule.rule_type == 'insure'}">
							确认类规则
						</c:if>
					</grid:cell>
					<grid:cell>
						<html:dateformat pattern="yyyy-MM-dd"
							d_time="${rule.rule_time_begin}"></html:dateformat>
					</grid:cell>
					<grid:cell>
						<html:dateformat pattern="yyyy-MM-dd"
							d_time="${rule.rule_time_end}"></html:dateformat>
					</grid:cell>
					<grid:cell>
						<html:dateformat pattern="yyyy-MM-dd" d_time="${rule.create_time}"></html:dateformat>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<div id="del_rule_div"></div>
<script>
	$(function() {
		$("input[dataType='date']").datepicker();
	});
</script>
