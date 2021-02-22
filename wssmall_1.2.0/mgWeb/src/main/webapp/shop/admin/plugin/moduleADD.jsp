<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="rightDiv">
	<!-- <div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_1" class="selected">
						<span class="word">模块列表</span><span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div> -->
	
 <div class="grid" id="selectChoiceDiv" >	
 <input name="sourceFrom" type="hidden" value="${source}"/>
<form id="gridform" class="grid" ajax="yes">
  <div class="top_up_div">
			<h4>
				可选模块
			</h4>
			<div class="top_up_con">
     <table width="100%" id="table1">
	    <thead>
		<tr>
			<td><input type="checkbox" onclick="selectedChange()" name="selectBox" id="toggleChked" /></td>
			<td>属性编码</td>
			<td>属性业务类型</td>
			<td>属性英文名</td>
			<td>属性中文名称</td>
			<td>映射属性英文名</td>
			<td>所属表</td>
		</tr>
	    </thead>
    <c:forEach var="pro" items="${property}">
       <tr >
          <td><input type="checkbox" name="selecteds"  value="${pro.field_attr_id }"  /></td>
           <td>${pro.field_attr_id}
           	<input type='hidden' name='e_names' value='${pro.field_name}' />
           	<input type='hidden' name='attr_ids' value='${pro.field_attr_id}' />
           	<input type='hidden' name='goodId' value='${pro.attr_spec_id}' />
           </td>
           <td>${pro.attr_spec_type}</td>
           <td>${pro.field_name}</td>
           <td>${pro.field_desc}</td>
           <td>${pro.rel_table_name}</td>
           <td>${pro.o_field_name}</td>
       </tr>
     </c:forEach>
     </table>
     </div>  
     </div>
</form>
</div>	
</div>   
<div class="submitlist" align="center" style="display: none;" >
	 <table>
	 <tr>
	 <th></th>
	 <td>
           <input  type="button"  value=" 确定 " class="submitBtn" id="selectOkBotc"/>
	 </td>
	 </tr>
	 </table>
 </div>
<jsp:include page="selectModule.jsp" />
<script type="text/javascript">
	
     var index = 1;
     //点击chenckbox把数据append到下面那个列表里面去1
      $("input[name=selecteds]").click(function(){
		 if(this.checked){
				   var tr1 = $(this).parents("tr");
					$("<td><input type='text' name='seq' value='"+index+"' /></td>").insertBefore(tr1.find("td:eq(1)"));
					tr1.find("td:first").hide();
					$("#select_attr_table").append(tr1.append('<td><a name="delde">删除</a></td>'));
					index++;
		 }
	 });  
     
     
     
       function selectedChange(){
	   var changed=$("#selectChoiceDiv").find("input[name=selecteds]").attr("checked",$("#toggleChked").is(":checked"));
	   changed.each(function(indx,item){
			   var tr0 = $(this).parents("tr");
				$("<td><input type='text' name='seq' value='"+index+"' /></td>").insertBefore(tr0.find("td:eq(1)"));
				tr0.find("td:first").hide();
				$("#select_attr_table").append(tr0.append('<td><a name="delde">删除</a></td>'));
				index++;
	   });
	   	 
      };

</script>
