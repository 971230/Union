<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>


<script type="text/javascript" src="${ctx}/shop/admin/workflow/js/myWork.js"></script>
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
	<form action="workflow!getWorkList.do" method="post" id="suppListForm">
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
	<form id="flowgridform" class="grid">
	<grid:grid  from="webpage">
	<grid:header>
	<grid:cell >流程实例ID</grid:cell>
  	<grid:cell >流程名称</grid:cell> 
	<grid:cell >流程发起人工号</grid:cell> 
	<grid:cell >流程发起人名称</grid:cell> 
	<grid:cell >部门</grid:cell> 
	<grid:cell >流程发起时间</grid:cell>
	<grid:cell >操作</grid:cell>
	</grid:header>

  <grid:body item="obj">
     <grid:cell >${obj.flow_inst_id} </grid:cell> 
     <grid:cell>
         ${obj.flow_name}
     </grid:cell>
     <grid:cell >${obj.username} </grid:cell> 
     <grid:cell >${obj.realname} </grid:cell>
     <grid:cell >${obj.dep_name} </grid:cell> 
     <grid:cell >${obj.apply_date} </grid:cell> 
     <grid:cell><a title="流程审核" href="workflow!flowAudit.do?&flow_inst_id=${obj.flow_inst_id }">审核</a></grid:cell>
     <input type=hidden  name="apply_url" value='${obj.apply_url}'>
  </grid:body>  
  
</grid:grid>  
	</form>
	 <div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>

<div id='flow_inst_iframe' style='border:0px solid red;width:100%;height:400px;overflow:hidden;display:none;'>

</div>

<div id="mywork_flow_item_list"></div>

<script type="text/javascript">
 
 
</script>
 

