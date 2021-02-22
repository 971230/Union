<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.tableform {
	background: none repeat scroll 0 0 #EFEFEF;
	border-color: #DDDDDD #BEC6CE #BEC6CE #DDDDDD;
	border-style: solid;
	border-width: 1px;
	margin: 10px;
	padding: 5px;
}

.division {
	background: none repeat scroll 0 0 #FFFFFF;
	border-color: #CCCCCC #BEC6CE #BEC6CE #CCCCCC;
	border-style: solid;
	border-width: 1px 2px 2px 1px;
	line-height: 150%;
	margin: 10px;
	padding: 5px;
	white-space: normal;
}

h4 {
	font-size: 1.2em;
	font-weight: bold;
	line-height: 1.25;
}

h1,h2,h3,h4,h5,h6 {
	clear: both;
	color: #111111;
	margin: 0.5em 0;
}
</style>
<form id="gridformoo" action="" class="grid" ajax="yes">
<div class="tableform">
	<h4>
		中间数据设置:  <a href="javascript:void(0);" style="margin-right:10px;" id="midProcessadd" name="addBtnPropertyAdd" class="graybtn1">添加</a>
	</h4>
	<div class="table_list">
		<table width="100%" id="select_attr_table1">
	    <thead>
			<tr id="grid_head">
				<th style="text-align:center;" >中间数编码</th>
				<th style="text-align:center;">计算方式</th>
				<th style="text-align:center;">计算逻辑</th>
				<th style="text-align:center;">Fact的Java类型</th>
				<th style="text-align:center;" >清单计算方式</th>
				<th style="text-align:center;">清单计算逻辑</th>
				<th style="text-align:center;">详情计算方式</th>
				<th style="text-align:center;">详情计算逻辑</th>
				<th style="text-align:center;">是否需要处理结果</th>
				<th style="text-align:center;">操作</th>
			</tr>
	 </thead>
	 <tbody id="processList">
		<c:forEach var="accps" items="${midProcess}">
			<tr>
				<td>${accps.mid_data_code}</td>
				<td>${accps.cal_type }</td>
				<td>${accps.cal_logic }</td>
				<td>${accps.fact_proess_class}</td>
				
				<td>${accps.list_cal_type }</td>
				<td>${accps.list_cal_logic}</td>
				<td>${accps.detail_cal_type }</td>
				<td>${accps.detail_cal_logic }</td>
				<td>${accps.need_process_data }</td>
				<td>
				    <a href="javascript:;"  name="addBtn3"　id="${accps.cfg_id }" class="delPayment" value="${accps.accounted_code}">修改</a>	
					<a href="javascript:;"  name="addBtn3"　id="${accps.cfg_id }" class="delPayment" value="${accps.accounted_code}">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
     </table>
	</div>
</div>
</form>
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