<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/cms/js/notice.js"></script>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div>
	<form  method="post" action='ottNotice!listOttNotice.do' id="noticeAddForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							公告标题:
						</th>
						<td>
							<input type="text" class="ipttxt" style="width: 120px"
								name="notice.title" value="${notice.title}" class="searchipt" />
						</td>
						
						<th>
							生效时间:
						</th>
						<td>
<!-- 							<input type="text" name="notice.begin_time"  -->
<!-- 								value='<html:dateformat pattern="yyyy-MM-dd" d_time="${notice.begin_time}" />'   -->
<!-- 								maxlength="60" class="dateinput ipttxt" />  -->
								
							<input type="text" class="ipttxt searchipt" dataType="date"
								style="width: 120px" name="notice.begin_time"
								value="${notice.begin_time}" />
						</td>
						
						<th>
							失效时间:
						</th>
						<td>
<!-- 							<input type="text" name="notice.end_time"  -->
<!-- 								value='<html:dateformat pattern="yyyy-MM-dd" d_time="${notice.end_time }" />'   -->
<!-- 								maxlength="60" class="dateinput ipttxt" />  -->
								
							<input type="text" class="ipttxt searchipt" dataType="date"
								style="width: 120px" name="notice.end_time"
								value="${notice.end_time}" />
						</td>
						
						<td>
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="comBtnDiv">
		  <a href="ottNotice!addOttNotice.do?n_action=add" id="addNoticeBtn"
					style="margin-right: 10px;" class="graybtn1"><span>添加</span>
		  </a>
			
		</div>
		
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" ajax="yes">
			<grid:header>
				<grid:cell sort="company_name">公告标题</grid:cell>
			    <grid:cell>发布人</grid:cell>
				<grid:cell>所属组织</grid:cell>
				<grid:cell>生效时间</grid:cell>
				<grid:cell>失效时间</grid:cell>
				<grid:cell>创建时间</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="notice">
				<grid:cell><a href="ottNotice!detail.do?notice_id=${notice.notice_id }">${notice.title}</a></grid:cell>
				<grid:cell>${notice.user_name} </grid:cell>
				<grid:cell>${notice.org_name} </grid:cell>
			    <grid:cell>
					<html:dateformat pattern="yyyy-MM-dd" d_time="${notice.begin_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd" d_time="${notice.end_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd" d_time="${notice.create_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>
					<c:if test="${curUserId==notice.user_id||founder==0 || curUserId == notice.paruserid}"><!-- 0:电信员工 -->
							<a title="编辑" href="ottNotice!addOttNotice.do?notice_id=${notice.notice_id }&n_action=edit">修改 </a><span class='tdsper'></span>
					        <a title="删除" class="del" href="javascript:;" notice_id="${notice.notice_id }">删除</a><span class='tdsper'></span>
					</c:if>
					<a title="置顶" href="ottNotice!sortOttNotice.do?notice_id=${notice.notice_id}">置顶</a>
				</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>
<script>
	$(function() {
		$("input[dataType='date']").datepicker();
	});
</script>

