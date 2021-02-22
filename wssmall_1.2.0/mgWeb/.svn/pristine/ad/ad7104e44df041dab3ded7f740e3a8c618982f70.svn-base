<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.contentPanel{ position:relative; padding-left:212px;}
.contentPanel .leftPanel{ width:212px; padding:2px; position:absolute; left:0px; top:0px;}
.contentPanel .rightPanel{ min-height:600px;}
</style>

<script type="text/javascript" src="<%=request.getContextPath() %>/core/admin/user/orgUser/orgUser.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/core/admin/user/orgUser/selector_agent.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/core/admin/user/orgUser/selector_supplier.js"></script>


<div id='search_form' style='width:100%;height:10%;'>
	<form method="post" id="serchform" >
		 <div class="searchformDiv">
		 <table>
			<tr>
				<th>组织名称：</th>
				<td><input type="text" class="ipttxt"  name="org_name" disabled="true" id="org_name" value="${org_name}"/></td><td><input id ="orgSel" type="button" value=" . . " />
				   </td>
				 <input type="hidden" value="" id ="org_id" name="org_id"/>
				
				<td>
				 <th>用户工号：</th>
				<td><input type="text" class="ipttxt"  name="username" id ="username"  value="${username}"/></td>
				 <th>用户名称：</th>
				<td><input type="text" class="ipttxt"  name="realname" id ="realname"  value="${realname}"/></td>
				<th>启用状态：</th>
				<td>
				<html:selectdict curr_val="${state}"  style="width:90px" id="state" name ="state"  attr_code="DC_USER_ADMIN_STATE"></html:selectdict>
		        </td>
				
				<td>
				<th></th>
				<td>
			    <input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
				</td>
				<td>
				   </td>
			</tr>
			   <div style="clear:both"></div>
		  </table>	
		</div>
     </form>
</div>

<div class="contentPanel" style='padding-left:450px;'>
	<div id='left_panel'  class="leftPanel" style="width:450px;border:1px solid #F7F9F9;">
		<iframe style="height:730px;width:450px;" id="orgListIframe" src="<%=request.getContextPath() %>/core/admin/user/userAdmin!getOrg.do?ajax=yes"></iframe>
	</div>
	
	<div id='right_panel' class="rightPanel" >
	     <div id="userPanle" style='height:200px;'>
	          <iframe style="height:100%;width:100%;" name="userListIframe" id="userListIframe" src="<%=request.getContextPath() %>/core/admin/user/userAdmin!orgUserList.do?org_id="></iframe>
	     </div>
	     
	     <div class='clear'></div>
	     
	     <div id="userDetail" >
	        <jsp:include page="userDetail.jsp" />
	     </div>
	     
	</div>
</div>
<div id="refOrgDlg"></div>
<div id="refAgentDlg"></div>
<div id="refSupplierDlg"></div>
<!-- <div id="refStaffDlg"></div> -->
<div id="selRoleDlg"></div>
<div class='clear'></div>
