<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link href="favicon.ico" rel="shortcut icon">
<link href="<%=request.getContextPath()%>/shop/admin/template/json/index/app.min.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/shop/admin/template/json/index/app.min.js" type="text/javascript"></script>
<div id="header" style="display: none;">
	<div id="name-menu">
		<div title="Document name. Click to change" id="name"></div>
		<div title="Changes are automatically saved online" id="name-status"></div>
	</div>
	<div id="menu">
		<ul>
			<li><a title="Open a new, empty document" id="new">New</a></li>
			<li><a title="Open file" id="open"> Open <span
					class="dropDownIcon">▼</span>
			</a>
				<ul id="openMenu">
					<li><a title="Open file from disk" id="openFromDisk">Open
							from disk</a></li>
					<li><a title="Open file from url" id="openUrl">Open url</a></li>
					<li id="filesList"></li>
				</ul></li>
			<li><a title="Save file" id="save"> Save <span
					class="dropDownIcon">▼</span>
			</a>
				<ul id="saveMenu">
					<li><a title="Save file to disk" id="saveToDisk">Save to
							disk</a></li>
					<li><a title="Save and share online" id="saveOnline">Save
							online</a></li>
				</ul></li>
			<li></li>
		</ul>
	</div>
</div>
<div id="auto">
	<div id="contents">
		<div id="codeEditor"></div>
		<div id="splitter" style="display: none;">
			<div id="buttons">
				<div>
					<button title="Copy code to tree editor" class="convert"
						id="toTree">
						<div class="convert-right"></div>
					</button>
				</div>
				<div>
					<button title="Copy tree to code editor" class="convert"
						id="toCode">
						<div class="convert-left"></div>
					</button>
				</div>
			</div>
			<div id="drag"></div>
		</div>
		<div id="treeEditor" style="display: none;"></div>
		<script type="text/javascript">
			app.load();
			app.resize();
		</script>
	</div>
</div>
<script type="text/javascript">
	app.resize();
</script>