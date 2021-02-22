<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/mgWeb/publics/js/admin/Tab.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/warehouse/js/warehouse_assgin.js"></script>

<style>
.distributorL{ width:65%; float:left;}
.distributorR{ width:35%; float:left;}
.noborder{
  border-top-style: none;
  border-right-style: none;
  border-left-style: none;
  border-bottom-style: none;
  }
</style>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div style="display: block;" class="partner_detail">
	<div class="tab-bar" style="position: relative;">
	<ul class="tab">
		<li id="assign" class="active">库存商品分配</li>
		<li id="assigned">已分配商品</li> 
	</ul>
	</div>
	<div class="tab-page">
		<div id="assignTab"></div>
		<div id="assignedTab"></div>
	</div>
	
</div>
<div id="select_goods_div"></div>
<div id="assign_goods_div"></div>
<div id="recover_goods_div"></div>
<div id="selectShop_dialog"></div>
<div id="query_sel_vir_dialog"></div>
<div id="sel_virtual_div"></div>
 <script>
 var type = "goods";
 var type_name = "商品";
 
$(function(){
	goodsTabs.init();
	selectGoods.init();
	selVirtual.init();
	
});
</script>