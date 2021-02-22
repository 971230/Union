<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/core/common/commonlibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/task/js/task.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/task/js/common.js"></script>
<div id="">
	<form id="searchTasksListForm" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody>
	    	    <tr>
					 <th>任务名称：</th>
				  <td>
				  	<input type="text" class="ipttxt" style="width:149px;" name="task.task_name" value="${task_name }" id="task_name" />
				  	<input type="hidden" value="${task.task_name}" name="task_name" id="task_name" />
				  </td>
				   <th>任务类型：</th>
	    	      <td>
						<select class="ipttxt"  style="width:100px;" id="task_type"  name = "task.task_type">
							<option value="">--请选择--</option>
							<option value="NEW_BUSINESS_OPEN">新业务订购</option>
							<option value="OFFER_ORDER">套餐新装</option>
							<option value="OFFER_CHANGE">套餐变更</option>
							<option value="OFFER_CANCEL">套餐退订</option>
							<option value="AIR_CHARGE">空中充值</option>
							<option value="NEW_BUSINESS_CANCEL">新业务退订</option>
							<option value="SELNUM_OPEN">选号开户</option>
							<option value="PAY_CHARGE">空充</option>
						</select> 
				  </td>
				  
	    	      <th>归属地市：</th>
	    	      <td><html:selectdict appen_options="<option value=''>--全部--</option>" style="width:200px;" curr_val="${task.lan_code}" name="task.lan_code" id="lan_code" attr_code="DC_LAN"></html:selectdict>
	    	      <th style="display:none;">归属区县：</th>
	    	      <td style="display:none;">
						<select class="ipttxt" style="width:100px;" id="city_code" name="task.city_code">
							<option value="">-请选择-</option>
							
						</select>
					</td>
				 
		    	</tr>
		    	<tr>
				  <th>创建时间：</th>
				  <td>
				  	<input type="text" datatype="date" class="dateinput ipttxt"  style="width:149px;" readonly="readonly" value="${start_date }" id="create_time" name="task.create_time" size="15">
				  </td>
				  <th>任务状态：</th>
				  <td>
				  		<select  class="ipttxt" style="width:100px" id="state"  name="task.state">
							<option value="">--请选择--</option>
							<option value="001">已录入</option>
						    <option value="002">已分解</option>
						    <option value="003">已下发</option>
						    <option value="004">已撤销</option>
						    <option value="005">已删除</option>
						</select>
				  </td>
				  <th>完成状态：</th>
				  <td>
				  		<select  class="ipttxt"  style="width:100px" id="finished"  name = "task.finished">
							<option  value="">--请选择--</option>
							<option  value="0">未完成</option>
						    <option  value="1">已完成</option>
						</select>
				  </td>
				  <td colspan="2">
				  	<input type="button" style="margin-left:25px;margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="qry_btn" name="qry_btn">
				  </td>
			  </tr>
	  	    </tbody>
	  	    </table>
    	</div>
    	<div class="comBtnDiv">
			<a href="task!addTask.do" id="addBtn" style="margin-right:10px;" class="graybtn1"><span>录入</span></a>
			<a id="updateBtn" style="margin-right:10px;" class="graybtn1"><span>修改</span></a>
			<a id="cancelBtn" style="margin-right:10px;" class="graybtn1"><span>撤销</span></a>
			<a id="delBtn" style="margin-right:10px;" class="graybtn1"><span>删除</span></a>
			<a id="taskFinish" style="margin-right:10px;display:none;" class="graybtn1"><span>关闭任务</span></a>
		</div>
	</form>
	
</div>
<div>
	<div id='left_panel' style='float:left;width:20%;height:550px;'>
		<iframe style="height:530px;width:218px;" src="<%=request.getContextPath() %>/shop/admin/task!getOrg.do?ajax=yes"></iframe>
	</div>
	
	<div id='right_panel' style='float:right;width:79%;height:580px' >
	     <div id="taskPanel"></div>
	     
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$("#lan_code").attr("style", "width:100px;");
	});
</script>