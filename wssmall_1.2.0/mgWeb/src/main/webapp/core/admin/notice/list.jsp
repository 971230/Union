<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="notice/notice.js"></script>
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
	<form  method="post" action='noticeAction!list.do' id="noticeAddForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							公告标题:
						</th>
						<td>
							<input type="text" class="ipttxt" style="width: 90px"
								name="noticeTitle" value="${noticeTitle}" class="searchipt" />
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
		  <a href="noticeAction!add.do" id="addNoticeBtn"
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
<!-- 				<grid:cell>状态</grid:cell> -->
				<grid:cell>生效时间</grid:cell>
				<grid:cell>失效时间</grid:cell>
				<grid:cell>创建时间</grid:cell>
			
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="notice">
				<grid:cell><a href="noticeAction!detail.do?notice_id=${notice.notice_id }">${notice.title}</a></grid:cell>
				<grid:cell>${notice.user_name} </grid:cell>
				<grid:cell>${notice.org_name} </grid:cell>
<!-- 				<grid:cell> -->
<!-- 					<c:if test="${notice.state == '11'}"> -->
<!-- 						待审核 -->
<!-- 					</c:if> -->
<!-- 					<c:if test="${notice.state == '12'}"> -->
<!-- 						审核通过 -->
<!-- 					</c:if> -->
<!-- 					<c:if test="${notice.state == '13'}"> -->
<!-- 						审核不通过 -->
<!-- 					</c:if> -->
<!-- 				</grid:cell> -->
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
					<c:if test="${curUserId==notice.user_id || curUserId==notice.paruserid || curUserId=='1'}">
							<a title="编辑" href="noticeAction!edit.do?notice_id=${notice.notice_id }">修改 </a><span class='tdsper'></span>
					        <a title="删除" class="del" href="javascript:;" notice_id="${notice.notice_id }">删除</a>
					</c:if>
				</grid:cell>
			</grid:body>

		</grid:grid>

		
	</form>
	
</div>


