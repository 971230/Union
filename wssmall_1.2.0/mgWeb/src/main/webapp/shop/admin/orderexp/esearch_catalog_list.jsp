<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listProducts">
	<form action="javascript:;" method="post" id="specvalue_catalog_list_form">
	<input type="hidden" id="show_catalog_list_source_button_id" value="<%=request.getParameter("button_id")%>"/>
		<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	     <tbody>
	    <tr>
	    <th>归类名称</th>
	    <td>
		   <input type="text"  name="eclInner.catalog_name" id="catalog_name_search" value='${eclInner.catalog_name}' style="width: 148px;" maxlength="60" class="ipttxt" /> 
		</td>
		<th></th> 
	   	<td>
		 <input class="comBtn" type="button" name="searchBtn" id="catalog_serButton" value="搜索" style="margin-right:5px;"/>
		</td>
	   </tr>
	  </tbody>
	  </table>
    	</div>  	
	</form>	
        <form  id="catalog_form_tc" class="grid">
            <grid:grid from="webpage" ajax="yes" formId ="specvalue_catalog_list_form">
                <grid:header>
                    <grid:cell width="50px">&nbsp;</grid:cell>
                    <grid:cell>归类编码</grid:cell>
                    <grid:cell>归类名称</grid:cell>
                    <grid:cell>归类描述</grid:cell>
                </grid:header>
                <grid:body item="catalog">
                    <grid:cell>
                    	<input type="radio"  name="catalog_radio" catalog_code="${catalog.catalog_code }" 
                    	catalog_id="${catalog.catalog_id }" catalog_name="${catalog.catalog_name }" />
                    </grid:cell>
                    <grid:cell>${catalog.catalog_code}</grid:cell>
                    <grid:cell>${catalog.catalog_name}</grid:cell>
                    <grid:cell>${catalog.catalog_desc}</grid:cell>
                </grid:body>
            </grid:grid>
            </br>
            
            <div style="margin-left: auto;margin-right: auto;" align="center">
                <input name="btn" type="button" id="selCatalogBtn" value="确定"  class="graybtn1" />
                <input name="btn" type="button" id="resetCatalogBtn" value="清空"  class="graybtn1" />
            </div>
            
        </form>
   
</div>
<script type="text/javascript">
function selectChange(){
	$("#listGoods").find("input[name=goodsId]").attr("checked",$("#toggleChk").is(":checked"));	
}
var esearchCatalog= {
     init:function(){
         var me=this;
         $("#selCatalogBtn").unbind("click").bind("click",function() {
     	me.addSelectCatalog();
      });
         $("#resetCatalogBtn").unbind("click").bind("click",function() {
     	me.restSelectCatalog();
      });
         $("#catalog_serButton").bind("click",function() {
             me.searchBottonClks();
        });
     },
     addSelectCatalog:function(){
     	var catalog = $("#esearchCatalogListDialog input[name='catalog_radio']:checked");
     	if(catalog && catalog.length > 0) {
     		var catalog_id = catalog.attr("catalog_id");
     	    var catalog_name = catalog.attr("catalog_name");
     	    var show_catalog_list_source_button_id = $("#show_catalog_list_source_button_id").val();
     	    if(show_catalog_list_source_button_id == "selectCatalogsBtn") {
     	    	$("#order_exp_param_f input[id='catalog_id']").val(catalog_id);
             	$("#order_exp_param_f input[id='catalog_name']").val(catalog_name);
     	    }else {
     	    	$("#specvaluesClassifyDialog input[id='catalog_id']").val(catalog_id);
             	$("#specvaluesClassifyDialog input[id='catalog_name']").val(catalog_name);
     	    }
         	Eop.Dialog.close("esearchCatalogListDialog");
     	}else {
     		alert("请选择一条归类信息!");
     	}
    },
    restSelectCatalog:function(){
       	$("#catalog_id").val("");
       	$("#catalog_name").val("");
       	Eop.Dialog.close("esearchCatalogListDialog");
    },
    searchBottonClks : function() {
        var me = this;
        var catalog_name_search = $("#catalog_name_search").val();
        var show_catalog_list_source_button_id = $("#show_catalog_list_source_button_id").val();
        var url =ctx + "/shop/admin/esearchCatalog!list.do?ajax=yes&show_type=dialog";
        $("#esearchCatalogListDialog").load(url,{"eclInner.catalog_name":catalog_name_search,"button_id":show_catalog_list_source_button_id},function(){});
    }
}
esearchCatalog.init();
</script>