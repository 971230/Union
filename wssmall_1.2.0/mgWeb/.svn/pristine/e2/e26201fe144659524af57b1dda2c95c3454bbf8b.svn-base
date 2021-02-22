<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listgoods">
<form id="goodsListForm" action="" method="post">
	<div class="searchformDiv">
		<table>
			<tr>
				<th>sku：</th>
				<td>
					<input type="text"  name="sku" id="sku" value='${sku}' style="width: 148px;" maxlength="60" class="ipttxt" />
				<th>商品名称：</th>
				<td>
					<input type="text"  name="name" id="name" value='${name}' style="width: 148px;" maxlength="60" class="ipttxt" />
					<input class="comBtn" type="button" id="goodsNameSearch" value="搜索" style="margin-right: 10px;" /></td>
				<th></th>
				<td>
				    <input type="button" style="float: right" class="comBtn" value="确定" id="goodsSure">
				</td>
			</tr>
		</table>
		<div style="clear: both"></div>
	</div>
</form>

        <form  id="form_tc" class="grid">
            <grid:grid from="webpage" ajax="yes" formId ="gridform">
                <grid:header>
                    <grid:cell width="50px"><input name="check" type="checkbox" id="toggleChk" onClick="selectChange()"></grid:cell>
                    <grid:cell>sku</grid:cell>
                    <grid:cell>商品名称</grid:cell>
                    <grid:cell>商品小类</grid:cell>
                </grid:header>
                <grid:body item="goods">
                    <grid:cell>
                    	<input type="checkbox"  name="goodsCodes" id="names" sku="${goods.sku }" 
                    	goodsName="${goods.name}" cat_name="${goods.cat_name}" 
                    	/>
                    </grid:cell>
                    <grid:cell>${goods.sku}</grid:cell>
                    <grid:cell>${goods.name}</grid:cell>
                    <grid:cell>${goods.cat_name}</grid:cell>
                </grid:body>
            </grid:grid>
            </br>
        </form>
</div>
<script type="text/javascript">
var addGoods={
        init:function(){
            var me=this;
            $("#goodsSure").unbind("click").bind("click",function() {
		      	me.addSelectGoods();
	        });
            $("#goodsNameSearch").bind("click",function() {
                me.searchBottonClks();
           });
        },
        
        addSelectGoods : function(){
        	var len = $("input[type='checkbox'][name='goodsCodes']:checked").length;
    		if(len<=0){
    			alert("请选择商品!");
    			return;
    		}
    		
    		var html = $("##goodsbody #goodsNode").html();
      		//var html = window.opener.document.getElementById("communityNode").innerHTML;
			$("input[type='checkbox'][name='goodsCodes']:checked").each(function() {
    			  var tr = "<tr>";
    			  var sku = $(this).attr("sku");
    			  var name = $(this).attr("goodsName");
    			  var cat_name = $(this).attr("cat_name");
    			  
    			  var select = $("##goodsbody #goodsNode input[id='"+sku+"']");
   			      if(select && select.length>0){
   				     alert("["+name+"]已经存在，不能重复添加!");
				     return ;
			      }
				  
   			      tr += "<td><input type='hidden' name='sku' id='"+sku+"' value='"+sku+"'/>"+sku+"</td>";
				  tr += "<td><input type='hidden' name='goodsName' id='"+name+"' value='"+name+"'/>"+name+"</td>";
				  tr += "<td><input type='hidden' name='cat_name' id='"+cat_name+"' value='"+cat_name+"'/>"+cat_name+"</td>";
				  tr += "<td><a href='javascript:;'><img  class='delete' src='${ctx}/shop/admin/images/transparent.gif' > </a></td>";
				  tr += "</tr>";
				  html += tr;
				  
			});
    		$("##goodsbody #goodsNode").html(html);
    		Eop.Dialog.close("goodsList");
        },
        
        searchBottonClks : function() {
        	var goods_type=$("#goods_type option:selected").val(); 
			var sku = $("#sku").val();
			var name = $("#name").val();
	        var url =ctx + "/shop/admin/goods!addGoodsFromSale.do?ajax=yes&sku="+encodeURI(encodeURI(sku,true),true)+"&name="+encodeURI(encodeURI(name,true),true)+"&goods_type="+encodeURI(encodeURI(goods_type,true),true);
	        $("#goodsDialog").load(url,function(){});
        }
}

addGoods.init();

</script>