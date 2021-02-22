<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link type="text/css" href="<%=request.getContextPath()%>/shop/admin/template/treeTable/css/gridTree.css" rel="stylesheet"/>
<script language="javascript" src="<%=request.getContextPath()%>/shop/admin/template/treeTable/js/Base.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/shop/admin/template/treeTable/js/GridEdit.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/shop/admin/template/treeTable/js/TreeObj.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/shop/admin/template/treeTable/js/TreeBase.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/shop/admin/template/js/node_tree.js"></script>
<div id="aimDiv" class="tree_tb"></div>
<input type="hidden" id="orderTemplateId" value="${id }" />
<input type="hidden" id="tree_oper_mode" value="${op_mode }" />
<div id="choose_div"></div>
<div class="contextMenu" id="tree_menu">
	<ul class="menu_style" style="display: none;">
		<li name="add_node" click_method="NodeManager.add_node"><em class=""></em><a id="add_node" href="javascript:void(0);">新增节点</a></li>
	    <li name="del_node" click_method="NodeManager.del_node"><em class=""></em><a id="del_node" href="javascript:void(0);">删除节点</a></li>
	    <li name="paste_nodes" click_method="NodeManager.paste_nodes"><em class=""></em><a id="paste" href="javascript:void(0);">粘贴</a></li>
	</ul>
</div>