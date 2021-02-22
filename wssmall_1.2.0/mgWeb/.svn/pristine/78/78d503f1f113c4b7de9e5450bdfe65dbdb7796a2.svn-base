<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@page import="com.ztesoft.remote.pojo.AppInfo"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.List"%>

<script type="text/javascript">
	loadScript("app/js/app_center.js");
</script>

<link href="/mgWebthemes/default/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="/mgWebthemes/default/css/style_n.css" rel="stylesheet" type="text/css" />
<div>
	<div class="columDivN" style="width:66.66%;">
	   <div class="columDivConN">
	       <ul class="tab_a">
	           <li class="curr"><a href="javascript:void(0)"><span>应用平台--核心版本</span><i class="tab_arrow"></i></a></li>
	           <div class="clear"></div>
	       </ul>
	       <div class="tab_a_content" style="min-height:200px;">
	           <div class="platform_div">
	           		<ul id="user_app_list">
	           			<%
	           				AdminUser user = ManagerUtils.getAdminUser();
	           				List<AppInfo> appList = (List<AppInfo>)request.getAttribute("appList");
	           				for(int i = 0; i < appList.size(); i++ ){
	           					AppInfo app = appList.get(i);
	           					JSONObject appObject = JSONObject.fromObject(app);
	           					String appInfo = appObject.toString();
	           			%>
	           				<c:set var="app" value="<%= app%>"></c:set>
			             	<li <c:if test="${app.themeSourceFrom == curAppSource}">class="curr"</c:if> app_name="${app.appName}" appInfo='<%=appInfo %>'>
			                    <a href="javascript:void(0);">
			                    	<!-- appico1 -->
									<!-- <p><i style='background: url("${context}/images/appico1_curr.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0)'></i></p> -->
									<p><i class="appico1"></i></p>
			                        <p>${app.appName}</p>
			                    </a>
			                </li>
			          	<%
	           				}
			          	%>
	                	<div class="clear"></div>
	          		</ul>
	           </div>
	       </div>
	   </div>
	   <div style="width:50%; float:left;">
	       <div class="columDivConN">
	           <ul class="tab_a">
	               <li class="curr"><a href="#"><span>系统公告</span><i class="tab_arrow"></i></a></li>
	               <div class="clear"></div>
	           </ul>
	           <div class="tab_a_content">
	               <ul class="app_news">
	               	<li class="curr"><a href="#">关于3666EC微信公众平知信公平台将公平台将于下月...</a><span>6/28</span></li>
	               	<li><a href="#">3666EC2013国庆放假通知及工通知及工通及工作排...</a><span>7/01</span></li>
	               	<li><a href="#">管家婆双核网店/分销批发系统将于本月正式...</a><span>6/28</span></li>
	               	<li><a href="#">全程通升级新增五个对接版系统将及工通于本月启动...</a><span>7/01</span></li>
	               	<li><a href="#">关于3666EC微信公众平台系统将式及工通正式上线...</a><span>6/28</span></li>
	               	<li><a href="#">全程通升级系统将于本五个对接正式启动...</a><span>7/01</span></li>
	               	<li><a href="#">666EC2013国庆系月正式放假通放假通...</a><span>6/28</span></li>
	               	<li><a href="#">管家婆双核网店/分销批系统月正式发及工通系店分统...</a><span>7/01</span></li>
	               </ul>
	           </div>
	       </div>
	   </div>
	   <div style="width:50%; float:left;">
	        <div class="columDivConN">
	            <ul class="tab_a">
	                <li class="curr"><a href="#"><span>系统公告</span><i class="tab_arrow"></i></a></li>
	                <div class="clear"></div>
	            </ul>
	            <div class="tab_a_content">
	                <ul class="app_news">
	                	<li><a href="#">关于3666EC微信公众平知信公平台将公平台将于下月...</a><span>6/28</span></li>
	                	<li><a href="#">3666EC2013国庆放假通知及工通知及工通及工作排...</a><span>7/01</span></li>
	                	<li><a href="#">管家婆双核网店/分销批发系统将于本月正式...</a><span>6/28</span></li>
	                	<li><a href="#">全程通升级新增五个对接版系统将及工通于本月启动...</a><span>7/01</span></li>
	                	<li><a href="#">关于3666EC微信公众平台系统将式及工通正式上线...</a><span>6/28</span></li>
	                	<li><a href="#">全程通升级系统将于本五个对接正式启动...</a><span>7/01</span></li>
	                	<li><a href="#">666EC2013国庆系月正式放假通放假通...</a><span>6/28</span></li>
	                	<li><a href="#">管家婆双核网店/分销批系统月正式发及工通系店分统...</a><span>7/01</span></li>
	                </ul>                        
	            </div>
	        </div>
	    </div>
	</div>
	<div class="columDivN">
	    <div class="columDivConN">
	        <ul class="tab_a">
	            <li class="curr"><a href="#"><span>系统通知</span><i class="tab_arrow"></i></a></li>
	            <div class="clear"></div>
	        </ul>
	        <div class="tab_a_content">
	            <ul class="sys_news">
	            	<li class="curr"><a href="#">互联网分销系统常见问题知识解答以及我们在日常生活分销系统常见问题知识解在日常题知活中。</a></li>
	            	<li><a href="#">互联网分销系统常见问题知识解答以及我们在日常生活分销系统常见问题知识解在日常题知活中。</a></li>
	            	<li><a href="#">互联网分销系统常见问题知识解答以及我们在日常生活分销系统常见问题知识解在日常题知活中。</a></li>
	            	<li><a href="#">互联网分销系统常见问题知识解答以及我们在日常生活分销系统常见问题知识解在日常题知活中。</a></li>
	            	<li><a href="#">互联网分销系统常见问题知识解答以及我们在日常生活分销系统常见问题知识解在日常题知活中。</a></li>
	            </ul>
	        </div>
	    </div>
	</div>
</div>
<script type="text/javascript">
	var userName = "<%= user.getUsername()%>";
	var passWord = "<%= user.getPassword()%>";
</script>