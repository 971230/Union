<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listProducts">
	<form action="javascript:;" method="post" id="choiceform1">
		<input type="hidden" name="ajax" value="yes"/>
		<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	     <tbody>
	    <tr>
	    <th>搜索编码</th>
	    <td>
		   <input type="text"  name="sdqInner.search_code" id="cres" value='${sdqInner.search_code}' style="width: 148px;" maxlength="60" class="ipttxt" /> 
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
            <grid:grid from="webpage" ajax="yes" formId ="choiceform1">
                <grid:header>
                    <grid:cell width="50px">&nbsp;</grid:cell>
                    <grid:cell>搜索编码</grid:cell>
                    <grid:cell>搜索ID</grid:cell>
                    <grid:cell>搜索名称</grid:cell>
                </grid:header>
                <grid:body item="esearchSpecs">
                    <grid:cell>
                    	<input type="radio"  name="search_id_radio" search_code="${esearchSpecs.search_code }" 
                    	search_id="${esearchSpecs.search_id }" search_name="${esearchSpecs.search_name }" />
                    </grid:cell>
                    <grid:cell>${esearchSpecs.search_code}</grid:cell>
                    <grid:cell>${esearchSpecs.search_id}</grid:cell>
                    <grid:cell>${esearchSpecs.search_name}</grid:cell>
                </grid:body>
            </grid:grid>
            </br>
            
            <div style="margin-left: auto;margin-right: auto;" align="center">
                <input name="btn" type="button" id="selSpecBtn" value="确定"  class="graybtn1" />
                <input name="btn" type="button" id="resetSpecBtn" value="清空"  class="graybtn1" />
            </div>
            
        </form>
   
</div>
<script type="text/javascript">
function selectChange(){
	$("#listGoods").find("input[name=goodsId]").attr("checked",$("#toggleChk").is(":checked"));	
}
var esearchSpec= {
    init:function(){
        var me=this;
        $("#selSpecBtn").unbind("click").bind("click",function() {
    		me.addSelectSpec();
     	});
        $("#resetSpecBtn").unbind("click").bind("click",function() {
    		me.restSelectSpec();
     	});
        $("#serButton").bind("click",function() {
            me.searchBottonClks();
       	});
    },
    addSelectSpec:function(){
    	var esearchSpecs = $("#esearchSpecListDialog input[name='search_id_radio']:checked");
    	if(esearchSpecs && esearchSpecs.length > 0) {
    		var search_id = esearchSpecs.attr("search_id");
    	    var search_code = esearchSpecs.attr("search_code");
        	$("#search_id").val(search_id);
        	$("#search_code").val(search_code);
        	Eop.Dialog.close("esearchSpecListDialog");
    	}else {
    		alert("请选择一个搜索编码!");
    	}
    },
    restSelectSpec:function(){
       	$("#search_id").val("");
       	$("#search_code").val("");
       	Eop.Dialog.close("esearchSpecListDialog");
    },
    searchBottonClks : function() {
        var me = this;
        var search_code = $("#cres").val();
        var url =ctx + "/shop/admin/esearchSpec!list.do?ajax=yes&show_type=dialog";
        $("#esearchSpecListDialog").load(url,{"sdqInner.search_code":search_code},function(){});
    }
}
esearchSpec.init();
</script>