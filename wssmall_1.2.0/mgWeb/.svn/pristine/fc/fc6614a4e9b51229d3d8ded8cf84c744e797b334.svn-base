<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="rightDiv" id="rightDiv">
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_1" class="selected">
						<span class="word">任务下发</span><span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
	
	<div>
		<form method="post" id="tcForm">
			<div class="searchformDiv">
			 <table>
			 	<input type="hidden" id="task_id" value="${task.task_id}" />
				<tr>
					<th>任务编号：</th>
					<td><input type="text" class="ipttxt" name="task.task_code" id="task_code" value="${task.task_code}" /></td>
					<th>任务名称：</th>
					<td>
						<input type="text" class="ipttxt"  name="task.task_name" id ="task_name"  value="${task.task_name}" />
					</td>
					<th>任务总量：</th>
					<td>
				    	<input type="text" class="ipttxt" name="task.task_num" id="task_num" value="${task.task_num}" />
					</td>
				</tr>
				<tr>
					<th>目标任务量：</th>
					<td>
						<input type="text" class="ipttxt"  name="task.target_num" id ="target_num"  value="${task.target_num}" />
					</td>
					<th>任务类型：</th>
					<td colspan="3">
				    	<input type="text" class="ipttxt" name="task_type_name" id="task_type_name" value="${task_type_name}" />
					</td>
				</tr>
			  </table>	
			</div>
	    </form>   
	</div>
	<div class="grid">
	    <form id="form_tc" class="grid">
			<div class="gridbody"  style="height:300px;overflow:scroll;">
				<table>
					<thead>
						<tr>
							<th>详情编号</th>
					  		<th>分销商工号</th>
					  		<th>分销商名称</th>
					  		<th>分配区域</th>
					  		<th>分配任务量</th>
						</tr>
					</thead>
				    <tbody>
					    <c:forEach items="${taskList}" var="ite">
					    	<tr>
					    		<td>${ite.detail_id}</td>
					    		<td>${ite.username}</td>
						    	<td>${ite.realname}</td>
						    	<td>${ite.task_city}</td>
						    	<td>${ite.task_number}</td>
					  		</tr>
					  	</c:forEach>	
				  	</tbody>  
				</table>
			</div>
		</form>
	</div>
	<div align="center">
		<input type="button" value="确认下发" id="open_account" class="graybtn1" style="margin-top:8px;margin-bottom:8px;"/>
	</div>
</div>