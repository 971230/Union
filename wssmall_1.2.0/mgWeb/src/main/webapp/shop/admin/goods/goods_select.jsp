<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listGoods">
	<form action="javascript:;" method="post" id="choiceform1">
		<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	     <tbody>
	    <tr>
	    <th>商品名称</th>
	    <td>
		   <input type="text"  name="name" id="cres" value='${name}' style="width: 148px;" maxlength="60" class="ipttxt" /> 
		</td>
		<th></th> 
	   	<td>
		 <input class="comBtn" type="button" name="searchBtn" id="serButton" value="搜索" style="margin-right:5px;"/>
		</td>
	   </tr>
	  </tbody>
	  </table>
    	</div>  	
	</form>	
        <form  id="form_tc" class="grid">
            <grid:grid from="webpage" ajax="yes" formId ="gridform">
                <grid:header>
                    <grid:cell width="50px"></grid:cell>
                    <grid:cell>SKU</grid:cell>
                    <grid:cell>商品名称</grid:cell>
                    <grid:cell>商品类型</grid:cell>
                    <grid:cell>商品分类</grid:cell>
                    <grid:cell>商品品牌</grid:cell>
                    <grid:cell>状态</grid:cell>
                </grid:header>
                <grid:body item="goods">
                    <grid:cell>
                    	<input type="radio"  name="goodsId" sn="${goods.sn }" sku="${goods.sku }" typeid="${goods.type_id }" 
                    			gid="${goods.goods_id }" pid="${goods.product_id }" id="names" goods_name="${goods.name }" mcode="${goods.model_code }"/>
                    </grid:cell>
                    <grid:cell>${goods.sku}</grid:cell>
                    <grid:cell>${goods.name} <input type="hidden" name="goods_name" value="${goods.name}"></grid:cell>
                    <grid:cell>${goods.type_name}</grid:cell>
                    <grid:cell>${goods.cat_name}</grid:cell>
                    <grid:cell>${goods.brand_name}</grid:cell>
                    <grid:cell>
                    	<c:choose>
                    		<c:when test="${goods.market_enable==1 }">正常</c:when>
                    		<c:when test="${goods.market_enable==0 }">停用</c:when>
                    	</c:choose>
                    </grid:cell>
                </grid:body>
            </grid:grid>
            </br>
            
            <div style="margin-left: auto;margin-right: auto;" align="center">
                <input name="btn" type="button" id="selProductsBtn" value="确定"  class="graybtn1" />
            </div>
            
        </form>
   
</div>
<script type="text/javascript">
function selectChange(){
	$("#listGoods").find("input[name=goodsId]").attr("checked",$("#toggleChk").is(":checked"));	
}
var addGoods= {
        init:function(){
            var me=this;
            $("#selProductsBtn").unbind("click").bind("click",function() {
		      	me.addSelectGoods();
	        });
            $("#serButton").bind("click",function() {
                me.searchBottonClks();
           });
        },
        addSelectGoods:function(){
        	var goods_type = $("#type_id").val();
        	var productsid = $("#listGoods input[name='goodsId']:checked");
    		productsid.each(function(idx,item){
			   	var pid = $(item).attr("pid");
			   	var gid = $(item).attr("gid");
			   	var sku = $(item).attr("sku");
			   	var sn = $(item).attr("sn");
			   	var typeid = $(item).attr("typeid");
			   	var name = $(item).attr("goods_name");
			   	$("#goodsInputDialog").focus();
			   	$("#goodsInputDialog").val(name);
			   	$("#prod_offer_code").val(sku);
			   	$("#planTitleInputDialog").val("");
				$("#contract_month").val("");
				$("#is_attached").val("-1");
			   	$.ajax({
					url: basePath + "/shop/admin/goods!getPlanTitleByGoodsId.do?ajax=yes&goods_id="+gid,
					type:"post",
					data:{},
					dataType:"json",
					success:function(data){
						$("#planTitleInputDialog").val(data.planTitle);
						$("#contract_month").val(data.packageLimit);
						$("#is_attached").val(data.isAttached);
					},
					error:function(){
						
					}
				});	
	        }),
	        Eop.Dialog.close("listGoodsDialog");
    },
    searchBottonClks : function() {
        var me = this;
        var name = $("#cres").val();
        var url =ctx + "/shop/admin/goods!goodsSelectList.do?ajax=yes&type=goods&name="+encodeURI(encodeURI(name,true),true);
        $("#goodsDialog").load(url,function(){});
    }
}
addGoods.init();
</script>

