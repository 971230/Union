<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="auth/checktree.css" />
<!-- <script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="../../mgWeb/menu.do"></script>
<script type="text/javascript" src="js/Auth.js"></script> -->

<!-- 添加人员组织关系页面 -->
<div class="searchformDiv">
<form method="post" id="OrganizationForm" action="">
<table>
	<tr>
	    <th>编码：</th>
		<td><input type="text" class="ipttxt" style="width:200px;" name=organizationTemp.org_code  id ="organizationTemp.org_code" value = "${organizationTemp.org_code}"/></td>
	    <th>名称：</th>
		<td><input type="text" class="ipttxt" style="width:200px;"  name="organizationTemp.org_name"  id ="organizationTemp.org_name" value = "${organizationTemp.org_name}"/></td>
		<th></th>
		<td>
	    <input class="comBtn"  type="button" name="searchOrganizationBtn" id="searchOrganizationBtn"  style="width:90px;" value="查询"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          style ="margin-                             right:10px;"/>
		</td>
		<th></th>
		<td>
	    <input class="comBtn"  type="button" name="sure" id = "sure" style="width:90px;" value="确定"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           style ="margin-                             right:10px;"/>
		</td>
	</tr>
 </table>
</form>
</div>
<div class="grid" >
<grid:grid  from="webpage" ajax="yes" formId ="OrganizationForm">
	<grid:header>
	<grid:cell width="10%">选择</grid:cell> 
	<grid:cell width="10%">组织编码</grid:cell> 
	<grid:cell width="15%">组织名称</grid:cell> 
	</grid:header>
  <grid:body item="organizationTempObj">
        <grid:cell>
        <input type="radio" name="organization_radio" org_code = "${organizationTempObj.org_code}" org_name = "${organizationTempObj.org_name}">
        </grid:cell>
        <grid:cell>${organizationTempObj.org_code}</grid:cell>
        <grid:cell>${organizationTempObj.org_name}</grid:cell> 
  </grid:body>  
</grid:grid>  
</div>
<script>
$(function() {
	$("#sure").click(function(){
		 var checked = $("input[name='organization_radio']:checked").val();
		 if(typeof(checked) == "undefined"){
			alert("未选中");
			return ;
		}  
		 var org_code = $("input[name='organization_radio']:checked").attr("org_code");
		 var org_name = $("input[name='organization_radio']:checked").attr("org_name");
		var method='setOrganization';
		eval(method+"('"+org_code+"','"+ org_name  + "')");
		Eop.Dialog.close('open_choose_organization_window'); 
	});
	$("#searchOrganizationBtn").click(function(){
		var url=ctx+"/shop/admin/workerPoolAction!workerOrganizationList.do?ajax=yes";
		Cmp.ajaxSubmit('OrganizationForm','open_choose_organization_window',url,{},function(){
		});
	})
});
function setOrganization (org_code,org_name){
	$("[name='add_org_code']").val(org_code);
	$("[name='add_org_name']").val(org_name);
}
</script>