<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

String path= request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eop</title>
<style type="text/css">
body{
	margin:0px;
	font-family:Arial,"宋体",Verdana,sans-serif;
	font-size:12px;
}
html,body {padding: 0;margin: 0; overflow-x:hidden; overflow-y:auto;}
dl,dd{margin:0px}
ul{
	list-style-type:none;
	margin:0px;
	padding:0px;
}

</style>

<script type="text/javascript">
var app_path="<%=path%>";
var basePath="<%=basePath%>";
var mainpage=false;
</script>
<script type="text/javascript" src="${staticserver }/js/common/common.js"></script>
<script type="text/javascript" src="${staticserver }/js/admin/ztp-min.js"></script>
<script type="text/javascript" src="${staticserver }/js/admin/api.js"></script>
<script type="text/javascript" src="${staticserver }/js/common/SelectTree.js"></script>
<script type="text/javascript" src="<%=path %>/editor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=path %>/editor/ckeditor/adapters/jquery.js"></script>
<link href="${context}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/input.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/validate.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/dialog.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/style_n.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />




<style>
body{background:none;}
</style>
</head>
<body >

${content}


</body>
<a id='iframe_back_a' class="graybtn1" onclick='window.history.back();' style='display:none;'><span>返回</span></a>
<script>
	$(function(){
	
		//查看商品信息隐藏不必要的
		$('input[type="text"]').attr("disabled","disabled");
		$('input[type="checkbox"]').attr("disabled","disabled");
		$('input[type="radio"]').attr("disabled","disabled");
		
		//删除确定按键
		$('.greenbtnbig').length>0 && ($('.greenbtnbig').parent().parent().remove());
		//隐藏图片预览下的删除按键
		$('.deleteBtn').length>0 && $('.deleteBtn').hide();
		
		$("a[calss='deleteBtn']").length >0 && ($("a[calss='deleteBtn']").hide());
		
		$('input[type="button"]').length>0 && ($('input[type="button"]').hide());
		
		
	})
</script>

</html>