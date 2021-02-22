<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>队列维护</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ecs_ord/js/queueOrder.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/public/comun.js"></script>
</head>
<body> 
<div class="gridWarp" >
	<div class="new_right">
		<!-- 选择框 -->
		<form action="" method="post" id="orderListForm">
		<jsp:include page="queueOrderParams.jsp"/>
        </form>
		<div class="right_warp">    
			<form action="" id="order_list_fm" style="border-top: 1">  
				<grid:grid from="webpage" formId="orderListForm" action="queueCardMateManagerAction!toQueueOrderList.do">
				<grid:header>
				<grid:cell style="text-align:center; width:8%;vertical-align:middle">状态<input type="checkbox" id="checkAlls" style="vertical-align:middle;padding-left: 5px"/></grid:cell>
					<grid:cell width="22%">队列信息</grid:cell>
					<grid:cell width="20%">开户信息</grid:cell>
					<grid:cell width="20%">写卡信息</grid:cell>
					<grid:cell width="20%">异常信息</grid:cell>
					<grid:cell width="10%">操作</grid:cell>
				</grid:header>
				<grid:body item="queueOrdervo" >
				<grid:cell clazz="alignCen" >
				   	<c:choose>
						<c:when test="${queueOrdervo.queue_status == '1'}">
							<i  title="回退中 "  class="rebacking"></i>							
						</c:when>
				   		<c:when test="${queueOrdervo.write_card_status == '1' || queueOrdervo.open_account_status == '1' }">
							
						</c:when>
						<c:otherwise>	
							<c:choose>
		   					<c:when test="${queueOrdervo.order_model == '06' &&(queueOrdervo.flow_trace_id == 'D' || queueOrdervo.flow_trace_id == 'X' || queueOrdervo.flow_trace_id == 'Y' )}">
			      	  		<input type="checkbox" name="orderidArray" value="${queueOrdervo.order_id}" style="vertical-align:middle"/>
							</c:when>
					   		<c:otherwise></c:otherwise>	
							</c:choose>		   
						</c:otherwise>			
					</c:choose>
				</grid:cell>
				<grid:cell>
					<div class="order_pri">
		             	<p class="tit">内部订单号：${queueOrdervo.order_id }</p>
		             	<p class="tit">当前环节：<html:dictCode2Name attr_code="DC_ORDER_TACHE_NODE" pkey="${queueOrdervo.flow_trace_id}" ></html:dictCode2Name></p>
		             	<p class="tit">生产模式：<html:dictCode2Name attr_code="DC_MODE_OPER_MODE" pkey="${queueOrdervo.order_model}" ></html:dictCode2Name></p>
		                <p class="tit">队列编码：${queueOrdervo.queue_code }</p>
		             </div>					
				</grid:cell>
				<grid:cell>
					<div class="order_pri">
		                <p class="tit">开户状态：<html:dictCode2Name attr_code="DC_OPEN_ACC_STATUS" pkey="${queueOrdervo.open_account_status}" ></html:dictCode2Name></p>
		             	<p class="tit">开户时间：${queueOrdervo.open_account_time }</p>
		             </div>						
				</grid:cell> 
				<grid:cell>
					<div class="order_pri">
		                <p class="tit">写卡状态：<html:dictCode2Name attr_code="DC_WRITE_CARD_STATUS" pkey="${queueOrdervo.write_card_status}"></html:dictCode2Name></p>
		             	<p class="tit">写卡时间：${queueOrdervo.write_card_time }</p>
		             </div>						
				</grid:cell>
				<grid:cell>
					<div class="order_pri">
		             	<p class="tit">进入队列时间：${queueOrdervo.create_time }</p>
		                <p class="tit">回退状态：<html:dictCode2Name attr_code="DC_QUEUE_REBACK_STATUS" pkey="${queueOrdervo.queue_status}"></html:dictCode2Name></p>
		             	<p class="tit">异常类型：<html:dictCode2Name attr_code="DIC_ORDER_EXCEPTION_TYPE" pkey="${queueOrdervo.exception_type}"></html:dictCode2Name></p>
		             	<p class="tit">异常描述：${queueOrdervo.exception_msg }</p>
		             </div>					
				</grid:cell> 
				<grid:cell>	   
				    <c:choose>
				      	<c:when test="${queueOrdervo.write_card_status == '1' || queueOrdervo.open_account_status == '1' }">
							
						</c:when>
						<c:when test="${queueOrdervo.queue_status == '1'}">				
						</c:when>
						<c:otherwise>	    
							<c:choose>
							<c:when test="${queueOrdervo.order_model == '06' &&(queueOrdervo.flow_trace_id == 'D' || queueOrdervo.flow_trace_id == 'X' || queueOrdervo.flow_trace_id == 'Y' )}">  						      				
										<a href="javascript:void(0);" 
											name="single_reback" 
											class="dobtn" 
											order_id="${queueOrdervo.order_id}">回退</a>					  				   		
						   	</c:when>
						   	<c:otherwise></c:otherwise>
							</c:choose>		
						</c:otherwise>
					</c:choose> 			      				
				</grid:cell>			
				</grid:body>
				</grid:grid>
			</form>        
		    <div class="clear"></div>
		</div>
	</div>
</div>
<div id="rebackDiv"></div>
<script type="text/javascript">
	$(function(){
		queueOrder.init();
		initDialog("rebackDiv", true, "回退", "600px", "600px");
	});	
</script>
</body>
</html>