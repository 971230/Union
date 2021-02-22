<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style type="text/css">
.icoFontlist{  
    width: 100px;  
    font-size: 12px;  
    border: 0px solid #ddd;  
    color:#5f5f5f;  
    overflow: hidden;  
    text-overflow: ellipsis;  
    white-space: nowrap;  
} 

.icoFontlist:hover  
{  
    width: 100px;  
    font-size: 12px;  
    border: 0px solid #ddd;  
    overflow: scroll;  
    text-overflow: ellipsis;  
    white-space: nowrap;  
    cursor:pointer;   
} 
.detailDiv 
{  
    display: none;   
} 
</style>
<script type="text/javascript">
	$(function() {
		 
	});
	(function($) {
		$.fn.aramsDiv = function() {
			var $this = $(this);
			$this.bind("mouseout", function() {});
			$(this).bind("mouseover", function() {});
		};
	})(jQuery); 
</script>
<div class="searchBx">
	<form action="/shop/admin/ordAuto!workList4Order.do" method="post" id="workList4Order" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
			<tbody>
				<tr>
					<th>订单号：</th>
					<td>
						<input type="text" id="order_id" name="order_id" value="${order_id}" style="width: 145px;" class="ipt_new">
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<div>
	<form class="grid">
		<grid:grid from="webpage" formId="workList4Order1111111111" action="/shop/admin/ordAuto!workList4Order.do">
			<grid:header>
				<grid:cell>&nbsp;工单类型</grid:cell>
				<grid:cell>&nbsp;工单编号</grid:cell>
				<grid:cell>&nbsp;工单状态</grid:cell>
				<grid:cell>&nbsp;派单人</grid:cell>
				<grid:cell>&nbsp;派单时间</grid:cell>
				<grid:cell>&nbsp;回单时间</grid:cell>
				<grid:cell>&nbsp;处理人</grid:cell>
				<grid:cell>&nbsp;派单备注</grid:cell>
				<grid:cell>&nbsp;工单反馈</grid:cell>
				<grid:cell>&nbsp;处理详情</grid:cell>
			</grid:header>
			<grid:body item="data">
				<grid:cell>&nbsp;${data.type} </grid:cell>
				<grid:cell>&nbsp;${data.work_order_id} </grid:cell>
				<grid:cell>&nbsp;${data.status} </grid:cell>
				<grid:cell>&nbsp;${data.order_send_username} </grid:cell>
				<grid:cell>&nbsp;${data.create_time} </grid:cell>
				<grid:cell>&nbsp;${data.update_time} </grid:cell>
				<grid:cell>&nbsp;${data.operator_name} </grid:cell>
				<grid:cell>&nbsp;${data.remark} </grid:cell>
				<grid:cell>&nbsp;${data.result_remark} </grid:cell>
				<grid:cell>
					<div class="icoFontlist" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;" title="${data.detail}" >&nbsp;&nbsp;${data.detail}</div>
				</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>
