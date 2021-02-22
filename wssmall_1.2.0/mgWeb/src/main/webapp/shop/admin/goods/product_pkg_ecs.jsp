<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
loadScript("goods/js/product_pkg_ecs.js");
</script>
<style>
.tableform {
background:none repeat scroll 0 0 #EFEFEF;
border-color:#DDDDDD #BEC6CE #BEC6CE #DDDDDD;
border-style:solid;
border-width:1px;
margin:10px;
padding:5px;
}
.division  {
background:none repeat scroll 0 0 #FFFFFF;
border-color:#CCCCCC #BEC6CE #BEC6CE #CCCCCC;
border-style:solid;
border-width:1px 2px 2px 1px;
line-height:150%;
margin:10px;
padding:5px;
white-space:normal;
}
h4{
font-size:1.2em;
font-weight:bold;
line-height:1.25;
}
h1, h2, h3, h4, h5, h6 {
clear:both;
color:#111111;
margin:0.5em 0;
}

thead tr td {
font-weight:bold;
}

.month-fee-selected{
	background:none repeat scroll 0 0 #F1F7FD;
}

</style>
<div class="rightDiv" style="overflow: auto;height:980px;">
	<div class="input" >
		<div class="tableform">
			<h4>&nbsp;&nbsp;货品包:&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0);" style="margin-right:10px;" class="graybtn1" id="selectPkgBtn"><span>选择货品包</span></a>
			</h4>
			<div class="division" id ="productPkgDiv">
			  	<table width="100%" id="productPkgTables">
				    <thead>
						<tr>
						 	<td>
						 		<input type="hidden" onclick="selectedChange()"  id="togggleChked"  />
						 		<input type="hidden" name="md" id="md"/>
						 	</td> 
							<td style="width:50%;">货品包名称</td>
							<td style="width:25%;">货品包类型</td>
							<td style="width:15%;">创建时间</td>
							<td style="width:10%;"></td>
						</tr>
				    </thead>
		     	</table>
		    </div>
		</div>
		<div class="tableform">
	  	<h4>&nbsp;&nbsp;货品包属性:</h4>
	    <form action="goods!searchPkgGoodsECS.do" id="searchPkgGoodsForm" method="post">
		    <div class="division searchformDiv" id ="pkgProps">
		    	<table width="100%" id="pkgPropsTable">
		            <tbody>
						<tr id="color">
							<td>颜色</td>
							<td>
								<input type="hidden" name="params.relation_id" id="relation_id">
								<input type="hidden" name="params.colors">
								
								<input type="checkbox" name="color_all" id="color_all">全选
							</td>
						</tr>
						<tr id="net_3g">
							<td>3G套餐</td>
							<td><input type="hidden" name="params.lvls_3g" value="">
								<input type="checkbox" name="all_3g" id="all_3g">全选
							</td>
							<td>
								<input type="checkbox" name="3g_46" lvl="46">46
							</td>
							<td><input type="checkbox" name="3g_66" lvl="66">66</td>
							<td><input type="checkbox" name="3g_96" lvl="96">96</td>
							<td><input type="checkbox" name="3g_126" lvl="126">126</td>
							<td><input type="checkbox" name="3g_156" lvl="156">156</td>
							<td><input type="checkbox" name="3g_186" lvl="186">186</td>
							<td><input type="checkbox" name="3g_226" lvl="226">226</td>
							<td><input type="checkbox" name="3g_286" lvl="286">286</td>
							<td><input type="checkbox" name="3g_386" lvl="386">386</td>
							<td><input type="checkbox" name="3g_586" lvl="586">586</td>
							<td><input type="checkbox" name="3g_886" lvl="886">886</td>
						</tr>
						<tr id="net_4g">
							<td>4G套餐</td>
							<td><input type="hidden" name="params.lvls_4g" value="">
								<input type="checkbox" name="all_4g" id="all_4g">全选
							</td>
							<td><input type="checkbox" name="4g_76" lvl="76">76</td>
							<td><input type="checkbox" name="4g_106" lvl="106">106</td>
							<td><input type="checkbox" name="4g_136" lvl="136">136</td>
							<td><input type="checkbox" name="4g_166" lvl="166">166</td>
							<td><input type="checkbox" name="4g_196" lvl="196">196</td>
							<td><input type="checkbox" name="4g_296" lvl="296">296</td>
							<td><input type="checkbox" name="4g_396" lvl="396">396</td>
							<td><input type="checkbox" name="4g_596" lvl="596">596</td>
						</tr>
		            </tbody>
		     	</table>
		    </div>
		    <div style="text-align:center;">
		   		<a href="javascript:void(0);" style="margin-right:10px;" class="graybtn1" id="searchPkgGoodsBtn"><span>查询</span></a>
		   		&nbsp;&nbsp;&nbsp;&nbsp;
		   		<a href="javascript:void(0);" style="margin-right:10px;" class="graybtn1" id="publishPkgBtn"><span>发布</span></a>
		   		&nbsp;&nbsp;&nbsp;&nbsp;
		   		<a href="javascript:void(0);" style="margin-right:10px;" class="graybtn1" id="pkgMarketDisable"><span>停用</span></a>
		   	</div>
	   	</form>
	  	</div>
	</div>
	<div style="clear: both; padding-top: 5px;"></div>
	<div class="input" >
 		<h4>&nbsp;&nbsp;商品列表:&nbsp;&nbsp;&nbsp;&nbsp;
 			<a href="javascript:void(0);" style="margin-right:10px;" class="graybtn1" id="publishGoodsBtn"><span>发布</span></a>
 			<a href="javascript:void(0);" style="margin-right:10px;" class="graybtn1" id="goodsMarketDisable"><span>停用</span></a>
 		</h4>
    	<form id="goodsGridForm" class="grid">
			<grid:grid from="webpage" formId="searchPkgGoodsForm" ajax="yes">
				<grid:header>
					<grid:cell style="width:20px;"></grid:cell>
					<grid:cell style="width:200px;">SKU</grid:cell>
					<grid:cell style="width:400px;">商品名</grid:cell>
					<grid:cell >商品类型</grid:cell>
					<grid:cell>发布状态</grid:cell>
					<grid:cell>状态</grid:cell>
					<grid:cell>创建时间</grid:cell>
					<grid:cell style="width:90px;">操作</grid:cell>
				</grid:header>
				<grid:body item="goods">
					<grid:cell>
						<input type="checkbox" name="id" value="${goods.goods_id }" mk="${goods.market_enable}"/>
					</grid:cell>
					<grid:cell>&nbsp;${goods.sku }</grid:cell>
					<grid:cell>&nbsp;${goods.name } </grid:cell>
					<grid:cell>&nbsp;${goods.type_name } </grid:cell>
					<grid:cell>&nbsp;
						<c:if test="${goods.state == 0}">未发布</c:if><c:if test="${goods.state != 0}">已发布</c:if>
					</grid:cell>
					<grid:cell>&nbsp;
						<c:if test="${goods.market_enable == 0}">停用</c:if><c:if test="${goods.market_enable != 0}">启用</c:if>
					</grid:cell>
					<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${goods.create_time}"></html:dateformat></grid:cell>
					<grid:cell>&nbsp;
						<a href="goods!goodsEditEcs.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}">修改 </a>
					</grid:cell>
				</grid:body>
		
			</grid:grid>
		</form>
	</div>
</div>
<div id="package_dialog" ></div>
<div id="goods_pub_dialog" ></div>
<div id="goods_org_list" align="center"></div>

