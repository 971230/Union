<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<%
String path= request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

AdminUser adminUser=ManagerUtils.getAdminUser();

String theme_source_from = "";

if(null != adminUser){
	theme_source_from =  adminUser.getTheme_source_from();
}
%>

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/ztp-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/api.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/SelectTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/adapters/jquery.js"></script>

<link href="${context}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/input.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/validate.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/dialog.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/style_n.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />

<link href="<%=request.getContextPath()%>/mgWeb/css/alert.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">
var theme_source_from = '<%=theme_source_from%>';
var app_path="<%=path%>";
var basePath="<%=basePath%>";
var mainpage=false;
</script>

<style>

  .clickClass{
     background:#f7f7f7
  }
  
</style>


<script type="text/javascript">
var theme_source_from = '<%=theme_source_from%>';
var app_path="<%=path%>";
var basePath="<%=basePath%>";
var mainpage=false;
</script>

<body style="min-width: 150px;background: white;">
	<form method="post" id="orgSearchform" action='<%=request.getContextPath()%>/core/admin/org/orgAdmin!searchOrgTable.do?'>
		<input type="hidden" class="ipttxt"  name="party_id"  value="${party_id}"/>
		<input type="hidden" class="ipttxt"  name="org_name"  value="${org_name}"/>
		<input type="hidden" class="ipttxt"  name="hq_dept_id"  value="${hq_dept_id}"/>
		<input type="hidden" class="ipttxt"  name="dept_id"  value="${dept_id}"/>
		<input type="hidden" class="ipttxt"  name="lan_id"  value="${lan_id}"/>
		<input type="hidden" class="ipttxt"  name="region_id"  value="${region_id}"/>
	</form>

	<div id="org_table_div" class="grid" >
		<form method="POST"  id="orgInfoForm" >
			<grid:grid from="webpage"  formId="orgSearchform" ajax="yes">
				<grid:header>
					<grid:cell width="25%">组织编码</grid:cell> 
					<grid:cell width="30%">组织名称</grid:cell> 
					<grid:cell width="25%">组织类型</grid:cell> 
					<grid:cell width="20%">操作</grid:cell> 
				</grid:header>
				
				<grid:body item="obj">
					<grid:cell>${obj.party_id}</grid:cell>
					<grid:cell>${obj.org_name}</grid:cell>
					<grid:cell>${obj.org_type_name}</grid:cell>
					<grid:cell>
						<a onclick="setDealerOrg('${obj.party_id}','${obj.org_name}');">设为处理组织</a>
					</grid:cell>
				</grid:body>  
			</grid:grid>
		</form>
	</div>
</body>

<script type="text/javascript">
function setDealerOrg(orgId,orgName){
	if(window.parent.WorkFlowTool){
		window.parent.WorkFlowTool.setDealerOrg(orgId,orgName);
	}else{
		alert("修改失败，选择组织的JS方法为空");
	}
}
</script>

