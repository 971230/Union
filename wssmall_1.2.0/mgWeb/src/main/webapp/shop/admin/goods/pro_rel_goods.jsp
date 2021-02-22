<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listProducts">
<form action="javascript:;" method="post" id="choiceform1" style="display:none;">
		<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	     <tbody>
	    <tr>
	    <th>货品名称</th>
	    <td>
		   <input type="text"  name="names" id="cres" value='${names}' style="width: 148px;" maxlength="60" class="ipttxt" /> 
		</td>
		<th></th> 
	   	<td>
		 <input class="comBtn" type="submit" name="searchBtn" id="serButton" value="搜索" style="margin-right:5px;"/>
		</td>
	   </tr>
	  </tbody>
	  </table>
    	</div>  	
</form>	
      <form  id="form_tc" class="grid">
          <grid:grid from="webpage" ajax="yes" formId ="gridform">
              <grid:header>
			
			<grid:cell sort="sn" width="140px">SKU</grid:cell>
			<grid:cell sort="name" width="150px">商品名称</grid:cell>
			<grid:cell sort="cat_id" width="100px">商品类型</grid:cell>
			<grid:cell sort="cat_id" width="140px">商品分类</grid:cell>
			<grid:cell sort="cat_id">商品品牌</grid:cell>
			<grid:cell sort="create_time">创建时间</grid:cell>
			
			<grid:cell >状态</grid:cell>
			<grid:cell width="140px">销售组织</grid:cell>
		</grid:header>
		<grid:body item="goods">

			<grid:cell>&nbsp;${goods.sku } </grid:cell>
			<grid:cell>&nbsp;
					<span >
						<a href="goods!edit.do?goods_id=${goods.goods_id}&selfProdType=${selfProdType}&store=${goods.store}&have_stock=${goods.have_stock}" >${goods.goods_name }</a> </span>
			</grid:cell>
			<grid:cell>&nbsp;
				${goods.type_name}
			</grid:cell>
			<grid:cell>&nbsp;
				${goods.cat_name}
			</grid:cell>
			<grid:cell>&nbsp;
				${goods.brand_name}
			</grid:cell>
			<grid:cell>&nbsp;${goods.create_time}
			</grid:cell>
			<grid:cell><c:if test="${goods.market_enable == 1}">正常</c:if><c:if test="${goods.market_enable != 1}">注销</c:if></grid:cell>
			<grid:cell>
				${goods.org_name }
			</grid:cell>
		
			
		</grid:body>
          </grid:grid>
          </br>
          
      </form>
   <div style="clear: both; padding-top: 5px;"></div>
   <div class="submitlist" align="center">
		<table>
			<tr>
				<td style="text-align:center;">
					<input id="confirmBtn" type="button" value=" 确定 " class="graybtn1" style="margin-right:10px;">
					<input id="cancelBtn" type="button" value=" 取消 " class="graybtn1" style="margin-right:10px;">
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
function selectChange(){
	$("#listGoods").find("input[name=goodsId]").attr("checked",$("#toggleChk").is(":checked"));	
}
var addGoods= {
        init:function(){
            var me=this;
            $("#selProductsBtn").unbind("click").bind("click",function() {
		      me. addSelectGoods();
	        });
            $("#serButton").bind("click",function() {
                me.searchBottonClks();
           });
        },
        addSelectGoods:function(){
			/* var goods=$("input[name=goodsId][type=checkBox]:checked").val();
        	if(!goods){
    			alert("请选择商品！");
    			return ;
    		} */
        	var productsid = $("#listProducts input[name='productsId']:checked");
    		productsid.each(function(idx,item){
			   var tr = $(item).parents("tr");
			   var gid = $(item).attr("gid");
			   var select = $("#tables input[gid='"+gid+"']");
			   if(select && select.length>0){
				   alert($(item).attr("goods_name")+"存在，不能重复添加!");
				   return ;
			   }
			   $("#tables").append(tr.append("<td><a href='javascript:void(0);' name='gooddelete'>删除</a></td>"));
			 	tr.find("input").hide();
			   $("#tables").append(tr);
	         }),
			Eop.Dialog.close("listAllGoods");
    },
    searchBottonClks : function() {
        var me = this;
        var options = {
                  url :'goodsPropertyAction!listAllGoods.do?ajax=yes',
                  type : "POST",
                  dataType : 'html',            
                  success : function(result) {  
                       $("#listAllGoods").empty().append(result);
                       me.init();
                  },
                  error : function(e,b) {
                       alert("出错啦:(");
                  }
        };
        $("#choiceform1").ajaxSubmit(options);
   }
}
addGoods.init();
</script>

