<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/commission_list.js"></script>
<c:if test="${from != 'list'}">
	<form method="post" id='cloud_query_form'
		action='commission!turnToDetail.do'>
		<div class="searchformDiv">
			<table  cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							清单ID：
						</th>
						<td>
							<input type='text' 
								name='commissionDetail.list_id' class="ipttxt" 
								value="${commissionDetail.list_id}" style="width: 100px;" />
						</td>
						<th>
							结算人：
						</th>
						<td>
							<input type="hidden" class="ipttxt" name="commissionDetail.cml_user_id"
								id="cml_user_id" value="${commissionDetail.cml_user_id }" />
							<input type='text' class="ipttxt" readonly="readonly"
								name='commissionDetail.cml_user_name' id='cml_user_name'
								value="${commissionDetail.cml_user_name}" style="width: 100px;" />
							<input type="button" id="refAgentBtn" value="选择" class="comBtn"
								style="width: 50px;" />
							<input type="button" value="清空" onclick="clean_agent();"
								class="comBtn" style="width: 50px;" />
						<td>
						<th>
							业务名称：
						</th>
						<td>
							<input type='text' 
								name='commissionDetail.service_name' class="ipttxt" 
								value="${commissionDetail.service_name}" style="width: 100px;" />
						<td>
						<th>
							商品名称：
						</th>
						<td>
							<%-- <input type="hidden" name="commissionDetail.goods_id"
								id="goods_id" value="${commissionDetail.goods_id }" /> --%>
							<input type='text' 
								name='commissionDetail.goods_name' class="ipttxt" id='goods_name'
								value="${commissionDetail.goods_name}" style="width: 100px;" />
							<!-- <input type="button" id="refGoodsBtn" value="选择" class="comBtn"
								style="width: 50px;" />
							<input type="button" value="清空" onclick="clean_goods();"
								class="comBtn" style="width: 50px;" /> -->
						<td>
					</tr>
					<tr>
						<th>
							账期：
						</th>
						<td>
							<input type="text" class="text ipttxt"  name="commissionDetail.monthcycle"
								dateType="date" value="${commissionDetail.monthcycle }" style="width: 100px;"
								id="monthcycle" />
						</td>
						<th style="width: 100px;">
							是否结算佣金：
						</th>
						<td>
							<select class="ipttxt" style="width: 100px"
								name="commissionDetail.status" id="commissionDetail.status">
								<option value="" selected="selected">
									--请选择--
								</option>
								<c:forEach var="state" items="${stateList}">
									<option value="${state.key }" ${commissionDetail.status==
										state.key  ? ' selected="selected"' : ''}>
										${state.value}
									</option>
								</c:forEach>
							</select>
						</td>
						<td colspan="3">
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" type="submit" id="submitButton" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</c:if>
<div class="grid">
	<form method="POST" id="commission_list_form">
		<grid:grid from="webpage" ajax="yes">
			<grid:header>
				<grid:cell sort="sn" style="width: 100px;">清单id</grid:cell>
				<grid:cell style="width: 100px;">业务名称</grid:cell>
				<grid:cell style="width: 100px;">商品名称</grid:cell>
				<grid:cell style="width: 60px;">佣金金额</grid:cell>
				<grid:cell style="width: 70px;">已结算金额</grid:cell>
				<grid:cell style="width: 100px;">账期</grid:cell>
				<grid:cell style="width: 70px;">账期未结算金额</grid:cell>
				<grid:cell style="width: 100px;">是否结算佣金</grid:cell>
				<grid:cell style="width: 100px;">结算人</grid:cell>
				
			</grid:header>
			<grid:body item="commissionDetail">
				<grid:cell>${commissionDetail.list_id}</grid:cell>
				<grid:cell>${commissionDetail.service_name}</grid:cell>
				<grid:cell>${commissionDetail.goods_name}</grid:cell>
				<grid:cell>
				<a href="/shop/admin/commission!toItemDetail.do?commissionDetail.list_id=${commissionDetail.list_id}&commissionDetail.goods_id=${commissionDetail.goods_id}">
					<fmt:formatNumber value="${commissionDetail.sum_amount}" type="currency" />
				</a>
				</grid:cell>
				<grid:cell>${commissionDetail.payed_amount}</grid:cell>
				<grid:cell>${commissionDetail.monthcycle}</grid:cell>
				<grid:cell>${commissionDetail.nopay_amount}</grid:cell>
				<grid:cell>
				   <c:if test="${commissionDetail.payed_amount==0}">未结算</c:if>
				   <c:if test="${commissionDetail.nopay_amount==0&&commissionDetail.statuscount!=0}">已结算</c:if>
				   <c:if test="${commissionDetail.nopay_amount>0&&commissionDetail.payed_amount>0}">部分结算</c:if>
				</grid:cell>
				<grid:cell>${commissionDetail.cml_user_name}</grid:cell>
				
			</grid:body>
		</grid:grid>
	</form>
</div>
<div id="refAgentDlg"></div>
<div id="refGoodsDlg"></div>
