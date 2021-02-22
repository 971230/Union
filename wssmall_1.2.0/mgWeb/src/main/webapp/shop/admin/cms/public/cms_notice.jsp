<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method='post' id='notice_form' action='cmsObj!getNotice.do'>
	<div class='searchformDiv'>
		<table width='100%' border='0' cellspacing='0' cellpadding='0'>
			<tbody>
				<tr>
					<th>标题:</th>
					<td><input type='text' name='notice_name'
						value='${notice_name}' class='searchipt'></td>
					<td style='text-align:center;'><a href='javascript:void(0);' class='searchBtn' id='search_notice'><span>查&nbsp;询</span></a></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<div id="ruleList">
	<div class="grid">
		<form method="POST" id="notice_form">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell style="width: 80px;">选择</grid:cell>
					<grid:cell style="width: 100px;">公告标题</grid:cell>
					<grid:cell style="width: 100px;">公告内容</grid:cell>
					<grid:cell style="width: 80px;">创建时间</grid:cell>
				</grid:header>
				<grid:body item="notice">
					<grid:cell>
						<input type="radio" name="check_notice"/>
						<input type="hidden" name='notice_id' value='${notice.notice_id}'/>
						<input type="hidden" name='title' value='${notice.title}'/>
						<input type="hidden" name='end_time' value='${notice.end_time}'/>
						<input type="hidden" name='content' value='${notice.content}'/>
						<input type="hidden" name='user_id' value='${notice.user_id}'/>
						<input type="hidden" name='create_time' value='${notice.create_time}'/>
						<input type="hidden" name='state' value='${notice.state}'/>
						<input type="hidden" name='source_from' value='${notice.source_from}'/>
						<input type="hidden" name='begin_time' value='${notice.begin_time}'/>
						<input type="hidden" name='pubilsh_org_id' value='${notice.pubilsh_org_id}'/>
					</grid:cell>
					<grid:cell>${notice.title}</grid:cell>
					<grid:cell>${notice.content}</grid:cell>
					<grid:cell>
						<html:dateformat pattern="yyyy-MM-dd" d_time="${notice.create_time}"></html:dateformat>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
