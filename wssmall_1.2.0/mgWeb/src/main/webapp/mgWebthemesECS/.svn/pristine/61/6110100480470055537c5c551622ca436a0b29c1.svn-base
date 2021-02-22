<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@page import="com.ztesoft.remote.pojo.AppInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>${title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="image/x-icon" href="${ico}" rel="icon" />
<link type="image/x-icon" href="${ico}" rel="bookmark" />
<script type="text/javascript" src="menu.do?timeStamp=<%= new java.util.Date().getTime()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/common.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/api.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/ztp-min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/editor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/editor/ckeditor/adapters/jquery.js"></script>

<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/input.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/validate.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/dialog.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />

<!-- 新加css -->
<link href="<%=request.getContextPath()%>/publics/css/reset.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/publics/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/publics/css/tab.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/publics/css/top.css" rel="stylesheet" type="text/css" />

<script>
	var g_staff_no =${staff_no};
</script>
<script type="text/javascript" src="${context}/js/index.js"></script>
<script type="text/javascript">
	function addfavor(url,title) {
		if(confirm("网站名称："+title+"\n网址："+url+"\n确定添加收藏?")){
			var ua = navigator.userAgent.toLowerCase();
			if(ua.indexOf("msie 8")>-1){
				external.AddToFavoritesBar(url,title,'IT有道');//IE8
			}else{
				try {
					window.external.addFavorite(url, title);
				} catch(e) {
					try {
						window.sidebar.addPanel(title, url, "");//firefox
					} catch(e) {
						alert("加入收藏失败，请使用Ctrl+D进行添加");
					}
				}
			}
		}
		return false;
	}
</script>
<%
	AdminUser adminUser=ManagerUtils.getAdminUser();
	AppInfo appInfo = ManagerUtils.getCurAppInfo();
	String theme_source_from = "";
	String app_key = appInfo.getAppKey();
	if(null != adminUser){
		theme_source_from =  adminUser.getTheme_source_from();
	}
%>
<script>
var theme_source_from = '<%=theme_source_from%>';
var app_key = '<%=app_key%>';
</script>
</head>
<!-- <body style='border:0px solid red;overflow:hidden;'> -->
<body>
	<div class="topContent">
		<div class="logoECS"></div>
		<div class="userTop">
			<div class="userIco">
				<img src="<%=request.getContextPath()%>/publics/images/top/userico.png" width="48" height="48">
			</div>
			<div class="userTxt" style="width: 500px;">
				<p class="p_name">
					<span class="item"><%=adminUser.getRealname()%></span><a
						href="javascipt:void(0);" class="vbb" style="margin-left:10px;"></a>
				</p>
				<p>
					用户名：<%=adminUser.getRealname()%>&nbsp;&nbsp;&nbsp;
					
					工号类型：<%=ManagerUtils.getTypeNameByFounder()%>&nbsp;&nbsp;&nbsp;
					上次登录时间： <%=adminUser.getLast_loginfail_time() != null ? adminUser.getLast_loginfail_time().substring(0, 19) : ""%> 
					</p>
				
				<!-- <p class="p_link">
					<a href="javascipt:void(0);">系统通知</a><span>|</span><a
						href="../core/admin/index.do" target='' iframeall='yes' id='desktop'
						style="margin-right:5px;">桌面</a><span>|</span><a
						href="javascript:void(0);"
						onclick="javascript:window.open('/index.jsp');return false;"
						target='iframe' style="margin-right:5px;">浏览</a><span>|</span><a
						target="_self" href="/shop/admin/logout.do" text="退出">退出</a>
						<span>|</span><a
						target='noneMenu' name="添加快捷菜单" app_menu_id="111008" class='shortCut' iframeall='yes' href="../core/admin/usersMenuAction!add.do" text="添加快捷菜单">添加快捷菜单</a>
				</p> -->
			</div>
			<p class="p_link">
				<a href="javascipt:void(0);"></a>
			</p>
		</div>
		<div class="quickMenu clearfix">
			<ul>
			</ul>
			<a href="javascript:void(0);" class="openTop"></a>
		</div>
	</div>
	<div class="top2">
		<a href="javascript:void(0);" id="hideAndShowMenuBtn" class="openTop">展开</a>
	</div>
	<div class="topBar" id='appmenu'>
		<ul class="navNew clearfix">
		</ul>
		<div class="useMsg">
	    	<span style="display:none;"><img src="/publics/images/useric.png" width="20" height="20">用户名：<span><%=adminUser.getUsername()%></span>   用户类型：<span><%=adminUser.getRealname()%></span></span>
	    </div>
		<!-- <span id="hideAndShowMenuBtn" title="显示菜单" style="display:none;float:right;margin-top:-30px;margin-right:30px;cursor:pointer;"><img src="/publics/images/arrow_down.png"></img></span> -->
	</div>

	<!--主体页面DIV-->
	<div class="mainContent">
		<!-- 
		<div class="leftDiv"style="border:0px solid red;overflow-y:auto;overflow-x:hidden;"id='leftMenuDiv'>
			<div class="leftMenuDiv" id='leftMenus' style='background:javascipt:void(0);fff;overflow-y:auto;overflow-x:hidden;'>
				子菜单</div>
		</div>
		 -->
<!-- 		<div id='right_content' style='border:0px solid red;'></div> -->
		
	</div>
	</div>

	<div class="fixedBottom" style="position: fixed;bottom:0px;width: 100%;">
	<div class="gridSper"></div>
	<div class="title_new">
		<div class="tab_bar">
			<ul class="clearfix" id="bottom_tab_ul">
			</ul>
		</div>
	</div>
	</div>
</body>
</html>