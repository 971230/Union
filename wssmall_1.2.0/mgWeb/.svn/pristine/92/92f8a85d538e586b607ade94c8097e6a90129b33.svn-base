<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="rightDiv">
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_1" class="selected">
						<span class="word">任务列表</span><span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
	
	<div class="table_list">
	    <form id="form_tc" class="grid">
			<grid:grid  from="webpage" ajax="yes">
				<grid:header>
				  	<grid:cell>任务名称</grid:cell> 
					<grid:cell>任务类型</grid:cell> 
					<grid:cell>任务目标值</grid:cell> 
					<grid:cell>已完成任务量</grid:cell>
					<grid:cell>开始日期</grid:cell> 
					<grid:cell>结束日期</grid:cell>
					<grid:cell>状态</grid:cell>
					<grid:cell>创建时间</grid:cell>
				</grid:header>
			    <grid:body item="obj">
				     <grid:cell>${obj.task_name}</grid:cell> 
				     <grid:cell>${obj.task_type_name}</grid:cell> 
				     <grid:cell>${obj.target_num}</grid:cell>
				     <grid:cell>${obj.finished_num}</grid:cell>
				     <grid:cell>${obj.begin_date}</grid:cell>
				   	 <grid:cell>${obj.end_date}</grid:cell>
				   	 <grid:cell>${obj.state_name}</grid:cell>
				   	 <grid:cell>${obj.create_time}</grid:cell>
			  </grid:body>  
			</grid:grid>
		</form>
	</div>
</div>
<div class="clear"></div>