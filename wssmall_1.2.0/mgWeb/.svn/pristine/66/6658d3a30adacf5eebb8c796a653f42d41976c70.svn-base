<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/template/js/tpl_main.js"></script>
<!-- 模板列表 -->
<div id="tplList">
	<form method="POST"  id="tplFrom" class="grid">
		<grid:grid from="webpage"  formId="serchform" ajax="yes">
		<grid:header>
			<grid:cell width="2%" ></grid:cell>
			<grid:cell width="30%">模板编码</grid:cell>
			<grid:cell width="30%">模板名称</grid:cell>
			<grid:cell width="26%">版本号</grid:cell>
			<grid:cell width="12%">模板类型</grid:cell>
		</grid:header>
		<grid:body item="ordTplDTO">
			<grid:cell>
			<input name="ordTplChk" type="radio" value="${ordTplDTO.order_template_id}" style="display: none;">
			<input type="hidden" template_id="${ordTplDTO.order_template_id}" name="tempInput"/>
			</grid:cell>
			<grid:cell>${ordTplDTO.order_template_code}</grid:cell>
			<grid:cell>${ordTplDTO.order_template_name}</grid:cell>
			<grid:cell>${ordTplDTO.order_template_version}</grid:cell>
			<grid:cell>
			<c:if test="${ordTplDTO.order_template_type==1}">
				原子类型
			</c:if>
			<c:if test="${ordTplDTO.order_template_type==2}">
				复合类型
			</c:if>
			</grid:cell>
		</grid:body>
		</grid:grid>
	</form>
	<div class="comBtnDiv" style="text-align: center;margin-top: 40px;cursor: pointer;">
		<a id="btn_add" class="graybtn1" style="margin-right:10px;width: 60px;">
		<span>新增</span>
		</a>
		<a id="btn_mod" class="graybtn1" style="margin-right:10px;width: 60px;">
		<span>修改</span>
		</a>
		<a id="btn_del" class="graybtn1" style="margin-right:10px;width: 60px;">
		<span>删除</span>
		</a>
	</div>
</div>
<!-- 弹出对话框 -->
<div id="add_tpl"></div>