<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<!-- 订单异常添加页面 订单异常按钮点击出现 -->

<!-- <div class="btnWarp"></div> -->

<div class="formWarp">
	<form action="javascript:void(0);" class="validate" method="post"
		name="order_exp_form" id="order_exp_form" enctype="multipart/form-data">
		<div id="order_tag_0" class="formGridDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="formGrid">
				<input type="hidden" name="orderExcepCollect.order_id"
					value="${orderExcepCollect.order_id}" />
				<tbody>
					<tr>
						<th>异常类型：</th>
						<td><select class="text" style="width: 100px"
							name="orderExcepCollect.exception_id">
								<c:forEach var="list" items="${exceptionList}">
									<option value="${list.exception_id }">${list.exception_name}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th>异常描述：</th>
						<td><textarea style="height: 100px;width: 98%;" class="text"
								name="orderExcepCollect.comments" dataType="string"
								value="${orderExcepCollect.comments}"></textarea></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="submitlist" align="left">
			<table style="width: 100%;">
				<tr>
					<td style="text-align: center;"><a href="javascript:void(0);" class="dobtn"
						id="order_exception_submit">确 定 </a>
				</tr>
			</table>
		</div>
	</form>
</div>
