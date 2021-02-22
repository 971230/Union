<%@ page contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
String src = request.getParameter("htmlPath");
String height = request.getParameter("height");
if(height == null){
	height = "550px";
}
String scrolling = request.getParameter("scrolling");
if(scrolling == null){
	scrolling = "auto";
}
%>
<html>
<body>
<iframe src="javascript:void(0);" id="html_iframe" name="html_iframe" width="100%" height="<%=height %>" scrolling="<%=scrolling %>"></iframe>
</body>
</html>
