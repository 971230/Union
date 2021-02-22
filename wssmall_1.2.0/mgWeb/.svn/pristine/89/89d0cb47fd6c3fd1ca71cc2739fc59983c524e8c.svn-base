<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/core/common/commonlibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/task/js/task_split.js"></script> 
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                       <li id="show_click_2" class="selected"><span class="word">任务分解</span><span class="bg"></span></li>
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
						&nbsp;
						
						<input type="text" class="ipttxt" id="task_name" name="task_name" value="${task.task_name}" disabled="true" />
						<input type="hidden" id="task_id" name="task_id2" value="${task.task_id }">
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							任务总量：
						</label>
					</th>
					<td valign="middle">
						&nbsp;
						<input type="text" class="ipttxt" id="task_num" name="task_num" value="${task.task_num}" disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							可分派任务总量：
						</label>
					</th>
					<td valign="middle">
						&nbsp;
						<input type="text" class="ipttxt" id="target_num" name="target_num" value="${task.target_num}" disabled="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							
						</label>
					</th>
					<td valign="middle">
						<input type="checkbox" name="average" id="average" value="1"/> 
						平均分配任务
						<input type="checkbox" name="sameArea" id="sameArea" value="1" />
						统一设定分配区域
					</td>
				</tr>
		</table>
		<div class="comBtnDiv">
			<a href="javascript:void(0);" id="addBtn" style="margin-right:10px;" class="graybtn1"><span>添加分销商</span></a>
		</div>
	</form>
</div>
<div  id="taskList" class="grid">
	
	<form action="task!savePartnerTask.do" method="post" id="partnerList" name="taskDetailForm" class="grid">
		<grid:grid from="catList">
			<grid:header>
				<grid:cell>分销商工号</grid:cell>
				<grid:cell>分销商名称</grid:cell>
				<grid:cell>分配地市</grid:cell>
				<grid:cell>分配县区</grid:cell>
				<grid:cell>任务分配量(额)</grid:cell>
				<grid:cell width="90px">操作</grid:cell>
			</grid:header>
			<grid:body item="adminUser">
				<grid:cell><input type="hidden" name="userid" class="userid" value="${adminUser.userid}" />${adminUser.username}</grid:cell>
				<grid:cell>${adminUser.realname}</grid:cell>
				<grid:cell><input type="hidden" name="lan_id" class="lan_id" value="${adminUser.lan_id }" /><input name="lan_name" class="ipttxt lan_name" value="${adminUser.lan_name}" /></grid:cell>
				<grid:cell><input type="hidden" name="city_id" class="city_id" value="${adminUser.city_id }" /><input  name="city_name" class="ipttxt city_name" value="${adminUser.city_name}" /></grid:cell>
				<grid:cell><input type="text" class="ipttxt task_number" value="${adminUser.task_number}" /></grid:cell>
				<grid:cell>
					<a href="javascript:void(0);" name="removeBtn">删除</a>
				</grid:cell>
			</grid:body>

		</grid:grid>
		<div class="comBtnDiv" align="center">
			<input type="button" style="margin-right:10px;" class="comBtn" value="保存"  id="saveBtn" >
			<input type="button" style="margin-right:10px;" class="comBtn" value="重置"  id="resetBtn" >
		</div>
	</form>
</div>
<div id="refPartnerDlg"></div>
<div id="assignDiv"></div>
