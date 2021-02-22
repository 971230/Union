<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listCommnuitys">
<form id="CommunityListForm" action="" method="post">
	<div class="searchformDiv">
		<table>
			<tr>
				<th>小区名称：</th>
				<td>
					<input type="text"  name="name" id="cres" value='${name}' style="width: 148px;" maxlength="60" class="ipttxt" />
					<input class="comBtn" type="button" id="communityNameSearch" value="搜索" style="margin-right: 10px;" /></td>
				<th></th>
				<td>
				    <input type="button" style="float: right" class="comBtn" value="确定" id="communitySure">
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
                    <grid:cell>地市</grid:cell>
                    <grid:cell>行政区县</grid:cell>
                    <grid:cell>县分公司</grid:cell>
                    <grid:cell>小区编号</grid:cell>
                    <grid:cell>小区名称</grid:cell>
                </grid:header>
                <grid:body item="community">
                    <grid:cell>
                    	<input type="checkbox"  name="communityCodes" id="names" community_name="${community.community_name }" 
                    	community_code="${community.community_code }" city_name="${community.city_name}" city_code="${community.city_code}" 
                    	area_name="${community.area_name}" area_code="${community.area_code}" 
                    	county_comp_name="${community.county_comp_name}" county_comp_id="${community.county_comp_id}"
                    	/>
                    </grid:cell>
                    <grid:cell>${community.city_name}</grid:cell>
                    <grid:cell>${community.area_name}</grid:cell>
                    <grid:cell>${community.county_comp_name}</grid:cell>
                    <grid:cell>${community.community_code}</grid:cell>
                    <grid:cell>${community.community_name}</grid:cell>
                </grid:body>
            </grid:grid>
            </br>
        </form>
</div>
<script type="text/javascript">
var addCommunity={
        init:function(){
            var me=this;
            $("#communitySure").unbind("click").bind("click",function() {
		      	me.addSelectCommunity();
	        });
            $("#communityNameSearch").bind("click",function() {
                me.searchBottonClks();
           });
        },
        
        addSelectCommunity : function(){
        	var len = $("input[type='checkbox'][name='communityCodes']:checked").length;
    		if(len<=0){
    			alert("请选择要关联的小区!");
    			return;
    		}
    		
    		var html = $("##communitybody #communityNode").html();
      		//var html = window.opener.document.getElementById("communityNode").innerHTML;
			$("input[type='checkbox'][name='communityCodes']:checked").each(function() {
    			  var tr = "<tr>";
    			  var community_code = $(this).attr("community_code");
    			  var community_name = $(this).attr("community_name");
    			  
    			  var select = $("##communitybody #communityNode input[id='"+community_code+"']");
   			      if(select && select.length>0){
   				     alert("["+community_name+"]已经存在，不能重复添加!");
				     return ;
			      }
    			  
				  tr += "<td><input type='hidden' name='community_code' id='"+community_code+"' value='"+community_code+"'/>"+community_code+"</td>";
				  tr += "<td><input type='hidden' name='community_name' id='"+community_name+"' value='"+community_name+"'/>"+community_name+"</td>";
				  tr += "<td><a href='javascript:;'><img  class='delete' src='${ctx}/shop/admin/images/transparent.gif' > </a></td>";
				  tr += "</tr>";
				  html += tr;
				  
			});
    		$("##communitybody #communityNode").html(html);
    		Eop.Dialog.close("listAllCommunitys");
        },
        
        searchBottonClks : function() {
			var name = $("#cres").val();
	        var url =ctx + "/shop/admin/goods!communitySelectList.do?ajax=yes&name="+encodeURI(encodeURI(name,true),true);
	        $("#communityDialog").load(url,function(){});
        }
}

addCommunity.init();

</script>