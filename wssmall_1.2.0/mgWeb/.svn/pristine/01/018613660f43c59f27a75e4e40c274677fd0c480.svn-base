<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/orderApplyList.js"></script>
<div ><form action="orderApply!list.do?service_type=${service_type}" id="" method="post">
<input type="hidden" id="service_type" value="${service_type}"/>
<div class="searchformDiv">
	<table class="form-table">
		<tbody><tr>
			<th>申请单号：</th>
			<td>
				<input size="15" type="text"  name="order_apply_id" id="order_apply_id"
								value="${order_apply_id}"
								class="ipttxt"
								maxlength="60" />  
			</td>		
			<th>订单号：</th>
			<td>		
				<input size="15" type="text"  name="a_order_item_id" id="a_order_item_id"
								value="${a_order_item_id}"
								class="ipttxt"
								/> 
			</td>
			
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <input class="comBtn" type="submit" name="searchBtn" id="" value="搜索" style="margin-right: 5px;">
			</td>
		</tr>
	</tbody></table>
	
</div>
</form>
</div>
<div class="grid">
	<form method="POST">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell sort="order_apply_id">申请单号</grid:cell>
				<grid:cell sort="a_order_item_id">订单号</grid:cell>
				<grid:cell sort="good_name">商品名称</grid:cell>
				<grid:cell sort="apply_state">状态</grid:cell>
				<grid:cell sort="member_name">申请人</grid:cell>
				<grid:cell sort="create_time">申请时间</grid:cell>
				<grid:cell >操作</grid:cell>
			</grid:header>
			<grid:body item="orderApply">
				<grid:cell>${orderApply.order_apply_id } </grid:cell>
				<grid:cell>${orderApply.a_order_item_id} </grid:cell>
				<grid:cell>${orderApply.good_name} </grid:cell>
				
				<grid:cell> 
				 <c:if test="${orderApply.apply_state == 0 }">未审核</c:if>
				 <c:if test="${orderApply.apply_state == 1 }">审核通过</c:if>
				 <c:if test="${orderApply.apply_state == 2 }">已拒绝</c:if>
				 <c:if test="${orderApply.apply_state == 3 }">已确认退货</c:if>
				 <c:if test="${orderApply.apply_state == 4 }">已确认退货</c:if>
				 <c:if test="${orderApply.apply_state == 5 }">已确认退费</c:if>
				 <c:if test="${orderApply.apply_state == 6 }">已确认退费</c:if>
				 <c:if test="${orderApply.apply_state == 7 }">已确认换货</c:if>
				 <c:if test="${orderApply.apply_state == 8 }">已确认换货</c:if>
				</grid:cell>
				<grid:cell>${orderApply.memeber_name} </grid:cell>
				<grid:cell>${orderApply.create_time} </grid:cell>
				<grid:cell><a href="javascript:void(0);" staff_no="" class="showSupplierOrder">查看详情</a>&nbsp;&nbsp;
				<c:if test="${orderApply.apply_state==0}">
				<c:if test="${isAdminUser==1}">
				   |&nbsp;&nbsp;<a  class="auditName" order_apply_id="${orderApply.order_apply_id }" good_name="${orderApply.good_name}">审批</a>
				</c:if>
				</c:if>
				</grid:cell>
				
			</grid:body>
		</grid:grid>
	</form>
</div>

<div id="orderApply_dialog"></div>