<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<form action="mblLoginAction!loginLog.do" method="post" id="serchform" >
	<div class="searchformDiv" id="searchformDiv">
<table class="form-table">
	<tr>
	
		<th>分销商名称：</th>
		<td>
			<input type="text"  name="staff_name" id="staff_name" value='${staff_name}' style="width: 148px;"
			maxlength="60" class="ipttxt" /> 
		</td>
	     <th>工号：</th>
			<td>
				<input type="text" id="nameInput"  name="staff_code" value='${staff_code}' class="ipttxt" /> 
				 <a class="sgreenbtn" href="#">
                   <span id="choiceBotton1">选择工号</span>
                 </a>		
			</td>
			<th>手机号码：</th>
		<td>
			<input type="text"  name="mobile" id="mobile" value='${mobile}' style="width: 148px;"
			maxlength="60" class="ipttxt" /> 
		</td>
   </tr>
   <tr>
	 <th>开始时间：</th>
		<td>
			<input type="text"  name="start_time" id="cre1"
						value='${start_time }'
						readonly="readonly"
						maxlength="60" class="dateinput ipttxt"  dataType="date"/> 
		</td>
		<th>结束时间：</th>
		<td>
			<input type="text"  name="end_time" id="cre"
						value='${end_time}'
						readonly="readonly"
						maxlength="60" class="dateinput ipttxt"  dataType="date"/> 
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
		<grid:cell >分销商名称</grid:cell>
	  	<grid:cell >工号</grid:cell>
	  	<grid:cell >工位</grid:cell>  
		<grid:cell >手机号码</grid:cell>
		<grid:cell >城市</grid:cell>  
		<grid:cell >IP地址</grid:cell> 
		<grid:cell >登录时间</grid:cell> 
	</grid:header>
  <grid:body item="obj">
  	  <grid:cell >${obj.seq} </grid:cell>
     <grid:cell >${obj.staff_name} </grid:cell> 
     <grid:cell> ${obj.staff_code}</grid:cell>
     <grid:cell >${obj.station} </grid:cell> 
     <grid:cell >${obj.mobile} </grid:cell> 
     <grid:cell >${obj.city} </grid:cell> 
     <grid:cell >${obj.ip} </grid:cell> 
     <grid:cell>${obj.create_time}</grid:cell>
  </grid:body>   
</grid:grid>  
</div>
<div class="excel" style="display:none;">
	<input class="comBtn" type="button" onclick="GridExcel.export_file({ url:'/shop/admin/mblLoginAction!loginLog.do', currentPage:'no', totalPage:'yes', params:{'is_administrator':'1', 'supplier_type':'2', excel:'yes' } });" value="导出" title="导出excel列表，导出符合要求的前500条记录">
	<input class="comBtn" type="button" onclick="GridExcel.export_file({ url:'/shop/admin/mblLoginAction!loginLog.do', currentPage:'yes', totalPage:'no', params:{'is_administrator':'1', 'supplier_type':'2', excel:'yes' } });" value="导出本页" title="导出excel列表，导出本页记录" style="margin-left:5px;">
</div>
<div id="staffDiog" style="width: 800;height: 800"></div>

<script type="text/javascript" src="log/staffList.js"></script>
<script type="text/javascript" src="log/staffListDalog.js"></script>
<script type="text/javascript" >
	function cl(){
	    $("#staff_name").val("");
		$("#mobile").val("");
		$("#nameInput").val("");
		$("#cre1").val("");
		$("#cre").val("");
	}
	 $("#choiceBotton1").unbind("click").bind("click",function() {
          self.showServiceListDialogs();
     });
	
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

