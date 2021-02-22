<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form action="javascript:void(0);" method="post" id="taskform">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	      <th>任务名称:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="task_name" value="${task_name }" class="searchipt" /></td>	    	 
	    	     	
	    	      <td><input type="button" style="margin-right:10px;" class="comBtn" value="查询"   id="searchTaskBot" ></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>  	
	</form>
 <div class="grid" id="selectTask" >	
 <form id="gridform" class="grid" ajax="yes">
	<grid:grid  from="webpage"  ajax="yes" formId="serchform">
	<grid:header>
	    <grid:cell width="50px">选择</grid:cell>
	    <grid:cell>任务编号</grid:cell>
		<grid:cell>任务名称</grid:cell>
	</grid:header>
  <grid:body item="obj">
	    <grid:cell><input type="radio"  id = "checkBox" name="checkBox" task_name="${obj.task_name}" value="${obj.task_id}" /></grid:cell>
    	<grid:cell>${obj.task_code}</grid:cell>
    	<grid:cell>${obj.task_name}</grid:cell>
  </grid:body>   
</grid:grid>  
</form>
</div>	   
<div class="submitlist" align="center">
	 <table>
	 <tr>
	 <th></th>
	 <td>
           <input  type="button"  value=" 确定 " class="submitBtn" id="selectTaskOkBot"/>
	 </td>
	 </tr>
	 </table>
</div>

<script type="text/javascript" src="alarm/js/showTaskDalog.js"></script>

