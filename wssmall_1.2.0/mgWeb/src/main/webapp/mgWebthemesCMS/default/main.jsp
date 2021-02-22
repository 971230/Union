<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>${title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="image/x-icon" href="${ico}" rel="icon" />
<link type="image/x-icon" href="${ico}" rel="bookmark" />
<%
	AdminUser adminUser=ManagerUtils.getAdminUser();
	String theme_source_from =  adminUser.getTheme_source_from();
%>
<script>
var theme_source_from = '<%=theme_source_from%>';
</script>
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
</head>
<body>
<!--LOGODIV-->
<div class="mainDiv">
	<div class="topbg">
        <div class="logoDiv">
        	
        	<div class="advertDiv"  id='sysmenu'>
                
                 <span>工号：<a href="javascript:void(0);"><%=adminUser.getUsername() %></a></span>
	             <span>用户名：<a href="javascript:void(0);"><%=adminUser.getRealname()%></a></span>
	             <span>工号类型：<a href="javascript:void(0);"><%=ManagerUtils.getcurrType() %></a></span>
	             	上次登录时间： <%=adminUser.getLast_loginfail_time()%> 
	             <span><a href="javascript:void(0);"><%=ManagerUtils.getLanName()%></a></span>
            
            	
            </div>
        </div>
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
            <div class="clear"></div>
        </div>
        </div>
    </div>
</div>

<!--主体页面DIV-->
<div class="local_div">
<div class="position">您的位置：<span  id='second_h3_title' ></span>


			
			
			
			
</div>
</div>
<div class="mainContent" >
	<div class="leftDiv" style="border:0px solid red;overflow-y:auto;overflow-x:hidden;" id='leftMenuDiv'>
    	<div class="leftMenuDiv" id='leftMenus' style='background:#fff;overflow-y:auto;overflow-x:hidden;'>
    		<!--子菜单 -->
	    </div>
    </div>
    
    
	<div class="rightDiv">
		<!--标题栏开始-->
		
        <div id='right_content' style='border:0px solid red;'>
        	
        </div>
   	</div>
    <div class="clear"></div>
</div>
</div>

</body>
</html>