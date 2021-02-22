<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div >
	<form action="workflow!getApplyList.do" method="post" id="myApplyForm">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
	    	      <th>流程名称:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="flow_name" value="${flow_name }" class="searchipt" /></td>
	    	      
	    	      <th>流程发起人:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="username" value="${username }" class="searchipt" /></td>
	    	      
	    	      <th>开始时间:</th>
	    	      <td><input type="text"  name="startTime" id="startTime"
						value='${startTime}'
						readonly="readonly"
						maxlength="60" class="dateinput ipttxt"  dataType="date"/> </td>
	    	      
	    	      
	    	      <th>结束时间:</th>
	    	      <td>
	    	         <input type="text"  name="endTime" id="endTime"
						value='${endTime}'
						readonly="readonly"
						maxlength="60" class="dateinput ipttxt"  dataType="date"/>
	    	         </td>
	    	      
	    	      <td><input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"   id="button" name="button"></td>
	  	      </tr>
	  	      
	  	     
	  	      
	  	    </tbody>
	  	    </table>
    	</div>
    	
		
	</form>
	<form id="" class="grid">
	<grid:grid  from="webpage">
	<grid:header>
	<grid:cell >流程实例ID</grid:cell>
  	<grid:cell >流程名称</grid:cell> 
	<grid:cell >流程当前环节名称</grid:cell> 
	<grid:cell >流程当前审批人工号</grid:cell> 
	<grid:cell >流程当前审批人名称</grid:cell>
	<grid:cell >流程当前审批人部门</grid:cell> 
	<grid:cell >流程发起时间</grid:cell>
	
	</grid:header>

  <grid:body item="obj">
     <grid:cell >${obj.flow_inst_id} </grid:cell> 
     <grid:cell><a href="javascript:qryFlowItemList('${obj.flow_inst_id}')">${obj.flow_name}</a> </grid:cell>

     <grid:cell >${obj.item_name} </grid:cell> 
     <grid:cell >${obj.username} </grid:cell>
     <grid:cell >${obj.realname} </grid:cell>
     <grid:cell >${obj.dep_name} </grid:cell> 
     <grid:cell >${obj.apply_date} </grid:cell> 
    
  </grid:body>  
  
</grid:grid>  
	</form>
	 <div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
 
<!-- 查看审批流程 -->
<div id="flow_item_list"></div>

<script type="text/javascript">
 Eop.Dialog.init({id:"flow_item_list",modal:false,title:"流程审批环节信息",width:"800px"});
 
 function qryFlowItemList(flow_inst_id){

	var url ="workflow!qryFlowItemById.do?ajax=yes&flow_inst_id="+flow_inst_id;
	$("#flow_item_list").load(url,function(responseText) {
		Eop.Dialog.open("flow_item_list");
		
	});	
}
</script>