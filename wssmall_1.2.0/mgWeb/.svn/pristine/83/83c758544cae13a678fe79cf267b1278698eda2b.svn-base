<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/template/js/tree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/template/js/tpl_index.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/template/css/tree.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/rule/css/style.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/js/admin/point.js"></script>
<div class="gridWarp" >
	<div class="tpl_left" style="width:15%;float:left">
		 <!-- 左边菜单树 -->
	   <div id="rule_page_left1">
	   <input type="hidden" id="catalogueId" value="" /> 
       <input type="hidden" id="catalogueName" value="" />
       <input type="hidden" id="orderTemplateId" value="" />
       <input type="hidden" id="orderTemplateName" value="" />
       <div style="position: absolute;margin-left:100px;margin-top:100px;"><img id="loading" src="<%=request.getContextPath()%>/shop/admin/template/images/loading.gif" style="display:none"></div>
	   <div class="tree" id="catalogue_div" style="height:100%;"></div>
    </div>
	<div class="contextMenu" id="rightMenu1">
		<ul id="menu1" style="display: none;">
		    <li><em class=""></em><a id="addCatalogue" href="javascript:void(0);">新增子目录</a></li>
		    <li><em class=""></em><a id="modCatalogue" href="javascript:void(0);">修改目录</a></li>
		    <li><em class=""></em><a id="delCatalogue" href="javascript:void(0);">删除目录</a></li>
		</ul>
		<ul id="menu3" style="display: none;">
	    <li><em class=""></em><a id="modOrderTpl" href="javascript:void(0);">修改模板</a></li>
	    <li><em class=""></em><a id="copyOrderTpl" href="javascript:void(0);">复制模板</a></li>
	    <li><em class=""></em><a id="delOrderTpl" href="javascript:void(0);">删除模板</a></li>
	</ul>
	</div>	
</div>
	<div class="tpl_right" style="width:85%;float:right;">
		<div class="operTit" style="padding-left: 25px;padding-top: 5px;">
		    <span name="model_switch" class="cutover" style="margin-left:15px;">
		    	<a name="simple_model" href="javascript:void(0);" class="r_curr">精简模式</a>
		    	<a name="oper_model" href="javascript:void(0);">操作模式</a>
		    </span>
		    <div id="btnView" style="float: right;display: none;">
				<input type="button" value="剪切" id="cutNode">&nbsp;&nbsp;
				<input type="button" value="复制" id="copyNode"> &nbsp;
				<input type="button" value="粘贴" id="pasteNode">&nbsp;
				<input type="button" value="删除" id="delNode">&nbsp;&nbsp;
				<input type="button" value="修改" id="ModNode">&nbsp;|&nbsp;
				<input type="button" value="上移" id="upNode">&nbsp;&nbsp;
				<input type="button" value="下移" id="downNode">&nbsp;&nbsp;
				<input type="button" value="插入" id="addNode">&nbsp;&nbsp;
				<input type="button" value="一键设置" id="setUp">&nbsp;&nbsp;
			</div>
		</div>
		<div style="padding-top: 5px;" id="topDiv">
			
		</div>
		<div class="graph_tab" style="position: fixed;bottom: 0px;">
			<ul id="view_tab">
				<li class="selected" name="view_page"><span class="word">视图</span><span class="bg"></span></li>
				<li name="code_page"><span class="word">源码</span><span class="bg"></span></li>
				<div class="clear"></div>
			</ul>
		</div>
	</div>
	<div id="showDlg"></div>
</div>
</div>