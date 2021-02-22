<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="auth/checktree.css" />
<script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="../../mgWeb/menu.do"></script>
<script type="text/javascript" src="js/Auth.js"></script>
<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/workerPoolAction!workPoolRelList.do'>
<div class="searchformDiv">
 <table>
	<tr>
	    <th>工号：</th>
		<td><input type="text" class="ipttxt" style="width:200px;" name="workPoolRel.operator_id"  id ="workPoolRel.operator_id"  value="${workPoolRel.operator_id }"  /></td>
	    <th>姓名：</th>
		<td><input type="text" class="ipttxt" style="width:200px;" name="workPoolRel.operator_name"  id ="workPoolRel.operator_name"  value="${workPoolRel.operator_name }"  /></td>
		

		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" style="width:90px;" value="查询"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           style ="margin-                             right:10px;"/>
		</td>
		
		<th></th>
		<td>
	    <input class="comBtn" type="button" name="back" id="back" style="width:90px;" value="返回"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           style ="margin-                             right:10px;"/>
		</td>
	</tr>
 </table>
</div>
<input id="workerPool.pool_id" type="hidden" name="workerPool.pool_id" value="${workerPool.pool_id }" />
</form>
<div class="grid">
	<div class="searchformDiv">
	<a href="javascript:void(0);" name="addDig" style="margin-right:10px;" class="graybtn1" ><span>添加工号</span></a>
	<a href="javascript:void(0);" name="saveEdit" style="margin-right:10px;" class="graybtn1" ><span>保存修改</span></a>
</div>
<form method="POST" >
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell width="15%">工号池名称</grid:cell> 
	<grid:cell width="15%">工号</grid:cell> 
	<grid:cell width="15%">姓名</grid:cell> 
	<grid:cell width="8%">分配权重</grid:cell>
	<grid:cell width="8%">保有量</grid:cell>
	<grid:cell width="8%">已分配订单数</grid:cell>
	<grid:cell width="15%">操作</grid:cell> 
	</grid:header>

  <grid:body item="workerPoolRelObj">
        <grid:cell>${workerPoolRelObj.pool_name } </grid:cell>
        <grid:cell> ${workerPoolRelObj.operator_id }</grid:cell> 
        <grid:cell> ${workerPoolRelObj.operator_name }</grid:cell> 
        <grid:cell><input type="text" class="ipttxt" name="weight" style="width:60px;margin-top:6px;text-align: center" value = "${workerPoolRelObj.weight }" pool_id = "${workerPoolRelObj.pool_id }" operator_id ="${workerPoolRelObj.operator_id }" onblur="if(this.value == '')this.value='1';"/></grid:cell>
        <grid:cell> <input type="text" class="ipttxt" name="max_counts" style="width:60px;margin-top:6px;text-align: center" value = "${workerPoolRelObj.max_counts }" onblur="if(this.value == '')this.value='0';"/></grid:cell>
        <grid:cell> ${workerPoolRelObj.lock_counts }</grid:cell>
        <grid:cell>
         <a  href="javascript:void(0);" name="deleteWorkerPoolRel" pool_id="${workerPoolRelObj.pool_id}" operator_id ="${workerPoolRelObj.operator_id }"  >删除</a>
        </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>
<!-- 添加工号池关系页面 -->
<div id="addDig">
<div class="searchformDiv">
<table  border="0" cellpadding="0">
	<tr>
		<th>工号名称：</th><td>
		<input type="text" class="ipttxt" name="add_realname" id="add_realname" style="width:280px;margin-top:6px;" readonly="readonly"/>
		<input type="hidden" class="ipttxt" name="add_userid" id="add_userid" style="width:280px;margin-top:6px;" readonly="readonly"/>
		<input type="button" name="chooseUser" class="graybtn1" style="margin-left:20px;margin-top:6px;width:70px;" value="选择"  />
		</td>
		</tr>
		<tr>
		<th>分配权重：</th><td>
		<input type="text" class="ipttxt" value="默认为 1" style="width:280px;margin-top:6px;" readonly="readonly"/>
		</td>
	</tr>
	
	<tr>
		<th>保有量：</th><td>
		<input type="text" class="ipttxt" value="默认为 0 ，代表无限制" style="width:280px;margin-top:6px;" readonly="readonly"/>
		</td>
	</tr>

</table>
<div class="grid">
	<input type="button" id="addWorkerPoolRel" style="margin-left:90px;margin-top:6px;width:100px;" class="graybtn1" value="添加" />
	<input type="button" name="cancer" style="margin-left:100px;margin-top:6px;width:100px;" class="graybtn1" value="取消" />
	</div>
</div>
</div>
<input id="success" type="hidden" name="success" value="${success }" />

<!-- 选择工号页面 -->
<div id="open_choose_admin_user_window"></div>
<script>
$(function() {
	ecs_common.init();
});
var ecs_common = {
	init : function() {
		Eop.Dialog.init({id:"addDig",modal:true,title:"添加工号",width:'520px'});
		Eop.Dialog.init({id:"open_choose_admin_user_window",modal:true,title:"选择工号",height:"300px",width:"900px"});
		ecs_common.add_worker_pool_rel_windows();
		ecs_common.choose_worker_windows();
		ecs_common.add_worker();
		ecs_common.common_cancer();
		ecs_common.common_back();
		ecs_common.delete_worker_pool_rel();
		ecs_common.save_edit();
	},
	/**添加工号池关系弹出框*/	
	add_worker_pool_rel_windows : function() {
		$("a[name='addDig']").click(function(){
			$("#addDig").load();
			Eop.Dialog.open("addDig");
			return false;
		});
	},
	/**选择工号出框*/
	choose_worker_windows : function() {
		 $("[name = 'chooseUser']").click(function(){
			  var url = ctx + "/shop/admin/workerPoolAction!workerList.do?ajax=yes";
				Eop.Dialog.open("open_choose_admin_user_window");	
				$("#open_choose_admin_user_window").load(url,function(){
				
				});	
		});	   
	},
	/**添加工号按钮*/
	add_worker : function() {
		$("#addWorkerPoolRel").click(function(){
			userid = $("#add_userid").val();
			 if(!userid){
				alert("请先选择工号！");
				return;
				}
			 var pool_id = $("#success").val();
			 $.ajax({
					type : "post",
					async : false,
					url : ctx + "/shop/admin/workerPoolAction!addWorkerPoolRel.do?ajax=yes",
					data :{"workPoolRel.operator_id":userid,"workPoolRel.pool_id":pool_id},
					dataType : "json",
					success : function(data) {
						if(data.result == 0){
							$("#searchBtn").click();
						}else {
							alert("添加失败！");
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {

					}
				});  
	    });
	},
	/**取消按钮*/
	common_cancer : function() {
		$("[name='cancer']").click(function(){
			Eop.Dialog.close("addDig");
			$("#add_realname").val("");
			$("#add_userid").val("");
	    });
	},
	/**返回按钮*/
	common_back : function() {
		$("#back").click(function(){
			var url = ctx + "/shop/admin/workerPoolAction!workerPoolList.do";
			 window.location.href = url;
	    });
	},
	/**删除按钮*/
	delete_worker_pool_rel : function() {
		$("a[name='deleteWorkerPoolRel']").click(function(){
			if (!confirm("是否确认删除！")) {
				return;
			}
			pool_id = $(this).attr("pool_id");
			operator_id = $(this).attr("operator_id");
			
			$.ajax({
				type : "post",
				async : false,
				url : ctx + "/shop/admin/workerPoolAction!deleteWorkerPoolRel.do?ajax=yes",
				data :{"workPoolRel.pool_id":pool_id,"workPoolRel.operator_id":operator_id},
				dataType : "json",
				success : function(data) {
					if(data.result == 0){
						$("#searchBtn").click();
					}else {
						alert("删除失败！");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {

				}
			});  
	    });
	},
	/**保存修改*/
	save_edit : function() {
		$("[name='saveEdit']").click(function(){
			if($("[name='weight']").size() == 0){
				alert("无可操作项!");
				return;
			}
			 var groups = ''; 
			$("[name='weight']").each(function(){
				var pool_id = $(this).attr("pool_id");
				var operator_id = $(this).attr("operator_id");
				var weight = $(this).val();
				var max_counts = $(this).parents("tr").find("td input[name='max_counts']").val();
				groups += pool_id +"," +operator_id+","+weight+","+max_counts + "/";
			})
			   $.ajax({
				type : "post",
				async : false,
				url : ctx + "/shop/admin/workerPoolAction!editWorkerPoolRel.do?ajax=yes",
				data :{"groups":groups},
				dataType : "json",
				success : function(data) {
					if(data.result == 0){
						alert("保存修改成功");
						$("#searchBtn").click();
					}else {
						alert("保存修改失败");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {

				}
			});   
	    });
	}
	
}
</script>