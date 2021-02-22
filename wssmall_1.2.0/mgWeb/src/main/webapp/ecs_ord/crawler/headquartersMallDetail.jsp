<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="auth/checktree.css" />
<script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="../../mgWeb/menu.do"></script>
<script type="text/javascript" src="js/Auth.js"></script>
<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/ordAuto!editHeadquartersMallStaff.do' class="validate" method="post" name="theForm" id="theForm" enctype="multipart/form-data">
<div >
 <table>
	<tr>
	    <th><label class="text"><span class="red">*</span>用户名：</label></th>
		<td>
			<input type="hidden" class="ipttxt"  name="headquartersMall.staff_code"  id ="staff_code"  value="${headquartersMall.staff_code}"  />
			<input type="text" class="ipttxt"  name="staff_code" value="${headquartersMall.staff_code}"  disabled="disabled" datatype="String"  class="ipttxt fail" value="" required="true" />
		</td>
	</tr>
	
		<tr>
	    <th><label class="text"><span class="red">*</span>密码：</label></th>
		<td>
			<input type="password" class="ipttxt"  name="headquartersMall.password" value="${headquartersMall.password}" datatype="String"  class="ipttxt fail" value="" required="true" />
		</td>
	</tr>
	
	<tr>
	    <th>用户名称：</th>
		<td><input type="text" class="ipttxt"  name="headquartersMall.staff_name"  id ="staff_name"  value="${headquartersMall.staff_name}"  /></td>
	</tr>
	
	<tr>
	    <th><label class="text"><span class="red">*</span>用户ID：</label></th>
		<td><input type="text" class="ipttxt"  name="headquartersMall.staff_id"  id ="staff_id"  value="${headquartersMall.staff_id}" datatype="String"  class="ipttxt fail" value="" required="true"  /></td>
	</tr>
	
	<tr>
	    <th>验证码：</th>
		<td><input type="text" class="ipttxt"  name="headquartersMall.message_code"  id ="message_code"  value="${headquartersMall.message_code}"  /></td>
	</tr>
	
	<tr>
	    <th>cookie：</th>
		<td><input type="text" class="ipttxt"  name="headquartersMall.cookie"  id ="cookie"  value="${headquartersMall.cookie}"  /></td>
	</tr>
	
	<tr>
	    <th>描述：</th>
		<td><input type="text" class="ipttxt"  name="headquartersMall.remark"  id ="remark"  value="${headquartersMall.remark}"  /></td>
	</tr>
	<tr>
		<td>
		    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="保存" style="margin-right:10px;"/>
		</td>
		<td>
		    <input class="comBtn" type="button" name="backBtn" id="back" value="返回" onClick="javascript :history.back(-1);" style="margin-right:10px;"/>
		</td>
	</tr>
 </table>
</div>
</form>
<div class="grid">
<div style="clear:both;padding-top:5px;"></div>
</div>

<div id="actionDlg"></div>