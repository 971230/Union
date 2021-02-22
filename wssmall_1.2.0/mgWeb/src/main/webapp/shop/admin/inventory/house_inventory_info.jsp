<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">
<c:if test="${warhouseInventory.status==0 }">
<div class="toolbar">
	<a class="sgreenbtn" href="javascript:void(0);" id="imventoryEditBtn" inventoryid="${warhouseInventory.inventory_id}" houseid="${warhouseInventory.house_id}">
	     <span >修改</span>
	</a>
	
	<a class="sgreenbtn" href="javascript:void(0);" id="imventoryConfirmBtn" inventoryid="${warhouseInventory.inventory_id}">
	     <span >确认/作废</span>
	</a>
</div>	
</c:if> 
	
<div style="display: block;" class="order_detail">
	<div class="tab-page">
		<div>
			<div class="grid">
		      <table cellspacing="0" cellpadding="0" border="0" width="100%"  >
		        <thead>
		        <tr>
		          <th>商品ID</th>
		          <th>商品名称</th>
		          <th>原有库存</th>
		          <th>现有库存</th>
		        </tr>
		        </thead>
		        <tbody>
		        <c:forEach items="${goodsInventory}" var="item">
		         <tr>
		          <td>${item.goods_id }</td>
		          <td>${item.goodsName }</td>
		          <td>${item.original_store }</td>
		          <td>${item.store }</td>
		        </tr>
				</c:forEach>
		         </tbody>
		      </table>
		</div>

			<div class="tableform">
				<table cellspacing="0" cellpadding="0" border="0" style="width: auto;" class="orderdetails_basic">
				  <tbody>
				  <tr>
				    <td style="vertical-align: top;">
						<h5>盘点信息</h5>
						<div class="division">
						    <table cellspacing="0" cellpadding="0" border="0">
						      <tbody>
						      <tr>
						        <th style="width: 80px;">盘点ID：</th>
						        <td>${warhouseInventory.inventory_id }</td>
						      </tr>
						       <tr>
						        <th style="width: 80px;">盘点名称：</th>
						        <td>${warhouseInventory.name }</td>
						      </tr>
						      <tr>
						        <th style="width: 80px;">盘点时间：</th>
						        <td>
						        	${fn:substring(warhouseInventory.create_time, 0 , 19)}
						        </td>
						      </tr>
						      <tr>
						        <th style="width: 80px;">操作员：</th>
						        <td>
						        	${warhouseInventory.op_name }
								</td>
						      </tr>
						      <tr>
						        <th style="width: 80px;">状态：</th>
						        <td style="color: red;">
						        	<c:if test="${warhouseInventory.status==0 }">已新建</c:if>
						        	<c:if test="${warhouseInventory.status==1 }">已确认</c:if>
						        	<c:if test="${warhouseInventory.status==2 }">已复核</c:if>
						        	<c:if test="${warhouseInventory.status==-1 }">已作废</c:if>
						        </td>
						      </tr>
						      <tr>
						        <th style="width: 80px;">确认人：</th>
						        <td>${warhouseInventory.confirm_name}</td>
						      </tr>
						    </tbody>
						    </table>
						</div>
				    </td>
				  </tr>
				</tbody>
				</table>
				</div>
		</div>
	</div>
	
</div>
<!-- 不包裹 -->
<div id="confirm_inventory_dialog">

</div>

<script>
$(function(){
	Eop.Dialog.init({id:"confirm_inventory_dialog",modal:true,title:"盘点确认", height:"750px",width:"750px"});
	
	$("#imventoryEditBtn").bind("click",function(){
		var inventoryid = $(this).attr("inventoryid");
		var houseid = $(this).attr("houseid");
		window.parent.showEditDialog(houseid,inventoryid);
	});
	
	//Cmp.ajaxSubmit('common_form_c','',url,{},self.jsonBack,'json');
	$("#imventoryConfirmBtn").bind("click",function(){
		Eop.Dialog.open("confirm_inventory_dialog");
		var inventory_id=$(this).attr("inventoryid");
		var url = ctx+"/shop/admin/inventory!showConfirmDialog.do?ajax=yes&inventory_id="+inventory_id;
		$("#confirm_inventory_dialog").load(url,function(){
			$("#inventori_confirm_btn").bind("click",function(){
				var confirmUrl = ctx+"/shop/admin/inventory!confirmInventory.do?ajax=yes";
				Cmp.ajaxSubmit('inventori_confirm_form','',confirmUrl,{},function(data){
					if(data.status==0){
						alert(data.msg);
						Eop.Dialog.close("confirm_inventory_dialog");
						window.location.reload();
					}else{
						alert(data.msg);
					}
				},'json');
			});
		});
	});
});
</script>