<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listConutrys">
<form id="CountryListForm" action="" method="post">
	<div class="searchformDiv">
		<table>
			<tr>
				<th>地市名称：</th>
				<td>
					<input type="text"  name="areadef" id="areadef" value='${areadef}' style="width: 148px;" maxlength="60" class="ipttxt" />
				<th>县区名称：</th>
				<td>
					<input type="text"  name="countyname" id="countyname" value='${countyname}' style="width: 148px;" maxlength="60" class="ipttxt" />
					<input class="comBtn" type="button" id="countryNameSearch" value="搜索" style="margin-right: 10px;" /></td>
				<th></th>
				<td>
				    <input type="button" style="float: right" class="comBtn" value="确定" id="countrySure">
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
                    <grid:cell>地市名称</grid:cell>
                    <grid:cell>地市编码</grid:cell>
                    <grid:cell>县区名称</grid:cell>
                    <grid:cell>县区编码</grid:cell>
                    <grid:cell>总部县区编码</grid:cell>
                </grid:header>
                <grid:body item="country">
                    <grid:cell>
                    	<input type="checkbox"  name="countryCodes" id="names" areadef="${country.areadef }" 
                    	areaid="${country.areaid}" countyname="${country.countyname}" countyid="${country.countyid}" 
                    	hq_countyid="${country.hq_countyid}" 
                    	/>
                    </grid:cell>
                    <grid:cell>${country.areadef}</grid:cell>
                    <grid:cell>${country.areaid}</grid:cell>
                    <grid:cell>${country.countyname}</grid:cell>
                    <grid:cell>${country.countyid}</grid:cell>
                    <grid:cell>${country.hq_countyid}</grid:cell>
                </grid:body>
            </grid:grid>
            </br>
        </form>
</div>
<script type="text/javascript">
var addCountry={
        init:function(){
            var me=this;
            $("#countrySure").unbind("click").bind("click",function() {
		      	me.addSelectCountry();
	        });
            $("#countryNameSearch").bind("click",function() {
                me.searchBottonClks();
           });
        },
        
        addSelectCountry : function(){
        	var len = $("input[type='checkbox'][name='countryCodes']:checked").length;
    		if(len<=0){
    			alert("请选择要关联的小区!");
    			return;
    		}
    		
    		var html = $("##countrybody #countryNode").html();
      		//var html = window.opener.document.getElementById("communityNode").innerHTML;
			$("input[type='checkbox'][name='countryCodes']:checked").each(function() {
    			  var tr = "<tr>";
    			  var areadef = $(this).attr("areadef");
    			  var areaid = $(this).attr("areaid");
    			  var countyname = $(this).attr("countyname");
    			  var countyid = $(this).attr("countyid");
    			  var hq_countyid = $(this).attr("hq_countyid");
    			  
    			  var select = $("##countrybody #countryNode input[id='"+countyid+"']");
   			      if(select && select.length>0){
   				     alert("["+countyname+"]已经存在，不能重复添加!");
				     return ;
			      }
				  
   			      tr += "<td><input type='hidden' name='areadef' id='"+areadef+"' value='"+areadef+"'/>"+areadef+"</td>";
				  tr += "<td><input type='hidden' name='areaid' id='"+areaid+"' value='"+areaid+"'/>"+areaid+"</td>";
				  tr += "<td><input type='hidden' name='countyname' id='"+countyname+"' value='"+countyname+"'/>"+countyname+"</td>";
				  tr += "<td><input type='hidden' name='countyid' id='"+countyid+"' value='"+countyid+"'/>"+countyid+"</td>";
				  tr += "<td><input type='hidden' name='hq_countyid' id='"+hq_countyid+"' value='"+hq_countyid+"'/>"+hq_countyid+"</td>";
				  tr += "<td><a href='javascript:;'><img  class='delete' src='${ctx}/shop/admin/images/transparent.gif' > </a></td>";
				  tr += "</tr>";
				  html += tr;
				  
			});
    		$("##countrybody #countryNode").html(html);
    		$("#region_type_show").val($("#region_type").val());
    		Eop.Dialog.close("listAllCountrys");
        },
        
        searchBottonClks : function() {
        	var region_type=$("#region_type option:selected").val();  
			var areadef = $("#areadef").val();
			var countyname = $("#countyname").val();
	        var url =ctx + "/shop/admin/goods!countrySelectList.do?ajax=yes&areadef="+encodeURI(encodeURI(areadef,true),true)+"&countyname="+encodeURI(encodeURI(countyname,true),true)+"&region_type="+encodeURI(encodeURI(region_type,true),true);
	        $("#countryDialog").load(url,function(){});
        }
}

addCountry.init();

</script>