<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="auth/checktree.css" />
<script type="text/javascript" src="../../mgWeb/menu.do"></script>
<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/workerPoolAction!workerPoolList.do'>
<div class="searchformDiv">
 <table>
	<tr>
	    <th>工号池名称：</th>
		<td><input type="text" class="ipttxt" style="width:200px;" name="workerPool.pool_name"  id ="workerPool.pool_name"  value="${workerPool.pool_name }"  /></td>

		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" style="width:90px;" value="查询"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           style ="margin-                             right:10px;"/>
		</td>
	</tr>
 </table>
</div>
</form>
<div class="grid">
	<div class="searchformDiv">
	<a href="javascript:void(0);" name="addDig" style="margin-right:10px;" class="graybtn1" ><span>添加工号池</span></a>
</div>
<form method="POST" >
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell width="15%">工号池ID</grid:cell> 
	<grid:cell width="15%">工号池名称</grid:cell> 
	<grid:cell width="10%">创建人</grid:cell> 
	<grid:cell width="10%">创建时间</grid:cell> 
	<grid:cell width="15%">预占时间（分钟）</grid:cell>
	<grid:cell width="10%">关联组织</grid:cell> 
	<grid:cell width="5%">状态</grid:cell>
	<grid:cell width="20%">操作</grid:cell> 
	</grid:header>

  <grid:body item="workerPoolObj">
        <grid:cell>${workerPoolObj.pool_id } </grid:cell>
        <grid:cell> ${workerPoolObj.pool_name }</grid:cell>
        <grid:cell> ${workerPoolObj.operator_name }</grid:cell> 
        <grid:cell> ${fn:substringBefore(workerPoolObj.create_time,".") == '' ? workerPoolObj.create_time : fn:substringBefore(workerPoolObj.create_time,".") }</grid:cell> 
        <grid:cell> ${workerPoolObj.lock_time }</grid:cell>
        <grid:cell> ${workerPoolObj.org_name }</grid:cell>
        <grid:cell> 
        	<c:if test="${workerPoolObj.status == '0' }">
        		有效
        	</c:if>
        	<c:if test="${workerPoolObj.status == '1' }">
        		失效
        	</c:if>
        </grid:cell>
        
        <grid:cell>
        <c:if test="${workerPoolObj.status == '0' }">
        		<a  href="javascript:void(0);" name="invalidateWorkerPool" pool_id="${workerPoolObj.pool_id}" >禁用</a>&nbsp;&nbsp;&nbsp;&nbsp;<span class='tdsper'></span>
        	</c:if>
        	<c:if test="${workerPoolObj.status == '1' }">
        		<a  href="javascript:void(0);" name="openWorkerPool" pool_id="${workerPoolObj.pool_id}" >启用</a>&nbsp;&nbsp;&nbsp;&nbsp;<span class='tdsper'></span>
        	</c:if>
        			&nbsp;&nbsp;&nbsp;
         <a  href="javascript:void(0);" name="editDig" pool_id="${workerPoolObj.pool_id}" pool_name="${workerPoolObj.pool_name}" lock_time="${workerPoolObj.lock_time}" org_name = "${workerPoolObj.org_name }" org_code = "${workerPoolObj.org_id }" >修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<span class='tdsper'></span>
 					&nbsp;&nbsp;&nbsp;
		  <a href="workerPoolAction!workPoolRelList.do?workerPool.pool_id=${workerPoolObj.pool_id }"  >工号管理</a>
        </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>
<input id="success" type="hidden" name="success" value="${success }" />
<input id="groups" type="hidden" name="groups" value="${groups }" />
<!-- 添加工号池页面 -->
<div id="addDig">
<div class="searchformDiv">
<table  border="0" cellpadding="0">
	<tr>
		<th>工号池名称：</th><td><input type="text" class="ipttxt"  name="add_pool_name" id="add_pool_name" style="width:280px;margin-top:6px;" onkeyup='this.value=this.value.replace(/\s+/g, "")'  /></td>
	</tr>
	<tr>
		<th>关联组织：</th><td>
		<input type="text" class="ipttxt" name="add_org_name" style="width:280px;margin-top:6px;" readonly="readonly"/>
		<input type="hidden" name="add_org_code" style="width:280px;margin-top:6px;" readonly="readonly"/>
		<input type="button" name="chooseOrganization" style="margin-left:20px;margin-top:6px;width:70px;" class="graybtn1" value="选择"  />
		</td>
		</tr>
	<tr>
		<th>订单预占时间：</th><td><input type="text" class="ipttxt"  name="add_lock_time" id="add_lock_time"  style="width:280px;margin-top:6px;" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"  /><span style="color: #666666">&nbsp;&nbsp;分钟&nbsp;&nbsp;&nbsp;0代表无限制</span></td>
	</tr>

</table>
<div class="grid">
	<input type="button" id="addWorkerPool" style="margin-left:90px;margin-top:6px;width:100px;" class="graybtn1" value="添加" />
	<input type="button" name="cancer" style="margin-left:100px;margin-top:6px;width:100px;" class="graybtn1" value="取消" />
</div>
</div>
</div>

<!-- 修改工号池页面 -->
<div id="editDig">
<div class="searchformDiv">
<table  border="0" cellpadding="0">
	<tr>
		<th>工号池名称：</th><td><input type="text" class="ipttxt"  name="edit_pool_name" id="edit_pool_name" style="width:280px;margin-top:6px;" onkeyup='this.value=this.value.replace(/\s+/g, "")'/></td>
	</tr>
	<tr>
		<th>关联组织：</th><td>
		<input type="text" class="ipttxt"  name="add_org_name" style="width:280px;margin-top:6px;" readonly="readonly"/>
		<input type="hidden" name="add_org_code" style="width:280px;margin-top:6px;" readonly="readonly"/>
		<input type="button" name="chooseOrganization" class="graybtn1" style="margin-left:20px;margin-top:6px;width:70px;" value="选择"  />
		</td>
		</tr> 
	<tr>
		<th>订单预占时间：</th><td><input type="text" class="ipttxt"  name="edit_lock_time" id="edit_lock_time"  style="width:280px;margin-top:6px;"  onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/><span style="color: #666666">&nbsp;&nbsp;分钟&nbsp;&nbsp;&nbsp;0代表无限制</span></td>
	</tr>

</table>
<div class="grid">
	<input type="button" id="editWorkerPool" style="margin-left:90px;margin-top:6px;width:100px;" class="graybtn1" value="修改" />
	<input type="button" name="cancer" style="margin-left:100px;margin-top:6px;width:100px;" class="graybtn1" value="取消" />
	<input type="hidden" id="editWorkerPoolId" />
</div>
</div>
</div>
<!-- 关联组织页面 -->
<div id="open_choose_organization_window"></div>
<script>
	$(function() {
		ecs_common.init();
	});
	var ecs_common = {
		init : function() {
			Eop.Dialog.init({id:"addDig",modal:true,title:"添加工号池配置",width:'520px'});
			Eop.Dialog.init({id:"editDig",modal:true,title:"修改工号池配置",width:'520px'});
			Eop.Dialog.init({id:"open_choose_organization_window",modal:true,title:"选择关联组织",height:"300px",width:"900px"});
			ecs_common.add_worker_pool_windows();
			ecs_common.edit_worker_pool_windows();
			ecs_common.open_organization_windows();
			ecs_common.add_worker_pool();
			ecs_common.edit_worker_pool();
			ecs_common.common_cancer();
			ecs_common.invalidate_worker_pool();
			ecs_common.open_worker_pool();
		},
		/**添加工号池弹出框*/	
		add_worker_pool_windows : function() {
			$("a[name='addDig']").click(function(){
				$("#addDig").load();
				Eop.Dialog.open("addDig");
				return false;
			});
		},
		/**修改工号池弹出框*/	
		edit_worker_pool_windows : function() {
			$("a[name='editDig']").click(function(){
				$("#editWorkerPoolId").val($(this).attr("pool_id"));
				$("#edit_pool_name").val($(this).attr("pool_name"));
				$("#edit_lock_time").val($(this).attr("lock_time"));
				$("[name = 'add_org_code']").val($(this).attr("org_code"));
				$("[name = 'add_org_name']").val($(this).attr("org_name"));
				$("#editDig").load();
				Eop.Dialog.open("editDig");
				return false;
			});
		},
		/**关联组织弹出框*/	
		open_organization_windows : function() {
			   $("[name = 'chooseOrganization']").click(function(){
				   var url = ctx + "/shop/admin/workerPoolAction!workerOrganizationList.do?ajax=yes";
					Eop.Dialog.open("open_choose_organization_window");	
					$("#open_choose_organization_window").load(url,function(){
					
					});	
				});	
		},
		/**添加工号池按钮*/
		add_worker_pool : function() {
			$("#addWorkerPool").click(function(){
				var pool_name = $("#add_pool_name").val();
				var lock_time = $("#add_lock_time").val();
				var org_id = $("[name = 'add_org_code']").val();
				 if(!pool_name || !lock_time || !org_id){
						alert("数据填写不完善！")
						return;
					}
				 $.ajax({
						type : "post",
						async : false,
						url : ctx + "/shop/admin/workerPoolAction!addWorkerPool.do?ajax=yes",
						data :{"workerPool.pool_name":pool_name,"workerPool.lock_time":lock_time,"workerPool.org_id":org_id},
						dataType : "json",
						success : function(data) {
							if(data.result == 0){
								alert("添加成功");
								$("#searchBtn").click();
							}else {
								alert("添加失败");
							}
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {

						}
					});  
		    });
		},
		/**修改工号池按钮*/
		edit_worker_pool : function() {
			$("#editWorkerPool").click(function(){
				var pool_name = $("#edit_pool_name").val();
				var lock_time = $("#edit_lock_time").val();
				var org_id = $("[name = 'add_org_code']").val();
				var pool_id = $("#editWorkerPoolId").val();
				 if(!pool_name || !lock_time || !org_id){
						alert("数据填写不完善！")
						return;
					}
				 $.ajax({
						type : "post",
						async : false,
						url : ctx + "/shop/admin/workerPoolAction!editWorkerPool.do?ajax=yes",
						data :{"workerPool.pool_name":pool_name,"workerPool.lock_time":lock_time,"workerPool.pool_id":pool_id,"workerPool.org_id":org_id},
						dataType : "json",
						success : function(data) {
							if(data.result == 0){
								alert("修改成功");
								$("#searchBtn").click();
							}else {
								alert("修改失败");
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
				Eop.Dialog.close("editDig");
				Eop.Dialog.close("addDig");
		    });
		},
		/**禁用按钮*/
		invalidate_worker_pool : function() {
			$("a[name='invalidateWorkerPool']").click(function(){
				if (!confirm("确认禁用此工号池！")) {
					return;
				}
				pool_id = $(this).attr("pool_id");
				 $.ajax({
						type : "post",
						async : false,
						url : ctx + "/shop/admin/workerPoolAction!invalidateWorkerPool.do?ajax=yes",
						data :{"workerPool.pool_id":pool_id},
						dataType : "json",
						success : function(data) {
							if(data.result == 0){
								alert("禁用成功");
								$("#searchBtn").click();
							}else {
								alert("禁用失败");
							}
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {

						}
					});  
		    });
		},
		/**启用按钮*/
		open_worker_pool : function() {
			$("a[name='openWorkerPool']").click(function(){
				if (!confirm("确认启用此工号池！")) {
					return;
				}
				pool_id = $(this).attr("pool_id");
				 $.ajax({
						type : "post",
						async : false,
						url : ctx + "/shop/admin/workerPoolAction!openWorkerPool.do?ajax=yes",
						data :{"workerPool.pool_id":pool_id},
						dataType : "json",
						success : function(data) {
							if(data.result == 0){
								alert("启用成功");
								$("#searchBtn").click();
							}else {
								alert("启用失败");
							}
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {

						}
					});  
		    });
		}
		
	}
</script>