<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listElements">
<form id="ElementListForm" action="" method="post">
	<div class="searchformDiv">
		<table>
			<tr>
				<th>活动ID：</th>
				<td>
					<input type="text"  name="q_scheme_id" id="q_scheme_id" value='${q_scheme_id}' style="width: 148px;" maxlength="60" class="ipttxt" />
					
				<th></th>	
				<td>
						<select name="q_element_type" id="q_element_type" class="ipt_new" style="width:150px;">
	                		<c:forEach items="${element_type_list }" var="ds">
	                			<option value="${ds.value }" ${ds.value==q_element_type?'selected':'' }>${ds.value_desc }</option>
	                		</c:forEach>
						</select>
				</td>
				<th></th>
				<td>
					<input class="comBtn" type="button" id="elementSearch" value="搜索" style="margin-right: 10px;" />
				    <input type="button" style="margin-right: 10px;" class="comBtn" value="确定" id="elementSure">
				</td>
			</tr>
		</table>
		<div style="clear: both"></div>
	</div>
</form>

        <form  id="form_tc" class="grid">
            <grid:grid from="webpage" ajax="yes" formId ="gridform">
                <grid:header>
                    <grid:cell width="50px"></grid:cell>
                    <grid:cell>活动ID</grid:cell>
                    <grid:cell>活动名称</grid:cell>
                    <grid:cell>元素ID</grid:cell>
                    <grid:cell>元素名称</grid:cell>
                    <grid:cell>元素类型</grid:cell>
                    <grid:cell>终端类型</grid:cell>
                    <grid:cell>终端名称</grid:cell>
                </grid:header>
                <grid:body item="element">
                    <grid:cell>
                    	<input type="checkbox" disabled="disabled" checked name="elementCodes" id="names" scheme_id="${element.scheme_id}" scheme_name="${element.scheme_name}"
                    	element_id="${element.element_id}" element_name="${element.element_name}" element_type_n="${element.element_type_n}" 
                    	element_type="${element.element_type}" mobile_type="${element.mobile_type}" terminal_name="${element.terminal_name}"/>
                    </grid:cell>
                    <grid:cell>${element.scheme_id}</grid:cell>
                    <grid:cell>${element.scheme_name}</grid:cell>
                    <grid:cell>${element.element_id}</grid:cell>
                    <grid:cell>${element.element_name}</grid:cell>
                    <grid:cell>${element.element_type_n}</grid:cell>
                    <grid:cell>${element.mobile_type}</grid:cell>
                    <grid:cell>${element.terminal_name}</grid:cell>
                </grid:body>
            </grid:grid>
            </br>
        </form>
</div>
<script type="text/javascript">
var addElement={
        init:function(){
            var me=this;
            $("#elementSure").unbind("click").bind("click",function() {
		      	me.addSelectElement();
	        });
            $("#elementSearch").bind("click",function() {
                me.searchElement();
           });
        },
        
        addSelectElement : function(){
        	var len = $("input[type='checkbox'][name='elementCodes']:checked").length;
        	var q_scheme_id = $("#q_scheme_id").val();
			var q_element_type = $("#q_element_type").find("option:selected").val();
    		
    		var html = $("##elementbody #elementNode").html();
    		var tr = "";
      		//var html = window.opener.document.getElementById("communityNode").innerHTML;
			$("input[type='checkbox'][name='elementCodes']:checked").each(function() {
				     tr += "<tr>";
    			  var scheme_id = $(this).attr("scheme_id");
    			  var scheme_name = $(this).attr("scheme_name");
    			  var element_id = $(this).attr("element_id");
    			  var element_name = $(this).attr("element_name");
    			  var element_type_n = $(this).attr("element_type_n");
    			  var element_type = $(this).attr("element_type");
    			  var mobile_type = $(this).attr("mobile_type");
    			  var terminal_name = $(this).attr("terminal_name");
    			  
    			  
				  tr += "<td><input type='hidden' name='scheme_id' id='"+scheme_id+"' value='"+scheme_id+"'/>"+scheme_id+"</td>";
				  tr += "<td><input type='hidden' name='scheme_name' id='"+scheme_name+"' value='"+scheme_name+"'/>"+scheme_name+"</td>";
				  tr += "<td><input type='hidden' name='element_id' id='"+element_id+"' value='"+element_id+"'/>"+element_id+"</td>";
				  tr += "<td><input type='hidden' name='element_name' id='"+element_name+"' value='"+element_name+"'/>"+element_name+"</td>";
				  tr += "<td><input type='hidden' name='element_type' id='"+element_type+"' value='"+element_type+"'/>"+element_type_n+"</td>";
				  tr += "<td><input type='hidden' name='mobile_type' id='"+mobile_type+"' value='"+mobile_type+"'/>"+mobile_type+"</td>";
				  tr += "<td><input type='hidden' name='community_nameterminal_name' id='"+terminal_name+"' value='"+terminal_name+"'/>"+terminal_name+"</td>";
				  tr += "</tr>";
				  
				  
				  
			});
			tr += "<input type='hidden' id='s_scheme_id' value='"+q_scheme_id+"'/>"
			  tr += "<input type='hidden' id='s_element_type' value='"+q_element_type+"'/>"
			html = tr;
    		$("##elementbody #elementNode").html(html);
    		Eop.Dialog.close("listAllElements");
        },
        
        searchElement : function() {
			var scheme_id = $("#q_scheme_id").val();
			var element_type = $("#q_element_type").find("option:selected").val();
	        var url =ctx + "/shop/admin/goods!elementSelectList.do?ajax=yes&q_scheme_id="+scheme_id+"&q_element_type="+element_type;
	        $("#elementDialog").load(url,function(){});
        }
}

addElement.init();

</script>