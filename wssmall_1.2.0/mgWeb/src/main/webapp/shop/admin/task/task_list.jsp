<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/task/js/task_list.js"></script>
<div id="taskList"  class="table_list">
	<form method="post" class="grid">
		<grid:grid from="webpage" formId="searchTasksListForm" ajax="yes">
			<grid:header>
				<grid:cell>
						选择<!-- <input type="checkbox" id="toggleChk" /> -->
				</grid:cell>
				<grid:cell>任务编号</grid:cell>
				<grid:cell>任务名称</grid:cell>
				<grid:cell>任务类型</grid:cell>
				<grid:cell>分配范围</grid:cell>
				<grid:cell>任务总量</grid:cell>
				<grid:cell>任务目标值</grid:cell>
				<grid:cell>任务状态</grid:cell>
				<grid:cell>完成状态</grid:cell>
				<grid:cell>任务生成时间</grid:cell>
				<grid:cell>操&nbsp;&nbsp;作</grid:cell>
			</grid:header>
			<grid:body item="task">
				<grid:cell>
					<input type="radio" name="id" value="${task.task_id }" taskState="${task.state}" adminUser="${adminUser.userid }" taskCode="${task.task_code }" taskName="${task.task_name }" />
					<input type="hidden" name="op_id" value="${task.op_id }"/>
				</grid:cell>
				<grid:cell>${task.task_code }</grid:cell>
				<grid:cell>${task.task_name }</grid:cell>
				
				<grid:cell>
					${task.task_type}
				</grid:cell>
				<grid:cell>${task.lan_name }&nbsp;${task.region_name }</grid:cell>
				<grid:cell>${task.task_num }</grid:cell>
				<grid:cell>${task.target_num }</grid:cell>
				<grid:cell>
					<c:if test="${task.state == '001'}">已录入</c:if>
					<c:if test="${task.state == '002'}">已分解</c:if>
					<c:if test="${task.state == '003'}">已下发</c:if>
					<c:if test="${task.state == '004'}">已撤销</c:if>
					<c:if test="${task.state == '005'}">已删除</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${task.finished=='0' }">未完成</c:if>
					<c:if test="${task.finished=='1' }">已完成</c:if>
				</grid:cell>
				<grid:cell><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${task.create_time}"></html:dateformat></grid:cell>
				<grid:cell>
					<c:choose>
						<c:when test="${task.state=='001'}">
							<a href="task!taskSplit.do?task_id=${task.task_id }">分解</a><span class='tdsper'></span>
						</c:when>
						<c:when test="${task.state=='002'}">
							<a href="task!taskSplit.do?task_id=${task.task_id }">分解</a><span class='tdsper'></span>
						</c:when>
					</c:choose>
					
					<a href="<%=request.getContextPath() %>/shop/admin/task!showTask.do?groupBy=region&task_id=${task.task_id }">查看</a>
				</grid:cell>
			</grid:body>

		</grid:grid>
		<input type="hidden" name="join_suced" value="${join_suced}" />
	</form>
</div>
<div id="assignDiv"></div>
