<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
<div class="goodsList" id="listOffer">
<form action="javascript:;" method="post" id="choiceOfferform">
		<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	     <tbody>
	    <tr>
	    <th>套餐名称</th>
	    <td>
		   <input type="text"  name="offer_name" id="offer_name" value='${offer_name}' style="width: 288px;" maxlength="60" class="ipttxt" /> 
		</td>
		<th></th> 
	   	<td style="text-align:right;">
		 <input class="comBtn" type="submit" name="serOfferBtn" id="serOfferBtn" value="搜索" style="margin-right:5px;"/>
		</td>
	   </tr>
	  </tbody>
	  </table>
    	</div>  	
</form>	
</form> 
        <form  id="form_tc" class="grid">
            <grid:grid from="webpage" ajax="yes" formId ="gridform">
                <grid:header>
                    <grid:cell width="50px"><input name="check" type="checkbox" id="toggleChk" onclick="selectOfferChange();"></grid:cell>
                    <grid:cell>套餐编码</grid:cell>
                    <grid:cell>套餐名称</grid:cell>
                    <grid:cell>档次</grid:cell>
                </grid:header>
                <grid:body item="offer">
                    <grid:cell><input type="checkbox"  name="goodsId" value="${offer.goods_id }" gid="${offer.goods_id }" pid="${offer.product_id }" 
                                 id="names" goods_name="${offer.name }" month_fee="${offer.stype_id}" rel_code="${offer.rel_code}" /></grid:cell>
                    <grid:cell>${offer.sku}</grid:cell>
                    <grid:cell>${offer.name} <input type="hidden" name="offer_name" value="${offer.name}"></grid:cell>
                    <grid:cell>${offer.stype_id}</grid:cell>
                </grid:body>
            </grid:grid>
            </br></br>
            
            <div style="text-align:center;margin-top:3px;">
                <input name="btn" type="button" id="selOfferBtn" value="确定"  class="graybtn1" />
            </div>
            
        </form>
   
</div>
<script type="text/javascript">
function selectOfferChange(){
	$("#listOffer").find("input[name=goodsId]").attr("checked",$("#listOffer").find("#toggleChk").is(":checked"));		
}
var addGoods= {
        init:function(){
            var me=this;
            $("#selOfferBtn").unbind("click").bind("click",function() {
		        me.addSelectGoods();
	        });
            $("#serOfferBtn").unbind("click").bind("click",function() {
                me.searchBottonClks();
            });
            
        },
        addSelectGoods:function(){
			/* var goods=$("input[name=goodsId][type=checkBox]:checked").val();
        	if(!goods){
    			alert("请选择商品！");
    			return ;
    		} */
        	var goodsid = $("#listOffer input[name='goodsId']:checked");
			goodsid.each(function(idx,item){
			   var tr = $(item).parents("tr");
			   var gid = $(item).attr("gid");
			   var select = $("#offerTables input[gid='"+gid+"']");
			   if(select && select.length>0){
				   alert($(item).attr("goods_name")+"存在，不能重复添加!");
				   return ;
			   }
			   $("#offerTables").append(tr.append("<td><a href='javascript:void(0);' name='gooddeleteOffer'>删除</a></td>"));
			   tr.find("input[type='checkbox']").hide();
			   $(tr).removeClass("grid-table-row-selected");
			   $("#offerTables").append(tr);
	         }),
			Eop.Dialog.close("offerList");
			contractConfig.flreshParamsBg();
    },
    searchBottonClks : function() {
    	
    	var offer_name = $.trim($("#offer_name").val());
    	contractConfig.showOffer(offer_name);
   }
}
addGoods.init();
</script>

