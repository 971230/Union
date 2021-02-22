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
				<th>${inputname}：</th>
				<td>
					<input type="text"  name="inputname" id="inputname" value='${name}' style="width: 148px;" maxlength="60" class="ipttxt" />
				<th></th>
				<td>
				    <input type="button" style="float: right" class="comBtn" value="确定" id="communitySure">
				</td>
			</tr>
		</table>
		<div style="clear: both"></div>
	</div>
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
        	var lenth = document.getElementById('inputname').value.length;
        	
    		if(lenth<=0){
    			alert("请输入相应的值！");
    			return;
    		}
    		
    		var html = $("##custidbody #custidNode").html();
    		
    		
      		//var html = window.opener.document.getElementById("communityNode").innerHTML;
			//$("input[type='checkbox'][name='communityCodes']:checked").each(function() {
    			  var tr = "<tr>";
    			  var cust_id = $("#inputname").val();
    			  
    			  var select = $("##custidbody #custidNode input[id='"+cust_id+"']");
   			      if(select && select.length>0){
   				     alert("客户号    "+"["+cust_id+"]已经存在，不能重复添加!");
				     return ;
			      }
    			  
				  tr += "<td><input type='hidden' name='cust_id' id='"+cust_id+"' value='"+cust_id+"'/>"+cust_id+"</td>";
				  tr += "<td><a href='javascript:;'><img  class='delete' src='${ctx}/shop/admin/images/transparent.gif' > </a></td>";
				  tr += "</tr>";
				  html += tr;
				  
			//});
    		$("##custidbody #custidNode").html(html);
    		Eop.Dialog.close("listRelation");
        },
        
        searchBottonClks : function() {
			var name = $("#cres").val();
	        var url =ctx + "/shop/admin/goods!communitySelectList.do?ajax=yes&name="+encodeURI(encodeURI(name,true),true);
	        $("#communityDialog").load(url,function(){});
        }
}

addCommunity.init();

</script>