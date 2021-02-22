<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/commons/taglibs.jsp"%>
<form action="mblLoginAction!workLog.do" method="post" id="serchform">
	<div class="searchformDiv" id="searchformDiv">
<table class="form-table">
	<tr>
	   <th style="width: 80px;">办理业务名称：</th>
		<td>
			<input type="text"  name="business_name" id="cres" value='${business_name}' style="width: 130px;"
			maxlength="60" class="ipttxt" /> 
		</td>
		<!--  
		<th width="80">办理业务类型：</th>
			<td>
				
			<select  class="ipttxt"  style="width: 135px;height:25px"  id="lan_sel" name = "business_type" required="true">
                    <option  value="">--请选择--</option>
       					<c:forEach var="type" items="${typeList}">
					 	<option  name="offType" value="${type.service_offer_name }" ${service_id == type.service_id ? ' selected="selected" ' : ''}>${type.service_offer_name }</option>
					 </c:forEach>
			</select>
			</td>
			-->
			<th width="80">办理业务状态：</th>
			<td>
				<select id="business_status" class="ipttxt" name="business_status" style="width: 135px;height: 25px">
				    <option value="" >------请选择----</option>
					<option value="0">成功</option>
					<option value="1">失败</option>
				</select>
			</td>
	    <th style="width: 110px;">客户手机号码：</th>
		<td>
			<input type="text"  name="phoneno" id="cres" value='${phoneno}' style="width: 130px;"
			maxlength="60" class="ipttxt" /> 
		</td>
		</tr>
		<tr>
	  <th>分销商工号：</th>
			<td>
				<input type="text" id="nameInput" class="ipttxt"   style="width: 130px" name="username" value='${username}' />
				<input type="hidden" id="nameInputVal"  name="userid" value='${userid}' />  
				<!--  <input type="button" value=" 选择工号 " class="submitBtn" id="choiceBotton"/>-->	
				<a class="sgreenbtn" href="#">
                   <span id="choiceBotton">选择工号</span>
                 </a>	
			</td>
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
	<grid:grid  from="webpage">
	<grid:header>
	    <grid:cell >序号</grid:cell>
	    <grid:cell >办理业务名称</grid:cell> 
	    <grid:cell >办理业务状态</grid:cell>
	    <grid:cell >业务金额</grid:cell> 
	    <grid:cell >客户姓名</grid:cell> 
		<grid:cell >客户手机号码</grid:cell>
		<grid:cell >手机品牌</grid:cell> 
		<grid:cell >地市</grid:cell> 
		<grid:cell >工号</grid:cell> 
		<grid:cell >操作时间</grid:cell> 
	</grid:header>
  <grid:body item="fx">
  	  <grid:cell >${fx.seq} </grid:cell>
  	  <grid:cell >${fx.business_name} </grid:cell> 
        <grid:cell>
		  <c:if test="${fx.business_status eq '0'}">成功</c:if>
		  <c:if test="${fx.business_status eq '1'}">失败</c:if>
      </grid:cell>
      <grid:cell >${fx.price} </grid:cell>
  	  <grid:cell >${fx.username} </grid:cell>
  	  <grid:cell >${fx.phoneno} </grid:cell> 
  	  <grid:cell >${fx.brand} </grid:cell> 
      <grid:cell >${fx.city} </grid:cell>
	  <grid:cell >${fx.op_name} </grid:cell> 
     <grid:cell>${fx.accept_time}</grid:cell>
  </grid:body>   
</grid:grid>  
</div>
</form>
<div id="workList" ></div>
<div class="excel" style="display:none;">
	<input class="comBtn" type="button" onclick="GridExcel.export_file({ url:'/shop/admin/mblLoginAction!workLog.do', currentPage:'no', totalPage:'yes', params:{'is_administrator':'1', 'supplier_type':'2', excel:'yes' } });" value="导出" title="导出excel列表，导出符合要求的前500条记录">
	<input class="comBtn" type="button" onclick="GridExcel.export_file({ url:'/shop/admin/mblLoginAction!workLog.do', currentPage:'yes', totalPage:'no', params:{'is_administrator':'1', 'supplier_type':'2', excel:'yes' } });" value="导出本页" title="导出excel列表，导出本页记录" style="margin-left:5px;">
</div>
<script type="text/javascript" src="log/work.js"></script>
<script type="text/javascript" src="log/workList.js"></script>

<script type="text/javascript">
	
  function cl() 
 { 
  $('#cre1').val(""); 
  $('#cre').val(""); 
  $('#cres').val(""); 
  $('#nameInput').val(""); 
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
