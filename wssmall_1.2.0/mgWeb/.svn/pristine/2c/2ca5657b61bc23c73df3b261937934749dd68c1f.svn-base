<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/shop/admin/setting/checktree.css" />

<script type="text/javascript" src="js/jquery.checktree.js"></script>

<div style="margin-left:10px" id="publish_div">
<form name="theForm" id="theForm">
<input type="hidden" name="params.esgoodscos" id="esgoodsco" value="${goodsIds }" >
<input type="hidden" name="actionName" id="actionName" value="${listFormActionVal }" >
<input type="hidden" name="type" id="type" value="${type }" >
</form>
	<div id="menubox"></div>
	<input type="hidden" id="resp_goodsid">
	<table>
		<tr>
			<td>
					 			<span style="padding-left:150px;">
					 				<a class="greenbtnbig" href="#" onclick="publicsh();"  style="height: 35px;line-height: 35px;">发布</a>
					 			</span>
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

	function publicsh() {
		var ids = "";
		var names = "";
		var k = 0;
		$("#menubox").find("div.checked").each(function(i, data) {
			//中国联通，新商城本身不记录
			var id = $(data).next().attr("id");
			var name = $(data).next().attr("value");
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
		var type = $("#type").val();
		var url = "";
		if("goods"==type){
			url = "goods!goodsBatchPublishByChoice.do";
		}else if("products"==type){
			url = "goods!productsBatchPublishByChoice.do";
		}
		var postData = "params.orgIds="+ids+"-"+names+"&params.goodsIds="+$("#esgoodsco").val();
		$.ajax({
		   url:url,
		   type:"POST",
		   dataType:"html",
		   data :postData,
		   success:function(reply){
			    reply=reply.substring(reply.indexOf("<body >")+7,reply.indexOf("</body>"));
			   	alert(reply);
			   cerrarCaja("goodsSelectShopDialog");
			   //window.location.reload();
		   },
		   error:function(reply){
			   //alert(reply.message);
			   alert("出错了!");
			   cerrarCaja("goodsSelectShopDialog");
		   }
	   });
	}
	
	
</script>