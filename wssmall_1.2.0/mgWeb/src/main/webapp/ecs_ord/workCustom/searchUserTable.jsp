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
	<form method="post" id="userSearchform" action='userAdmin!getCustomDealerUserTable.do?'>
		<input type="hidden" class="ipttxt"  name="username"  value="${username}"/>
		<input type="hidden" class="ipttxt"  name="realname"  value="${realname}"/>
		<input type="hidden" class="ipttxt"  name="lan_id"  value="${lan_id}"/>
		<input type="hidden" class="ipttxt"  name="city_id"  value="${city_id}"/>
	</form>

	<div id="user_table_div" class="grid" >
		<form method="POST"  id="userInfoForm" >
			<grid:grid from="webpage"  formId="userSearchform" ajax="yes">
				<grid:header>
					<grid:cell width="12.5%">用户工号</grid:cell>
					<grid:cell width="12.5%">用户名称</grid:cell>
					<grid:cell width="12.5%">用户类型</grid:cell>
					<grid:cell width="12.5%">工号类型</grid:cell>
					<grid:cell width="12.5%">状态</grid:cell>
					<grid:cell width="12.5%">创建时间</grid:cell>
					<grid:cell width="12.5%">同步工号</grid:cell>
					<grid:cell width="12.5%">操作</grid:cell>
				</grid:header>
				
				<grid:body item="userAdmin">
					<grid:cell>&nbsp;${userAdmin.username }
					</grid:cell>
					<grid:cell>&nbsp;${userAdmin.realname } </grid:cell>
					<grid:cell>
			         <c:if test="${userAdmin.founder==0}">${typeMap.manager}</c:if>
			         <c:if test="${userAdmin.founder==1}">${typeMap.admin}</c:if>
			         <c:if test="${userAdmin.founder==2}">${typeMap.second_partner}</c:if>
			         <c:if test="${userAdmin.founder==3}">${typeMap.partner}</c:if>
			         <c:if test="${userAdmin.founder==4}">${typeMap.supplier}</c:if>
			         <c:if test="${userAdmin.founder==5}">${typeMap.supplier_employee}</c:if>
			        </grid:cell>
			        <grid:cell>
			         <c:if test="${userAdmin.usertype==1}">系统工号</c:if>
			         <c:if test="${userAdmin.usertype==2}">接口工号</c:if>
			         <c:if test="${userAdmin.usertype==3}">领取工号</c:if>
			        </grid:cell>
					<grid:cell> 
					 <c:if test="${userAdmin.state==1}">启用</c:if>
					 <c:if test="${userAdmin.state==0}">禁用</c:if>
					</grid:cell>
					
					<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${userAdmin.create_time}"></html:dateformat></grid:cell>
					
					<grid:cell>
						<c:choose>
							<c:when test="${userAdmin.is_syn==1}">是</c:when>
							<c:otherwise>否</c:otherwise>
						</c:choose>
					</grid:cell> 
					
					<grid:cell>
						<a onclick="setDealerUser('${userAdmin.username}','${userAdmin.realname}');">设为处理人员</a>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</body>

<script type="text/javascript">
function setDealerUser(userId,userName){
	if(window.parent.WorkFlowTool){
		window.parent.WorkFlowTool.setDealerUser(userId,userName);
	}else{
		alert("修改失败，选择组织的JS方法为空");
	}
}
</script>

