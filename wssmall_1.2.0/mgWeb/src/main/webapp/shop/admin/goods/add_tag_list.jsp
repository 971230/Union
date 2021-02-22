<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listTag">
<form id="tagListForm" action="" method="post">
	<div class="searchformDiv">
		<table>
			<tr>
				<th>标签编码：</th>
				<td>
					<input type="hidden"  name="tag_group_type" id="tag_group_type" value='${tag_group_type}' style="width: 148px;" maxlength="60" class="ipttxt" />
					<input type="text"  name="tag_code" id="tag_code" value='${tag_code}' style="width: 148px;" maxlength="60" class="ipttxt" />
				<th>标签名称：</th>
				<td>
					<input type="text"  name="name" id="name" value='${name}' style="width: 148px;" maxlength="60" class="ipttxt" />
					<input class="comBtn" type="button" id="tagSearch" value="搜索" style="margin-right: 10px;" /></td>
				<th></th>
				<td>
				    <input type="button" style="float: right" class="comBtn" value="确定" id="tagSure">
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
                    <grid:cell>标签编码</grid:cell>
                    <grid:cell>标签名称</grid:cell>
                </grid:header>
                <grid:body item="tag">
                    <grid:cell>
                    	<input type="checkbox"  name="tagCodes" id="names" tag_code="${tag.tag_code }" tag_name="${tag.tag_name}" />
                    </grid:cell>
                    <grid:cell>${tag.tag_code}</grid:cell>
                    <grid:cell>${tag.tag_name}</grid:cell>
                </grid:body>
            </grid:grid>
            </br>
        </form>
</div>
<script type="text/javascript">
var addTag={
        init:function(){
            var me=this;
            $("#tagSure").unbind("click").bind("click",function() {
		      	me.addSelectTag();
	        });
            $("#tagSearch").bind("click",function() {
                me.searchBottonClks();
           });
        },
        
        addSelectTag : function(){
        	var len = $("input[type='checkbox'][name='tagCodes']:checked").length;
    		if(len<=0){
    			alert("请选择要关联的小区!");
    			return;
    		}
    		
    		var tag_group_type = $("#tag_group_type").val();
    		var html = $("##saleTagbody #saleTagNode").html();
    		if(tag_group_type == "goods"){
    			html = $("##goodsTagbody #goodsTagNode").html();
    		}
    		
      		//var html = window.opener.document.getElementById("communityNode").innerHTML;
			$("input[type='checkbox'][name='tagCodes']:checked").each(function() {
    			  var tr = "<tr>";
    			  var tag_code = $(this).attr("tag_code");
    			  var tag_name = $(this).attr("tag_name");
    			  
    			  var select = $("##saleTagbody #saleTagNode input[id='"+tag_code+"']");
    			  if(tag_group_type == "goods"){
    				  select = $("##goodsTagbody #goodsTagNode input[id='"+tag_code+"']");
    	    	  }
   			      if(select && select.length>0){
   				     alert("["+tag_name+"]已经存在，不能重复添加!");
				     return ;
			      }
   			   	
   			   	  var sorts=$("##goodsTagbody #goodsTagNode input[name='sort']");
   			   	  var sort = sorts.length + 1;
				  
   			   	  if(tag_group_type == "goods"){
   			      	tr += "<td><input type='hidden' name='tag_code' id='"+tag_code+"' value='"+tag_code+"'/>"+tag_code+"</td>";
				  	tr += "<td><input type='hidden' name='tag_name' id='"+tag_name+"' value='"+tag_name+"'/>"+tag_name+"</td>";
				  	tr += "<td><input type='text' style='width:30px; height:100%' name='sort' id='"+sort+"' value='"+sort+"'/></td>";
				  }else{
					tr += "<td><input type='hidden' name='sale_tag_code' id='"+tag_code+"' value='"+tag_code+"'/>"+tag_code+"</td>";
					tr += "<td><input type='hidden' name='sale_tag_name' id='"+tag_name+"' value='"+tag_name+"'/>"+tag_name+"</td>";
					  	
				  }
				  tr += "<td><a href='javascript:;'><img  class='delete' src='${ctx}/shop/admin/images/transparent.gif' > </a></td>";
				  tr += "</tr>";
				  html += tr;
				  
			});
    		if(tag_group_type == "goods"){
    			$("##goodsTagbody #goodsTagNode").html(html);
    		}else{
    			$("##saleTagbody #saleTagNode").html(html);
    		}
    		Eop.Dialog.close("tagList");
        },
        
        searchBottonClks : function() {
			var tag_code = $("#tag_code").val();
			var name = $("#name").val();
			var tag_group_type = $("#tag_group_type").val();
	        var url =ctx + "/shop/admin/goods!tagSelectList.do?ajax=yes&tag_code="+encodeURI(encodeURI(tag_code,true),true)+"&name="+encodeURI(encodeURI(name,true),true)+"&tag_group_type="+encodeURI(encodeURI(tag_group_type,true),true);
	        $("#tagDialog").load(url,function(){});
        }
}

addTag.init();

</script>