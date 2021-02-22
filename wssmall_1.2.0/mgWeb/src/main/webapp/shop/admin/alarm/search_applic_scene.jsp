<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form action="javascript:void(0);" method="post" id="alarmTaskform">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	      <th>任务名称:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="alarm_task_name" value="${alarm_task_name   }" class="searchipt" /></td>	    	 
	    	     	
	    	      <td><input type="button" style="margin-right:10px;" class="comBtn" value="查询"   id="searchAlarmTaskBot" ></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>  	
	</form>
 <div class="grid" id="selectAlarmTask" >	
 <form id="gridform" class="grid" ajax="yes">
	<grid:grid  from="webpage"  ajax="yes" formId="serchform">
	<grid:header>
	    <grid:cell width="50px">选择</grid:cell>
		<grid:cell>告警名称</grid:cell>
		<grid:cell>场景类型</grid:cell>
	</grid:header>
  <grid:body item="obj">
	    <grid:cell><input type="radio"  id = "checkBox" name="checkBox" alarm_task_name="${obj.alarm_task_name }" alarm_task_type="${obj.alarm_task_type}" value="${obj.alarm_task_id }" /></grid:cell>
    	<grid:cell>${obj.alarm_task_name }</grid:cell>
    	<grid:cell>
    				<c:if test="${obj.alarm_task_type =='1'}">
						对账告警
					</c:if>
					<c:if test="${obj.alarm_task_type =='2'}">
						任务告警
					</c:if>
    	</grid:cell>
    	
  </grid:body>   
</grid:grid>  
</form>
</div>	   
<div class="submitlist" align="center">
	 <table>
	 <tr>
	 <th></th>
	 <td>
           <input  type="button"  value=" 确定 " class="submitBtn" id="selectAlarmTaskOkBot"/>
	 </td>
	 </tr>
	 </table>
</div>

<script type="text/javascript" src="alarm/js/showAlarmTaskDalog.js"></script>

