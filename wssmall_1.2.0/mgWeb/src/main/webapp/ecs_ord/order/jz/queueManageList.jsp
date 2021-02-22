<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>队列维护</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ecs_ord/js/queueManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/public/comun.js"></script>
</head>
<body>   

<div class="gridWarp">
	<div class="new_right">
		<!-- 选择框 -->
		<form action="" method="post" id="orderListForm">
		 <input type="hidden" name="btnType" value="ordList"/>
		<jsp:include page="queueManageParams.jsp"/>
        </form>
		<div class="right_warp">    
			<form action="" id="order_list_fm" style="border-top: 1">  
				<grid:grid from="webpage" formId="orderListForm" action="queueCardMateManagerAction!toQueueManageList.do">
				<grid:header>
				<grid:cell style="text-align:center; width:6%;vertical-align:middle">状态<input type="checkbox" id="checkAlls" style="vertical-align:middle;padding-left: 5px"/></grid:cell>
					<grid:cell width="14%">队列信息</grid:cell>
					<grid:cell width="24%">开户阀值信息</grid:cell>
					<grid:cell width="16%">队列关闭条件（开户失败）</grid:cell>
					<grid:cell width="16%">队列关闭条件（写卡失败）</grid:cell>
					<grid:cell width="14%">状态</grid:cell>
					<grid:cell width="10%">操作</grid:cell>
				</grid:header>
				<grid:body item="queueManagelist" >
				<grid:cell clazz="alignCen" >
				   <c:choose>
				          <c:when test="${queueManagelist.queue_switch == '0'}">
						<i  title="已开启 "  class="opened"></i>
					</c:when>
					<c:otherwise>	      						      				
							<i  title="已关闭"  class="closed"></i>
						</c:otherwise>
					</c:choose>
					<input type="checkbox" name="orderidArray" value="${queueManagelist.id}" style="vertical-align:middle"/>
				</grid:cell>
				<grid:cell>
					<div class="order_pri">
			         	<ul>
			             	<p class="tit">队列编码：${queueManagelist.queue_no }</p>
			             	<p class="tit">队列描述：</p>
			             	<p class="tit">${queueManagelist.queue_desc }</p>
			             </ul>
			         </div>
				</grid:cell>
				<grid:cell>
					<div class="order_pri">
		                <p class="tit">可用写卡机数量：${queueManagelist.avail_card_mac_num }</p>
		            	<p class="tit">开户系数：${queueManagelist.open_coeff }</p>
		            	<p class="ps">队列最大自动开户数&nbsp;&lt;&nbsp;开户系数&nbsp;*&nbsp;队列写卡机数</p>
		             </div>
				</grid:cell> 
				<grid:cell>
					<div class="order_pri">
		                <p class="tit">关闭阀值(最大连续失败数)：${queueManagelist.open_max_failed_num }</p>
		             	<p class="tit">当前连续失败数：${queueManagelist.conti_open_failed_num}</p>
		             </div>
				</grid:cell> 
				<grid:cell>
					<div class="order_pri">
		             	<p class="tit">关闭阀值(最大连续失败数)：${queueManagelist.card_max_failed_num }</p>
		                 <p class="tit">当前连续失败数：${queueManagelist.conti_card_failed_num }</p>
		             </div>
				</grid:cell> 
				<grid:cell>
					<div class="order_pri">
		             	<p class="tit">状态：<html:dictCode2Name attr_code="DC_QUEUE_STATUS" pkey="${queueManagelist.queue_switch}"></html:dictCode2Name></p>
		               	<c:if test="${queueManagelist.queue_switch == '1'}">
		                <p class="tit">关闭原因：${queueManagelist.deal_reason }</p>
		                </c:if>
		             </div>
				</grid:cell> 
				<grid:cell>	    
					<a href="javascript:void(0);" name="single_edit" class="dobtn" id="edit${queueManagelist.id}" queue_id="${queueManagelist.id}" style="margin: 2px">编辑</a>		    
				    <c:choose>
					    <c:when test="${queueManagelist.queue_switch == '0'}">
							<a href="javascript:void(0);" class="dobtndisabled" style="margin: 2px">开启</a><br/>
							<a href="javascript:void(0);" class="dobtndisabled" style="margin: 2px">回退</a>	      				
							<a href="javascript:void(0);" name="single_close" class="dobtn" id="open${queueManagelist.id}" queue_id="${queueManagelist.id}" style="margin: 2px">关闭</a>
						</c:when>
						<c:otherwise>	      				
							<a href="javascript:void(0);" name="single_open" class="dobtn" id="colse${queueManagelist.id}" queue_id="${queueManagelist.id}" style="margin: 2px">开启</a><br/>
							<a href="javascript:void(0);" name="single_reback" class="dobtn" id="reback${queueManagelist.queue_no}" queue_no="${queueManagelist.queue_no}" style="margin: 2px">回退</a>	
							<a href="javascript:void(0);" class="dobtndisabled"  style="margin: 2px">关闭</a>	      				
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
<div id="switchDiv"></div>
<script type="text/javascript">
	$(function(){
		queueManage.init();
		initDialog("switchDiv", true, "关闭队列", "600px", "600px");
	});
	
</script>
</body>
</html>