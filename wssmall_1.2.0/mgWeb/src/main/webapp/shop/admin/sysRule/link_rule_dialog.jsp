<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/shop/admin/css/order.css"
	type="text/css">
<div class="division">
	<form class="validate" method="post" action="" id='goods_apply_form'
		validate="true">
		<table width="100%">
			<tbody>
				<input type="hidden" value="${serviceOffer.service_id}" id="hidden_service_id"/>
				<tr>
					<th>服务编码：</th>
					<input type="hidden" name="serviceOffer.service_code" value="${serviceOffer.service_code}" />
					<td>${serviceOffer.service_code}</td>
				</tr>
				<tr>
					<th>服务名称：</th>
					<input type="hidden" name="serviceOffer.service_offer_name"
						value="${serviceOffer.service_offer_name}" />
					<td>${serviceOffer.service_offer_name}</td>
				</tr>
				<tr>
					<th>关联规则：</th>
					<td colspan="3">
						<div class="partnerInfo">
							<table cellspacing="0" cellpadding="0" border="0" width="100%"
								class="">
								<thead>
									<tr>
										<th>规则名称</th>
										<th>规则类型</th>
										<th>规则编码</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="rule_tr">
									<c:forEach items="${ruleList}" var="rule">
										<tr>
											<td>${rule.rule_name}</td>
											<c:if test="${rule.rule_type == 'accept'}">
												<td>受理类规则</td>
											</c:if>
											<c:if test="${rule.rule_type == 'delvery'}">
												<td>发货类规则</td>
											</c:if>
											<c:if test="${rule.rule_type == 'pay'}">
												<td>支付类规则</td>
											</c:if>
											<c:if test="${rule.rule_type == 'insure'}">
												<td>确认类规则</td>
											</c:if>
											<td>${rule.rule_code}</td>
											<td><a href="javascript:void(0);" class="delLinkRule">删除 </a></td>
											<input type="hidden" value="${rule.rule_id}" name="rule_ids" class='hidden_rule_id'/>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="submitlist" align="center">
			<table>
				<tr>
					<td><input type="button" value="添加规则" name="addRules"
						 class="submitBtn addRules" /></td>
					<td><input type="button" value="确认" name="insureRules"
						 class="submitBtn insureRules" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
