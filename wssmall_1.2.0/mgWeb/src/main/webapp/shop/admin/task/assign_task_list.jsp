<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8"%>
<%@include file="/commons/taglibs.jsp"%>


<div class="rightDiv">
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_1" class="selected">
						<span class="word">任务基本信息</span><span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
	
	<div>
		<form  action="<%=request.getContextPath()%>/shop/admin/task!assignTask.do" method="post" id="tcForm">
			<div class="searchformDiv">
			 <table>
				<tr>
					 <th>任务名称：</th>
					<td>
						<input type="text" class="ipttxt"  name="task.task_name" id ="task_name"  value="${task.task_name}"/>
					</td>
					<td>
				    	<input class="comBtn" type="submit" name="searchTc" id="searchTc" value="搜&nbsp;索" style="margin-left:90px;margin-right:10px;"/>
					</td>
				</tr>
				   <div style="clear:both"></div>
			  </table>	
			</div>
	    </form>   
	</div>
	<div class="grid">
	    <form id="form_tc" class="grid">
			<grid:grid  from="webpage" ajax='yes' formId="tcForm">
				<grid:header>
				  	<grid:cell>任务编号</grid:cell>
				  	<grid:cell>任务名称</grid:cell>
				  	<grid:cell>任务类型</grid:cell>
				  	<grid:cell>任务总数</grid:cell>
				  	<grid:cell>任务目标比</grid:cell>
					<grid:cell>任务目标值</grid:cell>
					<grid:cell>开始日期</grid:cell>
					<grid:cell>结束日期</grid:cell>
					<grid:cell>状&nbsp;&nbsp;态</grid:cell>
					<grid:cell>创建时间</grid:cell>
					<grid:cell>操&nbsp;&nbsp;作</grid:cell>
				</grid:header>
			    <grid:body item="obj">
			    	<grid:cell>${obj.task_code}</grid:cell>
				    <grid:cell>${obj.task_name}</grid:cell>
				    <grid:cell>${obj.task_type_name}</grid:cell>
				    <grid:cell>${obj.task_num}</grid:cell>
				    <grid:cell>${obj.target_rate}</grid:cell>
				    <grid:cell>${obj.target_num}</grid:cell>
				    <grid:cell>${obj.begin_date}</grid:cell>
				   	<grid:cell>${obj.end_date}</grid:cell>
				   	<grid:cell>${obj.state_name}</grid:cell>
				   	<grid:cell>${obj.create_time}</grid:cell>
				   	<grid:cell>
				   		<a onclick="javascript:assignTa('${obj.task_id}');return false;">下发</a>
				   	</grid:cell>
			  </grid:body>  
			</grid:grid>
		</form>
	</div>
	
	<div id="assign_div"></div>
</div>
<script type="text/javascript">
$(function(){
	Eop.Dialog.init({id:"assign_div", modal:false, title:"任务信息", width:"850px"});
	
});
function assignTa(task_id){
	
	 var url = ctx + "/shop/admin/task!assignment.do?ajax=yes&task_id="+task_id ;
	 Eop.Dialog.open("assign_div");
	    $("#assign_div").load(url, function(){
		ret_back();
	}); 	
}

function ret_back(){
	$("#rightDiv #open_account").unbind("click").click(function(){
		var task_id = $("#tcForm #task_id").val();
		var task_code = $("#tcForm #task_code").val();
		
		if(confirm("确认下发当前编号"+ task_code + "的任务")){
			$.ajax({
				url: ctx + "/shop/admin/task!confirmSend.do?ajax=yes",
				type:"post",
				data:{task_id:task_id},
				dataType:"json",
				success:function(datas){
					alert(datas.msg);
					Eop.Dialog.close("assign_div");
					window.location.reload();
				},
				error:function(a, b){
				
				}
			});	
		}
	
	});
}
</script>