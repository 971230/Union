<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/shop/admin/setting/checktree.css" />

<script type="text/javascript" src="js/jquery.checktree.js"></script>

<div style="margin-left:10px" id="publish_div">
<form name="theForm" id="theForm">
<input type="hidden" name="goodsType" id="goodsType" value="${goods_type }" >
<input type="hidden" name="actionName" id="actionName" value="${listFormActionVal }" >
</form>
	<div id="menubox"></div>
	<input type="hidden" id="resp_goodsid">
	<table>
		<tr>
			<td>
			        <c:if test="${busqueda eq 'false' }">
					 			<span style="padding-left:150px;">
					 				<a class="greenbtnbig" href="#" onclick="publish();"  style="height: 35px;line-height: 35px;">发布</a>
					 			</span>
					 </c:if> 
					 				 <c:if test="${busqueda eq 'true' }">
					 			<span style="padding-left:150px;">
					 				<a class="greenbtnbig" href="#" onclick="busqueda();"  style="height: 35px;line-height: 35px;">确定</a>
					 			</span>
					 </c:if> 
			</td>
		</tr>
	</table>
</div>
<script>
		$.ajax({
			url : "goodsOrg!qryTree.do?ajax=yes",
			type : "POST",
			dataType : 'json',
			success : function(result) {
		    	if(result != null && result != '[]'){
		    		randerTree(result);
		    	}
			},
			error : function(){
			}
		});
	function openTreeNode() {
		$("ul.checktree").find("div.collapsed").trigger("click");
	}

	function randerTree(jsonStr) {
		var $container = $("#menubox");
		for ( var i = 0, j = jsonStr.length; i < j; i++) {
			createNodes($container, jsonStr[i]);
		}
		//等数据加载完毕
		$("#menubox").checkTree();
		//默认展开所有节点
		openTreeNode();
	}

	function createNodes(con, data) {
		<c:if test="${goods_type eq 'goods'}">
		//如果是WMS的話,商品同步的時候不需要顯示WMS的選項
		if("20001"==data.party_id)
			return;
		</c:if>
				
		var ul = $("<ul class='checktree'></ul>");
		var li = $("<li></li>");
		var node = $('<input type="checkbox" value="'+data.name+'" level="'+data.org_level+'"  name="eckBox" id="'+data.party_id+'" pid="'+data.parent_party_id+'"> <label>'
				+ data.name + '</label>');
		$(li).append(node);
		$(ul).append(li);
		$(con).append(ul);
		var sub_node = data["sub_node"];
		if (sub_node != undefined) {
			for ( var i = 0, j = sub_node.length; i < j; i++) {
				createNodes($(li), sub_node[i]);
			}

		}
	}

	function publish() {
		var ids = "";
		var names = "";
		var k = 0;
		$("#menubox").find("div.checked").each(function(i, data) {
			//中国联通，新商城本身不记录
			var id = $(data).next().attr("id");
			var name = $(data).next().attr("name");
			if(id != "10008" && id != "10000"){
				if(k++>0){
					ids += ",";
					names += ",";
				}
				ids += id;
				names += name;
			}
		});
		if (ids == "") {
			MessageBox.show("请选择要发布的商城", 2, 2000);
			return;
		}
		$("#batchOrgIds").val(ids);
		var url = $("#actionName").val();
		var goods_type = $("#goodsType").val();
		$.Loading.hide();
		$.Loading.show("正在发布，请稍后...");
		if(goods_type=="goods"){
			$("#searchGoodsListForm").attr("action",url);
			$("#searchGoodsListForm").submit();//商品批量发布
		}
		else{
			$("#searchProductsListForm").attr("action",url);
			$("#searchProductsListForm").submit();//货品批量发布
		}
		
	}
	
		function busqueda(){
		var ids = "";
		var v = getEckValue("eckBox");
		$("#menubox").find("div.checked").each(function(i, data) {
			ids += $(data).next().attr("id")+",";
			});
		$("#orgs").val(v[2]);//v[1] 直接展示商城
		$("#org_name").val(v[2]);//by liqingyi修复新增时文本框没有赋值
		$("#org_ids").val(ids);
		cerrarCaja("goods_pub_dialog");
	}
	
</script>