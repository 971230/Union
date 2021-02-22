<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<script type="text/javascript" src="report/common/query_agent_dialog.js"></script>
<c:if test="${startTime == null || startTime == '' }">
	<c:set var="startTime" value="${current_day }" scope="page"/>
</c:if>
<c:if test="${endTime == null || endTime == '' }">
	<c:set var="endTime" value="${current_day }" scope="page"/>
</c:if>
<div class="searchformDiv">
	<table class="form-table">
		<tr>
			<th>开始时间：</th>
			<td>
				<input type="text"  name="startTime" id="starttime"
					value='${startTime}'
					maxlength="60" class="dateinput ipttxt" dataType="date" style="width: 90px;"/> 
			</td>		
			<th>结束时间：</th>
			<td>		
				<input type="text" name="endTime" id="endtime"
					value='${endTime}'
					maxlength="60" class="dateinput ipttxt" dataType="date" style="width: 90px;"/>
			</td>
			<th ${adminUser.founder==2 or adminUser.founder == 3 ? 'style="display:none;"' : ''}>分销商：</th> 
			<td ${adminUser.founder==2 or adminUser.founder == 3 ? 'style="display:none;"' : ''} >
			    <!-- 导出excel 默认为false -->
				<input type="hidden" name="isExport" id="isExport" value="false"/>
				<input type="hidden" name="userid" id="userid" value="${userid }"/>
				<input type="hidden" name="username" id="username" value="${username }"/>
				
				<input type='text' readonly="readonly" name='realname' id='realname' value="${realname}" style="width: 120px;" />
				<c:if test="${adminUser.founder==0 or adminUser.founder == 1}">
					<input type="button" id="refAgentBtn" value="选择" class="comBtn" style="width: 50px;"/>
					<input type="button" value="清空" onclick="clean_agent();" class="comBtn" style="width: 50px;"/>
				</c:if>
			</td>
			
			<!-- 电信员工可见 -->
			<c:if test="${lanList != null && fn:length(lanList) > 0 }">
				<th>本地网：</th>
				<td>
						<select  class="ipttxt"  style="width: 100px" id="lan_id" name="lan_id" >
							<c:forEach var="lan" items="${lanList}">
								<option  value="${lan.lan_id }" ${lan_id == lan.lan_id ? ' selected="selected" ' : ''}>${lan.lan_name }</option>
							</c:forEach>
					    </select>
				<td>
			</c:if>
			<th></th> 
			<td>
			  <input class="comBtn" type="submit" name="searchBtn"
				id="cloudInfoBtn" value="搜索" style="margin-right: 5px;" />
			</td>
		</tr>
	</table>
	
		
	<!-- 申请页面 -->
	<div id="refAgentDlg">
	</div>
	
</div>

