<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='spread_grade_form' action="spread!listSpreadGrade.do">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>推荐人姓名:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="spreadGradeQry.name" value="${spreadGradeQry.name}" class="searchipt" /></td>
					<th>手机号码:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="spreadGradeQry.mobile" value="${spreadGradeQry.mobile}" class="searchipt" /></td>
					<th>父推荐人姓名:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="spreadGradeQry.parent_name" value="${spreadGradeQry.parent_name}" class="searchipt" /></td>
					<th>业务名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="spreadGradeQry.service_name" value="${spreadGradeQry.service_name}" class="searchipt" /></td>
					<th>归属对象类型:</th>
					<td><select class="ipttxt" style="width: 100px"
						name="spreadGradeQry.vested_type">
							<option value="">--请选择--</option>
							<c:forEach var="list" items="${memberTypeList}">
								<option value="${list.pkey }"
									${spreadGradeQry.vested_type == list.pkey ? ' selected="selected" ' : ''}>${list.pname
									}</option>
							</c:forEach>
					</select></td>
					<th>业务类型:</th>
					<td><select class="ipttxt" style="width: 100px"
						name="spreadGradeQry.service_type">
							<option value="">--请选择--</option>
							<c:forEach var="list" items="${serverTypeList}">
								<option value="${list.pkey }"
									${spreadGradeQry.service_type == list.pkey ? ' selected="selected" ' : ''}>${list.pname
									}</option>
							</c:forEach>
					</select></td>


					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" id="submitButton"
						name="ruleSubBtn"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<a style="margin-right:10px;" class="graybtn1"
			href="${ctx}/shop/admin/spread!addMember.do"><span>添加</span></a>
	</div>
</form>

<div id="ruleList">
	<div class="grid">
		<form method="POST" id="spread_grade_form">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell style="width: 100px;">推荐人姓名</grid:cell>
					<grid:cell style="width: 100px;">手机号码</grid:cell>
					<grid:cell style="width: 80px;">归属对象类型</grid:cell>
					<grid:cell style="width: 100px;">父推荐人姓名</grid:cell>
					<grid:cell style="width: 100px;">业务名称</grid:cell>
					<grid:cell style="width: 80px;">业务类型</grid:cell>
					<grid:cell style="width: 80px;">业务等级</grid:cell>
				</grid:header>
				<grid:body item="grade">
					<grid:cell>${grade.name}</grid:cell>
					<grid:cell>${grade.mobile}</grid:cell>
					<grid:cell>
						<c:if test="${grade.vested_type == 'admin'}">
							系统员工
						</c:if>
						<c:if test="${grade.vested_type == 'member'}">
							系统会员
						</c:if>
						<c:if test="${grade.vested_type == 'outer'}">
							外部人员
						</c:if>
					</grid:cell>
					<grid:cell>${grade.parent_name}</grid:cell>
					<grid:cell>${grade.service_name}</grid:cell>
					<grid:cell>
						<c:if test="${grade.service_type == 'common'}">
							通用
						</c:if>
						<c:if test="${grade.service_type == 'goods'}">
							产品
						</c:if>
					</grid:cell>
					<grid:cell>${grade.grade}</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
