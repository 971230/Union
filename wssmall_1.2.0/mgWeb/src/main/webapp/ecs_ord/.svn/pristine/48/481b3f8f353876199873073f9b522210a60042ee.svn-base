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
	<form method="post" id="orgSearchform" action='<%=request.getContextPath()%>/shop/admin/WorkCustom!getUserTeamTable.do?'>
		<input type="hidden" class="ipttxt"  name="team.team_id"  value="${team.team_id}"/>
		<input type="hidden" class="ipttxt"  name="team.team_name"  value="${team.team_name}"/>
		<input type="hidden" class="ipttxt"  name="team.team_level"  value="${team.team_level}"/>
		<input type="hidden" class="ipttxt"  name="team.region_id"  value="${team.region_id}"/>
		<input type="hidden" class="ipttxt"  name="team.county_id"  value="${team.county_id}"/>
	</form>

	<div id="org_table_div" class="grid" >
		<form method="POST"  id="orgInfoForm" >
			<grid:grid from="webpage"  formId="orgSearchform" ajax="yes">
				<grid:header>
					<grid:cell width="16%">团队编号</grid:cell> 
					<grid:cell width="20%">团队名称</grid:cell> 
					<grid:cell width="16%">团队层级</grid:cell>
					<grid:cell width="16%">地市</grid:cell>
					<grid:cell width="16%">县分</grid:cell>
					<grid:cell width="16%">操作</grid:cell> 
				</grid:header>
				
				<grid:body item="obj">
					<grid:cell>${obj.team_id}</grid:cell>
					<grid:cell>${obj.team_name}</grid:cell>
					<grid:cell>
						<c:choose>
							<c:when test="${obj.team_level == 'province'}">
								省公司
							</c:when>
							<c:when test="${obj.team_level == 'region'}">
								地市
							</c:when>
							<c:otherwise>
								县分
							</c:otherwise>
						</c:choose>
					</grid:cell>
					<grid:cell>${obj.region_name}</grid:cell>
					<grid:cell>${obj.county_name}</grid:cell>
					<grid:cell>
						<a onclick="setDealerTeam('${obj.team_id}','${obj.team_name}');">设为处理组织</a>
					</grid:cell>
				</grid:body>  
			</grid:grid>
		</form>
	</div>
</body>

<script type="text/javascript">
function setDealerTeam(team_id,team_name){
	if(window.parent.WorkFlowTool){
		window.parent.WorkFlowTool.setDealerTeam(team_id,team_name);
	}else{
		alert("修改失败，选择组织的JS方法为空");
	}
}
</script>

