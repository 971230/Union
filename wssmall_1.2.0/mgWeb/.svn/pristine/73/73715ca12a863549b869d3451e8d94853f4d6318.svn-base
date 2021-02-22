<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<link href="${staticserver}/themes/default/cmsline/Content/themes/base/minified/jquery-ui.min.css" rel="stylesheet" type="text/css" />

<link href="${staticserver}/themes/default/cmsline/Content/Admin/css/admin.min.css" rel="stylesheet" type="text/css" />
<link href="${staticserver}/themes/default/cmsline/Scripts/Jcrop/css/jquery.Jcrop.min.css" rel="stylesheet" type="text/css" />
<script src="${staticserver}/themes/default/cmsline/Scripts/common.min.js" type="text/javascript"></script>
<script src="${staticserver}/themes/default/cmsline/Scripts/Admin/admin.min.js" type="text/javascript"></script>
<script src="${staticserver}/themes/default/cmsline/Scripts/Admin/header.js" type="text/javascript"></script>
<script src="${staticserver}/themes/default/cmsline/Scripts/Tmpl_3/Admin/header.js" type="text/javascript"></script>
<script src="${staticserver}/themes/default/cmsline/General/logon_status.js" type="text/javascript"></script>

<!-- shopToolbarWidget头部工具栏-->
<div class="toolbar-box"></div>
<script id="toolbar_back_tmpl" type="text/x-jquery-tmpl">
	<div class="box_alpha"></div>
	<div class="toolbar-bg">
		<ul class="toolbarb">
			<li class="toolbar_landing-info">
				<ul>
					<li class="landing-info-call">您好,<%=request.getParameter("uname")%></li>
					<li class="landing-info-close"><span><a href="javascript:void(0)" href='/shop/admin/logout.do' target="_self">退出</a></span></li>
					
				</ul>
			</li>
			<li class="toolbar_pagedesign">
				<label id="admin_backstage_top">后台管理</label>
				<div class="header_button_g"></div>
				<ul class="toolbar_popup">
					<li><a href="javascript:void(0)" id="admin_backstage">后台管理</a></li>
					<li id="admin_design_li"><a href="javascript:void(0)" id="admin_design">编辑模式</a></li>
					<li id="admin_preview_li"><a href="javascript:void(0)" id="admin_preview">预览模式</a></li>
					<li><a href="javascript:void(0)" id="admin_mobile_design">手机商城</a></li>

				</ul>
			</li>
			
		</ul>
	</div>
</script>

<script type="text/javascript">
	var ctx='<%=request.getContextPath()%>';
	var reqUrl =ctx+"/istoreself";
	function getContentPath(path) {
		var ContentPath = '/';
		if (ContentPath.substr(ContentPath.length - 1, 1) != '/') {
		ContentPath += '/';
		}
		return ContentPath + path;
	}
	function getScriptPath(path) {
		var ScriptPath = '/';
		if (ScriptPath.substr(ScriptPath.length - 1, 1) != '/') {
		ScriptPath += '/';
		}
		return ScriptPath + "Scripts/" + path;
	}
	function getActionPath(action, controller) {
		var ActionPath = '/';
		if (ActionPath.substr(ActionPath.length - 1, 1) != '/') {
		ActionPath += '/';
		}
		//alert(reqUrl+ActionPath + controller + "/" + action)
		return reqUrl+ActionPath + controller + "/" + action;
	}
	function getFullActionPath(action, controller) {
		var ActionPath = "http://" + window.location.host + '/';
		if (ActionPath.substr(ActionPath.length - 1, 1) != '/') {
		ActionPath += '/';
		}
		return ActionPath + controller + "/" + action;
	}
	function getFullPath(path) {
		var FullPath = '/';
		FullPath = "http://" + window.location.host + FullPath;
		if (FullPath.substr(FullPath.length - 1, 1) != '/') {
		FullPath += '/';
		}
		return FullPath + path;
	}
	$.cookie.json = true;
</script>
<script>
$(function(){
	toolbar = new Toolbar(".toolbar-box", 0);
	toolbar.show_toolbar();
})
</script>