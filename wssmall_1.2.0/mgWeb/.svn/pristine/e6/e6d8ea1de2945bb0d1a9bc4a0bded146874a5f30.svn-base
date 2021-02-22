<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
loadScript("js/guiji_co_queue_list.js");
</script>

<div >
	<form  action="${listFormActionVal}"  id="searchOrderGjCoQueueForm" method="post" >
		<div class="searchformDiv">
	    	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	    		<tbody>
		    	    <tr>
		    	    	<th>
							外部订单号：
						</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" id="ext_order_id" name="params.ext_order_id" value="${params.ext_order_id }" />
						</td>
						<th>
							服务编码：
						</th>
						<td style="width:180px;">
							<select name="params.service_code" id="service_code">
								<option value="CO_GUIJI" <c:if test="${params.service_code=='CO_GUIJI'}">selected</c:if>>CO_GUIJI</option>
								<option value="CO_GUIJI_NEW" <c:if test="${params.service_code=='CO_GUIJI_NEW'}">selected</c:if>>CO_GUIJI_NEW</option>
							</select>
						</td>
		    	      	<td>
		    	      		<a href="javascript:void(0);" style="margin-left:20px;" id="searchQueueBtn" class="graybtn1"><span>搜  &nbsp 索</span></a>
		  					<a href="javascript:void(0);" style="margin-left:10px;" id="doGuijiBtn" class="graybtn1"><span>归 &nbsp 集</span></a>
		  					<a href="javascript:void(0);" style="margin-left:10px;" id="openOrderSynDialog" class="graybtn1"><span>暂 停/启 动</span></a>
		    	      	</td>
					</tr>
	  	    	</tbody>
	  	    </table>
    	</div>
	</form>

	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchOrderGjCoQueueForm" action="${listFormActionVal}" >
			<grid:header>
				<grid:cell width="30px">选择</grid:cell>
				<grid:cell width="130px">队列ID</grid:cell>
				<grid:cell width="150px">外部订单号</grid:cell>
				<grid:cell width="150px">内部订单号</grid:cell>
				<grid:cell width="60px">服务编码</grid:cell>
				<grid:cell width="60px">对象类型</grid:cell>
				<grid:cell width="60px">状态</grid:cell>
				<grid:cell width="130px">创建时间</grid:cell>
			</grid:header>
			<grid:body item="queue">
				<grid:cell>
					<input type="radio" name="co_id" id="co_id" value="${queue.co_id }">
				</grid:cell>
				<grid:cell>
					${queue.co_id}
				</grid:cell>
				<grid:cell>
					${queue.ext_order_id}
				</grid:cell>
				<grid:cell>
					${queue.order_id}
				</grid:cell>
				<grid:cell>
					${queue.service_code}
				</grid:cell>
				<grid:cell>
					${queue.object_type}
				</grid:cell>
				<grid:cell>
					${queue.status}
				</grid:cell>
				<grid:cell>
					${fn:substring(queue.create_time , 0 , 19)}
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="order_syn_dialog">
<div id="order_syn_service_list"></div>
</div>

