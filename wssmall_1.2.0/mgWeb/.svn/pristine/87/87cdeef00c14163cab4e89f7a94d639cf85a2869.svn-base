<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

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
	
	<form id="gridform" class="grid">
		<grid:grid from="webpage" ajax="yes">
			<grid:header>
				<grid:cell>公告标题</grid:cell>
			    <grid:cell>发布人</grid:cell>
				<grid:cell>所属组织</grid:cell>
				<grid:cell>生效时间</grid:cell>
				<grid:cell>失效时间</grid:cell>
				<grid:cell>创建时间</grid:cell>
			</grid:header>

			<grid:body item="notice">
				<grid:cell><a href="noticeAction!detail.do?notice_id=${notice.notice_id }">${notice.title}</a></grid:cell>
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
				
			</grid:body>

		</grid:grid>

		
	</form>
	
</div>


