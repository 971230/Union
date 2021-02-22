<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script>var ctx ='${ctx}';</script>
<form method="post" id="choiceOfferform">
	<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
		    <tbody>
			    <tr>
			    <th>套餐名称</th>
			    <td>
				   <input type="text"  name="offer_name" id="offer_name" value='${offer_name}' style="width: 148px;" maxlength="60" class="ipttxt" /> 
				</td>
				<th></th> 
		  		<td>
		 			<input class="comBtn" type="button" name="searchOfferBtn" id="searchOfferBtn" value="搜索" style="margin-right:5px;"/>
				</td>
			   	</tr>
		  	</tbody>
	  	</table>
   	</div>  	
</form>	
<div class="grid" id="listOffer">
   	<form method="POST" id="listOfferForm">
    	<grid:grid from="webpage" formId="choiceOfferform" ajax="yes">
            <grid:header>
                <grid:cell width="50px"><input name="check" type="checkbox" id="toggleChk" onclick="selectOfferChange();"></grid:cell>
                <grid:cell>套餐编码</grid:cell>
                <grid:cell>套餐名称</grid:cell>
                <grid:cell>档次</grid:cell>
            </grid:header>
            <grid:body item="offer">
                <grid:cell><input type="checkbox"  name="goodsId" value="${offer.goods_id }" gid="${offer.goods_id }" pid="${offer.product_id }"
                             id="names" goods_name="${offer.name }" month_fee="${offer.month_fee}" rel_code="${offer.rel_code}" /></grid:cell>
                <grid:cell>
                	${offer.sku}
                	<input type="hidden" name="goods_ids" value="${offer.goods_id }"/>
                	<input type="hidden" name="product_ids" value="${offer.goods_id }"/>
                	<input type="hidden" name="rel_types" value="CONTRACT_OFFER"/>
                	<input type="hidden" name="rel_codes" value="" />
                </grid:cell>
                <grid:cell>${offer.name} <input type="hidden" name="offer_name" value="${offer.name}"></grid:cell>
                <grid:cell>${offer.stype_id}</grid:cell>
            </grid:body>
        </grid:grid>
        </br>
        
        <div style="margin-left: auto;margin-right: auto;" align="center">
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
            $("#searchOfferBtn").unbind("click").bind("click",function() {
                me.searchBottonClks();
            });
            
        },
        addSelectGoods:function(){
        	var goodsid = $("#listOffer input[name='goodsId']:checked");
        	if(goodsid.length==0){
        		alert("请选择套餐！");
        		return ;
        	}
        	$("#tc_content").show();
        	
			goodsid.each(function(idx,item){
			   var tr = $(item).parents("tr");
			   var pid = $(item).attr("pid");
			   var existFlg = false;
			   $("#offerTables input[name='product_ids']").each(function(){
				   if (pid == $(this).val()) {
					   existFlg = true;
					   return false;
				   }
			   });
			   
			   if (existFlg) {
				   alert($(item).attr("goods_name")+"存在，不能重复添加!");
				   return false;
			   }
			   
			   tr.find("td:first-child").remove();
			   tr.append("<td><a href='javascript:;'><img  class='delete' src='${ctx}/shop/admin/images/transparent.gif' > </a></td>");
			   $("#tcNode").append(tr);
			   $(tr).find("img.delete").click(function(){
				   GoodsAddEdit.deleteOfferRow($(this));
				});	 
	         });
			
			Eop.Dialog.close("offerListDialog");
    },
    searchBottonClks : function() {
    	var offer_name = $("#offer_name").val();
		 //提交搜索
		var url = 'goods!offerList.do?ajax=yes&offer_name='+encodeURI(encodeURI(offer_name,true),true);
		$("#offerListDialog").load(url,function(){
			Eop.Dialog.open("offerListDialog");
		});
   }
}
addGoods.init();
</script>

