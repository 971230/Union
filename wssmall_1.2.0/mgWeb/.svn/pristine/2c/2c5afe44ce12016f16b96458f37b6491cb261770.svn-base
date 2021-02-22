<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='spread_form' action="spread!listMember.do">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>推荐人姓名:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="spreadMember.name" value="${spreadMember.name}" class="searchipt" /></td>
					<th>手机号码:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="spreadMember.mobile" value="${spreadMember.mobile}" class="searchipt" /></td>
					<th>归属对象类型:</th>
					<td><select class="ipttxt" style="width: 100px"
						name="spreadMember.vested_type">
							<option value="">--请选择--</option>
							<c:forEach var="list" items="${memberTypeList}">
								<option value="${list.pkey }"
									${spreadMember.vested_type == list.pkey ? ' selected="selected" ' : ''}>${list.pname
									}</option>
							</c:forEach>
					</select></td>
					<th>开始时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="spreadMember.start_time"
						value="${spreadMember.start_time}" class="searchipt" /></td>
					<th>结束时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="spreadMember.end_time"
						value="${spreadMember.end_time}" class="searchipt" /></td>


					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" id="submitButton"
						name="ruleSubBtn"></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<div id="ruleList">
	<div class="grid">
		<form method="POST" id="spread_form">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell style="width: 100px;">推荐人姓名</grid:cell>
					<grid:cell style="width: 100px;">手机号码</grid:cell>
					<grid:cell style="width: 80px;">归属对象类型</grid:cell>
					<grid:cell style="width: 100px;">开户姓名</grid:cell>
					<grid:cell style="width: 100px;">开户银行</grid:cell>
					<grid:cell style="width: 80px;">创建时间</grid:cell>
					<grid:cell style="width: 80px;">操作</grid:cell>
				</grid:header>
				<grid:body item="mem">
					<grid:cell>${mem.name}</grid:cell>
					<grid:cell>${mem.mobile}</grid:cell>
					<grid:cell>
						<c:if test="${mem.vested_type == 'admin'}">
							系统员工
						</c:if>
						<c:if test="${mem.vested_type == 'member'}">
							系统会员
						</c:if>
						<c:if test="${mem.vested_type == 'outer'}">
							外部人员
						</c:if>
					</grid:cell>
					<grid:cell>${mem.bank_account_name}</grid:cell>
					<grid:cell>${mem.bank_name}</grid:cell>
					<grid:cell>
						<html:dateformat pattern="yyyy-MM-dd" d_time="${mem.create_time}"></html:dateformat>
					</grid:cell>
					<grid:cell>
						<a href="spread!editSpread.do?spread_id=${mem.spread_id}">修改 </a>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<script>
	$(function() {
		$("input[dataType='date']").datepicker();
	});
</script>
