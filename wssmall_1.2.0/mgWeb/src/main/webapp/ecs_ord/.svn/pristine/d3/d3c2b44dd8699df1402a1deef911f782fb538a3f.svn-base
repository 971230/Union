<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<%-- <script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script> --%>
<form method="post" id="serchform" action='${ctx}/shop/admin/ordAuto!queryWriteCardPer.do'>
 <div class="searchformDiv">
	 <table>
		<tr>   
		 <th>创建时间：</th>   
	     <td>
	     <input style="width: 140px" type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">-
	   </td>
	   
	     <td><input style="width: 140px" type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	    </td>
	     
	      <td>
		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="查询" style="margin-right:10px;"/>
			</td> 
	</tr>
	 </table>
  </div>
</form>

<div class="grid" >

<form method="POST"  id="writeCardPerFrom" >
<grid:grid from="webpage"  formId="serchform">
	<grid:header>
		<grid:cell >时间</grid:cell>
		<grid:cell >成功量</grid:cell>
		<grid:cell >失败量</grid:cell>
		<grid:cell >失败率(%)</grid:cell>
	</grid:header>
	<grid:body item="writeCardPer">
		<grid:cell>${writeCardPer.create_time}</grid:cell>
		<grid:cell>${writeCardPer.success}</grid:cell>
		<grid:cell>${writeCardPer.fail}</grid:cell>
		<grid:cell>${writeCardPer.failPercent}</grid:cell>
	</grid:body>
</grid:grid>
</form>
