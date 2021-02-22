<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
</form> 
        <form  id="form_tc" class="grid">
            <grid:grid from="webpage" ajax="yes" formId ="gridform">
                <grid:header>
                    <grid:cell width="50px"><input name="check" type="checkbox" id="toggleChk" onClick="selectChange()"></grid:cell>
                    <grid:cell>商品名称</grid:cell>
                    <grid:cell>商品编码</grid:cell>
                </grid:header>
                <grid:body item="goods">
                    <grid:cell><input type="checkbox"  name="goodsId" value="${goods.goods_id }" gid="${goods.goods_id }" id="names" goods_name="${goods.name }" /></grid:cell>
                    <grid:cell>${goods.name} <input type="hidden" name="goods_name" value="${goods.name}"></grid:cell>
                    <grid:cell>${goods.sn}</grid:cell>
                </grid:body>
            </grid:grid>
            </br>
            
            <div style="margin-left: auto;margin-right: auto;" align="center">
                <input name="btn" type="button" id="selGoodsBtn" value="确定"  class="graybtn1" />
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
            $("#selGoodsBtn").unbind("click").bind("click",function() {
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
        	var goodsid = $("#listGoods input[name='goodsId']:checked");
			goodsid.each(function(idx,item){
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

