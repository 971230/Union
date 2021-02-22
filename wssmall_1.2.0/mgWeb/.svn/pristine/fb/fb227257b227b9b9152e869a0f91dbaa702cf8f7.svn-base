<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="fck"%>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="show_click_2" class="selected"><span class="word">新增广告列表</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form class="validate" method="post" action="adv!addSave.do"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<table cellspacing="1" cellpadding="3" width="100%" class="form-table"
			align="center">

			<tr>
				<th class="label"><label class="text">广告名称：</label></th>
				<td><input type="text" class="ipttxt" name="adv.aname"
					maxlength="60" value="" dataType="string" required="true" /></td>
			</tr>
			<tr>
				<th class="label"><label class="text">所属广告位：</label></th>
				<td><select class="ipttxt" name="adv.acid">
						<c:forEach var="item" items="${adColumnList }">
							<option value="${item.acid }">${item.cname }</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th class="label"><label class="text">发布渠道：</label></th>
				<td><select class="ipttxt" name="adv.source_from">
						<c:forEach var="sourceFrom" items="${sourceFromList}">
							<option value="${sourceFrom.pkey}">${sourceFrom.pname}</option>
						</c:forEach>
				</select></td>
			</tr>

			<tr>
				<th class="label"><label class="text">分辨率：</label></th>
				<td><select class="ipttxt" name="adv.resol">
						<c:forEach var="item" items="${resolList}">
							<option value="${item.pkey}">${item.pname}</option>
						</c:forEach>
				</select></td>
			</tr>

			<tr>
				<th><label class="text">是否开启：</label></th>
				<td><input type="radio" name="adv.isclose" value="0" checked />
					开启&nbsp;&nbsp; <input type="radio" name="adv.isclose" value="1" />
					关闭&nbsp;&nbsp;<span class="help_icon" helpid="ad_isclose"></span></td>
			</tr>

			<tr>
				<th><label class="text">起始时间：</label></th>
				<td><input type="text" class="dateinput" class="ipttxt"
					name="mstartdate" maxlength="60" value="" dataType="date"
					required="true" class="dateinput" /></td>
			</tr>
			<tr>
				<th><label class="text">终止时间：</label></th>
				<td><input type="text" class="dateinput" class="ipttxt"
					name="menddate" maxlength="60" value="" dataType="date"
					required="true" class="dateinput" /></td>
			</tr>
			<tr>
				<th><label class="text">广告链接：</label></th>
				<td><input type="text" class="ipttxt" name="adv.url"
					maxlength="60" value="" dataType="string" /></td>
			</tr>
			<tr>
				<th><label class="text">上传广告文件：</label></th>
				<td><input type="file" name="pic" id="pic" size="45" />&nbsp;&nbsp;<span
					class="help_icon" helpid="ad_file"></span></td>
			</tr>
			<tr>
				<th><label class="text">单位名称：</label></th>
				<td><input type="text" class="ipttxt" name="adv.company"
					value="" dataType="string" />&nbsp;&nbsp;<span class="help_icon"
					helpid="ad_link"></span></td>
			</tr>

			<tr>
				<th><label class="text">联系人：</label></th>
				<td><input type="text" class="ipttxt" name="adv.linkman"
					value="" dataType="string" />&nbsp;&nbsp;<span class="help_icon"
					helpid="ad_link"></span></td>
			</tr>

			<tr>
				<th><label class="text">联系方式：</label></th>
				<td><input type="text" class="ipttxt" name="adv.contact"
					value="" dataType="string" />&nbsp;&nbsp;<span class="help_icon"
					helpid="ad_link"></span></td>
			</tr>

		</table>
		<div class="submitlist" align="left">
			<table>
				<tr>
					<th></th>
					<td><input type="submit" value=" 确    定   " class="submitBtn" />
					</td>
				</tr>
			</table>
		</div>
	</form>

	<script type="text/javascript">
		$("form.validate").validate();
	</script>
</div>