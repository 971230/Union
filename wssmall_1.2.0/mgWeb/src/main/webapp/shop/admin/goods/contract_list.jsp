<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listContract">
<form action="javascript:;" method="post" id="choiceContractform">
		<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	     <tbody>
	    <tr>
	    <th>合约名称</th>
	    <td>
		   <input type="text"  name="contract_name" id="contract_name" value='${contract_name}' style="width: 288px;" maxlength="60" class="ipttxt" /> 
		</td>
		<th></th> 
	   	<td style="text-align:right;">
		 <input class="comBtn" type="submit" name="serContractBtn" id="serContractBtn" value="搜索" style="margin-right:5px;"/>
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
                    <grid:cell width="50px"><input name="check" type="hidden" id="toggleChk" onClick="selectContractChange()"></grid:cell>
                    <grid:cell>合约编码</grid:cell>
                    <grid:cell>合约名称</grid:cell>
                </grid:header>
                <grid:body item="goods">
                    <grid:cell><input type="radio"  name="goodsId" value="${goods.product_id }" gid="${goods.goods_id }"  pid="${goods.product_id }" id="names" goods_name="${goods.name }" /></grid:cell>
                    <grid:cell>${goods.sku}</grid:cell>
                    <grid:cell>${goods.name} <input type="hidden" name="goods_name" value="${goods.name}"></grid:cell>
                </grid:body>
            </grid:grid>
            </br></br>
            
            <div style="text-align:center;margin-top:3px;">
                <input name="btn" type="button" id="selContractBtn" value="确定"  class="graybtn1" />
            </div>
            
        </form>
   
</div>
<script type="text/javascript">
function selectContractChange(){
	$("#listContract").find("input[name=goodsId]").attr("checked",$("#listContract").find("#toggleChk").is(":checked"));	
}
var addGoods= {
        init:function(){
            var me=this;
            $("#selContractBtn").unbind("click").bind("click",function() {
            	me. addSelectGoods();
	        });
            //单选框双击选择
        	$("#listContract").find(".gridbody tbody tr").live("dblclick",function(){
            	//alert("doubleClick");
            	var box = $(this).find("input[type='checkbox'],input[type='radio']");
            	if($(box).attr("type") == "radio"){
            		box.attr("checked",true);
            		me. addSelectGoods();
            	}
        	});
            
            $("#serContractBtn").unbind("click").bind("click",function() {
                me.searchBottonClks();
            });
        },
        addSelectGoods:function(){
			/* var goods=$("input[name=goodsId][type=checkBox]:checked").val();
        	if(!goods){
    			alert("请选择商品！");
    			return ;
    		} */
        	var goodsid = $("#listContract input[name='goodsId']:checked");
			goodsid.each(function(idx,item){
			   var tr = $(item).parents("tr");
			   $("#contractTables").append(tr.append("<td><a href='javascript:void(0);' name='gooddelete'>删除</a></td>"));
			   tr.find("input[type='radio']").hide();
			   $(tr).removeClass("grid-table-row-selected");
			   $("#contractTables tbody").html("");
			   $("#contractTables").append(tr);
	         }),
			Eop.Dialog.close("contractList");
    },
    searchBottonClks : function() {
    	var contract_name = $.trim($("#contract_name").val());
    	contractConfig.showContract(contract_name);
   }
}
addGoods.init();
</script>

