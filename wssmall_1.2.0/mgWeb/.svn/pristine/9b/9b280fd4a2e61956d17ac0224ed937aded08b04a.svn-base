var CatalogTree = {
		init:function(){
			//CatalogTree.reload();
			CatalogTree.initSelected();
		},
		reload:function(){
			$("#catalog_list").treegrid("reload");
		},
		initSelected:function(){
		}
}
$(function(){
	$("body").children("div").children("div").attr("class","panel-body-noheader layout-body");
	$.ajaxSetup({ cache: false }); 
	
	var catalog_code = $("#parent_catalog_code",parent.document).val();
	
	$("#catalog_list").treegrid({
		
		queryParams: {
			catalog_code: $("#catalog_list").attr("catalog_code"),
			catalog_name: $("#catalog_list").attr("catalog_name"),
			flag:$("#catalog_list").attr("flag")
		},

        onBeforeExpand: function (node) {
        	if(node){
	            var catalog_id = node.catalog_id;
	            var catalog_code = $("#parent_catalog_code").val();
	            
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
            //加载归类基本信息
            var url =ctx+"/shop/admin/esearchCatalog!getEsearchCatalogInfo.do?catalog_id="+catalog_id;
            $("#catalogInfoIframe", window.parent.document).attr("src",url);
            //加载归类解决方案
            var url =ctx+"/shop/admin/solution!getCatalogSolution.do?solution_id="+solution_id+"&catalog_id="+catalog_id;
            $("#catalogSolutionIframe", window.parent.document).attr("src",url);
            
        }
    });
	CatalogTree.init();
});

