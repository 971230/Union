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

<link href="${context}/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/style_n.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />

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
<body style='border:0px solid red;overflow:hidden;'>
<!--LOGODIV-->
<div class="mainDiv">
	<div class="topbg">
        <div class="logoDiv">
        <!--导航栏DIV-->
        <div class="naviDiv">
            <div class="naviLeft">
                <div class="navi">
                    <div class="navL"  id='appmenu'>
                        <ul class="nav">
                           	<!-- 
		                   		导航菜单
		                     -->
                        </ul>
                    </div>
                </div>
            </div>
             <div class="advertDiv"  id='sysmenu'>
               <a class="topbtn2" href="#"><span><i class="allicon msgicon"></i>系统通知</span></a>
               <a href="../core/admin/index.do" target='iframe' id='desktop' class="topbtn2" style="margin-right:5px;"><span><i class="allicon deskbook"></i>桌面</span></a>
           	   <a href="javascript:void(0);" onclick="javascript:window.open('/index.jsp');return false;" target='iframe'  class="topbtn2" style="margin-right:5px;"><span><i class="allicon collect"></i>浏览</span></a>
            </div>
            <div class="clear"></div>
        </div>
        </div>
    </div>
</div>
<!--主体页面DIV-->
<div class="mainContent" >
	<div class="leftDiv" style="border:0px solid red;overflow-y:auto;overflow-x:hidden;" id='leftMenuDiv'>
    	<div class="leftMenuDiv" id='leftMenus' style='background:#fff;overflow-y:auto;overflow-x:hidden;'>
    		<!--子菜单 -->
	    </div>
    </div>
    
    
	<div class="rightDiv">
		<!--标题栏开始-->
        <div class="titlenew">
			<h3>
			<a href="javascript:history.go(-1);" class="back" id='back' style=''>
			    <span id="turnToImport">返回</span>
			</a>
			
			<a href="javascript:void(0)" class="back" id='back_his_a' style='display:none;'>
			    <span id="turnToImport">返回</span>
			</a>
			
			<span  id='second_h3_title' ></span>
			
			</h3>
            <div class="user_position">
            <span>工号：<%=adminUser.getUsername() %></span>
            <span>用户名：<%=adminUser.getRealname()%></span>
            <span>工号类型：<%=ManagerUtils.getType()%></span>
            <span><%=ManagerUtils.getLanName()%></span>
             </div>
            
        </div>
        <div id='right_content' style='border:0px solid red;'>
        	
        </div>
   	</div>
    <div class="clear"></div>
</div>
</div>

</body>
</html>