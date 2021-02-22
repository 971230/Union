<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/shop/admin/setting/checktree.css" />
<script type="text/javascript" src="js/jquery.checktree.js"></script>

<div style="margin-left:10px" id="publish_div">
<form name="theForm" id="theForm">
<input type="hidden" name="productos"  value="${productos }" id="productos">
</form>
	<div id="menubox"></div>
	<input type="hidden" id="resp_goodsid">
	<table>
		<tr>
			<td>

					 <c:if test="${busqueda eq 'false' }">
					 			<span style="padding-left:150px;">
					 				<a class="greenbtnbig" href="#" onclick="publicsh();"  style="height: 35px;line-height: 35px;">发布</a>
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
		//销售组织加标识
		$("#menubox").children("ul").addClass("first");
		$("#menubox").children().children().children("ul").addClass("second");
		$("#menubox").children().children().children("ul").children().children("ul").addClass("third");
	}

	function createNodes(con, data) {
		var ul = $("<ul class='checktree'></ul>");
		var li = $("<li></li>");
		var node = $('<input type="checkbox" name="eckBox" value ="'+data.name+'"   id="'+data.party_id+'" level="'+data.org_level+'"  pid="'+data.parent_party_id+'"> <label>'
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

	function publicsh() {
		var ids = "",names = "";
		var k=0;
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
			//ids += $(data).next().attr("id") + ":"+$(data).next().attr("level") +",";
		});
		//ids = ids.substring(0, ids.length - 1);
		if (ids == "") {
			MessageBox.show("请选择要发布的商城", 2, 2000);
			return;
		}
		var url = "producto!liberacion.do?ajax=yes&ids="+ids+"&params.productos="+$("#productos").val();
		$.ajax({
			url:url,
			dataType:"json",
			type:"POST",
			success:function(reply){
				alert(reply.message);
				window.location.reload();
			},error:function(){
			}
		});
		//doAjax("theForm", url, "call", "grupos");
	}
	
	function busqueda(){
		var ids = "";
		var v = getEckValue("eckBox");
		$("#menubox").find("div.checked").each(function(i, data) {
			ids += $(data).next().attr("id")+",";
		});
		$("#orgs").val(v[1]);
		$("#org_ids").val(ids);
		cerrarCaja("grupos");
	}
</script>