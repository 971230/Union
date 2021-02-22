<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid" id="selectChoice" >	
<form id="gridformoooo" class="grid" ajax="yes">
<input type="hidden" value="${source }" name="temp.source_from" />
<input type="hidden" value="${temp_name }" name="temp.temp_name" />
<input type="hidden" value="${goods_id }" name="temp.goods_id" />
<div class="top_up_div">
	<h4>
		已选模板属性
	</h4>
	<div class="top_up_con">
		<table width="100%" id="select_attr_table">
	    <thead>
		<tr>
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
       <tr>
           <input type='hidden' name='e_names' value='${pro.field_name}' />
           <input type='hidden' name='attr_ids' value='${pro.field_attr_id}' />
          <td><input type="checkbox" name="selecteds"  value="${pro.field_attr_id }"  /></td>
           <td>${pro.field_attr_id} </td>
           <td>${pro.attr_spec_type}</td>
           <td>${pro.field_name}</td>
           <td>${pro.field_type}</td>
           <td>${pro.rel_table_name}</td>
           <td>${pro.o_field_name}</td>
           <td><a href='javascript:;' onClick="clickDel();" name="delde" field_attr_id=${pro.field_attr_id}>删除</a></td>
       </tr>
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
           <input  type="button"  value=" 确定 " class="submitBtn" id="selectOkBots"/>
	 </td>
	 </tr>
	 </table>
     </div>

<script type="text/javascript">
	$(function(){
		var tmp = $("input[name='temp.temp_name']");
		if(!tmp || !tmp.val()){
			var tname = $("#goods_name").val();
			tmp.val(tname);
		}
		$("a[name='delde']").live("click",function(){
			var tr0 = $(this).parents("tr");
			$(this).parents("td").remove();
			tr0.find("td:first").show();
			tr0.find("td:eq(1)").remove();
			$("#table1").append(tr0);
			tr0.find("input[name=selecteds]").attr("checked","");
		});
		$("#selectOkBots").click(function() {
			var url = ctx+ "/shop/admin/goodsPropertyAction!addModule.do?ajax=yes";
			var options = {
					type : "post",
					url : url,
					dataType : 'html',
					success : function(result) {
						try{
							if(result.indexOf("hastmpl")!=-1){
								var aa = result.split("\|");
								alert(aa[1]);
								Eop.Dialog.close("goodModule");
								return ;
							}
						}catch(e){
							alert(e);
						}
						$("#moduleList").empty().append(result);
						
						Eop.Dialog.close("goodModule");
					},
					error : function(e) {
						alert("操作失败，请重试"+e);
					},
				}
				$("#gridformoooo").ajaxSubmit(options);
		});
	});

</script>