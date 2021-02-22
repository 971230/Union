<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/core/common/commonlibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/task/js/task_split.js"></script> 
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                       <li id="show_click_2" class="selected"><span class="word">任务完成情况</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
        </h3>
 </div>
</h3>

<div class="input">
	<form method="post" name="theForm" id="theForm" class="validate">
			<table class="form-table" align="center">
				<tr>
					<th>
						<label class="text">
							任务名称：
						</label>
					</th>
					<td valign="middle">
						&nbsp;${task.task_name}
						<input type="hidden" id="task_id" name="task_id2" value="${task.task_id }">
						<input type="hidden" class="ipttxt" id="task_name" name="task_name" value="${task.task_name}" disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							任务总量：
						</label>
					</th>
					<td valign="middle">
						&nbsp;${task.task_num}
						<input type="hidden" class="ipttxt" id="task_num" name="task_num" value="${task.task_num}" disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							完成数量：
						</label>
					</th>
					<td valign="middle">
						&nbsp;
						${task.finished_num}
						<input type="hidden" class="ipttxt" id="finished_num" name="finished_num" value="${task.finished_num}" disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							距目标值的完成进度：
						</label>
					</th>
					<td valign="middle">
						&nbsp;
						<fmt:formatNumber value="${task.finished_num/task.target_num}" pattern="#.##%" minFractionDigits="2"></fmt:formatNumber>
						<input type="hidden" class="ipttxt" id="target_num" name="target_num" value="${task.finished_num/task.target_num}%" disabled="true" />
					</td>
				</tr>
				
		</table>
		<div class="comBtnDiv">
			<a href="javascript:void(0);" id="areaGroup" style="margin-right:10px;" class="graybtn1"><span>按区域</span></a>
			<a href="javascript:void(0);" id="partnerGroup" style="margin-right:10px;" class="graybtn1"><span>按分销商</span></a>
		</div>
	</form>
</div>
<c:choose>
	<c:when test="${groupBy=='region'}">
		<div id="regionList" class="grid">
			<form method="post" id="finishedTaskList" name="finishedTaskList" class="grid">
				<grid:grid from="taskList">
					<grid:header>
						<grid:cell>地市</grid:cell>
						<grid:cell>区县</grid:cell>
						<grid:cell>分配量</grid:cell>
						<grid:cell>完成数量</grid:cell>
						<grid:cell>距目标值的完成进度</grid:cell>
					</grid:header>
					<grid:body item="tasktail">
						<grid:cell>${tasktail.lan_name}</grid:cell>
						<grid:cell>${tasktail.city_name}</grid:cell>
						<grid:cell>${tasktail.task_num}</grid:cell>
						<grid:cell>${tasktail.finished_num}</grid:cell>
						<grid:cell>${tasktail.finished_rate}</grid:cell>
					</grid:body>
				</grid:grid>
			</form>
		</div>
	</c:when>
	<c:otherwise>
		<div id="partnerList"  class="grid">
			<form method="post"  id="partnerFinishedList" name="partnerFinishedList" class="grid">
				<grid:grid from="partner_list">
					<grid:header>
						<grid:cell>分销商工号</grid:cell>
						<grid:cell>分销商名称</grid:cell>
						<grid:cell>分配量</grid:cell>
						<grid:cell>完成数量</grid:cell>
						<grid:cell>距目标值的完成进度</grid:cell>
					</grid:header>
					<grid:body item="partDetail">
						<grid:cell>${partDetail.user_name}</grid:cell>
						<grid:cell>${partDetail.real_name}</grid:cell>
						<grid:cell>${partDetail.task_num}</grid:cell>
						<grid:cell>${partDetail.finished_num}</grid:cell>
						<grid:cell>${partDetail.finished_rate}</grid:cell>
					</grid:body>
				</grid:grid>
			</form>
		</div>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
	$(function(){
		$("#areaGroup").click(function(){
			var task_id = $("#task_id").val();
			document.location.href=ctx+"/shop/admin/task!showTask.do?groupBy=region&task_id="+task_id;
		});
		
		$("#partnerGroup").click(function(){
			var task_id = $("#task_id").val();
			document.location.href=ctx+"/shop/admin/task!showTask.do?groupBy=partner&task_id="+task_id;
		});
	});
</script>
