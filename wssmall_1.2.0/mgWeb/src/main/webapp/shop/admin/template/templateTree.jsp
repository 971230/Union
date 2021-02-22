<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/template/css/tree.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/rule/css/style.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/template/js/tree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/js/admin/point.js"></script>


<div class="treeBox" id="rule_page_left" style="margin-left:30%;">
	   <input type="button" value="点击展示树" onClick="getTemplateTree('20150308163431100108')"> 
	   <input type="button" value="新增根节点" id="addTemplateRootNode"> 
	   <input type="hidden" id="nodeId" value="" /> 
	   <input type="hidden" id="orderTemplateId" value="" />
       <input type="hidden" id="nodeName" value="" />
	   <div class="tree" id="tree_div" style="height:700px; overflow:auto;"></div>
</div>
<div class="contextMenu" id="rightMenu">
	<ul id="menu0" style="display: none;">
		<li><em class=""></em><a id="viewNode" href="javascript:void(0);">查看节点</a></li>
	    <li><em class=""></em><a id="addNode" href="javascript:void(0);">新增子节点</a></li>
	    <li><em class=""></em><a id="modNode" href="javascript:void(0);">修改节点</a></li>
	    <li><em class=""></em><a id="delNode" href="javascript:void(0);">删除节点</a></li>
	</ul>
</div>	
<div id="showDlg"></div>
