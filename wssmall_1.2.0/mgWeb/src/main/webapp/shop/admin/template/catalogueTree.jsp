<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/template/css/tree.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/rule/css/style.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/template/js/tree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/js/admin/point.js"></script>


<div class="treeBox" id="rule_page_left" style="margin-left:30%;">
	   <input type="hidden" id="catalogueId" value="" /> 
       <input type="hidden" id="catalogueName" value="" />
       <input type="hidden" id="orderTemplateId" value="" />
       <input type="hidden" id="orderTemplateName" value="" />
	   <div class="tree" id="catalogue_div" style="height:700px; overflow:auto;"></div>
</div>
<div class="contextMenu" id="rightMenu">
	<ul id="menu1" style="display: none;">
	    <li><em class=""></em><a id="addCatalogue" href="javascript:void(0);">新增子目录</a></li>
	    <li><em class=""></em><a id="addOrderTpl" href="javascript:void(0);">新增模板</a></li>
	    <li><em class=""></em><a id="modCatalogue" href="javascript:void(0);">修改目录</a></li>
	    <li><em class=""></em><a id="delCatalogue" href="javascript:void(0);">删除目录</a></li>
	</ul>
	<ul id="menu3" style="display: none;">
	    <li><em class=""></em><a id="modOrderTpl" href="javascript:void(0);">修改模板</a></li>
	    <li><em class=""></em><a id="copyOrderTpl" href="javascript:void(0);">复制模板</a></li>
	    <li><em class=""></em><a id="delOrderTpl" href="javascript:void(0);">删除模板</a></li>
	</ul>
</div>	
<div id="showDlg"></div>
