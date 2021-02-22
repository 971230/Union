<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listPlanTitles">
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
                    <grid:cell width="50px"><input name="check" type="radio" id="toggleChk" onClick="selectChange()"></grid:cell>
                    <grid:cell width="200px">SKU</grid:cell>
                    <grid:cell width="350px">套餐名称</grid:cell>
                    <grid:cell>套餐分类</grid:cell>
                    <grid:cell>套餐品牌</grid:cell>
                    <grid:cell>状态</grid:cell>
                </grid:header>
                <grid:body item="goods">
                    <grid:cell>
                    	<input type="radio"  name="goodsId" sn="${goods.sn }" sku="${goods.sku }" typeid="${goods.type_id }" 
                    			gid="${goods.goods_id }" pid="${goods.product_id }" id="names" goods_name="${goods.name }" mcode="${goods.model_code }"/>
                    </grid:cell>
                    <grid:cell>${goods.sku}</grid:cell>
                    <grid:cell>${goods.name} <input type="hidden" name="goods_name" value="${goods.name}"></grid:cell>
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
                <input name="btn" type="button" id="selPlanTitleBtn" value="确定"  class="graybtn1" />
            </div>
            
        </form>
   
</div>
<script type="text/javascript">
function selectChange(){
	$("#listPlanTitles").find("input[name=goodsId]").attr("checked",$("#toggleChk").is(":checked"));	
}
var addGoods= {
        init:function(){
            var me=this;
            $("#selPlanTitleBtn").unbind("click").bind("click",function() {
		      	me.addSelectGoods();
	        });
            $("#serButton").bind("click",function() {
                me.searchBottonClks();
           });
        },
        addSelectGoods:function(){
        	var productsid = $("#listPlanTitles input[name='goodsId']:checked");
    		productsid.each(function(idx,item){
			   var pid = $(item).attr("pid");
			   var gid = $(item).attr("gid");
			   var sku = $(item).attr("sku");
			   var sn = $(item).attr("sn");
			   var typeid = $(item).attr("typeid");
			   var name = $(item).attr("goods_name");
			   $("#planTitleInputDialog").focus();
			   $("#planTitleInputDialog").val(name);
	         }),
	         Eop.Dialog.close("listPlanTitleDialog");
    },
    searchBottonClks : function() {
        var me = this;
        var name = $("#cres").val();
        var url =ctx + "/shop/admin/goods!planTitleSelectList.do?ajax=yes&type=product&type_id=10002&name="+encodeURI(encodeURI(name,true),true);
        $("#planTitleDialog").load(url,function(){});
    }
}
addGoods.init();
</script>

