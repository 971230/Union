<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%  String userid = ManagerUtils.getUserId();
System.out.println("登录用户："+userid);
%>
<style>
.contentPanel{ position:relative; padding-left:212px;}
.contentPanel .leftPanel{ width:212px; padding:2px; position:absolute; left:0px; top:0px;}
.contentPanel .rightPanel{ min-height:600px;}
</style>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/orderexp/js/catalog_tree_list.js"></script>
<div class="searchformDiv" style="margin-bottom:20px;margin-top:10px;">
	<form method="post" target="catalogListIframe" action="<%=request.getContextPath() %>/shop/admin/esearchCatalog!qryCatalogTree.do?ajax=yes">
		<input type="hidden" name="flag" value="search" />
		<table width="50%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>归类编码：</th>
					<td>
						<input type="text" value="${catalog_code }" name="catalog_code" class="ipttxt" />
					</td>
					<th>归类名称：</th>
					<td>
						<input type="text" value="${catalog_name }" name="catalog_name" class="ipttxt" />
					</td>
					<td>
						<input type="submit" style="margin-right:20px;" class="comBtn" value="搜&nbsp;索" id="button" name="button">
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<div id="catalog_div" class="contentPanel">
	<div id='left_panel'  class="leftPanel" style="width:200px;border:1px solid #F7F9F9;">
		<iframe style="height:530px;width:215px;" id="catalogListIframe" name="catalogListIframe" src="<%=request.getContextPath() %>/shop/admin/esearchCatalog!qryCatalogTree.do?ajax=yes"></iframe>
		<input type="hidden" name="nodeIsLeaf" title="归类树种选中的节点是否叶子节点" value="">
		<input type="hidden" name="selectCatalogId" title="归类树种选中节点的归类ID" value="">
		<input type="hidden" name="catalogStaffId" title="归类树种选中节点的创建人" value="">
		<input type="hidden" name="loginUserId" title="登录用户ID" value="<%=userid%>">
	</div>
	<div id='right_panel' class="rightPanel" >
		<div style='height:200px;' class="operCont bor">
		  	<div class="operTit">
		      	<span style="margin-right:15px;" class="f_bold">归类基本信息</span>
			    <a name="add_catalog" id="add_catalog" style="margin-right:10px;" class="graybtn1"><span>添加</span></a>
			    <a name="add_catalog" id="edit_catalog" style="margin-right:10px;" class="graybtn1"><span>修改</span></a>
			    <a name="add_catalog" id="delete_catalog" style="margin-right:10px;" class="graybtn1"><span>删除</span></a>
			</div>
			<div id="catalog_detail">
				<iframe style="height:100%;width:100%;" name="catalogInfoIframe" id="catalogInfoIframe" src="<%=request.getContextPath() %>/shop/admin/esearchCatalog!getEsearchCatalogInfo.do?catalog_id=1"></iframe>
			</div>
		</div>
		<div class="operCont">
			<div class="operTit">
		    	<span style="margin-right:15px;" class="f_bold">归类解决方案</span>
		        <a id="add_solution" name="add_solution" style="margin-right:10px;" class="graybtn1"><span>变更</span></a>
		    </div>
			<div id="exception_solution">
				<iframe style="height:100%;width:100%;" name="catalogSolutionIframe" id="catalogSolutionIframe" src="<%=request.getContextPath() %>/shop/admin/solution!getCatalogSolution.do"></iframe>
			</div>
			
		</div>
	</div>
</div>
<div id="addCatalogDlg"></div>
<div id="editCatalogDlg"></div>
<div id="addSolutionDlg"></div>