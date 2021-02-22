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
.second select {  
    width: 152px;  
    height: 106px;  
    margin: 0px;  
    outline: none;  
    border: 1px solid #999;  
    margin-top: 22px; 
    background-color: white; 
}  
.second select option{
	background-color: inherit; 
}
.op{
	background-color: transparent; 
    bacground:tansparent;
    -webkit-appearance: none;
}
.second input {  
    width: 150px;  
    top: 0px;  
    outline: none;  
    border: 0pt;  
    position: absolute;  
     line-height: 22px;   
    /* left: 8px; */  
    height: 22px;  
    border: 1px solid #CECECE;  
    background-color: #FFFFFF; 
    
     margin-top:4px; 
}  
.second ul {  
    position: absolute;  
    top: 27px;  
    border: 1px solid #999;  
    left: 8px;  
    width: 125px;  
    line-height: 16px;  
}  
.ul li{  
    list-style: none;  
    width: 161px;  
    /* left: 15px; */  
    margin-left: -40px;  
    font-family: 微软雅黑;  
    padding-left: 4px;  
}
.jib{
	color: red;
    margin: 11px 0 7px 30px;
    font-size: 14px;
    line-height: 30px;
}
.jib span{
	font-weight: bold/700;
}  
</style>
<body style="background: white;">
<div>
	<c:if test="${allotType != 'yxd' && allotType != 'yxdreturn' }">
	<h2 class = "jib">您当前的权限所能分配的人员如下所示:</h2>
	<div class="grid" >
		<div class="comBtnDiv">
			<grid:grid from="webpage"  formId="serchUserform" ajax="yes">
				<grid:header>
				    <grid:cell width="10%" >选择</grid:cell>
					<grid:cell width="15%">姓名</grid:cell>
					<grid:cell width="15%">工号</grid:cell>
					<grid:cell >电话</grid:cell>
					<grid:cell >
						<c:if test="${allotType == 'allot' }">县分</c:if>
					</grid:cell>
				</grid:header>
				<grid:body item="adminUser">
					<grid:cell>
						<input name="lockUserChk" type="radio" userid="${adminUser.userid}" realname="${adminUser.realname}" username="${adminUser.username}">
					</grid:cell>
					<grid:cell>${adminUser.realname}</grid:cell>
					<grid:cell>${adminUser.username}</grid:cell>
					<grid:cell>${adminUser.phone_num}</grid:cell>
					<grid:cell>
						<c:if test="${allotType == 'allot' }">
							<div class="order_pri">
								<p title="${adminUser.busicityAuth}">${adminUser.busicityAuth}</p>
				            </div>
						</c:if>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</div>
	</div>
	</c:if>
	<c:if test="${allotType == 'yxd' || allotType == 'yxdreturn' }">
	<h2 class = "jib">您当前的处理级别为:<span>
						<c:if test="${deal_level == 'region'}">
						地市
						</c:if>
						<c:if test="${deal_level == 'county'}">
						县分
						</c:if>
						<c:if test="${deal_level != 'county' && deal_level != 'region'}">
						省分
						</c:if>
						</span>
						,所能分配的处理单位类型如下所示:
						</h2>
	<div class="grid" >
		<div class="comBtnDiv">
			<grid:grid from="webpage"  formId="serchUserform" ajax="yes">
				<grid:header>
				    <grid:cell width="10%" >选择</grid:cell>
					<grid:cell width="15%">地市</grid:cell>
					<grid:cell width="15%">县分</grid:cell>
					<grid:cell >处理单位级别</grid:cell>
					<grid:cell >处理单位类型</grid:cell>
					<grid:cell >处理单位</grid:cell>
					<grid:cell >分配人员编号</grid:cell>
				</grid:header>
				<grid:body item="adminUser">
					<grid:cell>
						<input name="lockUserChk" type="radio" permission_level="${adminUser.permission_level}" col5="${adminUser.col5}" userid="${adminUser.userid}" realname="${adminUser.realname}" username="${adminUser.username}">
					</grid:cell>
					<grid:cell>${adminUser.city}</grid:cell>
					<grid:cell>${adminUser.busicityAuth}</grid:cell>
					<grid:cell>
					<c:if test="${adminUser.permission_level == 'region'}">
					地市
					</c:if>
					<c:if test="${adminUser.permission_level == 'county'}">
					县分
					</c:if>
					<c:if test="${adminUser.permission_level != 'county' && adminUser.permission_level != 'region'}">
					省分
					</c:if>
					</grid:cell>
					<grid:cell>
					<c:if test="${adminUser.col5 == 'person'}">
					个人
					</c:if>
					<c:if test="${adminUser.col5 == 'team'}">
					团队
					</c:if>
					<c:if test="${adminUser.col5 == 'org'}">
					组织
					</c:if>
					</grid:cell>
					<grid:cell>${adminUser.realname}</grid:cell>
					<grid:cell>${adminUser.username}</grid:cell>
				</grid:body>
			</grid:grid>
		</div>
	</div>
	</c:if>
		<input type="hidden" id="allotType"  value="${allotType}" />
	    <input type="hidden" id="county_code" value="${county_code}"/>
	    <input type="hidden" id="is_work_custom" value="${is_work_custom}">
	    <input type="text" class="ipttxt" id="lockOrderIdStr"  value="${lockOrderIdStr}" style="display :none;"/>
</div>
	<script type="text/javascript">
		function CheckedPersonnel(){
			var code = $("[name='lockUserChk']:checked").val();
			if (code == null) {
				alert("还未选中工号");
				return "0";
			} else {
				var obj = $("[name='lockUserChk']:checked");
				var realname = obj.attr("realname");
				var userid = obj.attr("userid");
				var lockOrderIdStr = $("#lockOrderIdStr").val();
				var allotType = $("#allotType").val();
				var col5 = obj.attr("col5");
				var permission_level = obj.attr("permission_level");
			var temp ={
					"realname":realname,
					"userid":userid,
					"lockOrderIdStr":lockOrderIdStr,
					"allotType":allotType,
					"col5":col5,
					"permission_level":permission_level
			};
			
		  window.parent.tempJson = temp;
		}
			return "1";
		};
	</script>
</body>