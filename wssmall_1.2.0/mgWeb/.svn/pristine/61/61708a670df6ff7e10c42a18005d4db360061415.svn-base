<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<link href="<%=request.getContextPath() %>/publics/lucene/info.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/lucene/lucene.js"></script>
<form  action="javascript:;" id="searchProductPkgForm" method="post" >
	<div class="searchformDiv">
    	<table width="100%" cellspacing="0" cellpadding="0" border="0">
    		<tbody>
	    	    <tr>
	    	    	<th>
						货品包类型：
					</th>
					<td style="width:130px;">
						<select name="type_id" id="type_id" style="width: 125px">
							<option value="">选择类型</option>
							<c:forEach items="${typeList }" var="type">
								<option value="${type.type_id }"<c:if test="${type.type_id==params.type_id }">selected</c:if>>${type.name }</option>
							</c:forEach>
						</select>
					</td>
	    	      	<th>
						品牌：
					</th>
					<td style="width:130px;">
						<select name="brand_id" id="brand_id" style="width: 125px">
							<option value="">选择品牌</option>
							<c:forEach items="${lanList }" var="brand">
								<option value="${brand.brand_id }"<c:if test="${brand.brand_id==params.brand_id }">selected</c:if>>${brand.name }</option>
							</c:forEach>
						</select>
					</td>
					<th>
						机型：
					</th>
					<td style="width:130px;">
						<input type="text" class="ipttxt" style="width: 120px" name="model_code" id="model_code" value="${params.model_code }">
					</td>
					<th>
						名称：
					</th>
					<td style="width:130px;">
						<input type="text" class="ipttxt" style="width: 120px" name="name" id="relation_name" value="${params.name }" tableColumn="name" keyFun="searchResult" objType="package">
					</td>
					<td style="width:70px;">
						<a href="javascript:void(0);" style="margin-right:10px;" class="graybtn1" id="searchPkgBtn"><span>搜索</span></a>
					</td>
			 	</tr>
  	    	</tbody>
  	    </table>
   	</div>
</form>
<form id="pkgGridForm" class="grid">
	<grid:grid from="webpage" formId="searchProductPkgForm" ajax="yes" excel="yes">
		<grid:header>
			<grid:cell>货品包名称</grid:cell>
			<grid:cell>货品包类型</grid:cell>
			<grid:cell>创建时间</grid:cell>
			<%--<grid:cell>状态</grid:cell>
		--%>
		</grid:header>
		<grid:body item="pkg">
			<grid:cell>${pkg.relation_name }</grid:cell>
			<grid:cell>${pkg.relation_type } </grid:cell>
			<grid:cell>${fn:substring(pkg.created_date,0,19) }${goods.status} </grid:cell>
			<%--<grid:cell>
				<c:if test="${pkg.status == '00A'}">有效</c:if><c:if test="${pkg.status != '00A'}">无效</c:if>
			</grid:cell>
		--%></grid:body>

	</grid:grid>
</form>

<div style="clear: both; padding-top: 5px;"></div>
<script>
$(function(){
	Lucene.init('relation_name');
	$("#confirmPkgBtn").click(function(){
		selectPkg();
	});
	$("#searchPkgBtn").click(function(){
		searchPkg();
	});
	$("#type_id").change(function(){
		ProductPackage.loadBrand();
	});
	$("#pkgGridForm").find(".gridbody tbody tr").live("dblclick",function(){
    	//alert("doubleClick");
    	var box = $(this).find("input[type='checkbox'],input[type='radio']");
    	if($(box).attr("type") == "radio"){
    		box.attr("checked",true);
    		selectPkg();
    		ProductPackage.searchColor();
    	}
	});
});

function selectPkg(){
	var pkg = $("#pkgGridForm input[name='id']:checked");
	var pkgid = $("#pkgGridForm input[name='id']:checked").val();
	var md = $("#pkgGridForm input[name='id']:checked").attr("md");
	$("#relation_id").val(pkgid);
	$("#md").val(md);
	pkg.each(function(idx,item){
	   	var tr = $(item).parents("tr");
	   	$("#productPkgTables").append(tr);
	   	tr.find("input[type='radio']").hide();
	   	$(tr).removeClass("grid-table-row-selected");
	   	$("#productPkgTables tbody").html("");
	   	$("#productPkgTables").append(tr);
	   
    });
	Eop.Dialog.close("package_dialog");
	ProductPackage.searchPkgGoods();
}
function searchPkg(){
	var params = {};  
	params.type_id = $("#type_id").val();
	params.brand_id = $("#brand_id").val();
	params.model_code = $("#model_code").val();
	params.name = $("#relation_name").val();
	ProductPackage.openPkgDialog(params);
}
</script>