<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/task/js/task_add.js"></script>
<style>
.input_text {
	width: 160px;
}
</style>
<script type="text/javascript">
var lan_id = "${lan_code}";

$(function(){
	$("#lan_code").attr("style", "width:148px;");
});
</script>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">任务信息</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>   
<div class="input">
<form  action="${taskAddFormActionVal }" class="validate" method="post" name="taskInfoForm" id="taskInfoForm"><!--  enctype="multipart/form-data" -->
<table  class="form-table">

	<tr>
		<th><span class='red'>*</span><label class="text">任务编号：</label></th>
		<td><input type="text" class="ipttxt" value="${task.task_code}" name="task.task_code"  style="width:149px;" dataType="string"  id ="task_code" required="true" />
				<input type="hidden" name="task.task_id" id="task_id" value="${task.task_id }">
		</td>
	</tr>
	<tr>
		<th><span class='red'>&nbsp;</span><label class="text">上级任务：</label></th>
		<td><input type="text" class="ipttxt" value="${p_task_name}" name="p_task_name"  dataType="string" style="width:149px;" id ="p_task_name" disabled="true" required="true" /><input id ="taskSel" style="margin-left:5px;" class="comBtn" type="button" value="选&nbsp;择">
				<input type="hidden" name="task.p_task_id" id="p_task_id" />
		</td>
	</tr>
	<tr>
		<th><label class="text"><span class='red'>*</span>任务名称：</label></th>
		<td><input type="text" class="ipttxt"  value="${task.task_name}" name="task.task_name" style="width:149px;" dataType="string"  id ="task_name" required="true" /></td>
	</tr>
	<tr>
		<th><span class='red'>*</span><label class="text">任务类型：</label></th>
		<td>
			   <!-- <html:selectdict curr_val="${task.task_type}" appen_options="<option value=''>--请选择--</option>" style="width:200px;" name="task.task_type" id="task_type" attr_code="DC_TASK_TYPE"></html:selectdict> -->
			   <select name="task.task_type"  id="task_type" class="ipttxt" dataType="string" style="width:148px;height:22px" autocomplete="on" required="true">
					<option value=''>--请选择--</option>
					<c:forEach items="${catList}" var="type">
						<option value="${type.service_code}" <c:if test="${type.service_code==task.task_type }">selected</c:if>>${type.service_offer_name}</option>
					</c:forEach>
				</select>
		       <input type="hidden" name="task_type" id="task_type" value="${task.task_type}" /> 
		</td>
	</tr>
	<tr>
		<th><span class='red'>*</span><label class="text">任务分配类型：</label></th>
		<td>
		   <select name="task.task_cate"  id="task_cate" value="${task.task_cate}" class="ipttxt" dataType="string" style="width:148px;height:22px" autocomplete="on" required="true">
				<option value="0">销售量</option>
				<option value="1">销售额</option>
			</select>
		</td>
	</tr>
	<tr>
		<th><span class='red'>*</span><label id="total_number" class="text">任务总量：</label></th>
		<td>
			<input type="text"  class="ipttxt" name="task.task_num" style="width:149px;" value="${task.task_num}"  required="true"  dataType="int"  id ="task_num" onblur="calTargetNum();"/>
		</td>
	</tr>
	<tr id="target_tr">
		<th><span class='red'>*</span><label class="text"><label class="text">任务目标比（%）：</label></th>
		<td><input type="text" style="width:149px;" class="ipttxt" dataType="string" value="${task.target_rate}" id="target_rate" name="task.target_rate" required="true" onblur="calTargetNum();"/></td>
	</tr>
	<tr >
		<th><span class='red'>*</span><label class="text"><label id="target_number" class="text">任务目标值：</label></th>
		<td><input type="text"  class="ipttxt" dataType="int" value="${task.target_num}" style="width:149px;" id="target_num" name="task.target_num" required="true" /><span id="tips" class='red'>输入目标比后自动生成</span></td>
	</tr>
	
	<tr id="lantr">
		<th><span class='red'>&nbsp;</span><label class="text">分配范围-归属地市：</label></th>
		<c:choose>
			<c:when test="${lan_code != null}">
				<td><select name="task.lan_code" id="lan_code" style='width:148px;' class="ipttxt">
					<option value="${lan_code}">${lan_name}</option>
				</select></td>
			</c:when>
			<c:otherwise>
				<td><html:selectdict curr_val="${task.lan_code}"  name="task.lan_code" id="lan_code" attr_code="DC_LAN"></html:selectdict>
		    	      		<input type="hidden" name="lan_code" id="lan_code2" value="${task.lan_code}" /> </td>
			</c:otherwise>
		</c:choose>
		
	</tr>
	<tr style="display:none;">
		<th><span class='red'>&nbsp;</span><label class="text">分配范围-归属区县：</label></th>
		<td>
			<select id="city_code" name="task.city_code" style='width:148px;' class="ipttxt">
				
				<c:if test="${task.city_code!=null}">
					<option value="${task.city_code}">${task.city_name }</option>
				</c:if>
				<c:if test="${task.city_code==null}">
					<option value="">--全部--</option>
				</c:if>
			</select>
			<!--<c:if test="${task.city_code!=null}">
				<html:selectdict curr_val="${task.city_code}" appen_options="<option value=''>--全部--</option>" style="width:200px;" name="task.city_code" id="city_code" attr_code="DC_REGION"></html:selectdict>
			</c:if> -->
			<input type="hidden" name="city_code" id="city_code" value="${task.city_code}" />
		</td>
	</tr>
	
	
	<tr id="roletr">
		<th><span class='red'>*</span><label class="text">任务开始时间：</label></th>
		<td>
			<input type="text" datatype="date" class="dateinput ipttxt"  style="width:149px;" readonly="readonly" value="${task.begin_date }" id="begin_date" name="task.begin_date" required="true" size="15">
		</td>		
	</tr>
	<tr id="roletr">
		<th><span class='red'>*</span><label class="text">任务结束时间：</label></th>
		<td>
			<input type="text" datatype="date" class="dateinput ipttxt" style="width:149px;" readonly="readonly" value="${task.end_date }" id="end_date" name="task.end_date" required="true" size="15">
		</td>		
	</tr>
	<tr style="display:none;">
		<th><label class="text">任务分配对象：</label></th>
		<td>
			<input type="radio"  name="task.partner_level" value="3" checked=true>一级分销商&nbsp;&nbsp;
			<!-- <input type="radio"  name="task.partner_level" value="2">二级分销商 --> 
		</td>
	</tr>
	<tr>
		<th><label class="text">单位：</label></th>
		<td><input type="text" class="ipttxt" id="unit" name="task.unit" value="${task.unit}" style="width:149px;" readOnly="true" /></td>
	</tr>
	<tr>
		<th>任务备注：</label></th>
		<td><input type="text" class="ipttxt" value="${task.task_desc}" style="width:149px;" name="task.task_desc"  id="task_desc"  /></td>
	</tr>
</table>
  
  <div class="submitlist" align="center">
	 <table>
			<tr>
	<th></th>
	<td> <input type="hidden"  name="" value="" >
     <input  type="button" id="submitBtn"	 value=" 确 定 "  class="submitBtn"  /></td>
	</tr>
	 </table>
	</div>  
</form>
</div>
<div id ="refOrgDlg"></div>