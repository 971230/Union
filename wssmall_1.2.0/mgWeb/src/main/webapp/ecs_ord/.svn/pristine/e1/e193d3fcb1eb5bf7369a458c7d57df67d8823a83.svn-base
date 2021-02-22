<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/taglibs.jsp" %>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/core/admin/auth/checktree.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/easyui/locale/easyui-lang-zh_CN.js"></script>


</head>
<body class="easyui-layout" style="height:100%;" border="0">
<div region="center" >
	<input type="hidden" value="${end_time }" id="end_time" name="end_time"/>
	<input type="hidden" value="${start_time }" id="start_time" name="start_time"/>
    <table id="catalog_list" url="<%=request.getContextPath() %>/shop/admin/esearchCatalog!qryCatalogTreeList.do?ajax=yes" idField="catalog_id" treeField="catalog_name" style="width:205;height:520px;" border=0>
        <thead>
        <tr>
            <th field="catalog_id" rowspan="2" align="center" hidden="true"></th> 
            <th field="catalog_name" width="200" align="left"></th>
        </tr>
        </thead>
    </table>
    
</div>
</body>
</html>
<<script type="text/javascript">
var CatalogTree = {
		init:function(){
			CatalogTree.reload();
		},
		reload:function(){
			$("#catalog_list").treegrid("reload");
		}
}
$(function(){
	$("body").children("div").children("div").attr("class","panel-body-noheader layout-body");
	$.ajaxSetup({ cache: false }); 
	$("#catalog_list").treegrid({
       
        onBeforeExpand: function (node) {
        	if(node){
	            var catalog_id = node.catalog_id;
	            
	            var nodes = $("#catalog_list").treegrid('getChildren', catalog_id);
	            
	            $.ajax({
	            	url:ctx+"/shop/admin/esearchCatalog!qryChildrenCatalog.do?ajax=yes",
	            	dataType:"json",
	            	data:{"catalog_id" : catalog_id},
	            	success:function(reply){
	            		if (nodes.length > 0) {
                        	for (var i = nodes.length - 1; i >= 0; i--) {
                            	$("#catalog_list").treegrid('remove', nodes[i].code);
                        	}
                    	}
                    	$("#catalog_list").treegrid('append', {
                        	parent: catalog_id,
                        	data: reply
                    	});
	            	},
	            	
	            	error:function(){
	            	
	            	}
	            });
	            
        	}
        },
        onClickRow:function(node){
    		var catalog_id  = node['catalog_id'];
    		var catalog_code = node['catalog_code'];
            var catalog_name = node['catalog_name'];
            var catalog_desc = node['catalog_desc'];
            var solution_id = node['solution_id'];
            var staff_no = node['staff_no'];
            var isLeaf = node['isLeaf'];
            //记录选中节点信息,用于添加解决方案时判断是否可以添加
            $("#catalog_div input[name='nodeIsLeaf']", window.parent.document).val(isLeaf);//是否叶子节点，只有叶子节点才可以添加方案
            $("#catalog_div input[name='selectCatalogId']", window.parent.document).val(catalog_id);//归类ID
            $("#catalog_div input[name='catalogStaffId']", window.parent.document).val(staff_no);//归类ID
            var start_time = $("#start_time").val();
            var end_time = $("#end_time").val();
            var url =ctx+"/shop/admin/orderExp!specCatalogList.do?catalog_id="+catalog_id+"&order_model=-1&start_time="+start_time+"&end_time="+end_time;
            $("#specCatalogIframe", window.parent.document).attr("src",url);
            //加载归类解决方案
            //var url =ctx+"/shop/admin/solution!getCatalogSolution.do?solution_id="+solution_id+"&catalog_id="+catalog_id;
            //$("#catalogSolutionIframe", window.parent.document).attr("src",url);
            
        }
    });
	CatalogTree.init();
});
</script>