<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="fck"%>
<script type="text/javascript"
	src='<%=request.getContextPath()%>/shop/admin/cms/js/notice_add.js'></script>

<div class="input">
	<form class="validate" method="post" name="addNoticeForm"
		id="addNoticeForm" enctype="multipart/form-data">
		<table cellspacing="1" cellpadding="3" width="100%" class="form-table"
			align="center">
			<tr>
				<th class="label"><label class="text">公告标题：</label></th>
				<td><input type="text" class="ipttxt" name="notice.title"
					maxlength="60" value="" dataType="string" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text">生效时间：</label></th>
				<td><input type="text" class="dateinput" class="ipttxt"
					name="notice.begin_time" maxlength="60" value="" dataType="date"
					required="true" class="dateinput" /></td>
			</tr>
			<tr>
				<th><label class="text">失效时间：</label></th>
				<td><input type="text" class="dateinput" class="ipttxt"
					name="notice.end_time" maxlength="60" value="" dataType="date"
					required="true" class="dateinput" /></td>
			</tr>
			
			<tr>
				<th><label class="text">内容：</label></th>
				<td>
					<div class="inputBox">
						<textarea class="textareaClass" name="notice.content" id='content'
							required="true" cols="100" rows="8"
							style="width: 98%; height: 300px;">
									
		  </textarea>
					</div>
				</td>
			</tr>

		</table>
		<div class="submitlist" align="left">
			<table>
				<tr>
					<th></th>
					<td><input type="button" id="noticeAddSaveBtn"
						value=" 确    定   " class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>

</div>