<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
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
		<form action="/shop/admin/task!assignTask.do" method="post" id="tcForm">
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
	<div class="table_list">
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
				   		<a onclick="javascript:revokTask('${obj.task_id}','${obj.task_code}','${obj.task_name}');return false;">撤销</a>
				   	</grid:cell>
			  </grid:body>  
			</grid:grid>
		</form>
	</div>
	
</div>
<script type="text/javascript">

function revokTask(task_id, task_code, task_name){
 	if(confirm("编号为" + task_code + "，名称为" + task_name + "的任务即将撤销，撤销后将不再进行跟踪监控")){
		$.ajax({
			url: ctx + "/shop/admin/task!revokeTask.do?ajax=yes",
			type:"post",
			data:{task_id:task_id},
			dataType:"json",
			success:function(datas){
				alert(datas.msg);
				window.location.reload();
			},
			error:function(a, b){
			
			}
		});	
	}
}
</script>