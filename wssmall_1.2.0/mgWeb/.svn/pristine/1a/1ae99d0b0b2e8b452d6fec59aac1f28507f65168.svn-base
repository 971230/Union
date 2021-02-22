<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<script type="text/javascript" >
loadScript("js/GoodsList.js");
</script>
<div class="grid">

	<div class="comBtnDiv">
		<form action="theForm" id="theForm" name="theForm">
		<input class="comBtn" type="button" onclick="reduccion();" value="还原" style="margin-right:10px;outline-style:none"/>
		<input class="comBtn" type="button" onclick="claro();" value="清除" style="margin-right:10px;outline-style:none"/>
		<div style="clear:both"></div>
	</form>
	</div>
<form method="POST">
<grid:grid  from="webpage" >

	<grid:header>
		<grid:cell  width="50px"><input type="checkbox" id="toggleChk"/></grid:cell> 
		
		<grid:cell sort="name" width="250px">商品名称</grid:cell>
		<grid:cell sort="sn" width="180px">商品编号</grid:cell> 
		<grid:cell sort="sn" width="180px">SKU</grid:cell> 
		<grid:cell sort="type_name">商品类型</grid:cell>
		
<%--		<grid:cell sort="cat_id"  width="100px">分类</grid:cell> --%>
		<grid:cell sort="price">销售价格</grid:cell>
		<!-- <grid:cell sort="store">库存</grid:cell> -->
		<!--<grid:cell sort="market_enable">上架</grid:cell>-->
		<!--<grid:cell sort="brand_id">品牌</grid:cell>-->
		<grid:cell sort="type_name">类型</grid:cell>
		<grid:cell sort="create_time">创建时间</grid:cell>
		
		
	</grid:header>


  <grid:body item="goods" >
  		<grid:cell><input type="checkbox" name="id" value="${goods.goods_id }" /></grid:cell>
        
        <grid:cell>&nbsp;<a >${goods.name }</a></grid:cell> 
        <grid:cell>&nbsp;${goods.sn } </grid:cell>
        <grid:cell>&nbsp;${goods.sku } </grid:cell>
        <grid:cell>&nbsp;${goods.type_name} </grid:cell>
<%--        <grid:cell>&nbsp;${goods.cat_name} </grid:cell> --%>
        <grid:cell>&nbsp;
        	<c:if test="${goods.have_price==1}">
        		<fmt:formatNumber value="${goods.price}" type="currency" pattern="￥.00" /></c:if>
			<c:if test="${goods.have_price==0}">无</c:if>
         </grid:cell> 
        <!-- <grid:cell>&nbsp;${goods.store} </grid:cell> -->
        <!--<grid:cell>
	        <c:if test="${goods.market_enable==0}" >&nbsp;&nbsp;否</c:if> 
	        <c:if test="${goods.market_enable==1}" >&nbsp;&nbsp;是</c:if></grid:cell>
        <grid:cell>&nbsp;${goods.brand_name} </grid:cell> -->
                <grid:cell>&nbsp;<a >${goods.type }</a></grid:cell> 
        <grid:cell>
        <html:dateformat pattern="yyyy-MM-dd" d_time="${goods.create_time}"></html:dateformat> </grid:cell> 
        
  </grid:body> 
  
  
</grid:grid>
</form>  
</div>
<script>
function reduccion(){
	var map =getEckValue("id");
	if(map[0]<1){
		MessageBox.show("请选择要操作的记录", 2, 2000);
		return ;
	}
	if(!confirm("确认要将这些商品还原吗？")){	
		return ;
	}
	doAjax("theForm", "goods!reduccion.do?ids="+map[1], "callBack");
}

function claro(){
	var map =getEckValue("id");
	if(map[0]<1){
		MessageBox.show("请选择要操作的记录", 2, 2000);
		return ;
	}
	if(!confirm("确认要将这些商品清除吗？")){	
		return ;
	}
	doAjax("theForm", "goods!claro.do?ids="+map[1], "callBack");
}
</script>