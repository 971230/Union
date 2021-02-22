<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="rightDiv">
	
	<div class="grid">
	    <form id="form_tc" class="grid">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell>选择</grid:cell>
				  	<grid:cell>任务编号</grid:cell>
				  	<grid:cell>任务名称</grid:cell>
				  	<grid:cell>任务类型</grid:cell>
				  	<grid:cell>任务总数</grid:cell>
					<grid:cell>任务目标值</grid:cell>
					<grid:cell>区域任务分配总量（额）</grid:cell>
					<grid:cell>开始日期</grid:cell>
					<grid:cell>结束日期</grid:cell>
					<grid:cell>状&nbsp;&nbsp;态</grid:cell>
					<grid:cell>创建时间</grid:cell>
				</grid:header>
			    <grid:body item="obj">
				    <grid:cell>
			   		 	<input type="radio" name="selectTask" ele="${obj.task_id}__${obj.task_name}__${obj.task_num}__${obj.target_num}__${obj.target_rate}__${obj.task_type}__${obj.task_cate}__${obj.target_amount}__${obj.total_num}" />
			   		 </grid:cell> 
			    	<grid:cell>${obj.task_code}</grid:cell>
				    <grid:cell>${obj.task_name}</grid:cell>
				    <grid:cell>${obj.task_type_name}</grid:cell>
				    <grid:cell>${obj.task_num}</grid:cell>
				    <grid:cell>${obj.target_num}</grid:cell>
				    <grid:cell>${obj.total_num}</grid:cell>
				    <grid:cell>${obj.begin_date}</grid:cell>
				   	<grid:cell>${obj.end_date}</grid:cell>
				   	<grid:cell>${obj.state_name}</grid:cell>
				   	<grid:cell>${obj.create_time}</grid:cell>
			  </grid:body>  
			</grid:grid>
			<div align="center" style="margin-top:10px;">
					<input type="button" style="margin-top:10px;" class="comBtn" value="确&nbsp;定"  id="insureBtn" />
				</div>
		</form>
	</div>
	
</div>