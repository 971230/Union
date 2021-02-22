<%@ page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<title>Cluster App Test</title>
	</head>
	<script language="javascript" src="/apidoc/js/jquery-1.4.2.min.js"></script>
	<link href="css/gridTree.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="js/Base.js"></script>
	<script language="javascript" src="js/GridEdit.js"></script>
	<script language="javascript" src="js/TreeObj.js"></script>
	<script language="javascript" src="js/TreeBase.js"></script>
	<script language="javascript" src="js/gridTree.js"></script>
	<body>
		<div id="aimDiv" style="width: 100%;height: 210px; border:double 1px #30C; float:left; overflow:scroll;overflow-x: hidden;"></div>
		<div class="contextMenu" id="tree_menu">
			<ul class="menu_style" style="display: none;">
				<li name="add_node" click_method="NodeManager.add_node"><em class=""></em><a id="add_node" href="javascript:void(0);">新增节点</a></li>
			    <li name="del_node" ><em class=""></em><a id="del_node" href="javascript:void(0);">删除节点</a></li>
			    <li name="paste"><em class=""></em><a id="paste" href="javascript:void(0);">粘贴</a></li>
			</ul>
		</div>
	</body>
</html>