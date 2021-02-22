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
				<th></th>	
				<td>
						<input type="hidden" value="${goods_ids }" id="goods_ids">
						<select name="q_element_type" id="q_element_type" class="ipt_new" style="width:150px;">
	                		<c:forEach items="${element_type_list }" var="ds">
	                			<option value="${ds.value }" ${ds.value==q_element_type?'selected':'' }>${ds.value_desc }</option>
	                		</c:forEach>
						</select>
				</td>
				<th></th>
				<td>
				    <input type="button" style="margin-right: 10px;" class="comBtn" value="确定" id="elementSure">
				</td>
			</tr>
		</table>
		<div style="clear: both"></div>
	</div>
</form>

</div>
<script type="text/javascript">
var addElement={
        init:function(){
            var me=this;
            $("#elementSure").unbind("click").bind("click",function() {
		      	me.addSelectElement();
	        });
        },
        
        addSelectElement : function(){
        	var goods_ids = $("#goods_ids").val();
			var q_element_type = $("#q_element_type").find("option:selected").val();
			$.ajax({
				url: basePath + "/shop/admin/goods!synElement.do?ajax=yes",
				type:"post",
				data:{"goods_ids":goods_ids,"s_element_type":q_element_type},
				dataType:"json",
				success:function(datas){
					if(datas.result==0){
						alert(datas.message);
						Eop.Dialog.close("synElementDialog");
					}else{
						alert(datas.message);
					}
				},
				error:function(){
					alert("后台出错,请重新操作!");
				}
			});	
    		
        }
}

addElement.init();

</script>