<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8"%>
<style>
.contentPanel{ position:relative; padding-left:222px;}
.contentPanel .leftPanel{ width:212px; padding:2px; position:absolute; left:0px; }
.contentPanel .rightPanel{ min-height:800px;}
</style>
<div id="catalog_div" style="margin-left: 4%" class="contentPanel">
	<div id='left_panel' class="leftPanel"
		style="width:200px;border:1px solid #F7F9F9;">
		<input type="hidden" value="${start_time }" id="start_time" name="start_time"/>
		<input type="hidden" value="${end_time }" id="end_time" name="end_time"/>
		<iframe style="height:530px;width:215px;" id="catalogListIframe"
			name="catalogListIframe"
			src="<%=request.getContextPath() %>/shop/admin/esearchCatalog!qrySpecCatalogTree.do?start_time=${start_time }&end_time=${end_time }"></iframe>
		<input type="hidden" name="nodeIsLeaf" title="归类树种选中的节点是否叶子节点"
			value=""> <input type="hidden" name="selectCatalogId"
			title="归类树种选中节点的归类ID" value=""> <input type="hidden"
			name="catalogStaffId" title="归类树种选中节点的创建人" value="">
	</div>
	<div id='right_panel' class="rightPanel">

		<div id="catalog_detail">
			<iframe style="height:800px;width:100%;" name="specCatalogIframe"
				id="specCatalogIframe"
				src="<%=request.getContextPath() %>/shop/admin/orderExp!specCatalogList.do?catalog_id=0&order_model=-1&start_time=${start_time }&end_time=${end_time }"></iframe>
		</div>

	</div>
</div>
