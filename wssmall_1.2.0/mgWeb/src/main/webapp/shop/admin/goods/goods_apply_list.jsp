<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
  <script type="text/javascript">
loadScript("js/goods_apply.js");

</script>
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
<div ><form action="goods!list_apply.do?isApplyParStock=yes" id="searchApplyListForm" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	  	<input type="text" style="display: none;" id="hidden_goodsid"/>
	    	    <tbody><tr>
	    	      <th>商品名称:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="name" value="${name }" class="searchipt" /></td>
	    	      <th>商品编号:</th>
	    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 90px" name="sn" value="${sn }" /> </td>
	    	      <th>商品类型：</th>
	    	      <td>
	    	      	<html:selectdict curr_val="${typeCode}" appen_options="<option value=''>--全部--</option>" id="goodsTypeSelected" name="typeCode" attr_code="dc_card_type" style="width:153px;"></html:selectdict>
	    	      </td>
	    	      <th>开始时间：</th>
					<td>
					<input size="15" type="text"  name="startTime" id="startTime"
								value='${startTime}'
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date" /> 
					结束时间：
					<input size="15" type="text"  name="endTime" id="endTime"
								value='${endTime}'
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date"/> 
					</td>
	    	      <td><input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"   id="button" name="button"></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
	</form>
	<br />
	<form id="gridform" class="grid">
		<grid:grid from="webpage" >
			<grid:header>
				<!--<grid:cell width="50px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>-->
				
				<grid:cell sort="name" width="250px">商品名称</grid:cell>
				<grid:cell sort="sn" width="200px">商品编号</grid:cell>
				<grid:cell sort="type_id" width="120px">商品类型</grid:cell>
				<!--<grid:cell sort="price">销售价格</grid:cell>
				<grid:cell sort="store">库存</grid:cell>
				<grid:cell sort="market_enable">上架</grid:cell>
				<grid:cell sort="brand_id">品牌</grid:cell>
				<grid:cell sort="lan_id">归属本地网</grid:cell>
				<grid:cell sort="staffNo" width="100px">商户名称</grid:cell>-->
				
				<grid:cell sort="create_time">创建时间</grid:cell>
				<grid:cell sort="store">可申请库存</grid:cell>
				<grid:cell >发布区域</grid:cell>
				<grid:cell width="50px">操作</grid:cell>
			</grid:header>
			<grid:body item="goods">

				<!--<grid:cell>
					<input type="checkbox" name="id" value="${goods.goods_id }" /></grid:cell>-->
				
				<grid:cell>&nbsp;
        
<%--       			 <a href="../wssdetails-${goods.goods_id }.html" target="_blank"></a>--%>
						<span>
							<a >${goods.goods_name}</a>
							<!-- <a href="${ctx}/wssdetail-${goods.goods_id }.html" target="_blank">${goods.goods_name}</a> -->
						</span> 

				</grid:cell>
				<grid:cell>&nbsp;${goods.sn} </grid:cell>
				<grid:cell>&nbsp;
		        ${goods.type_name}
		         </grid:cell>
				<!--<grid:cell>&nbsp;<fmt:formatNumber
						value="${goods.price}" type="currency" pattern="￥.00" />
				</grid:cell>
				<grid:cell>&nbsp;${goods.store} </grid:cell>
				<grid:cell>&nbsp;<c:if test="${goods.market_enable==0}">否</c:if>
					<c:if test="${goods.market_enable==1}">是</c:if>
				</grid:cell>
				<grid:cell>&nbsp;${goods.brand_name} </grid:cell>
				<grid:cell>&nbsp;${goods.lan_name} </grid:cell>
				<grid:cell>&nbsp;${goods.agent_name} </grid:cell>-->
				
				
				<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${goods.create_time}"></html:dateformat>
				</grid:cell>
				
				<grid:cell>
					<c:if test="${goods.goods_isHref == '0'}">
						&nbsp;${goods.goods_stock} 
					</c:if>
					<c:if test="${goods.goods_isHref != '0'}">
						<a href="javascript:;" class="stock_detail" goodsid='${goods.goods_id }'   goodsType='${goods.goodsType}' >&nbsp;${goods.goods_stock} </a>
					</c:if>
				</grid:cell>
				<grid:cell>&nbsp;
					<a href="javascript:;" class="areaMsg" goodsid='${goods.goods_id }' >详细</a>
				</grid:cell>
				<grid:cell>
				<a href="javascript:;" class="apply_use" goodsid='${goods.goods_id }' >申请</a>
					<%--<input class="apply_use" goodsid='${goods.goods_id }' type="button" value=" 申 请 " />
					<img class="apply_use" goodsid='${goods.goods_id }' src="/wssmall/shop/admin/images/transparent.gif" title="申请">--%>
				</grid:cell>

			</grid:body>

		</grid:grid>
	</form>
	<!-- 审核页面 -->
	<div id="apply_w_dialog" >
		
	</div>
	<!-- 通过的商品发布区域页面 -->
	<div id="auditAreaOk" >
	</div>
	<div style="clear: both; padding-top: 5px"></div>
</div>

<div id="cloud_stock_dialog">
</div>
<div id="card_stock_dialog">
</div>
<div id="contract_stock_dialog">
</div>

<script type="text/javascript">

$(function(){
	$.ajax({
		url:basePath+'goods!getCatTree.do?ajax=yes',
		type:"get",
		dataType:"html",
		success:function(html){
			var serachCatSel =$(html).appendTo("#searchCat");
			serachCatSel.removeClass("editinput").attr("name","catid");
			serachCatSel.children(":first").before("<option value=\"\" selected>全部类别</option>");
			<c:if test="${catid!=null}">serachCatSel.val(${catid})</c:if>
		},
		error:function(){
			alert("获取分类树出错");
		}
	});

	$("#tag_chek").click(function(){
		if(this.checked)
			$("#tagspan").show();
		else{
			$("#tagspan").hide();
			$("#tagsel option").attr("selected",false);
		}
	});
	
	$("#agent_chek").click(function(){
		if(this.checked)
			$("#agentspan").show();
		else{
			$("#agentspan").hide();
			$("#agentsel option").attr("selected",false);
		}
	});
});

</script>
