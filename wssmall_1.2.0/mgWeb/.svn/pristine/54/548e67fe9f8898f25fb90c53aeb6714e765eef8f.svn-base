<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/commons/taglibs.jsp"%>
<form action="mblLoginAction!callerLog.do" method="post" id="serchform">
	<div class="searchformDiv" id="searchformDiv">
<table class="form-table">
	<tr>
	  
	 <th>开始时间：</th>
		<td>
			<input type="text"  name="start_time" id="cre1" value='${start_time}'  style="width: 130px"
						readonly="readonly" maxlength="60" class="dateinput ipttxt"  dataType="date"/> 
		</td>
		<th>结束时间：</th>
		<td>
			<input type="text"  name="end_time" id="cre" value='${end_time}'
						 style="width: 130px" readonly="readonly" maxlength="60" class="dateinput ipttxt"  dataType="date"/> 
		</td>
		<th></th>
	   	<td>
		 <input class="comBtn" type="submit" name="searchBtn" id="serButton" value="搜索" style="margin-right:5px;"/>
		
		 <input class="comBtn" type="button" name="creBtn" onclick="cl();" id="creButton" value="清除" style="margin-right:5px;"/>
		</td>
   </tr>
</table>
</div>  	
</form>
<form id="gridform" class="grid" ajax="yes">
 <div class="grid" id="goodslist" >
	<grid:grid from="webpage">
		<grid:header>
		    <grid:cell >序号</grid:cell>
		    <grid:cell >op_code</grid:cell> 
		    <grid:cell >请求时间</grid:cell> 
		    <grid:cell >返回时间</grid:cell>
		    <grid:cell >请求渠道</grid:cell> 
		</grid:header>
		<grid:body item="obj">
			<grid:cell>${obj.log_id}</grid:cell> 
			<grid:cell>${obj.op_code}</grid:cell> 
			<grid:cell>${obj.req_time}</grid:cell> 
			<grid:cell>${obj.rsp_time}</grid:cell> 
			<grid:cell>
				<c:if test="${obj.source_from eq 'LLKJ_WT'}">网厅</c:if>
				<c:if test="${obj.source_from eq 'LLKJ_AGENT'}">代理商</c:if>
				<c:if test="${obj.source_from eq 'WSSMALL'}">统一后台</c:if>
			</grid:cell> 
		</grid:body>   
	</grid:grid>  
</div>
</form>
<div id="workList" ></div>
<div class="excel" style="display:none;">
	<input class="comBtn" type="button" onclick="GridExcel.export_file({ url:'/shop/admin/mblLoginAction!callerLog.do', currentPage:'no', totalPage:'yes', params:{'is_administrator':'1', 'supplier_type':'2', excel:'yes' } });" value="导出" title="导出excel列表，导出符合要求的前500条记录">
	<input class="comBtn" type="button" onclick="GridExcel.export_file({ url:'/shop/admin/mblLoginAction!callerLog.do', currentPage:'yes', totalPage:'no', params:{'is_administrator':'1', 'supplier_type':'2', excel:'yes' } });" value="导出本页" title="导出excel列表，导出本页记录" style="margin-left:5px;">
</div>
<script type="text/javascript" src="log/work.js"></script>
<script type="text/javascript" src="log/workList.js"></script>

<script type="text/javascript">
	
  function cl() 
 { 
  $('#cre1').val(""); 
  $('#cre').val(""); 
 } 
   $("#serButton").bind("click",function(){
   var timeCompare =(new Date($('#cre1').val()) - (new Date($('#cre').val())));
   var timeComp=(new Date($('#cre1').val()) - (new Date().getTime()));
   	if(timeCompare>0){
   		alert("开始时间不能大于结束时间！");
   		return false;
   		}
   	if(timeComp>365){
   		alert("超出查询时间，请选择十二个月以内的时间！");
   		return false;
   	}
   });
</script>
