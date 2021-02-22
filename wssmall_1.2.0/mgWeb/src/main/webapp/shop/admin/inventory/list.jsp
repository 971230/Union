<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="inventoryform" action="${pageContext.request.contextPath}/shop/admin/inventory!list.do" >
<div class="searchformDiv">
<table class="form-table">
	<tr>
		<th>
			盘点名称：
		</th>
		<td>
			<input type="text" class="ipttxt"  name="name"  value="${name }"/>
		</td>
		<th>
			盘点仓库：
		</th>
		<td>
			<select name="house_id" class="ipttxt">
				<c:forEach items="${wareHouseList }" var="hl">
					<option value="${hl.house_id }" ${hl.house_id==house_id?'selected="true"':'' }>${hl.house_name }</option>
				</c:forEach>
			</select>
		</td>
		<th>
		状态：
		</th>
		<td>
			<select class="ipttxt" name="status">
				<option value="">请选择</option>
				<option value="0" ${status==0?'selected="true"':'' }>已新建</option>
				<option value="1" ${status==1?'selected="true"':'' }>已确认</option>
				<%-- <option value="2" ${status==2?'selected="true"':'' }>已复核</option> --%>
				<option value="-1" ${status==-1?'selected="true"':'' }>已作废</option>
			</select>
			<a href="javascript:void(0)" id="inventorySearchBtn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
		
	</tr>
</table>		
</div>

<div class="comBtnDiv">
	<a href="javascript:;" id="addPurorderOrederBtn" style="margin-right: 10px;" class="graybtn1"><span>添加</span></a>
</div>
<div style="clear:both;padding-top:5px;"></div>
<div class="grid">
<grid:grid  from="houseInventoryPage" >
	<grid:header>
		
		<grid:cell >盘点单号</grid:cell>
		<grid:cell >盘点名称</grid:cell>
		<grid:cell >仓库名称</grid:cell>
		<grid:cell >状态</grid:cell> 
		<grid:cell >创建时间</grid:cell> 
		<grid:cell >操作员</grid:cell>
		<grid:cell >确认操作员</grid:cell>
	</grid:header>
    <grid:body item="hi">
        <grid:cell><span name="inventory_id">${hi.inventory_id}</span></grid:cell>
        <grid:cell>${hi.name}</grid:cell>
        <grid:cell>${hi.house_name}</grid:cell>
        <grid:cell>
        	<c:if test="${hi.status==0 }">已新建</c:if>
        	<c:if test="${hi.status==1 }">已确认</c:if>
        	<c:if test="${hi.status==2 }">已复核</c:if>
        	<c:if test="${hi.status==-1 }">已作废</c:if>
        </grid:cell>
        <grid:cell>
        	${fn:substring(hi.create_time, 0 , 19)}
        </grid:cell>
        <grid:cell>${hi.op_name}</grid:cell>
        <grid:cell>${hi.confirm_name}</grid:cell>
    </grid:body>  
    <tr id="iframe_tr" style="display: none;">
	  	<td colspan="10">
	        <iframe style="width: 100%;height: 400px;" src="">
	        </iframe>
	    </td>
    </tr>
</grid:grid>  
<div style="clear:both;padding-top:5px;"></div>
</div>
</form>	

<div id="inventorydialog" >
</div>

<div id="inventorygoodsdialog" >
</div>
<script type="text/javascript">

function searchGoods(){
	Eop.Dialog.open("inventorygoodsdialog");
	var goodsurl = ctx+"/shop/admin/inventory!qryHouseGoods.do?ajax=yes";
	var house_id = $("input[name=house_id]").val();
	var goodsName = $("input[name=goodsName]").val();
	if(!goodsName)goodsName="";
	$("#inventorygoodsdialog").load(goodsurl,{house_id:house_id,goodsName:goodsName},function(){
		//商品搜索按钮
		$("#inventoryGoodsSearchBtn").unbind("click").bind("click",function(){
			searchGoods();
		});
		$("#confirmaddgoodsBtn").unbind("click").bind("click",function(){
			var checkGoods = $("input[name=goods_checkbox]:checked");
			var str = "商品[";
			var flag = false;
			checkGoods.each(function(idx,item){
				var goodsid = $(item).val();
				var name = $(item).attr("goodsName");
				if($("input[name=goods_idArray][value="+goodsid+"]").length<1){
					var tr = "<tr><td>"+goodsid+"</td><td>"+name+"</td><td>"+
			          	"<input type=\"hidden\" name=\"goods_idArray\" value=\""+goodsid+"\" />"+
			          	"<input type=\"text\" name=\"goods_numArray\" value=\"1\" />"+
			          	"</td><td>"+
		          		"<a href=\"javascript:void(0);\" name=\"goods_delete\" style=\"margin-right: 10px;\" class=\"graybtn1\"><span>删除</span></a>"+
			          	"</td></tr>";
					$("#inventory_goods_body").append(tr);
				}else{
					str+=name+"、";
					flag = true;
				}
			});
			str += "]已存在不能添加。其它商品添清加成功。"
			if(flag)alert(str);
			Eop.Dialog.close("inventorygoodsdialog");
		});
	});
}

function addOrSaveInventory(){
	var action = $("input[name=action]").val();
	var hous_id = $("input[name=house_id]").val();
	var house_name = $("input[name=warhouseInventory.name]").val();
	var inventory_id = $("input[name=warhouseInventory.inventory_id]").val();
	var addurl = ctx+"/shop/admin/inventory!saveAddOrEdit.do?ajax=yes";
	var goodsIdArray = new Array();
	var goodsNumArray = new Array();
	var ids = $("input[name=goods_idArray]");
	ids.each(function(idx,item){
		goodsIdArray.push($(item).val());
	});
	var nums = $("input[name=goods_numArray]");
	nums.each(function(idx,item){
		goodsNumArray.push($(item).val());
	});
	var data = {action:action,house_id:hous_id,'warhouseInventory.inventory_id':inventory_id,
		'warhouseInventory.name':house_name,goods_idArray:goodsIdArray,goods_numArray:goodsNumArray
	};
	$.ajax({
		url:addurl,
		dataType:'json',
		type:'post',
		data:data,
		success:function(data){
			if(data.status==0){
				alert(data.msg);
				Eop.Dialog.close("inventorydialog");
				window.location.reload();
			}else{
				alert(data.msg);
			}
		},error:function(a,b){
			alert("失败"+b);
		}
	});
	
	var options = {
		url:addurl,
		dataType:'json',
		type:'post',
		success:function(data){
			if(data.status==0){
				alert(data.msg);
				Eop.Dialog.close("inventorydialog");
			}else{
				alert(data.msg);
			}
		},error:function(a,b){
			alert("失败"+b);
		}
	};
	$("#inventorygoosaddorsaveform").ajaxSubmit(options);
	
}

function showAddOrUpdateDialog(){
	Eop.Dialog.open("inventorydialog");
	var url = ctx+"/shop/admin/inventory!wareHouseList.do?ajax=yes"
	$("#inventorydialog").load(url,function(){
		//下一步
		$("#inventory_next_btn").unbind("click").bind("click",function(){
			var hos_id = $("input[name=house_id]:checked").val();
			if(!hos_id){
				alert("请选择仓库");
				return ;
			}
			var checkurl = ctx+"/shop/admin/inventory!checkHasGoodsInventory.do?ajax=yes"
			$.ajax({
				url:checkurl,
				dataType:'json',
				type:'post',
				data:{house_id:hos_id},
				success:function(data){
					if(data.status==1){
						if(window.confirm(data.msg)){
							Eop.Dialog.close("inventorydialog");
							showEditDialog(hos_id,data.inventoryid);
						}
					}else{
						showEditDialog(hos_id);
					}
				},error:function(a,b){
					alert("出错了，请重试"+b);
				}
			});
		});
		
	});
}

function showEditDialog(house_id,inventory_id){
	var nexturl = ctx+"/shop/admin/inventory!showInventoryEdit.do?ajax=yes&house_id="+house_id;
	if(inventory_id){
		Eop.Dialog.open("inventorydialog");
		nexturl += "&inventory_id="+inventory_id+"&action=1";
	}
	$("#inventorydialog").load(nexturl,function(){
		$("#addgoodsBtn").unbind("click").bind("click",searchGoods);
		//提交保存事件
		$("#inventory_submit_btn").unbind("click").bind("click",function(){
			addOrSaveInventory();
		});
	});
}

var select_order_id = "";

$(function(){
	Eop.Dialog.init({id:"inventorydialog",modal:true,title:"盘点管理", height:"750px",width:"850px"});
	Eop.Dialog.init({id:"inventorygoodsdialog",modal:true,title:"选择商品", height:"550px",width:"750px"});
	
	$("#inventorySearchBtn").bind("click",function(){
		inventoryform.submit();
	});
	
	$("a[name=goods_delete]").live("click",function(){
		$(this).closest("tr").remove();
	});
	
	$("#addPurorderOrederBtn").bind("click",showAddOrUpdateDialog);
	
	
	$(".grid tbody tr").bind("click",function(){
		var inventory_id = $(this).find("td span[name='inventory_id']").html();
		if(inventory_id==select_order_id){
			$("#iframe_tr").hide();
			select_order_id = "";
			return ;
		}
		select_order_id = inventory_id;
		var url = ctx+"/shop/admin/inventory!showDetial.do?inventory_id="+inventory_id;
		$("#iframe_tr").show().insertAfter($(this)).find("iframe").attr("src",url);
	});
});
</script>
