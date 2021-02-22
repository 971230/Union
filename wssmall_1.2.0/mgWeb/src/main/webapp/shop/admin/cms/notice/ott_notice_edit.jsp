<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="fck"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/shop/admin/cms/js/notice_edit.js"></script>

<div class="input">
	<form class="validate" method="post" name="editNoticeForm"
		id="editNoticeForm" enctype="multipart/form-data">
		<table cellspacing="1" cellpadding="3" width="100%" class="form-table"
			align="center">
			<input type="hidden" value="${notice.notice_id }"
				name="notice.notice_id"/>
			<input type="hidden" value="${n_action}"
				name="n_action"/>
			<tr>
				<th class="label"><label class="text">公告标题：</label></th>
				<td><input type="text" class="ipttxt" name="notice.title"
					maxlength="60" value="${notice.title }" dataType="string"
					required="true" /></td>
			</tr>

			<tr>
				<th><label class="text">生效时间：</label></th>
				<td><input type="text" class="dateinput" class="ipttxt"
					name="notice.begin_time" maxlength="60"
					value="<html:dateformat pattern="yyyy-MM-dd" d_time="${notice.begin_time}"></html:dateformat>"
					dataType="date" required="true" class="dateinput" /></td>
			</tr>
			<tr>
				<th><label class="text">失效时间：</label></th>
				<td><input type="text" class="dateinput" class="ipttxt"
					name="notice.end_time" maxlength="60"
					value="<html:dateformat pattern="yyyy-MM-dd" d_time="${notice.end_time}"></html:dateformat>"
					dataType="date" required="true" class="dateinput" /></td>
			</tr>
			<tr>
				<th><label class="text">发布模块：</label></th>
				<td>
					<c:forEach items="${modual_list}" var="list">
<!-- 						<label> -->
							<c:if test="${list.flag=='1' }">
								<input type="checkbox" style="outline-style: none;" name="modual_ids"
									value="${list.modual_id }" checked="checked"/>
							</c:if>
							<c:if test="${list.flag=='0' }">
								<input type="checkbox" style="outline-style: none;" name="modual_ids"
									value="${list.modual_id }" />
							</c:if>
							${list.modual_name}<span style="color: red;">(上限${list.notice_num}条)</span>&nbsp;&nbsp;
<!-- 						</label> -->
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th><label class="text">内容：</label></th>
				<td>
					<div class="inputBox">
						<textarea class="textareaClass" name="notice.content" id='content'
							required="true" cols="100" rows="8"
							style="width: 98%; height: 300px;">
								${notice.content}
		  </textarea>
					</div>
				</td>
			</tr>

		</table>
		<div class="submitlist" align="left">
			<table>
				<tr>
					<th></th>
					<td><input type="button" id="noticeEditvSaveBtn"
						value=" 确    定   " class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>
<div id="cms_modual_list"></div>
</div>

