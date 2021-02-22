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
</style>
<body style="background: white;">
<div>
	<div class="grid" >
	<!-- 人员 -->
		<div class="comBtnDiv" id="divPersonnel">
			<grid:grid from="webpage"  formId="serchUserform" ajax="yes">
				<grid:header>
				    <grid:cell width="10%" >选择</grid:cell>
					<grid:cell width="15%">团队编号</grid:cell>
					<grid:cell width="15%">团队名称</grid:cell>
					<grid:cell >层级</grid:cell>
					<grid:cell >地市</grid:cell>
					<grid:cell >县分</grid:cell>
				</grid:header>
				<grid:body item="group">
					<grid:cell>
						<input name="lockUserChkGroup" type="radio" team_id="${group.team_id}" team_name="${group.team_name}" team_level="${group.team_level}">
					</grid:cell>
					<grid:cell>${group.team_id}</grid:cell>
					<grid:cell>${group.team_name}</grid:cell>
					<grid:cell>${group.team_level}</grid:cell>
					<grid:cell>${group.region_name}</grid:cell>
					<grid:cell>${group.county_name}</grid:cell>
				</grid:body>
			</grid:grid>
		</div>
	</div>
	<input type="hidden" id="allotType"  value="${allotType}" />
	    <input type="hidden" id="county_code" value="${county_code}"/>
	    <input type="hidden" id="is_work_custom" value="${is_work_custom}">
	    <input type="text" class="ipttxt" id="lockOrderIdStr"  value="${lockOrderIdStr}" style="display :none;"/>
</div>
<script type="text/javascript">
		function CheckedPersonnel(){
			var code = $("[name='lockUserChkGroup']:checked").val();
			if (code == null) {
				alert("还未选中工号");
				return "0";
			} else {
				var obj = $("[name='lockUserChkGroup']:checked");
				var team_name = obj.attr("team_name");
				var team_id = obj.attr("team_id");
				var lockOrderIdStr = $("#lockOrderIdStr").val();
				var allotType = $("#allotType").val();
			var temp ={
					"team_name":team_name,
					"team_id":team_id,
					"lockOrderIdStr":lockOrderIdStr,
					"allotType":allotType
			};
			
		  window.parent.groupJSon = temp;
		}
			return "1";
		};
	</script>
</body>
