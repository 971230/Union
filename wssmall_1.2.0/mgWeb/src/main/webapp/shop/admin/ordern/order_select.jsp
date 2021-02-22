<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<script type="text/javascript" src="${ctx}/shop/admin/ordern/order_select.js"></script>
<div class="searchformDiv" id="searchformDiv">
<table class="form-table">
	
	<input type="text" class="ipttxt"  style="display: none;" id="leftState" name="action" value="${ordParam.action}"/>
   	<input type="text" class="ipttxt"  style="display: none;" id="topSourceFrom" name="${sourceFrom}" value="${sourceFrom}"/>
   <tr>
	<c:if test="${ordParam.action=='all' or ordParam.action=='' or ordParam.action== null}">
	   	<th>处理状态：</th>
		 <td>
			 <select  class="ipttxt"  name="orderstatus" id="orderstatus"  style="width: 150px;">
				<c:forEach var="orderStatusList" items="${orderStatusList}">
					<option value="${orderStatusList.pkey}">${orderStatusList.pname}</option>
				</c:forEach>
			</select> 
		</td>
		<input type="text" id="orderstatus_hide" name="ordParam.orderstatus" value="${ordParam.orderstatus}" style="display: none;"/>
	</c:if>
	<th style="display: none;">商品名称：</th>
		<td style="display: none;">
			<input type="text"  name="orderName" id="orderName"
						value='${orderName}'
						maxlength="60" class="ipttxt" /> 
		</td>
	  <th>订单号：</th>
			<td>
				<input type="text"  name="orderCode" id="orderCode"
							value='${orderCode}'
							maxlength="60" class="ipttxt" /> 
			</td>
   <!-- </tr>
   
   <tr> -->
   
   	 <c:if test="${adminUser.founder==0 or adminUser.founder == 1 or adminUser.founder == 3}">
 		<th>收货人：</th> 
			<td>
				<input type="hidden" name="userid" id="userid" value="${userid }"/>
				<input type="text" name="username" id="username" class="ipttxt"  value="${username }"/>
				
				<!-- input type='text' readonly="readonly" class="ipttxt" name='realname' id='realname' value="${realname}" style="width: 120px;" />
				<input type="button" id="refAgentBtn" value="选择" class="comBtn" style="width: 50px;"/>
				<input type="button" value="清空" onclick="clean_agent();" class="comBtn" style="width: 50px;"/ -->
			</td>
		</c:if>
		
	 <th>开始时间：</th>
		<td>
			<input type="text"  name="startTime" id="startTime"
						value='${startTime}'
						readonly="readonly"
						maxlength="60" class="dateinput ipttxt"  dataType="date"/> 
		</td>
		<th>结束时间：</th>
		<td>
			<input type="text"  name="endTime" id="endTime"
						value='${endTime}'
						readonly="readonly"
						maxlength="60" class="dateinput ipttxt"  dataType="date"/> 
		</td>
		
		
		<th></th> 
	   	<td>
		 <input class="comBtn" type="submit" name="searchBtn" id="orderInfoButton" value="搜索" style="margin-right:5px;"/>
		</td>
   </tr>
</table>
<div title="代理" id="refAgentDlg"></div>
</div>
