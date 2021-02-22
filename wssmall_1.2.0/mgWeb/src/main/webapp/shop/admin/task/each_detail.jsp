<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                       <li id="show_click_2" class="selected"><span class="word">任务详情</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
        </h3>
 </div>
</h3>

<div class="input">
	<form method="post" name="theForm" id="theForm" class="validate">
		<div class="main-div">
			<table class="form-table" align="center">
			 <c:forEach items="${taskList}" var="item">
				<tr>
					<th>
						<label class="text">
							任务名称：
						</label>
					</th>
					<td valign="middle">
						&nbsp;
						<input type="text" class="ipttxt" id="task_name" name="task_name" value="${item.task_name}" disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							任务目标值：
						</label>
					</th>
					<td valign="middle">
						&nbsp;
						<input type="text" class="ipttxt" id="target_num" name="target_num" value="${item.target_num}" disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							分派任务数量：
						</label>
					</th>
					<td valign="middle">
						&nbsp;
						<input type="text" class="ipttxt" id="task_num" name="task_num" value="${item.task_num}" disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							已完成任务数量：
						</label>
					</th>
					<td valign="middle">
						&nbsp;
						<input type="text" class="ipttxt" id="finished_num"  name="finished_num" value="${item.finished_num}"  disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							所属地市：
						</label>
					</th>

					<td valign="middle">
						&nbsp;
						<input type="text" class="ipttxt" id="lan_name" name="lan_name" value="${item.lan_name}"  disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							开始时间：
						</label>
					</th>

					<td valign="middle">
						&nbsp;
						<input type="text" class="ipttxt" id="begin_date" name="begin_date" value="${item.begin_date}"  disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							结束时间：
						</label>
					</th>

					<td valign="middle">
						&nbsp;
						<input type="text" class="ipttxt" id="end_date" name="end_date" value="${item.end_date}"  disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							创建时间：
						</label>
					</th>

					<td valign="middle">
						&nbsp;
						<input type="text" class="ipttxt" id="create_time" name="create_time" value="${item.create_time}"  disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							任务状态：
						</label>
					</th>

					<td valign="middle">
						&nbsp;
						<input type="text" class="ipttxt" id="state_name" name="state_name" value="${item.state_name}"  disabled="true" />
					</td>
				</tr>
			</c:forEach>	
		</table>
	</div>

	</form>
</div>
<div id="boss_user_div"></div>
