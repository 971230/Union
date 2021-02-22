<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/commission_list.js"></script>
<form method="post" id='cloud_query_form'
	action='commission!listCommission.do'>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>
						结算人：
					</th>
					<td>
						<input type="hidden" name="commissionList.cml_user_id"
							id="cml_user_id" value="${commissionList.cml_user_id }" />
						<input type='text' readonly="readonly" class="ipttxt"
							name='commissionList.cml_user_name'  id='cml_user_name'
							value="${commissionList.cml_user_name}" style="width: 120px;" />
						<input type="button" id="refAgentBtn" value="选择" class="comBtn"
							style="width: 50px;" />
						<input type="button" value="清空" onclick="clean_agent();"
							class="comBtn" style="width: 50px;" />
					<td>
					<th>
						开始账期：
					</th>
					<td>
						<input type="text" class="text ipttxt"  name="commissionList.start_time"
							dateType="date" value="${commissionList.start_time }"
							id="start_time" />
					</td>
					<th>
						结束账期：
					</th>
					<td>
						<input type="text" class="text ipttxt" name="commissionList.end_time"
							dateType="date" value="${commissionList.end_time }" id="end_time" />
					</td>
					<th>
						是否结算佣金：
					</th>
					<td>
						<select class="ipttxt" style="width: 100px"
							name="commissionList.status" id="commissionList.status">
							<option value="" selected="selected">
								--请选择--
							</option>
							<c:forEach var="state" items="${stateList}">
								<option value="${state.key }" ${commissionList.status==
									state.key  ? ' selected="selected"' : ''}>
									${state.value}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input type="submit" style="margin-right: 10px;" class="comBtn"
							value="搜&nbsp;索" type="submit" id="submitButton" name="button">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<div class="grid">
	<form method="POST" id="commission_list_form">
		<grid:grid from="webpage" ajax="yes">
			<grid:header>
				<grid:cell sort="sn" style="width: 100px;">清单id</grid:cell>
				<grid:cell style="width: 60px;">结算金额</grid:cell>
				<grid:cell style="width: 100px;">已结算金额</grid:cell>
				<grid:cell style="width: 100px;">未结算金额</grid:cell>
				<grid:cell style="width: 80px;">账期</grid:cell>
				<grid:cell style="width: 80px;">是否结算佣金</grid:cell>
				<grid:cell sort="sn" style="width: 100px;">结算人</grid:cell>
				<grid:cell style="width: 80px;">操作</grid:cell>
			</grid:header>
			<grid:body item="commissionList">
				<grid:cell><a
						href="/shop/admin/commission!turnToDetail.do?from=list&commissionDetail.list_id=${commissionList.list_id }"
						class="auditmodify">${commissionList.list_id}</a></grid:cell>
				<grid:cell>
					<fmt:formatNumber value="${commissionList.amount}" type="currency" />
				</grid:cell>
				<grid:cell>
					<fmt:formatNumber value="${commissionList.payed_amount}"
						type="currency" />
				</grid:cell>
				<grid:cell>
					<fmt:formatNumber value="${commissionList.unpayed_amount}"
						type="currency" />
				</grid:cell>
				<grid:cell>${commissionList.monthcycle}</grid:cell>
				<grid:cell>
				   <c:if test="${commissionList.statuscount==commissionList.nopaycount&&commissionList.statuscount!=0}">未结算</c:if>
				   <c:if test="${commissionList.nopaycount==0&&commissionList.statuscount!=0}">已结算</c:if>
				   <c:if test="${commissionList.nopaycount>0&&commissionList.nopaycount<commissionList.statuscount}">部分结算</c:if>
				</grid:cell>
				<grid:cell>${commissionList.cml_user_name}</grid:cell>
				<grid:cell>
					<a href="javascript:void(0);" class="auditmodify detailEditApply" attr="${commissionList.list_id}">调价申请</a>
					<c:if test="${commissionList.nopaycount>0&&commissionList.statuscount!=0}">
					|<a href="javascript:void(0);" class="auditmodify detailEndApply" attr="${commissionList.list_id}">结算申请</a>
				    </c:if>
				</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>
<div id="refAgentDlg"></div>
<div id="payApplyDlg"></div>
<div id="balanceApplyDlg"></div>