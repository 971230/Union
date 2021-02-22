<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listTerminal">
<form action="javascript:;" method="post" id="choiceTerminalform">
		<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	     <tbody>
	    <tr>
	    <th>终端名称</th>
	    <td>
		   <input type="text"  name="terminal_name" id="terminal_name" value='${terminal_name}' style="width: 288px;" maxlength="60" class="ipttxt" /> 
		</td>
		<th></th> 
	   	<td style="text-align:right;">
		 <input class="comBtn" type="submit" name="searchTerminalBtn" id="serTerminalButton" value="搜索" style="margin-right:5px;"/>
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
                    <grid:cell width="50px"><input  name="check" type="hidden" id="toggleChk" onClick="selectTerminalChange()"></grid:cell>
                    <grid:cell>终端编码</grid:cell>
                    <grid:cell>终端名称</grid:cell>
                </grid:header>
                <grid:body item="goods">
                    <grid:cell><input type="radio"  name="goodsId" value="${goods.product_id }" pid="${goods.product_id }"
                       gid="${goods.goods_id }" id="names" goods_name="${goods.name }" sn="${goods.sn }"
                       model_code="${goods.model_code }" /></grid:cell>
                    <grid:cell>${goods.sku}</grid:cell>
                    <grid:cell>${goods.name} <input type="hidden" name="goods_name" value="${goods.name}"></grid:cell>
                </grid:body>
            </grid:grid>
            </br></br>
	         <div style="text-align:center;margin-top:3px;"> 
	             <input name="btn" type="button" id="selTerminalBtn" value="确定"  class="graybtn1" />
	         </div>
        </form>

</div>
<script type="text/javascript">
function selectTerminalChange(){
	$("#listTerminal").find("input[name=goodsId]").attr("checked",$("#listTerminal").find("#toggleChk").is(":checked"));	
}
var addGoods= {
        init:function(){
            var me=this;
            $("#selTerminalBtn").unbind("click").bind("click",function() {
		        me.addSelectGoods();
	        });
            //单选框双击选择
        	$("#listTerminal").find(".gridbody tbody tr").live("dblclick",function(){
            	//alert("doubleClick");
            	var box = $(this).find("input[type='checkbox'],input[type='radio']");
            	if($(box).attr("type") == "radio"){
            		box.attr("checked",true);
            		me.addSelectGoods();
            	}
        	});
            $("#serTerminalButton").unbind("click").bind("click",function() {
                me.searchBottonClks();
            });
        },
        addSelectGoods:function(){
			/* var goods=$("input[name=goodsId][type=checkBox]:checked").val();
        	if(!goods){
    			alert("请选择商品！");
    			return ;
    		} */
        	var goodsid = $("#listTerminal input[name='goodsId']:checked");
			goodsid.each(function(idx,item){
			   var tr = $(item).parents("tr");
			   $("#terminalTables").append(tr.append("<td><a href='javascript:void(0);' name='gooddelete'>删除</a></td>"));
			   tr.find("input[type='radio']").hide();
			   $(tr).removeClass("grid-table-row-selected");
			   $("#terminalTables tbody").html("");
			   $("#terminalTables").append(tr);
			   
	         }),
			Eop.Dialog.close("terminalList");
    },
    searchBottonClks : function() {
    	var terminal_name = $.trim($("#terminal_name").val());
    	contractConfig.showTerminal(terminal_name);
		
   }
}
addGoods.init();
</script>

