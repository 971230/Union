<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/js/guiji_co_queue_list.js"></script>

<div >
	<form  action="${listFormActionVal}"  id="searchOrderGjCoQueueForm" method="post" >
		<div class="searchformDiv">
	    	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	    		<tbody>
		    	    <tr>
		    	    	<th>
							数据来源：
						</th>
						<td style="width:180px;">
							<select name="params.tb_name" id="tb_name">
								<option value="es_co_queue" <c:if test="${params.tb_name=='es_co_queue'}">selected</c:if>>在用</option>
								<option value="es_co_queue_bak" <c:if test="${params.tb_name=='es_co_queue_bak'}">selected</c:if>>历史</option>
								
							</select>
						</td>
						<th>
							服务编码：
						</th>
						<td style="width:180px;">
							<select name="params.service_code" id="service_code">
								<option value="CO_GUIJI_NEW" <c:if test="${params.service_code=='CO_GUIJI_NEW'}">selected</c:if>>新订单系统订单归集</option>
								<option value="CO_GUIJI" <c:if test="${params.service_code=='CO_GUIJI'}">selected</c:if>>订单归集</option>
								
							</select>
						</td>
						<th>
							外部订单号：
						</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" id="ext_order_id" name="params.ext_order_id" value="${params.ext_order_id }" />
						</td>
					</tr>
	  	    	</tbody>
	  	    </table>
    	</div>
    	<div class="comBtnDiv">
    		<a href="javascript:void(0);" style="margin-left:60px;width:180px;" id="searchQueueBtn" class="graybtn1"><span>搜  &nbsp 索</span></a>
			<%--<a href="javascript:void(0);" style="margin-left:10px;" id="openOrderSynDialog" class="graybtn1"><span>暂 停/启 动</span></a>
			<a href="javascript:void(0);" style="margin-left:10px;" id="moveDataBtn" class="graybtn1"><span>数据搬迁</span></a>--%>
			<a href="javascript:void(0);" style="margin-left:10px;width:180px;" id="moveOrderDataBtn" class="graybtn1"><span>订单数据搬迁</span></a>
			<a href="javascript:void(0);" style="margin-left:10px;width:180px;" id="cacheOrderDataBtn" class="graybtn1"><span>缓存订单商品数据</span></a>
			<a href="javascript:void(0);" style="margin-left:10px;width:180px;" id="doGuijiBtn" class="graybtn1"><span>归 &nbsp 集</span></a>
			<%--<a href="javascript:void(0);" style="margin-left:10px;" id="testRule" class="graybtn1"><span>规则测试</span></a>
    	--%></div>
	</form>

	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchOrderGjCoQueueForm" action="${listFormActionVal}" >
			<grid:header>
				<grid:cell width="30px">选择</grid:cell>
				<grid:cell width="110px">队列ID</grid:cell>
				<grid:cell width="150px">外部订单号</grid:cell>
				<grid:cell width="110px">内部订单号</grid:cell>
				<grid:cell width="110px">服务编码</grid:cell>
				
				<grid:cell width="60px">状态</grid:cell>
				<grid:cell width="110px">创建时间</grid:cell>
			</grid:header>
			<grid:body item="queue">
				<grid:cell>
					<input type="radio" name="co_id" id="co_id" value="${queue.co_id }" outid="${queue.ext_order_id}" >
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
					
					<c:choose> 
  <c:when test="${queue.service_code=='CO_GUIJI_NEW'}"> 
       新订单系统订单归集
  </c:when> 
  <c:when test="${queue.service_code=='CO_GUIJI'}"> 
        订单归集
  </c:when> 
 
  <c:otherwise> 
   其他情况
  </c:otherwise> 
</c:choose> 
				</grid:cell>
				
				<grid:cell>
					
						<c:choose> 
  <c:when test="${queue.status=='WFS'}"> 
       未发送
  </c:when> 
  <c:when test="${queue.status=='FSZ'}"> 
       发送中
  </c:when> 
   <c:when test="${queue.status=='XYCG'}"> 
      响应成功
  </c:when> 
  <c:when test="${queue.status=='XYSB'}"> 
       响应失败
  </c:when> 
  <c:when test="${queue.status=='DDGQ'}"> 
      订单挂起
  </c:when> 
 
  <c:otherwise> 
   其他情况
  </c:otherwise> 
</c:choose> 
				</grid:cell>
				<grid:cell>
					${fn:substring(queue.created_date, 0 , 19)}
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="order_syn_dialog">
<div id="order_syn_service_list"></div>
</div>

