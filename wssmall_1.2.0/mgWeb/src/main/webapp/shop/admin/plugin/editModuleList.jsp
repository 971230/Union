<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid" id="selectChoice" >	
<form id="gridformoo" class="grid" ajax="yes">
<input type="hidden" value="${temp.temp_inst_id }" name="temp.temp_inst_id" />
<span id="hasselectJson" style="display:none;">${temp.temp_cols }</span>
<input type="hidden" value="${goodsName.temp_name }" name="temp.temp_name" />
<div class="top_up_div">
	<h4>
		已选模板属性
	</h4>
	<div class="top_up_con">
		<table width="100%" id="select_attr_table1">
	    <thead>
		<tr>
			<!-- <td><input type="checkbox" id="toggleChk" /></td> -->
			<td>序号</td>
			<td>属性编码</td>
			<td>属性业务类型</td>
			<td>属性英文名</td>
			<td>属性中文名称</td>
			<td>映射属性英文名</td>
			<td>所属表</td>
			<td>操作</td>
		</tr>
	</thead>
    <c:forEach var="pro" items="${selectList}">
     </c:forEach>
     </table>
	</div>
</div>
</form>
</div>
<div class="submitlist" align="center" >
	 <table>
	 <tr>
	 <th></th>
	 <td>
           <input  type="button"  value=" 确定 " class="submitBtn" id="selectOkBotg"/>
	 </td>
	 </tr>
	 </table>
     </div>

<script type="text/javascript">
	$(function(){
		var jsonArray = eval($("#hasselectJson").html());
		var canSelectArray = $("#tables tr");
		$.each(jsonArray,function(idx,item){
			$.each(canSelectArray,function(cidx,citem){
				var id1 = $(citem).find("input[name='selectsss']").val();
				if(item.field_attr_id==id1){
					var tr = $(citem);
					$("<td><input type='text' name='seq' value='"+index+"'/></td>").insertBefore(tr.find("td:eq(1)"));
					tr.find("td:first").hide();
					tr.find("input[name=selectsss]").attr("checked","");
					$("#select_attr_table1").append(tr.append('<td><a name="deletessds">删除</a></td>'));
					$("#select_attr_table1").append(tr);
					
				}
			});
		}); 
		var tmp = $("input[name='temp.temp_name']");
		if(!tmp || !tmp.val()){
			var tname = $("#goods_name").val();
			tmp.val(tname);
		}
		 $("a[name='deletessds']").live("click",function(){
				var tr = $(this).parents("tr");
				$(this).parents("td").remove();
				tr.find("td:first").show();
				tr.find("td:eq(1)").remove();
				$("#tables").append(tr);
				tr.find("input[name=selectsss]").attr("checked","");
			});
		
		$("#selectOkBotg").click(function() {
			var url = ctx+ "/shop/admin/goodsPropertyAction!updateModuleSave.do?ajax=yes";
			var options = {
					type : "post",
					url : url,
					dataType : 'html',
					success : function(result) {
						$("#moduleList").empty().append(result);
						Eop.Dialog.close("moduleEdit");
					},
					error : function(e) {
						alert("操作失败，请重试"+e);
					},
				}
				$("#gridformoo").ajaxSubmit(options);
		});
		
	});
	

</script>