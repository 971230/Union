<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="infoPanel" style="">
<div class="infoContent" container="true"
	style="visibility: visible; opacity: 1;">
<div class="input">
<table class="form-table">
    <c:if test="${orderApply.service_type==4}">
	    <tr>
			<th>申请单号：</th>
			<td>${orderApply.order_apply_id }</td>
		</tr>
		<tr>
			<th>服务类型：</th>
			<td>退货</td>
		</tr>
		<tr>
			<th>提交数量：</th>
			<td>${orderApply.submit_num }</td>
		</tr>
		<tr>
			<th>提交数量：</th>
			<td>
			<c:if test="${orderApply.apply_proof==1}">
			有发票
			</c:if>
			<c:if test="${orderApply.apply_proof==1}">
			有检测报告
			</c:if>
			<c:if test="${orderApply.apply_proof=='1,2'}">
			有发票,有检测报告
			</c:if>
			</td>
		</tr>
		<tr>
			<th>问题描述：</th>
			<td>${orderApply.question_desc }</td>
		</tr>
		<tr>
			<th>商品返回方式：</th>
			<td>
			  <c:if test="${orderApply.good_return_type==1}">
			  上门取件
			  </c:if>
			   <c:if test="${orderApply.good_return_type==3}">
			  快递至特百
			  </c:if>
			</td>
		</tr>
		<tr>
			<th>退款方式：</th>
			<td>
			  
			   <c:if test="${orderApply.refund_type==3}">
			  退款至银行卡
			  </c:if>
			</td>
		</tr>
    </c:if>
	
	
</table>

</div>
</div>
</div>